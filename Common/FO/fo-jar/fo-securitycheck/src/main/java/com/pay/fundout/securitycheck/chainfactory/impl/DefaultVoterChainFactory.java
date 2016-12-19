package com.pay.fundout.securitycheck.chainfactory.impl;

import java.util.List;

import com.pay.fundout.securitycheck.chain.AbstractVoterChain;
import com.pay.fundout.securitycheck.chain.impl.DefaultVoterChain;
import com.pay.fundout.securitycheck.chainfactory.AbstractVoterChainFactory;
import com.pay.fundout.securitycheck.voter.Voter;
import com.pay.poss.base.env.context.ContextService;
import com.pay.poss.base.env.data.IDataService;
import com.pay.poss.base.model.CodeMapping;

public class DefaultVoterChainFactory extends AbstractVoterChainFactory {
	protected IDataService dataService;
	private String prefixBeanName = "fundout-securitycheck-";

	@Override
	public void afterPropertiesSet() throws Exception {
		List<CodeMapping> chains = dataService.getCodeMapping("VOTER", "CHAIN");
		long start = System.currentTimeMillis();
		for (CodeMapping codeMapping : chains) {
			String busiType = codeMapping.getCode();
			AbstractVoterChain chain = new DefaultVoterChain();
			cacheVoterChainIfNecessary(busiType, chain);

			// 初始化投票器
			initVoter4Chain(busiType, chain, codeMapping.getValue());
			
			// 注册投票器链支持业务类型
			chain.registBusiType(busiType);
		}
		if (logger.isInfoEnabled()) {
			logger.info("初始化投票链工厂完成  用时[" + (System.currentTimeMillis() - start) / 1000.00 + "]");
		}
	}

	private void initVoter4Chain(String busiType, AbstractVoterChain chain, String voterList) {
		long start = System.currentTimeMillis();
		String[] voters = voterList.split("[,]");

		StringBuffer configChainInfo = new StringBuffer("CONFIG CHAIN[").append(busiType).append("],VOTER[ ");
		for (int i = 0; i < voters.length; i++) {
			String shortBeanName = voters[i];
			String fullBeanName = "fundout-securitycheck-" + shortBeanName.trim();
			configChainInfo.append(fullBeanName).append(",");

			Object voterObj = ContextService.getBean(fullBeanName);
			if (voterObj == null) {
				logger.warn("不存在指定业务类型的投票器,系统忽略加载该投票器 [busiType=" + busiType + ",beanName=" + fullBeanName + "]");
				continue;
			}
			if ((voterObj instanceof Voter) == false) {
				logger.warn("非有效数据类型的投票链实现,系统忽略加载该投票链 [must implements Voter.busiType=" + busiType + ",beanName=" + fullBeanName + "]");
				continue;
			}

			Voter voter = (Voter) voterObj;
			chain.addNextVoter(voter);
		}
		configChainInfo.deleteCharAt(configChainInfo.length() - 1);
		configChainInfo.append("]");
		if (logger.isInfoEnabled()) {
			logger.info(configChainInfo.toString());
			logger.info("初始化投票链完成  用时[" + (System.currentTimeMillis() - start) / 1000.00 + "]");
		}
	}

	public void setDataService(IDataService dataService) {
		this.dataService = dataService;
	}

}
