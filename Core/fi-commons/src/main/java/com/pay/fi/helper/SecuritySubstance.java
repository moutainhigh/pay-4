package com.pay.fi.helper;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.security.KeyStore;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.springframework.util.Assert;

import com.pay.util.CharsetTypeEnum;
import com.pay.util.security.ByteArrayUtil;
import com.pay.util.security.HashAlgorithms;
import com.pay.util.security.MD5BaseAlgorithms;
import com.pay.util.security.RSAAlgorithms;
import com.pay.util.security.SHABaseAlgorithms;


/**
 * @Description 安全服务类
 * @project gateway-pay
 * @file SecuritySubstance.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 *          	Author Changes 2011-4-13 Sean.Chen Create
 */

public class SecuritySubstance {
	
	/**
	 *  将gateway-security.propertes 文件所在路径，设置在 classpath中
	 */
	static String initParam = "gateway-security.propertes"; 
	
	static Map<String,String> paramsMap = new HashMap<String,String>();
	
	static final String gatewayKeyPath="gateway.keyPath";
	static final String gatewayStorepass="gateway.storepass";
	static final String gatewayAlias="gateway.alias";
	static final String gatewayPwd="gateway.pwd";
	
	static boolean flag = true;
	
	static String pkeyHeader = "&pkey=";
	
	static String securityPropertiesPath = "fi-security.properties";
	
	static String rootPath="";
	
	static{
		
		Properties p = new Properties();
		
		try {
			p.load(SecuritySubstance.class.getClassLoader().getResourceAsStream(securityPropertiesPath));
			rootPath=(String)p.get("rootPath");
			initParam = rootPath + initParam;
			
		} catch (IOException e) {
			System.err.println("读取fi-security.properties异常");
			e.printStackTrace();
			flag = false;
		}
		
		try {
			p.load(new FileInputStream(initParam));
			paramsMap.put(gatewayKeyPath, p.get("gateway.keyPath")+"");
			paramsMap.put(gatewayStorepass, p.get("gateway.storepass")+"");
			paramsMap.put(gatewayAlias, p.get("gateway.alias")+"");
			paramsMap.put(gatewayPwd, p.get("gateway.pwd")+"");
			
		} catch (IOException e) {
			System.err.println("读取gateway-security.propertes异常");
			e.printStackTrace();
			flag = false;
		}
	}


	/**
	 * 生成签名MD5
	 * @param src
	 * @return
	 */
	public static String genSignByMD5(String src, CharsetTypeEnum charsetType,String parnterPublicKey)
			throws Exception {
		
		if(!flag){
			System.err.println("初始化配置未成功,genSignByMD5无法执行");
			throw new Exception("初始化配置未成功,genSignByMD5无法执行");
		}
		
		if(src==null || "".equals(src.trim())){
			System.err.println("src is empty,genSignByMD5无法执行");
			throw new Exception("src is empty,genSignByMD5无法执行");
		}
		
		if(parnterPublicKey==null || "".equals(parnterPublicKey.trim())){
			System.err.println("parnterPublicKey is empty,genSignByMD5无法执行");
			throw new Exception("parnterPublicKey is empty,genSignByMD5无法执行");
		}
		
		src += pkeyHeader+parnterPublicKey;
		
		String mac = null;
		
		
		mac = MD5BaseAlgorithms.getMD5Str(src);
		
		return mac;
	}
	
	
	public static void main(String[] args) {
		
		String tt="charset=1&completeTime=20160226104356&currencyCode=USD&dealId=1021602261043091148&language=en&orderAmount=1&orderId=8888888820160226104341&partnerId=10000003776&payAmount=1&remark=en.kvmart.net&resultCode=0000&resultMsg=The transaction has complete:交易成功&settlementCurrencyCode=CNY&signType=2&pkey=30819f300d06092a864886f70d010101050003818d003081890281810092ca19024e81349058236c86346f48e51e82250d13c3564e8464383410a15675f9b283bfe95dbe817e6852746eca89b6b64b53428b9ced8ae914a0bf1745cdf60ce1f0a3cd1c7389d0894a36ef1e49109707e619d72c3d67aec60218b596b3489f0da131f1ea9886841fdb12262b18a4ae2ffbcc13d31e3b10a856b3161a03f70203010001";
	    String tl="charset=1&completeTime=20160226145028&currencyCode=CNY&dealId=1021602261450091157&language=cn&orderAmount=320&orderId=160226000015&partnerId=10000003772&payAmount=320&resultCode=0000&resultMsg=The transaction has complete:交易成功&settlementCurrencyCode=CNY&signType=2&pkey=30819f300d06092a864886f70d010101050003818d0030818902818100c6378145dc88558f2f07286d551425d5b7a73937d1cfa3ab5b3a70289422508e4d056e03855ef4b615d7bd8f4657a52ba914aeedd18bb6b8f858a296695ee3d3816654bc253a42595ff4c999152094376b58ed0e1de22d63c9b5774896be3f0b1a2ceaeeb4ad407288fadb1e616dd488795afb5c5e3affc64af1fa7736fe445d0203010001";
		String t2="charset=1&completeTime=20160226145028&currencyCode=CNY&dealId=1021602261450091157&language=cn&merchantBillName=www.ipaylinks.com&orderAmount=320&orderId=160226000015&partnerId=10000003772&payAmount=320&resultCode=0000&resultMsg=The transaction has complete:交易成功&settlementCurrencyCode=CNY&signType=2&pkey=30819f300d06092a864886f70d010101050003818d0030818902818100c6378145dc88558f2f07286d551425d5b7a73937d1cfa3ab5b3a70289422508e4d056e03855ef4b615d7bd8f4657a52ba914aeedd18bb6b8f858a296695ee3d3816654bc253a42595ff4c999152094376b58ed0e1de22d63c9b5774896be3f0b1a2ceaeeb4ad407288fadb1e616dd488795afb5c5e3affc64af1fa7736fe445d0203010001";
	    
	    
		System.out.println(MD5BaseAlgorithms.getMD5Str(tl));
		System.out.println(MD5BaseAlgorithms.getMD5Str(t2));
	}
	
	/**
	 * 生成签名
	 * @param src
	 * @return
	 */
	public static String genSignBySHA512(String src, CharsetTypeEnum charsetType,String parnterPublicKey)
			throws Exception {
		
		if(!flag){
			System.err.println("初始化配置未成功,genSignBySHA512无法执行");
			throw new Exception("初始化配置未成功,genSignBySHA512无法执行");
		}
		
		if(src==null || "".equals(src.trim())){
			System.err.println("src is empty,genSignBySHA512无法执行");
			throw new Exception("src is empty,genSignBySHA512无法执行");
		}
		
		if(parnterPublicKey==null || "".equals(parnterPublicKey.trim())){
			System.err.println("parnterPublicKey is empty,genSignBySHA512无法执行");
			throw new Exception("parnterPublicKey is empty,genSignBySHA512无法执行");
		}
		
		src += pkeyHeader+parnterPublicKey;
		
		String mac = null;

		mac = SHABaseAlgorithms.getSHA512Str(src);
		return mac;
	}

	/**
	 * 生成签名RSA
	 * @param src
	 * @return
	 */
	public static String genSignByRSA(String src, CharsetTypeEnum charsetType)
			throws Exception {
		
		if(!flag){
			System.err.println("初始化配置未成功,genSignByRSA无法执行");
			throw new Exception("初始化配置未成功,genSignByRSA无法执行");
		}	
		
		if(src==null || "".equals(src.trim())){
			System.err.println("src is empty,genSignByRSA无法执行");
			throw new Exception("src is empty,genSignByRSA无法执行");
		}
		
		String mac = null;
			
		int hashCode = HashAlgorithms.PJWHash(src);
		mac = hashCode+"";
		
		String signMsg = "";
		
		PrivateKey prikey = null;
		PublicKey pubKey = null;

		InputStream in = new FileInputStream(rootPath + paramsMap.get(gatewayKeyPath));
		
		KeyStore ks = KeyStore.getInstance("JKS");
		ks.load(in, paramsMap.get(gatewayStorepass).toCharArray());
			
		java.security.cert.Certificate c = ks.getCertificate(paramsMap.get(gatewayAlias));
		pubKey = c.getPublicKey();
		
		// 取出 公钥 串
		// System.err.println(ByteArrayUtil.toHexString(pubKey.getEncoded()));
	
		// 取出网关私钥
		prikey = (PrivateKey) ks.getKey(paramsMap.get(gatewayAlias), paramsMap.get(gatewayPwd).toCharArray());
		
		RSAAlgorithms rsa = new RSAAlgorithms();
		
		signMsg = rsa.genSignature(mac.getBytes(charsetType.getDescription()), prikey);

		mac = signMsg;
			
		return mac;		
	}

	/**
	 * 校验签名MD5
	 * @param src 原数据
	 * @param dit 加签后数据
	 * @return result
	 */
	public static boolean verifySignatureByMD5(String src, String dit,
			CharsetTypeEnum charsetType,String parnterPublicKey) throws Exception {
		
		if(!flag){
			System.err.println("初始化配置未成功,verifySignatureByMD5无法执行");
			throw new Exception("初始化配置未成功,verifySignatureByMD5无法执行");
		}	
		
		if(src==null || "".equals(src.trim())){
			System.err.println("初始化配置未成功,verifySignatureByMD5无法执行");
			throw new Exception("src is empty ,verifySignatureByMD5无法执行");
		}
		
		if(dit==null || "".equals(dit.trim())){
			System.err.println("初始化配置未成功,verifySignatureByMD5无法执行");
			throw new Exception("arg is empty ,verifySignatureByMD5无法执行");
		}
		
		if(parnterPublicKey==null || "".equals(parnterPublicKey.trim())){
			System.err.println("初始化配置未成功,verifySignatureByMD5无法执行");
			throw new Exception("parnterPublicKey is empty ,verifySignatureByMD5无法执行");
		}
		
		src += pkeyHeader+parnterPublicKey;
		
		String mac = null;
		
		try{
			mac = MD5BaseAlgorithms.getMD5Str(src);
		}catch(Exception e){
			System.err.println("MD5 验签出现异常");
			e.printStackTrace();
			return false;
		}
		
		if(dit.equals(mac)){
			return true;
		}
		
		return false;
	}

	/**
	 * 校验签名RSA
	 * @param src 原数据
	 * @param dit 加签后数据
	 * @param publicKey Base64后的公钥
	 * @return result
	 */
	public static boolean verifySignatureByRSA(String src, String dit,
			CharsetTypeEnum charsetType, String publicKey) throws Exception {
		
		if(!flag){
			System.err.println("初始化配置未成功,verifySignatureByRSA无法执行");
			throw new Exception("初始化配置未成功,verifySignatureByRSA无法执行");
		}
		if(src==null || "".equals(src.trim())){
			System.err.println("初始化配置未成功,verifySignatureByMD5无法执行");
			throw new Exception("src is empty ,verifySignatureByMD5无法执行");
		}
		if(dit==null || "".equals(dit.trim())){
			System.err.println("初始化配置未成功,verifySignatureByMD5无法执行");
			throw new Exception("dit is empty ,verifySignatureByMD5无法执行");
		}
		
		boolean result = false;
		
		int hashCode = HashAlgorithms.PJWHash(src);
		String hashSrc = hashCode+"";
		
		RSAAlgorithms sign = new RSAAlgorithms();
		
		try {
			result = 
				sign.verifySignature(hashSrc.getBytes(), dit, ByteArrayUtil.toByteArray(publicKey));
		
		} catch (Exception e) {
			System.err.println("RSA 验签出现异常");
			e.printStackTrace();
			result = false;
		}
			
		return result;
	}
	
	public static String decryptData(String encryptedData,String publicKey) throws Exception{
		if(!flag){
			System.err.println("初始化配置未成功,verifySignatureByRSA无法执行");
			throw new Exception("初始化配置未成功,verifySignatureByRSA无法执行");
		}
		Assert.notNull(encryptedData,"encryptedData is required");
		Assert.notNull(publicKey,"publicKey is required");
		
		String srcData = null;
		RSAAlgorithms sign = new RSAAlgorithms();
		try {
			srcData = new String(sign.getDecryptArray(ByteArrayUtil.toByteArray(encryptedData), ByteArrayUtil.toByteArray(publicKey)));
		
		} catch (Exception e) {
			System.err.println("RSA 解密出现异常");
			e.printStackTrace();
		}
		
		return srcData;
		
		
	}

}
