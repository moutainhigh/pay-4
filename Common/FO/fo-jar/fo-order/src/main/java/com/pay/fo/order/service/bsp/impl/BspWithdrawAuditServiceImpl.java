package com.pay.fo.order.service.bsp.impl;

import java.math.BigDecimal;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fo.order.dao.bsp.BspWithdrawAuditDao;
import com.pay.fo.order.dto.bsp.BspWithdrawAuditResultDTO;
import com.pay.fo.order.service.bsp.BspWithdrawAuditService;
import com.pay.inf.dao.Page;
import com.pay.util.DateUtil;

/**
 * 出金审核
 * <p>
 * </p>
 * 
 * @author wucan
 * @since 2011-6-30
 * @see
 */
public class BspWithdrawAuditServiceImpl implements BspWithdrawAuditService {

	private Log logger = LogFactory.getLog(BspWithdrawAuditServiceImpl.class);
	private BspWithdrawAuditDao bspwithdrawauditdao;

	// set注入
	public void setBspwithdrawauditdao(BspWithdrawAuditDao bspwithdrawauditdao) {
		this.bspwithdrawauditdao = bspwithdrawauditdao;
	}


	@Override
	public Page<Map<String, Object>> queryResultList(Map<String, Object> map,
			Integer pageNo, Integer pageSize) {
		return bspwithdrawauditdao.queryResultList(map, pageNo, pageSize);
	}

	@Override
	public BspWithdrawAuditResultDTO view(Map<String, Object> map) {
		BspWithdrawAuditResultDTO resultDTO = new BspWithdrawAuditResultDTO();
		Map<String, Object> resultMap = bspwithdrawauditdao.view(map);
		resultDTO.setOrderSeq(String.valueOf(resultMap.get("ORDERSEQ")));
		resultDTO.setOrderType(String.valueOf(resultMap.get("ORDERTYPE")));
		resultDTO.setCreateDate(DateUtil.parse("yyyy-MM-dd", String
				.valueOf(resultMap.get("CREATEDATE"))));
		resultDTO.setMemberName(String.valueOf(resultMap.get("MEMBERNAME")));
		resultDTO.setAmount(new BigDecimal(String.valueOf(resultMap
				.get("AMOUNT"))));
		return resultDTO;
	}


}
