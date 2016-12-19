/*
 * Copyright © 2004-2013 pay.com . All rights reserved. 
 */
package com.pay.app.service.operator.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.app.dao.operator.OperatorDAO;
import com.pay.app.facade.dto.ResultDto;
import com.pay.app.model.OperatorExtLoginInfo;
import com.pay.app.model.TOperator;
import com.pay.app.service.operator.OperatorMenuService;
import com.pay.app.service.operator.OperatorServiceFacade;
import com.pay.base.model.PowersModel;
import com.pay.base.service.featuremenu.MemberFeatureService;
import com.pay.inf.exception.AppException;
import com.pay.inf.service.IMessageDigest;

/**
 * 操作员信息服务
 * 
 * @author wangzhi
 * @version
 * @data 2010-9-20 下午07:56:37
 * 
 */
public class OperatorServiceFacadeImpl implements OperatorServiceFacade {

	private static final Log logger = LogFactory
			.getLog(OperatorServiceFacadeImpl.class);

	private OperatorDAO operatorDAO;

	private IMessageDigest iMessageDigest;

	private OperatorMenuService operatorMenuService;

	/** 授权操作员菜单 */
	private MemberFeatureService memberFeatureService;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.app.service.operator.OperatorServiceFacade#createOperator
	 * (com.pay.app.model.TOperator)
	 */
	@Override
	public ResultDto createOperator(TOperator operator) throws AppException {
		ResultDto result = new ResultDto();
		if (operator != null && operator.getMemberCode() > 0
				&& StringUtils.isNotBlank(operator.getIdentity())
				&& StringUtils.isNotBlank(operator.getPassword())) {
			/* 确保有效状态操作员的登录名是唯一的 */
			// 判断登录名是否已被占用
			TOperator oper = operatorDAO.getByIdentity(operator.getIdentity());
			if (oper != null) {
				logger.warn("操作员的登录账号已被占用！identity=" + operator.getIdentity());
				result.setErrorMsg(operator.getIdentity() + "操作员的登录账号已被占用！");
			} else {
				try {
					operator
							.setPassword(iMessageDigest
									.genMessageDigest(operator.getPassword()
											.getBytes()));
					operatorDAO.createOperator(operator);
					TOperator creOperator = operatorDAO.getByIdentity(operator
							.getIdentity());
					result.setObject(creOperator.getOperatorId());
				} catch (Exception e) {
					logger.error("创建操作员系统异常！memberCode="
							+ operator.getMemberCode() + "操作员登录账号="
							+ operator.getIdentity(), e);
					result.setErrorCode("0001");
					result.setErrorMsg("创建操作员系统异常");
					// 抛出异常使其执行事务回滚.
					throw new AppException();
				}
			}
		} else {
			logger.error("操作员信息不全，创建操作员信息失败！");
			result.setErrorMsg("操作员信息不全，创建操作员信息失败");
		}
		return result;

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.app.service.operator.OperatorServiceFacade#getOperatorById
	 * (long)
	 */
	@Override
	public TOperator getOperatorById(long operatorId) {
		return operatorDAO.getOperatorById(operatorId);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.app.service.operator.OperatorServiceFacade#getByIdentityMemCode
	 * (java.lang.String, long)
	 */
	@Override
	public TOperator getByIdentityMemCode(String identity, long memberCode) {
		return operatorDAO.getByIdentityMemCode(identity, memberCode);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.app.service.operator.OperatorServiceFacade#getByIdentity
	 * (java.lang.String)
	 */
	@Override
	public TOperator getByIdentity(String identity) {
		// FIXME "admin"需要写到公共对象中。插入admin的操作员时也会有用到。
		if (!StringUtils.equals(identity, "admin")) {
			return operatorDAO.getByIdentity(identity);
		} else {
			return null;
		}
	}

	/**
	 * 
	 * @param memberCode
	 * @param curPageNo
	 * @param pageSize
	 * @return
	 * @see com.pay.app.service.operator.OperatorServiceFacade#getOperatorInfo(long,
	 *      int, int)
	 */
	@Override
	public List<TOperator> getOperatorInfo(long memberCode, int curPageNo,
			int pageSize) {
		int count = operatorDAO.queryCountByMemberCode(memberCode);
		return operatorDAO.getOperatorInfo(memberCode, this.getEndPage(
				curPageNo, pageSize, count), this.getBeginPage(curPageNo,
				pageSize));
	}

	/**
	 * 
	 * @param memberCode
	 * @param curPageNo
	 * @param pageSize
	 * @param operatorName
	 * @param startTime
	 * @param endTime
	 * @param status
	 * @return
	 * @see com.pay.app.service.operator.OperatorServiceFacade#getOperatorLoginInfo(long,
	 *      int, int, java.lang.String, java.lang.String, java.lang.String,
	 *      java.lang.String)
	 */
	@Override
	public List<OperatorExtLoginInfo> getOperatorLoginInfo(long memberCode,
			int curPageNo, int pageSize, String operatorName, String startTime,
			String endTime, String status,int securityLvl,int scale) {
		// 获取总记录数
		int count = operatorDAO.getOperatortLoginCount(memberCode,
				operatorName, startTime, endTime, status);
		// 查询
		List<OperatorExtLoginInfo> operatorList = operatorDAO
				.getOperatorLoginInfo(memberCode, this.getEndPage(curPageNo,
						pageSize, count), this
						.getBeginPage(curPageNo, pageSize), operatorName,
						startTime, endTime, status);
		// 获取授权菜单名称。
		for (OperatorExtLoginInfo operatorExtLoginInfo : operatorList) {
			if (StringUtils.isNotBlank(operatorExtLoginInfo.getMenuArray())) {
				String[] menuIds = StringUtils.split(operatorExtLoginInfo
						.getMenuArray(), ",");
				List<String> menuNames = this.getAllMenu(memberCode, menuIds,securityLvl,scale);
				operatorExtLoginInfo.setMenuNameList(menuNames);
			}
		}
		return operatorList;
	}

	/**
	 * 获取操作员授权菜单
	 * 
	 * @param memberCode
	 * @return
	 */
	private List<String> getAllMenu(long memberCode, String[] menuArray,int securityLvl,int scale) {
		List<String> mList = new ArrayList<String>();
//		List<PowersModel> list = memberFeatureService.getMemberMenu(
//				SecurityLvlEnum.NORMAL.getValue(), ScaleEnum.ENTERPRICE
//						.getValue(), memberCode);
		List<PowersModel> list = memberFeatureService.getMemberMenu(
				securityLvl, scale, memberCode);
		if (list != null && list.size() > 0 && menuArray != null
				&& menuArray.length > 0) {
			// 目前只考虑两级菜单
			for (PowersModel powersModel : list) {
				// 操作员信息中menuArray字段中有菜单的ID，则操作员详细信息中显示该菜单的名称
				for (int i = 0; i < menuArray.length; i++) {
					String string = menuArray[i];
					if (powersModel.getChildlist() != null
							&& powersModel.getChildlist().size() > 0) {
						// 有子菜单的情况
						for (PowersModel powersModel2 : powersModel
								.getChildlist()) {
							if (StringUtils
									.equals(powersModel2.getId(), string)) {
								mList.add(powersModel2.getMenuName());
								break;
							}
						}
					} else {
						// 无子菜单的情况
						if (StringUtils.equals(powersModel.getId(), string)) {
							mList.add(powersModel.getMenuName());
							break;
						}
					}
				}
			}
		}
		return mList;
	}

	/**
	 * 
	 * @param memberCode
	 * @return
	 * @see com.pay.app.service.operator.OperatorServiceFacade#getOperatortLoginCount(long)
	 */
	@Override
	public int getOperatortLoginCount(long memberCode, String operatorName,
			String startTime, String endTime, String status) {
		return operatorDAO.getOperatortLoginCount(memberCode, operatorName,
				startTime, endTime, status);
	}

	/**
	 * 获取最后记录数
	 * 
	 * @param curPage
	 * @param pageSize
	 * @param count
	 * @return
	 */
	private int getEndPage(int curPage, int pageSize, int count) {
		int endPage = curPage * pageSize;
		if (endPage < count) {
			return endPage;
		} else {
			return count;
		}
	}

	/**
	 * 获取开始记录数
	 * 
	 * @param curPage
	 * @param pageSize
	 * @return
	 */
	private int getBeginPage(int curPage, int pageSize) {
		int beginPage = (curPage - 1) * pageSize;
		return beginPage;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.app.service.operator.OperatorServiceFacade#updateOperator
	 * (com.pay.app.model.TOperator)
	 */
	@Override
	public int updateOperator(TOperator operator) {
		if (operator != null) {
			return operatorDAO.updateOperator(operator);
		} else {
			logger.error("操作员信息为空，更新操作员信息失败！");
			return 0;
		}
	}

	/**
	 * 
	 * @param operator
	 * @param menuIds
	 * @return
	 * @throws AppException
	 * @see com.pay.app.service.operator.OperatorServiceFacade#createOperatorMenuRnTx(com.pay.app.model.TOperator,
	 *      java.util.List)
	 */
	public ResultDto createOperatorMenuRnTx(TOperator operator,
			List<Integer> menuIds) throws AppException {
		ResultDto result = new ResultDto();
		// 创建操作员
		ResultDto creResult = this.createOperator(operator);
		if (StringUtils.isNotBlank(creResult.getErrorMsg())) {
			return creResult;
		}
		// 创建操作员菜单权限
		ResultDto menResult = operatorMenuService.createOperatorMenu(
				(Long) creResult.getObject(), menuIds);
		if (StringUtils.isNotBlank(menResult.getErrorMsg())) {
			return menResult;
		}
		return result;
	}

	/**
	 * 
	 * @param operator
	 * @param menuIds
	 * @return
	 * @throws AppException
	 * @see com.pay.app.service.operator.OperatorServiceFacade#modifyOperatorMenuRnTx(com.pay.app.model.TOperator,
	 *      java.util.List)
	 */
	public ResultDto modifyOperatorMenuRnTx(TOperator operator,
			List<Integer> menuIds) throws AppException {
		ResultDto result = new ResultDto();
		if (operator != null && menuIds != null && menuIds.size() > 0) {
			if (operatorDAO.getOperatorById(operator.getOperatorId()) != null) {
				try {
					// 修改操作员信息
					operator
							.setPassword(iMessageDigest
									.genMessageDigest(operator.getPassword()
											.getBytes()));
					if (operatorDAO.updateOperator(operator) > 0) {
						// 修改操作员菜单权限
						if (operatorMenuService.getByOperateId(operator
								.getOperatorId()) != null) {
							if (operatorMenuService.updateByOperatorId(menuIds,
									operator.getOperatorId()) > 0) {
								return result;
							} else {
								logger.error("修改操作员菜单权限失败！operatorId="
										+ operator.getOperatorId() + "menuIds="
										+ menuIds);
								result.setErrorMsg("修改操作员菜单权限失败");
								// 抛出异常使其执行事务回滚.
								throw new AppException();
							}
						} else {
							// 操作员菜单权限为空，则新添加一条操作员菜单权限
							operatorMenuService.createOperatorMenu(operator
									.getOperatorId(), menuIds);
						}
					} else {
						logger.error("修改操作员信息失败！operatorId="
								+ operator.getOperatorId() + "identity="
								+ operator.getIdentity());
						result.setErrorMsg("修改操作员信息失败");
					}
				} catch (Exception e) {
					logger.error("修改操作员系统异常！memberCode="
							+ operator.getMemberCode() + "操作员登录账号="
							+ operator.getIdentity() + "operatorId="
							+ operator.getOperatorId(), e);
					result.setErrorCode("0001");
					result.setErrorMsg("修改操作员系统异常");
					// 抛出异常使其执行事务回滚.
					throw new AppException();
				}
			} else {
				logger.error("操作员不存在！OperatorId=" + operator.getOperatorId());
				result.setErrorMsg("操作员不存在");
			}
		} else {
			logger.error("修改操作员信息时，参数不规范！");
			result.setErrorMsg("修改操作员信息失败");
		}
		return result;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * com.pay.app.service.operator.OperatorServiceFacade#updateOperatorStatus
	 * (long, int)
	 */
	@Override
	public int updateOperatorStatus(long operatorId, int status) {
		return operatorDAO.updateOperatorStatus(operatorId, status);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.pay.app.service.operator.OperatorServiceFacade#
	 * queryCountByMemberCode(long)
	 */
	@Override
	public int queryCountByMemberCode(long memberCode) {
		return operatorDAO.queryCountByMemberCode(memberCode);
	}

	/**
	 * 
	 * @param memberCode
	 * @return
	 * @see com.pay.app.service.operator.OperatorServiceFacade#getOperatorNameByMemCode(long)
	 */
	@Override
	public List<String> getOperatorNameByMemCode(long memberCode) {
		return operatorDAO.getOperatorNameByMemCode(memberCode);
	}

	public void setOperatorDAO(OperatorDAO operatorDAO) {
		this.operatorDAO = operatorDAO;
	}

	public void setiMessageDigest(IMessageDigest iMessageDigest) {
		this.iMessageDigest = iMessageDigest;
	}

	public void setOperatorMenuService(OperatorMenuService operatorMenuService) {
		this.operatorMenuService = operatorMenuService;
	}

	public void setMemberFeatureService(
			MemberFeatureService memberFeatureService) {
		this.memberFeatureService = memberFeatureService;
	}

}
