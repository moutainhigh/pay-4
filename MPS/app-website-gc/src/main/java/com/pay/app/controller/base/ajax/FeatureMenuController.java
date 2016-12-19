package com.pay.app.controller.base.ajax;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.base.dto.FeatureDto;
import com.pay.base.dto.FeatureMenuDto;
import com.pay.base.dto.MenuDto;
import com.pay.base.model.PowersModel;
import com.pay.base.service.featuremenu.FeatureMenuService;
import com.pay.base.service.featuremenu.FeatureService;
import com.pay.base.service.featuremenu.MemberFeatureService;
import com.pay.inf.service.IMessageDigest;
import com.pay.util.CheckUtil;


public class FeatureMenuController extends MultiActionController{
	private static final String keyVal = "704dc92faadbd200dc4c34b88183ac07be1484ab";
    private String toView;
    private String menuView;
    private String featureView;
    private String authorizeView;
    private String addMenuView;
    private String addFeatureView;
    private String sortingView;
    private String index;
    public void setSortingView(String sortingView) {
        this.sortingView = sortingView;
    }


    private FeatureMenuService featureMenuService;
    private FeatureService  featureService;
    private MemberFeatureService memberFeatureService;
    private IMessageDigest       iMessageDigest;
    


    public ModelAndView index(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	String str = request.getParameter("keyVal");
    	if(StringUtils.isNotBlank(str)){
    		if(StringUtils.equals(iMessageDigest.genMessageDigest(str.getBytes()), keyVal)){
    			request.getSession().setAttribute("AuthToken", "OK");
    			return new ModelAndView(toView);
    		}
    	}
    	return new ModelAndView(index);
    }
    
    
    public ModelAndView menuNew(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	if(request.getSession().getAttribute("AuthToken") == null ){
    		return new ModelAndView(index);
    	}
        Long menuId=request.getParameter("menuId")==null?0L:Long.parseLong(request.getParameter("menuId"));
        String parentMenuName=request.getParameter("pname")==null?"根目录":new String(request.getParameter("pname").getBytes("ISO-8859-1"), "UTF-8");
        String pid=request.getParameter("pid")==null?"0":request.getParameter("pid");
        MenuDto mDto=new MenuDto();
        if(menuId>0){
             mDto=featureMenuService.getMenuById(menuId);
        }
        return new ModelAndView(addMenuView)
                    .addObject("menuId", menuId)
                    .addObject("menu",mDto)
                    .addObject("pname", parentMenuName)
                    .addObject("pid", pid);
    }
    
    public ModelAndView featureNew(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	if(request.getSession().getAttribute("AuthToken") == null ){
    		return new ModelAndView(index);
    	}
        Long featureId=request.getParameter("featureId")==null?0L:Long.parseLong(request.getParameter("featureId"));
        FeatureDto fDto=new FeatureDto();
        if(featureId>0){
            fDto=featureService.getFeature(featureId);
        }
        return new ModelAndView(addFeatureView).addObject("feature", fDto);
    }

  


    public ModelAndView menuView(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	if(request.getSession().getAttribute("AuthToken") == null ){
    		return new ModelAndView(index);
    	}
        String pId=request.getParameter("pid")==null?"":request.getParameter("pid");
        String parentMenuName=request.getParameter("pname")==null?"根目录":new String(request.getParameter("pname").getBytes("ISO-8859-1"), "UTF-8");
        Integer type=StringUtils.isEmpty(request.getParameter("type"))?null:Integer.parseInt(request.getParameter("type"));
        Long parentId=0L;
        if(CheckUtil.isNumber(pId)){
            parentId=Long.valueOf(pId);
        }
        List<MenuDto> menuList=featureMenuService.queryMenuByParentId(parentId,type);
        return new ModelAndView(menuView)
                   .addObject("menuList",menuList)
                   .addObject("pname", parentMenuName)
                   .addObject("pid",parentId)
                   .addObject("type",type);
    }
    
    public ModelAndView featureView(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	if(request.getSession().getAttribute("AuthToken") == null ){
    		return new ModelAndView(index);
    	}
        List<FeatureDto> featureList=featureService.queryAllFeature();
        return new ModelAndView(featureView)
                    .addObject("featureList",featureList);
    }
    
    public ModelAndView authorizeView(HttpServletRequest request,
            HttpServletResponse response) throws Exception {
    	if(request.getSession().getAttribute("AuthToken") == null ){
    		return new ModelAndView(index);
    	}
        String fid=request.getParameter("fid")==null?"":request.getParameter("fid");
        Long featureId=0L;
        if(CheckUtil.isNumber(fid)){
            featureId=Long.valueOf(fid);
        }
        FeatureDto feature=featureService.getFeature(featureId);
        List<PowersModel> pwList=memberFeatureService.getFeatureMenuList(featureId);
       
        return new ModelAndView(authorizeView)
                    .addObject("feature",feature).addObject("pwList",pwList);
    }
    
    
    public ModelAndView doAuthor(HttpServletRequest request,
            HttpServletResponse response)throws Exception{
    	if(request.getSession().getAttribute("AuthToken") == null ){
    		return new ModelAndView(index);
    	}
        String[] menuIdArry=request.getParameterValues("menuId");
        String fid=request.getParameter("featureId");
        List<FeatureMenuDto> fmList=null;
        if(menuIdArry!=null && menuIdArry.length>0 && StringUtils.isNotEmpty(fid)){
            fmList=new ArrayList<FeatureMenuDto>(menuIdArry.length);
            Long featureId=Long.valueOf(fid);
            for(String menuId:menuIdArry){
                FeatureMenuDto fm=new FeatureMenuDto();
                fm.setFeatureId(featureId);
                fm.setMenuId(Long.valueOf(menuId));
                fmList.add(fm);
            }
            featureMenuService.doAuthorizeRnTx(fmList,featureId);
        }
        
        
       
        return this.featureView(request, response);
    }
    
    public ModelAndView sortingView(HttpServletRequest request,
            HttpServletResponse response)throws Exception{
    	if(request.getSession().getAttribute("AuthToken") == null ){
    		return new ModelAndView(index);
    	}
        Integer type=StringUtils.isEmpty(request.getParameter("type"))?null:Integer.parseInt(request.getParameter("type"));
        String pId=request.getParameter("pid")==null?"":request.getParameter("pid");
        Long parentId=0L;
        if(CheckUtil.isNumber(pId)){
            parentId=Long.valueOf(pId);
        }
       
        List<MenuDto> menuList=featureMenuService.queryMenuByParentId(parentId,type);
        return new ModelAndView(sortingView).addObject("menuList",menuList);
    }
    
    public ModelAndView doSorting(HttpServletRequest request,
            HttpServletResponse response)throws Exception{
    	if(request.getSession().getAttribute("AuthToken") == null ){
    		return new ModelAndView(index);
    	}
        List<MenuDto> mList=new ArrayList<MenuDto>();
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
        }
       
        return this.menuView(request, response);
    }
    
    public ModelAndView doRm(HttpServletRequest request,
            HttpServletResponse response)throws Exception{
    	if(request.getSession().getAttribute("AuthToken") == null ){
    		return new ModelAndView(index);
    	}
        Long menuId=request.getParameter("menuId")==null?0L:Long.parseLong(request.getParameter("menuId"));
        featureMenuService.removeMenu(menuId);
        return this.menuView(request, response);
    }
    
    public ModelAndView doMenu(HttpServletRequest request,
            HttpServletResponse response,MenuDto menu)throws Exception{
    	if(request.getSession().getAttribute("AuthToken") == null ){
    		return new ModelAndView(index);
    	}
            if(menu.getMenuId()!=null && menu.getMenuId()>0){
                featureMenuService.updateMenu(menu);
            }else{
                featureMenuService.createMenu(menu);
            }
           
        return this.menuView(request, response);
    }
    
    
    public ModelAndView doFeature(HttpServletRequest request,
            HttpServletResponse response,FeatureDto fDto)throws Exception{
    	if(request.getSession().getAttribute("AuthToken") == null ){
    		return new ModelAndView(index);
    	}
        if(fDto.getFeatureId()!=null && fDto.getFeatureId()>0){
            featureService.updateFeature(fDto);
        }else{
            featureService.createFeature(fDto);
        }
       
        return this.featureView(request, response);
    }
    
    
   

    public void setToView(String toView) {
        this.toView = toView;
    }
    
    public void setMenuView(String menuView) {
        this.menuView = menuView;
    }


    public void setAuthorizeView(String authorizeView) {
        this.authorizeView = authorizeView;
    }

    public void setFeatureView(String featureView) {
        this.featureView = featureView;
    }

    public void setMemberFeatureService(MemberFeatureService memberFeatureService) {
        this.memberFeatureService = memberFeatureService;
    }


    public void setFeatureMenuService(FeatureMenuService featureMenuService) {
        this.featureMenuService = featureMenuService;
    }


    public void setFeatureService(FeatureService featureService) {
        this.featureService = featureService;
    }
    
    public void setAddMenuView(String addMenuView) {
        this.addMenuView = addMenuView;
    }


    public void setAddFeatureView(String addFeatureView) {
        this.addFeatureView = addFeatureView;
    }

	public void setIndex(String index) {
		this.index = index;
	}

	public void setiMessageDigest(IMessageDigest iMessageDigest) {
		this.iMessageDigest = iMessageDigest;
	}
    
}
