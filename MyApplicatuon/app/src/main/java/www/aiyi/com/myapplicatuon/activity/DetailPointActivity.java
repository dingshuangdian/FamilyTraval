package www.aiyi.com.myapplicatuon.activity;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import www.aiyi.com.myapplicatuon.R;
import www.aiyi.com.myapplicatuon.fragment.JiangFragmnet;
import www.aiyi.com.myapplicatuon.fragment.KouFragment;

public class DetailPointActivity extends AppCompatActivity {
    private Button btn_back;
    private TextView title;
    private RadioGroup group;
    private static final String TAG = MyPointActivity.class.getSimpleName();
    private static final String CURR_INDEX = "currIndex";
    private static int currIndex = 0;
    private ArrayList<String> fragmentTags;
    private FragmentManager fragmentManager;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_point);
        fragmentManager = getSupportFragmentManager();

        initData(savedInstanceState);
        init();
        btn_back.setOnClickListener(clickListener);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt(CURR_INDEX, currIndex);
    }

    private void initData(Bundle savedInstanceState) {
        fragmentTags = new ArrayList<>(Arrays.asList("JiangFragment", "KouFragment"));
        currIndex = 0;
        if (savedInstanceState != null) {
            currIndex = savedInstanceState.getInt(CURR_INDEX);
            hideSavedFragment();
        }

    }

    private void hideSavedFragment() {
        Fragment fragment = fragmentManager.findFragmentByTag(fragmentTags.get(currIndex));
        if (fragment != null) {
            fragmentManager.beginTransaction().hide(fragment).commit();
        }
    }

    private void init() {
        btn_back = (Button) findViewById(R.id.btnBack);
        title = (TextView) findViewById(R.id.textHeadTitle);
        group = (RadioGroup) findViewById(R.id.ll);
        title.setText("积分明细");
        btn_back.setVisibility(View.VISIBLE);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.btn_jiang:
                        currIndex = 0;
                        break;
                    case R.id.btn_kou:
                        currIndex = 1;
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
            fragmentTransaction.add(R.id.fl, fragment, fragmentTags.get(currIndex));

        }
        fragmentTransaction.commitAllowingStateLoss();
        fragmentManager.executePendingTransactions();

    }

    private Fragment instantFragment(int currIndex) {
        switch (currIndex) {
            case 0:
                return new JiangFragmnet();
            case 1:
                return new KouFragment();
            default:
                return null;
        }
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnBack:
                    finish();
                    break;
            }
        }
    };
}
