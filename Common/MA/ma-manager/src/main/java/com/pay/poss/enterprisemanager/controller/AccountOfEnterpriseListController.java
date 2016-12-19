package com.pay.poss.enterprisemanager.controller;


import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;



import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.apache.poi.hssf.usermodel.HSSFFont;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.validation.BindException;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.SimpleFormController;

import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.enterprisemanager.common.Constants;
import com.pay.poss.enterprisemanager.dto.EnterpriseSearchDto;
import com.pay.poss.enterprisemanager.dto.EnterpriseSearchListDto;
import com.pay.poss.enterprisemanager.enums.AccountStatusEnum;
import com.pay.poss.enterprisemanager.formbean.EnterpriseSearchFormBean;
import com.pay.poss.enterprisemanager.service.IEnterpriseService;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.poss.userrelation.dto.NodesDto;
import com.pay.poss.userrelation.service.IUserRelationService;
/**
 * 
 * @Description 
 * @project 	poss-membermanager
 * @file 		AccountOfEnterpriseListController.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-10-22		ddr			Create
 */
 


public class AccountOfEnterpriseListController extends SimpleFormController {
	private Log log = LogFactory.getLog(AccountOfEnterpriseListController.class);
	private IEnterpriseService enterpriseService;
	private IUserRelationService userRelationService;
	
	
	
	public void setUserRelationService(IUserRelationService userRelationService) {
		this.userRelationService = userRelationService;
	}

	@Override
	protected Map<String,Object> referenceData(HttpServletRequest request) throws Exception {
		log.debug("AccountOfEnterpriseListController.referenceData is running...");
		
		Map<String,Object> dataMap = this.initData(request);
				
		return dataMap;
	}
	
	@Override
	protected ModelAndView onSubmit(HttpServletRequest request, HttpServletResponse response, Object command,
			BindException errors) throws Exception {
		log.debug("AccountOfEnterpriseListController.onSubmit is running...");
		EnterpriseSearchFormBean enterpriseSearchFormBean = (EnterpriseSearchFormBean) command;
		EnterpriseSearchDto enterpriseSearchDto = new EnterpriseSearchDto();
		
		BeanUtils.copyProperties(enterpriseSearchFormBean, enterpriseSearchDto);
		
		//数据库排序字段
		 String orderParam  = ServletRequestUtils.getStringParameter(request, "orderParam",null);
		 String ascOrDesc  = ServletRequestUtils.getStringParameter(request, "ascOrDesc",null);
		 enterpriseSearchDto.setOrderParam(orderParam);
		 enterpriseSearchDto.setAscOrDesc(ascOrDesc);
		 ////
		 
		
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
		
		List<EnterpriseSearchListDto> enterpriseList = enterpriseService.queryAccountOfEnterprise(enterpriseSearchDto);
		Integer enterpriseListCount = enterpriseService.queryAccountOfEnterpriseCount(enterpriseSearchDto);
		page.setResult(enterpriseList);
		page.setTotalCount(enterpriseListCount);
		
		///////////////
		//如果是导出
		String export  = ServletRequestUtils.getStringParameter(request, "export","0");
		Map<String,Object> dataMap = this.initData(request);
		if(export.equals("1")){
			List<EnterpriseSearchListDto> enterpriseListAll=enterpriseService.queryAccountOfEnterpriseAll(enterpriseSearchDto);
			return exportAccountList(enterpriseListAll,request, response,dataMap);
		}
		
		dataMap.put("page",page);
		dataMap.put("enterpriseSearchDto", enterpriseSearchDto);
		return new ModelAndView(this.getSuccessView()).addAllObjects(dataMap);
	}


	public ModelAndView exportAccountList(List<EnterpriseSearchListDto> list,HttpServletRequest request,HttpServletResponse response,Map dataMap) throws FileNotFoundException, IOException{
		
		
		
		 String fullPath = request.getSession().getServletContext().getRealPath("/WEB-INF/jsp/enterprisemanager/accountOfEnterpriseListExport.xls");
         HSSFWorkbook book ;
         OutputStream os = null ;
   	  //创建workbook
   	    book = new HSSFWorkbook(new FileInputStream(fullPath));
   	    HSSFFont fontHei = book.createFont();
   	    fontHei.setFontHeightInPoints( (short)11);
   	    fontHei.setFontName("黑体");
   	   HSSFSheet sheet = book.getSheetAt(0);
      	    // 取得第一张表
         try {
          //创建workbook
        	 int ROWNUM_DATA = 2;    // 数据的起始行位置
             SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
             int i=0 ;
             for(; i<list.size();i++){
            	 EnterpriseSearchListDto record = (EnterpriseSearchListDto)list.get(i);
                 HSSFRow row = sheet.createRow(ROWNUM_DATA+i);
                  int j = 0;	
                  row.createCell(j++).setCellValue(record.getMemberCode());
                  row.createCell(j++).setCellValue(record.getMerchantCode());
                  row.createCell(j++).setCellValue(record.getMerchantName());
                  row.createCell(j++).setCellValue((record.getLoginName()));
                  row.createCell(j++).setCellValue((record.getAccountCode()));
                  AcctTypeEnum[] accountTypeList=(AcctTypeEnum[]) dataMap.get("accountTypeList");
                  Boolean flag=false;
                  for (AcctTypeEnum acctTypeEnum : accountTypeList) {
                	  if(acctTypeEnum.getCode()==Integer.parseInt(record.getAccountType())){
                		  row.createCell(j++).setCellValue(acctTypeEnum.getDisplayName());
                		  flag=true;
                	  }
                  }	
                  if(!flag){
                	  row.createCell(j++).setCellValue("");
                  }
                  row.createCell(j++).setCellValue(record.getCreditBalance());
                  row.createCell(j++).setCellValue(record.getDebitBalance());
                  row.createCell(j++).setCellValue(record.getBalance());
                  row.createCell(j++).setCellValue((record.getFrozenBalance()));
                  AccountStatusEnum[] accountStatusEnum=(AccountStatusEnum[])dataMap.get("accountStatusEnum");
                  for (AccountStatusEnum accountStatusEnum2 : accountStatusEnum) {
                	  if(accountStatusEnum2.getCode()==Integer.parseInt(record.getAccountStatus())){
                		  row.createCell(j++).setCellValue(accountStatusEnum2.getDescription());
                	  }
				}
                  row.createCell(j++).setCellValue(record.getSignName());
                  
             };
        			 
          // 设置格式
         // 输出
         response.setContentType("application/vnd.ms-excel;charset=utf-8");
         response.addHeader("Content-Disposition", (new StringBuilder("attachment;   filename=\""))
                 .append(new String("账户信息当前页.xls".getBytes("gbk"), "ISO-8859-1")).append("\"").toString());
         os = response.getOutputStream();
         book.write(os);
         os.flush();
         
     } catch (Exception e) {
         e.printStackTrace();
         logger.error("写excel报错IO", e);
     }
     finally{
    	 try{
    		 if(os!=null)
          		 os.close();
    	 }catch (Exception e) {
    		 e.printStackTrace();
    		 logger.error("关闭流出错", e);
		}
     }
	return null;
		
		
	}
	
	
    private Map<String,Object> initData(HttpServletRequest request){
    	AcctTypeEnum[] accountTypeList = AcctTypeEnum.values();
    	AccountStatusEnum[] accountStatusEnum = AccountStatusEnum.values();
		Map<String,Object> dataMap = new Hashtable<String,Object>();
		dataMap.put("accountTypeList",accountTypeList );
		dataMap.put("accountStatusEnum",accountStatusEnum );
		/*List<NodesDto> loginSubNodes = getLoginIdSubNodes();
		dataMap.put("loginSubNodes", loginSubNodes == null ? new ArrayList<NodesDto>() :loginSubNodes);*/
		List<NodesDto> loginSubNodes=userRelationService.findAll();
		dataMap.put("loginSubNodes",loginSubNodes);
		return dataMap;
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
	
	
}
