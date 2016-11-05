package com.familytraval.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.familytraval.R;
import com.familytraval.bean.ProductAttribute;

import java.util.List;

/**
 * Created by dings on 2016/10/26.
 */

public class ProductAttrListAdapter extends BaseAdapter {

    private List<ProductAttribute> mAttrLst;
    private Context mContext;

    public ProductAttrListAdapter(Context context) {
        this.mContext = context;
    }

    public void setData(List<ProductAttribute> attrs) {
        if (attrs == null) return;
        this.mAttrLst = attrs;
    }

    @Override
    public int getCount() {
        if (mAttrLst == null) return 0;
        return mAttrLst.size();
    }

    @Override
    public Object getItem(int position) {
        if (mAttrLst == null) return null;
        return mAttrLst.get(position);
    }

    @Override
    public long getItemId(int position) {
        if (mAttrLst == null) return -1;
        if (mAttrLst.get(position).getAttrID() != null) {
            return Long.valueOf(mAttrLst.get(position).getAttrID());
        }
        return 0;

    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

        //category_left_item.xml
        final ViewHolder holder;
        if (convertView == null) {
            holder = new ViewHolder();
            //使用自定义的list_items作为Layout
            convertView = LayoutInflater.from(mContext).inflate(R.layout.product_attr_list_item, parent, false);
            holder.titleTxtv = ((TextView) convertView.findViewById(R.id.product_attr_title_txtv));
            holder.valueTxtv = ((TextView) convertView.findViewById(R.id.product_attr_value_txtv));
            //设置标记
            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }

        //获取该行数据
        ProductAttribute attribute = (ProductAttribute) mAttrLst.get(position);

        //设置数据到View
        holder.titleTxtv.setText(attribute.getAttrName());
        holder.valueTxtv.setText(attribute.getAttrValue());

        return convertView;
    }

    class ViewHolder {
        TextView titleTxtv;
        TextView valueTxtv;
    }

}

