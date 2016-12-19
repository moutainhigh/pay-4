package com.pay.base.service.matrixcard.vfy;

import com.pay.base.common.helper.matrixcard.MatrixCardToken;
import com.pay.base.common.helper.matrixcard.MatrixCardVfyRequest;
import com.pay.base.common.helper.matrixcard.OperatorInfo;
import com.pay.base.dto.matrixcard.MatrixCardVfyDto;
import com.pay.base.exception.matrixcard.MatrixCardException;
import com.pay.base.model.matrixcard.MatrixCardVfy;

/**
 * @author jim_chen
 * @version 2010-9-16
 */
public interface IMatrixCardVfyService {

	/**
	 * 保存 口令卡验证表
	 * 
	 * @param dto
	 * @return
	 */
	public MatrixCardVfy save(MatrixCardVfy dto);

	/**
	 * 统计口令卡验证表的纪录
	 * 
	 * @return
	 */
	int countMatrixCardVfy();

	/**
	 * 根据ID查询口令卡验证表
	 * 
	 * @param id
	 * @return
	 */
	public MatrixCardVfyDto selectMatrixCardVfyById(Long id);

	/**
	 * 更新口令卡验证表
	 * 
	 * @param dto
	 * @return
	 */
	public void updateMatrixCardVfy(MatrixCardVfyDto dto);

	/**
	 * 保存验证请求信息，并根据验证请求信息生成新的矩阵卡坐标及其token：
	 * 
	 * @param mcVfyRequest
	 * @return
	 * @throws MatrixCardException
	 */
	MatrixCardToken requestToken(MatrixCardVfyRequest mcVfyRequest) throws MatrixCardException;

	/**
	 * 验证矩阵卡token的正确性
	 * @param serialNo
	 * @param matrixCardToken
	 * @param operatorInfo
	 * @return
	 * @throws MatrixCardException
	 */
	boolean verify(String serialNo, MatrixCardToken matrixCardToken, OperatorInfo operatorInfo) throws MatrixCardException;
	
	
	/**
	 * 生成坐标token
	 * @param mcVfyRequest
	 * @return
	 * @throws MatrixCardException
	 */
	MatrixCardToken generateToken(MatrixCardVfyRequest mcVfyRequest) throws MatrixCardException;

}
