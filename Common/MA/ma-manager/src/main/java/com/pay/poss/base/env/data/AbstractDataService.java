package com.pay.poss.base.env.data;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.InitializingBean;

public class AbstractDataService implements IDataService, InitializingBean {
	protected List<IDataRepository> dataRepositoryContainer = new ArrayList<IDataRepository>();

	@Override
	public void afterPropertiesSet() throws Exception {
		Collections.sort(dataRepositoryContainer);
	}

	@Override
	public Object getData(String busiCode) {
		for (IDataRepository dataRepository : dataRepositoryContainer) {
			Object result = dataRepository.getService(busiCode);
			if (result != null) {
				return result;
			}
		}
		return null;
	}

}
