package com.pay.gateway.controller.cashier;

import com.pay.acc.member.model.MemberProduct;
import com.pay.acc.member.service.MemberBaseProductService;
import com.pay.acc.member.service.MemberService;
import com.pay.acc.service.account.constantenum.MemberTypeEnum;
import com.pay.acc.service.member.MemberProductService;
import com.pay.cardbin.model.CardBinInfo;
import com.pay.cardbin.model.CountryCurrency;
import com.pay.cardbin.service.CardBinInfoService;
import com.pay.cardbin.service.CountryCurrencyService;
import com.pay.dcc.model.PartnerDCCConfig;
import com.pay.dcc.service.ConfigurationDCCService;
import com.pay.fi.commons.PaymentTypeEnum;
import com.pay.fi.commons.TerminalType;
import com.pay.fi.commons.TransTypeEnum;
import com.pay.fi.helper.ChannelItemOrgCodeEnum;
import com.pay.fi.service.TradeDataSingnatureService;
import com.pay.gateway.client.ChannelClientService;
import com.pay.gateway.client.TxncoreClientService;
import com.pay.gateway.dto.*;
import com.pay.gateway.service.BuilderPayGateService;
import com.pay.inf.enums.DCCEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.jms.notification.request.CardBinNotifyRequest;
import com.pay.jms.notification.request.ExceptionCardNotifyRequest;
import com.pay.jms.notification.request.HttpNotifyRequest;
import com.pay.jms.sender.JmsSender;
import com.pay.util.*;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.math.BigDecimal;
import java.math.MathContext;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cuber.huang on 2016/7/21.
 */
public class CashierController extends MultiActionController {
    MathContext mc = new MathContext(2, RoundingMode.UP);
    DecimalFormat df = new DecimalFormat("##.##");
    private final Log logger = LogFactory
            .getLog(CashierController.class);
    private TxncoreClientService txncoreClientService;
    private ChannelClientService channelClientService;
    private BuilderPayGateService payGateService;
    private MemberService memberService;
    private CardBinInfoService cardBinInfoService;
    private CountryCurrencyService currencyService;
    private ConfigurationDCCService dccService;
    static String PRODUCT_CODE_DCC = "DCC";
    static String PRODUCT_CODE_DCC_EDC = "DCC_EDC";
    static String PRODUCT_CODE = "CUSTOM_DCC";
    protected MemberProductService memberProductService;
    private String failView;
    private String enFailView;
    private String cnFailView;
    private String enMobileFailView;
    private String cnMobileFailView;
    private String successView;
    private String enSuccessView;
    private String cnSuccessView;
    private String pcEnSuccessView;
    private String pcCnSuccessView;
    private String dccView;
    private String mobileDccView;
    private String enDccView;
    private String cnDccView;
    private String pcCnFailView;
    private String pcEnFailView;
    private String enMobileDccView;
    private String cnMobileDccView;
    private String cnMobileSuccessView;
    private String enMobileSuccessView;
    private MemberBaseProductService memberBaseProductService;

    private JmsSender jmsSender;
    private TradeDataSingnatureService tradeDataSingnatureService;

    public void setTradeDataSingnatureService(TradeDataSingnatureService tradeDataSingnatureService) {
        this.tradeDataSingnatureService = tradeDataSingnatureService;
    }

    public ModelAndView onSubmit(final HttpServletRequest request,
                                 final HttpServletResponse response, final PaymentRequest paymentDto)
            throws Exception {
    	System.out.println(paymentDto);
        response.setCharacterEncoding("utf-8");
        String orgCode = paymentDto.getOrgCode();
        if(!StringUtil.isEmpty(paymentDto.getMapData())){
            paymentDto.setData(JSonUtil.toObject(paymentDto.getMapData(),Map.class));
        }
        if(StringUtils.isEmpty(paymentDto.getLanguage())){
            paymentDto.setLanguage("en");//默认英文
        }
        ValidateResponse validResponse = payGateService.validation(paymentDto);
        if(validResponse.isPass()){//验证通过
            if("0000".equals(orgCode)){//进入国际化支付
                return gotoNagtionPay(request,response,paymentDto);
            }else{//一般通用支付
                Map<String, Object> returnMap = txncoreClientService.channelPayment(paymentDto);
                PaymentResponse responseFront = new PaymentResponse();//显示错误
                String responseDesc = (String)returnMap.get("responseDesc");
                String[] errMsgs = StringUtils.split(responseDesc, ":");
                if(errMsgs != null && errMsgs.length == 2){
                    responseDesc = "en".equals(paymentDto.getLanguage()) ? errMsgs[0]:errMsgs[1];
                }
                BeanUtils.copyProperties(paymentDto,responseFront);
                responseFront.setResultCode((String)returnMap.get("responseCode"));
                responseFront.setErrorMsg(responseDesc);//设置错误显示信息
                if(!"0000".equals(responseFront.getResultCode())){
                    return this.getErrorModelAndView("en", paymentDto.getOrderTerminal(), responseFront);
                }else{
                    Map<String,Object> returnFromFront = (Map<String,Object>)returnMap.get("backfromFront");
                    String continueUrl = (String) returnFromFront.get("continueUrl");
                    Map<String,String> submitDataMap = (Map<String,String>)returnFromFront.get("submitData");
                    if(ChannelItemOrgCodeEnum.CT_BOLETO.getCode().equals(orgCode)){
                        return this.getBoletoHoldView(paymentDto.getLanguage(), paymentDto.getOrderTerminal(), responseFront);
                    }else if(ChannelItemOrgCodeEnum.BELTO.getCode().equals(orgCode)){
                        return this.getBoletoHoldView(paymentDto.getLanguage(), paymentDto.getOrderTerminal(), responseFront, continueUrl);
                    }
                    if(StringUtil.isEmpty(continueUrl)){//显示支付完成
                        return this.getSuccessView(paymentDto.getLanguage(), paymentDto.getOrderTerminal(), responseFront);
                    }else{//采用下面方法跳转
                        StringBuffer submitform=new StringBuffer();
                        submitform.append("<html><head></head><BODY onload='document.forms[0].submit();'>");
                        submitform.append("<form action='" + continueUrl + "' ");
                        submitform.append(" method='post'>");
                        for(Map.Entry<String, String> entry : submitDataMap.entrySet()) {
                            submitform.append("<input type='hidden' name='" + entry.getKey() + "' value='"+entry.getValue()+"'>");
                        }
                        submitform.append("</form></BODY></html>");
                        response.getWriter().print(submitform);
                        response.getWriter().flush();
                    }
                }

            }
        }else{//验证不通过
            PaymentResponse responseFront = new PaymentResponse();//显示错误
            Payment4Save payment4Save = new Payment4Save();//更新订单
            BeanUtils.copyProperties(paymentDto,responseFront);
            BeanUtils.copyProperties(paymentDto,payment4Save);
            responseFront.setResultCode(validResponse.getErrCode());
            if("cn".equals(paymentDto.getLanguage())){
                responseFront.setErrorMsg(validResponse.getErrMsgCn());
            }else{
                responseFront.setErrorMsg(validResponse.getErrMsgEn());
            }
            payment4Save.setResultCode(validResponse.getErrCode());
            payment4Save.setResultMsg(responseFront.getResultMsg());
            Map<String,String> result = txncoreClientService.updateTradeOrder(payment4Save);
            logger.info("resultCode: "+result.get("responseCode")+",resultMsg: "+result.get("responseDesc"));
            return this.getErrorModelAndView(paymentDto.getLanguage(), paymentDto.getOrderTerminal(), responseFront);
        }
        return null;
    }

    private ModelAndView gotoNagtionPay(HttpServletRequest request,HttpServletResponse response,PaymentRequest paymentDto) throws Exception{
        Map<String,Object> resultMap = new HashMap<String,Object>();
        resultMap.put("orderId", paymentDto.getOrderId());
        resultMap.put("partnerId", paymentDto.getPartnerId());
        resultMap.put("tradeOrderNo", paymentDto.getTradeOrderNo());
        resultMap.put("dealDate", DateUtil.formatDateTime(
                DateUtil.SIMPLE_DATE_FROMAT, new Date()));
        resultMap.put("currencyCode", paymentDto.getCurrencyCode());
        resultMap.put("language",paymentDto.getLanguage());
        resultMap.put("returnUrl",paymentDto.getReturnUrl());

        logger.info("ip: "+paymentDto.getCustomerIP());
        PaymentInfo info = new PaymentInfo();
        BeanUtils.copyProperties(paymentDto,info);
        Map<String, String> travelData = paymentDto.getData();

        info.setBillName(travelData.get("cardHolderFirstName") + travelData.get("cardHolderLastName"));
        info.setCardHolderNumber(travelData.get("cardHolderNumber"));
        info.setCardExpirationMonth(travelData.get("cardExpirationMonth"));
        info.setCardHolderFirstName(travelData.get("cardHolderFirstName"));
        info.setCardHolderLastName(travelData.get("cardHolderLastName"));
        info.setCardExpirationYear(travelData.get("cardExpirationYear"));
        info.setSecurityCode(travelData.get("securityCode"));

        String cardHolderNumber = info.getCardHolderNumber() ;
        String bin = "" ;
        if((StringUtils.isNotEmpty(cardHolderNumber)) && (cardHolderNumber.length()>6)){
            bin = cardHolderNumber.substring(0, 6) ;
        }
        if(!StringUtils.isBlank(bin)){
            this.notifyCardBin(bin);
        }

        Map channelItems = channelClientService.getPaymentChannel(
                info.getPartnerId(), PaymentTypeEnum.PAYMENT.getCode()
                        + "", MemberTypeEnum.MERCHANT.getCode() + "", "");

        @SuppressWarnings("unchecked")
        List<Map> itemListMap = (List<Map>) channelItems
                .get("paymentChannelItems");

        @SuppressWarnings("unchecked")
        List<PaymentChannelItemDto> itemList = MapUtil.map2List(
                PaymentChannelItemDto.class, itemListMap);

        PaymentChannelItemDto paymentChannelItemDto = null;
        boolean isDCCFlg = false;
        if (null != itemList && !itemList.isEmpty()) {
            for (PaymentChannelItemDto item : itemList) {
                if (TransTypeEnum.DCC.getCode().equalsIgnoreCase(
                        item.getTransType())) {
                    paymentChannelItemDto = item;
                    isDCCFlg = true;
                    break;
                }else{
                    //走标准自建DCC
                    paymentChannelItemDto = item;
                }
            }
        }
        String memberCode = info.getPartnerId();
        logger.info("isDCCFlg: "+isDCCFlg);

        //-------------------------------------------------------------------------------------------
        ModelAndView dccView = null;

        //检查开通的DCC产品
        StringBuilder sb = new StringBuilder();
        sb.append("'").append(DCCEnum.CUSTOM_STANDARD.getCode()).append("','")
                .append(DCCEnum.CUSTOM_FORCED.getCode()).append("','")
                .append(DCCEnum.CUSTOM_HIDDEN.getCode()).append("','")
                .append(DCCEnum.PARTNER_DCC_PRDCT.getCode()).append("'");

        List<MemberProduct> list = memberBaseProductService.queryMemberProductsByMemberCode
                (Long.valueOf(memberCode), sb.toString());
        if(list!=null&&!list.isEmpty()){
            MemberProduct product = list.get(0);
            String prdtCode = product.getProductCode();

            logger.info("prdtCode: "+prdtCode);

            if(!StringUtil.isEmpty(prdtCode)){
                dccView = this.dccRounte(info, paymentChannelItemDto, resultMap,
                        isDCCFlg, info.getLanguage(), info.getOrderTerminal(), "",prdtCode);
                logger.info("PARTNER_DCC_PRDCT-isDCCFlg: "+isDCCFlg);
                if(dccView!=null)
                    return dccView;
            }
        }
        //---------------------------------------------------------------
        // EDC
        if (isDCCFlg) {
            Map<String, String> queryMap = new HashMap<String,String>();
            Map<String,String> rateMap = this.queryRate(info, paymentChannelItemDto,queryMap);
            logger.info("response rateMap:" + rateMap);
            String responseCode = rateMap.get("responseCode");
            resultMap.put("orgCode", paymentChannelItemDto.getOrgCode());
            resultMap.put("orgMerchantCode",
                    paymentChannelItemDto.getOrgMerchantCode());
            resultMap.putAll(MapUtil.bean2map(info));
            resultMap.putAll(queryMap);
            resultMap.putAll(rateMap);

            if ("99YY".equals(responseCode)) {
                String currency = rateMap.get("Currency");
                String conversionRateStr = rateMap.get("Conversion_Rate");
                String amountLocStr = rateMap.get("Amount_Loc")==null?"0":rateMap.get("Amount_Loc");
                String amountForStr = rateMap.get("Amount_For")==null?"0":rateMap.get("Amount_For");

                BigDecimal amountLoc = new BigDecimal(amountLocStr).divide(
                        new BigDecimal("100"), mc);
                BigDecimal amountFor = new BigDecimal(amountForStr).divide(
                        new BigDecimal("100"), mc);

                String amountForStr_ = df.format(amountFor);
                if (amountForStr_.startsWith(".")) {
                    amountForStr_ = "0" + amountForStr_;
                }

                String amountLocStr_ = df.format(amountLoc);
                if (amountLocStr_.startsWith(".")) {
                    amountLocStr_ = "0" + amountLocStr_;
                }

                String orderAmount = info.getOrderAmount();

                amountLocStr_ = new BigDecimal(orderAmount)
                        .divide(new BigDecimal("100")).toString();

                resultMap.put("cardHolderNumber",
                        info.getCardHolderNumber());

                resultMap.put("currencyCode",info.getCurrencyCode());

                String currencyCode = info.getCurrencyCode();
                String CurrencyCodeT = (String) resultMap.get("Currency_Code_T");

                //支付币种
                String payCurrencyCode = CurrencyNumKSEnum.getCurrencyCodeByNum(CurrencyCodeT);

                if(currencyCode.equals(currency)){
                    resultMap.put("Conversion_Rate","1.00");
                    amountForStr_ = amountLocStr_;
                }else{

                    Map<String, String> paraMap = new HashMap<String,String>();
                    paraMap.put("memberCode",memberCode);
                    paraMap.put("currency", currency);
                    paraMap.put("type","1");
                    paraMap.put("targetCurrency", payCurrencyCode);
                    paraMap.put("status","1");

                    //查询交易汇率
                    Map<String,Object> transRate = txncoreClientService.getTransactionRate(paraMap);

                    if(transRate!=null&&!StringUtil.isEmpty((String)transRate.get("exchangeRate"))){
                        BigDecimal rate = new BigDecimal((String)transRate.get("exchangeRate"));
                        BigDecimal conversionRate = new BigDecimal(conversionRateStr);
                        BigDecimal tmp = rate.multiply(conversionRate);

                        double rateTmp = tmp.setScale(4, BigDecimal.ROUND_HALF_UP).doubleValue();
                        resultMap.put("Conversion_Rate",String.valueOf(rateTmp));
                    }
                }

                boolean isHaveProduct = memberProductService.isHaveProduct(
                        Long.valueOf(info.getPartnerId()), PRODUCT_CODE_DCC);
                logger.info("isHaveProduct: "+isHaveProduct);
                //判断商户是否卡通强制DCC功能
                if(isHaveProduct){
                    info.setPrdtCode(DCCEnum.FORCED.getCode());
                    ModelAndView view = this.getForcedDccModelAndView(info.getLanguage(), info.getOrderTerminal(),
                            amountLocStr_, amountForStr_, "", paymentChannelItemDto.getOrgCode()
                            ,currency, paymentChannelItemDto.getOrgMerchantCode(), info);
                    view.addObject("prdtCode",DCCEnum.FORCED.getCode());
                    view.addObject("payType","DCC");
                    return view;
                }

                resultMap.put("prdtCode",DCCEnum.STANDARD.getCode());
                resultMap.put("payType","DCC");
                //dcc 页面
                return getDccModelAndView(info.getLanguage(), info.getOrderTerminal(),
                        amountLocStr_, amountForStr_, "", resultMap);
            }



        }
        //
        info.setPayType(TransTypeEnum.EDC.getCode());
        info.setPrdtCode("EDC");
        String billName=info.getCardHolderLastName()+info.getCardHolderFirstName();

        info.setBillName(billName);

        logger.info("billName: "+billName);

        Map<String,Object> returnMap = txncoreClientService.channelPayment(info);
        logger.info("txncoreClientService returnMap : "+returnMap);
        String responseCode = (String) returnMap.get("responseCode");
        String responseDesc = (String) returnMap.get("responseDesc");
        //=============当订单失败，增加异常卡判断异步处理机制， added by PengJiangbo sta===============================================
        //增加异常卡异步处理
        if(!ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)){
            //渠道返回码和渠道编号,特别注意， returnMap参数是收银台页面returnUrl返回给商户的参数并且会加入签名串，此处
            //channelRespCode和orgCode并不是需要给商户返回的，所以并不需要加入签名，否则会导致商户签名校验失败，
            //为防止这个错误发生，在异步调用处理异常卡处理请求之后，需要将returnUrl中的channelResponseCode和orgCode参数置为空
            String channelRespCode = (String) returnMap.get("channelRespCode") ;
            String _orgCode = (String) returnMap.get("orgCode") ;
            //this.notifyExceptionCard(responseCode, memberCode);
            this.notifyExceptionCard(channelRespCode, _orgCode, info.getPartnerId());
        }
        returnMap.put("channelRespCode", "") ;
        returnMap.put("orgCode", "") ;
        PaymentResult paymentResult = new PaymentResult();
        //=============当订单失败，增加异常卡判断异步处理机制， added by PengJiangbo end===============================================
        paymentResult = MapUtil.map2Object(PaymentResult.class, returnMap);
        paymentResult.setDealId(info.getTradeOrderNo());
        paymentResult.setRegisterUserId(paymentResult.getRegisterUserId());
        paymentResult.setResultCode(responseCode);
        paymentResult.setResultMsg(responseDesc);

        Map<String, String> resultMap_ = txncoreClientService
                .crosspayPartnerConfigQuery(paymentResult.getPartnerId(), "code1");
        String merchantKey = resultMap_.get("value");

        paymentResult.setOrderAmount(new BigDecimal(info.getOrderAmount())
                .divide(new BigDecimal("100")).toString());
        paymentResult.setPayAmount(null);
        paymentResult.setRemark("");
        paymentResult.setLanguage(info.getLanguage());

        String signData = paymentResult.generateSign();

        logger.info("收银台生成noticeUrl和returnUrl所需要的signMsg的源串为signData:"+ signData);
        String signMsg = tradeDataSingnatureService.genSignMsgBySignType(
                signData, paymentResult.getSignType(),
                paymentResult.getCharset(), merchantKey);

        if (!StringUtil.isEmpty(paymentResult.getNoticeUrl())) {
            //
            notifyMerchant(paymentResult, signMsg);
        }

        if (logger.isInfoEnabled()) {
            logger.info("responseCode:" + responseCode);
            logger.info("responseDesc:" + responseDesc);
        }
        resultMap.put("dealId", info.getTradeOrderNo());
        resultMap.put("resultCode", responseCode);
        resultMap.put("resultMsg", responseDesc);
        resultMap.put("remark","");
        resultMap.put("payAmount", paymentResult.getPayAmount());
        resultMap.put("merchantBillName", returnMap.get("merchantBillName"));
        resultMap.put("signMsg", signMsg);
        //payAmount置为空真实值不返回给商户 addedby PengJiangbo 2016.05.19
        returnMap.put("payAmount", "") ;
        resultMap.putAll(returnMap);
        resultMap.put("orderAmount",
                new BigDecimal(info.getOrderAmount())
                        .divide(new BigDecimal("100")));

        logger.info("resultMap-returnUrl: "+resultMap.get("returnUrl"));

        if (!ResponseCodeEnum.SUCCESS.getCode().equals(responseCode)) {
            resultMap.put("errorMsg", responseDesc);

            if(responseDesc!=null){
                String [] msgs = responseDesc.split(":");
                if("cn".equals(info.getLanguage())&&msgs!=null&&msgs.length>1){
                    resultMap.put("errorMsg",msgs[msgs.length-1]);
                }else if("en".equals(info.getLanguage())&&msgs!=null&&msgs.length>1){
                    resultMap.put("errorMsg",msgs[0]);
                }
            }

            if(TerminalType.MOBILE.getCode().equals(info.getOrderTerminal())){
                if("cn".equals(info.getLanguage())){
                    return new ModelAndView(cnMobileFailView, resultMap);
                }else if("en".equals(info.getLanguage())){
                    return new ModelAndView(enMobileFailView, resultMap);
                }
            }else if(TerminalType.PC.getCode().equals(info.getOrderTerminal())){
                if("cn".equals(info.getLanguage())){
                    return new ModelAndView(cnFailView, resultMap);
                }else if("en".equals(info.getLanguage())){
                    return new ModelAndView(enFailView, resultMap);
                }
            }

            return new ModelAndView(failView, resultMap);
        }
        if(TerminalType.MOBILE.getCode().equals(info.getOrderTerminal())){
            if("cn".equals(info.getLanguage())){
                return new ModelAndView(cnMobileSuccessView, resultMap);
            }else if("en".equals(info.getLanguage())){
                return new ModelAndView(enMobileSuccessView, resultMap);
            }
        }else{
            if("cn".equals(info.getLanguage())){
                return new ModelAndView(cnSuccessView, resultMap);
            }else if("en".equals(info.getLanguage())){
                return new ModelAndView(enSuccessView, resultMap);
            }
        }

        return new ModelAndView(successView, resultMap);
    }

    /**
     * added by Jiangbo.Peng
     * 异常卡异步处理方法
     * @param
     */
    private void notifyExceptionCard(String channelRespCode, String orgCode, String memberCode) { //(String responseCode, String memberCode)
        try {
            //发送mq消息到forpay
            ExceptionCardNotifyRequest notifyRequest = new ExceptionCardNotifyRequest();
            Map<String, String> data = new HashMap<String, String>() ;
            data.put("orgCode", orgCode) ;
            notifyRequest.setMerchantCode(memberCode) ;
            notifyRequest.setSystemRespCode(channelRespCode) ;
            notifyRequest.setData(data);
            //notifyRequest.setSystemRespDesc(systemDesc);
            jmsSender.send("notify.forpay",notifyRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    private ModelAndView getErrorModelAndView(String language,String orderTerminal, PaymentResponse responseFront){
        Map<String, Object> showPages = MapUtil.bean2map(responseFront);
        ModelAndView view = new ModelAndView(failView, showPages);
        if(TerminalType.MOBILE.getCode().equals(orderTerminal)){
            if("cn".equals(language)){
                view =  new ModelAndView(cnMobileFailView, showPages);
            }else if("en".equals(language)){
                view =  new ModelAndView(enMobileFailView, showPages);
            }
        }else if(TerminalType.PC.getCode().equals(orderTerminal)){
            if("cn".equals(language)){
                view =  new ModelAndView(cnFailView, showPages);
            }else if("en".equals(language)){
                view =  new ModelAndView(enFailView, showPages);
            }
        }else{
            if("cn".equals(language)){
                view =  new ModelAndView(pcCnFailView, showPages);
            }else if("en".equals(language)){
                view =  new ModelAndView(pcEnFailView, showPages);
            }
        }
        return view;
    }


    /**
     * DCC 产品获取
     * @return
     */
    private ModelAndView dccRounte(PaymentInfo paymentInfo
            ,PaymentChannelItemDto paymentChannelItemDto,
                                   Map<String,Object> resultMap,boolean isDCCFlg,
                                   String language,String orderTerminal,String remark,String prdtCode){
        String cardNumber = paymentInfo.getCardHolderNumber();
        String cardBin = cardNumber.substring(0, 6);
        CardBinInfo binInfo = cardBinInfoService.getCardBinInfo(cardBin);

        String countyCode = binInfo.getCurrencyNumber();
        List<CountryCurrency> currencys = currencyService.getCountryCurrencys(countyCode);

        String currencyQuery=null;//查询出来的卡本币

        logger.info("CountryCurrency: "+currencys);
        //删除掉本币币种相同的记录
        if(CollectionUtils.isNotEmpty(currencys) && currencys.size() != 1){
            for  ( int  i  =   0 ; i  <  currencys.size()  -   1 ; i ++ )  {
                for  ( int  j  =  currencys.size()  -   1 ; j  >  i; j -- )  {
                    if  (currencys.get(j).getCurrencyCode().equals(currencys.get(i).getCurrencyCode()))  {
                        currencys.remove(j);
                    }
                }
            }
        }
        if(currencys!=null&&!currencys.isEmpty()){
            if(currencys.size()!=1){
                //如果本币币种不止一个，调用卡司查汇接口去查询下
                Map<String,String> rateMap = this.queryRate(paymentInfo,paymentChannelItemDto, null);
                currencyQuery = rateMap.get("Currency");
            }else{
                CountryCurrency currency = currencys.get(0);
                currencyQuery = currency.getCurrencyCode();
            }
            String currencyCode = paymentInfo.getCurrencyCode();
            resultMap.put("Currency",currencyQuery);
            resultMap.put("prdtCode",DCCEnum.CUSTOM_STANDARD.getCode());
            resultMap.putAll(MapUtil.bean2map(paymentInfo));

            logger.info("currencyCode: "+currencyCode+" ,currencyQuery: "+currencyQuery);
            //交易币种与本币种相同走EDC
            if(currencyCode.equals(currencyQuery)){
                isDCCFlg = false;
            }else{
                //*******************************************************************
                //判断有没有开通自有强制ECC
                String orgCode = null;
                String orgMerchantCode = null;

                if(paymentChannelItemDto!=null){
                    orgCode = paymentChannelItemDto.getOrgCode();
                    orgMerchantCode = paymentChannelItemDto.getOrgMerchantCode();
                }
                //非标准DCC产品
                if(!DCCEnum.CUSTOM_STANDARD.getCode().equals(prdtCode)){
                    if(DCCEnum.CUSTOM_FORCED.getCode().equals(prdtCode)){
                        paymentInfo.setPrdtCode(DCCEnum.CUSTOM_FORCED.getCode());
                        ModelAndView view = this.getForcedDccModelAndView(language, orderTerminal,
                                null,null, remark,orgCode
                                ,currencyQuery,orgMerchantCode, paymentInfo);
                        view.addObject("prdtCode",DCCEnum.CUSTOM_FORCED.getCode());
                        return view;
                    }
                    //判断有没有开通自有隐藏DCC
                    if(DCCEnum.CUSTOM_HIDDEN.getCode().equals(prdtCode)){
                        paymentInfo.setPrdtCode(DCCEnum.CUSTOM_HIDDEN.getCode());
                        ModelAndView view = this.getForcedDccModelAndView(language, orderTerminal,
                                null, null, remark,orgCode
                                ,currencyQuery,orgMerchantCode, paymentInfo);
                        view.addObject("prdtCode",DCCEnum.CUSTOM_HIDDEN.getCode());
                        return view;
                    }
                    //判断有没有开通自有纯DCC
                    if(DCCEnum.PARTNER_DCC_PRDCT.getCode().equals(prdtCode)){
                        paymentInfo.setPrdtCode(DCCEnum.PARTNER_DCC_PRDCT.getCode());
                        ModelAndView view = this.getForcedDccModelAndView(language, orderTerminal,
                                null, null, remark,orgCode
                                ,currencyQuery,orgMerchantCode, paymentInfo);
                        view.addObject("prdtCode",DCCEnum.PARTNER_DCC_PRDCT.getCode());
                        return view;
                    }
                }

                //交易币种与卡本币不一致的时候
                Map<String,Object> param = new HashMap<String, Object>();
                param.put("partnerId",paymentInfo.getPartnerId());
                param.put("currencyCode",currencyQuery);

                PartnerDCCConfig dccConfig = dccService.getDccConfig(param);

                logger.info("dccConfig: "+dccConfig);

                if(dccConfig!=null){
                    String markupStr = dccConfig.getMarkUp();
                    BigDecimal markup = new BigDecimal(markupStr);

                    Map<String,String> paraMap = new HashMap<String,String>();
                    paraMap.put("currency", paymentInfo.getCurrencyCode());
                    paraMap.put("type","1");
                    paraMap.put("targetCurrency",currencyQuery);
                    paraMap.put("status","1");

                    //查询交易汇率
                    Map<String,Object> transRate = txncoreClientService
                            .getBaseRate(paraMap);
                    logger.info("transRate: "+transRate);
                    //add by mingmingzhang 添加null 判断
                    if(transRate!=null && transRate.get("exchangeRate")!=null && paymentInfo.getOrderAmount()!=null){
                        BigDecimal rate_ = new BigDecimal((String)transRate.get("exchangeRate"));
                        BigDecimal rate = rate_.add(rate_.multiply(markup.multiply(new BigDecimal("0.01"))));
                        BigDecimal amount = rate.multiply(new BigDecimal(paymentInfo.getOrderAmount()));
                        resultMap.put("Conversion_Rate",rate.toString());
                        //amountLocStr_ 交易币种金额
                        //amountForStr_ 本币金额
                        String orderAmount = paymentInfo.getOrderAmount();

                        String amountLocStr_ = new BigDecimal(orderAmount)
                                .divide(new BigDecimal("100")).toString();
                        BigDecimal amountFor = amount.divide(
                                new BigDecimal("100"));
                        String amountForStr_ = df.format(amountFor);
                        if (amountForStr_.startsWith(".")) {
                            amountForStr_ = "0" + amountForStr_;
                        }
                        return this.getDccModelAndView(language, orderTerminal, amountLocStr_,
                                amountForStr_, remark, resultMap);
                    }
                }else{//没有找到商户的DCC配置，就走EDC
                    isDCCFlg = false;
                }
                //********************************************************
            }
        } //return this.getModelAndView(language, orderTerminal, resultMap);
        //如果没有找到币种与国家的对应关系配置 则按照原有方式进行: 没有配置DCC则走EDC 如果有则走标准DCC

        return null;
    }

    /**
            * 获取强制DCC视图
    * @param language
    * @param orderTerminal
    * @param amountLocStr_
    * @param amountForStr_
    * @param remark
    * @param orgCode
    * @param currency
    * @param orgMechantCode
    * @param paymentInfo
    * @return
            */
    private ModelAndView getForcedDccModelAndView(String language,String orderTerminal,
                                                  String amountLocStr_,String amountForStr_,String remark,String orgCode,String currency,
                                                  String orgMechantCode,PaymentInfo paymentInfo){
        ModelAndView view = new ModelAndView("redirect:/channelPay.htm?method=dccPay");
        view.addObject("tradeOrderNo",paymentInfo.getTradeOrderNo());
        view.addObject("currencyCode", paymentInfo.getCurrencyCode());
        view.addObject("dccCurrencyCode",currency);
        view.addObject("orderAmount",paymentInfo.getOrderAmount());
        view.addObject("cardExpirationMonth",
                paymentInfo.getCardExpirationMonth());
        view.addObject("cardExpirationYear",
                paymentInfo.getCardExpirationYear());
        view.addObject("dccFlg","DCC");
        view.addObject("orderId",paymentInfo.getOrderId());
        view.addObject("orgMerchantCode",orgMechantCode);
        view.addObject("cardHolderNumber",paymentInfo.getCardHolderNumber());
        view.addObject("partnerId", paymentInfo.getPartnerId());
        view.addObject("orgCode",orgCode);
        view.addObject("amountFor",amountForStr_);
        view.addObject("dccAmount",amountForStr_);
        view.addObject("amountLoc",amountLocStr_);
        view.addObject("securityCode",paymentInfo.getSecurityCode());
        view.addObject("language",language);
        view.addObject("orderTerminal",orderTerminal);
        view.addObject("remark",remark);
        view.addObject("cardHolderFirstName",paymentInfo.getCardHolderFirstName());
        view.addObject("cardHolderLastName",paymentInfo.getCardHolderLastName());
        return view;
    }

    /**
     * DCC视图跳转选择
     * @param language
     * @param orderTerminal
     * @param amountLocStr_
     * @param amountForStr_
     * @param remark
     * @param resultMap
     * @return
     */
    private ModelAndView getDccModelAndView(String language,String orderTerminal,
                                            String amountLocStr_,String amountForStr_,String remark,
                                            Map<String,Object> resultMap){

        if(TerminalType.MOBILE.getCode().equals(orderTerminal)){
            if("en".equals(language)){
                return new ModelAndView(enMobileDccView, resultMap).addObject(
                        "amountLoc",amountLocStr_).addObject(
                        "amountFor", amountForStr_).addObject("orderTerminal",orderTerminal)
                        .addObject("language",language).addObject("remark",remark);
            }else if("cn".equals(language)){
                return new ModelAndView(cnMobileDccView, resultMap).addObject(
                        "amountLoc",amountLocStr_).addObject(
                        "amountFor", amountForStr_).addObject("orderTerminal",orderTerminal)
                        .addObject("language",language).addObject("remark",remark);
            }
        }else {
            if("en".equals(language)){
                return new ModelAndView(enDccView, resultMap).addObject(
                        "amountLoc",amountLocStr_).addObject(
                        "amountFor", amountForStr_).addObject("orderTerminal",orderTerminal)
                        .addObject("language",language).addObject("remark",remark);
            }else if("cn".equals(language)){
                return new ModelAndView(cnDccView, resultMap).addObject(
                        "amountLoc",amountLocStr_).addObject(
                        "amountFor", amountForStr_).addObject("orderTerminal",orderTerminal)
                        .addObject("language",language).addObject("remark",remark);
            }
        }

        return null;

    }

    private ModelAndView getSuccessView(String language,String orderTerminal, PaymentResponse responseFront){
        Map<String, Object> showPages = MapUtil.bean2map(responseFront);
        ModelAndView view = new ModelAndView(failView, showPages);
        if(TerminalType.MOBILE.getCode().equals(orderTerminal)){
            if("cn".equals(language)){
                view =  new ModelAndView(cnMobileSuccessView,showPages);
            }else if("en".equals(language)){
                view =  new ModelAndView(enMobileSuccessView,showPages);
            }
        }else if(TerminalType.PC.getCode().equals(orderTerminal)){
            if("cn".equals(language)){
                view =  new ModelAndView(cnSuccessView,showPages);
            }else if("en".equals(language)){
                view =  new ModelAndView(enSuccessView,showPages);
            }
        }else{
            if("cn".equals(language)){
                view =  new ModelAndView(cnSuccessView,showPages);
            }else if("en".equals(language)){
                view =  new ModelAndView(enSuccessView,showPages);
            }
        }
        return view;
    }

    private ModelAndView getBoletoHoldView(String language,String orderTerminal, PaymentResponse responseFront){
        Map<String, Object> showPages = MapUtil.bean2map(responseFront);
        ModelAndView view = new ModelAndView("cashier/local/success_hold", showPages);
        return view;
    }

    private ModelAndView getBoletoHoldView(String language,String orderTerminal, PaymentResponse responseFront, String boletoUrl){
        Map<String, Object> showPages = MapUtil.bean2map(responseFront);
        showPages.put("boletoUrl",boletoUrl);
        ModelAndView view = new ModelAndView("cashier/local/success_en", showPages);
        return view;
    }

    /**
     * 去卡司查询汇率
     * @param paymentInfo
     * @param paymentChannelItemDto
     * @param queryMap
     * @return
     */
    private Map<String,String> queryRate(PaymentInfo paymentInfo,
                                         PaymentChannelItemDto paymentChannelItemDto,Map<String, String> queryMap){

        if(queryMap==null)
            queryMap = new HashMap<String, String>();
        queryMap.put("tradeOrderNo", paymentInfo.getTradeOrderNo());
        queryMap.put("memberCode", paymentInfo.getPartnerId());
        queryMap.put("paymentType", PaymentTypeEnum.PAYMENT.getCode() + "");
        queryMap.put("memberType", MemberTypeEnum.MERCHANT.getCode() + "");
        queryMap.put("transType", TransTypeEnum.DCC.getCode());
        queryMap.put("currencyCode", paymentInfo.getCurrencyCode());
        queryMap.put("invoiceNo",
                new SimpleDateFormat("HHmmss").format(new Date()));
        queryMap.put(
                "orderAmount",
                new BigDecimal(paymentInfo.getOrderAmount()).multiply(
                        new BigDecimal("10")).toString());
        queryMap.put("cardHolderNumber", paymentInfo.getCardHolderNumber());
        queryMap.put("cardExpirationYear",
                paymentInfo.getCardExpirationYear());
        queryMap.put("cardExpirationMonth",
                paymentInfo.getCardExpirationMonth());
        queryMap.put("orgCode", paymentChannelItemDto.getOrgCode());
        queryMap.put("orgMerchantCode",
                paymentChannelItemDto.getOrgMerchantCode());
        Map<String, String> rateMap = txncoreClientService
                .queryOrgRateInfo(queryMap);

        logger.info("rateMap: "+rateMap);

        return rateMap;
    }
    private void notifyMerchant(final PaymentResult paymentResult, final String signMsg) {

        try {
            paymentResult.setSignMsg(signMsg);
            paymentResult.setDealId(paymentResult.getTradeOrderNo());
            paymentResult.setResultCode(paymentResult.getResponseCode());
            paymentResult.setResultMsg(paymentResult.getResponseDesc());
            HttpNotifyRequest notifyRequest = new HttpNotifyRequest();
            Map<String, String> notifyMap = MapUtil.bean2map(paymentResult);
            notifyRequest.setData(notifyMap);
            notifyRequest.setTemplateId(1001L);
            notifyRequest.setMerchantCode(paymentResult.getPartnerId());
            notifyRequest.setUrl(paymentResult.getNoticeUrl());
            jmsSender.send(notifyRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    private void notifyCardBin(final String bin) {
        try {
            CardBinNotifyRequest notifyRequest = new CardBinNotifyRequest();
            notifyRequest.setBin(bin);
            jmsSender.send(notifyRequest);
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
    public void setMc(MathContext mc) {
        this.mc = mc;
    }

    public void setDf(DecimalFormat df) {
        this.df = df;
    }

    public void setTxncoreClientService(TxncoreClientService txncoreClientService) {
        this.txncoreClientService = txncoreClientService;
    }

    public void setChannelClientService(ChannelClientService channelClientService) {
        this.channelClientService = channelClientService;
    }


    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    public void setCardBinInfoService(CardBinInfoService cardBinInfoService) {
        this.cardBinInfoService = cardBinInfoService;
    }

    public void setCurrencyService(CountryCurrencyService currencyService) {
        this.currencyService = currencyService;
    }

    public void setDccService(ConfigurationDCCService dccService) {
        this.dccService = dccService;
    }

    public static void setProductCodeDcc(String productCodeDcc) {
        PRODUCT_CODE_DCC = productCodeDcc;
    }

    public static void setProductCodeDccEdc(String productCodeDccEdc) {
        PRODUCT_CODE_DCC_EDC = productCodeDccEdc;
    }

    public static void setProductCode(String productCode) {
        PRODUCT_CODE = productCode;
    }

    public void setMemberProductService(MemberProductService memberProductService) {
        this.memberProductService = memberProductService;
    }

    public void setMemberBaseProductService(MemberBaseProductService memberBaseProductService) {
        this.memberBaseProductService = memberBaseProductService;
    }

    public void setJmsSender(JmsSender jmsSender) {
        this.jmsSender = jmsSender;
    }
    public void setEnFailView(String enFailView) {
        this.enFailView = enFailView;
    }

    public void setCnFailView(String cnFailView) {
        this.cnFailView = cnFailView;
    }

    public void setEnMobileFailView(String enMobileFailView) {
        this.enMobileFailView = enMobileFailView;
    }

    public void setCnMobileFailView(String cnMobileFailView) {
        this.cnMobileFailView = cnMobileFailView;
    }

    public void setSuccessView(String successView) {
        this.successView = successView;
    }

    public void setEnSuccessView(String enSuccessView) {
        this.enSuccessView = enSuccessView;
    }

    public void setCnSuccessView(String cnSuccessView) {
        this.cnSuccessView = cnSuccessView;
    }

    public void setPcEnSuccessView(String pcEnSuccessView) {
        this.pcEnSuccessView = pcEnSuccessView;
    }

    public void setPcCnSuccessView(String pcCnSuccessView) {
        this.pcCnSuccessView = pcCnSuccessView;
    }

    public void setDccView(String dccView) {
        this.dccView = dccView;
    }

    public void setMobileDccView(String mobileDccView) {
        this.mobileDccView = mobileDccView;
    }

    public void setEnDccView(String enDccView) {
        this.enDccView = enDccView;
    }

    public void setCnDccView(String cnDccView) {
        this.cnDccView = cnDccView;
    }

    public void setPcCnFailView(String pcCnFailView) {
        this.pcCnFailView = pcCnFailView;
    }

    public void setPcEnFailView(String pcEnFailView) {
        this.pcEnFailView = pcEnFailView;
    }

    public void setEnMobileDccView(String enMobileDccView) {
        this.enMobileDccView = enMobileDccView;
    }

    public void setCnMobileDccView(String cnMobileDccView) {
        this.cnMobileDccView = cnMobileDccView;
    }

    public void setCnMobileSuccessView(String cnMobileSuccessView) {
        this.cnMobileSuccessView = cnMobileSuccessView;
    }

    public void setEnMobileSuccessView(String enMobileSuccessView) {
        this.enMobileSuccessView = enMobileSuccessView;
    }

    public void setFailView(String failView) {
        this.failView = failView;
    }

    public void setPayGateService(BuilderPayGateService payGateService) {
        this.payGateService = payGateService;
    }
}
