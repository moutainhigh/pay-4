package com.pay.poss.controller.fi.channel;

import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.service.MemberService;
import com.pay.fi.helper.ChannelItemOrgCodeEnum;
import com.pay.fundout.withdraw.common.util.WithdrawJSON;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.client.ChannelClientService;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.JSonUtil;
import com.pay.util.MapUtil;
import com.pay.util.SpringControllerUtils;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by cuber.huang on 2016/7/16.
 */
public class PartnerChannelCountryController extends MultiActionController {

    private ChannelClientService channelClientService;

    private MemberService memberService;

    private String index;

    public void setIndex(String index) {
        this.index = index;
    }

    public void setList(String list) {
        this.list = list;
    }

    private String list;

    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }

    public void setChannelClientService(ChannelClientService channelClientService) {
        this.channelClientService = channelClientService;
    }

    public ModelAndView index(HttpServletRequest request, HttpServletResponse response){
        ModelAndView view = new ModelAndView(index);
        ChannelItemOrgCodeEnum[] channelItems = ChannelItemOrgCodeEnum.values();
        view.addObject("paymentChannels",channelItems);
        return   view;
    }

    public ModelAndView edit(HttpServletRequest request, HttpServletResponse response) throws Exception{
        String memCode=request.getParameter("memCode");
        String orgCode=request.getParameter("orgCode");
        String countryCode=request.getParameter("countryCode");
        String id = request.getParameter("editId");
        Map<String,Object>  map = new HashMap<String, Object>();
        Map<String,String> returnMap = new HashMap<String, String>();
        boolean failure = false;
        if(StringUtils.isBlank(memCode) || !StringUtils.isNumeric(memCode)){
            returnMap.put("errMsg","会员号只能是数字");
            failure = true;
        }else{
            MemberDto member = memberService.queryMemberByMemberCode(Long
                    .valueOf(memCode));
            if(member == null){
                returnMap.put("errMsg","会员号不存在");
                failure = true;
            }
        }
        map.put("memCode",memCode) ;
        map.put("orgCode",orgCode) ;
        map.put("countryCode",countryCode) ;
        String operator = SessionUserHolderUtil.getLoginId();
        map.put("operator",operator) ;
        Map resultMap =  null;
        if( !failure ){
            if(StringUtils.isNotBlank(id)){
                map.put("id",Long.parseLong(id)) ;
                resultMap=channelClientService.updateMemVorgVcountry(map);
            }else{
                resultMap=channelClientService.addMemVorgVcountry(map);
            }
            if(resultMap.get("responseCode").equals("0000")){
                returnMap.put("errCode","0000");
            }else{
                returnMap.put("errCode","9999");
                returnMap.put("errMsg",(String)resultMap.get("responseDesc"));
            }
        }
        if(failure){
            returnMap.put("errCode","9999");
        }
        WithdrawJSON json = WithdrawJSON.JsonBuilder();
        json.setSuccess("0000".equals(returnMap.get("errCode")));
        json.setReason(returnMap.get("errMsg"));
        SpringControllerUtils.renderText(response, json.toString());
        return null;
    }
    public ModelAndView delete(HttpServletRequest request, HttpServletResponse response)throws Exception{
        String id=request.getParameter("id");
        Map paraMap=new HashMap();
        paraMap.put("id", id);
        Map<String,String> returnMap = new HashMap<String, String>();
        Map resultMap=channelClientService.delMemVorgVcountry(paraMap);
        if(resultMap.get("responseCode").equals("0000")){
            returnMap.put("errCode","0000");
        }else{
            returnMap.put("errCode","9999");
            returnMap.put("errMsg",(String)resultMap.get("responseDesc"));
        }
        WithdrawJSON json = WithdrawJSON.JsonBuilder();
        json.setSuccess("0000".equals(returnMap.get("errCode")));
        json.setReason(returnMap.get("errMsg"));
        SpringControllerUtils.renderText(response, json.toString());
        return null;
    }

    public ModelAndView list(HttpServletRequest request,HttpServletResponse response){
        String memCode=request.getParameter("memCode");
        String orgCode=request.getParameter("orgCode");
        String countryCode = request.getParameter("countryCode");
        Page<Map> page = PageUtils.getPage(request);
        Map paraMap=new HashMap();
        if(StringUtils.isNotBlank(memCode)){
            paraMap.put("memCode", memCode);
        }
        if(StringUtils.isNotBlank(orgCode)){
            paraMap.put("orgCode", orgCode);
        }
        if(StringUtils.isNotBlank(countryCode)){
            paraMap.put("countryCode", countryCode);
        }
        paraMap.put("page", page);
        Map resultMap=channelClientService.queryMemVorgVcountry(paraMap);
        List paymentChannels = channelClientService.queryPaymentChannel(null,null,null,null);
        List<Map> result= (List<Map>) resultMap.get("result");
        ChannelItemOrgCodeEnum[] channelItems = ChannelItemOrgCodeEnum.values();
        Map<String,ChannelItemOrgCodeEnum> orgMaps = new HashMap<String, ChannelItemOrgCodeEnum>();
        for (ChannelItemOrgCodeEnum every:channelItems
             ) {
            orgMaps.put(every.getCode(), every);
        }
        if(result != null && result.size() > 0){
            for (Map map : result) {
                String orgCodeInside = (String)map.get("orgCode");
                map.put("orgCodeDesc",orgMaps.get(orgCodeInside).getDesc());
                String countryCodeInside = (String)map.get("countryCode");
                if("000".equals(countryCodeInside)){
                    map.put("countryCodeDesc","其他000");
                }else{
                    map.put("countryCodeDesc","美国USA，加拿大CAN");
                }
            }
        }
        Map pageMap = (Map) resultMap.get("page");
        page = MapUtil.map2Object(Page.class, pageMap);
        return new ModelAndView(list).addObject("result", result).addObject("page",page);
    }
}
