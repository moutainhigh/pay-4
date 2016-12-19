package com.pay.pe.manualbooking.controller;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;

import com.pay.pe.manualbooking.dto.VouchDataDto;
import com.pay.pe.manualbooking.service.VouchService;
import com.pay.pe.manualbooking.util.VouchDataStatus;
import com.pay.pe.manualbooking.util.VouchSearchCriteria;

/**
 * 
 * 手工记账申请查询控制器
 */
public class VouchSearchController extends BaseVouchSearchController {	
	private static final Log LOG = LogFactory.getLog(VouchSearchController.class);
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

	public VouchSearchController() {
		super();
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * 访问手工记账查询页面
	 */
	public ModelAndView init(final HttpServletRequest request, final HttpServletResponse response) {
		LOG.info("Start");
		VouchSearchCriteria vouchSearchCriteria = new VouchSearchCriteria();
		vouchSearchCriteria.setVouchSeq("");
		vouchSearchCriteria.setVouchCode("");
		vouchSearchCriteria.setVouchStatus(VouchDataStatus.UNAUDITED.getValue());
		vouchSearchCriteria.setDateFrom(null);
		vouchSearchCriteria.setDateTo(null);
		vouchSearchCriteria.setPage(new Integer(1));
		
		List<VouchDataDto> vouchDataDtos = new ArrayList<VouchDataDto>();
		
		LOG.info("End");
		return new ModelAndView(mainSearchPage)
			.addObject("appContext", request.getContextPath())
			.addObject(VOUCH_SEARCH_CRITERIA_NAME, vouchSearchCriteria)
			.addObject(SEARCH_RESULT_NAME, vouchDataDtos);
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
		return new ModelAndView(mainSearchPage)
			.addObject("appContext", request.getContextPath())
			.addObject(VOUCH_SEARCH_CRITERIA_NAME, vouchSearchCriteria);
	}
	
	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * 查询申请
	 */
	public ModelAndView searchVouchs(final HttpServletRequest request, final HttpServletResponse response) {
//		LOG.info("Start");
//		VouchSearchCriteria vouchSearchCriteria = getVouchSearchCriteria(request);
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
//		StringBuffer params = new StringBuffer("method=searchVouchs");
//		params.append("&vouchSeq=");
//		if (StringUtils.isNotEmpty(vouchSearchCriteria.getVouchSeq())) {
//			params.append(vouchSearchCriteria.getVouchSeq().trim());
//		}
//		params.append("&vouchCode=");
//		if (StringUtils.isNotEmpty(vouchSearchCriteria.getVouchCode())) {
//			params.append(vouchSearchCriteria.getVouchCode().trim());
//		}
//		params.append("&vouchStatus=").append(vouchSearchCriteria.getVouchStatus().intValue());
//		params.append("&dateFrom=").append(vouchSearchCriteria.getDateFromString());
//		params.append("&dateTo=").append(vouchSearchCriteria.getDateToString());
//		vpage.setParamName(params.toString());
//		
//		LOG.info("Before Search");
//		List<VouchDataDto> vouchDataDtos = vouchService.findVouch(vouchSearchCriteria, vpage);
//		LOG.info("Size : " + vouchDataDtos.size());
//		
//		vpage.setView(pageHelper.getPageBreakStr(vpage));
//		
//		vouchSearchCriteria.setPage(vpage.getTargetPage());
//		
//		LOG.info("End");
//		return new ModelAndView(searchResultPage)
//			.addObject("appContext", request.getContextPath())
//			.addObject(VOUCH_SEARCH_CRITERIA_NAME, vouchSearchCriteria)
//			.addObject(SEARCH_RESULT_NAME, vouchDataDtos)
//			.addObject("vpage", vpage);
		return null ;
	}
}
