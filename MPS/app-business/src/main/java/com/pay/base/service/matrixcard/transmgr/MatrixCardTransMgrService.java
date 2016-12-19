package com.pay.base.service.matrixcard.transmgr;

import java.util.Map;

import com.pay.base.dto.matrixcard.transmgr.MatrixCardTransInfoDto;
import com.pay.base.dto.matrixcard.transmgr.MatrixCardTransMgrDto;
import com.pay.base.exception.matrixcard.MatrixCardException;

/**
 * @author jim_chen
 * @version 2010-9-16
 */
public interface MatrixCardTransMgrService {

	/**
	 * 统计口令卡事务表所有的纪录
	 * 
	 * @return
	 */
	public long countMartrixCardTransMgr();

	/**
	 * 
	 * @param paramMap
	 * @return
	 */
	public int countMartrixCardTransMgrByParamMap(Map<String, Object> paramMap);

	/**
	 * 保存口令卡事务表纪录
	 * 
	 * @param dto
	 * @return
	 */
	public MatrixCardTransMgrDto save(MatrixCardTransMgrDto dto);

	/**
	 * 根据同一用户在单位分钟内请求的数量不大于total的数量来判断 是否显示验证码
	 * 
	 * @param sessionId
	 * @param minuts
	 *            单位分钟
	 * @param total
	 *            设置的数量
	 * @return
	 */
	boolean beShowValidateCode(String sessionId, int minuts, int total);

	/**
	 * 开户口令卡申请的事务
	 * 
	 * @param transInfoDto
	 * @return
	 * @throws MatrixCardException
	 */
	public MatrixCardTransInfoDto beginTransInIsolatedRdTx(MatrixCardTransInfoDto transInfoDto) throws MatrixCardException;

	/**
	 * 结束事务，同时更新事务信息
	 * 
	 * @param transId
	 * @param transInfo
	 * @throws MatrixCardException
	 */
	public void finishTransInIsolatedRnTx(Long transId, MatrixCardTransInfoDto transInfo) throws MatrixCardException;

	
	/**
	 * 结束事务，同时更新事务信息
	 * 
	 * @param transId
	 * @param transInfo
	 * @throws MatrixCardException
	 */
	public void finishTransInIsolatedRdTx(Long transId, MatrixCardTransInfoDto transInfo) throws MatrixCardException;

	
	/**
	 * 结束事务
	 * 
	 * @param transId
	 * @throws MatrixCardException
	 */
	public void finishTransInIsolatedRnTx(Long transId) throws MatrixCardException;

	/**
	 * 根据操作员，事务类型查询
	 * @param uId
	 * @param transType
	 * @return
	 * @throws MatrixCardException
	 */
//	MatrixCardTransMgrDto findValidTransByUserInfo(Long uId, int transType) throws MatrixCardException;
	
	/**
	 * 根据memberCode，事务类型查询
	 * @param uId
	 * @param transType
	 * @return
	 * @throws MatrixCardException
	 */
	MatrixCardTransMgrDto findValidTransByMemberCode(Long memberCode, int transType) throws MatrixCardException;

}
