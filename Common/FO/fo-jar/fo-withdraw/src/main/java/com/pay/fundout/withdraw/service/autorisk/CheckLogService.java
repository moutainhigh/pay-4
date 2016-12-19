package com.pay.fundout.withdraw.service.autorisk;

import java.util.List;

import com.pay.fundout.withdraw.dto.autorisk.AutoRiskDto;
import com.pay.fundout.withdraw.dto.autorisk.LoginFailLogDto;
import com.pay.inf.dao.Page;

/**
 * 出款交易风险审核日志接口
 * 
 * @author meng.li
 * 
 */
public interface CheckLogService {

	/**
	 * 创建日志
	 * 
	 * @param dto
	 *            日志实体
	 * @return
	 */
	public Long createCheckLog(AutoRiskDto dto);

	/**
	 * 创建登录失败信息
	 * 
	 * @param dto
	 *            错误日志信息
	 * @return
	 */
	public Long createLoginFailLog(LoginFailLogDto dto);

	/**
	 * 批量创建日志
	 * 
	 * @param list
	 *            日志列表
	 */
	public void createCheckLogsRdTx(List<AutoRiskDto> list);

	/**
	 * 修改日志
	 * 
	 * @param dto
	 *            日志实体
	 */
	public void updateCheckLog(AutoRiskDto dto);

	/**
	 * 查看分页日志信息
	 * 
	 * @param page
	 *            分页实体
	 * @param dto
	 *            查询dto
	 * @return 分页后的信息
	 */
	public Page<AutoRiskDto> getCheckLogList(Page<AutoRiskDto> page,
			AutoRiskDto dto);

	/**
	 * 根据条件查询日志列表
	 * 
	 * @param dto
	 * @return
	 */
	public List<AutoRiskDto> queryCheckLogList(AutoRiskDto dto);

	/**
	 * 根据主键查找对应的实体信息
	 * 
	 * @param recordNo
	 * @return
	 */
	public AutoRiskDto findById(Long recordNo);

	/**
	 * 批量审核日志，并且将原始工单状态设为初始状态
	 * 
	 * @param recordNos
	 * @param checkRemark
	 */
	public void CheckLogsRdTx(String recordNos, String operator,
			String checkRemark);

}
