package com.pay.rm.service.service.facade.impl;

import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.pay.rm.service.common.RCCons;
import com.pay.rm.service.dao.rmlimit.businesslimit.RcBusinessLimitDAO;
import com.pay.rm.service.dao.rmlimit.businesslimitcustom.RcBusinessLimitCustomDAO;
import com.pay.rm.service.dao.rmlimit.innerlimit.RcInnerLimitDAO;
import com.pay.rm.service.dao.rmlimit.mcclimit.RcMccLimitDAO;
import com.pay.rm.service.dao.rmlimit.userlimit.RcUserLimitDAO;
import com.pay.rm.service.dto.rmlimit.businesslimitcustom.RcBusinessLimitCustomDTO;
import com.pay.rm.service.model.rmlimit.businesslimit.RcBusinessLimit;
import com.pay.rm.service.model.rmlimit.businesslimitcustom.RcBusinessLimitCustom;
import com.pay.rm.service.model.rmlimit.innerlimit.RcInnerLimit;
import com.pay.rm.service.model.rmlimit.mcclimit.RcMccLimit;
import com.pay.rm.service.model.rmlimit.userlimit.RcUserLimit;
import com.pay.rm.service.service.facade.RCLimitService;

public class RCLimitServiceImpl implements RCLimitService {
	private RcMccLimitDAO<RcMccLimit> rcMccLimitDAO;
	private RcBusinessLimitCustomDAO<RcBusinessLimitCustom> rcBusinessLimitCustomDAO;
	private RcInnerLimitDAO<RcInnerLimit> rcInnerLimitDAO;
	private RcUserLimitDAO<RcUserLimit> rcUserLimitDAO;
	private RcBusinessLimitDAO<RcBusinessLimit> rcBusinessLimitDAO;

	@Override
	public long createBusinessLimitCustom(RcBusinessLimitCustomDTO dto) {

		RcBusinessLimitCustom pojo = new RcBusinessLimitCustom();
		BeanUtils.copyProperties(dto, pojo);
		return (Long) rcBusinessLimitCustomDAO.create(pojo);
	}

	@Override
	public RcMccLimit getMCCLimit(Long id) {
		return rcMccLimitDAO.findById(id);
	}

	/**
	 * @param mccLimitDAO
	 *            the mccLimitDAO to set
	 */
	public void setMccLimitDao(RcMccLimitDAO<RcMccLimit> mccLimitDAO) {
		this.rcMccLimitDAO = mccLimitDAO;
	}

	/**
	 * @param rcBusinessLimitCustomDAO
	 *            the rcBusinessLimitCustomDAO to set
	 */
	public void setRcBusinessLimitCustomDAO(
			RcBusinessLimitCustomDAO<RcBusinessLimitCustom> rcBusinessLimitCustomDAO) {
		this.rcBusinessLimitCustomDAO = rcBusinessLimitCustomDAO;
	}

	public void setRcInnerLimitDAO(RcInnerLimitDAO<RcInnerLimit> rcInnerLimitDAO) {
		this.rcInnerLimitDAO = rcInnerLimitDAO;
	}

	/**
	 * @param rcUserLimitDAO
	 *            the rcUserLimitDAO to set
	 */
	public void setRcUserLimitDAO(RcUserLimitDAO<RcUserLimit> rcUserLimitDAO) {
		this.rcUserLimitDAO = rcUserLimitDAO;
	}

	/**
	 * @param rcBusinessLimitDAO
	 *            the rcBusinessLimitDAO to set
	 */
	public void setRcBusinessLimitDAO(
			RcBusinessLimitDAO<RcBusinessLimit> rcBusinessLimitDAO) {
		this.rcBusinessLimitDAO = rcBusinessLimitDAO;
	}

	@Override
	public List<RcBusinessLimit> searchBusinessLimit(Map<String, String> map) {

		return this.rcBusinessLimitDAO.findByQuery(
				RCCons.LIMIT_SEARCH_BUSINESSLIMIT_QUERY_COMID, map);
	}

	@Override
	public List<RcUserLimit> searchUserLimit(Map<String, String> map) {

		return this.rcUserLimitDAO.findByQuery(
				RCCons.LIMIT_SEARCH_USERLIMIT_QUERY_COMID, map);
	}

	@Override
	public List<RcInnerLimit> searchSysLimit(Map<String, String> map) {

		return this.rcInnerLimitDAO.findByQuery(
				RCCons.LIMIT_SEARCH_INNERLIMIT_QUERY_COMID, map);
	}

	public List<RcBusinessLimitCustom> searchBusinessLimitCustom(
			Map<String, String> map) {
		// TODO Auto-generated method stub
		return this.rcBusinessLimitCustomDAO.findByQuery(
				RCCons.LIMIT_SEARCH_BUSINESSLIMITCUSTOM_QUERY_COMID, map);
	}

	public void setRcMccLimitDAO(RcMccLimitDAO<RcMccLimit> rcMccLimitDAO) {
		this.rcMccLimitDAO = rcMccLimitDAO;
	}

	@Override
	public boolean deleteBusinessLimitCustom(long businessId) {
		RcBusinessLimitCustom rcBusinessLimitCustom = new RcBusinessLimitCustom();
		rcBusinessLimitCustom.setBusinessId(businessId);
		rcBusinessLimitCustomDAO.delete(
				RCCons.LIMIT_DELETE_BUSINESSLIMITCUSTOM_QUERY_COMID,
				rcBusinessLimitCustom);
		return false;
	}

}
