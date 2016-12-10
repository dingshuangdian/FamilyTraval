package com.kingtangdata.inventoryassis.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.kingtangdata.inventoryassis.R;

public class PopForStringAdapter extends BaseAdapter {
		public String[] mData;
		
		private Context context;

		public PopForStringAdapter(Context context,String[] mData) {
			this.mData = mData;
			this.context = context;
		}

		@Override
		public int getCount() {
			return mData.length;
		}

		@Override
		public Object getItem(int paramInt) {
			return this.mData[paramInt];
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
			((TextView) view).setText(this.mData[paramInt]);
			return view;
		}

		public void resetData(String[] paramArray) {
			mData = paramArray;
			notifyDataSetChanged();
		}
	}