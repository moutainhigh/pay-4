package com.pay.poss.base.env.data;

import java.util.Collections;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.InitializingBean;

import com.pay.inf.dao.BaseDAO;

public abstract class AbstractDataManager implements IDataManager,
		InitializingBean {
	
	protected Log log = LogFactory.getLog(getClass());
	protected List<IDataRepository> dataRepositoryContainer;
	protected BaseDAO daoService;

	@Override
	public void afterPropertiesSet() throws Exception {
		Collections.sort(dataRepositoryContainer);
	}

	protected boolean registData(String busiCode, Object busiObject) {
		for (IDataRepository dataRepository : dataRepositoryContainer) {
			dataRepository.addService(busiCode, busiObject);
		}
		return true;
	}

	public List<IDataRepository> getDataRepositoryContainer() {
		return dataRepositoryContainer;
	}

	public void setDataRepositoryContainer(
			List<IDataRepository> dataRepositoryContainer) {
		this.dataRepositoryContainer = dataRepositoryContainer;
	}

	public void setDaoService(BaseDAO daoService) {
		this.daoService = daoService;
	}

}
