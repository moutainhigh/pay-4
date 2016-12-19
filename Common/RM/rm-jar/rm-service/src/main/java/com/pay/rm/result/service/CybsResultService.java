package com.pay.rm.result.service;

import java.util.List;

import com.pay.inf.dao.Page;
import com.pay.rm.result.dto.CybsResult;

public interface CybsResultService {

	List<CybsResult> findCybsResult(CybsResult cy);
	List<CybsResult> findCybsResult(CybsResult cy,Page page);

}
