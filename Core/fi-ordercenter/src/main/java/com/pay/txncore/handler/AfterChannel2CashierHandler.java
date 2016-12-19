package com.pay.txncore.handler;

import com.pay.fi.service.TradeDataSingnatureService;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.txncore.dao.AfterChannel2CashierDAO;
import com.pay.txncore.dto.AfterChannel2CashierDTO;
import com.pay.txncore.dto.PaymentResultForSign;
import com.pay.txncore.model.PartnerConfig;
import com.pay.txncore.service.PartnerConfigService;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cuber.huang on 2016/7/27.
 */
public class AfterChannel2CashierHandler implements EventHandler {
    public final Log logger = LogFactory.getLog(AfterChannel2CashierHandler.class);
    private AfterChannel2CashierDAO afterChannel2CashierDAO;

    private PartnerConfigService partnerConfigService;

    private TradeDataSingnatureService tradeDataSingnatureService;

    public void setTradeDataSingnatureService(TradeDataSingnatureService tradeDataSingnatureService) {
        this.tradeDataSingnatureService = tradeDataSingnatureService;
    }

    public void setPartnerConfigService(PartnerConfigService partnerConfigService) {
        this.partnerConfigService = partnerConfigService;
    }

    public void setAfterChannel2CashierDAO(AfterChannel2CashierDAO afterChannel2CashierDAO) {
        this.afterChannel2CashierDAO = afterChannel2CashierDAO;
    }
    @Override
    public String handle(String dataMsg) throws HessianInvokeException {
        Map<String, Object> paraMap = JSonUtil.toObject(dataMsg,
                new HashMap<String, Object>().getClass());
        Map<String,Object> afterChannel2CashierMap = new HashMap<String,Object>();
        AfterChannel2CashierDTO dto = MapUtil.map2Object(AfterChannel2CashierDTO.class,paraMap);
        AfterChannel2CashierDTO returnDto =  null;

        List<AfterChannel2CashierDTO> dtos = afterChannel2CashierDAO.queryChannel2CashierDTO(dto);
        if(dtos != null && dtos.size() > 0)
            returnDto = dtos.get(0);
        try{
            if(returnDto != null){
                afterChannel2CashierMap = MapUtil.bean2map(returnDto);
                PartnerConfig partnerConfig = partnerConfigService.findConfig(
                        Long.valueOf(returnDto.getPartnerId()), "code1");
                afterChannel2CashierMap.put("resultCode",paraMap.get("resultCode"));
                afterChannel2CashierMap.put("resultMsg",paraMap.get("resultMsg"));
                String merchantKey = partnerConfig.getValue();
                PaymentResultForSign paymentResult = MapUtil.map2Object(PaymentResultForSign.class, afterChannel2CashierMap);
                paymentResult.setCompleteTime(new SimpleDateFormat("yyyyMMddHHmmss").format(new Date()));//结束时间拿不到
                paymentResult.setPartnerId(null);
                String signMsg = tradeDataSingnatureService.genSignMsgBySignType(
                        paymentResult.generateSign(), paymentResult.getSignType(),
                        paymentResult.getCharset(), merchantKey);
                afterChannel2CashierMap.put("signMsg",signMsg);
                afterChannel2CashierMap.put("responseCode","0000");
                afterChannel2CashierMap.put("completeTime",paymentResult.getCompleteTime());
                afterChannel2CashierMap.put("responseMsg","The transaction has completed");
            }else{
                afterChannel2CashierMap.put("responseCode","3099");
                afterChannel2CashierMap.put("responseMsg","can't find record");
            }
        }catch (Exception e){
            e.printStackTrace();
            afterChannel2CashierMap.put("responseCode","3099");
            afterChannel2CashierMap.put("responseMsg","other err");
        }
        return JSonUtil.toJSonString(afterChannel2CashierMap);
    }
}
