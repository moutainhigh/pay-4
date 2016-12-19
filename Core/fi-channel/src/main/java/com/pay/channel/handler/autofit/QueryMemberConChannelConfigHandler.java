package com.pay.channel.handler.autofit;

import com.pay.channel.dao.MemberCurrentConnectDAO;
import com.pay.channel.dto.MemberCurrentConnectDTO;
import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;
import org.apache.commons.beanutils.BeanUtils;
import org.apache.commons.codec.binary.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by eva on 2016/8/17.
 */
public class QueryMemberConChannelConfigHandler implements EventHandler {
    public final Log logger = LogFactory.getLog(getClass());

    private MemberCurrentConnectDAO memberCurrentConnectDAO;

    public void setMemberCurrentConnectDAO(MemberCurrentConnectDAO memberCurrentConnectDAO) {
        this.memberCurrentConnectDAO = memberCurrentConnectDAO;
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
            MemberCurrentConnectDTO searchDto = new MemberCurrentConnectDTO();
            String partnerId = (String)paraMap.get("partnerId");
            String channelConfigId = (String)paraMap.get("channelConfigId");
            String paymentChannelItemId = (String)paraMap.get("paymentChannelItemId");
            if(partnerId != null){partnerId = partnerId.trim();}
            boolean nullSet = false;
            if(!StringUtil.isEmpty(partnerId) && StringUtil.isNumber(partnerId)){
                searchDto.setPartnerId(new BigDecimal(partnerId));
            }else if(partnerId != null && partnerId.length() > 0){
                nullSet = true;
            }
            if(!StringUtil.isEmpty(channelConfigId) && StringUtil.isNumber(channelConfigId)){
                searchDto.setChannelConfigId(new BigDecimal(channelConfigId));
            }
            if(!StringUtil.isEmpty(paymentChannelItemId) && StringUtil.isNumber(paymentChannelItemId)){
                searchDto.setPaymentChannelItemId(new BigDecimal(paymentChannelItemId));
            }
            searchDto.setOrgMerchantCode((String)paraMap.get("orgMerchantCode"));
            List<MemberCurrentConnectDTO> memberCurrentConnectDTOs = memberCurrentConnectDAO.findPageResult(searchDto,page);
            List<Map<String,Object>> showList = new ArrayList<Map<String, Object>>();
            if(memberCurrentConnectDTOs != null && memberCurrentConnectDTOs.size() > 0) {
                for (MemberCurrentConnectDTO memberSecondLimit : memberCurrentConnectDTOs
                        ) {
                    Map<String,Object> showOne = new HashMap<String, Object>();
                    memberSecondLimit.setCountAmount(memberSecondLimit.getCountAmount().divide(new BigDecimal(1000)).setScale(3,BigDecimal.ROUND_HALF_UP));
                    showOne.put("partnerId",String.valueOf(memberSecondLimit.getPartnerId()));
                    showOne.put("paymentChannelName",memberSecondLimit.getPaymentChannelName());
                    showOne.put("paymentChannelItemId",memberSecondLimit.getPaymentChannelItemId());
                    showOne.put("orgMerchantCode",memberSecondLimit.getOrgMerchantCode());
                    showOne.put("cardOrg",memberSecondLimit.getCardOrg());
                    showOne.put("connectTime",memberSecondLimit.getConnectTime());
                    showOne.put("days",memberSecondLimit.getDays());
                    showOne.put("hasWarning",memberSecondLimit.getHasWarning());
                    showOne.put("countTimes",String.valueOf(memberSecondLimit.getCountTimes()));
                    showOne.put("countAmount",String.valueOf(memberSecondLimit.getCountAmount()));
                    showOne.put("id",String.valueOf(memberSecondLimit.getId()));
                    showOne.put("manual",memberSecondLimit.getManual());
                    showList.add(showOne);
                }
            }

            resultMap.put("result", showList);
            resultMap.put("page", page);
            if(nullSet){
                resultMap.put("result", null);
                resultMap.put("page", new Page());
            }
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
