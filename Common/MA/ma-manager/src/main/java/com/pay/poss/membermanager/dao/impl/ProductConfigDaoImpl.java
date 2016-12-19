package com.pay.poss.membermanager.dao.impl;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.featuremenu.model.Menu;
import com.pay.poss.membermanager.dao.ProductConfigDao;
import com.pay.poss.membermanager.model.MemberProduct;
import com.pay.poss.membermanager.model.Product;

/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		ProductConfigDaoImpl.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Date				Author			Changes
 * 2012-5-29			DDR				Create
 */
public class ProductConfigDaoImpl extends BaseDAOImpl<Menu>
		implements ProductConfigDao {

	@Override
	public List<Menu> getBaseMenu(long parentId, int type) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentId", parentId);
		map.put("type", type);
		int isDefault = 0;
		if(type==2){
			isDefault = 1;
		}
		map.put("isDefault", isDefault);
		return getSqlMapClientTemplate().queryForList(this.getNamespace().concat("getBaseMenu"),map);
	}

	@Override
	public List<Menu> getMenuTreeByRootId(long rootId,boolean isDefault) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("rootId", rootId);
		map.put("isDefalut", isDefault?0:1);
		return getSqlMapClientTemplate().queryForList(this.getNamespace().concat("getMenuTreeByRootId"),map);
	}



	@Override
	public List<Product> getProducts(int type, Boolean isDefault) {
		Map<String, Object> map = new HashMap<String, Object>();
		if(isDefault!=null){
			map.put("isDefault", isDefault?1:0);
		}
		map.put("type", type);
		return getSqlMapClientTemplate().queryForList(this.getNamespace().concat("getProducts"),map);
	}

	@Override
	public List<Menu> getProductMenuTree(Long productId) {
		
		return getSqlMapClientTemplate().queryForList(this.getNamespace().concat("getProductMenuTree"),productId);
	}

	@Override
	public List<Product> getMemberProducts(long memberCode) {
		return getSqlMapClientTemplate().queryForList(this.getNamespace().concat("getMemberProducts"),memberCode);
	}

	

	@Override
	public int removeMemberCurProducts(long memberCode) {
		 return 
				 getSqlMapClientTemplate().delete(this.getNamespace().concat("removeMemberCurProducts"),memberCode);
		
	}

	@Override
	public Long createMemberProduct(MemberProduct pu) {
		return (Long) getSqlMapClientTemplate().insert(this.getNamespace().concat("createMemberProduc"),pu);
	}

	@Override
	public Product getByProductId(Long id) {
		return (Product) getSqlMapClientTemplate().queryForObject(this.getNamespace().concat("getByProductId"),id);
	}

	

}
