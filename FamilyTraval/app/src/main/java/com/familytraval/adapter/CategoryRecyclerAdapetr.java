package com.familytraval.adapter;

import android.content.Context;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.familytraval.R;
import com.familytraval.bean.Cheese;

import java.util.List;

/**
 * Created by dings on 2016/10/29.
 */

public class CategoryRecyclerAdapetr extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context mContext;
    //多布局加载(type)
    public static final int TYPE_1 = 0xff01;
    public static final int TYPE_2 = 0xff02;
    public static final int TYPE_3 = 0xff03;
    public static final int TYPE_4 = 0xff04;
    public static final int TYPE_5 = 0xff05;
    public static final int TYPE_6 = 0xff06;

    public CategoryRecyclerAdapetr(Context mContext, int srcId) {
        this.mContext = mContext;
    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        switch (viewType) {
            case TYPE_1:
                return new CategoryRecyclerAdapetr.MyViewHolder1(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_type6, parent, false));
            case TYPE_5:
                return new CategoryRecyclerAdapetr.MyViewHolder2(LayoutInflater.from(parent.getContext()).inflate(R.layout.fragment_home_menu, parent, false));
            default:
                return null;
        }
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 10;
    }

    public class MyViewHolder1 extends RecyclerView.ViewHolder {

        public MyViewHolder1(View itemView) {
            super(itemView);
        }
    }

    public class MyViewHolder2 extends RecyclerView.ViewHolder {

        public MyViewHolder2(View itemView) {
            super(itemView);
        }
    }

    @Override
    public int getItemViewType(int position) {
        if (position == 0) {
            return TYPE_1;
        } else if (position == 1) {
            return TYPE_5;

        } else {
            return TYPE_1;
        }
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);

        RecyclerView.LayoutManager manager = recyclerView.getLayoutManager();
        if (manager instanceof GridLayoutManager) {
            final GridLayoutManager gridManager = ((GridLayoutManager) manager);
            gridManager.setSpanSizeLookup(new GridLayoutManager.SpanSizeLookup() {
                @Override
                public int getSpanSize(int position) {
                    int type = getItemViewType(position);
                    switch (type) {
                        case TYPE_1:
                        case TYPE_2:
                        case TYPE_3:
                        case TYPE_4:
                        case TYPE_5:
                        case TYPE_6:
                            return gridManager.getSpanCount();
                        default:
                            return 1;
                    }
                }
            });
        }
    }
}
