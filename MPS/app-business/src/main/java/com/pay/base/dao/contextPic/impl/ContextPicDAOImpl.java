package com.pay.base.dao.contextPic.impl;

import java.sql.SQLException;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.pay.base.dao.contextPic.ContextPicDAO;
import com.pay.base.model.ContextPicture;
import com.pay.inf.dao.impl.BaseDAOImpl;

public class ContextPicDAOImpl extends BaseDAOImpl implements ContextPicDAO{
	
	/* (non-Javadoc)
	 * @see com.pay.base.dao.contextPic.impl.ContextPicDAO#createContextPic(com.pay.base.model.ContextPicture)
	 */
	public Long createContextPic(ContextPicture contextPic){
		return (Long) this.getSqlMapClientTemplate().insert(getNamespace().concat("insert"), contextPic);
	}
	
	
	/* (non-Javadoc)
	 * @see com.pay.base.dao.contextPic.impl.ContextPicDAO#batchUpdateOwner(java.util.List)
	 */
	public void batchUpdateOwner(final List<ContextPicture> cpList) throws DataAccessException{
        this.getSqlMapClientTemplate().execute(new SqlMapClientCallback(){ 
        public Object doInSqlMapClient(SqlMapExecutor executor) 
                throws SQLException { 
        executor.startBatch(); 
        int batch = 0; 
        for(ContextPicture tObject:cpList){
            executor.update(namespace.concat("updateOwner"),tObject);
            batch++; 
            if(batch==100){
                executor.executeBatch(); 
                batch = 0; 
            } 
        } 
        executor.executeBatch();
        return "";
        } 
        }); 
    }
	
	/* (non-Javadoc)
	 * @see com.pay.base.dao.contextPic.impl.ContextPicDAO#queryPicByParam(com.pay.base.model.ContextPicture)
	 */
	@SuppressWarnings("unchecked")
	public List<ContextPicture> queryPicByParam(ContextPicture contextPic){
		List<ContextPicture> cpList=this.getSqlMapClientTemplate().queryForList(namespace.concat("selectByCondition"),contextPic);
		return cpList;
	}
	
	
	/* (non-Javadoc)
	 * @see com.pay.base.dao.contextPic.impl.ContextPicDAO#deletePicByParam(com.pay.base.model.ContextPicture)
	 */
	public int deletePicByParam(ContextPicture contextPic){
		return this.getSqlMapClientTemplate().delete(namespace.concat("deleteByCondition"),contextPic);
	}
	
}
