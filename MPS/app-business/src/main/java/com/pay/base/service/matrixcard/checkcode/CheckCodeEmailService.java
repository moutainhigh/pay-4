package com.pay.base.service.matrixcard.checkcode;

import com.pay.base.exception.matrixcard.MatrixCardException;

public interface CheckCodeEmailService {
	
	/**根据checkCode来查询使用状态，以及更新状态
	 * @param checkCode
	 * @return
	 * @throws MatrixCardException
	 */
	boolean checkEmail(String checkCode)throws MatrixCardException;
	
}
