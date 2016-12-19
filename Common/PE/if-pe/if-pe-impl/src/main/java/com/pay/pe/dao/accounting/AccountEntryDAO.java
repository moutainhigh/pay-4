package com.pay.pe.dao.accounting;

import java.util.List;

import com.pay.pe.model.AccountEntry;


public interface AccountEntryDAO {
    
    /**
     * 保存分录。
     * 如果传入凭证号为空，则生成凭证号。如果凭证号不为空，则按凭证号取最大的分录号，然后递增分录号。
     * 凭证号格式：YYYYMMDD + SEQ_VOUCHER_ID(11)
     * 分录号：sequence，按凭证号递增。
     * @param accountEntry
     * @return
     */
	Long insertAccountEntry(AccountEntry accountEntry);
    
    
    /**
     * 保存一批分录。
     * @param accountEntries
     */
    void insertAccountEntries(List<AccountEntry> accountEntries,Long voucherNo);
    
    
    /**
     * 生成凭证号。
     * 凭证号格式：YYYYMMDD + SEQ_VOUCHER_ID(11)
     * @return
     */
    Long generateVoucherCode();
    
    Integer generateEntryCode(Long voucherCode);
    
    /**
     * 根据凭证号和分录号查询分录。
     * @param voucherCode 凭证号
     * @param entryCode 分录号
     * @return 分录
     */
    AccountEntry getAccountEntry(Long voucherCode, Integer entryCode);
    
    
    /**
     * 根据帐号查询所有分录。
     * @param accountCode 帐号
     * @return 所有分录
     */
    List <AccountEntry> getAccountEntryByAccountCode(Long accountCode);
    
    /**
     * 按交易号得到对该交易记账产生的所有分录.
     * @param dealId String
     * @return List <AccountEntry>
     */
    List <AccountEntry> getAccountEntryByDealId(String dealId);
    
    /**
     * 按凭证号得到对该交易记账产生的所有分录.
     * @param dealId voucherCode
     * @return List <AccountEntry>
     */
    List <AccountEntry> getAccountEntryByVoucherCode(String voucherCode);
    
    /**
	 * 按订单id得到记账产生的所有分录.
	 * @param orderId 订单id
	 */
    List <AccountEntry> getAccountEntryByOrderId(String orderId);
}
