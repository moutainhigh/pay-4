package com.pay.poss.controller.fi.dcc;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;

import org.apache.commons.collections.CollectionUtils;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.support.DefaultMultipartHttpServletRequest;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.member.dto.EnterpriseBaseDto;
import com.pay.acc.member.service.EnterpriseBaseService;
import com.pay.dcc.model.PartnerDCCConfig;
import com.pay.dcc.service.ConfigurationDCCService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.pe.dto.CurrencyDTO;
import com.pay.pe.service.currency.CurrencyService;
import com.pay.poss.security.model.SessionUserHolder;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.ExcelUtils;
import com.pay.util.JSonUtil;

/**
 * 
 *  File: ConfigurationDCCController.java
 *  Description:
 *  Copyright 2016-2030 IPAYLINKS Corporation. All rights reserved.
 *  Date      Author      Changes
 *  2016年4月20日   mmzhang     Changes
 *
 */
public class ConfigurationDCCController extends MultiActionController {
	
	private String dccList;
	private String upload;
	public String getUpload() {
		return upload;
	}

	public void setUpload(String upload) {
		this.upload = upload;
	}

	public String getDccListLoad() {
		return dccListLoad;
	}

	private String dccListLoad;
	public void setDccListLoad(String dccListLoad) {
		this.dccListLoad = dccListLoad;
	}

	public String getDccList() {
		return dccList;
	}

	public void setDccList(String dccList) {
		this.dccList = dccList;
	}

	private String dcc;
	private String saveDCC;
	private String configurationDCC;
	private String bulkEditingDCC;
	private String detailsDCC;
	private String updateDCC;
	private ConfigurationDCCService configurationDCCService;
	private EnterpriseBaseService enterpriseBaseService;
	private CurrencyService currencyService;

	public void setBulkEditingDCC(String bulkEditingDCC) {
		this.bulkEditingDCC = bulkEditingDCC;
	}

	public void setCurrencyService(CurrencyService currencyService) {
		this.currencyService = currencyService;
	}

	public void setUpdateDCC(String updateDCC) {
		this.updateDCC = updateDCC;
	}

	public void setDetailsDCC(String detailsDCC) {
		this.detailsDCC = detailsDCC;
	}

	public void setEnterpriseBaseService(
			EnterpriseBaseService enterpriseBaseService) {
		this.enterpriseBaseService = enterpriseBaseService;
	}

	public void setConfigurationDCCService(
			ConfigurationDCCService configurationDCCService) {
		this.configurationDCCService = configurationDCCService;
	}

	public void setConfigurationDCC(String configurationDCC) {
		this.configurationDCC = configurationDCC;
	}

	public void setSaveDCC(String saveDCC) {
		this.saveDCC = saveDCC;
	}

	public void setDcc(String dcc) {
		this.dcc = dcc;
	}

	//用于保存查询时的参数，在修改等操作返回时，返回上一次的查询的页面。
	private String queryPartnerId = "";
	private String queryPartnerName = "";
	private String queryCurrencyCode = "";
	
	//dcc主页面
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		
		queryPartnerId = (queryPartnerId==null?"":queryPartnerId);
		queryPartnerName = (queryPartnerName==null?"":queryPartnerName);
		queryCurrencyCode = (queryCurrencyCode==null?"":queryCurrencyCode);
		
		PartnerDCCConfig dccConfig = new PartnerDCCConfig();
		dccConfig.setPartnerId(queryPartnerId);
		dccConfig.setPartnerName(queryPartnerName);
		dccConfig.setCurrencyCode(queryCurrencyCode);
		
		return new ModelAndView(dcc)
		.addObject("dccConfig", dccConfig);//add by davis.guo at 2016-08-09
	}

	// 新增页面
	public ModelAndView saveDCC(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView(saveDCC);
	}

	// 设置默认页面
	public ModelAndView configurationDCC(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView(configurationDCC);
	}

	// DCC配置详情
	public ModelAndView detailsDCC(HttpServletRequest request,
			HttpServletResponse response) {
		String partnerId = request.getParameter("partnerId");
		String partnerName=request.getParameter("partnerName");
		/*String partnerName = null;
		try {
			partnerName = new String(request.getParameter("partnerName")
					.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}*/
		String currencyCode = request.getParameter("currencyCode");
		String markUp = request.getParameter("markUp");
		PartnerDCCConfig dccConfig = new PartnerDCCConfig();
		dccConfig.setPartnerId(partnerId);
		dccConfig.setPartnerName(partnerName);
		dccConfig.setCurrencyCode(currencyCode);
		dccConfig.setMarkUp(markUp);
		return new ModelAndView(detailsDCC).addObject("dccConfig", dccConfig);
	}

	// 回显修改DCC配置详情
	public ModelAndView updateDCC(HttpServletRequest request,
			HttpServletResponse response) {
		String partnerId = request.getParameter("partnerId");
		String partnerName = request.getParameter("partnerName");
		/*try {
			partnerName = new String(request.getParameter("partnerName")
					.getBytes("ISO-8859-1"), "UTF-8");
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}*/
		String markUp = request.getParameter("markUp");
		String currencyCode = request.getParameter("currencyCode");
		String id = request.getParameter("id");
		PartnerDCCConfig dccConfig = new PartnerDCCConfig();
		dccConfig.setPartnerId(partnerId);
		dccConfig.setPartnerName(partnerName);
		dccConfig.setMarkUp(markUp);
		dccConfig.setId(Integer.valueOf(id));
		dccConfig.setCurrencyCode(currencyCode);
		return new ModelAndView(updateDCC).addObject("dccConfig", dccConfig);
	}
	/**
	 * 批量上传操作
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView upload(HttpServletRequest request,
			HttpServletResponse response) {
		String type = request.getParameter("type");
		ModelAndView view = new ModelAndView(upload);
		view.addObject("type", type);
		return view;
	}
	/**
	 * 批量上传更新和新增
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView submitUploade(final HttpServletRequest request, final HttpServletResponse response) throws Exception {
		String memberCode = (String) request.getSession().getAttribute("memberCode");
		try {
			Page page = PageUtils.getPage(request) ;
			request.setCharacterEncoding("UTF-8");
			response.setCharacterEncoding("UTF-8");
			DefaultMultipartHttpServletRequest dmRequest = (DefaultMultipartHttpServletRequest) request;
			MultipartFile multiPartFile = dmRequest.getFile("file_trackinguploade");
			PrintWriter outPrintWriter = response.getWriter();
			if (multiPartFile != null) {
				Workbook book = Workbook.getWorkbook(multiPartFile.getInputStream()) ;
				@SuppressWarnings("unchecked")
				List<PartnerDCCConfig> expressTrackings = ExcelUtils.getListByReadShell(book.getSheet(0), 1, 0, 5,
						new String[] { "partnerId", "partnerName","currencyName","currencyCode","markUp" }, PartnerDCCConfig.class);
				String uploadtype = request.getParameter("type");
				//是否更新标志
				Boolean uploadFlag=true;
				if(CollectionUtils.isNotEmpty(expressTrackings)){
					configurationDCCService.deleteAll();
					for(PartnerDCCConfig partnerDCCConfig : expressTrackings){
						if (null == partnerDCCConfig.getPartnerId()|| "".equals(partnerDCCConfig.getPartnerId())
								|| null == partnerDCCConfig.getCurrencyCode()|| "".equals(partnerDCCConfig.getCurrencyCode())
								|| null == partnerDCCConfig.getMarkUp()|| "".equals(partnerDCCConfig.getMarkUp())
								|| null == partnerDCCConfig.getPartnerName()|| "".equals(partnerDCCConfig.getPartnerName())) 
							{
								partnerDCCConfig.setRemark("商户会员号,商户名称，货币代码，markup不能为空！");
								uploadFlag = false;
								continue;
							} 
						
							// 判断会员号是否存在
							EnterpriseBaseDto code = enterpriseBaseService
									.queryEnterpriseBaseByMemberCode(Long.valueOf(partnerDCCConfig
											.getPartnerId()));
							if (code == null) {
								partnerDCCConfig.setRemark("商户会员号不存在！");
								uploadFlag = false;
							}
						CurrencyDTO currencyDTO=new CurrencyDTO();
						currencyDTO.setCurrencyCode(partnerDCCConfig.getCurrencyCode());
						currencyDTO.setFlag("7");
						List<CurrencyDTO> returnList = currencyService.findByMapper(
								currencyDTO);
						if(CollectionUtils.isEmpty(returnList))
						{
							partnerDCCConfig.setRemark("货币代码不支持！");
							uploadFlag=false;
						}
						Boolean strResult = partnerDCCConfig.getMarkUp().matches("-?[0-9]+.*[0-9]*");
					    if(!strResult)
						{
							partnerDCCConfig.setRemark("markup值错误！");
							uploadFlag=false;
						}
					    if(Double.parseDouble(partnerDCCConfig.getMarkUp())<0 )
						{
					    	partnerDCCConfig.setRemark("markup值不能小于0！");
							uploadFlag=false;
						}
						PartnerDCCConfig partnerDCCConfigadd=new PartnerDCCConfig();
						partnerDCCConfigadd.setPartnerId(partnerDCCConfig.getPartnerId());
						partnerDCCConfigadd.setCurrencyCode(partnerDCCConfig.getCurrencyCode());
						List<PartnerDCCConfig> partnerDCCConfigList =configurationDCCService.findDCCConfiguration(partnerDCCConfig);
						if("add".equals(uploadtype))
						{
							if(CollectionUtils.isNotEmpty(partnerDCCConfigList))
							{
								partnerDCCConfig.setRemark("相同会员号相同的币种的数据已存在，不能新增！");
								uploadFlag=false;
							}
							partnerDCCConfig.setCreateTime(new Date());
							partnerDCCConfig.setMarkUpNew(partnerDCCConfig.getMarkUp());
							partnerDCCConfig.setMarkUp(null);
						}
						if("update".equals(uploadtype))
						{

							if(CollectionUtils.isEmpty(partnerDCCConfigList))
							{
								partnerDCCConfig.setRemark("相同会员号相同的币种的数据不存在，不能修改！");
								uploadFlag=false;
							}else{
								if(1==partnerDCCConfigList.size())
								{
									partnerDCCConfig.setId(partnerDCCConfigList.get(0).getId());
									partnerDCCConfig.setMarkUpNew(partnerDCCConfig.getMarkUp());
									partnerDCCConfig.setMarkUp(partnerDCCConfigList.get(0).getMarkUp());
								}
							}
							partnerDCCConfig.setUpdateTime(new Date());
							
						}
						
						configurationDCCService.saveDCCConfigurationLoad(partnerDCCConfig);
					}
					
					return new ModelAndView(dccListLoad).addObject("list", expressTrackings)
							.addObject("page", page).addObject("uploadFlag", uploadFlag).addObject("type", uploadtype);
					
					
				}else{
					String errormsg = "上传数据不能为空！" ;
					return new ModelAndView(this.dcc).addObject("errormsg", errormsg) ;
					//return null ;
				}
			}
		} catch (Exception e) {
			e.printStackTrace();
			String errormsg = "dcc配置上传失败，请按照下载模板填写数据上传!";
			return new ModelAndView(this.dcc).addObject("errormsg", errormsg) ;
		}
		return new ModelAndView(this.dcc).addObject("errormsg", "批量新增文件解析异常！") ;
		
}
	/**
	 * 确认上传更新的数据
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	public ModelAndView updateDCCConfigurationLoad(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		response.setCharacterEncoding("UTF-8");
		String uploadFlag = request.getParameter("uploadFlag");
		String uploadtype = request.getParameter("type");
		PrintWriter outPrintWriter = response.getWriter();
		String flag="false";
		List<PartnerDCCConfig> partnerDCCConfigList =configurationDCCService.findDCCUploadAll();
		if("true".equals(uploadFlag))
		{
			if("add".equals(uploadtype))
			{
				for(PartnerDCCConfig partnerDCCConfig : partnerDCCConfigList){
					configurationDCCService.saveDCCConfiguration(partnerDCCConfig);
					flag="true";
				}
			}else{
				for(PartnerDCCConfig partnerDCCConfig : partnerDCCConfigList){
					if(!partnerDCCConfig.getMarkUpNew().equals(partnerDCCConfig.getMarkUp()))
					{
						int i=configurationDCCService.updateDCCConfigurationLoad(partnerDCCConfig);
						if(i>1)
						{
							flag="true";
						}
					}else{
						logger.info("更新的markup和原表数据相同，不需要更新！");
					}
					
				}
			}
		}else{
			String errormsg = "导入文件有错误不能上传!";
			outPrintWriter.println("<script>parent.callback('N|"
					+ errormsg + "')</script>");
		}
		/*return new ModelAndView("redirect:/systemmanager/dccListLoad").addObject("list", expressTrackings)
				.addObject("page", page);*/
		/*return new ModelAndView("replace:/systemmanager/dccListLoad").addObject("list", expressTrackings)
				.addObject("page", page);*/
		return new ModelAndView(dcc).addObject("flag",flag);
	}
	/**
	 * 下载文件
	 * @param request
	 * @param response
	 * @param websiteConfig
	 * @return
	 * @throws Exception
	 */
	public ModelAndView downLoadFile(final HttpServletRequest request,
			final HttpServletResponse response)
			throws Exception {

		request.setCharacterEncoding("UTF-8");
		// MultiUploadForm multiUploadForm = (MultiUploadForm) form;
		String path = getServletContext().getRealPath(
				"/Template");

		String fileName = request.getParameter("fileName");
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
	// 根据商户会员号 查询商户的名称
	public void getPartnerName(HttpServletRequest request,
			HttpServletResponse response) throws InterruptedException,
			IOException {
		String partnerId = request.getParameter("partnerId");
		if (partnerId.equals("")) {
			return;
		}
		EnterpriseBaseDto code = enterpriseBaseService
				.queryEnterpriseBaseByMemberCode(Long.valueOf(partnerId));
		if (code == null) {
			response.getWriter().write("error");
			return;
		}
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html");
		response.getWriter().write(code.getZhName());
	}

	// 通过会员号查询已经存在的币种
	public StringBuffer findCurrencyCodeByPartnerId(String partnerId) {
		PartnerDCCConfig dccConfig = new PartnerDCCConfig();
		dccConfig.setPartnerId(partnerId);
		List<PartnerDCCConfig> dcclist = configurationDCCService
				.findDCCConfiguration(dccConfig);
		StringBuffer sb = new StringBuffer();
		for (PartnerDCCConfig partnerDCCConfig : dcclist) {
			sb.append(partnerDCCConfig.getCurrencyCode() + "&");
		}
		return sb;
	}

	
	// 新增企业DCC配置
	public ModelAndView DCCConfigurationSave(HttpServletRequest request,
			HttpServletResponse response) {
		String partnerId = request.getParameter("partnerId");
		String partnerName = request.getParameter("partnerName");
		String json =request.getParameter("json");
		//String[] values = request.getParameterValues("currencyCode");
		String[] currencyCodeMarkup= json.split("＆");//一天数据  多个币种对应对应相同的markup
		String[] currencyCode=null;
		for (int i = 0; i < currencyCodeMarkup.length; i++) {
			 currencyCode = currencyCodeMarkup[i].split(":");
		}
		StringBuffer sb = findCurrencyCodeByPartnerId(partnerId);
		String[] split = sb.toString().split("&");//已经存在的币种
		for (int i = 0; i < split.length; i++) {
			for (int j = 0; j < currencyCode.length; j++) {
				if (split[i].equals(currencyCode[j])) {
					return new ModelAndView(saveDCC).addObject("error",
							"已经存在此币种");
				}
			}
		}
		Page page = PageUtils.getPage(request) ;
		//String markup = request.getParameter("markup");
		SessionUserHolder sessionUserHolder = SessionUserHolderUtil
				.getSessionUserHolder();
		PartnerDCCConfig dccConfig = new PartnerDCCConfig();
		dccConfig.setPartnerId(partnerId);
		dccConfig.setPartnerName(partnerName);
		// dccConfig.setCurrencyCode(currencyCode);
		//dccConfig.setMarkUp(markup);
		dccConfig.setStatus("1");
		dccConfig.setCreateTime(new Date());
		dccConfig.setUpdateTime(new Date());
		dccConfig.setOperator(sessionUserHolder.getUsername());
		configurationDCCService.saveDCCConfiguration(dccConfig, currencyCodeMarkup);
		return new ModelAndView(saveDCC);
	}
	
/***
 * 修改数据
 * @param request
 * @param response
 * @return
 */
	public ModelAndView DCCConfigurationEdit(HttpServletRequest request,
			HttpServletResponse response) {
		String partnerId = request.getParameter("partnerId");
		String partnerName = request.getParameter("partnerName");
		String currencyCode = request.getParameter("currencyCode");
		String markup = request.getParameter("markup");
		String id = request.getParameter("id");
		SessionUserHolder sessionUserHolder = SessionUserHolderUtil
				.getSessionUserHolder();
		PartnerDCCConfig dccConfig = new PartnerDCCConfig();
		dccConfig.setId(Integer.valueOf(id));
		dccConfig.setPartnerId(partnerId);
		dccConfig.setPartnerName(partnerName);
		dccConfig.setCurrencyCode(currencyCode);
		dccConfig.setMarkUp(markup);
		dccConfig.setStatus("1");
		dccConfig.setCreateTime(new Date());
		dccConfig.setUpdateTime(new Date());
		dccConfig.setOperator(sessionUserHolder.getUsername());
		configurationDCCService.updateDCCConfiguration(dccConfig);
		return index(request,response);
	}

	// 查询企业DCC配置
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) {
		String partnerId = request.getParameter("partnerId");
		String partnerName = request.getParameter("partnerName");
		String currencyCode = request.getParameter("currencyCode");
		
		PartnerDCCConfig dccConfig = new PartnerDCCConfig();		
		dccConfig.setPartnerId(partnerId);
		dccConfig.setPartnerName(partnerName);
		dccConfig.setCurrencyCode(currencyCode);
		//保存查询时的参数，在修改等操作返回时，返回本次的查询的结果页面。add by davis.guo at 2016-08-09
		queryPartnerId = (partnerId==null?"":partnerId);
		queryPartnerName = (partnerName==null?"":partnerName);
		queryCurrencyCode = (currencyCode==null?"":currencyCode);
		
		Page page = PageUtils.getPage(request) ;
		List<PartnerDCCConfig> dcclist = configurationDCCService
				.findDCCConfiguration(dccConfig,page);

		return new ModelAndView(dccList).addObject("list", dcclist)
				.addObject("page", page);
	}
	
	/***
	 * 批量修改回显数据
	 */
	public ModelAndView bulkEditingDCC(HttpServletRequest request,
			HttpServletResponse response) {
		String partnerId = request.getParameter("partnerId");
		PartnerDCCConfig dccConfig = new PartnerDCCConfig();
		dccConfig.setPartnerId(partnerId);
		List<PartnerDCCConfig> dcclist = configurationDCCService
				.findDCCConfiguration(dccConfig);
		return new ModelAndView(bulkEditingDCC).addObject("dcclist", dcclist);
	}

	/***
	 * 批量修改数据
	 */
	public ModelAndView bulkUpdateDCC(HttpServletRequest request,
			HttpServletResponse response) {
		String partnerId = request.getParameter("partnerId");
		String json=request.getParameter("json");
		String[] split = json.split("＆");//一天数据  多个币种对应对应相同的markup
		PartnerDCCConfig dccConfig = new PartnerDCCConfig();
		dccConfig.setPartnerId(partnerId);
		configurationDCCService.bulkUpdateDCC(dccConfig,split);
		return bulkEditingDCC (request, response);			
	}

	/**
	 * 设置企业DCC默认配置
	 * */
	public ModelAndView SettingsDCC(HttpServletRequest request,
			HttpServletResponse response) {
		String json=request.getParameter("json");
		String[] currencyCodeMarkup = json.split("＆");
		
		SessionUserHolder sessionUserHolder = SessionUserHolderUtil
				.getSessionUserHolder();
		PartnerDCCConfig dccConfig = new PartnerDCCConfig();
		dccConfig.setPartnerId("0");
		dccConfig.setPartnerName(null);
		dccConfig.setStatus("1");
		dccConfig.setCreateTime(new Date());
		dccConfig.setUpdateTime(new Date());
		dccConfig.setOperator(sessionUserHolder.getUsername());
		Boolean flag=configurationDCCService.saveDCCConfiguration(dccConfig, currencyCodeMarkup);
		if(!flag){
			return new ModelAndView(configurationDCC).addObject("info", "已经存在这个币种的默认配置！！！");
		}
		return new ModelAndView(configurationDCC).addObject("info", "设置完成！！！");
	}

	/***
	 * DCC兑换的币种
	 * 
	 * @param request
	 * @param response
	 * @throws Exception
	 */
	public void saveDcc(HttpServletRequest request, HttpServletResponse response)
			throws Exception {

		String currencyName = request.getParameter("currencyName");
		String currencyCode = request.getParameter("currencyCode");
		String currencyNum = request.getParameter("currencyNum");
		String flag = request.getParameter("flag");

		CurrencyDTO currencyDTO = new CurrencyDTO();

		if (currencyCode != null && currencyCode.trim().length() > 0) {
			currencyDTO.setCurrencyCode(currencyCode);
		}
		if (currencyName != null && currencyName.trim().length() > 0) {
			currencyDTO.setCurrencyName(currencyName);
		}
		if (currencyNum != null && currencyNum.trim().length() > 0) {
			currencyDTO.setCurrencyName(currencyNum);
		}
		if (flag != null && flag.trim().length() > 0) {
			currencyDTO.setFlag(flag);
		}

		List<CurrencyDTO> returnList = currencyService.findByMapper(
				currencyDTO);
		String jSonString = JSonUtil.toJSonString(returnList);
		response.getWriter().println(jSonString);
	}
	
	public ModelAndView deleteDcc(HttpServletRequest request,HttpServletResponse response){
		String id = request.getParameter("id");
		configurationDCCService.deleteDcc(id);
		return new ModelAndView(dcc);
	}





}
