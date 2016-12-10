package com.zxing.activity;

import java.io.IOException;
import java.util.List;
import java.util.Vector;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.res.AssetFileDescriptor;
import android.graphics.Bitmap;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.MediaPlayer.OnCompletionListener;
import android.os.Bundle;
import android.os.Handler;
import android.os.Vibrator;
import android.util.Log;
import android.view.SurfaceHolder;
import android.view.SurfaceHolder.Callback;
import android.view.SurfaceView;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Result;
import com.kingtangdata.inventoryassis.R;
import com.kingtangdata.inventoryassis.activity.ActivityCheckTask;
import com.kingtangdata.inventoryassis.activity.ActivityCheckTaskForm;
import com.kingtangdata.inventoryassis.activity.ActivityPKForm;
import com.kingtangdata.inventoryassis.activity.ActivityPY;
import com.kingtangdata.inventoryassis.activity.ActivityPYForm;
import com.kingtangdata.inventoryassis.base.BaseActivity;
import com.kingtangdata.inventoryassis.bean.Plan;
import com.kingtangdata.inventoryassis.db.PlanManager;
import com.zxing.camera.CameraManager;
import com.zxing.decoding.CaptureActivityHandler;
import com.zxing.decoding.InactivityTimer;
import com.zxing.view.ViewfinderView;
/**
 * Initial the camera
 * @author Ryan.Tang
 */
public class CaptureActivity extends BaseActivity implements Callback {

	private CaptureActivityHandler handler;
	private ViewfinderView viewfinderView;
	private boolean hasSurface;
	private Vector<BarcodeFormat> decodeFormats;
	private String characterSet;
	private InactivityTimer inactivityTimer;
	private MediaPlayer mediaPlayer;
	private boolean playBeep;
	private static final float BEEP_VOLUME = 0.10f;
	private boolean vibrate;
//	private Button cancelScanButton;
//	private Button btnFlash;	
	private boolean statusFlag = false;
	
	private Plan plan = null;
	private String code = "";	
	private String function = "";

	/** Called when the activity is first created. */
	@Override
	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.camera);
	    super.setTopLabel("扫码");
	    super.setLeftButtonText("返回");
	    super.setRightButtonText("闪光灯");
		//ViewUtil.addTopView(getApplicationContext(), this, R.string.scan_card);
		CameraManager.init(getApplication());
		viewfinderView = (ViewfinderView) findViewById(R.id.viewfinder_view);
//		cancelScanButton = (Button) this.findViewById(R.id.btn_cancel_scan);
//		btnFlash = (Button) this.findViewById(R.id.btn_flash);
		hasSurface = false;
		inactivityTimer = new InactivityTimer(this);
		
		if (this.getIntent().hasExtra("function")) {
			function = this.getIntent().getStringExtra("function");
		}
	}

	@Override
	public void doClickLeftBtn() {
		if (function.equals("")) setResult(RESULT_OK);
		this.finish();
	}

	@Override
	public void doClickRightBtn() {
		if (statusFlag) {
			//关闪光灯
			CameraManager.get().offLight();
			statusFlag = false;
		} else {
			//开闪光灯
			CameraManager.get().openLight();
			statusFlag = true;
		}
	}

	@Override
	protected void onResume() {
		super.onResume();
		SurfaceView surfaceView = (SurfaceView) findViewById(R.id.preview_view);
		SurfaceHolder surfaceHolder = surfaceView.getHolder();
		if (hasSurface) {
			initCamera(surfaceHolder);
		} else {
			surfaceHolder.addCallback(this);
			/**
			 * SURFACE_TYPE_PUSH_BUFFERS表明该Surface不包含原生数据，Surface用到的数据由其他对象提供，
			 * 在Camera图像预览中就使用该类型的Surface，有Camera负责提供给预览Surface数据，这样图像预览会比较流畅。
			 */
			surfaceHolder.setType(SurfaceHolder.SURFACE_TYPE_PUSH_BUFFERS);
		}
		decodeFormats = null;
		characterSet = null;

		playBeep = true;
		AudioManager audioService = (AudioManager) getSystemService(AUDIO_SERVICE);
		if (audioService.getRingerMode() != AudioManager.RINGER_MODE_NORMAL) {
			playBeep = false;
		}
		initBeepSound();
		vibrate = true;
		
//		//quit the scan view
//		cancelScanButton.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				CaptureActivity.this.finish();
//			}
//		});
//		
//		btnFlash.setOnClickListener(new OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				if (statusFlag) {
//					//关闪光灯
//					CameraManager.get().offLight();
//					statusFlag = false;
//				} else {
//					//开闪光灯
//					CameraManager.get().openLight();
//					statusFlag = true;
//				}
//			}
//		});		
	}

	@Override
	protected void onPause() {
		super.onPause();
		if (handler != null) {
			handler.quitSynchronously();
			handler = null;
		}
		CameraManager.get().closeDriver();
	}

	@Override
	protected void onDestroy() {
		inactivityTimer.shutdown();
		super.onDestroy();
	}
	
	/**
	 * Handler scan result
	 * @param result
	 * @param barcode
	 */
	public void handleDecode(Result result, Bitmap barcode) {
		inactivityTimer.onActivity();
		playBeepSoundAndVibrate();
		String resultString = result.getText();
		//FIXME
		if (resultString.equals("")) {
			Toast.makeText(CaptureActivity.this, "Scan failed!", Toast.LENGTH_SHORT).show();
		}else {
			if (function.equals("bind")) {			
				Intent resultIntent = new Intent();
				Bundle bundle = new Bundle();
				bundle.putString("value", resultString);
				resultIntent.putExtras(bundle);
				this.setResult(RESULT_OK, resultIntent);
				CaptureActivity.this.finish();
			} else {			
				handleDecodeInternally(result, barcode);
			}
		}
//		CaptureActivity.this.finish();
	}
	
	// Put up our own UI for how to handle the decoded contents.
	private void handleDecodeInternally(Result rawResult, Bitmap barcode) {
		Log.d("DEBUG", "TEXT = " + rawResult.getText());

		code = rawResult.getText();
		// 开始根据扫描到的rfid数据从数据库查找对应的记录
		StringBuffer buffer = new StringBuffer();
		buffer.append("select * from plans  where label_code = ?");
		List<Plan> plans = PlanManager.getInstance(this).getPlans(buffer, new String[] { code });

		// 不管找到几条符合记录 就只取第一条
		if (!plans.isEmpty()) {
			plan = plans.get(0);

			if ("py".equals(plan.getCheck_result())) {
				Intent i = new Intent(this, ActivityPYForm.class);
				i.putExtra("plan", plan);
				i.putExtra("type", ActivityPY.PY_MODIFY);
				startActivity(i);
			}

			// 找到的记录是已经盘亏过的记录则询问是否正常盘点
			else if ("pk".equals(plan.getCheck_result())) {
				showDialog(0);
			}

			// 正常盘点
			else {
				Intent i = new Intent(this, ActivityCheckTaskForm.class);
				i.putExtra("plan", plan);
				i.putExtra("type", ActivityCheckTask.PD_CHECK);
				startActivity(i);
			}
		} else {
			showDialog(1);
		}
	}
	  
	@Override
	protected Dialog onCreateDialog(int id) {
		if (id == 0) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("温馨提示");
			builder.setMessage("该设备已经存在盘亏记录，是否需要重新盘点？");
			builder.setNeutralButton("是", new LeftListener());
			builder.setNegativeButton("否", new RightListener());
			return builder.create();
		} else if (id == 1) {
			AlertDialog.Builder builder = new AlertDialog.Builder(this);
			builder.setTitle("温馨提示");
			builder.setMessage("没有找到匹配的标签,是否需要盘盈?");
			builder.setNeutralButton("是", new AddListener());
			builder.setNegativeButton("否", new NotAddListener());
			return builder.create();
		} else {
			return super.onCreateDialog(id);
		}
	}

	/**
	 * 盘亏时进行重新盘点的监听事件
	 */
	class LeftListener implements DialogInterface.OnClickListener {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			Intent i = new Intent(CaptureActivity.this, ActivityCheckTaskForm.class);
			i.putExtra("plan", plan);
			i.putExtra("type", ActivityCheckTask.PD_CHECK);
			startActivity(i);
		}
	}

	/**
	 * 盘亏时不进行重新盘点的监听事件
	 */
	class RightListener implements DialogInterface.OnClickListener {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(CaptureActivity.this, ActivityPKForm.class);
			intent.putExtra("plan", plan);
			startActivity(intent);
		}
	}

	/**
	 * 确认盘盈的按钮监听
	 */
	class AddListener implements DialogInterface.OnClickListener {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			Intent intent = new Intent(CaptureActivity.this, ActivityPYForm.class);
			intent.putExtra("type", ActivityPY.PY_ADD);
			intent.putExtra("rfid", code);
			startActivity(intent);
		}
	}

	/**
	 * 不进行盘盈的按钮监听
	 */
	class NotAddListener implements DialogInterface.OnClickListener {
		@Override
		public void onClick(DialogInterface dialog, int which) {
			// TODO Auto-generated method stub
			onPause();
			onResume();
		}
	}
	
	@Override
	public void onBackPressed() {
		if (function.equals("")) setResult(RESULT_OK);		
		finish();
	}
	
	private void initCamera(SurfaceHolder surfaceHolder) {
		try {
			CameraManager.get().openDriver(surfaceHolder);
		} catch (IOException ioe) {
			return;
		} catch (RuntimeException e) {
			return;
		}
		if (handler == null) {
			handler = new CaptureActivityHandler(this, decodeFormats, characterSet);
		}
	}

	@Override
	public void surfaceChanged(SurfaceHolder holder, int format, int width,
			int height) {

	}

	@Override
	public void surfaceCreated(SurfaceHolder holder) {
		if (!hasSurface) {
			hasSurface = true;
			initCamera(holder);
		}

	}

	@Override
	public void surfaceDestroyed(SurfaceHolder holder) {
		hasSurface = false;

	}

	public ViewfinderView getViewfinderView() {
		return viewfinderView;
	}

	public Handler getHandler() {
		return handler;
	}

	public void drawViewfinder() {
		viewfinderView.drawViewfinder();

	}

	private void initBeepSound() {
		if (playBeep && mediaPlayer == null) {
			// The volume on STREAM_SYSTEM is not adjustable, and users found it
			// too loud,
			// so we now play on the music stream.
			setVolumeControlStream(AudioManager.STREAM_MUSIC);
			mediaPlayer = new MediaPlayer();
			mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
			mediaPlayer.setOnCompletionListener(beepListener);

			AssetFileDescriptor file = getResources().openRawResourceFd(R.raw.beep);
			try {
				mediaPlayer.setDataSource(file.getFileDescriptor(), file.getStartOffset(), file.getLength());
				file.close();
				mediaPlayer.setVolume(BEEP_VOLUME, BEEP_VOLUME);
				mediaPlayer.prepare();
			} catch (IOException e) {
				mediaPlayer = null;
			}
		}
	}

	private static final long VIBRATE_DURATION = 200L;

	private void playBeepSoundAndVibrate() {
		if (playBeep && mediaPlayer != null) {
			mediaPlayer.start();
		}
		if (vibrate) {
			Vibrator vibrator = (Vibrator) getSystemService(VIBRATOR_SERVICE);
			vibrator.vibrate(VIBRATE_DURATION);
		}
	}

	/**
	 * When the beep has finished playing, rewind to queue up another one.
	 */
	private final OnCompletionListener beepListener = new OnCompletionListener() {
		public void onCompletion(MediaPlayer mediaPlayer) {
			mediaPlayer.seekTo(0);
		}
	};

}