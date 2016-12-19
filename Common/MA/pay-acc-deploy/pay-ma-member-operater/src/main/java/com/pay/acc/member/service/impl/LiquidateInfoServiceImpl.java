package com.pay.acc.member.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.pay.acc.member.dao.LiquidateInfoDAO;
import com.pay.acc.member.dto.LiquidateInfoDto;
import com.pay.acc.member.exception.MemberException;
import com.pay.acc.member.exception.MemberUnknowException;
import com.pay.acc.member.model.LiquidateInfo;
import com.pay.acc.member.service.LiquidateInfoService;
import com.pay.inf.dao.Page;
import com.pay.util.BeanConvertUtil;

public class LiquidateInfoServiceImpl implements LiquidateInfoService {
	private LiquidateInfoDAO liquidateInfoDAO;

	@Override
	public void insertLiquidateInfo(final LiquidateInfoDto liquidateInfo) {

		liquidateInfoDAO.create("insertLiquidateInfo",
				BeanConvertUtil.convert(LiquidateInfo.class, liquidateInfo));
	}

	@Override
	public List<LiquidateInfoDto> queryLiquidateInfoByMemberCode(final Long memberCode)
			throws MemberException, MemberUnknowException {
		if (memberCode == null || memberCode.longValue() == 0) {
			throw new MemberException("memberCode参数输入 " + memberCode + " 不正确");
		}
		List<LiquidateInfo> list = null;
		List<LiquidateInfoDto> liquidateInfoDtoList = null;
		try {
			list = liquidateInfoDAO.findByTemplate(
					"queryLiquidateInfoByMemberCode", memberCode);
		} catch (Exception e) {
			throw new MemberUnknowException(e);
		}

		try {
			if (null != list && list.size() > 0) {
				liquidateInfoDtoList = new ArrayList<LiquidateInfoDto>();
				for (LiquidateInfo liquidateInfo : list) {
					LiquidateInfoDto liquidateInfoDto = BeanConvertUtil
							.convert(LiquidateInfoDto.class, liquidateInfo);
					liquidateInfoDtoList.add(liquidateInfoDto);
				}
			}
		} catch (Exception e) {
			throw new MemberException(
					"liquidateInfo 转  LiquidateInfoDto：BeanConvertUtil.convert方法 出错");
		}

		return liquidateInfoDtoList;

	}

	public LiquidateInfoDAO getLiquidateInfoDAO() {
		return liquidateInfoDAO;
	}

	public void setLiquidateInfoDAO(final LiquidateInfoDAO liquidateInfoDAO) {
		this.liquidateInfoDAO = liquidateInfoDAO;
	}

	@Override
	public boolean querySettlePeriod(final Long memberCode, final int period) {
		int obj = this.liquidateInfoDAO.countSettlePeriod(memberCode, period);
		if (obj > 0) {
			return true;
		}
		return false;
	}

	@Override
	public List<LiquidateInfo> findByCriteria(final Map<String, Object> hMap,
			final Page<?> page) {
		return this.liquidateInfoDAO.findByCriteria(hMap, page) ;
	}

	@Override
	public int updateLiquidateInfoStatus(final Map<String, Object> hMap) {
		return this.liquidateInfoDAO.updateLiquidateInfoStatus(hMap) ;
	}

}
