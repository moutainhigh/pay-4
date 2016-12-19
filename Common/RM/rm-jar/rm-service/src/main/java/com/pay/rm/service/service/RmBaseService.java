package com.pay.rm.service.service;

import com.pay.inf.dao.Page;
import com.pay.rm.base.exception.PossException;

/**
 * 风控限额限次BaseService接口 <T,E> T model,E dto
 * 
 * @Description
 * @project rm-service
 * @file RmLimitBaseService.java
 * @note <br>
 * @develop JDK1.6 + Eclipse 3.5
 * @version 1.0 Copyright 2006-2010 pay Corporation. All rights reserved.
 */
public interface RmBaseService<T, E> {

	public Long save(E dto) throws PossException;

	public boolean delete(long id) throws PossException;

	public boolean update(E dto) throws PossException;

	public T get(long id);

	public abstract Page<E> search(Page<E> page, Object... params);
}
