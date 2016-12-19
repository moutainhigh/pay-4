/**
 * 
 */
package com.pay.fo.bankcorp.service.impl;

import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import com.pay.fo.bankcorp.common.BankCorpTransCode;
import com.pay.fo.bankcorp.common.ParseXMLHelper;
import com.pay.fo.bankcorp.common.TemplateHelper;
import com.pay.fo.bankcorp.dto.BankCorpPaymentReqDTO;
import com.pay.fo.bankcorp.dto.BankCorpPaymentRespDTO;
import com.pay.fo.bankcorp.dto.BankCorpQueryBalanceReqDTO;
import com.pay.fo.bankcorp.dto.BankCorpQueryBalanceRespDTO;
import com.pay.fo.bankcorp.model.BankChannelConfig;
import com.pay.fo.bankcorp.service.BankCorporateProcessService;
import com.pay.inf.dao.BaseDAO;

/**
 * @author new
 * 
 */
public class BankCorporateProcessServiceImpl implements BankCorporateProcessService {
	
	Logger log = LoggerFactory.getLogger(getClass());

	private BaseDAO<BankChannelConfig> bankChannelConfigDAO;

	public void setBankChannelConfigDAO(BaseDAO<BankChannelConfig> bankChannelConfigDAO) {
		this.bankChannelConfigDAO = bankChannelConfigDAO;
	}

	@Override
	public BankCorpPaymentRespDTO doPayment(BankCorpPaymentReqDTO req) {
		req.setTransCode(BankCorpTransCode.PAYMENT_ORDER.getValue());
		return processOrder(req, BankCorpTransCode.PAYMENT_ORDER.getValue());
	}

	@Override
	public BankCorpPaymentRespDTO doQuery(BankCorpPaymentReqDTO req) {
		req.setTransCode(BankCorpTransCode.QUERY_TRADE_STATUS.getValue());
		return processOrder(req, BankCorpTransCode.QUERY_TRADE_STATUS.getValue());
	}

	private BankCorpPaymentRespDTO processOrder(BankCorpPaymentReqDTO req, String txnCode) {
		Assert.notNull(req, "the param req must not null");

		BankChannelConfig config = bankChannelConfigDAO.findById(req.getChannelCode());

		req.setFundoutBankName(config.getBankName());
		req.setPayerBankAcc(config.getBankAcc());
		req.setPayerBankAccName(config.getBankAccName());
		String respXML = process(req, txnCode, config.getServerAddress(), config.getServerPort(),config.getMacKey());
		// 获取信息，解析成返回对象
		BankCorpPaymentRespDTO resp = (BankCorpPaymentRespDTO) ParseXMLHelper.doc2Model(respXML, BankCorpPaymentRespDTO.class);
		return resp;
	}

	/*private String process(Object req, String txnCode, String serverAddress, int serverPort,String macKey) {
		// 转换成Socket通讯对象
		SocketMessage message = new SocketMessage("UTF-8");
		SocketHeader header = new SocketHeader();
		header.setTxnCode(txnCode);
		message.setHeader(header);
		message.setMacKey(macKey);
		String content = TemplateHelper.generateReqDoc(txnCode, req);
		message.setContent(content);
		
		// 请求银企直连应用
		String respXML =  DefaultClient.process(message, serverAddress, serverPort);
		
		if(BankCorpTransCode.ERROR.getValue().equals(message.getHeader().getTxnCode())){
			log.error(respXML);
			throw new RuntimeException();
		}
		return respXML;
	}*/
	
	private String process(Object req, String txnCode, String serverAddress, int serverPort,String macKey) {
		// 封装http请求数据
		Map<String,String> fields = new HashMap<String,String>();
		fields.put("reqMessage", TemplateHelper.generateReqDoc(txnCode, req));
		fields.put("txnCode", txnCode);
		
		// 请求银企直连应用
		HttpBankClient client = new HttpBankClient();
		String respXML = client.httpPost(serverAddress,fields);
		
		if(BankCorpTransCode.ERROR.getValue().equals(txnCode)){
			log.error(respXML);
			throw new RuntimeException();
		}
		return respXML;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.fo.bankcorp.service.BankCorporateProcessService#doQueryBalance
	 * (com.pay.fo.bankcorp.dto.BankCorpQueryBalanceReqDTO)
	 */
	@Override
	public BankCorpQueryBalanceRespDTO doQueryBalance(BankCorpQueryBalanceReqDTO req) {
		Assert.notNull(req, "the param req must not null");

		BankChannelConfig config = bankChannelConfigDAO.findById(req.getChannelCode());

		req.setFundoutBankName(config.getBankName());
		req.setPayerBankAcc(config.getBankAcc());
		req.setPayerBankAccName(config.getBankAccName());
		req.setTransCode(BankCorpTransCode.QUERY_BANK_ACC_BALANCE.getValue());

		String respXML = process(req, BankCorpTransCode.QUERY_BANK_ACC_BALANCE.getValue(), config.getServerAddress(), config.getServerPort(),config.getMacKey());
		// 获取信息，解析成返回对象
		BankCorpQueryBalanceRespDTO resp = (BankCorpQueryBalanceRespDTO) ParseXMLHelper.doc2Model(respXML, BankCorpQueryBalanceRespDTO.class);
		return resp;
	}

}
