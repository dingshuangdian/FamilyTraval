package www.aiyi.com.myapplicatuon.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import butterknife.Bind;
import butterknife.ButterKnife;
import in.srain.cube.views.ptr.PtrClassicFrameLayout;
import www.aiyi.com.myapplicatuon.R;
import www.aiyi.com.myapplicatuon.activity.MainActivity;
import www.aiyi.com.myapplicatuon.ui.LoadMoreListView;
import www.aiyi.com.myapplicatuon.ui.quickadapter.QuickAdapter;

/**
 * Created by dings on 2016-11-28.
 */

public class JiangFragmnet extends Fragment {
    private MainActivity context;
    private SearchParam param;
    private int pno = 1;
    private boolean isLoadAll;
    @Bind(R.id.rotate_header_list_view_frame)
    PtrClassicFrameLayout ptrClassicFrameLayout;
    @Bind(R.id.listView)
    LoadMoreListView listView;
    QuickAdapter<> adapter;

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
        context= (MainActivity) getActivity();
    }
}
