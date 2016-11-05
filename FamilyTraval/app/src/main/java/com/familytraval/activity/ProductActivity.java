package com.familytraval.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.familytraval.R;
import com.familytraval.fragment.Home2Fragment;
import com.familytraval.fragment.HomeFragment;
import com.familytraval.fragment.ProductContainerFragment;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class ProductActivity extends FragmentActivity {
    ViewPager detail_pager;
    Button btnBack;
    TextView textHeadTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product);
        textHeadTitle = (TextView) findViewById(R.id.textHeadTitle);
        btnBack = (Button) findViewById(R.id.btnBack);
        detail_pager = (ViewPager) findViewById(R.id.detail_pager);
        init();

    }

    /**
     * 初始化View
     */

    private void init() {
        textHeadTitle.setText("商品详情");
        btnBack.setVisibility(View.VISIBLE);
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                finish();
            }
        });
        final MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getSupportFragmentManager());
        adapter.addFragment(new ProductContainerFragment().newInstance(), "HomeFragment");
        detail_pager.setAdapter(adapter);
    }

    private class MyFragmentPagerAdapter extends FragmentPagerAdapter {
        private List<Fragment> mFragments = new LinkedList<>();
        private List<String> mTitles = new LinkedList<>();

        public MyFragmentPagerAdapter(FragmentManager fm) {
            super(fm);
        }

        public void addFragment(Fragment fragment, String title) {
            mFragments.add(fragment);
            mTitles.add(title);
        }

        @Override
        public Fragment getItem(int position) {
            return mFragments.get(position);
        }

        @Override
        public CharSequence getPageTitle(int position) {
            return mTitles.get(position);
        }

        @Override
        public int getCount() {
            return mFragments.size();
        }
    }

}
