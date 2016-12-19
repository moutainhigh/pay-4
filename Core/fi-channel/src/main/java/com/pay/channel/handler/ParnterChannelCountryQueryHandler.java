package com.pay.channel.handler;

import com.pay.channel.dao.ParnterChannelCountryDAO;

import com.pay.channel.model.ParnterChannelCountry;
import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cuber.huang on 2016/7/16.
 */
public class ParnterChannelCountryQueryHandler implements EventHandler {
    public final Log logger = LogFactory.getLog(ParnterChannelCountryQueryHandler.class);
    private ParnterChannelCountryDAO  parnterChannelCountryDAO;

    public void setParnterChannelCountryDAO(ParnterChannelCountryDAO parnterChannelCountryDAO) {
        this.parnterChannelCountryDAO = parnterChannelCountryDAO;
    }

    @Override
    public String handle(String dataMsg) throws HessianInvokeException {
        Map<String, Object> paraMap = null;
        Map resultMap = new HashMap();

        try {
            paraMap = JSonUtil.toObject(dataMsg,
                    new HashMap<String, String>().getClass());
            Map pageMap = (Map) paraMap.get("page");
            Page page = MapUtil.map2Object(Page.class, pageMap);
            ParnterChannelCountry cc=new ParnterChannelCountry();
            BeanUtils.copyProperties(cc, paraMap);
            List<ParnterChannelCountry> channels = parnterChannelCountryDAO.queryListpcc(cc,page);
            resultMap.put("result", channels);
            resultMap.put("page", page);
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
}
