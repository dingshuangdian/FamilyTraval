package com.kingtangdata.inventoryassis.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.sql.Timestamp;

import android.os.Environment;
import android.text.TextUtils;


/**
 * @author aihua.yan 2012-04-01 SD卡工具类，处理一切与sd卡相关的操作
 * @see isSDCardExist(), 查看是否存在sd卡。
 * 
 */
public class SDCardUtil {
	// SD卡不存在
	public static  String SDCARD_IS_UNMOUTED = "sdcard is not exist";

	// 判断sdcard是否存在,true为存在，false为不存在
	public static boolean isSDCardExist() {
		String status = Environment.getExternalStorageState();
		boolean flag =  status.equals(
				android.os.Environment.MEDIA_MOUNTED);
		 LogUtils.logd("SDCardUtil", "检查SD卡结果： "+ ""+((flag)?"可读  ":"不可读  ")+"\n"+"SDCard Status - " + status+" "+"\n" 
					+ ".....................! \n"+ " current time is: "
					+ new Timestamp(System.currentTimeMillis()).toString());
		 return flag;
	}
	
	
	// 判断sdcard的状态，并告知用户
	public static String checkAndReturnSDCardStatus() {
		String status = Environment.getExternalStorageState();
		if(status!=null){
		//SD已经挂载,可以使用
		if (status.equals(android.os.Environment.MEDIA_MOUNTED)) {
			return "1";
		} else if (status.equals(android.os.Environment.MEDIA_REMOVED)) {
			//SD卡已经已经移除
			return "SD卡已经移除或不存在";

		} else if (status.equals(android.os.Environment.MEDIA_SHARED)) {
			//SD卡正在使用中
			return "SD卡正在使用中";

		} else if (status.equals(android.os.Environment.MEDIA_MOUNTED_READ_ONLY)) {
			//SD卡只能读，不能写
			return "SD卡只能读，不能写";
		} else {
			//SD卡的其它情况
			return "SD卡不能使用或不存在";
		}
		} else {
			//SD卡的其它情况
			return "SD卡不能使用或不存在";
		}
	}

	// 获取sdcard路径
	public static String getSdcardUrl() {
		File sdDir = null;
		if (isSDCardExist()) {
			sdDir = Environment.getExternalStorageDirectory().getAbsoluteFile();// 获取跟目录
//			LogUtils.loge("sd卡路径", sdDir.toString());
			return sdDir.toString();
		} else {
			return SDCARD_IS_UNMOUTED;
		}
	}

	/************************************************************************
	 * 获取各种路径 的 方法
	 ************************************************************************/

	// 获取本地临时数据的根路径，要及时删除里面内容
	public static String getRootPath() {
		if (isSDCardExist()) {
			String root = getSdcardUrl() + FilePath.APP_DATA_PATH;
			File dir = new File(root);
			if (!dir.exists()) {
				dir.mkdir();
			}
			return root;
		} else {
			return SDCARD_IS_UNMOUTED;
		}
	}

	
	// 获取本地Log存放路径
	public static String getLogPath() {
		if (isSDCardExist()) {
			// 分两部检测 /mama100_data/storepic/ 这两个目录的建立情况
			String saveDir = getRootPath() + FilePath.APP_LOG_PATH;
			File dir = new File(saveDir);
			if (!TextUtils.isEmpty(dir.getPath())&&!dir.exists()) {
					dir.mkdir();
			}
			LogUtils.loge("getLogPath", " - "+saveDir);
			return saveDir;
		} else {
			return SDCARD_IS_UNMOUTED;
		}
	}	
	

	/******************************************************************************
	 * 删除用到的 方法
	 ******************************************************************************/

	/**
	 * 删除SDCARD上的照片
	 * 
	 * @param fName
	 *            文件名
	 * @return
	 */
	public static boolean deletePicture(String fName) {
		if (isSDCardExist()) {
			File f = new File(fName);
			if (f.exists()) {
				if (f.delete()) {
					return true;
				} else {
					return false;
				}
			} else {
				return false;
			}
		} else {
			return false;
		}
	}

	/**
	 * 复制文件a 到文件 b
	 */
	public static boolean CopyFile(String afile, String bfolder,
			String newFileName) {
		if (isSDCardExist()) {
			File srcFile = new File(afile);
			File destDir = new File(bfolder);
			long copySizes = 0;
			if (!srcFile.exists()) {
				System.out.println("源文件不存在");
				copySizes = -1;
				return false;
			} else if (!destDir.exists()) {
				System.out.println("目标目录不存在");
				copySizes = -1;
				return false;
			} else if (newFileName == null) {
				System.out.println("文件名为null");
				copySizes = -1;
				return false;
			} else {
				try {
					FileChannel fcin = new FileInputStream(srcFile)
							.getChannel();
					FileChannel fcout = new FileOutputStream(new File(destDir,
							newFileName)).getChannel();
					ByteBuffer buff = ByteBuffer.allocate(1024);
					int b = 0, i = 0;
					// long t1 = System.currentTimeMillis();
					/*
					 * while(fcin.read(buff) != -1){ buff.flip();
					 * fcout.write(buff); buff.clear(); i++; }
					 */
					long size = fcin.size();
					fcin.transferTo(0, fcin.size(), fcout);
					// fcout.transferFrom(fcin,0,fcin.size());
					// 一定要分清哪个文件有数据，那个文件没有数据，数据只能从有数据的流向
					// 没有数据的文件
					// long t2 = System.currentTimeMillis();
					fcin.close();
					fcout.close();
					copySizes = size;
					// long t = t2-t1;
					// System.out.println("复制了" + i + "个字节\n" + "时间" + t);
					// System.out.println("复制了" + size + "个字节\n" + "时间" + t);
				} catch (FileNotFoundException e) {
					e.printStackTrace();
					return false;
				} catch (IOException e) {
					e.printStackTrace();
					return false;
				}
			}
			return true;

		} else {
			return false;
		}
	}
}
