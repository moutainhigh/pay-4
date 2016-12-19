/**
 * ABCResponseHandler.java 
 * 海航集团新生支付
 */
package com.hnapay.fi.order.repair.abc.conn.responsehandler;

import java.util.ArrayList;
import java.util.List;

import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.DocumentHelper;
import org.dom4j.Element;

import com.hitrust.trustpay.client.MerchantConfig;
import com.hitrust.trustpay.client.TrxException;
import com.hitrust.trustpay.client.XMLDocument;
import com.hnapay.fi.dto.batchrepair.api.BankOrderDTO;
import com.hnapay.fi.enums.batchrepair.api.BankOrderStatusEnum;
import com.hnapay.fi.order.repair.conn.base.BaseHttpResponseHandler;

/**
 * latest modified time : 2011-8-26 上午10:39:14
 * 
 * @author bigknife
 */
public class ABCResponseHandler extends BaseHttpResponseHandler {
	private static final String SUCCESS_CODE = "0000";
	private String bankName = "abc003";

	/**
	 * @param bankName
	 *            the bankName to set
	 */
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}

	@Override
	public boolean isSuccessful(String response) {
		log.info(response);
		// 验签
		try {
			MerchantConfig mc = MerchantConfig.getUniqueInstance();
			XMLDocument doc = new XMLDocument(response);
			mc.verifySign(doc);
			return true;
			/*
			 * Document d = DocumentHelper.parseText(response); String xpath =
			 * "/MSG/Message/TrxResponse/ReturnCode"; String code =((Element) (
			 * List<Element> ) d.selectNodes(xpath).get(0)).getTextTrim();
			 * if(SUCCESS_CODE.equals(code)){ return true; }else{ xpath =
			 * "/MSG/Message/TrxResponse/ErrorMessage"; String message
			 * =((Element) ( List<Element> )
			 * d.selectNodes(xpath).get(0)).getTextTrim(); log.error(message);
			 * return false; }
			 */
		} catch (TrxException e) {
			log.error("农行B2C大额验签失败", e);
			return false;
		}
	}

	@Override
	public void handleErrorResponse(String response) {
		log.error(response);
	}

	@SuppressWarnings("unchecked")
	@Override
	public List<BankOrderDTO> handleOkResponse(String response) {
		
		BankOrderDTO bankOrder = new BankOrderDTO();

		Document d;
		try {
			d = DocumentHelper.parseText(response);

			String xpath = "/MSG/Message/TrxResponse/ReturnCode";
			String code = ((Element) d.selectSingleNode(xpath)).getTextTrim();
			//仅返回成功的订单
			if (SUCCESS_CODE.equals(code)) {
				xpath = "/MSG/Message/TrxResponse/Order/OrderAmount";
				String sAmount = ((Element) d.selectSingleNode(xpath))
						.getTextTrim();
				// 农行返回的单位是分
				double dAmount = Double.parseDouble(sAmount);
				bankOrder.setAmount((long) (dAmount * 1000));
				bankOrder.setBankName(bankName);
				xpath = "/MSG/Message/TrxResponse/Order/OrderStatus";
				String status = ((Element) d.selectSingleNode(xpath)).getTextTrim();
				if("03".equals(status) || "04".equals(status)){
					bankOrder.setStatus(BankOrderStatusEnum.SUCCESS);
					xpath = "/MSG/Message/TrxResponse/Order/OrderNo";
					String orderNo = ((Element) d.selectSingleNode(xpath)).getTextTrim();
					bankOrder.setDepositProtocolNo(orderNo);
					List<BankOrderDTO> ret = new ArrayList<BankOrderDTO>(1);
					ret.add(bankOrder);
					return ret;
				}
				else{
					log.info("订单未成功,订单状态码:" + status);
					bankOrder.setStatus(BankOrderStatusEnum.FAILURE);
					return null;
				}
			}
			log.info("订单未成功,状态码：" + code);
			bankOrder.setStatus(BankOrderStatusEnum.FAILURE);
			return null;

		} catch (DocumentException e) {
			log.error("解析返回结果失败", e);
			return null;
		}
	}

}
