package com.pay.poss.controller.fi.channel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pay.util.StringUtil;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.fi.helper.ChannelItemOrgCodeEnum;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.client.ChannelClientService;
import com.pay.util.MapUtil;

public class ChannelCurrencyReviewedController extends MultiActionController {

	private String index;
	
	private String list;

	private ChannelClientService channelClientService;
	/**
	 * 渠道币种审核查询页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView index(HttpServletRequest request,HttpServletResponse response){
		ChannelItemOrgCodeEnum[] channelItems = ChannelItemOrgCodeEnum.values();		
		return new ModelAndView(index).addObject("channelItems", channelItems);
	}		
	/**
	 * 渠道币种审核查询结果
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView list(HttpServletRequest request,HttpServletResponse response){
		String orgCode=request.getParameter("orgCode");
		String flag=request.getParameter("flag");
		String  status=request.getParameter("status");
		Page<Map> page = PageUtils.getPage(request);
		Map paraMap=new HashMap();
		paraMap.put("orgCode", orgCode);
		paraMap.put("flag", flag);
		paraMap.put("status", status);
		String  partnerId=request.getParameter("partnerId");
		if(StringUtil.isNumber(partnerId)){
			paraMap.put("partnerId", Long.parseLong(partnerId));
		}
		paraMap.put("page", page);
		Map<String, Object>  resultMap = channelClientService.queryReviewedChannelCurrency(paraMap);
		List<Map> result=(List<Map>) resultMap.get("result");
		ChannelItemOrgCodeEnum[] values = ChannelItemOrgCodeEnum.values();
		Map pageMap = (Map) resultMap.get("page");
		page = MapUtil.map2Object(Page.class, pageMap);
		return new ModelAndView(list).addObject("result", result).addObject("channelItems", values).addObject("page",page);
	}
	/**
	 * 审核
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView reviewed(HttpServletRequest request,HttpServletResponse response){
		 	ModelAndView indexView = new ModelAndView(index);	
			String id=request.getParameter("id");
			String status=request.getParameter("status");//1：通过 2：拒绝
			String flag=request.getParameter("flag");//1：新增，2：修改  3：删除
			String channelCurrencyId=request.getParameter("channelCurrencyId");
			Map<String,String> paraMap=new HashMap<String,String>();
			paraMap.put("id", id);
			paraMap.put("status",status);
			paraMap.put("flag", flag);
			paraMap.put("channelCurrencyId", channelCurrencyId);
			Map<String, Object>  resultMap =channelClientService.reviewedChannelCurrency(paraMap);
			ChannelItemOrgCodeEnum[] values = ChannelItemOrgCodeEnum.values();
			if(resultMap.get("responseCode").equals("0000")){
				indexView.addObject("msg", "审核成功");
			}
		return indexView.addObject("channelItems", values);
	}
	
	
	public void setIndex(String index) {
		this.index = index;
	}
	public void setList(String list) {
		this.list = list;
	}
	public void setChannelClientService(ChannelClientService channelClientService) {
		this.channelClientService = channelClientService;
	}
	
}
