package com.familytraval.fragment;

/**
 * Created by dings on 2016/10/26.
 */

import android.app.Activity;
import android.content.Context;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebView;
import android.webkit.WebViewClient;

import com.familytraval.R;

/**
 *  商品详情 -> 图文详情
 *
 */
public class ProductPictureTextDetaiFragment extends BaseFragment {

    private String mHtml;
    private Context mContext;
    private WebView mWebView ;
    boolean isFirstLoad;


    @Override
    public void onAttach(Activity activity) {
        super.onAttach(activity);
        this.mContext = activity;
    }

    public void setContent(String content){
        this.mHtml = content;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        isFirstLoad = true;
        View view = inflater.inflate(R.layout.common_webview_main, null,false);
        mWebView = (WebView)view.findViewById(R.id.common_webview);
        mWebView.getSettings().setJavaScriptEnabled(true);
        mWebView.getSettings().setJavaScriptCanOpenWindowsAutomatically(true);
        mWebView.getSettings().setSupportMultipleWindows(true);
        mWebView.setWebViewClient(new WebViewClient());
        mWebView.setWebChromeClient(new WebChromeClient());
        mWebView.getSettings().setBuiltInZoomControls(false);
        mWebView.getSettings().setSupportZoom(false);//设定支持缩放

        loadData();
        return view;
    }

    public void loadData(){
        if (isFirstLoad && mWebView!=null) {
            mWebView.loadDataWithBaseURL(null, mHtml, "text/html", "utf-8", null);
            isFirstLoad = false;
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
    }

    @Override
    public void initSubView(View view) {
        // TODO Auto-generated method stub

    }

    @Override
    public void initEvent() {
        // TODO Auto-generated method stub

    }

    @Override
    public void initData() {

    }


}

