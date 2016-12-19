package com.pay.base.service.matrixcard.transmgr;

import com.pay.base.dto.matrixcard.transmgr.MatrixCardTransInfoDto;
import com.pay.base.exception.matrixcard.MatrixCardException;


/**
 * @author jim_chen
 * @version 
 * 2010-9-16 
 */
public interface IMatrixCardTransInfoService {
	
	/**
	 * 保存 口令卡操作信息表
	 * @param dto
	 * @return
	 */
	public MatrixCardTransInfoDto save(MatrixCardTransInfoDto dto)throws MatrixCardException;
	
	/**统计口令卡操作信息表的纪录
	 * @return
	 */
	int countMatrixCardTransInfo();
	
	/**根据ID查询口令卡操作信息表
	 * @param id
	 * @return
	 */
	public MatrixCardTransInfoDto selectMatrixCardTransInfoById(Long id);
	
	/**根据trandsId查询口令卡操作信息表
	 * @param trandsId
	 * @return
	 */
	public MatrixCardTransInfoDto selectMatrixCardTransInfoByTransId(Long trandsId);
	
	/**更新口令卡操作信息表
	 * @param dto
	 * @return
	 */
	public void updateMatrixCardTransInfo(MatrixCardTransInfoDto dto)throws MatrixCardException;
	
	
	/**根据memberCode查询用户申请的口令卡
	 * @return
	 * @throws MatrixCardException
	 */
	public  MatrixCardTransInfoDto selectRequestMcIdByMemberCode(Long memberCode)throws MatrixCardException;
	


}
