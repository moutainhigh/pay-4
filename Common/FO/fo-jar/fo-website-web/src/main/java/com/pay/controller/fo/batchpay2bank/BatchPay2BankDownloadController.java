/**
 * 
 */
package com.pay.controller.fo.batchpay2bank;

import java.io.BufferedOutputStream;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jxls.transformer.XLSTransformer;

import org.apache.poi.ss.usermodel.Workbook;
import org.apache.velocity.Template;
import org.apache.velocity.VelocityContext;
import org.apache.velocity.app.VelocityEngine;
import org.springframework.web.servlet.ModelAndView;

import com.pay.app.base.api.annotation.OperatorPermission;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.fo.order.common.OrderType;
import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fo.order.dto.batchpayment.BatchPaymentOrderDTO;
import com.pay.fo.order.dto.batchpayment.BatchPaymentReqBaseInfoDTO;
import com.pay.fo.order.dto.batchpayment.BatchPaymentToBankReqDetailDTO;
import com.pay.fundout.service.OrderStatus;
import com.pay.fundout.util.AmountUtils;
import com.pay.fundout.withdraw.common.util.PoiExcelUtil;
import com.pay.util.StringUtil;

/**
 * @author NEW
 *
 */
public class BatchPay2BankDownloadController extends
		AbstractBatchPay2BankController {
	
	
	/**
	 * 下载支付模板文件
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "FO_MASSPAY2BANK_DOWNLOAD")
	public ModelAndView downloadTemplateFile(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String type = request.getParameter("type");
		InputStream inputStream  = null;
		OutputStream os = null;
		String fileName = "";
		try {
			if("Excel".equals(type)){
				inputStream = new PoiExcelUtil().getClass().getResourceAsStream("/properties/BatchPay2BankTemplate.xls");
				fileName = "BatchPay2BankTemplate.xls";
				response.setContentType("application/vnd.ms-excel;charset=utf-8");
			}else if("Csv".equals(type)){
				inputStream = new PoiExcelUtil().getClass().getResourceAsStream("/properties/BatchPay2BankTemplate.csv");
				fileName = "BatchPay2BankTemplate.csv";
				response.setContentType("text/plain;charset=GBK");
			}

			response.reset();
			response.setHeader("Expires", "0");
			response.setHeader("Pragma", "public");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Cache-Control", "public");
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF8"));
			response.setBufferSize(1024);
	        
			os = new BufferedOutputStream(response.getOutputStream());
			byte[] b = new byte[1024];
			int len = 0;
			while ((len = inputStream.read(b)) > 0) {
				os.write(b, 0, len);
				os.flush();
			}
			os.close();
			inputStream.close();
			return null;
		} catch (Throwable e) {
			throw new RuntimeException(e);
		}finally{
			os = null;
			inputStream = null;
		}
	}
	
	/**
	 * 下载导入的错误记录列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "FO_MASSPAY2BANK_DOWNLOAD")
	public ModelAndView downloadErrorList(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		OutputStream os = null;
		try{
			String fileKy = request.getParameter("fileKy");
			String businessBatchNo = StringUtil.null2String(request.getParameter("businessBatchNo"));
			Long requestSeq = Long.valueOf(fileKy);
			String fileName = "";
			String fileTemplate ="/properties/batchpay2bankImpotList.xls";
			BatchPaymentReqBaseInfoDTO reqInfo = batchPaymentReqBaseInfoService.getBatchPaymentReqBaseInfo(requestSeq);
			Long memberCode = Long.valueOf(loginSession.getMemberCode());
			if(reqInfo == null || !reqInfo.getPayerMemberCode().equals(memberCode)){
				return new ModelAndView();
			}
			reqInfo.setBusinessBatchNo(businessBatchNo);
			
			Workbook book = null;
			Map<String, Object> map = new HashMap<String, Object>();
			
			List<BatchPaymentToBankReqDetailDTO> detailList = batchPaymentToBankReqDetailService.getValidateDetailList(requestSeq, 0);
			fileName = "ImportErrorRecord.xls";
			map.put("businessNo", businessBatchNo);
			map.put("detailList", detailList);
			book = createWorkbook(map,fileTemplate);
	
			response.reset();
			response.setHeader("Expires", "0");
			response.setHeader("Pragma", "public");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Cache-Control", "public");
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF8"));
			os = new BufferedOutputStream(response.getOutputStream());
			book.write(os);
			os.flush();
			os.close();
			return null;
		}catch(Throwable e){
			throw new RuntimeException(e);
		}finally{
			os = null;
		}
		
	}
	
	/**
	 * 下载导入的有效记录列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "FO_MASSPAY2BANK_DOWNLOAD")
	public ModelAndView downloadValidList(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		Long memberCode = Long.valueOf(loginSession.getMemberCode());
		OutputStream os = null;
		try{
			String fileKy = request.getParameter("fileKy");
			String businessBatchNo = StringUtil.null2String(request.getParameter("businessBatchNo"));
			Long requestSeq = Long.valueOf(fileKy);
			String fileName = "";
			String fileTemplate ="/properties/batchpay2bankImpotList.xls";
			BatchPaymentReqBaseInfoDTO reqInfo = batchPaymentReqBaseInfoService.getBatchPaymentReqBaseInfo(requestSeq);
			
			if(reqInfo == null || !reqInfo.getPayerMemberCode().equals(memberCode)){
				throw new RuntimeException();
			}
			reqInfo.setBusinessBatchNo(businessBatchNo);
			
			Workbook book = null;
			Map<String, Object> map = new HashMap<String, Object>();
			List<BatchPaymentToBankReqDetailDTO> detailList = null;
			BatchPaymentOrderDTO order = batchPaymentOrderService.getOrder(memberCode, OrderType.BATCHPAY2BANK.getValue(), businessBatchNo);
			if(order!=null && order.getOrderStatus()==OrderStatus.PROCESSED_SUCCESS.getValue()){
				detailList = new ArrayList<BatchPaymentToBankReqDetailDTO>();
				List<FundoutOrderDTO> fundoutOrderList = fundoutOrderService.getChildFundoutOrderList(order.getOrderId(), memberCode);
				BatchPaymentToBankReqDetailDTO detail = null;
				for (FundoutOrderDTO fundoutOrderDTO : fundoutOrderList) {
					detail = buildBatchPaymentToBankReqDetailDTO(fundoutOrderDTO);
					detailList.add(detail);
				}
				fileTemplate = "/properties/batchpay2bankResultList.xls";
				fileName = "BatchPaytoBankResult.xls";
			}else{
				detailList = batchPaymentToBankReqDetailService.getValidateDetailList(requestSeq, 1);
				fileName = "ImportValidRecord.xls";
			}
			
			map.put("businessNo", businessBatchNo);
			map.put("detailList", detailList);
			book = createWorkbook(map,fileTemplate);
	
			response.reset();
			response.setHeader("Expires", "0");
			response.setHeader("Pragma", "public");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Cache-Control", "public");
			response.setContentType("application/vnd.ms-excel;charset=utf-8");
			response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF8"));
			os = new BufferedOutputStream(response.getOutputStream());
			book.write(os);
			os.flush();
			os.close();
			return null;
		}catch(Throwable e){
			throw new RuntimeException(e);
		}finally{
			os = null;
		}
			
	}
	
	/**
	 * 下载导入的有效记录列表 csv
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@OperatorPermission(operatPermission = "FO_MASSPAY2BANK_DOWNLOAD")
	public ModelAndView downloadValidListCsv(HttpServletRequest request, HttpServletResponse response)
			throws Exception {
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		Long memberCode = Long.valueOf(loginSession.getMemberCode());
		PrintWriter w = response.getWriter();
		try{
			String fileKy = request.getParameter("fileKy");
			String businessBatchNo = StringUtil.null2String(request.getParameter("businessBatchNo"));
			Long requestSeq = Long.valueOf(fileKy);
			String fileName = "";
			String fileTemplate ="batchpay2bankImpotList.vm";
			String templatePath = "/opt/pay/fo/template";
			BatchPaymentReqBaseInfoDTO reqInfo = batchPaymentReqBaseInfoService.getBatchPaymentReqBaseInfo(requestSeq);
			
			if(reqInfo == null || !reqInfo.getPayerMemberCode().equals(memberCode)){
				throw new RuntimeException();
			}
			reqInfo.setBusinessBatchNo(businessBatchNo);
			
			List<BatchPaymentToBankReqDetailDTO> detailList = null;
			BatchPaymentOrderDTO order = batchPaymentOrderService.getOrder(memberCode, OrderType.BATCHPAY2BANK.getValue(), businessBatchNo);
			if(order!=null && order.getOrderStatus()==OrderStatus.PROCESSED_SUCCESS.getValue()){
				detailList = new ArrayList<BatchPaymentToBankReqDetailDTO>();
				List<FundoutOrderDTO> fundoutOrderList = fundoutOrderService.getChildFundoutOrderList(order.getOrderId(), memberCode);
				BatchPaymentToBankReqDetailDTO detail = null;
				for (FundoutOrderDTO fundoutOrderDTO : fundoutOrderList) {
					detail = buildBatchPaymentToBankReqDetailDTO(fundoutOrderDTO);
					detailList.add(detail);
				}
				fileTemplate = "batchpay2bankResultList.vm";
				fileName = "BatchPaytoBankResult.csv";
			}else{
				detailList = batchPaymentToBankReqDetailService.getValidateDetailList(requestSeq, 1);
				fileName = "ImportValidRecord.csv";
			}
			
			VelocityEngine engine = new VelocityEngine(); 
			Properties properties = new Properties();
			properties.setProperty(VelocityEngine.FILE_RESOURCE_LOADER_PATH,templatePath); 
			engine.init(properties);
			Template t = engine.getTemplate(fileTemplate,"GBK");
			VelocityContext ctx = new VelocityContext();
			ctx.put("businessNo", businessBatchNo);
			ctx.put("detailList", detailList);
	
			response.reset();
			response.setHeader("Expires", "0");
			response.setHeader("Pragma", "public");
			response.setHeader("Cache-Control", "must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Cache-Control", "public");
			response.setContentType("text/plain;charset=GBK");
			response.addHeader("Content-Disposition", "attachment; filename=" + URLEncoder.encode(fileName, "UTF8"));
			t.merge(ctx, w);
			w.flush();
			return null;
		}catch(Throwable e){
			throw new RuntimeException(e);
		}finally{
			try {
		    	if(w != null){
		    		w.close();
		    	}
			} catch (Exception e) {
				w = null;
			}
		}
	}
	
	/**
	 * 构建批量付款到银行请求明细对象
	 * @param dto
	 * @return
	 */
	private BatchPaymentToBankReqDetailDTO buildBatchPaymentToBankReqDetailDTO(FundoutOrderDTO dto){
		
		BatchPaymentToBankReqDetailDTO detail = new BatchPaymentToBankReqDetailDTO();
		detail.setPayeeBankProvinceName(dto.getPayeeBankProvinceName());
		detail.setPayeeBankCityName(dto.getPayeeBankCityName());
		detail.setPayeeBankName(dto.getPayeeBankName());
		detail.setPayeeOpeningBankName(dto.getPayeeOpeningBankName());
		detail.setPayeeName(dto.getPayeeName());
		detail.setPayeeBankAcctCode(dto.getPayeeBankAcctCode()==null?"":dto.getPayeeBankAcctCode()+"\t");
		detail.setRequestAmount(AmountUtils.numberFormat(dto.getOrderAmount()));
		if(dto.getOrderStatus()==OrderStatus.PROCESSED_SUCCESS.getValue()){
			detail.setOrderStatusDesc("成功");
		}else if(dto.getOrderStatus()==OrderStatus.PROCESSED_FAILURE.getValue()){
			detail.setOrderStatusDesc("失败");
		}else if(dto.getOrderStatus()==OrderStatus.PROCESSING.getValue()){
			detail.setOrderStatusDesc("处理中");
		}else if(dto.getOrderStatus()==OrderStatus.REFUND_SUCCESS.getValue()){
			detail.setOrderStatusDesc("已退票");
		}else{
			detail.setOrderStatusDesc(String.valueOf(dto.getOrderStatus()));
		}
		detail.setErrorMsg(dto.getFailedReason());
		detail.setRemark(dto.getPaymentReason());
		detail.setForeignOrderId(dto.getForeignOrderId()==null?"":dto.getForeignOrderId()+"\t");
		
		return detail;
	}
	
	private static Workbook createWorkbook(Map<String, Object> map,String pathAndFile){
		XLSTransformer transformer = new XLSTransformer();
		Workbook workbook = null;
		try {
			workbook = transformer.transformXLS(BatchPay2BankDownloadController.class.getResourceAsStream(pathAndFile),map);
		} catch (Throwable e) {
			throw new RuntimeException(e);
		} 
		return workbook;
	}

}
