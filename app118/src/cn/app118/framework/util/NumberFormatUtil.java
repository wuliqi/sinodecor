/**
 * @(#)UserAction.java	10/13/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-10-13
 */
package cn.app118.framework.util;

import java.math.BigDecimal;
import java.text.DecimalFormat;

/**
 * 数字格式化工具类
 * 
 * @author wRitchie
 * 
 */
public class NumberFormatUtil {

	public static void main(String[] args) {
		float ft = 0;

		int scale = 2;// 设置位数
		int roundingMode = 4;// 表示四舍五入，可以选择其他舍值方式，例如去尾，等等.
		BigDecimal bd = new BigDecimal((double) ft);
		bd = bd.setScale(scale, roundingMode);
		ft = bd.floatValue();
		System.out.println("111:"+ft);
		///////////////////////////////
		float scale2 = 0f;
		DecimalFormat fnum = new DecimalFormat("##0.00");
		String dd = fnum.format(scale2);
		System.out.println("2222:"+dd);

		// ///////

		float a = 0f;
		float b = (float) (Math.round(a * 100)) / 100;// (这里的100就是2位小数点,如果要其它位,如4位,这里两个100改成10000)
		System.out.println(b);
		
		System.out.println("***:"+NumberFormatUtil.formatFloat(0.0f));
	}
	
	public static String formatFloat(float srcFloat){
		DecimalFormat fnum = new DecimalFormat("##0.00");
		String result = fnum.format(srcFloat);
		return result;
	}

}
