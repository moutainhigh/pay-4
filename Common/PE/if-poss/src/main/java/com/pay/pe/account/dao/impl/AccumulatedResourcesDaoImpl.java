package com.pay.pe.account.dao.impl;

import com.pay.inf.dao.Page;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.pe.account.dao.AccumulatedResourcesDao;
import com.pay.pe.account.dao.EntryQueryDao;
import com.pay.pe.account.dto.AccumulatedResourcesDTO;

/**
 * ACCUMULATED_RESOURCES 累计资源配制表
 * 
 * @author 戴德荣 2012-02-28
 */
@SuppressWarnings("unchecked")
public class AccumulatedResourcesDaoImpl extends
		BaseDAOImpl<AccumulatedResourcesDTO> implements AccumulatedResourcesDao {

	private EntryQueryDao entryQueryDao;

	public void setEntryQueryDao(EntryQueryDao entryQueryDao) {
		this.entryQueryDao = entryQueryDao;
	}

	@Override
	public int repeatCount(AccumulatedResourcesDTO dto) {

		return (Integer) getSqlMapClientTemplate().queryForObject(
				this.getNamespace().concat("repeat_COUNT"), dto);
	}

	@Override
	public Page<AccumulatedResourcesDTO> search(AccumulatedResourcesDTO dto,
			Page<AccumulatedResourcesDTO> pageParam) {
		return entryQueryDao.findByQuery(
				this.namespace.concat("findBySelective"), pageParam, dto);
	}

}
