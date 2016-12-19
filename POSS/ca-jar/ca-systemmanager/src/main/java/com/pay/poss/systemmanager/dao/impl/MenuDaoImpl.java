package com.pay.poss.systemmanager.dao.impl;



import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.systemmanager.dao.IMenuDao;
import com.pay.poss.systemmanager.model.ResMenu;



public class MenuDaoImpl extends BaseDAOImpl<ResMenu> implements IMenuDao{
	
	private Log log = LogFactory.getLog(MenuDaoImpl.class);
	public long insertMenu(ResMenu menu){
		log.debug("MenuDaoImpl.insertMenu is running...");
		return (Long) this.getSqlMapClientTemplate().insert(namespace.concat("insertMenu"), menu);						
	}
		
	public boolean dropMenuById(String id){
		log.debug("MenuDaoImpl.dropMenuById is running...");
		return this.delete(Long.parseLong(id));
		
	}

	@Override
	public int queryMaxPositionByPerant(Long parentId) {
		 Object obj =	getSqlMapClientTemplate().queryForObject(namespace.concat("maxPositionByPerant"), parentId);
		 return new Integer(obj.toString());
	}
					
	

	
	
	
	
	
	
}
