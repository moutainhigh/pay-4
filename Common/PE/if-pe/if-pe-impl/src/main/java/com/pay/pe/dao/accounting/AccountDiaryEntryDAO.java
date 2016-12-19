package com.pay.pe.dao.accounting;

import java.util.List;

import com.pay.pe.model.AccountEntry;


public interface AccountDiaryEntryDAO {
    
    /**
     * 保存分录。
     * 如果传入凭证号为空，则生成凭证号。如果凭证号不为空，则按凭证号取最大的分录号，然后递增分录号。
     * 凭证号格式：YYYYMMDD + SEQ_VOUCHER_ID(11)
     * 分录号：sequence，按凭证号递增。
     * @param accountEntry
     * @return
     */
//    Object insertAcctDiaryEntry(AccountEntry accountEntry);
    
    
    /**
     * 保存一批分录。
     * @param accountEntries
     */
    boolean insertAcctDiaryEntries(Long voucherCode ,List<AccountEntry> accountEntries);
    
    
}
