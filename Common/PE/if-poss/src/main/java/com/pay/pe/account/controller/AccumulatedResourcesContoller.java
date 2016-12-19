/**
 * 
 */
package com.pay.pe.account.controller;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.pe.account.dto.AccumulatedResourcesDTO;
import com.pay.pe.account.service.AccumulatedResourcesService;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.JSonUtil;
/**
 * 
 * @author 戴德荣
 *
 */
public class AccumulatedResourcesContoller extends MultiActionController {
	
	
	private String indexView;
	private String listView;
	private String detailView;
	
	private AccumulatedResourcesService accumulatedResourcesService;

	/**
	 * @param indexView the indexView to set
	 */
	public void setIndexView(String indexView) {
		this.indexView = indexView;
	}

	/**
	 * @param listView the listView to set
	 */
	public void setListView(String listView) {
		this.listView = listView;
	}

	/**
	 * @param detailView the detailView to set
	 */
	public void setDetailView(String detailView) {
		this.detailView = detailView;
	}

	
	
	
	
	public void setAccumulatedResourcesService(
			AccumulatedResourcesService accumulatedResourcesService) {
		this.accumulatedResourcesService = accumulatedResourcesService;
	}

	/**
	 * 到导航页
	 * @param request
	 * @param response
	 * @param bindParamDto
	 * @return
	 */
	public ModelAndView index(HttpServletRequest request,
            HttpServletResponse response){
		return new ModelAndView(indexView);
	}
	
	
	/**
	 * 到查询页
	 * @param request
	 * @param response
	 * @param bindParamDto
	 * @return
	 */
	public ModelAndView search(HttpServletRequest request,
            HttpServletResponse response,AccumulatedResourcesDTO dto){
		Page pageParam  = PageUtils.getPage(request);
		Page<AccumulatedResourcesDTO> page = accumulatedResourcesService.searchPage(dto,pageParam);
				
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("page", page);
		return new ModelAndView(listView,map);
	}
	/**
	 * 查询详情信息
	 * @param request
	 * @param response
	 * @param bindParamDto
	 * @return
	 */
	public ModelAndView queryDetail(HttpServletRequest request,
            HttpServletResponse response){
		Long id  = ServletRequestUtils.getLongParameter(request, "id",-1L);
		String htmlType  = ServletRequestUtils.getStringParameter(request, "htmlType",null);
		AccumulatedResourcesDTO dto = null;
		if(id!=-1){
			dto = accumulatedResourcesService.findById(id);
		}
		if(htmlType.equals("json")){
			String js = JSonUtil.toJSonString(dto);
			response.setContentType("text/plain;charset=UTF-8");
			response.setHeader("Cache-Control", "no-cache");
			try {
				response.getWriter().write(js);
			} catch (IOException e) {
				e.printStackTrace();
			}
			return null;
		}
		
		
		return new ModelAndView(detailView).addObject("dto", dto);
	}
	
	
	
	/**
	 * 创建
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws IOException
	 */
		public ModelAndView creareRes(HttpServletRequest request,
            HttpServletResponse response,AccumulatedResourcesDTO dto) throws IOException{
		String loginUser = SessionUserHolderUtil.getLoginId();
		dto.setModifiedBy(loginUser);
		dto.setCreatedBy(loginUser);
		Long id =  accumulatedResourcesService.addAccumulatedResRdTx(dto);
		response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		String resultMsg = "S";
		if(id==-1){
			resultMsg = "新增失败，请保证订单类型 、支付方式、订单类型、支付服务代码及作用方五个不能同时相同";
		}
		response.getWriter().write(resultMsg);
		return null;
	}
	/**
	 * 修改
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws IOException
	 */
	public ModelAndView modifyRes(HttpServletRequest request,
            HttpServletResponse response,AccumulatedResourcesDTO dto) throws IOException{
		String loginUser = SessionUserHolderUtil.getLoginId();
		dto.setModifiedBy(loginUser);
		response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		boolean isOk =  accumulatedResourcesService.modifyAccumulatedResRdTx(dto);
		response.getWriter().write(isOk?"S":"更新失败，请保证订单类型 、支付方式、订单类型、支付服务代码及作用方五个不能同时相同");
		return null;
	}
	
	
	
	/**
	 * 注销整个证书
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView removeAccRes(HttpServletRequest request,
            HttpServletResponse response) throws Exception{
		Long  id = ServletRequestUtils.getLongParameter(request, "id",-1L); 
		boolean isRemove = accumulatedResourcesService.removeAccuResRdTx(id);
		response.getWriter().write(isRemove?"S":"F");
		return null;
	}
	

}
