package com.kingtangdata.inventoryassis.activity;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.view.WindowManager;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnLastItemVisibleListener;
import com.handmark.pulltorefresh.library.PullToRefreshBase.OnRefreshListener;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.kingtangdata.inventoryassis.R;
import com.kingtangdata.inventoryassis.adapter.ItemAdapter;
import com.kingtangdata.inventoryassis.adapter.PopForStringAdapter;
import com.kingtangdata.inventoryassis.base.BaseActivity;
import com.kingtangdata.inventoryassis.bean.Plan;
import com.kingtangdata.inventoryassis.db.PlanManager;
import com.kingtangdata.inventoryassis.view.SliderPopupChoicer;

/**
 * 盘点查询界面
 * @author Administrator
 *
 */
public class ActivityDataSearch extends BaseActivity {
	
	private PullToRefreshListView mPullRefreshListView = null;
	
	private static final int SELECTED_ALL = 0;
	private static final int SELECTED_CHECKED = 1;
	private static final int SELECTED_NOT = 2;
	private static final int SELECTED_SHORTAGE = 3;
	private static final int SELECTED_SURPLUS = 4;
	//默认为全部
	private int select  =  SELECTED_ALL;
	
	//标签编码的下拉选项框
	private SliderPopupChoicer choicer;
	//查找字段输入内容
	private EditText search_input;
	private TextView tvNoRecord;
	
	private ItemAdapter adapter = null;
	
	// 请求查看第几页面
	public int nextpage = 0; 
		
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_data_search);
		super.setRightButtonText("全部");
		//super.setTopLabel("盘点查询");
		super.setStatusBarVisibility(View.VISIBLE);
		super.setTopLabel(getString(R.string.title_activity_search));
		
		//查找条件
		search_input = (EditText)findViewById(R.id.search_input);
		tvNoRecord = (TextView)findViewById(R.id.tv_no_record);
		
		String[] mData = getResources().getStringArray(R.array.marks); 
		PopForStringAdapter psadapter = new PopForStringAdapter(this, mData); 
		choicer = (SliderPopupChoicer)findViewById(R.id.checker_choice);
		choicer.setAdapter(psadapter);
		
		mPullRefreshListView = (PullToRefreshListView)findViewById(R.id.pull_refresh_list);
		mPullRefreshListView.setPullToRefreshEnabled(false);
		mPullRefreshListView.setOnItemClickListener(new OnItemClickListener() {
			/**
			 * 如果子类需要实现list选项点击事件  则必须override该方法
			 * 选择某一项显示对应的表单信息
			 */
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				
				Plan plan = (Plan)adapterView.getItemAtPosition(i);
				Intent intent = new Intent(ActivityDataSearch.this, ActivityDataSearchForm.class);
				intent.putExtra("plan", plan);
				startActivity(intent);
			}
		});
		mPullRefreshListView.setOnRefreshListener(new OnRefreshListener<ListView>() {
			@Override
			public void onRefresh(PullToRefreshBase<ListView> refreshView) {
				
				
			}
		});
		mPullRefreshListView.setOnLastItemVisibleListener(new OnLastItemVisibleListener() {
			@Override
			public void onLastItemVisible() {
				
				if(adapter.getCount() < getTotalNum()){
					tvNoRecord.setVisibility(View.GONE);
					DataLoadTask task = new DataLoadTask();
					task.execute();
				}else{
					tvNoRecord.setVisibility(View.VISIBLE);
					return;
				}
			}
		});

		adapter = new ItemAdapter(this);

		ListView listView = mPullRefreshListView.getRefreshableView();
		listView.setSelector(getResources().getDrawable(android.R.color.transparent));
		listView.setAdapter(adapter);
		
		//加载数据
		DataLoadTask task = new DataLoadTask();
		task.execute();
	}
	
	@Override
	public void doClickRightBtn() {
		
		showBottomOperationMenu();
	}
	
	
	@Override
	protected void onResume() {
		
		super.onResume();
		
		refresh();
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
	 * 查询操作
	 * @param view
	 */
	public void doSearch(View view){
		//隐藏软键盘
		hideSoftKeyboard(view);
		
		adapter.clear();
		nextpage = 0;
		
		if(adapter.getCount() < getTotalNum()){
			tvNoRecord.setVisibility(View.GONE);
			DataLoadTask task = new DataLoadTask();
			task.execute();
		}else{
			tvNoRecord.setVisibility(View.VISIBLE);
			return;
		}
	}
	
	class DataLoadTask extends AsyncTask<String,Void,Object>{
		@Override
		protected void onPreExecute() {
			
			if(adapter.getCount() < getTotalNum()){
				tvNoRecord.setVisibility(View.GONE);
				nextpage ++;
			}
			else {
				tvNoRecord.setVisibility(View.VISIBLE);
			}
		}
		
		@Override
		protected Object doInBackground(String... s) {
			
			StringBuffer querySQL = new StringBuffer("select * from  plans where running='c'");
			String[] params = null;
			//输入查找条件
			String where_value = search_input.getText().toString();
			if(select == SELECTED_SURPLUS){
				//盘盈
				querySQL.append(" and check_result=?");
				if(!where_value.equals("")){
					params=new String[]{"py",where_value};
				}else{
					params = new String[]{"py"};
				}
			}else if(select == SELECTED_SHORTAGE){
				//盘亏
				querySQL.append(" and check_result=?");
				if(!where_value.equals("")){
					params=new String[]{"pk",where_value};
				}else{
					params = new String[]{"pk"};
				}
			}else if(select == SELECTED_NOT){
				//未盘
				querySQL.append(" and check_result=?");
				if(!where_value.equals("")){
					params=new String[]{"kb",where_value};
				}else{
					params = new String[]{"kb"};
				}
			}else if(select == SELECTED_CHECKED){
				//已盘
				querySQL.append(" and check_result in(?,?)");
				if(!where_value.equals("")){
					params=new String[]{"zc","pk",where_value};
				}else{
					params = new String[]{"zc","pk"};
				}
			}else{
				//全部
				if(!where_value.equals("")){
					querySQL.append(" and 1=1 ");
					params=new String[]{where_value};
				}
			}
			
			//查找array.xml查询字段信息
			String[] arrayStr = getResources().getStringArray(R.array.ids); 		
			
			//下拉选择对应的字段
			String where_col = arrayStr[choicer.getSelectIndex()];		
					
			if(!where_value.equals("")){
				querySQL.append(" and "+where_col+" like '%'||?||'%' ");
			}
			
			querySQL.append(" order by  check_result desc,dept_id, device_code");
			
			return PlanManager.getInstance(getApplicationContext()).getPlans(querySQL, params, nextpage);
		}
		
		@Override
		protected void onPostExecute(Object result) {
			
			List<Plan> plans = (List<Plan>)result;
			
			adapter.addList(plans);
			adapter.notifyDataSetChanged();
		}
	}
	
	protected int getTotalNum() {
		
		StringBuffer querySQL = new StringBuffer("select count(*) as sumId from  plans  where  running='c'");
		String[] params = null;
		//输入查找条件
		String where_value = search_input.getText().toString();
		if(select == SELECTED_SURPLUS){
			//盘盈
			querySQL.append(" and check_result=?");
			if(!where_value.equals("")){
				params=new String[]{"py",where_value};
			}else{
				params = new String[]{"py"};
			}
		}else if(select == SELECTED_SHORTAGE){
			//盘亏
			querySQL.append(" and check_result=?");
			if(!where_value.equals("")){
				params=new String[]{"pk",where_value};
			}else{
				params = new String[]{"pk"};
			}
		}else if(select == SELECTED_NOT){
			//未盘
			querySQL.append(" and check_result=?");
			if(!where_value.equals("")){
				params=new String[]{"kb",where_value};
			}else{
				params = new String[]{"kb"};
			}
		}else if(select == SELECTED_CHECKED){
			//已盘
			querySQL.append(" and check_result in(?,?)");
			if(!where_value.equals("")){
				params=new String[]{"zc","pk",where_value};
			}else{
				params = new String[]{"zc","pk"};
			}
		}else{
			//全部
			if(!where_value.equals("")){
				querySQL.append(" and 1=1 ");
				params=new String[]{where_value};
			}
		}
		
		//查找array.xml查询字段信息
		String[] arrayStr = getResources().getStringArray(R.array.ids); 		
		
		//下拉选择对应的字段
		String where_col = arrayStr[choicer.getSelectIndex()];		
				
		if(!where_value.equals("")){
			querySQL.append(" and "+where_col+" like '%'||?||'%' ");
		}
		
		querySQL.append(" order by  check_result desc,dept_id, device_code");
		
		return PlanManager.getInstance(getApplicationContext()).getPlanCount(querySQL.toString(), params);
	}

	
	/**
	 * 显示底部call菜单
	 */
	public void showBottomOperationMenu() {
		final Dialog call_menu = new AlertDialog.Builder(this).create();
		call_menu.show();
		call_menu.getWindow().setWindowAnimations(R.style.PopupAnimation);
		
		final View view = View.inflate(getApplicationContext(),R.layout.ui_menu_search, null);
		if(select == SELECTED_ALL){
			view.findViewById(R.id.selected_all).setVisibility(View.VISIBLE);
		}else if(select == SELECTED_CHECKED){
			view.findViewById(R.id.selected_checked).setVisibility(View.VISIBLE);
		}else if(select == SELECTED_NOT){
			view.findViewById(R.id.selected_not).setVisibility(View.VISIBLE);
		}else if(select == SELECTED_SHORTAGE){
			view.findViewById(R.id.selected_shortage).setVisibility(View.VISIBLE);
		}else if(select == SELECTED_SURPLUS){
			view.findViewById(R.id.selected_surplus).setVisibility(View.VISIBLE);
		}
		
		view.findViewById(R.id.ll_all).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				select = SELECTED_ALL;
				setRightButtonText("全部");
				view.findViewById(R.id.selected_all).setVisibility(View.VISIBLE);
				view.findViewById(R.id.selected_checked).setVisibility(View.GONE);
				view.findViewById(R.id.selected_not).setVisibility(View.GONE);
				view.findViewById(R.id.selected_shortage).setVisibility(View.GONE);
				view.findViewById(R.id.selected_surplus).setVisibility(View.GONE);
				xx();
				call_menu.dismiss();
			}
		});
		
		view.findViewById(R.id.ll_checked).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				select = SELECTED_CHECKED;
				setRightButtonText("已盘");
				view.findViewById(R.id.selected_all).setVisibility(View.GONE);
				view.findViewById(R.id.selected_checked).setVisibility(View.VISIBLE);
				view.findViewById(R.id.selected_not).setVisibility(View.GONE);
				view.findViewById(R.id.selected_shortage).setVisibility(View.GONE);
				view.findViewById(R.id.selected_surplus).setVisibility(View.GONE);
				xx();
				call_menu.dismiss();
			}
		});
		
		view.findViewById(R.id.ll_not).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				select = SELECTED_NOT;
				setRightButtonText("未盘");
				view.findViewById(R.id.selected_all).setVisibility(View.GONE);
				view.findViewById(R.id.selected_checked).setVisibility(View.GONE);
				view.findViewById(R.id.selected_not).setVisibility(View.VISIBLE);
				view.findViewById(R.id.selected_shortage).setVisibility(View.GONE);
				view.findViewById(R.id.selected_surplus).setVisibility(View.GONE);
				xx();
				call_menu.dismiss();
			}
		});
		
		view.findViewById(R.id.ll_shortage).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				select = SELECTED_SHORTAGE;
				setRightButtonText("盘亏");
				view.findViewById(R.id.selected_all).setVisibility(View.GONE);
				view.findViewById(R.id.selected_checked).setVisibility(View.GONE);
				view.findViewById(R.id.selected_not).setVisibility(View.GONE);
				view.findViewById(R.id.selected_shortage).setVisibility(View.VISIBLE);
				view.findViewById(R.id.selected_surplus).setVisibility(View.GONE);
				xx();	
				call_menu.dismiss();
			}
		});
		
		view.findViewById(R.id.ll_surplus).setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				
				select = SELECTED_SURPLUS;
				setRightButtonText("盘盈");
				view.findViewById(R.id.selected_all).setVisibility(View.GONE);
				view.findViewById(R.id.selected_checked).setVisibility(View.GONE);
				view.findViewById(R.id.selected_not).setVisibility(View.GONE);
				view.findViewById(R.id.selected_shortage).setVisibility(View.GONE);
				view.findViewById(R.id.selected_surplus).setVisibility(View.VISIBLE);
				xx();
				call_menu.dismiss();
			}
		});
		
		view.findViewById(R.id.cancel).setOnClickListener(new OnClickListener() {
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
	
	public void xx(){
		adapter.clear();
		nextpage = 0;
		
		DataLoadTask task = new DataLoadTask();
		task.execute();
	}	
}