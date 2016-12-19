package com.pay.channel.handler;

import com.pay.channel.dao.ParnterChannelCountryDAO;
import com.pay.channel.dao.PaymentChannelItemDAO;
import com.pay.channel.model.ParnterChannelCountry;
import com.pay.channel.model.PaymentChannelItem;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by peiyu.yang on 2016/7/16.
 */
public class PccQueryHandler implements EventHandler {
    public final Log logger = LogFactory.getLog(PccQueryHandler.class);
    private ParnterChannelCountryDAO  parnterChannelCountryDAO;
    private PaymentChannelItemDAO channelItemDAO;

    public void setParnterChannelCountryDAO(ParnterChannelCountryDAO parnterChannelCountryDAO) {
        this.parnterChannelCountryDAO = parnterChannelCountryDAO;
    }

    @SuppressWarnings("unchecked")
	@Override
    public String handle(String dataMsg) throws HessianInvokeException {
        Map<String, Object> paraMap = null;
        Map<String,Object> resultMap = new HashMap<String, Object>();

        try {
            paraMap = JSonUtil.toObject(dataMsg,
                    new HashMap<String, String>().getClass());
            String memberCode = (String) paraMap.get("memberCode");
            
            ParnterChannelCountry cc=new ParnterChannelCountry();
            cc.setMemCode(Long.valueOf(memberCode));
            
            List<ParnterChannelCountry> channels = parnterChannelCountryDAO.queryListpcc(cc);
            
            /*List<PaymentChannelItem> itemList = null; 
            
            if(channels!=null&&!channels.isEmpty()){
            	itemList = new ArrayList<PaymentChannelItem>();
            	
            	for(ParnterChannelCountry pcc:channels){
            		String orgCode = pcc.getOrgCode();
            		Map<String,Object> params = new HashMap<String, Object>();
            		params.put("memberCode",memberCode);
            		params.put("orgCode",orgCode);
            		params.put("cardOrg",cardOrg);
            		
            		List<PaymentChannelItem> list = channelItemDAO.queryPaymentChannelItem(params);
            		if(list==null||list.isEmpty()){
            			list = channelItemDAO.
            					     queryPaymentChannelItemConfig(params);
            		}
            		
            		itemList.addAll(list);
            	}
            }*/
            resultMap.put("pccList",channels);
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

	public void setChannelItemDAO(PaymentChannelItemDAO channelItemDAO) {
		this.channelItemDAO = channelItemDAO;
	}
}
