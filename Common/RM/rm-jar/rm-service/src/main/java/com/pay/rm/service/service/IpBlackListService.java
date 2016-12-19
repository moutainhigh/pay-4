package com.pay.rm.service.service;

import java.util.HashMap;
import java.util.List;

import com.pay.rm.service.model.GatewayIpBlacklist;

public interface IpBlackListService {

	/**
	 * IP黑名单查询
	 * 
	 * @param params
	 * @param pageSize
	 * @param pageNo
	 * @return
	 */
	public List<GatewayIpBlacklist> queryIpBlackList(
			HashMap<String, String> params, int pageSize, int pageNo);

	public List<GatewayIpBlacklist> queryIpBlackList(
			HashMap<String, String> params);

	public int countIpBlackList(HashMap<String, String> params);

	public boolean saveIpBlack(String ipaddr, String desc);

	public boolean editIpBlack(String ipBlackNo, String desc);

	public GatewayIpBlacklist queryGatewayIpBlacklist(String ipBlackNo);

	public boolean isExistIpBlack(String ipaddr);

	public boolean updateStatus(String ipBlackNo, String status);

}
