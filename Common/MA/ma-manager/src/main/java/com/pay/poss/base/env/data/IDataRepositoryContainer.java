package com.pay.poss.base.env.data;

import java.util.List;

public interface IDataRepositoryContainer {
	public List<IDataRepository> getAllDataRepositorys();
	public void addDataRepository(IDataRepository dataRepository);
}
