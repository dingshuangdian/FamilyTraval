package com.kingtangdata.inventoryassis.adapter;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kingtangdata.inventoryassis.R;
import com.kingtangdata.inventoryassis.bean.Plan;

public class ItemAdapterForBD extends BaseAdapter{

	private Context context;
	private List<Plan> list = new ArrayList<Plan>();
	
	public ItemAdapterForBD(Context context){
		this.context = context;
	}
	
	public void addList(List<Plan> list){
		if(list != null){
			this.list.addAll(list);
		}
	}
	
	public List<Plan> getList(){
		return list;
	}
	
	
	public void clear(){
		if(list != null){
			this.list.clear();
		}
	}
	
	@Override
	public int getCount() {
		
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		
		return list.get(position);
	}

	@Override
	public long getItemId(int position) {
		
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null;
		
		if(convertView == null){
			convertView = LayoutInflater.from(context).inflate(R.layout.ui_item,null);
			
			holder = new ViewHolder();
			holder.tv1 = (TextView)convertView.findViewById(R.id.text1);
			holder.tv2 = (TextView)convertView.findViewById(R.id.text2);
			holder.tv3 = (TextView)convertView.findViewById(R.id.text3);
			holder.status1 = (TextView)convertView.findViewById(R.id.status1);
			holder.status2 = (TextView)convertView.findViewById(R.id.status2);
			convertView.setTag(holder);
		}else{
			holder = (ViewHolder)convertView.getTag();
		}
		
		final Plan plan = (Plan) list.get(position);
		String checkResult = "未盘";//盘点结果
		String isNormal = "";
		if("pk".equals(plan.getCheck_result())){
			checkResult = "盘亏";
		}else if("zc".equals(plan.getCheck_result())){
			checkResult = "正常";
			//只有在正常的情况下才显示是否相符
			isNormal =plan.isNormal()? "相符":"不相符" ;
		}else if("py".equals(plan.getCheck_result())){
			checkResult = "盘盈";
		}else{
			checkResult = "";
		}
		
//		holder.tv1.setText(checkResult);//盘点结果
//		holder.tv1.setTextColor(context.getResources().getColor(R.color.gray));	
//		holder.tv2.setText(isNormal);//是否相符
//		holder.tv2.setTextColor(context.getResources().getColor(R.color.gray));		
//		holder.tv3.setText(plan.getDevice_code());//设备编号			
//		holder.tv3.setTextColor(context.getResources().getColor(R.color.gray));
//		holder.tv4.setText(plan.getDevice_name());//设备名称			
//		holder.tv4.setTextColor(context.getResources().getColor(R.color.gray));
//		holder.tv5.setText(plan.getDevice_type());//设备型号
//		holder.tv5.setTextColor(context.getResources().getColor(R.color.gray));
//		holder.tv6.setText(plan.getDevice_size());//设备规格
//		holder.tv6.setTextColor(context.getResources().getColor(R.color.gray));
//		holder.tv7.setText(plan.getInstall_place());//安装地点			
//		holder.tv7.setTextColor(context.getResources().getColor(R.color.gray));
//		holder.tv8.setText(plan.getLabel_code());//标签编码			
//		holder.tv8.setTextColor(context.getResources().getColor(R.color.gray));
//		holder.tv9.setText(plan.isUpload()?"已上传":"—");//标签编码			
//		holder.tv9.setTextColor(context.getResources().getColor(R.color.gray));
		
		holder.tv1.setText("设备编号："+plan.getDevice_code());//设备编号			
		holder.tv1.setTextColor(context.getResources().getColor(R.color.gray));
		holder.tv2.setText("设备名称："+plan.getDevice_name());//设备名称			
		holder.tv2.setTextColor(context.getResources().getColor(R.color.gray));
		holder.tv3.setText("安装地点："+plan.getInstall_place());//安装地点			
		holder.tv3.setTextColor(context.getResources().getColor(R.color.gray));
		holder.status1.setText(plan.getLabel_code());//状态		
		return convertView;
	}
	
	class ViewHolder {
		public TextView tv1;
		public TextView tv2;
		public TextView tv3;
		public TextView status1;
		public TextView status2;
	}
}
