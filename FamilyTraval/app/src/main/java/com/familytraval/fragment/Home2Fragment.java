package com.familytraval.fragment;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.RelativeLayout;
import android.widget.TextView;
import com.familytraval.R;
import com.familytraval.activity.CitySelectActivity;
import com.familytraval.activity.SearchBaseCommonActivity_;
import com.familytraval.zxing.MipcaActivityCapture;
import java.util.LinkedList;
import java.util.List;
/**
 * Created by dings on 2016/10/28.
 */
public class Home2Fragment extends Fragment {
    ViewPager pager;
    RelativeLayout iv_action_right;
    private TextView tv_location;
    EditText searchkey_edtv;
    TextView image_left;
    private final static int SCANNIN_GREQUEST_CODE = 1;


    public static Home2Fragment newInstance() {
        Home2Fragment home2Fragment = new Home2Fragment();
        return home2Fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home2, container, false);
        pager = (ViewPager) view.findViewById(R.id.frameLayout);
        init();
        //搜索框
        searchkey_edtv = (EditText) view.findViewById(R.id.searchkey_edtv);
        //扫码
        image_left = (TextView) view.findViewById(R.id.image_left);
        //干嘛的！！？？？
        searchkey_edtv.setFocusable(false);
        searchkey_edtv.setFocusableInTouchMode(false);
        iv_action_right = (RelativeLayout) view.findViewById(R.id.iv_action_right);
        tv_location = (TextView) view.findViewById(R.id.tv_location);
        iv_action_right.setOnClickListener(onClickListener);
        searchkey_edtv.setOnClickListener(onClickListener);
        image_left.setOnClickListener(onClickListener);
        return view;
    }
    private void init() {
        final MyFragmentPagerAdapter adapter = new MyFragmentPagerAdapter(getChildFragmentManager());
        adapter.addFragment(new HomeFragment().newInstance(), "HomeFragment");
        pager.setAdapter(adapter);
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

    //定位
    private View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_action_right:
                    startActivityForResult(new Intent(getContext(), CitySelectActivity.class), 99);
                    break;
                case R.id.searchkey_edtv:
                    Intent intent = new Intent(getContext(), SearchBaseCommonActivity_.class);
                    startActivity(intent);
                    break;
                case R.id.image_left:
                    Intent intentC = new Intent();
                    intentC.setClass(getContext(), MipcaActivityCapture.class);
                    intentC.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                    startActivityForResult(intentC, SCANNIN_GREQUEST_CODE);
                    break;
            }
        }
    };

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        // TODO Auto-generated method stub
        try {
            switch (resultCode) {
                case 99:
                    tv_location.setText(data.getStringExtra("city"));
                    break;
                default:
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        super.onActivityResult(requestCode, resultCode, data);
    }
}
