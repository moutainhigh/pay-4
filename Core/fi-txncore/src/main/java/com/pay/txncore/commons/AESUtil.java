package com.pay.txncore.commons;

import java.util.Arrays;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.spec.IvParameterSpec;

import sun.misc.BASE64Encoder;


public final class AESUtil {
	static Cipher cipher;  
    static final String KEY_ALGORITHM = "AES";  
    static final String CIPHER_ALGORITHM_ECB = "AES/ECB/PKCS5Padding";  
    /* 
     *  
     */  
    static final String CIPHER_ALGORITHM_CBC = "AES/CBC/PKCS5Padding";  
    /*  
     * AES/CBC/NoPadding 要求 
     * 密钥必须是16位的；Initialization vector (IV) 必须是16位 
     * 待加密内容的长度必须是16的倍数，如果不是16的倍数，就会出如下异常： 
     * javax.crypto.IllegalBlockSizeException: Input length not multiple of 16 bytes 
     *  
     *  由于固定了位数，所以对于被加密数据有中文的, 加、解密不完整 
     *   
     *  可 以看到，在原始数据长度为16的整数n倍时，假如原始数据长度等于16*n，则使用NoPadding时加密后数据长度等于16*n， 
     *  其它情况下加密数据长 度等于16*(n+1)。在不足16的整数倍的情况下，假如原始数据长度等于16*n+m[其中m小于16]， 
     *  除了NoPadding填充之外的任何方 式，加密数据长度都等于16*(n+1). 
     */  
    static final String CIPHER_ALGORITHM_CBC_NoPadding = "AES/CBC/NoPadding";   
      
    static SecretKey secretKey;  
          
    public static void main(String[] args) throws Exception {  
       // method1("yangpeiyu");  
       // method2("sdsadsadsadsad");  
       String srt = encrypt("yangpeiyu1985","123");
       String str = decrypt(srt);
       
       System.out.println("srt: "+srt+",str: "+str);
       
    }
    
    public static String encrypt(String str,String key) throws Exception{
    	String encrypt=null;
    	if(str!=null){
    		cipher = Cipher.getInstance(KEY_ALGORITHM);
            //KeyGenerator 生成aes算法密钥  
            secretKey = KeyGenerator.getInstance(KEY_ALGORITHM).generateKey(); 
            cipher.init(Cipher.ENCRYPT_MODE, secretKey);//使用加密模式初始化 密钥 
    		if(key!=null){
    			str+=","+key;
    		}
    		 byte[] dataByte = cipher.doFinal(str.getBytes()); //按单部分操作加密或解密数据，或者结束一个多部分操作。
    		 encrypt = new BASE64Encoder().encode(dataByte);
    	}
    	return encrypt;
    }
    
    public static String decrypt(String str) throws Exception{
    	String decrypt=null;
    	if(str!=null){
    		cipher.init(Cipher.DECRYPT_MODE, secretKey);//使用解密模式初始化 密钥
    		byte[] base = new sun.misc.BASE64Decoder().decodeBuffer(str);
    		byte[] decryptByte = cipher.doFinal(base);
    		decrypt = new String(decryptByte);
    		String [] array = decrypt.split(",");
    		decrypt = array[0];
    	}
    	return decrypt;
    }
      
    /** 
     * 使用AES 算法 加密，默认模式  AES/ECB 
     */  
    static void method1(String str) throws Exception {  
        cipher = Cipher.getInstance(KEY_ALGORITHM);  
        //KeyGenerator 生成aes算法密钥  
        secretKey = KeyGenerator.getInstance(KEY_ALGORITHM).generateKey();  
        System.out.println("密钥的长度为：" + secretKey.getEncoded().length);  
          
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);//使用加密模式初始化 密钥  
        byte[] encrypt = cipher.doFinal(str.getBytes()); //按单部分操作加密或解密数据，或者结束一个多部分操作。  
          
        System.out.println("method1-加密：" +new BASE64Encoder().encode(encrypt));  
        cipher.init(Cipher.DECRYPT_MODE, secretKey);//使用解密模式初始化 密钥  
        byte[] decrypt = cipher.doFinal(encrypt);  
        System.out.println("method1-解密后：" + new String(decrypt));  
    }
    
    
      
    /** 
     * 使用AES 算法 加密，默认模式 AES/ECB/PKCS5Padding 
     */  
    static void method2(String str) throws Exception {  
        cipher = Cipher.getInstance(CIPHER_ALGORITHM_ECB);  
        //KeyGenerator 生成aes算法密钥  
        secretKey = KeyGenerator.getInstance(KEY_ALGORITHM).generateKey();  
        System.out.println("密钥的长度为：" + secretKey.getEncoded().length);  
          
        cipher.init(Cipher.ENCRYPT_MODE, secretKey);//使用加密模式初始化 密钥  
        byte[] encrypt = cipher.doFinal(str.getBytes()); //按单部分操作加密或解密数据，或者结束一个多部分操作。  
          
        System.out.println("method2-加密：" +new String(encrypt,"utf-8"));  
        cipher.init(Cipher.DECRYPT_MODE, secretKey);//使用解密模式初始化 密钥  
        byte[] decrypt = cipher.doFinal(encrypt);  
        System.out.println("method2-解密后：" + new String(decrypt));  
          
    }  
      
    static byte[] getIV() {  
        String iv = "1234567812345678"; //IV length: must be 16 bytes long  
        return iv.getBytes();  
    }  
      
    /** 
     * 使用AES 算法 加密，默认模式 AES/CBC/PKCS5Padding 
     */  
    static void method3(String str) throws Exception {  
        cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC);  
        //KeyGenerator 生成aes算法密钥  
        secretKey = KeyGenerator.getInstance(KEY_ALGORITHM).generateKey();  
        System.out.println("密钥的长度为：" + secretKey.getEncoded().length);  
          
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(getIV()));//使用加密模式初始化 密钥  
        byte[] encrypt = cipher.doFinal(str.getBytes()); //按单部分操作加密或解密数据，或者结束一个多部分操作。  
          
        System.out.println("method3-加密：" + Arrays.toString(encrypt));  
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(getIV()));//使用解密模式初始化 密钥  
        byte[] decrypt = cipher.doFinal(encrypt);  
        System.out.println("method3-解密后：" + new String(decrypt));  
          
    }  
      
    /** 
     * 使用AES 算法 加密，默认模式 AES/CBC/NoPadding  参见上面对于这种mode的数据限制 
     */  
    static void method4(String str) throws Exception {  
        cipher = Cipher.getInstance(CIPHER_ALGORITHM_CBC_NoPadding);  
        //KeyGenerator 生成aes算法密钥  
        secretKey = KeyGenerator.getInstance(KEY_ALGORITHM).generateKey();  
        System.out.println("密钥的长度为：" + secretKey.getEncoded().length);  
          
        cipher.init(Cipher.ENCRYPT_MODE, secretKey, new IvParameterSpec(getIV()));//使用加密模式初始化 密钥  
        byte[] encrypt = cipher.doFinal(str.getBytes(), 0, str.length()); //按单部分操作加密或解密数据，或者结束一个多部分操作。  
          
        System.out.println("method4-加密：" + Arrays.toString(encrypt));  
        cipher.init(Cipher.DECRYPT_MODE, secretKey, new IvParameterSpec(getIV()));//使用解密模式初始化 密钥  
        byte[] decrypt = cipher.doFinal(encrypt);  
          
        System.out.println("method4-解密后：" + new String(decrypt));  
          
    }   
}
