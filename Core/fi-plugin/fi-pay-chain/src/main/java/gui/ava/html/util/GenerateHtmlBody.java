package gui.ava.html.util;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.util.List;
import java.util.Map;

import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

/**
 * 生成目标html
 * @author PengJiangbo
 *
 */
public class GenerateHtmlBody {

	private static final Log logger = LogFactory.getLog(GenerateHtmlBody.class) ;
	@SuppressWarnings("rawtypes")
	public static String desHtml(final List<Map> resultMap, final Map<String, Object> map){
		StringBuilder sb = new StringBuilder() ;
		
		//样式
		sb.append("<style type='text/css'>\n");
		sb.append("table{border-collapse:collapse}\n");
		sb.append("table th{background-color:#CCCCFF;}\n");
		sb.append("table th, table td{border:solid 1px #e5e5e5; }\n") ;
		sb.append(".border0{border:0px;}\n");
		sb.append("#t1 tr td{border:0px;}\n") ;
		sb.append("</style>\n") ;
		
		//主体内容商品支付成功信息
		sb.append("<table cellspacing='0' cellpadding='5' width='90%' id='t1'>\n") ;
		sb.append("<tbody>\n");
		sb.append("<tr>\n");
		sb.append("<td width='125px;' style='font-weight:bold;font-size:14px;'>Dear sir (miss)：</td>\n") ;
		sb.append("<td style='font-weight:bold;font-size:14px;'>"+ map.get("shippingName") +"</td>\n");
		sb.append("</tr>\n") ;
		sb.append("<tr>\n") ;
		sb.append("<td colspan='2' style='color:#FF6633;font-weight:bold;font-size:18px;'>! Payment "+ returnPaymentDesc(map.get("status")) +" Information !</td>\n") ;
		sb.append("</tr>\n") ;
		sb.append("<tr>\n") ;
		sb.append("<td colspan='2' style='color:#FF6633;font-weight:bold;font-size:18px;'>Order Number："+ map.get("tradeOrderNo") +"</td>\n") ;
		sb.append("</tr>\n");
		sb.append("<tr>\n");
		sb.append("<td colspan='2' style='font-weight:bold;font-size:14px;'>Please remenber the order number, to inquire about the progress of the order.</td>\n") ;
		sb.append("</tr>\n") ;
		sb.append("</tbody>\n") ;
		sb.append("</table>\n") ;
		sb.append("<br/>\n") ;
		
		//主体内容[表格1]， 支付汇总信息
		sb.append("<table cellspacing='0' cellpadding='5' width='90%' >\n");
		//表格头
		sb.append("<thead>\n");
		sb.append("<tr>\n");
		sb.append("<th >Currency</th>\n") ;
		sb.append("<th>Sub-Total</th>\n") ;
		sb.append("<th>Fee</th>\n") ;
		sb.append("<th>Total</th>\n") ;
		sb.append("<th>contactInfomation</th>\n");	
		sb.append("</tr>\n") ;
		sb.append("</thead>\n") ;
		//表格体
		sb.append("<tbody>\n");
		sb.append("<tr>\n");
		sb.append("<td>"+ null2String(map.get("currencyCode")) +"</td>\n") ;
		sb.append("<td>"+ amountFormat2Str(map.get("subTotalAmount")) +"</td>\n") ;
		sb.append("<td>"+ amountFormat2Str(map.get("fee")) +"</td>\n") ;
		sb.append("<td>"+ amountFormat2Str(map.get("totalAmount")) +"</td>\n") ;
		sb.append("<td>"+ null2String(map.get("contact")) +"</td>\n") ;
		sb.append("</tr>\n") ;
		sb.append("</tbody>\n") ;
		sb.append("</table>\n") ;
		sb.append("<br/>\n") ;
		
		//主体内容[表格2]， 商品内容
		sb.append("<table cellspacing='0' cellpadding='5' width='90%' >\n");
		//表格头
		sb.append("<thead>\n");
		sb.append("<tr>\n");
		sb.append("<th >Product Name</th>\n") ;
		sb.append("<th>Model</th>\n") ;
		sb.append("<th>Quantity</th>\n") ;
		sb.append("<th>Price</th>\n") ;
		sb.append("<th>URL</th>\n");
		sb.append("</tr>\n");
		sb.append("</thead>\n") ;
		//表格体
		//-----------------------------商品信息，多条记录需循环   Sta
		sb.append("<tbody>\n");
		if(CollectionUtils.isNotEmpty(resultMap)){
			for(Map desMap : resultMap){
				sb.append("<tr>\n");
				sb.append("<td>"+ null2String(desMap.get("productName")) +"</td>\n") ;
				sb.append("<td>"+ null2String(desMap.get("productSpec")) +"</td>\n") ;
				sb.append("<td>"+ null2String(desMap.get("productNum")) +"</td>\n") ;
				sb.append("<td>"+ amountFormat2Str(desMap.get("price")) +"</td>\n") ;
				sb.append("<td>"+ null2String(desMap.get("url")) +"</td>\n") ;
				sb.append("</tr>\n") ;
				
			}
		}
		sb.append("</tbody>\n") ;
		sb.append("</table>\n") ;
		sb.append("<br/>\n"); 
		//-----------------------------商品信息，多条记录需循环   End
		//主体内容[表格3]
		sb.append("<table cellspacing='0' cellpadding='5' width='90%' >\n");
		//表格头
		sb.append("<thead>\n");
		sb.append("<tr>\n");
		/*sb.append("<th >Payment Model</th>\n") ;
		sb.append("<th>Payment Method</th>\n") ;*/
		sb.append("<th>Card Number</th>\n") ;
		sb.append("</tr>\n");
		sb.append("</thead>\n") ;
		//表格体
		sb.append("<tbody>\n");
		sb.append("<tr>\n");
		/*sb.append("<td>CNY</td>\n") ;
		sb.append("<td>99</td>\n") ;*/
		sb.append("<td>"+ reViewCardNo(map.get("cardNo")) +"</td>\n") ;
		sb.append("</tr>\n");
		sb.append("</tbody>\n") ;
		sb.append("</table>\n") ;
		sb.append("<br/>\n") ;
		
		//账单、收货信息
		sb.append("<table width='90%'>\n");
		sb.append("<thead>\n");
		sb.append("<tr>\n") ;
		sb.append("<th colspan='2' style='text-align:left;'>Billing Information:</th>\n"); 
		sb.append("<th class='border0' style='background-color:white;'>&nbsp;</th>\n");
		sb.append("<th colspan='2' style='text-align:left;'>Shipping Information:</th>\n") ;
		sb.append("</tr>\n") ;
		sb.append("</thead>\n") ;
		sb.append("<tbody>\n");
		sb.append("<tr>\n");
		sb.append("<td width='150px;'>Billing Name</td>\n") ;
		sb.append("<td>"+ null2String(map.get("billName")) +"</td>\n") ;
		sb.append("<td class='border0'>&nbsp;</td>\n") ;
		sb.append("<td width='150px;'>Shipping Name</td>\n") ;
		sb.append("<td>"+ null2String(map.get("shippingName")) +"</td>\n") ;
		sb.append("</tr>\n") ;
		sb.append("<tr>\n") ;
		sb.append("<td>Billing Address</td>\n");
		sb.append("<td>"+ null2String(map.get("billAddress")) +"</td>\n") ;
		sb.append("<td style='border:0px;'>&nbsp;</td>\n") ;
		sb.append("<td>Shipping Address</td>\n");
		sb.append("<td>"+ null2String(map.get("shippingAddress")) +"</td>\n");
		sb.append("</tr>\n");
		sb.append("<tr>\n") ;
		sb.append("<td>Billing Postal Code</td>\n");
		sb.append("<td>"+ null2String(map.get("billPostCode")) +"</td>\n") ;
		sb.append("<td style='border:0px;'>&nbsp;</td>\n") ;
		sb.append("<td>Shipping Postal Code</td>\n");
		sb.append("<td>"+ null2String(map.get("shippingPostCode")) +"</td>\n");
		sb.append("</tr>\n");
		sb.append("<tr>\n") ;
		sb.append("<td>Billing Country</td>\n");
		sb.append("<td>"+ null2String(map.get("billCountry")) +"</td>\n") ;
		sb.append("<td style='border:0px;'>&nbsp;</td>\n") ;
		sb.append("<td>Shipping Country</td>\n");
		sb.append("<td>"+ null2String(map.get("shippingCountry")) +"</td>\n");
		sb.append("</tr>\n");
		sb.append("<tr>\n") ;
		sb.append("<td>Billing State</td>\n");
		sb.append("<td>"+ null2String(map.get("billState"))  +"</td>\n") ;
		sb.append("<td style='border:0px;'>&nbsp;</td>\n") ;
		sb.append("<td>Shipping State</td>\n");
		sb.append("<td>"+ null2String(map.get("shippingState")) +"</td>\n");
		sb.append("</tr>\n");
		sb.append("<tr>\n") ;
		sb.append("<td>Billing City</td>\n");
		sb.append("<td>"+ null2String(map.get("billCity")) +"</td>\n") ;
		sb.append("<td style='border:0px;'>&nbsp;</td>\n") ;
		sb.append("<td>Shipping City</td>\n");
		sb.append("<td>"+ null2String(map.get("shippingCity")) +"</td>\n");
		sb.append("</tr>\n");
		sb.append("<tr>\n") ;
		sb.append("<td>Billing Phone Number</td>\n");
		sb.append("<td>"+ null2String(map.get("billPhone")) +"</td>\n") ;
		sb.append("<td style='border:0px;'>&nbsp;</td>\n") ;
		sb.append("<td>Shipping Phone Number</td>\n");
		sb.append("<td>"+ null2String(map.get("shippingPhone")) +"</td>\n");
		sb.append("</tr>\n");
		sb.append("<tr>\n") ;
		sb.append("<td>Billing Email</td>\n");
		sb.append("<td>"+ null2String(map.get("billEmail")) +"</td>\n") ;
		sb.append("<td style='border:0px;'>&nbsp;</td>\n") ;
		sb.append("<td>Shipping Email</td>\n");
		sb.append("<td>"+ null2String(map.get("shippingEmail")) +"</td>\n");
		sb.append("</tr>\n");
		sb.append("</tbody>\n");
		sb.append("</table>\n");
		sb.append("<br/>\n");
		
		return sb.toString() ;
	}
	
	/**
	 * 金额格式化，以字符串形式返回
	 * @param amount
	 * @return
	 */
	private static String amountFormat2Str(final Object obj){
		Long amount = null;
		try {
			String obj2 = "0" ;
			if(obj != null){
				obj2 = obj.toString() ;
			}
			amount = Long.valueOf(obj2);
		} catch (NumberFormatException e) {
			logger.info("java类型转换出错了！");
			e.printStackTrace();
		}
		if(null != amount){
			BigDecimal bigDecimal = new BigDecimal(amount).divide(new BigDecimal(100)) ;
			//不四舍五入的方法  
			//,代表分隔符    
			//.后面的##代表位数 如果换成0 效果就是位数不足0补齐
		    DecimalFormat df =new DecimalFormat("#,##0.000");
		    // 设置舍入模式
		    df.setRoundingMode(RoundingMode.FLOOR); 
		    String formatStr = df.format(bigDecimal) ;
		  	return formatStr ;
		}
		return "0.00" ;
	}
	/**
	 * 卡号隐藏展示
	 * @param object
	 * @return
	 */
	private static String reViewCardNo(final Object object){
		//String desCardNo = "" ;
		String cardNo = "" ;
		if(object != null){
			cardNo = object.toString() ;
		}
		if(StringUtils.isNotEmpty(cardNo) && cardNo.length() >= 16){
			String beforeStr = cardNo.substring(0,6) ;
			String endStr = cardNo.substring(12, cardNo.length()) ;
			cardNo = new StringBuilder(beforeStr).append("******").append(endStr).toString() ;
		}
		
		return cardNo ;
	}
	/**
	 * 支付信息描述信息
	 * @param object
	 * @return
	 */
	private static String returnPaymentDesc(final Object object){
		String paymentDesc = "Failed" ;
		String status = "" ;
		if(null != object){
			status = object.toString() ;
		}
		if(StringUtils.isNotEmpty(status)){
			if(status.equals("1") ){
				paymentDesc = "Successful" ;
			}else{
				paymentDesc = "Failed" ;
			}
		}
		return paymentDesc ;
		
	}
	/**
	 * null转""
	 * @param object
	 * @return
	 */
	private static String null2String(final Object object){
		String str = "" ;
		if(object != null){
			str = object.toString() ;
		}
		return str ;
	}
	/*public static void main(final String args[]){
		System.out.println(desHtml());
	}*/
}
