package com.pay.channel.handler.autofit;

import com.pay.channel.dao.MemberSecondLimitDAO;
import com.pay.channel.model.MemberSecondLimit;
import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
import com.pay.inf.excepiton.HessianInvokeException;
import com.pay.inf.handler.EventHandler;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;
import com.pay.util.StringUtil;
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
public class MemberSecondLimitQueryHandler implements EventHandler {
    public final Log logger = LogFactory.getLog(getClass());
    private MemberSecondLimitDAO memberSecondLimitDAO;

    public void setMemberSecondLimitDAO(MemberSecondLimitDAO memberSecondLimitDAO) {
        this.memberSecondLimitDAO = memberSecondLimitDAO;
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
            MemberSecondLimit cc=new MemberSecondLimit();
            String partnerId = (String)paraMap.get("partnerId");
            if(partnerId != null){partnerId = partnerId.trim();}
            boolean nullSet = false;
            if(!StringUtil.isEmpty(partnerId) && StringUtil.isNumber(partnerId)){
                cc.setPartnerId(new BigDecimal(partnerId));
            }else if(partnerId != null && partnerId.length() > 0){
                nullSet = true;
            }
            List<MemberSecondLimit> memberSecondLimits = memberSecondLimitDAO.findPageResult(cc,page);
            List<Map<String,String>> showList = new ArrayList<Map<String, String>>();
            if(memberSecondLimits != null && memberSecondLimits.size() > 0){
                for (MemberSecondLimit memberSecondLimit:memberSecondLimits
                     ) {
                    Map<String,String> showOne = new HashMap<String, String>();
                    memberSecondLimit.setLimitAmount(memberSecondLimit.getLimitAmount().divide(new BigDecimal(1000)).setScale(3,BigDecimal.ROUND_HALF_UP));
                    showOne.put("id",String.valueOf(memberSecondLimit.getId()));
                    showOne.put("cardOrg",memberSecondLimit.getCardOrg());
                    showOne.put("limitTimes",String.valueOf(memberSecondLimit.getLimitTimes()));
                    showOne.put("limitAmount",String.valueOf(memberSecondLimit.getLimitAmount()));
                    showOne.put("limitDay",String.valueOf(memberSecondLimit.getLimitDay()));
                    showOne.put("switchFlag",memberSecondLimit.getSwitchFlag());
                    showOne.put("partnerId",String.valueOf(memberSecondLimit.getPartnerId()));
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
