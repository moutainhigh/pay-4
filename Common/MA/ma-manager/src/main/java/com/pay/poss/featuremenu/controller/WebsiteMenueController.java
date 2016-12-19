/**
 * 
 */
package com.pay.poss.featuremenu.controller;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.base.common.BeanUtil;
import com.pay.poss.featuremenu.common.CheckUtil;
import com.pay.poss.featuremenu.dto.JSONDto;
import com.pay.poss.featuremenu.dto.MenuDto;
import com.pay.poss.featuremenu.model.Menu;
import com.pay.poss.featuremenu.service.FeatureMenuService;
import com.pay.util.StringUtil;

/**
 * @Description website菜单查询
 * @project 	poss-membermanager
 * @file 		WebsiteMenueController.java 
 * @note		<br>
 * @develop		JDK1.6 + SpringSource 2.3.2
 * @version     1.0
 * Copyright © 2004-2013 pay.com . All rights reserved. 版权所有
 * Date				Author			Changes
 * 2010-10-30		tianqing_wang			Create
 */
public class WebsiteMenueController extends MultiActionController {
	
	private Log log = LogFactory.getLog(WebsiteMenueController.class);
	
    private FeatureMenuService featureMenuService;
	
	private String menuSearchView;
	private String menuSearchListView;
	private String menuEditView; 
	private String menuCreateView;
	private String sortView;
	private String copyMenuToView;
	private String delPassword = "ddr";

	
	//菜单查询
	public ModelAndView index(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
        String pId=request.getParameter("pid")==null?"":request.getParameter("pid");
        
        String parentMenuName=request.getParameter("pname")==null?"根目录":request.getParameter("pname");
        Integer type=StringUtils.isEmpty(request.getParameter("type"))?null:Integer.parseInt(request.getParameter("type"));
        Long parentId=0L;
        if(CheckUtil.isNumber(pId)){
            parentId=Long.valueOf(pId);
        }
        //此判断为了赋值方便menuDto
        if(null != parentMenuName){
        	//为了避免parentMenuName乱码,在此在数据库再取一次
        	MenuDto menuDto = featureMenuService.getMenuById(parentId);
        	parentMenuName = menuDto == null?"根目录":menuDto.getName();
        };
        return new ModelAndView(menuSearchView)
                   .addObject("pname", parentMenuName)
                   .addObject("pid",parentId)
                   .addObject("type",type);
    }
	//菜单列表
	 @SuppressWarnings("unchecked")
	public ModelAndView menuSearchList(HttpServletRequest request,
	            HttpServletResponse response) throws Exception {
        String pId=request.getParameter("pid")==null?"":request.getParameter("pid");
        //String parentMenuName=request.getParameter("pname")==null?"根目录":new String(request.getParameter("pname").getBytes("ISO-8859-1"), "UTF-8");
        String parentMenuName = request.getParameter("pname")==null?"根目录":request.getParameter("pname");
        Integer type=StringUtils.isEmpty(request.getParameter("type"))?null:Integer.parseInt(request.getParameter("type"));
        Long parentId = 0L;
        if(CheckUtil.isNumber(pId)){
            parentId=Long.valueOf(pId);
        }
        Page<MenuDto> page = PageUtils.getPage(request);
        Map paramMap = new HashMap();
        paramMap.put("parentId", parentId);
        //控制初始菜单列表的时候按类别显示
        if(0 == parentId){
        	 paramMap.put("type", type);
        }
        List<MenuDto> menuList = featureMenuService.queryMenuByParentId(paramMap,page);
        page.setResult(menuList);
        String backParentId = null; 
        if(null != parentMenuName){
        	//为了避免parentMenuName乱码,在此在数据库再取一次
        	MenuDto menuDto = featureMenuService.getMenuById(parentId);
        	parentMenuName = menuDto == null?"根目录":menuDto.getName();
        	backParentId = menuDto == null?"0":String.valueOf(menuDto.getParentId());
        };;
        return new ModelAndView(menuSearchListView)
                   .addObject("page",page)
                   .addObject("pname", parentMenuName)
                   .addObject("pid",parentId)
                   .addObject("type",type)
                   .addObject("backParentId",backParentId)
                   ;
    }
	//菜单编辑显示
	public ModelAndView menuEditView(HttpServletRequest request,
	    HttpServletResponse response) throws Exception {
        Long menuId=request.getParameter("menuId")==null?0L:Long.parseLong(request.getParameter("menuId"));
        MenuDto mDto=new MenuDto();
        if(menuId>0){
             mDto=featureMenuService.getMenuById(menuId);
        }
        return new ModelAndView(menuEditView)
                    .addObject("menuId", menuId)
                    .addObject("menu",mDto);
	    }
	//菜单编辑保存
	public ModelAndView editSaveMenu(HttpServletRequest request,
        HttpServletResponse response,MenuDto menu)throws Exception{
	/////
		String msg = "";
		boolean isOk = false;
		Long id = menu.getMenuId();
		if( StringUtil.isEmpty(menu.getMenuCode()) ){
			msg = "菜单编码不能为空";
		}
		//
		else if(featureMenuService.isExistsMenuCodeFilterId(menu.getMenuCode(),id)){
			msg = "菜单编码已被占用，请换一个！";
		}
		else{
			try{
				featureMenuService.updateMenu(menu);
				featureMenuService.fushAllMenuCach();
				msg = "更新成功";
				isOk = true;
			}catch (Exception e) {
				e.printStackTrace();
				msg = "更新菜单异常,"+e.getClass();
			}
		}
		
		 response.setContentType("text/plain;charset=UTF-8");
		 response.getWriter().write(BeanUtil.bean2JSON(new JSONDto((isOk?"S":"F")+"", msg, ""+(id==null?"":id) )));
		 return null;
    }
	//菜单新增显示
	public ModelAndView menuCreateView(HttpServletRequest request,
	    HttpServletResponse response) throws Exception {
		String parentId = request.getParameter("parentId");
		String type = request.getParameter("type");
        return new ModelAndView(menuCreateView)
		 			.addObject("parentId", parentId)
		 			.addObject("type", type);
	}
	//菜单新增保存
	public ModelAndView createSaveMenu(HttpServletRequest request,
        HttpServletResponse response,MenuDto menu)throws Exception{
		String parentId = request.getParameter("parentId");
		if("".equals(parentId)){
			menu.setParentId(0L);
		}
		//如果type无值，则表明是添加子菜单select框为灰色没有值传过来，取父菜单的type
		if(null == menu.getType()){
			menu.setType(menu.getHiddenType());
		}
		/////
		String msg = "";
		boolean isOk = false;
		Long id = null;
		if( StringUtil.isEmpty(menu.getMenuCode()) ){
			msg = "菜单编码不能为空";
		}
		//
		else if(featureMenuService.isExistsMenuCode(menu.getMenuCode())){
			msg = "菜单编码已被占用，请换一个！";
		}
		else{
			try{
				id =  featureMenuService.createMenu(menu);
				featureMenuService.fushMenuCacheByAdd();
				msg = "创建成功";
				isOk = true;
			}catch (Exception e) {
				e.printStackTrace();
				msg = "创建菜单异常,"+e.getClass();
			}
		}
		
		 response.setContentType("text/plain;charset=UTF-8");
		 String json = BeanUtil.bean2JSON(new JSONDto((isOk?"S":"F")+"", msg, ""+(id==null?"":id) ));
		 log.info(json);
		 response.getWriter().write(json);
		 return null;
		   
	    }
	//排序显示页面
	@SuppressWarnings("unchecked")
	public ModelAndView sortView(HttpServletRequest request,
	    HttpServletResponse response) throws Exception {
		Integer type=StringUtils.isEmpty(request.getParameter("type"))?null:Integer.parseInt(request.getParameter("type"));
        String pId=request.getParameter("pId")==null?"":request.getParameter("pId");
        Long parentId=0L;
        if(CheckUtil.isNumber(pId)){
            parentId=Long.valueOf(pId);
        }
        Map paramMap = new HashMap(2);
        paramMap.put("parentId", parentId);
        if(parentId == 0){
        	paramMap.put("type", type);
        }
        List<MenuDto> menuList=featureMenuService.queryMenuByParentIdNoType(paramMap);
        return new ModelAndView(sortView)
        .addObject("menuList",menuList)
        .addObject("type",type)
        .addObject("pId",pId);
     }
	//调整排序后保存
	 public ModelAndView doSorting(HttpServletRequest request,
	            HttpServletResponse response)throws Exception{
	        List<MenuDto> mList=new ArrayList<MenuDto>();
	        Integer type=StringUtils.isEmpty(request.getParameter("type"))?null:Integer.parseInt(request.getParameter("type"));
	        String pId=request.getParameter("pId")==null?"":request.getParameter("pId");
	        Long parentId=0L;
	        if(CheckUtil.isNumber(pId)){
	            parentId=Long.valueOf(pId);
	        }
	        //提交排序部分
	        String menuIds=request.getParameter("menuIdArry");
	        if(menuIds!=null &&  menuIds.length()>0){
	            String[] menuIdArray=menuIds.split(",");
	            int k=1;
	            MenuDto md=null;
	            for(String s: menuIdArray){
	                md=new MenuDto();
	                md.setMenuId(Long.valueOf(s));
	                md.setOrderId(k);
	                k++;
	                mList.add(md);
	            }
	            featureMenuService.updateSorting(mList);
	            featureMenuService.fushAllMenuCach();
	        }
	        //提交完以后的页面刷新
	        List<MenuDto> menuList=featureMenuService.queryMenuByParentId(parentId,type);
	        return new ModelAndView(sortView)
	        .addObject("menuList",menuList)
	        .addObject("type",type)
	        .addObject("pId",pId);
	    }
	 
	 
	//删除
	 public ModelAndView delMenu(HttpServletRequest request,
	            HttpServletResponse response)throws Exception{
	     
		 String checkPassword = ServletRequestUtils.getStringParameter(request, "checkPassword"); 
		 String id = ServletRequestUtils.getStringParameter(request, "id");
		 String result = "";
		 response.setContentType("text/plain;charset=UTF-8");
		 
		 if(StringUtil.isEmpty(id)){
			 result = "删除的ID号为空，无法定位删除的记录";
			 response.getWriter().write(result);
			 return null;
		 }
		 
		 if(! delPassword.equals(checkPassword)){
			 	result = "密码不正确，输入正确的密码才能删除";
			 
		 }else{
			 //删除
			 boolean  isOk = featureMenuService.delMenuById(new Long(id));
			 result = isOk?"S":"删除失败，不存在对应的数据或已被删除了";
			 if(isOk){
				 featureMenuService.fushAllMenuCach();
			 }
		 }
		 
		response.getWriter().write(result);
	 	return null;
	   }
	 
	//复制/剪切页面
	 public ModelAndView copyAddTo(HttpServletRequest request,
	            HttpServletResponse response)throws Exception{
	     
		 Long id = ServletRequestUtils.getLongParameter(request, "id",-1L);
		 //1为copy （默认），2为cut
		 int copyType = ServletRequestUtils.getIntParameter(request, "copyType", 1);
		 Long rootId = 0L;
		 //查询所有树，
		 List<Menu> treeMenuList = featureMenuService.queryTreeMenuList(rootId);
		 //查询当前
		 List<Menu> currMenuList = featureMenuService.queryTreeMenuList(id);
		 MenuDto currMenu =  featureMenuService.getMenuById(id);
		 Map<String, Object> map = new HashMap<String, Object>();
		 map.put("treeMenuList", treeMenuList);
		 map.put("currMenuList", currMenuList);
		 map.put("currMenu", currMenu);
		 map.put("copyType", copyType);
		 return new ModelAndView(copyMenuToView,map);
	   }
	 
	 //执行copy
	 public ModelAndView exeCopyMenu(HttpServletRequest request,
	            HttpServletResponse response)throws Exception{
		 
		 long[] ids = ServletRequestUtils.getLongParameters(request, "copyId");
		 long[] pids = ServletRequestUtils.getLongParameters(request, "copyPId");
		 String msg = "";
		 boolean isOk = false;
		try{
			  featureMenuService.exeCopyMenusRnTx(ids,pids);
			msg = "复制成功";
			isOk = true;
		}catch(Exception e){
			msg = "复制出现异常信息";
		}

		 response.setContentType("text/plain;charset=UTF-8");
		 response.getWriter().write(BeanUtil.bean2JSON(new JSONDto((isOk?"S":"F")+"", msg, "" )));
		 return null;
	   }
	 
	//执行剪切
		 public ModelAndView exeCutMenu(HttpServletRequest request,
		            HttpServletResponse response)throws Exception{
			 
			 long[] ids = ServletRequestUtils.getLongParameters(request, "copyId");
			 long[] pids = ServletRequestUtils.getLongParameters(request, "copyPId");
			 String msg = "";
			 boolean isOk = false;
			try{
				  featureMenuService.exeCutMenusRnTx(ids,pids);
				msg = "剪切成功";
				isOk = true;
			}catch(Exception e){
				msg = "剪切出现异常信息";
			}

			 response.setContentType("text/plain;charset=UTF-8");
			 response.getWriter().write(BeanUtil.bean2JSON(new JSONDto((isOk?"S":"F")+"", msg, "" )));
			 return null;
		   }
		 
	 public ModelAndView setSecuLevel(HttpServletRequest request,
	            HttpServletResponse response)throws Exception{
	     
		 long[] menuIds = ServletRequestUtils.getLongParameters(request, "menuId"); 
		 String securityLevel = ServletRequestUtils.getStringParameter(request, "securityLevel"); 
		 String result = "";
		 response.setContentType("text/plain;charset=UTF-8");
		 
		 if(menuIds==null || menuIds.length == 0){
			 result = "要设置的id号必须至少选择一个";
			 response.getWriter().write(result);
			 return null;
		 }
		 else{
			 //设置安全级别
			 boolean  isOk = featureMenuService.updateSecurityLevel(menuIds, securityLevel);
			 result = isOk?"S":"安全级别设置失败";
			 if(isOk){
				 featureMenuService.fushAllMenuCach();
			 }
		 }
		response.getWriter().write(result);
	 	return null;
	   }
	 
	 
	
	public String getSortView() {
		return sortView;
	}
	public void setSortView(String sortView) {
		this.sortView = sortView;
	}
	public String getMenuSearchView() {
		return menuSearchView;
	}
	public void setMenuSearchView(String menuSearchView) {
		this.menuSearchView = menuSearchView;
	}
	public String getMenuEditView() {
		return menuEditView;
	}
	public void setMenuEditView(String menuEditView) {
		this.menuEditView = menuEditView;
	}
	public String getMenuCreateView() {
		return menuCreateView;
	}
	public void setMenuCreateView(String menuCreateView) {
		this.menuCreateView = menuCreateView;
	}
	public String getMenuSearchListView() {
		return menuSearchListView;
	}
	public void setMenuSearchListView(String menuSearchListView) {
		this.menuSearchListView = menuSearchListView;
	}
	public void setFeatureMenuService(FeatureMenuService featureMenuService) {
		this.featureMenuService = featureMenuService;
	}
	/**
	 * @return the copyMenuToView
	 */
	public String getCopyMenuToView() {
		return copyMenuToView;
	}
	/**
	 * @param copyMenuToView the copyMenuToView to set
	 */
	public void setCopyMenuToView(String copyMenuToView) {
		this.copyMenuToView = copyMenuToView;
	}
	
}
