package com.kingtangdata.inventoryassis.activity;

/*
 * 主页面通用类
 */

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.kingtangdata.inventoryassis.R;
import com.kingtangdata.inventoryassis.adapter.HomeGridAdapter;
import com.kingtangdata.inventoryassis.base.BaseActivity;

public class ActivityMain extends BaseActivity{
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.listview);
		super.setTopViewVisibility(View.GONE);
		super.setLogoVisibility(View.VISIBLE);
		super.setStatusBarVisibility(View.VISIBLE);
	
		HomeGridAdapter adapter = new HomeGridAdapter(this);
		ListView listView = (ListView)findViewById(R.id.listview);
		listView.setOnItemClickListener(new OnItemClickListener() {
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				switch (i) {
				case 0://盘点任务
					startActivity(new Intent(ActivityMain.this, ActivityCheckTask.class));			
					break;
				case 1://标签绑定
					startActivity(new Intent(ActivityMain.this, ActivityRFID.class));
					break;
				case 2://盘盈处理
					startActivity(new Intent(ActivityMain.this, ActivityPY.class));
					break;
				case 3://盘亏处理
					startActivity(new Intent(ActivityMain.this, ActivityPK.class));
					break;
				case 4://下载任务
					startActivity(new Intent(ActivityMain.this, ActivityDownloadTask.class));
					break;
				case 5://上传结果
					startActivity(new Intent(ActivityMain.this, ActivityUploadData.class));
					break;
				default:
					break;
				}
			}
		});
		listView.setAdapter(adapter);
	}
	
	@Override
	protected void onResume() {
		super.onResume();
		refresh();
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