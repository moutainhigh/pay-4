package com.pay.fundout.withdraw.service.check.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.pay.fundout.withdraw.dao.check.FundoutCheckDao;
import com.pay.fundout.withdraw.dao.fileservice.QueryBatchFileDao;
import com.pay.fundout.withdraw.dto.check.FundoutCheckBatchDto;
import com.pay.fundout.withdraw.dto.check.FundoutCheckDto;
import com.pay.fundout.withdraw.model.result.WithdrawImportFile;
import com.pay.fundout.withdraw.service.check.FundoutCheckService;
import com.pay.inf.dao.Page;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;
import com.pay.poss.base.model.BatchFileInfo;

public class FundoutCheckServiceImpl implements FundoutCheckService{
	protected transient Log log = LogFactory.getLog(getClass());

	private FundoutCheckDao fundoutCheckDao;
	private QueryBatchFileDao queryBatchFileDao;

	public void setFundoutCheckDao(FundoutCheckDao fundoutCheckDao) {
		this.fundoutCheckDao = fundoutCheckDao;
	}

	public void setQueryBatchFileDao(final QueryBatchFileDao queryBatchFileDao) {
		this.queryBatchFileDao = queryBatchFileDao;
	}


	@Override
	public List<FundoutCheckDto> checkFundoutFile(List<FundoutCheckDto> list) {
		List<FundoutCheckDto> fundoutCheckDtos = new ArrayList<FundoutCheckDto>();
		int successCount = 0;
		if (list == null)
			return fundoutCheckDtos;
		String batchNo = list.get(0).getBatchNum();
		String fileKy = list.get(0).getId();//add by davis.guo 借用id来保存，fileKy的值。
		for (FundoutCheckDto fundoutCheckDto : list) {
			FundoutCheckDto fundoutCheck = fundoutCheckDao
					.checkFundoutFile(fundoutCheckDto);
			FundoutCheckDto bankInfo = fundoutCheckDao
					.checkBankInfo(fundoutCheckDto);
			if (fundoutCheck == null || bankInfo == null) { // 未查到数据 复核失败
															// 与数据库里的数据不匹配
				fundoutCheckDto.setStatus("2");
				fundoutCheckDtos.add(fundoutCheckDto);
			} else { // 查到数据 复核成功
				fundoutCheckDto.setStatus("1");
				successCount++;
				fundoutCheckDtos.add(fundoutCheckDto);
			}
		}
		fundoutCheckDao.updateFundoutFileBatch(successCount, batchNo);//更新复核状态
		//保存复核账目
		for (FundoutCheckDto fundoutCheckDto : fundoutCheckDtos) {
			fundoutCheckDto.setSuccessCount(successCount + "");
			fundoutCheckDao.createFundoutCheck(fundoutCheckDto);
		}
		
		// 更新批次文件信息为已复核 add by davis.guo at 2016-08-29 begin
		BatchFileInfo batchFileInfoParam = new BatchFileInfo();
		batchFileInfoParam.setBatchNum(batchNo);
		batchFileInfoParam.setFileKy(Long.valueOf(fileKy));
		//批次文件状态 3(已下载)---->8(已复核)
		batchFileInfoParam.setOldBatchFileStatus(3);
		batchFileInfoParam.setBatchFileStatus(8L);
		batchFileInfoParam.setFoReviewTime(new Date());
		
		boolean intStatusFetch = queryBatchFileDao.update("fundoutStatus.fo_updateBatchFileInfoByFileky",batchFileInfoParam);
		if (!intStatusFetch) {
			log.error("导入银行文件：批次文件状态"
					+ batchFileInfoParam.getOldBatchFileStatus() + "---->" + batchFileInfoParam.getBatchFileStatus()
					+ "失败batchNum:" + batchFileInfoParam.getBatchNum()
					+ " fileKy:" + batchFileInfoParam.getFileKy());
		}
		//add end----------------------------------
		return fundoutCheckDtos;
	}
	
	@Override
	public void createFundoutFileBatch(FundoutCheckBatchDto fundoutCheckBatchDto) {
     fundoutCheckDao.createFundoutFileBatch(fundoutCheckBatchDto);
	}



	@Override
	public List<FundoutCheckBatchDto> queryFundoutCheck(
			FundoutCheckBatchDto batchDto, Page page) {
		return fundoutCheckDao.queryFundoutCheck(batchDto,page);
	}



	@Override
	public List<FundoutCheckDto> queryFundoutCheckDetail(String batchNo) {
		return fundoutCheckDao.queryFundoutCheckDetail(batchNo);
	}
	


}
