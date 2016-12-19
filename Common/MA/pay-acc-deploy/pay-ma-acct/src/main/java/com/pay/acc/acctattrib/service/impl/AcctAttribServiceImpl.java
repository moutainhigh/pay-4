/**
 * 
 */
package com.pay.acc.acctattrib.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.util.StringUtils;

import com.pay.acc.acctattrib.dao.AcctAttribDAO;
import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.acctattrib.exception.AcctAttribException;
import com.pay.acc.acctattrib.exception.AcctAttribUnknowException;
import com.pay.acc.acctattrib.model.AcctAttrib;
import com.pay.acc.acctattrib.service.AcctAttribService;
import com.pay.util.BeanConvertUtil;

/**
 * @author Administrator
 * 
 */
public class AcctAttribServiceImpl implements AcctAttribService {

	private AcctAttribDAO acctAttribDAO;

	public Long createAcctAttrib(AcctAttribDto acctAttrib) {
		return (Long) acctAttribDAO.create(BeanConvertUtil.convert(
				AcctAttrib.class, acctAttrib));
	}

	@Override
	public boolean isAllowAcctrib(Map<String, Object> paramMap) {
		return this.acctAttribDAO.isAllowAcctrib(paramMap);
	}

	@Override
	public AcctAttribDto queryAcctAttribWithAcctCode(String acctCode)
			throws AcctAttribException, AcctAttribUnknowException {
		if (acctCode == null || !StringUtils.hasText(acctCode)) {
			throw new AcctAttribException("参数输入 " + acctCode + " 不正确");

		}
		AcctAttrib acctAttrib = null;
		try {
			acctAttrib = this.acctAttribDAO
					.queryAcctAttribWithAcctCode(acctCode);
		} catch (Exception e) {
			throw new AcctAttribUnknowException(e);
		}
		return BeanConvertUtil.convert(AcctAttribDto.class, acctAttrib);
	}

	public AcctAttribDto queryAcctAttribByAcctCode(Long acctCode) {
		AcctAttrib acctAttrib = (AcctAttrib) this.acctAttribDAO
				.findObjectByTemplate("queryAcctAttribByAcctCode", acctCode);
		return BeanConvertUtil.convert(AcctAttribDto.class, acctAttrib);
	}

	@Override
	public boolean resetAcctAttribPwd(String acctCode, String newPayPwd)
			throws AcctAttribException, AcctAttribUnknowException {
		if (null == acctCode || !StringUtils.hasText(acctCode)) {
			throw new AcctAttribException("参数输入 " + acctCode + " 不正确");
		}
		if (null == newPayPwd || !StringUtils.hasText(newPayPwd)) {
			throw new AcctAttribException("参数输入 " + newPayPwd + " 不正确");
		}
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("acctCode", acctCode);
		map.put("newPayPwd", newPayPwd);
		boolean result = false;
		try {
			result = this.acctAttribDAO.updateAcctAttribPwd(map) == 1;
		} catch (Exception e) {
			throw new AcctAttribUnknowException(e);
		}

		return result;
	}

	/**
	 * @param acctAttribDao
	 *            the acctAttribDao to set
	 */
	public void setAcctAttribDAO(AcctAttribDAO acctAttribDAO) {
		this.acctAttribDAO = acctAttribDAO;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.acc.acctattrib.service.AcctAttribService#
	 * queryAcctAttribWithMemberAcctCode(java.lang.Long)
	 */
	@Override
	public AcctAttribDto queryAcctAttribWithMemberAcctCode(Long memberAcctCode)
			throws AcctAttribException, AcctAttribUnknowException {
		if (memberAcctCode == null || memberAcctCode.longValue() <= 0) {
			throw new AcctAttribException("参数输入 " + memberAcctCode + " 不正确");
		}
		AcctAttrib acctAttrib = null;
		try {
			acctAttrib = this.acctAttribDAO
					.queryAcctAttribWithMemberAcctCode(memberAcctCode);
		} catch (Exception e) {
			throw new AcctAttribUnknowException(e);
		}

		return BeanConvertUtil.convert(AcctAttribDto.class, acctAttrib);
	}

	@Override
	public boolean updateAcctFreezeAllowInStatusWithAcctCode(String acctCode,
			Integer status) throws AcctAttribException,
			AcctAttribUnknowException {
		if (!StringUtils.hasText(acctCode)) {
			throw new AcctAttribException("参数输入 " + acctCode + " 不正确");
		}
		try {
			return this.acctAttribDAO.updateAcctAllowInStatus(acctCode, status);
		} catch (Exception e) {
			throw new AcctAttribUnknowException(e);
		}

	}

	@Override
	public boolean updateAcctFreezeAllowOutStatusWithAcctCode(String acctCode,
			Integer status) throws AcctAttribException,
			AcctAttribUnknowException {
		if (!StringUtils.hasText(acctCode)) {
			throw new AcctAttribException("参数输入 " + acctCode + " 不正确");
		}
		try {
			return this.acctAttribDAO
					.updateAcctAllowOutStatus(acctCode, status);
		} catch (Exception e) {
			throw new AcctAttribUnknowException(e);
		}
	}

	@Override
	public boolean updateAcctFreezeAllowOutStatusWithMemberCode(
			String memberCode, Integer status) throws AcctAttribException,
			AcctAttribUnknowException {
		if (!StringUtils.hasText(memberCode)) {
			throw new AcctAttribException("参数输入 " + memberCode + " 不正确");
		}
		try {
			return this.acctAttribDAO.updateAcctAllowOutStatusWithMemberCode(
					memberCode, status);
		} catch (Exception e) {
			throw new AcctAttribUnknowException(e);
		}
	}

	@Override
	public boolean updateAcctFreezeWithAcctCode(String acctCode, Integer status)
			throws AcctAttribException, AcctAttribUnknowException {
		if (!StringUtils.hasText(acctCode)) {
			throw new AcctAttribException("参数输入 " + acctCode + " 不正确");
		}
		try {
			return this.acctAttribDAO.updateAcctFreeze(acctCode, status);
		} catch (Exception e) {
			throw new AcctAttribUnknowException(e);
		}
	}

	@Override
	public int countAcctAttribFreeze(String acctCode) {
		return this.acctAttribDAO.countAcctAttribFreeze(acctCode);
	}

	@Override
	public int countAllowOutRecord(String acctCode) {
		return this.acctAttribDAO.countAllowOutRecord(acctCode);
	}

	@Override
	public int countPossAcctAttrib(String acctCode, int status, int type) {
		Map<String, Object> paramMap = new HashMap<String, Object>(3);
		paramMap.put("status", status);
		paramMap.put("type", type);
		paramMap.put("acctCode", acctCode);
		return this.acctAttribDAO.countPossAcctAttrib(paramMap);
	}

	@Override
	public boolean countIsAllowAcctribByMemberCode(Map<String, Object> paramMap) {
		return acctAttribDAO.countIsAllowAcctribByMemberCode(paramMap);
	}

	public AcctAttribDto queryAcctAttribByAcctCode(String acctCode) {
		AcctAttrib acctAttrib = (AcctAttrib) acctAttribDAO
				.findObjectByTemplate("queryAcctAttribByAcctCode", acctCode);
		return BeanConvertUtil.convert(AcctAttribDto.class, acctAttrib);
	}

	@Override
	public AcctAttribDto queryAcctAttribByMemberCodeAndAcctType(
			Long memberCode, Integer acctType) {

		Map paraMap = new HashMap();
		paraMap.put("memberCode", memberCode);
		paraMap.put("acctType", acctType);

		return BeanConvertUtil.convert(AcctAttribDto.class, acctAttribDAO
				.findObjectByCriteria("queryByMemberCodeAndAcctType", paraMap));
	}

	@Override
	public boolean updatePayPwd(String acctCode, String payPwd) {

		Map paraMap = new HashMap();
		paraMap.put("acctCode", acctCode);
		paraMap.put("newPayPwd", payPwd);
		int cnt = acctAttribDAO.updateAcctAttribPwd(paraMap);
		return cnt == 1;
	}

	@Override
	public AcctAttribDto doQueryDefaultAcctAttribNsTx(Long memberCode) {

		return BeanConvertUtil.convert(AcctAttribDto.class, acctAttribDAO
				.findObjectByCriteria("queryDefaultAcctAttrib", memberCode));
	}

	@Override
	public List<AcctAttribDto> doQueryAcctAttribByMemberCodeAndCurrencyCodeNsTx(
			Long memberCode, String currencyCode) {

		Map paraMap = new HashMap();
		paraMap.put("memberCode", memberCode);
		paraMap.put("curCode", currencyCode);

		return (List<AcctAttribDto>) BeanConvertUtil.convert(
				AcctAttribDto.class, acctAttribDAO.findByCriteria(
						"queryAcctAttribByMemberCodeAndCurrencyCode", paraMap));
	}

	@Override
	public List<AcctAttribDto> QueryAcctAttribNsTx(Long memberCode) {

		return (List<AcctAttribDto>) BeanConvertUtil.convert(
				AcctAttribDto.class, acctAttribDAO.findByCriteria(
						"QueryAcctAttribNsTx", memberCode));
	}
	@Override
	public List<AcctAttribDto> QueryByMemberCodeAndAcctTypeNsTx(Long memberCode) {
		
		return (List<AcctAttribDto>) BeanConvertUtil.convert(
				AcctAttribDto.class, acctAttribDAO.findByCriteria(
						"queryByMemberCodeAndBaseAccount", memberCode));
	}
	
}
