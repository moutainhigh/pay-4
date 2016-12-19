package com.pay.fundout.withdraw.dao.check;

import java.util.List;

import com.pay.fundout.withdraw.dto.check.FundoutCheckBatchDto;
import com.pay.fundout.withdraw.dto.check.FundoutCheckDto;
import com.pay.inf.dao.Page;

public interface FundoutCheckDao {

	FundoutCheckDto checkFundoutFile(FundoutCheckDto fundoutCheckDto);

	FundoutCheckDto checkBankInfo(FundoutCheckDto fundoutCheckDto);

	void createFundoutCheck(FundoutCheckDto fundoutCheckDto);

	void createFundoutFileBatch(FundoutCheckBatchDto fundoutCheckBatchDto);

	void updateFundoutFileBatch(int successCount, String batchNo);

	List<FundoutCheckBatchDto> queryFundoutCheck(FundoutCheckBatchDto batchDto, Page page);

	List<FundoutCheckDto> queryFundoutCheckDetail(String batchNo);


}
