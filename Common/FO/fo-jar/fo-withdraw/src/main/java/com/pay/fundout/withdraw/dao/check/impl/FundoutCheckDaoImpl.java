package com.pay.fundout.withdraw.dao.check.impl;

import java.util.ArrayList;
import java.util.List;

import com.pay.fundout.withdraw.dao.check.FundoutCheckDao;
import com.pay.fundout.withdraw.dto.check.FundoutCheckBatchDto;
import com.pay.fundout.withdraw.dto.check.FundoutCheckDto;
import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;

public class FundoutCheckDaoImpl  extends BaseDAOImpl implements FundoutCheckDao {

	@Override
	public FundoutCheckDto checkFundoutFile(FundoutCheckDto fundoutCheckDto) {
		  List<FundoutCheckDto> fundoutChecks= getSqlMapClientTemplate().queryForList(
					this.namespace.concat("checkFundoutFile"), fundoutCheckDto);
		  return fundoutChecks.isEmpty()?null:fundoutChecks.get(0);
	}

	@Override
	public FundoutCheckDto checkBankInfo(FundoutCheckDto fundoutCheckDto) {
		  List<FundoutCheckDto> fundoutChecks= getSqlMapClientTemplate().queryForList(
					this.namespace.concat("checkBankInfo"), fundoutCheckDto);
		  return fundoutChecks.isEmpty()?null:fundoutChecks.get(0);
	}

	@Override
	public void createFundoutCheck(FundoutCheckDto fundoutCheckDto) {
		this.create("createFundoutCheck", fundoutCheckDto);
	}

	@Override
	public void createFundoutFileBatch(FundoutCheckBatchDto createFundoutFileBatch) {
		this.create("createFundoutFileBatch", createFundoutFileBatch);
	}

	@Override
	public void updateFundoutFileBatch(int successCount, String batchNo) {
		FundoutCheckBatchDto pojo=new FundoutCheckBatchDto();
		pojo.setSuccessCount(successCount+"");
		pojo.setBatchNo(batchNo);
		this.update("updateFundoutFileBatch", pojo);
	}

	@Override
	public List<FundoutCheckBatchDto> queryFundoutCheck(
			FundoutCheckBatchDto batchDto,Page page) {
		List<FundoutCheckBatchDto> checkBatchDtos=super.findByCriteria(batchDto, page);
		return checkBatchDtos;
	}

	@Override
	public List<FundoutCheckDto> queryFundoutCheckDetail(String batchNum) {
		List<FundoutCheckDto> fundoutCheckDtos=	super.findByCriteria("queryFundoutCheckDetail",batchNum);
		return fundoutCheckDtos;
	}

}




 