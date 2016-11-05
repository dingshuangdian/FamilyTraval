package com.familytraval.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ListView;
import android.widget.TextView;

import com.familytraval.Model.SPFilter;
import com.familytraval.R;
import com.familytraval.activity.ProductListActivity;
import com.familytraval.activity.ProductListSearchResultActivity;
import com.familytraval.adapter.ProductListFilterAdapter;
import com.familytraval.bean.Tag;
import com.familytraval.common.MobileApplication;
import com.familytraval.common.MobileConstants;
import com.familytraval.ui.TagListView;
import com.familytraval.ui.TagView;

import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dings on 2016/10/25.
 */

public class ProductListFilterFragment extends Fragment implements TagListView.OnTagCheckedChangedListener, TagListView.OnTagClickListener {

    private String TAG = "SPProductListFilterFragment";
    private View mView;
    private TextView mTitleView;
    private ListView mFilterListv;
    private TagListView mTagListView;
    private JSONObject dataJson;
    private List<SPFilter> mFilters;
    private ProductListFilterAdapter mFilterAdapter;
    private static ProductListFilterFragment instance;
    private static Handler mHandler;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        if (mView == null) {
            initView(inflater, container);
        }
        instance = this;
        return mView;
    }

    @Override
    public void onResume() {
        super.onResume();
        try {
            if (MobileApplication.getInstance().productListType == 1) {
                //产品列表
                JSONObject dataJson = ProductListActivity.getInstance().mDataJson;
                setDataSource(dataJson);
            } else {
                //搜索结果列表
                JSONObject dataJson = ProductListSearchResultActivity.getInstance().mDataJson;
                setDataSource(dataJson);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    private void initView(LayoutInflater inflater, ViewGroup container) {
        mView = inflater.inflate(R.layout.fragment_spproduct_list_filter, container, false);
        mFilterListv = (ListView) mView.findViewById(R.id.product_filter_lstv);
        mFilterAdapter = new ProductListFilterAdapter(getActivity(), this);
        mFilterListv.setAdapter(mFilterAdapter);
    }


    public void setDataSource(JSONObject jsonObject) {
        if (jsonObject == null) return;
        if (mFilters == null) {
            mFilters = new ArrayList<SPFilter>();
        } else {
            mFilters.clear();
        }
        dataJson = jsonObject;
        try {
            if (dataJson.has("menu")) {
                SPFilter menuFilter = (SPFilter) dataJson.get("menu");
                mFilters.add(menuFilter);
            }
            if (dataJson.has("filter")) {
                List<SPFilter> filters = (List<SPFilter>) dataJson.get("filter");
                mFilters.addAll(filters);
            }
            mFilterAdapter.setData(mFilters);


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static ProductListFilterFragment getInstance(Handler handler) {
        mHandler = handler;
        return instance;
    }

    @Override
    public void onTagCheckedChanged(TagView tagView, Tag tag) {
    }

    @Override
    public void onTagClick(TagView tagView, Tag tag) {
        if (mHandler != null) {
            Message msg = mHandler.obtainMessage(MobileConstants.MSG_CODE_FILTER_CHANGE_ACTION);
            msg.obj = tag.getValue();
            mHandler.sendMessage(msg);
        }
    }
}

