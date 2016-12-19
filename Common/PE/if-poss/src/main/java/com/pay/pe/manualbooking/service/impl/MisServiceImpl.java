package com.pay.pe.manualbooking.service.impl;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.pe.manualbooking.dao.MisDao;
import com.pay.pe.manualbooking.model.VouchData;
import com.pay.pe.manualbooking.model.VouchDetailData;
import com.pay.pe.manualbooking.service.MisService;

public class MisServiceImpl implements MisService {

	private MisDao<VouchData> misDao;

	public void setMisDao(MisDao misDao) {
		this.misDao = misDao;
	}

	public List getVouchDetail() {
		return misDao.getVouchDetail();
	}

	public List getVouchData(VouchData vd) {

		return misDao.getVouchData(vd);
	}

	public void insertVouchData(VouchData vd) {
		misDao.insertVouchData(vd);
	}

	public void updateVouchstatus(VouchData vd) {
		misDao.updateVouchstatus(vd);
	}

	public List getQueryVouchDetailInfo(long id) {

		return misDao.getQueryVouchDetailInfo(id);
	}

	public boolean deleteVouchDetail(long id) {

		return misDao.deleteVouchDetail(id);
	}

	public Page<VouchData> search(Page<VouchData> page, Map<String, String> map)
			throws Exception {
		return misDao.findByQuery("getVouchDataInfo", page, map);
	}

	public Page<VouchDetailData> searchVouchDetail(Page<VouchDetailData> page,
			Map<String, String> map) throws Exception {

		return misDao.findByQuery("getVouchDetailInfo", page, map);

	}
}
