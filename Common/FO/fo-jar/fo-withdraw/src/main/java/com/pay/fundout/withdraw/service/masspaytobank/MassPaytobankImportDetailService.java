/**
 *  File: MassPaytobankImportDetailService.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-6      bill_peng     Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.masspaytobank;

import java.util.List;
import java.util.Map;

import com.pay.fundout.withdraw.dto.WithdrawOrderAppDTO;
import com.pay.fundout.withdraw.dto.masspaytobank.MassPaytobankImportDetailDTO;
import com.pay.inf.exception.AppException;
import com.pay.poss.base.exception.PossException;

/**
 * @author bill_peng
 *
 */
public interface MassPaytobankImportDetailService {
	

	/**
	 * 根据上传流水号获得所有批量付款到银行记录信息
	 * @param uploadSeq
	 * @return
	 */
	List<MassPaytobankImportDetailDTO> getMassPaytobankImportDetails(Long uploadSeq);
	/**
	 * 根据上传流水号获取有效的批量付款到银行记录信息
	 * @param uploadSeq
	 * @return
	 */
	List<MassPaytobankImportDetailDTO> getValidMassPaytobankImportDetails(Long uploadSeq);
	
	/**
	 * 根据上传流水号获取无效的批量付款到银行记录信息
	 * @param uploadSeq
	 * @return
	 */
	List<MassPaytobankImportDetailDTO> getInValidMassPaytobankImportDetails(Long uploadSeq);
	
	/**
	 * 保存上传的批量付款到银行明细记录信息
	 * @param dto
	 */
	Long saveMassPaytobankImportDetailInfo(MassPaytobankImportDetailDTO dto);

	/**
	 * 得到明细列表(分页)
	 * @param params
	 * @return
	 */
	public List getDetailInfoList(Map params);
	
	/**
	 * 记录总数
	 * @param params
	 * @return
	 */
	public Integer getTotalCount(Map params);
	
	/**
	 * 得到明细列表
	 * 
	 * @param params
	 * @return
	 */
	public List getDetailInfoListAll(Map params);
	
	/**
	 * 更新是否已生成订单标志
	 * @param detail
	 * @return
	 */
	public int updateOrderStatus(MassPaytobankImportDetailDTO detail);
	
	/**
	 * 插入子订单
	 * @param detail
	 * @param base
	 * @param order
	 * @throws PossException
	 */
	void createSingleOrderRnTx(MassPaytobankImportDetailDTO detail, WithdrawOrderAppDTO withdrawOrderAppDTO) throws PossException;
	
	/**
	 * 批量存储上传明细信息 
	 * @param details
	 * @param uploadSeq
	 * @throws AppException
	 */
	public void saveMassPaytobankImportDetailsRdTx(List<MassPaytobankImportDetailDTO> details,Long uploadSeq) throws AppException;
}
