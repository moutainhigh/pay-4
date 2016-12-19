package com.pay.pe.service;

import java.util.List;

import com.pay.pe.dto.AccountEntryDTO;

public interface PEService {
	
	/**
	 * 进行计价 响应 带有 calFeeDetails 相当于分录对象（包含 acctCode amount crdr)
	 * 
	 * @param dealRequest
	 *            请求对象
	 * @return calFeeReponse
	 * @throws
	 */
	PaymentResponseDto processPayment(PaymentReqDto paymentReqDto);

	/**
	 * 处理手工凭证的交易
	 * 
	 * @param dealRequest
	 *            请求对象
	 * @return calFeeReponse
	 * @throws
	 */
	PaymentResponseDto processVoucherCode(PaymentResponseDto paymentResponseDto);

	/**
	 * 进行计价 只是单单计算费用
	 * 
	 * @param dealRequest
	 *            请求对象
	 * @return calFeeReponse 响应
	 * @throws
	 */
	PaymentResponseDto caculateFee(PaymentReqDto paymentReqDto);

	/**
	 * 
	 * @param deal
	 *            记帐
	 * @param calFeeReponse请求对象
	 * @return boolean 处理是否成功
	 * @throws Exception
	 */
	boolean accounting(PaymentResponseDto paymentResponse) throws Exception;

	/**
	 * 按订单id得到记账产生的所有分录.
	 * 
	 * @param orderId
	 *            订单id
	 */
	List<AccountEntryDTO> getAccountEntryByOrderId(String orderId);

	/**
	 * 得到ma更新余额方向
	 * 
	 * @param acctCode
	 *            请求对象
	 * @param crdr
	 *            借贷方向
	 * @return int + - 方向， 1 + 2 -
	 * @throws
	 */
	int getMaBalanceBy(String acctCode, Integer crdr);

	/**
	 * 
	 * @param deal
	 *            记帐
	 * @return COSResponse 处理结果.包括处理状态、交易号, 分录
	 * @throws Exception
	 */
	// public COSResponse processOrder(DealDto deal)throws Exception ;

}
