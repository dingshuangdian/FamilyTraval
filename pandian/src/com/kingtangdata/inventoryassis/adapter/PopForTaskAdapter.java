package com.kingtangdata.inventoryassis.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kingtangdata.inventoryassis.R;
import com.kingtangdata.inventoryassis.bean.Task;

public class PopForTaskAdapter extends BaseAdapter {
		public List<Task> mData;
		
		private Context context;

		public PopForTaskAdapter(Context context,List<Task> mData) {
			this.mData = mData;
			this.context = context;
		}

		@Override
		public int getCount() {
			return mData.size();
		}

		@Override
		public Object getItem(int paramInt) {
			return mData.get(paramInt);
		}

		@Override
		public long getItemId(int paramInt) {
			return paramInt;
		}

		@Override
		public View getView(int paramInt, View view, ViewGroup viewGroup) {
			if (view == null) {
				view = LayoutInflater.from(context).inflate(R.layout.pop_item, null);
			}
			Task task = mData.get(paramInt);
			((TextView) view).setText("["+task.getCheck_no()+"] " + task.getDept_name());
			return view;
		}
	}