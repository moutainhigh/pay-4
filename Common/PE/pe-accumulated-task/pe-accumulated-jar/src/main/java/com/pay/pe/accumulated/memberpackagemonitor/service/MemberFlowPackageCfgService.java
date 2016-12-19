package com.pay.pe.accumulated.memberpackagemonitor.service;

import java.util.List;
import java.util.Map;

import com.pay.pe.accumulated.memberpackagemonitor.dto.MemberFlowPackageCfgDto;
/**
 * 会员包量配置Service
 * @author 
 *
 */
public interface MemberFlowPackageCfgService {
	/**
	 * 根据条件查询会员包量流量配置列表
	 * @param paramMap
	 * @return
	 */
	public List<MemberFlowPackageCfgDto> queryMemFlowPckCfgList(Map<String, Object> paramMap);
	/**
	 * 
	 * @param paramMap
	 * @param sql
	 * @return
	 */
	public List<MemberFlowPackageCfgDto> queryMemFlowPckCfgList(Map<String, Object> paramMap, String sql);
	/**
	 * 更新会员包量配置信息
	 * @param MemberFlowPackageCfgDto
	 */
	public void updateMemFlowPckRnTx(MemberFlowPackageCfgDto memberFlowPackageCfgDto);
}
