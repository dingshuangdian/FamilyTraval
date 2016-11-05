package com.familytraval.activity;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.view.View;
import android.widget.Button;

import com.familytraval.R;


public class MessageActivity extends FragmentActivity {
    private Button bt_next;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);
        bt_next = (Button) findViewById(R.id.bt_next);
        bt_next.setOnClickListener(clickListener);

    }

    private View.OnClickListener clickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.bt_next:
                    Intent homeIntent = new Intent(MessageActivity.this, MainActivity.class);
                    startActivity(homeIntent);
            }
        }
    };
}
