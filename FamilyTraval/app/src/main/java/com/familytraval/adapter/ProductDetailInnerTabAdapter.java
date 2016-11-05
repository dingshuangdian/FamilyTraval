package com.familytraval.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import com.familytraval.fragment.ProductCommentListFragment;
import com.familytraval.fragment.ProductPictureTextDetaiFragment;

/**
 * Created by dings on 2016/10/26.
 */

public class ProductDetailInnerTabAdapter extends FragmentPagerAdapter {

    private ProductPictureTextDetaiFragment pictureTextDetaiFragment;
    private ProductCommentListFragment commentListFragment;

    private String goodsId;
    private String contents;

    //Titles的标识
    public static String[] productDetailInnerTitles = new String[]{"图文详情", "商品评价"};

    public ProductDetailInnerTabAdapter(FragmentManager fm, String goodsId, String contents) {
        super(fm);
        this.goodsId = goodsId;
        this.contents = contents;
        pictureTextDetaiFragment = new ProductPictureTextDetaiFragment();
        pictureTextDetaiFragment.setContent(this.contents);
        commentListFragment = new ProductCommentListFragment();
        commentListFragment.setGoodsId(this.goodsId);
    }

    @Override
    public Fragment getItem(int position) {
        if (position == 0) {
            pictureTextDetaiFragment.loadData();
            return pictureTextDetaiFragment;
        } else {
            commentListFragment.loadData();
            return commentListFragment;
        }
    }

    @Override
    public CharSequence getPageTitle(int position) {
        return productDetailInnerTitles[position];
    }

    @Override
    public int getCount() {
        return productDetailInnerTitles.length;
    }

}

