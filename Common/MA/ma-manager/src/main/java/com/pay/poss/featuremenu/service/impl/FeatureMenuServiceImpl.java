package com.pay.poss.featuremenu.service.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.dao.Page;
import com.pay.inf.service.impl.BaseServiceImpl;
import com.pay.poss.featuremenu.dao.FeatureMenuDAO;
import com.pay.poss.featuremenu.dto.FeatureMenuDto;
import com.pay.poss.featuremenu.dto.MenuDto;
import com.pay.poss.featuremenu.model.CopyMenu;
import com.pay.poss.featuremenu.model.Menu;
import com.pay.poss.featuremenu.service.FeatureMenuService;

public class FeatureMenuServiceImpl extends BaseServiceImpl implements
		FeatureMenuService {
	private Log logger = LogFactory.getLog(FeatureMenuServiceImpl.class);

	// 角色对应的菜单key 前缀
	private final static String CACHE_PRE = "menu";
	// 角色对应的菜单key集合
	private final static String CACHE_KEY = "menu_key";
	// 所有website 菜单key
	private final static String ALL_MENU = "all_website_menu";

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
		if (menuDto.getOrderId() == null) {
			menuDto.setOrderId(0);
		}
		return ((FeatureMenuDAO) getMainDao()).createMenu(menuDto);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.base.service.featuremenu.impl.FeatureMenuService#updateMenu(com
	 * .pay.base.dto.MenuDto)
	 */
	public boolean updateMenu(MenuDto menuDto) {
		if (menuDto.getOrderId() == null) {
			menuDto.setOrderId(0);
		}
		return ((FeatureMenuDAO) getMainDao()).updateMenu(menuDto);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.base.service.featuremenu.impl.FeatureMenuService#updateMenuStatus
	 * (int, java.lang.Long)
	 */
	public boolean updateMenuStatus(final int status, Long menuId) {
		return ((FeatureMenuDAO) getMainDao()).updateMenuStatus(status, menuId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.base.service.featuremenu.impl.FeatureMenuService#queryMenuByParentId
	 * (java.lang.Long)
	 */
	@SuppressWarnings("unchecked")
	public List<MenuDto> queryMenuByParentId(Map paramMap, Page page) {
		Integer pageStartRow;// 起始行
		Integer pageEndRow;// 结束行
		FeatureMenuDAO fAO = (FeatureMenuDAO) super.getMainDao();
		if (null == page) {
			return null;
		}
		Integer totalCount = fAO.countMenuByParentId(paramMap);
		page.setTotalCount(totalCount);
		if (totalCount == 0) {
			return null;
		}
		pageEndRow = page.getPageNo() * page.getPageSize();
		if ((page.getPageNo() - 1) == 0) {
			pageStartRow = 0;
		} else {
			pageStartRow = (page.getPageNo() - 1) * page.getPageSize();
		}
		paramMap.put("pageStartRow", pageStartRow);
		paramMap.put("pageEndRow", pageEndRow);
		return fAO.getMenuByParentId(paramMap);
	}

	public List<MenuDto> queryMenuByParentId(Long parentId, Integer type) {
		FeatureMenuDAO fAO = (FeatureMenuDAO) super.getMainDao();
		return fAO.getMenuByParentId(parentId, type);
	}

	public List<MenuDto> queryMenuByParentIdNoType(Map paramMap) {
		FeatureMenuDAO fAO = (FeatureMenuDAO) super.getMainDao();
		return fAO.getMenuByParentIdNoType(paramMap);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.base.service.featuremenu.impl.FeatureMenuService#queryMenuByFeature
	 * (java.lang.Long)
	 */
	public List<MenuDto> queryMenuByFeature(Long featureId) {
		return ((FeatureMenuDAO) getMainDao()).getMenuByFeature(featureId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.base.service.featuremenu.impl.FeatureMenuService#getMenuById(
	 * java.lang.Long)
	 */
	public MenuDto getMenuById(Long menuId) {
		return ((FeatureMenuDAO) getMainDao()).getMenuById(menuId);
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
			if (((FeatureMenuDAO) getMainDao())
					.deleteFeatureMenuByFeatureId(featureId)) {
				((FeatureMenuDAO) getMainDao()).batchCreate(fmList);
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
		return ((FeatureMenuDAO) getMainDao()).getAllMenu();
	}

	public List<MenuDto> getAllMenuByType(String type) {
		return ((FeatureMenuDAO) getMainDao()).getAllMenuByType(type);
	}

	public List<MenuDto> queryMenuByIdsArray(String ids) {
		return ((FeatureMenuDAO) getMainDao()).getMenuByIdsArray(ids);
	}

	public boolean removeMenu(Long menuId) {
		return ((FeatureMenuDAO) getMainDao()).removeMenu(menuId);
	}

	public void updateSorting(final List<MenuDto> mList) {
		((FeatureMenuDAO) getMainDao()).batchUpdate(mList);
	}

	@Override
	public boolean delMenuById(Long id) {

		return ((FeatureMenuDAO) getMainDao()).deleteByMenuId(id) == 1;
	}

	@Override
	public boolean isExistsMenuCode(String menuCode) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("menuCode", menuCode);
		int c = ((FeatureMenuDAO) getMainDao()).countMenuCode(map);
		return c > 0;
	}

	@Override
	public boolean isExistsMenuCodeFilterId(String menuCode, Long menuId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("menuCode", menuCode);
		map.put("menuId", menuId);
		return ((FeatureMenuDAO) getMainDao()).countMenuCode(map) > 0;
	}

	@Override
	public List<MenuDto> fushMenuByFeature(int securLevel, int appScale) {

		// TODO
		List menuList = new ArrayList(0);
		// CacheUtils.getMACache().put(CACHE_PRE + "_" + securLevel + "_" +
		// appScale, menuList);
		return menuList;
	}

	@Override
	public void fushMenuCacheByAdd() {
		List menuList = new ArrayList(0);
		// CacheUtils.getMACache().put(ALL_MENU ,menuList);
	}

	@Override
	public void fushAllMenuCach() {

		// 去所有菜单列表
		List menuList = new ArrayList(0);
		// CacheUtils.getMACache().put(ALL_MENU ,menuList);

		// List<String> keyList =
		// (List<String>)CacheUtils.getMACache().get(CACHE_KEY);

		// if(keyList != null && !keyList.isEmpty()){
		// for(String k : keyList){
		// List list = new ArrayList(0);
		// 去掉角色菜单
		// CacheUtils.getMACache().put(k, list);
		// }
		// CacheUtils.getMACache().put(CACHE_KEY, new ArrayList<String>(0));
		// }
	}

	@Override
	public List<Menu> queryTreeMenuList(Long rootId) {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("parentId", rootId);
		return ((FeatureMenuDAO) getMainDao()).queryTreeMenu(map);
	}

	// @Override
	// public void exeCopyMenusRnTx(long[] ids, long[] pids, Long descId) {
	// List<Menu> listMenus = queryTreeMenuList(0l);
	// List<Long> copyIds = new ArrayList<Long>();
	// List<Long> copyPIds = new ArrayList<Long>();
	// Map<Long,Long> idForSaveMap = new HashMap<Long,Long>();
	// Map<Long,Long> pidForSaveMap = new HashMap<Long,Long>();
	//
	// for(int i=0;i<ids.length;i++){
	// long id = ids[i];
	// long pid = pids[i];
	// if(isExistsIdInList(id,listMenus)){
	// copyIds.add(new Long(id));
	// copyPIds.add(new Long(pids[i]));
	// Long nid = ((FeatureMenuDAO)getMainDao()).genMenuId();
	// idForSaveMap.put(id, nid);
	// }
	// }
	// for(int i=0;i<copyPIds.size();i++){
	// long pid = copyPIds.get(i);
	// if(pid ==descId || isExistsIdInList(pid,listMenus)){
	// pidForSaveMap.put(pid, pid);
	// }
	// else{
	// pidForSaveMap.put(pid, idForSaveMap.get(pid));
	// }
	// }
	// System.out.println(descId);
	// System.out.println(copyIds);
	// System.out.println(copyPIds);
	// Long type =((FeatureMenuDAO)getMainDao()).getMenuById(descId).getType();
	// listMenus.clear();
	//
	// //执行copy
	// for(int i=0;i<copyIds.size();i++){
	// long id = copyIds.get(i);
	// long pid = copyPIds.get(i);
	// CopyMenu cm = new CopyMenu();
	// cm.setMenuId(idForSaveMap.get(id));
	// cm.setParentId(pidForSaveMap.get(pid));
	// cm.setSrcMenuId(id);
	// cm.setType(type);
	// System.out.println(cm);
	// ((FeatureMenuDAO)getMainDao()).exeCopyMenu(cm);
	// }
	// }

	@Override
	public void exeCopyMenusRnTx(long[] copyIds, long[] copyPIds) {
		exeCopyMenus(copyIds, copyPIds);
	}

	private Map<Long, Long> exeCopyMenus(long[] copyIds, long[] copyPIds) {

		Map<Long, Long> idForSaveMap = new HashMap<Long, Long>();
		Map<Long, Long> pidForSaveMap = new HashMap<Long, Long>();

		for (int i = 0; i < copyIds.length; i++) {
			long id = copyIds[i];
			long newId = ((FeatureMenuDAO) getMainDao()).genMenuId();
			idForSaveMap.put(id, newId);
		}
		for (int i = 0; i < copyPIds.length; i++) {
			long pid = copyPIds[i];
			Long newpid = idForSaveMap.get(pid);
			if (newpid != null)
				pidForSaveMap.put(pid, newpid);
			else
				pidForSaveMap.put(pid, pid);
		}
		// 执行copy
		for (int i = 0; i < copyIds.length; i++) {
			long id = copyIds[i];
			long pid = copyPIds[i];
			Long newPid = 0L;
			Long type = -1L;
			if (pid < -1) {
				type = -1 * pid;
				newPid = 0L;

			} else {
				newPid = pidForSaveMap.get(pid);
			}
			if (type == -1L) {
				MenuDto fatherDto = ((FeatureMenuDAO) getMainDao())
						.getMenuById(pid);
				type = fatherDto.getType();
			}
			CopyMenu cm = new CopyMenu();
			cm.setMenuId(idForSaveMap.get(id));
			cm.setParentId(newPid);
			cm.setSrcMenuId(id);
			cm.setType(type);
			// System.out.println(cm);
			((FeatureMenuDAO) getMainDao()).exeCopyMenu(cm);
		}

		return idForSaveMap;
	}

	@Override
	public void exeCutMenusRnTx(long[] copyIds, long[] copyPIds) {

		// 执行剪切
		for (int i = 0; i < copyIds.length; i++) {
			long id = copyIds[i];
			long pid = copyPIds[i];

			if (pid < 0) {
				pid = 0;
			}
			MenuDto cm = new MenuDto();
			cm.setMenuId(id);
			cm.setParentId(pid);
			// System.out.println(cm);
			try {
				((FeatureMenuDAO) getMainDao()).updateMenu(cm);
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

	}

	public boolean isExistsIdInList(long id, List<Menu> listMenus) {
		for (Menu m : listMenus) {
			if (m.getMenuId().longValue() == id) {
				return true;
			}
		}
		return false;
	}

	@Override
	public boolean updateSecurityLevel(long[] ids, String securityLevel) {
		return ((FeatureMenuDAO) getMainDao()).updateSecurityLevel(ids,
				securityLevel) > 0;
	}

	@Override
	protected Class getModelClass() {
		// TODO Auto-generated method stub
		return Menu.class;
	}

}
