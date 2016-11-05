package com.familytraval.fragment;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.Toast;

import com.familytraval.R;
import com.familytraval.activity.MainActivity;
import com.familytraval.adapter.RecyclerAdapter;
import com.familytraval.bean.Cheese;
import com.familytraval.callback.MyItemClickListener;
import com.familytraval.common.MyAppContext;
import com.familytraval.ui.UIHelper;
import com.familytraval.utils.DeviceUtil;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;

/**
 * Created by dings on 2016/10/24.
 */

public class HomeFragment extends Fragment implements MyItemClickListener {
    private View rootView = null;
    private RecyclerAdapter recyclerAdapter;
    PtrClassicFrameLayout mPtrFrame;
    private View mainView = null;

    public static HomeFragment newInstance() {
        HomeFragment homeFragment = new HomeFragment();
        return homeFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        rootView = inflater.inflate(R.layout.fragment_home, container, false);

        initBase();
        initView();

        return rootView;
    }

    private void initBase() {
        RecyclerView recyclerView = (RecyclerView) this.rootView.findViewById(R.id.recyclerView);
        mPtrFrame = (PtrClassicFrameLayout) rootView.findViewById(R.id.rotate_header_list_view_frame);
        recyclerView.setLayoutManager(new GridLayoutManager(recyclerView.getContext(), 2, GridLayoutManager.VERTICAL, false));
        List<Cheese> results = new ArrayList<Cheese>();
        results.add(new Cheese("", "my titles develop by random1"));
        results.add(new Cheese("", "my titles develop by random2"));
        results.add(new Cheese("", "my titles develop by random3"));
        results.add(new Cheese("", "my titles develop by moremoremoremoremoremoremoremore random4"));
        results.add(new Cheese("", "my titles develop by random5"));
        results.add(new Cheese("", "my titles develop by moremoremoremoremoremoremoremore random6"));
        results.add(new Cheese("", "my titles develop by random7"));
        results.add(new Cheese("", "my titles develop by moremoremoremoremoremoremoremore random8"));
        results.add(new Cheese("", "my titles develop by moremoremoremoremoremoremoremore random9"));
        results.add(new Cheese("", "my titles develop by random10"));
        results.add(new Cheese("", "my titles develop by moremoremoremoremoremoremoremore random11"));
        results.add(new Cheese("", "my titles develop by moremoremoremoremoremoremoremore random12"));
        results.add(new Cheese("", "my titles develop by random13"));
        results.add(new Cheese("", "my titles develop by random14"));
        results.add(new Cheese("", "my titles develop by random15"));
        recyclerView.setAdapter(recyclerAdapter = new RecyclerAdapter(getActivity(), R.layout.item_recycle, results));
        recyclerAdapter.setOnItemClickListener(this);
    }


    public void initData() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                List<Cheese> results = recyclerAdapter.getResults();
                results.add(0, new Cheese("", "new fresh item"));
                results.add(0, new Cheese("", "new fresh item"));
                recyclerAdapter.notifyDataSetChanged();
            }
        }, 1000);

    }

    private void initView() {
        // header custom begin
        final StoreHouseHeader header = new StoreHouseHeader(getContext());
        header.setPadding(0, DeviceUtil.dp2px(getContext(), 15), 0, 0);
        header.initWithString("Fine");
        header.setTextColor(
                getResources().getColor(R.color.gray)
        );
        mPtrFrame.setHeaderView(header);
        mPtrFrame.addPtrUIHandler(header);
        // 下拉刷新
        mPtrFrame.setLastUpdateTimeRelateObject(this);
        mPtrFrame.setPtrHandler(new PtrHandler() {
            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                initData();
            }

            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }
        });
    }
    //主页的点击事件

    @Override
    public void onItemClick(View view, int position) {
        switch (view.getId()) {
            case R.id.home_menu_categroy_layout:
                Toast.makeText(getContext(), "暂无数据", Toast.LENGTH_SHORT).show();
                break;
            case R.id.up_left_imgv:
                UIHelper.ProductDetailActivity(getActivity());
                break;
        }
    }
}
