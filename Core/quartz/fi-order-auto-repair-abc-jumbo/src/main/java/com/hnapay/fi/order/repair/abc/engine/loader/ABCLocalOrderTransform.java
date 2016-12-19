package com.hnapay.fi.order.repair.abc.engine.loader;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.hitrust.trustpay.client.MerchantConfig;
import com.hitrust.trustpay.client.XMLDocument;
import com.hnapay.fi.dto.batchrepair.api.LocalOrderDTO;
import com.hnapay.fi.order.repair.engine.loader.LocalOrderBasedBankOrderLoader.LocalOrderTransform;

/**
 * 农行参数转换 <br />
 * latest modified time : 2011-8-26 上午10:45:00
 * 
 * @author bigknife
 */
public class ABCLocalOrderTransform implements LocalOrderTransform {
	private boolean queryDeail = false;
	
	private String merchantId = "231000001885A03";
	private String merchantType = "B2C";// B2C OR B2B
	
	
	/**
	 * @param merchantType the merchantType to set
	 */
	public void setMerchantType(String merchantType) {
		this.merchantType = merchantType;
	}

	/**
	 * @param merchantId the merchantId to set
	 */
	public void setMerchantId(String merchantId) {
		this.merchantId = merchantId;
	}

	/**
	 * @param queryDeail the queryDeail to set
	 */
	public void setQueryDeail(boolean queryDeail) {
		this.queryDeail = queryDeail;
	}

	/**
	 * 农行请求参数格式：<br />
	 * <code>
	 * <TrxRequest>
	 * 		<TrxType>Query</TrxType>
	 * 		<OrderNo>${OrderNo}</OrderNo>
	 * 		<QueryDetail>${bQueryDetail}</QueryDetail>
	 * </TrxRequest>
	 * </code>
	 * 
	 * StringBuffer tMessage = new StringBuffer("");
    tMessage.append("<Merchant>")
      .append("<ECMerchantType>").append(this.iECMerchantType).append("</ECMerchantType>")
      .append("<MerchantID>").append(this.tMerchantConfig.getMerchantID(i)).append("</MerchantID>")
      .append("</Merchant>")
      .append(aMessage.toString());
	 */
	@Override
	public Map<String, String> toQueryParam(LocalOrderDTO localOrder) {
		if(localOrder == null){
			return null;
		}
		/**
		 * Key 使用 "MSG", 农行的请求报文发送时会自动加上“MSG”
		 */
		Map<String, String> map = new HashMap<String, String>();
		
		StringBuffer buf = new StringBuffer();
		
		//商户信息
		buf.append("<Merchant>")
				.append("<ECMerchantType>")
					.append(merchantType)
				.append("</ECMerchantType>")
				.append("<MerchantID>")
					.append(this.merchantId)
				.append("</MerchantID>")
			.append("</Merchant>");
		
		//请求信息
		buf.append("<TrxRequest>")
				.append("<TrxType>")
					.append("Query")
				.append("</TrxType>")
				.append("<OrderNo>")
					.append(localOrder.getDepositProtocolNo())
				.append("</OrderNo>")
				.append("<QueryDetail>")
					.append(queryDeail)
				.append("</QueryDetail>")
			.append("</TrxRequest>");
		//需要加签，考虑加签的设计
		//对报文加签
		String msg = buf.toString();
		try {
			msg = sign(msg);
			map.put("MSG", "<MSG>"+msg+"</MSG>");
			return map;
		} catch (Exception e) {
			e.printStackTrace();
			throw new RuntimeException(e);
		}
		
	}

	/**
	 * 签名
	 * @param msg
	 * @return
	 * @throws Exception
	 */
	private String sign(String msg) throws Exception {
		MerchantConfig mc = MerchantConfig.getUniqueInstance();
		XMLDocument doc = mc.signMessage(1, new XMLDocument(msg));
		return doc.toString();
	}

	@Override
	public Map<String, String> toQueryParam(List<LocalOrderDTO> localOrders) {
		return null;
	}
	
}
