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

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import com.pay.rm.blackwhitelist.service.BlackWhiteListService;
import com.pay.util.StringUtil;

/**
 * @Description 黑白名单审核管理
 * @project ma-membermanager
 * @file BlackWhiteListController.java
 * @note <br>
 * @develop JDK1.6 + SpringSource 2.3.2
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有 Date
 *          Author Changes 2011-1-18 tianqing_wang Create
 */
public class BlackWhiteListApprovalController extends MultiActionController {
	private final Log log = LogFactory.getLog(BlackWhiteListApprovalController.class);

	private String blackWhiteListApprovalAddView;
	private String blackWhiteListApprovalBatchAddView;
	private String blackWhiteListApprovalSearch;
	private String blackWhiteListApprovalSearchList;
	private String blackWhiteListApprovalUpdateView;

	private String blackWhiteListApprovalUploadView;

	private BlackWhiteListService blackWhiteListService;

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		return null;
	}

	public ModelAndView checkIsExsit(HttpServletRequest request,
			HttpServletResponse response, BlackWhiteListApprovalDto dto)
			throws Exception {
		Page<BlackWhiteListApprovalDto> page = PageUtils.getPage(request);
		Long id = dto.getId();
		dto.setId(null);
		List<BlackWhiteListApprovalDto> info = blackWhiteListService.getBlackWhiteListApprovalService()
				.queryBlackWhiteListApprovalPage(dto, page);
		if (info != null && info.size() > 0) {
			if (id != null) {
				for (BlackWhiteListApprovalDto blackWhiteListDto : info) {
					if (blackWhiteListDto.getId().longValue() != id.longValue()) {
						response.getWriter().print(1);
						break;
					}
				}
			} else {
				response.getWriter().print(1);
			}
		} else {
			response.getWriter().print(0);
		}
		return null;
	}

	// 黑白名单创建
	public ModelAndView blackWhiteListApprovalCteateView(HttpServletRequest request,
			HttpServletResponse response, BlackWhiteListDto dto)
			throws Exception {
		return new ModelAndView(blackWhiteListApprovalAddView);
	}
	
	// 黑白名单创建
	public ModelAndView blackWhiteListApprovalBatchCteateView(HttpServletRequest request,
			HttpServletResponse response, BlackWhiteListDto dto)
			throws Exception {
		return new ModelAndView(blackWhiteListApprovalBatchAddView);
	}
	
	//黑白名单审核
	public ModelAndView approval(HttpServletRequest request,
			HttpServletResponse response)
			throws Exception {
		String operator = SessionUserHolderUtil.getLoginId();
		String ids = request.getParameter("ids");
		String remark = request.getParameter("remark");
		String approvalFlg = request.getParameter("approvalFlg");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		
		if(StringUtil.isEmpty(remark)){
			response.getWriter().print("请输入备注信息！");
			return null;
		}
		int rst = blackWhiteListService.approvalBlackWhiteList(ids, remark,
				                                    approvalFlg,operator);
		if(rst==1){
			response.getWriter().print("审核成功！");
			return null;	
		}else{
			response.getWriter().print("审核失败，或部分失败");
		}
		
		return null;
	}
	
	//黑白名单审核
	public ModelAndView count(HttpServletRequest request,
			HttpServletResponse response,BlackWhiteListApprovalDto dto)
			throws Exception {
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");		
	    int count=  blackWhiteListService.getBlackWhiteListApprovalService().countAmount(dto);
		response.getWriter().print(count);
		return null;
	}

	// 黑白名单创建保存
	public ModelAndView blackWhiteListApprovalAddSave(HttpServletRequest request,
			HttpServletResponse response, BlackWhiteListApprovalDto dto)
			throws Exception {
		String message = null;
		String operator = SessionUserHolderUtil.getLoginId();
		dto.setCreateDate(new Date());
		dto.setOperator(operator);
		dto.setStatus(0);
		if(!StringUtil.isEmpty(dto.getValue1())){
			dto.setContent(dto.getValue1());
		}
		if(!StringUtil.isEmpty(dto.getValue2())){
			dto.setContent(" 至  "+dto.getValue2());
		}
		Long id = blackWhiteListService.getBlackWhiteListApprovalService().createBlackWhiteListApproval(dto);
		if(id>0){
		    message = "添加成功";
		}else{
			message = "添加失败";
		}
		return new ModelAndView(blackWhiteListApprovalAddView).addObject("message",
				message);
	}

	// 黑白名单搜索页面
	public ModelAndView blackWhiteListApprovalSearchView(HttpServletRequest request,
			HttpServletResponse response, BlackWhiteListApprovalDto dto)
			throws Exception {
		return new ModelAndView(blackWhiteListApprovalSearch).addObject("dto", dto);
	}

	// 黑白名单搜索列表页面
	public ModelAndView blackWhiteListApprovalSearchList(HttpServletRequest request,
			HttpServletResponse response, BlackWhiteListApprovalDto dto)
			throws Exception {
		Page<BlackWhiteListApprovalDto> page = PageUtils.getPage(request);
		List<BlackWhiteListApprovalDto> info = blackWhiteListService.getBlackWhiteListApprovalService()
				.queryBlackWhiteListApprovalPage(dto,page);
		page.setResult(info);
		return new ModelAndView(blackWhiteListApprovalSearchList).addObject("page",
				page);
	}

	public ModelAndView downloadBlackWhite(HttpServletRequest request,
			HttpServletResponse response, BlackWhiteListApprovalDto dto) {
		List<BlackWhiteListApprovalDto> info = blackWhiteListService.getBlackWhiteListApprovalService()
				.queryBlackWhiteListApproval(dto);
		List<BlackWhiteListDtoDownload> downloads=new ArrayList<BlackWhiteListDtoDownload>();
		for (BlackWhiteListApprovalDto blackWhiteListDto : info) {
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
	public ModelAndView deleteBlackWhiteListApproval(HttpServletRequest request,
			HttpServletResponse response, BlackWhiteListApprovalDto dto)
			throws Exception {
		blackWhiteListService.getBlackWhiteListApprovalService().deleteBlackWhiteListApproval(dto);
		return new ModelAndView(blackWhiteListApprovalSearch);
	}

	// 黑白名单修改页面
	public ModelAndView updateBlackWhiteListApprovalView(HttpServletRequest request,
			HttpServletResponse response, BlackWhiteListApprovalDto dto)
			throws Exception {
		dto = blackWhiteListService.getBlackWhiteListApprovalService().queryBlackWhiteListApprovalById(dto);
		return new ModelAndView(blackWhiteListApprovalUpdateView).addObject("dto", dto);
	}

	// 黑白名单修改保存
	public ModelAndView updateBlackWhiteListApprovalSave(HttpServletRequest request,
			HttpServletResponse response, BlackWhiteListApprovalDto dto)
			throws Exception {
		String message = null;
		blackWhiteListService.getBlackWhiteListApprovalService().updateBlackWhiteListApproval(dto);
		message = "操作已成功";
		// Integer typeId = dto.getBusinessTypeId();
		BlackWhiteListApprovalDto resultDto = blackWhiteListService.getBlackWhiteListApprovalService()
				.queryBlackWhiteListApprovalById(dto);
		return new ModelAndView(blackWhiteListApprovalUpdateView).addObject("message",
				message).addObject("dto", resultDto);
	}

	// businessType异步校验
	@SuppressWarnings("unchecked")
	public ModelAndView checkMemberCode(HttpServletRequest request,
			HttpServletResponse response, BlackWhiteListDto dto)
			throws Exception {
		Map paramMap = new HashMap();
		paramMap.put("memberCode", dto.getMemberCode());
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
		return new ModelAndView(blackWhiteListApprovalUploadView);
	}

	/**
	 * 
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

				// 读取直到最后一行
				String line = br.readLine();
				int successCount = 0;// 成功处理数目
				int totalLine = 0;// 总数目
				List<BlackWhiteListApprovalDto> blackWhiteListDtos = new ArrayList<BlackWhiteListApprovalDto>();
				BlackWhiteListApprovalDto blackWhiteListDto;
				// JSONObject json = new JSONObject();

				Map<String, Integer> map = new HashMap<String, Integer>();
				boolean failure = false;
				StringBuilder errMsg =  new StringBuilder();
				while ((line = br.readLine()) != null) {
					if (line.trim().isEmpty()) {
						continue;
					}
					totalLine++;
					String[] arrStrings = line.split(",");

					if (arrStrings.length != 5) {
						failure  = true;
						errMsg.append("第" + totalLine + "行数据格式不正确").append("\n");
					} else if (arrStrings.length == 5 && !arrStrings[0].isEmpty()) {

						String businessType = arrStrings[1];
						String content = arrStrings[0];
						String listType = arrStrings[4];
						String partType = arrStrings[2];
						String status = arrStrings[3];

						String key = businessType + "-" + listType + "-" + partType + "-" + content;
						if(!map.containsKey(key))
							map.put(key,totalLine);
						else{
							failure = true;
							errMsg.append("第" + totalLine + "行和第" + map.get(key) + "行重复数据").append("\n");
							continue;
						}
						blackWhiteListDto = new BlackWhiteListApprovalDto();
						blackWhiteListDto.setBusinessTypeId(Integer
								.valueOf(businessType));
						blackWhiteListDto.setContent(content);
						blackWhiteListDto.setCreateDate(new Date());
						blackWhiteListDto
								.setListType(Integer.valueOf(listType));
						blackWhiteListDto
								.setPartType(Integer.valueOf(partType));
						blackWhiteListDto.setStatus(Integer.valueOf(status));
						blackWhiteListDto.setUpdateDate(new Date());
						Integer checkNums = blackWhiteListService.getBlackWhiteListApprovalService().checkInDatabase(blackWhiteListDto);
						if(checkNums > 10){
							failure = true;
							errMsg.append("第" + totalLine + "行已存在相同配置在审核").append("\n");
						}else if(checkNums > 0){
							failure = true;
							errMsg.append("第" + totalLine + "行已存在相同配置在黑白名单").append("\n");
						}
						blackWhiteListDtos.add(blackWhiteListDto);
					}
				}
				if(failure){
					String errormsg = errMsg.toString();
					return new ModelAndView(blackWhiteListApprovalSearch).addObject(
							"errorMsg", errormsg);
				}else{
					if (!blackWhiteListService.getBlackWhiteListApprovalService()
							.createBlackWhiteListApproval(blackWhiteListDtos)) {
						String errormsg = "上传失败";
						return new ModelAndView(blackWhiteListApprovalSearch).addObject(
								"errorMsg", errormsg);
					} else {
						return new ModelAndView(blackWhiteListApprovalSearch).addObject(
								"errorMsg", "上传完成");
					}
				}
			} catch (Exception k) {
				k.printStackTrace();
			} finally {
				br.close();
				isw.close();
				fis.close();
			}
		}

		return new ModelAndView(blackWhiteListApprovalSearch);
	}

	/**
	 * 
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
	public void setBlackWhiteListApprovalUploadView(
			String blackWhiteListApprovalUploadView) {
		this.blackWhiteListApprovalUploadView = blackWhiteListApprovalUploadView;
	}

	public void setBlackWhiteListApprovalAddView(
			String blackWhiteListApprovalAddView) {
		this.blackWhiteListApprovalAddView = blackWhiteListApprovalAddView;
	}
	public void setBlackWhiteListApprovalBatchAddView(
			String blackWhiteListApprovalBatchAddView) {
		this.blackWhiteListApprovalBatchAddView = blackWhiteListApprovalBatchAddView;
	}
	public void setBlackWhiteListApprovalSearch(String blackWhiteListApprovalSearch) {
		this.blackWhiteListApprovalSearch = blackWhiteListApprovalSearch;
	}

	public void setBlackWhiteListApprovalSearchList(
			String blackWhiteListApprovalSearchList) {
		this.blackWhiteListApprovalSearchList = blackWhiteListApprovalSearchList;
	}

	public void setBlackWhiteListApprovalUpdateView(
			String blackWhiteListApprovalUpdateView) {
		this.blackWhiteListApprovalUpdateView = blackWhiteListApprovalUpdateView;
	}

	public void setBlackWhiteListService(BlackWhiteListService blackWhiteListService) {
		this.blackWhiteListService = blackWhiteListService;
	}
}
