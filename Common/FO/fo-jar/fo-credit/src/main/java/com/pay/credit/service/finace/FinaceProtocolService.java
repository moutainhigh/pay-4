package com.pay.credit.service.finace;

import java.util.List;

import com.pay.credit.model.finace.FinaceProtocoDetail;
import com.pay.credit.model.finace.FinaceProtocol;
import com.pay.inf.dao.Page;


public interface FinaceProtocolService {

	List<FinaceProtocol> finaceProtocoQuery(FinaceProtocol protocol, Page page);

}
