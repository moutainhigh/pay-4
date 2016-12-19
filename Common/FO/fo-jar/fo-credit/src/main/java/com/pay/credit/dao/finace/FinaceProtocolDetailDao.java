package com.pay.credit.dao.finace;

import java.util.List;

import com.pay.credit.model.finace.FinaceProtocoDetail;
import com.pay.inf.dao.BaseDAO;

public interface FinaceProtocolDetailDao extends BaseDAO<FinaceProtocoDetail> {

	List<FinaceProtocoDetail> finaceProtocoDetailByCreditOrderId(
			String creditOrderId);

}
