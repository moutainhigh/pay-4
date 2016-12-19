package com.pay.rm.service.service;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.pay.inf.dao.BaseDAO;
import com.pay.rm.base.exception.PossException;
import com.pay.rm.base.exception.enums.ExceptionCodeEnum;
import com.pay.rm.base.util.ReflectionUtils;

/**
 * 领域对象的业务管理类基类.
 * 
 * 提供了领域对象的简单CRUD方法.
 *
 * @param <T> model
 * @param <E> dto
 * 
 * @author calvin volcano.wu
 */
public abstract class RmBaseServiceImpl<T,E> {

	private Log logger = LogFactory.getLog(RmBaseServiceImpl.class);

	protected Class<T> entityClass;

	/**
	 * 通过子类的范型定义取得领域对象类型Class.
	 * 
	 * eg.
	 * public class UserManager extends EntityManager<User, Long>
	 */
	@SuppressWarnings("unchecked")
	public RmBaseServiceImpl() {
		this.entityClass = ReflectionUtils.getSuperClassGenricType(getClass());
	}

	/**
	 * 在子类实现的回调函数,为EntityManager提供默认CRUD操作所需的DAO.
	 */
	protected abstract BaseDAO<T> getEntityDao();

	public Long save(E dto) throws PossException {
		Long id = null;
		try{
			T model = entityClass.newInstance();
			BeanUtils.copyProperties(dto,model);
			id = (Long) getEntityDao().create(model);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new  PossException("",ExceptionCodeEnum.DATA_ACCESS_EXCEPTION,e);
		}
		return id;
	}

	public boolean delete(long id) throws PossException{
		return getEntityDao().delete(id);
	}
	
	public boolean update(E dto) throws PossException{
		Boolean bl = false;
		try{
			T model = entityClass.newInstance();
			BeanUtils.copyProperties(dto,model);
			bl = getEntityDao().update(model);
		}catch (Exception e) {
			logger.error(e.getMessage(), e);
			throw new  PossException("",ExceptionCodeEnum.DATA_ACCESS_EXCEPTION,e);
		}
		return bl;
	}
	
	public T get(long id) {
		return getEntityDao().findById(id);
	}

	//public abstract Page<E> search(Page<E> page, Object... params);
}
