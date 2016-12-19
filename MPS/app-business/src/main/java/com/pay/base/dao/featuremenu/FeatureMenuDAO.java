package com.pay.base.dao.featuremenu;

import java.util.List;

import org.springframework.dao.DataAccessException;

import com.pay.base.dto.FeatureMenuDto;
import com.pay.base.dto.MenuDto;

public interface FeatureMenuDAO {

    /* (non-Javadoc)
     * @see com.pay.base.dao.featuremenu.impl.FeatureMenuDAO#getModelClass()
     */
    public abstract Class getModelClass();

    /* (non-Javadoc)
     * @see com.pay.base.dao.featuremenu.impl.FeatureMenuDAO#createMenu(com.pay.base.model.Menu)
     */
    public abstract Long createMenu(MenuDto menuDto);

    /* (non-Javadoc)
     * @see com.pay.base.dao.featuremenu.impl.FeatureMenuDAO#createFeatureMenu(com.pay.base.model.FeatureMenu)
     */
    public abstract Long createFeatureMenu(FeatureMenuDto fmDto);

    /* (non-Javadoc)
     * @see com.pay.base.dao.featuremenu.impl.FeatureMenuDAO#deleteFeatureMenuByFeatureId()
     */
    public abstract boolean deleteFeatureMenuByFeatureId(Long featureId);

    /* (non-Javadoc)
     * @see com.pay.base.dao.featuremenu.impl.FeatureMenuDAO#updateMenu(com.pay.base.model.Menu)
     */
    public abstract boolean updateMenu(MenuDto menuDto);

    /* (non-Javadoc)
     * @see com.pay.base.dao.featuremenu.impl.FeatureMenuDAO#updateMenuStatus(int, java.lang.Long)
     */
    public abstract boolean updateMenuStatus(int status, Long menuId);

    /* (non-Javadoc)
     * @see com.pay.base.dao.featuremenu.impl.FeatureMenuDAO#getMenuById(java.lang.Long)
     */
    public abstract MenuDto getMenuById(Long menuId);

    /* (non-Javadoc)
     * @see com.pay.base.dao.featuremenu.impl.FeatureMenuDAO#getMenuByFeature(java.lang.Long)
     */
    public abstract List<MenuDto> getMenuByFeature(Long featureId);

    /* (non-Javadoc)
     * @see com.pay.base.dao.featuremenu.impl.FeatureMenuDAO#getMenuByParentId(java.lang.Long)
     */
    public abstract List<MenuDto> getMenuByParentId(Long parentId,Integer type);
    
    
    public List<MenuDto> getMenuByOperator(Long parentId);
    
    public abstract void batchCreate(final List<FeatureMenuDto> fmList) throws DataAccessException;
    
    
    public List<MenuDto> getAllMenu();
    
    public List<MenuDto> getMenuByIdsArray(String ids);
    /**
     * 根据菜单类型获取菜单列表
     * @param types
     * @return
     */
    public List<MenuDto> findMenuByTypesArray(Integer[] types);
    
    public boolean removeMenu(Long menuId);
    
    public void batchUpdate(final List<MenuDto> mList) throws DataAccessException;

}