package com.pay.poss.controller.risk;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.redis.RedisClientTemplate;
import com.pay.rm.orderthrehold.dto.OrderThreholdRuleDTO;
import com.pay.rm.orderthrehold.service.OrderThreholdService;
import com.pay.util.StringUtil;

/**
 * 商户订单阈值配置
 * 
 * @Description
 * @file PartnerOrderThreholdRuleController
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2012 hnapayay Corporation. All rights reserved.
 *          Date
 */
public class PartnerOrderThreholdRuleController extends MultiActionController{
	private final Log logger = LogFactory.getLog(getClass());
	private String queryInit;
	private OrderThreholdService orderThreholdService;
	private RedisClientTemplate redisClient;
	
	private final String threholdKey = "orderThrehold";

	public void setQueryInit(String queryInit) {
		this.queryInit = queryInit;
	}
	
	public void setOrderThreholdService(OrderThreholdService orderThreholdService) {
		this.orderThreholdService = orderThreholdService;
	}

	public void setRedisClient(RedisClientTemplate redisClient) {
		this.redisClient = redisClient;
	}

	/**
	 * 默认查询页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		OrderThreholdRuleDTO threhold = this.orderThreholdService.findOrderThreholdRule(threholdKey);
		return new ModelAndView(queryInit)
				.addObject("description", threhold.getDescription())
				.addObject("duration", threhold.getDuration())
				.addObject("threhold", threhold.getThrehold());
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView save(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String description = StringUtil.null2String(request
				.getParameter("description"));
		int duration = this.getPositiveInteger(request
				.getParameter("duration"));
		int threhold = this.getPositiveInteger(request
				.getParameter("threhold"));

		ModelAndView mv = new ModelAndView(queryInit)
				.addObject("description", description)
				.addObject("duration", request.getParameter("duration"))
				.addObject("threhold", request.getParameter("threhold"));
		
		if (description.trim().length() == 0) {
			mv.addObject("responseDesc", "描述不能为空");
		} else if (description.length() > 60) {
			mv.addObject("responseDesc", "描述不能超过60个字符");
		} else if (duration <= 0) {
			mv.addObject("responseDesc", "时间间隔必须为正整数！");
		} else if (threhold <= 0) {
			mv.addObject("responseDesc", "次数阈值必须为正整数！");
		} else {
			try {
				redisClient.set(threholdKey + "_duration", duration + "");
				redisClient.set(threholdKey + "_threhold", threhold + "");
			} catch (Exception ex) {
				mv.addObject("responseDesc", "redis写入异常！" + ex.getMessage());
			}
			OrderThreholdRuleDTO threholdDTO = new OrderThreholdRuleDTO();
			threholdDTO.setType(threholdKey);
			threholdDTO.setDescription(description);
			threholdDTO.setDuration(duration);
			threholdDTO.setThrehold(threhold);
			this.orderThreholdService.updateOrderThreholdRule(threholdDTO);
			mv.addObject("responseDesc", "更新成功！");
		}

		return mv;
	}
	
	// 将字符串转成正整数，如果出错则返回-1
	private  int getPositiveInteger(String str) {
		int ret = -1;
		try {
			ret = Integer.parseInt(str);
		} catch (NumberFormatException ex) {
			ret = -1;
		}
		return ret;
	}
}
