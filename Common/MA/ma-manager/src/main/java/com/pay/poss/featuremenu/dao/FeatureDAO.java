package com.pay.poss.featuremenu.dao;

import java.util.List;

import com.pay.poss.featuremenu.dto.FeatureDto;

public interface FeatureDAO {

    public abstract Class getModelClass();

    /* (non-Javadoc)
     * @see com.pay.base.dao.featuremenu.impl.FeatureDAO#queryAllFeature()
     */
    public abstract List<FeatureDto> queryAllFeature();

    /* (non-Javadoc)
     * @see com.pay.base.dao.featuremenu.impl.FeatureDAO#getFeatureById(java.lang.Long)
     */
    public abstract FeatureDto getFeatureById(Long featureId);
    
    public Long creatFeature(FeatureDto f);
    
    public boolean updateFeature(FeatureDto f);
    
    public Long getFeatureIdByLvl(int securLevel,int appScale);

}