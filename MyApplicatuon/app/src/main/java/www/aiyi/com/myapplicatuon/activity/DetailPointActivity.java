package www.aiyi.com.myapplicatuon.activity;

import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioGroup;
import android.widget.TextView;

import java.util.ArrayList;

import www.aiyi.com.myapplicatuon.R;

public class DetailPointActivity extends AppCompatActivity {
    private Button btn_back;
    private TextView title;
    private Button btn_jiang;
    private Button btn_kou;
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
        init();
        btn_jiang.setOnClickListener(clickListener);
        btn_kou.setOnClickListener(clickListener);
        btn_back.setOnClickListener(clickListener);
    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    private void init() {
        btn_back = (Button) findViewById(R.id.btnBack);
        title = (TextView) findViewById(R.id.textHeadTitle);
        btn_jiang = (Button) findViewById(R.id.btn_jiang);
        btn_kou = (Button) findViewById(R.id.btn_kou);
        group = (RadioGroup) findViewById(R.id.ll);
        title.setText("积分明细");
        btn_back.setVisibility(View.VISIBLE);
        group.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup group, int checkedId) {
                switch (checkedId) {
                    case R.id.btn_jiang:

                }
            }
        });
    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        private int flag = 0;

        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btnBack:
                    finish();
                    break;
                case R.id.btn_jiang:
                    if (flag == 1) {
                        btn_kou.isClickable();
                        //btn_kou.getResources().getColor(R.color.background_color);
                        btn_jiang.setBackgroundResource(R.drawable.btn_back_normal);
                        btn_jiang.setClickable(false);
                        //btn_jiang.getResources().getColor(R.color.gray);
                        btn_jiang.setBackgroundResource(R.drawable.btn_back_press);
                        flag = 0;
                    }
                    break;
                case R.id.btn_kou:
                    if (flag == 0) {
                        btn_jiang.isClickable();
                        // btn_jiang.getResources().getColor(R.color.background_color);
                        btn_jiang.setBackgroundResource(R.drawable.btn_back_normal);
                        btn_kou.setClickable(false);
                        //btn_kou.getResources().getColor(R.color.gray);
                        btn_kou.setBackgroundResource(R.drawable.btn_back_press);
                        flag = 1;
                    }
                    break;
            }
        }
    };
}
