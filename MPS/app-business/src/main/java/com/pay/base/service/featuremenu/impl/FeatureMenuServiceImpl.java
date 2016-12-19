package com.pay.base.service.featuremenu.impl;

import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.base.dao.featuremenu.FeatureMenuDAO;
import com.pay.base.dto.FeatureMenuDto;
import com.pay.base.dto.MenuDto;
import com.pay.base.service.featuremenu.FeatureMenuService;
import com.pay.inf.dao.BaseDAO;

public class FeatureMenuServiceImpl implements FeatureMenuService {

	private Log logger = LogFactory.getLog(FeatureMenuServiceImpl.class);

	private BaseDAO mainDao;

	public void setMainDao(BaseDAO mainDao) {
		this.mainDao = mainDao;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.base.service.featuremenu.impl.FeatureMenuService#getDtoClass()
	 */
	@Override
	public Class getDtoClass() {
		return MenuDto.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.base.service.featuremenu.impl.FeatureMenuService#createMenu(com
	 * .pay.base.dto.MenuDto)
	 */
	public Long createMenu(MenuDto menuDto) {
		return ((FeatureMenuDAO) mainDao).createMenu(menuDto);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.base.service.featuremenu.impl.FeatureMenuService#updateMenu(com
	 * .pay.base.dto.MenuDto)
	 */
	public boolean updateMenu(MenuDto menuDto) {
		return ((FeatureMenuDAO) mainDao).updateMenu(menuDto);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.base.service.featuremenu.impl.FeatureMenuService#updateMenuStatus
	 * (int, java.lang.Long)
	 */
	public boolean updateMenuStatus(final int status, Long menuId) {
		return ((FeatureMenuDAO) mainDao).updateMenuStatus(status, menuId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.base.service.featuremenu.impl.FeatureMenuService#queryMenuByParentId
	 * (java.lang.Long)
	 */
	public List<MenuDto> queryMenuByParentId(Long parentId, Integer type) {

		FeatureMenuDAO fAO = (FeatureMenuDAO) mainDao;
		return fAO.getMenuByParentId(parentId, type);
	}

	public List<MenuDto> getMenuByOperator(Long parentId) {

		FeatureMenuDAO fAO = (FeatureMenuDAO) mainDao;
		return fAO.getMenuByOperator(parentId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.base.service.featuremenu.impl.FeatureMenuService#queryMenuByFeature
	 * (java.lang.Long)
	 */
	public List<MenuDto> queryMenuByFeature(Long featureId) {
		return ((FeatureMenuDAO) mainDao).getMenuByFeature(featureId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.base.service.featuremenu.impl.FeatureMenuService#getMenuById(
	 * java.lang.Long)
	 */
	public MenuDto getMenuById(Long menuId) {
		return ((FeatureMenuDAO) mainDao).getMenuById(menuId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.base.service.featuremenu.impl.FeatureMenuService#doAuthorizeRnTx
	 * (java.util.List)
	 */
	public boolean doAuthorizeRnTx(List<FeatureMenuDto> fmList, Long featureId) {
		try {
			if (((FeatureMenuDAO) mainDao)
					.deleteFeatureMenuByFeatureId(featureId)) {
				((FeatureMenuDAO) mainDao).batchCreate(fmList);
				return true;
			}
		} catch (Exception e) {
			logger.error(
					"FeatureMenuServiceImpl doAuthorizeRnTx  throws error :", e);
			return false;
		}

		return false;
	}

	public List<MenuDto> getAllMenu() {
		return ((FeatureMenuDAO) mainDao).getAllMenu();
	}

	public List<MenuDto> queryMenuByIdsArray(String ids) {
		return ((FeatureMenuDAO) mainDao).getMenuByIdsArray(ids);
	}

	public boolean removeMenu(Long menuId) {
		return ((FeatureMenuDAO) mainDao).removeMenu(menuId);
	}

	public void updateSorting(final List<MenuDto> mList) {
		((FeatureMenuDAO) mainDao).batchUpdate(mList);
	}

	@Override
	public List<MenuDto> findMenuByTypesArray(Integer[] types) {
		return ((FeatureMenuDAO) mainDao).findMenuByTypesArray(types);
	}
}
