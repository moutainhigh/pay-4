/**
 *  File: WithdrawReportServiceImpl.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-12-9      Jason_wang      Changes
 *  
 *
 */
package com.pay.fundout.withdraw.service.flowprocess.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.pay.fundout.withdraw.common.util.WithDrawConstants;
import com.pay.fundout.withdraw.common.util.WithDrawUtil;
import com.pay.fundout.withdraw.dto.flowprocess.WithdrawAuditDTO;
import com.pay.fundout.withdraw.dto.flowprocess.WithdrawReportQueryDTO;
import com.pay.fundout.withdraw.model.flowprocess.WithdrawQueryOrder;
import com.pay.fundout.withdraw.service.flowprocess.WithdrawReportService;
import com.pay.fundout.withdraw.service.flowprocess.WithdrawService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.inf.dto.CityDTO;
import com.pay.inf.dto.ProvinceDTO;
import com.pay.poss.service.inf.input.BankInfoFacadeService;

/**
 * @author Jason_wang
 * 
 */
public class WithdrawReportServiceImpl implements WithdrawReportService {

	private BaseDAO baseDao;

	private WithdrawService withdrawService;

	private BankInfoFacadeService bankInfoService;

	/**
	 * @param bankInfoService
	 *            the bankInfoService to set
	 */
	public void setBankInfoService(BankInfoFacadeService bankInfoService) {
		this.bankInfoService = bankInfoService;
	}

	/**
	 * @param baseDao
	 *            the baseDao to set
	 */
	public void setBaseDao(BaseDAO<Object> baseDao) {
		this.baseDao = baseDao;
	}

	/**
	 * @param withdrawService
	 *            the withdrawService to set
	 */
	public void setWithdrawService(WithdrawService withdrawService) {
		this.withdrawService = withdrawService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.fundout.withdraw.service.flowprocess.WithdrawReportService#
	 * queryNoDisposeData(com.pay.inf.dao.Page,
	 * com.pay.fundout.withdraw.dto.flowprocess.WithdrawReportQueryDTO)
	 */
	@Override
	public Map<String, Object> queryNoDisposeData(Page<WithdrawAuditDTO> page,
			WithdrawReportQueryDTO queryDto) {
		// 统计信息
		Map<String, Object> resultMap = (Map) baseDao.findObjectByCriteria(
				"withdrawexception.queryNoDealWithdrawInfo_SUM", queryDto);

		Page<WithdrawQueryOrder> pageService = new Page<WithdrawQueryOrder>();
		PageUtils.setServicePage(pageService, page);
		pageService = baseDao.findByQuery(
				"withdrawexception.queryNoDealWithdrawInfo", pageService,
				queryDto);
		PageUtils.setServicePage(page, pageService);

		List<WithdrawQueryOrder> resultList = pageService.getResult();
		List<WithdrawAuditDTO> list = transferOrderInfo(resultList);
		page.setResult(list);
		resultMap.put("page", page);
		return resultMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.fundout.withdraw.service.flowprocess.WithdrawReportService#
	 * queryDisposedData(com.pay.inf.dao.Page,
	 * com.pay.fundout.withdraw.dto.flowprocess.WithdrawReportQueryDTO)
	 */
	@Override
	public Map<String, Object> queryDisposedData(Page<WithdrawAuditDTO> page,
			WithdrawReportQueryDTO queryDto) {
		// 统计信息
		Map<String, Object> resultMap = (Map) baseDao.findObjectByCriteria(
				"withdrawexception.queryDealedWithdrawInfo_SUM", queryDto);

		Page<WithdrawQueryOrder> pageService = new Page<WithdrawQueryOrder>();
		PageUtils.setServicePage(pageService, page);
		pageService = baseDao.findByQuery(
				"withdrawexception.queryDealedWithdrawInfo", pageService,
				queryDto);
		PageUtils.setServicePage(page, pageService);

		List<WithdrawQueryOrder> resultList = pageService.getResult();
		List<WithdrawAuditDTO> list = transferOrderInfo(resultList);
		page.setResult(list);
		resultMap.put("page", page);
		return resultMap;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.fundout.withdraw.service.flowprocess.WithdrawReportService#
	 * queryDisposedDataForDownload
	 * (com.pay.fundout.withdraw.dto.flowprocess.WithdrawReportQueryDTO)
	 */
	@Override
	public Map<String, Object> queryDisposedDataForDownload(
			WithdrawReportQueryDTO queryDto) {
		Map<String, Object> resultMap = new HashMap<String, Object>();
		// 统计信息
		// Map<String,Object> resultMap =
		// (Map)baseDao.findObjectByQuery("withdrawexception.queryDealedWithdrawInfo_SUM",queryDto);
		List<WithdrawQueryOrder> orderList = baseDao.findByQuery(
				"withdrawexception.queryDealedWithdrawInfo", queryDto);
		List<WithdrawAuditDTO> list = transferOrderInfoForDownload(orderList);
		resultMap.put("reportList", list);

		return resultMap;
	}

	/**
	 * <p>
	 * 为工单跟订单表在页面显示将两表合成一张表的转换操作
	 * </p>
	 * 
	 * @Auther Jonathen Ni
	 * @since 2010-09-20
	 */
	private List<WithdrawAuditDTO> transferOrderInfo(
			List<WithdrawQueryOrder> resultList) {
		List<WithdrawAuditDTO> list = new ArrayList<WithdrawAuditDTO>();
		if (resultList != null) {
			WithdrawAuditDTO dto = null;
			WithdrawQueryOrder order = null;
			List<Map<String, String>> logList = null;
			CityDTO cityDto = null;
			ProvinceDTO privinceDto = null;

			for (int i = 0; i < resultList.size(); i++) {
				dto = new WithdrawAuditDTO();
				order = resultList.get(i);
				BeanUtils.copyProperties(order.getOrderDto(), dto);
				BeanUtils.copyProperties(order.getWorkOrderDto(), dto);
				// 由于工单表中增加了createDate跟updateDate字段，在拼装WithdrawAuditDTO时候要单独把订单的创建时间取出来
				dto.setCreateTime(order.getOrderDto().getCreateTime());

				privinceDto = withdrawService
						.getProvince(dto.getBankProvince() == null ? null
								: Integer.valueOf(dto.getBankProvince()
										.intValue()));
				cityDto = withdrawService
						.getCity(dto.getBankCity() == null ? null : Integer
								.valueOf(dto.getBankCity().intValue()));
				dto.setBankCityStr(cityDto == null ? "" : cityDto.getCityname());
				dto.setBankProvinceStr(privinceDto == null ? "" : privinceDto
						.getProvincename());
				// 获取审核与复核员信息
				logList = baseDao.findByQuery(
						"withdrawexception.queryAuditAndReAuditUserInfo", order
								.getWorkOrderDto().getWorkOrderky());
				for (Map<String, String> map : logList) {
					if (WithDrawConstants.AUDIT_NODE.equals(map.get("NODE"))) {
						dto.setAuditUser(map.get("OPERATOR"));
					} else if (WithDrawConstants.SECOND_AUDIT_NODE.equals(map
							.get("NODE"))) {
						dto.setReAuditUser(map.get("OPERATOR"));
					}
				}
				list.add(dto);
			}
		}
		return list;
	}

	/**
	 * 转换订单信息
	 * 
	 * @param resultList
	 * @return
	 */
	private List<WithdrawAuditDTO> transferOrderInfoForDownload(
			List<WithdrawQueryOrder> resultList) {
		List<WithdrawAuditDTO> list = new ArrayList<WithdrawAuditDTO>();
		if (resultList != null) {
			WithdrawAuditDTO dto = null;
			WithdrawQueryOrder order = null;
			List<Map<String, String>> logList = null;
			CityDTO cityDto = null;
			ProvinceDTO privinceDto = null;

			for (int i = 0; i < resultList.size(); i++) {
				dto = new WithdrawAuditDTO();
				order = resultList.get(i);
				BeanUtils.copyProperties(order.getOrderDto(), dto);
				BeanUtils.copyProperties(order.getWorkOrderDto(), dto);
				// 由于工单表中增加了createDate跟updateDate字段，在拼装WithdrawAuditDTO时候要单独把订单的创建时间取出来
				dto.setCreateTime(order.getOrderDto().getCreateTime());

				privinceDto = withdrawService
						.getProvince(dto.getBankProvince() == null ? null
								: Integer.valueOf(dto.getBankProvince()
										.intValue()));
				cityDto = withdrawService
						.getCity(dto.getBankCity() == null ? null : Integer
								.valueOf(dto.getBankCity().intValue()));
				dto.setBankCityStr(cityDto == null ? "" : cityDto.getCityname());
				dto.setBankProvinceStr(privinceDto == null ? "" : privinceDto
						.getProvincename());
				dto.setBankKyStr(this.bankInfoService.getBankNameById(dto.getBankKy()));
				dto.setBankAcct(WithDrawUtil.transfBankAcct(dto.getBankAcct()));
				// 获取审核与复核员信息
				logList = baseDao.findByQuery(
						"withdrawexception.queryAuditAndReAuditUserInfo", order
								.getWorkOrderDto().getWorkOrderky());
				for (Map<String, String> map : logList) {
					if (WithDrawConstants.AUDIT_NODE.equals(map.get("NODE"))) {
						dto.setAuditUser(map.get("OPERATOR"));
					} else if (WithDrawConstants.SECOND_AUDIT_NODE.equals(map
							.get("NODE"))) {
						dto.setReAuditUser(map.get("OPERATOR"));
					}
				}
				list.add(dto);
			}
		}
		return list;
	}
}
