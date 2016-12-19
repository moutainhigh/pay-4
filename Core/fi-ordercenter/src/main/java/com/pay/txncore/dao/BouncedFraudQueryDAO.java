package com.pay.txncore.dao;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.txncore.dto.BouncedFraudResultDTO;
import com.pay.txncore.dto.BouncedResultDTO;
 /**
  * 单项登记查询
  *  File: BouncedQueryDAO.java
  *  Description:
  *  Copyright 2016-2030 IPAYLINKS Corporation. All rights reserved.
  *  Date      Author      Changes
  *  2016年5月11日   mmzhang     Create
  *
  */
public interface BouncedFraudQueryDAO extends BaseDAO<BouncedFraudResultDTO> {

	public List<BouncedFraudResultDTO> bouncedFraudQuery(Map<String, Object> map);

}