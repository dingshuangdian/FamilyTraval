package com.kingtangdata.inventoryassis.util;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 验证工具类
 * 
 * @author jimmy
 * 
 *         modified by liyang 2012-11-28 修改密码验证规则
 */
public class ValidateUtils {

	public static int MEMBER_CARD_NUMBER_LENGTH = 16;
	public static int ANTI_FAKE_NUMBER_LENGTH = 16;
	public static int MOBILE_NUMBER_LENGTH = 11;

	// 新序列号是12位，老序列号是14位
	public static int SERIAL_NUMBER_LENGTH1 = 12;
	public static int SERIAL_NUMBER_LENGTH2 = 14;
	public static int TRADE_PASSWORD_LENGTH = 6;

	/**
	 * 正则表达式检查
	 * 
	 * @param reg
	 * @param string
	 * @return
	 */
	public static boolean check(String reg, String string) {

		boolean tem = false;

		Pattern pattern = Pattern.compile(reg);
		Matcher matcher = pattern.matcher(string);

		tem = matcher.matches();

		return tem;
	}

	/**
	 * 手机号码验证,11位，不知道详细的手机号码段，只是验证开头必须是1和位数
	 * 
	 * 国家号码段分配如下： 　　移动：134、135、136、137、138、139、150、151、157(TD)、158、159、187、188
	 * 　　联通：130、131、132、152、155、156、185、186 　　电信：133、153、180、189、（1349卫通）
	 */
	public static boolean checkMobile(String mobile) {
		// 以1开头且为11位数字
		String reg = "^[1]([3][0-9]{1}|[5][0-9]{1}|[8][5-9]{1}|80)[0-9]{8}$";
		return check(reg, mobile);
	}

	/**
	 * 检查EMAIL地址 用户名和网站名称必须>=1位字符
	 * 地址结尾必须是以com|cn|com|cn|net|org|gov|gov.cn|edu|edu.cn结尾
	 */
	public static boolean checkEmail(String email) {
		String regex = "\\w+\\@\\w+\\.(com|cn|com.cn|net|org|gov|gov.cn|edu|edu.cn)";
		return check(regex, email);
	}

	/**
	 * 检查邮政编码(中国),6位，第一位必须是非0开头，其他5位数字为0-9
	 */
	public static boolean checkPostcode(String postCode) {
		String regex = "^[1-9]\\d{5}";
		return check(regex, postCode);
	}

	/**
	 * 验证国内电话号码 格式：010-67676767，区号长度3-4位，必须以"0"开头，号码是7-8位
	 */
	public static boolean checkPhoneNr(String phoneNr) {
		String regex = "^[0]\\d{2,3}\\-\\d{7,8}";
		return check(regex, phoneNr);
	}

	/**
	 * 验证国内身份证号码：15或18位，由数字组成，不能以0开头
	 */
	public boolean checkIdCard(String idNr) {
		String reg = "^[1-9](\\d{14}|\\d{17})";
		return check(reg, idNr);
	}

	/**
	 * 网址验证<br>
	 * 符合类型：<br>
	 * http://www.test.com<br>
	 * http://163.com
	 */
	public static boolean checkWebSite(String url) {
		String reg = "^(http)\\://(\\w+\\.\\w+\\.\\w+|\\w+\\.\\w+)";
		return check(reg, url);
	}

	/**
	 * 由数字和字母组成,且长度要在6-20位之间。 不需要要同时含有数字和字母
	 * 
	 * @param pwd
	 * @return
	 */
	public static boolean checkPassword(String pwd) {
		return !(pwd.length() < 6 || pwd.length() > 20);
		// 由数字和字母组成,且长度要在8-16位之间
		// String reg = ".{6,18}";
		// return check(reg, pwd.trim());
	}
}
