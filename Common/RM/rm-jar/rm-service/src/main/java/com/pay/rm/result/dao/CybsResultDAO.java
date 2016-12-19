package com.pay.rm.result.dao;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.rm.result.dto.CybsResult;

public interface CybsResultDAO  {

	List<CybsResult> findCybsResult(CybsResult cy);

	List<CybsResult> findCybsResult(CybsResult cy, Page page);

}
