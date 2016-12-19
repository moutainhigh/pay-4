package com.pay.channel.handler;

import com.pay.channel.dao.PaymentChannelItemDAO;
import com.pay.channel.model.PaymentChannelItem;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cuber.huang on 2016/7/24.
 */
public class ChannelQueryDirectHandler implements EventHandler {
    public final Log logger = LogFactory.getLog(getClass());
    private PaymentChannelItemDAO paymentChannelItemDAO;

    public void setPaymentChannelItemDAO(PaymentChannelItemDAO paymentChannelItemDAO) {
        this.paymentChannelItemDAO = paymentChannelItemDAO;
    }

    @Override
    public String handle(String dataMsg) throws HessianInvokeException {
        logger.info("dataMsg : "+dataMsg);
        Map resultMap = new HashMap();
        Map<String, String> paraMap = JSonUtil.toObject(dataMsg,
                new HashMap<String, String>().getClass());
        String paymentType = paraMap.get("paymentType");
        String memberCode = paraMap.get("memberCode");
        String orgCode = paraMap.get("orgCode");
        //获取商户配置的本地化支付的渠道
        List<PaymentChannelItem> configItems =
                paymentChannelItemDAO.queryPaymentChannelItemConfig(paymentType, memberCode,orgCode);
        if (null == configItems || configItems.isEmpty()) {
            logger.info("请配置"+memberCode+"的本地化支付渠道。。。");
        }
        resultMap.put("paymentChannelItems", configItems);
        resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
        resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
        return JSonUtil.toJSonString(resultMap);
    }
}
