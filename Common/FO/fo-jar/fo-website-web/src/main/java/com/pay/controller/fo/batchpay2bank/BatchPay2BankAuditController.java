/**
 * 
 */
package com.pay.controller.fo.batchpay2bank;

import java.util.UUID;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.pay.app.base.api.annotation.OperatorPermission;
import com.pay.app.base.api.wrapper.SafeControllerWrapper;
import com.pay.app.base.common.pagination.PageUtil;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.fo.order.common.OrderType;
import com.pay.fo.order.dto.batchpayment.BatchPaymentReqBaseInfoDTO;
import com.pay.fo.order.model.batchpayment.BatchPaymentReqBaseQueryInfo;
import com.pay.inf.dao.Page;
import com.pay.util.DateUtil;
import com.pay.util.StringUtil;

/**
 * @author NEW
 *
 */
public class BatchPay2BankAuditController extends
		AbstractBatchPay2BankController {
	private static final String TIME_FMT = "yyyy-MM-dd";
	
	private String indexView;
	
	private String listView;
	
	private String detailView;
	
	private String errorView;
	
	private String successView;
	
	//操作员权限控制更改Jiangbo.Peng
	//@OperatorPermission(operatPermission = "FO_MASSPAY2BANK_CHECK")
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response, BatchPay2BankCommand command)
			throws Exception {
		SessionHelper.getLoginSession(request);
		ModelAndView view = new ModelAndView(indexView);
		String message = command.getErrorMsg();
		if(StringUtil.isNull(command.getBeginDate())){
			command.setBeginDate(DateUtil.getTime(TIME_FMT, -30));
		}
		if(StringUtil.isNull(command.getEndDate())){
			command.setEndDate(DateUtil.getNowDate(TIME_FMT));
		}
		command.setStatus(1);
		return view.addObject("command", command).addObject("message", StringUtil.null2String(message));
	}
	
	//操作员权限控制更改Jiangbo.Peng
	//@OperatorPermission(operatPermission = "FO_MASSPAY2BANK_CHECK")
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response, BatchPay2BankCommand command)
			throws Exception {
		LoginSession loginSession =  SessionHelper.getLoginSession(request);
		ModelAndView view = new ModelAndView(indexView);
		try{
			long memberCode = Long.valueOf(loginSession.getMemberCode());
			
			String message = command.getErrorMsg();
			BatchPaymentReqBaseQueryInfo auditQueryInfo = new BatchPaymentReqBaseQueryInfo();
			auditQueryInfo.setPayerMemberCode(memberCode);
			if(command.getStatus()>=1){
				auditQueryInfo.setStatus(command.getStatus());
			}
			
			auditQueryInfo.setRequestType(OrderType.BATCHPAY2BANK.getValue());
			String beginDate = StringUtil.null2String(command.getBeginDate());
			if(!StringUtil.isNull(beginDate)){
				auditQueryInfo.setBeginDate(DateUtil.strToDate(beginDate+" 00:00:00", "yyyy-MM-dd HH:mm:ss"));
			}
			String endDate = StringUtil.null2String(command.getEndDate());
			if(!StringUtil.isNull(endDate)){
				auditQueryInfo.setEndDate(DateUtil.strToDate(endDate+" 23:59:59", "yyyy-MM-dd HH:mm:ss"));
			}
			if(!StringUtil.isNull(command.getBusinessBatchNo())){
				auditQueryInfo.setBusinessBatchNo(command.getBusinessBatchNo());
			}
			int pager_offset = 1;
			if (StringUtils.isNumeric(request.getParameter("pager_offset"))) {
				pager_offset = Integer.parseInt(request.getParameter("pager_offset"));
			}
			
			Page<BatchPaymentReqBaseQueryInfo>  page = new Page<BatchPaymentReqBaseQueryInfo>();
			page.setPageNo(pager_offset);
			page.setPageSize(10);
			
			page = batchPaymentAuditQueryService.queryBatchPaymentAuditList(page, auditQueryInfo);
			PageUtil pageUtil = new PageUtil(pager_offset, page.getPageSize(), page.getTotalCount());
			view.addObject("auditQueryInfoList", page.getResult());
			view.addObject("pu", pageUtil);
			view.addObject("message", StringUtil.null2String(message));
			view.addObject("command", command);
		}catch(Exception e){
			log.error("加载审核列表发生异常", e);
			view.setViewName(errorView);
		}
		
		return view;
	}
	
	//操作员权限控制更改Jiangbo.Peng
	//@OperatorPermission(operatPermission = "FO_MASSPAY2BANK_CHECK")
	public ModelAndView detail(HttpServletRequest request,
			HttpServletResponse response, BatchPay2BankCommand command)
			throws Exception {
		ModelAndView view = new ModelAndView(detailView);
		try{
			String orderIdStr = StringUtil.null2String(request.getParameter("requestSeq"));
			long orderId = 0L;
			String message = null;
			if(StringUtil.isNull(orderIdStr)){
				message = "请选择要审核的记录";
			}
			if(!StringUtil.isNull(message)&&StringUtils.isNumeric(orderIdStr)){
				message = "无效的请求";
			}else{
				orderId = Long.valueOf(orderIdStr);
			}
			
			LoginSession loginSession = SessionHelper.getLoginSession(request);
			long memberCode = Long.valueOf(loginSession.getMemberCode());
			
			if(StringUtil.isNull(message)){
				BatchPaymentReqBaseQueryInfo info = getBatchPaymentReqBaseQueryInfo(orderId, memberCode);
				if(info==null){
					message = "无效的请求";
				}else{
					view.addObject("info", info);
				}
			}
			if(!StringUtil.isNull(message)){
				command.setErrorMsg(message);
				return index(request, response, command);
			}
			setBatchPay2BankToken(request, command, "audit");
			return view.addObject("command", command).addObject("token", command.getToken());
		}catch(Exception e){
			view.setViewName(errorView);
			return view;
		}
	}
	
	//操作员权限控制更改Jiangbo.Peng
	//@OperatorPermission(operatPermission = "FO_MASSPAY2BANK_CHECK")
	public ModelAndView audit(HttpServletRequest request,
			HttpServletResponse response, BatchPay2BankCommand command)
			throws Exception {
		LoginSession loginSession = SessionHelper.getLoginSession(request);
		long memberCode = Long.valueOf(loginSession.getMemberCode());
		ModelAndView view = new ModelAndView(successView);
		int workOrderStatus = 2;
		synchronized (loginSession.getMemberCode()) {
			try{
				
				if(!validateToken(request)){
					return index(request, response, command);
				}
				
				String message = null;
				//验证请求类型
				String op = StringUtil.null2String(request.getParameter("op"));
				if(StringUtil.isNull(op)){
					message = "无效的请求";
				}else if(!"agree".equalsIgnoreCase(op)&&!"reject".equalsIgnoreCase(op)){
					message = "无效的请求";
				}
				//验证备注信息
				if(StringUtil.isNull(message)){
					if(!StringUtil.isNull(command.getAuditRemark())&&(command.getAuditRemark().contains("<")||command.getAuditRemark().contains("%3c"))){
						message =  "请输入有效的备注";
					}else if(!BatchPay2BankValidateUtils.vertifyReason(StringUtil.null2String(command.getAuditRemark()))){
						message = "请输入有效的备注";
					}
				}
				if(StringUtil.isNull(message)&&"reject".equalsIgnoreCase(op)){
					workOrderStatus = 3;
					if(StringUtil.isNull(command.getAuditRemark())){
						message = "拒绝操作必须填写备注信息";
					}
				}
				
				BatchPaymentReqBaseInfoDTO dto = null;
				if(StringUtil.isNull(message)){
					dto = batchPaymentReqBaseInfoService.getBatchPaymentReqBaseInfo(memberCode, OrderType.BATCHPAY2BANK.getValue(), command.getBusinessBatchNo());
				  if(dto==null){
					  message = "无效的请求";
				  }else if(dto.getStatus()!=1){
					  message = "请求已被处理";
				  }else if(loginSession.getOperatorIdentity().equalsIgnoreCase(dto.getCreator())){
					  message = "复核操作员与申请操作员不能是同一人";
				  }
				}
				
				if(!StringUtil.isNull(message)){
					view.addObject("message", message);
				}
				
				//验证密码
				if(StringUtil.isNull(message)){
					SafeControllerWrapper safeControllerWrapper = new SafeControllerWrapper(request, new String[] { "payPwd" });
					String payPwd = safeControllerWrapper.getParameter("payPwd");
					System.out.println("paymentValidateService:" + paymentValidateService );
					message = paymentValidateService.validatePaymentPassword(Long.valueOf(memberCode), command.getPayerAcctType(),loginSession.getOperatorId(), payPwd);
					if(!StringUtil.isNull(message)){
						view.addObject("paymentPwdTip", message);
						setBatchPay2BankToken(request, command, "audit");
					}
				}
				
				if(!StringUtil.isNull(message)){
					view.setViewName(detailView);
					BatchPaymentReqBaseQueryInfo info = getBatchPaymentReqBaseQueryInfo(command.getRequestSeq(), memberCode);
					view.addObject("info", info);
					setBatchPay2BankToken(request, command, "audit");
				}else{
				    
					if(workOrderStatus==2){
							
						if(dto.getProcessType()==null||dto.getProcessType()==0){
							  message = batchPay2BankOrderValidateService.validateRCLimitInfo(memberCode, dto.getValidAmount(), dto.getValidCount());
							  
							  if(StringUtil.isNull(message)){
							    	message = paymentValidateService.validatePayerBanlance(dto.getValidAmount(), dto.getIsPayerPayFee(), dto.getFee(), memberCode, dto.getPayerAcctType());
							  }
						}
					  
					  if(!StringUtil.isNull(message)){
						  	view.setViewName(detailView);
							BatchPaymentReqBaseQueryInfo info = getBatchPaymentReqBaseQueryInfo(command.getRequestSeq(), memberCode);
							view.addObject("info", info);
							view.addObject("message", message);
							setBatchPay2BankToken(request, command, "audit");
					  }else{
						  batchPay2BankRequestService.auditPassRequestRdTx(command.getRequestSeq(), loginSession.getOperatorIdentity(), command.getAuditRemark());
					  }
					}else{
						batchPay2BankRequestService.auditRejectRequestRdTx(command.getRequestSeq(), loginSession.getOperatorIdentity(), command.getAuditRemark());
					}
				}
				
				 command.setStatus(workOrderStatus);
				return view.addObject("command", command);
			}catch(Exception e){
				e.printStackTrace();
				view.setViewName(errorView);
				return view;
			}
		}
	}
	
	private BatchPaymentReqBaseQueryInfo getBatchPaymentReqBaseQueryInfo(long requestSeq,long memberCode){
		BatchPaymentReqBaseQueryInfo order = batchPaymentAuditQueryService.getAuditQueryInfo(requestSeq, memberCode);
		return order;
	}
	

	private void setBatchPay2BankToken(HttpServletRequest request,
			BatchPay2BankCommand command, String step) {
		String token = step+UUID.randomUUID().toString();
		command.setToken(token);
		request.getSession().setAttribute("audit_token_batchpay2bank", token);
	}
	
	private static boolean validateToken(HttpServletRequest request){
		String token = StringUtil.null2String(request.getParameter("token"));
		String token_batchpay2bank = null;
		token_batchpay2bank = StringUtil.null2String(request.getSession().getAttribute("audit_token_batchpay2bank"));
		request.getSession().removeAttribute("audit_token_batchpay2bank");
		if(StringUtil.isNull(token)||(StringUtil.isNull(token_batchpay2bank)||!token.equalsIgnoreCase(token_batchpay2bank))){
			return false;
		}
		return true;
	}

	public void setIndexView(String indexView) {
		this.indexView = indexView;
	}

	public void setListView(String listView) {
		this.listView = listView;
	}

	public void setDetailView(String detailView) {
		this.detailView = detailView;
	}

	public void setErrorView(String errorView) {
		this.errorView = errorView;
	}

	public void setSuccessView(String successView) {
		this.successView = successView;
	}
	

}
