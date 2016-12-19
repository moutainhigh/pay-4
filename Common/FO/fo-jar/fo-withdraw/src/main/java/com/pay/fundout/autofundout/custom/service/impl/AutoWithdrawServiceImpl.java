package com.pay.fundout.autofundout.custom.service.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fundout.autofundout.custom.dao.AutoFundoutConfigDAO;
import com.pay.fundout.autofundout.custom.dao.AutoQuotaConfigDAO;
import com.pay.fundout.autofundout.custom.dao.AutoTimeConfigDAO;
import com.pay.fundout.autofundout.custom.model.AutoFundoutConfig;
import com.pay.fundout.autofundout.custom.model.AutoFundoutResult;
import com.pay.fundout.autofundout.custom.model.AutoQuotaConfig;
import com.pay.fundout.autofundout.custom.model.AutoTimeConfig;
import com.pay.fundout.autofundout.custom.service.AutoWithdrawService;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;

public class AutoWithdrawServiceImpl implements AutoWithdrawService {
	private AutoFundoutConfigDAO autoFunoutConfigDAO;
	private AutoTimeConfigDAO autoTimeConfigDAO;
	private AutoQuotaConfigDAO autoQuotaConfigDAO;
	private Log log = LogFactory.getLog(getClass());

	public void setAutoTimeConfigDAO(AutoTimeConfigDAO autoTimeConfigDAO) {
		this.autoTimeConfigDAO = autoTimeConfigDAO;
	}

	public void setAutoQuotaConfigDAO(AutoQuotaConfigDAO autoQuotaConfigDAO) {
		this.autoQuotaConfigDAO = autoQuotaConfigDAO;
	}

	public void setAutoFunoutConfigDAO(AutoFundoutConfigDAO autoFunoutConfigDAO) {
		this.autoFunoutConfigDAO = autoFunoutConfigDAO;
	}

	public boolean createRdTx(Map<String, Object> config) throws PossException {
		boolean flag = false;
		try {
			AutoFundoutConfig autoFundoutConfig = (AutoFundoutConfig) config.get("autoFunoutConfig");
			if(autoFundoutConfig == null){
				throw new RuntimeException("委托提现配置对象为空");
			}
			long sequenceid = (Long) autoFunoutConfigDAO.create(autoFundoutConfig);
			if (autoFundoutConfig.getAutoType().intValue() == 1) {
				List<AutoTimeConfig> autoTimeConfigs = (List<AutoTimeConfig>) config.get("autoTimeConfig");
				for (AutoTimeConfig autoTimeConfig : autoTimeConfigs) {
					autoTimeConfig.setConfigId(sequenceid);
				}
				autoTimeConfigDAO.create(autoTimeConfigs);
			} else {
				AutoQuotaConfig autoQuotaConfig = (AutoQuotaConfig) config.get("autoQuotaConfig");
				autoQuotaConfig.setConfigId(sequenceid);
				autoQuotaConfigDAO.create(autoQuotaConfig);
			}
			flag = true;
		} catch (Throwable e) {
			log.info("添加委托提现配置发生异常",e);
			throw new PossException("添加委托提现配置失败", ExceptionCodeEnum.UN_KNOWN_EXCEPTION);
		}
		return flag;
	}

	@Override
	public Map<String, Object> existsConfig(Long memberCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		AutoFundoutConfig autoFundoutConfig = autoFunoutConfigDAO.findById(memberCode);
		if (autoFundoutConfig == null || autoFundoutConfig.getSequenceid().longValue() == 0l) {
			return null;
		}
		map.put("autoFundoutConfig", autoFundoutConfig);
		if (autoFundoutConfig.getAutoType().intValue() == 1) {
			List<AutoTimeConfig> autoTimeConfigs = autoTimeConfigDAO.findByConfigId(autoFundoutConfig.getSequenceid());
			map.put("autoTimeConfig", autoTimeConfigs);
		} else {
			AutoQuotaConfig autoQuotaConfig = autoQuotaConfigDAO.findById(autoFundoutConfig.getSequenceid());
			map.put("autoQuotaConfig", autoQuotaConfig);
		}
		return map;
	}

	public boolean updateRdTx(Map<String, Object> config) throws PossException {
		boolean flag = false;
		try {
			AutoFundoutConfig autoFundoutConfig = (AutoFundoutConfig) config.get("autoFunoutConfig");
			Integer autoType = autoFunoutConfigDAO.findType(autoFundoutConfig.getMemberCode());
			autoFunoutConfigDAO.update(autoFundoutConfig);
			if (autoFundoutConfig.getAutoType().equals(autoType)) {
				if (autoFundoutConfig.getAutoType().intValue() == 2) {
					AutoQuotaConfig autoQuotaConfig = (AutoQuotaConfig) config.get("autoQuotaConfig");
					autoQuotaConfigDAO.update(autoQuotaConfig);
				} else {
					List<AutoTimeConfig> autoTimeConfigs = (List<AutoTimeConfig>) config.get("autoTimeConfig");
					for (AutoTimeConfig autoTimeConfig : autoTimeConfigs) {
						autoTimeConfig.setConfigId(autoFundoutConfig.getSequenceid());
					}
					autoTimeConfigDAO.delete(autoFundoutConfig.getSequenceid());
					autoTimeConfigDAO.create(autoTimeConfigs);
				}
			} else {
				if (autoFundoutConfig.getAutoType().intValue() == 2) {
					AutoQuotaConfig autoQuotaConfig = (AutoQuotaConfig) config.get("autoQuotaConfig");
					autoTimeConfigDAO.delete(autoFundoutConfig.getSequenceid());
					autoQuotaConfigDAO.create(autoQuotaConfig);
				} else {
					List<AutoTimeConfig> autoTimeConfigs = (List<AutoTimeConfig>) config.get("autoTimeConfig");
					for (AutoTimeConfig autoTimeConfig : autoTimeConfigs) {
						autoTimeConfig.setConfigId(autoFundoutConfig.getSequenceid());
					}
					autoQuotaConfigDAO.delete(autoFundoutConfig.getSequenceid());
					autoTimeConfigDAO.delete(autoFundoutConfig.getSequenceid());
					autoTimeConfigDAO.create(autoTimeConfigs);
				}

			}
			flag = true;
		}catch(Exception e){
			log.info("更新委托提现配置发生异常",e);
			throw new PossException("更新委托提现配置失败", ExceptionCodeEnum.UN_KNOWN_EXCEPTION);
		}
		return flag;
	}

	@Override
	public boolean disable(Long configId) throws Exception {
		return autoFunoutConfigDAO.disable(configId);
	}

	@Override
	public List<AutoFundoutResult> queryAutoQuotaFundoutResult() {
		return autoFunoutConfigDAO.queryAutoQuotaFundoutResult();
	}

	@Override
	public List<AutoFundoutResult> queryAutoTimeFundoutResult() {
		return autoFunoutConfigDAO.queryAutoTimeFundoutResult();
	}

	@Override
	public List<AutoFundoutResult> queryAutoMoreTimeFundoutResult() {
		return autoFunoutConfigDAO.queryAutoMoreTimeFundoutResult();
	}

}
