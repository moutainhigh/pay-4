package com.pay.base.dao.featuremenu.impl;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;

import org.springframework.dao.DataAccessException;
import org.springframework.orm.ibatis.SqlMapClientCallback;

import com.ibatis.sqlmap.client.SqlMapExecutor;
import com.pay.base.dao.featuremenu.FeatureMenuDAO;
import com.pay.base.dto.FeatureMenuDto;
import com.pay.base.dto.MenuDto;
import com.pay.base.model.FeatureMenu;
import com.pay.base.model.Menu;
import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.util.BeanConvertUtil;

public class FeatureMenuDAOImpl extends BaseDAOImpl implements FeatureMenuDAO  {
    
    /* (non-Javadoc)
     * @see com.pay.base.dao.featuremenu.impl.FeatureMenuDAO#getModelClass()
     */
    /* (non-Javadoc)
     * @see com.pay.base.dao.featuremenu.impl.FeatureMenuDAO#getModelClass()
     */
    @Override
    public Class getModelClass() {
        return FeatureMenu.class;
    }
    
    
    /* (non-Javadoc)
     * @see com.pay.base.dao.featuremenu.impl.FeatureMenuDAO#createMenu(com.pay.base.model.Menu)
     */
    /* (non-Javadoc)
     * @see com.pay.base.dao.featuremenu.impl.FeatureMenuDAO#createMenu(com.pay.base.dto.MenuDto)
     */
    public Long createMenu(MenuDto menuDto){
       //return (Long) this.getSqlMapClientTemplate().insert(namespace.concat("create"), menu);
       return (Long) super.create(BeanConvertUtil.convert(Menu.class, menuDto));
    }
    
    /* (non-Javadoc)
     * @see com.pay.base.dao.featuremenu.impl.FeatureMenuDAO#createFeatureMenu(com.pay.base.model.FeatureMenu)
     */
    /* (non-Javadoc)
     * @see com.pay.base.dao.featuremenu.impl.FeatureMenuDAO#createFeatureMenu(com.pay.base.dto.FeatureMenuDto)
     */
    public Long createFeatureMenu(FeatureMenuDto fmDto){
        return (Long) this.getSqlMapClientTemplate().insert(namespace.concat("createFeatureMenu"), BeanConvertUtil.convert(FeatureMenu.class, fmDto));
    }
    
    /* (non-Javadoc)
     * @see com.pay.base.dao.featuremenu.impl.FeatureMenuDAO#deleteFeatureMenuByFeatureId()
     */
    /* (non-Javadoc)
     * @see com.pay.base.dao.featuremenu.impl.FeatureMenuDAO#deleteFeatureMenuByFeatureId()
     */
    public boolean deleteFeatureMenuByFeatureId(Long featureId){
        if(this.getFeatureMenuCountByFeatureId(featureId)==0)
            return true;
        int result=this.getSqlMapClientTemplate().delete(namespace.concat("deleteFeatureMenuByFeature"),featureId);
        return (result>0);
    }
    
    /* (non-Javadoc)
     * @see com.pay.base.dao.featuremenu.impl.FeatureMenuDAO#updateMenu(com.pay.base.model.Menu)
     */
    /* (non-Javadoc)
     * @see com.pay.base.dao.featuremenu.impl.FeatureMenuDAO#updateMenu(com.pay.base.dto.MenuDto)
     */
    public boolean updateMenu(MenuDto menuDto){
        return super.update(BeanConvertUtil.convert(Menu.class, menuDto));
    }
    
    /* (non-Javadoc)
     * @see com.pay.base.dao.featuremenu.impl.FeatureMenuDAO#updateMenuStatus(int, java.lang.Long)
     */
    /* (non-Javadoc)
     * @see com.pay.base.dao.featuremenu.impl.FeatureMenuDAO#updateMenuStatus(int, java.lang.Long)
     */
    public boolean updateMenuStatus(int status,Long menuId){
        HashMap map=new HashMap();
        map.put("status", status);
        map.put("menuId", menuId);
        return (this.getSqlMapClientTemplate().update(namespace.concat("updateMenuStates"), map)==1);
    }
    
    /* (non-Javadoc)
     * @see com.pay.base.dao.featuremenu.impl.FeatureMenuDAO#getMenuById(java.lang.Long)
     */
    /* (non-Javadoc)
     * @see com.pay.base.dao.featuremenu.impl.FeatureMenuDAO#getMenuById(java.lang.Long)
     */
    public MenuDto getMenuById(Long menuId){
        Menu menu=(Menu)this.getSqlMapClientTemplate().queryForObject(namespace.concat("getByMenuId"),menuId);
        return (MenuDto)BeanConvertUtil.convert(MenuDto.class, menu);

    }
    
    /* (non-Javadoc)
     * @see com.pay.base.dao.featuremenu.impl.FeatureMenuDAO#getMenuByFeature(java.lang.Long)
     */
    /* (non-Javadoc)
     * @see com.pay.base.dao.featuremenu.impl.FeatureMenuDAO#getMenuByFeature(java.lang.Long)
     */
    public List<MenuDto> getMenuByFeature(Long featureId){
        List<Menu> menuList=this.getSqlMapClientTemplate().queryForList(namespace.concat("findMenuByFeatureId"),featureId);
        List<MenuDto> menuDtoList=new ArrayList<MenuDto>(menuList.size());
        for(Menu m:menuList){
            MenuDto mDto=BeanConvertUtil.convert(MenuDto.class, m);
            menuDtoList.add(mDto);
        }
        return menuDtoList;
    }
    
    public List<MenuDto> getMenuByIdsArray(String ids){
        List<Menu> menuList=null;
        List<MenuDto> menuDtoList=null;
        
        if(ids!=null && ids.length()>0){
            String[] idsArray=ids.split(",");
            if(idsArray.length>0){
                List<String> idsList=Arrays.asList(idsArray);
                HashMap<String,Object> map=new HashMap<String,Object>();
                map.put("ids", idsList);
                menuList =this.getSqlMapClientTemplate().queryForList(namespace.concat("findMenuByIdsArray"),map);
                menuDtoList=new ArrayList<MenuDto>(menuList.size());
                for(Menu m:menuList){
                    MenuDto mDto=BeanConvertUtil.convert(MenuDto.class, m);
                    menuDtoList.add(mDto);
                }
            }
            
        }
        
        return menuDtoList;
    }
    
    @SuppressWarnings("unchecked")
	public List<MenuDto> findMenuByTypesArray(Integer[] types){
    	List<Menu> menuList=null;
        List<MenuDto> menuDtoList=null;
        if(types!=null && types.length >0){
            HashMap<String,Object> map=new HashMap<String,Object>();
            map.put("types", types);
            menuList =this.getSqlMapClientTemplate().queryForList(namespace.concat("findMenuByTypesArray"),map);
            menuDtoList=new ArrayList<MenuDto>(menuList.size());
            for(Menu m:menuList){
                MenuDto mDto=BeanConvertUtil.convert(MenuDto.class, m);
                menuDtoList.add(mDto);
            }
        }
        return menuDtoList;
    }
    
    public boolean removeMenu(Long menuId){
        return (this.getSqlMapClientTemplate().delete(namespace.concat("deleteMenuById"),menuId)==1);
    }

    
    /* (non-Javadoc)
     * @see com.pay.base.dao.featuremenu.impl.FeatureMenuDAO#getMenuByParentId(java.lang.Long)
     */
    /* (non-Javadoc)
     * @see com.pay.base.dao.featuremenu.impl.FeatureMenuDAO#getMenuByParentId(java.lang.Long)
     */
    public List<MenuDto> getMenuByParentId(Long parentId,Integer type){
        HashMap map=new HashMap(2);
        map.put("parentId", parentId);
        map.put("type", type);
        List<Menu> menuList=this.getSqlMapClientTemplate().queryForList(namespace.concat("findByParentId"),map);
        List<MenuDto> menuDtoList=new ArrayList<MenuDto>(menuList.size());
        for(Menu m:menuList){
            MenuDto mDto=BeanConvertUtil.convert(MenuDto.class, m);
            menuDtoList.add(mDto);
        }
        return menuDtoList;
    }
    
    
    public List<MenuDto> getMenuByOperator(Long parentId){
        List<Menu> menuList=this.getSqlMapClientTemplate().queryForList(namespace.concat("findByOperator"),parentId);
        List<MenuDto> menuDtoList=new ArrayList<MenuDto>(menuList.size());
        for(Menu m:menuList){
            MenuDto mDto=BeanConvertUtil.convert(MenuDto.class, m);
            menuDtoList.add(mDto);
        }
        return menuDtoList;
    }
    
    
    public List<MenuDto> getAllMenu(){
        List<Menu> menuList=this.getSqlMapClientTemplate().queryForList(namespace.concat("findAll"));;
        List<MenuDto> menuDtoList=new ArrayList<MenuDto>(menuList.size());
        for(Menu m:menuList){
            MenuDto mDto=BeanConvertUtil.convert(MenuDto.class, m);
            menuDtoList.add(mDto);
        }
        return menuDtoList;
    }
    
    public Integer getFeatureMenuCountByFeatureId(Long featureId){
        return (Integer)this.getSqlMapClientTemplate().queryForObject(namespace.concat("findFmCountByFeatureId"),featureId);
    }
    
    
    public void batchCreate(final List<FeatureMenuDto> fmList) throws DataAccessException{
        this.getSqlMapClientTemplate().execute(new SqlMapClientCallback(){ 
        public Object doInSqlMapClient(SqlMapExecutor executor) 
                throws SQLException { 
        executor.startBatch(); 
        int batch = 0; 
      //  System.out.println(receiveList.size());
        for(FeatureMenuDto tObject:fmList){
           // System.out.println(batch+"=="+tObject.getDelflag()+"=======================");
            executor.insert(namespace.concat("createFeatureMenu"),BeanConvertUtil.convert(FeatureMenu.class, tObject) );
            batch++; 
            if(batch==100){
                executor.executeBatch(); 
                batch = 0; 
            } 
        } 
        executor.executeBatch();
        return "";
        } 
        }); 
    }
    
    public void batchUpdate(final List<MenuDto> mList) throws DataAccessException{
        this.getSqlMapClientTemplate().execute(new SqlMapClientCallback(){ 
        public Object doInSqlMapClient(SqlMapExecutor executor) 
                throws SQLException { 
        executor.startBatch(); 
        int batch = 0; 
      //  System.out.println(receiveList.size());
        for(MenuDto tObject:mList){
           // System.out.println(batch+"=="+tObject.getDelflag()+"=======================");
            executor.update(namespace.concat("updateSorting"),BeanConvertUtil.convert(Menu.class, tObject) );
            batch++; 
            if(batch==100){
                executor.executeBatch(); 
                batch = 0; 
            } 
        } 
        executor.executeBatch();
        return "";
        } 
        }); 
    }
    
    
}
