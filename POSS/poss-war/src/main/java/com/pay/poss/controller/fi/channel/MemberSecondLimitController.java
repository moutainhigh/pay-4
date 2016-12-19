package com.pay.poss.controller.fi.channel;

import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.service.MemberService;
import com.pay.fundout.withdraw.common.util.WithdrawJSON;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.inf.enums.ResponseCodeEnum;
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
public class MemberSecondLimitController extends MultiActionController {
    private final Log logger = LogFactory.getLog(getClass());
    private ChannelClientService channelClientService;
    private MemberService memberService;

    public void setChannelClientService(ChannelClientService channelClientService) {
        this.channelClientService = channelClientService;
    }

    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    public ModelAndView index(HttpServletRequest request, HttpServletResponse response) {
        PaymentChannelItemDto paymentChannelItem = new PaymentChannelItemDto();
        List result = channelClientService.queryChannelItem(paymentChannelItem);
        return new ModelAndView("channel/membersecondlimit/index").addObject("channelItems", result);
    }

    public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String limitTimes=request.getParameter("limitTimes");
        String limitAmount=request.getParameter("limitAmount");
        String limitDay=request.getParameter("limitDay");
        String switchFlag=request.getParameter("switchFlag");
        String partnerId=request.getParameter("partnerId");
        String cardOrg = request.getParameter("cardOrg");
        String id = request.getParameter("id");
        String operator = SessionUserHolderUtil.getLoginId();
        Map<String,String> returnMap = new HashMap<String, String>();
        boolean failure = false;
        String errorMsg = null;
        if(StringUtils.isBlank(partnerId) || !StringUtils.isNumeric(partnerId)){
            errorMsg = "会员号只能是数字";
            failure = true;
        }else if(!"0".equals(partnerId)){
            MemberDto member = memberService.queryMemberByMemberCode(Long
                    .valueOf(partnerId));
            if(member == null){
                errorMsg = "会员号不存在";
                failure = true;
            }
        }
        Map resultMap = null;
        if(!failure){
            if(StringUtils.isNotBlank(id)){
                resultMap=channelClientService.memberSecondLimitUpdateHandler(limitTimes,id, limitAmount, limitDay, switchFlag,operator);
            }else{
                resultMap=channelClientService.memberSecondLimitInsertHandler(partnerId,cardOrg, limitTimes,
                        limitAmount, limitDay, switchFlag, operator);
            }
            if(!ResponseCodeEnum.SUCCESS.getCode().equals(resultMap.get("responseCode"))){
                failure = true;
                errorMsg = (String)resultMap.get("responseDesc");
            }
        }
        WithdrawJSON json = WithdrawJSON.JsonBuilder();
        json.setSuccess(!failure);
        json.setReason(errorMsg);
        SpringControllerUtils.renderText(response, json.toString());
        return null;
    }
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response)throws Exception{
        String id=request.getParameter("id");
        WithdrawJSON json = WithdrawJSON.JsonBuilder();
        json.setSuccess(channelClientService.memberSecondLimitDelHandler(id));
        json.setReason("未知错误");
        SpringControllerUtils.renderText(response, json.toString());
        return null;
    }

    public ModelAndView list(HttpServletRequest request,HttpServletResponse response){
        String partnerId=request.getParameter("partnerId");
        Page<Map> page = PageUtils.getPage(request);
        Map paraMap=new HashMap();

        paraMap.put("page", page);
        paraMap.put("partnerId", partnerId);
        Map resultMap=channelClientService.memberSecondLimitQueryHandler(paraMap);
        List<Map> result= (List<Map>) resultMap.get("result");
        Map pageMap = (Map) resultMap.get("page");
        page = MapUtil.map2Object(Page.class, pageMap);
        return new ModelAndView("channel/membersecondlimit/resultList").addObject("result", result).addObject("page",page);
    }
}
