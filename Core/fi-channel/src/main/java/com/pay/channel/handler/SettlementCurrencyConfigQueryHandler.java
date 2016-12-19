package com.pay.channel.handler;

import com.pay.channel.dao.SettlementCurrencyConfigDAO;
import com.pay.channel.model.SettlementCurrencyConfig;
import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;

import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cuber.huang on 2016/7/16.
 */
public class SettlementCurrencyConfigQueryHandler implements EventHandler {
    public final Log logger = LogFactory.getLog(SettlementCurrencyConfigQueryHandler.class);
    private SettlementCurrencyConfigDAO settlementCurrencyConfigDAO;

    @Override
    public String handle(String dataMsg) throws HessianInvokeException {
        Map<String, Object> paraMap = null;
        Map resultMap = new HashMap();

        try {
            paraMap = JSonUtil.toObject(dataMsg,
                    new HashMap<String, String>().getClass());
            
            SettlementCurrencyConfig scc=new SettlementCurrencyConfig();
            BeanUtils.copyProperties(scc, paraMap);
            if(scc.getMemberCode() == 0){
            	scc.setMemberCode(null);
            }
            List<SettlementCurrencyConfig> channels = new ArrayList<SettlementCurrencyConfig>();
            if(paraMap.get("page") != null){
            	Map pageMap = (Map) paraMap.get("page");
                Page page = MapUtil.map2Object(Page.class, pageMap);
                channels =settlementCurrencyConfigDAO.queryListScc(scc, page);
                resultMap.put("page", page);
            }else{
            	channels =settlementCurrencyConfigDAO.queryListScc(scc);
            }
            
            resultMap.put("result", channels);
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
   
    public void setSettlementCurrencyConfigDAO(
			SettlementCurrencyConfigDAO settlementCurrencyConfigDAO) {
		this.settlementCurrencyConfigDAO = settlementCurrencyConfigDAO;
	}
}
