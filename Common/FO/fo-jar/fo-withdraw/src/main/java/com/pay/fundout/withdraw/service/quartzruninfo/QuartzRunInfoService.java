package com.pay.fundout.withdraw.service.quartzruninfo;

import com.pay.fundout.withdraw.dto.quartzruninfo.QuartzRunInfoDTO;

public interface QuartzRunInfoService {
	
	/**
	 * 保存quartzrunInfo 信息
	 * @param dto
	 */
	public Long createQuartzRunInfo(QuartzRunInfoDTO dto);
	
	/**
	 * 更新Quartz信息
	 * @param dto
	 */
	public void updateQuartzRunInfo(QuartzRunInfoDTO dto);
	
	/**
	 * 通过主标识查找
	 * @param sequenceId
	 * @return
	 */
	public QuartzRunInfoDTO getQuartzRunInfoById(Long sequenceId);
	
	/**
	 * 根据外键和业务类型查找
	 * @param fkId
	 * @param bussiType
	 * @return
	 */
	public QuartzRunInfoDTO getQuartzRunInfoByBussiType(Long fkId,Integer busiType);

}
