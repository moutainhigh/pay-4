/**
 * 
 */
package com.pay.app.controller.base.ordercompletion;

import java.sql.Timestamp;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.app.base.session.SessionHelper;
import com.pay.base.fi.model.CustomizationParam;
import com.pay.base.fi.service.OrderCompletionService;
import com.pay.util.StringUtil;
import com.pay.util.StringUtils;

/**
 * poss补单
 * @author PengJiangbo
 *
 */
public class OrderCompletionController extends MultiActionController {
	
	private String indexView ;
	
	private OrderCompletionService orderCompletionService ;
	
	/**
	 * 默认页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mv = new ModelAndView(indexView) ;
		loadCustomizationParam(mv);
		return mv ;
	}
	
	/**
	 * 新增或修改
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView customizationParamSoU(HttpServletRequest request, HttpServletResponse response){
		
		ModelAndView mv = new ModelAndView(indexView) ;
		String doFlag = StringUtil.null2String(request.getParameter("doFlag")) ;
		long effectiveTime = StringUtils.stringToLong(request.getParameter("effectiveTime")) ;
		String effectiveUnit = StringUtil.null2String(request.getParameter("effectiveUnit")) ;
		if(null == doFlag || "".equals(doFlag)){
			
			CustomizationParam customizationParam = constructorVo(effectiveTime, effectiveUnit);
			this.orderCompletionService.insertCustomizationParam(customizationParam) ;
			loadCustomizationParam(mv);
		}else if(doFlag.equalsIgnoreCase("update")){
			long id = StringUtils.stringToLong(request.getParameter("id")) ;
			Map<String, Object> hMap = new HashMap<String, Object>() ;
			hMap.put("id", id);
			hMap.put("effectiveTime", effectiveTime) ;
			hMap.put("effectiveUnit", effectiveUnit) ;
			hMap.put("updateTime", new Timestamp(new Date().getTime())) ;
			hMap.put("updator", SessionHelper.getMemeberCodeBySession()) ;
			this.orderCompletionService.updateCustomizationParam(hMap) ;
			loadCustomizationParam(mv);
		}else{
			
		}
		
		return mv ;
	}

	/**
	 * 构造值对象
	 * @param effectiveTime
	 * @param effectiveUnit
	 * @return
	 */
	private CustomizationParam constructorVo(long effectiveTime,
			String effectiveUnit) {
		CustomizationParam customizationParam = new CustomizationParam() ;
		customizationParam.setCreateTime(new Timestamp(new Date().getTime()));
		customizationParam.setEffectiveTime(effectiveTime);
		customizationParam.setEffectiveUnit(effectiveUnit);
		customizationParam.setMemberCode(Long.valueOf(SessionHelper.getMemeberCodeBySession()));
		return customizationParam;
	}

	/**
	 * 加载现有定制化参数
	 * @return
	 */
	public void loadCustomizationParam(ModelAndView mv){
		CustomizationParam customizationParam =  this.orderCompletionService.findCustomizationParam() ;
		if(null == customizationParam)
			customizationParam = new CustomizationParam() ;
		mv.addObject("customizationParam", customizationParam) ;
	}
	
	//-------------setter
	public void setIndexView(String indexView) {
		this.indexView = indexView;
	}

	public void setOrderCompletionService(
			OrderCompletionService orderCompletionService) {
		this.orderCompletionService = orderCompletionService;
	}
	
}
