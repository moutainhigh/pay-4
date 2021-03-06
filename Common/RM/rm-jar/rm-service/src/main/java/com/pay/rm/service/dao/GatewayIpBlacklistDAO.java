/**
 *
 * auto generated by ibatis tools 
 *
 **/
package com.pay.rm.service.dao;

import java.util.HashMap;
import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.rm.service.model.GatewayIpBlacklist;

public interface GatewayIpBlacklistDAO extends BaseDAO<GatewayIpBlacklist> {

	public List<GatewayIpBlacklist> queryIpBlackList(
			HashMap<String, String> params, int pageSize, int pageNo);

	public List<GatewayIpBlacklist> queryIpBlackList(
			HashMap<String, String> params);

	public int coutIpBlackList(HashMap<String, String> params);

	public boolean isExistIpBlack(String ipaddr, String status);

	public boolean updateStatus(String ipBlackNo, String status);

}