package com.pay.rm.service.service.impl;

import java.util.HashMap;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.rm.service.dao.GatewayIpBlacklistDAO;
import com.pay.rm.service.dao.impl.GatewayIpBlacklistDAOImpl;
import com.pay.rm.service.model.GatewayIpBlacklist;
import com.pay.rm.service.service.IpBlackListService;

public class IpBlackListServiceImpl implements IpBlackListService {

	private GatewayIpBlacklistDAO ipBlackListDAO;
	private Log logger = LogFactory.getLog(IpBlackListServiceImpl.class);

	@Override
	public List<GatewayIpBlacklist> queryIpBlackList(
			HashMap<String, String> params, int pageSize, int pageNo) {
		logger.info("---@poss AntiPhishingServiceImpl.queryIpBlackList IP黑名单查询--- parameter==>"
				+ params);
		return ipBlackListDAO.queryIpBlackList(params, pageSize, pageNo);
	}

	@Override
	public List<GatewayIpBlacklist> queryIpBlackList(
			HashMap<String, String> params) {
		logger.info("---@poss AntiPhishingServiceImpl.queryIpBlackList IP黑名单查询--- parameter==>"
				+ params);
		return ipBlackListDAO.queryIpBlackList(params);
	}

	@Override
	public int countIpBlackList(HashMap<String, String> params) {

		return ipBlackListDAO.coutIpBlackList(params);
	}

	@Override
	public boolean isExistIpBlack(String ipaddr) {
		logger.info("---@poss AntiPhishingServiceImpl.isExistIpBlack IP是否存在--- ipaddr==>"
				+ ipaddr);
		return ipBlackListDAO.isExistIpBlack(ipaddr, "");
	}

	@Override
	public boolean saveIpBlack(String ipaddr, String desc) {
		GatewayIpBlacklist gatewayIp = new GatewayIpBlacklist();
		gatewayIp.setEnableCount(1L);
		gatewayIp.setDisableCount(0L);
		gatewayIp.setIpAddress(ipaddr);
		gatewayIp.setRemark(desc);
		gatewayIp.setStatus("1");
		ipBlackListDAO.create(gatewayIp);
		return true;
	}

	@Override
	public boolean editIpBlack(String ipBlackNo, String desc) {
		GatewayIpBlacklist gatewayIp = new GatewayIpBlacklist();
		gatewayIp.setRemark(desc);
		gatewayIp.setIpBlacklistNo(Long.parseLong(ipBlackNo));
		return ipBlackListDAO.update(gatewayIp);
	}

	@Override
	public GatewayIpBlacklist queryGatewayIpBlacklist(String ipBlackNo) {
		return ipBlackListDAO.findById(Long.parseLong(ipBlackNo));
	}

	@Override
	public boolean updateStatus(String ipBlackNo, String status) {
		return ipBlackListDAO.updateStatus(ipBlackNo, status);
	}

	public void setIpBlackListDAO(GatewayIpBlacklistDAOImpl ipBlackListDAO) {
		this.ipBlackListDAO = ipBlackListDAO;
	}

}
