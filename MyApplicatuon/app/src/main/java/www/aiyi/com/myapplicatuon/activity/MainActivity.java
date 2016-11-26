package www.aiyi.com.myapplicatuon.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.KeyEvent;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import www.aiyi.com.myapplicatuon.R;
import www.aiyi.com.myapplicatuon.fragment.MainFragment;
import www.aiyi.com.myapplicatuon.fragment.MeFragment;
import www.aiyi.com.myapplicatuon.fragment.MessageFragment;
import www.aiyi.com.myapplicatuon.fragment.TaskFragment;

public class MainActivity extends BaseFragmentActivity {
    private static final String TAG = MainActivity.class.getSimpleName();
    private static final String CURR_INDEX = "curr_Index";
    private static int currIndex = 0;
    private RadioGroup group;
    private TextView title;
    private ArrayList<String> fragmentTags;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        fragmentManager = getSupportFragmentManager();
        initData(savedInstanceState);
        initView();
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURR_INDEX, currIndex);
    }

    private void initData(Bundle savedInstanceState) {
        fragmentTags = new ArrayList<>(Arrays.asList("HomeFragment", "ImFragment", "InterestFragment", " MemberFragment"));
        currIndex = 0;
        if (savedInstanceState != null) {
            currIndex = savedInstanceState.getInt(CURR_INDEX);
            hideSafedFragment();

        }
    }

    private void hideSafedFragment() {
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTags.get(currIndex));
        if (fragment != null) {
            fragmentManager.beginTransaction().hide(fragment).commit();

        }
    }

    private void initView() {
        title = (TextView) findViewById(R.id.textHeadTitle);
        group = (RadioGroup) findViewById(R.id.group);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.foot_bar_home:
                        currIndex = 0;
                        title.setText("首页");
                        break;
                    case R.id.foot_bar_im:
                        currIndex = 1;
                        title.setText("任务");
                        break;
                    case R.id.foot_bar_interest:
                        currIndex = 2;
                        title.setText("消息");
                        break;
                    case R.id.main_footbar_user:
                        currIndex = 3;
                        title.setText("我");
                        break;
                    default:
                        break;
                }
                showFragment();

            }
        });
        showFragment();
    }

    private void showFragment() {
        if (currIndex == 3) {

        }
        FragmentTransaction fragmentTransaction = fragmentManager.beginTransaction();
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTags.get(currIndex));
        if (fragment == null) {
            fragment = instantFragment(currIndex);
        }
        for (int i = 0; i < fragmentTags.size(); i++) {
            Fragment f = fragmentManager.findFragmentByTag(fragmentTags.get(i));
            if (f != null && f.isAdded()) {
                fragmentTransaction.hide(f);
            }
        }
        if (fragment.isAdded()) {
            fragmentTransaction.show(fragment);

        } else {
            fragmentTransaction.add(R.id.fragment_container, fragment, fragmentTags.get(currIndex));
        }
        fragmentTransaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();


    }

    private Fragment instantFragment(int currIndex) {
        switch (currIndex) {
            case 0:
                return new MainFragment();
            case 1:
                return new MessageFragment();
            case 2:
                return new TaskFragment();
            case 3:
                return new MeFragment();
            default:
                return null;

        }
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }
}
