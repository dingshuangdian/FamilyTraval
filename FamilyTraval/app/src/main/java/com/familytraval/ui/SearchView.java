package com.familytraval.ui;

import android.content.Context;
import android.util.AttributeSet;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.familytraval.R;
import com.familytraval.utils.SPStringUtils;

/**
 * Created by dings on 2016/10/24.
 */

public class SearchView extends LinearLayout {

    private ImageView backImgv;
    private EditText searchEdtv;
    private SPSearchViewListener searchListener;

    /**
     * @param context
     * @param attrs
     */
    public SearchView(Context context, AttributeSet attrs) {
        super(context, attrs);
        View view = LayoutInflater.from(context).inflate(R.layout.search_heard_view, this);
        searchEdtv = (EditText) view.findViewById(R.id.search_edtv);
        backImgv = (ImageView) view.findViewById(R.id.back_imgv);
        searchEdtv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (searchListener != null) {
                    //searchListener.onSearchBoxClick(searchEdtv.getText().toString());
                }
            }
        });
        backImgv.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (searchListener != null) searchListener.onBackClick();
            }
        });

        searchEdtv.setOnEditorActionListener(new TextView.OnEditorActionListener() {
            @Override
            public boolean onEditorAction(TextView textView, int actionId, KeyEvent keyEvent) {
                if (actionId == EditorInfo.IME_ACTION_SEARCH) {

                    notifyStartSearching(textView.getText().toString());
                }
                return true;
            }
        });
    }

    /**
     * 通知监听者 进行搜索操作
     *
     * @param text
     */
    private void notifyStartSearching(String text) {
        if (searchListener != null) {
            searchListener.onSearchBoxClick(text);
        }
    }

    public void setSearchKey(String searchKey) {
        if (this.searchEdtv != null && !SPStringUtils.isEmpty(searchKey)) {
            this.searchEdtv.setText(searchKey);
        }
    }

    public EditText getSearchEditText() {
        return this.searchEdtv;
    }

    public void setSearchViewListener(SPSearchViewListener listener) {
        this.searchListener = listener;
    }

    public interface SPSearchViewListener {
        public void onBackClick();

        public void onSearchBoxClick(String keyword);
    }

}
