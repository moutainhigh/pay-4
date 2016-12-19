package com.pay.pe.service.payment.common;

import java.util.Map;
import java.util.Set;

import com.pay.util.DateUtil;

public class LogUtil {

	
	/**
	 * @param busDis 业务操作简单描述：自己定义，用简单的英文单词描述业务操作
	 * @param status 业务操作状态：（Start/Success/Fail/Exception）
	 * @param busTrac 业务跟踪标识：根据业务选择一个业务跟踪标识，例如：订单号、业务请求号、dealId等
	 * @param mainIden 主要业务标识：
	 *                                  订单类型：记录OrderCode（930,955等）
	 *                                  支付订单号：PaymentOrder的orderNo
	 *                             	交易类型：记录DealCode（101，706等）
	 *                             	交易ID：dealId
	 *                             	MerchantId：记录商户的memberacctcode
	 *                             	MemberCode：记录会员的membercode
	 * 		          主要业务字段使用key=value&key=value&…的方式连接起来，不存在的字段可以不记录
	 * @param addition 附加业务字段：根据具体业务将需要记录的业务属性使用key=value&key=value&…的方式连接起来
	 * @param errMsg 异常信息：发生异常时，需要将异常信息记录下来
	 * @param errCode 错误代码：已经对错误进行编码的小组将错误代码信息记录下来，不存在留空
	 * @param errDis 错误信息：记录错误的描述性的信息
	 * @return
	 */
	public static String wrapLog(String busDis,String status,
								 String busTrac, Map mainIden,
								 Map addition,String errMsg,
								 String errCode,String errDis){
		
		String strMainInden = convertMapToKeyValueString(mainIden);
		String strAddition = convertMapToKeyValueString(addition);
		
		StringBuffer logstr = new StringBuffer();
		logstr.append(DateUtil.formatDateTime("yyyy-MM-dd HH:mm:ss"))
			  .append("|PE")
			  .append("|")
			  .append(busDis == null ? "" : busDis)
			  .append("|")
			  .append(status == null ? "" : status)
			  .append("|")
			  .append(busTrac == null ? "" : busTrac)
			  .append("|")
			  .append(strMainInden == null ? "" : strMainInden)
			  .append("|")
			  .append(strAddition == null ? "" : strAddition)
			  .append("|")
			  .append(errMsg == null ? "" : errMsg)
			  .append("|")
			  .append(errCode == null ? "" : errCode)
			  .append("|")
			  .append(errDis == null ? "" : errDis);
		return logstr.toString();
	}
	
	private static String convertMapToKeyValueString(Map map) {
		StringBuffer sb = new StringBuffer("");
		if (map != null && !map.isEmpty()) {
			Set<String> keySet = map.keySet();
			int i = 1;
			for (String key : keySet) {
				if (i != 1) {
					sb.append("&");
				}
				sb.append(key)
					.append("=")
					.append(map.get(key));
				i++;
			}
		}
		String s = sb.toString();
		sb = null;
		return s;
	}
	
//	public static void main(String[] args) {
//		Map map = new HashMap();
//		Integer i = null;
//		map.put("1", i);
//		//map.put("meifjei", 1);
//		//map.put("aaaa", "aaaa");
//		
//		String busDis = null;
//		String s = LogUtil.wrapLog(null, null, null, map, null, null, null, null);
//		System.out.println(s);
//		//System.out.println(busDis);
//	}
}
