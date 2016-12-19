package com.pay.poss.memberrelation.controller;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.memberrelation.model.MemberRelation;
import com.pay.poss.memberrelation.service.MemberRelationService;



/**   
* @Title: MemberRelationQueryController.java 
* @Package com.pay.poss.memberrelation.controller 
* @Description: 会员关联查询管理
* @author cf
* @date 2011-10-18 上午10:35:21 
* @version V1.0   
*/
public class MemberRelationQueryController extends MultiActionController {
	private static final Log log = LogFactory.getLog(MemberRelationQueryController.class);

	private MemberRelationService memberRelationService;	
	private String queryView;
	private String listView;
	
	
	public ModelAndView init(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		return new ModelAndView(queryView);
	}
	
	
	public ModelAndView queryList(HttpServletRequest request,
			HttpServletResponse response, MemberRelation dto)
			throws Exception {
		Page page = PageUtils.getPage(request);
		List<MemberRelation> info = memberRelationService.queryMemberRelationByCondition(dto, page);
		page.setResult(info);
	
		return new ModelAndView(listView).addObject("page", page);
	}


	public String getQueryView() {
		return queryView;
	}


	public void setQueryView(String queryView) {
		this.queryView = queryView;
	}


	public String getListView() {
		return listView;
	}


	public void setListView(String listView) {
		this.listView = listView;
	}


	public void setMemberRelationService(MemberRelationService memberRelationService) {
		this.memberRelationService = memberRelationService;
	}


}
