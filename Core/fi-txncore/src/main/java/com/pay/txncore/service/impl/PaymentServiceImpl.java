/**
 *  modify History:
 *  2016-04-20  sch  payByChannels() 中如果获取渠道参数失败，则设置该网关订单为失败状态
 *  2016-05-23  sch  收银台支付函数中增加 对tradeExtends的更新 
 */
package com.pay.txncore.service.impl;

import java.io.IOException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;
import java.util.Random;

import com.pay.acc.member.dto.EnterpriseBaseDto;
import com.pay.txncore.dto.*;
import com.pay.txncore.service.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
//import org.apache.commons.logging.Log;
//import org.apache.commons.logging.LogFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.util.StringUtils;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.member.service.EnterpriseBaseService;
import com.pay.acc.rate.dto.MerchantRateDto;
import com.pay.acc.rate.service.MerchantRateService;
import com.pay.acc.service.account.AccountQueryService;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.member.MemberQueryService;
import com.pay.acc.service.member.dto.MemberBaseInfoBO;
import com.pay.cardbin.model.CardBinInfo;
import com.pay.cardbin.service.CardBinInfoService;
import com.pay.dcc.model.PartnerDCCConfig;
import com.pay.dcc.service.ConfigurationDCCService;
import com.pay.fi.commons.PaymentTypeEnum;
import com.pay.fi.commons.PaymentWayEnum;
import com.pay.fi.commons.TransTypeEnum;
import com.pay.fi.exception.BusinessException;
import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.fi.helper.ChannelItemOrgCodeEnum;
import com.pay.fi.helper.CredoraxCurrencyCodeEnum;
import com.pay.inf.enums.DCCEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.enums.SysRespCodeEnum;
import com.pay.inf.model.IpCountryInfo;
import com.pay.inf.service.IpCountryInfoService;
import com.pay.jms.notification.request.ChannelMidAmountNotifyRequest;
import com.pay.jms.notification.request.RequestType;
import com.pay.jms.notification.request.WeiXinNotifyRequest;
import com.pay.jms.sender.JmsSender;
import com.pay.rm.facade.BusinessTradeCountService;
import com.pay.rm.facade.dto.BusinessTradeCountDTO;
import com.pay.txncore.client.ChannelClientService;
import com.pay.txncore.client.RiskClientService;
import com.pay.txncore.commons.BCMIGSRespContant;
import com.pay.txncore.commons.CardOrgUtil;
import com.pay.txncore.commons.ChannelOrderStatusEnum;
import com.pay.txncore.commons.OrderBuilder;
import com.pay.txncore.commons.PaymentOrderStatusEnum;
import com.pay.txncore.commons.TradeOrderStatusEnum;
import com.pay.txncore.crosspay.model.ExpressTracking;
import com.pay.txncore.crosspay.service.ExpressTrackingService;
import com.pay.txncore.dao.ChannelCurrencyDAO;
import com.pay.txncore.dao.PaymentOrderExpandDAO;
import com.pay.txncore.model.PartnerChannelCountry;
import com.pay.txncore.model.PartnerConfig;
import com.pay.txncore.model.PartnerRateFloat;
import com.pay.txncore.model.PartnerSettlementOrder;
import com.pay.txncore.model.PaymentOrderExpand;
import com.pay.txncore.model.SettlementBaseRate;
import com.pay.txncore.model.SettlementRate;
import com.pay.txncore.model.SettlementCurrencyConfig;
import com.pay.txncore.model.TradeAmountCount;
import com.pay.txncore.model.TradeBase;
import com.pay.txncore.model.TradeData;
import com.pay.txncore.model.TransRateMarkup;
import com.pay.txncore.model.TransactionBaseRate;
import com.pay.txncore.model.TransactionRate;
import com.pay.util.CurrencyCodeEnum;
import com.pay.util.DateUtil;
import com.pay.util.IPv4Util;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;

/**
 * @author chaoyue
 *
 */
public class PaymentServiceImpl implements PaymentService {
	private final Logger logger = LoggerFactory.getLogger(PaymentServiceImpl.class);
	private TradeOrderService tradeOrderService;
	private TradeExtendsService tradeExtendsService;
	private ExpressTrackingService expressTrackingService;
	private PaymentOrderService paymentOrderService;
	private MemberQueryService memberQueryService;
	private AccountQueryService accountQueryService;
	private PartnerSettlementOrderService partnerSettlementOrderService;
	private EnterpriseBaseService enterpriseBaseService;
	private ChannelService channelService;
	private ChannelOrderService channelOrderService;
	private PaymentOrderExpandDAO paymentOrderExpandDAO;
	final MathContext mc = new MathContext(4, RoundingMode.HALF_DOWN);
	private SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
	// 限额下次服务
	private BusinessTradeCountService businessTradeCountService;
	private MerchantRateService merchantRateService;
	private ChannelClientService channelClientService;
	private TradeAmountCountService tradeAmountCountService;
	private PartnerConfigService partnerConfigService;
	private ChannelCurrencyDAO channelCurrencyDAO;
	private TransRateMarkupService transRateMarkupService;
	private ConfigurationDCCService dccService;
	private CardBinInfoService cardBinInfoService;
	private TradeDataService tradeDataService;
	private RiskClientService riskClientService;
	private IpCountryInfoService ipCountryInfoService;
	private static final String COUNTRY = "USA,CAN";

	private JmsSender jmsSender;
	private DecryptService decryptService;

	public void setDecryptService(DecryptService decryptService) {
		this.decryptService = decryptService;
	}

	public JmsSender getJmsSender() {
		return jmsSender;
	}

	public void setJmsSender(JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}

	public void setPartnerConfigService(PartnerConfigService partnerConfigService) {
		this.partnerConfigService = partnerConfigService;
	}

	/**
	 * 设置订单清算币种
	 * 
	 * @param paymentInfo
	 */
	public void setSettlementCurrencyCode(PaymentInfo paymentInfo) {
		// 获取汇率
		String settlementCurrencyCode = paymentInfo.getSettlementCurrencyCode();
		if (StringUtil.isEmpty(settlementCurrencyCode)) {
			// 获取商户基本结算户币种
			AcctAttribDto acctAttribDto = accountQueryService
					.doQueryDefaultAcctAttribNsTx(Long.valueOf(paymentInfo.getPartnerId()));
			if(acctAttribDto!=null){
				settlementCurrencyCode = acctAttribDto.getCurCode();
				paymentInfo.setSettlementCurrencyCode(settlementCurrencyCode);
			}
		}
	}

	/**
	 * by peiyu 支付回调，主要用于那些异步通知的渠道通知支付结果的
	 */
	@Override
	public PaymentResult paymentCallback(ChannelPaymentResult channelPaymentResult) {
		Map<String, String> dataMap = new HashMap<String, String>();
		PaymentResult paymentResult = new PaymentResult();
		if (channelPaymentResult != null) {
			String errorCode = channelPaymentResult.getErrorCode();
			String errorMsg = channelPaymentResult.getErrorMsg();
			String authCode = channelPaymentResult.getAuthorisation() != null ? channelPaymentResult.getAuthorisation()
					: "";
			Long payAmount = channelPaymentResult.getPayAmount() != null ? channelPaymentResult.getPayAmount() : 0L;
			String returnNo = channelPaymentResult.getChannelReturnNo();
			Long channelOrderNo = channelPaymentResult.getChannelOrderNo();
			logger.info("errorCode: " + errorCode + " ,errorMsg: " + errorMsg);
			ChannelOrderDTO channelOrderDTO = channelOrderService.queryByChannelOrderNo(channelOrderNo);
			if (channelOrderDTO != null) {
				String partnerId = String.valueOf(channelOrderDTO.getPartnerId());
				String dealId = String.valueOf(channelOrderDTO.getTradeOrderNo());
				String orderAmount = String.valueOf(channelOrderDTO.getAmount() / 10);
				String registerUserId = null;
				String orgCode = channelOrderDTO.getOrgCode();
				String acquiringTime = null;
				String completeTime = null;
				String merchantBillName = channelOrderDTO.getMerchantBillName();
				String noticeUrl = null;
				String returnUrl = null;
				String settlementCurrencyCode = null ;
				String merchantOrderId = null;
				String currencyCode = channelOrderDTO.getCurrencyCode();
				TradeOrderDTO tradeOrderDTO = null;
				TradeBaseDTO tradeBaseDTO = null;
				Long paymentOrderNo = channelOrderDTO.getPaymentOrderNo();
				PaymentOrderDTO paymentOrderDTO = null;
				PaymentOrderExpand paymentOrderExpand = null;
				String cardHolderPhoneNumber = null;
				String idCardNo = null;
				String cardHolderName = null;
				String cardHolderNumber = null;
				PartnerConfig partnerConfig = partnerConfigService.findConfig(Long.valueOf(partnerId), "code1");

				if (partnerConfig != null) {
					dataMap.put("pkey", partnerConfig.getValue());
				}
				logger.info("channelOrderDTO: " + channelOrderDTO);
				if (ChannelOrderStatusEnum.INIT.getCode() == // 渠道订单必须是存在的并且必须是初始化状态的,防止重复更新
				channelOrderDTO.getStatus().intValue()) {
					Long amount = channelOrderDTO.getPayAmount();
					logger.info("amount: " + amount.longValue() + " ,payAmount: " + payAmount.longValue());
					channelOrderDTO.setErrorCode(errorCode);
					channelOrderDTO.setErrorMsg(errorMsg);
					channelOrderDTO.setReturnNo(returnNo);
					channelOrderDTO.setAuthorisation(authCode);
					if (ResponseCodeEnum.SUCCESS.getCode().equals(errorCode)) {
						channelOrderDTO.setStatus(ChannelOrderStatusEnum.SUCCESS.getCode());
					} else {
						channelOrderDTO.setStatus(ChannelOrderStatusEnum.FAIL.getCode());
					}
					channelOrderDTO.setUpdateDate(new Date());
					channelOrderDTO.setCompleteDate(new Date());

					boolean updateFlg = channelOrderService.updateChannelProtocolRnTx(channelOrderDTO,
							ChannelOrderStatusEnum.INIT.getCode());
					if (!updateFlg) {
						logger.error("do not update channel order," + channelOrderDTO.getChannelOrderNo());
					}
					Condition condition = decryptService.getFixCondition(orgCode,errorCode);
					String resultCode = "3099";
					String resultMsg = "Other Error:其他异常";
					//Add by yangjian 2016-08-24
					if(condition == null){
						if(orgCode.equals(ChannelItemOrgCodeEnum.WFT.getCode())){
							resultCode = errorCode ;
							resultMsg = errorMsg ;
						}else if(errorCode.equals("0001")&&orgCode.equals("10081016")){
							logger.info("前海万融："+errorCode+"-"+errorMsg);
							resultCode = "0001";
							resultMsg = errorMsg;
						}else{
						// 渠道返回消息转换
							SysRespCodeEnum srce = SysRespCodeEnum.getResponseCodeEnum(errorCode, orgCode);
							if (srce != null) {
								resultCode = srce.getRespCode();
								resultMsg = srce.getRespDescEn() + ":" + srce.getRespDEscCn();
							}
						}
					}else{
						resultCode= condition.getReturnCode();
						resultMsg = condition.getReturnMsg() + ":" + condition.getReturnMsgCn();
					}

					dataMap.put("resultCode", resultCode);
					dataMap.put("resultMsg", resultMsg);
					paymentOrderDTO = paymentOrderService.queryByPaymentOrderNo(paymentOrderNo);
					tradeOrderDTO = tradeOrderService.queryTradeOrderById(paymentOrderDTO.getTradeOrderNo());
					// 调用渠道记账
					channelService.doAccounting(channelOrderDTO, merchantOrderId);
					paymentOrderDTO = paymentOrderService.queryByPaymentOrderNo(paymentOrderNo);
					if (paymentOrderDTO == null) {
						paymentResult.setResponseCode("10501");
						paymentResult.setResponseDesc("paymentOrder not exist!");
						return paymentResult;
					}
					paymentOrderExpand = paymentOrderExpandDAO.findById("queryPayOrderExpandByPayNO", paymentOrderNo);
					if (tradeOrderDTO == null) {
						paymentResult.setResponseCode("10201");
						paymentResult.setResponseDesc("tradeOrder not exist!");
						return paymentResult;
					}
					acquiringTime = this.getDateTime(tradeOrderDTO.getCreateDate());
					completeTime = this.getDateTime(new Date());
					tradeOrderDTO.setRespCode(resultCode);
					tradeOrderDTO.setRespMsg(resultMsg);
					paymentResult.setResponseCode(resultCode);
					paymentResult.setResponseDesc(resultMsg);
					tradeBaseDTO = tradeOrderService.queryTradeBaseById(tradeOrderDTO.getTradeBaseNo());
					TradeExtendsDTO extendsDTO = tradeExtendsService
							.findByTradeOrderNo(tradeOrderDTO.getTradeOrderNo());
					idCardNo = extendsDTO.getIdCardNo();
					cardHolderName = extendsDTO.getCardHolderName();
					cardHolderPhoneNumber = extendsDTO.getCardHolderMobile();
					registerUserId = extendsDTO.getRegisterUserId();
					noticeUrl = tradeBaseDTO.getNotifyUrl();
					returnUrl = tradeBaseDTO.getReturnUrl();
					settlementCurrencyCode = tradeBaseDTO.getSettlementCurrencyCode() ;

					if (ResponseCodeEnum.SUCCESS.getCode().equals(errorCode)) {
						// 更新支付订单和网关订单状态
						paymentOrderDTO.setStatus(PaymentOrderStatusEnum.SUCCESS.getCode());
						paymentOrderDTO.setCompleteDate(new Date());
						paymentOrderDTO.setOrgCode(channelPaymentResult.getOrgCode());
						boolean updateFlag = paymentOrderService.updatePaymentOrderRnTx(paymentOrderDTO,
								PaymentOrderStatusEnum.INIT.getCode());
						if (!updateFlag) {
							logger.error("do not update paymentOrder," + paymentOrderDTO.getPaymentOrderNo());
						}
						// 更新网关订单
						tradeOrderDTO.setCompleteDate(new Date());
						tradeOrderDTO.setStatus(TradeOrderStatusEnum.SUCCESS.getCode());
						tradeOrderDTO.setRefundAmount(tradeOrderDTO.getOrderAmount());
						boolean tradeUpFlg = tradeOrderService.updateTradeOrderRnTx(tradeOrderDTO,
								TradeOrderStatusEnum.PROCESSING.getCode());
						if (!tradeUpFlg) {
							logger.error("do not update trade order," + tradeOrderDTO.getTradeOrderNo());
						}

						/**
						 * add by zhaoyang at 20160908
						 * 结算币种更改。
						 * 1，根据用户号、交易类型、交易币种、结算币种查找通过POSS配置的结算币种信息
						 * 2，如果无查询信息，则使用原有的结算币种；如果有查询信息，则使用查询出来的结算币种与tradeBaseDTO.getSettlementCurrencyCode()
						 * 比较，若相等则使用原有的结算币种，否则使用查询出来的结算币种。
						 * 3，更新交易订单，保存清算订单
						 *
						tradeBaseDTO = this.confirmTheSettlementCurrency(tradeBaseDTO, paymentOrderDTO.getCurrencyCode());
						TradeBase newTb = new TradeBase();
						newTb.setTradeBaseNo(tradeBaseDTO.getTradeBaseNo());
						newTb.setSettlementCurrencyCode(tradeBaseDTO.getSettlementCurrencyCode());
						tradeOrderService.updateTradeBase(newTb);
						 */
						String cardOrg = CardOrgUtil.getCardType(paymentOrderExpand.getCardNo());
						// 保存清算订单
						List<PartnerSettlementOrder> settlementOrders = OrderBuilder.buildSettlementOrder(
								paymentOrderDTO.getPaymentOrderNo(), tradeBaseDTO, tradeOrderDTO,
								tradeBaseDTO.getSettlementCurrencyCode(), cardOrg, enterpriseBaseService,
								channelService);
						partnerSettlementOrderService.createPartnerSettlementOrder(settlementOrders);
						updateTradeAmountCount(tradeOrderDTO, paymentOrderDTO.getPaymentOrderNo());
					} else {
						// 更新支付订单状态
						paymentOrderDTO.setStatus(PaymentOrderStatusEnum.FAIL.getCode());
						paymentOrderDTO.setCompleteDate(new Date());
						paymentOrderDTO.setOrgCode(channelPaymentResult.getOrgCode());
						boolean updateFlag = paymentOrderService.updatePaymentOrderRnTx(paymentOrderDTO,
								PaymentOrderStatusEnum.INIT.getCode());
						if (!updateFlag) {
							logger.error("do not update paymentOrder," + paymentOrderDTO.getPaymentOrderNo());
						}
						// 所有渠道都支付失败了或者符合不需要重试的返回码，需要做网关订单的更改和其他的最终处理
						// 更新网关订单
						tradeOrderDTO.setCompleteDate(new Date());
						tradeOrderDTO.setStatus(TradeOrderStatusEnum.FAILED.getCode());
						boolean tradeUpFlg = tradeOrderService.updateTradeOrderRnTx(tradeOrderDTO);
						if (!tradeUpFlg) {
							logger.error("do not update trade order," + tradeOrderDTO.getTradeOrderNo());
						}
					}
				} else {
					tradeOrderDTO = tradeOrderService.queryTradeOrderById(channelOrderDTO.getTradeOrderNo());
					if (tradeOrderDTO != null) {
						tradeBaseDTO = tradeOrderService.queryTradeBaseById(tradeOrderDTO.getTradeBaseNo());
						noticeUrl = tradeBaseDTO.getNotifyUrl();
						returnUrl = tradeBaseDTO.getReturnUrl();
					}

					TradeExtendsDTO extendsDTO = tradeExtendsService
							.findByTradeOrderNo(tradeOrderDTO.getTradeOrderNo());
					cardHolderPhoneNumber = extendsDTO.getCardHolderMobile();
					cardHolderName = extendsDTO.getCardHolderName();
					idCardNo = extendsDTO.getIdCardNo();
					completeTime = this.getDateTime(channelOrderDTO.getCompleteDate());
					acquiringTime = this.getDateTime(tradeOrderDTO.getCreateDate());
					registerUserId = extendsDTO.getRegisterUserId();

					String resultCode = tradeOrderDTO.getRespCode();
					String resultMsg = tradeOrderDTO.getRespMsg();
					dataMap.put("resultCode", resultCode);
					dataMap.put("resultMsg", resultMsg);
				}
				orderAmount = new BigDecimal(orderAmount).divide(new BigDecimal("100")).toString();
				dataMap.put("dealId", dealId);
				dataMap.put("orderAmount", orderAmount);
				dataMap.put("partnerId", partnerId);
				dataMap.put("orderId", tradeOrderDTO.getOrderId());
				dataMap.put("acquiringTime", DateUtil.formatDateTime(DateUtil.PATTERN1, tradeOrderDTO.getCreateDate()));
				dataMap.put("completeTime", completeTime);
				dataMap.put("dealDate", DateUtil.formatDateTime(DateUtil.SIMPLE_DATE_FROMAT, new Date()));
				dataMap.put("cardHolderNumber", cardHolderNumber);
				dataMap.put("registerUserId", registerUserId);
				dataMap.put("cardHolderName", cardHolderName);
				dataMap.put("idCardNo", idCardNo);
				dataMap.put("cardHolderPhoneNumber", cardHolderPhoneNumber);
				dataMap.put("merchantBillName", merchantBillName);
				dataMap.put("currencyCode", currencyCode);
				dataMap.put("noticeUrl", noticeUrl);
				dataMap.put("returnUrl", returnUrl);
				dataMap.put("settlementCurrencyCode", settlementCurrencyCode) ;

				dataMap.put("remark", tradeBaseDTO.getRemark());
				dataMap.put("language", tradeBaseDTO.getCharset());
				dataMap.put("signtype", tradeBaseDTO.getSignType());
				dataMap.put("charset", tradeBaseDTO.getCharset()) ;

				paymentResult.setResponseCode("0000");
				paymentResult.setResponseDesc("处理完成");
				paymentResult.setDataMap(dataMap);
			} else {
				paymentResult.setResponseCode("11101");
				paymentResult.setResponseDesc("找不到渠道订单");
			}
			return paymentResult;
		} else {
			paymentResult.setResponseCode("9999");
			paymentResult.setResponseDesc("没有返回参数");
		}
		return paymentResult ;
		//return null;
	}

	/**
	 * 3D支付回调
	 */

	// add by liu
	public PaymentResult payment3DCallback(Map channelPaymentResult) {
		PaymentResult paymentResult = new PaymentResult();
		if (channelPaymentResult != null) {
			String authCode = null2unknown((String) channelPaymentResult.get("vpc_AuthorizeId"));
			String returnNo = null2unknown((String) channelPaymentResult.get("vpc_TransactionNo"));
			String referenceNo = null2unknown((String) channelPaymentResult.get("vpc_ReceiptNo"));
			
		
			Long channelOrderNo= channelPaymentResult.get("vpc_MerchTxnRef") == null ? null
						: Long.valueOf(channelPaymentResult.get("vpc_MerchTxnRef").toString());
			if(channelOrderNo==null&&channelPaymentResult.get("vpc_OrderInfo")!=null){channelOrderNo=Long.valueOf((channelPaymentResult.get("vpc_OrderInfo").toString()).substring(5));};
			Long payAmount = channelPaymentResult.get("vpc_Amount") == null ? 0
					: Long.valueOf(channelPaymentResult.get("vpc_Amount").toString());
			String errorCode = null2unknown((String) channelPaymentResult.get("vpc_TxnResponseCode"));
			String errorMsg = BCMIGSRespContant.getRespCodeEnum(errorCode).getRespDesc();
			ChannelOrderDTO channelOrderDTO = channelOrderService.queryByChannelOrderNo(channelOrderNo);
			TradeOrderDTO tradeOrderDTO = tradeOrderService.queryTradeOrderById(channelOrderDTO.getTradeOrderNo());
			TradeBaseDTO tradeBaseDTO = tradeOrderService.queryTradeBaseById(tradeOrderDTO.getTradeBaseNo());
			Long amount = new BigDecimal(channelOrderDTO.getPayAmount()).divide(new BigDecimal("10")).longValue();
			logger.info("3d migs 响应报文" + channelPaymentResult);// 添加响应报文报文log
			paymentResult.setResponseCode(errorCode);
			paymentResult.setResponseDesc(errorMsg);

			// paymentResult.setChannelOrderNo(channelOrderNo);
			paymentResult.setTradeOrderNo(channelOrderDTO.getTradeOrderNo());
			paymentResult.setOrderId(tradeOrderDTO.getOrderId());
			paymentResult.setOrderAmount(tradeOrderDTO.getOrderAmount().toString());
			paymentResult.setPayAmount(amount);
			paymentResult.setCurrencyCode(tradeOrderDTO.getCurrencyCode());
			paymentResult.setPartnerId(tradeOrderDTO.getPartnerId().toString());
			paymentResult.setAcquiringTime(DateUtil.formatDateTime(DateUtil.PATTERN1, tradeOrderDTO.getCreateDate()));
			paymentResult.setRemark(tradeBaseDTO.getRemark());
			paymentResult.setLanguage(tradeBaseDTO.getCharset());
			paymentResult.setSignType(tradeBaseDTO.getSignType());

			// 传入网关基础id号，便于查询return_url
			paymentResult.setPaymentOrderNo(tradeBaseDTO.getTradeBaseNo());
			if (channelOrderDTO != null) {
				channelOrderDTO.setErrorCode(errorCode);
				channelOrderDTO.setErrorMsg(errorMsg);
				channelOrderDTO.setReturnNo(returnNo);
				channelOrderDTO.setReferenceNo(referenceNo);
				channelOrderDTO.setAuthorisation(authCode);
				if (errorCode.equals("0")) {
					errorCode = "0000";
					channelOrderDTO.setStatus(ChannelOrderStatusEnum.SUCCESS.getCode());

				} else {
					channelOrderDTO.setStatus(ChannelOrderStatusEnum.FAIL.getCode());
				}
				channelOrderDTO.setUpdateDate(new Date());
				channelOrderDTO.setCompleteDate(new Date());

				boolean updateFlg = channelOrderService.updateChannelProtocolRnTx(channelOrderDTO,
						ChannelOrderStatusEnum.INIT.getCode());

				if (!updateFlg) {
					logger.error("do not update channel order," + channelOrderDTO.getChannelOrderNo());
				}
				logger.info("channel order update result:" + updateFlg + ",channelOrderNo:" + channelOrderNo);

				// 渠道返回消息转换
				// SystemRespCodeEnum srce =
				// SystemRespCodeEnum.getResponseCodeEnum(errorCode);
				SysRespCodeEnum srce = SysRespCodeEnum.getResponseCodeEnum(errorCode, channelOrderDTO.getOrgCode());

				String resultCode = "3099";
				String resultMsg = "Other Error:其他异常";
				if (srce != null) {
					resultCode = srce.getRespCode();
					resultMsg = srce.getRespDescEn() + ":" + srce.getRespDEscCn();
				}
				channelOrderDTO.setErrorCode(resultCode);
				channelOrderDTO.setErrorMsg(resultMsg);
				paymentResult.setResponseCode(resultCode);
				paymentResult.setResponseDesc(resultMsg);
				String merchantOrderId = channelOrderDTO.getOrderId();

				if (ResponseCodeEnum.SUCCESS.getCode().equals(errorCode) && updateFlg) {

					if (null == payAmount) {

						logger.error("return payAmount must be not null: " + ExceptionCodeEnum.ILLEGAL_PARAMETER);

						// 这地方要加个报警、但是已经支付成功了
						Map<String, String> data = new HashMap<String, String>();

						data.put("first", "系统异常，返回的支付金额为空,txncore:ChannelServiceImpl");
						data.put("keyword1", "返回的支付金额为空");
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						data.put("keyword2", formatter.format(new Date()));
						data.put("remark", "请尽快检查！");

						this.sendAlertMsg(data);
						paymentResult.setResponseCode("111");
						paymentResult.setResponseDesc("channelOrder not exist!");
					} else if (payAmount < amount) {
						logger.error("channelOrderNo:" + channelOrderDTO.getChannelOrderNo() + "payAmount:" + payAmount
								+ ",amount:" + amount);
						logger.error("payAmount error: " + ExceptionCodeEnum.ORDERAMOUNT_ERROR);

						Map<String, String> data = new HashMap<String, String>();

						data.put("first", "系统异常: 支付订单金额小于渠道订单金额,,txncore:ChannelServiceImpl");
						data.put("keyword1", "返回的支付金额为空");
						SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
						data.put("keyword2", formatter.format(new Date()));
						data.put("remark", "请尽快检查！");

						this.sendAlertMsg(data);
						paymentResult.setResponseCode("111");
						paymentResult.setResponseDesc("channelOrder not exist!");
						// 这地方要加个报警、但是已经支付成功了
					} else {
						// 调用渠道记账
						channelService.doAccounting(channelOrderDTO, merchantOrderId);
					}
				}

				PaymentOrderDTO paymentOrderDTO = paymentOrderService
						.queryByPaymentOrderNo(channelOrderDTO.getPaymentOrderNo());

				if (paymentOrderDTO == null) {
					paymentResult.setResponseCode("105");
					paymentResult.setResponseDesc("paymentOrder not exist!");

				}

				PaymentOrderExpand paymentOrderExpand = paymentOrderExpandDAO.findById("queryPayOrderExpandByPayNO",
						paymentOrderDTO.getPaymentOrderNo());
				if (tradeOrderDTO == null) {
					paymentResult.setResponseCode("102");
					paymentResult.setResponseDesc("tradeOrder not exist!");

				}
				tradeOrderDTO.setRespCode(resultCode);
				tradeOrderDTO.setRespMsg(resultMsg);
				if (ResponseCodeEnum.SUCCESS.getCode().equals(errorCode)) {
					// 更新支付订单和网关订单状态
					paymentOrderDTO.setStatus(PaymentOrderStatusEnum.SUCCESS.getCode());
					paymentOrderDTO.setCompleteDate(new Date());
					paymentOrderDTO.setOrgCode(channelOrderDTO.getOrgCode());
					boolean updateFlag = paymentOrderService.updatePaymentOrderRnTx(paymentOrderDTO,
							PaymentOrderStatusEnum.INIT.getCode());
					if (!updateFlag) {
						logger.error("do not update paymentOrder," + paymentOrderDTO.getPaymentOrderNo());
					}
					// 更新网关订单
					tradeOrderDTO.setCompleteDate(new Date());
					tradeOrderDTO.setStatus(TradeOrderStatusEnum.SUCCESS.getCode());
					tradeOrderDTO.setRefundAmount(tradeOrderDTO.getOrderAmount());
					tradeOrderDTO.setPaymentWay("");

					boolean tradeUpFlg = tradeOrderService.updateTradeOrderRnTx(tradeOrderDTO,
							TradeOrderStatusEnum.PROCESSING.getCode());
					if (!tradeUpFlg) {
						logger.error("do not update trade order," + tradeOrderDTO.getTradeOrderNo());
					}
					paymentResult.setCompleteTime(
							DateUtil.formatDateTime(DateUtil.PATTERN1, tradeOrderDTO.getCompleteDate()));
					
					/**
					 * add by zhaoyang at 20160908
					 * 结算币种更改。
					 * 1，根据用户号、交易类型、交易币种、结算币种查找通过POSS配置的结算币种信息
					 * 2，如果无查询信息，则使用原有的结算币种；如果有查询信息，则使用查询出来的结算币种与tradeBaseDTO.getSettlementCurrencyCode()
					 * 比较，若相等则使用原有的结算币种，否则使用查询出来的结算币种。
					 * 3，更新交易订单，保存清算订单
					 *
					tradeBaseDTO = this.confirmTheSettlementCurrency(tradeBaseDTO, paymentOrderDTO.getCurrencyCode());
					TradeBase newTb = new TradeBase();
					newTb.setTradeBaseNo(tradeBaseDTO.getTradeBaseNo());
					newTb.setSettlementCurrencyCode(tradeBaseDTO.getSettlementCurrencyCode());
					tradeOrderService.updateTradeBase(newTb);
					*/
					String cardOrg = CardOrgUtil.getCardType(paymentOrderExpand.getCardNo());
					// 保存清算订单
					List<PartnerSettlementOrder> settlementOrders = OrderBuilder.buildSettlementOrder(
							paymentOrderDTO.getPaymentOrderNo(), tradeBaseDTO, tradeOrderDTO,
							tradeBaseDTO.getSettlementCurrencyCode(), cardOrg, enterpriseBaseService, channelService);
					partnerSettlementOrderService.createPartnerSettlementOrder(settlementOrders);
					updateTradeAmountCount(tradeOrderDTO, paymentOrderDTO.getPaymentOrderNo());
				} else {
					// 更新支付订单状态
					paymentOrderDTO.setStatus(PaymentOrderStatusEnum.FAIL.getCode());
					paymentOrderDTO.setCompleteDate(new Date());
					paymentOrderDTO.setOrgCode(channelOrderDTO.getOrgCode());
					boolean updateFlag = paymentOrderService.updatePaymentOrderRnTx(paymentOrderDTO,
							PaymentOrderStatusEnum.INIT.getCode());
					if (!updateFlag) {
						logger.error("do not update paymentOrder," + paymentOrderDTO.getPaymentOrderNo());
					}

					// 更新网关订单
					tradeOrderDTO.setCompleteDate(new Date());
					tradeOrderDTO.setStatus(TradeOrderStatusEnum.FAILED.getCode());
					boolean tradeUpFlg = tradeOrderService.updateTradeOrderRnTx(tradeOrderDTO,
							TradeOrderStatusEnum.PROCESSING.getCode());
					if (!tradeUpFlg) {
						logger.error("do not update trade order," + tradeOrderDTO.getTradeOrderNo());
					}

				}
				return paymentResult;
			}
		}
		return paymentResult;

	}
	
	/**
	 * 检查有没有匹配到的国家
	 * @param ipCtry
	 * @param list
	 * @return
	 */
	private static boolean checkedCtry(String ipCtry,List<PartnerChannelCountry> list){
		if(list!=null&&!list.isEmpty()){
			for(PartnerChannelCountry pcc:list){
				if(pcc.getCountryCode().contains(ipCtry)){
					return false;
				}
			}
		}
		return true;
	}
	
	/**
	 * 检查需要过滤的掉的渠道。
	 * @param list
	 * @param ipCtry
	 * @param pccMap
	 * @return
	 */
	public static void getChannelS(List<PaymentChannelItemDto> list,String ipCtry,
			Map<String,List<PartnerChannelCountry>> pccMap){
		Iterator<PaymentChannelItemDto> it = list.iterator();
		while(it.hasNext()){
			PaymentChannelItemDto item = it.next();
			String orgCode = item.getOrgCode();
			if(ipCtry!=null&&pccMap!=null&&!pccMap.isEmpty()){
				List<PartnerChannelCountry> list0 = pccMap.get(orgCode);
				if(checkedCtry(ipCtry,list0)){
					it.remove();
				}
			}
		}		
	}

	// add by: Bobby Guo
	// 根据路由到的渠道支付，重试到成功或没有渠道为止
	public PaymentResult payByChannels(PaymentInfo paymentInfo, TradeOrderDTO tradeOrderDTO, TradeBaseDTO tradeBaseDTO,
			String paymentWay) {
		tradeOrderDTO.setStatus(TradeOrderStatusEnum.PROCESSING.getCode());
		boolean updateResult = tradeOrderService.updateTradeOrderRnTx(tradeOrderDTO,
				TradeOrderStatusEnum.WAIT_PAY.getCode());
		if (!updateResult) {
			logger.info("set processing status to trade order is failed.");
		}
		PaymentResult paymentResult = new PaymentResult();
		EnterpriseBaseDto enterpriseBaseDto = enterpriseBaseService.queryEnterpriseBaseByMemberCode(Long.parseLong(paymentInfo.getPartnerId()));
		String merchantCode = String.valueOf(enterpriseBaseDto.getMerchantCode());
		boolean gcMember = merchantCode.startsWith("800");
		List<PaymentChannelItemDto> channels = routeChannels(paymentInfo, tradeOrderDTO);
		if (null == channels || channels.isEmpty() || removeZeroPriority(channels)) {
			logger.info("channel config error: " + ExceptionCodeEnum.CHANNEL_CONFING_FAIL);
			paymentResult.setResponseCode(ExceptionCodeEnum.CHANNEL_CONFING_FAIL.getCode());
			paymentResult.setResponseDesc(ExceptionCodeEnum.CHANNEL_CONFING_FAIL.getDescription());
			// add by sch 2016-04-20 设置网关订单状态为失败
			if (null == channels || channels.isEmpty()) {
				logger.info("channels = null or isEmpty");
			} else {
				logger.info("removeZeroPriority(channels)");
			}

			// note： 如果测试方法为删除渠道路由的话 ， 收银台接口 可以跑到这里，api接口在前面就档掉了
			tradeOrderDTO.setCompleteDate(new Date());
			tradeOrderDTO.setStatus(TradeOrderStatusEnum.FAILED.getCode());
			tradeOrderDTO.setRespCode(paymentResult.getResponseCode());
			tradeOrderDTO.setRespMsg("商户无可用渠道");
			// 下面这句要写么？
			// tradeOrderDTO.setPaymentWay(PaymentWayEnum.PAYMENT_LINK.getCode().equalsIgnoreCase(paymentInfo.getPaymentWay())?PaymentWayEnum.PAYMENT_LINK.getCode():paymentWay);
			boolean tradeUpFlg = tradeOrderService.updateTradeOrderRnTx(tradeOrderDTO,
					TradeOrderStatusEnum.PROCESSING.getCode());
			if (!tradeUpFlg) {
				logger.error("do not update trade order," + tradeOrderDTO.getTradeOrderNo());
			}
			// end by sch 2016-04-20

			return paymentResult;
		}
		logger.info("收单路由到的渠道是：" + tradeOrderDTO.getTradeOrderNo() + "," + Arrays.toString(channels.toArray()));
		ExpressTracking expressTracking = expressTrackingService
				.findByTradeOrderNo(tradeOrderDTO.getTradeOrderNo() + "");

		// ---------------------------------------------------------
		// 获取商户配置的汇率浮动值
		PartnerRateFloat rateFloat = channelService.getPartnerRateFloatService()
				.getPartnerRateFloat(paymentInfo.getPartnerId());
		if (rateFloat != null) {
			int start = Integer.valueOf(rateFloat.getStartPoint());
			int end = Integer.valueOf(rateFloat.getEndPoint());

			Random random = new Random();
			int s = random.nextInt(end - start + 1) + start;
			BigDecimal max0 = new BigDecimal(s + ".0");
			BigDecimal min0 = new BigDecimal(1000);

			BigDecimal t = max0.divide(min0);
			tradeOrderDTO.setFloatValue(t.toString());
			paymentInfo.setFloatValue(t.toString());
		}

		paymentInfo.setOrderAmount(tradeOrderDTO.getOrderAmount().toString());

		TransactionRate transRate = this.getTransactionRate(paymentInfo);
		if (transRate != null) {
			if (!StringUtil.isEmpty(paymentInfo.getFloatValue())) {
				BigDecimal floatValue = new BigDecimal(paymentInfo.getFloatValue());
				BigDecimal rateO = new BigDecimal(transRate.getExchangeRate());
				BigDecimal tmp = rateO.add(rateO.multiply(floatValue));
				tradeOrderDTO.setRate(tmp.toString());
			} else {
				tradeOrderDTO.setRate(transRate.getExchangeRate());
			}
			tradeOrderDTO.setOriginalRate(transRate.getExchangeRate());
		}
		// -----------------------------------------------------------------------
		String cardNo = paymentInfo.getCardHolderNumber();
		String cardBin = "";

		if (!StringUtil.isEmpty(cardNo)) {
			cardBin = cardNo.substring(0, 6);
		}

		CardBinInfo info = cardBinInfoService.getCardBinInfo(cardBin);
		String cardCountry = null;
		String cardCurrencyCode = paymentInfo.getDccCurrencyCode();

		if (info != null) {
			cardCountry = info.getCountryCode3();
			paymentInfo.setCardCountry(cardCountry);
			if (!StringUtil.isEmpty(info.getCurrencyCode()) && StringUtil.isEmpty(cardCurrencyCode)) {
				cardCurrencyCode = info.getCurrencyCode();
			}
		}
		//
		String currencyCode = paymentInfo.getCurrencyCode();
		String cardOrg = CardOrgUtil.getCardType(paymentInfo.getCardHolderNumber());
		String payType = paymentInfo.getPayType();
		String prdtCode = paymentInfo.getPrdtCode();

		
		
		Map<String,String> params = new HashMap<String, String>();
		params.put("memberCode",paymentInfo.getPartnerId());
		
		//根据会员号获取该商户配置的指定国家的通道
		Map<String,List<PartnerChannelCountry>> pccMap = channelClientService.queryPartnerCountryChannels(params);
		
		String ip = paymentInfo.getCustomerIP();
		logger.info("ip: " + ip);
		String ipCtry = null;
		if (!StringUtil.isEmpty(ip)) {// 如果IP不为空，根据IP地址找到该Ip地址所对应的国家。
			Map<String, Object> params_ = new HashMap<String, Object>();
			long ipLong = IPv4Util.ipToLong(ip);
			logger.info("ip: " + ip + ", ipLong: " + ipLong);
			params_.put("ip", ipLong);
			IpCountryInfo ipInfo = ipCountryInfoService.getCountryInfo(params_);
			if (ipInfo != null) {
				ipCtry = ipInfo.getCountryCode3();
				logger.info("ipCtry: "+ipCtry);
				if(!COUNTRY.contains(ipCtry))//如果获取的国家不在指定的国家范围内就顶为其他国家
					ipCtry="000";
			}
		}

		logger.info("ipCtry: " + ipCtry);
		logger.info("pccMap: " + pccMap);
		//重新过滤掉不需要的渠道
		this.getChannelS(channels, ipCtry, pccMap);
		Iterator<PaymentChannelItemDto> iterator = channels.iterator();
		boolean updateFailureTradeOrder = true;
		while (iterator.hasNext()) {
			paymentInfo.setPayType(payType);// add by peiyu.yang 这样是因为如果走DCC，就会
			PaymentChannelItemDto paymentChannelItemDto = iterator.next();
			if(gcMember && !"1".equals(paymentChannelItemDto.getGetGcCurrentFlag())){//
				logger.error("会员[" + paymentInfo.getPartnerId() + "]在通道下[" + paymentChannelItemDto.getName()+"]没有默认的2级商户号" );
				notifyChannel(null,0l,null,paymentInfo.getPartnerId(),paymentChannelItemDto.getId(),false,paymentChannelItemDto.getChannelConfigId(),"missSecond");
				continue;
			}
			String orgCode = paymentChannelItemDto.getOrgCode();
			logger.info("orgCode: "+orgCode);

			String channelPayType = paymentChannelItemDto.getTransType();
			ChannelCurrencyDTO currencyDTO = this.getChannelCurrency(paymentInfo.getPartnerId(),currencyCode, cardCurrencyCode, prdtCode, orgCode,
					payType);

			logger.info("ChannelCurrencyDTO: " + currencyDTO);
			// 如果找不到送渠道币种
			if (currencyDTO == null) {
				if (!iterator.hasNext()) {
					throw new BusinessException(
							"找不到送渠道币种, orgCode：" + orgCode + ",currencyCode:" + currencyCode + ",prdtCode:" + prdtCode
									+ ",payType: " + payType + ",cardCurrencyCode:" + cardCurrencyCode,
							ExceptionCodeEnum.OTHER_ERROR);
				} else {
					continue;
				}
			}

			TransRateMarkup markup = this.getMarkup(paymentInfo, currencyDTO);
			// 如果是DCC支付类型，如果找不到markup就走EDC
			if ("DCC".equals(currencyDTO.getPayType()) && markup == null) {
				currencyDTO = this.getChannelCurrency(paymentInfo.getPartnerId(),currencyCode, cardCurrencyCode, "EDC", orgCode, "EDC");
				if (currencyDTO == null) {
					if (!iterator.hasNext()) {
						throw new BusinessException(
								"找不到送渠道币种, orgCode：" + orgCode + ",currencyCode:" + currencyCode + ",prdtCode:"
										+ prdtCode + ",payType: " + payType + ",cardCurrencyCode:" + cardCurrencyCode,
								ExceptionCodeEnum.OTHER_ERROR);
					} else {
						continue;
					}
				}
				markup = this.getMarkup(paymentInfo, currencyDTO);
			}

			logger.info("currencyDTO: " + currencyDTO);

			if (markup != null) {
				paymentInfo.setMarkup(markup.getMarkup());
			}
			// 重新获取支付类型，DCC或EDC
			// 重新获取支付类型，保存到支付订单里
			paymentInfo.setPayType(currencyDTO.getPayType());
			paymentInfo.setChannelCurrencyCode(currencyDTO.getChannelCurrencyCode());
			paymentInfo.setChannelPayType(channelPayType);

			// create payment order
			//TODO
			PaymentOrderDTO paymentOrderDTO = OrderBuilder.buildPaymentOrder(paymentInfo, tradeBaseDTO, tradeOrderDTO,
					tradeBaseDTO.getSettlementCurrencyCode(), memberQueryService, accountQueryService);
			Long paymentOrderNo = paymentOrderService.savePaymentOrderRnTx(paymentOrderDTO);
			paymentOrderDTO.setPaymentOrderNo(paymentOrderNo);
			paymentInfo.setPrdtCode(currencyDTO.getPrdtCode());

			if (StringUtil.isEmpty(paymentInfo.getPayType())) {
				paymentInfo.setPayType("ALL");
			}

			paymentInfo.setPaymentOrderNo(paymentOrderDTO.getPaymentOrderNo().toString());
			paymentInfo.setOrderAmount(paymentOrderDTO.getPaymentAmount().toString());
			paymentInfo.setTradeType(tradeOrderDTO.getTradeType());
			OrderBuilder.savePaymentOrderExpand(paymentOrderNo, paymentInfo, paymentOrderExpandDAO);

			paymentInfo.setPaymentType(PaymentTypeEnum.PAYMENT.getCode());
			paymentInfo.setTransType(TransTypeEnum.EDC.getCode());
			paymentInfo.setCurrencyCode(tradeOrderDTO.getCurrencyCode());
			paymentInfo.setOrderId(tradeOrderDTO.getOrderId());

			paymentInfo.setBillName(expressTracking.getBillName());
			paymentInfo.setBillEmail(expressTracking.getBillEmail());
			paymentInfo.setBillPostalCode(expressTracking.getBillPostalCode());
			paymentInfo.setCardOrg(cardOrg);
			// 添加mcc，商户账单名 by tom 2016年5月26日21:30:05
			paymentInfo.setMerchantBillName(paymentChannelItemDto.getMerchantBillName());
			paymentInfo.setMcc(paymentChannelItemDto.getMcc());

			ChannelResponseDto channelResult = channelService.channelPay(paymentOrderDTO, paymentInfo,
					paymentChannelItemDto);

			paymentResult.setMerchantBillName(paymentChannelItemDto.getMerchantBillName());
			paymentResult.setOrgMerchantCode(paymentChannelItemDto.getOrgMerchantCode());
			iterator.remove();
			// 渠道返回原因码 ， add by Jiangbo.Peng
			String channelRespCode = channelResult.getChannelRespCode();
			String errorCode = channelResult.getResponseCode();
			String errorMsg = channelResult.getResponseDesc();
			String merchantBillName = channelResult.getMerchantBillName();

			tradeOrderDTO.setRespCode(errorCode);
			tradeOrderDTO.setRespMsg(errorMsg);
			tradeOrderDTO.setMerchantBillName(merchantBillName);
			tradeOrderDTO.setUpdateDate(new Date());
			paymentOrderDTO.setUpdateDate(new Date());
			paymentInfo.setCurrencyCode(currencyCode);

			paymentResult.setCurrencyCode(channelResult.getPayCurrencyCode());
			paymentResult.setMerchantBillName(merchantBillName);
			// add byJiangbo.Peng,供失败订单根据渠道返回码捕获异常卡使用
			paymentResult.setChannelRespCode(channelRespCode);
			paymentResult.setOrgCode(channelResult.getOrgCode());
			paymentResult.setResponseCode(errorCode);
			paymentResult.setResponseDesc(errorMsg);
			paymentResult.setChannelOrderNo(channelResult.getChannelOrderNo());
			paymentResult.setPayAmount(channelResult.getPayAmount());
			paymentResult.setPaymentOrderNo(paymentOrderNo);
			paymentResult.setMerchantBillName(paymentChannelItemDto.getMerchantBillName());
			long payAmounttran = channelResult.getPayAmount() == null ? 0l: channelResult.getPayAmount();
			notifyChannel(paymentChannelItemDto.getMemberCurrentConnectId(),payAmounttran,channelResult.getPayCurrencyCode(),paymentInfo.getPartnerId(),paymentChannelItemDto.getId(),ResponseCodeEnum.SUCCESS.getCode().equals(errorCode),paymentChannelItemDto.getChannelConfigId(),null);
			if (ResponseCodeEnum.SUCCESS.getCode().equals(errorCode)) {
				// 有成功的订单就跳出，不再进行交易了
				// 更新支付订单和网关订单状态
				updateFailureTradeOrder = false;//更新网管订单为成功
				
				/**信用卡支付
    			 * add by zhaoyang at 201609011
    			 * 结算币种更改。
    			 * 1，根据用户号、交易类型、交易币种、结算币种查找通过POSS配置的结算币种信息
    			 * 2，如果无查询信息，则使用原有的结算币种；如果有查询信息，则使用查询出来的结算币种与tradeBaseDTO.getSettlementCurrencyCode()
    			 * 比较，若相等则使用原有的结算币种，否则使用查询出来的结算币种。
    			 * 3，更新交易基础订单(结算币种)，更新支付订单（收款人账户类型）
    			 */
				logger.info("信用卡支付结算币种确认--paymentOrderNo is {},memberCode is {},payType is {},tradeCurrencyCode is {},payCurrencyCode is {}", new Object[]{paymentOrderDTO.getPaymentOrderNo(),tradeBaseDTO.getPartnerId(),tradeBaseDTO.getPayType(),tradeBaseDTO.getCurrencyCode(),channelResult.getPayCurrencyCode()});
    			tradeBaseDTO = this.confirmTheSettlementCurrency(tradeBaseDTO, channelResult.getPayCurrencyCode());
    			logger.info("信用卡支付结算币种确认--paymentOrderNo is {},SettlementCurrencyCode is {}", paymentOrderDTO.getPaymentOrderNo(),tradeBaseDTO.getSettlementCurrencyCode());
    			TradeBase newTb = new TradeBase();
    			newTb.setTradeBaseNo(tradeBaseDTO.getTradeBaseNo());
    			newTb.setSettlementCurrencyCode(tradeBaseDTO.getSettlementCurrencyCode());
    			tradeOrderService.updateTradeBase(newTb);
    			paymentInfo.setSettlementCurrencyCode(tradeBaseDTO.getSettlementCurrencyCode());
    			paymentOrderDTO.setPayeeType(this.getPayeeTypeBySettlementCurrency(tradeBaseDTO.getSettlementCurrencyCode(), tradeOrderDTO.getPartnerId()));
				paymentOrderDTO.setStatus(PaymentOrderStatusEnum.SUCCESS.getCode());
				paymentOrderDTO.setCompleteDate(new Date());
				paymentOrderDTO.setOrgCode(channelResult.getOrgCode());
				boolean updateFlag = paymentOrderService.updatePaymentOrderRnTx(paymentOrderDTO,
						PaymentOrderStatusEnum.INIT.getCode());
				if (!updateFlag) {
					logger.error("do not update paymentOrder," + paymentOrderNo);
				}
				// 保存清算订单
				List<PartnerSettlementOrder> settlementOrders = OrderBuilder.buildSettlementOrder(paymentOrderNo,
						tradeBaseDTO, tradeOrderDTO, tradeBaseDTO.getSettlementCurrencyCode(), cardOrg,
						enterpriseBaseService, channelService);
				partnerSettlementOrderService.createPartnerSettlementOrder(settlementOrders);
				updateTradeAmountCount(tradeOrderDTO, paymentOrderNo);
				// 记录金额和交易笔数 updateDate 2016年6月15日20:27:01 delin.dong
				try {
					updateBusTradeCount(paymentInfo, tradeOrderDTO);
				} catch (Exception e) {
					e.printStackTrace();
				}
				break;
			} else {
				// 更新支付订单状态
				paymentOrderDTO.setStatus(PaymentOrderStatusEnum.FAIL.getCode());
				paymentOrderDTO.setCompleteDate(new Date());
				paymentOrderDTO.setOrgCode(channelResult.getOrgCode());
				boolean updateFlag = paymentOrderService.updatePaymentOrderRnTx(paymentOrderDTO,
						PaymentOrderStatusEnum.INIT.getCode());
				if (!updateFlag) {
					logger.error("do not update paymentOrder," + paymentOrderNo);
				}
				List<String> notRetryCodes = getChangeChannelCodes(paymentChannelItemDto.getOrgCode());
				if (channels.isEmpty()
						|| (notRetryCodes != null && notRetryCodes.contains(channelResult.getChannelRespCode()))) {
					if (!channels.isEmpty()) {
						break;
					}
				}
			}
		}
		finalUpdateTradeOrderResult(tradeOrderDTO,paymentWay,paymentInfo.getPaymentWay(),updateFailureTradeOrder);
		try { // delin.dong 2016年5月23日 16:33:41
			if (ResponseCodeEnum.SUCCESS.getCode().equals(paymentResult.getResponseCode())) {
				// 发送mq消息到记录单个MID，卡组织的总金额
				paymentResult.setCardOrg(CardOrgUtil.getCardType(paymentInfo.getCardHolderNumber()));
				paymentResult.setPartnerId(paymentInfo.getPartnerId());
				ChannelMidAmountNotifyRequest notifyRequest = new ChannelMidAmountNotifyRequest();
				Map<String, String> notifyMap = MapUtil.bean2map(paymentResult);
				notifyRequest.setData(notifyMap);
				notifyRequest.setMerchantCode(paymentResult.getPartnerId());
				jmsSender.send("notify.forpay", notifyRequest);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}

		if(StringUtil.isEmpty(paymentResult.getResponseCode())&&
				StringUtil.isEmpty(paymentResult.getResponseDesc())){
			logger.info("没有匹配到支付人国家与渠道的配置");
			paymentResult.setResponseCode("3099");
			paymentResult.setResponseDesc("Other errors:其他异常");
		}
		return paymentResult;
	}

	private void finalUpdateTradeOrderResult(TradeOrderDTO tradeOrderDTO, String paymentWay, String paymentInfoSubPaymentWay, boolean updateFailureTradeOrder){
		tradeOrderDTO.setCompleteDate(new Date());
		tradeOrderDTO.setStatus(updateFailureTradeOrder?TradeOrderStatusEnum.FAILED.getCode(): TradeOrderStatusEnum.SUCCESS.getCode());
		if(!updateFailureTradeOrder)
			tradeOrderDTO.setRefundAmount(tradeOrderDTO.getOrderAmount());
		tradeOrderDTO.setPaymentWay(
				PaymentWayEnum.PAYMENT_LINK.getCode().equalsIgnoreCase(paymentInfoSubPaymentWay)
						? PaymentWayEnum.PAYMENT_LINK.getCode() : paymentWay);
		boolean tradeUpFlg = tradeOrderService.updateTradeOrderRnTx(tradeOrderDTO,
				TradeOrderStatusEnum.PROCESSING.getCode());
		logger.info(tradeOrderDTO.getTradeOrderNo() + ": now is " + updateFailureTradeOrder + " status be care");
		if (!tradeUpFlg) {
			logger.error("do not update trade order," + tradeOrderDTO.getTradeOrderNo());
		}
	}

	/**
	 * 获取汇率的markup
	 * 
	 * @param
	 * @return
	 */
	private TransRateMarkup getMarkup(PaymentInfo paymentInfo, ChannelCurrencyDTO currencyDTO) {
		TransRateMarkup markup = null;
		Map<String, Object> params = new HashMap<String, Object>();

		String payType = currencyDTO.getPayType();
		String memberCode = paymentInfo.getPartnerId();

		if ("EDC".equals(payType)) {// 获取支付方式为EDC方式的
			params.put("status", "1");
			params.put("memberCode", paymentInfo.getPartnerId());
			params.put("point", getTime());
			if (!StringUtil.isEmpty(paymentInfo.getCardCountry())) {
				params.put("cardCurrencyCode", paymentInfo.getCurrencyCode());
			}
			params.put("cardOrg", CardOrgUtil.getCardType(paymentInfo.getCardHolderNumber()));
			params.put("currency", paymentInfo.getCurrencyCode());
			params.put("targetCurrency", currencyDTO.getChannelCurrencyCode());

			TransactionBaseRate baseRate_ = channelService.getCurrencyRateService()
					.findTransactionBaseRate(paymentInfo.getCurrencyCode(), "USD", "1", null);
			String orderAmount = paymentInfo.getOrderAmount();
			if (baseRate_ != null) {
				BigDecimal rate0 = new BigDecimal(baseRate_.getExchangeRate());
				BigDecimal amount = new BigDecimal(orderAmount).multiply(rate0).multiply(new BigDecimal("0.001"));
				params.put("leastTransAmount", amount);
			}
			markup = transRateMarkupService.getTransRateMarkup(params);
			logger.info("EDC-markup: " + markup);
		} else if ("DCC".equals(payType)) {
			Map<String, Object> param_ = new HashMap<String, Object>();
			param_.put("partnerId", memberCode);
			param_.put("currencyCode", currencyDTO.getChannelCurrencyCode());

			PartnerDCCConfig dccConfig = dccService.getDccConfig(param_);
			if (dccConfig != null) {
				String markupStr = dccConfig.getMarkUp();
				markup = new TransRateMarkup();
				markup.setMarkup(markupStr);
			}

			logger.info("DCC-markup: " + markup);
		}

		return markup;
	}

	/**
	 * 获取送渠道币种
	 * 
	 * @return
	 */
	private ChannelCurrencyDTO getChannelCurrency(String partnerId,String currencyCode, String cardCurrencyCode, String prdtCode,
			String orgCode, String payType) {
		logger.info("method:getChannelCurrency,currencyCode:" + currencyCode + ",cardCurrencyCode: " + cardCurrencyCode
				+ ",prdtCode:" + prdtCode + ",orgCode: " + orgCode + ",payType: " + payType + ",partnerId:" + partnerId);
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("orgCode", orgCode);
		params.put("payType", payType);
		params.put("currencyCode", currencyCode);
		params.put("cardCurrencyCode", cardCurrencyCode);
		params.put("partnerId", Long.parseLong(partnerId));
		return channelCurrencyDAO.findOneMatch(params);
	}
	/**
	 * 获取送渠道币种
	 * 
	 * @return
	 */
	// 走3D渠道 addby liu 2016-04-27
	public PaymentResult payBy3DChannels(PaymentInfo paymentInfo, TradeOrderDTO tradeOrderDTO,
			TradeBaseDTO tradeBaseDTO, String paymentWay) {
		tradeOrderDTO.setStatus(TradeOrderStatusEnum.PROCESSING.getCode());
		boolean updateResult = tradeOrderService.updateTradeOrderRnTx(tradeOrderDTO,
				TradeOrderStatusEnum.WAIT_PAY.getCode());
		if (!updateResult) {
			logger.info("set processing status to trade order is failed.");
		}
		PaymentResult paymentResult = new PaymentResult();
		List<PaymentChannelItemDto> channels = routeChannels(paymentInfo, tradeOrderDTO);
		if (null == channels || channels.isEmpty() || removeZeroPriority(channels)) {
			logger.info("channel config error: " + ExceptionCodeEnum.CHANNEL_CONFING_FAIL);
			paymentResult.setResponseCode(ExceptionCodeEnum.CHANNEL_CONFING_FAIL.getCode());
			paymentResult.setResponseDesc(ExceptionCodeEnum.CHANNEL_CONFING_FAIL.getDescription());
			// add by sch 2016-04-20 设置网关订单状态为失败
			if (null == channels || channels.isEmpty()) {
				logger.info("channels = null or isEmpty");
			} else {
				logger.info("removeZeroPriority(channels)");
			}
			// note： 如果测试方法为删除渠道路由的话 ， 收银台接口 可以跑到这里，api接口在前面就档掉了
			tradeOrderDTO.setCompleteDate(new Date());
			tradeOrderDTO.setStatus(TradeOrderStatusEnum.FAILED.getCode());
			tradeOrderDTO.setRespCode(paymentResult.getResponseCode());
			tradeOrderDTO.setRespMsg("商户无可用渠道");
			// 下面这句要写么？
			// tradeOrderDTO.setPaymentWay(PaymentWayEnum.PAYMENT_LINK.getCode().equalsIgnoreCase(paymentInfo.getPaymentWay())?PaymentWayEnum.PAYMENT_LINK.getCode():paymentWay);
			boolean tradeUpFlg = tradeOrderService.updateTradeOrderRnTx(tradeOrderDTO,
					TradeOrderStatusEnum.PROCESSING.getCode());
			if (!tradeUpFlg) {
				logger.error("do not update trade order," + tradeOrderDTO.getTradeOrderNo());
			}
			// end by sch 2016-04-20
			return paymentResult;
		}

		logger.info("收单路由到的渠道是：" + tradeOrderDTO.getTradeOrderNo() + "," + Arrays.toString(channels.toArray()));
		ExpressTracking expressTracking = expressTrackingService
				.findByTradeOrderNo(tradeOrderDTO.getTradeOrderNo() + "");

		// ---------------------------------------------------------
		// 获取商户配置的汇率浮动值
		PartnerRateFloat rateFloat = channelService.getPartnerRateFloatService()
				.getPartnerRateFloat(paymentInfo.getPartnerId());
		if (rateFloat != null) {
			int start = Integer.valueOf(rateFloat.getStartPoint());
			int end = Integer.valueOf(rateFloat.getEndPoint());

			Random random = new Random();
			int s = random.nextInt(end - start + 1) + start;
			BigDecimal max0 = new BigDecimal(s + ".0");
			BigDecimal min0 = new BigDecimal(1000);

			BigDecimal t = max0.divide(min0);
			tradeOrderDTO.setFloatValue(t.toString());
			paymentInfo.setFloatValue(t.toString());
		}
		/* 赵阳 20160911
		TransactionRate transRate = this.getTransactionRate(paymentInfo);

		if (transRate != null) {
			if (!StringUtil.isEmpty(paymentInfo.getFloatValue())) {
				BigDecimal floatValue = new BigDecimal(paymentInfo.getFloatValue());
				BigDecimal rateO = new BigDecimal(transRate.getExchangeRate());
				BigDecimal tmp = rateO.add(rateO.multiply(floatValue));
				tradeOrderDTO.setRate(tmp.toString());
			} else {
				tradeOrderDTO.setRate(transRate.getExchangeRate());
			}
			tradeOrderDTO.setOriginalRate(transRate.getExchangeRate());
		}*/

		// -----------------------------------------------------------------------

		String cardOrg = CardOrgUtil.getCardType(paymentInfo.getCardHolderNumber());
		PaymentChannelItemDto paymentChannelItemDto = channels.get(0);

		// 送渠道币种配置---------------------------------------------------
		String prdtCode = paymentInfo.getPrdtCode();
		String currencyCode = paymentInfo.getCurrencyCode();
		String payType = paymentInfo.getPayType();

		if (StringUtil.isEmpty(payType)) {
			payType = "EDC";
		}

		String orgCode = paymentChannelItemDto.getOrgCode();

		ChannelCurrencyDTO currencyDTO = this.getChannelCurrency(paymentInfo.getPartnerId(),currencyCode, null, prdtCode, orgCode, payType);

		logger.info("ChannelCurrencyDTO: " + currencyDTO);
		// 如果找不到送渠道币种
		if (currencyDTO == null) {
			throw new BusinessException("找不到送渠道币种, orgCode：" + orgCode + ",currencyCode:" + currencyCode + ",prdtCode:"
					+ prdtCode + ",payType: " + payType + ",cardCurrencyCode:" + null, ExceptionCodeEnum.OTHER_ERROR);
		}

		TransRateMarkup markup = this.getMarkup(paymentInfo, currencyDTO);

		logger.info("currencyDTO: " + currencyDTO);

		if (markup != null) {
			paymentInfo.setMarkup(markup.getMarkup());
		}
		// ----------------------------

		// create payment order
		/**3D渠道支付
		 * add by zhaoyang at 201609011
		 * 结算币种更改。
		 * 1，根据用户号、交易类型、交易币种、结算币种查找通过POSS配置的结算币种信息
		 * 2，如果无查询信息，则使用原有的结算币种；如果有查询信息，则使用查询出来的结算币种与tradeBaseDTO.getSettlementCurrencyCode()
		 * 比较，若相等则使用原有的结算币种，否则使用查询出来的结算币种。
		 * 3，更新交易基础订单(结算币种)
		 * 4,根据结算币种获取当前
		 * 5,更新支付订单（收款人账户类型）
		 */
		logger.info("3D支付结算币种确认--tradeOrderNo is {},memberCode is {},payType is {},tradeCurrencyCode is {},payCurrencyCode is {}", new Object[]{tradeOrderDTO.getTradeOrderNo(),tradeBaseDTO.getPartnerId(),tradeBaseDTO.getPayType(),tradeBaseDTO.getCurrencyCode(),currencyDTO.getCardCurrencyCode()});
		tradeBaseDTO = this.confirmTheSettlementCurrency(tradeBaseDTO, currencyDTO.getChannelCurrencyCode());
		logger.info("3D支付结算币种确认--tradeOrderNo is {},SettlementCurrencyCode is {}", tradeOrderDTO.getTradeOrderNo(),tradeBaseDTO.getSettlementCurrencyCode());
		TradeBase newTb = new TradeBase();
		newTb.setTradeBaseNo(tradeBaseDTO.getTradeBaseNo());
		newTb.setSettlementCurrencyCode(tradeBaseDTO.getSettlementCurrencyCode());
		tradeOrderService.updateTradeBase(newTb);
		paymentInfo.setSettlementCurrencyCode(tradeBaseDTO.getSettlementCurrencyCode());
		
		TransactionRate transRate = this.getTransactionRate(paymentInfo);
		if (transRate != null) {
			if (!StringUtil.isEmpty(paymentInfo.getFloatValue())) {
				BigDecimal floatValue = new BigDecimal(paymentInfo.getFloatValue());
				BigDecimal rateO = new BigDecimal(transRate.getExchangeRate());
				BigDecimal tmp = rateO.add(rateO.multiply(floatValue));
				tradeOrderDTO.setRate(tmp.toString());
			} else {
				tradeOrderDTO.setRate(transRate.getExchangeRate());
			}
			tradeOrderDTO.setOriginalRate(transRate.getExchangeRate());
		}
		PaymentOrderDTO paymentOrderDTO = OrderBuilder.buildPaymentOrder(paymentInfo, tradeBaseDTO, tradeOrderDTO,
				tradeBaseDTO.getSettlementCurrencyCode(), memberQueryService, accountQueryService);
		Long paymentOrderNo = paymentOrderService.savePaymentOrderRnTx(paymentOrderDTO);
		paymentOrderDTO.setPaymentOrderNo(paymentOrderNo);
		paymentOrderDTO.setPayeeType(this.getPayeeTypeBySettlementCurrency(tradeBaseDTO.getSettlementCurrencyCode(), tradeOrderDTO.getPartnerId()));
		if (StringUtil.isEmpty(paymentInfo.getPayType())) {
			paymentInfo.setPayType("ALL");
		}

		paymentInfo.setPaymentOrderNo(paymentOrderDTO.getPaymentOrderNo().toString());
		paymentInfo.setOrderAmount(paymentOrderDTO.getPaymentAmount().toString());
		paymentInfo.setTradeType(tradeOrderDTO.getTradeType());
		OrderBuilder.savePaymentOrderExpand(paymentOrderNo, paymentInfo, paymentOrderExpandDAO);

		paymentInfo.setPaymentType(PaymentTypeEnum.PAYMENT.getCode());
		paymentInfo.setTransType(TransTypeEnum.EDC.getCode());
		paymentInfo.setCurrencyCode(tradeOrderDTO.getCurrencyCode());
		paymentInfo.setOrderId(tradeOrderDTO.getOrderId());
		paymentInfo.setChannelCurrencyCode(currencyDTO.getChannelCurrencyCode());
		paymentInfo.setBillName(expressTracking.getBillName());
		paymentInfo.setBillEmail(expressTracking.getBillEmail());
		paymentInfo.setBillPostalCode(expressTracking.getBillPostalCode());
		paymentInfo.setCardOrg(cardOrg);
		//
		paymentInfo = channelService.channel3DPay(paymentOrderDTO, paymentInfo, paymentChannelItemDto);
		// 验证参数
		if (StringUtil.isEmpty(paymentInfo.getOrgMerchantCode())) {
			paymentResult.setResponseCode(ResponseCodeEnum.INVALID_PARAMETER.getCode());
			paymentResult.setResponseDesc("商户号不能为空");

		} else if (StringUtil.isEmpty(paymentInfo.getAccessCode())) {
			paymentResult.setResponseCode(ResponseCodeEnum.INVALID_PARAMETER.getCode());
			paymentResult.setResponseDesc("授权码不能为空");

		} else if (StringUtil.isEmpty(paymentInfo.getCurrencyCode())) {
			paymentResult.setResponseCode(ResponseCodeEnum.INVALID_PARAMETER.getCode());
			paymentResult.setResponseDesc("货币代码不能为空");

		} else if (StringUtil.isEmpty(paymentInfo.getOrderAmount())) {
			paymentResult.setResponseCode(ResponseCodeEnum.INVALID_PARAMETER.getCode());
			paymentResult.setResponseDesc("交易金额不能为空");

		} else if (StringUtil.isEmpty(paymentInfo.getChannelOrderNo())) {
			paymentResult.setResponseCode(ResponseCodeEnum.INVALID_PARAMETER.getCode());
			paymentResult.setResponseDesc("订单号不能为空");

		} else if (StringUtil.isEmpty(paymentInfo.getOrgKey())) {
			paymentResult.setResponseCode(ResponseCodeEnum.INVALID_PARAMETER.getCode());
			paymentResult.setResponseDesc("机构密钥不能为空");

		}
		logger.info("channelOrderNo:" + paymentInfo.getChannelOrderNo());

		// todo
		paymentResult.setAccessCode(paymentInfo.getAccessCode());
		paymentResult.setSecurityCode(paymentInfo.getSecurityCode());
		paymentResult.setCardExp(paymentInfo.getCardExpirationYear() + paymentInfo.getCardExpirationMonth());
		paymentResult.setCardHolderNumber(paymentInfo.getCardHolderNumber());
		paymentResult.setReturnUrl(paymentInfo.getReturnUrl());
		paymentResult.setCardOrg(paymentInfo.getCardOrg());
		paymentResult.setLanguage(null == paymentInfo.getLanguage() ? "en" : paymentInfo.getLanguage());
		paymentResult.setOrgMerchantCode(paymentInfo.getOrgMerchantCode());
		paymentResult.setOrderAmount(paymentInfo.getOrderAmount());
		paymentResult.setOrgKey(paymentInfo.getOrgKey());
		paymentResult.setPreServerUrl(paymentInfo.getPreServerUrl());
		paymentResult.setVersion(paymentInfo.getVersion());
		paymentResult.setChannelOrderNo(Long.valueOf(paymentInfo.getChannelOrderNo()));
		paymentResult.setCurrencyCode(paymentInfo.getCurrencyCode());

		return paymentResult;
	}

	/**
	 * 获取交易汇率(基本汇率加markup方式)
	 * 
	 * @param paymentInfo
	 * @return
	 */
	protected TransactionRate getTransactionRate(PaymentInfo paymentInfo) {
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("currency", paymentInfo.getCurrencyCode());
		param.put("targetCurrency", paymentInfo.getSettlementCurrencyCode());
		param.put("status", "1");
		param.put("memberCode", paymentInfo.getPartnerId());
		param.put("cardOrg", CardOrgUtil.getCardType(paymentInfo.getCardHolderNumber()));
		param.put("ltaCurrencyCode", "USD");
		param.put("point", getTime());

		TransactionBaseRate baseRate = channelService.getCurrencyRateService().findTransactionBaseRate(
				paymentInfo.getCurrencyCode(), paymentInfo.getSettlementCurrencyCode(), "1", null);
		if (baseRate == null)
			return null;
		else {
			TransactionBaseRate baseRate_ = channelService.getCurrencyRateService()
					.findTransactionBaseRate(paymentInfo.getCurrencyCode(), "USD", "1", null);
			String orderAmount = paymentInfo.getOrderAmount();
			if (baseRate_ != null) {
				BigDecimal rate0 = new BigDecimal(baseRate_.getExchangeRate());
				BigDecimal amount = new BigDecimal(orderAmount).multiply(rate0).multiply(new BigDecimal("0.01"));
				param.put("leastTransAmount", amount);
			}
			TransRateMarkup markup = transRateMarkupService.getTransRateMarkup(param);
			TransactionRate rate = new TransactionRate();
			if (markup == null) {
				rate.setExchangeRate(baseRate.getExchangeRate());
			} else {
				BigDecimal rateTmp = new BigDecimal(baseRate.getExchangeRate());
				BigDecimal rateB = rateTmp
						.add(rateTmp.multiply(new BigDecimal(markup.getMarkup()).multiply(new BigDecimal("0.01"))));
				rate.setExchangeRate(rateB.toString());
			}
			return rate;
		}
	}

	public static double getTime() {
		Calendar c = Calendar.getInstance();
		int hour = c.get(Calendar.HOUR_OF_DAY);
		int min = c.get(Calendar.MINUTE);
		double s = min / 100.0;
		double rst = hour + s;

		return rst;
	}

	// add by:Bobby Guo
	// 路由到所有的可用渠道
	private List<PaymentChannelItemDto> routeChannels(PaymentInfo paymentInfo, TradeOrderDTO tradeOrderDTO) {
		MemberBaseInfoBO memberBaseInfoBO = memberQueryService
				.queryMemberBaseInfoByMemberCode(tradeOrderDTO.getPartnerId());
		String partnerId = paymentInfo.getPartnerId();
		String paymentType = PaymentTypeEnum.PAYMENT.getCode();
		String currencyCode = null;
		if (DCCEnum.CUSTOM_STANDARD.getCode().equals(paymentInfo.getPrdtCode())
				|| DCCEnum.CUSTOM_FORCED.getCode().equals(paymentInfo.getPrdtCode())
				|| DCCEnum.CUSTOM_HIDDEN.getCode().equals(paymentInfo.getPrdtCode())
				|| DCCEnum.PARTNER_DCC_PRDCT.getCode().equals(paymentInfo.getPrdtCode())) {
			currencyCode = paymentInfo.getDccCurrencyCode();
		} else {
			currencyCode = paymentInfo.getCurrencyCode();
		}
		if (CredoraxCurrencyCodeEnum.isNeedChange(currencyCode)) {
			currencyCode = CurrencyCodeEnum.USD.getCode();
		}

		String vom = "";
		String cardNumber = paymentInfo.getCardHolderNumber();
		vom = CardOrgUtil.getCardType(cardNumber);
		if (StringUtil.isEmpty(paymentInfo.getPayType())) {
			paymentInfo.setPayType("ALL");
		}
		List<PaymentChannelItemDto> channels = channelClientService.queryChannels(partnerId,
				String.valueOf(memberBaseInfoBO.getMemberType()), paymentType, paymentInfo.getPayType(), currencyCode,
				paymentInfo.getSiteId(), vom, paymentInfo.getPrdtCode());
		// addby liu 添加3d模型判断
		if (channels != null) {
			if ("3000".equals(tradeOrderDTO.getTradeType())) {
				for (int i = 0; i < channels.size(); i++) {

					if (!"E".equals(((PaymentChannelItemDto) channels.get(i)).getPattern())) {
						channels.remove(i);
						i--;
					}
					;
				}
			} else {
				for (int i = 0; i < channels.size(); i++) {

					if ("E".equals(((PaymentChannelItemDto) channels.get(i)).getPattern())) {
						channels.remove(i);
						i--;
					}
					;
				}
			}
		}

		return channels;
	}

	// add by:Bobby Guo
	// 获取每个渠道配置不需要重试的返回码
	public static List<String> getChangeChannelCodes(String channelCode) {
		Properties properties = new Properties();
		Resource resource = new ClassPathResource("properties/config.properties");
		try {
			properties.load(resource.getInputStream());
			String changeChannelCodes = properties.getProperty(channelCode + ".ChangeChannelCodes");
			if (StringUtils.hasText(changeChannelCodes)) {
				return Arrays.asList(changeChannelCodes.split(","));
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}

	// add by:Bobby Guo
	// 过滤掉优先级为0的渠道
	public static boolean removeZeroPriority(List<PaymentChannelItemDto> channels) {
		Iterator<PaymentChannelItemDto> iterator = channels.iterator();
		while (iterator.hasNext()) {
			PaymentChannelItemDto paymentChannelItemDto = iterator.next();
			if ("0".equals(paymentChannelItemDto.getChannelPriority())) {
				iterator.remove();
			}
		}
		return channels.isEmpty();
	}

	/**
	 * 保存交易扩展信息
	 */
	public void saveExtendsInfo(PaymentInfo paymentInfo, TradeOrderDTO tradeOrderDTO) {
		TradeExtendsDTO dto = OrderBuilder.buildTradeExendsDTO(paymentInfo,
				Long.valueOf(tradeOrderDTO.getTradeOrderNo()), "");
		tradeExtendsService.createTradeExtendsRnTx(dto);
		// 保存运单
		ExpressTracking expressTracking = OrderBuilder.buildExpressTracking(paymentInfo, tradeOrderDTO);
		expressTrackingService.createExpressTracking(expressTracking);
		TradeData tradeData = OrderBuilder.builTradeData(paymentInfo, tradeOrderDTO, dto, cardBinInfoService);
		tradeData.setToken(paymentInfo.getToken());
		tradeDataService.save(tradeData);
	}

	private List<PaymentChannelItemDto> getChannel(PaymentInfo paymentInfo, TradeOrderDTO tradeOrderDTO) {
		MemberBaseInfoBO memberBaseInfoBO = memberQueryService
				.queryMemberBaseInfoByMemberCode(tradeOrderDTO.getPartnerId());
		String partnerId = paymentInfo.getPartnerId();
		String paymentType = PaymentTypeEnum.PAYMENT.getCode();
		String currencyCode = null;
		if (DCCEnum.CUSTOM_STANDARD.getCode().equals(paymentInfo.getPrdtCode())
				|| DCCEnum.CUSTOM_FORCED.getCode().equals(paymentInfo.getPrdtCode())
				|| DCCEnum.CUSTOM_HIDDEN.getCode().equals(paymentInfo.getPrdtCode())
				|| DCCEnum.PARTNER_DCC_PRDCT.getCode().equals(paymentInfo.getPrdtCode())) {
			currencyCode = paymentInfo.getDccCurrencyCode();
		} else {
			currencyCode = paymentInfo.getCurrencyCode();
		}
		if (CredoraxCurrencyCodeEnum.isNeedChange(currencyCode)) {
			currencyCode = CurrencyCodeEnum.USD.getCode();
		}
		if (StringUtil.isEmpty(paymentInfo.getPayType())) {
			paymentInfo.setPayType("ALL");
		}

		List<PaymentChannelItemDto> channels = channelClientService.getChannel(paymentInfo.getTradeType(), partnerId,
				String.valueOf(memberBaseInfoBO.getMemberType()), paymentType, paymentInfo.getPayType(), currencyCode,
				paymentInfo.getSiteId(), paymentInfo.getPrdtCode());
		return channels;
	}

	/**
	 * 风控验证
	 * 
	 * @param paymentInfo
	 * @param tradeOrderDTO
	 * @param paymentResult
	 * @return
	 */
	public void riskValidate(PaymentInfo paymentInfo, TradeOrderDTO tradeOrderDTO, PaymentResult paymentResult) {
		try {
			// 过风控系统
			Map<String, String> paraMap = MapUtil.bean2map(paymentInfo);
			Map<String, String> riskMap = riskClientService.validate(paraMap);
			logger.info("riskMap: " + riskMap);
			if (riskMap != null && !riskMap.isEmpty()) {
				String desc = (String) riskMap.get("desc");
				paymentResult.setRiskDesc(desc);
				if (!"ACCEPT".equals(desc)) {// 如果交易被拒绝
					String respCode = (String) riskMap.get("respCode");
					String respMsg = (String) riskMap.get("respMsg");
					paymentResult.setResponseCode(respCode);
					paymentResult.setResponseDesc(respMsg);
					tradeOrderDTO.setStatus(TradeOrderStatusEnum.FAILED.getCode());
					tradeOrderDTO.setRespCode(respCode);
					tradeOrderDTO.setCompleteDate(new Date());
					tradeOrderDTO.setRespMsg(respMsg);
					tradeOrderService.updateTradeOrderRnTx(tradeOrderDTO);
					paymentResult.setRiskDesc("REJECT");
				}
			}
		} catch (Exception e) {
			logger.error("validate error:", e);
			paymentResult.setResponseCode(ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			paymentResult.setResponseDesc(ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
			paymentResult.setRiskDesc("ACCEPT");
			tradeOrderDTO.setCompleteDate(new Date());
			tradeOrderDTO.setStatus(TradeOrderStatusEnum.FAILED.getCode());
			tradeOrderDTO.setRespCode(ResponseCodeEnum.UNDEFINED_ERROR.getCode());
			tradeOrderDTO.setRespMsg(ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
			tradeOrderService.updateTradeOrderRnTx(tradeOrderDTO);
		}
	}
	
	// delin.dong 本地化支付 二〇一六年五月四日 10:58:30
	public PaymentResult payByLocaleChannels(PaymentInfo paymentInfo, TradeOrderDTO tradeOrderDTO,
			TradeBaseDTO tradeBaseDTO, String paymentWay) {
		tradeOrderDTO.setStatus(TradeOrderStatusEnum.PROCESSING.getCode()); // 网关订单状态改为订单处理中
		boolean updateResult = tradeOrderService.updateTradeOrderRnTx(tradeOrderDTO,
				TradeOrderStatusEnum.WAIT_PAY.getCode());
		if (!updateResult) {
			logger.info("set processing status to trade order is failed.");
		}
		PaymentResult paymentResult = new PaymentResult();
		List<PaymentChannelItemDto> channels = getChannel(paymentInfo, tradeOrderDTO);

		if (null == channels || channels.isEmpty() || removeZeroPriority(channels)) {
			logger.info("channel config error: " + ExceptionCodeEnum.CHANNEL_CONFING_FAIL);
			paymentResult.setResponseCode(ExceptionCodeEnum.CHANNEL_CONFING_FAIL.getCode());
			paymentResult.setResponseDesc(ExceptionCodeEnum.CHANNEL_CONFING_FAIL.getDescription());

			// add by sch 2016-04-20 设置网关订单状态为失败
			if (null == channels || channels.isEmpty()) {
				logger.info("channels = null or isEmpty");
			} else {
				logger.info("removeZeroPriority(channels)");
			}
			// note： 如果测试方法为删除渠道路由的话 ， 收银台接口 可以跑到这里，api接口在前面就档掉了
			tradeOrderDTO.setCompleteDate(new Date());
			tradeOrderDTO.setStatus(TradeOrderStatusEnum.FAILED.getCode());
			tradeOrderDTO.setRespCode(paymentResult.getResponseCode());
			tradeOrderDTO.setRespMsg("商户无可用渠道");
			// 下面这句要写么？
			// tradeOrderDTO.setPaymentWay(PaymentWayEnum.PAYMENT_LINK.getCode().equalsIgnoreCase(paymentInfo.getPaymentWay())?PaymentWayEnum.PAYMENT_LINK.getCode():paymentWay);
			boolean tradeUpFlg = tradeOrderService.updateTradeOrderRnTx(tradeOrderDTO,
					TradeOrderStatusEnum.PROCESSING.getCode());
			if (!tradeUpFlg) {
				logger.error("do not update trade order," + tradeOrderDTO.getTradeOrderNo());
			}
			// end by sch 2016-04-20

			return paymentResult;
		}

		logger.info("收单路由到的渠道是：" + tradeOrderDTO.getTradeOrderNo() + "," + Arrays.toString(channels.toArray()));
		ExpressTracking expressTracking = expressTrackingService
				.findByTradeOrderNo(tradeOrderDTO.getTradeOrderNo() + "");

		// ---------------------------------------------------------
		// 获取商户配置的汇率浮动值
		PartnerRateFloat rateFloat = channelService.getPartnerRateFloatService()
				.getPartnerRateFloat(paymentInfo.getPartnerId());
		if (rateFloat != null) {
			int start = Integer.valueOf(rateFloat.getStartPoint());
			int end = Integer.valueOf(rateFloat.getEndPoint());

			Random random = new Random();
			int s = random.nextInt(end - start + 1) + start;
			BigDecimal max0 = new BigDecimal(s + ".0");
			BigDecimal min0 = new BigDecimal(1000);

			BigDecimal t = max0.divide(min0);
			tradeOrderDTO.setFloatValue(t.toString());
			paymentInfo.setFloatValue(t.toString());
		}

		/* 赵阳 20160911
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("sourceCurrency", paymentInfo.getCurrencyCode());
		param.put("targetCurrency", paymentInfo.getSettlementCurrencyCode());
		param.put("status", "1");
		param.put("memberCode", paymentInfo.getPartnerId());
//		param.put("cardOrg",this.getCardType(paymentInfo.getCardHolderNumber() ));
		param.put("orderAmount", paymentInfo.getOrderAmount());
		param.put("ltaCurrencyCode", "USD");
		param.put("point", getTime());

		TransactionRate transRate = channelService.getCurrencyRateService().getNewTransactionRate(param);

		if (transRate != null) {
			if (!StringUtil.isEmpty(paymentInfo.getFloatValue())) {
				BigDecimal floatValue = new BigDecimal(paymentInfo.getFloatValue());
				BigDecimal rateO = new BigDecimal(transRate.getExchangeRate());
				BigDecimal tmp = rateO.add(rateO.multiply(floatValue));
				tradeOrderDTO.setRate(tmp.toString());
			} else {
				tradeOrderDTO.setRate(transRate.getExchangeRate());
			}
			tradeOrderDTO.setOriginalRate(transRate.getExchangeRate());
		}*/
		// -----------------------------------------------------------------------
		// String currencyCode = paymentInfo.getCurrencyCode();

		PaymentChannelItemDto paymentChannelItemDto = channels.get(0);

		// --------------------------------------
		// 本地化交易支付类型和产品编码都设置为EDC add by tom 2016年7月11日15:34:15
		String prdtCode = "EDC";
		String payType = "EDC";
		String currencyCode = paymentInfo.getCurrencyCode();

		String orgCode = paymentChannelItemDto.getOrgCode();

		ChannelCurrencyDTO currencyDTO = this.getChannelCurrency(paymentInfo.getPartnerId(),currencyCode, null, prdtCode, orgCode, payType);

		logger.info("ChannelCurrencyDTO: " + currencyDTO);
		// 如果找不到送渠道币种
		if (currencyDTO == null) {
			throw new BusinessException("找不到送渠道币种, orgCode：" + orgCode + ",currencyCode:" + currencyCode + ",prdtCode:"
					+ prdtCode + ",payType: " + payType + ",cardCurrencyCode:" + null, ExceptionCodeEnum.OTHER_ERROR);
		}

		TransRateMarkup markup = this.getMarkup(paymentInfo, currencyDTO);
		logger.info("currencyDTO: " + currencyDTO);

		if (markup != null) {
			paymentInfo.setMarkup(markup.getMarkup());
		}

		// --------------------------------------

		paymentInfo.setChannelCurrencyCode(currencyDTO.getChannelCurrencyCode());
		
		/**本地化支付
		 * add by zhaoyang at 201609011
		 * 结算币种更改。
		 * 1，根据用户号、交易类型、交易币种、结算币种查找通过POSS配置的结算币种信息
		 * 2，如果无查询信息，则使用原有的结算币种；如果有查询信息，则使用查询出来的结算币种与tradeBaseDTO.getSettlementCurrencyCode()
		 * 比较，若相等则使用原有的结算币种，否则使用查询出来的结算币种。
		 * 3，更新交易基础订单(结算币种)
		 * 4,根据结算币种获取当前
		 * 5,更新支付订单（收款人账户类型）
		 */
		logger.info("本地化支付结算币种确认--tradeOrderNo is {},memberCode is {},payType is {},tradeCurrencyCode is {},payCurrencyCode is {}", new Object[]{tradeOrderDTO.getTradeOrderNo(),tradeBaseDTO.getPartnerId(),tradeBaseDTO.getPayType(),tradeBaseDTO.getCurrencyCode(),currencyDTO.getChannelCurrencyCode()});
		tradeBaseDTO = this.confirmTheSettlementCurrency(tradeBaseDTO, currencyDTO.getChannelCurrencyCode());
		logger.info("本地化支付结算币种确认--tradeOrderNo is {},SettlementCurrencyCode is {}", tradeOrderDTO.getTradeOrderNo(),tradeBaseDTO.getSettlementCurrencyCode());
		TradeBase newTb = new TradeBase();
		newTb.setTradeBaseNo(tradeBaseDTO.getTradeBaseNo());
		newTb.setSettlementCurrencyCode(tradeBaseDTO.getSettlementCurrencyCode());
		tradeOrderService.updateTradeBase(newTb);
		paymentInfo.setSettlementCurrencyCode(tradeBaseDTO.getSettlementCurrencyCode());
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("sourceCurrency", paymentInfo.getCurrencyCode());
		param.put("targetCurrency", tradeBaseDTO.getSettlementCurrencyCode());
		param.put("status", "1");
		param.put("memberCode", paymentInfo.getPartnerId());
//		param.put("cardOrg",this.getCardType(paymentInfo.getCardHolderNumber() ));
		param.put("orderAmount", paymentInfo.getOrderAmount());
		param.put("ltaCurrencyCode", "USD");
		param.put("point", getTime());
		TransactionRate transRate = channelService.getCurrencyRateService().getNewTransactionRate(param);
		if (transRate != null) {
			if (!StringUtil.isEmpty(paymentInfo.getFloatValue())) {
				BigDecimal floatValue = new BigDecimal(paymentInfo.getFloatValue());
				BigDecimal rateO = new BigDecimal(transRate.getExchangeRate());
				BigDecimal tmp = rateO.add(rateO.multiply(floatValue));
				tradeOrderDTO.setRate(tmp.toString());
			} else {
				tradeOrderDTO.setRate(transRate.getExchangeRate());
			}
			tradeOrderDTO.setOriginalRate(transRate.getExchangeRate());
		}
		// create payment order
		PaymentOrderDTO paymentOrderDTO = OrderBuilder.buildPaymentOrder(paymentInfo, tradeBaseDTO, tradeOrderDTO,
				tradeBaseDTO.getSettlementCurrencyCode(), memberQueryService, accountQueryService);
		paymentOrderDTO.setOrgCode(channels.get(0).getOrgCode());
		paymentOrderDTO.setPayeeType(this.getPayeeTypeBySettlementCurrency(tradeBaseDTO.getSettlementCurrencyCode(), tradeOrderDTO.getPartnerId()));
		Long paymentOrderNo = paymentOrderService.savePaymentOrderRnTx(paymentOrderDTO);
		paymentOrderDTO.setPaymentOrderNo(paymentOrderNo);

		if (StringUtil.isEmpty(paymentInfo.getPayType())) {
			paymentInfo.setPayType("ALL");
		}

		paymentInfo.setPaymentOrderNo(paymentOrderDTO.getPaymentOrderNo().toString());
		paymentInfo.setOrderAmount(paymentOrderDTO.getPaymentAmount().toString());
		paymentInfo.setTradeType(tradeOrderDTO.getTradeType());
		OrderBuilder.savePaymentOrderExpand(paymentOrderNo, paymentInfo, paymentOrderExpandDAO);

		paymentInfo.setPaymentType(PaymentTypeEnum.PAYMENT.getCode());
		paymentInfo.setTransType(TransTypeEnum.EDC.getCode());
		paymentInfo.setCurrencyCode(tradeOrderDTO.getCurrencyCode());
		paymentInfo.setOrderId(tradeOrderDTO.getOrderId());
		paymentInfo.setBillName(expressTracking.getBillName());
		paymentInfo.setBillEmail(expressTracking.getBillEmail());
		paymentInfo.setBillPostalCode(expressTracking.getBillPostalCode());
		paymentInfo.setCardOrg(String.valueOf(param.get("cardOrg")));
		//
		paymentInfo = channelService.channel3DPay(paymentOrderDTO, paymentInfo, paymentChannelItemDto);

		// 验证参数
		if (StringUtil.isEmpty(paymentInfo.getOrgMerchantCode())) {
			paymentResult.setResponseCode(ResponseCodeEnum.INVALID_PARAMETER.getCode());
			paymentResult.setResponseDesc("商户号不能为空");

		} else if (StringUtil.isEmpty(paymentInfo.getCurrencyCode())) {
			paymentResult.setResponseCode(ResponseCodeEnum.INVALID_PARAMETER.getCode());
			paymentResult.setResponseDesc("货币代码不能为空");

		} else if (StringUtil.isEmpty(paymentInfo.getOrderAmount())) {
			paymentResult.setResponseCode(ResponseCodeEnum.INVALID_PARAMETER.getCode());
			paymentResult.setResponseDesc("交易金额不能为空");

		} else if (StringUtil.isEmpty(paymentInfo.getChannelOrderNo())) {
			paymentResult.setResponseCode(ResponseCodeEnum.INVALID_PARAMETER.getCode());
			paymentResult.setResponseDesc("订单号不能为空");

		} else if (StringUtil.isEmpty(paymentInfo.getOrgKey())) {
			paymentResult.setResponseCode(ResponseCodeEnum.INVALID_PARAMETER.getCode());
			paymentResult.setResponseDesc("机构密钥不能为空");

		}
		logger.info("channelOrderNo:" + paymentInfo.getChannelOrderNo());

		paymentResult.setLanguage(null == paymentInfo.getLanguage() ? "en" : paymentInfo.getLanguage());
		paymentResult.setSecurityCode(paymentInfo.getSecurityCode());
		paymentResult.setCardExp(paymentInfo.getCardExpirationYear() + paymentInfo.getCardExpirationMonth());
		/*
		 * paymentResult.setCardHolderNumber(paymentInfo.getCardHolderNumber());
		 */
		paymentResult.setReturnUrl(paymentInfo.getReturnUrl());
		paymentResult.setCardOrg(paymentInfo.getCardOrg());
		paymentResult.setLanguage(null == paymentInfo.getLanguage() ? "en" : paymentInfo.getLanguage());
		paymentResult.setOrgMerchantCode(paymentInfo.getOrgMerchantCode());
		paymentResult.setOrderAmount(paymentInfo.getOrderAmount());
		paymentResult.setOrgKey(paymentInfo.getOrgKey());
		paymentResult.setPreServerUrl(paymentInfo.getPreServerUrl());
		paymentResult.setVersion(paymentInfo.getVersion());
		paymentResult.setChannelOrderNo(Long.valueOf(paymentInfo.getChannelOrderNo()));
		paymentResult.setCurrencyCode(paymentInfo.getCurrencyCode());
		return paymentResult;
	}

	//peiyu.yang
	public PaymentResult payByChinaLocaleChannels(PaymentInfo paymentInfo, TradeOrderDTO tradeOrderDTO,
			TradeBaseDTO tradeBaseDTO, String paymentWay) {
		tradeOrderDTO.setStatus(TradeOrderStatusEnum.PROCESSING.getCode()); // 网关订单状态改为订单处理中
		boolean updateResult = tradeOrderService.updateTradeOrderRnTx(tradeOrderDTO,
				TradeOrderStatusEnum.WAIT_PAY.getCode());
		if (!updateResult) {
			logger.info("set processing status to trade order is failed.");
		}
		PaymentResult paymentResult = new PaymentResult();
		List<PaymentChannelItemDto> channels = getChannel(paymentInfo, tradeOrderDTO);

		if (null == channels || channels.isEmpty() || removeZeroPriority(channels)) {
			logger.info("channel config error: " + ExceptionCodeEnum.CHANNEL_CONFING_FAIL);
			paymentResult.setResponseCode(ExceptionCodeEnum.CHANNEL_CONFING_FAIL.getCode());
			paymentResult.setResponseDesc(ExceptionCodeEnum.CHANNEL_CONFING_FAIL.getDescription());

			// add by sch 2016-04-20 设置网关订单状态为失败
			if (null == channels || channels.isEmpty()) {
				logger.info("channels = null or isEmpty");
			} else {
				logger.info("removeZeroPriority(channels)");
			}
			// note： 如果测试方法为删除渠道路由的话 ， 收银台接口 可以跑到这里，api接口在前面就档掉了
			tradeOrderDTO.setCompleteDate(new Date());
			tradeOrderDTO.setStatus(TradeOrderStatusEnum.FAILED.getCode());
			tradeOrderDTO.setRespCode(paymentResult.getResponseCode());
			tradeOrderDTO.setRespMsg("商户无可用渠道");
			// 下面这句要写么？
			boolean tradeUpFlg = tradeOrderService.updateTradeOrderRnTx(tradeOrderDTO,
					TradeOrderStatusEnum.PROCESSING.getCode());
			if (!tradeUpFlg) {
				logger.error("do not update trade order," + tradeOrderDTO.getTradeOrderNo());
			}
			return paymentResult;
		}

		logger.info("收单路由到的渠道是：" + tradeOrderDTO.getTradeOrderNo() + "," + Arrays.toString(channels.toArray()));
		ExpressTracking expressTracking = expressTrackingService
				.findByTradeOrderNo(tradeOrderDTO.getTradeOrderNo() + "");

		// ---------------------------------------------------------
		// 获取商户配置的汇率浮动值
		PartnerRateFloat rateFloat = channelService.getPartnerRateFloatService()
				.getPartnerRateFloat(paymentInfo.getPartnerId());
		if (rateFloat != null) {
			int start = Integer.valueOf(rateFloat.getStartPoint());
			int end = Integer.valueOf(rateFloat.getEndPoint());

			Random random = new Random();
			int s = random.nextInt(end - start + 1) + start;
			BigDecimal max0 = new BigDecimal(s + ".0");
			BigDecimal min0 = new BigDecimal(1000);

			BigDecimal t = max0.divide(min0);
			tradeOrderDTO.setFloatValue(t.toString());
			paymentInfo.setFloatValue(t.toString());
		}

		Map<String, Object> param = new HashMap<String, Object>();
		param.put("sourceCurrency", paymentInfo.getCurrencyCode());
		param.put("targetCurrency", paymentInfo.getSettlementCurrencyCode());
		param.put("status", "1");
		param.put("memberCode", paymentInfo.getPartnerId());
		param.put("orderAmount", paymentInfo.getOrderAmount());
		param.put("ltaCurrencyCode", "USD");
		param.put("point", getTime());

		TransactionRate transRate = channelService.getCurrencyRateService().getNewTransactionRate(param);
		if (transRate != null) {
			if (!StringUtil.isEmpty(paymentInfo.getFloatValue())) {
				BigDecimal floatValue = new BigDecimal(paymentInfo.getFloatValue());
				BigDecimal rateO = new BigDecimal(transRate.getExchangeRate());
				BigDecimal tmp = rateO.add(rateO.multiply(floatValue));
				tradeOrderDTO.setRate(tmp.toString());
			} else {
				tradeOrderDTO.setRate(transRate.getExchangeRate());
			}
			tradeOrderDTO.setOriginalRate(transRate.getExchangeRate());
		}
		// -----------------------------------------------------------------------
		PaymentChannelItemDto paymentChannelItemDto = channels.get(0);
		// --------------------------------------
		// 本地化交易支付类型和产品编码都设置为EDC add by tom 2016年7月11日15:34:15
		String prdtCode = "EDC";
		String payType = "EDC";
		String currencyCode = paymentInfo.getCurrencyCode();
		String orgCode = paymentChannelItemDto.getOrgCode();
		ChannelCurrencyDTO currencyDTO = this.getChannelCurrency(paymentInfo.getPartnerId(),currencyCode, null, prdtCode, orgCode, payType);

		logger.info("ChannelCurrencyDTO: " + currencyDTO);
		// 如果找不到送渠道币种
		if (currencyDTO == null) {
			throw new BusinessException("找不到送渠道币种, orgCode：" + orgCode + ",currencyCode:" + currencyCode + ",prdtCode:"
					+ prdtCode + ",payType: " + payType + ",cardCurrencyCode:" + null, ExceptionCodeEnum.OTHER_ERROR);
		}

		TransRateMarkup markup = this.getMarkup(paymentInfo, currencyDTO);
		logger.info("currencyDTO: " + currencyDTO);
		if (markup != null) {
			paymentInfo.setMarkup(markup.getMarkup());
		}
		// --------------------------------------

		paymentInfo.setChannelCurrencyCode(currencyDTO.getChannelCurrencyCode());
		// create payment order
		PaymentOrderDTO paymentOrderDTO = OrderBuilder.buildPaymentOrder(paymentInfo, tradeBaseDTO, tradeOrderDTO,
				tradeBaseDTO.getSettlementCurrencyCode(), memberQueryService, accountQueryService);
		paymentOrderDTO.setOrgCode(channels.get(0).getOrgCode());
		Long paymentOrderNo = paymentOrderService.savePaymentOrderRnTx(paymentOrderDTO);
		paymentOrderDTO.setPaymentOrderNo(paymentOrderNo);

		if (StringUtil.isEmpty(paymentInfo.getPayType())) {
			paymentInfo.setPayType("EDC");
		}

		paymentInfo.setPaymentOrderNo(paymentOrderDTO.getPaymentOrderNo().toString());
		paymentInfo.setOrderAmount(paymentOrderDTO.getPaymentAmount().toString());
		paymentInfo.setTradeType(tradeOrderDTO.getTradeType());
		OrderBuilder.savePaymentOrderExpand(paymentOrderNo, paymentInfo, paymentOrderExpandDAO);

		paymentInfo.setPaymentType(PaymentTypeEnum.PAYMENT.getCode());
		paymentInfo.setTransType(TransTypeEnum.EDC.getCode());
		paymentInfo.setCurrencyCode(tradeOrderDTO.getCurrencyCode());
		paymentInfo.setOrderId(tradeOrderDTO.getOrderId());
		paymentInfo.setBillName(expressTracking.getBillName());
		paymentInfo.setBillEmail(expressTracking.getBillEmail());
		paymentInfo.setBillPostalCode(expressTracking.getBillPostalCode());
		paymentInfo.setCardOrg(String.valueOf(param.get("cardOrg")));

		paymentInfo = channelService.channel3DPay(paymentOrderDTO, paymentInfo, paymentChannelItemDto);

		logger.info("channelOrderNo:" + paymentInfo.getChannelOrderNo());
		paymentResult.setLanguage(null == paymentInfo.getLanguage() ? "en" : paymentInfo.getLanguage());
		paymentResult.setSecurityCode(paymentInfo.getSecurityCode());
		paymentResult.setCardExp(paymentInfo.getCardExpirationYear() + paymentInfo.getCardExpirationMonth());
		paymentResult.setReturnUrl(paymentInfo.getReturnUrl());
		paymentResult.setCardOrg(paymentInfo.getCardOrg());
		paymentResult.setLanguage(null == paymentInfo.getLanguage() ? "en" : paymentInfo.getLanguage());
		paymentResult.setOrgMerchantCode(paymentInfo.getOrgMerchantCode());
		paymentResult.setOrderAmount(paymentInfo.getOrderAmount());
		paymentResult.setOrgKey(paymentInfo.getOrgKey());
		paymentResult.setPreServerUrl(paymentInfo.getPreServerUrl());
		paymentResult.setVersion(paymentInfo.getVersion());
		paymentResult.setChannelOrderNo(Long.valueOf(paymentInfo.getChannelOrderNo()));
		paymentResult.setCurrencyCode(paymentInfo.getCurrencyCode());
		return paymentResult;
	}
	
	

	// 累加交易金额到商户月交易统计表
	private void updateTradeAmountCount(TradeOrderDTO tradeOrderDTO, Long paymentOrderNo) {
		MerchantRateDto merchantRate = new MerchantRateDto();
		PaymentOrderExpand paymentOrderExpand = paymentOrderExpandDAO.queryPayOrderExpandByPayNO(paymentOrderNo);
		String cardNumber = paymentOrderExpand.getCardNo();
		String cardType = CardOrgUtil.getCardType(cardNumber);
		merchantRate.setOrganisation(cardType);
		merchantRate.setMemberCode(tradeOrderDTO.getPartnerId());
		merchantRate.setDealCode(202);
		List<MerchantRateDto> merchantRates = merchantRateService.queryMerchantRate(merchantRate);
		if (null == merchantRates || merchantRates.isEmpty()) {
			throw new BusinessException("未找到商户手续费", ExceptionCodeEnum.NO_FEE_RATE);
		}
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMM");
		TradeAmountCount tradeAmountCount = new TradeAmountCount();
		tradeAmountCount.setPartnerId(tradeOrderDTO.getPartnerId() + "");
		tradeAmountCount.setCountMonth(sdf.format(Calendar.getInstance().getTime()));
		// 转汇
		SettlementRate settlementRete = channelService.getCurrencyRateService().getSettlementRate(
				tradeOrderDTO.getCurrencyCode(), merchantRates.get(0).getLevelCurrencyCode(), "1",
				String.valueOf(tradeOrderDTO.getPartnerId()), null);
		// 以元为单位进行累加
		BigDecimal tradeAmountYuan = new BigDecimal(tradeOrderDTO.getOrderAmount()).divide(new BigDecimal("1000"));
		Long currentTradeAmount = new BigDecimal(settlementRete.getExchangeRate()).multiply(tradeAmountYuan)
				.setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
		tradeAmountCount.setTotalAmount(currentTradeAmount);
		tradeAmountCount.setTotalCurrencyCode(merchantRates.get(0).getLevelCurrencyCode());
		tradeAmountCount.setUpdateDate(Calendar.getInstance().getTime());
		tradeAmountCountService.saveOrUpdate(tradeAmountCount);
	}

	public void updateBusTradeCount(PaymentInfo paymentInfo, TradeOrderDTO tradeOrderDTO) {
		String memberCode = paymentInfo.getPartnerId();
		String exchangeRate = "1";
		SettlementBaseRate baseRate = channelService.getCurrencyRateService()
				.findSettlementBaseRate(paymentInfo.getCurrencyCode(), "CNY", "1", null);
		if (!StringUtils.isEmpty(baseRate.getExchangeRate())) {
			exchangeRate = baseRate.getExchangeRate();
		}
		Long orderAmountS = tradeOrderDTO.getOrderAmount();
		BigDecimal multiply = new BigDecimal(orderAmountS).multiply(new BigDecimal(exchangeRate));
		Long orderAmount = multiply.longValue();
		Calendar c = Calendar.getInstance();
		int year = c.get(Calendar.YEAR);
		int month = c.get(Calendar.MONTH) + 1;
		int day = c.get(Calendar.DAY_OF_MONTH);

		BusinessTradeCountDTO tradeCount = new BusinessTradeCountDTO();
		tradeCount.setDay(day);
		tradeCount.setMonth(month);
		tradeCount.setYear(year);
		tradeCount.setMemberCode(memberCode);

		// 只查找当月所有记录，tradeCountList的size最大为31，即月最大天数
		List<BusinessTradeCountDTO> tradeCountList = businessTradeCountService.getBusinessTradeCountDTO(tradeCount);

		if (tradeCountList != null && tradeCountList.size() > 0) {
			BusinessTradeCountDTO countDTO = tradeCountList.get(tradeCountList.size() - 1);
			Map<String, Object> params = new HashMap<String, Object>();
			params.put("day", day);
			params.put("month", month);
			params.put("year", year);
			params.put("memberCode", memberCode);
			params.put("monthCount", (countDTO.getMonthCount() + 1));
			params.put("dayCount", (countDTO.getDayCount() + 1));
			params.put("yearCount", (countDTO.getYearCount() + 1));

			if (day != countDTO.getDay()) {
				tradeCount.setDayAmount(orderAmount);
				tradeCount.setDayCount(1L);
				// tradeCount.setMonthAmount(orderAmount);
				tradeCount.setMonthAmount(countDTO.getMonthAmount() + orderAmount);
				tradeCount.setMonthCount(countDTO.getMonthCount() + 1);
				tradeCount.setYearAmount(countDTO.getYearAmount() + Long.valueOf(Long.valueOf(orderAmount)));
				tradeCount.setYearCount(countDTO.getYearCount() + 1);
				tradeCount.setUpdateTime(new Date());
				businessTradeCountService.createTradeCountDTO(tradeCount);
			} else {
				params.put("dayAmount", (countDTO.getDayAmount() + orderAmount));
				params.put("monthAmount", (countDTO.getMonthAmount() + orderAmount));
				params.put("yearAmount", (countDTO.getYearAmount() + orderAmount));
				params.put("updateTime", new Date());
				businessTradeCountService.updateBTC(params);
			}
		} else {
			tradeCount.setDayAmount(orderAmount);
			tradeCount.setDayCount(1L);
			tradeCount.setMonthAmount(orderAmount);
			tradeCount.setMonthCount(1L);
			tradeCount.setYearAmount(orderAmount);
			tradeCount.setYearCount(1L);
			tradeCount.setUpdateTime(new Date());
			businessTradeCountService.createTradeCountDTO(tradeCount);
		}
	}

	/**
	 * 获取当月使用费率
	 */
	public String[] getCurrenctMonthChargeRate(MerchantRateDto merchantRate, boolean needReset) {
		logger.info("getCurrenctMonthChargeRate merchantRate: " + merchantRate);
		logger.info("getCurrenctMonthChargeRate isNextMonth: " + needReset);
		if (StringUtil.isEmpty(merchantRate.getMonthChargeRate()) || needReset) {
			Calendar calendar = Calendar.getInstance();
			String yearAndMonth = String.valueOf(calendar.get(Calendar.YEAR)) + calendar.get(Calendar.MONTH);
			TradeAmountCount tradeAmountCount2 = new TradeAmountCount();
			tradeAmountCount2.setPartnerId(merchantRate.getMemberCode() + "");
			tradeAmountCount2.setCountMonth(yearAndMonth);
			TradeAmountCount tac2 = tradeAmountCountService.query(tradeAmountCount2);
			if (tac2 == null) {
				Collection<String[]> values = getFourLevelData(merchantRate).values();
				String[] data = (String[]) values.toArray()[0];
				merchantRate.setMonthChargeRate(data[0]);
				logger.info("更新当月费率到清算表：" + data[0]);
				merchantRateService.updateMerchantRate(merchantRate);
				return data;
			}
			// 得到上个月的交易数据
			Long totalAmount = tac2.getTotalAmount();
			// 如果本月的统计币种和上月的不一致，要转汇
			if (!tac2.getTotalCurrencyCode().equalsIgnoreCase(merchantRate.getLevelCurrencyCode())) {
				SettlementRate settlementRate = channelService.getCurrencyRateService().getSettlementRate(
						tac2.getTotalCurrencyCode(), merchantRate.getLevelCurrencyCode(), "1",
						merchantRate.getMemberCode() + "", null);
				totalAmount = new BigDecimal(settlementRate.getExchangeRate()).multiply(new BigDecimal(totalAmount))
						.setScale(0, BigDecimal.ROUND_HALF_UP).longValue();
			}
			// 根据上个月的交易统计决定当月的费率
			String[] chargeRate = geMathedLevelChargeRate(totalAmount, merchantRate);
			// 把当月费率更新到清算费率表中
			merchantRate.setMonthChargeRate(chargeRate[0]);
			logger.info("更新当月费率到清算表：" + chargeRate[0]);
			merchantRateService.updateMerchantRate(merchantRate);
			return chargeRate;
		} else {
			Collection<String[]> values = getFourLevelData(merchantRate).values();
			for (String[] level : values) {
				if (level[0].equals(merchantRate.getMonthChargeRate())) {
					return level;
				}
			}
			return null;
		}
	}

	public void setPaymentOrderService(PaymentOrderService paymentOrderService) {
		this.paymentOrderService = paymentOrderService;
	}

	public void setTradeOrderService(TradeOrderService tradeOrderService) {
		this.tradeOrderService = tradeOrderService;
	}

	public void setTradeExtendsService(TradeExtendsService tradeExtendsService) {
		this.tradeExtendsService = tradeExtendsService;
	}

	public TradeExtendsService getTradeExtendsService() {
		return tradeExtendsService;
	}

	public void setExpressTrackingService(ExpressTrackingService expressTrackingService) {
		this.expressTrackingService = expressTrackingService;
	}

	public ExpressTrackingService getExpressTrackingService() {
		return expressTrackingService;
	}

	public void setMemberQueryService(MemberQueryService memberQueryService) {
		this.memberQueryService = memberQueryService;
	}

	public void setAccountQueryService(AccountQueryService accountQueryService) {
		this.accountQueryService = accountQueryService;
	}

	public void setPartnerSettlementOrderService(PartnerSettlementOrderService partnerSettlementOrderService) {
		this.partnerSettlementOrderService = partnerSettlementOrderService;
	}

	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}

	public void setEnterpriseBaseService(EnterpriseBaseService enterpriseBaseService) {
		this.enterpriseBaseService = enterpriseBaseService;
	}

	public void setPaymentOrderExpandDAO(PaymentOrderExpandDAO paymentOrderExpandDAO) {
		this.paymentOrderExpandDAO = paymentOrderExpandDAO;
	}

	/*
	 * 收银台支付过中，调用该函数，来把TradeExtendsDTO 扩充信息补充完整
	 */
	public void updateTradesExtendsDTO(TradeExtendsDTO tradeExtendsDTO, PaymentInfo paymentInfo) {
		if (null == tradeExtendsDTO)
			return;

		tradeExtendsDTO.setExtOrderInfo2(paymentInfo.getSecurityCode());
		tradeExtendsDTO
				.setExtOrderInfo5(paymentInfo.getCardExpirationMonth() + "" + paymentInfo.getCardExpirationYear());
		tradeExtendsDTO
				.setCardHolderName(paymentInfo.getCardHolderLastName() + "" + paymentInfo.getCardHolderFirstName());
		tradeExtendsDTO.setCardHolderCardNo(paymentInfo.getCardHolderNumber());
		tradeExtendsService.updateTradeExtends(tradeExtendsDTO);
	}

	public void updateTradeData(PaymentInfo paymentInfo, TradeOrderDTO tradeOrderDTO) {
		TradeData tradeData = new TradeData();
		String cardNo = paymentInfo.getCardHolderNumber();
		if (!StringUtil.isEmpty(cardNo)) {
			logger.info("cardNo: " + cardNo);
			String cardBin = cardNo.substring(0, 6);
			tradeData.setCardBin("00" + cardBin + "12");
			CardBinInfo binInfo = cardBinInfoService.getCardBinInfo(cardBin);
			if (binInfo != null) {
				tradeData.setCardClass(binInfo.getCardClass());
				tradeData.setCardCountry(binInfo.getCountryCode2());
				tradeData.setCardCurrency(binInfo.getCurrencyCode());
				tradeData.setCardNo("10203" + cardNo + "05");
				tradeData.setCardOrg(CardOrgUtil.getCardType(cardNo));
				tradeData.setCardType(binInfo.getCardType());
				tradeData.setIssuer(binInfo.getIssuer());
				tradeData.setIssuerCountry(binInfo.getIssuerCountry());
			}
		}
		tradeData.setTradeOrderNo(tradeOrderDTO.getTradeOrderNo());
		tradeData.setCardEmail(paymentInfo.getCardHolderEmail());
		tradeData.setCardFirstName(paymentInfo.getCardHolderFirstName());
		tradeData.setCardLastName(paymentInfo.getCardHolderLastName());
		tradeData.setCardPhone(paymentInfo.getCardHolderPhoneNumber());
		tradeData.setExpMonth("23" + paymentInfo.getCardExpirationMonth() + "31");
		tradeData.setExpYear("11" + paymentInfo.getCardExpirationYear() + "12");
		tradeData.setSecCode("01" + paymentInfo.getSecurityCode() + "12");
		tradeData.setBillName(paymentInfo.getBillName());
		tradeData.setShippingName(paymentInfo.getShippingName());
		boolean rst = tradeDataService.update(tradeData);
		logger.info("rst-update:" + rst);
	}

	public void setChannelOrderService(ChannelOrderService channelOrderService) {
		this.channelOrderService = channelOrderService;
	}

	public void setBusinessTradeCountService(BusinessTradeCountService businessTradeCountService) {
		this.businessTradeCountService = businessTradeCountService;
	}

	public void setMerchantRateService(MerchantRateService merchantRateService) {
		this.merchantRateService = merchantRateService;
	}

	private static String null2unknown(String in) {
		if (in == null || in.length() == 0) {
			return "No Value Returned";
		} else {
			return in;
		}
	}

	public void setChannelClientService(ChannelClientService channelClientService) {
		this.channelClientService = channelClientService;
	}

	public void setTradeAmountCountService(TradeAmountCountService tradeAmountCountService) {
		this.tradeAmountCountService = tradeAmountCountService;
	}

	private Map<Long, String[]> getFourLevelData(MerchantRateDto merchantRate) {
		Map<Long, String[]> tradeCountLevelMap = new LinkedHashMap<Long, String[]>();
		if (merchantRate.getMonthAmountLevel() != null) {
			tradeCountLevelMap.put(merchantRate.getMonthAmountLevel(), new String[] { merchantRate.getChargeRate(),
					merchantRate.getFixedCharge(), merchantRate.getFixedCurrencyCode() });
		}
		if (merchantRate.getMonthAmountLevel1() != null) {
			tradeCountLevelMap.put(merchantRate.getMonthAmountLevel1(), new String[] { merchantRate.getChargeRate1(),
					merchantRate.getFixedCharge1(), merchantRate.getFixed1CurrencyCode() });
		}
		if (merchantRate.getMonthAmountLevel2() != null) {
			tradeCountLevelMap.put(merchantRate.getMonthAmountLevel2(), new String[] { merchantRate.getChargeRate2(),
					merchantRate.getFixedCharge2(), merchantRate.getFixed2CurrencyCode() });
		}
		if (merchantRate.getMonthAmountLevel3() != null) {
			tradeCountLevelMap.put(merchantRate.getMonthAmountLevel3(), new String[] { merchantRate.getChargeRate3(),
					merchantRate.getFixedCharge3(), merchantRate.getFixed3CurrencyCode() });
		}
		return tradeCountLevelMap;
	}

	private String[] geMathedLevelChargeRate(Long totalAmount, MerchantRateDto merchantRate) {
		logger.info("geMathedLevelChargeRate : totalAmount is " + totalAmount);
		Map<Long, String[]> tradeCountLevelMap = getFourLevelData(merchantRate);
		Long[] data = (Long[]) tradeCountLevelMap.keySet().toArray(new Long[tradeCountLevelMap.keySet().size()]);
		Long targetLevel = null;
		if (totalAmount <= data[0]) {
			targetLevel = data[0];
		} else if (totalAmount >= data[data.length - 1]) {
			targetLevel = data[data.length - 1];
		} else {
			int index = -1;
			for (int i = 0; i < data.length; i++) {
				if (totalAmount >= data[i]) {
					continue;
				} else {
					index = i;
					break;
				}
			}
			targetLevel = data[index - 1];
		}
		return tradeCountLevelMap.get(targetLevel);
	}

	private void sendAlertMsg(Map<String, String> data) {
		WeiXinNotifyRequest request = new WeiXinNotifyRequest();
		request.setBizCode("0015");
		request.setOpenId("0000");
		request.setType(RequestType.WEIXIN);
		request.setData(data);
		jmsSender.send(request);
	}

	private String getDateTime(Date date) {
		return formatter.format(date);
	}

	public void setChannelCurrencyDAO(ChannelCurrencyDAO channelCurrencyDAO) {
		this.channelCurrencyDAO = channelCurrencyDAO;
	}

	public void setTransRateMarkupService(TransRateMarkupService transRateMarkupService) {
		this.transRateMarkupService = transRateMarkupService;
	}

	public void setDccService(ConfigurationDCCService dccService) {
		this.dccService = dccService;
	}

	public void setCardBinInfoService(CardBinInfoService cardBinInfoService) {
		this.cardBinInfoService = cardBinInfoService;
	}

	public void setTradeDataService(TradeDataService tradeDataService) {
		this.tradeDataService = tradeDataService;
	}

	public void setRiskClientService(RiskClientService riskClientService) {
		this.riskClientService = riskClientService;
	}

	public TradeOrderService getTradeOrderService() {
		return tradeOrderService;
	}

	public TradeDataService getTradeDataService() {
		return tradeDataService;
	}

	public void setIpCountryInfoService(IpCountryInfoService ipCountryInfoService) {
		this.ipCountryInfoService = ipCountryInfoService;
	}

	public void updateTradeDataNew(PaymentInfo paymentInfo, TradeOrderDTO tradeOrderDTO) {
		TradeDataDTO dto = new TradeDataDTO();
		dto.setTradeOrderDTO(tradeOrderDTO);
		dto.setData(paymentInfo.getData());
		tradeOrderService.updateTradeDataRnTx(dto);
	}

	public PaymentResult pay4All(PaymentInfo paymentInfo, TradeOrderDTO tradeOrderDTO, TradeBaseDTO tradeBaseDTO,
			String paymentWay) {
		tradeOrderDTO.setStatus(TradeOrderStatusEnum.PROCESSING.getCode()); // 网关订单状态改为订单处理中
		boolean updateResult = tradeOrderService.updateTradeOrderRnTx(tradeOrderDTO,
				TradeOrderStatusEnum.WAIT_PAY.getCode());
		if (!updateResult) {
			logger.info("set processing status to trade order is failed.");
		}
		PaymentResult paymentResult = new PaymentResult();
		List<PaymentChannelItemDto> channels = channelClientService.getChannel(PaymentTypeEnum.PAYMENT.getCode(),
				paymentInfo.getPartnerId(), paymentInfo.getOrgCode());
		if (null == channels || channels.isEmpty() || removeZeroPriority(channels)) {
			logger.info("channel config error: " + ExceptionCodeEnum.CHANNEL_CONFING_FAIL);
			paymentResult.setResponseCode(ExceptionCodeEnum.CHANNEL_CONFING_FAIL.getCode());
			paymentResult.setResponseDesc(ExceptionCodeEnum.CHANNEL_CONFING_FAIL.getDescription());

			// add by sch 2016-04-20 设置网关订单状态为失败
			if (null == channels || channels.isEmpty()) {
				logger.info("channels = null or isEmpty");
			} else {
				logger.info("removeZeroPriority(channels)");
			}
			// note： 如果测试方法为删除渠道路由的话 ， 收银台接口 可以跑到这里，api接口在前面就档掉了
			tradeOrderDTO.setCompleteDate(new Date());
			tradeOrderDTO.setStatus(TradeOrderStatusEnum.FAILED.getCode());
			tradeOrderDTO.setRespCode(paymentResult.getResponseCode());
			tradeOrderDTO.setRespMsg("商户无可用渠道");
			// 下面这句要写么？
			// tradeOrderDTO.setPaymentWay(PaymentWayEnum.PAYMENT_LINK.getCode().equalsIgnoreCase(paymentInfo.getPaymentWay())?PaymentWayEnum.PAYMENT_LINK.getCode():paymentWay);
			boolean tradeUpFlg = tradeOrderService.updateTradeOrderRnTx(tradeOrderDTO,
					TradeOrderStatusEnum.PROCESSING.getCode());
			if (!tradeUpFlg) {
				logger.error("do not update trade order," + tradeOrderDTO.getTradeOrderNo());
			}
			// end by sch 2016-04-20

			return paymentResult;
		}

		logger.info("收单路由到的渠道是：" + tradeOrderDTO.getTradeOrderNo() + "," + Arrays.toString(channels.toArray()));
		ExpressTracking expressTracking = expressTrackingService
				.findByTradeOrderNo(tradeOrderDTO.getTradeOrderNo() + "");

		// ---------------------------------------------------------
		// 获取商户配置的汇率浮动值
		PartnerRateFloat rateFloat = channelService.getPartnerRateFloatService()
				.getPartnerRateFloat(paymentInfo.getPartnerId());
		if (rateFloat != null) {
			int start = Integer.valueOf(rateFloat.getStartPoint());
			int end = Integer.valueOf(rateFloat.getEndPoint());

			Random random = new Random();
			int s = random.nextInt(end - start + 1) + start;
			BigDecimal max0 = new BigDecimal(s + ".0");
			BigDecimal min0 = new BigDecimal(1000);

			BigDecimal t = max0.divide(min0);
			tradeOrderDTO.setFloatValue(t.toString());
			paymentInfo.setFloatValue(t.toString());
		}
		// delete by zhaoyang at 20160918
		/**
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("sourceCurrency", paymentInfo.getCurrencyCode());
		param.put("targetCurrency", paymentInfo.getSettlementCurrencyCode());
		param.put("status", "1");
		param.put("memberCode", paymentInfo.getPartnerId());
//		param.put("cardOrg",this.getCardType(paymentInfo.getCardHolderNumber() ));
		param.put("orderAmount", paymentInfo.getOrderAmount());
		param.put("ltaCurrencyCode", "USD");
		param.put("point", getTime());
		TransactionRate transRate = channelService.getCurrencyRateService().getNewTransactionRate(param);
		if (transRate != null) {
			if (!StringUtil.isEmpty(paymentInfo.getFloatValue())) {
				BigDecimal floatValue = new BigDecimal(paymentInfo.getFloatValue());
				BigDecimal rateO = new BigDecimal(transRate.getExchangeRate());
				BigDecimal tmp = rateO.add(rateO.multiply(floatValue));
				tradeOrderDTO.setRate(tmp.toString());
			} else {
				tradeOrderDTO.setRate(transRate.getExchangeRate());
			}
			tradeOrderDTO.setOriginalRate(transRate.getExchangeRate());
		}
 * 
 */
		// -----------------------------------------------------------------------
		// String currencyCode = paymentInfo.getCurrencyCode();

		PaymentChannelItemDto paymentChannelItemDto = channels.get(0);

		// --------------------------------------
		// 本地化交易支付类型和产品编码都设置为EDC add by tom 2016年7月11日15:34:15
		String prdtCode = "EDC";
		String payType = "EDC";
		String currencyCode = paymentInfo.getCurrencyCode();

		String orgCode = paymentChannelItemDto.getOrgCode();

		ChannelCurrencyDTO currencyDTO = this.getChannelCurrency(paymentInfo.getPartnerId(),currencyCode, null, prdtCode, orgCode, payType);

		logger.info("ChannelCurrencyDTO: " + currencyDTO);
		// 如果找不到送渠道币种
		if (currencyDTO == null) {
			throw new BusinessException("找不到送渠道币种, orgCode：" + orgCode + ",currencyCode:" + currencyCode + ",prdtCode:"
					+ prdtCode + ",payType: " + payType + ",cardCurrencyCode:" + null, ExceptionCodeEnum.OTHER_ERROR);
		}
		//fix bugs;
		paymentInfo.setPrdtCode(currencyDTO.getPrdtCode());
		paymentInfo.setPayType(currencyDTO.getPayType());
		TransRateMarkup markup = this.getMarkup(paymentInfo, currencyDTO);
		logger.info("currencyDTO: " + currencyDTO);

		if (markup != null) {
			paymentInfo.setMarkup(markup.getMarkup());
		}

		// --------------------------------------

		paymentInfo.setChannelCurrencyCode(currencyDTO.getChannelCurrencyCode());
		/**本地化支付
		 * add by zhaoyang at 201609018
		 * 结算币种更改。
		 * 1，根据用户号、交易类型、交易币种、结算币种查找通过POSS配置的结算币种信息
		 * 2，如果无查询信息，则使用原有的结算币种；如果有查询信息，则使用查询出来的结算币种与tradeBaseDTO.getSettlementCurrencyCode()
		 * 比较，若相等则使用原有的结算币种，否则使用查询出来的结算币种。
		 * 3，更新交易基础订单(结算币种)
		 * 4,根据结算币种获取当前
		 * 5,更新支付订单（收款人账户类型）
		 */
		logger.info("本地化支付pay4All结算币种确认--tradeOrderNo is {},memberCode is {},payType is {},tradeCurrencyCode is {},payCurrencyCode is {}", new Object[]{tradeOrderDTO.getTradeOrderNo(),tradeBaseDTO.getPartnerId(),tradeBaseDTO.getPayType(),tradeBaseDTO.getCurrencyCode(),currencyDTO.getChannelCurrencyCode()});
		tradeBaseDTO = this.confirmTheSettlementCurrency(tradeBaseDTO, currencyDTO.getChannelCurrencyCode());
		logger.info("本地化支付pay4All结算币种确认--tradeOrderNo is {},SettlementCurrencyCode is {}", tradeOrderDTO.getTradeOrderNo(),tradeBaseDTO.getSettlementCurrencyCode());
		TradeBase newTb = new TradeBase();
		newTb.setTradeBaseNo(tradeBaseDTO.getTradeBaseNo());
		newTb.setSettlementCurrencyCode(tradeBaseDTO.getSettlementCurrencyCode());
		tradeOrderService.updateTradeBase(newTb);
		paymentInfo.setSettlementCurrencyCode(tradeBaseDTO.getSettlementCurrencyCode());
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("sourceCurrency", paymentInfo.getCurrencyCode());
		param.put("targetCurrency", tradeBaseDTO.getSettlementCurrencyCode());
		param.put("status", "1");
		param.put("memberCode", paymentInfo.getPartnerId());
		param.put("orderAmount", paymentInfo.getOrderAmount());
		param.put("ltaCurrencyCode", "USD");
		param.put("point", getTime());
		TransactionRate transRate = channelService.getCurrencyRateService().getNewTransactionRate(param);
		if (transRate != null) {
			if (!StringUtil.isEmpty(paymentInfo.getFloatValue())) {
				BigDecimal floatValue = new BigDecimal(paymentInfo.getFloatValue());
				BigDecimal rateO = new BigDecimal(transRate.getExchangeRate());
				BigDecimal tmp = rateO.add(rateO.multiply(floatValue));
				tradeOrderDTO.setRate(tmp.toString());
			} else {
				tradeOrderDTO.setRate(transRate.getExchangeRate());
			}
			tradeOrderDTO.setOriginalRate(transRate.getExchangeRate());
		}
		// create payment order
		PaymentOrderDTO paymentOrderDTO = OrderBuilder.buildPaymentOrder(paymentInfo, tradeBaseDTO, tradeOrderDTO,
				tradeBaseDTO.getSettlementCurrencyCode(), memberQueryService, accountQueryService);
		paymentOrderDTO.setOrgCode(channels.get(0).getOrgCode());
		Long paymentOrderNo = paymentOrderService.savePaymentOrderRnTx(paymentOrderDTO);
		paymentOrderDTO.setPaymentOrderNo(paymentOrderNo);

		if (StringUtil.isEmpty(paymentInfo.getPayType())) {
			paymentInfo.setPayType("ALL");
		}

		paymentInfo.setPaymentOrderNo(paymentOrderDTO.getPaymentOrderNo().toString());
		paymentInfo.setOrderAmount(paymentOrderDTO.getPaymentAmount().toString());
		paymentInfo.setTradeType(tradeOrderDTO.getTradeType());
		OrderBuilder.savePaymentOrderExpand(paymentOrderNo, paymentInfo, paymentOrderExpandDAO);

		paymentInfo.setPaymentType(PaymentTypeEnum.PAYMENT.getCode());
		paymentInfo.setTransType(TransTypeEnum.EDC.getCode());
		paymentInfo.setCurrencyCode(tradeOrderDTO.getCurrencyCode());
		paymentInfo.setOrderId(tradeOrderDTO.getOrderId());
		paymentInfo.setBillName(expressTracking.getBillName());
		paymentInfo.setBillEmail(expressTracking.getBillEmail());
		paymentInfo.setBillPostalCode(expressTracking.getBillPostalCode());
		paymentInfo.setCardOrg(String.valueOf(param.get("cardOrg")));
		//
		paymentInfo = channelService.channelPay4Return(paymentOrderDTO, paymentInfo, paymentChannelItemDto);

		// 验证参数
		if (StringUtil.isEmpty(paymentInfo.getOrgMerchantCode())) {
			paymentResult.setResponseCode(ResponseCodeEnum.INVALID_PARAMETER.getCode());
			paymentResult.setResponseDesc("商户号不能为空");

		} else if (StringUtil.isEmpty(paymentInfo.getCurrencyCode())) {
			paymentResult.setResponseCode(ResponseCodeEnum.INVALID_PARAMETER.getCode());
			paymentResult.setResponseDesc("货币代码不能为空");

		} else if (StringUtil.isEmpty(paymentInfo.getOrderAmount())) {
			paymentResult.setResponseCode(ResponseCodeEnum.INVALID_PARAMETER.getCode());
			paymentResult.setResponseDesc("交易金额不能为空");

		} else if (StringUtil.isEmpty(paymentInfo.getChannelOrderNo())) {
			paymentResult.setResponseCode(ResponseCodeEnum.INVALID_PARAMETER.getCode());
			paymentResult.setResponseDesc("订单号不能为空");

		} else if (StringUtil.isEmpty(paymentInfo.getOrgKey())) {
			paymentResult.setResponseCode(ResponseCodeEnum.INVALID_PARAMETER.getCode());
			paymentResult.setResponseDesc("机构密钥不能为空");

		}
		logger.info("channelOrderNo:" + paymentInfo.getChannelOrderNo());

		paymentResult.setLanguage(null == paymentInfo.getLanguage() ? "en" : paymentInfo.getLanguage());
		paymentResult.setSecurityCode(paymentInfo.getSecurityCode());
		paymentResult.setCardExp(paymentInfo.getCardExpirationYear() + paymentInfo.getCardExpirationMonth());
		/*
		 * paymentResult.setCardHolderNumber(paymentInfo.getCardHolderNumber());
		 */
		paymentResult.setReturnUrl(paymentInfo.getReturnUrl());
		paymentResult.setCardOrg(paymentInfo.getCardOrg());
		paymentResult.setLanguage(null == paymentInfo.getLanguage() ? "en" : paymentInfo.getLanguage());
		paymentResult.setOrgMerchantCode(paymentInfo.getOrgMerchantCode());
		paymentResult.setOrderAmount(paymentInfo.getOrderAmount());
		paymentResult.setOrgKey(paymentInfo.getOrgKey());
		paymentResult.setPreServerUrl(paymentInfo.getPreServerUrl());
		paymentResult.setVersion(paymentInfo.getVersion());
		paymentResult.setChannelOrderNo(Long.valueOf(paymentInfo.getChannelOrderNo()));
		paymentResult.setCurrencyCode(paymentInfo.getCurrencyCode());
		paymentResult.setResponseCode("0000");
		paymentResult.setReturnfromFront(paymentInfo.getReturnFromFront());
		return paymentResult;
	}
	
	/**
	 * 威富通【微信｜支付宝】
	 */
	public PaymentResult payByWft(PaymentInfo paymentInfo, TradeOrderDTO tradeOrderDTO, TradeBaseDTO tradeBaseDTO,
			String paymentWay) {
		tradeOrderDTO.setStatus(TradeOrderStatusEnum.PROCESSING.getCode()); // 网关订单状态改为订单处理中
		boolean updateResult = tradeOrderService.updateTradeOrderRnTx(tradeOrderDTO,
				TradeOrderStatusEnum.WAIT_PAY.getCode());
		if (!updateResult) {
			logger.info("set processing status to trade order is failed.");
		}
		PaymentResult paymentResult = new PaymentResult();
		List<PaymentChannelItemDto> channels = channelClientService.getChannel(PaymentTypeEnum.PAYMENT.getCode(),
				paymentInfo.getPartnerId(), ChannelItemOrgCodeEnum.WFT.getCode());
		if (null == channels || channels.isEmpty() || removeZeroPriority(channels)) {
			logger.info("channel config error: " + ExceptionCodeEnum.CHANNEL_CONFING_FAIL);
			paymentResult.setResponseCode(ExceptionCodeEnum.CHANNEL_CONFING_FAIL.getCode());
			paymentResult.setResponseDesc(ExceptionCodeEnum.CHANNEL_CONFING_FAIL.getDescription());
			
			// add by sch 2016-04-20 设置网关订单状态为失败
			if (null == channels || channels.isEmpty()) {
				logger.info("channels = null or isEmpty");
			} else {
				logger.info("removeZeroPriority(channels)");
			}
			// note： 如果测试方法为删除渠道路由的话 ， 收银台接口 可以跑到这里，api接口在前面就档掉了
			tradeOrderDTO.setCompleteDate(new Date());
			tradeOrderDTO.setStatus(TradeOrderStatusEnum.FAILED.getCode());
			tradeOrderDTO.setRespCode(paymentResult.getResponseCode());
			tradeOrderDTO.setRespMsg("商户无可用渠道");
			// 下面这句要写么？
			// tradeOrderDTO.setPaymentWay(PaymentWayEnum.PAYMENT_LINK.getCode().equalsIgnoreCase(paymentInfo.getPaymentWay())?PaymentWayEnum.PAYMENT_LINK.getCode():paymentWay);
			boolean tradeUpFlg = tradeOrderService.updateTradeOrderRnTx(tradeOrderDTO,
					TradeOrderStatusEnum.PROCESSING.getCode());
			if (!tradeUpFlg) {
				logger.error("do not update trade order," + tradeOrderDTO.getTradeOrderNo());
			}
			// end by sch 2016-04-20
			
			return paymentResult;
		}
		
		logger.info("收单路由到的渠道是：" + tradeOrderDTO.getTradeOrderNo() + "," + Arrays.toString(channels.toArray()));
		ExpressTracking expressTracking = expressTrackingService
				.findByTradeOrderNo(tradeOrderDTO.getTradeOrderNo() + "");
		
		// ---------------------------------------------------------
		// 获取商户配置的汇率浮动值
		PartnerRateFloat rateFloat = channelService.getPartnerRateFloatService()
				.getPartnerRateFloat(paymentInfo.getPartnerId());
		if (rateFloat != null) {
			int start = Integer.valueOf(rateFloat.getStartPoint());
			int end = Integer.valueOf(rateFloat.getEndPoint());
			
			Random random = new Random();
			int s = random.nextInt(end - start + 1) + start;
			BigDecimal max0 = new BigDecimal(s + ".0");
			BigDecimal min0 = new BigDecimal(1000);
			
			BigDecimal t = max0.divide(min0);
			tradeOrderDTO.setFloatValue(t.toString());
			paymentInfo.setFloatValue(t.toString());
		}
		// delete by zhaoyang at 20160918
		// -----------------------------------------------------------------------
		// String currencyCode = paymentInfo.getCurrencyCode();
		
		PaymentChannelItemDto paymentChannelItemDto = channels.get(0);
		
		// --------------------------------------
		// 本地化交易支付类型和产品编码都设置为EDC add by tom 2016年7月11日15:34:15
		String prdtCode = "EDC";
		String payType = "EDC";
//		String currencyCode = paymentInfo.getCurrencyCode();
//		
//		String orgCode = paymentChannelItemDto.getOrgCode();
//		
//		ChannelCurrencyDTO currencyDTO = this.getChannelCurrency(paymentInfo.getPartnerId(),currencyCode, null, prdtCode, orgCode, payType);
//		
//		logger.info("ChannelCurrencyDTO: " + currencyDTO);
//		// 如果找不到送渠道币种
//		if (currencyDTO == null) {
//			throw new BusinessException("找不到送渠道币种, orgCode：" + orgCode + ",currencyCode:" + currencyCode + ",prdtCode:"
//					+ prdtCode + ",payType: " + payType + ",cardCurrencyCode:" + null, ExceptionCodeEnum.OTHER_ERROR);
//		}
		//fix bugs;
//		paymentInfo.setPrdtCode(currencyDTO.getPrdtCode());
//		paymentInfo.setPayType(currencyDTO.getPayType());
//		TransRateMarkup markup = this.getMarkup(paymentInfo, currencyDTO);
//		logger.info("currencyDTO: " + currencyDTO);
//		
//		if (markup != null) {
//			paymentInfo.setMarkup(markup.getMarkup());
//		}
//		
//		// --------------------------------------
//		
//		paymentInfo.setChannelCurrencyCode(currencyDTO.getChannelCurrencyCode());
		paymentInfo.setChannelCurrencyCode(paymentInfo.getCurrencyCode());
		/**本地化支付
		 * add by zhaoyang at 201609018
		 * 结算币种更改。
		 * 1，根据用户号、交易类型、交易币种、结算币种查找通过POSS配置的结算币种信息
		 * 2，如果无查询信息，则使用原有的结算币种；如果有查询信息，则使用查询出来的结算币种与tradeBaseDTO.getSettlementCurrencyCode()
		 * 比较，若相等则使用原有的结算币种，否则使用查询出来的结算币种。
		 * 3，更新交易基础订单(结算币种)
		 * 4,根据结算币种获取当前
		 * 5,更新支付订单（收款人账户类型）
		 */
		logger.info("威富能支付payByWft结算币种确认--tradeOrderNo is {},memberCode is {},payType is {},tradeCurrencyCode is {},payCurrencyCode is {}", new Object[]{tradeOrderDTO.getTradeOrderNo(),tradeBaseDTO.getPartnerId(),tradeBaseDTO.getPayType(),tradeBaseDTO.getCurrencyCode(),paymentInfo.getCurrencyCode()});
		tradeBaseDTO = this.confirmTheSettlementCurrency(tradeBaseDTO, paymentInfo.getCurrencyCode());
		logger.info("威富通支付payByWft结算币种确认--tradeOrderNo is {},SettlementCurrencyCode is {}", tradeOrderDTO.getTradeOrderNo(),tradeBaseDTO.getSettlementCurrencyCode());
		TradeBase newTb = new TradeBase();
		newTb.setTradeBaseNo(tradeBaseDTO.getTradeBaseNo());
		newTb.setSettlementCurrencyCode(tradeBaseDTO.getSettlementCurrencyCode());
		tradeOrderService.updateTradeBase(newTb);
		paymentInfo.setSettlementCurrencyCode(tradeBaseDTO.getSettlementCurrencyCode());
		Map<String, Object> param = new HashMap<String, Object>();
		param.put("sourceCurrency", paymentInfo.getCurrencyCode());
		param.put("targetCurrency", tradeBaseDTO.getSettlementCurrencyCode());
		param.put("status", "1");
		param.put("memberCode", paymentInfo.getPartnerId());
		param.put("orderAmount", paymentInfo.getOrderAmount());
		param.put("ltaCurrencyCode", "USD");
		param.put("point", getTime());
		TransactionRate transRate = channelService.getCurrencyRateService().getNewTransactionRate(param);
		if (transRate != null) {
			if (!StringUtil.isEmpty(paymentInfo.getFloatValue())) {
				BigDecimal floatValue = new BigDecimal(paymentInfo.getFloatValue());
				BigDecimal rateO = new BigDecimal(transRate.getExchangeRate());
				BigDecimal tmp = rateO.add(rateO.multiply(floatValue));
				tradeOrderDTO.setRate(tmp.toString());
			} else {
				tradeOrderDTO.setRate(transRate.getExchangeRate());
			}
			tradeOrderDTO.setOriginalRate(transRate.getExchangeRate());
		}
		// create payment order
		PaymentOrderDTO paymentOrderDTO = OrderBuilder.buildPaymentOrder(paymentInfo, tradeBaseDTO, tradeOrderDTO,
				tradeBaseDTO.getSettlementCurrencyCode(), memberQueryService, accountQueryService);
		paymentOrderDTO.setOrgCode(channels.get(0).getOrgCode());
		Long paymentOrderNo = paymentOrderService.savePaymentOrderRnTx(paymentOrderDTO);
		paymentOrderDTO.setPaymentOrderNo(paymentOrderNo);
		
		if (StringUtil.isEmpty(paymentInfo.getPayType())) {
			paymentInfo.setPayType("ALL");
		}
		
		paymentInfo.setPaymentOrderNo(paymentOrderDTO.getPaymentOrderNo().toString());
		paymentInfo.setOrderAmount(paymentOrderDTO.getPaymentAmount().toString());
		paymentInfo.setTradeType(tradeOrderDTO.getTradeType());
		OrderBuilder.savePaymentOrderExpand(paymentOrderNo, paymentInfo, paymentOrderExpandDAO);
		
		paymentInfo.setPaymentType(PaymentTypeEnum.PAYMENT.getCode());
		paymentInfo.setTransType(TransTypeEnum.EDC.getCode());
		paymentInfo.setCurrencyCode(tradeOrderDTO.getCurrencyCode());
		paymentInfo.setOrderId(tradeOrderDTO.getOrderId());
		paymentInfo.setBillName(expressTracking.getBillName());
		paymentInfo.setBillEmail(expressTracking.getBillEmail());
		paymentInfo.setBillPostalCode(expressTracking.getBillPostalCode());
		paymentInfo.setCardOrg(String.valueOf(param.get("cardOrg")));
		//
		paymentInfo = channelService.channelPay4Wft(paymentOrderDTO, paymentInfo, paymentChannelItemDto);
		
		// 验证参数
		if (StringUtil.isEmpty(paymentInfo.getOrgMerchantCode())) {
			paymentResult.setResponseCode(ResponseCodeEnum.INVALID_PARAMETER.getCode());
			paymentResult.setResponseDesc("商户号不能为空");
			
		} else if (StringUtil.isEmpty(paymentInfo.getCurrencyCode())) {
			paymentResult.setResponseCode(ResponseCodeEnum.INVALID_PARAMETER.getCode());
			paymentResult.setResponseDesc("货币代码不能为空");
			
		} else if (StringUtil.isEmpty(paymentInfo.getOrderAmount())) {
			paymentResult.setResponseCode(ResponseCodeEnum.INVALID_PARAMETER.getCode());
			paymentResult.setResponseDesc("交易金额不能为空");
			
		} else if (StringUtil.isEmpty(paymentInfo.getChannelOrderNo())) {
			paymentResult.setResponseCode(ResponseCodeEnum.INVALID_PARAMETER.getCode());
			paymentResult.setResponseDesc("订单号不能为空");
			
		} else if (StringUtil.isEmpty(paymentInfo.getOrgKey())) {
			paymentResult.setResponseCode(ResponseCodeEnum.INVALID_PARAMETER.getCode());
			paymentResult.setResponseDesc("机构密钥不能为空");
			
		}
		logger.info("channelOrderNo:" + paymentInfo.getChannelOrderNo());
		
		paymentResult.setLanguage(null == paymentInfo.getLanguage() ? "en" : paymentInfo.getLanguage());
		paymentResult.setSecurityCode(paymentInfo.getSecurityCode());
		paymentResult.setCardExp(paymentInfo.getCardExpirationYear() + paymentInfo.getCardExpirationMonth());
		/*
		 * paymentResult.setCardHolderNumber(paymentInfo.getCardHolderNumber());
		 */
		paymentResult.setReturnUrl(paymentInfo.getReturnUrl());
		paymentResult.setCardOrg(paymentInfo.getCardOrg());
		paymentResult.setLanguage(null == paymentInfo.getLanguage() ? "en" : paymentInfo.getLanguage());
		paymentResult.setOrgMerchantCode(paymentInfo.getOrgMerchantCode());
		paymentResult.setOrderAmount(paymentInfo.getOrderAmount());
		paymentResult.setOrgKey(paymentInfo.getOrgKey());
		paymentResult.setPreServerUrl(paymentInfo.getPreServerUrl());
		paymentResult.setVersion(paymentInfo.getVersion());
		paymentResult.setChannelOrderNo(Long.valueOf(paymentInfo.getChannelOrderNo()));
		paymentResult.setCurrencyCode(paymentInfo.getCurrencyCode());
		paymentResult.setResponseCode("0000");
		//TODO修改为威富通所需要的参数
		//paymentResult.setReturnfromFront(paymentInfo.getReturnFromFront());
		paymentResult.setWftBackVo(paymentInfo.getWftBackVo());
		return paymentResult;
	}

	private void notifyChannel(String memberCurrentId, long payAmount, String payCurreneyCode, String partnerId,
							   String paymentChannelId, boolean success, String channelConfigId,String failflag){
		try {
			TransactionBaseRate baseRate_ = channelService.getCurrencyRateService()
					.findTransactionBaseRate(payCurreneyCode, "CNY", "1", null);
			BigDecimal rateO = BigDecimal.ZERO;
			if(baseRate_!= null){
				rateO = new BigDecimal(baseRate_.getExchangeRate());
				rateO  = rateO.multiply(new BigDecimal(payAmount));
			}else{
				logger.error("没有找到[" + payCurreneyCode + "]到[CNY]的基本汇率");
			}
			ChannelMidAmountNotifyRequest notifyRequest = new ChannelMidAmountNotifyRequest();
			Map<String, String> notifyMap = new HashMap<String, String>();
			notifyMap.put("memberCurrentId",memberCurrentId);
			notifyMap.put("partnerId",partnerId);
			notifyMap.put("paymentChannelItemId",paymentChannelId);
			notifyMap.put("payAmount",rateO.toString());
			notifyMap.put("success",success?"1":"0");
			notifyMap.put("failure",failflag);
			notifyMap.put("channelConfigId",channelConfigId);
			notifyRequest.setData(notifyMap);
			notifyRequest.setMerchantCode(partnerId);
			jmsSender.send("notify.freeAuto", notifyRequest);
		}catch (Exception e){
			e.printStackTrace();
		}
	}
	
	/**确认结算币种
	 * @param tradeBaseDTO 交易基础信息对象
	 * @param payCurrencyCode 渠道配置的支付币种
	 * @return tradeBaseDTO 交易基础信息对象
	 */
	public TradeBaseDTO confirmTheSettlementCurrency(TradeBaseDTO tradeBaseDTO,String payCurrencyCode){
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("memberCode", tradeBaseDTO.getPartnerId());
        paraMap.put("payType", tradeBaseDTO.getPayType());
        paraMap.put("tradeCurrencyCode", tradeBaseDTO.getCurrencyCode());
        paraMap.put("payCurrencyCode", payCurrencyCode);
		List<SettlementCurrencyConfig> sccList = channelClientService.querySettlementCurrencyConfig(paraMap);
		if(sccList.size()>0){
			SettlementCurrencyConfig scc = sccList.get(0);
			if(!scc.getSettlementCurrencyCode().equals(tradeBaseDTO.getSettlementCurrencyCode())){
				tradeBaseDTO.setSettlementCurrencyCode(scc.getSettlementCurrencyCode());
			}
		}
		return tradeBaseDTO;
	}
	
	/**根据结算币种获得对应收款人的结算账户类型
	 * @param settlementCurrencyCode 结算币种
	 * @param memberCode 会员号
	 * @return 收款人账户类型
	 */
	public Integer getPayeeTypeBySettlementCurrency(String settlementCurrencyCode,Long memberCode){
		AcctAttribDto acctAttribDto = accountQueryService.doQueryDefaultAcctAttribNsTx(memberCode);
		if (StringUtil.isEmpty(settlementCurrencyCode)) {
			return acctAttribDto.getAcctType();
		}

		Integer acctType = AcctTypeEnum.getBasicAcctTypeByCurrency(settlementCurrencyCode);
		acctAttribDto = accountQueryService.doQueryAcctAttribNsTx(memberCode, acctType);
		return acctAttribDto.getAcctType();
	}
}
