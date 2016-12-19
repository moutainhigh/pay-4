package com.pay.credit.dao.finace;

import java.util.List;

import com.pay.credit.model.finace.FinaceProtocoDetail;
import com.pay.credit.model.finace.FinaceProtocol;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;

public interface FinaceProtocolDao extends BaseDAO<FinaceProtocol> {

	List<FinaceProtocol> finaceProtocoQuery(FinaceProtocol protocol, Page page);

}
