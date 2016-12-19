/**
 * 
 */
package com.pay.app.controller.base.bulkpayment;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.batchpay.dto.BulkPaymentTemplate;
import com.pay.batchpay.dto.OrderTemplateFileDbUnion;
import com.pay.batchpay.service.bulkpayment.BulkpayOrderService;
import com.pay.batchpay.service.bulkpayment.BulkpayTemplateService;
import com.pay.batchpay.util.StringUtils;
import com.pay.util.ExcelUtils;

/**
 * 批次订单商户审核控制器
 * @author PengJiangbo
 *
 */
public class BulkorderCheckController extends MultiActionController {

	private String checkView ;
	
	private String checkDetailView ;
	
	//批量付款模板服务
	private BulkpayTemplateService bulkpayTemplateService ;
	//批量付款订单服务
	private BulkpayOrderService bulkpayOrderService ;
	
	/**
	 * 商户审核，上传审核文件
	 * @param request
	 * @param response
	 * @return
	 * @throws BiffException
	 * @throws IOException
	 */
	public ModelAndView bulkorderFileCheck(HttpServletRequest request, HttpServletResponse response) throws BiffException, IOException{
		ModelAndView mv = new ModelAndView(checkDetailView) ;
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request ;
		CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file_check") ;
		Workbook book = Workbook.getWorkbook(file.getInputStream()) ;
		
		List<BulkPaymentTemplate> list = null;
		try {
			list = ExcelUtils.getListByReadShell(
					book.getSheet(0), 1, 0, 11, new String[] { "toAccountName",
							"bankName", "branchBankName", "siCode",
							"bankAddress", "bankAccount", "accountType","accountTypeCode", "payAmount", "currency","currencyCode" },
							BulkPaymentTemplate.class);
		} catch (Exception e) {
			if (logger.isInfoEnabled()) {
				logger.info(e.getMessage());
				logger.info("模板文件数据解析错误！");
			}
			return mv.addObject("dataAnalysisError", "数据解析错误，请严格按照模板填写数据！") ;
		}
		
		String bulkorderNo = StringUtils.isNotNull(request.getParameter("bulkorderNo")) ;
		String bulkorderId = StringUtils.isNotNull(request.getParameter("bulkorderId")) ;
		List<BulkPaymentTemplate> templateResultList = this.bulkpayTemplateService.findBulkpayByBulkorderNo(bulkorderNo) ;
		
		List<BulkPaymentTemplate> templateResultListMerged = new ArrayList<BulkPaymentTemplate>() ;
		BigDecimal sumPayAmount = new BigDecimal(0) ;	//原付款总金额
		
		//不需要比较的属性设置默认值
		for(BulkPaymentTemplate bpt : templateResultList){
			bpt.setId(0);
			bpt.setBulkpayNo(null);
			bpt.setSiCode("");
			templateResultListMerged.add(bpt) ;
			sumPayAmount = sumPayAmount.add(bpt.getPayAmount()) ;
		}
		mv.addObject("bulkorderNo", bulkorderNo) ;
		mv.addObject("bulkorderId", bulkorderId) ;
		mv.addObject("sumPayAmount", sumPayAmount) ;
		int fileListSize = list.size() ;
		int dbListSize = templateResultListMerged.size() ;				   //原付款笔数
		mv.addObject("dbListSize", dbListSize) ;
		int minSize = fileListSize <= dbListSize ? fileListSize : dbListSize ;
		
		OrderTemplateFileDbUnion fdb = null ;
		List<OrderTemplateFileDbUnion> fdbList = null ;
		int disEqualCount = 0 ;
		int equalCount = 0 ;
		BigDecimal disMeetAmount = new BigDecimal(0) ;	//记录不符合金额
		BigDecimal meetAmount = new BigDecimal(0) ;		//记录符合金额
		//审核文件为空时
		if(minSize == 0 && minSize == fileListSize){
			mv.addObject("equalCount", equalCount) ;
			disEqualCount = dbListSize ;
			mv.addObject("disEqualCount", disEqualCount) ;
			mv.addObject("meetAmount",meetAmount) ;
			mv.addObject("disMeetAmount", sumPayAmount) ;
			
			fdbList = new ArrayList<OrderTemplateFileDbUnion>() ;
			BulkPaymentTemplate fileTemplate = new BulkPaymentTemplate() ;
			for(int i=0; i<dbListSize; i++){
				fdb = new OrderTemplateFileDbUnion() ;
				fdb.setDbTemplate(templateResultListMerged.get(i));
				fdb.setFileTemplate(fileTemplate);
				fdbList.add(fdb) ;
			}
			mv.addObject("fdbList", fdbList) ;
			mv.addObject("rejectFlag", "true") ;
			return mv ;
		}
		//审核文件非空
		for(int i=0; i<minSize; i++){
			if(list.get(i).equals(templateResultListMerged.get(i))){
				equalCount ++ ;
				meetAmount = meetAmount.add(templateResultListMerged.get(i).getPayAmount()) ;
				//logger.info("审核文件数据和库数据比较一致，haha……");
			}else{
				fdb = new OrderTemplateFileDbUnion() ;
				if(null == fdbList){
					fdbList = new ArrayList<OrderTemplateFileDbUnion>() ;
				}
				fdb.setFileTemplate(list.get(i));
				//System.out.println("file payAmount:" + list.get(i).getPayAmount());
				fdb.setDbTemplate(templateResultListMerged.get(i));
				fdbList.add(fdb) ;
				disEqualCount ++ ;					//记录不符合笔数
				mv.addObject("rejectFlag", "true") ;
				if(null != list.get(i).getPayAmount()){
					disMeetAmount = disMeetAmount.add(list.get(i).getPayAmount()) ;
				}
				//logger.info("审核文件数据和库数据比较不一致，ee……");
			}
		}
		mv.addObject("equalCount", equalCount) ;
		mv.addObject("disEqualCount", disEqualCount) ;
		mv.addObject("meetAmount", meetAmount) ;
		mv.addObject("disMeetAmount", disMeetAmount) ;
		mv.addObject("fdbList", fdbList) ;
		return mv ;
	}
	
	/**
	 * 商户审核，修改状态
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView checkOrderStatusChange(HttpServletRequest request, HttpServletResponse response){
		long bulkorderId = StringUtils.stringToLong(request.getParameter("bulkorderId")) ;
		String statusCode = StringUtils.isNotNull(request.getParameter("statusCode")) ;
		Map<String, Object> hMap = new HashMap<String, Object>() ;
		hMap.put("newStatus", statusCode) ;
		hMap.put("id", bulkorderId) ;
		this.bulkpayOrderService.updateBulkordedrStatus(hMap) ;
		return new ModelAndView("redirect:/corp/bulkPayment.htm?method=checkBulkOrderQuery") ;
	}
	
	//setter----------------------------------------------------------------------------------------
	public void setCheckView(String checkView) {
		this.checkView = checkView;
	}

	public void setCheckDetailView(String checkDetailView) {
		this.checkDetailView = checkDetailView;
	}

	public void setBulkpayTemplateService(
			BulkpayTemplateService bulkpayTemplateService) {
		this.bulkpayTemplateService = bulkpayTemplateService;
	}

	public void setBulkpayOrderService(BulkpayOrderService bulkpayOrderService) {
		this.bulkpayOrderService = bulkpayOrderService;
	}
	
	
}
