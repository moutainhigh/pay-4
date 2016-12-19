package com.pay.pe.service.payment.posting.impl;
///**
// * 
// */
//package com.pay.pe.service.payment.posting.impl;
//
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//import com.pay.pe.dao.accounting.AccountEntryDAO;
//import com.pay.pe.dao.accounting.PostingDao;
//import com.pay.pe.dao.accounting.impl.PostingProcAcctParameter;
//import com.pay.pe.dao.accounting.impl.PostingProcEntryParameter;
//import com.pay.pe.dao.accounting.impl.PostingProcParameter;
//import com.pay.pe.dto.AccountEntryDTO;
//import com.pay.pe.dto.AccountEntryDTOUtil;
//import com.pay.pe.dto.AcctSpecDTO;
//import com.pay.pe.exception.ErrorCodeType;
//import com.pay.pe.exception.PEBisnessRuntimeException;
//import com.pay.pe.helper.CRDBTYPE;
//import com.pay.pe.model.AccountEntry;
//import com.pay.pe.service.account.AcctSpecService;
//import com.pay.pe.service.closing.AccountingIntervalService;
//import com.pay.pe.service.payment.common.LogUtil;
//import com.pay.pe.service.payment.posting.PostingService;
//import com.pay.pe.util.PELogger;
//import com.pay.util.StringUtil;
//
//public final class PostingServiceImpl implements PostingService {
//	/**
//	 * 日志.
//	 */
//	private PELogger logger = PELogger.getLogger(PostingServiceImpl.class);
//	
//	/**
//	 * Deprecated.
//	 */
//	private AccountEntryDAO accountEntryDao;
//
////	private AccountService accountService;
//
//	/**
//	 * Deprecated.
//	 */
//	private PostingDao postingDao;
//	
//	
//	
//	/**
//	 * 得到帐户对应的科目.
//	 */
//	private AcctSpecService acctSpecService;
//	
//	/**
//	 * 得到帐户对应的科目.
//	 */
//	
//	
//	private AccountingIntervalService accountingIntervalService;
//
//	public AccountingIntervalService getAccountingIntervalService() {
//		return accountingIntervalService;
//	}
//
//	public void setAccountingIntervalService(
//			AccountingIntervalService accountingIntervalService) {
//		this.accountingIntervalService = accountingIntervalService;
//	}
//
//	/**
//	 * @return Returns the acctSpecService.
//	 */
//	public AcctSpecService getAcctSpecService() {
//		return acctSpecService;
//	}
//
//	/**
//	 * @param acctSpecService The acctSpecService to set.
//	 */
//	public void setAcctSpecService(AcctSpecService acctSpecService) {
//		this.acctSpecService = acctSpecService;
//	}
//
//	
//
//
//
//	
//	
//	public long postingByProc(final List<AccountEntryDTO> accountEntryDTOs)throws PEBisnessRuntimeException{
//		
//		
//		List<AccountEntry> entries = AccountEntryDTOUtil.convertDTOtoAccountEntry(accountEntryDTOs);
//		
//		Iterator <AccountEntry> it = entries.iterator();
//		PostingProcParameter procPara = new PostingProcParameter();
//		ArrayList <PostingProcAcctParameter> asyncAcctParameters = new ArrayList <PostingProcAcctParameter>();
//		
//		Date transactiondate = new Date();
//		boolean isPassed = false;
//		while (it.hasNext()) {
//			PostingProcAcctParameter acctPara = new PostingProcAcctParameter();
//			PostingProcEntryParameter entryPara = new PostingProcEntryParameter();
//			AccountEntry entry = it.next();
//			
//			//如果会计日期为空则用当前时间作为会计日期  add by haidy.wei20100415
//			if(entry.getTransactionDate()==null){
//				entry.setTransactionDate(transactiondate);
//				//如果是当前日期，肯定没有轧过帐
//				isPassed = true;
//			}
//			
//			//判断会计日期是否已经被轧过帐，只判断一次  add by haidy.wei20100415
//			if(!isPassed){
//				isPassed = accountingIntervalService.isAvailable(entry.getTransactionDate());
//				if(!isPassed){
//					throw new PEBisnessRuntimeException(ErrorCodeType.TRANSACTIONDATE_IS_CLOSED,"transactiondate=" + entry.getTransactionDate());
//				}
//			}
//			
//			
//			entryPara.setAccountCode(entry.getAcctCode());
//			//异步记帐.
//			if (0 == entry.getStatus()) {
//				entryPara.setStatus(0);//AccountEntryStatus.UnPosting.getValue()
//			} else {
//				//同步记帐.
//				entryPara.setStatus(1);//AccountEntryStatus.Posted.getValue()
//			}
//			
//			entryPara.setAmount(entry.getValue());
//			entryPara.setCrdr(entry.getCrdr().intValue());
//			entryPara.setDealId(""+entry.getDealid());
//			entryPara.setEntryCode(entry.getEntryCode());
//			entryPara.setMemo(entry.getText());
//			entryPara.setPaymentServiceCode(entry.getPaymentServiceCode());//FS:set 币种
//			//设置记账币种和汇率
//			entryPara.setCurrencyCode(entry.getCurrencyCode());
//			entryPara.setExchangeRate(entry.getExchangeRate());
//			entryPara.setTransactiondate(entry.getTransactionDate());//设置交易时间
//			procPara.addEntryPara(entryPara);
//			
//			AcctSpecDTO acct = getAcctSpec(entry.getAcctCode());
//			if (null == acct) {
//				throw new PEBisnessRuntimeException(ErrorCodeType.SUBJECT_IS_NULL,"acctcode=" + entry.getAcctCode());
//			}
//			
//			
//			acctPara.setAccountCode(entry.getAcctCode());
//			
//			
//			// 修改帐户的余额与记分录是在同一个事务中，因此，可以认为分录已经被反映到帐户的余额上.
//			boolean balanceByDebit = isBalanceByDebit(acct);
//			
//			boolean canBeNegativeBalance = 1==acct.getNegativeBalance();
//			acctPara.setCanBeNegativeBalance(canBeNegativeBalance);
//			if (isDebit(entry)) { // 借
//				if (balanceByDebit) {
//					acctPara.setBalance(entry.getValue());
//					// balance为正数，收方不判断帐户余额
////					acctPara.setCanBeNegativeBalance(true);
//				} else {
//					acctPara.setBalance(-entry.getValue());
//				}
//				acctPara.setCreditBalance(0L);
//				acctPara.setDebitBalance(entry.getValue());
//			} else { // 贷
//				if (balanceByDebit) {
//					acctPara.setBalance(-entry.getValue());
//				} else {
//					acctPara.setBalance(entry.getValue());
//					// balance为正数，收方不判断帐户余额
////					acctPara.setCanBeNegativeBalance(true);
//				}
//				acctPara.setCreditBalance(entry.getValue());
//				acctPara.setDebitBalance(0L);
//			}
//			// 如果是更新内部账号的余额,则以异步的方式更新.
//			//0 表示此分录需要异步记帐.
//			if (0 == entry.getStatus()) {
//				asyncAcctParameters.add(acctPara);
//			} else {
//				procPara.addAcctPara(acctPara);
//			}
//		}
//		
//		if (!validatePostingEntries(procPara)) {
//			//如果借贷不平衡则打印出分录的相关信息
//			StringBuilder str = new StringBuilder();
//			str.append("entries:(");
//			for(PostingProcEntryParameter entrypara : procPara.getEntryParameters()){
//				str.append("acctcode=");
//				str.append(entrypara.getAccountCode());
//				str.append(",crdr=");
//				str.append(entrypara.getCrdr());
//				str.append(",amount=");
//				str.append(entrypara.getAmount());
//				str.append(";");
//			}
//			str.append(")");			
//			// 借贷不平衡抛错
//			throw new PEBisnessRuntimeException(ErrorCodeType.DRCR_NOT_BALANCE,str.toString());
//		}
//		//调用存储过程来记账
//		long voucherCode = getPostingDao().callPostingProc(procPara);
//		
//		
//		//发消息进行AcctDairyPosting
////		List<Integer> entryCodeList = new ArrayList<Integer>();
////		for (AccountEntry entry : entries) {
////			entryCodeList.add(entry.getEntrycode());
////		}
////		AcctDiaryUpdateMsg msg = new AcctDiaryUpdateMsg(voucherCode, entryCodeList.toArray(new Integer[entryCodeList.size()]));
////		NotifyRequest acctDiaryUpdateNotifyRequest = new AcctDiaryUpdateNotifyRequest(msg);
////
////		sendMessageOrAddTasks(acctDiaryUpdateNotifyRequest, tasks, true);
//		// 如果有需要异步记帐的记录. 通知异步更新余额.
//		// 通知如果错误是不处理异常的.
//		
//
//		return voucherCode;
//	}
//	
//	
//	
//	/* (non-Javadoc)
//	 * 
//	 */
////	public long posting(final List<AccountEntryDTO> accountEntryDTOs) 
////	throws PEBisnessRuntimeException {
//////		StringBuffer entries_str = new StringBuffer();
//////		for(AccountEntryDTO accountEntryDTO:accountEntryDTOs){
//////			entries_str.append("[")
//////					.append(accountEntryDTO.toString())
//////					.append("]");
//////		}
////		
////		List<AccountEntry> entries = AccountEntryDTOUtil
////				.convertDTOtoAccountEntry(accountEntryDTOs);
////		Long voucherCode = accountEntryDao.generateVoucherCode();
////		for (AccountEntry entry : entries) {
////			entry.setVoucherCode(voucherCode);
////			//  add
////			// check if the account existed.
//////			String accountCode = entry.getAcctCode();
//////			entry.setAcctCode(accountDto.getAcctcode());
////			
////			
////			// 修改帐户的余额与记分录是在同一个事务中，因此，可以认为分录已经被反映到帐户的余额上.
////			entry.setStatus(1);//AccountEntryStatus.Posted.getValue()
////			
////			accountEntryDao.insertAccountEntry(entry);
////			AcctSpecDTO acct = getAcctSpecService().getAcctSpec(String.valueOf(accountDto
////					.getAcctcode()));
////			boolean balanceByDebit = isBalanceByDebit(acct);
////			if (isDebit(entry)) { // 借
////				if (balanceByDebit) {
////					accountDto.setBalance(accountDto.getBalance() + entry.getValue());
////				} else {
////					accountDto.setBalance(accountDto.getBalance() - entry.getValue());
////				}
////				accountDto.setDebitbalance(accountDto.getDebitbalance()
////						+ entry.getValue());
////			} else { // 贷
////				if (balanceByDebit) {
////					accountDto.setBalance(accountDto.getBalance() - entry.getValue());
////				} else {
////					accountDto.setBalance(accountDto.getBalance() + entry.getValue());
////				}
////				accountDto.setCreditbalance(accountDto.getCreditbalance()
////						+ entry.getValue());
////			}
////			// 如果是更新内部账号的余额,则以异步的方式更新.
////			// TODO 可以在账号规则上增加一个关于是否允许异步更新余额的属性.
////			
////			accountService.changeAccount(accountDto);
////			
////		}
////		logger.info(LogUtil.wrapLog("posting",
////				"Success",
////				null,null,
////				addition,null,
////				null,null));
////		addition = null;
////		return voucherCode;
////	}
//	
//	/**
//	 * 根据会员账号或者银行账号或者系统内部账号得到对应的科目对象.
//	 * @param acctCode String
//	 * @return AcctSpecDTO
//	 */
////	private AcctSpecDTO getAcctSpec(final String acctCode) {
////		logger.info(LogUtil.wrapLog("getAcctSpec",
////				"Start",
////				acctCode,
////				null,null,null,null,null));
////		AcctSpecDTO acct = null;
////		MemberAcctSpecDTO spec = getMemberAcctSpecService().getByAcctCode(acctCode);
////		
////		//[PE重构]:通过查询acctspecservice替换dto依赖
////		if (null != spec) {
////			acct = getAcctSpecService().getAcctSpec(spec.getParentCOA());
////		}
////		if (null == acct) {
////			acct = getAcctSpecService().getAcctSpec(acctCode);
////		}
////		logger.info(LogUtil.wrapLog("getAcctSpec",
////				"Success",
////				acctCode,
////				null,null,null,null,null));
////		return acct;
////	}
//
//	// 判断借贷, true:借, false:贷
//	private boolean isDebit(final AccountEntry entry) {
//		if (entry.getCrdr() == CRDBTYPE.DEBIT.getValue()) {
//			return true;
//		}
//		return false;
//	}
//	
//	/**
//	 * 在调用存储过程之前，验证参数的合法性，目前验证：
//	 * Entries是否借贷平衡
//	 * @param procPara
//	 * @return false验证失败，true成功
//	 */
//	private boolean validatePostingEntries(PostingProcParameter procPara) {
//		logger.info(LogUtil.wrapLog("validatePostingEntries",
//				"Start",
//				null,null,null,null,null,null));
//		try {
//			List<PostingProcEntryParameter> entries = procPara.getEntryParameters();
//			long crAmount = 0L;
//			long drAmount = 0L;
//			PostingProcEntryParameter entry = null;
//			for (int i = 0; entries != null && i < entries.size(); i++) {
//				entry = entries.get(i);
//				if (entry.getCrdr() == CRDBTYPE.DEBIT.getValue()) {
//					drAmount = drAmount + entry.getAmount();
//				} else {
//					crAmount = crAmount + entry.getAmount();
//				}
//			}
//			if (Math.abs(drAmount) == Math.abs(crAmount)) {
//				logger.info(LogUtil.wrapLog("validatePostingEntries",
//						"Success",
//						null,null,null,null,null,null));
//				return true;
//			}
//		} catch(Exception e) {
//			logger.info(LogUtil.wrapLog("validatePostingEntries",
//					"Exception",
//					null,null,null,e.getMessage(),null,null));
//			// do not handle the exception
//		}
//		logger.info(LogUtil.wrapLog("validatePostingEntries",
//				"Fail",
//				null,null,null,null,null,null));
//		return false;
//	}
//
//	/**
//	 * 得到帐户的余额方向.
//	 * 
//	 * @param acctCode
//	 *            科目代码
//	 * @return boolean
//	 */
//	private boolean isBalanceByDebit(final AcctSpecDTO acct) {
//		if (null != acct) {
//			if (acct.getBalanceBy().intValue() == CRDBTYPE.DEBIT.getValue()) {
//				return true;
//			}
//		}
//		return false;
//	}
//	
//	
//	/* (non-Javadoc)
//	 * 
//	 */
//	public void changeEntriesStatus(final String voucherCode, final int status) {
//		Map addition = new HashMap();
//		addition.put("status", status);
//		logger.info(LogUtil.wrapLog("changeEntriesStatus",
//				"Start",
//				voucherCode,
//				null,
//				addition,null,null,null));
//		if (StringUtil.isEmpty(voucherCode)){
//			logger.info(LogUtil.wrapLog("changeEntriesStatus",
//					"Exception",
//					voucherCode,
//					null,
//					addition,null,null,"voucherCode must not be empty"));
//			addition = null;
//			throw new IllegalArgumentException("voucherCode must not be empty");
//		
//		}
//		getPostingDao().changeEntriesStatus(voucherCode, status);
//		logger.info(LogUtil.wrapLog("changeEntriesStatus",
//				"Success",
//				voucherCode,
//				null,
//				addition,null,null,null));
//		addition = null;
//	}
//
//	/* (non-Javadoc)
//	 * 
//	 */
//	public long callPostingProc(final PostingProcParameter procPara)throws PEBisnessRuntimeException{
//		logger.info(LogUtil.wrapLog("callPostingProc",
//				"Start",
//				null,null,null,
//				null,null,null));
//		if (!validatePostingEntries(procPara)) {
//			logger.info(LogUtil.wrapLog("callPostingProc",
//					"Exception",
//					null,null,null,
//					null,null,"Dr/Cr not balance"));
//			throw new PEBisnessRuntimeException(ErrorCodeType.DRCR_NOT_BALANCE);
//		}
//		long vouchercode = getPostingDao().callPostingProc(procPara);
//		logger.info(LogUtil.wrapLog("callPostingProc",
//				"Success",
//				null,null,null,
//				null,null,null));
//		return vouchercode;
//	}
//
//	
//
//	/**
//	 * @return Returns the postingDao.
//	 */
//	public PostingDao getPostingDao() {
//		return postingDao;
//	}
//
//	/**
//	 * @param postingDao
//	 *            The postingDao to set.
//	 */
//	public void setPostingDao(PostingDao postingDao) {
//		this.postingDao = postingDao;
//	}
//
//	/**
//	 * @return Returns the accountEntryDao.
//	 */
//	public AccountEntryDAO getAccountEntryDao() {
//		return accountEntryDao;
//	}
//
//	/**
//	 * @param accountEntryDao
//	 *            The accountEntryDao to set.
//	 */
//	public void setAccountEntryDao(AccountEntryDAO accountEntryDao) {
//		this.accountEntryDao = accountEntryDao;
//	}
//
//	/**
//
//
//	/**
//	 * 
//	 * 
//	 */
//	public void changeEntryStatus(final String voucherCode, final Integer entryCode,
//			final int status) {
//		Map addition = new HashMap();
//		addition.put("entryCode", entryCode);
//		addition.put("orderNo", status);
//		logger.info(LogUtil.wrapLog("changeEntryStatus",
//							"Start",
//							voucherCode,
//							null,
//							addition,null,null,null));
//		if (StringUtil.isEmpty(voucherCode)){
//			logger.info(LogUtil.wrapLog("changeEntryStatus",
//					"Exception",
//					voucherCode,
//					null,
//					addition,null,null,"voucherCode must not be empty"));
//			addition = null;
//			throw new IllegalArgumentException("voucherCode must not be empty");
//		}
//		if (entryCode == null){
//			logger.info(LogUtil.wrapLog("changeEntryStatus",
//					"Exception",
//					voucherCode,
//					null,
//					addition,null,null,"entryCode must not be null"));
//			addition = null;
//			throw new IllegalArgumentException("entryCode must not be null");
//		}
//		postingDao.changeEntryStatus(voucherCode, entryCode, status);
//		logger.info(LogUtil.wrapLog("changeEntryStatus",
//				"Success",
//				voucherCode,
//				null,
//				addition,null,null,null));
//	}
//
////	/**
////	 * 
////	 * 
////	 */
////	public void asyncPosting(List<PostingProcAcctParameter> acctParameters,
////			String voucherCode) {
////		logger.info(LogUtil.wrapLog("asyncPosting",
////				"Start",
////				null,null,null,null,null,null));
////		//更改余额.
////		PostingProcParameter procPara = new PostingProcParameter();
////		procPara.setAcctParameters(acctParameters);
////		procPara.setEntryParameters(new ArrayList<PostingProcEntryParameter>());
////		callPostingProc(procPara);
////		//更改分录状态.
////		changeEntriesStatus(String.valueOf(voucherCode), AccountEntryStatus.Posted.getValue());
////		logger.info(LogUtil.wrapLog("asyncPosting",
////				"Success",
////				null,null,null,null,null,null));
////	}
//}
