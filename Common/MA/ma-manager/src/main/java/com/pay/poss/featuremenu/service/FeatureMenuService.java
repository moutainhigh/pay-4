package com.pay.poss.featuremenu.service;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.inf.service.BaseService;
import com.pay.poss.featuremenu.dto.FeatureMenuDto;
import com.pay.poss.featuremenu.dto.MenuDto;
import com.pay.poss.featuremenu.model.Menu;

public interface FeatureMenuService extends BaseService {

	public abstract Class getDtoClass();

	public abstract Long createMenu(MenuDto menuDto);

	public abstract boolean updateMenu(MenuDto menuDto);

	public abstract boolean updateMenuStatus(final int status, Long menuId);

	@SuppressWarnings("unchecked")
	public abstract List<MenuDto> queryMenuByParentId(Map paramMap, Page page);

	public abstract List<MenuDto> queryMenuByParentId(Long parent, Integer type);

	@SuppressWarnings("unchecked")
	public List<MenuDto> queryMenuByParentIdNoType(Map paramMap);

	public abstract List<MenuDto> queryMenuByFeature(Long featureId);

	public abstract MenuDto getMenuById(Long menuId);

	public abstract boolean doAuthorizeRnTx(List<FeatureMenuDto> fmList,
			Long featureId);

	public List<MenuDto> getAllMenu();

	public List<MenuDto> getAllMenuByType(String type);

	public List<MenuDto> queryMenuByIdsArray(String ids);

	public boolean removeMenu(Long menuId);

	public void updateSorting(final List<MenuDto> mList);

	/**
	 * 通过 id删除一条记录
	 * 
	 * @param id
	 * @return
	 */
	public boolean delMenuById(Long id);

	/**
	 * 查询是否存在menuCode
	 * 
	 * @param menuCode
	 * @return true/false
	 */
	public boolean isExistsMenuCode(String menuCode);

	/**
	 * 查询是否存在menuCode
	 * 
	 * @param menuCode
	 * @param menuId过滤的id
	 * @return true/false
	 */
	public boolean isExistsMenuCodeFilterId(String menuCode, Long menuId);

	/**
	 * 刷新website中的缓存，配置权限时用到
	 * 
	 * @param securLevel
	 * @param appScale
	 * @return
	 */
	public List<MenuDto> fushMenuByFeature(int securLevel, int appScale);

	/**
	 * 刷新website中的缓存，菜单新增时用到。
	 */
	public void fushMenuCacheByAdd();

	/**
	 * 刷新website中的缓存,菜单修改，删除时。
	 */
	public void fushAllMenuCach();

	/**
	 * 所有菜单以树形式返回,
	 * 
	 * @param rootId
	 *            , 根结点id号，如果没有默认是0
	 * @return List<Menu>
	 */
	public List<Menu> queryTreeMenuList(Long rootId);

	public void exeCopyMenusRnTx(long[] ids, long[] pids);

	public void exeCutMenusRnTx(long[] copyIds, long[] copyPIds);

	/**
	 * 更新安全级别
	 * 
	 * @param ids
	 * @param securityLevel
	 * @return true/false
	 */
	public boolean updateSecurityLevel(long ids[], String securityLevel);

}