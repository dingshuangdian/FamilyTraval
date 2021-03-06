/**
 * 
 */
package com.kingtangdata.inventoryassis.version;

import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStream;
import java.math.BigDecimal;
import java.net.HttpURLConnection;
import java.net.URL;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Handler;
import android.os.IBinder;
import android.os.Message;
import android.view.View;
import android.widget.RemoteViews;

import com.kingtangdata.inventoryassis.R;
import com.kingtangdata.inventoryassis.base.AsyncReqTask;
import com.kingtangdata.inventoryassis.http.HttpConstants;
import com.kingtangdata.inventoryassis.http.HttpResponseCode;
import com.kingtangdata.inventoryassis.http.business.SystemProvider;
import com.kingtangdata.inventoryassis.http.domain.BaseReq;
import com.kingtangdata.inventoryassis.http.domain.BaseRes;
import com.kingtangdata.inventoryassis.http.domain.VersionRes;
import com.kingtangdata.inventoryassis.util.LogUtils;
import com.kingtangdata.inventoryassis.util.SDCardUtil;

/**
 * <p>
 * Description:软件版本检查及下载，
 * 
 * </p>
 * 
 * @author liyang
 * 
 */
public class VersionService extends Service {
	private Activity mActivity;
	
	private VersionHandler handler;
	private VersionTask task;
	private File file = null;
	private String url = "";
	
	//是否正在下载
	private boolean isDownloading = false;
	
	@Override
	public void onCreate() {
		// TODO Auto-generated method stub
		super.onCreate();
		//初始化文件对象
		String apkName = getResources().getString(R.string.app_name);
		file = new File(SDCardUtil.getRootPath(), apkName +".apk");
	}
	
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	private LocalBinder mBinder = new LocalBinder();
	public class LocalBinder extends  VersionBinder{
		@Override
		public void setActivity(Activity mActivity) {
			// TODO Auto-generated method stub
			VersionService.this.mActivity = mActivity;
		}
	}
	
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		// TODO Auto-generated method stub
		if(handler == null){
			handler = new VersionHandler(mActivity);
		}
		//正在下载的时候不重复进行下载
		if(!isDownloading){
			// 检查软件版本
			task = new VersionTask(mActivity);	
			task.execute(new BaseReq());
		}else{
			handler.obtainMessage(VersionHandler.APK_UPDATEING).sendToTarget();
		}
		
		return super.onStartCommand(intent, flags, startId);
	}
	
	private class VersionTask extends AsyncReqTask{

		public VersionTask(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
			
			isDownloading = true;
		}

		@Override
		protected BaseRes doRequest(BaseReq request) {
			// TODO Auto-generated method stub
			return SystemProvider.getInstance(mActivity).checkVersion(request);
		}

		@Override
		protected void handleResponse(BaseRes response) {
			// TODO Auto-generated method stub
			try {
				VersionRes res = (VersionRes)response;
				// 如果检测成功
				if (HttpResponseCode.SUCCESS.equals(res.getCode())) {
					url = res.getUrl();
					
					PackageManager pm = getPackageManager();
					PackageInfo pi = pm.getPackageInfo(getPackageName(), 0);
					float serviceVersion = Float.parseFloat(res.getVersion());
					float localVersion = pi.versionCode;
					if (serviceVersion > localVersion) {
						showUpdateDialog(res);
					} else {
						isDownloading =false;
						handler.obtainMessage(VersionHandler.APK_UPDATE_NONEED).sendToTarget();
					}
				}else{
					isDownloading = false;
					handler.obtainMessage(VersionHandler.APK_UPDATE_DESC, res.getDesc()).sendToTarget();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				isDownloading = false;
				handler.obtainMessage(VersionHandler.APK_UPDATE_FAIL).sendToTarget();
			}
		}
	}
	
	private class DowanloadTask extends AsyncReqTask{
		public DowanloadTask(Context context) {
			super(context);
			// TODO Auto-generated constructor stub
		}
		
		@Override
		protected void onPreExecute() {
			// TODO Auto-generated method stub
			//发送通知
			showNotification();
		}
		
		@Override
		protected BaseRes doRequest(BaseReq request) {
			// TODO Auto-generated method stub
			//开始下载
			downloadApp();
			
			//执行完成后将下载标志设为初始状态
			isDownloading = false;
			return null;
		}

		@Override
		protected void handleResponse(BaseRes response) {
			// TODO Auto-generated method stub
			
		}
	}
	
	/**
	 * 显示升级提示
	 * @param desc
	 */
	private void showUpdateDialog(final VersionRes res) {
		AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
		builder.setTitle("升级提示");
		builder.setMessage(res.getDesc());
		builder.setPositiveButton("立刻升级",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int which) {
				DowanloadTask task  = new DowanloadTask(mActivity);
				task.execute(new BaseReq());
			}
		});
		builder.setNegativeButton("取消",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int which) {
				isDownloading = false;
				
				dialog.cancel();
			}
		});
		builder.create().show();
	}
	
	/**
	 * 显示安装提示
	 * @param desc
	 */
	private void showInstallDialog() {
		AlertDialog.Builder builder = new AlertDialog.Builder(mActivity);
		builder.setTitle("安装提示");
		builder.setMessage(DOWNLOAD_WAIT_TIPS);
		builder.setPositiveButton("确定",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int which) {
				install(file);
			}
		});
		builder.setNegativeButton("取消",new DialogInterface.OnClickListener() {
			public void onClick(DialogInterface dialog,int which) {
				dialog.cancel();
			}
		});
		builder.create().show();
	}
	
	/**
	 * 启动安装界面
	 * @param f
	 */
	private void install(File f) {
		Intent intent = new Intent();
		intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
		intent.setAction(android.content.Intent.ACTION_VIEW);
		String type = getMIMEType(f);
		intent.setDataAndType(Uri.fromFile(f), type);
		mActivity.startActivity(intent);
	}
	
	//通知
	private NotificationManager mNotificationManager;
	private Notification mNotification;
	
	private String DOWNLOAD_APP_START = "正在下载盘点易最新版本";
	private String DOWNLOAD_SUCCESS = "盘点易最新版本下载成功";
	private String DOWNLOAD_SUCCESS_TIP = "盘点易最新版本下载成功，马上点击安装";
	private String DOWNLOAD_WAIT_TIPS="确定要安装盘点易最新版本吗？";

	private void showNotification() {
		mNotificationManager = (NotificationManager) mActivity.getSystemService(Context.NOTIFICATION_SERVICE);
		// 2. Instantiate the Notification
		long when = System.currentTimeMillis();
		mNotification = new Notification(R.drawable.downloading, DOWNLOAD_APP_START, when);
		// 3.set the contentView
		// 是否在点击一次以后，自动清除安装通知
		mNotification.flags = Notification.FLAG_AUTO_CANCEL;
		mNotification.contentView = new RemoteViews(mActivity.getPackageName(),R.layout.ui_notification);
	}
	
	private Handler pHandler = new Handler() {
		@Override
		public void handleMessage(Message msg) {
			int integer = msg.what;
			
			LogUtils.logd(getClass(), "integer = " + integer);
			//开始下载
			mNotification.contentView.setTextViewText(R.id.notification_title, DOWNLOAD_APP_START);
			if (integer == 100) {
				mNotification.tickerText = DOWNLOAD_SUCCESS_TIP;
				mNotification.contentView.setTextViewText( R.id.notification_label, DOWNLOAD_SUCCESS);
				mNotification.contentView.setViewVisibility(R.id.notification_value, View.INVISIBLE);
			} else {
				mNotification.contentView.setTextViewText( R.id.notification_label,"下载进度...");
				mNotification.contentView.setViewVisibility(R.id.notification_value, View.VISIBLE);
				mNotification.contentView.setTextViewText(R.id.notification_value,"" + integer + "%");
			}
			
			// 准备安装新的版本软件
			if (integer == 100) {
				Intent intent = new Intent();
				intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
				intent.setAction(android.content.Intent.ACTION_VIEW);
				String type = getMIMEType(file);
				intent.setDataAndType(Uri.fromFile(file), type);
				// PendingIntent
				mNotification.contentIntent = PendingIntent.getActivity(mActivity,R.string.app_name, intent,PendingIntent.FLAG_UPDATE_CURRENT);
				mNotification.icon = R.drawable.ic_launcher;
				mNotification.setLatestEventInfo(mActivity, DOWNLOAD_SUCCESS, "马上点击安装",mNotification.contentIntent);
			}
			
			mNotification.contentView.setProgressBar(R.id.ProgressBar01, 100,integer, false);
			mNotificationManager.notify(1001, mNotification);
		}
	};
	
	private Handler mHandler = new Handler(){
		public void handleMessage(Message msg) {
			showInstallDialog();
		}
	};
	
	private String getMIMEType(File f) {
		String type = "";
		String fName = f.getName();
		String end  =   fName.substring(fName.lastIndexOf(".") + 1, fName.length()).toLowerCase();

		if (end.equals("m4a") || end.equals("mp3") || end.equals("mid")
				|| end.equals("xmf") || end.equals("ogg") || end.equals("wav")) {
			type = "audio";
		} else if (end.equals("3gp") || end.equals("mp4")) {
			type = "video";
		} else if (end.equals("jpg") || end.equals("gif") || end.equals("png")
				|| end.equals("jpeg") || end.equals("bmp")) {
			type = "image";
		} else if (end.equals("apk")) {
			type = "application/vnd.android.package-archive";
		} else {
			type = "*";
		}

		if (end.equals("apk")) {
		} else {
			type += "/*";
		}
		return type;
	}
	
	/**
	 * 显示软件升级提示对话框
	 * 
	 * @param checkResult
	 * @description 是否要升级软件
	 */
	private void downloadApp() {
		try {
			// 创建文件夹
			doCreateFolder();
			// 如果存在apk的话先删除
			doDeleteApp();
			// 开始下载
			doDownloadTheFile(url);
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

	/**
	 * 向sdcard写文件夹
	 */
	private void doCreateFolder() {
		try {
			// 目录
			File path = new File(SDCardUtil.getRootPath());
			// 文件
			if (!path.exists()) {
				// 2.创建目录，可以在应用启动的时候创建
				path.mkdirs();
			}
		} catch (Exception e) {
			LogUtils.loge(getClass(), LogUtils.getStackTrace(e));
		}
	}

	/**
	 * 清理工作, 删除旧的下载的临时文件
	 */
	private void doDeleteApp() {
		String apkName = getResources().getString(R.string.app_name);
		File file = new File(SDCardUtil.getRootPath(), "/" + apkName + ".apk");
		if (file != null && file.exists()) {
			file.delete();
		}
	}

	@Override
	public void onRebind(Intent intent) {
		super.onRebind(intent);
	}

	@Override
	public boolean onUnbind(Intent intent) {
		if (task != null && !task.isCancelled()) {
			task.cancel(true);
		}
		stopSelf();
		return true;
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		if (task != null && !task.isCancelled()) {
			task.cancel(true);
		}
		stopSelf();
	}
	
	
	private void doDownloadTheFile(String apkUrl)throws Exception {
		HttpURLConnection conn = null;
		InputStream is = null;
		FileOutputStream fos = null;

		int apkTotalSize = 0;
		try {
			if (!file.exists()) {
				file.createNewFile();
			}

			URL myURL = new URL(apkUrl);
			conn = (HttpURLConnection) myURL.openConnection();

			conn.setConnectTimeout(HttpConstants.CONNECT_TIME_OUT);
			conn.setReadTimeout(HttpConstants.READ_TIME_OUT);
			
			apkTotalSize = conn.getContentLength();

			if (conn.getResponseCode() == 404) {
				handler.obtainMessage(VersionHandler.APK_UPDATE_FAIL).sendToTarget();
				return;
			}

			is = conn.getInputStream();
			if (is == null) {
				handler.obtainMessage(VersionHandler.APK_UPDATE_FAIL).sendToTarget();
			}

			fos = new FileOutputStream(file);

			int downloadedSize = 0;
			int downloadedPercent = 0;

			byte buf[] = new byte[4096];
			
			int numread = 0 ;
			while((numread = is.read(buf))!=-1){
				if (numread <= 0) {
					break;
				}
				fos.write(buf, 0, numread);
				downloadedSize += numread;
				
				if(downloadedSize == apkTotalSize){
					pHandler.obtainMessage(100).sendToTarget();
					mHandler.obtainMessage().sendToTarget();
				}else if(div(downloadedSize, apkTotalSize) - downloadedPercent >= 5){
					downloadedPercent = div(downloadedSize, apkTotalSize);
					pHandler.obtainMessage(downloadedPercent).sendToTarget();
				}
			}
		} catch (Exception e) {
			handler.obtainMessage(VersionHandler.APK_UPDATE_FAIL).sendToTarget();
		} finally {
			if (conn != null) {
				conn.disconnect();
			}

			if (is != null) {
				is.close();
			}

			if (fos != null) {
				fos.close();
			}
		}
	}
	
	/**
	 * 获取百分比
	 * @param x
	 * @param total
	 * @return
	 */
	 public int div(int v1, int v2) {
		  BigDecimal b1 = new BigDecimal(Double.toString(v1));
		  BigDecimal b2 = new BigDecimal(Double.toString(v2));
		  double d = b1.divide(b2, 2, BigDecimal.ROUND_HALF_UP).doubleValue();
		  return (int)(d*100);
	 }  
}
