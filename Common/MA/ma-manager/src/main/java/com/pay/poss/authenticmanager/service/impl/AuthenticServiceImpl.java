package com.pay.poss.authenticmanager.service.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.service.account.AccountBalanceHandlerService;
import com.pay.acc.service.account.exception.MaAcctBalanceException;
import com.pay.inf.exception.AppException;
import com.pay.pe.service.CalFeeRequest;
import com.pay.pe.service.PEService;
import com.pay.poss.authenticmanager.common.Constants;
import com.pay.poss.authenticmanager.dao.IAuthenticDao;
import com.pay.poss.authenticmanager.dto.VerifyLogDto;
import com.pay.poss.authenticmanager.dto.VerifySearchDto;
import com.pay.poss.authenticmanager.dto.VerifySearchListDto;
import com.pay.poss.authenticmanager.model.PossOperate;
import com.pay.poss.authenticmanager.model.TransLog;
import com.pay.poss.authenticmanager.service.IAuthenticService;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.personmanager.dao.IndividualDAO;
import com.pay.rm.base.exception.enums.ExceptionCodeEnum;
import com.pay.util.DESUtil;

/**
 * 
 * @Description
 * @project poss-membermanager
 * @file AuthenticServiceImpl.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 *          Date Author Changes 2010-11-10 gungun_zhang Create
 */
public class AuthenticServiceImpl implements IAuthenticService {
	private Log log = LogFactory.getLog(AuthenticServiceImpl.class);
	private IAuthenticDao authenticDao;
	private PEService peService;
	private AccountBalanceHandlerService balanceService;
	private IndividualDAO individualDAO;

	@Override
	public List<VerifySearchListDto> queryVerifyLog(
			VerifySearchDto verifySearchDto) {
		log.debug("AuthenticDaoImpl.queryVerifyLog is running...");
		if (StringUtils.isNotEmpty(verifySearchDto.getCardId())) {
			verifySearchDto.setCardId(DESUtil.encrypt(verifySearchDto
					.getCardId()));
		}
		List<VerifySearchListDto> resultList = this.authenticDao
				.queryVerifyLog(verifySearchDto);
		if (resultList != null && resultList.size() > 0) {
			for (int i = 0; i < resultList.size(); i++) {
				VerifySearchListDto verifySearchListDto = resultList.get(i);
				try {
					verifySearchListDto.setCardId(DESUtil
							.decrypt(verifySearchListDto.getCardId()));
				} catch (Exception e) {
					log.error("AuthenticDaoImpl.queryVerifyLog.DESUtil is error...");
					e.printStackTrace();
				}
			}
		}
		return resultList;
	}

	@Override
	public Integer queryVerifyLogCount(VerifySearchDto verifySearchDto) {
		log.debug("AuthenticDaoImpl.queryVerifyLogCount is running...");
		return this.authenticDao.queryVerifyLogCount(verifySearchDto);
	}

	@Override
	public VerifyLogDto getVerifyLogById(String verifyId) {
		log.debug("AuthenticDaoImpl.getVerifyLogById is running...");
		VerifyLogDto verifyLogDto = this.authenticDao.getVerifyLogById(Long
				.valueOf(verifyId));
		if (StringUtils.isNotEmpty(verifyLogDto.getCardId())) {
			try {
				verifyLogDto
						.setCardId(DESUtil.decrypt(verifyLogDto.getCardId()));
			} catch (Exception e) {
				log.error("AuthenticDaoImpl.getVerifyLogById.DESUtil is error...");
				e.printStackTrace();
			}
		}
		return verifyLogDto;
	}

	@Override
	// 收费的补单操作
	public Boolean updateVerifyLogStatusTrans(VerifyLogDto verifyLogDto)
			throws PossException, AppException {
		Boolean isSucess = true;

		if (verifyLogDto != null) {
			try {
				String policeMessage = verifyLogDto.getMessage();
				if (Constants.POLICE_TRUE.equals(policeMessage)) {
					this.updateVerifyLogStatus(
							Long.valueOf(verifyLogDto.getVerifyId()),
							Constants.TRUE);
					// this.updateMiddleAccount(verifyLogDto.getVerifyId());
					this.updateIndividualName(verifyLogDto.getVerifyId());
				} else if (Constants.POLICE_FALSE.equals(policeMessage)) {
					this.updateVerifyLogStatus(
							Long.valueOf(verifyLogDto.getVerifyId()),
							Constants.FALSE);
					// this.updateMiddleAccount(verifyLogDto.getVerifyId());
				} else if (Constants.POLICE_NOBODY.equals(policeMessage)) {
					this.updateVerifyLogStatus(
							Long.valueOf(verifyLogDto.getVerifyId()),
							Constants.FALSE);
					// this.refundment(verifyLogDto.getMemberCode(),verifyLogDto.getVerifyId());
				} else if (Constants.POLICE_NOLOG.equals(policeMessage)) {
					this.updateVerifyLogStatus(
							Long.valueOf(verifyLogDto.getVerifyId()),
							Constants.FALSE);
					// this.refundment(verifyLogDto.getMemberCode(),verifyLogDto.getVerifyId());
				} else {
					isSucess = false;
					return isSucess;
				}
				this.insertOpLog(verifyLogDto);
				// this.updatePoliceMessage(Long.valueOf(verifyLogDto.getVerifyId()),verifyLogDto.getMessage());
			} catch (PossException e) {
				isSucess = false;
				log.error("AuthenticDaoImpl.updateVerifyLogStatusTrans is error...");
				e.printStackTrace();
				throw new PossException("实名认证补单失败,pe调用失败",
						ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
			}
			// catch(MaAcctBalanceException e){
			// isSucess = false;
			// log.error("AuthenticDaoImpl.updateVerifyLogStatusTrans is error...");
			// e.printStackTrace();
			// throw new PossException("实名认证补单失败,更新余额失败",
			// ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
			// }
			catch (Exception e) {
				isSucess = false;
				log.error("AuthenticDaoImpl.updateVerifyLogStatusTrans is error...");
				e.printStackTrace();
				throw new AppException();
			}
		}
		return isSucess;
	}

	private void updatePoliceMessage(Long verifyId, String message)
			throws PossException {
		Map<String, Object> dataMap = new Hashtable<String, Object>();
		dataMap.put("verifyId", verifyId);
		dataMap.put("message", message);
		this.authenticDao.updatePoliceMessage(dataMap);

	}

	private void updateVerifyLogStatus(Long verifyId, Integer status)
			throws PossException {
		Map<String, Object> dataMap = new Hashtable<String, Object>();
		dataMap.put("verifyId", verifyId);
		dataMap.put("status", status);
		this.authenticDao.updateVerifyLogStatus(dataMap);

	}

	private String getAccountCode(String memberCode) {
		return this.authenticDao.getAccountCode(Long.valueOf(memberCode));
	}

	private String getOrderId() {
		return this.authenticDao.getOrderId();
	}

	private String getOldOrderId(String verifyId) {
		return this.authenticDao.getOldOrderId(verifyId);
	}

	// 补单为失败状态,退费处理
	private void refundment(String memberCode, String verifyId)
			throws MaAcctBalanceException, PossException {
		if (StringUtils.isEmpty(this.getOldOrderId(verifyId))) {
			throw new PossException("实名认证补单失败-交易记录查不到",
					ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		}
		String accountCode = this.getAccountCode(memberCode);// 获取个人账户,用membercode关联
		String orderId = this.getOrderId();

		// PaymentReqDto paymentReqDto =
		// this.setBackCaculateFee(accountCode,memberCode,orderId);
		// PaymentResponseDto calFeeRespone_back =
		// peService.processPayment(paymentReqDto);
		// if(calFeeRespone_back != null){
		// boolean isSucess =
		// false;//calFeeRespone_back.isHasCaculatedPrice();//计费是否成功
		// if(isSucess){
		// CalFeeReponseDto calFeeReponseDto_back =
		// this.setCalFeeReponseDto(calFeeRespone_back);
		// balanceService.doUpdateAcctBalanceRntx(calFeeReponseDto_back,Constants.DEALTYPEOUT);
		// }else{
		// throw new PossException("实名认证补单失败-计费失败",
		// ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		// }
		//
		// }else{
		// throw new PossException("实名认证补单失败-获取calFeeRespone_back失败",
		// ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		// }
		//
		// //退费完毕,同时关联收费
		// this.updateTransLog(this.getOldOrderId(verifyId), orderId);
		// //退费完毕,插入交易日志表t_trans_log
		//
		// TransLog transLog = new TransLog();
		// Date time = new Date();
		// transLog.setAcctType(Integer.valueOf(Constants.ACCOUNT_RMB));
		// transLog.setAmount(900L);
		// transLog.setPayType(Constants.PAYTYPE_17);
		// List<CalFeeDetail> list_calFeeDetail =
		// calFeeRespone_back.getCalFeeDetails();
		// String payAcct =null;
		// String recvAcct =null;
		// for(CalFeeDetail calFeeDetail : list_calFeeDetail){
		// if(calFeeDetail.getCrdr() == Constants.DEBIT){
		// payAcct = calFeeDetail.getAcctcode();
		// }else{
		// recvAcct = calFeeDetail.getAcctcode();
		// }
		// }
		//
		// transLog.setPayAcct(payAcct);
		// transLog.setRecvAcct(recvAcct);
		// transLog.setRelatxSerialNo(Long.valueOf(this.getOldOrderId(verifyId)));
		// transLog.setSerialNo(Long.parseLong(orderId));
		// transLog.setStatus(Constants.PAYSTATUS_1);
		// transLog.setUpdateDate(time);
		// transLog.setLinkId(Long.valueOf(verifyId));
		// transLog.setConfirmDate(time);
		// transLog.setCreateDate(time);
		// transLog.setPayDate(time);
		// this.insertTransLog(transLog);

	}

	// 退费时,组装balance参数
	// public CalFeeReponseDto setCalFeeReponseDto(PaymentResponseDto
	// calFeeRespone) {
	// CalFeeReponseDto calFeeReponseDto =
	// BeanConvertUtil.convert(CalFeeReponseDto.class, calFeeRespone);
	// CalFeeRequestDto calFeeRequestDto =
	// BeanConvertUtil.convert(CalFeeRequestDto.class,
	// calFeeRespone.getPaymentReq());
	// List<CalFeeDetailDto> calDetailList=new ArrayList<CalFeeDetailDto>();
	// for(PaymentDetailDto calFeeDetail:calFeeRespone.getPaymentDetails()){
	// CalFeeDetailDto calFeeDetailDtos= new CalFeeDetailDto();
	// calFeeDetailDtos.setAcctcode(calFeeDetail.getAcctcode());
	// calFeeDetailDtos.setCrdr(calFeeDetail.getCrdr());
	// calFeeDetailDtos.setCreatedate(calFeeDetail.getCreatedate());
	// calFeeDetailDtos.setCurrencyCode(calFeeDetail.getCurrencyCode());
	// calFeeDetailDtos.setDealId(calFeeDetail.getDealId());
	// calFeeDetailDtos.setEntrycode(calFeeDetail.getEntrycode());
	// calFeeDetailDtos.setExchangeRate(calFeeDetail.getExchangeRate());
	// calFeeDetailDtos.setMaBlanceBy(calFeeDetail.getMaBlanceBy());
	// calFeeDetailDtos.setPaymentServiceId(calFeeDetail.getPaymentServiceId());
	// calFeeDetailDtos.setStatus(calFeeDetail.getStatus());
	// calFeeDetailDtos.setText(calFeeDetail.getText());
	// calFeeDetailDtos.setTransactiondate(calFeeDetail.getTransactiondate());
	// calFeeDetailDtos.setValue(calFeeDetail.getValue());
	// calFeeDetailDtos.setVouchercode(calFeeDetail.getVouchercode());
	// calDetailList.add(calFeeDetailDtos);
	// }
	// calFeeReponseDto.setCalFeeRequestDto(calFeeRequestDto);
	// calFeeReponseDto.setCalFeeDetailDtos(calDetailList);
	// return calFeeReponseDto;
	// }
	//

	// //退费时,组装pe参数
	// public PaymentReqDto setBackCaculateFee(String accountCode,String
	// memberCode, String orderId) {
	// String rmb_account = Constants.ACCOUNT_RMB;
	// String payerOrgType =Constants.PAYERORGTYPE;
	// String payerOrgCode = Constants.PAYERORGCODE;
	// String dealCode = Constants.GOV_PAYCODE;
	// String orderCode = Constants.GOV_ORDERCODE;
	// String payMethod = Constants.PAYMETHOD;
	// String acctCode = accountCode;
	// String acctountId = memberCode + rmb_account;
	//
	// PaymentReqDto calFeeRequest=new PaymentReqDto();
	// calFeeRequest.setOrderId(orderId);
	// calFeeRequest.setAmount(0L);
	// calFeeRequest.setOrderCode(Integer.parseInt(orderCode));
	// calFeeRequest.setDealCode(Integer.parseInt(dealCode));
	// calFeeRequest.setOrderAmount(0L);
	// calFeeRequest.setPayerOrgCode(payerOrgCode);
	// calFeeRequest.setPayerOrgType(ORGTYPE.BANK.getValue() + "");
	// calFeeRequest.setPayee(memberCode);
	// calFeeRequest.setPayeeAcctType(rmb_account);
	// calFeeRequest.setPayeeOrgType(payerOrgType);
	// calFeeRequest.setPayeeMemberAcctCode(acctountId);
	// calFeeRequest.setPayeeFullMemberAcctCode(acctCode);
	// calFeeRequest.setPayMethod(Integer.parseInt(payMethod));
	// calFeeRequest.setRequestDate(new Date());
	// calFeeRequest.setSubmitAcctCode(acctountId);
	// return calFeeRequest;
	// }
	// 补单为成功状态,更新中间账户2次
	private void updateMiddleAccount(String verifyId)
			throws MaAcctBalanceException, PossException {
		// 补单为成功状态时,更新中间科目账号,调用pe参数1
		String orderId = this.getOldOrderId(verifyId);
		if (StringUtils.isEmpty(orderId)) {
			throw new PossException("实名认证补单失败-交易记录查不到",
					ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		}
		// CalFeeRequest calFeeRequest_back1 = this.setFeeToGovAccount(orderId);
		// CalFeeReponse calFeeRespone_back1 =
		// peService.calculateFeeDetail(calFeeRequest_back1);
		// if(calFeeRespone_back1 != null){
		// boolean isSucess =
		// calFeeRespone_back1.isHasCaculatedPrice();//更新中间账户是否成功
		// if(isSucess){
		// CalFeeReponseDto calFeeReponseDto_back1 =
		// this.setCalFeeReponseDto(calFeeRespone_back1);
		// balanceService.doUpdateAcctBalanceRntx(calFeeReponseDto_back1,Constants.DEALTYPEOUT_16);
		// }else{
		// throw new PossException("实名认证补单失败-更新中间_1账户失败",
		// ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		// }
		//
		// }else{
		// throw new PossException("实名认证补单失败-获取calFeeRespone_back1失败",
		// ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		// }
		// //更新中间科目完毕,插入交易日志表t_trans_log
		//
		// TransLog transLog1 = new TransLog();
		// Date time1 = new Date();
		// transLog1.setAcctType(Integer.valueOf(Constants.ACCOUNT_RMB));
		// transLog1.setAmount(calFeeRequest_back1.getAmount());
		// transLog1.setPayType(Constants.PAYTYPE_16);
		// List<CalFeeDetail> list_calFeeDetail1 =
		// calFeeRespone_back1.getCalFeeDetails();
		// String payAcct1 =null;
		// String recvAcct1 =null;
		// for(CalFeeDetail calFeeDetail : list_calFeeDetail1){
		// if(calFeeDetail.getCrdr() == Constants.DEBIT){
		// payAcct1 = calFeeDetail.getAcctcode();
		// }else{
		// recvAcct1 = calFeeDetail.getAcctcode();
		// }
		// }
		//
		// transLog1.setPayAcct(payAcct1);
		// transLog1.setRecvAcct(recvAcct1);
		// transLog1.setSerialNo(Long.parseLong(orderId));
		// transLog1.setStatus(Constants.PAYSTATUS_1);
		// transLog1.setUpdateDate(time1);
		// transLog1.setLinkId(Long.valueOf(verifyId));
		// transLog1.setConfirmDate(time1);
		// transLog1.setCreateDate(time1);
		// transLog1.setPayDate(time1);
		//
		// //补单为成功状态时,更新中间科目账号,调用pe参数2
		//
		// CalFeeRequest calFeeRequest_back2 =
		// this.setSuccessCaculateFee(orderId);
		// CalFeeReponse calFeeRespone_back2 =
		// peService.calculateFeeDetail(calFeeRequest_back2);
		// if(calFeeRespone_back2 != null){
		// boolean isSucess =
		// calFeeRespone_back2.isHasCaculatedPrice();//更新中间账户是否成功
		// if(isSucess){
		// CalFeeReponseDto calFeeReponseDto_back2 =
		// this.setCalFeeReponseDto(calFeeRespone_back2);
		// balanceService.doUpdateAcctBalanceRntx(calFeeReponseDto_back2,Constants.DEALTYPEOUT_16);
		// }else{
		// throw new PossException("实名认证补单失败-更新中间_2账户失败",
		// ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		// }
		//
		// }else{
		// throw new PossException("实名认证补单失败-获取calFeeRespone_back2失败",
		// ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		// }
		//
		// //更新中间科目完毕,插入交易日志表t_trans_log
		// TransLog transLog2 = new TransLog();
		// Date time2 = new Date();
		// transLog2.setAcctType(Integer.valueOf(Constants.ACCOUNT_RMB));
		// transLog2.setAmount(900L);
		// transLog2.setPayType(Constants.PAYTYPE_16);
		// List<CalFeeDetail> list_calFeeDetail2 =
		// calFeeRespone_back2.getCalFeeDetails();
		// String payAcct2 =null;
		// String recvAcct2 =null;
		// for(CalFeeDetail calFeeDetail : list_calFeeDetail2){
		// if(calFeeDetail.getCrdr() == Constants.DEBIT){
		// payAcct2 = calFeeDetail.getAcctcode();
		// }else{
		// recvAcct2 = calFeeDetail.getAcctcode();
		// }
		// }
		//
		// transLog2.setPayAcct(payAcct2);
		// transLog2.setRecvAcct(recvAcct2);
		// transLog2.setSerialNo(Long.parseLong(orderId));
		// transLog2.setStatus(Constants.PAYSTATUS_1);
		// transLog2.setUpdateDate(time2);
		// transLog2.setLinkId(Long.valueOf(verifyId));
		// transLog2.setConfirmDate(time2);
		// transLog2.setCreateDate(time2);
		// transLog2.setPayDate(time2);
		// this.insertTransLog(transLog2);

	}

	// 补单为成功状态时,组装pe参数1
	public CalFeeRequest setFeeToGovAccount(String orderId) {
		// String rmb_account = Constants.ACCOUNT_RMB;
		Integer payerOrgType = Constants.PAYERORGTYPE_3;
		String payerOrgCode = "010010001";
		String payeeOrgCode = "010010007";
		Integer dealCode = Constants.GOV_PAYCODE_184;
		Integer orderCode = Constants.GOV_ORDERCODE;
		Integer payMethod = Constants.PAYMETHOD;
		String acctountId = Constants.MIDDLEACCOUNT;
		CalFeeRequest calFeeRequest = new CalFeeRequest();
		calFeeRequest.setOrderId(orderId);
		calFeeRequest.setOrderAmount(0L);
		calFeeRequest.setAmount(0L);
		calFeeRequest.setOrderCode(orderCode);
		calFeeRequest.setDealCode(dealCode);
		calFeeRequest.setPaymentServicePkgCode(dealCode);

		calFeeRequest.setPayerOrgCode(payerOrgCode);
		calFeeRequest.setPayerOrgType(payerOrgType);
		calFeeRequest.setPayeeOrgCode(payeeOrgCode);
		calFeeRequest.setPayeeOrgType(payerOrgType);

		calFeeRequest.setPayMethod(payMethod);
		calFeeRequest.setRequestDate(new Date());
		calFeeRequest.setSubmitAcctCode(acctountId);
		return calFeeRequest;
	}

	// 补单为成功状态时,组装pe参数2
	public CalFeeRequest setSuccessCaculateFee(String orderId) {
		// String rmb_account = Constants.ACCOUNT_RMB;
		Integer payerOrgType = Constants.PAYERORGTYPE_3;
		String payerOrgCode = Constants.PAYERORGCODE;
		// String gov_amount = Constants.GOV_AMOUNT;
		Integer dealCode = Constants.GOV_PAYCODE_182;
		Integer orderCode = Constants.GOV_ORDERCODE;
		Integer payMethod = Constants.PAYMETHOD;
		String acctountId = Constants.MIDDLEACCOUNT;
		CalFeeRequest calFeeRequest = new CalFeeRequest();
		calFeeRequest.setOrderId(orderId);
		calFeeRequest.setOrderAmount(0L);
		calFeeRequest.setAmount(0L);
		calFeeRequest.setOrderCode(orderCode);
		calFeeRequest.setDealCode(dealCode);
		calFeeRequest.setPaymentServicePkgCode(dealCode);

		calFeeRequest.setPayerOrgCode(payerOrgCode);
		calFeeRequest.setPayerOrgType(payerOrgType);
		calFeeRequest.setPayeeOrgCode(payerOrgCode);
		calFeeRequest.setPayeeOrgType(payerOrgType);
		calFeeRequest.setPayMethod(payMethod);
		calFeeRequest.setRequestDate(new Date());
		calFeeRequest.setSubmitAcctCode(acctountId);
		return calFeeRequest;
	}

	private void insertOpLog(VerifyLogDto verifyLogDto) {
		PossOperate possOperate = new PossOperate();

		possOperate.setObjectCode(verifyLogDto.getVerifyId());
		possOperate.setLoginName(verifyLogDto.getOpLoginName());
		possOperate.setLoginDate(verifyLogDto.getLoginTime());
		possOperate.setLoginIp(verifyLogDto.getUserIp());
		possOperate.setActionUrl("verifyLogEdit.do");
		possOperate.setType(Constants.LOGTYPE_7);

		this.authenticDao.insertOpLog(possOperate);

	}

	private void insertTransLog(TransLog transLog) {
		this.authenticDao.insertTransLog(transLog);
	};

	private void updateTransLog(String orderId, String relatxOrderId) {
		this.authenticDao.updateTransLog(orderId, relatxOrderId);
	};

	public void setAuthenticDao(IAuthenticDao authenticDao) {
		this.authenticDao = authenticDao;
	}

	public void setPeService(PEService peService) {
		this.peService = peService;
	}

	public void setBalanceService(AccountBalanceHandlerService balanceService) {
		this.balanceService = balanceService;
	}

	public void setIndividualDAO(IndividualDAO individualDAO) {
		this.individualDAO = individualDAO;
	}

	@Override
	// 免费的补单操作
	public Boolean updateVerifyLogStatusFreeTrans(VerifyLogDto verifyLogDto)
			throws PossException, AppException {
		Boolean isSucess = true;

		if (verifyLogDto != null) {
			try {
				String policeMessage = verifyLogDto.getMessage();
				if (Constants.POLICE_TRUE.equals(policeMessage)) {
					this.updateVerifyLogStatus(
							Long.valueOf(verifyLogDto.getVerifyId()),
							Constants.TRUE);
					this.updateMiddleAccountFree(verifyLogDto.getVerifyId());
					this.updateIndividualName(verifyLogDto.getVerifyId());
				} else if (Constants.POLICE_FALSE.equals(policeMessage)) {
					this.updateVerifyLogStatus(
							Long.valueOf(verifyLogDto.getVerifyId()),
							Constants.FALSE);
					this.updateMiddleAccountFree(verifyLogDto.getVerifyId());
				} else if (Constants.POLICE_NOBODY.equals(policeMessage)) {
					this.updateVerifyLogStatus(
							Long.valueOf(verifyLogDto.getVerifyId()),
							Constants.FALSE);
				} else if (Constants.POLICE_NOLOG.equals(policeMessage)) {
					this.updateVerifyLogStatus(
							Long.valueOf(verifyLogDto.getVerifyId()),
							Constants.FALSE);

				} else {
					isSucess = false;
					return isSucess;
				}
				this.insertOpLog(verifyLogDto);
				// this.updatePoliceMessage(Long.valueOf(verifyLogDto.getVerifyId()),verifyLogDto.getMessage());
			} catch (PossException e) {
				isSucess = false;
				log.error("AuthenticDaoImpl.updateVerifyLogStatusTrans is error...");
				e.printStackTrace();
				throw new PossException("实名认证补单失败,pe调用失败",
						ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
			} catch (MaAcctBalanceException e) {
				isSucess = false;
				log.error("AuthenticDaoImpl.updateVerifyLogStatusTrans is error...");
				e.printStackTrace();
				throw new PossException("实名认证补单失败,更新余额失败",
						ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
			} catch (Exception e) {
				isSucess = false;
				log.error("AuthenticDaoImpl.updateVerifyLogStatusTrans is error...");
				e.printStackTrace();
				throw new AppException();
			}
		}
		return isSucess;
	}

	private void updateMiddleAccountFree(String verifyId)
			throws MaAcctBalanceException, PossException {
		// 补单为成功状态时,更新中间科目账号,调用pe参数1
		String orderId = this.getOldOrderId(verifyId);
		if (StringUtils.isEmpty(orderId)) {
			throw new PossException("实名认证补单失败-交易记录查不到",
					ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		}
		// CalFeeRequest calFeeRequest_back1 = this.setFeeToGovAccount(orderId);
		// CalFeeReponse calFeeRespone_back1 =
		// peService.calculateFeeDetail(calFeeRequest_back1);
		// if(calFeeRespone_back1 != null){
		// boolean isSucess =
		// calFeeRespone_back1.isHasCaculatedPrice();//更新中间账户是否成功
		// if(isSucess){
		// CalFeeReponseDto calFeeReponseDto_back1 =
		// this.setCalFeeReponseDto(calFeeRespone_back1);
		// balanceService.doUpdateAcctBalanceRntx(calFeeReponseDto_back1,Constants.DEALTYPEOUT_16);
		// }else{
		// throw new PossException("实名认证补单失败-更新中间_1账户失败",
		// ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		// }
		//
		// }else{
		// throw new PossException("实名认证补单失败-获取calFeeRespone_back1失败",
		// ExceptionCodeEnum.DATA_ACCESS_EXCEPTION);
		// }
		// //更新中间科目完毕,插入交易日志表t_trans_log
		//
		// TransLog transLog1 = new TransLog();
		// Date time1 = new Date();
		// transLog1.setAcctType(Integer.valueOf(Constants.ACCOUNT_RMB));
		// transLog1.setAmount(Long.valueOf(Constants.GOV_AMOUNT));
		// transLog1.setPayType(Constants.PAYTYPE_16);
		// List<CalFeeDetail> list_calFeeDetail1 =
		// calFeeRespone_back1.getCalFeeDetails();
		// String payAcct1 =null;
		// String recvAcct1 =null;
		// for(CalFeeDetail calFeeDetail : list_calFeeDetail1){
		// if(calFeeDetail.getCrdr() == Constants.DEBIT){
		// payAcct1 = calFeeDetail.getAcctcode();
		// }else{
		// recvAcct1 = calFeeDetail.getAcctcode();
		// }
		// }
		//
		// transLog1.setPayAcct(payAcct1);
		// transLog1.setRecvAcct(recvAcct1);
		// transLog1.setSerialNo(Long.parseLong(orderId));
		// transLog1.setStatus(Constants.PAYSTATUS_1);
		// transLog1.setUpdateDate(time1);
		// transLog1.setLinkId(Long.valueOf(verifyId));
		// transLog1.setConfirmDate(time1);
		// transLog1.setCreateDate(time1);
		// transLog1.setPayDate(time1);

	}

	/**
	 * 把实名认证中的姓名，身份证号，更新到个人帐户信息中
	 * 
	 * @param verifyId
	 */
	private void updateIndividualName(String verifyId) {

		VerifyLogDto dto = this.authenticDao.getVerifyLogById(Long
				.valueOf(verifyId));
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("memberName", dto.getMemberName());
		map.put("cardId", dto.getCardId());
		map.put("cardType", dto.getCardType());
		map.put("memberCode", dto.getMemberCode());

		individualDAO.updateIndividualInfo(map);
	}
}
