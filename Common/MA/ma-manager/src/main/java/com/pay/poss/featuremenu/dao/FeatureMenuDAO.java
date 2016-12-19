package com.pay.poss.featuremenu.dao;

import java.util.List;
import java.util.Map;

import org.springframework.dao.DataAccessException;

import com.pay.poss.featuremenu.dto.FeatureMenuDto;
import com.pay.poss.featuremenu.dto.MenuDto;
import com.pay.poss.featuremenu.model.CopyMenu;
import com.pay.poss.featuremenu.model.Menu;



public interface FeatureMenuDAO {

    /* (non-Javadoc)
     * @see com.pay.base.dao.featuremenu.impl.FeatureMenuDAO#getModelClass()
     */
    public  Class getModelClass();

    /* (non-Javadoc)
     * @see com.pay.base.dao.featuremenu.impl.FeatureMenuDAO#createMenu(com.pay.base.model.Menu)
     */
    public  Long createMenu(MenuDto menuDto);

    /* (non-Javadoc)
     * @see com.pay.base.dao.featuremenu.impl.FeatureMenuDAO#createFeatureMenu(com.pay.base.model.FeatureMenu)
     */
    public  Long createFeatureMenu(FeatureMenuDto fmDto);

    /* (non-Javadoc)
     * @see com.pay.base.dao.featuremenu.impl.FeatureMenuDAO#deleteFeatureMenuByFeatureId()
     */
    public  boolean deleteFeatureMenuByFeatureId(Long featureId);

    /* (non-Javadoc)
     * @see com.pay.base.dao.featuremenu.impl.FeatureMenuDAO#updateMenu(com.pay.base.model.Menu)
     */
    public  boolean updateMenu(MenuDto menuDto);

    /* (non-Javadoc)
     * @see com.pay.base.dao.featuremenu.impl.FeatureMenuDAO#updateMenuStatus(int, java.lang.Long)
     */
    public  boolean updateMenuStatus(int status, Long menuId);

    /* (non-Javadoc)
     * @see com.pay.base.dao.featuremenu.impl.FeatureMenuDAO#getMenuById(java.lang.Long)
     */
    public  MenuDto getMenuById(Long menuId);

    /* (non-Javadoc)
     * @see com.pay.base.dao.featuremenu.impl.FeatureMenuDAO#getMenuByFeature(java.lang.Long)
     */
    public  List<MenuDto> getMenuByFeature(Long featureId);

    /* (non-Javadoc)
     * @see com.pay.base.dao.featuremenu.impl.FeatureMenuDAO#getMenuByParentId(java.lang.Long)
     */
    public  List<MenuDto> getMenuByParentId(Map paramMap);
    public  List<MenuDto> getMenuByParentId(Long parent,Integer type);
    public List<MenuDto> getMenuByParentIdNoType(Map paramMap);
    public  Integer countMenuByParentId(Map paramMap);
    
    
    public  void batchCreate(final List<FeatureMenuDto> fmList) throws DataAccessException;
    
    
    public List<MenuDto> getAllMenu();
    public List<MenuDto> getAllMenuByType(String type); 
    
    public List<MenuDto> getMenuByIdsArray(String ids);
    
    public boolean removeMenu(Long menuId);
    
    public void batchUpdate(final List<MenuDto> mList) throws DataAccessException;
    
    /**
     * 删除一条记录通过id号
     * @param id
     * @return 删除和条数
     */
    public int  deleteByMenuId(Long id);
    
    /**
     * 查询MenuCode有几个，可过滤 menuId
     * @param map key menuId 过滤的id
     * 			  key menuCode 要查询的menuCode	
     * @return MenuCode的个数
     * @author 戴德荣
     */
    public int countMenuCode(Map<String,Object> map);
    
    /**
     *  查询所有菜单以树形式返回
     * @param map 参数
     * @return List<Menu>
     * @author 戴德荣
     */
    public List<Menu> queryTreeMenu(Map map);
    
    /**
     * 生成一个id号
     * @return long
     */
    public long genMenuId();
    /**
     * 拷贝方式生成一个menu
     * @param copyMenu
     */
    public void exeCopyMenu(CopyMenu copyMenu);
    /**
     * 查询父子关系啊
     * @param id 子id
     * @param pid 父亲id
     * @return true/false
     */
    public boolean existsFatherAndSon(long sid,long pid);
    
    /**
     * 修改/设置安全级别
     * @param id
     * @param securityLevel
     * @return 更新的个数
     */
    public int updateSecurityLevel(long[] id,String securityLevel);
    

}