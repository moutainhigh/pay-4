/**
 *  <p>File: DepositQueryServiceFacadeImpl.java</p>
 *  <p>Description:</p>
 *  <p>Copyright: © 2004-2013 pay.com . All rights reserved.版权所有</p>
 *	<p>Company: </p>
 *  @author terry_ma
 *  @version 1.0  
 */
package com.pay.poss.service.gateway.impl;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.beans.BeanUtils;

import com.pay.poss.dao.ExternalFacadeDao;
import com.pay.poss.dto.fi.DepositQueryDetalisDTO;
import com.pay.poss.dto.fi.DepositQueryParamDTO;
import com.pay.poss.dto.fi.QueryDepositDetalisDTO;
import com.pay.poss.service.gateway.DepositQueryServiceFacade;

public class DepositQueryServiceFacadeImpl implements DepositQueryServiceFacade {

	private ExternalFacadeDao externalFacadeDao;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.poss.service.gateway.DepositQueryServiceFacade#queryDeposits
	 * (com.pay.poss.dto.fi.DepositQueryParamDTO)
	 */
	@Override
	public List<DepositQueryDetalisDTO> queryDeposits(
			DepositQueryParamDTO depositQueryParamDTO) throws Exception {

		// 拼接查询条件(查询dto转map)
		Map<String, Object> modelParamMap = putParam(depositQueryParamDTO);

		// 查询
		List<QueryDepositDetalisDTO> list = new ArrayList<QueryDepositDetalisDTO>();
		list = externalFacadeDao.queryDepositDetails(modelParamMap,
				depositQueryParamDTO.getPageSize(), depositQueryParamDTO
						.getPageNo());

		// 组装结果
		List<DepositQueryDetalisDTO> listResult = new ArrayList<DepositQueryDetalisDTO>();
		DepositQueryDetalisDTO depositQueryDetalisDTO;
		for (QueryDepositDetalisDTO queryDepositDetalis : list) {
			depositQueryDetalisDTO = new DepositQueryDetalisDTO();
			BeanUtils.copyProperties(queryDepositDetalis,
					depositQueryDetalisDTO);
			listResult.add(depositQueryDetalisDTO);
		}

		return listResult;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.poss.service.gateway.DepositQueryServiceFacade#queryDepositsCount
	 * (com.pay.poss.dto.fi.DepositQueryParamDTO)
	 */
	@Override
	public Integer queryDepositsCount(DepositQueryParamDTO depositQueryParamDTO)
			throws Exception {

		// 拼接查询条件(查询dto转map)
		Map<String, Object> modelParamMap = putParam(depositQueryParamDTO);

		// 查询数据库
		Integer intResult = externalFacadeDao
				.queryDepositDetailsCount(modelParamMap);
		return intResult;
	}

	/**
	 * 得到可用的时间类型
	 * 
	 * @param startTime
	 *            开始时间
	 * @param endTime
	 *            结束时间
	 * @return String[0]开始时间，String[1]结束时间
	 * @throws Exception
	 */
	private String[] parseTime(Date startTime, Date endTime)
			throws ParseException, Exception {
		String[] timeArr = new String[2];
		String sTime = null;
		String eTime = null;

		SimpleDateFormat dateFormat = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		if (null != startTime) {
			startTime = dateFormat.parse(dateFormat.format(startTime)
					.substring(0, 11)
					+ "00:00:00");
			sTime = dateFormat.format(startTime);
		}
		if (null != endTime) {
			endTime = dateFormat.parse(dateFormat.format(endTime).substring(0,
					11)
					+ "23:59:59");
			eTime = dateFormat.format(endTime);
		}
		timeArr[0] = sTime;
		timeArr[1] = eTime;
		return timeArr;
	}

	/**
	 * 拼接查询条件(查询dto转map)
	 * 
	 * @param depositQueryParamDTO条件dto
	 * @return dao可用map
	 */
	private Map<String, Object> putParam(
			DepositQueryParamDTO depositQueryParamDTO) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String[] times = parseTime(depositQueryParamDTO
				.getDepositProtocolStartDate(), depositQueryParamDTO
				.getDepositProtocolEndDate());
		map.put("memberCode", depositQueryParamDTO.getMemberCode());
		map.put("memberType", depositQueryParamDTO.getMemberTypeEnum());
		map.put("depositProtocolId", depositQueryParamDTO
				.getDepositProtocolId());
		map.put("startTime", times[0]);
		map.put("endTime", times[1]);
		map.put("status", depositQueryParamDTO.getDepositStatus());
		map.put("bankOrderId", depositQueryParamDTO.getBankOrderId());
		map.put("bankSerialNo", depositQueryParamDTO.getBankSerialNo());
		map.put("bankChannel", depositQueryParamDTO.getBankChannel());
		return map;
	}

	public void setExternalFacadeDao(ExternalFacadeDao externalFacadeDao) {
		this.externalFacadeDao = externalFacadeDao;
	}

}
