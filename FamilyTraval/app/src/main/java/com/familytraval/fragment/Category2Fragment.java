package com.familytraval.fragment;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.chanven.lib.cptr.loadmore.GridViewWithHeaderAndFooter;
import com.familytraval.R;
import com.familytraval.adapter.CategoryRecyclerAdapetr;
import com.familytraval.adapter.TypeAdapter;
import com.familytraval.bean.Cheese;
import com.familytraval.bean.Type;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by dings on 2016/10/28.
 */

public class Category2Fragment extends Fragment {
    private ArrayList<Type> list;
    private ImageView hint_img;
    private TypeAdapter adapter;
    private Type type;
    private ProgressBar progressBar;
    private String typename;
    private RecyclerView recyclerViewCategory;
    private CategoryRecyclerAdapetr categoryRecyclerAdapetr;


    public static Category2Fragment newInstance() {
        Category2Fragment category2Fragment = new Category2Fragment();
        return category2Fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_category2, container, false);


        progressBar = (ProgressBar) view.findViewById(R.id.progressBar);
        hint_img = (ImageView) view.findViewById(R.id.hint_img);
        recyclerViewCategory = (RecyclerView) view.findViewById(R.id.recyclerViewCategory);
        typename = getArguments().getString("typename");
        ((TextView) view.findViewById(R.id.toptype)).setText(typename);
        recyclerViewCategory.setLayoutManager(new GridLayoutManager(recyclerViewCategory.getContext(), 2, GridLayoutManager.VERTICAL, false));
        recyclerViewCategory.setAdapter(categoryRecyclerAdapetr = new CategoryRecyclerAdapetr(getActivity(), R.layout.item_recycle));
        //GetTypeList();
        /**adapter = new TypeAdapter(getActivity(), list);
         recyclerViewCategory.setAdapter(adapter);
         recyclerViewCategory.setOnItemClickListener(new AdapterView.OnItemClickListener() {
        @Override public void onItemClick(AdapterView<?> arg0, View arg1, int arg2, long arg3) {
        }
        });*/
        return view;
    }


    private void GetTypeList() {
        list = new ArrayList<Type>();
        for (int i = 1; i < 35; i++) {
            type = new Type(i, typename + i, "");
            list.add(type);
        }
        progressBar.setVisibility(View.GONE);
    }

    /*private class LoadTask extends AsyncTask<Void, Void, String>{

		@Override
		protected String doInBackground(Void... params) {
			String name[]=new String[]{"shopid","type"};
			String value[]=new String[]{"0","store"};
			return NetworkHandle.requestBypost("app=u_favorite&act=index",name,value);
		}

		@Override
		protected void onPostExecute(String result) {
			progressBar.setVisibility(View.GONE);
			list=new ArrayList<Shop>();
			try {
				if(Constant.isDebug)System.out.println("result:"+result);
				JSONObject ob=new JSONObject(result);
				if(ob.getString("state").equals("1")){
					arrayToList(ob.getJSONArray("list"));
					adapter=new Love_shop_adapter(getActivity(), list,listView);
					listView.setAdapter(adapter);
					listView.onRefreshComplete();
					if(list.size()<20)
						listView.onPullUpRefreshFail();
					if(list.size()==0)hint_img.setVisibility(View.VISIBLE);
					else hint_img.setVisibility(View.GONE);
				}else{
					//if(tradestate.equals("0"))
						//ResultUtils.handle((Activity_order)getActivity(), ob);
				}
			} catch (Exception e) {
			//	if(tradestate.equals("0"))
					//ResultUtils.handle((Activity_order)getActivity(), "");
				e.printStackTrace();
			}
		}
	}

	private void arrayToList(JSONArray array) throws JSONException{
		JSONObject ob;
		for (int i = 0; i < array.length(); i++) {
			ob=array.getJSONObject(i);
			shop=new Shop(ob.getString("shopid"),ob.getString("shopname"), ob.getString("shoplogo"), ob.getString("weixin"), ob.getString("shopurl"));
			list.add(shop);
		   }
		}
	*/

	/*private class LoadTaskMore extends AsyncTask<Void, Void, String>{
        @Override
		protected String doInBackground(Void... params) {
			String name[]=new String[]{"shopid","type"};
			String value[]=new String[]{list.get(list.size()-1).getShopid(),"store"};
			return NetworkHandle.requestBypost("app=u_favorite&act=index",name,value);
		}
		@Override
		protected void onPostExecute(String result) {
			if(Constant.isDebug)System.out.println("result:"+result);
			try {
				JSONObject ob=new JSONObject(result);
				if(ob.getString("state").equals("1")){
					JSONArray array=ob.getJSONArray("list");
					arrayToList(array);
					if(array.length()>0)
						adapter.notifyDataSetChanged();
					if(array.length()<20)
						listView.onPullUpRefreshFail();
					else
						listView.onPullUpRefreshComplete();
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		}

	}*/

}
