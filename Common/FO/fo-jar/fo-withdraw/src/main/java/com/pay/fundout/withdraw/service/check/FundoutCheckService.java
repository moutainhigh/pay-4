package com.pay.fundout.withdraw.service.check;

import java.util.List;

import com.pay.fundout.withdraw.dto.check.FundoutCheckBatchDto;
import com.pay.fundout.withdraw.dto.check.FundoutCheckDto;
import com.pay.inf.dao.Page;

public interface FundoutCheckService {

	List<FundoutCheckDto> checkFundoutFile(List<FundoutCheckDto> list);

	void createFundoutFileBatch(FundoutCheckBatchDto fundoutCheckBatchDto);
	
	List<FundoutCheckBatchDto> queryFundoutCheck(FundoutCheckBatchDto batchDto,
			Page page);

	List<FundoutCheckDto> queryFundoutCheckDetail(String batchNo);

}
