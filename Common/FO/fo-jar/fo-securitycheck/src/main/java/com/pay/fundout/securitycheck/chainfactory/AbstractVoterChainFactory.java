package com.pay.fundout.securitycheck.chainfactory;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

import com.pay.fundout.securitycheck.chain.VoterChain;
import com.pay.fundout.securitycheck.chain.impl.DenyVoterChain;

public abstract class AbstractVoterChainFactory implements VoterChainFactory, InitializingBean {
	protected Log logger = LogFactory.getLog(getClass());
	// 是否采用默认投票链。
	private boolean allowDefaultChain = true;
	// 是否缓存业务对应投票链
	private boolean cacheChain = true;
	protected Map<String, VoterChain> repository = new HashMap<String, VoterChain>();

	@Override
	public final VoterChain fetchVoterChain(String busiType) {
		VoterChain result = repository.get(busiType);
		if (result == null) {
			result = createVoterChainIfNecessary(busiType);
			if (result == null) {
				logger.error("无法创建投票链 [" + busiType + "]");
				if (allowDefaultChain) {
					logger.warn("对于[" + busiType + "],使用默认投票链");
					result = getDefaultVoterChain();
				}
			}
			cacheVoterChainIfNecessary(busiType, result);
		}

		return result;
	}

	protected void cacheVoterChainIfNecessary(String busiType, VoterChain chain) {
		if (cacheChain && chain != null) {
			repository.put(busiType, chain);
		}
	}
	
	protected VoterChain createVoterChainIfNecessary(String busiType) {
		return repository.get(busiType);
	}

	private VoterChain getDefaultVoterChain() {
		return new DenyVoterChain();
	}


	public boolean isAllowDefaultChain() {
		return allowDefaultChain;
	}

	public void setAllowDefaultChain(boolean allowDefaultChain) {
		this.allowDefaultChain = allowDefaultChain;
	}

	public boolean isCacheChain() {
		return cacheChain;
	}

	public void setCacheChain(boolean cacheChain) {
		this.cacheChain = cacheChain;
	}

	public Map<String, VoterChain> getRepository() {
		return repository;
	}

	public void setRepository(Map<String, VoterChain> repository) {
		this.repository = repository;
	}

}
