package com.pay.base.service.matrixcard.request;

import com.pay.base.dto.ResultDto;
import com.pay.base.dto.matrixcard.transmgr.MatrixCardTransInfoDto;
import com.pay.base.exception.matrixcard.MatrixCardException;

/**
 * @author jim_chen
 * @version 2010-9-16
 */
public interface IMatrixCardReqService {

	/**
	 * 发放口令卡
	 * 
	 * @param transInfoDto
	 * @return ResultDto MatrixCardDto
	 */
	public ResultDto processRequest(MatrixCardTransInfoDto transInfoDto);

	/**
	 * 下载口令卡
	 * 
	 * @param id
	 * @param sessionId
	 * @return
	 * @throws MatrixCardException
	 */
	public byte[] download(String id, String sessionId) throws MatrixCardException;

	/**
	 * 判断用户是否申请了口令卡没有绑定
	 * 
	 * @param memberCode
	 * @return
	 */
	public ResultDto showRequestMatrixCard(Long memberCode);
}
