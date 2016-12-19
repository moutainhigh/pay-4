package com.pay.txncore.crosspay.service;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.txncore.crosspay.model.PartnerWebsiteConfig;
import com.pay.txncore.crosspay.model.PartnerWebsiteConfigCriteria;

public interface PartnerWebsiteConfigService {

	PartnerWebsiteConfig findById(Long id);

	long createPartnerWebsiteConfig(PartnerWebsiteConfig partnerWebsiteConfig);

	boolean updatePartnerWebsiteConfig(PartnerWebsiteConfig partnerWebsiteConfig);

	boolean deletePartnerWebsiteConfig(Long id);

	List<PartnerWebsiteConfig> findByCriteria(
			PartnerWebsiteConfigCriteria criteria);

	List<PartnerWebsiteConfig> findByCriteria(
			PartnerWebsiteConfigCriteria criteria, Page page);

	public Page<PartnerWebsiteConfig> queryPartnerWebsiteConfigForPage(
			PartnerWebsiteConfigCriteria partnerWebsiteConfigCriteria,
			Page<PartnerWebsiteConfig> origPage);

	/**
	 * 判断该网站是否合法www.xxx.com
	 * 
	 * @param webSiteStr
	 * @return
	 */
	public boolean checkPartnerWebSite(String webSiteStr);

	/**
	 * 判断该商户ID是否存在该网址的方法
	 * 
	 * @param orgCode
	 * @param webSiteStr
	 * @return
	 */
	public boolean checkPartnerWebSiteExits(String partnerId, String webSiteStr);

	/**
	 * 根据网站编号以及状态修改该记录的状态为审核状态
	 * 
	 * @param orgCode
	 * @param webSiteStr
	 * @return
	 */
	public boolean updatePartnerWebSiteStatusById(String status, Long siteId);

	/**
	 * 后台网站审核
	 * 
	 * @param webSite
	 * @return
	 */
	boolean updateSiteSetStatus(PartnerWebsiteConfig webSite);

	List<PartnerWebsiteConfig> findWebsiteConfig(
			PartnerWebsiteConfig partnerWebsiteConfig, Page page);

	List<PartnerWebsiteConfig> findWebsiteConfigs(
			List<Map> partnerWebsiteConfigs, Page page);

	void createPartnerWebsiteConfig(List<Map> partnerWebsiteConfigs);
	/***
	 * 通过一个或多个状态查询域名的数量 
	 * @param paraMap
	 * @return
	 */
	String countWebsiteByStatus(Map<String, Object> paraMap);

}