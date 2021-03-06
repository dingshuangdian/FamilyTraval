package com.kingtangdata.inventoryassis.activity;

import java.util.List;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
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
import com.kingtangdata.inventoryassis.util.LogUtils;
import com.kingtangdata.inventoryassis.view.SliderPopupChoicer;

/**
 * 
 * 盘亏处理界面
 * @author Administrator
 *
 */
public class ActivityPK extends BaseActivity {
	private PullToRefreshListView mPullRefreshListView = null;
	//查找字段输入内容
	private EditText search_input;
	private TextView tvNoRecord;
	
	//标签编码的下拉选项框
	private SliderPopupChoicer choicer;
	
	//点击选择的记录
	private Plan plan = null;
	
	private ItemAdapter adapter = null;
	
	// 请求查看第几页面
	public int nextpage = 0; 
	
	private DataLoadTask task =  null;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		
		super.onCreate(savedInstanceState);
		super.setContentView(R.layout.activity_pk);
		super.setStatusBarVisibility(View.VISIBLE);
		super.setTopLabel(R.string.title_activity_shortage);
		super.setLeftButtonText("返回");
		
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
			@Override
			public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
				
				plan = (Plan)adapterView.getItemAtPosition(i);
				
				Intent intent = new Intent(ActivityPK.this, ActivityPKForm.class);
				intent.putExtra("plan", plan);
				startActivityForResult(intent, 100);
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
					task = new DataLoadTask();
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
		task = new DataLoadTask();
		task.execute();
	}
	
	@Override
	protected void onResume() {
		
		super.onResume();
		refresh();
	}
	
	@Override
	public void doClickLeftBtn() {
		
		onBackPressed();
	}
	
	@Override
	public void onBackPressed() {
		
		finish();
	}
	
	@Override
	protected void onDestroy() {
		
		super.onDestroy();
	
		if(task != null && !task.isCancelled()){
			task.cancel(true);
		}
	}
	
	@Override
	protected Dialog onCreateDialog(int id) {
		
		if(id == 0){
			AlertDialog.Builder builder= new AlertDialog.Builder(this);
			builder.setTitle("提示");
			builder.setMessage("没有任何盘亏记录");
			builder.setNeutralButton("确定", null);
			return builder.create();
		}
		return super.onCreateDialog(id);
	}
	
	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		
		super.onActivityResult(requestCode, resultCode, data);
		
		//盘亏的请求吗101
		if(resultCode == RESULT_OK && requestCode == 100 ){
			Plan p =(Plan)data.getSerializableExtra("plan");
			String type = data.getStringExtra("type");
			
			if(ActivityPKForm.PK_SAVE.equals(type)){
				//进行盘亏信息修改保存的刷新
				plan.copyFrom(p);
				adapter.notifyDataSetChanged();
			}else if(ActivityPKForm.PK_DELETE.equals(type)){
				//进行盘亏信息撤销的刷新
				List<Plan> list = adapter.getList();
				
				Plan s = null;
				for (Plan plan : list) {
					if(plan.getDet_id().equals(p.getDet_id())){
						s = plan;
					}
				}
				
				if(s != null){
					list.remove(s);
				}
				
				adapter.notifyDataSetChanged();
			}
		}		
	}
	
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
			task = new DataLoadTask();
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
			
			StringBuffer querySQL = new StringBuffer("select * from plans where check_result='pk'  and running='c'");
			//输入查找条件
			String where_value = search_input.getText().toString();
			
			String[] params = null;;
			
			//查找array.xml查询字段信息
			String[] arrayStr = getResources().getStringArray(R.array.ids); 		
			
			//下拉选择对应的字段
			String where_col = arrayStr[choicer.getSelectIndex()];		
					
			if(!where_value.equals("")){
				querySQL.append(" and "+where_col+" like '%'||?||'%' ");
				params = new String[]{where_value};
			}
			querySQL.append(" order by dept_id, device_code ");
			
			LogUtils.logd(getClass(), "nextpage = "+ nextpage);
			
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
		
		
		StringBuffer querySQL = new StringBuffer("select count(*) as sumId from  plans  where check_result='pk'  and running='c'");
		
		//输入查找条件
		String where_value = search_input.getText().toString();
		
		String[] params = null;
		
		//查找array.xml查询字段信息
		String[] arrayStr = getResources().getStringArray(R.array.ids); 		
		
		//下拉选择对应的字段
		String where_col = arrayStr[choicer.getSelectIndex()];		
				
		if(!where_value.equals("")){
			querySQL.append(" and "+where_col+" like '%'||?||'%' ");
			params =new String[]{where_value};
		}
		querySQL.append(" order by dept_id, device_code");
		
		return PlanManager.getInstance(getApplicationContext()).getPlanCount(querySQL.toString(), params);
	}
}
