package com.pay.txncore.crosspay.service.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;

import com.pay.fi.commons.PartnerWebsiteConfigStatusEnum;
import com.pay.fi.util.Constants;
import com.pay.inf.dao.BaseDAO;
import com.pay.inf.dao.Page;
import com.pay.txncore.crosspay.model.PartnerWebsiteConfig;
import com.pay.txncore.crosspay.model.PartnerWebsiteConfigCriteria;
import com.pay.txncore.crosspay.service.PartnerWebsiteConfigService;

public class PartnerWebsiteConfigServiceImpl implements
		PartnerWebsiteConfigService {

	private BaseDAO<PartnerWebsiteConfig> partnerWebsiteConfigDao;

	public void setPartnerWebsiteConfigDao(
			BaseDAO<PartnerWebsiteConfig> partnerWebsiteConfigDao) {
		this.partnerWebsiteConfigDao = partnerWebsiteConfigDao;
	}

	@Override
	public PartnerWebsiteConfig findById(Long id) {
		return partnerWebsiteConfigDao.findById(id);
	}

	@Override
	public List<PartnerWebsiteConfig> findByCriteria(
			PartnerWebsiteConfigCriteria criteria) {
		return partnerWebsiteConfigDao.findByCriteria(criteria);
	}

	@Override
	public long createPartnerWebsiteConfig(
			PartnerWebsiteConfig partnerWebsiteConfig) {
		// 待审核状态
		partnerWebsiteConfig.setStatus(PartnerWebsiteConfigStatusEnum.CHECKING
				.getCode());
		partnerWebsiteConfig.setCreateDate(new Date());

		return (Long) partnerWebsiteConfigDao.create(partnerWebsiteConfig);
	}

	@Override
	public boolean updatePartnerWebsiteConfig(
			PartnerWebsiteConfig partnerWebsiteConfig) {
		return partnerWebsiteConfigDao.update(partnerWebsiteConfig);
	}

	@Override
	public boolean deletePartnerWebsiteConfig(Long id) {
		return partnerWebsiteConfigDao.delete(id);
	}

	@Override
	public Page<PartnerWebsiteConfig> queryPartnerWebsiteConfigForPage(
			PartnerWebsiteConfigCriteria partnerWebsiteConfigCriteria,
			Page<PartnerWebsiteConfig> origPage) {
		// 转换成查询page对象
		List<PartnerWebsiteConfig> resultList = partnerWebsiteConfigDao
				.findByCriteria(partnerWebsiteConfigCriteria, origPage);
		// 转换成页面对象
		origPage.setResult(resultList);
		return origPage;
	}

	@Override
	public boolean checkPartnerWebSite(String webSiteStr) {
		if (StringUtils.isEmpty(webSiteStr)) {
			return false;
		}

		Pattern pWww = Pattern.compile(Constants.REGWWW);
		Matcher mWww = pWww.matcher(webSiteStr);
		if (mWww.matches()) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean checkPartnerWebSiteExits(String partnerId, String webSiteStr) {
		if (StringUtils.isEmpty(webSiteStr) || partnerId == null) {
			return false;
		}

		PartnerWebsiteConfigCriteria partnerWebsiteConfigCriteria = new PartnerWebsiteConfigCriteria();
		partnerWebsiteConfigCriteria.createCriteria()
				.andPartnerIdEqualTo(partnerId).andUrlEqualTo(webSiteStr);

		List<PartnerWebsiteConfig> webSites = partnerWebsiteConfigDao
				.findByCriteria(partnerWebsiteConfigCriteria);
		if (webSites != null && webSites.size() > 0) {
			return true;
		} else {
			return false;
		}
	}

	@Override
	public boolean updatePartnerWebSiteStatusById(String status, Long siteId) {
		PartnerWebsiteConfig siteConfig = partnerWebsiteConfigDao
				.findById(siteId);
		siteConfig.setStatus(status);
		return updatePartnerWebsiteConfig(siteConfig);
	}

	public static void main(String[] args) {
		PartnerWebsiteConfigServiceImpl swcsi = new PartnerWebsiteConfigServiceImpl();
		System.out.println(swcsi.checkPartnerWebSite("www.baidu.com"));
	}

	@Override
	public boolean updateSiteSetStatus(PartnerWebsiteConfig webSite) {
		PartnerWebsiteConfig dbConfig = this.findById(webSite.getId());
		dbConfig.setStatus(webSite.getStatus());
		dbConfig.setRemark(webSite.getRemark());
		return updatePartnerWebsiteConfig(dbConfig);
	}

	@Override
	public List<PartnerWebsiteConfig> findByCriteria(
			PartnerWebsiteConfigCriteria criteria, Page page) {
		return partnerWebsiteConfigDao.findByCriteria(criteria, page);
	}

	@Override
	public List<PartnerWebsiteConfig> findWebsiteConfig(
			PartnerWebsiteConfig partnerWebsiteConfig, Page page) {
		return partnerWebsiteConfigDao.findByCriteria(
				partnerWebsiteConfig, page);
	}

	@Override
	public List<PartnerWebsiteConfig> findWebsiteConfigs(List<Map> websiteConfigs, Page page) {
		List<PartnerWebsiteConfig> partnerWebsiteConfigs=new ArrayList<PartnerWebsiteConfig>();
		for (Map map: websiteConfigs) {
			PartnerWebsiteConfig partnerWebsiteConfig=new PartnerWebsiteConfig();
			String url=(String)map.get("url");
			String partnerId=(String)map.get("partnerId");
			String statusIn = (String)map.get("statusIn") ;
			String urlQueryModel = (String) map.get("urlQueryModel") ;
			
			partnerWebsiteConfig.setUrl(url);
			partnerWebsiteConfig.setPartnerId(partnerId);
			partnerWebsiteConfig.setStatusIn(statusIn);
			partnerWebsiteConfig.setUrlQueryModel(urlQueryModel);
			
			List<PartnerWebsiteConfig> partnerWebsite=partnerWebsiteConfigDao.findByCriteria(
					partnerWebsiteConfig, page);
			if(partnerWebsite!=null && !partnerWebsite.isEmpty()){
				partnerWebsiteConfigs.addAll(partnerWebsite);
			}
		}
		return partnerWebsiteConfigs;
	}

	@Override
	public void createPartnerWebsiteConfig(List<Map> partnerWebsiteConfigs) {
		
		for (Map<String,String> map : partnerWebsiteConfigs) {
			PartnerWebsiteConfig partnerWebsiteConfig = new PartnerWebsiteConfig();
			partnerWebsiteConfig.setCreateDate(new Date());
			partnerWebsiteConfig.setIp(map.get("ip"));
			partnerWebsiteConfig.setRemark(map.get("remark"));
			partnerWebsiteConfig.setStatus("0");
			partnerWebsiteConfig.setUrl(map.get("url"));
			partnerWebsiteConfig.setPartnerId(map.get("partnerId"));
			partnerWebsiteConfig.setOperator(map.get("operator"));
			
			// 待审核状态
			partnerWebsiteConfig.setStatus(PartnerWebsiteConfigStatusEnum.CHECKING
							.getCode());
		    partnerWebsiteConfigDao.create(partnerWebsiteConfig);
		}
	}
	
	/**
	 * 通过一个或多个状态查询域名的数量 
	 */
	@Override
	public String countWebsiteByStatus(Map<String, Object> paraMap) {
		return String.valueOf(partnerWebsiteConfigDao.countByCriteria("countWebsiteByStatus", paraMap));
	}

}