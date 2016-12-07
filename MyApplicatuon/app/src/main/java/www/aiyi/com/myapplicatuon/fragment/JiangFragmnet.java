package www.aiyi.com.myapplicatuon.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.AdapterView;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.List;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import in.srain.cube.views.ptr.PtrDefaultHandler;
import in.srain.cube.views.ptr.PtrFrameLayout;
import in.srain.cube.views.ptr.PtrHandler;
import in.srain.cube.views.ptr.header.StoreHouseHeader;
import okhttp3.Request;
import www.aiyi.com.myapplicatuon.R;
import www.aiyi.com.myapplicatuon.Utils.DeviceUtil;
import www.aiyi.com.myapplicatuon.activity.DetailPointActivity;
import www.aiyi.com.myapplicatuon.activity.MainActivity;
import www.aiyi.com.myapplicatuon.http.HttpClient;
import www.aiyi.com.myapplicatuon.http.HttpResponseHandler;
import www.aiyi.com.myapplicatuon.model.NewsParam;
import www.aiyi.com.myapplicatuon.model.RecomantParam;
import www.aiyi.com.myapplicatuon.ui.LoadMoreListView;
import www.aiyi.com.myapplicatuon.ui.quickadapter.BaseAdapterHelper;
import www.aiyi.com.myapplicatuon.ui.quickadapter.QuickAdapter;

/**
 * Created by dings on 2016-11-28.
 */

public class JiangFragmnet extends Fragment {
    private DetailPointActivity context;
    private NewsParam param;
    private int pno = 1;
    private boolean isLoadAll;
    @Bind(R.id.rotate_header_list_view_frame)
    PtrClassicFrameLayout ptrClassicFrameLayout;
    @Bind(R.id.listView)
    LoadMoreListView listView;
    QuickAdapter<NewsParam> adapter;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.jiang_layout, container, false);
        ButterKnife.bind(this, view);
        return view;
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        context = (DetailPointActivity) getActivity();
        initData();
        initView();
        loadData();
    }

    void initView() {
        adapter = new QuickAdapter<NewsParam>(context, R.layout.newslistitem) {
            @Override
            protected void convert(BaseAdapterHelper helper, NewsParam news) {
                helper.setText(R.id.detail, news.getTitle())
                        .setText(R.id.date, news.getDate())
                        .setImageUrl(R.id.logo, news.getThumbnail_pic_s()).setText(R.id.name, news.getRealtype());

            }
        };
        listView.setDrawingCacheEnabled(true);
        listView.setAdapter(adapter);
        final StoreHouseHeader houseHeader = new StoreHouseHeader(context);
        houseHeader.setPadding(0, DeviceUtil.dp2px(context, 15), 0, 0);
        houseHeader.initWithString("AYI");
        houseHeader.setTextColor(getResources().getColor(R.color.gray));
        ptrClassicFrameLayout.setHeaderView(houseHeader);
        ptrClassicFrameLayout.addPtrUIHandler(houseHeader);
        //下拉刷新
        ptrClassicFrameLayout.setLastUpdateTimeRelateObject(this);
        ptrClassicFrameLayout.setPtrHandler(new PtrHandler() {
            @Override
            public boolean checkCanDoRefresh(PtrFrameLayout frame, View content, View header) {
                return PtrDefaultHandler.checkContentCanBePulledDown(frame, content, header);
            }

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                initData();
                loadData();

            }
        });
        //加载更多
        listView.setOnLoadMoreListener(new LoadMoreListView.OnLoadMoreListener() {
            @Override
            public void onLoadMore() {

            }
        });
        //点击事件
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

            }
        });
        listView.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
                if (scrollState == AbsListView.OnScrollListener.SCROLL_STATE_FLING) {
                    Picasso.with(context).pauseTag(context);
                } else {
                    Picasso.with(context).resumeTag(context);
                }
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {

            }
        });
    }

    private void initData() {
        param = new NewsParam();
        pno = 1;
        isLoadAll = false;
    }

    private void loadData() {
        if (isLoadAll) {
            return;
        }
        param.setPno(pno);
        HttpClient.getRecommendNews(param, new HttpResponseHandler() {
            @Override
            public void onSuccess(String body) {
                ptrClassicFrameLayout.refreshComplete();
                JSONObject object = JSON.parseObject(body);
                List<NewsParam> list = JSONArray.parseArray(object.getString("body"), NewsParam.class);
                listView.updateLoadMoreViewText(list);
                isLoadAll = list.size() < HttpClient.PAGE_SIZE;
                if (pno == 1) {
                    adapter.clear();

                }
                adapter.addAll(list);
                pno++;
            }

            @Override
            public void onFailure(Request request, IOException e) {
                ptrClassicFrameLayout.refreshComplete();
                listView.setLoadMoreViewTextError();
            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        Picasso.with(context).resumeTag(context);
    }

    @Override
    public void onPause() {
        super.onPause();
        Picasso.with(context).pauseTag(context);
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        Picasso.with(context).cancelTag(context);
    }
}
