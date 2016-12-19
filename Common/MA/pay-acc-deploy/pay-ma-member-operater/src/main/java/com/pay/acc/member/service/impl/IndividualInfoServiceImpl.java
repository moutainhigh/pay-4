package com.pay.acc.member.service.impl;

import java.util.HashMap;
import java.util.Map;

import com.pay.acc.member.dao.IndividualInfoDAO;
import com.pay.acc.member.dto.IndividualInfoDto;
import com.pay.acc.member.model.IndividualInfo;
import com.pay.acc.member.service.IndividualInfoService;
import com.pay.util.BeanConvertUtil;

public class IndividualInfoServiceImpl implements IndividualInfoService {

	private IndividualInfoDAO individualInfoDAO;

	@Override
	public Long createIndividualInfo(IndividualInfoDto individualInfoDto) {

		return (Long) individualInfoDAO.create(BeanConvertUtil.convert(
				IndividualInfo.class, individualInfoDto));
	}

	public IndividualInfoDto queryIndividualInfoByMemberCode(Long memberCode) {
		Map<String, Object> paraMap = new HashMap<String, Object>();
		paraMap.put("memberCode", memberCode);
		IndividualInfo individualInfo = null;
		individualInfo = (IndividualInfo) individualInfoDAO
				.findObjectByTemplate("queryIndividualInfoByMemberCode",
						paraMap);

		return BeanConvertUtil.convert(IndividualInfoDto.class, individualInfo);
	}

	@Override
	public boolean updateIndividualInfoByMemberCode(
			IndividualInfoDto individualInfoDto) {

		return individualInfoDAO.update(BeanConvertUtil.convert(
				IndividualInfo.class, individualInfoDto));
	}

	public IndividualInfoDAO getIndividualInfoDAO() {
		return individualInfoDAO;
	}

	public void setIndividualInfoDAO(IndividualInfoDAO individualInfoDAO) {
		this.individualInfoDAO = individualInfoDAO;
	}

}
