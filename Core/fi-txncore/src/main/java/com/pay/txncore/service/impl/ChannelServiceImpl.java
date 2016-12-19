/**
 * 
 */
package com.pay.txncore.service.impl;

import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.txncore.model.*;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.util.StringUtils;

import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.cardbin.model.CardBinInfo;
import com.pay.dcc.model.PartnerDCCConfig;
import com.pay.fi.commons.CountryCodeEnum;
import com.pay.fi.commons.PaymentTypeEnum;
import com.pay.fi.commons.TransTypeEnum;
import com.pay.fi.exception.BusinessException;
import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.fi.helper.ABCCurrencyCodeEnum;
// add end
import com.pay.fi.helper.ChannelItemOrgCodeEnum;
import com.pay.fi.helper.CredoraxCurrencyCodeEnum;
//add by Mack for CybsCTV 2016年5月24日
import com.pay.fi.helper.CybsCtvCurrencyCodeEnum;
import com.pay.inf.dao.Page;
import com.pay.inf.dto.CardFilterDTO;
import com.pay.inf.enums.DCCEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.enums.SysRespCodeEnum;
import com.pay.inf.service.CardFilterService;
import com.pay.jms.notification.request.RequestType;
import com.pay.jms.notification.request.WeiXinNotifyRequest;
import com.pay.jms.sender.JmsSender;
import com.pay.pe.dto.AccountingDto;
import com.pay.pe.dto.AccountingFeeRe;
import com.pay.pe.dto.AccountingFeeRes;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.txncore.client.ChannelClientService;
import com.pay.txncore.commons.ChannelOrderStatusEnum;
import com.pay.txncore.crosspay.model.PartnerWebsiteConfig;
import com.pay.txncore.crosspay.service.CurrencyRateService;
import com.pay.txncore.crosspay.service.PartnerRateFloatService;
import com.pay.txncore.crosspay.service.PartnerWebsiteConfigService;
import com.pay.txncore.dto.ChannelOrderDTO;
import com.pay.txncore.dto.ChannelPaymentResult;
import com.pay.txncore.dto.ChannelPreauthResponseDto;
import com.pay.txncore.dto.ChannelPreauthResult;
import com.pay.txncore.dto.ChannelResponseDto;
import com.pay.txncore.dto.PaymentChannelItemDto;
import com.pay.txncore.dto.PaymentInfo;
import com.pay.txncore.dto.PaymentOrderDTO;
import com.pay.txncore.dto.PreauthInfo;
import com.pay.txncore.dto.PreauthOrderDTO;
import com.pay.txncore.newpaymentprocess.processvo.BackfromChannelVo;
import com.pay.txncore.newpaymentprocess.processvo.SendChannelVo;
import com.pay.txncore.newpaymentprocess.processvo.WftBackVo;
import com.pay.txncore.service.ChannelOrderService;
import com.pay.txncore.service.ChannelService;
import com.pay.util.CurrencyCodeEnum;
import com.pay.util.StringUtil;

/**
 * @author chaoyue
 *
 */
public class ChannelServiceImpl implements ChannelService {

	private final Log logger = LogFactory.getLog(getClass());
	private ChannelOrderService channelOrderService;
	private AccountingService accountingService;
	private ChannelClientService channelClientService;
	MathContext mc = new MathContext(4, RoundingMode.HALF_DOWN);
	// 对账成功记账
	private AccountingService accounting_100_101;
	private AccountingService accounting_100_102;
	private CurrencyRateService currencyRateService;
	private JmsSender jmsSender;
	private PartnerWebsiteConfigService partnerWebsiteConfigService;
	private PartnerRateFloatService partnerRateFloatService;

	private CardFilterService cardFilterService;
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.channel.service.ChannelService#channelPay(com.pay.channel.dto
	 * .ChannelRequest)
	 */
	@Override
	public ChannelResponseDto channelPay(PaymentOrderDTO paymentOrderDTO,
			PaymentInfo paymentInfo) {

		ChannelResponseDto channelResponseDto = new ChannelResponseDto();

		return channelResponseDto;
	}
	public void setPartnerRateFloatService(
			PartnerRateFloatService partnerRateFloatService) {
		this.partnerRateFloatService = partnerRateFloatService;
	}
	//Add by : Bobby Guo
	public ChannelResponseDto channelPay(PaymentOrderDTO paymentOrderDTO,
			PaymentInfo paymentInfo,PaymentChannelItemDto paymentChannelItemDto) {
		ChannelResponseDto channelResponseDto = new ChannelResponseDto();
		ChannelOrderDTO channelOrderDTO = buildChannelOrder(paymentInfo,
				paymentChannelItemDto);
		
		channelOrderDTO.setOrgKey(paymentChannelItemDto.getOrgKey());
		channelOrderDTO.setAccessCode(paymentChannelItemDto.getAccessCode());
		channelOrderDTO.setMerchantBillName(paymentChannelItemDto.getMerchantBillName());

		Long channelOrderNo = channelOrderService
				.saveChannelProtocolRnTx(channelOrderDTO);
		channelOrderDTO.setChannelOrderNo(channelOrderNo);
		if(TransTypeEnum.DCC.getCode()//只有走标准DCC的时候才会调用这段代码，自有DCC仍按照EDC送渠道
				.equals(paymentOrderDTO.getPayType())
				&&(DCCEnum.STANDARD.getCode().equals(paymentInfo.getPrdtCode())
						||DCCEnum.FORCED.getCode().equals(paymentInfo.getPrdtCode()))){
			dccRateSet(paymentInfo, channelOrderDTO);
			paymentInfo.setTransType("DCC");
			channelOrderService.updateChannelProtocolRnTx(channelOrderDTO);
		}
		channelOrderDTO.setChannelOrderNo(channelOrderNo);
		paymentInfo.setChannelOrderNo(String.valueOf(channelOrderNo));
		paymentInfo.setOrgCode(paymentChannelItemDto.getOrgCode());
		paymentInfo.setSerialNo(channelOrderDTO.getSerialNo());
		BeanUtils.copyProperties(paymentChannelItemDto, paymentInfo,
				new String[] { "currencyCode", "serialNo" });
		//模式
		if("A".equals(paymentInfo.getPattern()))paymentInfo.setPattern("M");
		//处理Credorax是否要包含账单名称
		PartnerWebsiteConfig partnerWebsiteConfig = new PartnerWebsiteConfig();
		partnerWebsiteConfig.setPartnerId(paymentInfo.getPartnerId());
		partnerWebsiteConfig.setUrl(paymentInfo.getSiteId());
		List<PartnerWebsiteConfig> websiteConfigs = partnerWebsiteConfigService.findWebsiteConfig(partnerWebsiteConfig, new Page());
		if(websiteConfigs!=null && !websiteConfigs.isEmpty() && "Y".equalsIgnoreCase(websiteConfigs.get(0).getSendCredorax())){
			paymentInfo.setSendCredorax("Y");
		}else{
			paymentInfo.setSendCredorax("N");
		}		
		// 调用前置完成支付
		ChannelPaymentResult channelPaymentResult = channelClientService
				.channelPay(paymentInfo);
		channelResponseDto.setResponseCode(channelPaymentResult.getErrorCode());
		channelResponseDto.setResponseDesc(channelPaymentResult.getErrorMsg());
		// add by Dl for 负载均衡 2016年5月23日22:22:10
		channelResponseDto.setPayCurrencyCode(paymentInfo.getCurrencyCode());
		//add end
		channelResponseDto.setOrgCode(paymentChannelItemDto.getOrgCode());
		channelResponseDto.setChannelOrderNo(channelOrderNo);

		String errorCode = channelPaymentResult.getErrorCode();
		if (ResponseCodeEnum.SUCCESS.getCode().equals(errorCode)) {
			channelOrderDTO.setStatus(ChannelOrderStatusEnum.SUCCESS.getCode());
			channelResponseDto
					.setPayAmount(channelPaymentResult.getPayAmount());
		} else{
			channelOrderDTO.setStatus(ChannelOrderStatusEnum.FAIL.getCode());
		}
		channelResponseDto.setChannelRespCode(errorCode);
		channelOrderDTO.setErrorCode(errorCode);
		channelOrderDTO.setErrorMsg(channelPaymentResult.getErrorMsg());
		channelOrderDTO.setUpdateDate(new Date());
		channelOrderDTO.setCompleteDate(new Date());
		channelOrderDTO.setAuthorisation(channelPaymentResult
				.getAuthorisation());
		channelOrderDTO.setReturnNo(channelPaymentResult.getChannelReturnNo());
		//中行moto migs不传订单号，传参考号
		channelOrderDTO.setReferenceNo(channelPaymentResult.getReferenceNo());
		logger.info("获取参考号referenceNo:"+channelPaymentResult.getReferenceNo());
		channelOrderDTO.setCardOrg(paymentInfo.getCardOrg());
		boolean updateFlg = channelOrderService.updateChannelProtocolRnTx(
				channelOrderDTO, ChannelOrderStatusEnum.INIT.getCode());

		logger.info("channel order update result:" + updateFlg
				+ ",channelOrderNo:" + channelOrderNo + ",orgCode="+paymentChannelItemDto.getOrgCode());

		
          //渠道返回消息转换
		//Mack comment below line 按渠道包装码
		//SystemRespCodeEnum srce = SystemRespCodeEnum.getResponseCodeEnum(errorCode);
		String resultCode = "3099";
		String resultMsg = "Other errors:其他异常";
		//Add by yangjian 2016-08-24
		if(errorCode.equals("0001")&&paymentChannelItemDto.getOrgCode().equals("10081016")){
			logger.info("前海万融:"+errorCode+"-"+channelPaymentResult.getErrorMsg());
			resultCode = errorCode;
			resultMsg = channelPaymentResult.getErrorMsg();
		}else{
			SysRespCodeEnum srce = SysRespCodeEnum.getResponseCodeEnum(errorCode, paymentChannelItemDto.getOrgCode());
			if(srce!=null){
				resultCode = srce.getRespCode();
				resultMsg = srce.getRespDescEn()+":"+srce.getRespDEscCn();
			}
		}
		channelResponseDto.setResponseCode(resultCode);
		channelResponseDto.setResponseDesc(resultMsg);
		
		// 记账处理
		if (ResponseCodeEnum.SUCCESS.getCode().equals(errorCode) && updateFlg) {

			Long amount = channelOrderDTO.getPayAmount();
			Long payAmount = channelPaymentResult.getPayAmount();
			if (null == payAmount) {
				
				logger.error("return payAmount must be not null: "+ExceptionCodeEnum.ILLEGAL_PARAMETER);
				
				//这地方要加个报警、但是已经支付成功了
				Map<String,String> data = new HashMap<String, String>();

				data.put("first","系统异常，返回的支付金额为空,txncore:ChannelServiceImpl");
				data.put("keyword1","返回的支付金额为空");
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
		        data.put("keyword2",formatter.format(new Date()));
		        data.put("remark","请尽快检查！");
				
				this.sendAlertMsg(data);
				
				return channelResponseDto;
			}

			
			if (payAmount < amount) {
				logger.error("channelOrderNo:"
						+ channelOrderDTO.getChannelOrderNo() + "payAmount:"
						+ payAmount + ",amount:" + amount);
				logger.error("payAmount error: "+ExceptionCodeEnum.ORDERAMOUNT_ERROR);
				
				Map<String,String> data = new HashMap<String, String>();

				data.put("first","系统异常: 支付订单金额小于渠道订单金额,,txncore:ChannelServiceImpl");
				data.put("keyword1","返回的支付金额为空");
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
		        data.put("keyword2",formatter.format(new Date()));
		        data.put("remark","请尽快检查！");
				
				this.sendAlertMsg(data);
				
				return channelResponseDto;
				//这地方要加个报警、但是已经支付成功了
                
			}

			try {

				logger.info("orderId: "+paymentInfo.getOrderId()+ new Date());
				
				accountingService
						.doAccounting(buildAccountingVo(channelOrderDTO,paymentInfo.getOrderId()));
				logger.info(" doAccounting 101 start: "+ new Date());  
				String orderId = channelOrderDTO.getChannelOrderNo() + "";
				doAccounting_100_101(channelOrderDTO.getOrgCode(), orderId,
						channelOrderDTO.getAmount(),
						channelOrderDTO.getCurrencyCode(),paymentInfo.getOrderId());
				logger.info(" doAccounting 102 start: "+ new Date()); 
				doAccounting_100_102(channelOrderDTO.getOrgCode(), orderId,
						channelOrderDTO.getAmount(),
						channelOrderDTO.getTransferCurrencyCode(),
						channelOrderDTO.getTransferRate(),paymentInfo.getOrderId());
				logger.info(" doAccounting 102 end: "+ new Date()); 
			} catch (Exception e) {
				logger.error("do channel accounting error:" + channelOrderDTO.getChannelOrderNo(), e);
				logger.error("call pe error: "+ExceptionCodeEnum.PE_ERROR.getCode());
				
				//这地方要加个报警、这个地方是更新本地账户余额失败、但是已经支付成功了
				
				return channelResponseDto;
			}
		}

		return channelResponseDto;
	}
	
	//Add by : liu 2016-04-27
		public PaymentInfo channel3DPay(PaymentOrderDTO paymentOrderDTO,
				PaymentInfo paymentInfo,PaymentChannelItemDto paymentChannelItemDto) {
			ChannelResponseDto channelResponseDto = new ChannelResponseDto();
			ChannelOrderDTO channelOrderDTO = buildChannelOrder(paymentInfo,
					paymentChannelItemDto);
			
			channelOrderDTO.setOrgKey(paymentChannelItemDto.getOrgKey());
			channelOrderDTO.setAccessCode(paymentChannelItemDto.getAccessCode());
			channelOrderDTO.setMerchantBillName(paymentChannelItemDto.getMerchantBillName());

			Long channelOrderNo = channelOrderService
					.saveChannelProtocolRnTx(channelOrderDTO);
			channelOrderDTO.setChannelOrderNo(channelOrderNo);
			if(TransTypeEnum.DCC.getCode()//只有走标准DCC的时候才会调用这段代码，自有DCC仍按照EDC送渠道
					.equals(paymentOrderDTO.getPayType())
					&&(DCCEnum.STANDARD.getCode().equals(paymentInfo.getPrdtCode())
							||DCCEnum.FORCED.getCode().equals(paymentInfo.getPrdtCode()))){
				dccRateSet(paymentInfo, channelOrderDTO);
				channelOrderService.updateChannelProtocolRnTx(channelOrderDTO);
			}
			channelOrderDTO.setChannelOrderNo(channelOrderNo);
			paymentInfo.setChannelOrderNo(String.valueOf(channelOrderNo));
			paymentInfo.setOrgCode(paymentChannelItemDto.getOrgCode());
			paymentInfo.setSerialNo(channelOrderDTO.getSerialNo());
			paymentInfo.setReturnUrl(paymentChannelItemDto.getFrontCallbackUrl());//添加银行回调地址 add by liu 2016-04-27
			BeanUtils.copyProperties(paymentChannelItemDto, paymentInfo,
					new String[] { "currencyCode", "serialNo" });
			//模式
			if("A".equals(paymentInfo.getPattern()))paymentInfo.setPattern("M");
			//处理Credorax是否要包含账单名称
			PartnerWebsiteConfig partnerWebsiteConfig = new PartnerWebsiteConfig();
			partnerWebsiteConfig.setPartnerId(paymentInfo.getPartnerId());
			partnerWebsiteConfig.setUrl(paymentInfo.getSiteId());
			List<PartnerWebsiteConfig> websiteConfigs = partnerWebsiteConfigService.findWebsiteConfig(partnerWebsiteConfig, new Page());
			if(websiteConfigs!=null && !websiteConfigs.isEmpty() && "Y".equalsIgnoreCase(websiteConfigs.get(0).getSendCredorax())){
				paymentInfo.setSendCredorax("Y");
			}else{
				paymentInfo.setSendCredorax("N");
			}
			return paymentInfo;
	}
		
		/**
		 * 中国本地化支付
		 */
		public ChannelResponseDto channelChinaLocalPay(PaymentOrderDTO paymentOrderDTO,
				PaymentInfo paymentInfo,PaymentChannelItemDto paymentChannelItemDto) {
			ChannelResponseDto channelResponseDto = new ChannelResponseDto();
			ChannelOrderDTO channelOrderDTO = buildChannelOrder(paymentInfo,
					paymentChannelItemDto);
			
			channelOrderDTO.setOrgKey(paymentChannelItemDto.getOrgKey());
			channelOrderDTO.setAccessCode(paymentChannelItemDto.getAccessCode());
			channelOrderDTO.setMerchantBillName(paymentChannelItemDto.getMerchantBillName());

			Long channelOrderNo = channelOrderService
					.saveChannelProtocolRnTx(channelOrderDTO);
			channelOrderDTO.setChannelOrderNo(channelOrderNo);
			paymentInfo.setChannelOrderNo(String.valueOf(channelOrderNo));
			paymentInfo.setOrgCode(paymentChannelItemDto.getOrgCode());
			paymentInfo.setSerialNo(channelOrderDTO.getSerialNo());
			BeanUtils.copyProperties(paymentChannelItemDto, paymentInfo,
					new String[] { "currencyCode", "serialNo" });
			//模式
			if("A".equals(paymentInfo.getPattern()))paymentInfo.setPattern("M");
			//处理Credorax是否要包含账单名称
			PartnerWebsiteConfig partnerWebsiteConfig = new PartnerWebsiteConfig();
			partnerWebsiteConfig.setPartnerId(paymentInfo.getPartnerId());
			partnerWebsiteConfig.setUrl(paymentInfo.getSiteId());
			List<PartnerWebsiteConfig> websiteConfigs = partnerWebsiteConfigService.findWebsiteConfig(partnerWebsiteConfig, new Page());
			if(websiteConfigs!=null && !websiteConfigs.isEmpty() && "Y".equalsIgnoreCase(websiteConfigs.get(0).getSendCredorax())){
				paymentInfo.setSendCredorax("Y");
			}else{
				paymentInfo.setSendCredorax("N");
			}		
			// 调用前置完成支付
			ChannelPaymentResult channelPaymentResult = channelClientService
					.channelPay(paymentInfo);
			channelResponseDto.setResponseCode(channelPaymentResult.getErrorCode());
			channelResponseDto.setResponseDesc(channelPaymentResult.getErrorMsg());
			// add by Dl for 负载均衡 2016年5月23日22:22:10
			channelResponseDto.setPayCurrencyCode(paymentInfo.getCurrencyCode());
			//add end
			channelResponseDto.setOrgCode(paymentChannelItemDto.getOrgCode());
			channelResponseDto.setChannelOrderNo(channelOrderNo);

			String errorCode = channelPaymentResult.getErrorCode();
			if (ResponseCodeEnum.SUCCESS.getCode().equals(errorCode)) {
				channelOrderDTO.setStatus(ChannelOrderStatusEnum.SUCCESS.getCode());
				channelResponseDto
						.setPayAmount(channelPaymentResult.getPayAmount());
			} else if(ResponseCodeEnum.ZFZZ.getCode().equals(errorCode)){//组装支付数据
				//如果是装在支付数据的先不更新渠道订单状态，等异步通知来了再更新
			}else{
				channelOrderDTO.setStatus(ChannelOrderStatusEnum.FAIL.getCode());
			}
			channelResponseDto.setChannelRespCode(errorCode);
			channelOrderDTO.setErrorCode(errorCode);
			channelOrderDTO.setErrorMsg(channelPaymentResult.getErrorMsg());
			channelOrderDTO.setUpdateDate(new Date());
			channelOrderDTO.setCompleteDate(new Date());
			channelOrderDTO.setAuthorisation(channelPaymentResult
					.getAuthorisation());
			channelOrderDTO.setReturnNo(channelPaymentResult.getChannelReturnNo());
			//中行moto migs不传订单号，传参考号
			channelOrderDTO.setReferenceNo(channelPaymentResult.getReferenceNo());
			logger.info("获取参考号referenceNo:"+channelPaymentResult.getReferenceNo());
			channelOrderDTO.setCardOrg(paymentInfo.getCardOrg());
			boolean updateFlg = channelOrderService.updateChannelProtocolRnTx(
					channelOrderDTO, ChannelOrderStatusEnum.INIT.getCode());

			logger.info("channel order update result:" + updateFlg
					+ ",channelOrderNo:" + channelOrderNo + ",orgCode="+paymentChannelItemDto.getOrgCode());
			
			if(ResponseCodeEnum.ZFZZ.getCode().equals(errorCode)){//组装支付数据
				//如果是装在支付数据的先不更新渠道订单状态，等异步通知来了再更新
				channelResponseDto.setResponseCode(errorCode);
				channelResponseDto.setResponseDesc(channelPaymentResult.getErrorMsg());
				channelResponseDto.setDataMap(channelPaymentResult.getDataMap());
				return channelResponseDto;
			}

	        //渠道返回消息转换
			//Mack comment below line 按渠道包装码
			String resultCode = "3099";
			String resultMsg = "Other errors:其他异常";
			//Add by yangjian 2016-08-24
			if(errorCode.equals("0001")&&paymentChannelItemDto.getOrgCode().equals("10081016")){
				logger.info("前海万融:"+errorCode+"-"+channelPaymentResult.getErrorMsg());
				resultCode = errorCode;
				resultMsg = channelPaymentResult.getErrorMsg();
			}else{
				SysRespCodeEnum srce = SysRespCodeEnum.getResponseCodeEnum(errorCode, paymentChannelItemDto.getOrgCode());
				if(srce!=null){
					resultCode = srce.getRespCode();
					resultMsg = srce.getRespDescEn()+":"+srce.getRespDEscCn();
				}
			}
			channelResponseDto.setResponseCode(resultCode);
			channelResponseDto.setResponseDesc(resultMsg);
			
			// 记账处理
			if (ResponseCodeEnum.SUCCESS.getCode().equals(errorCode) && updateFlg) {
				Long amount = channelOrderDTO.getPayAmount();
				Long payAmount = channelPaymentResult.getPayAmount();
				if (null == payAmount) {
					logger.error("return payAmount must be not null: "+ExceptionCodeEnum.ILLEGAL_PARAMETER);
					//这地方要加个报警、但是已经支付成功了
					Map<String,String> data = new HashMap<String, String>();
					data.put("first","系统异常，返回的支付金额为空,txncore:ChannelServiceImpl");
					data.put("keyword1","返回的支付金额为空");
					SimpleDateFormat formatter = new SimpleDateFormat(
							"yyyy-MM-dd HH:mm:ss");
			        data.put("keyword2",formatter.format(new Date()));
			        data.put("remark","请尽快检查！");					
					this.sendAlertMsg(data);				
					return channelResponseDto;
				}
		
				if (payAmount < amount) {
					logger.error("channelOrderNo:"
							+ channelOrderDTO.getChannelOrderNo() + "payAmount:"
							+ payAmount + ",amount:" + amount);
					logger.error("payAmount error: "+ExceptionCodeEnum.ORDERAMOUNT_ERROR);
					Map<String,String> data = new HashMap<String, String>();
					data.put("first","系统异常: 支付订单金额小于渠道订单金额,,txncore:ChannelServiceImpl");
					data.put("keyword1","返回的支付金额为空");
					SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			        data.put("keyword2",formatter.format(new Date()));
			        data.put("remark","请尽快检查！");	
					this.sendAlertMsg(data);
					return channelResponseDto;
					//这地方要加个报警、但是已经支付成功了 
				}
				try {
					logger.info("orderId: "+paymentInfo.getOrderId()+ new Date());
					accountingService.doAccounting(buildAccountingVo(channelOrderDTO,paymentInfo.getOrderId()));
					logger.info(" doAccounting 101 start: "+ new Date());  
					String orderId = channelOrderDTO.getChannelOrderNo() + "";
					doAccounting_100_101(channelOrderDTO.getOrgCode(), orderId,
							channelOrderDTO.getAmount(),channelOrderDTO.getCurrencyCode(),paymentInfo.getOrderId());
					logger.info(" doAccounting 102 start: "+ new Date()); 
					doAccounting_100_102(channelOrderDTO.getOrgCode(), orderId,
							channelOrderDTO.getAmount(),channelOrderDTO.getTransferCurrencyCode(),channelOrderDTO.getTransferRate(),paymentInfo.getOrderId());
					logger.info(" doAccounting 102 end: "+ new Date()); 
				} catch (Exception e) {
					logger.error("do channel accounting error:" + channelOrderDTO.getChannelOrderNo(), e);
					logger.error("call pe error: "+ExceptionCodeEnum.PE_ERROR.getCode());
					//这地方要加个报警、这个地方是更新本地账户余额失败、但是已经支付成功了					
					return channelResponseDto;
				}
			}
			return channelResponseDto;
	}
		
	public void  doAccounting(ChannelOrderDTO channelOrderDTO,String merchantOrderId){
		try {

			accountingService
					.doAccounting(buildAccountingVo(channelOrderDTO,merchantOrderId));
			String orderId = channelOrderDTO.getChannelOrderNo() + "";
			doAccounting_100_101(channelOrderDTO.getOrgCode(), orderId,
					channelOrderDTO.getAmount(),
					channelOrderDTO.getCurrencyCode(),merchantOrderId);
			doAccounting_100_102(channelOrderDTO.getOrgCode(), orderId,
					channelOrderDTO.getAmount(),
					channelOrderDTO.getTransferCurrencyCode(),
					channelOrderDTO.getTransferRate(),merchantOrderId);
		} catch (Exception e) {
			logger.error("do channel accounting error:" + channelOrderDTO.getChannelOrderNo(), e);
			logger.error("call pe error: "+ExceptionCodeEnum.PE_ERROR.getCode());
			Map<String,String> data = new HashMap<String, String>();

			data.put("first","系统异常: 支付后更新余额记账失败,txncore:ChannelServiceImpl");
			data.put("keyword1","支付后更新余额记账失败");
			SimpleDateFormat formatter = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
	        data.put("keyword2",formatter.format(new Date()));
	        data.put("remark","请尽快检查！");
			
			this.sendAlertMsg(data);
		}
	}
	
	
	private void sendAlertMsg(Map<String,String> data){
		WeiXinNotifyRequest request = new WeiXinNotifyRequest();
        request.setBizCode("0015");
        request.setOpenId("0000");
        request.setType(RequestType.WEIXIN);
		
		request.setData(data);
		jmsSender.send(request);
	}
	
	/**
	 * 系统存活探测
	 * @param paymentOrderDTO
	 * @param paymentInfo
	 * @return
	 */
	public ChannelResponseDto systemDetected(PaymentOrderDTO paymentOrderDTO,
			PaymentInfo paymentInfo) {

		ChannelResponseDto channelResponseDto = new ChannelResponseDto();

		String partnerId = paymentInfo.getPartnerId();
		String paymentType = PaymentTypeEnum.PAYMENT.getCode();
		String transType = paymentOrderDTO.getPayType();

		String currencyCode = paymentInfo.getCurrencyCode();
		if (CredoraxCurrencyCodeEnum.isNeedChange(currencyCode)) {
			currencyCode = CurrencyCodeEnum.USD.getCode();
		}

		// 保存渠道订单
		List<PaymentChannelItemDto> pcidList = channelClientService
				.queryChannels(partnerId,
						String.valueOf(paymentOrderDTO.getMemberType()),
						paymentType, transType, currencyCode, paymentInfo.getSiteId(),null,paymentInfo.getPrdtCode());
        
		String error="";
		String orgCode = paymentInfo.getOrgCode();
		
		if(pcidList!=null&&pcidList.size()>0){
			for(PaymentChannelItemDto pcid: pcidList){
				String channelOrderNo = "111"+System.currentTimeMillis();
				int serialNoInt = (int)(1+Math.random()*(10-1+1));
				String serialNo =this.getSerial(serialNoInt);
				
				paymentInfo.setChannelOrderNo(channelOrderNo);
				paymentInfo.setOrgCode(pcid.getOrgCode());
				paymentInfo.setSerialNo(serialNo);
				BeanUtils.copyProperties(pcid, paymentInfo,
						new String[] { "currencyCode", "serialNo" });

				logger.info("payment orgCode: "+orgCode+" ,pcid-orgCode: "+pcid.getOrgCode());
				
				if(pcid.getOrgCode().equals(orgCode)){
					// 调用前置完成支付
					String result = channelClientService
							.systemDetected(paymentInfo);
					
					channelResponseDto.setResponseCode(result);
					logger.info("result: "+result+",The Channel is Testing : "+pcid);
				}
			}
			
			if(!StringUtil.isEmpty(error)){
				channelResponseDto.setResponseCode(error);
			}
			
		}else{
			channelResponseDto.setResponseCode("9999");
			return channelResponseDto;
		}
		
		return channelResponseDto;
		
	}

	public void setChannelOrderService(ChannelOrderService channelOrderService) {
		this.channelOrderService = channelOrderService;
	}

	public void setAccountingService(AccountingService accountingService) {
		this.accountingService = accountingService;
	}

	public void setAccounting_100_101(AccountingService accounting_100_101) {
		this.accounting_100_101 = accounting_100_101;
	}

	public void setAccounting_100_102(AccountingService accounting_100_102) {
		this.accounting_100_102 = accounting_100_102;
	}

	public void setChannelClientService(
			ChannelClientService channelClientService) {
		this.channelClientService = channelClientService;
	}
	
	/**
	 * 构建渠道订单及送渠道支付数据
	 * @param paymentInfo
	 * @param paymentChannelItemDto
	 * @return
	 */
	private ChannelOrderDTO buildChannelOrder(PaymentInfo paymentInfo,
			PaymentChannelItemDto paymentChannelItemDto) {
		ChannelOrderDTO channelOrderDTO = new ChannelOrderDTO();
		String currencyCode = paymentInfo.getCurrencyCode();
		String orderAmount = paymentInfo.getOrderAmount();
		String prdtCode = paymentInfo.getPrdtCode();
		String payType = paymentInfo.getPayType();
		String cardNo = paymentInfo.getCardHolderNumber();
		String markup = paymentInfo.getMarkup();

		BigDecimal amount = new BigDecimal(paymentInfo.getOrderAmount());
		String channelPayType = paymentInfo.getChannelPayType();

		channelOrderDTO.setCurrencyCode(paymentInfo.getCurrencyCode());
		channelOrderDTO.setTransferCurrencyCode(paymentInfo.getCurrencyCode());
		channelOrderDTO.setAmount(amount.longValue());
		channelOrderDTO.setPayAmount(amount.longValue());
		channelOrderDTO.setTransferRate("1");
		channelOrderDTO.setTerminalCode(paymentChannelItemDto.getTerminalCode());
		logger.info("订单号：PaymentOrderNo:" + paymentInfo.getPaymentOrderNo());
		
		//获取送渠道币种
		String channelCurrencyCode = paymentInfo.getChannelCurrencyCode();
		//设置渠道支付币种
		channelOrderDTO.setTransferCurrencyCode(channelCurrencyCode);
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("currency",currencyCode);
		param.put("sourceCurrency",currencyCode);
		param.put("status","1");
		param.put("prdtCode",prdtCode);
		param.put("payType",payType);
		param.put("memberCode",paymentInfo.getPartnerId());
		if(!StringUtil.isEmpty(cardNo)){
			param.put("cardOrg",this.getCardType(cardNo));
		}
		param.put("orderAmount",paymentInfo.getOrderAmount());
		param.put("ltaCurrencyCode","USD");
		param.put("point",getTime());
		param.put("targetCurrency",channelCurrencyCode);
		
		if(!StringUtil.isEmpty(paymentInfo.getCardCountry())){
			param.put("cardCurrencyCode",paymentInfo.getCurrencyCode());
		}
		
		String f = paymentInfo.getFloatValue();//汇率浮动值
		BigDecimal floatValue = (f==null? new BigDecimal("0.0"):new BigDecimal(f));
		//判断交易是否做cardBin的过滤
		boolean isFilter = false;
		logger.info("payType: "+payType);
		//卡bin 校验，该情况只有走EDC的情况下有效，在EDC的情况下去查询该卡号有没有被过滤
		//被过滤的卡在做交易的时候交易汇率取清算的 add by peiyu.yang 2016年4月19日15:42:32
		if("EDC".equals(payType)&&!StringUtil.isEmpty(cardNo)){
			Map<String,Object> params_=new HashMap<String, Object>();
			params_.put("cardNo",cardNo);
			List<CardFilterDTO> list =   cardFilterService.queryCardFilter(params_);
			logger.info("cardfilter-list: "+list);
			if(list!=null&&!list.isEmpty()){
				isFilter=true;
			}
		}
		//查询交易汇率
		TransactionRate rate = this.getTransactionRate(param, isFilter, 
				currencyCode, channelCurrencyCode,markup);
		logger.info("transRate: "+rate);
		//检查获取的汇率,如果找不到汇率
		if(rate==null||StringUtil.isEmpty(rate.getExchangeRate())){
			throw new BusinessException("transactionRate not exists",
					ExceptionCodeEnum.OTHER_ERROR);
		}
		Long amount_ = 0L;
		if("DCC".equals(payType)){
			paymentInfo.setCurrencyCode(channelCurrencyCode);
			BigDecimal payAmount = new BigDecimal("0");
			BigDecimal rateB = new BigDecimal(rate.getExchangeRate());
			payAmount = rateB.multiply(new BigDecimal(paymentInfo.getOrderAmount())
			                                     .multiply(new BigDecimal("0.1")));
			DecimalFormat df = new DecimalFormat("##.##");
			BigDecimal amountFor = payAmount.divide(
					new BigDecimal("100"));
			String amountForStr = df.format(amountFor);
			amount_ = new BigDecimal(amountForStr).multiply(new BigDecimal("1000")).longValue();
			channelOrderDTO.setTransferRate(rateB.toString());
			channelOrderDTO.setOriginalRate(rate.getExchangeRate());
			channelOrderDTO.setFloatValue(rate.getMarkup());
			
			//对于小币种把小数点后面的都抹去
			Long amountLoc=0L;
			if ("BYR".equals(channelCurrencyCode) || "JPY".equals(channelCurrencyCode)
					|| "KRW".equals(channelCurrencyCode) || "VND".equals(channelCurrencyCode)) {
				amountLoc = new BigDecimal(amount_).divide(new BigDecimal("1000"))
						.longValue();
				amountLoc = new BigDecimal(amountLoc).multiply(new BigDecimal("1000")).longValue();
			}else{
				amountLoc = amount_;
			}
			channelOrderDTO.setPayAmount(amountLoc);
			paymentInfo.setOrderAmount(String.valueOf(amount_));
			
			//如果渠道配置是EDC配置的，或者是DCC的配置的设置
			/**
			 *这个判断目前是针对采用渠道DCC和自建DCC的时候用的
			 */
			if("EDC".equals(channelPayType)){
				paymentInfo.setPayType("EDC");
				paymentInfo.setTransType("EDC");
				paymentInfo.setDccAmount(null);
				paymentInfo.setDccCurrencyCode(null);
			}else if("DCC".equals(channelPayType)){
			    paymentInfo.setTransType("DCC");
			    paymentInfo.setPayType("DCC");
			}	
		}else if("EDC".equals(payType)){
			BigDecimal rateO = new BigDecimal(rate.getExchangeRate());
			BigDecimal tmp = rateO.add(rateO.multiply(floatValue));
			amount = new BigDecimal(orderAmount).multiply(tmp);
			long tAmount = amount.longValue();
			String amountStr = String.valueOf(tAmount);
			if (!amountStr.endsWith("0")) {
				logger.info("paymentInfo paymentOrderNo:"
						+ paymentInfo.getPaymentOrderNo() + "amountStr:"
						+ amountStr);
				tAmount = amount.divide(new BigDecimal(10)).longValue() * 10 + 10;// 厘如果不为0，则向前进1
			}
			channelOrderDTO.setOriginalRate(rate.getExchangeRate());
			channelOrderDTO.setTransferRate(tmp.toString());
			paymentInfo.setCurrencyCode(channelCurrencyCode);
			paymentInfo.setOrderAmount(String.valueOf(tAmount));
			channelOrderDTO.setPayAmount(tAmount);
			channelOrderDTO.setTransferCurrencyCode(channelCurrencyCode);
			channelOrderDTO.setFloatValue(f);
		}

		long serialNo = channelOrderService
				.getMaxChannelSerialNo(paymentChannelItemDto.getOrgCode());
		String serialStr = getSerial(serialNo + 1);
		channelOrderDTO.setSerialNo(serialStr);
		
		channelOrderDTO.setCreateDate(new Date());
		channelOrderDTO.setMerchantNo(paymentChannelItemDto
				.getOrgMerchantCode());
		channelOrderDTO.setOrgCode(paymentChannelItemDto.getOrgCode());
		channelOrderDTO.setPaymentOrderNo(Long.valueOf(paymentInfo
				.getPaymentOrderNo()));
		channelOrderDTO.setStatus(ChannelOrderStatusEnum.INIT.getCode());
		channelOrderDTO.setUpdateDate(new Date());
		channelOrderDTO.setReconciliationFlg(0);	
		return channelOrderDTO;
		
	}	
	
	private ChannelOrderDTO buildChannelOrder_(PaymentInfo paymentInfo,
			PaymentChannelItemDto paymentChannelItemDto) {

		ChannelOrderDTO channelOrderDTO = new ChannelOrderDTO();

		String currencyCode = paymentInfo.getCurrencyCode();
		String orgCode = paymentChannelItemDto.getOrgCode();
		String orderAmount = paymentInfo.getOrderAmount();
		BigDecimal amount = new BigDecimal(paymentInfo.getOrderAmount());
		String cardNo = paymentInfo.getCardHolderNumber();
		logger.info("menthod:buildChannelOrder,currencyCode:"+currencyCode
				+",orgCode:"+orgCode+",orderAmount: "+orderAmount);

		channelOrderDTO.setCurrencyCode(paymentInfo.getCurrencyCode());
		channelOrderDTO.setTransferCurrencyCode(paymentInfo.getCurrencyCode());
		channelOrderDTO.setAmount(amount.longValue());
		channelOrderDTO.setPayAmount(amount.longValue());
		channelOrderDTO.setTransferRate("1");
		channelOrderDTO.setTerminalCode(paymentChannelItemDto.getTerminalCode());
		logger.info("订单号：PaymentOrderNo:" + paymentInfo.getPaymentOrderNo());
		
		Map<String,Object> param = new HashMap<String, Object>();
		param.put("currency",paymentInfo.getCurrencyCode());
		param.put("status","1");
		param.put("memberCode",paymentInfo.getPartnerId());
		//ADD BY tom FOR LOCAL
		if(!StringUtil.isEmpty(paymentInfo.getCardHolderNumber())){
			param.put("cardOrg",this.getCardType(paymentInfo.getCardHolderNumber()));
		}
		param.put("orderAmount",paymentInfo.getOrderAmount());
		param.put("ltaCurrencyCode","USD");
		param.put("point",getTime());
		//ADD BY tom FOR LOCAL
		if(!StringUtils.isEmpty(cardNo)){
		String cardBin = cardNo.substring(0, 6);
		CardBinInfo info = null;//cardBinInfoService.getCardBinInfo(cardBin);
		
		if(info!=null){
			if(!CountryCodeEnum.isExists(info.getCountryCode3())){
				param.put("cardCountry","OOO");
			}else{
				param.put("cardCountry",info.getCountryCode3());
			}
			if(!StringUtil.isEmpty(info.getCurrencyCode())){
				if("EUR".equals(info.getCurrencyCode())){
					param.put("cardCountry","EUR");
				}
				param.put("cardCurrencyCode",info.getCurrencyCode());
			}
		}
	}
		String f = paymentInfo.getFloatValue();//汇率浮动值
		channelOrderDTO.setFloatValue(f);
		BigDecimal floatValue = (f==null? new BigDecimal("0.0"):new BigDecimal(f));
		
		TransactionRate transRate=null;
		
		String payType= paymentInfo.getPayType();
		
		boolean isDccFlag=false;
		Long amount_ = 0L;
		
		//如果采用自有DCC相关产品,仍然后走EDC但是送渠道币种为卡本币
		if((DCCEnum.CUSTOM_STANDARD.getCode()
				.equals(paymentInfo.getPrdtCode())&&"DCC".equals(payType))||
		   DCCEnum.CUSTOM_FORCED.getCode()
		        .equals(paymentInfo.getPrdtCode())||
		   DCCEnum.CUSTOM_HIDDEN.getCode()
		        .equals(paymentInfo.getPrdtCode())||
		   DCCEnum.PARTNER_DCC_PRDCT.getCode()
		        .equals(paymentInfo.getPrdtCode())){
			
			String oldCurrencyCode = paymentInfo.getCurrencyCode(); 
			String dccCurrencyCode = paymentInfo.getDccCurrencyCode();
			
			logger.info("用户卡本币-DCC-CurrencyCode: "+dccCurrencyCode);
			
			/**
			 * 如果用户的卡本币在渠道那边不支持
			 * 目前都选择送USD
			 * peiyu.yang add 2016年3月12日14:54:58
			 */
			//Mack add CYBS CTV
			if((ChannelItemOrgCodeEnum.ABC.getCode().equals(orgCode)
					&&!ABCCurrencyCodeEnum.isExists(dccCurrencyCode))||
					(ChannelItemOrgCodeEnum.CYBSCTV.getCode().equals(orgCode)
							&&!CybsCtvCurrencyCodeEnum.isExists(dccCurrencyCode))){
				dccCurrencyCode = "USD";
			}else if(ChannelItemOrgCodeEnum.CREDORAX.getCode().equals(orgCode)&&
					CredoraxCurrencyCodeEnum.isNeedChange(dccCurrencyCode)){
				dccCurrencyCode = "USD";
			}else if(ChannelItemOrgCodeEnum.BOCS.getCode().equals(orgCode)||
					ChannelItemOrgCodeEnum.BOCM.getCode().equals(orgCode)
					||ChannelItemOrgCodeEnum.BOCI.getCode().equals(orgCode)){
				dccCurrencyCode = "CNY";
			}
			isDccFlag = true;
			channelOrderDTO.setTransferCurrencyCode(dccCurrencyCode);
			
			TransactionBaseRate baseRate = currencyRateService.findTransactionBaseRate(
					oldCurrencyCode,dccCurrencyCode,"1",null);

			logger.info("currencyCode: "+paymentInfo.getCurrencyCode()+" ,DccCurrencyCode: "+dccCurrencyCode);
			
			if(baseRate==null){
				throw new BusinessException("transactionRate not exists",
						ExceptionCodeEnum.OTHER_ERROR);
			}
			
			logger.info("baseRate: "+baseRate.getExchangeRate());
			paymentInfo.setCurrencyCode(dccCurrencyCode);
			//获取该币种的
			Map<String,Object> param_ = new HashMap<String, Object>();
			param_.put("partnerId",paymentInfo.getPartnerId());
			param_.put("currencyCode",dccCurrencyCode);
			
			//已经不再使用了。
			PartnerDCCConfig dccConfig = new PartnerDCCConfig();//dccService.getDccConfig(param_);
			
			BigDecimal payAmount = new BigDecimal("0");
			if(dccConfig!=null){
				String markupStr = dccConfig.getMarkUp();
				BigDecimal markup = null;
				try{
					markup = new BigDecimal(markupStr);
				}catch(Exception e){
					markup = new BigDecimal("0");
				}				
				BigDecimal rate_ = new BigDecimal(baseRate.getExchangeRate());
				BigDecimal rate = rate_.add(rate_.multiply(markup.multiply(new BigDecimal("0.01"))));
				payAmount = rate.multiply(new BigDecimal(paymentInfo.getOrderAmount())
				                                     .multiply(new BigDecimal("0.1")));
				DecimalFormat df = new DecimalFormat("##.##");
				BigDecimal amountFor = payAmount.divide(
						new BigDecimal("100"));
				String amountForStr = df.format(amountFor);
				amount_ = new BigDecimal(amountForStr).multiply(new BigDecimal("1000")).longValue();
				channelOrderDTO.setTransferRate(rate.toString());
			}
			Long amountLoc=0L;
			logger.info("amount_: "+amount_);
			if ("BYR".equals(dccCurrencyCode) || "JPY".equals(dccCurrencyCode)
					|| "KRW".equals(dccCurrencyCode) || "VND".equals(dccCurrencyCode)) {
				amountLoc = new BigDecimal(amount_).divide(new BigDecimal("1000"))
						.longValue();
				amountLoc = new BigDecimal(amountLoc).multiply(new BigDecimal("1000")).longValue();
			}else{
				amountLoc = amount_;
			}
			logger.info("amountLoc: "+amountLoc);
			channelOrderDTO.setPayAmount(amountLoc);
			paymentInfo.setOrderAmount(String.valueOf(amount_));
		}
		boolean isFilter = false;		
		logger.info("isDccFlag: "+isDccFlag);
		
		//卡bin 校验，该情况只有走EDC的情况下有效，在EDC的情况下去查询该卡号有没有被过滤
		//被过滤的卡在做交易的时候交易汇率取清算的 add by peiyu.yang 2016年4月19日15:42:32
		if(!isDccFlag){
			Map<String,Object> params_=new HashMap<String, Object>();
			params_.put("cardNo",paymentInfo.getCardHolderNumber());
			List<CardFilterDTO> list =   cardFilterService.queryCardFilter(params_);
			logger.info("cardfilter-list: "+list);
			if(list!=null&&!list.isEmpty()){
				isFilter=true;
			}
		}
		if (ChannelItemOrgCodeEnum.BOCS.getCode().equals(orgCode)||
				ChannelItemOrgCodeEnum.BOCM.getCode().equals(orgCode)
				||ChannelItemOrgCodeEnum.BOCI.getCode().equals(orgCode)) {// 卡司,中银MOTO
			String rate = "1";
				param.put("targetCurrency",CurrencyCodeEnum.CNY.getCode());
				transRate = this.getTransactionRate(param,isFilter,currencyCode,
						                            CurrencyCodeEnum.CNY.getCode(),"");
				//---------------------------
				if (null == transRate) {
					throw new BusinessException("transactionRate not exists",
							ExceptionCodeEnum.OTHER_ERROR);
				} else {
					rate = transRate.getExchangeRate();
					logger.info("获取到交易汇率,订单号：PaymentOrderNo:"
							+ paymentInfo.getPaymentOrderNo() + "，交易汇率为：" + rate);
				}

				BigDecimal rateO = new BigDecimal(rate);
				BigDecimal tmp = rateO.add(rateO.multiply(floatValue));
				
				amount = new BigDecimal(orderAmount).multiply(tmp);
				long tAmount = amount.longValue();
				String amountStr = String.valueOf(tAmount);
				
				if (!amountStr.endsWith("0")) {
					logger.info("paymentInfo paymentOrderNo:"
							+ paymentInfo.getPaymentOrderNo() + "amountStr:"
							+ amountStr);
					tAmount = amount.divide(new BigDecimal(10)).longValue() * 10 + 10;// 厘如果不为0，则向前进1
				}
				channelOrderDTO.setOriginalRate(rate);
				channelOrderDTO.setTransferRate(tmp.toString());
				paymentInfo.setCurrencyCode(CurrencyCodeEnum.CNY.getCode());
				paymentInfo.setOrderAmount(String.valueOf(tAmount));
				channelOrderDTO.setPayAmount(tAmount);
				channelOrderDTO.setTransferCurrencyCode(CurrencyCodeEnum.CNY
						.getCode());
		} else if( (ChannelItemOrgCodeEnum.ABC.getCode().equals(orgCode))||
				(ChannelItemOrgCodeEnum.CYBSCTV.getCode().equals(orgCode)) ){
			// add code to support CYBS CTV on upper line 2016年5月24日
			String rate = "1";
			logger.info("currencyCode: "+currencyCode+" ,isDccFlag: "+isDccFlag);
			if(!isDccFlag){
				param.put("targetCurrency",CurrencyCodeEnum.CNY.getCode());
                transRate = this.getTransactionRate(param, isFilter, currencyCode,
                		            CurrencyCodeEnum.CNY.getCode(),"");
				if (null == transRate) {
					throw new BusinessException("transactionRate not exists",
							ExceptionCodeEnum.OTHER_ERROR);
				} else {
					rate = transRate.getExchangeRate();
					logger.info("获取到交易汇率,订单号：PaymentOrderNo:"
							+ paymentInfo.getPaymentOrderNo() + "，交易汇率为：" + rate);
				}
				BigDecimal rateO = new BigDecimal(rate);
				BigDecimal tmp = rateO.add(rateO.multiply(floatValue));
				
				amount = new BigDecimal(orderAmount).multiply(tmp);
				long tAmount = amount.longValue();
				String amountStr = String.valueOf(tAmount);
				
				if (!amountStr.endsWith("0")) {
					logger.info("paymentInfo paymentOrderNo:"
							+ paymentInfo.getPaymentOrderNo() + "amountStr:"
							+ amountStr);
					tAmount = amount.divide(new BigDecimal(10)).longValue() * 10 + 10;// 厘如果不为0，则向前进1
				}
				channelOrderDTO.setOriginalRate(rate);
				channelOrderDTO.setTransferRate(tmp.toString());
				paymentInfo.setCurrencyCode(CurrencyCodeEnum.CNY.getCode());
				paymentInfo.setOrderAmount(String.valueOf(tAmount));
				channelOrderDTO.setPayAmount(tAmount);
				channelOrderDTO.setTransferCurrencyCode(CurrencyCodeEnum.CNY
						.getCode());
			}
			
		}else if(ChannelItemOrgCodeEnum.CREDORAX.getCode().equals(orgCode)) {
			boolean isCredoraxNotExists = false;
			// 不支持的币种
			if (!CredoraxCurrencyCodeEnum.isExists(currencyCode)) {
				isCredoraxNotExists = true;
				logger.info("Credorax unSupport currencCode: "+currencyCode+",isCredoraxNotExists: "+isCredoraxNotExists);
			}
            //2016年4月27日09:46:57 peiyu.yang 新增了对credorax不支持的币种转成USD送过去。
			if (CredoraxCurrencyCodeEnum.isNeedChange(currencyCode)||isCredoraxNotExists) {// 需要转换的币种或者credorax不支持的币种都转成USD传过去
				paymentInfo.setCurrencyCode(CurrencyCodeEnum.USD.getCode());
				String rate = "1";
				param.put("sourceCurrency",currencyCode);
				param.put("currency",currencyCode);
				param.put("targetCurrency",CurrencyCodeEnum.USD.getCode());
				
				transRate = this.getTransactionRate(param, isFilter, currencyCode,
						CurrencyCodeEnum.USD.getCode(),"");
				if (null == transRate) {
					logger.info("未取到机构汇率，订单号：PaymentOrderNo:"
							+ paymentInfo.getPaymentOrderNo());
					throw new BusinessException("transactionRate not exists",
							ExceptionCodeEnum.OTHER_ERROR);
				} else {
					rate = transRate.getExchangeRate();
				}
				BigDecimal rateO = new BigDecimal(rate);
				BigDecimal tmp = rateO.add(rateO.multiply(floatValue));
				
				amount = new BigDecimal(orderAmount).multiply(tmp);
				long tAmount = amount.longValue();
				paymentInfo.setOrderAmount(String.valueOf(tAmount));
				channelOrderDTO.setTransferCurrencyCode(CurrencyCodeEnum.USD
						.getCode());
				channelOrderDTO.setTransferRate(tmp.toString());
				channelOrderDTO.setPayAmount(tAmount);
				channelOrderDTO.setOriginalRate(rate);
			}

		}else {
			channelOrderDTO.setTransferRate("1");
			channelOrderDTO.setPayAmount(amount.longValue());
			channelOrderDTO.setOriginalRate("1");
		}

		long serialNo = channelOrderService
				.getMaxChannelSerialNo(paymentChannelItemDto.getOrgCode());
		String serialStr = getSerial(serialNo + 1);
		channelOrderDTO.setSerialNo(serialStr);
		
		channelOrderDTO.setCreateDate(new Date());
		channelOrderDTO.setMerchantNo(paymentChannelItemDto
				.getOrgMerchantCode());
		channelOrderDTO.setOrgCode(paymentChannelItemDto.getOrgCode());
		channelOrderDTO.setPaymentOrderNo(Long.valueOf(paymentInfo
				.getPaymentOrderNo()));
		channelOrderDTO.setStatus(ChannelOrderStatusEnum.INIT.getCode());
		channelOrderDTO.setUpdateDate(new Date());
		channelOrderDTO.setReconciliationFlg(0);
		
		if(isDccFlag){
			paymentInfo.setPayType("EDC");
			paymentInfo.setDccAmount(null);
			paymentInfo.setDccCurrencyCode(null);
		}
		
		return channelOrderDTO;
	}
	
	/**
	 * 获取交易汇率
	 * @param params
	 * @param isFilter
	 * @return
	 */
	public TransactionRate getTransactionRate(Map<String,Object> params
			,boolean isFilter,String currencyCode,String targetCurrencyCode,String markup){
		TransactionRate  transRate = null;
		if(isFilter){
			SettlementRate tmp = currencyRateService.getNewSettlementRate(params);
			logger.info("settleRate: "+tmp);
			transRate = new TransactionRate();
			if(tmp!=null){
				transRate.setExchangeRate(tmp.getExchangeRate());
			}else{
				TransactionBaseRate baseRate = currencyRateService.
						findTransactionBaseRate(currencyCode,targetCurrencyCode,"1",null);
				if(baseRate==null){
					throw new BusinessException("transactionRate not exists",
							ExceptionCodeEnum.OTHER_ERROR);
				}
				TransactionBaseRate baseRate_ = currencyRateService
						.findTransactionBaseRate(currencyCode,"USD","1", null);
				String orderAmount = (String) params.get("orderAmount");
				if(baseRate_!=null){
					  BigDecimal rate0 = new BigDecimal(baseRate_.getExchangeRate());
					  BigDecimal amount = new BigDecimal(orderAmount).multiply(rate0);
					  params.put("leastTransAmount",amount);
				}
				//markup = this.getMarkup(params);
				logger.info("markup:"+markup);
				if(!StringUtil.isEmpty(markup)){
					BigDecimal rateTmp = new BigDecimal(baseRate.getExchangeRate());
					BigDecimal rate = rateTmp.add(rateTmp.multiply(new BigDecimal(markup)
					             .multiply(new BigDecimal("0.01"))));
					transRate.setExchangeRate(rate.toString());
				}else{
					transRate.setExchangeRate(baseRate.getExchangeRate());
				}
			}
		}else{
			TransactionBaseRate baseRate = currencyRateService.findTransactionBaseRate(
					currencyCode,targetCurrencyCode,"1",null);
			if(baseRate==null){
				throw new BusinessException("transactionRate not exists",
						ExceptionCodeEnum.OTHER_ERROR);
			}
			transRate = new TransactionRate();
			TransactionBaseRate baseRate_ = currencyRateService
					.findTransactionBaseRate(currencyCode,"USD","1", null);
			String orderAmount = (String) params.get("orderAmount");
			if(baseRate_!=null){
				  BigDecimal rate0 = new BigDecimal(baseRate_.getExchangeRate());
				  BigDecimal amount = new BigDecimal(orderAmount).multiply(rate0)
						  .multiply(new BigDecimal("0.001"));
				  params.put("leastTransAmount",amount);
			}
			//markup = this.getMarkup(params);
			logger.info("markup:"+markup);
			if(!StringUtil.isEmpty(markup)){
				BigDecimal rateTmp = new BigDecimal(baseRate.getExchangeRate());
				BigDecimal rate = rateTmp.add(rateTmp.multiply(new BigDecimal(markup)
				             .multiply(new BigDecimal("0.01"))));
				transRate.setExchangeRate(rate.toString());
			}else{
				transRate.setExchangeRate(baseRate.getExchangeRate());
			}
		}
		
		return transRate;
	}
	
	private String getCardType(String cardNo){
		int cardLen = cardNo.length();
		
		if(cardLen == 16){
			long subCard = Long.valueOf(cardNo.substring(0,6));
			if(subCard>=400000 && subCard <=499999){
				return "VISA";
			}
			//Mack comment below line and add nee line
			//if(subCard>=510000 && subCard <=559999){
		   if((subCard>=510000 && subCard <=559999)||(subCard>=222100 && subCard <=272099)){
				return "MASTER";
			}
			if((subCard>=352800 && subCard <=358999)||(subCard>=213100 && subCard <=213199)||(subCard>=180000 && subCard <=180099)){
				return "JCB";
			}
		}
		if(cardLen == 14){
			long subCard = Long.valueOf(cardNo.substring(0,6));
			if(subCard>=300000 && subCard <=305999){
				return "DC";
			}
			if(subCard>=309500 && subCard <=309599){
				return "DC";
			}
			if(subCard>=360000 && subCard <=369999){
				return "DC";
			}
			if(subCard>=380000 && subCard <=399999){
				return "DC";
			}
		}
		if(cardLen == 15){
			long subCard = Long.valueOf(cardNo.substring(0,6));
			if(subCard>=340000 && subCard <=349999){
				return "AE";
			}
			if(subCard>=370000 && subCard <=379999){
				return "AE";
			}
		}
		return null;
	}
	
	private double getTime(){
        Calendar c = Calendar.getInstance();
        int hour = c.get(Calendar.HOUR_OF_DAY);
        int min = c.get(Calendar.MINUTE);
        double s = min/100.0;
        double rst = hour+s;
        
        return rst;
	}
	
    /**
     * 渠道送什么币种是可以配置
     * @param paymentInfo
     * @param paymentChannelItemDto
     * @return
     */
	private ChannelOrderDTO buildChannelOrder_(PaymentInfo paymentInfo,
			PaymentChannelItemDto paymentChannelItemDto,String channelCurrency) {

		ChannelOrderDTO channelOrderDTO = new ChannelOrderDTO();

		String orderAmount = paymentInfo.getOrderAmount();
		BigDecimal amount = new BigDecimal(paymentInfo.getOrderAmount());

		channelOrderDTO.setCurrencyCode(paymentInfo.getCurrencyCode());
		channelOrderDTO.setTransferCurrencyCode(channelCurrency);
		channelOrderDTO.setAmount(amount.longValue());
		channelOrderDTO.setPayAmount(amount.longValue());
		channelOrderDTO.setTransferRate("1");
		
		logger.info("订单号：PaymentOrderNo:" + paymentInfo.getPaymentOrderNo());
		
		TransactionRate transRate = currencyRateService.getTransactionRate(
				paymentInfo.getCurrencyCode(),
				channelCurrency, "1",
				paymentInfo.getPartnerId(), null);
		
		String rate="1";
		
		if (null == transRate) {
				throw new BusinessException("transactionRate not exists",
						ExceptionCodeEnum.OTHER_ERROR);
		} else {
				rate = transRate.getExchangeRate();
				logger.info("获取到交易汇率,订单号：PaymentOrderNo:"
						+ paymentInfo.getPaymentOrderNo() + "，交易汇率为：" + rate);
		}
			
		amount = new BigDecimal(orderAmount).multiply(new BigDecimal(rate));
		long tAmount = amount.longValue();
		String amountStr = String.valueOf(tAmount);
		if (!amountStr.endsWith("0")) {
			logger.info("paymentInfo paymentOrderNo:"
					+ paymentInfo.getPaymentOrderNo() + "amountStr:"
					+ amountStr);
			tAmount = amount.divide(new BigDecimal(10)).longValue() * 10 + 10;// 厘如果不为0，则向前进1
		}

		channelOrderDTO.setTransferRate(rate);
		paymentInfo.setCurrencyCode(channelCurrency);
		paymentInfo.setOrderAmount(String.valueOf(tAmount));
		channelOrderDTO.setPayAmount(tAmount);
		channelOrderDTO.setTransferCurrencyCode(channelCurrency);
		
		long serialNo = channelOrderService
				.getMaxChannelSerialNo(paymentChannelItemDto.getOrgCode());
		String serialStr = getSerial(serialNo + 1);
		channelOrderDTO.setSerialNo(serialStr);
		
		channelOrderDTO.setCreateDate(new Date());
		channelOrderDTO.setMerchantNo(paymentChannelItemDto
				.getOrgMerchantCode());
		channelOrderDTO.setOrgCode(paymentChannelItemDto.getOrgCode());
		channelOrderDTO.setPaymentOrderNo(Long.valueOf(paymentInfo
				.getPaymentOrderNo()));
		channelOrderDTO.setStatus(ChannelOrderStatusEnum.INIT.getCode());
		channelOrderDTO.setUpdateDate(new Date());
		channelOrderDTO.setReconciliationFlg(0);
		return channelOrderDTO;
	}

	private AccountingDto buildAccountingVo(ChannelOrderDTO channelOrderDTO,String merchantOrderId) {

		AccountingDto accountingDto = new AccountingDto();
		accountingDto.setAmount(channelOrderDTO.getAmount());
		accountingDto.setOrderId(channelOrderDTO.getChannelOrderNo());
		accountingDto.setBankCode(channelOrderDTO.getOrgCode());
		accountingDto.setPayerCurrencyCode(channelOrderDTO.getCurrencyCode());
		accountingDto.setPayeeCurrencyCode(channelOrderDTO.getCurrencyCode());
		accountingDto.setOrderAmount(channelOrderDTO.getAmount());
		accountingDto.setMerchantOrderId(merchantOrderId);
		return accountingDto;
	}

	public void doAccounting_100_101(String orgCode, String orderId,
			Long amount, String currencyCode,String merchantOrderId) throws Exception {

		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(amount);
		accountingDto.setOrderAmount(amount);
		accountingDto.setBankCode(orgCode);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);

		accounting_100_101.doAccounting(accountingDto);
	}

	public void doAccounting_100_102(String orgCode, String orderId,
			Long amount, String currencyCode, String rate,String merchantOrderId) throws Exception {

		BigDecimal aAmount = new BigDecimal(amount).multiply(new BigDecimal(
				rate));
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		accountingDto.setAmount(aAmount);
		accountingDto.setOrderAmount(aAmount);
		accountingDto.setBankCode(orgCode);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accounting_100_102.doAccounting(accountingDto);
	}

	public void doReconciliationAccounting(AccountingService accountingService,
			String orgCode, String orderId, Long amount, String currencyCode,
			String rate,String dealAmount,String channelFee,String merchantOrderId) throws Exception {

		Long fee = 0L;
		AccountingFeeRe accountingDto = new AccountingFeeRe();
		
		//农行订制处理
		
		
		accountingDto.setBankCode(orgCode);
		accountingDto.setPayerCurrencyCode(currencyCode);
		accountingDto.setPayeeCurrencyCode(currencyCode);
		accountingDto.setOrderId(orderId);
		accountingDto.setAmount(amount);
		accountingDto.setMerchantOrderId(merchantOrderId);
		accountingDto.setOrderAmount(amount);
		
		if(ChannelItemOrgCodeEnum.ABC.getCode().equals(orgCode)){
			amount = new BigDecimal(dealAmount).multiply(new BigDecimal("1000")).longValue();
			fee = new BigDecimal(channelFee).multiply(new BigDecimal("1000")).longValue();
			accountingDto.setAmount(amount);
			accountingDto.setOrderAmount(amount);
		}else{
			AccountingFeeRes accountingFeeRes = accountingService
					.caculateFee(accountingDto);
			
			if (null != accountingFeeRes) {
				fee = accountingFeeRes.getPayeeFee();
				logger.info("fee:" + fee);
			}
		}

		BigDecimal dAmount = new BigDecimal(amount - fee)
				.multiply(new BigDecimal(rate));
		BigDecimal fAmount = new BigDecimal(fee).multiply(new BigDecimal(rate));

		accountingDto.setHasCaculatedPrice(true);
		accountingDto.setPayeeFee(fAmount.longValue());
		accountingDto.setAmount(dAmount);
		accountingDto.setOrderAmount(dAmount);

		logger.info("begin do accounting :" + orderId);
		accountingService.doAccounting(accountingDto);
	}

	private String getSerial(long serialNo) {
		String str = String.valueOf(serialNo);
		int l = str.length();
		if (str.length() < 6) {
			for (int i = 0; i < 6 - l; i++) {
				str = "0" + str;
			}
		}
		return str;
	}

	@Override
	public ChannelPreauthResponseDto channelPreauth(
			PreauthOrderDTO paymentOrderDTO, PreauthInfo paymentInfo) {

		ChannelPreauthResponseDto channelResponseDto = new ChannelPreauthResponseDto();

		String partnerId = paymentInfo.getPartnerId();
		String paymentType = PaymentTypeEnum.PREAUTH.getCode();
		String transType = TransTypeEnum.EDC.getCode();

		// 保存渠道订单
		PaymentChannelItemDto paymentChannelItemDto = channelClientService
				.queryChannel(partnerId,
						String.valueOf(paymentOrderDTO.getMemberType()),
						paymentType, transType, null, null,"",paymentInfo.getPrdtCode());

		if (null == paymentChannelItemDto) {
			throw new BusinessException("paymentChannelItemDto was exists",
					ExceptionCodeEnum.ORDER_IS_COMPLETED_OR_SUCCESS);
		}
		
		ChannelOrderDTO channelOrderDTO = buildChannelOrder(paymentInfo,
				paymentChannelItemDto);

		Long channelOrderNo = channelOrderService
				.saveChannelProtocolRnTx(channelOrderDTO);
		
		channelOrderDTO.setChannelOrderNo(channelOrderNo);
		paymentInfo.setChannelOrderNo(String.valueOf(channelOrderNo));
		paymentInfo.setOrgCode(paymentChannelItemDto.getOrgCode());
		

		// 调用前置完成支付
		ChannelPreauthResult channelPaymentResult = channelClientService
				.channelPreauth(paymentInfo, channelOrderDTO);
		logger.info("【channelPaymentResult】： " + channelPaymentResult);
		
		channelResponseDto.setResponseCode(channelPaymentResult.getErrorCode());
		channelResponseDto.setResponseDesc(channelPaymentResult.getErrorMsg());

		channelResponseDto.setOrgCode(paymentChannelItemDto.getOrgCode());
		channelResponseDto.setChannelOrderNo(channelOrderNo);
		channelResponseDto.setAuthCode(channelPaymentResult.getAuthCode());
		channelResponseDto.setAuthorStr(channelPaymentResult.getAuthorStr());
		channelResponseDto.setRefNo(channelPaymentResult.getRefNo());

		return channelResponseDto;
	}

	@Override
	public ChannelPreauthResponseDto channelPreauthCompeleted(
			PreauthOrderDTO paymentOrderDTO, PreauthInfo paymentInfo) {

		ChannelPreauthResponseDto channelResponseDto = new ChannelPreauthResponseDto();

		String partnerId = paymentInfo.getPartnerId();
		String paymentType = PaymentTypeEnum.PREAUTH.getCode();
		String transType = TransTypeEnum.EDC.getCode();
		
		// 保存渠道订单
		PaymentChannelItemDto paymentChannelItemDto = channelClientService
				.queryChannel(partnerId,
						String.valueOf(paymentOrderDTO.getMemberType()),
						paymentType, transType, null, null,"",paymentInfo.getPrdtCode());

		if (null == paymentChannelItemDto) {
			throw new BusinessException("tradeOrder was exists",
					ExceptionCodeEnum.ORDER_IS_COMPLETED_OR_SUCCESS);
		}

		// 根据交易订单查询渠道订单
		ChannelOrderDTO channelOrderDTO = channelOrderService
				.queryByTradeOrderNo(Long.valueOf(paymentInfo.getPaymentOrderNo()));

		if (channelOrderDTO == null) {
			throw new BusinessException("ChannelOrder was not exists",
					ExceptionCodeEnum.UN_KNOWN_EXCEPTION);
		}
 
		paymentInfo.setOrgCode(paymentChannelItemDto.getOrgCode());

		// 调用前置完成支付
		ChannelPreauthResult channelPaymentResult = channelClientService
				.channelPreauthCompeleted(paymentInfo, channelOrderDTO);

		channelResponseDto.setResponseCode(channelPaymentResult.getErrorCode());
		channelResponseDto.setResponseDesc(channelPaymentResult.getErrorMsg());

		channelResponseDto.setOrgCode(paymentChannelItemDto.getOrgCode());
		channelResponseDto.setChannelOrderNo(channelOrderDTO
				.getChannelOrderNo());
		channelResponseDto.setAuthCode(channelPaymentResult.getAuthCode());
		channelResponseDto.setAuthorStr(channelPaymentResult.getAuthorStr());
		channelResponseDto.setRefNo(channelPaymentResult.getRefNo());

		String errorCode = channelPaymentResult.getErrorCode();

		if (ResponseCodeEnum.SUCCESS.getCode().equals(errorCode)) {
			channelOrderDTO.setStatus(ChannelOrderStatusEnum.SUCCESS.getCode());
			channelOrderDTO.setPayAmount(channelPaymentResult.getPayAmount());
			channelResponseDto
					.setPayAmount(channelPaymentResult.getPayAmount());
		} else if (ResponseCodeEnum.FAIL.getCode().equals(errorCode)) {
			channelOrderDTO.setStatus(ChannelOrderStatusEnum.FAIL.getCode());
		}
		
		channelOrderDTO.setUpdateDate(new Date());
		channelOrderDTO.setCompleteDate(new Date());
		channelOrderDTO.setReturnNo(channelPaymentResult.getChannelReturnNo());

		boolean updateFlg = channelOrderService.updateChannelProtocolRnTx(
				channelOrderDTO,
				ChannelOrderStatusEnum.INIT.getCode());

		logger.info("channel order update result:" + updateFlg
				+ ",channelOrderNo:" + channelOrderDTO.getChannelOrderNo());
		
		// 记账处理
		if (ResponseCodeEnum.SUCCESS.getCode().equals(errorCode) && updateFlg) {

			Long amount = channelOrderDTO.getPayAmount();
			Long payAmount = channelPaymentResult.getPayAmount();
			if (null == payAmount) {
				throw new BusinessException(
						"return payAmount must be not null",
						ExceptionCodeEnum.ILLEGAL_PARAMETER);
			}

			if (payAmount < amount) {
				logger.error("channelOrderNo:"
						+ channelOrderDTO.getChannelOrderNo() + "payAmount:"
						+ payAmount + ",amount:" + amount);

				throw new BusinessException("payAmount error",
						ExceptionCodeEnum.ORDERAMOUNT_ERROR);
			}

			try {

				accountingService
						.doAccounting(buildAccountingVo(channelOrderDTO,paymentInfo.getOrderId()));

				String orderId = channelOrderDTO.getChannelOrderNo() + "";
				if ("10076001".equals(channelOrderDTO.getOrgCode())) {
					doAccounting_100_101(channelOrderDTO.getOrgCode(), orderId,
							channelOrderDTO.getAmount(),
							channelOrderDTO.getCurrencyCode(),paymentInfo.getOrderId());

					doAccounting_100_102(channelOrderDTO.getOrgCode(), orderId,
							channelOrderDTO.getAmount(),
							channelOrderDTO.getTransferCurrencyCode(),
							channelOrderDTO.getTransferRate(),paymentInfo.getOrderId());
				}
			} catch (Exception e) {
				logger.error("do channel accounting error:", e);
				channelResponseDto
						.setResponseCode(ExceptionCodeEnum.UNKNOW_ERROR
								.getCode());
				channelResponseDto
						.setResponseDesc(ExceptionCodeEnum.UNKNOW_ERROR
								.getDescription());
				throw new BusinessException("call pe error",
						ExceptionCodeEnum.PE_ERROR);
			}
		}

		return channelResponseDto;
	}

	@Override
	public ChannelPreauthResponseDto channelPreauthRevoke(
			PreauthOrderDTO paymentOrderDTO, PreauthInfo paymentInfo) {
		ChannelPreauthResponseDto channelResponseDto = new ChannelPreauthResponseDto();

		String partnerId = paymentInfo.getPartnerId();
		String paymentType = PaymentTypeEnum.PREAUTH.getCode();
		String transType = TransTypeEnum.EDC.getCode();
		// 保存渠道订单
		PaymentChannelItemDto paymentChannelItemDto = channelClientService
				.queryChannel(partnerId,
						String.valueOf(paymentOrderDTO.getMemberType()),
						paymentType, transType, null, null,"",paymentInfo.getPrdtCode());

		if (null == paymentChannelItemDto) {
			throw new BusinessException("tradeOrder was exists",
					ExceptionCodeEnum.ORDER_IS_COMPLETED_OR_SUCCESS);
		}
		ChannelOrderDTO channelOrderDTO = channelOrderService
				.queryByTradeOrderNo(Long.valueOf(paymentInfo.getTradeOrderNo()));

		if (channelOrderDTO == null) {
			throw new BusinessException("channelOrder was not exists",
					ExceptionCodeEnum.UN_KNOWN_EXCEPTION);
		}

		paymentInfo.setOrgCode(paymentChannelItemDto.getOrgCode());
		paymentInfo.setChannelOrderNo(String.valueOf(channelOrderDTO
				.getChannelOrderNo()));

		// 调用前置完成支付
		ChannelPreauthResult channelPaymentResult = channelClientService
				.channelPreauthRevoke(paymentInfo, channelOrderDTO);

		channelResponseDto.setResponseCode(channelPaymentResult.getErrorCode());
		channelResponseDto.setResponseDesc(channelPaymentResult.getErrorMsg());

		channelResponseDto.setOrgCode(paymentChannelItemDto.getOrgCode());
		channelResponseDto.setChannelOrderNo(channelOrderDTO
				.getChannelOrderNo());
		channelResponseDto.setAuthCode(channelPaymentResult.getAuthCode());
		channelResponseDto.setAuthorStr(channelPaymentResult.getAuthorStr());
		channelResponseDto.setRefNo(channelPaymentResult.getRefNo());

		String errorCode = channelPaymentResult.getErrorCode();
		int status = -1;
		if (ResponseCodeEnum.SUCCESS.getCode().equals(errorCode)) {
			channelOrderDTO
					.setStatus(ChannelOrderStatusEnum.SUCCESS_PREAUTH_REVOKE
							.getCode());
			status = ChannelOrderStatusEnum.SUCCESS_PREAUTH_REVOKE.getCode();
		} else if (ResponseCodeEnum.FAIL.getCode().equals(errorCode)) {
			channelOrderDTO
					.setStatus(ChannelOrderStatusEnum.FAIL_PREAUTH_REVOKE
							.getCode());
			status = ChannelOrderStatusEnum.FAIL_PREAUTH_REVOKE.getCode();
		}

		channelOrderDTO.setUpdateDate(new Date());
		channelOrderDTO.setCompleteDate(new Date());
		channelOrderDTO.setReturnNo(channelPaymentResult.getChannelReturnNo());

		boolean updateFlg = channelOrderService.updateChannelProtocolRnTx(
				channelOrderDTO, status);

		logger.info("channel order update result:" + updateFlg
				+ ",channelOrderNo:" + channelOrderDTO.getChannelOrderNo());

		return channelResponseDto;
	}

	public void setCurrencyRateService(CurrencyRateService currencyRateService) {
		this.currencyRateService = currencyRateService;
	}

	public CurrencyRateService getCurrencyRateService() {
		return currencyRateService;
	}
	public JmsSender getJmsSender() {
		return jmsSender;
	}

	public void setPartnerWebsiteConfigService(
			PartnerWebsiteConfigService partnerWebsiteConfigService) {
		this.partnerWebsiteConfigService = partnerWebsiteConfigService;
	}
	
	private void dccRateSet(PaymentInfo paymentInfo,
			ChannelOrderDTO channelOrder) {
		Map<String, String> queryMap = new HashMap();
		queryMap.put("orderNo", channelOrder.getChannelOrderNo() + "");
		queryMap.put("memberCode", paymentInfo.getPartnerId());
		queryMap.put("paymentType", PaymentTypeEnum.PAYMENT.getCode() + "");
		queryMap.put("memberType", MemberTypeEnum.MERCHANT.getCode() + "");
		queryMap.put("transType", TransTypeEnum.DCC.getCode());
		queryMap.put("currencyCode", paymentInfo.getCurrencyCode());
		queryMap.put("invoiceNo",channelOrder.getSerialNo());

		// 转换金额
		queryMap.put("orderAmount", paymentInfo.getOrderAmount());
		queryMap.put("cardHolderNumber", paymentInfo.getCardHolderNumber());
		queryMap.put("cardExpirationYear", paymentInfo.getCardExpirationYear());
		queryMap.put("cardExpirationMonth",
				paymentInfo.getCardExpirationMonth());
		queryMap.put("orgCode", channelOrder.getOrgCode());
		queryMap.put("orgMerchantCode", channelOrder.getMerchantNo());
		Map<String, String> rateMap = channelClientService
				.queryOrgRateInfo(queryMap);

		String responseCode = rateMap.get("responseCode");

		if ("99YY".equals(responseCode)) {

			String currency = rateMap.get("Currency");
			String Conversion_Rate = rateMap.get("Conversion_Rate");
			String Amount_For = rateMap.get("Amount_For");

			BigDecimal amountFor = new BigDecimal(Amount_For)
					.multiply(new BigDecimal("10"));

			paymentInfo.setDccCurrencyCode(currency);
			paymentInfo.setDccRate(Conversion_Rate);
			paymentInfo.setDccAmount(amountFor.longValue() + "");
			channelOrder.setDccAmount(amountFor.longValue() + "");
			channelOrder.setDccCurrencyCode(currency);
			channelOrder.setDccRate(Conversion_Rate);
		}
	}
	
	
	
	public void setJmsSender(JmsSender jmsSender) {
		this.jmsSender = jmsSender;
	}


	public static void main(String[] args){
		// 判断金额
				Long payAmount = 100000L;
				String dealAmount = "100";
				BigDecimal dAmt = new BigDecimal(dealAmount).multiply(new BigDecimal("100"));
				boolean b = Math.abs(payAmount/10 - dAmt.longValue()) > 1;
				System.out.println(b);
	}
	
	
	@Override
	public PartnerRateFloatService getPartnerRateFloatService() {
		return partnerRateFloatService;
	}
	public void setCardFilterService(CardFilterService cardFilterService) {
		this.cardFilterService = cardFilterService;
	}
	
	@Override
	public PaymentInfo channelPay4Return(PaymentOrderDTO paymentOrderDTO, PaymentInfo paymentInfo, PaymentChannelItemDto paymentChannelItemDto) {
		ChannelResponseDto channelResponseDto = new ChannelResponseDto();
		ChannelOrderDTO channelOrderDTO = buildChannelOrder(paymentInfo,
				paymentChannelItemDto);


		channelOrderDTO.setOrgKey(paymentChannelItemDto.getOrgKey());
		channelOrderDTO.setAccessCode(paymentChannelItemDto.getAccessCode());
		channelOrderDTO.setMerchantBillName(paymentChannelItemDto.getMerchantBillName());

		Long channelOrderNo = channelOrderService
				.saveChannelProtocolRnTx(channelOrderDTO);
		channelOrderDTO.setChannelOrderNo(channelOrderNo);
		if(TransTypeEnum.DCC.getCode()//只有走标准DCC的时候才会调用这段代码，自有DCC仍按照EDC送渠道
				.equals(paymentOrderDTO.getPayType())
				&&(DCCEnum.STANDARD.getCode().equals(paymentInfo.getPrdtCode())
				||DCCEnum.FORCED.getCode().equals(paymentInfo.getPrdtCode()))){
			dccRateSet(paymentInfo, channelOrderDTO);
			channelOrderService.updateChannelProtocolRnTx(channelOrderDTO);
		}
		channelOrderDTO.setChannelOrderNo(channelOrderNo);
		paymentInfo.setChannelOrderNo(String.valueOf(channelOrderNo));
		paymentInfo.setOrgCode(paymentChannelItemDto.getOrgCode());
		paymentInfo.setSerialNo(channelOrderDTO.getSerialNo());
		paymentInfo.setReturnUrl(paymentChannelItemDto.getFrontCallbackUrl());//添加银行回调地址 add by liu 2016-04-27
		BeanUtils.copyProperties(paymentChannelItemDto, paymentInfo,
				new String[] { "currencyCode", "serialNo" });
		//模式
		if("A".equals(paymentInfo.getPattern()))paymentInfo.setPattern("M");
		//处理Credorax是否要包含账单名称
		PartnerWebsiteConfig partnerWebsiteConfig = new PartnerWebsiteConfig();
		partnerWebsiteConfig.setPartnerId(paymentInfo.getPartnerId());
		partnerWebsiteConfig.setUrl(paymentInfo.getSiteId());
		List<PartnerWebsiteConfig> websiteConfigs = partnerWebsiteConfigService.findWebsiteConfig(partnerWebsiteConfig, new Page());
		if(websiteConfigs!=null && !websiteConfigs.isEmpty() && "Y".equalsIgnoreCase(websiteConfigs.get(0).getSendCredorax())){
			paymentInfo.setSendCredorax("Y");
		}else{
			paymentInfo.setSendCredorax("N");
		}
		SendChannelVo vo = new SendChannelVo();
		BeanUtils.copyProperties(paymentInfo,vo);
		vo.setCurrencyCode(paymentInfo.getChannelCurrencyCode());
		vo.setChannel2front(paymentInfo.getData());
		BackfromChannelVo backVo = channelClientService.channelPay(vo);
		paymentInfo.setReturnFromFront(backVo);
		return paymentInfo;
	}
	
	/**
	 * 
	 * 威富通渠道支付
	 * 
	 */
	@Override
	public PaymentInfo channelPay4Wft(PaymentOrderDTO paymentOrderDTO, PaymentInfo paymentInfo, PaymentChannelItemDto paymentChannelItemDto) {
		ChannelResponseDto channelResponseDto = new ChannelResponseDto();
		ChannelOrderDTO channelOrderDTO = buildChannelOrder(paymentInfo,
				paymentChannelItemDto);


		channelOrderDTO.setOrgKey(paymentChannelItemDto.getOrgKey());
		channelOrderDTO.setAccessCode(paymentChannelItemDto.getAccessCode());
		channelOrderDTO.setMerchantBillName(paymentChannelItemDto.getMerchantBillName());

		Long channelOrderNo = channelOrderService
				.saveChannelProtocolRnTx(channelOrderDTO);
		channelOrderDTO.setChannelOrderNo(channelOrderNo);
		if(TransTypeEnum.DCC.getCode()//只有走标准DCC的时候才会调用这段代码，自有DCC仍按照EDC送渠道
				.equals(paymentOrderDTO.getPayType())
				&&(DCCEnum.STANDARD.getCode().equals(paymentInfo.getPrdtCode())
				||DCCEnum.FORCED.getCode().equals(paymentInfo.getPrdtCode()))){
			dccRateSet(paymentInfo, channelOrderDTO);
			channelOrderService.updateChannelProtocolRnTx(channelOrderDTO);
		}
		channelOrderDTO.setChannelOrderNo(channelOrderNo);
		paymentInfo.setChannelOrderNo(String.valueOf(channelOrderNo));
		paymentInfo.setOrgCode(paymentChannelItemDto.getOrgCode());
		paymentInfo.setSerialNo(channelOrderDTO.getSerialNo());
		paymentInfo.setReturnUrl(paymentChannelItemDto.getFrontCallbackUrl());//添加银行回调地址 add by liu 2016-04-27
		BeanUtils.copyProperties(paymentChannelItemDto, paymentInfo,
				new String[] { "currencyCode", "serialNo" });
		//模式
		if("A".equals(paymentInfo.getPattern()))paymentInfo.setPattern("M");
		//处理Credorax是否要包含账单名称
		PartnerWebsiteConfig partnerWebsiteConfig = new PartnerWebsiteConfig();
		partnerWebsiteConfig.setPartnerId(paymentInfo.getPartnerId());
		partnerWebsiteConfig.setUrl(paymentInfo.getSiteId());
		List<PartnerWebsiteConfig> websiteConfigs = partnerWebsiteConfigService.findWebsiteConfig(partnerWebsiteConfig, new Page());
		if(websiteConfigs!=null && !websiteConfigs.isEmpty() && "Y".equalsIgnoreCase(websiteConfigs.get(0).getSendCredorax())){
			paymentInfo.setSendCredorax("Y");
		}else{
			paymentInfo.setSendCredorax("N");
		}
		WftBackVo backVo = channelClientService.channelPay4Wft(paymentInfo);
		paymentInfo.setWftBackVo(backVo);
		return paymentInfo;
	}

	@Override
	public ChannelResponseDto preAuthPayChannel(PaymentOrderDTO paymentOrderDTO, PaymentInfo paymentInfo, PaymentChannelItemDto paymentChannelItemDto, final PreController controller) {

		ChannelResponseDto channelResponseDto = new ChannelResponseDto();
		ChannelOrderDTO channelOrderDTO = buildChannelOrder(paymentInfo,
				paymentChannelItemDto);

		channelOrderDTO.setOrgKey(paymentChannelItemDto.getOrgKey());
		channelOrderDTO.setAccessCode(paymentChannelItemDto.getAccessCode());
		channelOrderDTO.setMerchantBillName(paymentChannelItemDto.getMerchantBillName());

		Long channelOrderNo = channelOrderService
				.saveChannelProtocolRnTx(channelOrderDTO);
		channelOrderDTO.setChannelOrderNo(channelOrderNo);
		controller.setActAuthorAmount(Long.parseLong(paymentInfo.getOrderAmount()));
		if(TransTypeEnum.DCC.getCode()//只有走标准DCC的时候才会调用这段代码，自有DCC仍按照EDC送渠道
				.equals(paymentOrderDTO.getPayType())
				&&(DCCEnum.STANDARD.getCode().equals(paymentInfo.getPrdtCode())
				||DCCEnum.FORCED.getCode().equals(paymentInfo.getPrdtCode()))){
			dccRateSet(paymentInfo, channelOrderDTO);
			paymentInfo.setTransType("DCC");
			channelOrderService.updateChannelProtocolRnTx(channelOrderDTO);
		}
		channelOrderDTO.setChannelOrderNo(channelOrderNo);
		paymentInfo.setChannelOrderNo(String.valueOf(channelOrderNo));
		paymentInfo.setOrgCode(paymentChannelItemDto.getOrgCode());
		paymentInfo.setSerialNo(channelOrderDTO.getSerialNo());
		BeanUtils.copyProperties(paymentChannelItemDto, paymentInfo,
				new String[] { "currencyCode", "serialNo" });
		//模式
		if("A".equals(paymentInfo.getPattern()))paymentInfo.setPattern("M");
		//处理Credorax是否要包含账单名称
		PartnerWebsiteConfig partnerWebsiteConfig = new PartnerWebsiteConfig();
		partnerWebsiteConfig.setPartnerId(paymentInfo.getPartnerId());
		partnerWebsiteConfig.setUrl(paymentInfo.getSiteId());
		List<PartnerWebsiteConfig> websiteConfigs = partnerWebsiteConfigService.findWebsiteConfig(partnerWebsiteConfig, new Page());
		if(websiteConfigs!=null && !websiteConfigs.isEmpty() && "Y".equalsIgnoreCase(websiteConfigs.get(0).getSendCredorax())){
			paymentInfo.setSendCredorax("Y");
		}else{
			paymentInfo.setSendCredorax("N");
		}
		// 调用前置完成支付
		ChannelPaymentResult channelPaymentResult = channelClientService
				.preAuthPayChannel(paymentInfo);
		String errorCode = channelPaymentResult.getErrorCode();
		String resultCode = "3099";
		String resultMsg = "Other errors:其他异常";
		SysRespCodeEnum srce = SysRespCodeEnum.getResponseCodeEnum(errorCode, paymentChannelItemDto.getOrgCode());
		if(srce!= null){
			resultCode = srce.getRespCode();
			resultMsg = srce.getRespDescEn()+":"+srce.getRespDEscCn();
		}
		channelResponseDto.setResponseCode(resultCode);
		channelResponseDto.setResponseDesc(resultMsg);

		// add by Dl for 负载均衡 2016年5月23日22:22:10
		channelResponseDto.setPayCurrencyCode(paymentInfo.getCurrencyCode());
		//add end
		channelResponseDto.setOrgCode(paymentChannelItemDto.getOrgCode());
		channelResponseDto.setChannelOrderNo(channelOrderNo);

		if (ResponseCodeEnum.SUCCESS.getCode().equals(errorCode)) {
			channelOrderDTO.setStatus(ChannelOrderStatusEnum.SUCCESS.getCode());
			channelResponseDto
					.setPayAmount(channelPaymentResult.getPayAmount());
		} else{
			channelOrderDTO.setStatus(ChannelOrderStatusEnum.FAIL.getCode());
		}
		channelResponseDto.setChannelRespCode(errorCode);
		channelOrderDTO.setErrorCode(errorCode);
		channelOrderDTO.setErrorMsg(channelPaymentResult.getErrorMsg());
		channelOrderDTO.setUpdateDate(new Date());
		channelOrderDTO.setCompleteDate(new Date());
		channelOrderDTO.setAuthorisation(channelPaymentResult
				.getAuthorisation());

		channelOrderDTO.setReturnNo(channelPaymentResult.getChannelReturnNo());
		channelResponseDto.setChannelReturnNo(channelPaymentResult.getChannelReturnNo());
		//中行moto migs不传订单号，传参考号
		channelOrderDTO.setReferenceNo(channelPaymentResult.getReferenceNo());
		logger.info("获取参考号referenceNo:"+channelPaymentResult.getReferenceNo());
		channelOrderDTO.setCardOrg(paymentInfo.getCardOrg());
		boolean updateFlg = channelOrderService.updateChannelProtocolRnTx(
				channelOrderDTO, ChannelOrderStatusEnum.INIT.getCode());
		if(!updateFlg){
			logger.info("channel order update result:" + updateFlg
					+ ",channelOrderNo:" + channelOrderNo + ",orgCode="+paymentChannelItemDto.getOrgCode());

			resultCode = "3099";
			resultMsg = "Other errors:其他异常";

			channelResponseDto.setResponseCode(resultCode);
			channelResponseDto.setResponseDesc(resultMsg);
		}

		return channelResponseDto;
	}


	@Override
	public ChannelResponseDto preAuthCapture(PaymentOrderDTO paymentOrderDTO, ChannelOrder origChannelOrder, PaymentInfo paymentInfo, final PreController controller) {

		ChannelResponseDto channelResponseDto = new ChannelResponseDto();
		long orderAmountAsk = Long.parseLong(paymentInfo.getOrderAmount());
		ChannelOrderDTO channelOrderDTO = buildChannelOrder(paymentInfo,origChannelOrder);

		paymentInfo.setOrgCode(origChannelOrder.getOrgCode());
		paymentInfo.setOrgKey(origChannelOrder.getOrgKey());
		paymentInfo.setAccessCode(origChannelOrder.getAccessCode());
		paymentInfo.setOrgMerchantCode(origChannelOrder.getMerchantNo());
		paymentInfo.setMerchantBillName(origChannelOrder.getMerchantBillName());
		paymentInfo.setTerminalCode(controller.getTerminalCode());
		paymentInfo.setMcc(controller.getMcc());
		paymentInfo.setCardOrg(controller.getCardOrg());
		paymentInfo.setChannelCurrencyCode(origChannelOrder.getTransferCurrencyCode());

		Long channelOrderNo = channelOrderService
				.saveChannelProtocolRnTx(channelOrderDTO);
		channelOrderDTO.setChannelOrderNo(channelOrderNo);
		if(TransTypeEnum.DCC.getCode()//只有走标准DCC的时候才会调用这段代码，自有DCC仍按照EDC送渠道
				.equals(paymentOrderDTO.getPayType())
				&&(DCCEnum.STANDARD.getCode().equals(paymentInfo.getPrdtCode())
				||DCCEnum.FORCED.getCode().equals(paymentInfo.getPrdtCode()))){
			dccRateSet(paymentInfo, channelOrderDTO);
			paymentInfo.setTransType("DCC");
			channelOrderService.updateChannelProtocolRnTx(channelOrderDTO);
		}
		channelOrderDTO.setChannelOrderNo(channelOrderNo);
		paymentInfo.setChannelOrderNo(String.valueOf(channelOrderNo));
		paymentInfo.setSerialNo(channelOrderDTO.getSerialNo());

		if("A".equals(paymentInfo.getPattern()))paymentInfo.setPattern("M");
		//处理Credorax是否要包含账单名称
		PartnerWebsiteConfig partnerWebsiteConfig = new PartnerWebsiteConfig();
		partnerWebsiteConfig.setPartnerId(paymentInfo.getPartnerId());
		partnerWebsiteConfig.setUrl(paymentInfo.getSiteId());
		List<PartnerWebsiteConfig> websiteConfigs = partnerWebsiteConfigService.findWebsiteConfig(partnerWebsiteConfig, new Page());
		if(websiteConfigs!=null && !websiteConfigs.isEmpty() && "Y".equalsIgnoreCase(websiteConfigs.get(0).getSendCredorax())){
			paymentInfo.setSendCredorax("Y");
		}else{
			paymentInfo.setSendCredorax("N");
		}

		// 调用前置完成预授权完成
		ChannelPaymentResult channelPaymentResult = channelClientService
				.preAuthProcess(paymentInfo,controller.getChannelResponseNo(),String.valueOf(controller.getChannelOrderNo()),"1", String.valueOf(controller.getPaymentChannelItemId()),origChannelOrder.getAuthorisation());

		String errorCode = channelPaymentResult.getErrorCode();
		String resultCode = "3099";
		String resultMsg = "Other errors:其他异常";
		SysRespCodeEnum srce = SysRespCodeEnum.getResponseCodeEnum(errorCode, paymentInfo.getOrgCode());
		if(srce!= null){
			resultCode = srce.getRespCode();
			resultMsg = srce.getRespDescEn()+":"+srce.getRespDEscCn();

		}
		channelResponseDto.setResponseCode(resultCode);
		channelResponseDto.setResponseDesc(resultMsg);

		// add by Dl for 负载均衡 2016年5月23日22:22:10
		channelResponseDto.setPayCurrencyCode(paymentInfo.getCurrencyCode());
		//add end
		channelResponseDto.setOrgCode(origChannelOrder.getOrgCode());
		channelResponseDto.setChannelOrderNo(channelOrderNo);

		if (ResponseCodeEnum.SUCCESS.getCode().equals(errorCode)) {
			channelOrderDTO.setStatus(ChannelOrderStatusEnum.SUCCESS.getCode());
			controller.setCaptureAmountTotal(controller.getCaptureAmountTotal() + orderAmountAsk);
			controller.setActCapAmountTotal(controller.getActCapAmountTotal() + channelOrderDTO.getPayAmount());
			channelResponseDto
					.setPayAmount(channelPaymentResult.getPayAmount());
		} else{
			channelOrderDTO.setStatus(ChannelOrderStatusEnum.FAIL.getCode());
		}
		channelResponseDto.setChannelRespCode(errorCode);
		channelOrderDTO.setErrorCode(errorCode);
		channelOrderDTO.setErrorMsg(channelPaymentResult.getErrorMsg());
		channelOrderDTO.setUpdateDate(new Date());
		channelOrderDTO.setCompleteDate(new Date());
		channelOrderDTO.setAuthorisation(channelPaymentResult
				.getAuthorisation());
		channelOrderDTO.setReturnNo(channelPaymentResult.getChannelReturnNo());
		//中行moto migs不传订单号，传参考号
		channelOrderDTO.setReferenceNo(channelPaymentResult.getReferenceNo());
		logger.info("获取参考号referenceNo:"+channelPaymentResult.getReferenceNo());
		channelOrderDTO.setCardOrg(paymentInfo.getCardOrg());
		boolean updateFlg = channelOrderService.updateChannelProtocolRnTx(
				channelOrderDTO, ChannelOrderStatusEnum.INIT.getCode());
		if(!updateFlg){
			logger.info("channel order update result:" + updateFlg
					+ ",channelOrderNo:" + channelOrderNo + ",orgCode="+origChannelOrder.getOrgCode());

			resultCode = "3099";
			resultMsg = "Other errors:其他异常";

			channelResponseDto.setResponseCode(resultCode);
			channelResponseDto.setResponseDesc(resultMsg);
		}
		// 记账处理
		if (ResponseCodeEnum.SUCCESS.getCode().equals(errorCode) && updateFlg) {

			Long amount = channelOrderDTO.getPayAmount();
			Long payAmount = channelPaymentResult.getPayAmount();
			if (null == payAmount) {

				logger.error("return payAmount must be not null: "+ExceptionCodeEnum.ILLEGAL_PARAMETER);

				//这地方要加个报警、但是已经支付成功了
				Map<String,String> data = new HashMap<String, String>();

				data.put("first","系统异常，返回的预授权完成金额为空,txncore:ChannelServiceImpl");
				data.put("keyword1","返回的预授权完成金额为空");
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				data.put("keyword2",formatter.format(new Date()));
				data.put("remark","请尽快检查！");

				this.sendAlertMsg(data);

				return channelResponseDto;
			}


			if (payAmount < amount) {
				logger.error("channelOrderNo:"
						+ channelOrderDTO.getChannelOrderNo() + "payAmount:"
						+ payAmount + ",amount:" + amount);
				logger.error("payAmount error: "+ExceptionCodeEnum.ORDERAMOUNT_ERROR);

				Map<String,String> data = new HashMap<String, String>();

				data.put("first","系统异常: 预授权完成金额小于渠道订单金额,,txncore:ChannelServiceImpl");
				data.put("keyword1","返回的预授权完成金额为空");
				SimpleDateFormat formatter = new SimpleDateFormat(
						"yyyy-MM-dd HH:mm:ss");
				data.put("keyword2",formatter.format(new Date()));
				data.put("remark","请尽快检查！");

				this.sendAlertMsg(data);

				return channelResponseDto;
				//这地方要加个报警、但是已经支付成功了

			}

			finalDoAccting(channelOrderDTO,paymentInfo);
		}

		return channelResponseDto;
	}

	private void finalDoAccting(ChannelOrderDTO channelOrderDTO,PaymentInfo paymentInfo){
		try {
			logger.info("orderId: "+paymentInfo.getOrderId()+ new Date());
			accountingService
					.doAccounting(buildAccountingVo(channelOrderDTO,paymentInfo.getOrderId()));
			logger.info(" doAccounting 101 start: "+ new Date());
			String orderId = channelOrderDTO.getChannelOrderNo() + "";
			doAccounting_100_101(channelOrderDTO.getOrgCode(), orderId,
					channelOrderDTO.getAmount(),
					channelOrderDTO.getCurrencyCode(),paymentInfo.getOrderId());
			logger.info(" doAccounting 102 start: "+ new Date());
			doAccounting_100_102(channelOrderDTO.getOrgCode(), orderId,
					channelOrderDTO.getAmount(),
					channelOrderDTO.getTransferCurrencyCode(),
					channelOrderDTO.getTransferRate(),paymentInfo.getOrderId());
			logger.info(" doAccounting 102 end: "+ new Date());
		} catch (Exception e) {
			logger.error("do channel accounting error:" + channelOrderDTO.getChannelOrderNo(), e);
			logger.error("call pe error: "+ExceptionCodeEnum.PE_ERROR.getCode());
		}
	}


	@Override
	public ChannelResponseDto preAuthVoid(PaymentInfo paymentInfo, final PreController controller,ChannelOrder origChannelOrder,final AuthChangeOrder saveAuthChangeOrder) {

		paymentInfo.setChannelOrderNo(String.valueOf(controller.getId()));//发送预授权单据

		ChannelResponseDto channelResponseDto = new ChannelResponseDto();

		paymentInfo.setOrgCode(origChannelOrder.getOrgCode());
		paymentInfo.setOrgKey(origChannelOrder.getOrgKey());
		paymentInfo.setAccessCode(origChannelOrder.getAccessCode());
		paymentInfo.setOrgMerchantCode(origChannelOrder.getMerchantNo());
		paymentInfo.setMerchantBillName(origChannelOrder.getMerchantBillName());
		paymentInfo.setTerminalCode(controller.getTerminalCode());
		paymentInfo.setMcc(controller.getMcc());
		paymentInfo.setCardOrg(controller.getCardOrg());
		paymentInfo.setChannelCurrencyCode(origChannelOrder.getTransferCurrencyCode());
		paymentInfo.setSerialNo(origChannelOrder.getSerialNo());
		//模式
		if("A".equals(paymentInfo.getPattern()))paymentInfo.setPattern("M");
		//处理Credorax是否要包含账单名称
		PartnerWebsiteConfig partnerWebsiteConfig = new PartnerWebsiteConfig();
		partnerWebsiteConfig.setPartnerId(paymentInfo.getPartnerId());
		partnerWebsiteConfig.setUrl(paymentInfo.getSiteId());
		List<PartnerWebsiteConfig> websiteConfigs = partnerWebsiteConfigService.findWebsiteConfig(partnerWebsiteConfig, new Page());
		if(websiteConfigs!=null && !websiteConfigs.isEmpty() && "Y".equalsIgnoreCase(websiteConfigs.get(0).getSendCredorax())){
			paymentInfo.setSendCredorax("Y");
		}else{
			paymentInfo.setSendCredorax("N");
		}
		String authorisation = origChannelOrder.getAuthorisation();



		// 调用前置完成预授权失效
		ChannelPaymentResult channelPaymentResult = channelClientService
				.preAuthProcess(paymentInfo,controller.getChannelResponseNo(),String.valueOf(controller.getChannelOrderNo()),"2",String.valueOf(controller.getPaymentChannelItemId()),authorisation);

		String errorCode = channelPaymentResult.getErrorCode();
		String resultCode = "3099";
		String resultMsg = "Other errors:其他异常";
		SysRespCodeEnum srce = SysRespCodeEnum.getResponseCodeEnum(errorCode, paymentInfo.getOrgCode());
		if(srce!= null){
			resultCode = srce.getRespCode();
			resultMsg = srce.getRespDescEn()+":"+srce.getRespDEscCn();
		}
		saveAuthChangeOrder.setChannelResponseCode(errorCode);
		saveAuthChangeOrder.setChannelResponseNo(channelPaymentResult.getChannelReturnNo());
		saveAuthChangeOrder.setChannelResponseDesc(channelPaymentResult.getErrorMsg());
		saveAuthChangeOrder.setCompleteDate(new Date());

		channelResponseDto.setResponseCode(resultCode);
		channelResponseDto.setResponseDesc(resultMsg);
		channelResponseDto.setPayCurrencyCode(paymentInfo.getCurrencyCode());
		channelResponseDto.setOrgCode(origChannelOrder.getOrgCode());
		channelResponseDto.setChannelOrderNo(controller.getId());
		channelResponseDto.setChannelRespCode(errorCode);
		return channelResponseDto;
	}


	/**
	 * 构建预授权完成channelOrder
	 * @param paymentInfo
	 * @return
	 */
	private ChannelOrderDTO buildChannelOrder(PaymentInfo paymentInfo,ChannelOrder origChannelOrder) {
		ChannelOrderDTO channelOrderDTO = new ChannelOrderDTO();
		String orderAmount = paymentInfo.getOrderAmount();
		String channelCurrencyCode = origChannelOrder.getTransferCurrencyCode();

		BigDecimal amount = new BigDecimal(paymentInfo.getOrderAmount());
		channelOrderDTO.setCurrencyCode(origChannelOrder.getCurrencyCode());
		channelOrderDTO.setTransferCurrencyCode(origChannelOrder.getTransferCurrencyCode());
		paymentInfo.setCurrencyCode(origChannelOrder.getCurrencyCode());

		channelOrderDTO.setAmount(amount.longValue());
		channelOrderDTO.setPayAmount(amount.longValue());
		channelOrderDTO.setTransferRate("1");
		channelOrderDTO.setTransferRate(origChannelOrder.getTransferRate());
		channelOrderDTO.setOriginalRate(origChannelOrder.getOriginalRate());
		channelOrderDTO.setFloatValue(origChannelOrder.getFloatValue());

		if("DCC".equals(paymentInfo.getPayType())){
			BigDecimal payAmount = BigDecimal.ZERO;
			BigDecimal rateB = new BigDecimal(origChannelOrder.getTransferRate());
			payAmount = rateB.multiply(new BigDecimal(paymentInfo.getOrderAmount())
					.multiply(new BigDecimal("0.1")));
			DecimalFormat df = new DecimalFormat("##.##");
			BigDecimal amountFor = payAmount.divide(
					new BigDecimal("100"));
			String amountForStr = df.format(amountFor);
			long amount_ = new BigDecimal(amountForStr).multiply(new BigDecimal("1000")).longValue();
			Long amountLoc=0L;
			if ("BYR".equals(channelCurrencyCode) || "JPY".equals(channelCurrencyCode)
					|| "KRW".equals(channelCurrencyCode) || "VND".equals(channelCurrencyCode)) {
				amountLoc = new BigDecimal(amount_).divide(new BigDecimal("1000"))
						.longValue();
				amountLoc = new BigDecimal(amountLoc).multiply(new BigDecimal("1000")).longValue();
			}else{
				amountLoc = amount_;
			}

			channelOrderDTO.setPayAmount(amountLoc);
			paymentInfo.setOrderAmount(String.valueOf(amount_));

			if("EDC".equals(paymentInfo.getChannelPayType())){
				paymentInfo.setPayType("EDC");
				paymentInfo.setTransType("EDC");
				paymentInfo.setDccAmount(null);
				paymentInfo.setDccCurrencyCode(null);
			}else if("DCC".equals(paymentInfo.getChannelPayType())){
				paymentInfo.setTransType("DCC");
				paymentInfo.setPayType("DCC");
			}

		}else{
			BigDecimal rateO = new BigDecimal(origChannelOrder.getOriginalRate());
			BigDecimal tmp = new BigDecimal(origChannelOrder.getTransferRate());
			amount = new BigDecimal(orderAmount).multiply(tmp);
			long tAmount = amount.longValue();
			String amountStr = String.valueOf(tAmount);
			if (!amountStr.endsWith("0")) {
				logger.info("paymentInfo paymentOrderNo:"
						+ paymentInfo.getPaymentOrderNo() + "amountStr:"
						+ amountStr);
				tAmount = amount.divide(new BigDecimal(10)).longValue() * 10 + 10;// 厘如果不为0，则向前进1
			}

			paymentInfo.setCurrencyCode(channelCurrencyCode);
			paymentInfo.setOrderAmount(String.valueOf(tAmount));
			channelOrderDTO.setPayAmount(tAmount);
			channelOrderDTO.setTransferCurrencyCode(channelCurrencyCode);
		}

		long serialNo = channelOrderService
				.getMaxChannelSerialNo(origChannelOrder.getOrgCode());
		String serialStr = getSerial(serialNo + 1);
		channelOrderDTO.setSerialNo(serialStr);

		channelOrderDTO.setCreateDate(new Date());
		channelOrderDTO.setMerchantNo(origChannelOrder.getMerchantNo());
		channelOrderDTO.setOrgCode(origChannelOrder.getOrgCode());
		channelOrderDTO.setPaymentOrderNo(Long.valueOf(paymentInfo
				.getPaymentOrderNo()));
		channelOrderDTO.setStatus(ChannelOrderStatusEnum.INIT.getCode());
		channelOrderDTO.setUpdateDate(new Date());
		channelOrderDTO.setReconciliationFlg(0);
		return channelOrderDTO;

	}


}
