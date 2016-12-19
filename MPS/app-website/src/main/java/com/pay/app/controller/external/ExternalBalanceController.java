package com.pay.app.controller.external;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.tuckey.web.filters.urlrewrite.utils.StringUtils;

import com.google.gson.JsonObject;
import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.checkcode.CheckCodeService;
import com.pay.acc.deal.dto.BalanceDealDto;
import com.pay.acc.deal.service.BalanceDealService;
import com.pay.acc.exception.MaAccountQueryUntxException;
import com.pay.acc.service.account.AccountQueryService;
import com.pay.acc.service.account.FrozenAmountService;
import com.pay.acc.service.account.constantenum.PayForEnum;
import com.pay.acc.service.account.dto.CalFeeReponseDto;
import com.pay.acc.service.account.exception.MaAcctBalanceException;
import com.pay.base.common.enums.ErrorCodeEnum;
import com.pay.base.common.pe.PEHelper;
import com.pay.base.common.pe.PEObject;
import com.pay.base.model.Acct;
import com.pay.base.service.acct.AcctService;
import com.pay.base.service.member.MemberService;
import com.pay.pe.service.CalFeeReponse;
import com.pay.pe.service.CalFeeRequest;
import com.pay.pe.service.PEService;
import com.pay.util.CheckUtil;
import com.pay.util.SSOSignatureUtil;

/**
 * 
 * 冻结账户CONTROLLER
 * 
 * @author Administrator
 * 
 */
public class ExternalBalanceController extends MultiActionController {

	private CheckCodeService checkCodeService;

	private AccountQueryService accountQueryService;
	
	private FrozenAmountService frozenAmountService;

	private BalanceDealService balanceDealService;
	
	private MemberService memberService;

	private PEService peService;
	
	private AcctService acctService;

	/**
	 * 查询可用余额
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	public void queryBalance(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String sign = request.getParameter("signMsg");

		String memberCode = request.getParameter("memberCode");
		if(StringUtils.isBlank(memberCode) || !CheckUtil.isNumber(memberCode)){
			write(response,getJSONHtml(false,ErrorCodeEnum.MEMBER_CODE_ERROR.getErrorCode(),ErrorCodeEnum.MEMBER_CODE_ERROR.getMessage(),""));
			return;
		}
		
		//会员不存在
		if(memberService.findByMemberCode(Long.valueOf(memberCode))==null){
			write(response,getJSONHtml(false,ErrorCodeEnum.MEMBER_NON_EXIST_ERROR.getErrorCode(),ErrorCodeEnum.MEMBER_NON_EXIST_ERROR.getMessage(),""));
			return;
		}
		
		String message = "method=queryBalance&memberCode="+memberCode;
		//验签
		try {
			if(!SSOSignatureUtil.doSignature4Mall(sign, message)){
				write(response,getJSONHtml(false,ErrorCodeEnum.SIGN_FAILD.getErrorCode(),ErrorCodeEnum.SIGN_FAILD.getMessage(),""));
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			write(response,getJSONHtml(false,ErrorCodeEnum.SIGN_FAILD.getErrorCode(),ErrorCodeEnum.SIGN_FAILD.getMessage(),""));
			return;
		}
		Acct acct = acctService.getByMemberCode(Long.valueOf(memberCode),10);
		if(acct==null){
			write(response,getJSONHtml(false,ErrorCodeEnum.ACCT_NON_EXIST_ERROR.getErrorCode(),ErrorCodeEnum.ACCT_NON_EXIST_ERROR.getMessage(),""));
			return;
		}else{
			write(response,getJSONHtml(true,"","",String.valueOf(acct.getBalance())));
			return;
		}
	}

	/**
	 * 冻结账户
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	public void freezeBalance(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String sign = request.getParameter("signMsg");

		String memberCode = request.getParameter("memberCode");
		String amount = request.getParameter("amount");
		if(StringUtils.isBlank(memberCode) || !CheckUtil.isNumber(memberCode)){
			write(response,getJSONHtml(false,ErrorCodeEnum.MEMBER_CODE_ERROR.getErrorCode(),ErrorCodeEnum.MEMBER_CODE_ERROR.getMessage(),""));
			return;
		}
		
		//会员不存在
		if(memberService.findByMemberCode(Long.valueOf(memberCode))==null){
			write(response,getJSONHtml(false,ErrorCodeEnum.MEMBER_NON_EXIST_ERROR.getErrorCode(),ErrorCodeEnum.MEMBER_NON_EXIST_ERROR.getMessage(),""));
			return;
		}
		
		//验证金额格式
		if(StringUtils.isBlank(amount) || !CheckUtil.isNumber(amount)){
			write(response,getJSONHtml(false,ErrorCodeEnum.MALL_FREEZE_AMOUNT_ERROR.getErrorCode(),ErrorCodeEnum.MALL_FREEZE_AMOUNT_ERROR.getMessage(),""));
			return;
		}
		if(amount.indexOf("-")!=-1 && !amount.equals("0")){
			write(response,getJSONHtml(false,ErrorCodeEnum.MALL_FREEZE_AMOUNT_ERROR.getErrorCode(),ErrorCodeEnum.MALL_FREEZE_AMOUNT_ERROR.getMessage(),""));
			return;
		}
		
		String message = "method=freezeBalance&memberCode="+memberCode+"&amount="+amount;
		//验签
		try {
			if(!SSOSignatureUtil.doSignature4Mall(sign, message)){
				write(response,getJSONHtml(false,ErrorCodeEnum.SIGN_FAILD.getErrorCode(),ErrorCodeEnum.SIGN_FAILD.getMessage(),""));
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			write(response,getJSONHtml(false,ErrorCodeEnum.SIGN_FAILD.getErrorCode(),ErrorCodeEnum.SIGN_FAILD.getMessage(),""));
			return;
		}
		
		String orderId = checkCodeService.getOrderId();
		AcctAttribDto acctAttribDto = null;
		try {
			acctAttribDto = accountQueryService.doQueryAcctAttribNsTx(Long.parseLong(memberCode), 10);
			CalFeeRequest calFeeRequest = PEHelper.generateCaculateRequest(acctAttribDto, getPEObj(memberCode, orderId, 201),amount);
			CalFeeReponse calFeeRespone = null;//peService.calculateFeeDetail(calFeeRequest);// PE计费接口
			if (null != calFeeRespone) {
				CalFeeReponseDto calFeeReponseDto = PEHelper.generateCaculateResponse(calFeeRespone);
				try {
					frozenAmountService.doFrozenAmountRnTx(calFeeReponseDto, PayForEnum.FREEZE_BALANCE.getCode());
					write(response,getJSONHtml(true,"","",orderId));//操作成功
					return;
				} catch (MaAcctBalanceException e) {
					write(response,getJSONHtml(false,e.getErrorEnum().getErrorCode(),e.getErrorEnum().getMessage(),""));
					return;
				}
			} else {
				//计费失败
				write(response,getJSONHtml(false,ErrorCodeEnum.PE_FAILED.getErrorCode(),ErrorCodeEnum.PE_FAILED.getMessage(),""));
				return;
			}
		} catch (MaAccountQueryUntxException e) {
			e.printStackTrace();
			write(response,getJSONHtml(false,ErrorCodeEnum.ACCT_ATTRI_NO_EXIST.getErrorCode(),ErrorCodeEnum.ACCT_ATTRI_NO_EXIST.getMessage(),""));
			return;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			write(response,getJSONHtml(false,"C001",ErrorCodeEnum.ACCT_ATTRI_NO_EXIST.getMessage(),""));
			return;
		}
	}
	

	/**
	 * 解冻账户
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException 
	 */
	public void unfreezeBalance(HttpServletRequest request,
			HttpServletResponse response) throws IOException {
		String sign = request.getParameter("signMsg");
		String memberCode = request.getParameter("memberCode");
		String dealId = request.getParameter("dealId");
		
		//验证交易流水号格式
		if(StringUtils.isBlank(dealId)){
			write(response,getJSONHtml(false,ErrorCodeEnum.MALL_DEALID_ERROR.getErrorCode(),ErrorCodeEnum.MALL_DEALID_ERROR.getMessage(),""));
			return;
		}
		
		//验证会员号格式
		if(StringUtils.isBlank(memberCode) || !CheckUtil.isNumber(memberCode)){
			write(response,getJSONHtml(false,ErrorCodeEnum.MEMBER_CODE_ERROR.getErrorCode(),ErrorCodeEnum.MEMBER_CODE_ERROR.getMessage(),""));
			return;
		}
		
		//验证订单是否存在
		BalanceDealDto deal = balanceDealService.queryBalanceDealForFlushes(dealId,201);
		if(deal==null){
			write(response,getJSONHtml(false,ErrorCodeEnum.MEMBER_CODE_ERROR.getErrorCode(),ErrorCodeEnum.MEMBER_CODE_ERROR.getMessage(),""));
			return;
		}
		
		//订单号和会员不匹配
		if(String.valueOf(deal.getPayerFullMemberAcctCode()).indexOf(memberCode)==-1){
			write(response,getJSONHtml(false,ErrorCodeEnum.MALL_FREEZE_MEMBER_DEAL_ERROR.getErrorCode(),ErrorCodeEnum.MALL_FREEZE_MEMBER_DEAL_ERROR.getMessage(),""));
			return;
		}
		
		//会员不存在
		if(memberService.findByMemberCode(Long.valueOf(memberCode))==null){
			write(response,getJSONHtml(false,ErrorCodeEnum.MEMBER_NON_EXIST_ERROR.getErrorCode(),ErrorCodeEnum.MEMBER_NON_EXIST_ERROR.getMessage(),""));
			return;
		}
		
		String message = "method=unfreezeBalance&memberCode="+memberCode+"&dealId="+dealId;
		//验签
		try {
			if(!SSOSignatureUtil.doSignature4Mall(sign, message)){
				write(response,getJSONHtml(false,ErrorCodeEnum.SIGN_FAILD.getErrorCode(),ErrorCodeEnum.SIGN_FAILD.getMessage(),""));
				return;
			}
		} catch (Exception e) {
			e.printStackTrace();
			write(response,getJSONHtml(false,ErrorCodeEnum.SIGN_FAILD.getErrorCode(),ErrorCodeEnum.SIGN_FAILD.getMessage(),""));
			return;
		}
		
		String newOrderId = checkCodeService.getOrderId();
		try {
			// 调PE 退费
			AcctAttribDto acctAttribDto = accountQueryService.doQueryAcctAttribNsTx(Long.parseLong(memberCode), 10);
			CalFeeRequest calFeeRequest = PEHelper.unfreezeCaculateRequest(acctAttribDto, getPEObj(memberCode, newOrderId, 202),String.valueOf(deal.getAmount()));
			CalFeeReponse calFeeRespone = null;//peService.calculateFeeDetail(calFeeRequest);// PE计费接口
			if (null != calFeeRespone) {
				CalFeeReponseDto calFeeReponseDto = PEHelper.generateCaculateResponse(calFeeRespone);
				try{
					frozenAmountService.doUnFrozenAmountRnTx(calFeeReponseDto, newOrderId, deal.getOrderId(), 202, deal.getAmount(), PayForEnum.UNFREEZE_BALANCE.getCode());
					write(response,getJSONHtml(true,"","",newOrderId));//操作成功
					return;
				} catch (MaAcctBalanceException e) {
					write(response,getJSONHtml(false,e.getErrorEnum().getErrorCode(),e.getErrorEnum().getMessage(),""));
					return;
				}
			} else {
				//计费失败
				write(response,getJSONHtml(false,ErrorCodeEnum.PE_FAILED.getErrorCode(),ErrorCodeEnum.PE_FAILED.getMessage(),""));
				return;
			}
		} catch (MaAccountQueryUntxException e) {
			e.printStackTrace();
			write(response,getJSONHtml(false,ErrorCodeEnum.ACCT_ATTRI_NO_EXIST.getErrorCode(),ErrorCodeEnum.ACCT_ATTRI_NO_EXIST.getMessage(),""));
			return;
		} catch (NumberFormatException e) {
			e.printStackTrace();
			write(response,getJSONHtml(false,"C001",ErrorCodeEnum.ACCT_ATTRI_NO_EXIST.getMessage(),""));
			return;
		}
	}
	
	private String getJSONHtml(boolean resultFlag,String errorCode,String resultMsg,String resultObj){
		JsonObject o = new JsonObject();
		o.addProperty("resultFlag", resultFlag);
		o.addProperty("errorCode", errorCode);
		o.addProperty("resultMsg", resultMsg);
		o.addProperty("resultObj", resultObj);
		return o.toString();
	}
	
	private void write(HttpServletResponse response,String html) throws IOException{
		response.setCharacterEncoding("UTF-8");
		PrintWriter w = response.getWriter();
		w.write(html);
		w.flush();
		w.close();
	}

	private PEObject getPEObj(String memberCode, String orderId, Integer dealCode) {
		PEObject peo = new PEObject(memberCode, orderId, 10, 1, "0008", dealCode,
				200, 1);
		return peo;
	}

	public void setCheckCodeService(CheckCodeService checkCodeService) {
		this.checkCodeService = checkCodeService;
	}

	public void setAccountQueryService(AccountQueryService accountQueryService) {
		this.accountQueryService = accountQueryService;
	}

	public void setFrozenAmountService(FrozenAmountService frozenAmountService) {
		this.frozenAmountService = frozenAmountService;
	}

	public void setBalanceDealService(BalanceDealService balanceDealService) {
		this.balanceDealService = balanceDealService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public void setPeService(PEService peService) {
		this.peService = peService;
	}

	public void setAcctService(AcctService acctService) {
		this.acctService = acctService;
	}
	
}
