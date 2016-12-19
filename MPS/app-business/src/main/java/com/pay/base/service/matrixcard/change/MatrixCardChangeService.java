package com.pay.base.service.matrixcard.change;

import com.pay.base.common.helper.matrixcard.MatrixCardVfyRequest;
import com.pay.base.common.helper.matrixcard.OperatorInfo;
import com.pay.base.dto.ResultDto;
import com.pay.base.dto.matrixcard.MatrixCardVfyDto;
import com.pay.base.exception.matrixcard.MatrixCardException;

public interface MatrixCardChangeService {

	/**
	 * 启动换卡事务
	 * 
	 * @param operatorInfo
	 * @return obj:MatrixCardTransInfoDto
	 * @throws MatrixCardException
	 */
	public ResultDto startChangeTrans(OperatorInfo operatorInfo);

	/**
	 * 申请旧卡验证码
	 * 
	 * @param mcVfyRequest
	 * @param operatorInfo
	 * @return MatrixCardToken
	 * @throws MatrixCardException
	 */
	public  ResultDto requestOldToken(MatrixCardVfyRequest mcVfyRequest, OperatorInfo operatorInfo);

	/**
	 * 申请新卡验证码
	 * 
	 * @param mcVfyRequest
	 * @param operatorInfo
	 * @return MatrixCardToken
	 * @throws MatrixCardException
	 */
	public  ResultDto requestNewToken(MatrixCardVfyRequest mcVfyRequest, OperatorInfo operatorInfo);

	
	/** 换卡处理
	 * @param u_id
	 * @param oldMatrixCardVfyDto
	 * @param newMatrixCardVfyDto
	 * @return  boolean
	 */
	public ResultDto change(Long u_id, MatrixCardVfyDto oldMatrixCardVfyDto, MatrixCardVfyDto newMatrixCardVfyDto);
}
