package com.pay.pe.service.accounting.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.Assert;

import com.pay.pe.dao.account.AccountDairyDao;
import com.pay.pe.dao.accounting.AccountDiaryEntryDAO;
import com.pay.pe.dao.accounting.AccountEntryDAO;
import com.pay.pe.dto.AccountEntryDTO;
import com.pay.pe.dto.AccountEntryDTOUtil;
import com.pay.pe.model.AccountEntry;
import com.pay.pe.service.accounting.AccountEntryService;
import com.pay.pe.service.payment.common.LogUtil;

/**
 * 分录 业务类。
 * 
 * 
 */
public class AccountEntryServiceImpl implements AccountEntryService {

	private Log logger = LogFactory.getLog(getClass());
	/**
	 * Account entry dao.
	 */
	private AccountEntryDAO accountEntryDAO;
	
	private AccountDiaryEntryDAO accountDiaryEntryDAO;
	
	private AccountDairyDao accountDairyDao ;

	/**
	 * @return Returns the accountEntryDAO.
	 */
	public AccountEntryDAO getAccountEntryDAO() {
		return accountEntryDAO;
	}

	/**
	 * @param accountEntryDAO
	 *            The accountEntryDAO to set.
	 */
	public void setAccountEntryDAO(AccountEntryDAO accountEntryDAO) {
		this.accountEntryDAO = accountEntryDAO;
	}

	


	public AccountDiaryEntryDAO getAccountDiaryEntryDAO() {
		return accountDiaryEntryDAO;
	}

	public void setAccountDiaryEntryDAO(AccountDiaryEntryDAO accountDiaryEntryDAO) {
		this.accountDiaryEntryDAO = accountDiaryEntryDAO;
	}

	public AccountDairyDao getAccountDairyDao() {
		return accountDairyDao;
	}

	public void setAccountDairyDao(AccountDairyDao accountDairyDao) {
		this.accountDairyDao = accountDairyDao;
	}

	/**
	 * 保存分录。
	 * 
	 * @param accountEntryDTO
	 */
	public void insertAccountEntry(AccountEntryDTO accountEntryDTO) {
		Map addition = new HashMap();
		addition.put("accountEntryDTO", accountEntryDTO.toString());
		logger.info(LogUtil.wrapLog("insertAccountEntry", "Start",
				accountEntryDTO.getDealId(), null, addition, null, null, null));
		AccountEntry accountEntry = AccountEntryDTOUtil
				.convertDTOtoAccountEntry(accountEntryDTO);

		Long voucherCode = accountEntry.getVoucherCode();
		if (voucherCode == null) {
			// generate vouchercode
			voucherCode = accountEntryDAO.generateVoucherCode();
			accountEntry.setVoucherCode(voucherCode);
		}

		Integer entryCode = accountEntry.getEntryCode();
		if (null == entryCode) {
			// generate entrycode
			entryCode = accountEntryDAO.generateEntryCode(voucherCode);
			accountEntry.setEntryCode(entryCode);
		}

		accountEntryDAO.insertAccountEntry(accountEntry);
		logger.info(LogUtil.wrapLog("insertAccountEntry", "Success",
				accountEntryDTO.getDealId(), null, addition, null, null, null));
		addition = null;
	}

	/**
	 * 根据凭证号和分录号查询分录。
	 * 
	 * @param voucherCode
	 *            凭证号
	 * @param entryCode
	 *            分录号
	 * @return 分录
	 */
	public AccountEntryDTO getAccountEntry(Long voucherCode, Integer entryCode) {
		Assert.notNull(voucherCode);
		Map addition = new HashMap();
		addition.put("entryCode", entryCode);
		logger.info(LogUtil.wrapLog("getAccountEntry", "Start", voucherCode
				.toString(), null, addition, null, null, null));
		AccountEntry accountEntry = accountEntryDAO.getAccountEntry(
				voucherCode, entryCode);
		AccountEntryDTO dto = AccountEntryDTOUtil
				.convertAccountEntryToDTO(accountEntry);
		logger.info(LogUtil.wrapLog("getAccountEntry", "Success", voucherCode
				.toString(), null, addition, null, null, null));
		addition = null;
		return dto;
	}

	/**
	 * 根据帐号查询所有分录。
	 * 
	 * @param accountCode
	 *            帐号
	 * @return 所有分录
	 */
	public List<AccountEntryDTO> getAccountEntryByAccountCode(Long accountCode) {
		logger.info(LogUtil.wrapLog("getAccountEntryByAccountCode", "Start",
				(accountCode == null ? null : accountCode.toString()), null,
				null, null, null, null));
		List<AccountEntry> accountEntries = accountEntryDAO
				.getAccountEntryByAccountCode(accountCode);
		List<AccountEntryDTO> list = AccountEntryDTOUtil
				.convertAccountEntryToDTO(accountEntries);
		logger.info(LogUtil.wrapLog("getAccountEntryByAccountCode", "Success",
				(accountCode == null ? null : accountCode.toString()), null,
				null, null, null, null));
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see.domain.service.accouting.AccountEntryService#
	 * getAccountEntryByDealId(java.lang.String)
	 */
	public List<AccountEntryDTO> getAccountEntryByDealId(final String dealId) {
		logger.info(LogUtil.wrapLog("getAccountEntryByDealId", "Start", dealId,
				null, null, null, null, null));
		List<AccountEntry> entries = getAccountEntryDAO()
				.getAccountEntryByDealId(dealId);
		List<AccountEntryDTO> list = AccountEntryDTOUtil
				.convertAccountEntryToDTO(entries);
		logger.info(LogUtil.wrapLog("getAccountEntryByDealId", "Success",
				dealId, null, null, null, null, null));
		return list;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see.domain.service.accouting.AccountEntryService#
	 * getAccountEntryByVoucherCode(java.lang.String)
	 */
	public List<AccountEntryDTO> getAccountEntryByVoucherCode(
			final String voucherCode) {
		logger.info(LogUtil.wrapLog("getAccountEntryByVoucherCode", "Start",
				voucherCode, null, null, null, null, null));
		List<AccountEntry> entries = getAccountEntryDAO()
				.getAccountEntryByVoucherCode(voucherCode);
		List<AccountEntryDTO> list = AccountEntryDTOUtil
				.convertAccountEntryToDTO(entries);
		logger.info(LogUtil.wrapLog("getAccountEntryByVoucherCode", "Success",
				voucherCode, null, null, null, null, null));
		return list;
	}

	/**
	 * 保存分录. <li>如果分录的凭证号为空则成一个凭证号赋给该分录 <li>如果分录的分录号为空则成一个分录号赋给该分录
	 * 
	 * @param accountEntryDTO
	 *            需要保存的分录
	 */
	public long insertAccountEntrys(List<AccountEntryDTO> accountEntryDTOs,Long voucherCode) {
		// INSERT INTO ACCT_DIARY_ENTRY too
		List<AccountEntry> entrys = AccountEntryDTOUtil.convertDTOtoAccountEntry(accountEntryDTOs);
		
		//记分录
		accountEntryDAO.insertAccountEntries(entrys,voucherCode);
//		logger.info("insertAccountEntrys voucherCode="+voucherCode);
		//记日记账分录
		accountDiaryEntryDAO.insertAcctDiaryEntries(voucherCode, entrys);
//		logger.info("insertAcctDiaryEntries voucherCode="+voucherCode);

		//更新日记账		
		accountDairyDao.updateAcctDiary(entrys);
		return voucherCode ;
	}

	@Override
	public List<AccountEntryDTO> getAccountEntryByOrderId(String orderId) {
		List<AccountEntry> entries =  accountEntryDAO.getAccountEntryByOrderId(orderId);
		List<AccountEntryDTO> list = AccountEntryDTOUtil
		.convertAccountEntryToDTO(entries);
		return list;
	}
}
