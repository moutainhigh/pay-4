package com.pay.pe.manualbooking.controller;

import java.util.Date;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.pe.manualbooking.model.VouchData;
import com.pay.pe.manualbooking.model.VouchDetailData;
import com.pay.pe.manualbooking.service.MisService;
import com.pay.pe.manualbooking.util.VouchSearchCriteria;
import com.pay.poss.security.model.SessionUserHolder;
import com.pay.util.SpringControllerUtils;

public class MisController extends BaseVouchSearchController{
	private static final Log LOG = LogFactory.getLog(MisController.class);
	private MisService misService;
	
	
	public void setMisService(MisService misService) {
		this.misService = misService;
	}
	
	protected transient Map<String,String> urlMap;
	
	public Map<String, String> getUrlMap() {
		return urlMap;
	}

	public void setUrlMap(Map<String, String> urlMap) {
		this.urlMap = urlMap;
	}

	//手工记账查询页面
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response){
		VouchSearchCriteria vouchSearchCriteria = new VouchSearchCriteria();
		vouchSearchCriteria.setVouchSeq("");
		vouchSearchCriteria.setVouchCode("");
		vouchSearchCriteria.setRemark("");
//		vouchSearchCriteria.setVouchStatus(VouchDataStatus.UNAUDITED.getValue());
		vouchSearchCriteria.setDateFrom(null);
		vouchSearchCriteria.setDateTo(null);
		vouchSearchCriteria.setPage(new Integer(1));
		
		
		String url=this.urlMap.get("vouchqueryinit");
		return new ModelAndView(url).addObject(VOUCH_SEARCH_CRITERIA_NAME, vouchSearchCriteria);
    }
	
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * 从其他页面回到查询页面
	 */
	public ModelAndView reSearchVouchs(final HttpServletRequest request, final HttpServletResponse response) {
		LOG.info("Start");
		VouchSearchCriteria vouchSearchCriteria = getVouchSearchCriteria(request);
		
		LOG.info("End");
		return new ModelAndView(this.urlMap.get("vouchqueryinit"))
			.addObject("appContext", request.getContextPath())
			.addObject(VOUCH_SEARCH_CRITERIA_NAME, vouchSearchCriteria);
	}
	
	
	
	/**
	 *手工记账  添加
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView addInfo(HttpServletRequest request,
			HttpServletResponse response) {
		  String url=this.urlMap.get("bothInfo");
		  String creator = getSessionUserHolder()!= null ? getSessionUserHolder().getUsername() : "sys"; //申请人
//		  String creator = vouchUserService.getCurrentUser();
		  return new ModelAndView(url).addObject("appContext", request.getContextPath()).addObject("curdate", new Date()).addObject("creator", creator);
	}
	/**
	 *手工记账分页查询 只读 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView queryMis(HttpServletRequest request,
			HttpServletResponse response) {
		VouchSearchCriteria vouchSearchCriteria = getVouchSearchCriteria(request);
		
		Map<String, String> map = new HashMap<String, String>();
		if(request.getParameter("vouchStatus")!=null && !request.getParameter("vouchStatus").equals("4")){
			map.put("status", request.getParameter("vouchStatus"));
		}
		map.put("vouchCode", request.getParameter("vouchCode"));
		map.put("dataFrom", vouchSearchCriteria.getDateFromString());
		map.put("dataTo", vouchSearchCriteria.getDateToString());
		map.put("amountFrom", vouchSearchCriteria.getAmountFromToString());
		map.put("amountTo", vouchSearchCriteria.getAmountToString());
		map.put("remark", vouchSearchCriteria.getRemark());

		Page<VouchData> page = PageUtils.getPage(request);
		try {
			page =misService.search(page, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    String url=this.urlMap.get("vouchquerylist");
		Map<String,Object> model=new HashMap<String,Object>() ;
		model.put("page", page);
		return new ModelAndView(url,model).addObject(VOUCH_SEARCH_CRITERIA_NAME, vouchSearchCriteria);
	}

	/**
	 *手工记账 审核申请状态更新 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView updateVouchstatus(HttpServletRequest request,
			HttpServletResponse response) {
		
		String flag = request.getParameter("flag");//1是删除更新状态为3(作废)   2 是审核申请 更新状态为1(审核通过)
		String id = request.getParameter("id");
		StringBuffer results = new StringBuffer();
		VouchData vd=new VouchData();
		vd.setVouchDataId(Long.valueOf(id));
		if(flag!=null && flag.equals("2")){
			vd.setStatus(1);
		}else if(flag!=null && flag.equals("1")){
			vd.setStatus(3);
		}
		try{
			if(StringUtils.isNotEmpty(id)){
				misService.updateVouchstatus(vd);
				results.append("success");
			}
		}catch(Exception e){
			e.printStackTrace();
		}finally{
			SpringControllerUtils.renderText(response, results.toString());
		}
		return null;
	}
	/**
	 *手工记账 查询明细
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView queryVouchDetailInfo(HttpServletRequest request,
			HttpServletResponse response) {
		
		VouchData vd=new VouchData();
		vd.setVouchDataId(Long.valueOf(request.getParameter("id")));
		Map<String, String> map = new HashMap<String, String>();
		map.put("vouchdataId", request.getParameter("id"));
		
		Page<VouchDetailData> page = PageUtils.getPage(request);
		try {
			List list=misService.getVouchData(vd);
			if(list!=null && list.size()>0){
				for(Iterator it=list.iterator();it.hasNext();){
					vd=(VouchData)it.next();
				}
			}
			page =misService.searchVouchDetail(page, map);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
	    String url=this.urlMap.get("misinfo");
		Map<String,Object> model=new HashMap<String,Object>() ;
		model.put("page", page);
		return new ModelAndView(url,model).addObject("vouchData", vd);
	}
	
	
	
	
	
	
	
	
	protected SessionUserHolder getSessionUserHolder(){
			
			Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
			
			if(authentication != null ) {
				Object sessionObj = authentication.getPrincipal() ;
				
				SessionUserHolder  sessionUserHolder = null;
				
				if(sessionObj instanceof SessionUserHolder){
					
					sessionUserHolder = (SessionUserHolder) sessionObj;
					
				}
				return sessionUserHolder;
			}
			return null;
	}

	
	
	

}
