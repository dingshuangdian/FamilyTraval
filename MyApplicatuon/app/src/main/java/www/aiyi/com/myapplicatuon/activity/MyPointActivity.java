package www.aiyi.com.myapplicatuon.activity;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import www.aiyi.com.myapplicatuon.R;
import www.aiyi.com.myapplicatuon.ui.UIHelper;
import www.aiyi.com.myapplicatuon.ui.swipebacklayout.SwipeBackActivity;

public class MyPointActivity extends AppCompatActivity {
    private Button btn_back;
    private TextView title;
    private Button btn_detail;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_my_point);
        init();
        btn_detail.setOnClickListener(clickListener);
    }

    private void init() {
        btn_back = (Button) findViewById(R.id.btnBack);
        title = (TextView) findViewById(R.id.textHeadTitle);
        btn_detail = (Button) findViewById(R.id.btn_detail);
        title.setText("我的积分");
        btn_back.setVisibility(View.VISIBLE);
        btn_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_detail:
                    UIHelper.showDetailPoint(MyPointActivity.this);
                    break;
            }
        }
    };
}
