package com.pay.fo.controller.merchantconfigure;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.pay.api.model.MerchantConfigure;
import com.pay.api.model.MerchantConfigureCriteria;
import com.pay.api.service.MerchantConfigureService;
import com.pay.inf.dao.Page;
import com.pay.poss.base.controller.AbstractBaseController;
import com.pay.poss.security.util.SessionUserHolderUtil;

public class MerchantConfigureController extends AbstractBaseController{

	private MerchantConfigureService merchantConfigureService;

	public void setMerchantConfigureService(MerchantConfigureService merchantConfigureService) {
		this.merchantConfigureService = merchantConfigureService;
	}
	
	/**
	 * 新增配置初始化页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView index(HttpServletRequest request , HttpServletResponse response) throws ServletException{
		String viewName = urlMap.get("addMerchantConfigure");
		return new ModelAndView(viewName);
	}
	
	/**
	 * 添加配置
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView add(HttpServletRequest request , HttpServletResponse response) throws ServletException{
		String viewName = urlMap.get("addMerchantConfigure");
		MerchantConfigure dto = new MerchantConfigure();
		bind(request, dto, "dto", null);
		dto.setCreator(SessionUserHolderUtil.getLoginId());
		dto.setUpdator(SessionUserHolderUtil.getLoginId());
		dto.setCreateDate(new Date());
		dto.setUpdateDate(new Date());
		dto.setId(0);
		try{
			Integer id = merchantConfigureService.create(dto);
			Map model = new HashMap();
			model.put("info", "添加成功");
			return new ModelAndView(viewName, model);
		}catch (Exception e) {
			log.error(e.getMessage(), e);
			return new ModelAndView(viewName).addObject("info","添加失败");
		}
	}
	
	/**
	 * 配置查询初始化页面
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView initSearch(HttpServletRequest request , HttpServletResponse response) throws ServletException{
		String viewName = urlMap.get("searchMerchantConfigureInit");
		return new ModelAndView(viewName);
	}
	
	/**
	 * 配置分页列表
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView search(HttpServletRequest request , HttpServletResponse response) throws ServletException{
		String viewName = urlMap.get("searchMerchantConfigureList");
		MerchantConfigure dto = new MerchantConfigure();
		bind(request, dto, "dto", null);
		MerchantConfigureCriteria criteria = new MerchantConfigureCriteria();
		if(StringUtils.isNotBlank(dto.getMerchantCode())){
			criteria.createCriteria().andMerchantCodeEqualTo(dto.getMerchantCode()).andNotifyFlagEqualTo(dto.getNotifyFlag()).andStatusEqualTo(dto.getStatus());
		}else{
			criteria.createCriteria().andNotifyFlagEqualTo(dto.getNotifyFlag()).andStatusEqualTo(dto.getStatus());
		}
		
		Page page = new Page();
		if(StringUtils.isNotBlank(request.getParameter("pageNo")) && !("undefined".equalsIgnoreCase(request.getParameter("pageNo")))){
			page.setTargetPage(Integer.parseInt(request.getParameter("pageNo")));
		}
		if(StringUtils.isNotBlank(request.getParameter("pageSize")) && !("undefined".equalsIgnoreCase(request.getParameter("pageSize")))){
			page.setPageSize(Integer.parseInt(request.getParameter("pageSize")));
		}
		if(StringUtils.isNotBlank(request.getParameter("totalCount")) && !("undefined".equalsIgnoreCase(request.getParameter("totalCount")))){
			page.setTotalRecord(Integer.parseInt(request.getParameter("totalCount")));
		}
		
		List<MerchantConfigure> list = merchantConfigureService.query(page, criteria);
		return new ModelAndView(viewName).addObject("list", list).addObject("page", page);
	}
	
	/**
	 * init修改配置信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView initModify(HttpServletRequest request , HttpServletResponse response) throws ServletException{
		String viewName = urlMap.get("modify");
		int id = Integer.parseInt(request.getParameter("id"));
		MerchantConfigure dto = merchantConfigureService.findById(id);
		return new ModelAndView(viewName).addObject("dto", dto);
	}
	/**
	 * execute修改配置信息
	 * @param request
	 * @param response
	 * @return
	 * @throws ServletException
	 */
	public ModelAndView modifyMerchantConfigure(HttpServletRequest request , HttpServletResponse response) throws ServletException{
		MerchantConfigure dto = new MerchantConfigure();
		bind(request, dto, "", null);
		dto.setUpdateDate(new Date());
		dto.setUpdator(SessionUserHolderUtil.getLoginId());
		merchantConfigureService.update(dto);
		ModelAndView modelAndView = new ModelAndView();
		String viewName = urlMap.get("searchMerchantConfigureInit");
		return  new ModelAndView(viewName);
	}
	
	/**
	 * 检查商户编号是否已经存在
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView checkRepeat(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String merchantCode = request.getParameter("merchantCode");
		String id = request.getParameter("id");

		String res = "yes";
		MerchantConfigureCriteria criteria = new MerchantConfigureCriteria();
		if(StringUtils.isNotBlank(id)){
			criteria.createCriteria().andMerchantCodeEqualTo(merchantCode).andIdEqualTo(Integer.valueOf(id));
			MerchantConfigure merchantConfigure = merchantConfigureService.findObjectByCriteria(criteria);
			if(merchantConfigure != null && merchantConfigure.getId().intValue() != Integer.parseInt(id)){
				res = "repeat";
			}
		}else{
			criteria.createCriteria().andMerchantCodeEqualTo(merchantCode);
			MerchantConfigure merchantConfigure = merchantConfigureService.findObjectByCriteria(criteria);
			if(merchantConfigure != null){
				res = "repeat";
			}
		}
		response.getWriter().print(res);
		response.getWriter().close();
		return null;
	}
}
