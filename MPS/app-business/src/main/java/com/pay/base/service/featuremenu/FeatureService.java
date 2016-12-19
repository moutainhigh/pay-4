package com.pay.base.service.featuremenu;

import java.util.List;

import com.pay.base.dto.FeatureDto;

public interface FeatureService {

    public abstract Class getDtoClass();

    public abstract List<FeatureDto> queryAllFeature();

    public abstract FeatureDto getFeature(Long featureId);

    public abstract Long createFeature(FeatureDto fDto);

    public abstract boolean updateFeature(FeatureDto fDto);

    /**
     * @param securLevel  登录级别
     * @param appScale   会员类别 2-企业3-个人
     * @return
     */
    public abstract Long getFeatureIdByLvl(int securLevel, int appScale);

}