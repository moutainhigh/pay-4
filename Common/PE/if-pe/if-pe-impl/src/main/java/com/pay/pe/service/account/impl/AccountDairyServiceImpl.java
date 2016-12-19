package com.pay.pe.service.account.impl;
//package com.pay.pe.service.account.impl;
//
//import java.util.ArrayList;
//import java.util.Collections;
//import java.util.Comparator;
//import java.util.HashMap;
//import java.util.Iterator;
//import java.util.List;
//import java.util.Map;
//
//import com.pay.pe.dao.account.AccountDairyDao;
//import com.pay.pe.dao.account.AcctDiaryPostingDao;
//import com.pay.pe.dto.AccountDTO;
//import com.pay.pe.dto.AccountDairyDTO;
//import com.pay.pe.dto.AccountDairyDTOUtil;
//import com.pay.pe.dto.AccountEntryDTO;
//import com.pay.pe.dto.AcctSpecDTO;
//import com.pay.pe.dto.MemberAcctSpecDTO;
//import com.pay.pe.exception.ErrorCodeType;
//import com.pay.pe.exception.PEBisnessRuntimeException;
//import com.pay.pe.service.account.AccountDairyService;
//import com.pay.pe.service.account.AccountService;
//import com.pay.pe.service.account.AcctDiaryUpdateMsg;
//import com.pay.pe.service.account.AcctSpecService;
//import com.pay.pe.service.account.MemberAcctSpecService;
//import com.pay.pe.service.accounting.AccountEntryService;
//import com.pay.pe.service.payment.common.LogUtil;
//import com.pay.pe.util.PELogger;
//import com.pay.pe.util.MfDate;
//
///**
// * 
// *
// */
//public class AccountDairyServiceImpl implements AccountDairyService {
//	/**
//	 * 日志.
//	 */
//	private PELogger logger = PELogger.getLogger(AccountDairyServiceImpl.class);
//	
//    /**
//     * 引入dao.
//     */
//    private AccountDairyDao accountDariyDao;
//    
////    private AccountService accountService;
//    
//    
//    /**
//     * 日记账更新DAO
//     * 调用存储过程进行日记账更新
//     */
//    private AcctDiaryPostingDao acctDiaryPostingDao;
//    
//    /**
//     * 账户说明DAO
//     * 用以判断账户的日记账更新方式
//     */
//    private AcctSpecService acctSpecService;
//    
//    /**
//     * 会员账户说明DAO
//     * 用以获得会员账户对应的科目的账户说明
//     */
//    private MemberAcctSpecService memberAcctSpecService;
//    
//    /**
//     * 通知服务
//     * 将延时更新的日记账放置到消息服务中
//     */
////    private NotifyService notifyService;
//
//    /**
//     * 分录服务
//     * 用以根据凭证号和分录号获得AccountEntryDTO
//     */
//    private AccountEntryService acctEntryService;
//    
//    /* (non-Javadoc)
//     * 
//     */
//    @SuppressWarnings("unchecked")
//	public AccountDairyDTO getByAcctcode(String acctcode, MfDate date) {
//    	Map mainIden = new HashMap();
//    	Map addition = new HashMap(); 
//    	addition.put("acctcode", acctcode);
//    	addition.put("date", date);
//    	
//    	logger.info(
//			LogUtil.wrapLog(
//				"getByAcctcode", 
//					"Start", 
//					null, 
//					mainIden, 
//					addition,
//					null, 
//					null, 
//					null));
//    	
//        assert null != acctcode;
//        assert null != date;
//        AccountDTO accountDto = accountService.getAccountByCode(acctcode);
//        assert null != accountDto;
//        
//        AccountDairyDTO result = AccountDairyDTOUtil.convertToDto(this.accountDariyDao.getDairyByAccount(accountDto.getAcctcode(), date));
//        
//        addition.put("result", result);
//        logger.info(
//			LogUtil.wrapLog(
//				"getByAcctcode", 
//					"Success", 
//					null, 
//					mainIden, 
//					addition,
//					null, 
//					null, 
//					null));
//        mainIden = null;
//        addition = null;
//    	
//        
//        return result;
//    }
//
//    /* (non-Javadoc)
//     * 
//     */
//    @SuppressWarnings("unchecked")
//	public long getDebitBal(String acctcode, MfDate date) {
//    	Map mainIden = new HashMap();
//    	Map addition = new HashMap(); 
//    	addition.put("acctcode", acctcode);
//    	addition.put("date", date);
//    	
//    	logger.info(
//			LogUtil.wrapLog(
//				"getDebitBal", 
//					"Start", 
//					null, 
//					mainIden, 
//					addition,
//					null, 
//					null, 
//					null));
//    	
//        assert null != acctcode;
//        assert null != date;
//        AccountDairyDTO dto = this.getByAcctcode(acctcode, date);
//        if (null != dto) {
//        	long result = dto.getDebitBalance();
//        	
//        	addition.put("result", result);
//            logger.info(
//    			LogUtil.wrapLog(
//    				"getDebitBal", 
//    					"Success", 
//    					null, 
//    					mainIden, 
//    					addition,
//    					null, 
//    					null, 
//    					null));
//        	
//            return result;
//        }
//        
//        addition.put("result", 0);
//        logger.info(
//			LogUtil.wrapLog(
//				"getDebitBal", 
//					"Success", 
//					null, 
//					mainIden, 
//					addition,
//					null, 
//					null, 
//					null));
//        mainIden = null;
//        addition = null;
//        
//        return 0;
//    }
//    /*
//     * (non-Javadoc)
//     * 
//     */
//    @SuppressWarnings("unchecked")
//	public long getDebitBalByMbrAcctCode(String MbrAcctCode , MfDate date) {
//    	Map mainIden = new HashMap();
//    	Map addition = new HashMap(); 
//    	addition.put("MbrAcctCode", MbrAcctCode);
//    	addition.put("date", date);
//    	
//    	logger.info(
//			LogUtil.wrapLog(
//				"getDebitBalByMbrAcctCode", 
//					"Start", 
//					null, 
//					mainIden, 
//					addition,
//					null, 
//					null, 
//					null));
//    	
//        MemberAcctSpecDTO mbrAcctSpecDto = this.memberAcctSpecService.getMemberAcctSpec(MbrAcctCode);
//        if (null == mbrAcctSpecDto) {
//        	addition.put("result", 0);
//        	logger.info(
//    			LogUtil.wrapLog(
//    				"getDebitBalByMbrAcctCode", 
//    					"Success", 
//    					null, 
//    					mainIden, 
//    					addition,
//    					null, 
//    					null, 
//    					null));
//        	
//            return 0;
//        }
//        
//        long result = this.getDebitBal(mbrAcctSpecDto.getAcctcode(), date);
//        
//        addition.put("result", result);
//        logger.info(
//			LogUtil.wrapLog(
//				"getDebitBalByMbrAcctCode", 
//					"Success", 
//					null, 
//					mainIden, 
//					addition,
//					null, 
//					null, 
//					null));
//        
//        return result;
//    }
//    /*
//     *  (non-Javadoc)
//     * 
//     */
//    @SuppressWarnings("unchecked")
//	public long getCreditBal(String acctcode , MfDate date) {
//    	Map mainIden = new HashMap();
//    	Map addition = new HashMap(); 
//    	addition.put("acctcode", acctcode);
//    	addition.put("date", date);
//    	
//    	logger.info(
//			LogUtil.wrapLog(
//				"getCreditBal", 
//					"Start", 
//					null, 
//					mainIden, 
//					addition,
//					null, 
//					null, 
//					null));
//    	
//        assert null != acctcode;
//        assert null != date;
//        AccountDairyDTO dto = this.getByAcctcode(acctcode, date);
//        if (null != dto) {
//        	long result = dto.getCreditBalance();
//        	
//        	addition.put("result", result);
//            logger.info(
//    			LogUtil.wrapLog(
//    				"getCreditBal", 
//    					"Success", 
//    					null, 
//    					mainIden, 
//    					addition,
//    					null, 
//    					null, 
//    					null));
//        	
//            return result;
//        }
//        
//        addition.put("result", 0);
//        logger.info(
//			LogUtil.wrapLog(
//				"getCreditBal", 
//					"Success", 
//					null, 
//					mainIden, 
//					addition,
//					null, 
//					null, 
//					null));
//        mainIden = null;
//        addition = null;
//        
//        return 0;
//    }
//    /*
//     * (non-Javadoc)
//     * 
//     */
//    @SuppressWarnings("unchecked")
//	public long getCreditBalByMbrAcctCode(String MbrAcctCode , MfDate date) {
//    	Map mainIden = new HashMap();
//    	Map addition = new HashMap(); 
//    	addition.put("MbrAcctCode", MbrAcctCode);
//    	addition.put("date", date);
//    	
//    	logger.info(
//			LogUtil.wrapLog(
//				"getCreditBalByMbrAcctCode", 
//					"Start", 
//					null, 
//					mainIden, 
//					addition,
//					null, 
//					null, 
//					null));
//    	
//        MemberAcctSpecDTO mbrAcctSpecDto = this.memberAcctSpecService.getMemberAcctSpec(MbrAcctCode);
//        if (null == mbrAcctSpecDto) {
//        	addition.put("result", 0);
//        	logger.info(
//    			LogUtil.wrapLog(
//    				"getCreditBalByMbrAcctCode", 
//    					"Success", 
//    					null, 
//    					mainIden, 
//    					addition,
//    					null, 
//    					null, 
//    					null));
//        	
//            return 0;
//        }
//        
//        long result = this.getCreditBal(mbrAcctSpecDto.getAcctcode(), date);
//        
//        addition.put("result", result);
//        logger.info(
//			LogUtil.wrapLog(
//				"getCreditBalByMbrAcctCode", 
//					"Success", 
//					null, 
//					mainIden, 
//					addition,
//					null, 
//					null, 
//					null));
//        mainIden = null;
//        addition = null;
//        
//        return result;
//    }
//   
//    public void setAccountService(AccountService accountService) {
//		this.accountService = accountService;
//	}
//
//	public void setAccountDariyDao(AccountDairyDao accountDariyDao) {
//        this.accountDariyDao = accountDariyDao;
//    }
//    
//    
//    //Added by Albert, Apr 18, 2007
//    @SuppressWarnings("unchecked")
//	public void posting(long voucherCode, List <AccountEntryDTO> acctEntries){
//    	Map mainIden = new HashMap();
//    	Map addition = new HashMap(); 
//    	addition.put("voucherCode", voucherCode);
//    	addition.put("acctEntries", acctEntries);
//    	
//    	logger.info(
//			LogUtil.wrapLog(
//				"posting", 
//					"Start", 
//					null, 
//					mainIden, 
//					addition,
//					null, 
//					null, 
//					null));
//    	
//    	if (0 == voucherCode || null == acctEntries || acctEntries.size()==0){
//    		logger.info(
//				LogUtil.wrapLog(
//					"posting", 
//						"Success", 
//						null, 
//						mainIden, 
//						addition,
//						null, 
//						null, 
//						null));
//    		return;
//    	}else{
//    		logger.debug("Got voucher of " + voucherCode + " and " + acctEntries.size() + " entries.");
//    		ArrayList<Integer> instant = new ArrayList<Integer>();	//即时更新分录
//    		ArrayList<Integer> noninstant = new ArrayList<Integer>(); //延时更新分录
//    		Iterator <AccountEntryDTO> it = acctEntries.iterator();
//    		
//    		while (it.hasNext()){
//    			AccountEntryDTO entry = it.next();
//    			//分录的账户代码
//    			String entryAcctCode = entry.getAcctcode();
//    			    			
//    			//获得AcctSpecDTO，从而获得日记账的更新方式
//    			//尝试分录中的账户代码是否是科目表中直接定义的账户
//    			AcctSpecDTO acctSpecDto = acctSpecService.getAcctSpec(entryAcctCode);
//    			
//    			//如果分录中的账户代码非科目表中直接定义的账户，即会员账户；则通过会员账户说明取得对应的科目
//    			if (null == acctSpecDto){
//    				MemberAcctSpecDTO mbrAcctSpecDto = memberAcctSpecService.getByAcctCode(entryAcctCode);
//    				if (null == mbrAcctSpecDto)  {
//    					logger.info(
//							LogUtil.wrapLog(
//								"posting", 
//									"Fail", 
//									null, 
//									mainIden, 
//									addition,
//									"Account Not Found : " + entryAcctCode, 
//									null, 
//									"Account Not Found : " + entryAcctCode));
//    					
//    					throw new PEBisnessRuntimeException(ErrorCodeType.MEMBERACCT_IS_NULL,"acctcode:"+entryAcctCode);
//    				}
//    				
//    				//[PE重构]:通过acctSpecService查询，代替原来的DTO依赖
//    				acctSpecDto = acctSpecService.getAcctSpec(mbrAcctSpecDto.getParentCOA());
//    			}	
//				
//    			if (null != acctSpecDto){
//    				int entryCode = entry.getEntrycode();
//    				int updateMethod = acctSpecDto.getAcctDiaryUpdateMethod();
//    				//如果日记账延时更新，把分录号放到延时更新数组中
//    				if (1 == updateMethod){
//    					noninstant.add(entryCode);
//    				}else if(2 == updateMethod){
//    					instant.add(entryCode);
//    				}
//    			}else{
//    				logger.info(
//						LogUtil.wrapLog(
//							"posting", 
//								"Fail", 
//								null, 
//								mainIden, 
//								addition,
//								"Account Not Found : " + entryAcctCode, 
//								null, 
//								"Account Not Found : " + entryAcctCode));
//    				
//    				throw new PEBisnessRuntimeException(ErrorCodeType.SUBJECT_IS_NULL,"acctcode:"+entryAcctCode);
//    			}
//    		}
//			//分别处理即时更新和延时更新的日记账
//    		
//    		if (!instant.isEmpty()) {
//    			Integer[] in = (Integer[])instant.toArray(new Integer[instant.size()]);
//    			instantPosting(voucherCode, in);
//    		}
//    		
//    		if (!noninstant.isEmpty()) {
//    			Integer[] non = (Integer[])noninstant.toArray(new Integer[noninstant.size()]);
//    			noninstantPosting(voucherCode, non);
//    		}
//    	}
//    	
//    	logger.info(
//			LogUtil.wrapLog(
//				"posting", 
//					"Success", 
//					null, 
//					mainIden, 
//					addition,
//					null, 
//					null, 
//					null));
//    	mainIden = null;
//    	addition = null;
//    }
//
//    @SuppressWarnings("unchecked")
//	public void posting(long voucherCode, int[] acctEntries){
//    	Map mainIden = new HashMap();
//    	Map addition = new HashMap(); 
//    	addition.put("voucherCode", voucherCode);
//    	addition.put("acctEntries", acctEntries);
//    	
//    	logger.info(
//			LogUtil.wrapLog(
//				"posting", 
//					"Start", 
//					null, 
//					mainIden, 
//					addition,
//					null, 
//					null, 
//					null));
//    	
//    	if(0 == voucherCode || null == acctEntries || 0 == acctEntries.length){
//    		logger.info(
//				LogUtil.wrapLog(
//					"posting", 
//						"Success", 
//						null, 
//						mainIden, 
//						addition,
//						null, 
//						null, 
//						null));
//    		
//    		return;
//    	}else{
//    		logger.debug("Got voucher of " + voucherCode + " and " + acctEntries.length + " entries.");
//    		
//    		//根据凭证号和分录号获得DTO，构造成ArrayList<AccountEntryDTO>，再使用多态方法处理
//    		ArrayList<AccountEntryDTO> entries = new ArrayList<AccountEntryDTO>();
//    	  	
//    		for(int i=0; i< acctEntries.length; i++){
//    			AccountEntryDTO dto = acctEntryService.getAccountEntry(voucherCode, acctEntries[i]);
//    			entries.add(dto);
//    		}
//    		
//    		posting(voucherCode, entries);
//    	}
//    	logger.info(
//			LogUtil.wrapLog(
//				"posting", 
//					"Success", 
//					null, 
//					mainIden, 
//					addition,
//					null, 
//					null, 
//					null));
//    	mainIden = null;
//    	addition = null;
//    }
//    
//    @SuppressWarnings("unchecked")
//	public void posting(long voucherCode){
//    	Map mainIden = new HashMap();
//    	Map addition = new HashMap(); 
//    	addition.put("voucherCode", voucherCode);
//    	
//    	logger.info(
//			LogUtil.wrapLog(
//				"posting", 
//					"Start", 
//					null, 
//					mainIden, 
//					addition,
//					null, 
//					null, 
//					null));
//    	
//    	if(0 == voucherCode){
//    		logger.info(
//				LogUtil.wrapLog(
//					"posting", 
//						"Success", 
//						null, 
//						mainIden, 
//						addition,
//						null, 
//						null, 
//						null));
//    		return;
//    	}else{
//    		logger.debug("Got voucher of " + voucherCode + " and its all entries.");
//    		
//    		//根据凭证号获得ArrayList<AccountEntryDTO>，再使用多态方法处理
//    		List<AccountEntryDTO> entries = acctEntryService.getAccountEntryByVoucherCode("" + voucherCode);
//    		sortEntries(entries);
//    		posting(voucherCode, entries);
//    	}
//    	
//    	logger.info(
//			LogUtil.wrapLog(
//				"posting", 
//					"Success", 
//					null, 
//					mainIden, 
//					addition,
//					null, 
//					null, 
//					null));
//    	mainIden = null;
//    	addition = null;
//    }
//	/**
//	 * @return the acctSpecService
//	 */
//	public AcctSpecService getAcctSpecService() {
//		return acctSpecService;
//	}
//
//	/**
//	 * @param acctSpecService the acctSpecService to set
//	 */
//	public void setAcctSpecService(AcctSpecService acctSpecService) {
//		this.acctSpecService = acctSpecService;
//	}
//
//	/**
//	 * 即时更新日记账
//	 * 直接调用DAO完成日记账的更新
//	 * @param voucherCode String
//	 * @param entries List<String>
//	 */
//	@SuppressWarnings("unchecked")
//	private void instantPosting(long voucherCode, Integer[] entries) {
//		Map mainIden = new HashMap();
//    	Map addition = new HashMap(); 
//    	addition.put("voucherCode", voucherCode);
//    	addition.put("entries", entries);
//    	
//    	logger.info(
//			LogUtil.wrapLog(
//				"instantPosting", 
//					"Start", 
//					null, 
//					mainIden, 
//					addition,
//					null, 
//					null, 
//					null));
//		
//		acctDiaryPostingDao.callPostingProc(voucherCode, entries);
//		logger.debug(entries.length + " entries of " + voucherCode + " were updated instantly.");
//		
//		logger.info(
//			LogUtil.wrapLog(
//				"instantPosting", 
//					"Success", 
//					null, 
//					mainIden, 
//					addition,
//					null, 
//					null, 
//					null));
//		mainIden = null;
//		addition = null;
//	}
//
//	/**
//	 * 延时更新日记账
//	 * 降延时分录对象以凭证为单位发送到延时处理队列中
//	 * @param voucherCode String
//	 * @param entries List<String>
//	 */
//	private void noninstantPosting(long voucherCode, Integer[] entries){
//		Map mainIden = new HashMap();
//    	Map addition = new HashMap(); 
//    	addition.put("voucherCode", voucherCode);
//    	addition.put("entries", entries);
//    	
////    	logger.info(
////			LogUtil.wrapLog(
////				"noninstantPosting", 
////					"Start", 
////					null, 
////					mainIden, 
////					addition,
////					null, 
////					null, 
////					null));
////		
////		AcctDiaryUpdateMsg msg = new AcctDiaryUpdateMsg(voucherCode, entries);
////		AcctDiaryUpdateNotifyRequest request = new AcctDiaryUpdateNotifyRequest(msg);
////		request.setReferenceId("" + voucherCode);
////		request.setInvokeClass(this.getClass());
////		try {
////			getNotifyService().notify(request);
////			logger.debug(entries.length + " entries of " + voucherCode + " were put off to noninstant update.");
////		} catch (NotifyException e) {
////			logger.error(e);
////		}
////		
////		logger.info(
////			LogUtil.wrapLog(
////				"noninstantPosting", 
////					"Success", 
////					null, 
////					mainIden, 
////					addition,
////					null, 
////					null, 
////					null));
////		mainIden = null;
////		addition = null;
//	}
//
//	/**
//	 * @return the acctDiaryPostingDao
//	 */
//	public AcctDiaryPostingDao getAcctDiaryPostingDao() {
//		return acctDiaryPostingDao;
//	}
//
//	/**
//	 * @param acctDiaryPostingDao the acctDiaryPostingDao to set
//	 */
//	public void setAcctDiaryPostingDao(AcctDiaryPostingDao acctDiaryPostingDao) {
//		this.acctDiaryPostingDao = acctDiaryPostingDao;
//	}
//
//	/**
//	 * @return the notifyService
//	 */
////	public NotifyService getNotifyService() {
////		return notifyService;
////	}
////
////	/**
////	 * @param notifyService the notifyService to set
////	 */
////	public void setNotifyService(NotifyService notifyService) {
////		this.notifyService = notifyService;
////	}
//
//	/**
//	 * @return the memberAcctSpecService
//	 */
//	public MemberAcctSpecService getMemberAcctSpecService() {
//		return memberAcctSpecService;
//	}
//
//	/**
//	 * @param memberAcctSpecService the memberAcctSpecService to set
//	 */
//	public void setMemberAcctSpecService(MemberAcctSpecService memberAcctSpecService) {
//		this.memberAcctSpecService = memberAcctSpecService;
//	}
//
//	/**
//	 * @return the acctEntryService
//	 */
//	public AccountEntryService getAcctEntryService() {
//		return acctEntryService;
//	}
//
//	/**
//	 * @param acctEntryService the acctEntryService to set
//	 */
//	public void setAcctEntryService(AccountEntryService acctEntryService) {
//		this.acctEntryService = acctEntryService;
//	}
//	
//    /**
//	 * 对分录按账号进行排序.
//	 * 
//	 */
//	private static void sortEntries(List<AccountEntryDTO> entries) {
//		Collections.sort(entries, new Comparator<AccountEntryDTO>() {
//			public int compare(AccountEntryDTO o1, AccountEntryDTO o2) {
//				return o1.getAcctcode().compareTo(o2.getAcctcode());
//			}
//		});
//	}
//}
