package com.familytraval.fragment;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.familytraval.R;
import com.familytraval.ui.DragLayout;

/**
 * Created by dings on 2016/11/3.
 */

public class ProductContainerFragment extends Fragment {
    private ProductVerticalFragment1 fragment1;
    private ProductVerticalFragment2 fragment2;
    DragLayout draglayout;

    public static ProductContainerFragment newInstance() {
        ProductContainerFragment productContainerFragment = new ProductContainerFragment();
        return productContainerFragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_container, container, false);
        fragment1 = new ProductVerticalFragment1();
        fragment2 = new ProductVerticalFragment2();
        this.getChildFragmentManager().beginTransaction()
                .add(R.id.first, fragment1).add(R.id.second, fragment2)
                .commit();
        DragLayout.ShowNextPageNotifier nextIntf = new DragLayout.ShowNextPageNotifier() {
            @Override
            public void onDragNext() {

            }
        };
        draglayout = (DragLayout) view.findViewById(R.id.draglayout);
        draglayout.setNextPageListener(nextIntf);
        return view;
    }
}
