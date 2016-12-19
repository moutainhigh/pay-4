package com.pay.pe.service;



public interface CalFeeService {
	
	/**
	 * 进行计价 响应  带有 calFeeDetails 相当于分录对象（包含  acctCode  amount  crdr) 
	 * @param dealRequest 请求对象 
	 * @return calFeeReponse 
	 * @throws 
	 */
	CalFeeReponse calculateFeeDetail(CalFeeRequest dealRequest) ;
	
	/**
	 * 进行计价 只是单单计算费用 
	 * @param dealRequest 请求对象 
	 * @return calFeeReponse 响应
	 * @throws 
	 */
	CalFeeReponse caculateFee(CalFeeRequest dealRequest) ;
	
	
	/**
	 * 得到ma更新余额方向
	 * @param acctCode 请求对象 
	 * @param crdr  借贷方向
	 * @return int   + - 方向， 1 +  2 - 	
	 * @throws 
	 */
	int getMaBalanceBy(String acctCode , Integer crdr) ;
	
	
	
}
