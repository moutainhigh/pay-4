package com.pay.txncore.handler.token;

import com.pay.fi.exception.BusinessException;
import com.pay.fi.exception.ExceptionCodeEnum;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dto.PaymentInfo;
import com.pay.txncore.dto.PaymentResult;
import com.pay.txncore.model.TokenPayInfo;
import com.pay.txncore.service.ApiPayService;
import com.pay.txncore.service.TokenPayInfoService;
import com.pay.txncore.service.preauth.PreAuth2Service;
import com.pay.util.DESUtil;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cuber on 2016/9/21.
 */
public class TokenPreAuthHandler implements EventHandler {
    public final Log logger = LogFactory.getLog(getClass());
    private TokenPayInfoService tokenPayInfoService;
    private String securityKey;
    private PreAuth2Service preAuth2Service;
    @Override
    public String handle(String dataMsg) throws HessianInvokeException {
        Map<String, String> paraMap = null;
        Map resultMap = new HashMap();
        try {
            paraMap = JSonUtil.toObject(dataMsg,
                    new HashMap<String, String>().getClass());

            PaymentInfo paymentInfo = MapUtil.map2Object(PaymentInfo.class,
                    paraMap);
            Map<String,Object> params = new HashMap<String, Object>();
            params.put("token",paymentInfo.getToken());
            params.put("registerUserId",paymentInfo.getRegisterUserId());
            params.put("partnerId",paymentInfo.getPartnerId());
            params.put("status","1");
            List<TokenPayInfo> list = tokenPayInfoService.getTokenPayInfos(params);
            TokenPayInfo payInfo =  null;
            if(list!=null&&!list.isEmpty()){
                payInfo = list.get(0);
                payInfo.setCardHolderNumber(DESUtil.decrypt(payInfo.getCardHolderNumber(),securityKey));
                payInfo.setCardExpirationMonth(DESUtil.decrypt(payInfo.getCardExpirationMonth(),securityKey));
                payInfo.setCardExpirationYear(DESUtil.decrypt(payInfo.getCardExpirationYear(),securityKey));
                payInfo.setSecurityCode(DESUtil.decrypt(payInfo.getSecurityCode(),securityKey));
                paymentInfo.setToken(payInfo.getToken());
                paymentInfo.setCardHolderNumber(payInfo.getCardHolderNumber());
                paymentInfo.setCardHolderFirstName(payInfo.getCardHolderFirstName());
                paymentInfo.setCardHolderLastName(payInfo.getCardHolderLastName());
                paymentInfo.setCardExpirationMonth(payInfo.getCardExpirationMonth());
                paymentInfo.setCardExpirationYear(payInfo.getCardExpirationYear());
                paymentInfo.setSecurityCode(payInfo.getSecurityCode());
                paymentInfo.setCardHolderEmail(payInfo.getCardHolderEmail());
                paymentInfo.setCardHolderPhoneNumber(payInfo.getCardHolderPhoneNumber());
                PaymentResult paymentResult = preAuth2Service.preAuthApply(paymentInfo);
                paymentResult.setCardHolderNumber(paymentInfo.getCardHolderNumber());
                resultMap.putAll(MapUtil.bean2map(paymentResult));
                resultMap.put("orgCode", paymentResult.getOrgCode()) ;
                resultMap.put("channelRespCode", paymentResult.getChannelRespCode()) ;
                resultMap.put("responseCode", paymentResult.getResponseCode());
                resultMap.put("responseDesc", paymentResult.getResponseDesc());
            }else{
                resultMap.put("responseCode", "0452");
                resultMap.put("responseDesc", "Invalid token: 无效令牌");
            }


        } catch (BusinessException e) {
            logger.error("CrosspayCashierPayHandler error:", e);
            ExceptionCodeEnum error = e.getCode();
            resultMap.put("responseCode", error.getCode());
            resultMap.put("responseDesc", error.getDescription());
        } catch (Exception e) {
            logger.error("api pay error:", e);
            resultMap.put("responseCode",
                    ResponseCodeEnum.UNDEFINED_ERROR.getCode());
            resultMap.put("responseDesc",
                    ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
        }
        return JSonUtil.toJSonString(resultMap);
    }

    public void setTokenPayInfoService(TokenPayInfoService tokenPayInfoService) {
        this.tokenPayInfoService = tokenPayInfoService;
    }

    public void setSecurityKey(String securityKey) {
        this.securityKey = securityKey;
    }

    public void setPreAuth2Service(PreAuth2Service preAuth2Service) {
        this.preAuth2Service = preAuth2Service;
    }
}
