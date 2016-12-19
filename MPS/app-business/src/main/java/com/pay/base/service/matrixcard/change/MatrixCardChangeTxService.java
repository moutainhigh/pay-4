package com.pay.base.service.matrixcard.change;

import com.pay.base.common.helper.matrixcard.MatrixCardToken;
import com.pay.base.common.helper.matrixcard.MatrixCardVfyRequest;
import com.pay.base.common.helper.matrixcard.OperatorInfo;
import com.pay.base.dto.matrixcard.MatrixCardVfyDto;
import com.pay.base.dto.matrixcard.transmgr.MatrixCardTransInfoDto;
import com.pay.base.exception.matrixcard.MatrixCardException;

public interface MatrixCardChangeTxService {

	/**
	 * 启动换卡事务
	 * 
	 * @param operatorInfo
	 * @return
	 * @throws MatrixCardException
	 */
	public MatrixCardTransInfoDto startChangeTransRdTx(OperatorInfo operatorInfo) throws MatrixCardException;

	/**
	 * 申请旧卡验证码，如果未启动换卡事务则抛异常
	 * 
	 * @param mcVfyRequest
	 * @param operatorInfo
	 * @return
	 * @throws MatrixCardException
	 */
	public MatrixCardToken requestOldTokenRdTx(MatrixCardVfyRequest mcVfyRequest, OperatorInfo operatorInfo) throws MatrixCardException;

	/**
	 * 申请新卡验证码，如果未启动换卡事务则抛异常
	 * 
	 * @param mcVfyRequest
	 * @param operatorInfo
	 * @return
	 * @throws MatrixCardException
	 */
	public MatrixCardToken requestNewTokenRdTx(MatrixCardVfyRequest mcVfyRequest, OperatorInfo operatorInfo) throws MatrixCardException;

	/**
	 * 换卡处理，如果未启动卡事务则抛异常
	 * 
	 * @param u_id
	 * @param serialNoOld
	 * @param serialNoNew
	 * @throws MatrixCardException
	 */
	public void changeRdTx(Long u_id, MatrixCardVfyDto oldMatrixCardVfyDto, MatrixCardVfyDto newMatrixCardVfyDto) throws MatrixCardException;

}
