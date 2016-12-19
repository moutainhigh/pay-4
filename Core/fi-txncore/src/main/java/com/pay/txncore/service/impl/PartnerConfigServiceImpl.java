/**
 * 
 */
package com.pay.txncore.service.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fi.exception.BusinessException;
import com.pay.txncore.dao.PartnerConfigDAO;
import com.pay.txncore.model.PartnerConfig;
import com.pay.txncore.service.PartnerConfigService;

/**
 * @author chaoyue
 *
 */
public class PartnerConfigServiceImpl implements PartnerConfigService {

	private Log logger = LogFactory.getLog(getClass());

	private PartnerConfigDAO partnerConfigDAO;

	/**
	 * 网关公钥
	 */
	private static final String PARTNER_CONFIG_CODE1 = "code1";
	/**
	 * 同步通知
	 */
	private static final String PARTNER_CONFIG_CODE12 = "code12";
	/**
	 * 异步通知
	 */
	private static final String PARTNER_CONFIG_CODE13 = "code13";
	/**
	 * 直连权限
	 */
	private static final String PARTNER_CONFIG_CODE8 = "code8";

	public void setPartnerConfigDAO(PartnerConfigDAO partnerConfigDAO) {
		this.partnerConfigDAO = partnerConfigDAO;
	}

	@Override
	public void delete(Long id) {
		// TODO Auto-generated method stub
		partnerConfigDAO.delete(id);
	}

	@Override
	public PartnerConfig findConfig(Long partnerID, String paramCode) {
		// TODO Auto-generated method stub
		return partnerConfigDAO.findConfig(partnerID, paramCode);
	}

	@Override
	public boolean createPartnerConfigRnTx(String memberCode,
			String publicKeyValue) throws BusinessException {
		PartnerConfig partnerConfig = null;
		boolean isSuccessful = false;
		if (null != memberCode && null != publicKeyValue) {
			// 查看商户是否下载过密钥
			PartnerConfig checkartnerConfig = partnerConfigDAO.findConfig(
					Long.valueOf(memberCode), PARTNER_CONFIG_CODE1);
			if (null != checkartnerConfig) {
				logger.info("商户下载过密钥，开始更新密钥");
				partnerConfig = new PartnerConfig();
				partnerConfig.setPartnerConfigNo(checkartnerConfig
						.getPartnerConfigNo());
				partnerConfig.setValue(publicKeyValue);
				isSuccessful = partnerConfigDAO.update(partnerConfig);
				logger.info("网关公钥更新完成。");
			} else {
				logger.info("商户第一次下载过密钥，开始插入密钥");
				logger.info("网关公钥");
				partnerConfig = new PartnerConfig();
				partnerConfig.setDescription("网关密钥");
				partnerConfig.setMemberCode(Long.valueOf(memberCode));
				partnerConfig.setValue(publicKeyValue);
				partnerConfig.setParamCode(PARTNER_CONFIG_CODE1);
				partnerConfig.setRemark("网关密钥");
				long id1 = (Long) partnerConfigDAO.create(partnerConfig);
				logger.info("添加同步通知参数");
				partnerConfig = new PartnerConfig();
				partnerConfig.setDescription("同步通知是否带参数");
				partnerConfig.setMemberCode(Long.valueOf(memberCode));
				partnerConfig.setValue("1");
				partnerConfig.setParamCode(PARTNER_CONFIG_CODE12);
				partnerConfig.setRemark("同步通知是否带参数");
				long id2 = (Long) partnerConfigDAO.create(partnerConfig);
				logger.info("添加异步通知参数");
				partnerConfig = new PartnerConfig();
				partnerConfig.setDescription("异步通知是否带参数");
				partnerConfig.setMemberCode(Long.valueOf(memberCode));
				partnerConfig.setValue("1");
				partnerConfig.setParamCode(PARTNER_CONFIG_CODE13);
				partnerConfig.setRemark("异步通知是否带参数");
				long id3 = (Long) partnerConfigDAO.create(partnerConfig);
				logger.info("添加直连参数");
				partnerConfig = new PartnerConfig();
				partnerConfig.setDescription("直连权限");
				partnerConfig.setMemberCode(Long.valueOf(memberCode));
				partnerConfig.setValue("1");
				partnerConfig.setParamCode(PARTNER_CONFIG_CODE8);
				partnerConfig.setRemark("直连权限");
				long id4 = (Long) partnerConfigDAO.create(partnerConfig);
				if (0L != id1 && 0L != id2 && 0L != id3 && 0L != id4) {
					isSuccessful = true;
					logger.info("更新完成");
				}
			}
		}
		return isSuccessful;
	}

}
