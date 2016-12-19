package com.pay.pe.dao.accounting.impl;

import java.util.List;

import com.pay.inf.dao.impl.IbatisDAOSupport;
import com.pay.pe.dao.accounting.AccountDiaryEntryDAO;
import com.pay.pe.model.AccountEntry;

@SuppressWarnings("unchecked")
public class AccountDiaryEntryDAOImpl extends IbatisDAOSupport implements AccountDiaryEntryDAO{

	@Override
	public boolean insertAcctDiaryEntries(Long voucherCode,List<AccountEntry> accountEntries) {
		int i = 0;	
		for (java.util.Iterator<AccountEntry> iterator = accountEntries.iterator(); iterator.hasNext();) {
			//其实这边的数据，改设置的值在insertAccountEntries 已经设置 是同一个list
			AccountEntry entry = iterator.next();
			i++;
			entry.setVoucherCode(voucherCode);
			Integer entryCode = entry.getEntryCode();
			if (null == entryCode) {
				entryCode =   i;
				entry.setEntryCode(entryCode);
			}
			super.saveObject(entry);
		}		
		return true;
	}
}
