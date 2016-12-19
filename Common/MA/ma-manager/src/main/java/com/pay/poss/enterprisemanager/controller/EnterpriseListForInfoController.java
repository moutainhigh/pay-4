package com.pay.poss.enterprisemanager.controller;


import java.util.ArrayList;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.pay.acc.comm.MerchantStatusEnum;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.base.common.PossIpUtil;
import com.pay.poss.base.common.SessionUserUtils;
import com.pay.poss.enterprisemanager.common.Constants;
import com.pay.poss.enterprisemanager.dto.EnterpriseSearchDto;
import com.pay.poss.enterprisemanager.dto.EnterpriseSearchListDto;
import com.pay.poss.enterprisemanager.formbean.EnterpriseSearchFormBean;
import com.pay.poss.enterprisemanager.service.IEnterpriseService;
import com.pay.poss.merchantmanager.dto.SignMessageDto;
import com.pay.poss.merchantmanager.service.ISignMessageService;
import com.pay.poss.security.model.SessionUserHolder;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.poss.userrelation.dto.NodesDto;
import com.pay.poss.userrelation.dto.UserRelationDto;
import com.pay.poss.userrelation.service.IUserRelationService;


/**
 * 
 * @Description
 * @project poss-membermanager
 * @file MerchantListController.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2010-8-11 gungun_zhang Create
 */

public class EnterpriseListForInfoController extends SimpleFormController {

	private Log log = LogFactory.getLog(EnterpriseListForInfoController.class);

	private IEnterpriseService enterpriseService ;
	private ISignMessageService iSignMessageService;
	private IUserRelationService userRelationService;
	
	
	

	public void setUserRelationService(IUserRelationService userRelationService) {
		this.userRelationService = userRelationService;
	}

	@Override
	protected Map<String, Object> referenceData(HttpServletRequest request) throws Exception {
		log.debug("EnterpriseListForInfoController.referenceData() is running...");
		Map<String, Object> paramMap = new Hashtable<String, Object>();
		//获取当前登录用户及页面入参
		SessionUserHolder user = SessionUserUtils.getUserInfo(request);
		String memberCode = request.getParameter("memberCode");
		String memberStatus = request.getParameter("memberStatus");
		String ip = PossIpUtil.getIp(request);
		
		Map<String, Object> dataMap = new Hashtable<String, Object>();
		//保证参数有值
		if(memberCode!=null&&memberStatus!=null && user!=null){
			paramMap.put("objectCode", memberCode);
			paramMap.put("memberStatus", memberStatus);
			paramMap.put("loginIp", ip);
			paramMap.put("loginName", user.getUsername());
			enterpriseService.updateMemberStatusTrans(paramMap);
			//noted by wangtq
			/*MerchantDto merchantDto = enterpriseService.getMerchantByMemberCode(memberCode);
			dataMap.put("merchantEmail", merchantDto.getEmail());*/
			
			EnterpriseSearchListDto dto = enterpriseService.queryEnterpriseByMemberCode(memberCode);
			dataMap.put("merchantEmail", dto.getLoginName());
		}
		
		MerchantStatusEnum[] merchantStatusEnum = MerchantStatusEnum.values();
		dataMap.put("merchantStatusEnum", merchantStatusEnum);
		//从企业密码重置中过来的参数added by wangtq
		String merchantEmail = request.getParameter("merchantEmail");  
		if(null != merchantEmail && !"".equals(merchantEmail)){
			dataMap.put("merchantEmail", merchantEmail);
		}
		//取频道(市场名称)added by wangtq
		List<SignMessageDto> info = iSignMessageService.querySignMessageByCondition(null);
		dataMap.put("info", info);
		//List<NodesDto> loginSubNodes = getLoginIdSubNodes();
		//dataMap.put("loginSubNodes", loginSubNodes == null ? new ArrayList<NodesDto>() :loginSubNodes);
		return dataMap;
	}

	private  List<UserRelationDto> allUserRelationDto = new ArrayList<UserRelationDto>();
	
	public void getrelationDto(List<UserRelationDto> data){
		for(UserRelationDto item : data){  
			List<UserRelationDto> subData = userRelationService.findByLayer(item.getId());
			if(subData!=null && !subData.isEmpty()){
				allUserRelationDto.addAll(subData);
				getrelationDto(subData);
			}else{
				allUserRelationDto.add(item);
			}
		}
	}
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) throws Exception {
		log.debug("EnterpriseListForInfoController.onSubmit() is running...");
		EnterpriseSearchFormBean enterpriseSearchFormBean = (EnterpriseSearchFormBean) command;
		//获取登入的用户
		String loginId = SessionUserHolderUtil.getLoginId();
		UserRelationDto relationDto=null;
		if(StringUtils.isEmpty(enterpriseSearchFormBean.getSignLoginId())){
			relationDto	= userRelationService.findUserRelatoinByLoginId(loginId);
		}
		if(StringUtils.isNotEmpty(enterpriseSearchFormBean.getSignLoginId())){
			enterpriseSearchFormBean.setSignLoginId("'"+enterpriseSearchFormBean.getSignLoginId()+"'");
		}
		allUserRelationDto.clear();
		if(relationDto!=null){
			allUserRelationDto.add(relationDto);
			long id = relationDto.getId();
			List<UserRelationDto> findById = userRelationService.findByLayer(id);
			if(findById!=null || !findById.isEmpty()){
				allUserRelationDto.addAll(findById);
				getrelationDto(findById);
			}
		}
		
		EnterpriseSearchDto enterpriseSearchDto = new EnterpriseSearchDto();
		BeanUtils.copyProperties(enterpriseSearchFormBean, enterpriseSearchDto);
		
		if(StringUtils.isNotEmpty(enterpriseSearchFormBean.getMerchantName())){
			if(Pattern.compile(Constants.SEARCHKEY).matcher(enterpriseSearchFormBean.getMerchantName()).matches()){
				enterpriseSearchDto.setEnterpriseSearchKey(enterpriseSearchFormBean.getMerchantName());
				enterpriseSearchDto.setMerchantName(null);
			}
		}
		Page<EnterpriseSearchListDto> page = PageUtils.getPage(request);
		enterpriseSearchDto.setPageEndRow((page.getPageNo()*page.getPageSize())+"");
		if((page.getPageNo()-1)==0){
			
			enterpriseSearchDto.setPageStartRow("0");
		}else{
			enterpriseSearchDto.setPageStartRow((page.getPageNo()-1)*page.getPageSize()+"");
		}
		
		//处理所属销售条件 2014/5/12
		if(StringUtils.isEmpty(enterpriseSearchDto.getSignLoginId())){
			//List<NodesDto> loginSubNodes = getLoginIdSubNodes();
			//enterpriseSearchDto.setSignLoginIds((this.convertNodesToString(loginSubNodes)));
		}else{
			enterpriseSearchDto.setSignLoginIds(new String[]{enterpriseSearchDto.getSignLoginId()});
		}
		
		Integer enterpriseListCount=null;
		List<EnterpriseSearchListDto> enterpriseList=null;
		List<UserRelationDto> tempList=null;
		if(allUserRelationDto!=null && allUserRelationDto.size()>0){
			tempList = new ArrayList<UserRelationDto>();  
		    for(UserRelationDto userRelationDto:allUserRelationDto){  
		    	if(!tempList.contains(userRelationDto)){  
		            tempList.add(userRelationDto);  
		        } 
		    } 
		    String signLoginIdS="";
		    for (UserRelationDto userRelation : tempList) {
		    	signLoginIdS+="'"+userRelation.getLoginId()+"',";
		    }
		    signLoginIdS=signLoginIdS.substring(0,signLoginIdS.length()-1);
		    
		    enterpriseSearchDto.setSignLoginId(signLoginIdS);

		    enterpriseList = enterpriseService.queryEnterprise(enterpriseSearchDto);
			enterpriseListCount = enterpriseService.queryEnterpriseCount(enterpriseSearchDto);
		}else{
			enterpriseList = enterpriseService.queryEnterprise(enterpriseSearchDto);
			enterpriseListCount = enterpriseService.queryEnterpriseCount(enterpriseSearchDto);
		}
		
		page.setResult(enterpriseList);
		page.setTotalCount(enterpriseListCount);
		
		MerchantStatusEnum[] merchantStatusEnum = MerchantStatusEnum.values();		
		Map<String, Object> dataMap = new Hashtable<String, Object>();
		dataMap.put("merchantStatusEnum",merchantStatusEnum);
		dataMap.put("page",page);
		return new ModelAndView(this.getSuccessView()).addAllObjects(dataMap).addObject("userRelation", relationDto);
	}
	
	
	private List<NodesDto> getLoginIdSubNodes() {
		//登录人的子节点
		String loginId = SessionUserHolderUtil.getLoginId();
		List<NodesDto> loginSubNodes = userRelationService.findAllSubLoginId(loginId);
		return loginSubNodes;
	} 
	
	private String [] convertNodesToString(List<NodesDto> loginSubNodes){
		if(null == loginSubNodes) return null;
		//String strs = "";
		String[] signIds = new String [loginSubNodes.size()];
		NodesDto nodesDto = null;
		for (int i = 0; i < loginSubNodes.size(); i++) {
			nodesDto = loginSubNodes.get(i);
			signIds[i] = nodesDto.getLoginId();
		}
//		for (NodesDto nodesDto : loginSubNodes) {
//			strs +=  "'"+ nodesDto.getLoginId() +"',";
//		}
//		if(strs.length() > 0)
//			return strs.substring(0,strs.length()-1);
		return signIds;
	}
	
	

	public void setEnterpriseService(IEnterpriseService enterpriseService) {
		this.enterpriseService = enterpriseService;
	}
	public void setiSignMessageService(ISignMessageService iSignMessageService) {
		this.iSignMessageService = iSignMessageService;
	}
}
