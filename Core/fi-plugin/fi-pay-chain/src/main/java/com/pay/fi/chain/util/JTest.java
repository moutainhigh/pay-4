///**
// * 
// */
//package com.pay.fi.chain.util;
//
//import java.sql.Timestamp;
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.Date;
//
//import org.junit.After;
//import org.junit.Before;
//import org.junit.Test;
//
//import com.pay.util.CurrencyCodeEnum;
//import com.pay.util.DESUtil;
//import com.pay.util.MD5Util;
//
///**
// * @author PengJiangbo
// *
// */
//public class JTest {
//
//	/**
//	 * @throws java.lang.Exception
//	 */
//	
//	private String payChainKey = "873057a9-6311-4de3-a5b9-2552d4ddc844-80d74e11-fb71-4534-89d4-93bd9efc6751" ;
//	@Before
//	public void setUp() throws Exception {
//	}
//
//	/**
//	 * @throws java.lang.Exception
//	 */
//	@After
//	public void tearDown() throws Exception {
//	}
//
//	@Test
//	public void test() {
//		for(CurrencyCodeEnum item : CurrencyCodeEnum.values()){
//			System.out.println("code:" + item.getCode() + "-->desc:" + item.getDesc());
//		}
//	}
//	@Test
//	public void testDesUtil(){
//		SimpleDateFormat sdf = new SimpleDateFormat("yyMMdd");
//		String chainNum = sdf.format(new Date()) + "1000606" ;
//		String encryptStr = encryptChainNum(chainNum) ;
//		System.out.println("原字符：" + chainNum + "-->密文：" + encryptStr);
//		String decryptStr = decryptChainNum(encryptStr) ;
//		System.out.println("密文：" + encryptStr + "-->原文：" + decryptStr);
//	}
//	//加密
//	public String encryptChainNum(String chainNum){
//    	String e1=DESUtil.encrypt(chainNum,payChainKey);
//    	return DESUtil.encrypt(e1,payChainKey);
//    }
//	//解密
//	public String decryptChainNum(String encryChainNum){
//    	String d1=DESUtil.decrypt(encryChainNum,payChainKey);
//    	return DESUtil.decrypt(d1,payChainKey);
//    }
//	@Test
//	public void testMD5(){
//		String source = "781dcfca-2940-4db1-8879-4f1a75d31a12" ;  // --d8dd4a72dd3f3582d101cf6608691fe8
//		//t = MD5Util.
//		byte[] md5 = MD5Util.md5(source) ;
//		
//		String md5Hex = MD5Util.md5Hex(source) ;
//		System.out.println(md5Hex);
//	}
//	@Test
//	public void testTimestamp(){
//		
//		Timestamp t = new Timestamp(new Date().getTime()) ;
//		System.out.println("t:" + t + "  , t.getTime:" + t.getTime() + "  , now.getTime:" + new Date().getTime());
//		
//	}
//}
