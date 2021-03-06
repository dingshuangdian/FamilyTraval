package com.kingtangdata.inventoryassis.activity.set;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.kingtangdata.inventoryassis.R;
import com.kingtangdata.inventoryassis.activity.ActivityDownloadTask;
import com.kingtangdata.inventoryassis.base.BaseActivity;
import com.kingtangdata.inventoryassis.http.HttpConstants;
import com.kingtangdata.inventoryassis.util.LogUtils;
import com.kingtangdata.inventoryassis.util.MessageUtil;
import com.kingtangdata.inventoryassis.util.StorageUtils;

/**
 * 基础设置类
 * @author xj.chen
 * @since 2014-6-9
 */
public class ActivitySetIP extends BaseActivity {
	private EditText mServerAddr; //服务器
	private EditText mServerPort; //端口号
	private EditText mServerApp;  //应用名

	@Override
	public void onCreate(Bundle paramBundle) {
		super.onCreate(paramBundle);
		super.setContentView(R.layout.activity_setip);
		super.setLeftButtonText("返回");
		super.setTopLabel("服务器设置");
		
		//定义
		mServerAddr = (EditText)findViewById(R.id.input_server_addr);
		mServerPort = (EditText)findViewById(R.id.input_server_port);
		mServerApp  = (EditText)findViewById(R.id.input_server_app);
		
		// 显示默认的值
		String ipaddress = StorageUtils.getString(StorageUtils.IP_ADDRESS,this, HttpConstants.DEFAULT_IP);
		String port      = StorageUtils.getString(StorageUtils.PORT, this, HttpConstants.DEFAULT_PORT);
		String app		 = StorageUtils.getString(StorageUtils.APP, this, HttpConstants.DEFAULT_APP);

		mServerAddr.setText(ipaddress);
		mServerPort.setText(port);
		mServerApp.setText(app);
	}
	
	public void doTest(View view) {
		TestNetworkTask task = new TestNetworkTask();
		task.execute();
	}

	public void doSave(View view) {
		String ipaddress = mServerAddr.getText().toString().trim();
		String port = mServerPort.getText().toString().trim();
		String app = mServerApp.getText().toString().trim();

		StorageUtils.setProperty(StorageUtils.IP_ADDRESS, ipaddress, this);
		StorageUtils.setProperty(StorageUtils.PORT, port, this);
		StorageUtils.setProperty(StorageUtils.APP, app, this);
			
		if (StorageUtils.getBoolean(StorageUtils.ISINIT, this)) {
			makeText("保存成功");
			finish();
		}
		else {
			makeText("保存成功");
			startActivityForResult(new Intent(this, ActivityDownloadTask.class),100);
		}
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {		
		super.onActivityResult(requestCode, resultCode, data);
		
		if(resultCode == RESULT_OK && requestCode == 100){
			setResult(RESULT_OK);
			finish();
		}
	}
	
	
	@Override
	public void onBackPressed() {		
		setResult(RESULT_OK);
		finish();
	}
	

	/**
	 * 测试服务器是否连通
	 * 
	 * @author xj.chen
	 * 
	 */
	class TestNetworkTask extends AsyncTask<String, Integer, Boolean> {

		@Override
		protected Boolean doInBackground(String... params) {

			try {
				String ip_address = mServerAddr.getText().toString();
				String port = mServerPort.getText().toString();
				String app  = mServerApp.getText().toString();
				String httpAddress = "http://" + ip_address + ":" + port + "/" + app + HttpConstants.TEST_ACTION;

				LogUtils.logd(getClass(), "httpAddress = " + httpAddress);
				URL url = new URL(httpAddress);
				HttpURLConnection connection = (HttpURLConnection) url.openConnection();
				connection.setReadTimeout(HttpConstants.CONNECT_TIME_OUT);
				connection.setConnectTimeout(HttpConstants.READ_TIME_OUT);
				LogUtils.logd(getClass(),"code = " + connection.getResponseCode());

				return connection.getResponseCode() == 200;
			} catch (MalformedURLException e) {
				e.printStackTrace();
				return false;
			} catch (IOException e) {
				e.printStackTrace();
				return false;
			}
		}

		@Override
		protected void onPostExecute(Boolean result) {
			
			if (result) {
				// 目前判断服务器是否能连接成功就根据是否能调获取任务列表接口
				MessageUtil.setMessage("连接成功", ActivitySetIP.this);
			} else {
				MessageUtil.setMessage("连接失败", ActivitySetIP.this);
			}
		}
	}
}