package com.pay.poss.membermanager.service.impl;

import java.util.ArrayList;
import java.util.List;

import com.pay.poss.enterprisemanager.common.Constants;
import com.pay.poss.featuremenu.model.Menu;
import com.pay.poss.membermanager.dao.ProductConfigDao;
import com.pay.poss.membermanager.model.MemberProduct;
import com.pay.poss.membermanager.model.Product;
import com.pay.poss.membermanager.service.ProductConfigService;


/**
 * 
 * @Description 
 * @project 	ma-manager
 * @file 		ProductConfigServiceImpl.java 
 * @note		<br>
 * @develop		JDK1.6 + Eclipse 3.5
 * @version     1.0
 * Date				Author			Changes
 * 2012-5-29			DDR				Create
 */
public class ProductConfigServiceImpl implements ProductConfigService{
	
	private ProductConfigDao productConfigDao;
	public void setProductConfigDao(ProductConfigDao productConfigDao) {
		this.productConfigDao = productConfigDao;
	}
	
	@Override
	public List<List<Menu>> getBaseProductTrees(int type) {
		
		List<Menu> mtitles =  productConfigDao.getBaseMenu(0, type);
		List<List<Menu>> listTrees = new ArrayList<List<Menu>>();
		for(Menu m:mtitles){
			List<Menu> mTreeList  = productConfigDao.getMenuTreeByRootId(m.getMenuId(),true);
			if(mTreeList!=null && !mTreeList.isEmpty()){
				listTrees.add(mTreeList);
			}
		}
		
		return listTrees;
	}

	@Override
	public List<List<Menu>> getExtraProductTrees(int type) {
		
		List<Menu> mtitles =  productConfigDao.getBaseMenu(0, 9);
		List<List<Menu>> listTrees = new ArrayList<List<Menu>>();
		for(Menu m:mtitles){
			List<Menu> mTreeList  = productConfigDao.getMenuTreeByRootId(m.getMenuId(),false);
			if(mTreeList!=null && !mTreeList.isEmpty()){
				listTrees.add(mTreeList);
			}
		}
			
		return listTrees;
	}
	

	@Override
	public List<Product> getDefaultProducts(int type) {
		//查询 产品菜单
		List<Product> defaultProducts = productConfigDao.getProducts(type, true);
		if(! defaultProducts.isEmpty()){
			for(Product pr: defaultProducts){
				pr.setMenuList(productConfigDao.getProductMenuTree(Long.valueOf( pr.getId())));
			}
		}
		return defaultProducts;
	}

	@Override
	public List<Product> getExtraProducts(int type) {
		List<Product> extraProducts = productConfigDao.getProducts(type, null);
		if(! extraProducts.isEmpty()){
			for(Product pr: extraProducts){
				pr.setMenuList(productConfigDao.getProductMenuTree(Long.valueOf( pr.getId())));
			}
		}
		return extraProducts;
	}

	@Override
	public List<Product> getMemberProducts(long memberCode) {
		return productConfigDao.getMemberProducts(memberCode);
	}

	@Override
	public boolean createMemberProductRdTx(long memberCode, String[] productIds) {
		//删除当前产品
		int removeCount = productConfigDao.removeMemberCurProducts(memberCode); 
		for(int i = 0 ;i<productIds.length;i++){
			String productId =  productIds[i];
			MemberProduct mproduct = new MemberProduct();
			mproduct.setProductId(Integer.valueOf(productId));
			mproduct.setMemberCode(Long.valueOf(memberCode));
			mproduct.setStatus(Constants.PRODUCTSTATUS_OPEN);
			this.productConfigDao.createMemberProduct(mproduct);
		}
		return productIds.length>0;
	}

	@Override
	public Product getByProductId(Long id) {
		return productConfigDao.getByProductId(id);
	}
	
	

}
