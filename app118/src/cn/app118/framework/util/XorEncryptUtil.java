package cn.app118.framework.util;

public class XorEncryptUtil {

	
	/** 异或加密算法
	 * 
	 *  使用方式 
	 *  加密: encrypt(">Get-Status!","C9");
	 *  解密：encrypt(encrypt(">Get-Status!","C9"),"C9");
	 * 
	 *  @author 吴理琪
	 *  @param initStr 要加密的字符串
	 *  @param password 加密密码
	 *  @return string 
	 */
	public static String encrypt(String initStr, String password) {
		byte[] data = initStr.getBytes();
		byte[] keyData = password.getBytes();
		int keyIndex = 0;
		for (int x = 0; x < initStr.length(); x++) {
			data[x] = (byte) (data[x] ^ keyData[keyIndex]);
			if (++keyIndex == keyData.length) {
				keyIndex = 0;
			}
		}
		return new String(data);
	}
	
	public static void main(String[] args) {
		String enStr=encrypt(">Get-Status!", "27");
		System.out.println("加密："+enStr);
		System.out.println("解密："+encrypt(enStr, "27"));
	}
}
