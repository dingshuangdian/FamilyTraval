package com.kingtangdata.inventoryassis.activity.set;

import android.app.AlertDialog;
import android.app.Dialog;
import android.app.Service;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.IBinder;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.TextView;

import com.kingtangdata.inventoryassis.R;
import com.kingtangdata.inventoryassis.base.BaseActivity;
import com.kingtangdata.inventoryassis.util.StorageUtils;
import com.kingtangdata.inventoryassis.version.VersionBinder;
import com.kingtangdata.inventoryassis.version.VersionService;

/**
 * 基础设置类
 * 
 * @author Administrator
 * 
 */
public class ActivitySet extends BaseActivity {
	
	Intent serviceIntent = null;
	
	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		super.setContentView(R.layout.activity_set);
		super.setTopLabel("系统设置");
		
		serviceIntent = new Intent(ActivitySet.this, VersionService.class);
	}

	public void setIp(View view) {
		startActivity(new Intent(this,ActivitySetIP.class));
	}

	public void doFeedBack(View view) {
//	    //发送对象
//        String to = "leo.li@kingtangdata.com";  
//        //抄送对象
//        String cc = "crazyly520@163.com";  
//        String subject = "盘点易问题反馈";  
//        Uri uri = Uri.parse("mailto:leo.li@kingtangdata.com");
//        // 创建Intent  
//        Intent emailIntent = new Intent(android.content.Intent.ACTION_SENDTO,uri);  
//        //设置内容类型  
//        emailIntent.setType("plain/text");  
//        //设置额外信息  
//	    //emailIntent.putExtra(android.content.Intent.EXTRA_EMAIL,new String[] { to });  
//	    emailIntent.putExtra(android.content.Intent.EXTRA_SUBJECT,subject);  
//	    emailIntent.putExtra(android.content.Intent.EXTRA_CC,cc);  
//	    //启动Activity  
//	    startActivity(Intent.createChooser(emailIntent, "发送邮件..."));  
	
		// 必须明确使用mailto前缀来修饰邮件地址,如果使用

		// intent.putExtra(Intent.EXTRA_EMAIL, email)，结果将匹配不到任何应用
		Uri uri = Uri.parse("mailto:4001004168@kingtangdata.com");
		Intent intent = new Intent(Intent.ACTION_SENDTO, uri);
		intent.putExtra(Intent.EXTRA_SUBJECT, "盘点易问题反馈"); // 主题
		intent.putExtra(Intent.EXTRA_TEXT, "请写下您的宝贵意见"); // 正文
		startActivity(Intent.createChooser(intent, "邮件发送"));
	}

	public void doCheckVersion(View view) {
		startService(serviceIntent);
	}

	public void doAbout(View view) {
		startActivity(new Intent(this,ActivityAbout.class));
	}

	public void doCall(View view) {
		showBottomOperationMenu() ;
	}
	
	ServiceConnection connection = new ServiceConnection() {
		VersionBinder mBinder;
		@Override
		public void onServiceDisconnected(ComponentName name) {
			
			mBinder= null;
		}
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			
			mBinder = (VersionBinder)service;
			mBinder.setActivity(ActivitySet.this);
		}
	};
	
	
	
	@Override
	protected void onResume() {
		
		super.onResume();
		//绑定service
		getApplicationContext().bindService(serviceIntent,connection, Service.BIND_AUTO_CREATE);
	}

	@Override
	protected void onDestroy() {
		
		super.onDestroy();
		//解除service绑定
		getApplicationContext().unbindService(connection);
		//销毁service
		stopService(serviceIntent);
	}

//	@Override
//	public void onBackPressed() {
//		
//		Builder builder = new AlertDialog.Builder(this);
//		builder.setTitle("提示");
//		builder.setMessage("确认要退出应用？");
//		builder.setNegativeButton("取消", null);
//		builder.setNeutralButton("确定", new DialogInterface.OnClickListener() {
//			@Override
//			public void onClick(DialogInterface dialog, int which) {
//				
//				finish();
//			}
//		});
//		builder.create().show();
//	}
	
	@Override
	public void onBackPressed() {
		
		if(flag){
			finish();
		}else{
			makeText("再按一次退出程序");
			flag = true ;
			handler.postDelayed(thread, 2000);
		}
	}
	
	boolean flag = false;
	
	private Handler handler = new Handler(){};
	
	private Thread thread = new Thread(){
		public void run() {
			flag = !flag;
		}
	};
	
	/**
	 * 显示底部call菜单
	 */
	public void showBottomOperationMenu() {
		View view = View.inflate(getApplicationContext(),R.layout.ui_call_menu, null);
		TextView tvNumber = (TextView) view.findViewById(R.id.tv_number);
		tvNumber.setText("4001004168");
		final Dialog call_menu = new AlertDialog.Builder(this).create();
		call_menu.show();
		call_menu.getWindow().setWindowAnimations(R.style.PopupAnimation);
		view.findViewById(R.id.imgV_call).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						
						Intent intent = new Intent(Intent.ACTION_CALL, Uri.parse("tel:4001004168"));
						startActivity(intent);
						call_menu.dismiss();
					}
				});
		view.findViewById(R.id.imgV_cancel).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						
						call_menu.dismiss();
					}
				});
		Window window = call_menu.getWindow();
		WindowManager.LayoutParams wl = window.getAttributes();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int widthPixels = dm.widthPixels;
		int heightPixels = dm.heightPixels;
		wl.y = 0;
		wl.y += heightPixels / 2 - view.getHeight() / 2;
		// 对话框宽度
		wl.width = widthPixels;
		window.setAttributes(wl);
		window.setContentView(view);
	}
	
	/**
	 * 显示底部退出菜单
	 */
	public void showExitOperationMenu() {
		View view = View.inflate(getApplicationContext(),
				R.layout.hyt_exit_menu, null);
		final Dialog call_menu = new AlertDialog.Builder(this).create();
		call_menu.show();
		call_menu.getWindow().setWindowAnimations(R.style.PopupAnimation);

		view.findViewById(R.id.bt_confirm).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						doLogout();
					}
				});

		view.findViewById(R.id.bt_cancel).setOnClickListener(
				new OnClickListener() {
					@Override
					public void onClick(View v) {
						call_menu.dismiss();
					}
				});

		Window window = call_menu.getWindow();
		WindowManager.LayoutParams wl = window.getAttributes();
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		int widthPixels = dm.widthPixels;
		int heightPixels = dm.heightPixels;
		wl.y = 0;
		wl.y += heightPixels / 2 - view.getHeight() / 2;
		// 对话框宽度
		wl.width = widthPixels;
		window.setAttributes(wl);
		window.setContentView(view);
	}
	
	public void doExit(View view){
		showExitOperationMenu();
	}
	
	// 注销用户
	private void doLogout() {
		// 清除所有登陆信息
		StorageUtils.clearAll(this);
		
		//stopService(new Intent(ActivitySet.this, MsgService.class));

		// 注销成功
		setResult(RESULT_OK);
		finish();
	}
}