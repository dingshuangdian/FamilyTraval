package com.familytraval.activity;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.widget.DrawerLayout;
import android.view.Gravity;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.chanven.lib.cptr.PtrClassicFrameLayout;
import com.chanven.lib.cptr.PtrDefaultHandler;
import com.chanven.lib.cptr.PtrFrameLayout;
import com.chanven.lib.cptr.loadmore.OnLoadMoreListener;
import com.familytraval.Model.ShopOrder;
import com.familytraval.R;
import com.familytraval.adapter.ProductListAdapter;
import com.familytraval.bean.Category;
import com.familytraval.bean.Product;
import com.familytraval.callback.FailuredListener;
import com.familytraval.callback.SuccessListener;
import com.familytraval.common.MobileApplication;
import com.familytraval.common.MobileConstants;
import com.familytraval.fragment.ProductListFilterFragment;
import com.familytraval.http.SPShopRequest;
import com.familytraval.ui.ProductFilterTabView;
import com.familytraval.utils.DialogUtils;
import com.soubao.tpshop.utils.SPStringUtils;

import org.json.JSONObject;

import java.util.List;

import static com.familytraval.ui.ProductFilterTabView.ProductSortType.composite;

/**
 * Created by dings on 2016/10/25.
 */

public class ProductListSearchResultActivity extends BaseActivity implements ProductListAdapter.ItemClickListener, ProductFilterTabView.OnSortClickListener {

    private String TAG = "ProductListSearchResultActivity";

    private static ProductListSearchResultActivity instance;

    PtrClassicFrameLayout ptrClassicFrameLayout;
    ListView mListView;
    TextView syntheisTxtv;
    TextView salenumTxtv;
    TextView priceTxtv;
    EditText searchText;//搜索文本框
    ImageView backImgv;
    ProductListAdapter mAdapter;
    Category mCategory;    //分类

    ProductFilterTabView mFilterTabView;

    DrawerLayout mDrawerLayout;

    int mPageIndex = 1;        //当前第几页
    boolean mIsMaxPage;            //是否最大页数
    ShopOrder mShopOrder;        //排序实体
    String mHref;                //请求URL
    String mSearchkey;            //搜索关键字
    List<Product> mProducts;
    ProductListFilterFragment mFilterFragment;
    Handler mHandler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case MobileConstants.MSG_CODE_FILTER_CHANGE_ACTION:
                    if (msg.obj != null) {
                        mHref = msg.obj.toString();
                        refreshData();
                    }
                    break;
            }
        }
    };

    @Override
    protected void onCreate(Bundle bundle) {
        //super.setCustomerTitle(true, true , "商品列表");
        super.onCreate(bundle);
        /** 自定义标题栏 , 执行顺序必须是一下顺序, 否则无效果.  */
        requestWindowFeature(Window.FEATURE_CUSTOM_TITLE);
        setContentView(R.layout.product_list);
        getWindow().setFeatureInt(Window.FEATURE_CUSTOM_TITLE, R.layout.product_list_header);
        /** 自定义标题栏  */
        Intent intent = getIntent();
        if (intent != null) {
            mCategory = (Category) intent.getSerializableExtra("category");
        }
        //过滤标题
        super.init();
        refreshData();
        instance = this;
    }

    public static ProductListSearchResultActivity getInstance() {
        return instance;
    }

    @Override
    public void initSubViews() {

        WindowManager wm = (WindowManager) getBaseContext().getSystemService(Context.WINDOW_SERVICE);

        mFilterTabView = (ProductFilterTabView) findViewById(R.id.filter_tabv);
        mFilterTabView.setOnSortClickListener(this);
        ptrClassicFrameLayout = (PtrClassicFrameLayout) this.findViewById(R.id.test_list_view_frame);
        mListView = (ListView) findViewById(R.id.pull_product_listv);

        //综合
        syntheisTxtv = (TextView) findViewById(R.id.sort_button_synthesis);
        salenumTxtv = (TextView) findViewById(R.id.sort_button_salenum);
        priceTxtv = (TextView) findViewById(R.id.sort_button_price);

        searchText = (EditText) findViewById(R.id.search_edtv);
        backImgv = (ImageView) findViewById(R.id.title_back_imgv);
        mDrawerLayout = (DrawerLayout) findViewById(R.id.drawerlayout);

        MobileApplication.getInstance().productListType = 2;
        mFilterFragment = (ProductListFilterFragment) getSupportFragmentManager().findFragmentById(R.id.right_rlayout);

        mDrawerLayout.setDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerOpened(View drawerView) {
                super.onDrawerOpened(drawerView);
            }

            @Override
            public void onDrawerClosed(View drawerView) {
                super.onDrawerClosed(drawerView);
            }

            @Override
            public void onDrawerStateChanged(int newState) {
                super.onDrawerStateChanged(newState);
            }
        });
    }


    @Override
    public void initData() {

        if (getIntent() != null && getIntent().getStringExtra("searchKey") != null) {
            mSearchkey = getIntent().getStringExtra("searchKey");
        }

        if (searchText != null) searchText.setText(mSearchkey);


        mPageIndex = 1;
        mIsMaxPage = false;
        mAdapter = new ProductListAdapter(this, this);
        mListView.setAdapter(mAdapter);

        ptrClassicFrameLayout.postDelayed(new Runnable() {

            @Override
            public void run() {
                //ptrClassicFrameLayout.autoRefresh(true);
            }
        }, 150);

        ptrClassicFrameLayout.setPtrHandler(new PtrDefaultHandler() {

            @Override
            public void onRefreshBegin(PtrFrameLayout frame) {
                //下拉刷新
                refreshData();
            }
        });

        ptrClassicFrameLayout.setOnLoadMoreListener(new OnLoadMoreListener() {

            @Override
            public void loadMore() {
                //上拉加载更多
                loadMoreData();
                ;
            }
        });
    }


    /**
     * 加载数据
     *
     * @return void    返回类型
     * @throws
     * @Description: 加载数据
     */
    public void refreshData() {

        mPageIndex = 1;
        mIsMaxPage = false;
        try {
            showLoadingToast("正在加载商品数据");
            SPShopRequest.searchResultProductListWithPage(mPageIndex, mSearchkey, mHref, new SuccessListener() {
                @Override
                public void onRespone(String msg, Object data) {
                    hideLoadingToast();
                    try {
                        mDataJson = (JSONObject) data;
                        if (mDataJson != null) {
                            mProducts = (List<Product>) mDataJson.get("product");
                            mShopOrder = (ShopOrder) mDataJson.get("order");

                            if (mProducts != null) {
                                mAdapter.setData(mProducts);
                                mAdapter.notifyDataSetChanged();
                            }
                            if (ProductListFilterFragment.getInstance(mHandler) != null) {
                                ProductListFilterFragment.getInstance(mHandler).setDataSource(mDataJson);
                            }
                            mIsMaxPage = false;
                            ptrClassicFrameLayout.setLoadMoreEnable(true);
                        } else {
                            mIsMaxPage = true;
                            ptrClassicFrameLayout.setLoadMoreEnable(false);
                        }
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    refreshView();
                    ptrClassicFrameLayout.refreshComplete();

                }
            }, new FailuredListener() {
                @Override
                public void onRespone(String msg, int errorCode) {
                    hideLoadingToast();
                    DialogUtils.showToast(ProductListSearchResultActivity.this, msg);
                    ptrClassicFrameLayout.refreshComplete();
                    ptrClassicFrameLayout.setLoadMoreEnable(true);
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 加载数据
     *
     * @return void    返回类型
     * @throws
     * @Description: 加载数据
     */
    public void loadMoreData() {
        if (mIsMaxPage) {
            return;
        }
        mPageIndex++;
        try {
            showLoadingToast("正在加载商品数据");
            SPShopRequest.searchResultProductListWithPage(mPageIndex, mSearchkey, mHref, new SuccessListener() {
                @Override
                public void onRespone(String msg, Object data) {
                    hideLoadingToast();
                    try {
                        mDataJson = (JSONObject) data;
                        if (mDataJson != null) {
                            mShopOrder = (ShopOrder) mDataJson.get("order");
                            List<Product> results = (List<Product>) mDataJson.get("product");
                            if (results != null && mProducts != null) {
                                mProducts.addAll(results);
                                mAdapter.setData(mProducts);
                                mAdapter.notifyDataSetChanged();
                            } else if (results == null) {
                                ptrClassicFrameLayout.setLoadMoreEnable(false);
                            }
                            if (ProductListFilterFragment.getInstance(mHandler) != null) {
                                ProductListFilterFragment.getInstance(mHandler).setDataSource(mDataJson);
                            }
                            mIsMaxPage = false;
                            ptrClassicFrameLayout.setLoadMoreEnable(true);
                        } else {
                            mIsMaxPage = true;
                            ptrClassicFrameLayout.setLoadMoreEnable(false);
                        }
                        refreshView();
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                }
            }, new FailuredListener() {
                @Override
                public void onRespone(String msg, int errorCode) {
                    hideLoadingToast();
                    DialogUtils.showToast(ProductListSearchResultActivity.this, msg);
                    ptrClassicFrameLayout.loadMoreComplete(true);
                    mPageIndex--;
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    public void initEvent() {
        searchText.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(ProductListSearchResultActivity.this, SearchBaseCommonActivity_.class);
                if (!SPStringUtils.isEmpty(mSearchkey)) {
                    intent.putExtra("searchKey", mSearchkey);
                }
                ProductListSearchResultActivity.this.startActivity(intent);
            }
        });
        backImgv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ProductListSearchResultActivity.this.finish();
            }
        });
    }

    public void startupActivity(String goodsID) {
        Intent intent = new Intent(this, ProductDetailActivity.class);
        intent.putExtra("goodsID", goodsID);
        startActivity(intent);
    }


    @Override
    public void onItemClickListener(Product product) {
        startupActivity(product.getGoodsID());
    }

    @Override
    public void onFilterClick(ProductFilterTabView.ProductSortType sortType) {

        switch (sortType) {
            case composite:
                if (mShopOrder != null) {
                    mHref = mShopOrder.getDefaultHref();
                }
                break;
            case salenum:
                if (mShopOrder != null) {
                    mHref = mShopOrder.getSaleSumHref();
                }
                break;
            case price:
                if (mShopOrder != null) {
                    mHref = mShopOrder.getPriceHref();
                }
                break;
            case filter:
                openRightFilterView();
                return;
        }
        refreshData();
    }

    public void openRightFilterView() {
        mDrawerLayout.openDrawer(Gravity.RIGHT);
        mDrawerLayout.setDrawerLockMode(DrawerLayout.LOCK_MODE_UNLOCKED, Gravity.RIGHT);
    }

    public void refreshView() {
        if (mShopOrder != null && mShopOrder.getSortAsc() != null) {
            if (mShopOrder.getSortAsc().equalsIgnoreCase("desc")) {
                mFilterTabView.setSort(true);
            } else {
                mFilterTabView.setSort(false);
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();

        searchText.setFocusable(false);
        searchText.setFocusableInTouchMode(false);
    }
}

