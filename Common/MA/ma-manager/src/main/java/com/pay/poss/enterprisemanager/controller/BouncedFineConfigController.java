package com.pay.poss.enterprisemanager.controller;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.poss.enterprisemanager.service.BouncedFineConfigService;
/**
 * 配置拒付罚款规则
 * @author delin
 * @date 2016年7月19日20:03:50
 */
public class BouncedFineConfigController extends MultiActionController {

	private BouncedFineConfigService bouncedFineConfigService;
	
	/**
	 * 新增拒付罚款配置
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView addConfig(HttpServletRequest request,HttpServletResponse response){
		String rules=request.getParameter("rules");
		Map map = requestParam(request);
		List<Map<String,Object>> result = bouncedFineConfigService.findBouncedFineConfig(map);
		map.put("rules", rules);
		if(result !=null && result.size()>0){
			try {
				response.getWriter().print(1000);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		Object object = bouncedFineConfigService.addBouncedFineConfig(map);
		try {
			response.getWriter().print(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	/**
	 * 封装参数
	 * @param request
	 * @return
	 */
	public Map requestParam(HttpServletRequest request){
		 String cardOrg=request.getParameter("cardOrg");
			String rules=request.getParameter("rules");
			String memberCode=request.getParameter("memberCode");
			Map<String, String> map=new HashMap<String, String>();
			if(rules.equals("1")){//规则一 参数
			String content1=request.getParameter("content1");
			map.put("content1", new BigDecimal(content1).multiply(new BigDecimal(1000)).toString());
			}else if(rules.equals("2")){//规则二参数
			String content2=request.getParameter("content2");	
			String content3=request.getParameter("content3");	
			String content5=request.getParameter("content5");	
			String content6=request.getParameter("content6");	
			String content7=request.getParameter("content7");	
			String content8=request.getParameter("content8");	
			map.put("content1", content2);
			map.put("content2", new BigDecimal(content3).multiply(new BigDecimal(1000)).toString());
			map.put("content3", content5);
			map.put("content4", new BigDecimal(content6).multiply(new BigDecimal(1000)).toString());
			map.put("content5", content7);
			map.put("content6", new BigDecimal(content8).multiply(new BigDecimal(1000)).toString());
			}else if(rules.equals("3")){//规则三参数
			String content9=request.getParameter("content9");	
			String content10=request.getParameter("content10");	
			String content12=request.getParameter("content12");	
			String content13=request.getParameter("content13");	
			String content14=request.getParameter("content14");	
			String content15=request.getParameter("content15");	
			map.put("content1", new BigDecimal(content9).divide(new BigDecimal(100)).toString());
			map.put("content2", new BigDecimal(content10).multiply(new BigDecimal(1000)).toString());
			map.put("content3", new BigDecimal(content12).divide(new BigDecimal(100)).toString());
			map.put("content4", new BigDecimal(content13).multiply(new BigDecimal(1000)).toString());
			map.put("content5", new BigDecimal(content14).divide(new BigDecimal(100)).toString());
			map.put("content6", new BigDecimal(content15).multiply(new BigDecimal(1000)).toString());
			}else if(rules.equals("4")){//规则四参数
			String content16=request.getParameter("content16");	
			String content17=request.getParameter("content17");	
			String content19=request.getParameter("content19");	
			String content20=request.getParameter("content20");	
			String content21=request.getParameter("content21");	
			String content22=request.getParameter("content22");	
			map.put("content1", new BigDecimal(content16).divide(new BigDecimal(100)).toString());
			map.put("content2", new BigDecimal(content17).multiply(new BigDecimal(1000)).toString());
			map.put("content3", new BigDecimal(content19).divide(new BigDecimal(100)).toString());
			map.put("content4", new BigDecimal(content20).multiply(new BigDecimal(1000)).toString());
			map.put("content5", new BigDecimal(content21).divide(new BigDecimal(100)).toString());
			map.put("content6", new BigDecimal(content22).multiply(new BigDecimal(1000)).toString());
			}
			map.put("cardOrg", cardOrg);
			map.put("memberCode", memberCode);
			return map;
	}
	/**
	 * update拒付罚款配置
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView updateConfig(HttpServletRequest request,HttpServletResponse response){
		String rules=request.getParameter("rules");
		Map map = this.requestParam(request);
		map.put("rules", rules);
		String id=request.getParameter("id");
		map.put("id", id);
		bouncedFineConfigService.updateBouncedFineConfig(map);
		try {
			response.getWriter().print(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	/**
	 * 删除拒付罚款配置 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView deleteBouncedConf(HttpServletRequest request,HttpServletResponse response){
		String id=request.getParameter("id");
		bouncedFineConfigService.deleteBouncedConf(id);
		try {
			response.getWriter().print(true);
		} catch (IOException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	
	public void setBouncedFineConfigService(
			BouncedFineConfigService bouncedFineConfigService) {
		this.bouncedFineConfigService = bouncedFineConfigService;
	}
}
