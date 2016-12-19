package com.pay.poss.featuremenu.service;

import java.util.List;

import com.pay.poss.featuremenu.model.PowersModel;




public interface MemberFeatureService {

    public abstract Class getDtoClass();

    /**
     * 企业会员管理员授权操作员菜单列表
     * @param securLevel  登录级别  详细查看SecurityLvlEnum枚举类
     * @param memberCode
     * @return
     */
    public List<PowersModel> getEnterpriseMenu(int securLevel,Long memberCode);
    
    public List<PowersModel> getIndividualMenu(int securLevel,Long memberCode);

    public abstract List<PowersModel> getFeatureMenuList(Long featureId,String type);

    public List<PowersModel> getOperatorMenu(Long operatorId);

}