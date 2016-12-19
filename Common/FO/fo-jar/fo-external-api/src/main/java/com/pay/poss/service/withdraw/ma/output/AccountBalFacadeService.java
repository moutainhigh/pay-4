/**
 * 
 */
package com.pay.poss.service.withdraw.ma.output;

import com.pay.acc.service.account.dto.CalFeeReponseDto;

/**
 * 更新余额服务
 * @author zliner
 *
 */
public interface AccountBalFacadeService {
	/**
	 * 更新余额服务
	 * @param dto                 更新余额参数
	 * @param  payType            支付业务类型  对应到MA的枚举类
	 * @exception                 更新余额时候出现异常
	 * @return int                1为成功，0为失败
	 */
	int doUpdateAcctBalanceRntx(CalFeeReponseDto updateBalanceRequestDto,Integer payType) throws Exception;
}
