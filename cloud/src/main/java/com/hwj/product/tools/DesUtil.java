package com.hwj.product.tools;
import java.security.Key;
import java.security.Security;
import javax.crypto.Cipher;


public class DesUtil {
	public static final String ALGORITHM = "DES";

	private static Key getKey(String strKey) throws Exception {
		Security.addProvider(new com.sun.crypto.provider.SunJCE());
		return getKey(strKey.getBytes("UTF-8"));
	}

	/**
	 * 从指定字符串生成密钥，密钥所需的字节数组长度为8位 不足8位时后面补0，超出8位只取前8位
	 *
	 * @param arrBTmp 构成该字符串的字节数组
	 * @return 生成的密钥
	 * @throws Exception
	 */
	private static Key getKey(byte[] arrBTmp) throws Exception {
		// 创建一个空的8位字节数组（默认值为0）
		byte[] arrB = new byte[8];
		// 将原始字节数组转换为8位
		for (int i = 0; i < arrBTmp.length && i < arrB.length; i++) {
			arrB[i] = arrBTmp[i];
		}
		// 生成密钥
		Key key;
		key = new javax.crypto.spec.SecretKeySpec(arrB, "DES");
		return key;
	}

	/**
	 * 根据密匙进行DES加密
	 * 
	 * @param key  密匙   例如：86R8k53kfb52768
	 * @param info 要加密的信息
	 * @return String 加密后的信息
	 */
	public static String encrypt(String keyStr, String srcString) throws Exception {
		if (srcString == null || "".equals(srcString)) {
			return "";
		}
		try {
			// 定义要生成的密文
			byte[] cipherByte = null;

			// 生成密钥
			Key key = getKey(keyStr);

			// 得到加密/解密器
			Cipher c1 = Cipher.getInstance(ALGORITHM);
			// 用指定的密钥和模式初始化Cipher对象
			// 参数:(ENCRYPT_MODE, DECRYPT_MODE, WRAP_MODE,UNWRAP_MODE)
			c1.init(Cipher.ENCRYPT_MODE, key);
			// 对要加密的内容进行编码处理,
			cipherByte = c1.doFinal(srcString.getBytes("UTF-8"));
			// 返回密文的十六进制形式
			return byte2hex(cipherByte);
		} catch (Exception e) {
			throw new Exception();
		}
	}

	/**
	 * 根据密匙进行DES解密
	 * 
	 * @param key   密匙
	 * @param sInfo 要解密的密文
	 * @return String 返回解密后信息
	 */
	public static String decrypt(String keyStr, String encryptedString) throws Exception {
		if (encryptedString == null || "".equals(encryptedString)) {
			return "";
		}
		try {
			byte[] cipherByte = null;
			// 生成密钥
			Key key = getKey(keyStr);
			// 得到加密/解密器
			Cipher c1 = Cipher.getInstance(ALGORITHM);
			// 用指定的密钥和模式初始化Cipher对象
			c1.init(Cipher.DECRYPT_MODE, key);
			// 对要解密的内容进行编码处理
			cipherByte = c1.doFinal(hex2byte(encryptedString));
			String str = new String(cipherByte, "UTF-8");
			return str;
		} catch (Exception e) {
			throw new Exception();
		}
	}

	/**
	 * 将二进制转化为16进制字符串
	 * 
	 * @param b 二进制字节数组
	 * @return String
	 */
	private static String byte2hex(byte[] b) {

		if (null == b) {
			return null;
		}

		StringBuffer hs = new StringBuffer("");
		String stmp = "";
		for (int n = 0; n < b.length; n++) {
			stmp = (java.lang.Integer.toHexString(b[n] & 0XFF));
			if (stmp.length() == 1) {
				hs.append("0").append(stmp);
			} else {
				hs.append(stmp);
			}
		}
		return hs.toString().toUpperCase();
	}

	/**
	 * 十六进制字符串转化为2进制
	 * 
	 * @param hex
	 * @return
	 */
	private static byte[] hex2byte(String hex) throws Exception {
		if (null == hex || hex.length() % 2 != 0) {
			throw new Exception();
		}
		char[] arr = hex.toCharArray();
		byte[] b = new byte[hex.length() / 2];
		for (int i = 0, j = 0, l = hex.length(); i < l; i++, j++) {
			String swap = "" + arr[i++] + arr[i];
			int byteint = Integer.parseInt(swap, 16) & 0xFF;
			b[j] = new Integer(byteint).byteValue();
		}
		return b;
	}
}
