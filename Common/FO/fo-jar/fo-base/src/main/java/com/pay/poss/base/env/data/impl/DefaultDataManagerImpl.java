package com.pay.poss.base.env.data.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.poss.base.env.data.AbstractDataManager;
import com.pay.poss.base.exception.PossException;
import com.pay.poss.base.model.CodeMapping;

public class DefaultDataManagerImpl extends AbstractDataManager {

	@Override
	public boolean load() throws PossException {
		// code_mapping
		List<CodeMapping> codeMappings = daoService.findByQuery("base.dataservice.queryCodeMappings", new HashMap());
		for (CodeMapping codeMapping : codeMappings) {
			registData(codeMapping.getIdentity(), codeMapping);
		}

		return false;
	}

	@Override
	public boolean reload(Map<String, String> params, boolean reInstance) {
		try {
			if (params.containsKey("STATUS") == false) {
				params.put("STATUS", "1");
			}
			List<CodeMapping> codeMappings = daoService.findByQuery("base.dataservice.queryCodeMappings", params);
			for (CodeMapping codeMapping : codeMappings) {
				registData(codeMapping.getIdentity(), codeMapping);
			}
		} catch (Exception e) {
			log.error("重新加载数据出现错误 [" + params + "]", e);
			return false;
		}
		return true;
	}
}
