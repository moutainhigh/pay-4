package com.pay.base.service.featuremenu;

import java.util.List;

import com.pay.base.model.PowersModel;


public interface MemberFeatureService {

    public abstract Class getDtoClass();

    /**
     * 企业会员管理员授权操作员菜单列表
     * @param securLevel  登录级别  详细查看SecurityLvlEnum枚举类
     * @param memberCode
     * @return
     */
    public List<PowersModel> getEnterpriseMenu(int securLevel,Long memberCode,int scale);
    /**
     * 个人会员管理员授权操作员菜单列表
     * @param securLevel  登录级别  详细查看SecurityLvlEnum枚举类
     * @param memberCode
     * @return
     */
    public List<PowersModel> getIndividualMenu(int securLevel,Long memberCode);

    public abstract List<PowersModel> getFeatureMenuList(Long featureId);
    
    /**
     * 获取操作员授权操作员菜单列表，过滤非管理员的产品菜单
     *
     * @param operatorId
     * @param memberCode
     * @return
     */
    public List<PowersModel> getOperatorMenu(Long operatorId,Long memberCode);
    
    /**
     * 
     * 获取操作员授权操作员菜单列表
     * @param operatorId
     * @return
     */
    public List<PowersModel> getOperatorMenu(Long operatorId);
    
    public List<PowersModel> getFeatureMenu(int securLevel,int appScale);
    /**
     * 获取会员菜单（包括会员产品类菜单）
     *
     * @param securLevel
     * @param appScale
     * @param memberCode
     * @return
     */
    public List<PowersModel> getMemberMenu(int securLevel,int appScale,Long memberCode);
    
    
    public boolean isOperatorHaveMenu(Long operatorId, Long memberCode,
			String url);
}