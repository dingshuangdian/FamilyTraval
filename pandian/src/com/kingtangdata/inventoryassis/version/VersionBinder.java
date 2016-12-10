/**
 * 
 */
package com.kingtangdata.inventoryassis.version;

import android.app.Activity;
import android.os.Binder;

/**
 * <p>
 * Description: ILocalService.java
 * 定义回调方法，用于活动与服务的通信
 * 
 * </p>
 * @author liyang
 * 2011-10-27
 * 
 */
public abstract class VersionBinder extends Binder{
	public abstract void setActivity(Activity mActivity);
}
