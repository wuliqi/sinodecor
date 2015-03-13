/**
 * @(#)UserAction.java	06/28/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-06-28
 */
package cn.app118.framework.util;

import java.awt.image.BufferedImage;
import java.io.File;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Map;

import javax.imageio.ImageIO;

import org.apache.log4j.Logger;

import com.google.zxing.BarcodeFormat;
import com.google.zxing.Binarizer;
import com.google.zxing.BinaryBitmap;
import com.google.zxing.EncodeHintType;
import com.google.zxing.LuminanceSource;
import com.google.zxing.MultiFormatReader;
import com.google.zxing.MultiFormatWriter;
import com.google.zxing.Result;
import com.google.zxing.common.BitMatrix;
import com.google.zxing.common.HybridBinarizer;
/**
 * 二维码生成及读取工具类
 * 
 * 
 * @author wRitchie
 *
 */
public class QRCodeUtil {
	static Logger log = Logger.getLogger(QRCodeUtil.class);// 日志

	/**
	 * 生成二维码
	 * 
	 * @param path
	 *            保存二维码文件文件的路径
	 * @param fileName
	 *            保存二维码文件的文件名称
	 * @param content
	 *            二维码内容
	 */
	public static void writeQRCode(String path, String fileName, String content) {
		try {
			// String content = "120605181003;http://www.cnblogs.com/jtmjx";
			// String path = "E:/";
			MultiFormatWriter multiFormatWriter = new MultiFormatWriter();

			Hashtable<EncodeHintType,String> hints = new Hashtable<EncodeHintType,String>();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			BitMatrix bitMatrix = multiFormatWriter.encode(content,
					BarcodeFormat.QR_CODE, 400, 400, hints);

			File file1 = new File(path, fileName);
			MatrixToImageWriter.writeToFile(bitMatrix, "jpg", file1);

		} catch (Exception e) {
			log.info("生成二维码异常：" + e);
		}
	}

	/**
	 * 二维码读取
	 * 
	 * @param filePath 二维码文件所在路径
	 * @return
	 */
	public static String readQRCode(String filePath) {
		String text = "";
		try {
			MultiFormatReader formatReader = new MultiFormatReader();
			// String filePath =
			// "C:/Users/Administrator/Desktop/testImage/test.jpg";
			filePath="C:\\Users\\Administrator\\Desktop\\airbest订阅号.jpg";
			File file = new File(filePath);
			BufferedImage image = ImageIO.read(file);
			;
			LuminanceSource source = new BufferedImageLuminanceSource(image);
			Binarizer binarizer = new HybridBinarizer(source);
			BinaryBitmap binaryBitmap = new BinaryBitmap(binarizer);
			Hashtable<EncodeHintType,String> hints = new Hashtable<EncodeHintType,String>();
			hints.put(EncodeHintType.CHARACTER_SET, "UTF-8");
			Result result = formatReader.decode(binaryBitmap, hints);

//			System.out.println("result = " + result.toString());
//			System.out.println("resultFormat = " + result.getBarcodeFormat());
//			System.out.println("resultText = " + result.getText());
			text = result.getText();

		} catch (Exception e) {
			log.info("读取二维码异常："+e);
		}
		return text;
	}

	public static void main(String[] args) {
//		String json = "{\"deviceSn\":\"CD39AEFFA3\",\"deviceMac\":\"20:CD:39:AE:FF:A3\",\"devicePassword\":\"1\"}";
//		QRCodeUtil.writeQRCode("E:/", "CD39AEFFA3.jpg", json);
//		
//		String text=QRCodeUtil.readQRCode("e:/CD39AEFFA3.jpg");
//		System.out.println("二维码信息："+text);
		String json="http://app.app118.cn/app118/softwareUpdAction/downloadFile?fileName=AirBestTaxi.apk";
		QRCodeUtil.writeQRCode("E:/", "空气卫士出租车版.jpg", json);
	}
}
