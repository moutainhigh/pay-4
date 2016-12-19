package com.pay.pe.manualbooking.service;

import com.pay.pe.manualbooking.dto.VouchDataDto;


/**
 * 手工记帐日志服务
 */
public interface VouchLogService {
	/**
	 * @param currentDto
	 * @param operator
	 * @param ip
	 * @param remark
	 * 记录手工记账创建日志
	 */
	void logCreate(VouchDataDto currentDto, String operator, String ip, String remark);
	
	/**
	 * @param beforeActionDto
	 * @param afterActionDto
	 * @param operator
	 * @param ip
	 * @param remark
	 * 记录手工记账取消成功日志
	 */
	void logCancelSuccess(VouchDataDto beforeActionDto, VouchDataDto afterActionDto, String operator, String ip, String remark);
	
	/**
	 * @param currentDto
	 * @param operator
	 * @param ip
	 * @param remark
	 * 记录手工记账取消失败日志
	 */
	void logCancelFailure(VouchDataDto currentDto, String operator, String ip, String remark);
	
	/**
	 * @param beforeActionDto
	 * @param afterActionDto
	 * @param operator
	 * @param ip
	 * @param remark
	 * 记录手工记账审核不通过成功日志
	 */
	void logRejectSuccess(VouchDataDto beforeActionDto, VouchDataDto afterActionDto, String operator, String ip, String remark);
	
	/**
	 * @param currentDto
	 * @param operator
	 * @param ip
	 * @param remark
	 * 记录手工记账审核不通过失败日志
	 */
	void logRejectFailure(VouchDataDto currentDto, String operator, String ip, String remark);
	
	/**
	 * @param beforeActionDto
	 * @param afterActionDto
	 * @param operator
	 * @param ip
	 * @param remark
	 * 记录手工记账审核通过成功日志
	 */
	void logApproveSuccess(VouchDataDto beforeActionDto, VouchDataDto afterActionDto, String operator, String ip, String remark);
	
	/**
	 * @param currentDto
	 * @param operator
	 * @param ip
	 * @param remark
	 * 记录手工记账审核不通过失败日志
	 */
	void logApproveFailure(VouchDataDto currentDto, String operator, String ip, String remark);
}
