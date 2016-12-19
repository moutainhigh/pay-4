package com.pay.acc.member.service;

import com.pay.acc.member.dto.IndividualInfoDto;

public interface IndividualInfoService {

	/**
	 * 
	 * @param individualInfoDto
	 * @return
	 */
	Long createIndividualInfo(IndividualInfoDto individualInfoDto);

	/**
	 * 根据会员号查询基本信息
	 * 
	 * @param memberCode
	 * @return
	 */
	public IndividualInfoDto queryIndividualInfoByMemberCode(Long memberCode);

	/**
	 * 修改个人会员基本信息
	 * 
	 * @param individualInfoDto
	 * @return
	 */
	boolean updateIndividualInfoByMemberCode(IndividualInfoDto individualInfoDto);
}
