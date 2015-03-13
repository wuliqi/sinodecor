/**
 * @(#)UserAction.java	05/15/2015
 * 
 * Copyright (c) 2015 app118.cn.All rights reserved.
 * Created by 2015-05-15
 */
package cn.app118.framework.util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

import javax.crypto.Cipher;
import javax.crypto.spec.IvParameterSpec;
import javax.crypto.spec.SecretKeySpec;

 
/**
 * AES对文件或字符串进行加密解密功能
 * @author 吴理琪
 *
 */
public class AESUtil {
    // 
	/**
	 * 根据密钥对明文加密
	 * @param sSrc 明文字符串
	 * @param sKey 密钥
	 * @return String 若返回null表示加密失败，否则返回加密后的字符串
	 */
    public static String encrypt(String sSrc, String sKey)  {
        try {
			if (sKey == null) {
			    System.out.print("Key为空null");
			    return null;
			}
			// 判断Key是否为16位
			if (sKey.length() != 16) {
			    System.out.print("Key长度不是16位");
			    return null;
			}
			byte[] raw = sKey.getBytes();
			SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
			Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
			IvParameterSpec iv = new IvParameterSpec("0102030405060708".getBytes());
			cipher.init(Cipher.ENCRYPT_MODE, skeySpec, iv);
			byte[] encrypted = cipher.doFinal(sSrc.getBytes());
			return byte2hex(encrypted).toLowerCase();
		} catch (Exception e) {
			System.out.println(e.toString());
			return null;
		} 
    }

    /**
     * 根据密钥对明文解密
     * @param sSrc 密文字符串
     * @param sKey 密钥
     * @return String 若返回null表示加密失败，否则返回加密后的字符串
     */
    public static String decrypt(String sSrc, String sKey) {
        try {
            // 判断Key是否正确
            if (sKey == null) {
                System.out.print("Key为空null");
                return null;
            }
            // 判断Key是否为16位
            if (sKey.length() != 16) {
                System.out.print("Key长度不是16位");
                return null;
            }
            byte[] raw = sKey.getBytes("ASCII");
            SecretKeySpec skeySpec = new SecretKeySpec(raw, "AES");
            Cipher cipher = Cipher.getInstance("AES/CBC/PKCS5Padding");
            IvParameterSpec iv = new IvParameterSpec("0102030405060708"
                    .getBytes());
            cipher.init(Cipher.DECRYPT_MODE, skeySpec, iv);
            byte[] encrypted1 = hex2byte(sSrc);
            try {
                byte[] original = cipher.doFinal(encrypted1);
                String originalString = new String(original);
                return originalString;
            } catch (Exception e) {
                System.out.println(e.toString());
                return null;
            }
        } catch (Exception ex) {
            System.out.println(ex.toString());
            return null;
        }
    }

    public static byte[] hex2byte(String strhex) {
        if (strhex == null) {
            return null;
        }
        int l = strhex.length();
        if (l % 2 == 1) {
            return null;
        }
        byte[] b = new byte[l / 2];
        for (int i = 0; i != l / 2; i++) {
            b[i] = (byte) Integer.parseInt(strhex.substring(i * 2, i * 2 + 2),
                    16);
        }
        return b;
    }

    public static String byte2hex(byte[] b) {
        String hs = "";
        String stmp = "";
        for (int n = 0; n < b.length; n++) {
            stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
            if (stmp.length() == 1) {
                hs = hs + "0" + stmp;
            } else {
                hs = hs + stmp;
            }
        }
        return hs.toUpperCase();
    }

    /**
     * 加密文件
     * @param filePath:待加密文件，如/sdcard/encryxml/xml2DB.xml
     * @param sKey:加密密钥,a_z,A_Z,0_9数字字母组成
     * @param destFilePath:文件加密后的路径，如/sdcard/decryptxml/
     */
    public static void encryptFile(String filePath, String sKey,String destFilePath)
    {
    	
    	BufferedReader bufferReader=null;
    	BufferedWriter writer=null;
    	try {
			bufferReader=new BufferedReader(new FileReader(filePath));
			File destFilePathFolder=new File(destFilePath);
			if(!destFilePathFolder.exists())destFilePathFolder.mkdirs();
			int slashLoc=filePath.lastIndexOf(File.separator);
			String fileName=filePath.substring(slashLoc+1,filePath.length());
			writer=new BufferedWriter(new FileWriter(destFilePath+File.separator+fileName));
			String data=null;
			while((data=bufferReader.readLine())!=null)
			{
				String encryStr=AESUtil.encrypt(data, sKey);
				writer.write(encryStr);
				writer.newLine();
				writer.flush();
			}
		} catch (Exception e) {
			System.out.println("加密文件异常："+e);
		}finally{
			try {
				writer.close();
				bufferReader.close();
			} catch (IOException e) {
				System.out.println("加密文件关闭流异常:"+e);
			}
		}
    }
   
    /**
     * 解密文件
     * @param filePath:待解密文件，如/sdcard/decryptxml/xml2DB.xml
     * @param sKey:加密密钥,a_z,A_Z,0_9数字字母组成
     * @param destFilePath:文件解密后的路径，如/sdcard/encryptxml/
     */
    public static void decryptFile(String filePath, String sKey,String destFilePath)
    {
    	BufferedReader bufferReader=null;
    	BufferedWriter writer=null;
    	try {
    		bufferReader=new BufferedReader(new FileReader(filePath));
    		File destFilePathFolder=new File(destFilePath);
    		if(!destFilePathFolder.exists())destFilePathFolder.mkdirs();
    		int slashLoc=filePath.lastIndexOf(File.separator);
    		String fileName=filePath.substring(slashLoc+1,filePath.length());
    		writer=new BufferedWriter(new FileWriter(destFilePath+File.separator+fileName));
    		String data=null;
    		while((data=bufferReader.readLine())!=null)
    		{
    			String decryStr=AESUtil.decrypt(data, sKey);
    			writer.write(decryStr);
    			writer.newLine();
    			writer.flush();
    		}
    	} catch (Exception e) {
    		System.out.println("解密文件异常："+e);
    	}finally{
    		try {
    			writer.close();
    			bufferReader.close();
    		} catch (IOException e) {
    			System.out.println("解密文件关闭流异常："+e);
    		}
    	}
    }
}