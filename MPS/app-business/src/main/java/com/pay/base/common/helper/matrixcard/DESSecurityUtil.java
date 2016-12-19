package  com.pay.base.common.helper.matrixcard;

import java.security.Key;

import javax.crypto.Cipher;
import javax.crypto.spec.SecretKeySpec;

import sun.misc.BASE64Decoder;
import sun.misc.BASE64Encoder;



/**
 * 
 * 使用DES加密与解密,可对byte[],String类型进行加密与解密 密文可使用String,byte[]存储.
 * 
 * 方法: void getKey(String strKey)从strKey的字条生成一个Key
 * 
 * String getEncString(String strMing)对strMing进行加密,返回String密文 String
 * getDesString(String strMi)对strMin进行解密,返回String明文
 * 
 * byte[] getEncCode(byte[] byteS)byte[]型的加密 byte[] getDesCode(byte[]
 * byteD)byte[]型的解密
 */

public final class DESSecurityUtil {	

	
	private final static String ENCODING="UTF8";

	static Key key ;	
	
	/**
	 * 根据参数生成KEY
	 * 
	 * @param strKey
	 */
	private static void getKey() {
		if(key == null){
			byte[] raw = new byte[]{0x51, 0x70, 0x53, 0x3A, 0x2B, 0x7A, 0x77};
		    byte[] keyBytes = addParity(raw);
		    
		    
		    // Convert the bytes into a SecretKey suitable for use by Cipher
		    key = new SecretKeySpec(keyBytes, "DES");
		}
/*		
		try {
			KeyGenerator _generator = KeyGenerator.getInstance("DES");
			_generator.init(new SecureRandom(keyString.getBytes()));
			key = _generator.generateKey();
			_generator = null;
		} catch (Exception e) {
			logger.error("error in DESSecurityUtil's getKey:" + e.getMessage());
		}
*/
	}
	
	/**
	 * 检查字串是否被加密过
	 * @param encryptedData 被检查的字符串
	 * @return
	 */
	public static boolean isEncrypted(final String encryptedData){
		String str = decrypt(encryptedData);
		return str != null && !"".equals(str);
	}
	
	/**
	 * 加密String明文输入,String密文输出
	 * 
	 * @param strMing
	 * @return
	 */
	public static String encrypt(final String encryptData) {
		getKey();
		byte[] byteMi = null;
		byte[] byteMing = null;
		String strMi = "";
		BASE64Encoder base64en = new BASE64Encoder();
		try {
			byteMing = encryptData.getBytes(ENCODING);
			byteMi = getEncCode(byteMing);
			strMi = base64en.encode(byteMi);
		} catch (Exception e) {
			//logger.error("error in DESSecurityUtil's encrypt:"+ e.getMessage());
		} finally {
			base64en = null;
			byteMing = null;
			byteMi = null;
		}
		return strMi;
	}

	/**
	 * 解密 以String密文输入,String明文输出
	 * 
	 * @param strMi
	 * @return
	 */
	public static String decrypt(final String decryptData) {
		getKey();
		BASE64Decoder base64De = new BASE64Decoder();
		byte[] byteMing = null;
		byte[] byteMi = null;
		String strMing = "";
		try {
			byteMi = base64De.decodeBuffer(decryptData);
			byteMing = getDesCode(byteMi);
			strMing = new String(byteMing, ENCODING);
		} catch (Exception e) {
			//logger.error("error in DESSecurityUtil's encrypt:"+ e.getMessage());
		} finally {
			base64De = null;
			byteMing = null;
			byteMi = null;
		}
		return strMing;
	}

	/**
	 * 加密以byte[]明文输入,byte[]密文输出
	 * 
	 * @param byteS
	 * @return
	 */
	private static byte[] getEncCode(byte[] byteS) {
		byte[] byteFina = null;
		Cipher cipher;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.ENCRYPT_MODE, key);
			byteFina = cipher.doFinal(byteS);
		} catch (Exception e) {
			//logger.error("error in DESSecurityUtil's encrypt:"+ e.getMessage());
		} finally {
			cipher = null;
		}
		return byteFina;
	}

	/**
	 * 解密以byte[]密文输入,以byte[]明文输出
	 * 
	 * @param byteD
	 * @return
	 */
	private static byte[] getDesCode(byte[] byteD) {
		Cipher cipher;
		byte[] byteFina = null;
		try {
			cipher = Cipher.getInstance("DES");
			cipher.init(Cipher.DECRYPT_MODE, key);
			byteFina = cipher.doFinal(byteD);
		} catch (Exception e) {
			//logger.error("error in DESSecurityUtil's encrypt:"+ e.getMessage());
		} finally {
			cipher = null;
		}
		return byteFina;

	}
	
	//Takes a 7-byte quantity and returns a valid 8-byte DES key.
    // The input and output bytes are big-endian, where the most significant
    // byte is in element 0.
    private static byte[] addParity(byte[] in) {
        byte[] result = new byte[8];
    
        // Keeps track of the bit position in the result
        int resultIx = 1;
    
        // Used to keep track of the number of 1 bits in each 7-bit chunk
        int bitCount = 0;
    
        // Process each of the 56 bits
        for (int i=0; i<56; i++) {
            // Get the bit at bit position i
            boolean bit = (in[6-i/8]&(1<<(i%8))) > 0;
    
            // If set, set the corresponding bit in the result
            if (bit) {
                result[7-resultIx/8] |= (1<<(resultIx%8))&0xFF;
                bitCount++;
            }
    
            // Set the parity bit after every 7 bits
            if ((i+1) % 7 == 0) {
                if (bitCount % 2 == 0) {
                    // Set low-order bit (parity bit) if bit count is even
                    result[7-resultIx/8] |= 1;
                }
                resultIx++;
                bitCount = 0;
            }
            resultIx++;
        }
        return result;
    }

	public static void main(String[] args) {
		System.out.println("hello");
		DESSecurityUtil des = new DESSecurityUtil();// 实例化一个对像
		String strEnc = des.encrypt("aacafd");// 加密字符串,返回String的密文
		System.out.println(strEnc);
		String strDes = des.decrypt(strEnc);// 把String 类型的密文解密
		System.out.println(strDes);
	}

}
