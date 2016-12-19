package com.pay.base.service.featuremenu.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.base.common.enums.ScaleEnum;
import com.pay.base.dto.MenuDto;
import com.pay.base.model.OperatorMenu;
import com.pay.base.model.PowersModel;
import com.pay.base.service.featuremenu.FeatureMenuService;
import com.pay.base.service.featuremenu.FeatureService;
import com.pay.base.service.featuremenu.MemberFeatureService;
import com.pay.base.service.featuremenu.MemberProductService;
import com.pay.base.service.featuremenu.cache.MemberFeatureCacheService;
import com.pay.base.service.operator.OperatorMenuService;
import com.pay.inf.dao.BaseDAO;
import com.pay.util.StringUtil;

public class MemberFeatureServiceImpl implements MemberFeatureService {

	private static final Log log = LogFactory
			.getLog(MemberFeatureServiceImpl.class);

	private FeatureMenuService featureMenuService;
	private FeatureService featureService;
	private OperatorMenuService operatorMenuService;
	private MemberProductService memberProductService;
	private MemberFeatureCacheService memberFeatureCacheService;

	private BaseDAO mainDao;

	public void setMainDao(BaseDAO mainDao) {
		this.mainDao = mainDao;
	}

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
	public List<PowersModel> getFeatureMenu(int securLevel, int appScale) {
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

	public List<PowersModel> getEnterpriseMenu(int securLevel, Long memberCode,
			int scale) {
		return this.getFeatureMenu(securLevel, scale);
	}

	public List<PowersModel> getIndividualMenu(int securLevel, Long memberCode) {
		return this.getFeatureMenu(securLevel, ScaleEnum.INDIVIDUAL.getValue());
	}

	/**
	 * 获取会员菜单（包括会员产品类菜单）
	 * 
	 * @param securLevel
	 * @param appScale
	 * @param memberCode
	 * @return
	 */
	public List<PowersModel> getMemberMenu(int securLevel, int appScale,
			Long memberCode) {
		// List<PowersModel> pmAllList = null;
		List<PowersModel> pmSelfAllList = null;
		/*
		 * try { // 获取会员菜单 Long
		 * featureId=featureService.getFeatureIdByLvl(securLevel, appScale);
		 * if(featureId!=null){ List<MenuDto> listp =
		 * featureMenuService.queryMenuByFeature(featureId); pmAllList =
		 * this.getPowersList(listp); } // 查询会员产品类菜单 List<MenuDto> proMenuList =
		 * memberProductService.findProductMenuByMemCode(memberCode);
		 * if(proMenuList != null){ List<PowersModel> proPowersList =
		 * this.getPowersList(proMenuList); if(pmAllList!=null){
		 * List<PowersModel> powersList = new ArrayList<PowersModel>(); for
		 * (PowersModel powersModel : proPowersList) { // 过滤相同的菜单
		 * if(!pmAllList.contains(powersModel)){ powersList.add(powersModel); }
		 * } pmAllList.addAll(powersList); }else{ pmAllList = proPowersList; } }
		 * if (pmAllList != null && pmAllList.size() > 0) pmSelfAllList =
		 * this.getSelfPmList(pmAllList); } catch (Exception e) { log.error(
		 * "MemberFeatureServiceImpl.getFeatureMenu throws error",e); }
		 */
		pmSelfAllList = memberFeatureCacheService.getMemberMenu(securLevel,
				appScale, memberCode);
		return pmSelfAllList;
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

	public List<PowersModel> getOperatorMenu(Long operatorId, Long memberCode) {
		List<PowersModel> pmAllList = null;
		List<PowersModel> pmSelfAllList = null;
		try {
			OperatorMenu opm = operatorMenuService.getByOperateId(operatorId);
			if (opm != null) {
				List<MenuDto> listp = featureMenuService
						.queryMenuByIdsArray(opm.getMenuArray());
				// 过滤非管理员的产品菜单
				// 查询会员产品类菜单
				List<MenuDto> proMenuList = memberProductService
						.findProductMenuByMemCode(memberCode);
				boolean checkProMenu = false;
				List<MenuDto> tempList = new ArrayList<MenuDto>();
				for (MenuDto menuDto : listp) {
					if (menuDto.getType() == 9) {// 产品菜单
						if (proMenuList != null && proMenuList.size() > 0) {
							for (MenuDto menuDto2 : proMenuList) {
								if (menuDto.getMenuId().equals(
										menuDto2.getMenuId())) {
									checkProMenu = true;
									break;
								}
							}
						}
						if (checkProMenu) {
							tempList.add(menuDto);
						}
					} else { // 非产品菜单
						tempList.add(menuDto);
					}
				}
				pmAllList = this.getPowersList(tempList);
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
			pm.setMenuCode(m.getMenuCode());
			pm.setDisplayFlag(m.getDisplayFlag());
			pmList.add(pm);
		}

		return pmList;
	}

	public List<PowersModel> getFeatureMenuList(Long featureId) {
		List<MenuDto> menuList = featureMenuService.getAllMenu();
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
			pm.setMenuCode(m.getMenuCode());
			pm.setDisplayFlag(m.getDisplayFlag());
			pm.setDesc(getTypeDesc(Integer.parseInt(m.getType().toString())));
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
		case 10:// bsp
			return ScaleEnum.TRADING_CENTER.getMemo();
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
						List<PowersModel> selfChildList2 = null;
						for (PowersModel pm2 : selfChildList1) {
							selfChildList2 = this.getChildResouces(pmAllList,
									pm2.getId());
							if (selfChildList2 != null
									&& selfChildList2.size() > 0) {
								pm2.setChildlist(selfChildList2);
							}
						}
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

	@Override
	public boolean isOperatorHaveMenu(Long operatorId, Long memberCode,
			String url) {
		if (StringUtil.isEmpty(url)) {
			// ?
			return false;
		}
		List<PowersModel> pmAllList = getOperatorMenu(operatorId, memberCode);
		if (pmAllList == null || pmAllList.isEmpty()) {
			return false;
		}
		for (PowersModel item : pmAllList) {
			if ((url.equals(item.getMenuUrl()) || item.getMenuUrl().contains(
					url))
					&& StringUtils.isNotBlank(item.getMenuUrl())) {
				return true;
			}
		}
		return false;
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

	public void setMemberProductService(
			MemberProductService memberProductService) {
		this.memberProductService = memberProductService;
	}

	public void setMemberFeatureCacheService(
			MemberFeatureCacheService memberFeatureCacheService) {
		this.memberFeatureCacheService = memberFeatureCacheService;
	}

}
