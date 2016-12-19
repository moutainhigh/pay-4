package com.pay.base.service.featuremenu;

import java.util.List;

import com.pay.base.dto.FeatureMenuDto;
import com.pay.base.dto.MenuDto;

public interface FeatureMenuService {

    public abstract Class getDtoClass();

    public abstract Long createMenu(MenuDto menuDto);

    public abstract boolean updateMenu(MenuDto menuDto);

    public abstract boolean updateMenuStatus(final int status, Long menuId);

    public abstract List<MenuDto> queryMenuByParentId(Long parentId,Integer Type);

    public abstract List<MenuDto> queryMenuByFeature(Long featureId);

    public abstract MenuDto getMenuById(Long menuId);

    public abstract boolean doAuthorizeRnTx(List<FeatureMenuDto> fmList,Long featureId);
    
    public List<MenuDto> getAllMenu();
    
    public List<MenuDto> queryMenuByIdsArray(String ids);
    /**
     * 根据菜单类型获取菜单列表
     * @param types
     * @return
     * @author zhi.wang
     */
    public List<MenuDto> findMenuByTypesArray(Integer[] types);
    
    public boolean removeMenu(Long menuId);
    
    public void updateSorting(final List<MenuDto> mList);
    
    public List<MenuDto> getMenuByOperator(Long parentId);

}