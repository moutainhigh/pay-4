package com.pay.txncore.service.preauth.impl;

import com.pay.acc.member.dto.EnterpriseBaseDto;
import com.pay.acc.member.service.EnterpriseBaseService;
import com.pay.acc.rate.service.MerchantRateService;
import com.pay.acc.service.account.AccountQueryService;
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
import com.pay.fi.helper.CredoraxCurrencyCodeEnum;
import com.pay.inf.enums.DCCEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.model.IpCountryInfo;
import com.pay.inf.service.IpCountryInfoService;
import com.pay.jms.notification.request.BlacklistCheckNotifyRequest;
import com.pay.jms.notification.request.ChannelMidAmountNotifyRequest;
import com.pay.jms.sender.JmsSender;
import com.pay.rm.facade.BusinessTradeCountService;
import com.pay.txncore.client.ChannelClientService;
import com.pay.txncore.client.RiskClientService;
import com.pay.txncore.commons.CardOrgUtil;
import com.pay.txncore.commons.OrderBuilder;
import com.pay.txncore.commons.PaymentOrderStatusEnum;
import com.pay.txncore.commons.TradeOrderStatusEnum;
import com.pay.txncore.crosspay.model.ExpressTracking;
import com.pay.txncore.crosspay.service.ExpressTrackingService;
import com.pay.txncore.dao.*;
import com.pay.txncore.dto.*;
import com.pay.txncore.model.*;
import com.pay.txncore.service.*;
import com.pay.txncore.service.impl.PaymentServiceImpl;
import com.pay.txncore.service.preauth.PreAuth2Service;
import com.pay.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeanUtils;

import java.lang.reflect.InvocationTargetException;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.SimpleDateFormat;
import java.util.*;

/**
 * Created by cuber on 2016/9/1.
 */
public class PreAuth2ServiceImpl implements PreAuth2Service {
    private PaymentService paymentService;
    private PreControllerService preControllerService;
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

    private TradeOrderDAO tradeOrderDAO;

    private ChannelOrderDAO channelOrderDAO;

    private PaymentOrderDAO paymentOrderDAO;

    private AuthChaneOrderService authChaneOrderService;

    private AuthChaneOrderDAO authChaneOrderDAO;
    
    private TradeBaseDAO tradeBaseDAO;

    public void setAuthChaneOrderService(AuthChaneOrderService authChaneOrderService) {
        this.authChaneOrderService = authChaneOrderService;
    }

    public void setAuthChaneOrderDAO(AuthChaneOrderDAO authChaneOrderDAO) {
        this.authChaneOrderDAO = authChaneOrderDAO;
    }

    public void setTradeOrderDAO(TradeOrderDAO tradeOrderDAO) {
        this.tradeOrderDAO = tradeOrderDAO;
    }

    public void setChannelOrderDAO(ChannelOrderDAO channelOrderDAO) {
        this.channelOrderDAO = channelOrderDAO;
    }

    public void setPaymentOrderDAO(PaymentOrderDAO paymentOrderDAO) {
        this.paymentOrderDAO = paymentOrderDAO;
    }

    private static final String COUNTRY = "USA,CAN";

    private JmsSender jmsSender;
    private DecryptService decryptService;

    public void setTradeOrderService(TradeOrderService tradeOrderService) {
        this.tradeOrderService = tradeOrderService;
    }

    public void setTradeExtendsService(TradeExtendsService tradeExtendsService) {
        this.tradeExtendsService = tradeExtendsService;
    }

    public void setExpressTrackingService(ExpressTrackingService expressTrackingService) {
        this.expressTrackingService = expressTrackingService;
    }

    public void setPaymentOrderService(PaymentOrderService paymentOrderService) {
        this.paymentOrderService = paymentOrderService;
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

    public void setEnterpriseBaseService(EnterpriseBaseService enterpriseBaseService) {
        this.enterpriseBaseService = enterpriseBaseService;
    }

    public void setChannelService(ChannelService channelService) {
        this.channelService = channelService;
    }

    public void setChannelOrderService(ChannelOrderService channelOrderService) {
        this.channelOrderService = channelOrderService;
    }

    public void setPaymentOrderExpandDAO(PaymentOrderExpandDAO paymentOrderExpandDAO) {
        this.paymentOrderExpandDAO = paymentOrderExpandDAO;
    }

    public void setFormatter(SimpleDateFormat formatter) {
        this.formatter = formatter;
    }

    public void setBusinessTradeCountService(BusinessTradeCountService businessTradeCountService) {
        this.businessTradeCountService = businessTradeCountService;
    }

    public void setMerchantRateService(MerchantRateService merchantRateService) {
        this.merchantRateService = merchantRateService;
    }

    public void setChannelClientService(ChannelClientService channelClientService) {
        this.channelClientService = channelClientService;
    }

    public void setTradeAmountCountService(TradeAmountCountService tradeAmountCountService) {
        this.tradeAmountCountService = tradeAmountCountService;
    }

    public void setPartnerConfigService(PartnerConfigService partnerConfigService) {
        this.partnerConfigService = partnerConfigService;
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

    public void setIpCountryInfoService(IpCountryInfoService ipCountryInfoService) {
        this.ipCountryInfoService = ipCountryInfoService;
    }

    public void setJmsSender(JmsSender jmsSender) {
        this.jmsSender = jmsSender;
    }

    public void setDecryptService(DecryptService decryptService) {
        this.decryptService = decryptService;
    }

    public static void setLogger(Logger logger) {
        PreAuth2ServiceImpl.logger = logger;
    }

    public void setPreControllerService(PreControllerService preControllerService) {
        this.preControllerService = preControllerService;
    }

    public void setPaymentService(PaymentService paymentService) {
        this.paymentService = paymentService;
    }

    private static Logger logger = LoggerFactory.getLogger(PreAuth2ServiceImpl.class);

    @Override
    public PaymentResult preAuthApply(PaymentInfo paymentInfo) {
        PaymentResult paymentResult = new PaymentResult();
        dupliCheck(paymentInfo);
        paymentService.setSettlementCurrencyCode(paymentInfo);

        // first setup save traderOrder
        TradeBaseDTO tradeBaseDTO = OrderBuilder.buildTradeBase(paymentInfo);
        TradeOrderDTO tradeOrderDTO = OrderBuilder.buildTradeOrder(paymentInfo);

        Long tradeOrderNo = paymentService.getTradeOrderService().saveTradeOrderRnTx(tradeBaseDTO,
                tradeOrderDTO);
        tradeOrderDTO.setTradeOrderNo(tradeOrderNo);
        paymentInfo.setPaymentWay("A");
        paymentInfo.setTradeOrderNo(tradeOrderNo);
        paymentInfo.setOrderAmount(String.valueOf(tradeOrderDTO.getOrderAmount()));

        // 保存额外信息
        paymentService.saveExtendsInfo(paymentInfo, tradeOrderDTO);

        paymentService.riskValidate(paymentInfo, tradeOrderDTO, paymentResult);
        String desc = paymentResult.getRiskDesc();
        logger.info("paymentResult: " + paymentResult);
        if (!"ACCEPT".equals(desc)) {
            return paymentResult;
        }
        PreController controller = preControllerService.saveAuthControllerRnTx(
                paymentInfo.getOrderId(), Long.parseLong(paymentInfo.getPartnerId()), Long.parseLong(paymentInfo.getOrderAmount()), paymentInfo.getCurrencyCode());
        try {
            paymentResult = doAuthApplay(paymentInfo, tradeOrderDTO, tradeBaseDTO, PaymentWayEnum.API.getCode(), controller);
            try {
            	//add by zhaoyang at 20160912
            	paymentInfo.setSettlementCurrencyCode(paymentResult.getSettlementCurrencyCode());
                sendCounting(paymentInfo, paymentResult);
            } catch (Exception e) {
                logger.info("send notify.forpay.blacklist fail"
                        + tradeOrderDTO.getTradeOrderNo());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return saveException(paymentResult, tradeOrderDTO);
        }
        paymentResult.setTradeOrderNo(tradeOrderDTO.getTradeOrderNo());
        paymentResult.setCompleteTime(DateUtil.formatDateTime(
                DateUtil.PATTERN1, tradeOrderDTO.getCompleteDate()));
        return paymentResult;
    }

    private PaymentResult doAuthApplay(PaymentInfo paymentInfo, TradeOrderDTO tradeOrderDTO, TradeBaseDTO tradeBaseDTO,
                                       String paymentWay, PreController controller) {

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
        List<PaymentChannelItemDto> channels = routeChannels(paymentInfo);

        logger.info("收单路由到的渠道是：" + tradeOrderDTO.getTradeOrderNo() + "," + Arrays.toString(channels.toArray()));
        ExpressTracking expressTracking = expressTrackingService
                .findByTradeOrderNo(tradeOrderDTO.getTradeOrderNo() + "");

        getUseFullChannelItem(paymentInfo, tradeOrderDTO, channels);
        if (!(channels != null && channels.size() > 0)) {
            paymentResult = doNoChannelProcess(paymentResult, tradeOrderDTO);
            return paymentResult;
        }

        setAmountStuff(paymentInfo, tradeOrderDTO);

        String currencyCode = paymentInfo.getCurrencyCode();
        String cardOrg = CardOrgUtil.getCardType(paymentInfo.getCardHolderNumber());
        String cardCurrencyCode = getCardCurrencyCode(paymentInfo);

        Iterator<PaymentChannelItemDto> iterator = channels.iterator();
        boolean updateFailureTradeOrder = true;
        while (iterator.hasNext()) {
            PaymentChannelItemDto paymentChannelItemDto = iterator.next();

            if (gcMember && !"1".equals(paymentChannelItemDto.getGetGcCurrentFlag())) {//
                logger.error("会员[" + paymentInfo.getPartnerId() + "]在通道下[" + paymentChannelItemDto.getName() + "]没有默认的2级商户号");
                notifyChannel(null, 0l, null, paymentInfo.getPartnerId(), paymentChannelItemDto.getId(), false, paymentChannelItemDto.getChannelConfigId(), "missSecond");
                continue;
            }
            String channelPayType = paymentChannelItemDto.getTransType();
            String orgCode = paymentChannelItemDto.getOrgCode();
            ChannelCurrencyDTO  currencyDTO = this.getChannelCurrency(paymentInfo.getPartnerId(),paymentInfo.getCurrencyCode(), cardCurrencyCode, paymentInfo.getPrdtCode(), orgCode,
                    paymentInfo.getPayType());
            if (currencyDTO == null) {
                throw new BusinessException("找不到送渠道币种, orgCode：" + orgCode + ",currencyCode:" + paymentInfo.getCurrencyCode() + ",prdtCode:"
                        + paymentInfo.getPrdtCode() + ",payType: " + paymentInfo.getPayType() + ",cardCurrencyCode:" + null, ExceptionCodeEnum.OTHER_ERROR);
            }
            TransRateMarkup markup = this.getMarkup(paymentInfo, currencyDTO);
            if (markup != null) {
            } else if ("DCC".equals(currencyDTO.getPayType())) {
                currencyDTO = this.getChannelCurrency(paymentInfo.getPartnerId(),paymentInfo.getCurrencyCode(), cardCurrencyCode, "EDC", orgCode, "EDC");
                markup = this.getMarkup(paymentInfo, currencyDTO);
            }

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

            paymentInfo.setPaymentType(PaymentTypeEnum.PREAUTH.getCode());
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

            ChannelResponseDto channelResult = channelService.preAuthPayChannel(paymentOrderDTO, paymentInfo,
                    paymentChannelItemDto, controller);

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
            paymentResult.setChannelRespCode(channelRespCode);
            paymentResult.setOrgCode(channelResult.getOrgCode());
            paymentResult.setResponseCode(errorCode);
            paymentResult.setResponseDesc(errorMsg);
            paymentResult.setChannelOrderNo(channelResult.getChannelOrderNo());
            paymentResult.setPayAmount(channelResult.getPayAmount());
            paymentResult.setPaymentOrderNo(paymentOrderNo);
            paymentResult.setMerchantBillName(paymentChannelItemDto.getMerchantBillName());

            controller.setTradeOrderNo(tradeOrderDTO.getTradeOrderNo());
            controller.setPaymentOrderNo(paymentOrderDTO.getPaymentOrderNo());
            controller.setChannelOrderNo(channelResult.getChannelOrderNo());
            controller.setChannelResponseNo(channelResult.getChannelReturnNo());
            controller.setActCurrencyCode(channelResult.getPayCurrencyCode());
            controller.setPaymentChannelItemId(Long.parseLong(paymentChannelItemDto.getId()));

            if (ResponseCodeEnum.SUCCESS.getCode().equals(errorCode)) {
                // 有成功的订单就跳出，不再进行交易了
                // 更新支付订单和网关订单状态
                updateFailureTradeOrder = false;//更新网管订单为成功
                /**
    			 * add by zhaoyang at 20160908
    			 * 结算币种更改。
    			 * 1，根据用户号、交易类型、交易币种、结算币种查找通过POSS配置的结算币种信息
    			 * 2，如果无查询信息，则使用原有的结算币种；如果有查询信息，则使用查询出来的结算币种与tradeBaseDTO.getSettlementCurrencyCode()
    			 * 比较，若相等则使用原有的结算币种，否则使用查询出来的结算币种。
    			 * 3，更新交易基础订单(结算币种)，更新支付订单（收款人账户类型）
    			 * 4,更新paymentResult的结算币种,为sendCounting()做准备
    			 */
                logger.info("预授权成功--paymentOrderNo is {},memberCode is {},payType is {},tradeCurrencyCode is {},payCurrencyCode is {}", new Object[]{paymentOrderDTO.getPaymentOrderNo(),tradeBaseDTO.getPartnerId(),tradeBaseDTO.getPayType(),tradeBaseDTO.getCurrencyCode(),channelResult.getPayCurrencyCode()});
    			tradeBaseDTO = paymentService.confirmTheSettlementCurrency(tradeBaseDTO, channelResult.getPayCurrencyCode());
    			logger.info("预授权成功结算币种确认--paymentOrderNo is {},SettlementCurrencyCode is {}", paymentOrderDTO.getPaymentOrderNo(),tradeBaseDTO.getSettlementCurrencyCode());
    			TradeBase newTb = new TradeBase();
    			newTb.setTradeBaseNo(tradeBaseDTO.getTradeBaseNo());
    			newTb.setSettlementCurrencyCode(tradeBaseDTO.getSettlementCurrencyCode());
    			tradeOrderService.updateTradeBase(newTb);
    			paymentResult.setSettlementCurrencyCode(tradeBaseDTO.getSettlementCurrencyCode());
    			paymentOrderDTO.setPayeeType(paymentService.getPayeeTypeBySettlementCurrency(tradeBaseDTO.getSettlementCurrencyCode(), tradeOrderDTO.getPartnerId()));
                paymentOrderDTO.setStatus(PaymentOrderStatusEnum.SUCCESS.getCode());
                paymentOrderDTO.setCompleteDate(new Date());
                paymentOrderDTO.setOrgCode(channelResult.getOrgCode());
                boolean updateFlag = paymentOrderService.updatePaymentOrderRnTx(paymentOrderDTO,
                        PaymentOrderStatusEnum.INIT.getCode());
                if (!updateFlag) {
                    logger.error("do not update paymentOrder," + paymentOrderNo);
                }
                controller.setCardOrg(paymentChannelItemDto.getPaymentCategoryCode());
                controller.setMcc(paymentChannelItemDto.getMcc());
                controller.setTerminalCode(paymentChannelItemDto.getTerminalCode());
                if((!StringUtil.isEmpty(paymentChannelItemDto.getChannelConfigId())) && StringUtil.isNumber(paymentChannelItemDto.getChannelConfigId()))
                    controller.setChannelConfigId(Long.parseLong(paymentChannelItemDto.getChannelConfigId()));
                if((!StringUtil.isEmpty(paymentChannelItemDto.getMemberCurrentConnectId())) && StringUtil.isNumber(paymentChannelItemDto.getMemberCurrentConnectId()))
                    controller.setMemberCurrentId(Long.parseLong(paymentChannelItemDto.getMemberCurrentConnectId()));
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
                List<String> notRetryCodes = PaymentServiceImpl.getChangeChannelCodes(paymentChannelItemDto.getOrgCode());
                if (notRetryCodes != null && notRetryCodes.contains(channelResult.getChannelRespCode())) {
                    break;
                }
            }

        }
        controller.setAuthorStatus(updateFailureTradeOrder ? "2" : "1");
        preControllerService.updateAuthControllerRnTx(controller);
        finalUpdateTradeOrderResult(tradeOrderDTO, paymentWay, paymentInfo.getPaymentWay(), updateFailureTradeOrder, "0");
        return paymentResult;
    }



    private PaymentResult doAuthCap(PaymentInfo paymentInfo, TradeOrderDTO tradeOrderDTO, TradeBaseDTO tradeBaseDTO,
                                    String paymentWay, PreController controller,
                                    TradeOrder origTradeOrder,PaymentOrder origPaymentOrder,ChannelOrder origChannelOrder,
                                    PaymentResult paymentResult) {

        tradeOrderDTO.setStatus(TradeOrderStatusEnum.PROCESSING.getCode());
        boolean updateResult = tradeOrderService.updateTradeOrderRnTx(tradeOrderDTO,
                TradeOrderStatusEnum.WAIT_PAY.getCode());
        if (!updateResult) {
            logger.info("set processing status to trade order is failed.");
        }

        tradeOrderDTO.setFloatValue(origTradeOrder.getFloatValue());
        paymentInfo.setFloatValue(origTradeOrder.getFloatValue());
        tradeOrderDTO.setRate(origTradeOrder.getRate());
        tradeOrderDTO.setOriginalRate(origTradeOrder.getOriginalRate());
        paymentInfo.setChannelCurrencyCode(controller.getActCurrencyCode());

        PaymentOrderDTO paymentOrderDTO = OrderBuilder.buildPaymentOrder(paymentInfo, origPaymentOrder, tradeOrderDTO);
        Long paymentOrderNo = paymentOrderService.savePaymentOrderRnTx(paymentOrderDTO);
        paymentOrderDTO.setPaymentOrderNo(paymentOrderNo);
        paymentInfo.setPrdtCode(paymentOrderDTO.getPrdtCode());
        paymentInfo.setPaymentOrderNo(paymentOrderDTO.getPaymentOrderNo().toString());
        paymentInfo.setTradeType(tradeOrderDTO.getTradeType());
        paymentInfo.setCardOrg(controller.getCardOrg());

        OrderBuilder.savePaymentOrderExpand(paymentOrderNo, paymentInfo, paymentOrderExpandDAO);

        paymentInfo.setPaymentType(PaymentTypeEnum.PREAUTH.getCode());
        paymentInfo.setTransType(TransTypeEnum.EDC.getCode());
        paymentInfo.setOrderId(tradeOrderDTO.getOrderId());

        ExpressTracking expressTracking = expressTrackingService
                .findByTradeOrderNo(origTradeOrder.getTradeOrderNo() + "");
        paymentInfo.setBillName(expressTracking.getBillName());
        paymentInfo.setBillEmail(expressTracking.getBillEmail());
        paymentInfo.setBillPostalCode(expressTracking.getBillPostalCode());
        // 添加mcc，商户账单名 by tom 2016年5月26日21:30:05
        paymentInfo.setMerchantBillName(origChannelOrder.getMerchantBillName());
        paymentInfo.setMcc(controller.getMcc());//缺少MCC

        ChannelResponseDto channelResult = channelService.preAuthCapture(paymentOrderDTO, origChannelOrder, paymentInfo, controller);

        paymentResult.setMerchantBillName(origChannelOrder.getMerchantBillName());
        paymentResult.setOrgMerchantCode(origChannelOrder.getMerchantNo());
        String channelRespCode = channelResult.getChannelRespCode();
        String errorCode = channelResult.getResponseCode();
        String errorMsg = channelResult.getResponseDesc();
        String merchantBillName = channelResult.getMerchantBillName();

        tradeOrderDTO.setRespCode(errorCode);
        tradeOrderDTO.setRespMsg(errorMsg);
        tradeOrderDTO.setMerchantBillName(merchantBillName);
        tradeOrderDTO.setUpdateDate(new Date());
        paymentOrderDTO.setUpdateDate(new Date());
        paymentInfo.setCurrencyCode(origTradeOrder.getCurrencyCode());

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
        paymentResult.setMerchantBillName(origChannelOrder.getMerchantBillName());
        long payAmounttran = channelResult.getPayAmount() == null ? 0l : channelResult.getPayAmount();
        notifyChannel(String.valueOf(controller.getMemberCurrentId()),
                payAmounttran, channelResult.getPayCurrencyCode(),
                paymentInfo.getPartnerId(), String.valueOf(controller.getPaymentChannelItemId()),
                ResponseCodeEnum.SUCCESS.getCode().equals(errorCode),
                String.valueOf(controller.getChannelConfigId()), null);

        controller.setChannelResponseNo(channelResult.getChannelReturnNo());
        controller.setActCurrencyCode(channelResult.getPayCurrencyCode());

        boolean updateFailureTradeOrder = true;
        if (ResponseCodeEnum.SUCCESS.getCode().equals(errorCode)) {
            // 有成功的订单就跳出，不再进行交易了
            // 更新支付订单和网关订单状态
            updateFailureTradeOrder = false;//更新网管订单为成功
            paymentOrderDTO.setStatus(PaymentOrderStatusEnum.SUCCESS.getCode());
            paymentOrderDTO.setCompleteDate(new Date());
            paymentOrderDTO.setOrgCode(channelResult.getOrgCode());
            boolean updateFlag = paymentOrderService.updatePaymentOrderRnTx(paymentOrderDTO,
                    PaymentOrderStatusEnum.INIT.getCode());
            if (!updateFlag) {
                logger.error("do not update paymentOrder," + paymentOrderNo);
            }

            TradeOrderDTO origDto = new TradeOrderDTO();
            BeanUtils.copyProperties(origTradeOrder, origDto);
            origDto.setStatus(23);
            tradeOrderService.updateTradeOrderRnTx(origDto);
            // 保存清算订单
            List<PartnerSettlementOrder> settlementOrders = OrderBuilder.buildSettlementOrder(paymentOrderNo,
                    tradeBaseDTO, tradeOrderDTO, tradeBaseDTO.getSettlementCurrencyCode(), controller.getCardOrg(),
                    enterpriseBaseService, channelService);
            partnerSettlementOrderService.createPartnerSettlementOrder(settlementOrders);
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
        }
        controller.setCaptureStatus(updateFailureTradeOrder ? "2" : "1");
        controller.setLastestUpdateType("1");
        controller.setLastestUpdateNo(tradeOrderDTO.getTradeOrderNo());
        preControllerService.updateAuthControllerRnTx(controller);
        finalUpdateTradeOrderResult(tradeOrderDTO, paymentWay, paymentInfo.getPaymentWay(), updateFailureTradeOrder, "1");
        return paymentResult;
    }


    private void finalUpdateTradeOrderResult(TradeOrderDTO tradeOrderDTO, String paymentWay, String paymentInfoSubPaymentWay, boolean updateFailureTradeOrder, String model) {
        tradeOrderDTO.setCompleteDate(new Date());
        tradeOrderDTO.setStatus(updateFailureTradeOrder ? TradeOrderStatusEnum.FAILED.getCode() : TradeOrderStatusEnum.SUCCESS.getCode());
        if("0".equals(model)){
            tradeOrderDTO.setStatus(updateFailureTradeOrder ? 24 : 21);
        }
        tradeOrderDTO.setPaymentWay(
                PaymentWayEnum.PAYMENT_LINK.getCode().equalsIgnoreCase(paymentInfoSubPaymentWay)
                        ? PaymentWayEnum.PAYMENT_LINK.getCode() : paymentWay);
        if ("1".equals(model) && !updateFailureTradeOrder) {//正常支付
            tradeOrderDTO.setRefundAmount(tradeOrderDTO.getOrderAmount());
        }
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
            params.put("point", PaymentServiceImpl.getTime());
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


    private String getCardCurrencyCode(PaymentInfo paymentInfo) {
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
        return cardCurrencyCode;
    }

    private void setAmountStuff(PaymentInfo paymentInfo, TradeOrderDTO tradeOrderDTO) {
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
    }

    private PaymentResult doNoChannelProcess(PaymentResult paymentResult, TradeOrderDTO tradeOrderDTO) {
        logger.info("channel config error: " + ExceptionCodeEnum.CHANNEL_CONFING_FAIL);
        paymentResult.setResponseCode(ExceptionCodeEnum.CHANNEL_CONFING_FAIL.getCode());
        paymentResult.setResponseDesc(ExceptionCodeEnum.CHANNEL_CONFING_FAIL.getDescription());
        tradeOrderDTO.setCompleteDate(new Date());
        tradeOrderDTO.setStatus(TradeOrderStatusEnum.FAILED.getCode());
        tradeOrderDTO.setRespCode(paymentResult.getResponseCode());
        tradeOrderDTO.setRespMsg("商户无可用渠道");

        boolean tradeUpFlg = tradeOrderService.updateTradeOrderRnTx(tradeOrderDTO,
                TradeOrderStatusEnum.PROCESSING.getCode());
        if (!tradeUpFlg) {
            logger.error("do not update trade order," + tradeOrderDTO.getTradeOrderNo());
        }

        return paymentResult;
    }

    protected TransactionRate getTransactionRate(PaymentInfo paymentInfo) {
        Map<String, Object> param = new HashMap<String, Object>();
        param.put("currency", paymentInfo.getCurrencyCode());
        param.put("targetCurrency", paymentInfo.getSettlementCurrencyCode());
        param.put("status", "1");
        param.put("memberCode", paymentInfo.getPartnerId());
        param.put("cardOrg", CardOrgUtil.getCardType(paymentInfo.getCardHolderNumber()));
        param.put("ltaCurrencyCode", "USD");
        param.put("point", PaymentServiceImpl.getTime());

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

    private List<PaymentChannelItemDto> getUseFullChannelItem(PaymentInfo paymentInfo, TradeOrderDTO tradeOrderDTO, List<PaymentChannelItemDto> channels) {
        if (channels != null && channels.size() > 0) {
            PaymentServiceImpl.removeZeroPriority(channels); //first
            Map<String, String> params = new HashMap<String, String>();
            params.put("memberCode", paymentInfo.getPartnerId());

            //根据会员号获取该商户配置的指定国家的通道
            Map<String, List<PartnerChannelCountry>> pccMap = channelClientService.queryPartnerCountryChannels(params);

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
                    logger.info("ipCtry: " + ipCtry);
                    if (!COUNTRY.contains(ipCtry))//如果获取的国家不在指定的国家范围内就顶为其他国家
                        ipCtry = "000";
                }
            }
            logger.info("ipCtry: " + ipCtry);
            logger.info("pccMap: " + pccMap);
            //重新过滤掉不需要的渠道
            PaymentServiceImpl.getChannelS(channels, ipCtry, pccMap);


        }
        return channels;
    }

    // 路由到所有的可用渠道
    private List<PaymentChannelItemDto> routeChannels(PaymentInfo paymentInfo) {
        MemberBaseInfoBO memberBaseInfoBO = memberQueryService
                .queryMemberBaseInfoByMemberCode(Long.parseLong(paymentInfo.getPartnerId()));
        String partnerId = paymentInfo.getPartnerId();
        String paymentType = PaymentTypeEnum.PREAUTH.getCode();
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
        // addby liu 添加3d模型判断 先不加，留作以后做判断
        /*if (channels != null) {
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
        }*/

        return channels;
    }

    private PaymentResult saveException(PaymentResult paymentResult, TradeOrderDTO tradeOrderDTO) {
        paymentResult.setResponseCode("3099");
        paymentResult.setResponseDesc("Other Error:其他异常");
        tradeOrderDTO.setStatus(TradeOrderStatusEnum.FAILED.getCode());
        tradeOrderDTO.setRespCode("3099");
        tradeOrderDTO.setCompleteDate(new Date());
        tradeOrderDTO.setRespMsg("Other Error:其他异常");
        paymentService.getTradeOrderService().updateTradeOrderRnTx(tradeOrderDTO);
        return paymentResult;
    }


    private void sendCounting(PaymentInfo paymentInfo, PaymentResult paymentResult) {
        String partnerId = paymentInfo.getPartnerId();
        //去除人工交易
        if (!"10000003681".equals(partnerId) && !"10000003765".equals(partnerId) && !"10000003766".equals(partnerId) && !"10000003767".equals(partnerId) && !"10000003768".equals(partnerId) && !"10000003770".equals(partnerId)) {
            BlacklistCheckNotifyRequest bnotifyRequest = new BlacklistCheckNotifyRequest();
            Map<String, String> bnotifyMap = MapUtil.bean2map(paymentInfo);
            bnotifyMap.put("result", paymentResult.getResponseCode());

            bnotifyMap.put("responseDesc", paymentResult.getResponseDesc());
            bnotifyMap.put("cardCountry", paymentInfo.getCardCountry());
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String datetime = formatter.format(new Date());
            bnotifyMap.put("completeDate", datetime);
            bnotifyRequest.setData(bnotifyMap);
            bnotifyRequest.setMerchantCode(paymentInfo.getPartnerId());
            paymentService.getJmsSender().send("notify.forpay.blacklist", bnotifyRequest);
        }
    }

    private void dupliCheck(PaymentInfo paymentInfo) {
        PaymentResult paymentResult = new PaymentResult();
        Map<String, String> params = new HashMap<String, String>();
        params.put("orderId", paymentInfo.getOrderId());
        params.put("memberCode", paymentInfo.getPartnerId());
        params.put("status", "1,2,3,4,20");

        TradeOrderDTO tradeOrderDTO = paymentService.getTradeOrderService()
                .queryCompletedTradeOrder(params);
        // 检查商户订单号对应的是否有已成功交易的。
        if (null != tradeOrderDTO) {
            throw new BusinessException("tradeOrder was exists",
                    ExceptionCodeEnum.ORDER_IS_COMPLETED_OR_SUCCESS);
        }
    }

    private void notifyChannel(String memberCurrentId, long payAmount, String payCurreneyCode, String partnerId,
                               String paymentChannelId, boolean success, String channelConfigId, String failflag) {
        try {
            TransactionBaseRate baseRate_ = channelService.getCurrencyRateService()
                    .findTransactionBaseRate(payCurreneyCode, "CNY", "1", null);
            BigDecimal rateO = BigDecimal.ZERO;
            if (baseRate_ != null) {
                rateO = new BigDecimal(baseRate_.getExchangeRate());
                rateO = rateO.multiply(new BigDecimal(payAmount));
            } else {
                logger.error("没有找到[" + payCurreneyCode + "]到[CNY]的基本汇率");
            }
            ChannelMidAmountNotifyRequest notifyRequest = new ChannelMidAmountNotifyRequest();
            Map<String, String> notifyMap = new HashMap<String, String>();
            notifyMap.put("memberCurrentId", memberCurrentId);
            notifyMap.put("partnerId", partnerId);
            notifyMap.put("paymentChannelItemId", paymentChannelId);
            notifyMap.put("payAmount", rateO.toString());
            notifyMap.put("success", success ? "1" : "0");
            notifyMap.put("failure", failflag);
            notifyMap.put("channelConfigId", channelConfigId);
            notifyRequest.setData(notifyMap);
            notifyRequest.setMerchantCode(partnerId);
            jmsSender.send("notify.freeAuto", notifyRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
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

    @Override
    public PaymentResult preAuthCapture(PaymentInfo paymentInfo) {
        String memberCode = paymentInfo.getPartnerId();
        String origOrderId = paymentInfo.getOrigOrderId();

        PreController controller = preControllerService.getAuthControllerByMemberCodeAndOrderId(origOrderId, Long.parseLong(memberCode));
        PreOrderCheck(controller, paymentInfo, "1");//先检查
        PaymentResult paymentResult = new PaymentResult();
        dupliCheck(paymentInfo);
        TradeExtendsDTO tradeExtendsDTO = tradeExtendsService.findByTradeOrderNo(controller.getTradeOrderNo());
        TradeOrder origTradeOrder = (TradeOrder) tradeOrderDAO.findById(controller.getTradeOrderNo());
        //add by zhaoyang 20160914 获得tradeBase对象以取得结算币种
        TradeBase origTradeBase = tradeBaseDAO.findById(origTradeOrder.getTradeBaseNo());
        paymentInfo.setSettlementCurrencyCode(origTradeBase.getSettlementCurrencyCode());
        PaymentOrder origPaymentOrder = paymentOrderDAO.findById(controller.getPaymentOrderNo());
        ChannelOrder origChannelOrder = (ChannelOrder) channelOrderDAO.findById(controller.getChannelOrderNo());
        Map<String,Object> queryTradeDateMap = new HashMap<String,Object>();
        queryTradeDateMap.put("tradeOrderNo",origTradeOrder.getTradeOrderNo());
        TradeData tradeDateFind = tradeDataService.queryTradeDate(queryTradeDateMap);
        paymentInfo.setSettlementCurrencyCode(origTradeBase.getSettlementCurrencyCode());
        paymentInfo = rebuilPaymentInfo(paymentInfo,tradeDateFind,tradeExtendsDTO,controller,origChannelOrder);


//        paymentService.setSettlementCurrencyCode(paymentInfo);

        // first setup save traderOrder
        TradeBaseDTO tradeBaseDTO = OrderBuilder.buildTradeBase(paymentInfo);
        TradeOrderDTO tradeOrderDTO = OrderBuilder.buildTradeOrder(paymentInfo);
        tradeOrderDTO.setLinkTradeNo(controller.getTradeOrderNo());
        Long tradeOrderNo = paymentService.getTradeOrderService().saveTradeOrderRnTx(tradeBaseDTO,
                tradeOrderDTO);
        tradeOrderDTO.setTradeOrderNo(tradeOrderNo);
        paymentInfo.setPaymentWay("A");
        paymentInfo.setTradeOrderNo(tradeOrderNo);
        paymentInfo.setOrderAmount(String.valueOf(tradeOrderDTO.getOrderAmount()));


        // 保存额外信息
        paymentService.saveExtendsInfo(paymentInfo, tradeOrderDTO);
        String tradeType = paymentInfo.getTradeType();

        paymentService.riskValidate(paymentInfo, tradeOrderDTO, paymentResult);
        String desc = paymentResult.getRiskDesc();
        logger.info("paymentResult: " + paymentResult);
        if (!"ACCEPT".equals(desc)) {
            return paymentResult;
        }

        paymentResult = doAuthCap(paymentInfo, tradeOrderDTO, tradeBaseDTO, PaymentWayEnum.API.getCode(), controller,
                origTradeOrder,origPaymentOrder,origChannelOrder,
                paymentResult);
        try {
            sendCounting(paymentInfo, paymentResult);
        } catch (Exception e) {
            logger.info("send notify.forpay.blacklist fail"
                    + tradeOrderDTO.getTradeOrderNo());
        }
        paymentResult.setTradeOrderNo(tradeOrderDTO.getTradeOrderNo());
        paymentResult.setCompleteTime(DateUtil.formatDateTime(
                DateUtil.PATTERN1, tradeOrderDTO.getCompleteDate()));
        return paymentResult;
    }

    public void PreOrderCheck(PreController controller, PaymentInfo paymentInfo, String modelType) {
        if (controller == null) {
            throw new BusinessException("预授权订单不存在", ExceptionCodeEnum.PRE_AUTH_ORDER_NO_EXIST);
        }
        if ("1".equals(modelType)) {
            if (!"1".equals(controller.getAuthorStatus())) {
                throw new BusinessException("预授权不成功直接失败", ExceptionCodeEnum.PRE_AUTH_CAP_FAIL);
            }
            long leftMoney = controller.getPreAuthorAmount() - controller.getCaptureAmountTotal();
            if (leftMoney/10 < Long.parseLong(paymentInfo.getOrderAmount())) {
                throw new BusinessException("预授权金额不够扣", ExceptionCodeEnum.PRE_AUTH_CAP_AMOUNT);
            }
            if ("1".equals(controller.getAuthorVoidStatus())) {
                throw new BusinessException("预授权已经撤销", ExceptionCodeEnum.PRE_CAP_ALREADY_VOID);
            }
        } else {
            if (!("1".equals(controller.getAuthorStatus()) && "0".equals(controller.getCaptureStatus()))) {
                throw new BusinessException("不能允许预授权撤销", ExceptionCodeEnum.PRE_AUTH_VOID_FAIL);
            }
            if ("1".equals(controller.getAuthorVoidStatus())) {
                throw new BusinessException("预授权已经撤销", ExceptionCodeEnum.PRE_VOID_ALREADY_VOID);
            }
        }
    }

    private PaymentInfo rebuilPaymentInfo(PaymentInfo paymentInfo,TradeData tradeDateFind,
                                          TradeExtendsDTO tradeExtendsDTO,PreController controller,
                                          ChannelOrder origChannelOrder){
        paymentInfo.setCurrencyCode(controller.getPreCurrencyCode());
        paymentInfo.setCardHolderNumber(tradeExtendsDTO.getCardHolderCardNo());
        String carExm = tradeDateFind.getExpMonth();
        String carExy = tradeDateFind.getExpYear();
        String carSer = tradeDateFind.getSecCode();
        String carFrs = tradeDateFind.getBillFirstName();
        String carLrs = tradeDateFind.getBillLastName();

        if((!StringUtil.isEmpty(carExm)) && carExm.length() > 4){
            paymentInfo.setCardExpirationMonth(carExm.substring(2,carExm.length() - 2));
        }
        if((!StringUtil.isEmpty(carExy)) && carExy.length() > 4){
            paymentInfo.setCardExpirationYear(carExy.substring(2,carExy.length() - 2));
        }

        if((!StringUtil.isEmpty(carSer)) && carSer.length() > 4){
            paymentInfo.setSecurityCode(carSer.substring(2,carSer.length() - 2));
        }
        paymentInfo.setCardHolderFirstName(carFrs);
        paymentInfo.setCardHolderLastName(carLrs);
//        paymentInfo.setSettlementCurrencyCode(tradeDateFind.getSettlementCurrencyCode());
        paymentInfo.setSiteId(tradeDateFind.getSiteId());
        paymentInfo.setCustomerIP(tradeDateFind.getCustomerIP());
        paymentInfo.setPayType(tradeDateFind.getPayType());
        paymentInfo.setGoodsName(tradeDateFind.getGoodsName());

        paymentInfo.setOrgCode(origChannelOrder.getOrgCode());
        paymentInfo.setOrgKey(origChannelOrder.getOrgKey());
        paymentInfo.setAccessCode(origChannelOrder.getAccessCode());
        paymentInfo.setOrgMerchantCode(origChannelOrder.getMerchantNo());
        paymentInfo.setMerchantBillName(origChannelOrder.getMerchantBillName());
        paymentInfo.setTerminalCode(controller.getTerminalCode());
        paymentInfo.setMcc(controller.getMcc());
        paymentInfo.setCardOrg(controller.getCardOrg());
        paymentInfo.setChannelCurrencyCode(origChannelOrder.getTransferCurrencyCode());
        paymentInfo.setAuthorisation(origChannelOrder.getAuthorisation());
        return paymentInfo;
    }

    @Override
    public PaymentResult preAuthVoid(PaymentInfo paymentInfo) {

        String memberCode = paymentInfo.getPartnerId();
        String origOrderId = paymentInfo.getOrigOrderId();
        logger.info("preAuthVoid==> origOrderId="+origOrderId+",memberCode="+memberCode);
        PreController controller = preControllerService.getAuthControllerByMemberCodeAndOrderId(origOrderId, Long.parseLong(memberCode));
        PreOrderCheck(controller, paymentInfo, "2");//先检查
        Map map = new HashMap();
        map.put("memberCode",Long.parseLong(paymentInfo.getPartnerId()));
        map.put("requestId", paymentInfo.getOrderId());
        List<AuthChangeOrderDTO> authChangeOrderDTOs = authChaneOrderDAO.findList(map);
        if(authChangeOrderDTOs != null && authChangeOrderDTOs.size() > 0){
            throw new BusinessException("tradeOrder was exists",
                    ExceptionCodeEnum.ORDER_IS_COMPLETED_OR_SUCCESS);
        }
        paymentInfo.setOrderAmount(controller.getActAuthorAmount() + "");

        AuthChangeOrder saveAuthChangeOrder = authChaneOrderService.saveAuthControllerRnTx(paymentInfo.getOrderId(),controller.getId());

        PaymentResult paymentResult = new PaymentResult();

        paymentResult = doAuthVoid(paymentInfo, controller, paymentResult,saveAuthChangeOrder);
        boolean success = paymentResult != null && ResponseCodeEnum.SUCCESS.getCode().equals(paymentResult.getResponseCode());
        if(success){
            saveAuthChangeOrder.setChannelResponseCode(paymentResult.getResponseCode());
            saveAuthChangeOrder.setChannelResponseDesc(paymentResult.getResponseDesc());
            paymentResult.setOrderAmount(controller.getPreAuthorAmount() + "");
            paymentResult.setResponseCode(ExceptionCodeEnum.PRE_AUTH_VOID_SUCCESS.getCode());
            paymentResult.setResponseDesc(ExceptionCodeEnum.PRE_AUTH_VOID_SUCCESS.getDescription());
        }
        paymentResult.setTradeOrderNo(saveAuthChangeOrder.getId());
        paymentResult.setCompleteTime(DateUtil.formatDateTime(
                DateUtil.PATTERN1, new Date()));
        return paymentResult;
    }

    private PaymentResult doAuthVoid(PaymentInfo paymentInfo, PreController controller, PaymentResult paymentResult,final AuthChangeOrder saveAuthChangeOrder) {
        Map<String,Object> queryTradeDateMap = new HashMap<String,Object>();
        queryTradeDateMap.put("tradeOrderNo",controller.getTradeOrderNo());
        TradeData tradeDateFind = tradeDataService.queryTradeDate(queryTradeDateMap);
        TradeExtendsDTO tradeExtendsDTO = tradeExtendsService.findByTradeOrderNo(controller.getTradeOrderNo());
        ChannelOrder origChannelOrder = (ChannelOrder) channelOrderDAO.findById(controller.getChannelOrderNo());
        paymentInfo = rebuilPaymentInfo(paymentInfo,tradeDateFind,tradeExtendsDTO,controller,origChannelOrder);
        TradeOrder origTradeOrder = (TradeOrder) tradeOrderDAO.findById(controller.getTradeOrderNo());
        ChannelResponseDto channelResult = channelService.preAuthVoid(paymentInfo,controller,origChannelOrder,saveAuthChangeOrder);
        controller.setAuthorVoidStatus(ResponseCodeEnum.SUCCESS.getCode().equals(channelResult.getResponseCode()) ? "1" : "2");
        if(ResponseCodeEnum.SUCCESS.getCode().equals(channelResult.getResponseCode())){
            controller.setLastestUpdateType("2");//成功
            controller.setLastestUpdateNo(saveAuthChangeOrder.getId());
            TradeOrderDTO origDto = new TradeOrderDTO();
            BeanUtils.copyProperties(origTradeOrder, origDto);
            origDto.setStatus(22);
            tradeOrderService.updateTradeOrderRnTx(origDto);
        }
        authChaneOrderService.updateAuthChangeOrderRnTx(saveAuthChangeOrder);
        paymentResult.setResponseCode(channelResult.getResponseCode());
        paymentResult.setResponseDesc(channelResult.getResponseDesc());
        preControllerService.updateAuthControllerRnTx(controller);
        paymentResult.setDealId(saveAuthChangeOrder.getId() + "");
        return paymentResult;
    }

    public PaymentResult cashierPreAuthApply(PaymentInfo paymentInfo){
        PaymentResult paymentResult = new PaymentResult();
        Long tradeOrderNo = paymentInfo.getTradeOrderNo();
        TradeOrderDTO tradeOrderDTO = paymentService.getTradeOrderService()
                .queryTradeOrderById(Long.valueOf(tradeOrderNo));
        // 订单不存在
        if (null == tradeOrderDTO) {
            throw new BusinessException("tradeOrder not exists",
                    ExceptionCodeEnum.ORDER_NOT_EXIST);
        }
        if (tradeOrderDTO.getStatus() == TradeOrderStatusEnum.PROCESSING
                .getCode()) {
            throw new BusinessException("tradeOrder is processing",
                    ExceptionCodeEnum.ORDER_DOUBLED);
        }
        // 订单已支付
        if (tradeOrderDTO.getStatus() != TradeOrderStatusEnum.WAIT_PAY
                .getCode()) {
            throw new BusinessException("tradeOrder was payment",
                    ExceptionCodeEnum.TRANSACTIONS_PAYMENTS);
        }
        // 基础订单
        TradeBaseDTO tradeBaseDTO = paymentService.getTradeOrderService()
                .queryTradeBaseById(tradeOrderDTO.getTradeBaseNo());
        TradeExtendsDTO tradeExtendsDTO = paymentService.getTradeExtendsService()
                .findByTradeOrderNo(Long.valueOf(tradeOrderNo));
        if (null == tradeExtendsDTO) {
            throw new BusinessException("TradeExtends not exists",
                    ExceptionCodeEnum.ORDER_NOT_EXIST);
        }

        // 更新 TradeExtendsDTO 2016-05-23 sch.
        paymentService.updateTradesExtendsDTO(tradeExtendsDTO, paymentInfo);
        // end 2016-05-23
        // 更新交易数据
        paymentService.updateTradeData(paymentInfo, tradeOrderDTO);

        String settlementCurrencyCode = tradeBaseDTO
                .getSettlementCurrencyCode();
        paymentInfo.setSettlementCurrencyCode(settlementCurrencyCode);
        Map<String,Object> params = new HashMap<String, Object>();
        params.put("tradeOrderNo",tradeOrderDTO.getTradeOrderNo());

        TradeData tradeData = paymentService.getTradeDataService().queryTradeDate(params);
        paymentInfo.setOrderAmount(String.valueOf(tradeOrderDTO.getOrderAmount()));
        paymentInfo.setBillAddress(tradeData.getBillAddress());
        paymentInfo.setBillCity(tradeData.getBillCity());
        paymentInfo.setBillCountryCode(tradeData.getBillCountry());
        paymentInfo.setBillPostalCode(tradeData.getBillPostCode());
        paymentInfo.setBillEmail(tradeData.getBillEmail());
        paymentInfo.setBillFirstName(tradeData.getBillFirstName());
        paymentInfo.setBillLastName(tradeData.getBillLastName());
        paymentInfo.setBillState(tradeData.getBillState());
        paymentInfo.setBillStreet(tradeData.getBillStreet());
        paymentInfo.setShippingAddress(tradeData.getShippingAddress());
        paymentInfo.setShippingCity(tradeData.getShippingCity());
        paymentInfo.setShippingCountryCode(tradeData.getShippingCountry());
        paymentInfo.setShippingMail(tradeData.getShippingEmail());
        paymentInfo.setShippingPostalCode(tradeData.getShippingPostalCode());
        paymentInfo.setShippingState(tradeData.getShippingState());
        paymentInfo.setCustomerIP(tradeData.getCustomerIP());
        paymentService.riskValidate(paymentInfo, tradeOrderDTO, paymentResult);
        String desc = paymentResult.getRiskDesc();
        if(!"ACCEPT".equals(desc)){
            return paymentResult;
        }
        PreController controller = preControllerService.saveAuthControllerRnTx(
                paymentInfo.getOrderId(), Long.parseLong(paymentInfo.getPartnerId()), Long.parseLong(paymentInfo.getOrderAmount()), paymentInfo.getCurrencyCode());
        try {
            paymentResult = doAuthApplay(paymentInfo, tradeOrderDTO, tradeBaseDTO, PaymentWayEnum.CASHIER.getCode(), controller);
            try {
                sendCounting(paymentInfo, paymentResult);
            } catch (Exception e) {
                logger.info("send notify.forpay.blacklist fail"
                        + tradeOrderDTO.getTradeOrderNo());
            }
        } catch (Exception e) {
            e.printStackTrace();
            return saveException(paymentResult, tradeOrderDTO);
        }
        paymentResult.setNotifyUrl(tradeBaseDTO.getNotifyUrl());
        paymentResult.setNoticeUrl(tradeBaseDTO.getNotifyUrl());
        paymentResult.setReturnUrl(tradeBaseDTO.getReturnUrl());
        paymentResult.setTradeOrderNo(tradeOrderDTO.getTradeOrderNo());
        paymentResult.setCompleteTime(DateUtil.formatDateTime(
                DateUtil.PATTERN1, tradeOrderDTO.getCompleteDate()));
        return paymentResult;
    }

	public void setTradeBaseDAO(TradeBaseDAO tradeBaseDAO) {
		this.tradeBaseDAO = tradeBaseDAO;
	}

}


