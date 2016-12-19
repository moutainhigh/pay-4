/**
 * 
 */
package com.pay.fi.chain.dao.impl;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.pay.fi.chain.dao.LogoPictureDao;
import com.pay.fi.chain.model.LogoPicture;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author PengJiangbo
 *
 */
@SuppressWarnings("rawtypes")
public class LogoPictureDaoImpl extends BaseDAOImpl implements LogoPictureDao {

	@SuppressWarnings("deprecation")
	@Override
	public Long createlogoPic(LogoPicture logoPic) {
		return (Long) this.getSqlMapClientTemplate().insert(getNamespace().concat("insert"), logoPic);
	}

	@Override
	public void batchUpdateOwner(List<LogoPicture> cpList)
			throws DataAccessException {

	}

	@Override
	public List<LogoPicture> queryPicByParam(LogoPicture logoPic) {
		List<LogoPicture> cpList=this.getSqlMapClientTemplate().queryForList(namespace.concat("selectByCondition"),logoPic);
		return cpList;
	}

	@Override
	public int deletePicByParam(LogoPicture logoPic) {
		return this.getSqlMapClientTemplate().delete(namespace.concat("deleteByCondition"),logoPic);
	}

	@SuppressWarnings({ "deprecation", "unchecked" })
	@Override
	public List<LogoPicture> findPicByMemberCode(Long memberCode) {
		return this.getSqlMapClientTemplate().queryForList(this.getNamespace().concat("findPicByMemberCode"), memberCode);
	}

	@Override
	public int updatePicByParam(LogoPicture logoPic) {
		return this.getSqlMapClientTemplate().update(this.getNamespace().concat("updatePicByParam"), logoPic) ;
	}

}
