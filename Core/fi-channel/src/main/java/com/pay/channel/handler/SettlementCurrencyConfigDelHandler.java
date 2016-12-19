package com.pay.channel.handler;

import com.pay.channel.model.SettlementCurrencyConfig;
import com.pay.channel.service.SettlementCurrencyConfigService;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by cuber.huang on 2016/7/16.
 */
public class SettlementCurrencyConfigDelHandler implements EventHandler {
    public final Log logger = LogFactory.getLog(SettlementCurrencyConfigDelHandler.class);
    private SettlementCurrencyConfigService settlementCurrencyConfigService;

    @Override
    public String handle(String dataMsg) throws HessianInvokeException {
        Map<String, Object> paraMap = null;
        Map resultMap = new HashMap();

        try {
            paraMap = JSonUtil.toObject(dataMsg,
                    new HashMap<String, String>().getClass());
            SettlementCurrencyConfig scc=new SettlementCurrencyConfig();
            BeanUtils.copyProperties(scc, paraMap);
            settlementCurrencyConfigService.deleteSettlementCurrencyConfig(scc);
            resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
            resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
        } catch (Exception e) {
            logger.error("error:", e);
            resultMap.put("responseCode",
                    ResponseCodeEnum.UNDEFINED_ERROR.getCode());
            resultMap.put("responseDesc",
                    ResponseCodeEnum.UNDEFINED_ERROR.getDesc());
        }

        return JSonUtil.toJSonString(resultMap);
    }
    
    public void setSettlementCurrencyConfigService(
			SettlementCurrencyConfigService settlementCurrencyConfigService) {
		this.settlementCurrencyConfigService = settlementCurrencyConfigService;
	}
}
