
package com.pay.pe.dao.account.impl;

import java.util.Date;
import java.util.List;

import com.pay.inf.dao.impl.IbatisDAOSupport;
import com.pay.pe.dao.account.AccountDairyDao;
import com.pay.pe.model.AccountDairy;
import com.pay.pe.model.AccountEntry;




public class AccountDairyDaoImpl extends IbatisDAOSupport<AccountDairy> implements
    AccountDairyDao {
    
    public AccountDairy getDairyByAccount(String acctcode , Date mdate) {
    	 assert null != acctcode;
         assert null != mdate;
    	AccountDairy accountDairy = new AccountDairy();
    	accountDairy.setAcctCode(acctcode);
//    	accountDairy.setAcctPeriodDate(new java.sql.Timestamp(mdate.getTime().getTime()));
    	accountDairy.setAcctPeriodDate(new java.sql.Timestamp(mdate.getTime()));
    	return super.findObjectBySelective(accountDairy);
    }

//	@Override
//	public AccountDairy insertDairyByAccount(AccountDairy accountDairy) {
//		return null;
//	}
    /*
     * 更新记账,最新的计算逻辑
     * 1.进来先更新日记账的数据
     * 2.如果没更新到,在插入一条,当插入的时候发生并发,直接抛出异常,不在继续处理,然后有MA的补偿机制来完成
     */
	@Override
	public boolean updateAcctDiary(List<AccountEntry> accountEntries) {
		for (java.util.Iterator<AccountEntry> iterator = accountEntries
				.iterator(); iterator.hasNext();) {
			AccountEntry entry = iterator.next();
			
			AccountDairy accountDairy = new AccountDairy();
			
			accountDairy.setCreditBalance(entry.getCrdr()==2?entry.getValue():0);
			accountDairy.setDebitBalance(entry.getCrdr()==1?entry.getValue():0);
			accountDairy.setBalance(entry.getMaBlanceBy()==1?entry.getValue():-entry.getValue());
			accountDairy.setAcctCode(entry.getAcctCode());
			accountDairy.setAcctPeriodDate(new java.sql.Timestamp(entry.getCreationDate().getTime()));
			Integer UpdateCnt = super.updateObjectBySqlName("acctDiaryUpdate", accountDairy);
			//没有更新到数据,如果插入的时候发生了并发
			if(UpdateCnt == null ||UpdateCnt.intValue()<1)
			{
				accountDairy = new AccountDairy(); 
				accountDairy.setClosingCrBal(0L);
				accountDairy.setClosingDrBal(0L);
				accountDairy.setClosingBal(0L);
				
				accountDairy.setCreditBalance((entry.getCrdr()==2?entry.getValue():0));
				accountDairy.setDebitBalance((entry.getCrdr()==1?entry.getValue():0));
				accountDairy.setBalance((entry.getMaBlanceBy()==1?entry.getValue():-entry.getValue()));
				
				accountDairy.setOpeningCrBal(0L);
				accountDairy.setOpeningDrBal(0L);
				accountDairy.setOpeningBal(0L);
				
				accountDairy.setClosingTime(null);
				accountDairy.setClosed(0);
//				accountDairy.setAcctPeriodDate(new java.sql.Timestamp(entry.getCreationDate().getTime()));
				accountDairy.setAcctPeriodDate(entry.getCreationDate());
				accountDairy.setAcctCode(entry.getAcctCode());
				super.saveObject(accountDairy);
			}
		}
		return true;
	}
//	@Override
//	public boolean updateAcctDiary(List<AccountEntry> accountEntries) {
//		for (java.util.Iterator<AccountEntry> iterator = accountEntries
//				.iterator(); iterator.hasNext();) {
//			AccountEntry entry = iterator.next();
//			//这里是不是用 entry.getCreationDate()拿到 日记账余额对象
//			AccountDairy accountDairy = this.getDairyByAccount(entry.getAcctCode(), entry.getCreationDate());
//			if(accountDairy==null){
//				accountDairy = new AccountDairy(); 
//				accountDairy.setClosingCrBal(0L);
//				accountDairy.setClosingDrBal(0L);
//				accountDairy.setClosingBal(0L);
//				
//				accountDairy.setCreditBalance((entry.getCrdr()==2?entry.getValue():0));
//				accountDairy.setDebitBalance((entry.getCrdr()==1?entry.getValue():0));
//				accountDairy.setBalance((entry.getMaBlanceBy()==1?entry.getValue():-entry.getValue()));
//				
//				accountDairy.setOpeningCrBal(0L);
//				accountDairy.setOpeningDrBal(0L);
//				accountDairy.setOpeningBal(0L);
//				
//				accountDairy.setClosingTime(null);
//				accountDairy.setClosed(0);
////				accountDairy.setAcctPeriodDate(new java.sql.Timestamp(entry.getCreationDate().getTime()));
//				accountDairy.setAcctPeriodDate(entry.getCreationDate());
//				accountDairy.setAcctCode(entry.getAcctCode());
//				super.saveObject(accountDairy);
//			}else{
//				accountDairy.setCreditBalance(accountDairy.getCreditBalance()+(entry.getCrdr()==2?entry.getValue():0));
//				accountDairy.setDebitBalance(accountDairy.getDebitBalance()+(entry.getCrdr()==1?entry.getValue():0));
//				accountDairy.setBalance(accountDairy.getBalance()+(entry.getMaBlanceBy()==1?entry.getValue():-entry.getValue()));
//				super.updateObject(accountDairy);
//			}
//		}
//		return true;
//	}

}
