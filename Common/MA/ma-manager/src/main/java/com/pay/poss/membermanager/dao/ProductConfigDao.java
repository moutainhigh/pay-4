package com.pay.poss.membermanager.dao;

import java.util.List;

import com.pay.inf.dao.BaseDAO;
import com.pay.poss.featuremenu.model.Menu;
import com.pay.poss.membermanager.model.MemberProduct;
import com.pay.poss.membermanager.model.Product;

/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		ProductConfigDao.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Date				Author			Changes
 * 2012-5-29			DDR				Create
 */
public interface ProductConfigDao extends BaseDAO<Menu> {
	
	/**
	 * 查询基础菜单
	 * @param parentId 父级id号
	 * @param type 类型
	 * 1:ALL，2:企业，3:个人, 4:后台 5业务级 6功能7.个人卖家8.个人产品9企业产品';
	 * @return 菜单
	 */
	
	List<Menu> getBaseMenu(long  parentId,int type);
	/**
	 * 查询菜单全部根据顶端id号
	 * @param rootId
	 * @param isDefault
	 * @return List<Menu> 以树的形式查询
	 */
	public List<Menu> getMenuTreeByRootId(long rootId,boolean isDefault);
	
	
	/**
	 * 根据类型查询所有产品
	 * @param type 2企业 
	 * @param isDefalut true默认产品 false非默认
	 * @return
	 */
	List<Product> getProducts(int type,Boolean isDefalut);
	
	/**
	 * 
	 * @param productId
	 * @return
	 */
	List<Menu> getProductMenuTree(Long productId);
	
	
	/**
	 * 查询用户的当前有效产品 
	 * @param memberCode
	 * @return
	 */
	List<Product> getMemberProducts(long memberCode);	
	
	/**
	 * 
	 * @param memberCode
	 * @return
	 */
	int removeMemberCurProducts(long memberCode);
	
	/**
	 * 创建用户产品
	 * @param pu
	 * @return
	 */
	Long createMemberProduct(MemberProduct pu);
	
	/**
	 * 
	 * @param id
	 * @return
	 */
	Product getByProductId(Long id);
	
	
	
	

}
