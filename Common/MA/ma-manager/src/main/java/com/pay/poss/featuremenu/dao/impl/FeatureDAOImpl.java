package com.pay.poss.featuremenu.dao.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

import com.pay.inf.dao.impl.BaseDAOImpl;
import com.pay.poss.featuremenu.dao.FeatureDAO;
import com.pay.poss.featuremenu.dto.FeatureDto;
import com.pay.poss.featuremenu.model.Feature;
import com.pay.util.BeanConvertUtil;

public class FeatureDAOImpl extends BaseDAOImpl implements FeatureDAO {
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.base.dao.featuremenu.impl.FeatureDAO#getModelClass()
	 */
	@Override
	public Class getModelClass() {
		return Feature.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.base.dao.featuremenu.impl.FeatureDAO#queryAllFeature()
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.base.dao.featuremenu.impl.FeatureDAO#queryAllFeature()
	 */
	public List<FeatureDto> queryAllFeature() {
		List<Feature> featureList = this.getSqlMapClientTemplate()
				.queryForList(namespace.concat("findAllFeature"));
		List<FeatureDto> fDtoList = new ArrayList<FeatureDto>(
				featureList.size());
		for (Feature f : featureList) {
			FeatureDto fd = BeanConvertUtil.convert(FeatureDto.class, f);
			fDtoList.add(fd);

		}

		return fDtoList;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.base.dao.featuremenu.impl.FeatureDAO#getFeatureById(java.lang
	 * .Long)
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.base.dao.featuremenu.impl.FeatureDAO#getFeatureById(java.lang
	 * .Long)
	 */
	public FeatureDto getFeatureById(Long featureId) {
		Feature ft = (Feature) this.getSqlMapClientTemplate().queryForObject(
				namespace.concat("getByFeatureId"), featureId);
		return BeanConvertUtil.convert(FeatureDto.class, ft);
	}

	public Long creatFeature(FeatureDto f) {
		return (Long) super.create(BeanConvertUtil.convert(Feature.class, f));
	}

	public boolean updateFeature(FeatureDto f) {
		return super.update(BeanConvertUtil.convert(Feature.class, f));
	}

	public Long getFeatureIdByLvl(int securLevel, int appScale) {
		HashMap<String, Integer> map = new HashMap<String, Integer>(2);
		map.put("securLevel", securLevel);
		map.put("appScale", appScale);
		return (Long) this.getSqlMapClientTemplate().queryForObject(
				namespace.concat("getLoginLevel"), map);

	}
}
