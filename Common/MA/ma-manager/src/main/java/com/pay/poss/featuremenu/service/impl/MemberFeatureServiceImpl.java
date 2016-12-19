package com.pay.poss.featuremenu.service.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.service.impl.BaseServiceImpl;
import com.pay.poss.featuremenu.dto.MenuDto;
import com.pay.poss.featuremenu.enums.ScaleEnum;
import com.pay.poss.featuremenu.model.Menu;
import com.pay.poss.featuremenu.model.OperatorMenu;
import com.pay.poss.featuremenu.model.PowersModel;
import com.pay.poss.featuremenu.service.FeatureMenuService;
import com.pay.poss.featuremenu.service.FeatureService;
import com.pay.poss.featuremenu.service.MemberFeatureService;
import com.pay.poss.featuremenu.service.OperatorMenuService;

public class MemberFeatureServiceImpl extends BaseServiceImpl implements
		MemberFeatureService {

	private static final Log log = LogFactory
			.getLog(MemberFeatureServiceImpl.class);

	private FeatureMenuService featureMenuService;
	private FeatureService featureService;
	private OperatorMenuService operatorMenuService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.base.service.featuremenu.impl.MemberFeatureService#getDtoClass()
	 */
	@Override
	public Class getDtoClass() {
		return MenuDto.class;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.base.service.featuremenu.impl.MemberFeatureService#getFeatureMenu
	 * (int, int)
	 */
	private List<PowersModel> getFeatureMenu(int securLevel, int appScale) {
		List<PowersModel> pmAllList = null;
		List<PowersModel> pmSelfAllList = null;
		try {
			Long featureId = featureService.getFeatureIdByLvl(securLevel,
					appScale);
			if (featureId != null) {
				List<MenuDto> listp = featureMenuService
						.queryMenuByFeature(featureId);
				pmAllList = this.getPowersList(listp);
			}

			if (pmAllList != null && pmAllList.size() > 0)
				pmSelfAllList = this.getSelfPmList(pmAllList);
		} catch (Exception e) {
			log.error("MemberFeatureServiceImpl.getFeatureMenu throws error", e);
		}
		return pmSelfAllList;
	}

	public List<PowersModel> getEnterpriseMenu(int securLevel, Long memberCode) {
		return this.getFeatureMenu(securLevel, ScaleEnum.ENTERPRICE.getValue());
	}

	public List<PowersModel> getIndividualMenu(int securLevel, Long memberCode) {
		return this.getFeatureMenu(securLevel, ScaleEnum.INDIVIDUAL.getValue());
	}

	public List<PowersModel> getOperatorMenu(Long operatorId) {
		List<PowersModel> pmAllList = null;
		List<PowersModel> pmSelfAllList = null;
		try {
			OperatorMenu opm = operatorMenuService.getByOperateId(operatorId);
			if (opm != null) {
				List<MenuDto> listp = featureMenuService
						.queryMenuByIdsArray(opm.getMenuArray());
				pmAllList = this.getPowersList(listp);
				if (pmAllList != null && pmAllList.size() > 0)
					pmSelfAllList = this.getSelfPmList(pmAllList);
			}
		} catch (Exception e) {
			log.error("MemberFeatureServiceImpl.getOperatorMenu throws error",
					e);
		}
		return pmSelfAllList;
	}

	private List<PowersModel> getPowersList(List<MenuDto> menuList) {
		List<PowersModel> pmList = new ArrayList<PowersModel>();
		PowersModel pm = null;
		for (MenuDto m : menuList) {
			pm = new PowersModel();
			pm.setId(m.getMenuId().toString());
			pm.setMenuName(m.getName());
			pm.setMenuUrl(m.getUrl());
			pm.setParentId(m.getParentId().toString());
			pm.setStatus(m.getStatus());
			pmList.add(pm);
		}

		return pmList;
	}

	public List<PowersModel> getFeatureMenuList(Long featureId, String type) {
		// List<MenuDto> menuList=featureMenuService.getAllMenu();
		List<MenuDto> menuList = featureMenuService.getAllMenuByType(type);

		List<MenuDto> listp = featureMenuService.queryMenuByFeature(featureId);
		List<PowersModel> featureMenuList = new ArrayList<PowersModel>(
				menuList.size());
		PowersModel pm = null;
		for (MenuDto m : menuList) {
			pm = new PowersModel();
			pm.setId(m.getMenuId().toString());
			pm.setMenuName(m.getName());
			pm.setMenuUrl(m.getUrl());
			pm.setParentId(m.getParentId().toString());
			pm.setStatus(m.getStatus());
			pm.setDesc(getTypeDesc(Integer.parseInt(m.getType().toString())));
			pm.setDisplayFlag(m.getDisplayFlag());
			for (MenuDto md : listp) {
				// System.out.println("da====="+m.getMenuId()+"xiao:========="+md.getMenuId()+"bijiao :"+(md.getMenuId().compareTo(m.getMenuId())));
				if (md.getMenuId().compareTo(m.getMenuId()) == 0) {
					pm.setChecked(true);
					continue;
				}
			}
			featureMenuList.add(pm);
		}

		return this.getSelfPmList(featureMenuList);
	}

	private String getTypeDesc(int type) {
		switch (type) {
		case 1:
			return ScaleEnum.PUBLIC.getMemo();
		case 2:
			return ScaleEnum.ENTERPRICE.getMemo();
		case 3:
			return ScaleEnum.INDIVIDUAL.getMemo();
		default:
			break;
		}
		return "";
	}

	private List<PowersModel> getSelfPmList(List<PowersModel> pmAllList) {
		List<PowersModel> selfList = new ArrayList<PowersModel>();
		List<PowersModel> selfRootList = this.getChildResouces(pmAllList, "0");
		List<PowersModel> selfChildList = null;
		for (PowersModel pm : selfRootList) {
			selfChildList = this.getChildResouces(pmAllList, pm.getId());
			if (selfChildList != null && selfChildList.size() > 0) {
				List<PowersModel> selfChildList1 = null;
				for (PowersModel pm1 : selfChildList) {
					selfChildList1 = this.getChildResouces(pmAllList,
							pm1.getId());
					if (selfChildList1 != null && selfChildList1.size() > 0) {
						pm1.setChildlist(selfChildList1);
					}
				}
				pm.setChildlist(selfChildList);
			}
			selfList.add(pm);
		}
		return selfList;
	}

	private List<PowersModel> getChildResouces(List<PowersModel> pmAllList,
			String parentResourceId) {
		List<PowersModel> childPmList = null;
		if (pmAllList != null && pmAllList.size() > 0) {
			childPmList = new ArrayList<PowersModel>();
			for (PowersModel pm : pmAllList) {
				if (pm.getParentId().equals(parentResourceId)) { // 找到子节点
					childPmList.add(pm);
				}
			}
		}
		return childPmList;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.base.service.featuremenu.impl.MemberFeatureService#
	 * setFeatureMenuService
	 * (com.pay.base.service.featuremenu.FeatureMenuService)
	 */
	public void setFeatureMenuService(FeatureMenuService featureMenuService) {
		this.featureMenuService = featureMenuService;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.base.service.featuremenu.impl.MemberFeatureService#setFeatureService
	 * (com.pay.base.service.featuremenu.FeatureService)
	 */
	public void setFeatureService(FeatureService featureService) {
		this.featureService = featureService;
	}

	public void setOperatorMenuService(OperatorMenuService operatorMenuService) {
		this.operatorMenuService = operatorMenuService;
	}

	@Override
	protected Class getModelClass() {
		// TODO Auto-generated method stub
		return Menu.class;
	}

}
