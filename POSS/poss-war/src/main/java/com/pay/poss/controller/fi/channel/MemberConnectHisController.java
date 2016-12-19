package com.pay.poss.controller.fi.channel;

import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.service.MemberService;
import com.pay.fundout.withdraw.common.util.WithdrawJSON;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.client.ChannelClientService;
import com.pay.poss.dto.PaymentChannelItemDto;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.MapUtil;
import com.pay.util.SpringControllerUtils;
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by eva on 2016/8/17.
 */
public class MemberConnectHisController extends MultiActionController {
    private final Log logger = LogFactory.getLog(getClass());
    private ChannelClientService channelClientService;

    public void setChannelClientService(ChannelClientService channelClientService) {
        this.channelClientService = channelClientService;
    }
    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        PaymentChannelItemDto paymentChannelItem = new PaymentChannelItemDto();
        List result = channelClientService.queryChannelItem(paymentChannelItem);
        return new ModelAndView("channel/memberconnecthis/index").addObject("channelItems", result);
    }

    public ModelAndView list(HttpServletRequest request,HttpServletResponse response){
        String partnerId=request.getParameter("partnerId");
        String paymentChannelItemId=request.getParameter("paymentChannelItemId");
        String orgMerchantCode=request.getParameter("orgMerchantCode");
        Page<Map> page = PageUtils.getPage(request);
        if(partnerId != null) {partnerId.trim();partnerId.replaceAll(" ","");}
        Map paraMap=new HashMap();
        paraMap.put("page", page);
        paraMap.put("partnerId", partnerId);
        paraMap.put("paymentChannelItemId", paymentChannelItemId);
        paraMap.put("orgMerchantCode", orgMerchantCode);
        Map resultMap=channelClientService.memberConnectHisQueryHandler(paraMap);
        List<Map> result= (List<Map>) resultMap.get("result");
        Map pageMap = (Map) resultMap.get("page");
        page = MapUtil.map2Object(Page.class, pageMap);
        List channelItems = channelClientService
                .queryChannelItem(new PaymentChannelItemDto());
        return new ModelAndView("channel/memberconnecthis/resultList").addObject("result", result).addObject("page",page).addObject("channelItems", channelItems);
    }
}
