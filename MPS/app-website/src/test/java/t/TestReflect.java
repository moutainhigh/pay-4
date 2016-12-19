/**
 * 
 */
package t;

import java.lang.reflect.Field;

import org.apache.commons.lang.StringUtils;

import com.pay.fi.dto.CrosspayApiRequest;

/**
 * @author PengJiangbo
 *
 */
public class TestReflect {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
//		try {
//			Class<TestXml2Bean> clazz = (Class<TestXml2Bean>) Class.forName("tes.TestXml2Bean") ;
//			Object obj = clazz.newInstance() ;
//			System.out.println(obj);
////			clazz.
//			Field[] fields = clazz.getDeclaredFields() ;
//			//obj.
//			System.out.println("ffffffffffffffff=====================");
//			for(Field f : fields){
//				System.out.println(f);
//			}
//			System.out.println("***************************ffffffffffffffff=====================");
//			Field field = clazz.getDeclaredField("acquiringTime") ;
//			field.setAccessible(true);
//			System.out.println("前" + field.get(obj));
//			field.set(obj, "122233223");
//			System.out.println("后:" + field.get(obj));
//			
//		} catch (ClassNotFoundException e) {
//			e.printStackTrace();
//		} catch (InstantiationException e) {
//			e.printStackTrace();
//		} catch (IllegalAccessException e) {
//			e.printStackTrace();
//		} catch (SecurityException e) {
//			e.printStackTrace();
//		} catch (NoSuchFieldException e) {
//			e.printStackTrace();
//		}
		CrosspayApiRequest crosspayApiRequest = new CrosspayApiRequest() ;
		crosspayApiRequest.setGoodsName("一二三四五");
		String goodsName = crosspayApiRequest.getGoodsName() ;
		strLenValid(crosspayApiRequest, "goodsName", goodsName, 3);
		
		System.out.println(crosspayApiRequest.getGoodsName());
		
	}
	
	private static void strLenValid(CrosspayApiRequest crosspayApiRequest,
			String decalaredField, String str, int len) {
		if(StringUtils.isNotEmpty(str)){
			if(str.length() > len){
				str = str.substring(0, len) ;
				crosspayApiRequest.setGoodsName(str);
				try {
					//Class clazz = Class.forName("com.pay.fi.dto.CrosspayApiRequest") ;
					Class<CrosspayApiRequest> clazz = CrosspayApiRequest.class ;
					Object obj = clazz.newInstance() ;
					Field field = clazz.getDeclaredField(decalaredField) ;
					field.setAccessible(true);
					field.set(obj, str);
				} catch (Exception e) {
					e.printStackTrace();
				} 
			}
		}
	}

}
