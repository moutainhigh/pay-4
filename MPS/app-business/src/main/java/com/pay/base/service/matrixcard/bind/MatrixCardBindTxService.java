package com.pay.base.service.matrixcard.bind;

import com.pay.base.common.helper.matrixcard.MatrixCardToken;
import com.pay.base.common.helper.matrixcard.MatrixCardVfyRequest;
import com.pay.base.common.helper.matrixcard.OperatorInfo;
import com.pay.base.dto.matrixcard.MatrixCardVfyDto;
import com.pay.base.exception.matrixcard.MatrixCardException;

/**
 * @author jim_chen
 * @version 
 * 2010-9-17 
 */
public interface MatrixCardBindTxService {
	
	/**
	 * @param mcVfyRequest
	 * @param operatorInfo
	 * @return
	 */
	MatrixCardToken processBindRnTx(MatrixCardVfyRequest mcVfyRequest, OperatorInfo operatorInfo)throws MatrixCardException;
	
	/**
	 * 用户登陆生成TOKEN
	 * @param mcVfyRequest
	 * @param operatorInfo
	 * @return
	 * @throws MatrixCardException
	 */
	public MatrixCardToken processLoginRnTx(MatrixCardVfyRequest mcVfyRequest, OperatorInfo operatorInfo) throws MatrixCardException ;
	
	/**
	 * @param u_id
	 * @param matrixCardVfyDto
	 * @throws MatrixCardException
	 */
	void bindRdTx(MatrixCardVfyDto matrixCardVfyDto)throws MatrixCardException;
	
	/**
	 * @param u_id
	 * @param serialNo
	 * @param operatorInfo
	 * @throws MatrixCardException
	 */
	void bind(String serialNo, OperatorInfo operatorInfo) throws MatrixCardException;
	
	void unBind(String serialNo, OperatorInfo operatorInfo)throws MatrixCardException;
	
	/**验证点击的链接地址是否发送的EMAIL地址
	 * @param memberCode
	 * @param checkCode
	 * @return
	 * @throws MatrixCardException
	 */
	boolean validateEmail(Long memberCode,String checkCode) throws MatrixCardException ;
	
	
	

}
