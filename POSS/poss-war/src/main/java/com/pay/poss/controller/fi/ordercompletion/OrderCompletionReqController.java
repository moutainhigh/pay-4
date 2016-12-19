/**
 * 
 */
package com.pay.poss.controller.fi.ordercompletion;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.read.biff.BiffException;
import net.sf.json.JSONArray;

import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.fi.fill.model.FillRecordInfo;
import com.pay.fi.fill.model.OrderFillBatch;
import com.pay.fi.fill.service.OrderFillBatchService;
import com.pay.fi.fill.service.OrderFillRecordInfoService;
import com.pay.fi.fill.util.OrderFillBatchStatusEnum;
import com.pay.fi.fill.util.OrderFillRecordStatusEnum;
import com.pay.poss.client.ChannelClientService;
import com.pay.poss.dto.PaymentChannelItemDto;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.ExcelUtils;
import com.pay.util.StringUtil;

/**
 * 补单申请
 * @author PengJiangbo
 *
 */
public class OrderCompletionReqController extends MultiActionController {
	
	private String reqView ;
	private String reqConfirmView ;
	
	private ChannelClientService channelClientService ;
	
	private OrderFillBatchService orderFillBatchService ;
	
	private OrderFillRecordInfoService orderFillRecordInfoService ;
	
	/**
	 * 默认页面
	 * @param request
	 * @param response
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mv = new ModelAndView(reqView) ;
		List paymentChannelItems = extractPaymentChannelItems();
		return mv.addObject("paymentChannelItems", paymentChannelItems) ;
	}

	/**
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	private List extractPaymentChannelItems() {
		PaymentChannelItemDto paymentChannelItem = new PaymentChannelItemDto();
		List paymentChannelItems = channelClientService
				.queryChannelItem(paymentChannelItem);
		return paymentChannelItems;
	}
	
	/**
	 * 下载补单模板文件
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public ModelAndView downloadTempFile(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		String path = this.getServletContext().getRealPath("/WEB-INF/jsp/ordercompletion") ;
		String filename = request.getParameter("filename") ;
		
		FileInputStream fis = new FileInputStream(path + File.separator + filename);
		byte[] bt = new byte[fis.available()] ;
		fis.read(bt) ;
		//设置下载响应信息
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		response.setContentType("UTF-8");
		filename = URLEncoder.encode(filename, "UTF-8") ;
		response.setHeader("Content-Disposition", "attachment;filename=" + new String(filename.getBytes("iso-8859-1"), "UTF-8"));
		response.setContentLength(bt.length);
		ServletOutputStream sos = response.getOutputStream() ;
		sos.write(bt);
		sos.close();
		fis.close();
		return null ;
	}

	/**
	 * 补单文件上传｜预览
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 * @throws BiffException 
	 */
	public ModelAndView doUpload(HttpServletRequest request, HttpServletResponse response) throws BiffException, IOException{
		
		String orgCode = StringUtil.null2String(request.getParameter("orgCode")) ;
		MultipartHttpServletRequest multipartRequest = (MultipartHttpServletRequest) request ;
		//上传的文件
		CommonsMultipartFile file = (CommonsMultipartFile) multipartRequest.getFile("file") ;
		String fileName = file.getOriginalFilename() ;
		Workbook book = Workbook.getWorkbook(file.getInputStream()) ;
		@SuppressWarnings("unchecked")
		List<FillRecordInfo> list = ExcelUtils.getListByReadShell(book.getSheet(0), 1, 0, 5,
				new String[] { "channelOrderNo", "returnNo", "currencyCode",
						"amount", "authorization" }, FillRecordInfo.class);
		//校验
		List<FillRecordInfo> errorList = new ArrayList<FillRecordInfo>() ;
		List<FillRecordInfo> successList = new ArrayList<FillRecordInfo>() ;
		StringBuilder sb = null ;
		for(FillRecordInfo fillRecordInfo : list){
			sb = new StringBuilder() ;
			Long channelOrderNo = fillRecordInfo.getChannelOrderNo() ;
			String returnNo = fillRecordInfo.getReturnNo() ;
			if((null == channelOrderNo || 0L == channelOrderNo) || (null == returnNo || "".equals(channelOrderNo))){
				sb.append("渠道订单号和机构端流水号不能同时为空｜") ;
				fillRecordInfo.setFailReason(sb.toString());
				errorList.add(fillRecordInfo) ;
			}else
				successList.add(fillRecordInfo) ;
		}
		JSONArray jsonArr = JSONArray.fromObject(list);
		List<?> paymentChannelItems = extractPaymentChannelItems() ;
		return new ModelAndView(reqConfirmView)
				.addObject("applyDataS", successList)
				.addObject("applyDataF", errorList)
				.addObject("applyDataA", jsonArr)
				.addObject("orgCode", orgCode)
				.addObject("fileName", fileName)
				.addObject("paymentChannelItems", paymentChannelItems);
	}
	
	/**
	 * 补单申请记录保存
	 * @param request
	 * @param response
	 * @return
	 * @throws UnsupportedEncodingException
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView doApplyData(HttpServletRequest request, HttpServletResponse response) throws UnsupportedEncodingException{
		
		String orgCode = StringUtil.null2String(request.getParameter("orgCode")) ;
		String fileName = StringUtil.null2String(request.getParameter("fileName")) ;
		String ac =  URLDecoder.decode(request.getParameter("applyDataA"), "UTF-8") ;
		JSONArray jsonA = JSONArray.fromObject(ac) ;
		List<FillRecordInfo> fillRecordInfos = JSONArray.toList(jsonA, FillRecordInfo.class) ;
		OrderFillBatch orderFillBatch = new OrderFillBatch() ;
		orderFillBatch.setApplicant(SessionUserHolderUtil.getLoginId());
		orderFillBatch.setApplyAmount(fillRecordInfos.size());
		orderFillBatch.setAuditStatus(OrderFillBatchStatusEnum.orderFillInit.getStatus());
		orderFillBatch.setCreateTime(new Timestamp(new Date().getTime()));
		orderFillBatch.setFileName(fileName);
		orderFillBatch.setOrgCode(orgCode); 
		long reqBatchNo = this.orderFillBatchService.fillBatchSave(orderFillBatch) ;
		if(reqBatchNo > 0){
			for(FillRecordInfo fillRecordInfo : fillRecordInfos){
				fillRecordInfo.setReqBatchNo(reqBatchNo);
				fillRecordInfo.setRecordStatus(OrderFillRecordStatusEnum.init.getCode());
			}
			this.orderFillRecordInfoService.orderFillRecordSave(fillRecordInfos); 
		}
		return new ModelAndView("redirect:/orderCompletionReq.do") ; 
	}
	
	public void setReqView(String reqView) {
		this.reqView = reqView;
	}

	public void setReqConfirmView(String reqConfirmView) {
		this.reqConfirmView = reqConfirmView;
	}
	
	public void setChannelClientService(ChannelClientService channelClientService) {
		this.channelClientService = channelClientService;
	}

	public void setOrderFillBatchService(OrderFillBatchService orderFillBatchService) {
		this.orderFillBatchService = orderFillBatchService;
	}

	public void setOrderFillRecordInfoService(
			OrderFillRecordInfoService orderFillRecordInfoService) {
		this.orderFillRecordInfoService = orderFillRecordInfoService;
	}
	
}
