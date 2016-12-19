package com.pay.pe.dao.account;


import java.util.Date;
import java.util.List;

import com.pay.pe.model.AccountDairy;
import com.pay.pe.model.AccountEntry;



public interface AccountDairyDao {
    /**
     * 根据account和日期，得到mdate的日记帐数据.
     * 如果没有返回null.
     * @param account Account
     * @param mdate MfDate
     * @return AccountDairy
     */
    AccountDairy getDairyByAccount(String acctcode, Date mdate);
    
//    AccountDairy insertDairyByAccount(AccountDairy accountDairy);
    

    
    /**
     * 保存一批分录。
     * @param accountEntries
     */
    boolean updateAcctDiary(List<AccountEntry> accountEntries);
    
    
    
}
