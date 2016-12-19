package com.pay.pe.report.service.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.pe.report.dao.QueryReportDao;
import com.pay.pe.report.dto.PerformanceReportDTO;
import com.pay.pe.report.dto.QueryBankKeyDTO;
import com.pay.pe.report.dto.QueryRequstParameter;
import com.pay.pe.report.dto.QueryResponseDTO;
import com.pay.pe.report.dto.SumaryRepDTO;
import com.pay.pe.report.dto.SumaryResuleDTO;
import com.pay.pe.report.service.QueryReportServicre;

public class QueryReportServiceImpl implements QueryReportServicre {
	
	private QueryReportDao queryReportDao;

	/**
	 * 查询所有符合条件的数据
	 * @param parameter
	 * @return
	 */
	@Override
	public List<QueryResponseDTO> getAllQueryReport(
			QueryRequstParameter parameter) {
		if(parameter == null || parameter.getBusinessType() == null){
			return null;
		}
		if("1".equalsIgnoreCase(parameter.getBusinessType())){
			return queryReportDao.queryReportForFundOut(parameter);
		}else if("2".equalsIgnoreCase(parameter.getBusinessType())){
			return queryReportDao.queryReportForReFundOut(parameter);
		}else if("3".equalsIgnoreCase(parameter.getBusinessType())){
			return queryReportDao.queryReportForFundIn(parameter);
		}else if("4".equalsIgnoreCase(parameter.getBusinessType())){
			return queryReportDao.queryReportForReFundIn(parameter);
		}
		return null;
	}
	
	/**
	 * 查询所有符合条件数据 并带分页信息
	 * @param parameter
	 * @param page
	 * @return
	 */
	@Override
	public Page<QueryResponseDTO> getAllQueryReport(
			QueryRequstParameter parameter, Page<QueryResponseDTO> page) {
		if(parameter == null || parameter.getBusinessType() == null){
			return null;
		}
		if("1".equalsIgnoreCase(parameter.getBusinessType())){
			return queryReportDao.queryReportFundOut(parameter, page);
		}else if("2".equalsIgnoreCase(parameter.getBusinessType())){
			return queryReportDao.queryReportReFundOut(parameter, page);
		}else if("3".equalsIgnoreCase(parameter.getBusinessType())){
			return queryReportDao.queryReportFundIn(parameter, page);
		}else if("4".equalsIgnoreCase(parameter.getBusinessType())){
			return queryReportDao.queryReportReFundIn(parameter, page);
		}
		return null;
	}

	public void setQueryReportDao(QueryReportDao queryReportDao) {
		this.queryReportDao = queryReportDao;
	}

	@Override
	public List<QueryBankKeyDTO> getFundOutChennalBank() {
		return queryReportDao.getFundOutChannelBank();
	}

	@Override
	public List<QueryBankKeyDTO> getFundInChennalBank() {
		return queryReportDao.getFundInChannelBank();
	}

	@Override
	public List<SumaryResuleDTO> getSumaryReportInfo(Map<String, Object> map) {
				
		List<SumaryResuleDTO> retList = new ArrayList<SumaryResuleDTO>();
		
		List<SumaryRepDTO> payList = queryReportDao.getSumaryPayInfo(map);
		List<SumaryRepDTO> desList = queryReportDao.getSumaryDepositInfo(map);
		
		List<SumaryRepDTO> payToAccList = queryReportDao.getSumaryPayToAccInfo(map);
		List<SumaryRepDTO> payToBankList = queryReportDao.getSumaryPayToBankInfo(map);
		List<SumaryRepDTO> withDrowList = queryReportDao.getSumaryWithDrowInfo(map);
		List<SumaryRepDTO> refuncList = queryReportDao.getSumaryrefundInfo(map);
		
		List<SumaryRepDTO> creditQuickPayList = queryReportDao.getCreditQuickPayInfo(map);
		List<SumaryRepDTO> debitQuickPayList = queryReportDao.getDebitQuickPayInfo(map);
		
		//网关交易
		if(payList != null && payList.size() > 0 ){
			for(SumaryRepDTO pay : payList){
				SumaryResuleDTO sum = new SumaryResuleDTO();
				sum.setCreateDate(pay.getCreateDate());
				sum.setPayAmount(pay.getAmount());
				sum.setPayCount(pay.getCount());
				retList.add(sum);
			}
		}
		//充值
		if(desList != null && desList.size() >0) {
			for(SumaryRepDTO des : desList){
				SumaryResuleDTO sum = existInList(des.getCreateDate(),retList);
				if(sum == null){
					sum = new SumaryResuleDTO();
					sum.setCreateDate(des.getCreateDate());
					sum.setDepositAmount(des.getAmount());
					sum.setDepositCount(des.getCount());
					retList.add(sum);
				}else{
					sum.setDepositAmount(des.getAmount());
					sum.setDepositCount(des.getCount());
				}
			}
		}
		//付款到帐户
		if(payToAccList != null && payToAccList.size() >0) {
			for(SumaryRepDTO acc : payToAccList){
				SumaryResuleDTO sum = existInList(acc.getCreateDate(),retList);
				if(sum == null){
					sum = new SumaryResuleDTO();
					sum.setCreateDate(acc.getCreateDate());
					sum.setPay2AccAmount(acc.getAmount());
					sum.setPay2AccCount(acc.getCount());
					retList.add(sum);
				}else{
					sum.setPay2AccAmount(acc.getAmount());
					sum.setPay2AccCount(acc.getCount());
				}
			}
		}
		//付款到银行
		if(payToBankList != null && payToBankList.size() >0) {
			for(SumaryRepDTO acc : payToBankList){
				SumaryResuleDTO sum = existInList(acc.getCreateDate(),retList);
				if(sum == null){
					sum = new SumaryResuleDTO();
					sum.setCreateDate(acc.getCreateDate());
					sum.setPay2BankAmount(acc.getAmount());
					sum.setPay2BankCount(acc.getCount());
					retList.add(sum);
				}else{
					sum.setPay2BankAmount(acc.getAmount());
					sum.setPay2BankCount(acc.getCount());
				}
			}
		}
		//提现
		if(withDrowList != null && withDrowList.size() >0) {
			for(SumaryRepDTO acc : withDrowList){
				SumaryResuleDTO sum = existInList(acc.getCreateDate(),retList);
				if(sum == null){
					sum = new SumaryResuleDTO();
					sum.setCreateDate(acc.getCreateDate());
					sum.setWithDrowAmount(acc.getAmount());
					sum.setWithDrowCount(acc.getCount());
					retList.add(sum);
				}else{
					sum.setWithDrowAmount(acc.getAmount());
					sum.setWithDrowCount(acc.getCount());
				}
			}
		}
		//充退
		if(refuncList != null && refuncList.size() > 0) {
			for(SumaryRepDTO acc : refuncList){
				SumaryResuleDTO sum = existInList(acc.getCreateDate(),retList);
				if(sum == null){
					sum = new SumaryResuleDTO();
					sum.setCreateDate(acc.getCreateDate());
					sum.setRefundAmount(acc.getAmount());
					sum.setRefundCount(acc.getCount());
					retList.add(sum);
				}else{
					sum.setRefundAmount(acc.getAmount());
					sum.setRefundCount(acc.getCount());
				}
			}
		}
		
		//信用卡快捷支付
		if(creditQuickPayList != null && creditQuickPayList.size() > 0) {
			for(SumaryRepDTO cqp : creditQuickPayList){
				SumaryResuleDTO sum = existInList(cqp.getCreateDate(),retList);
				if(sum == null){
					sum = new SumaryResuleDTO();
					sum.setCreateDate(cqp.getCreateDate());
					sum.setCreditQuickPayAmount(cqp.getAmount());
					sum.setCreditQuickPayCount(cqp.getCount());
					retList.add(sum);
				}else{
					sum.setCreditQuickPayAmount(cqp.getAmount());
					sum.setCreditQuickPayCount(cqp.getCount());
				}
			}
		}
		//借记卡快捷支付
		if(debitQuickPayList != null && debitQuickPayList.size() > 0) {
			for(SumaryRepDTO dqp : debitQuickPayList){
				SumaryResuleDTO sum = existInList(dqp.getCreateDate(),retList);
				if(sum == null){
					sum = new SumaryResuleDTO();
					sum.setCreateDate(dqp.getCreateDate());
					sum.setDebitQuickPayAmount(dqp.getAmount());
					sum.setDebitQuickPayCount(dqp.getCount());
					retList.add(sum);
				}else{
					sum.setDebitQuickPayAmount(dqp.getAmount());
					sum.setDebitQuickPayCount(dqp.getCount());
				}
			}
		}
		return retList;
	}
	
	@Override
	public List<PerformanceReportDTO> getPerformanceReport(String accTime) 
	{
		return this.queryReportDao.getPerformanceReport(accTime);
	}
	
	
	private SumaryResuleDTO existInList(String createDate,List<SumaryResuleDTO> list){
		if(list != null && list.size() >0 ){
			for(SumaryResuleDTO dto :list){
				if(createDate.equalsIgnoreCase(dto.getCreateDate())){
					return dto;
				}
			}
		}
		return null;
	}
}
