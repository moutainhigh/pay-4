package com.pay.app.controller.common;

import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.pay.util.CharsetTypeEnum;
import com.pay.util.security.ByteArrayUtil;
import com.pay.util.security.HashAlgorithms;
import com.pay.util.security.MD5Algorithms;
import com.pay.util.security.RSAAlgorithms;



/** 
* @ClassName: SecuritySubstanceUtil 
* @Description: 安全服务类 
* @author cf
* @date 2011-9-27 上午10:17:43 
*  
*/

public class SecuritySubstanceUtil {
	
	/**
	 *  将gateway-security.propertes 文件所在路径，设置在 classpath中
	 */
	static String initParam = "ma-security.propertes"; 
	
	static Map<String,String> paramsMap = new HashMap<String,String>();
	
	static final String gatewayKeyPath="ma.keyPath";
	static final String gatewayStorepass="ma.storepass";
	static final String gatewayAlias="ma.alias";
	static final String gatewayPwd="ma.pwd";
	
	static{
		Properties p = new Properties();
		try {
			p.load(SecuritySubstanceUtil.class.getClassLoader().getResourceAsStream(initParam));
			paramsMap.put(gatewayKeyPath, p.get("ma.keyPath")+"");
			paramsMap.put(gatewayStorepass, p.get("ma.storepass")+"");
			paramsMap.put(gatewayAlias, p.get("ma.alias")+"");
			paramsMap.put(gatewayPwd, p.get("ma.pwd")+"");
			
		} catch (IOException e) {
			e.printStackTrace();
		}
	}


	/**
	 * 生成签名MD5
	 * @param src
	 * @return
	 */
	public static String genSignByMD5(String arg, CharsetTypeEnum charsetType)
			throws Exception {
		if(arg==null || "".equals(arg.trim())){
			return null;
		}
		
		String mac = null;
		
		int hashCode = HashAlgorithms.PJWHash(arg);
		MD5Algorithms md5 = new MD5Algorithms();
		mac = md5.getMD5ofStr(hashCode+"");
		
		return mac;
	}

	/**
	 * 生成签名RSA
	 * @param src
	 * @return
	 */
	public static String genSignByRSA(String arg, CharsetTypeEnum charsetType)
			throws Exception {
			
		String mac = null;
			
		int hashCode = HashAlgorithms.PJWHash(arg);
		mac = hashCode+"";
		
		String signMsg = "";
		
		PrivateKey prikey = null;
		PublicKey pubKey = null;

		InputStream in = 
			SecuritySubstanceUtil.class.getClassLoader().getResourceAsStream(paramsMap.get(gatewayKeyPath));
		KeyStore ks = KeyStore.getInstance("JKS");
		ks.load(in, paramsMap.get(gatewayStorepass).toCharArray());
			
		java.security.cert.Certificate c = ks.getCertificate(paramsMap.get(gatewayAlias));
		pubKey = c.getPublicKey();
		
		// 取出 公钥 串
		System.err.println(ByteArrayUtil.toHexString(pubKey.getEncoded()));
	
		prikey = (PrivateKey) ks.getKey(paramsMap.get(gatewayAlias), paramsMap.get(gatewayPwd).toCharArray());
		
		RSAAlgorithms rsa = new RSAAlgorithms();
		
		signMsg = rsa.genSignature(mac.getBytes(charsetType.getDescription()), prikey);

		mac = signMsg;
			
		return mac;		
	}

	/**
	 * 校验签名MD5
	 * @param src 原数据
	 * @param arg 加签后数据
	 * @return result
	 */
	public static boolean verifySignatureByMD5(String src, String arg,
			CharsetTypeEnum charsetType) throws Exception {
		
		if(src==null || "".equals(src.trim())){
			return false;
		}
		
		if(arg==null || "".equals(arg.trim())){
			return false;
		}
		
		String mac = null;
		int hashCode = HashAlgorithms.PJWHash(src);
		MD5Algorithms md5 = new MD5Algorithms();
		mac = md5.getMD5ofStr(hashCode+"");
	
		if(arg.equals(mac)){
			return true;
		}
		
		return false;
	}

	/**
	 * 校验签名RSA
	 * @param src 原数据
	 * @param arg 加签后数据
	 * @param publicKey Base64后的公钥
	 * @return result
	 */
	public static boolean verifySignatureByRSA(String src, String arg,
			CharsetTypeEnum charsetType, String publicKey) throws Exception {
		
		boolean result = false;
		
		int hashCode = HashAlgorithms.PJWHash(src);
		String hashSrc = hashCode+"";
		
		RSAAlgorithms sign = new RSAAlgorithms();
		
		try {
			result = 
				sign.verifySignature(hashSrc.getBytes(charsetType.getDescription()), arg, ByteArrayUtil.toByteArray(publicKey));
		
		} catch (Exception e) {
			e.printStackTrace();
			result = false;
		}
			
		return result;
	}

}
