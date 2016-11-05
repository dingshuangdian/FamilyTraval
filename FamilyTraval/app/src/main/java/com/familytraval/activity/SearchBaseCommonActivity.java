package com.familytraval.activity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.KeyEvent;
import android.view.View;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;

import com.familytraval.R;
import com.familytraval.adapter.SearchkeyListAdapter;
import com.familytraval.common.MobileConstants;
import com.familytraval.data.SaveData;
import com.familytraval.ui.SearchView;
import com.soubao.tpshop.utils.SPStringUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dings on 2016/10/24.
 */

@EActivity(R.layout.common_search)
public class SearchBaseCommonActivity extends BaseActivity implements SearchView.SPSearchViewListener {
    private String TAG = "SPSearchCommonActivity";

    @ViewById(R.id.search_delete_btn)
    Button deleteBtn;

    @ViewById(R.id.search_key_listv)
    ListView searchkeyListv;

    @ViewById(R.id.search_view)
    SearchView searchView;
    SearchkeyListAdapter mAdapter;
    List<String> mSearchkeys;

    Handler mHandler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case MobileConstants.MSG_CODE_SEARCHKEY:
                    startSearch(msg.obj.toString());
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        super.onCreate(savedInstanceState);
    }

    @AfterViews
    public void init() {
        super.init();
    }

    @Override
    public void initSubViews() {
        searchView.getSearchEditText().setFocusable(true);
        searchView.setSearchViewListener(this);
    }

    @Override
    public void initData() {

        mAdapter = new SearchkeyListAdapter(this, mHandler);
        searchkeyListv.setAdapter(mAdapter);

        if (getIntent() != null && getIntent().getStringExtra("searchKey") != null) {
            String searchKey = getIntent().getStringExtra("searchKey");
            searchView.setSearchKey(searchKey);
        }

       /* InputMethodManager imm = (InputMethodManager)getSystemService(Context.INPUT_METHOD_SERVICE);
        if(searchView.getSearchEditText()!=null)imm.showSoftInputFromInputMethod(searchView.getSearchEditText().getWindowToken(), 0);*/

        loadKey();
        mAdapter.setData(mSearchkeys);
    }

    @Override
    protected void onStart() {
        super.onStart();
        //loadKey();
        //if(mAdapter == null)mAdapter.setData(mSearchkeys);
    }

    @Override
    public void initEvent() {
        searchkeyListv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                String key = (String) mAdapter.getItem(position);
                startSearch(key);
            }
        });

        if (searchView.getSearchEditText() != null) {
            searchView.getSearchEditText().setOnKeyListener(new View.OnKeyListener() {
                @Override
                public boolean onKey(View v, int keyCode, KeyEvent event) {
                    if (keyCode == KeyEvent.KEYCODE_SEARCH || keyCode == KeyEvent.KEYCODE_ENTER) {
                        String searchKey = searchView.getSearchEditText().getText().toString();
                        startSearch(searchKey);
                    }
                    return false;
                }
            });
        }
    }

    @Click({R.id.search_delete_btn})
    public void onButtonClick(View v) {
        switch (v.getId()) {
            case R.id.search_delete_btn:
                if (searchView.getSearchEditText() != null) {
                    searchView.getSearchEditText().setText("");
                }
                //清空搜索历史
                SaveData.putValue(this, MobileConstants.KEY_SEARCH_KEY, "");
                loadKey();
                mAdapter.setData(mSearchkeys);
                break;
        }
    }

    @Override
    public void onBackClick() {
        this.finish();
    }

    @Override
    public void onSearchBoxClick(String keyword) {

    }

    public void loadKey() {
        mSearchkeys = new ArrayList<String>();
        String searchKey = SaveData.getString(this, MobileConstants.KEY_SEARCH_KEY);
        if (!SPStringUtils.isEmpty(searchKey)) {
            String[] keys = searchKey.split(",");
            if (keys != null)
                for (int i = 0; i < keys.length; i++) {
                    if (!SPStringUtils.isEmpty(keys[i])) {
                        mSearchkeys.add(keys[i]);
                    }
                }
        }/*else{
            mSearchkeys.add("香水");
            mSearchkeys.add("充值卡");
        }*/

    }

    public void saveKey(String key) {
        String searchKey = SaveData.getString(this, MobileConstants.KEY_SEARCH_KEY);
        if (!SPStringUtils.isEmpty(searchKey) && !searchKey.contains(key)) {
            searchKey += "," + key;
        } else {
            searchKey = key;
        }
        SaveData.putValue(this, MobileConstants.KEY_SEARCH_KEY, searchKey);
    }

    public void startSearch(String searchKey) {
        if (!SPStringUtils.isEmpty(searchKey)) {
            saveKey(searchKey);
        }
        Intent intent = new Intent(this, ProductListSearchResultActivity.class);
        intent.putExtra("searchKey", searchKey);
        startActivity(intent);
        this.finish();
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (searchView.getSearchEditText() != null) {
            searchView.getSearchEditText().setFocusable(true);
            searchView.getSearchEditText().setFocusableInTouchMode(true);
        }
        // searchView.getSearchEditText().requestFocus();
    }
}
