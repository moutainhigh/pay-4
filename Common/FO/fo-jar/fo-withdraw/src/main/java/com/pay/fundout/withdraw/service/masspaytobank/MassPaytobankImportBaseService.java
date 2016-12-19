/**
 *  File: MassPaytobankImportBaseService.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-6      bill_peng     Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.masspaytobank;

import java.util.List;
import java.util.Map;

import com.pay.fundout.withdraw.dto.masspaytobank.MassPaytobankImportBaseDTO;

/**
 * @author bill_peng
 *
 */
public interface MassPaytobankImportBaseService {

	/**
	 * 根据上传流水获取批量付款到银行的基本信息
	 * @param uploadSeq
	 */
	MassPaytobankImportBaseDTO  getMassPaytobankImportBaseInfo(Long uploadSeq);
	/**
	 * 根据业务参考号获取批量付款到银行的基本信息
	 * @param businessNo
	 * @return
	 */
	MassPaytobankImportBaseDTO  getMassPaytobankImportBaseByBusinessNo(String businessNo,Long membCode);
	
	/**
	 * 保存批量付款到银行的基本信息
	 * @param dto
	 */
	Long saveMassPaytobankImportBaseInfo(MassPaytobankImportBaseDTO dto);
	

	/**
	 * 查询上传列表(分页)
	 * @param page
	 * @param params
	 * @return
	 */
	public List getUploadFileInfoList(Map params); 

	/**
	 * 判断批次号是否存在
	 * @param businessNo
	 * @return
	 */
	public boolean isExistBusinessNo(String businessNo,Long membCode);

	/**
	 * 记录总数
	 * @param params
	 * @return
	 */
	public Integer getTotalCount(Map params);
	
	/**
	 * 更新上传信息
	 * @param page
	 * @param params
	 * @return
	 */
	public boolean updatePaytobankImportBase(MassPaytobankImportBaseDTO dto);

}
