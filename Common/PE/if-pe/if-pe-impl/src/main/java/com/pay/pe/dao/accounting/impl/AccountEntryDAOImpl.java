package com.pay.pe.dao.accounting.impl;

import java.util.Date;
import java.util.List;

import org.springframework.util.Assert;

import com.pay.inf.dao.impl.IbatisDAOSupport;
import com.pay.pe.dao.accounting.AccountEntryDAO;
import com.pay.pe.model.AccountEntry;
import com.pay.util.MfDate;

/**
 * 分录的DAO实现.
 * 

 */
public final class AccountEntryDAOImpl extends IbatisDAOSupport implements
		AccountEntryDAO {

	/**
	 * 保存分录. 如果传入凭证号为空，则生成凭证号。如果凭证号不为空，则按凭证号取最大的分录号，然后递增分录号。 凭证号格式：YYYYMMDD +
	 * SEQ_VOUCHER_ID(11) 分录号：sequence，按凭证号递增。
	 * 
	 * @param accountEntry
	 * @return
	 */
	@SuppressWarnings("unchecked")
	public Long insertAccountEntry(AccountEntry accountEntry) {
		Assert.notNull(accountEntry, "accountEntry should not be null!");
		Long id = (Long) super.saveObject(accountEntry);
		return id;
	}

	/**
	 * 保存一批分录。
	 * 
	 * @param accountEntries
	 * @return voucherCode
	 */
	public void insertAccountEntries(List<AccountEntry> accountEntries,Long voucherCode) {
		int i = 0;
		//生成凭证号
		Date postDate = new Date();
		for (java.util.Iterator<AccountEntry> iterator = accountEntries
				.iterator(); iterator.hasNext();) {
			AccountEntry entry = iterator.next();
			i++;
			entry.setVoucherCode(voucherCode);
			Integer entryCode = entry.getEntryCode();
			if (null == entryCode) {
				entryCode =   i;
				entry.setEntryCode(entryCode);
			}
			entry.setStatus(1);
			entry.setPostDate(postDate);
			insertAccountEntry(entry);
		}
	}

	/**
	 * 根据凭证号和分录号查询分录.
	 * 
	 * @param voucherCode
	 *            凭证号
	 * @param entryCode
	 *            分录号
	 * @return 分录
	 */
	public AccountEntry getAccountEntry(Long voucherCode, Integer entryCode) {
		AccountEntry accountEntry = new AccountEntry();
		accountEntry.setVoucherCode(voucherCode);
		accountEntry.setEntryCode(entryCode);
		return (AccountEntry) super.findObjectBySelective(accountEntry);

		// ExpressionType exp1 = ExpressionTypeFactory.equal("vouchercode",
		// voucherCode);
		// ExpressionType exp2 = ExpressionTypeFactory.equal("entrycode",
		// entryCode);
		//        
		// return (AccountEntry) findObjectByExpTypes(AccountEntry.class, new
		// ExpressionType[] {exp1, exp2});
	}

	/**
	 * 根据帐号查询所有分录。
	 * 
	 * @param accountCode
	 *            帐号
	 * @return 所有分录
	 */
	@SuppressWarnings("unchecked")
	public List<AccountEntry> getAccountEntryByAccountCode(Long accountCode) {
		AccountEntry accountEntry = new AccountEntry();
		accountEntry.setAcctCode("" + accountCode);
		return super.findBySelective(accountCode);
	}

	/**
	 * 生成凭证号。 凭证号格式：YYYYMMDD + SEQ_VOUCHER_ID(11)
	 * 
	 * @return
	 */
	public Long generateVoucherCode() {
		MfDate date = new MfDate();
		String month = date.getMonth() > 9 ? date.getMonth().toString() : "0"
				+ date.getMonth().toString();
		String voucherCodePrefix = date.getYear().toString() + month
				+ date.getDay().toString();

		String voucherCodeSuffix = getNextID("SEQ_VOUCHER_ID").toString();
		if (voucherCodeSuffix.length() < 12) {
			int len = voucherCodeSuffix.length();
			for (int i = 0; i < 11 - len; i++) {
				voucherCodeSuffix = "0" + voucherCodeSuffix;
			}
		}
		Long voucherCode = Long.valueOf(voucherCodePrefix + voucherCodeSuffix);
		return voucherCode;
	}

	/**
	 * 根据凭证号得到最大的分录号。如果没有，分录号从1开始；如果有，则递增.
	 * 
	 * @param voucherCode
	 *            Long
	 */
	public Integer generateEntryCode(final Long voucherCode) {
		// Integer entryCode = 1;
		// Number lastEntryCode = null;
		// ExpressionType type1 = ExpressionTypeFactory.equal("vouchercode",
		// voucherCode);
		// ExpressionType type2 = ExpressionTypeFactory.max("entrycode");
		// List result = reportObjectsByExpTypes(AccountEntry.class, new
		// ExpressionType[]{type1, type2});
		//        
		// Iterator it = result.iterator();
		// if (it.hasNext()) {
		// ReportQueryResult reportResult = (ReportQueryResult) it.next();
		// lastEntryCode = (Number) reportResult.getByIndex(0);
		// }
		//        
		// if (lastEntryCode != null) {
		// entryCode = lastEntryCode.intValue() + 1;
		// }
		// return entryCode;
		return null;
	}

	public List<AccountEntry> getAccountEntryByDealId(final String dealId) {
		AccountEntry accountEntry = new AccountEntry();
		accountEntry.setDealId(dealId);
		return super.findBySelective(accountEntry);
	}

	public List<AccountEntry> getAccountEntryByVoucherCode(
			final String voucherCode) {
		AccountEntry accountEntry = new AccountEntry();
		accountEntry.setVoucherCode(Long.parseLong(voucherCode));
		return super.findBySelective(accountEntry);
	}

	
	@Override
	public List<AccountEntry> getAccountEntryByOrderId(String  orderId) {
		return super.findByTemplate("findByOrderId", orderId);
	}
}
