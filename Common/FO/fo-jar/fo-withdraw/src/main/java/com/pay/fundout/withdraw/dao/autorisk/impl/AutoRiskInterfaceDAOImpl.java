package com.pay.fundout.withdraw.dao.autorisk.impl;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.fundout.withdraw.dao.autorisk.AutoRiskInterfaceDAO;
import com.pay.fundout.withdraw.dto.autorisk.IpDto;
import com.pay.fundout.withdraw.dto.autorisk.MemBerDto;
import com.pay.fundout.withdraw.dto.autorisk.TransSumDto;
import com.pay.fundout.withdraw.dto.autorisk.TransWebsiteDto;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;

public class AutoRiskInterfaceDAOImpl extends BaseDAOImpl implements
		AutoRiskInterfaceDAO {

	@Override
	public boolean isIpConnected(Long memberCode, Date beginDate, Date endDate) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("memberCode", memberCode);
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);
		int connectedNums = (Integer) this.getSqlMapClientTemplate()
				.queryForObject("autoriskinterface.isIpConnected", params);
		if (connectedNums > 0) {
			return true;
		}
		return false;
	}

	@Override
	public BigDecimal queryFundInAmount(Long memberCode, Date beginDate,
			Date endDate) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("memberCode", memberCode);
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);
		return (BigDecimal) this.getSqlMapClientTemplate().queryForObject(
				"autoriskinterface.queryFundInAmount", params);
	}

	@Override
	public BigDecimal queryFundInMaxAmount(Long memberCode, Date beginDate,
			Date endDate) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("memberCode", memberCode);
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);
		return (BigDecimal) this.getSqlMapClientTemplate().queryForObject(
				"autoriskinterface.queryFundInMaxAmount", params);
	}

	@Override
	public int queryFundInNums(Long memberCode, Date beginDate, Date endDate) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("memberCode", memberCode);
		params.put("beginDate", beginDate);
		params.put("endDate", endDate);
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				"autoriskinterface.queryFundInNums", params);
	}

	@Override
	public int queryFundOutTimes(Long memberCode) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("memberCode", memberCode);
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				"autoriskinterface.queryFundOutTimes", params);
	}

	@Override
	public int queryBatchFundOutTimes(Long memberCode, Long parentOrderid) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("memberCode", memberCode);
		params.put("parentOrderid", parentOrderid);
		return (Integer) this.getSqlMapClientTemplate().queryForObject(
				"autoriskinterface.queryBatchFundOutTimes", params);
	}

	@Override
	public MemBerDto queryIndividualInfo(Long memberCode) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("memberCode", memberCode);
		return (MemBerDto) this.getSqlMapClientTemplate().queryForObject(
				"autoriskinterface.queryIndividualInfo", params);
	}

	@Override
	public MemBerDto queryEnterpriseInfo(Long memberCode) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("memberCode", memberCode);
		return (MemBerDto) this.getSqlMapClientTemplate().queryForObject(
				"autoriskinterface.queryEnterpriseInfo", params);
	}

	@Override
	public Page<IpDto> queryIpInfo(Page<IpDto> page, Long memberCode) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("memberCode", memberCode);
		// return
		// (List<IpDto>)this.getSqlMapClientTemplate().queryForList("autoriskinterface.queryIpInfo",
		// params);
		return this.findByQuery("autoriskinterface.queryIpInfo", page, params);
	}

	@Override
	public List<TransSumDto> queryTransSumInfo(Long memberCode) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("memberCode", memberCode);
		return (List<TransSumDto>) this.getSqlMapClientTemplate().queryForList(
				"autoriskinterface.queryTransSumInfo", params);
	}

	/**
	 * 根据会员号查找交易网址
	 * 
	 * @param memberCode
	 *            会员号
	 * @return 交易网址统计列表
	 */
	public List<TransWebsiteDto> queryTransWebsiteInfo(Long memberCode) {
		Map<String, Object> params = new HashMap<String, Object>();
		params.put("memberCode", memberCode);
		return (List<TransWebsiteDto>) this
				.getSqlMapClientTemplate()
				.queryForList("autoriskinterface.queryTransWebsiteInfo", params);
	}

}
