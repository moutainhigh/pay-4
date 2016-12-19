/**
 * 
 */
package com.pay.acc.service.account;

import com.pay.acc.service.account.dto.CalFeeReponseDto;
import com.pay.acc.service.account.exception.MaAcctBalanceException;

/**
 * @author Administrator
 *
 */
public interface AccountBalanceHandlerService {

	/**
	 * 更新成功
	 */
	int SUCCESS = 1;

	/**
	 * 更新余额，DTO是由计费后传入的参数
	 * 
	 * @param calFeeReponseDto
	 *            记账后的 DTO
	 * @return 1 成功其他失败
	 * @throws MaAcctBalanceException
	 */
	public int doUpdateAcctBalanceRntx(
			CalFeeReponseDto updateBalanceRequestDto, Integer payType)
			throws MaAcctBalanceException;
	
	/**
	 * 更新余额，DTO是由计费后传入的参数
	 * 
	 * @param calFeeReponseDto
	 *            记账后的 DTO
	 * @return 1 成功其他失败
	 * @throws MaAcctBalanceException
	 */
	public int doUpdateAcctBalanceRntx_(
			CalFeeReponseDto updateBalanceRequestDto, Integer payType)
			throws MaAcctBalanceException;
	
	/**
	 * 更新账户余额
	 * 
	 * @param calFeeReponseDto
	 * @param payType
	 * @return
	 * @throws MaAcctBalanceException
	 */
	public int doUpdateBalanceRntx(CalFeeReponseDto calFeeReponseDto,
			Integer payType) throws MaAcctBalanceException;

	/**
	 * 更新余额，没有事务控制
	 * 
	 * @param updateBalanceRequestDto
	 * @param payType
	 * @return
	 * @throws MaAcctBalanceException
	 */
	public Long updateAccBalance(CalFeeReponseDto updateBalanceRequestDto,
			Integer payType) throws MaAcctBalanceException;
	
	/**
	 * 更新余额，没有事务控制
	 * 
	 * @param updateBalanceRequestDto
	 * @param payType
	 * @return
	 * @throws MaAcctBalanceException
	 */
	public Long updateAccBalance_(CalFeeReponseDto updateBalanceRequestDto,
			Integer payType) throws MaAcctBalanceException;
	

	/**
	 * 调用PE接口，通知记账
	 * 
	 * @param updateBalanceRequestDto
	 * @return
	 */
	public int sendMsgToPe(CalFeeReponseDto updateBalanceRequestDto, Long seqId);

}
