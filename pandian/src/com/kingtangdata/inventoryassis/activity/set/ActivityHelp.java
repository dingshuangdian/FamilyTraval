package com.kingtangdata.inventoryassis.activity.set;

import android.os.Bundle;
import android.os.Handler;
import android.webkit.WebView;

import com.kingtangdata.inventoryassis.R;
import com.kingtangdata.inventoryassis.base.BaseActivity;

public class ActivityHelp extends BaseActivity{
	
	public void onCreate(Bundle bundle){
		super.onCreate(bundle);
		super.setContentView(R.layout.activity_help);
		super.setTopLabel("帮助手册");

		WebView webView = (WebView)findViewById(R.id.webview);
		webView.getSettings().setDefaultTextEncodingName("gbk");
		webView.loadUrl("file:///android_asset/help.htm");
	}
	
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
}
