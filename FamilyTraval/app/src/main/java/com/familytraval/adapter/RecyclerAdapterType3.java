package com.familytraval.adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.familytraval.R;
import com.familytraval.bean.Cheese;
import com.familytraval.callback.CustomBitmapLoadCallBack;

import org.xutils.image.ImageOptions;
import org.xutils.x;

import java.util.List;

/**
 * Created by dings on 2016/10/24.
 */

public class RecyclerAdapterType3 extends RecyclerView.Adapter<RecyclerView.ViewHolder> {
    private Context context;
    private List<Cheese> results;

    //get & set
    public List<Cheese> getResults() {
        return results;
    }

    public RecyclerAdapterType3(Context context, List<Cheese> results) {
        this.context = context;
        this.results = results;
    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        return new ItemViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.recycle_item_type3_item, parent, false));
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        if (holder instanceof ItemViewHolder) {
            bind((ItemViewHolder) holder, position);
        }
    }

    @Override
    public int getItemCount() {
        return results.size();
    }

    /////////////////////////////

    private void bind(ItemViewHolder holder, int position) {
        x.image().bind(holder.item_recyc_type3_item_img, results.get(position).getImg(), new ImageOptions.Builder().build(), new CustomBitmapLoadCallBack(holder.item_recyc_type3_item_img));
    }

    public class ItemViewHolder extends RecyclerView.ViewHolder {
        public ImageView item_recyc_type3_item_img;

        public ItemViewHolder(View itemView) {
            super(itemView);
            item_recyc_type3_item_img = (ImageView) itemView.findViewById(R.id.item_recyc_type3_item_img);
        }
    }
}

