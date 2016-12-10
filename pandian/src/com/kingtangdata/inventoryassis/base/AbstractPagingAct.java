package com.kingtangdata.inventoryassis.base;

import android.os.Bundle;
import android.widget.AbsListView;
import android.widget.AbsListView.OnScrollListener;

import com.kingtangdata.inventoryassis.R;

/**
 * 抽象的分页活动界面基类 
 * @author liyang
 */
public abstract class AbstractPagingAct extends BaseActivity implements OnScrollListener {

	protected static final int DEFAULT_PAGE_SIZE = 20; //每页10条

	//下载过程中，各种文本提示
	protected static String FOOTER_LOADING = "";
	protected static String FOOTER_NO_DATA = "";
	protected static String FOOTER_LOAD_END = "";

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		FOOTER_LOADING = "正在加载,请稍候...";
		FOOTER_NO_DATA = "抱歉，没有查询到相关数据";
		FOOTER_LOAD_END = "已经全部加载完成";
	}

	// 当前屏幕上可见的最后一个条目的位置
	protected int visibleLastItemIdx;
	
	/***************************************************************
	 * 父类只关注滑动事件处理，子类去实现具体的
	 ***************************************************************/
	/**
	 * 显示分页提示信息
	 */
	protected abstract void showPageHint();

	/**
	 * 更新分页提示信息内容
	 */
	protected abstract void updatePageHint(String content);

	/**
	 * 关闭分页提示信息
	 */
	protected abstract void hidePageHint();

	/**
	 * 获取已经下载到客户端的条目大小
	 */
	protected abstract int getDownloadedItemSize();

	/**
	 * 获取服务端返回的条目总数
	 */
	protected abstract int getTotalNum();

	/**
	 * 获取每页条数
	 */
	protected abstract int getPageSize();

	/**
	 * 开始加载下一页的数据
	 */
	protected abstract void loadNextPageData();

	/**
	 * 是否正在加载数据
	 * 
	 * @return
	 */
	protected abstract boolean isLoading();

	/**
	 * 处理是否开始加载下一页的数据
	 */
	private void handleLoadNextPageData() {
		String navInfo = getNavInfo();
		updatePageHint(navInfo);
		//如果子类下载的数据比总数据小，就继续下载
		if (getDownloadedItemSize() < getTotalNum()) {
			if (!isLoading()) {
				loadNextPageData();
			}
		}
	}

	/**
	 * 当用户滑动了列表,完成了一次滑动
	 */
	@Override
	public void onScrollStateChanged(AbsListView view, int state) {
		//从子类获取已下载数
		int sizeOfItems = getDownloadedItemSize();

		//如果滑动处于空闲状态
		if (state == OnScrollListener.SCROLL_STATE_IDLE) {
			if (visibleLastItemIdx >= sizeOfItems && sizeOfItems >= DEFAULT_PAGE_SIZE) {
				handleLoadNextPageData();
			}
		} else {
			// 处理用户手指一直在拖动(已经拖到要换页的地方未松手)的情况
			//比如，一共30条，现在已下载sizeOfItems = 20条， 用户滑动索引到第20条，才下载剩下的
			if ((visibleLastItemIdx >= sizeOfItems && sizeOfItems >= DEFAULT_PAGE_SIZE)) {
				handleLoadNextPageData();
			}
		}
	}

	/**
	 * 当用户触动了列表
	 * @firstVisibleItem  视野范围内可见第一条数据的索引
	 * @visibleItemCount  视野范围内总可见条数 
	 */	
	@Override
	public void onScroll(AbsListView view, int firstVisibleItem,
			int visibleItemCount, int totalItemCount) {

		//获取视野范围内最后一条记录的索引数
		visibleLastItemIdx = firstVisibleItem + visibleItemCount;
	}

	/**
	 * 获取分页信息内容，获取当前页数/总页数，如： 2/4页
	 * 
	 * @return
	 */
	protected String getNavInfo() {

		int currentPage = getCurrentPageNo();
		int totalPage = getTotalPageCount();

		if (currentPage > totalPage) {
			currentPage = totalPage;
		}

		String navInfo = currentPage + " / " + totalPage
				+ getResources().getString(R.string.glb_message_page_tip);
		return navInfo;
	}

	/**
	 * 获取当前页号
	 * 
	 * @return
	 */
	protected int getCurrentPageNo() {

		int currentPage = 0;
		int pageSize = getPageSize();

		int visibleCount = visibleLastItemIdx + 1;

		if (visibleCount % pageSize == 0) {
			currentPage = visibleCount / pageSize;
		} else {
			currentPage = visibleCount / pageSize + 1;
		}

		return currentPage;
	}

	/**
	 * 获取正要准备显示的页号（马上要加载的下一页）
	 * 
	 * @return
	 */
	protected int getCurrent4DisplayPageNo() {

		int sizeOfItems = getDownloadedItemSize();
		if (sizeOfItems == 0) {
			return 1;
		}

		int nextPage = getCurrentPageNo();

		return nextPage;
	}

	/**
	 * 获取总页数
	 * 
	 * @return
	 */
	protected int getTotalPageCount() {

		int totalPage = 0;

		int totalCount = getTotalNum();
		int pageSize = getPageSize();

		if (totalCount % pageSize == 0) {
			totalPage = totalCount / pageSize;
		} else {
			totalPage = totalCount / pageSize + 1;
		}

		return totalPage;
	}
}
