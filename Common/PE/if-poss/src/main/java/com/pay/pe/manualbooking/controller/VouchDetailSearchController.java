package com.pay.pe.manualbooking.controller;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import com.pay.pe.manualbooking.service.VouchService;
import com.pay.pe.manualbooking.util.VouchDetailSearchCriteria;

/**
 *
 * 手工记账明细查询控制器
 */
public class VouchDetailSearchController extends BaseVouchSearchController {	
	private static final Log LOG = LogFactory.getLog(VouchDetailSearchController.class);
	private String mainSearchPage;
	
	private String searchResultPage;
	
	private VouchService vouchService;
	
	public String getMainSearchPage() {
		return mainSearchPage;
	}

	public void setMainSearchPage(String mainSearchPage) {
		this.mainSearchPage = mainSearchPage;
	}
	
	public String getSearchResultPage() {
		return searchResultPage;
	}

	public void setSearchResultPage(String searchResultPage) {
		this.searchResultPage = searchResultPage;
	}

	public VouchService getVouchService() {
		return vouchService;
	}

	public void setVouchService(VouchService vouchService) {
		this.vouchService = vouchService;
	}
	
	public VouchDetailSearchController() {
		super();
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * 访问明细查询主页面
	 */
	public ModelAndView init(final HttpServletRequest request, final HttpServletResponse response) {
		LOG.info("Start");
		VouchDetailSearchCriteria vouchDetailSearchCriteria = new VouchDetailSearchCriteria("", null, null);
		LOG.info("End");
		return new ModelAndView(mainSearchPage)
			.addObject("appContext", request.getContextPath())
			.addObject(VOUCH_DETAIL_SEARCH_CRITERIA_NAME, vouchDetailSearchCriteria);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * 查询明细
	 */
	public ModelAndView searchVouchDetails(final HttpServletRequest request, final HttpServletResponse response) {
//		LOG.info("Start");
//		VouchDetailSearchCriteria vouchDetailSearchCriteria = getVouchDetailSearchCriteria(request);
//		
//		VPage vpage = new VPage();
//		
//		int pageSize = 10;
//		PageHelper pageHelper = new PageHelper(request);
//		pageSize = pageHelper.doCustomPerPage(pageSize);
//		vpage.setPageSize(pageSize);
//		
//		int page = ParamUtil.getInt(request, "page", 1);
//		vpage.setTargetPage(page);
//		
//		String realPath = getServletContext().getRealPath("/");
//		vpage.setRealPath(realPath);
//		
//		vpage.getParamName();
//		
//		StringBuffer params = new StringBuffer("method=searchVouchDetails");
//		params.append("&accountCode=");
//		if (StringUtils.isNotEmpty(vouchDetailSearchCriteria.getAccountCode())) {
//			params.append(vouchDetailSearchCriteria.getAccountCode().trim());
//		}
//		params.append("&dateFrom=").append(vouchDetailSearchCriteria.getDateFromString());
//		params.append("&dateTo=").append(vouchDetailSearchCriteria.getDateToString());
//		vpage.setParamName(params.toString());
//		
//		
//		LOG.info("Before Search");
//		List<VouchDataDetailSearchDto> vouchDataDetailSearchDtos = 
//			vouchService.findVouchDetail(vouchDetailSearchCriteria, vpage);
//		LOG.info("Size : " + vouchDataDetailSearchDtos.size());
//		
//		vpage.setView(pageHelper.getPageBreakStr(vpage));
//		
//		LOG.info("End");
//		return new ModelAndView(searchResultPage)
//			.addObject("appContext", request.getContextPath())
//			.addObject(VOUCH_DETAIL_SEARCH_CRITERIA_NAME, vouchDetailSearchCriteria)
//			.addObject(DETAIL_SEARCH_RESULT_NAME, vouchDataDetailSearchDtos)
//			.addObject("vpage", vpage);
		return null ;
	}
}
