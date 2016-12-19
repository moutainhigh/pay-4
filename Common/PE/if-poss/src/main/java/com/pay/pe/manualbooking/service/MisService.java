package com.pay.pe.manualbooking.service;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.pe.manualbooking.model.VouchData;
import com.pay.pe.manualbooking.model.VouchDetailData;

public interface MisService {

	public List getVouchDetail();

	public List getVouchData(VouchData vd);

	public void insertVouchData(VouchData vd);

	public void updateVouchstatus(VouchData vd);

	public List getQueryVouchDetailInfo(long id);

	public boolean deleteVouchDetail(long id);

	public Page<VouchData> search(Page<VouchData> page, Map<String, String> map)
			throws Exception;

	public Page<VouchDetailData> searchVouchDetail(Page<VouchDetailData> page,
			Map<String, String> map) throws Exception;

}
