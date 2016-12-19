package com.pay.channel.handler;

import com.pay.channel.model.ParnterChannelCountry;
import com.pay.channel.service.ParnterChannelCountryService;
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
public class ParnterChannelCountryUpdateHandler implements EventHandler {
    public final Log logger = LogFactory.getLog(ParnterChannelCountryUpdateHandler.class);
    private ParnterChannelCountryService parnterChannelCountryService;

    public void setParnterChannelCountryService(ParnterChannelCountryService parnterChannelCountryService) {
        this.parnterChannelCountryService = parnterChannelCountryService;
    }


    @Override
    public String handle(String dataMsg) throws HessianInvokeException {
        Map<String, Object> paraMap = null;
        Map resultMap = new HashMap();

        try {
            paraMap = JSonUtil.toObject(dataMsg,
                    new HashMap<String, String>().getClass());
            ParnterChannelCountry cc=new ParnterChannelCountry();
            BeanUtils.copyProperties(cc, paraMap);
            parnterChannelCountryService.updateConfig(cc);
            resultMap.put("responseCode", ResponseCodeEnum.SUCCESS.getCode());
            resultMap.put("responseDesc", ResponseCodeEnum.SUCCESS.getDesc());
        } catch (Exception e) {
            logger.error("error:", e);
            resultMap.put("responseCode",
                    ResponseCodeEnum.UNDEFINED_ERROR.getCode());
            resultMap.put("responseDesc",
                    e.getMessage());
        }

        return JSonUtil.toJSonString(resultMap);
    }
}
