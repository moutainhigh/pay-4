package com.pay.credit.service.finace;

import java.util.List;

import com.pay.credit.model.finace.FinaceProtocoDetail;

public interface FinaceProtocolDetailService {

	List<FinaceProtocoDetail> finaceProtocoDetailByCreditOrderId(
			String creditOrderId);

}
