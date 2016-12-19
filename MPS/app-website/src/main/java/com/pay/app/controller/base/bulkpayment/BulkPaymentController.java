/**
 * 
 */
package com.pay.app.controller.base.bulkpayment;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.math.BigDecimal;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import jxl.Workbook;
import jxl.read.biff.BiffException;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.multipart.MultipartHttpServletRequest;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.acctattrib.service.AcctAttribService;
import com.pay.acc.service.account.AccountBalanceHandlerService;
import com.pay.app.base.common.pagination.PageUtil;
import com.pay.app.base.session.SessionHelper;
import com.pay.batchpay.constant.FoBatchpayConstants;
import com.pay.batchpay.dto.BulkPaymentOrder;
import com.pay.batchpay.dto.BulkPaymentTemplate;
import com.pay.batchpay.dto.OrderTemplateUnion;
import com.pay.batchpay.service.bulkpayment.BulkpayOrderService;
import com.pay.batchpay.service.bulkpayment.BulkpayTemplateService;
import com.pay.batchpay.service.bulkpayment.WithdrawWorkOrderService;
import com.pay.batchpay.service.merchant.MerchantService;
import com.pay.batchpay.util.BulkorderStatusEnum;
import com.pay.batchpay.util.PageParaDataUtil;
import com.pay.batchpay.util.PayResultEnum;
import com.pay.batchpay.util.SimpleDateUtil;
import com.pay.batchpay.util.StringUtils;
import com.pay.fo.order.common.OrderProcessType;
import com.pay.fo.order.common.OrderSmallType;
import com.pay.fo.order.common.OrderStatus;
import com.pay.fo.order.common.OrderType;
import com.pay.fo.order.dto.Order;
import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fo.order.service.batchpayment.BatchPaymentOrderService;
import com.pay.fo.order.service.withdraw.WithdrawOrderService;
import com.pay.fundout.service.ma.AccountInfo;
import com.pay.fundout.service.ma.AccountQueryFacadeService;
import com.pay.fundout.service.ma.MemberInfo;
import com.pay.fundout.service.ma.MemberQueryFacadeService;
import com.pay.inf.dao.Page;
import com.pay.pe.dto.AccountingDto;
import com.pay.pe.service.accounting.AccountingService;
import com.pay.util.ExcelUtils;


/**
 * 批量付款
 * @author PengJiangbo
 *
 */
public class BulkPaymentController extends MultiActionController {

	private static final Log logger = LogFactory.getLog(BulkPaymentController.class) ;
	
	private static final String BULKPAYMENT_EXCEL_DOWN_NAME = "商户批量付款" ;
	
	private String bulkPaymentTemplate;
	
	private String queryView;
	
	private String checkView;
	
	private String excelBulkpayQueryBusiness ;
	
	//批次订单服务
	private BulkpayOrderService bulkpayOrderService ;
	
	//批量付款模板服务
	private BulkpayTemplateService bulkpayTemplateService ;
	
	//获取商户信息服务
	private MerchantService merchantService ;
	
	//提现工单[批付记录]服务
	private WithdrawWorkOrderService withdrawWorkOrderService ;
	
	//会员信息服务类
	private MemberQueryFacadeService memberQueryFacadeService ;
	
	//账户信息服务类
	private AccountQueryFacadeService accountQueryFacadeService ;
	
	private AccountingService accountingService ; 
	
	//账户属性服务类
	private AcctAttribService acctAttribService ;
	
	//账户余额处理
	private AccountBalanceHandlerService accountBalanceHandlerService ;
	
	private WithdrawOrderService withdrawOrderService;
	
	//批量付款订单服务类
	private BatchPaymentOrderService batchPaymentOrderService ;
	
	/**
	 * 批量付款模板
	 * @param req
	 * @param resp
	 * @return
	 * @throws Exception
	 */
	public ModelAndView index(HttpServletRequest req,
			HttpServletResponse resp) throws Exception{
		return new ModelAndView(bulkPaymentTemplate);
	}
	
	/**
	 * 到达批量付款记录查询页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toQueryBulkHistory(HttpServletRequest request, HttpServletResponse response){
		
		return new ModelAndView(queryView) ;
	}
	
	/**
	 * 到达批量付款审核页面
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView toBulkPaymentCheck(HttpServletRequest request, HttpServletResponse response){
		
		return new ModelAndView(checkView) ;
	}
	
	/**
	 *下载批量付款模板文件 
	 * @throws FileNotFoundException 
	 */
	public ModelAndView downLoad(HttpServletRequest request, HttpServletResponse response) throws Exception{
		request.setCharacterEncoding("UTF-8");
		
		String path = this.getServletContext().getRealPath("/ftl/gcpayment/bulkpayment/template") ; 
		
		String filename = request.getParameter("filename") ;
		
		FileInputStream fis = new FileInputStream(path + File.separator + filename) ;
		byte[] bt = new byte[fis.available()] ;
		
		fis.read(bt);
		response.setContentType("application/vnd.ms-excel;charset=UTF-8");
		
		response.setContentType("UTF-8");
		
		filename = URLEncoder.encode(filename, "UTF-8") ;
		response.setHeader("Content-Disposition", "attachment; filename="
				    + new String(filename.getBytes("UTF-8"), "GBK"));
		response.setContentLength(bt.length);
		
		ServletOutputStream sos = response.getOutputStream() ;
		sos.write(bt);
		sos.close();
		fis.close();
		return null ;
	}
	
	/**
	 * 批量插入模板数据
	 * @param request
	 * @param response
	 * @return
	 * @throws BiffException
	 * @throws IOException
	 */
	@SuppressWarnings("unchecked")
	//@Transactional(rollbackFor=Exception.class) 
	public ModelAndView templateAdd(HttpServletRequest request, HttpServletResponse response) throws BiffException, IOException {
		ModelAndView mv = new ModelAndView(bulkPaymentTemplate) ;
		//上传的文件
		CommonsMultipartFile file = null;
		List<BulkPaymentTemplate> list = null;
		try {
			MultipartHttpServletRequest multipartRequest = null;
			try {
				multipartRequest = (MultipartHttpServletRequest) request;
			} catch (Exception e) {
				logger.info(e.getMessage());
				return mv ;
			}
			file = (CommonsMultipartFile) multipartRequest.getFile("file");
			Workbook book = Workbook.getWorkbook(file.getInputStream());
			list = ExcelUtils.getListByReadShell(
					book.getSheet(0), 1, 0, 11, new String[] { "toAccountName",
							"bankName", "branchBankName", "siCode",
							"bankAddress", "bankAccount", "accountType","accountTypeCode", "payAmount", "currency","currencyCode" },
							BulkPaymentTemplate.class);
		} catch (Exception e) {
			if(logger.isInfoEnabled()){
				logger.info(e.getMessage());
				logger.info("模板数据解析异常！");
			}
			return mv.addObject("dataAnalysisError", "数据解析错误，请严格按照模板填写数据！") ;
		}
		
		//上传模板文件为空
		if(list.size() == 0){
			return mv.addObject("templateError", "模板文件数据不能为空") ;
		}
		StringBuilder sb = excelDataNotNullValid(list);
		String notNullMsg = sb.toString() ;
		if(notNullMsg.substring(notNullMsg.indexOf("必填项不能丢失") + 2).contains("必填")) {
			return mv.addObject("notNullMsg", notNullMsg.toString()) ;
		}
		
		String fileName = file.getOriginalFilename() ;
		String timestamp = SimpleDateUtil.getTimeStamp(new Date()) ;
		fileName = fileName.substring(0, fileName.lastIndexOf(".")) + timestamp + ".xls" ;
		//定义上路径
		String memberCode = SessionHelper.getMemeberCodeBySession() ;
		String realPath = request.getSession().getServletContext().getRealPath(File.separator + FoBatchpayConstants.UPLOAD_REALPATH_SUFFIX_FIRST) ;
		String path = realPath + File.separator + memberCode + File.separator + fileName ;
		//父级目录不存在，则创建
		if(!new File(path).getParentFile().exists()){
			new File(path).mkdirs() ;
		}
		File localFile = new File(path) ;
		file.transferTo(localFile);
		
		//String dbSavePath = path.substring(path.indexOf(File.separator + FoBatchpayConstants.UPLOAD_REALPATH_SUFFIX_FIRST)) ;
		String dbSavePath = path ;
		
		BulkPaymentOrder bulkPaymentOrder = new BulkPaymentOrder() ;
		String bulkpayNo = SimpleDateUtil.getTimeStamp(new Date()) ;	
		bulkPaymentOrder.setBulkpayNo(bulkpayNo); 						
		bulkPaymentOrder.setCreateTime(new Date());
		long memberCodeL = Long.valueOf(SessionHelper.getMemeberCodeBySession()) ;
		bulkPaymentOrder.setCreator(memberCodeL);
		//商户名
		String loginName = this.merchantService.findMerchantNameByMemberCode(memberCodeL) ;
		bulkPaymentOrder.setLoginName(loginName);
		bulkPaymentOrder.setFileName(file.getOriginalFilename());
		bulkPaymentOrder.setFilePath(dbSavePath);
		bulkPaymentOrder.setStatus(0);
		Long result = this.bulkpayOrderService.insertBulkpayOrder(bulkPaymentOrder) ;
		Map<String, Object> map = null ;
		
		Map<String, Object> mapForTem = null ;
		//登录名
		//LoginSession loginSession = SessionHelper.getLoginSession() ;
		//String emLoginName = loginSession.getLoginName() ;
		MemberInfo payer = memberQueryFacadeService.getMemberInfo(memberCodeL);
		AccountInfo payerAcct = accountQueryFacadeService.getAccountInfo(memberCodeL, 101);
		//order.setPayerAcctCode(payerAcct.getAcctCode());
		String acctCode = payerAcct.getAcctCode() ;
		if( result > 0 ){
			mapForTem = new HashMap<String, Object>() ;
			mapForTem.put("bulkpayNo", bulkpayNo) ;
			mapForTem.put("bulkPaymentTemplateList", list) ;
			//默认为付款中
			mapForTem.put("payResult", PayResultEnum.paying.getProcessStatus()) ;	
			//批付订单模板数据
			this.bulkpayTemplateService.insertBulkpayBatch(mapForTem);
			//------------------------------------------------------
			//----------------------------------------以下部分数据需要根据业务逻辑获取-----------------------------
			//付款金额 以厘为单位 
			List<BulkPaymentTemplate> listToFundoutOrder = new ArrayList<BulkPaymentTemplate>() ;
			for(BulkPaymentTemplate bt : list){
				bt.setPayAmount(bt.getPayAmount().multiply(new BigDecimal(1000)));
				listToFundoutOrder.add(bt) ;
			}
			
			map = bulkpaymentTempInfo(bulkpayNo, memberCodeL, payer, acctCode,
					listToFundoutOrder);
			//------------------------------------------根据业务逻辑获取相应数据结束-------------------------------
			this.bulkpayTemplateService.insertBulkpayFundoutBatch(map);
			
			//更新余额
			
			
			//存储订单、更新余额、发送消息
			/*FundoutOrderDTO order = buildOrder(list, payer, acctCode) ;
			withdrawOrderService.createOrder(order);*/
			
//			try {
//				if (null == accountingService) {
//					logger.info("当前请求处理类型为【" + order.getProcessType().getDesc()
//							+ "】，将不进行更新余额操作。");
//					//return;
//				}
//				// 更新余额
//				if (1 == accountingService
//						.doAccountingReturn(buildAccountingDto(order))) {
//					logger.info("当前请求处理类型为【" + order.getProcessType().getDesc()
//							+ "】，更新余额成功。");
//				} else {
//					throw new RuntimeException("更新余额失败");
//				}
//
//				// 更新订单状态
//				/*if (!updateOrderStatus(order)) {
//					throw new RuntimeException("更新订单状态失败");
//				}*/
//
//			} catch (Throwable e) {
//				logger.error("更新余额发生异常", e);			//、更新订单状态失败或
//				throw new AppException("更新余额发生异常");	//、更新订单状态失败或
//			}
			//-------------------------------------------------------------------------------------------
			
		}
		return mv.addObject("operateSuccess", "操作成功！");
	}

	/**
	 * @param bulkpayNo
	 * @param memberCodeL
	 * @param payer
	 * @param acctCode
	 * @param listToFundoutOrder
	 * @return
	 */
	private Map<String, Object> bulkpaymentTempInfo(String bulkpayNo,
			long memberCodeL, MemberInfo payer, String acctCode,
			List<BulkPaymentTemplate> listToFundoutOrder) {
		Map<String, Object> map;
		map = new HashMap<String, Object>();
		map.put("bulkpayNo", bulkpayNo) ;
		map.put("bulkPaymentTemplateList", listToFundoutOrder) ;
		//map.put("bulkpayNo", bulkpayNo) ;
		//this.bulkpayTemplateService.insertBulkpayBatch(map);
		
		//orderId
		map.put("orderType", 4) ;
		map.put("orderSmallType", 401) ;
		map.put("orderStatus", 101) ;			//未出批次
		//orderAmount
		map.put("isPayerPayFee", 1) ;
		map.put("fee", 0) ;
		//realpayAmount
		//realoutAmount
		//付款者姓名
		map.put("payerName", this.merchantService.findMerchantNameByMemberCode(memberCodeL) +"测试！") ;		
		
		map.put("payerLoginName", payer.getLoginName()) ;
		map.put("payerMemberCode", memberCodeL);
		map.put("payerMemberType", payer.getMemberType()) ;	//会员类型
		
		map.put("payerAcctCode", acctCode) ;
		map.put("payerAcctType", 101) ;		//付款方账号类型
		//payeeName
		//payeeBankAcctCode
		map.put("payeeBankAcctType", 1) ;
		map.put("payeeBankCode", "000收") ;
		//payeeBankName
		
		//payeeOpeningBankName
		map.put("payeeBankProvince", "2901") ;
		map.put("payeeBankProvinceName", "上海市") ;
		map.put("payeeBankCity", "2901") ;
		map.put("payeeBankCityName", "上海市") ;
		map.put("paymentReason", "") ;				//对应页面交易备注
		map.put("failedReason", "") ;			//对应页面滞留原因
		map.put("createDate", new Date()) ;
		/*updateDate*/
		map.put("tradeAlias", OrderSmallType.COMMON_BATCHPAY2BANK.getDesc()) ;				//"批量付款记录"
		
		map.put("tradeType", 1) ;
		map.put("payeeMobile", "") ;
		map.put("bankOrderId", "") ;
		map.put("foreignOrderId", "") ;
		map.put("fundoutBankCode", 0001) ;
		map.put("fundoutBankName", "出款行名称") ;
		map.put("fundoutMode", 1) ;
		map.put("priority", 5) ;
		map.put("parentOrderId", null) ;
		map.put("bankNumber", "银行联行号") ;
		
		map.put("riskLevelCode", 200) ;
		map.put("accountMode", "0") ;
		map.put("isLoaning", 0) ;
		map.put("channelCode", "渠道编号") ;
		return map;
	}
	
	/**
	 * 
	 * @param list
	 * @param payer
	 * @param acctCode
	 * @return
	 */
	public FundoutOrderDTO buildOrder(List<BulkPaymentTemplate> list, MemberInfo payer, String acctCode){
		FundoutOrderDTO fundoutOrderDTO = new FundoutOrderDTO() ;
		fundoutOrderDTO.setOrderType(0);			//OrderType.BATCHPAY2BANK.getValue()				
		fundoutOrderDTO.setOrderSmallType(OrderSmallType.COMMON_BATCHPAY2BANK.getValue());
		fundoutOrderDTO.setOrderStatus(OrderStatus.INIT.getValue());
		BigDecimal orderAmount = new BigDecimal(0) ;
		for(BulkPaymentTemplate bpt : list){
			orderAmount = orderAmount.add(bpt.getPayAmount()) ;
		}
		
		String orderAmountStr = orderAmount.toString() ;
		fundoutOrderDTO.setOrderAmount(Long.valueOf(orderAmountStr));
		fundoutOrderDTO.setIsPayerPayFee(1);
		fundoutOrderDTO.setFee(0L);
		fundoutOrderDTO.setRealpayAmount(Long.valueOf(orderAmountStr));
		fundoutOrderDTO.setRealoutAmount(Long.valueOf(orderAmountStr));
		
		fundoutOrderDTO.setPayerName(payer.getMemberName());
		fundoutOrderDTO.setPayerLoginName(payer.getLoginName());
		fundoutOrderDTO.setPayerMemberCode(payer.getMemberCode());
		fundoutOrderDTO.setPayerMemberType(payer.getMemberType());
		fundoutOrderDTO.setPayerAcctCode(acctCode);	
		fundoutOrderDTO.setPayerAcctType(101);		//暂时，人民币
		fundoutOrderDTO.setPayeeName("测试吧");
		fundoutOrderDTO.setPayeeBankAcctCode("6215423654791111");
		fundoutOrderDTO.setPayeeBankAcctType(1);
		fundoutOrderDTO.setPayeeBankCode("10003001");
		fundoutOrderDTO.setPayeeBankName("中国银行");
		fundoutOrderDTO.setPayeeBankProvince("2900");
		fundoutOrderDTO.setPayeeBankCity("2900");
		fundoutOrderDTO.setCreateDate(new Date());
		fundoutOrderDTO.setTradeAlias(OrderSmallType.COMMON_BATCHPAY2BANK.getDesc());
		fundoutOrderDTO.setTradeType(OrderType.BATCHPAY2BANK.getValue());
		fundoutOrderDTO.setFundoutMode(1);				//手动
		fundoutOrderDTO.setPriority(5);
		fundoutOrderDTO.setBankNumber("104290030012");
		
		
		fundoutOrderDTO.setProcessType(OrderProcessType.COMMON_BATCHPAY2BANK_REQ);
		
		return fundoutOrderDTO ;
	}
	
	private FundoutOrderDTO init(Order order) {
		if (!(order instanceof FundoutOrderDTO)) {
			throw new RuntimeException("传入订单对象不匹配");
		}
		return (FundoutOrderDTO) order;
	}
	
	/**
	 * 
	 * @param order
	 * @return
	 */
	protected AccountingDto buildAccountingDto(Order order) {

		AccountingDto acctDto = new AccountingDto();

		FundoutOrderDTO dto = init(order);

		String acctCode = dto.getPayerAcctCode();
		try {
			AcctAttribDto acctAttribDto = acctAttribService
					.queryAcctAttribWithAcctCode(acctCode);
			System.out.println("acctAttribDto");
			System.out.println(acctAttribDto);
			System.out.println("acctAttribDto");
			acctDto.setPayerCurrencyCode(acctAttribDto.getCurCode());
			acctDto.setPayeeCurrencyCode(acctAttribDto.getCurCode());
			acctDto.setPayerAcctType(acctAttribDto.getAcctType());
		} catch (Exception e) {
			logger.error("error:", e);
		}

		acctDto.setAmount(dto.getRealoutAmount());
		//acctDto.setBankCode(String.valueOf(dto.getFundoutBankCode()));
		acctDto.setOrderAmount(dto.getOrderAmount());
		acctDto.setOrderId(dto.getOrderId());
		acctDto.setHasCaculatedPrice(true);
		if (dto.getIsPayerPayFee() == 1) {
			acctDto.setPayerFee(dto.getFee());
		} else {
			acctDto.setPayeeFee(dto.getFee());
		}

		if (dto.getProcessType()
				.getValue()
				.equalsIgnoreCase(
						OrderProcessType.COMMON_BATCHPAY2BANK_REQ.getValue())) {
			//acctDto.setBusinessType(PayForEnum.FO_WITHDRAW.getCode());
			acctDto.setPayer(dto.getPayerMemberCode());
		} else if (dto
				.getProcessType()
				.getValue()
				.equalsIgnoreCase(
						OrderProcessType.COMMON_BATCHPAY2BANK_FAIL.getValue())) {
			acctDto.setPayee(dto.getPayerMemberCode());
			//acctDto.setBusinessType(PayForEnum.FO_WITHDRAW_BACK.getCode());
		} else if (dto
				.getProcessType()
				.getValue()
				.equalsIgnoreCase(
						OrderProcessType.COMMON_BATCHPAY2BANK_SUCCESS.getValue())) {
			//acctDto.setBusinessType(PayForEnum.FO_WITHDRAW.getCode());
		} else if (dto
				.getProcessType()
				.getValue()
				.equalsIgnoreCase(
						OrderProcessType.COMMON_BATCHPAY2BANK_REFUND.getValue())) {
			acctDto.setPayee(dto.getPayerMemberCode());
			acctDto.setOrderId(dto.getRefundOrderId());
			//acctDto.setBusinessType(PayForEnum.FO_WITHDRAW_REFUND_TICKET.getCode());
		}

		return acctDto;
	}
	
	/**
	 * 简单的excel模板数据非空校验
	 * @param list
	 * @return
	 */
	private StringBuilder excelDataNotNullValid(List<BulkPaymentTemplate> list) {
		//非空时，校验必填项
		StringBuilder sb = new StringBuilder("模板文件中必填项不能丢失:\\n'") ;
		String sbStr = "" ;
		for(BulkPaymentTemplate bpt : list){
			//sb.toString();
			sbStr = sb.toString() ;
			if(null == bpt.getToAccountName() || "".equals(bpt.getToAccountName())){
				if(!sbStr.contains("款人姓名必填！")){
					sb.append("+'收款人姓名必填！\\n'");
				}
			}
			if(null == bpt.getBankName() || "".equals(bpt.getBankName())){
				if(!sbStr.contains("银行名称必填！")){
					sb.append("+'银行名称必填！\\n'");
				}
			}
			if(null == bpt.getBankAccount() || "".equals(bpt.getBankAccount())){
				if(!sbStr.contains("收款人银行账户必填！")){
					sb.append("+'收款人银行账户必填！\\n'");
				}
			}
			if(null == bpt.getPayAmount() || "".equals(bpt.getPayAmount())){
				if(!sbStr.contains("付款金额必填！")){
					sb.append("+'付款金额必填！\\n'") ;
				}
			}
		}
		return sb;
	}
	
	/**
	 * 批量付款记录查询
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView queryBulkOrderDetail(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mv = null ;
		
		String beginTime = StringUtils.isNotNull(request.getParameter("beginTime")) ;
		String endTime = StringUtils.isNotNull(request.getParameter("endTime")) ;
		
		String status = StringUtils.isNotNull(request.getParameter("status")) ;
		String bulkpayNo = StringUtils.isNotNull(request.getParameter("bulkpayNo")) ;
		String fileName = StringUtils.isNotNull(request.getParameter("fileName")) ;
		
		//--------------------------------------分页功能实现
		Page<?> page = PageParaDataUtil.pagePara(request);
		//-------------------------------------------分页实现---------------------－
		
		Map<String, Object> hMap = new HashMap<String, Object>() ;
		if(!"".equals(beginTime) && !"".equals(endTime)){
			hMap.put("timeQuery", true) ;
			hMap.put("beginTime", beginTime) ;
			hMap.put("endTime", endTime) ;
		}
		hMap.put("status", status) ;
		hMap.put("bulkpayNo", bulkpayNo) ;
		hMap.put("fileName", fileName) ;
		
		List<OrderTemplateUnion> bulkOrderResult = this.bulkpayOrderService.findBulkpayOrder(hMap) ;
		
		//----------------------------分页功能实现----------------------------------
		page = PageParaDataUtil.pageUtilBean(page, bulkOrderResult);
		
		bulkOrderResult = (List<OrderTemplateUnion>) PageParaDataUtil.pageUtilData(page, bulkOrderResult);
		
		PageUtil pu = new PageUtil(page.getPageNo(), page.getPageSize(),
				page.getTotalRecord());// 分页处理
		//---------------------------分页功能实现--------------------------------
		bulkOrderResult = extractResultDes(bulkOrderResult);
		
		mv = new ModelAndView(queryView).addObject("bulkOrderResult", bulkOrderResult).addObject("pu", pu) ;
		return mv ;
	}

	/**
	 * 解析抽取付款结果描述
	 * @param bulkOrderResult
	 */
	private List<OrderTemplateUnion> extractResultDes(List<OrderTemplateUnion> bulkOrderResult) {
		int listsLen = bulkOrderResult.size() ;
		List<OrderTemplateUnion> lt = new ArrayList<OrderTemplateUnion>() ;
		for(int i=0; i<listsLen; i++){
			String pwd = bulkOrderResult.get(i).getResultDes() ;
			String pwdArr[] = pwd.split(",") ;
			StringBuilder sb = new StringBuilder() ;
			for(int j=0; j<pwdArr.length; j++){
				String pwdArrEv = pwdArr[j] ;
				if(pwdArr[j].contains("_0")){
					sb.append(pwdArrEv.substring(0, pwdArrEv.indexOf("_"))).append("笔付款成功").append("<br/>") ;
				}else if(pwdArr[j].contains("_1")){
					sb.append(pwdArrEv.substring(0, pwdArrEv.indexOf("_"))).append("笔付款中").append("<br/>") ;
				}else if(pwdArr[j].contains("_2")){
					sb.append(pwdArrEv.substring(0, pwdArrEv.indexOf("_"))).append("付款失败").append("<br/>") ;
				}
			}
			bulkOrderResult.get(i).setResultDes(sb.toString());
			lt.add(bulkOrderResult.get(i)) ;
		}
		return lt ;
	}

	/**
	 * 废弃批次
	 * @param rquest
	 * @param response
	 * @return
	 */
	public ModelAndView abandonBulkOrderRecord(HttpServletRequest request, HttpServletResponse response){
		long bulkOrderId = StringUtils.stringToLong(request.getParameter("bulkOrderId")) ;
		Map<String, Object> hMap = new HashMap<String, Object>() ;
		hMap.put("newStatus", BulkorderStatusEnum.anandoned.getStatus()) ;
		hMap.put("id", bulkOrderId) ;
		this.bulkpayOrderService.updateBulkordedrStatus(hMap) ;
		return new ModelAndView("redirect:/corp/bulkPayment.htm?method=queryBulkOrderDetail") ;
	}
	
	
	/**
	 * 批量付款审核－－查询
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView checkBulkOrderQuery(HttpServletRequest request, HttpServletResponse response){
		ModelAndView mv = new ModelAndView(checkView) ;
		String beginTime= StringUtils.isNotNull(request.getParameter("beginTime")) ;
		String endTime = StringUtils.isNotNull(request.getParameter("endTime")) ;
		//--------------------------------------分页功能实现
		Page<?> page = PageParaDataUtil.pagePara(request);
		//-------------------------------------------分页实现----------------------
		Map<String, Object> hMap = new HashMap<String, Object>() ;
		if(!"".equals(beginTime) && !"".equals(endTime)){
			hMap.put("timeQuery", true) ;
			hMap.put("beginTime", beginTime) ;
			hMap.put("endTime", endTime) ;
		}
		List<OrderTemplateUnion> listResult = this.bulkpayOrderService.findBulkpayOrder(hMap) ;
		
		//----------------------------分页功能实现----------------------------------
		page = PageParaDataUtil.pageUtilBean(page, listResult);
		
		listResult = (List<OrderTemplateUnion>) PageParaDataUtil.pageUtilData(page, listResult);
		
		PageUtil pu = new PageUtil(page.getPageNo(), page.getPageSize(),
				page.getTotalRecord());// 分页处理
		//---------------------------分页功能实现--------------------------------
		mv.addObject("beginTime", beginTime).addObject("endTime", endTime) ;			//页面参数回传
		mv.addObject("listResult", listResult).addObject("pu", pu) ;
		
		return mv ;
	}
	
	/**
	 * 批量付款结果下载excel
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView downLoadBulkpayResult(HttpServletRequest request, HttpServletResponse response){
		try {
			String bulkpayNo = StringUtils.isNotNull(request.getParameter("bulkpayNo")) ;
			logger.info("request parameter bulkpayNo......" + bulkpayNo);
			List<BulkPaymentTemplate> resultList = this.bulkpayTemplateService.findBulkpayByBulkorderNo(bulkpayNo) ;
			Map<String, Object> paraMap = new HashMap<String, Object>() ;
			String nowDate = SimpleDateUtil.yyyyMMddFormat(new Date()) ;
			paraMap.put("nowDate", nowDate) ;
			
			//下载格式设置
			ModelAndView mv = new ModelAndView(excelBulkpayQueryBusiness) ;
			response.setContentType("application/vnd.ms-excel;charset=UTF-8");
			response.setHeader("Content-Transfer-Encoding", "binary");
			response.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
			response.setHeader("Pragma", "public");
			String agent = request.getHeader("User-Agent") ;
			String fileName = BULKPAYMENT_EXCEL_DOWN_NAME ;
			String timeStamp = SimpleDateUtil.getTimeStamp(new Date()) ;
			fileName += timeStamp ;
			if (agent.contains("MSIE")) {
				response.setHeader("Content-Disposition","attachment;filename="
						+ URLEncoder.encode(fileName, "UTF-8") + ".xls");
			}else {
				 response.setHeader("Content-Disposition","attachment;filename="
						+ new String((fileName + ".xls").getBytes("UTF-8"), "ISO8859_1"));
			}
			paraMap.put("resultList", resultList) ;
			mv.addAllObjects(paraMap) ;
			return mv ;
		} catch (Exception e) {
			e.printStackTrace();
			//return new ModelAndView("xxx") ;
			return null ;
		}
	}
	/************set****************/
	public void setBulkPaymentTemplate(String bulkPaymentTemplate) {
		this.bulkPaymentTemplate = bulkPaymentTemplate;
	}

	public void setQueryView(String queryView) {
		this.queryView = queryView;
	}

	public void setCheckView(String checkView) {
		this.checkView = checkView;
	}

	public void setBulkpayOrderService(BulkpayOrderService bulkpayOrderService) {
		this.bulkpayOrderService = bulkpayOrderService;
	}

	public void setBulkpayTemplateService(
			BulkpayTemplateService bulkpayTemplateService) {
		this.bulkpayTemplateService = bulkpayTemplateService;
	}

	public void setExcelBulkpayQueryBusiness(String excelBulkpayQueryBusiness) {
		this.excelBulkpayQueryBusiness = excelBulkpayQueryBusiness;
	}

	public void setMerchantService(MerchantService merchantService) {
		this.merchantService = merchantService;
	}

	public void setWithdrawWorkOrderService(
			WithdrawWorkOrderService withdrawWorkOrderService) {
		this.withdrawWorkOrderService = withdrawWorkOrderService;
	}

	public void setMemberQueryFacadeService(
			MemberQueryFacadeService memberQueryFacadeService) {
		this.memberQueryFacadeService = memberQueryFacadeService;
	}

	public void setAccountQueryFacadeService(
			AccountQueryFacadeService accountQueryFacadeService) {
		this.accountQueryFacadeService = accountQueryFacadeService;
	}
	
	public void setAccountingService(AccountingService accountingService) {
		this.accountingService = accountingService;
	}

	public void setAcctAttribService(AcctAttribService acctAttribService) {
		this.acctAttribService = acctAttribService;
	}

	public void setWithdrawOrderService(WithdrawOrderService withdrawOrderService) {
		this.withdrawOrderService = withdrawOrderService;
	}

	public void setBatchPaymentOrderService(
			BatchPaymentOrderService batchPaymentOrderService) {
		this.batchPaymentOrderService = batchPaymentOrderService;
	}
	
}
