package com.coopcoder.getcoder;
import java.io.UnsupportedEncodingException;
import java.math.BigInteger;
import java.nio.charset.StandardCharsets;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.util.Date;

import org.apache.commons.codec.binary.Base64;

public class Cipher {
	

	// дһ��md5���ܵķ���
	public static String md5(String plainText) {
		// ����һ���ֽ�����
		byte[] secretBytes = null;
		try {
			// ����һ��MD5���ܼ���ժҪ
			MessageDigest md = MessageDigest.getInstance("MD5");
			// ���ַ������м���
			md.update(plainText.getBytes());
			// ��ü��ܺ������
			secretBytes = md.digest();
		} catch (NoSuchAlgorithmException e) {
			throw new RuntimeException("û��md5����㷨��");
		}
		// �����ܺ������ת��Ϊ16��������
		String md5code = new BigInteger(1, secretBytes).toString(16);// 16��������
		// �����������δ��32λ����Ҫǰ�油0
		for (int i = 0; i < 32 - md5code.length(); i++) {
			md5code = "0" + md5code;
		}
		return md5code;
	}

	public static String base64_encode(String plainText) {
		Base64 base64 = new Base64();
		String encodedString = "";
		byte[] encodedBytes;
		try {
			encodedBytes = base64.encode(plainText.getBytes("UTF-8"));
			encodedString = new String(encodedBytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return encodedString;
	}

	public static String base64_decode(String encodedText) {
		Base64 base64 = new Base64();
		byte[] bytesToDecode;
		String decodedString = "";
		try {
			bytesToDecode = encodedText.getBytes("UTF-8");
			byte[] decodedBytes = base64.decode(bytesToDecode);
			decodedString = new String(decodedBytes, "UTF-8");
		} catch (UnsupportedEncodingException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		return decodedString;
	}

	public static Long microtime() {
		Date date = new Date();
		return date.getTime();
	}

	public static int time() {
		Date date = new Date();
		return Integer.parseInt(Long.toString(date.getTime()).substring(0, 10));
	}

	// & 0xff ���utf-8����
	public static int ord(String s) {
		return s.length() > 0 ? (s.getBytes(StandardCharsets.UTF_8)[0] & 0xff) : 0;
	}

	public static int ord(char c) {
		return c < 0x80 ? c : ord(Character.toString(c));
	}

	public static String chr(int i) {
		return Character.toString((char) i);
	}

	public static String substr(String str, int beginIndex, int length) {
		return str.substring(beginIndex, beginIndex + length);
	}

	public static String substr(String str, int beginIndex) {
		return str.substring(beginIndex);
	}

	public static String sprintf(String format, int value) {

		return String.format(format, value);
	}

	public static String str_replace(String rep, String str, String oristr) {
		return oristr.replace(rep, str);
	}

	public static String authcode(String codeText, String operation, String key) {
		if (operation == "DECODE") {
			return hexStr2Str(codeText);
		} else {
			return str2HexStr(codeText);
		}
		
//		// ��̬�ܳ׳��ȣ���ͬ�����Ļ����ɲ�ͬ���ľ���������̬�ܳ�
//		int ckey_length = 4;
//		// �ܳ�
//		String keyMD5 = md5(key);
//		// �ܳ�a�����ӽ���
//		String keya = md5(substr(keyMD5, 0, 16));
//		// �ܳ�b��������������������֤
//		String keyb = md5(substr(keyMD5, 16, 16));
//
//		// �ܳ�c���ڱ仯���ɵ�����
//		String keyc = "";
//
//		if (operation == "DECODE") {
//			keyc = substr(codeText, 0, ckey_length);
//			
//			codeText = base64_decode(substr(codeText, ckey_length));
//
//		} else {
//			keyc = substr(md5(Long.toString(time())), 28);
//			
//			keyc = substr(md5("aa"), 28);
//			
//			expiry = expiry > 0 ? expiry + time() : 0;
//
//			codeText = sprintf("%010d", expiry) + substr(md5(codeText + keyb), 0, 16) + codeText;
//		}
//		
//		
//
//		// ����������ܳ�
//		String cryptkey = keya + md5(keya + keyc);
//		
//		System.out.println("cryptkey---" + cryptkey);
//
//		int key_length = cryptkey.length();
//
//		// ���ģ�ǰ10λ��������ʱ���������ʱ��֤������Ч�ԣ�10��26λ��������$keyb(�ܳ�b)��
//		// ����ʱ��ͨ������ܳ���֤����������
//		// ����ǽ���Ļ�����ӵ�$ckey_lengthλ��ʼ����Ϊ����ǰ$ckey_lengthλ���� ��̬�ܳף��Ա�֤������ȷ
//
//		int string_length = codeText.length();
//		String result = "";
//
////	        $box = range(0, 255);
//
//		int[] box = new int[256];
//
//		int[] rndkey = new int[256];
//
//		char[] cryptkeyArr = cryptkey.toCharArray();
//
//		// �����ܳײ�
//		for (int i = 0; i <= 255; i++) {
//			box[i] = i;
//			rndkey[i] = ord(cryptkeyArr[i % key_length]);
//			System.out.println("ri"+rndkey[i]);
//		}
//
//		int j = 0;
//		// �ù̶����㷨�������ܳײ�����������ԣ�����ܸ��ӣ�ʵ���϶Բ������������ĵ�ǿ��
//		for (int i = 0; i < 256; i++) {
//			j = (j + box[i] + rndkey[i]) % 256;
//			int tmp = box[i];
//			box[i] = box[j];
//			box[j] = tmp;
//		}
//
//		char[] codeTextArr = codeText.toCharArray();
//		
//		
//		int a = 0;
//		j = 0;
//		// ���ļӽ��ܲ���
//		for (int i = 0; i < string_length; i++) {
//			a = (a + 1) % 256;
//			j = (j + box[a]) % 256;
//			int tmp = box[a];
//			box[a] = box[j];
//			box[j] = tmp;
////			System.out.println("a---" + a);
////			System.out.println("j---" + j);
//			// ���ܳײ��ó��ܳ׽��������ת���ַ�
//			int ordchar = ord(codeTextArr[i]) ^ (box[(box[a] + box[j]) % 256]);
//			result += str2HexStr(String.valueOf(ordchar));
//			
//			
//			System.out.println("ordchar" + ordchar);
////			System.out.println("bj---" + box[j]);
//		}
//		System.out.println("codeText" + codeText);
//		
//		System.out.println("resl---" + result.length());
//		System.out.println("keycl---" + keyc.length());
//		System.out.println("keyc---" + keyc);
//		System.out.println("res---" + result);
//		
//		if (operation == "DECODE") {
//			return substr(result, 26);
//			// ��֤������Ч�ԣ��뿴δ�������ĵĸ�ʽ
////	            if((substr(result, 0, 10) == 0 || substr(result, 0, 10) - time() > 0) &&  
////	    substr(result, 10, 16) == substr(md5(substr(result, 26) + keyb), 0, 16)) {   
////	                return substr(result, 26);   
////	            } else {   
////	                return "";   
////	            }   
//		} else {
//			// �Ѷ�̬�ܳױ������������Ҳ��Ϊʲôͬ�������ģ�������ͬ���ĺ��ܽ��ܵ�ԭ��
//			// ��Ϊ���ܺ�����Ŀ�����һЩ�����ַ������ƹ��̿��ܻᶪʧ��������base64����
//			result = str2HexStr(result);
//		
////			System.out.println("hex result---" + result);
//			return keyc + str_replace("=", "", base64_encode(result));
//			
//		}
	}
	
	/**
	 * �ַ���ת����Ϊ16����(����Unicode����)
	 * @param str
	 * @return
	 */
	public static String str2HexStr(String str) {
	    char[] chars = "0123456789ABCDEF".toCharArray();
	    StringBuilder sb = new StringBuilder("");
	    byte[] bs = str.getBytes();
	    int bit;
	    for (int i = 0; i < bs.length; i++) {
	        bit = (bs[i] & 0x0f0) >> 4;
	        sb.append(chars[bit]);
	        bit = bs[i] & 0x0f;
	        sb.append(chars[bit]);
	        // sb.append(' ');
	    }
	    return sb.toString().trim();
	}
	
	/**
	 * 16����ֱ��ת����Ϊ�ַ���(����Unicode����)
	 * @param hexStr
	 * @return
	 */
	public static String hexStr2Str(String hexStr) {
	    String str = "0123456789ABCDEF";
	    char[] hexs = hexStr.toCharArray();
	    byte[] bytes = new byte[hexStr.length() / 2];
	    int n;
	    for (int i = 0; i < bytes.length; i++) {
	        n = str.indexOf(hexs[2 * i]) * 16;
	        n += str.indexOf(hexs[2 * i + 1]);
	        bytes[i] = (byte) (n & 0xff);
	    }
	    return new String(bytes);
	}

}
