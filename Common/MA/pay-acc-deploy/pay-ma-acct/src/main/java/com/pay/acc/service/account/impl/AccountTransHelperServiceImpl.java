package com.pay.acc.service.account.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import com.pay.acc.acct.dto.AcctDto;
import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.commons.AmountByEnum;
import com.pay.acc.commons.ConstantHelper;
import com.pay.acc.service.account.AccountHelperService;
import com.pay.acc.service.account.AccountTransHelperService;
import com.pay.acc.service.account.ParameterHelperService;
import com.pay.acc.service.account.dto.CalFeeDetailDto;
import com.pay.acc.service.account.exception.MaAcctBalanceException;
import com.pay.acc.service.errorenum.ErrorExceptionEnum;
import com.pay.pe.helper.CRDRType;

/**
 * @author Administrator
 * 
 */
public class AccountTransHelperServiceImpl implements AccountTransHelperService {

	private ThreadPoolTaskExecutor balanceCoreTransTaskExecutor;

	private AccountHelperService accountHelperService;

	private ParameterHelperService parameterHelperService;

	private Log log = LogFactory.getLog(AccountTransHelperServiceImpl.class);

	@Override
	public void handlerAccountBalanceTrans(CalFeeDetailDto calFeeDetailDto, Long serialNo) {
		boolean isNegative = false;
		this.balanceCoreTransTaskExecutor.execute(new BalanceTransCoreWork(calFeeDetailDto, serialNo, isNegative));

	}

	private synchronized void executeAccountBalanceCoreTrans(CalFeeDetailDto calFeeDetailDto, Long serialNo, boolean isNegative) throws MaAcctBalanceException {
		AcctDto acctDto = this.accountHelperService.queryAcct(calFeeDetailDto.getAcctcode(), true);
		boolean result=false;
		if(acctDto==null){
			if(log.isInfoEnabled()){
				log.info("您输入的账户号：["+calFeeDetailDto.getAcctcode()+"]无效，不存在该账户");
				throw new MaAcctBalanceException("您输入的账户号：["+calFeeDetailDto.getAcctcode()+"]无效，不存在该账户");
			}
		}
		if (calFeeDetailDto.getCrdr().intValue() == CRDRType.DEBIT.getValue()) {
			result=this.handlerCrebitBalance(calFeeDetailDto, acctDto, isNegative);
		}
		if (calFeeDetailDto.getCrdr().intValue() == CRDRType.CREDIT.getValue()){
			result=this.handlerDebitBalance(calFeeDetailDto, acctDto, isNegative);
		}
		if(result){
			
		}
		

	}

	/**
	 * 根据信息操作借方余额信息
	 * 
	 * @param calFeeDetailDto
	 * @throws MaAcctBalanceException
	 */
	private boolean handlerCrebitBalance(CalFeeDetailDto calFeeDetailDto, AcctDto acctDto,boolean isNegative) throws MaAcctBalanceException {
		Long chargeAmount= new Long(0);
		if (this.parameterHelperService.checkParameterAcctCodeOfMember(calFeeDetailDto.getAcctcode())) {
			AcctAttribDto acctAttribDto = this.accountHelperService.queryAcctAttrib(calFeeDetailDto.getAcctcode());
			if (acctAttribDto != null) {
				if (!acctAttribDto.getAllowIn().equals(ConstantHelper.ALLOW_IN)) {
					log.error("对于账户[" + acctDto.getAcctCode() + "]止入");
					throw new MaAcctBalanceException(ErrorExceptionEnum.ACCT_ALLOWOUT_ERROR, "对于账户[" + acctDto.getAcctCode() + "]止入");
				}
			}
		}
		// 对余额方向进行处理
		// 余额方向为加
		if (calFeeDetailDto.getMaBlanceBy().equals(AmountByEnum.ADD.getCode())) {
			// 对冲正而言，其金额为负，需要验证金额是否够做一笔冲正
			if (isNegative) {
				// 取正方向的金额
				Long tmpAmount = (-1)*calFeeDetailDto.getValue();
				// 验证是否够一笔冲正
				if (acctDto.getBalance() < tmpAmount) {
					log.error("对于账户[" + acctDto.getAcctCode() + "]余额不足");
					throw new MaAcctBalanceException(ErrorExceptionEnum.ACCT_NO_SAVE_ACCOUNT, "对于账户[" + acctDto.getAcctCode() + "]余额不足");
				}
			}
			chargeAmount = calFeeDetailDto.getValue();
		}
		// 余额为减方向
		if (calFeeDetailDto.getMaBlanceBy().equals(AmountByEnum.REDUCE.getCode())) {
			chargeAmount = (-1)*calFeeDetailDto.getValue();
			// 余额不为负
			if (!isNegative) {
				if (acctDto.getBalance() < calFeeDetailDto.getValue()) {
					log.error("对于账户[" + acctDto.getAcctCode() + "]余额不足");
					throw new MaAcctBalanceException(ErrorExceptionEnum.ACCT_NO_SAVE_ACCOUNT, "对于账户[" + acctDto.getAcctCode() + "]余额不足");
				}
			}
		}
		// 更新借余额
		log.info("关于账户[" + acctDto.getAcctCode() + "]的余额是[" + acctDto.getBalance() + "]");
		return this.accountHelperService.executeUpdateCrebitBalance(chargeAmount, chargeAmount, acctDto.getAcctCode());
		

	}
	/**
	 * 根据信息操作贷方余额信息
	 * @param calFeeDetailDto
	 * @param acctDto
	 * @param isNegative
	 * @throws MaAcctBalanceException 
	 */
	private boolean handlerDebitBalance(CalFeeDetailDto calFeeDetailDto, AcctDto acctDto,boolean isNegative) throws MaAcctBalanceException{
		Long chargeAmount=new Long(0);
		// 查询账户属性，如果是中间账户不做账户验证，否则要对账户的止入，止出进行处理
		if (this.parameterHelperService.checkParameterAcctCodeOfMember(calFeeDetailDto.getAcctcode())) {
			AcctAttribDto acctAttribDto = this.accountHelperService.queryAcctAttrib(calFeeDetailDto.getAcctcode());
			// 验证账户是否止出
			if (acctAttribDto != null) {
				if (!acctAttribDto.getAllowOut().equals(ConstantHelper.ALLOW_OUT)) {
					log.error("对于账号[" + acctDto.getAcctCode() + "]止出");
					throw new MaAcctBalanceException(ErrorExceptionEnum.ACCT_ALLOWOUT_ERROR, "对于账号[" + acctDto.getAcctCode() + "]止出");
				}
			}
		}
		// 对余额方向进行处理
		// 余额方向为加
		if (calFeeDetailDto.getMaBlanceBy().equals(AmountByEnum.ADD.getCode())) {
			// 最冲正进行处理
			if (isNegative) {
				// 验证余额是否够一次冲正
				Long tmpAmount = -calFeeDetailDto.getValue();
				if (acctDto.getBalance() < tmpAmount) {
					log.error("对于账户[" + acctDto.getAcctCode() + "]余额不足");
					throw new MaAcctBalanceException(ErrorExceptionEnum.ACCT_NO_SAVE_ACCOUNT, "对于账户[" + acctDto.getAcctCode() + "]余额不足");
				}
			}
			chargeAmount = calFeeDetailDto.getValue();
		}// 余额为减方向
		if (calFeeDetailDto.getMaBlanceBy().equals(AmountByEnum.REDUCE.getCode())) {
			// 验证是否为负
			if (!isNegative) {
				if (acctDto.getBalance() < calFeeDetailDto.getValue()) {
					log.error("对于账户[" + acctDto.getAcctCode() + "]余额不足");
					throw new MaAcctBalanceException(ErrorExceptionEnum.ACCT_NO_SAVE_ACCOUNT, "对于账户[" + acctDto.getAcctCode() + "]余额不足");
				}
			}
			chargeAmount = (-1)*calFeeDetailDto.getValue();

		}
		// 更新贷方余额
		log.info("关于账户[" + acctDto.getAcctCode() + "]的余额是[" + acctDto.getBalance() + "]");
		return this.accountHelperService.executeUpdateDebitBalance(chargeAmount, chargeAmount, acctDto.getAcctCode());
		
	}
	

	private class BalanceTransCoreWork implements Runnable {
		private CalFeeDetailDto calFeeDetailDto;

		private Long serialNo;

		private boolean isNegative;

		public BalanceTransCoreWork(CalFeeDetailDto calFeeDetailDto, Long serialNo, boolean isNegative) {
			this.calFeeDetailDto = calFeeDetailDto;
			this.serialNo = serialNo;
			this.isNegative = isNegative;

		}

		@Override
		public void run() {
			try {
				executeAccountBalanceCoreTrans(calFeeDetailDto, serialNo, isNegative);
//				TransactionTemplate transactionTemplate = null;
//				transactionTemplate.setTimeout(300);
//				transactionTemplate.execute(TransactionCallbackWithoutResult)
				
			} catch (MaAcctBalanceException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}

	}

}
