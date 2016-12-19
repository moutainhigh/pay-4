package com.pay.pe.service.accounting;

import java.util.List;

import com.pay.pe.dto.AccountEntryDTO;

/**
 * 分录 业务类。
 */
public interface AccountEntryService {

	/**
	 * 保存分录.
	 * <li>如果分录的凭证号为空则成一个凭证号赋给该分录
	 * <li>如果分录的分录号为空则成一个分录号赋给该分录
	 * @param accountEntryDTO 需要保存的分录
	 */
	void insertAccountEntry(AccountEntryDTO accountEntryDTO);
	
	
	/**
	 * 保存分录.
	 * <li>如果分录的凭证号为空则成一个凭证号赋给该分录
	 * <li>如果分录的分录号为空则成一个分录号赋给该分录
	 * @param accountEntryDTO 需要保存的分录
	 */
	long insertAccountEntrys(List<AccountEntryDTO> accountEntryDTOs,Long voucherCode);

	/**
	 * 根据凭证号和分录号查询分录.
	 * @param voucherCode 凭证号
	 * @param entryCode 分录号
	 * @return AccountEntryDTO 查询到的分录
	 */
	AccountEntryDTO getAccountEntry(Long voucherCode, Integer entryCode);

	/**
	 * 根据帐号查询所有分录。
	 * @param accountCode 帐号
	 * @return 分录LIST
	 */
	List <AccountEntryDTO> getAccountEntryByAccountCode(Long accountCode);
	
	/**
	 * 按交易号得到对该交易记账产生的所有分录.
	 * @param dealId 交易号
	 */
	List <AccountEntryDTO> getAccountEntryByDealId(String dealId);
	
	/**
	 * 按凭证号得到对该交易记账产生的所有分录.
	 * @param voucherCode 凭证号
	 */
	List <AccountEntryDTO> getAccountEntryByVoucherCode(String voucherCode);
	
	/**
	 * 按订单id得到记账产生的所有分录.
	 * @param orderId 订单id
	 */
	List <AccountEntryDTO> getAccountEntryByOrderId(String orderId);
}
