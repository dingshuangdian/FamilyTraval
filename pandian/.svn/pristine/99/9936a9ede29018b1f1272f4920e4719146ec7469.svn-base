package com.kingtangdata.inventoryassis.widget;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;

public class MyViewPager extends ViewPager {

	private GestureDetector mGestureDetector;

	public MyViewPager(Context context) {
		super(context);

		mGestureDetector = new GestureDetector(new YScrollDetector());
	}

	public MyViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);

		mGestureDetector = new GestureDetector(new YScrollDetector());
	}

	// 拦截 TouchEvent
	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		// TODO Auto-generated method stub
		return super.onInterceptTouchEvent(ev) || mGestureDetector.onTouchEvent(ev);
	}

	// 处理 TouchEvent
	@Override
	public boolean onTouchEvent(MotionEvent arg0) {
		// TODO Auto-generated method stub
		return super.onTouchEvent(arg0);
	}

	// 因为这个执行的顺序是 父布局先得到 action_down的事件

	/**
	 * onInterceptTouchEvent(MotionEvent ev)方法，这个方法只有ViewGroup类有
	 * 如LinearLayout，RelativeLayout等 可以包含子View的容器的
	 * 
	 * 用来分发 TouchEvent 此方法 返回true 就交给本 View的 onTouchEvent处理 此方法 返回false
	 * 就交给本View的 onInterceptTouchEvent 处理
	 */
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {

		// 让父类不拦截触摸事件就可以了。
		// this.getParent().requestDisallowInterceptTouchEvent(true);
		return super.dispatchTouchEvent(ev);

	}

	class YScrollDetector extends SimpleOnGestureListener {
		@Override
		public boolean onScroll(MotionEvent e1, MotionEvent e2,
				float distanceX, float distanceY) {
			Log.d("DEBUG", "distance = " + distanceY);
			
			if(Math.abs(distanceY) <5){
				Log.d("DEBUG", "result = " + "竖屏滑动距离太短直接拦截");
				
				return true;
			}
			
			if (Math.abs(distanceY) < Math.abs(distanceX)) {
				Log.d("DEBUG", "result = " + "拦截");
				
				return true;
			}else{
				Log.d("DEBUG", "result = " + "不拦截");
				
				return false;
			}
		}
	}
}
