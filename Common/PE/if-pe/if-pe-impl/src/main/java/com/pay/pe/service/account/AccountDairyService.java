
package com.pay.pe.service.account;

import java.util.List;

import com.pay.pe.dto.AccountDairyDTO;
import com.pay.pe.dto.AccountEntryDTO;
import com.pay.util.MfDate;


/**
 * 
 * 日记帐service.
 */
public interface AccountDairyService {
    /**
     * 根据acctcode和日期得到信息.
     * @param acctcode String
     * @param date MfDate
     * @return AccountDairyDTO
     */
    AccountDairyDTO getByAcctcode(String acctcode , MfDate date);
    /**
     * 根据acctcode和日期得到当前贷方的交易金额.
     * 如果没有对应的记录返回0.
     * @param acctcode String
     * @param date MfDate
     * @return long
     */
    long getDebitBal(String acctcode , MfDate date);
    /**
     * 根据MbrAcctCode和日期得到当前贷方的交易金额.
     * 如果没有对应的记录返回0.
     * @param acctcode String
     * @param date MfDate
     * @return long
     */
    long getDebitBalByMbrAcctCode(String MbrAcctCode , MfDate date);
    /**
     * 根据acctcode和日期得到当前借方的交易金额.
     * 如果没有对应的记录返回0.
     * @param acctcode String
     * @param date MfDate
     * @return long
     */
    long getCreditBal(String acctcode , MfDate date);
    /**
     * 根据MbrAcctCode和日期得到当前借方的交易金额.
     * 如果没有对应的记录返回0.
     * @param acctcode String
     * @param date MfDate
     * @return long
     */
    long getCreditBalByMbrAcctCode(String MbrAcctCode , MfDate date);
    
    /**
     * 对已经过账的分录记日记账
     * @param voucherCode long
     * @param acctEntries List<AccountEntryDTO>
     * @return void
     */
    void posting(long voucherCode, List <AccountEntryDTO> acctEntries);
    
    /**
     * 对已经过账的分录记日记账
     * 没有构造AcctEntryDTO，则直接传入分录号
     * @param voucherCode long
     * @param acctEntries int[]
     * @return void
     */
    void posting(long voucherCode, int[] acctEntries);

    /**
     * 对已经过账的1个凭证号下的所有分录记日记账
     * @param voucherCode long
     * @return void
     */
    void posting(long voucherCode);
}
