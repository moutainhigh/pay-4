package com.pay.poss.featuremenu.service;

import java.util.List;

import com.pay.inf.service.BaseService;
import com.pay.poss.featuremenu.dto.FeatureDto;

public interface FeatureService extends BaseService {

	public abstract Class getDtoClass();

	public abstract List<FeatureDto> queryAllFeature();

	public abstract FeatureDto getFeature(Long featureId);

	public abstract Long createFeature(FeatureDto fDto);

	public abstract boolean updateFeature(FeatureDto fDto);

	/**
	 * @param securLevel
	 *            登录级别
	 * @param appScale
	 *            会员类别 2-企业3-个人
	 * @return
	 */
	public abstract Long getFeatureIdByLvl(int securLevel, int appScale);

}