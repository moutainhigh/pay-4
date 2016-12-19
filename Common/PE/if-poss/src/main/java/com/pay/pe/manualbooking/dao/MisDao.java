package com.pay.pe.manualbooking.dao;

import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.pe.manualbooking.model.VouchData;

public interface MisDao<VouchData> extends BaseDAO {

	public List getVouchData(VouchData vd);

	public void insertVouchData(VouchData vd);

	public void updateVouchstatus(VouchData vd);

	public List getVouchDetail();

	public boolean deleteVouchDetail(long id);

	public List getQueryVouchDetailInfo(long id);
}
