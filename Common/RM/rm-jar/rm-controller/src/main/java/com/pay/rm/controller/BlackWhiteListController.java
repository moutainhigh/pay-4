/**
 * 
 */
package com.pay.rm.controller;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.rm.blackwhitelist.dto.BlackWhiteListApprovalDto;
import com.pay.rm.blackwhitelist.dto.BlackWhiteListDto;
import com.pay.rm.blackwhitelist.dto.BlackWhiteListDtoDownload;
import com.pay.rm.blackwhitelist.dto.BusinessTypeDto;
import com.pay.rm.blackwhitelist.service.BlackWhiteListService;
import com.pay.util.CurrencyCodeEnum;
import com.pay.util.IPv4Util;
import com.pay.util.SpringControllerUtils;
import com.pay.util.StringUtil;

/**
 * @Description 黑白名单管理
 * @project ma-membermanager
 * @file BlackWhiteListController.java
 * @note <br>
 * @develop JDK1.6 + SpringSource 2.3.2
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2011-1-18 tianqing_wang Create
 */
public class BlackWhiteListController extends MultiActionController {
	private final Log log = LogFactory.getLog(BlackWhiteListController.class);
	
	private final String MATCH_TYPE_ENUM = "1,2,3,4";//匹配类型
	//业务类型= "1=卡号,2=邮箱,3=IP,4=国家,5=MAC地址,6=手机号,7=证件号,8=收货地址,9=账单地址,10=卡BIN段,11=IP地址段"
	private static Map<String,Integer> BUSINESS_TYPE_ENUM;
	static{
		BUSINESS_TYPE_ENUM = new HashMap<String,Integer>();
		for(int i = 1; i < 12; i++){
			BUSINESS_TYPE_ENUM.put(String.valueOf(i), i);
		}
	}
	private final String ZONE = "4";//区段
	private String businessTypeAddView;
	private String businessTypeSearch;
	private String businessTypeSearchList;
	private String businessTypeUpdateView;

	private String blackWhiteListAddView;
	private String blackWhiteListBatchAddView;
	private String blackWhiteListSearch;
	private String blackWhiteListSearchList;
	private String blackWhiteListUpdateView;

	private String blackWhiteListimportResult;//
	
	private String blackWhiteListUploadView;

	private BlackWhiteListService blackWhiteListService;

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return null;
	}
	
	public void setBlackWhiteListBatchAddView(String blackWhiteListBatchAddView) {
		this.blackWhiteListBatchAddView = blackWhiteListBatchAddView;
	}
	// businessType创建
	public ModelAndView businessTypeCteateView(HttpServletRequest request,
			HttpServletResponse response, BusinessTypeDto dto) throws Exception {
		return new ModelAndView(businessTypeAddView);
	}

	// businessType创建保存
	public ModelAndView businessTypeAddSave(HttpServletRequest request,
			HttpServletResponse response, BusinessTypeDto dto) throws Exception {
		String message = null;
		blackWhiteListService.createBusinessType(dto);
		message = "操作已成功";
		return new ModelAndView(businessTypeAddView).addObject("message",
				message);
	}

	// businessType搜索页面
	public ModelAndView businessTypeSearchView(HttpServletRequest request,
			HttpServletResponse response, BusinessTypeDto dto) throws Exception {
		return new ModelAndView(businessTypeSearch);
	}

	
	// businessType搜索列表页面
	@SuppressWarnings("unchecked")
	public ModelAndView businessTypeSearchList(HttpServletRequest request,
			HttpServletResponse response, BusinessTypeDto dto) throws Exception {
		Page page = PageUtils.getPage(request);
		List<BusinessTypeDto> info = blackWhiteListService
				.queryBusinessTypeList(dto, page);
		page.setResult(info);
		return new ModelAndView(businessTypeSearchList).addObject("page", page);
	}

	public ModelAndView checkIsExsit(HttpServletRequest request,
			HttpServletResponse response, BlackWhiteListDto dto)
			throws Exception {
		dto.setId(null);
		BlackWhiteListApprovalDto dto1 =  new BlackWhiteListApprovalDto();
		dto1.setBusinessTypeId(dto.getBusinessTypeId());
		dto1.setListType(dto.getListType());
		dto1.setPartType(dto.getPartType());
		dto1.setContent(dto.getContent());
		Integer intNumbers = blackWhiteListService.getBlackWhiteListApprovalService().checkInDatabase(dto1);
		if (intNumbers > 0) {
			response.getWriter().print(1);
		} else {
			response.getWriter().print(0);
		}
		return null;
	}

	// businessType删除
	public ModelAndView deleteBusinessType(HttpServletRequest request,
			HttpServletResponse response, BusinessTypeDto dto) throws Exception {
		blackWhiteListService.deleteBusinessType(dto);
		return new ModelAndView(businessTypeSearch);
	}

	// businessType修改页面
	public ModelAndView updateView(HttpServletRequest request,
			HttpServletResponse response, BusinessTypeDto dto) throws Exception {
		dto = blackWhiteListService.queryBusinessTypeById(dto);
		return new ModelAndView(businessTypeUpdateView).addObject("dto", dto);
	}

	// businessType修改保存
	public ModelAndView updateSave(HttpServletRequest request,
			HttpServletResponse response, BusinessTypeDto dto) throws Exception {
		String message = null;
		blackWhiteListService.updateBusinessTypeList(dto);
		message = "操作已成功";
		// Integer typeId = dto.getBusinessTypeId();
		BusinessTypeDto resultDto = blackWhiteListService
				.queryBusinessTypeById(dto);
		return new ModelAndView(businessTypeUpdateView).addObject("message",
				message).addObject("dto", resultDto);
	}

	// 黑白名单创建
	public ModelAndView blackWhiteListCteateView(HttpServletRequest request,
			HttpServletResponse response, BlackWhiteListDto dto)
			throws Exception {
		String listTypeStr = request.getParameter("listType");
		int listType=2;
		if(!StringUtil.isEmpty(listTypeStr)){
			listType = Integer.valueOf(listTypeStr);
		}
		return new ModelAndView(blackWhiteListAddView).addObject("listType",listType);
	}
	
	
	/**跳转到批量上传页面
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 * @throws Exception
	 */
	public ModelAndView blackWhiteListBatchCteateView(HttpServletRequest request,
			HttpServletResponse response, BlackWhiteListDto dto)
			throws Exception {
		String listTypeStr = request.getParameter("listType");
		int listType=2;
		if(!StringUtil.isEmpty(listTypeStr)){
			listType = Integer.valueOf(listTypeStr);
		}
		return new ModelAndView(blackWhiteListBatchAddView).addObject("listType",listType);
	}

	// 黑白名单创建保存
	public ModelAndView blackWhiteListAddSave(HttpServletRequest request,
			HttpServletResponse response, BlackWhiteListDto dto)
			throws Exception {
		logger.info("dto: "+dto);
		String message = null;
		String operator = SessionUserHolderUtil.getLoginId();
		if(dto.getBusinessTypeId()==null){
			message = "请选择业务类型";
			return new ModelAndView(blackWhiteListAddView).addObject("message",
					message).addObject("listType",dto.getListType());
		}
		if(dto.getPartType()==null){
			message = "请选择匹配类型";
			return new ModelAndView(blackWhiteListAddView).addObject("message",
					message).addObject("listType",dto.getListType());
		}
		if(dto.getBusinessTypeId()!=10&&dto.getBusinessTypeId()!=11){
			if(StringUtil.isEmpty(dto.getContent())){
				message = "请输入内容";
				return new ModelAndView(blackWhiteListAddView).addObject("message",
						message).addObject("listType",dto.getListType());
			}else{
				dto.setContent(dto.getContent().trim());
			}
		}
		//卡BIN段校验
		if(dto.getBusinessTypeId()==10){
			if(StringUtil.isEmpty(dto.getValue1())){
				message = "请输入起始卡BIN段";
				return new ModelAndView(blackWhiteListAddView).addObject("message",
						message).addObject("listType",dto.getListType());
			}
			if(StringUtil.isEmpty(dto.getValue2())){
				message = "请输入截止卡BIN段";
				return new ModelAndView(blackWhiteListAddView).addObject("message",
						message).addObject("listType",dto.getListType());
			}
			long start = Long.valueOf(dto.getValue1()).longValue();
			long end = Long.valueOf(dto.getValue2()).longValue();
			if(start>=end){
				message = "请输入正确的卡BIN段";
				return new ModelAndView(blackWhiteListAddView).addObject("message",
						message).addObject("listType",dto.getListType());
			}
		}
		//卡BIN段校验
		if(dto.getBusinessTypeId()==11){
			if(StringUtil.isEmpty(dto.getValue1())||dto.getValue1().endsWith(".")){
				message = "请输入正确的起始IP地址段";
				return new ModelAndView(blackWhiteListAddView).addObject("message",
						message).addObject("listType",dto.getListType());
			}
			if(StringUtil.isEmpty(dto.getValue2())||dto.getValue2().endsWith(".")){
				message = "请输入正确的截止IP地址段";
				return new ModelAndView(blackWhiteListAddView).addObject("message",
						message).addObject("listType",dto.getListType());
			}
			int start = IPv4Util.ipToInt(dto.getValue1());
			int end = IPv4Util.ipToInt(dto.getValue2());
			if(start>=end){
				message = "请输入正确的IP地址段";
				return new ModelAndView(blackWhiteListAddView).addObject("message",
						message).addObject("listType",dto.getListType());
			}
		}
		
		if(StringUtil.isEmpty(dto.getRemark())&&dto.getListType().intValue()==2){
			message = "请输入备注信息";
			return new ModelAndView(blackWhiteListAddView).addObject("message",
					message).addObject("listType",dto.getListType());
		}
		dto.setOperator(operator);
		Long id = blackWhiteListService.createBlackWhiteList(dto);
		if(id>0){
		    message = "添加成功";
		}else if(id==-2){
			message = "相同内容的记录已存在";
		}else if(id==-3){
			message = "该黑名单修改申请还没审核,暂时不能提交新的修改";
		}
		return new ModelAndView(blackWhiteListAddView).addObject("message",
				message).addObject("listType",dto.getListType());
	}

	// 黑白名单搜索页面
	public ModelAndView blackWhiteListSearchView(HttpServletRequest request,
			HttpServletResponse response, BlackWhiteListDto dto)
			throws Exception {
		String listTypeStr = request.getParameter("listType");
		int listType=2;
		if(!StringUtil.isEmpty(listTypeStr)){
			listType = Integer.valueOf(listTypeStr);
		}
		return new ModelAndView(blackWhiteListSearch).addObject("listType",listType);
	}

	// 黑白名单搜索列表页面
	@SuppressWarnings("unchecked")
	public ModelAndView blackWhiteListSearchList(HttpServletRequest request,
			HttpServletResponse response, BlackWhiteListDto dto)
			throws Exception {
		Page page = PageUtils.getPage(request);
		List<BlackWhiteListDto> info = blackWhiteListService
				.queryBlackWhiteList(dto, page);
		page.setResult(info);
		return new ModelAndView(blackWhiteListSearchList).addObject("page",
				page);
	}

	/**数据导出
	 * @param request
	 * @param response
	 * @param dto
	 * @return
	 */
	public ModelAndView downloadBlackWhite(HttpServletRequest request,
			HttpServletResponse response, BlackWhiteListDto dto) {
		List<BlackWhiteListDto> info = blackWhiteListService
				.queryBlackWhiteList(dto);
		List<BlackWhiteListDtoDownload> downloads=new ArrayList<BlackWhiteListDtoDownload>();
		for (BlackWhiteListDto blackWhiteListDto : info) {
			BlackWhiteListDtoDownload download=new BlackWhiteListDtoDownload(); 
			//名单类型
				switch (blackWhiteListDto.getListType()) {
				case 1:
						download.setListType("白名单");
					break;
				case 2:
						download.setListType("黑名单");
					break;
				default:
					break;
				}
			//业务类型ID
				switch (blackWhiteListDto.getBusinessTypeId()) {
				case 1:
					download.setBusinessTypeId("卡号");
					break;
				case 2:
					download.setBusinessTypeId("邮箱");
					break;
				case 3:
					download.setBusinessTypeId("IP");
					break;
				case 4:
					download.setBusinessTypeId("国家");
					break;
				case 5:
					download.setBusinessTypeId("MAC地址");
					break;
				case 6:
					download.setBusinessTypeId("手机号码");
					break;
				case 7:
					download.setBusinessTypeId("证件号码");
					break;
				default:
					break;
				}
				//分类
				switch (blackWhiteListDto.getPartType()) {
				case 1:
						download.setPartType("全匹配");;
					break;
				case 2:
						download.setPartType("部分匹配");
					break;
				default:
					break;
				}
				//有效性标志
				switch (blackWhiteListDto.getStatus()) {
				case 1:
						download.setStatus("启用");
					break;
				case 0:
						download.setStatus("停用");
					break;
				default:
					break;
				}
				download.setContent(blackWhiteListDto.getContent());
				downloads.add(download);	
		}
		try {
			String[] headers = new String[] { "内容", "名单类型", "业务类型ID", "分类",
					"有效性标志" };
			String[] fields = new String[] { "content", "listType", "businessTypeId",
					"partType", "status"};
			Workbook grid = SimpleExcelGenerator.generateGrid("黑 白 名 单 查 询",
					headers, fields, downloads);
			Date myDate = new Date();
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddhhmmss");
			String dd = sdf.format(myDate);
			response.setContentType("application/force-download");
			response.setContentType("applicationnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Disposition", "attachment;filename="
					+ dd + ".xls");
			grid.write(response.getOutputStream());
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	// 黑白名单删除
	public ModelAndView deleteBlackWhiteList(HttpServletRequest request,
			HttpServletResponse response, BlackWhiteListDto dto)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		String operator = SessionUserHolderUtil.getLoginId();
		
		if(StringUtil.isEmpty(dto.getRemark())){
			response.getWriter().print("请输入备注信息！");
			return null;
		}
		
		BlackWhiteListApprovalDto appDto = new BlackWhiteListApprovalDto();
		appDto.setBlackWhiteListId(dto.getId());
		appDto.setStatus(0);
		List<BlackWhiteListApprovalDto> list = blackWhiteListService.getBlackWhiteListApprovalService()
				.queryBlackWhiteListApprovalCheck(appDto);
		
		if(list!=null&&!list.isEmpty()){
			response.getWriter().print("该条记录已提交过删除申请，请不要重复提交！");
			return null;
		}
		dto.setOperator(operator);
		int rst = blackWhiteListService.deleteBlackWhiteList(dto);

		if(rst>0){
			response.getWriter().print("删除申请已提交！");
		}else{
			response.getWriter().print("删除申请提交失败！");
		}
		
		return null;
	}
	
	// 黑白名单删除
	public ModelAndView updateBlackWhiteList(HttpServletRequest request,
			HttpServletResponse response, BlackWhiteListDto dto)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		String operator = SessionUserHolderUtil.getLoginId();
		dto.setOperator(operator);
		dto.setUpdateDate(new Date());
		int rst = blackWhiteListService.updateBlackWhiteListStatus(dto);

		if(rst>0){
			response.getWriter().print("操作成功");
		}else{
			response.getWriter().print("操作失败");
		}
		
		return null;
	}

	// 黑白名单修改页面
	public ModelAndView updateBlackWhiteListView(HttpServletRequest request,
			HttpServletResponse response, BlackWhiteListDto dto)
			throws Exception {
		dto = blackWhiteListService.queryBlackWhiteById(dto);
		String listTypeStr = request.getParameter("listType");
		int listType=2;
		if(!StringUtil.isEmpty(listTypeStr)){
			listType = Integer.valueOf(listTypeStr);
		}
		return new ModelAndView(blackWhiteListUpdateView).addObject("dto", dto)
				.addObject("listType",listType);
	}

	// 黑白名单修改保存
	public ModelAndView updateBlackWhiteListSave(HttpServletRequest request,
			HttpServletResponse response, BlackWhiteListDto dto)
			throws Exception {
		String message = null;
		String operator = SessionUserHolderUtil.getLoginId();
		int listType = dto.getListType();
		if(dto.getBusinessTypeId()==null){
			message = "请选择业务类型";
			return new ModelAndView(blackWhiteListAddView).addObject("message",
					message).addObject("listType",listType);
		}
		if(dto.getPartType()==null){
			message = "请选择匹配类型";
			return new ModelAndView(blackWhiteListAddView).addObject("message",
					message).addObject("listType",listType);
		}
		if(dto.getBusinessTypeId()!=10&&dto.getBusinessTypeId()!=11){
			if(StringUtil.isEmpty(dto.getContent())){
				message = "请输入内容";
				return new ModelAndView(blackWhiteListAddView).addObject("message",
						message).addObject("listType",listType);
			}else{
				dto.setContent(dto.getContent().trim());
			}
		}
		//卡BIN段校验
		if(dto.getBusinessTypeId()==10){
			if(StringUtil.isEmpty(dto.getValue1())){
				message = "请输入起始卡BIN段";
				return new ModelAndView(blackWhiteListAddView).addObject("message",
						message).addObject("listType",listType);
			}
			if(StringUtil.isEmpty(dto.getValue2())){
				message = "请输入截止卡BIN段";
				return new ModelAndView(blackWhiteListAddView).addObject("message",
						message).addObject("listType",listType);
			}
			long start = Long.valueOf(dto.getValue1()).longValue();
			long end = Long.valueOf(dto.getValue2()).longValue();
			if(start>=end){
				message = "请输入正确的卡BIN段";
				return new ModelAndView(blackWhiteListAddView).addObject("message",
						message).addObject("listType",listType);
			}
		}
		//卡BIN段校验
		if(dto.getBusinessTypeId()==11){
			if(StringUtil.isEmpty(dto.getValue1())||dto.getValue1().endsWith(".")){
				message = "请输入正确的起始IP地址段";
				return new ModelAndView(blackWhiteListAddView).addObject("message",
						message).addObject("listType",listType);
			}
			if(StringUtil.isEmpty(dto.getValue2())||dto.getValue2().endsWith(".")){
				message = "请输入正确的截止IP地址段";
				return new ModelAndView(blackWhiteListAddView).addObject("message",
						message).addObject("listType",listType);
			}
			int start = IPv4Util.ipToInt(dto.getValue1());
			int end = IPv4Util.ipToInt(dto.getValue2());
			if(start>=end){
				message = "请输入正确的IP地址段";
				return new ModelAndView(blackWhiteListAddView).addObject("message",
						message).addObject("listType",listType);
			}
		}
		
		if(StringUtil.isEmpty(dto.getRemark())&&dto.getListType().intValue()==2){
			message = "请输入备注信息";
			return new ModelAndView(blackWhiteListAddView).addObject("message",
					message).addObject("listType",listType);
		}
		
		dto.setCreateDate(new Date());
		dto.setOperator(operator);
		dto.setStatus(0);
		if(!StringUtil.isEmpty(dto.getValue1())){
			dto.setContent(dto.getValue1());
		}
		if(!StringUtil.isEmpty(dto.getValue2())){
			dto.setContent(" 至  "+dto.getValue2());
		}
		
		if(listType==1){//白名单直接修改
			dto.setStatus(1);
			int rst = blackWhiteListService.updateBlackWhiteList(dto);
			if(rst>0){
				   message = "修改成功";
				}else{
				   message = "修改失败";
			}
		}else{
			BlackWhiteListApprovalDto approvalDto = new BlackWhiteListApprovalDto();
			approvalDto.setBlackWhiteListId(dto.getId());
			approvalDto.setApprovalType(3);
			approvalDto.setStatus(0);
			List<BlackWhiteListApprovalDto> list = blackWhiteListService.getBlackWhiteListApprovalService()
					.queryBlackWhiteListApprovalCheck(approvalDto);
			
			if(list!=null&&!list.isEmpty()){
				message = "该黑名单已存在申请已存在,请不要重复提交";
				return new ModelAndView(blackWhiteListAddView).addObject("message",
						message).addObject("listType",listType);
			}
			int rst = blackWhiteListService.updateBlackWhiteList(dto);
			if(rst>0){
			   message = "修改申请成功";
			}else{
			   message = "修改申请失败";
			}
		}
		
		BlackWhiteListDto resultDto = blackWhiteListService
				.queryBlackWhiteById(dto);
		return new ModelAndView(blackWhiteListUpdateView).addObject("message",
				message).addObject("dto", resultDto).addObject("listType",listType);
	}

	// businessType异步校验
	public ModelAndView checkBusinessTypeId(HttpServletRequest request,
			HttpServletResponse response, BusinessTypeDto dto) throws Exception {
		BusinessTypeDto resultDto = blackWhiteListService
				.queryBusinessTypeById(dto);
		if (null != resultDto) {
			SpringControllerUtils.renderText(response, "1");
		}
		return null;
	}

	// businessType异步校验
	@SuppressWarnings("unchecked")
	public ModelAndView checkMemberCode(HttpServletRequest request,
			HttpServletResponse response, BlackWhiteListDto dto)
			throws Exception {
		Map paramMap = new HashMap();
		paramMap.put("memberCode", dto.getMemberCode());
		// PersonMemberInfoDto resultDto =
		// personMemberService.selectMemberByMemberCode(paramMap);
		// if (null == resultDto) {
		// SpringControllerUtils.renderText(response, "0");
		// }
		return null;
	}

	/**
	 * 跳转上传页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView uploadInit(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		Map paramMap = new HashMap();

		return new ModelAndView(blackWhiteListUploadView);
	}

	/**
	 * Excel文件上传
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView upload(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		response.setCharacterEncoding("UTF-8");
		DefaultMultipartHttpServletRequest dmRequest = (DefaultMultipartHttpServletRequest) request;

		MultipartFile multiPartFile = dmRequest.getFile("file");

		if (multiPartFile != null) {
			InputStream fis = null;
			InputStreamReader isw = null;
			BufferedReader br = null;
			try {
				// 处理数据
				fis = multiPartFile.getInputStream();
				isw = new InputStreamReader(fis, "GBK");
				br = new BufferedReader(isw);

				// 读取第一行表头，不处理
				String line = br.readLine();
				int successCount = 0;// 成功处理数目
				int totalLine = 1;// 总数目
				List<BlackWhiteListApprovalDto> blackWhiteListDtos = new ArrayList<BlackWhiteListApprovalDto>();
				BlackWhiteListApprovalDto blackWhiteListDto;
				//此map是做Excel中数据是否重复的
				Map<String, Integer> map = new HashMap<String, Integer>();
				//存放错误行信息
				List<Map<String,String>> errorList = new ArrayList<Map<String,String>>();
				
				while ((line = br.readLine()) != null) {
					totalLine++;//不管此行有无数据，都自增1
					if (line.trim().isEmpty()) {
						continue;
					}
					/*
					 * 获取的每行数据以逗号隔开
					 */
					String[] arrStrings = line.split(",");
					int len = arrStrings.length;
					if (len < 5) {
						errorList.add(this.getErrorMap(arrStrings, totalLine, "请输入操作备注内容"));
						continue;
					}else if(len > 5){
						errorList.add(this.getErrorMap(arrStrings, totalLine, "数据非法"));
						continue;
					} else {
						String businessType = arrStrings[0];//业务类型
						String matchType = arrStrings[1];//匹配类型
						String content1 = arrStrings[2];//内容1
						String content2 = arrStrings[3];;//内容2
						String remark = arrStrings[4];//备注
						if(!this.parameterCheckout(businessType)){
							errorList.add(this.getErrorMap(arrStrings, totalLine, "业务类型必须为数字"));
							continue;
						}
						if(StringUtils.isBlank(businessType) || BUSINESS_TYPE_ENUM.get(businessType) == null){
							errorList.add(this.getErrorMap(arrStrings, totalLine, "请输入正确业务类型"));
							continue;
						}
						
						if(!this.parameterCheckout(matchType)){
							errorList.add(this.getErrorMap(arrStrings, totalLine, "匹配类型必须为数字"));
							continue;
						}
						if(StringUtils.isBlank(matchType) || !MATCH_TYPE_ENUM.contains(matchType)){
							errorList.add(this.getErrorMap(arrStrings, totalLine, "请输入正确匹配类型"));
							continue;
						}else{//校验业务类型和匹配类型是否匹配
							if(!bTypeIsMatchMType(businessType, matchType)){
								errorList.add(this.getErrorMap(arrStrings, totalLine, "请输入正确匹配类型"));
								continue;
							}
						}
						if(StringUtils.isBlank(content1)){
							errorList.add(this.getErrorMap(arrStrings, totalLine, "请输入正确数据内容"));
							continue;
						}
						if(StringUtils.isBlank(remark)){
							errorList.add(this.getErrorMap(arrStrings, totalLine, "请输入操作备注内容"));
							continue;
						}
						StringBuilder sb = new StringBuilder(businessType);
						sb.append("-");
						sb.append(matchType);
						sb.append("-");
						sb.append(content1);
						StringBuilder content = new StringBuilder(content1);
						if(ZONE.equals(matchType)){//区段时，要判断content2是否存在
							if(StringUtils.isBlank(content2)){
								errorList.add(this.getErrorMap(arrStrings, totalLine, "请输入正确数据内容"));
								continue;
							}else{
								//content2格式校验
								if(!this.matchRule(businessType, content2)){
									errorList.add(this.getErrorMap(arrStrings, totalLine, "内容格式错误"));
									continue;
								}
								//做区间段的比较
								if(!this.c1Compared2c2(content1, content2)){
									errorList.add(this.getErrorMap(arrStrings, totalLine, "请输入正确数据内容"));
									continue;
								}
								content.append(" 至 ");
								content.append(content2);
							}
							sb.append("-");
							sb.append(content2);
						}
						//内容格式校验
						if(!this.matchRule(businessType, content1)){
							errorList.add(this.getErrorMap(arrStrings, totalLine, "内容格式错误"));
							continue;
						}
						String key = sb.toString();
						if(!map.containsKey(key))
							map.put(key,totalLine);
						else{
							errorList.add(this.getErrorMap(arrStrings, totalLine, "第"+totalLine+"行和第"+map.get(key)+"行数据相同"));
							continue;
						}
						Date date = new Date();
						blackWhiteListDto = new BlackWhiteListApprovalDto();
						blackWhiteListDto.setBusinessTypeId(Integer.valueOf(businessType));
						blackWhiteListDto.setContent(content.toString());
						blackWhiteListDto.setCreateDate(date);
						blackWhiteListDto.setListType(2);//默认是黑名单
						blackWhiteListDto.setPartType(Integer.valueOf(matchType));//匹配类型
						blackWhiteListDto.setStatus(0);//默认是待审核
						blackWhiteListDto.setUpdateDate(date);
						Integer checkNums = blackWhiteListService.getBlackWhiteListApprovalService().checkInDatabase(blackWhiteListDto);
						if(checkNums >= 10){
							errorList.add(this.getErrorMap(arrStrings, totalLine, "已添加，待审核"));
							continue;
						}else if(checkNums > 0){
							errorList.add(this.getErrorMap(arrStrings, totalLine, "该黑名单已存在"));
							continue;
						}
						blackWhiteListDto.setApprovalType(1);//审批类型默认是新增
						blackWhiteListDto.setRemark(remark);
						blackWhiteListDtos.add(blackWhiteListDto);
					}
					successCount++;
				}
				int flag = 0;
				if(errorList.size() > 0){
					flag = 1;
				}else{
					if (!blackWhiteListService.getBlackWhiteListApprovalService()
							.createBlackWhiteListApproval(blackWhiteListDtos)) {
						flag = 2;
					} else {
						flag = 3;
					}
				}
				return new ModelAndView(blackWhiteListimportResult).addObject(
						"flag", flag).addObject("listType",2).addObject("errorList", errorList).addObject("successCount", successCount);
			} catch (Exception k) {
				k.printStackTrace();
			} finally {
				br.close();
				isw.close();
				fis.close();
			}
		}

		return new ModelAndView(blackWhiteListimportResult).addObject(
				"flag", 2).addObject("listType",2);
	}

	/**
	 * 模板下载
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView downLoadFile(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		request.setCharacterEncoding("UTF-8");

		String path = getServletContext().getRealPath(
				"/WEB-INF/jsp/blackwhitelist");

		String fileName = "blackListTemplate.csv";
		FileInputStream fi = new FileInputStream(path + "//" + fileName);
		byte[] bt = new byte[fi.available()];
		fi.read(bt);
		response.setContentType("application/msdownload;charset=UTF-8");

		// System.out.print(response.getContentType());
		response.setCharacterEncoding("UTF-8");
		fileName = java.net.URLEncoder.encode(fileName, "UTF-8");
		response.setHeader("Content-Disposition", "attachment; filename="
				+ new String(fileName.getBytes("UTF-8"), "GBK"));
		response.setContentLength(bt.length);
		ServletOutputStream sos = response.getOutputStream();
		sos.write(bt);
		return null;
	}
	
	/**将错误行的信息转换成map
	 * @param args
	 * @return
	 */
	private Map<String,String> getErrorMap(String[] args,int lineNum,String errorMsg){
		Map<String,String> map = new HashMap<String,String>();
		map.put("lineNum", String.valueOf(lineNum));
		for(int i = 0,len = args.length; i < len;i++){
			map.put(String.valueOf(i), args[i]);
		}
		map.put("errorMsg", errorMsg);
		return map;
	}
	
	/**区间两个端点的比较，必须是c1<c2
	 * @param c1 区间开始
	 * @param c2 区间结束
	 * @return
	 */
	private boolean c1Compared2c2(String c1,String c2){
		int result = c1.compareToIgnoreCase(c2);
		if(result >= 0){return false;}
		return true;
	}

	/**校验交易类型和匹配类型是否匹配
	 * @param businessType
	 * @param matchType
	 * @return
	 */
	private boolean bTypeIsMatchMType(String businessType,String matchType ){
		//国家、收货地址、账单地址必须是全匹配
		if("4".equals(businessType) || "8".equals(businessType) || "9".equals(businessType)){
			if(!"1".equals(matchType)){return false;}
		}
		//卡BIN段、IP段必须是区段,其他的一定为非区段
		if("10".equals(businessType) || "11".equals(businessType)){
			if(!"4".equals(matchType)){return false;}
		}else{
			if("4".equals(matchType)){return false;}
		}
		return true;
	}
	
	/**根据业务类型做内容的规则匹配
	 * @param businessType
	 * @param content
	 * @return boolean true=匹配交易通过
	 */
	private boolean matchRule(String businessType,String content){
		int bs = Integer.parseInt(businessType);
		boolean result=false;
		Pattern pattern = null;
		switch (bs) {
			//卡号和卡BIN只能是1-21位的数字
			case 1:
			case 10:
				pattern = Pattern.compile("^\\d{1,21}$");
				Matcher matcher = pattern.matcher(content);
				result= matcher.matches(); 
				break;
			//邮箱
			case 2:
				pattern = Pattern.compile("^\\w+([-+.]\\w+)*@\\w+([-.]\\w+)*\\.\\w+([-.]\\w+)*$");
				Matcher matcher2 = pattern.matcher(content);
				result= matcher2.matches(); 
				break;
			//IP
			case 3:
			case 11:
				pattern = Pattern.compile("([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}");
				Matcher matcher3 = pattern.matcher(content);
				result= matcher3.matches(); 
				break;
			//国家码
			case 4:
//				pattern = Pattern.compile("[A-Z]{3}");
//				Matcher matcher4 = pattern.matcher(content);
//				result= matcher4.matches(); 
				result = CurrencyCodeEnum.isExists(content);
				break;
			//卡BIN
			default:
				result=true;
				break;
		}
		return result;
	}
	
	/**参数不为空时，必须时1-2位的数字
	 * @param paramter
	 * @return
	 */
	private boolean parameterCheckout(String paramter){
		if(StringUtils.isBlank(paramter)){
			return true;
		}
		Pattern pattern =Pattern.compile("^\\d{1,2}$");
		Matcher matcher10 = pattern.matcher(paramter);
		return matcher10.matches();
	}
	
	public String getBusinessTypeAddView() {
		return businessTypeAddView;
	}

	public void setBusinessTypeAddView(String businessTypeAddView) {
		this.businessTypeAddView = businessTypeAddView;
	}

	public String getBusinessTypeSearch() {
		return businessTypeSearch;
	}

	public void setBusinessTypeSearch(String businessTypeSearch) {
		this.businessTypeSearch = businessTypeSearch;
	}

	public String getBusinessTypeSearchList() {
		return businessTypeSearchList;
	}

	public void setBusinessTypeSearchList(String businessTypeSearchList) {
		this.businessTypeSearchList = businessTypeSearchList;
	}

	public String getBlackWhiteListAddView() {
		return blackWhiteListAddView;
	}

	public void setBlackWhiteListAddView(String blackWhiteListAddView) {
		this.blackWhiteListAddView = blackWhiteListAddView;
	}

	public String getBlackWhiteListSearch() {
		return blackWhiteListSearch;
	}

	public void setBlackWhiteListSearch(String blackWhiteListSearch) {
		this.blackWhiteListSearch = blackWhiteListSearch;
	}

	public String getBlackWhiteListSearchList() {
		return blackWhiteListSearchList;
	}

	public void setBlackWhiteListSearchList(String blackWhiteListSearchList) {
		this.blackWhiteListSearchList = blackWhiteListSearchList;
	}

	public BlackWhiteListService getBlackWhiteListService() {
		return blackWhiteListService;
	}

	public void setBlackWhiteListService(
			BlackWhiteListService blackWhiteListService) {
		this.blackWhiteListService = blackWhiteListService;
	}

	public String getBusinessTypeUpdateView() {
		return businessTypeUpdateView;
	}

	public void setBusinessTypeUpdateView(String businessTypeUpdateView) {
		this.businessTypeUpdateView = businessTypeUpdateView;
	}

	public String getBlackWhiteListUpdateView() {
		return blackWhiteListUpdateView;
	}

	public void setBlackWhiteListUpdateView(String blackWhiteListUpdateView) {
		this.blackWhiteListUpdateView = blackWhiteListUpdateView;
	}

	public void setBlackWhiteListUploadView(String blackWhiteListUploadView) {
		this.blackWhiteListUploadView = blackWhiteListUploadView;
	}
	
	public String getBlackWhiteListimportResult() {
		return blackWhiteListimportResult;
	}

	public void setBlackWhiteListimportResult(String blackWhiteListimportResult) {
		this.blackWhiteListimportResult = blackWhiteListimportResult;
	}

	public static void main(String[] args){
//		String c1 = "172.168.1.1";
//		String c2 = "172.168.2.1";
//		String i1 = "123";
//		String i2 = "121";
//		String a1 = "123";
//		String a2 = "123";		
//		System.out.println(c1.compareTo(c2));
//		System.out.println(i1.compareTo(i2));
//		System.out.println(a1.compareTo(a2));
		 Pattern pattern = Pattern.compile("^\\d{1,2}$");
		 Matcher matcher = pattern.matcher("1");
		 boolean b= matcher.matches();
		 System.out.println(b);
//		String s = "1,2,ni,,a";
//		String[] ss = s.split(",");
//		System.out.println(ss);
	}
}
