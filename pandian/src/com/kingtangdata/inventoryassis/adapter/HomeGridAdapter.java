package com.kingtangdata.inventoryassis.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.kingtangdata.inventoryassis.R;
import com.kingtangdata.inventoryassis.util.LogUtils;

/**
 * 门店选项适配
 * 
 * @author leo
 * 
 */
public class HomeGridAdapter extends BaseAdapter {
	private Context context;

	private String[] titles = { "盘点任务", "标签绑定", "盘盈处理", "盘亏处理", "下载任务", "上传结果" };

	private int[] ids = { R.drawable.btn_list_00,R.drawable.btn_list_01,
			R.drawable.btn_list_02,R.drawable.btn_list_03,
			R.drawable.btn_list_04, R.drawable.btn_list_05 };

	public HomeGridAdapter(Context context){
		this.context = context;
	}
	
	@Override
	public int getCount() {
		return titles.length;
	}

	@Override
	public Object getItem(int positon) {
		return titles[positon];
	}

	@Override
	public long getItemId(int positon) {
		return positon;
	}

	@Override
	public View getView(int position, View view, ViewGroup viewGroup) {
		ViewHolder holder = null;
		if (view == null) {
			view = LayoutInflater.from(context).inflate(R.layout.ui_home_grid_item, null);
			holder = new ViewHolder();
			holder.title = (TextView) view.findViewById(R.id.title);
			holder.num = (TextView) view.findViewById(R.id.bt_num);
			holder.icon = (ImageView) view.findViewById(R.id.icon);
			view.setTag(holder);
		} else {
			holder = (ViewHolder) view.getTag();
		}

		holder.title.setText(titles[position]);
		holder.icon.setImageResource(ids[position]);
		holder.num.setText("10");
		LogUtils.logd(getClass(), "srcs[position] = " +ids[position]);
		return view;
	}

	private class ViewHolder {
		TextView title;
		TextView num;
		ImageView icon;
	}

}