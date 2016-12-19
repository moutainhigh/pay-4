/*
 * Copyright © 2004-2013 pay.com . All rights reserved. 
 */
package com.pay.base.service.operator.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.base.dao.operator.OperatorDAO;
import com.pay.base.dto.MenuDto;
import com.pay.base.dto.ResultDto;
import com.pay.base.model.Operator;
import com.pay.base.model.OperatorExtLoginInfo;
import com.pay.base.model.UserLog;
import com.pay.base.service.featuremenu.FeatureMenuService;
import com.pay.base.service.operator.OperatorMenuService;
import com.pay.base.service.operator.OperatorServiceFacade;
import com.pay.base.service.user.UserLogService;
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

    private static final Log     logger = LogFactory.getLog(OperatorServiceFacadeImpl.class);

    private OperatorDAO          operatorDAO;

    private IMessageDigest       iMessageDigest;

    private OperatorMenuService  operatorMenuService;

    /** 授权操作员菜单 */
    private FeatureMenuService   featureMenuService;
    
    private UserLogService       userLogService;

    /*
     * (non-Javadoc)
     * 
     * @see
     * com.pay.app.service.operator.OperatorServiceFacade#createOperator
     * (com.pay.app.model.TOperator)
     */
    @Override
    public ResultDto createOperator(Operator operator) throws AppException {
        ResultDto result = new ResultDto();
        if (operator != null && operator.getMemberCode() > 0
            && StringUtils.isNotBlank(operator.getIdentity())
            && StringUtils.isNotBlank(operator.getPassword())) {
            /* 确保有效状态操作员的登录名是唯一的 */
            // 判断登录名是否已被占用
            Operator oper = operatorDAO.getByIdentityMemCode(operator.getIdentity(),operator.getMemberCode());
            if (oper != null) {
                logger.warn("操作员的登录账号已被占用！identity=" + operator.getIdentity());
                result.setErrorMsg(operator.getIdentity() + "操作员的登录账号已被占用！");
            } else {
                try {
                	if(StringUtils.isEmpty(operator.getPassword())){
                		result.setErrorMsg("操作员登录密码不能为空！");
                	}/*else if(StringUtils.isEmpty(operator.getPayPassWord())){
                		result.setErrorMsg("操作员支付密码不能为空！");
                	}else if(operator.getPassword().equals(operator.getPayPassWord())){
                		result.setErrorMsg("登录密码与支付密码不能相同！");
                	}*/else{
                		operator.setPassword(iMessageDigest.genMessageDigest(operator.getPassword().getBytes()));
                            operator.setPayPassWord(operator.getPayPassWord()==null?"":iMessageDigest.genMessageDigest(operator.getPayPassWord().getBytes()));
                            operatorDAO.createOperator(operator);
                            Operator creOperator = operatorDAO.getByIdentityMemCode(operator.getIdentity(), operator.getMemberCode());
                            result.setObject(creOperator.getOperatorId());
                	}
                    
                } catch (Exception e) {
                    logger.error("创建操作员系统异常！memberCode=" + operator.getMemberCode() + "操作员登录账号="
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
    public Operator getOperatorById(long operatorId) {
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
    public Operator getByIdentityMemCode(String identity, long memberCode) {
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
    public Operator getByIdentity(String identity) {
        // 由于初始化企业信息时，默认生成一个identity为admin的操作员。所以这里不能根据identity为admin来查单个操作员信息
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
     * @see com.pay.base.service.operator.OperatorServiceFacade#getOperatorInfo(long, int, int)
     */
    @Override
    public List<Operator> getOperatorInfo(long memberCode, int curPageNo, int pageSize) {
        int count = operatorDAO.queryCountByMemberCode(memberCode);
        return operatorDAO.getOperatorInfo(memberCode, this.getEndPage(curPageNo, pageSize, count),
            this.getBeginPage(curPageNo, pageSize));
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
     * @see com.pay.app.service.operator.OperatorServiceFacade#getOperatorLoginInfo(long, int, int, java.lang.String, java.lang.String, java.lang.String, java.lang.String)
     */
    @Override
    public List<OperatorExtLoginInfo> getOperatorLoginInfo(long memberCode, int curPageNo,
                                                           int pageSize, String operatorName,
                                                           String startTime, String endTime,
                                                           String status) {
        // 获取总记录数
        int count = operatorDAO.getOperatortLoginCount(memberCode, operatorName, startTime, endTime, status);
        // 查询
        List<OperatorExtLoginInfo> operatorList = operatorDAO.getOperatorLoginInfo(memberCode, this
            .getEndPage(curPageNo, pageSize, count), this.getBeginPage(curPageNo, pageSize),
            operatorName, startTime, endTime, status);
        // 获取授权菜单名称。
        for (OperatorExtLoginInfo operatorExtLoginInfo : operatorList) {
            if (StringUtils.isNotBlank(operatorExtLoginInfo.getMenuArray())) {
                List<String> menuNames = this.getAllMenu(memberCode, operatorExtLoginInfo.getMenuArray());
                operatorExtLoginInfo.setMenuNameList(menuNames);
            }
        }
        return operatorList;
    }

    /**
     * 获取操作员功能权限
     *
     * @param memberCode
     * @return
     */
    private List<String> getAllMenu(long memberCode, String menuArray) {
        List<String> mList = new ArrayList<String>();
        List<MenuDto> menuList = featureMenuService.queryMenuByIdsArray(menuArray);
        if(menuList != null && menuList.size() >0){
        	for (MenuDto menuDto : menuList) {
				if((menuDto.getType() == 6 || menuDto.getType() == 9) && menuDto.getDisplayFlag() == 1){
					mList.add(menuDto.getName());
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
    public int getOperatortLoginCount(long memberCode,String operatorName,
                                      String startTime, String endTime,
                                      String status) {
        return operatorDAO.getOperatortLoginCount(memberCode, operatorName, startTime, endTime, status);
    }

    /**
     * 获取最后记录数
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
    public int updateOperator(Operator operator) {
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
     * @see com.pay.app.service.operator.OperatorServiceFacade#createOperatorMenuRnTx(com.pay.app.model.TOperator, java.util.List)
     */
    public ResultDto createOperatorMenuRnTx(Operator operator, List<Integer> menuIds)
                                                                                      throws AppException {
        ResultDto result = new ResultDto();
        // 创建操作员
        ResultDto creResult = this.createOperator(operator);
        if (StringUtils.isNotBlank(creResult.getErrorMsg())) {
            return creResult;
        }
        // 创建操作员菜单权限
        ResultDto menResult = operatorMenuService.createOperatorMenu((Long) creResult.getObject(),
            menuIds);
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
     * @see com.pay.app.service.operator.OperatorServiceFacade#modifyOperatorMenuRnTx(com.pay.app.model.TOperator, java.util.List)
     */
    public ResultDto modifyOperatorMenuRnTx(Operator operator, List<Integer> menuIds)
                                                                                      throws AppException {
        ResultDto result = new ResultDto();
        if (operator != null && menuIds != null && menuIds.size() > 0) {
        	Operator op=operatorDAO.getOperatorById(operator.getOperatorId()) ;
            if (op!= null && !op.getName().equals("admin")) {
                try {
                    if (operatorDAO.updateOperator(operator) > 0) {
                        // 修改操作员菜单权限
                        if (operatorMenuService.getByOperateId(operator.getOperatorId()) != null) {
                            if (operatorMenuService.updateByOperatorId(menuIds, operator
                                .getOperatorId()) > 0) {
                                return result;
                            } else {
                                logger.error("修改操作员菜单权限失败！operatorId=" + operator.getOperatorId()
                                             + "menuIds=" + menuIds);
                                result.setErrorMsg("修改操作员菜单权限失败");
                                // 抛出异常使其执行事务回滚.
                                throw new AppException();
                            }
                        } else {
                            // 操作员菜单权限为空，则新添加一条操作员菜单权限
                            operatorMenuService.createOperatorMenu(operator.getOperatorId(),
                                menuIds);
                        }
                    } else {
                        logger.error("修改操作员信息失败！operatorId=" + operator.getOperatorId()
                                     + "identity=" + operator.getIdentity());
                        result.setErrorMsg("修改操作员信息失败");
                    }
                } catch (Exception e) {
                    logger.error("修改操作员系统异常！memberCode=" + operator.getMemberCode() + "操作员登录账号="
                                 + operator.getIdentity() + "operatorId="
                                 + operator.getOperatorId(), e);
                    result.setErrorCode("0001");
                    result.setErrorMsg("修改操作员系统异常");
                    // 抛出异常使其执行事务回滚.
                    throw new AppException();
                }
            } else {
                logger.error("操作员不存在！OperatorId=" + operator.getOperatorId());
                result.setErrorMsg("操作员不存在！");
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
    public int updateOperatorStatus(long memberCode ,long operatorId, int status) {
        return operatorDAO.updateOperatorStatus(memberCode,operatorId, status);
    }

    
    @Override
    public boolean updateOperatorPayPWD(Long memberCode ,Long operatorId, String payPassWord) {
    	int result=0;
    		if(StringUtils.isNotBlank(payPassWord) && operatorId != null && memberCode!=null){
                try {
                    String pwd = iMessageDigest.genMessageDigest(payPassWord.getBytes());
                    result= operatorDAO.updateOperatorPayPassWord(memberCode, operatorId, pwd);
                } catch (Exception e) {
                    logger.error("修改操作员支付 密码异常！opeatorId="+operatorId, e);
                }
            }
        return (result==1);
    }
    /**
     * 
     * @param password
     * @param operatorId
     * @return
     * @see com.pay.base.service.operator.OperatorServiceFacade#updateOperatorPWD(java.lang.String, java.lang.Long)
     */
    public boolean updateOperatorPWD(String password,Long operatorId,Long memberCode){
        if(StringUtils.isNotBlank(password) && operatorId != null){
            try {
                String pwd = iMessageDigest.genMessageDigest(password.getBytes());
                return (operatorDAO.updateOperatorPWD(pwd, operatorId,memberCode)==1);
            } catch (Exception e) {
                logger.error("修改操作员登录密码异常！opeatorId="+operatorId, e);
            }
        }
        return false;
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
    
    public int getOperatorCountForEndLog(long memberCode){
        return operatorDAO.getOperatorPageCount(memberCode);
    }
    /**
     * 
     * @param memberCode
     * @param curPageNo
     * @param pageSize
     * @return
     * @see com.pay.base.service.operator.OperatorServiceFacade#getOperatorInfoForEndLog(long, int, int)
     */
    @Override
    public List<OperatorExtLoginInfo> getOperatorInfoForEndLog(long memberCode, int curPageNo,
        int pageSize) {
        int count = operatorDAO.getOperatorPageCount(memberCode);
        List<OperatorExtLoginInfo> list = operatorDAO.getOperatorPageInfo(memberCode, this.getEndPage(curPageNo, pageSize, count), this.getBeginPage(curPageNo, pageSize));
        if(list != null && list.size() > 0){
            List<Long> operatorIds = new ArrayList<Long>();
            for (OperatorExtLoginInfo operatorExtLoginInfo : list) {
                operatorIds.add(operatorExtLoginInfo.getOperatorId());
            }
            // 获取操作员最后登录信息
            List<UserLog> userLogList = userLogService.getOperatorEndLogInfo(operatorIds);
            if(userLogList != null && userLogList.size() > 0) {
                for (OperatorExtLoginInfo operatorExtLoginInfo : list) {
                    for (UserLog userLog : userLogList) {
                        if(userLog.getOperatorId().equals(operatorExtLoginInfo.getOperatorId())){
                            operatorExtLoginInfo.setLoginDate(userLog.getLoginDate());
                            operatorExtLoginInfo.setLoginIP(userLog.getLoginIp());
                            operatorExtLoginInfo.setLoginName(userLog.getLoginName());
                        }
                    }
                }
            }
            
            // 获取授权菜单名称。
            for (OperatorExtLoginInfo operatorExtLoginInfo : list) {
                if (StringUtils.isNotBlank(operatorExtLoginInfo.getMenuArray())) {
                    List<String> menuNames = this.getAllMenu(memberCode, operatorExtLoginInfo.getMenuArray());
                    operatorExtLoginInfo.setMenuNameList(menuNames);
                }
            }
        }
        return  list;
    }
    public Operator getAdminOperator(Long memberCode){
    	if(memberCode != null){
    		return operatorDAO.getAdminOperator(memberCode);
    	}
    	return null;
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

    
    public void setFeatureMenuService(FeatureMenuService featureMenuService) {
		this.featureMenuService = featureMenuService;
	}

	public void setUserLogService(UserLogService userLogService) {
        this.userLogService = userLogService;
    }

	/* (non-Javadoc)
	 * @see com.pay.base.service.operator.OperatorServiceFacade#getbindMobileByMeberCodeOperatorId(java.lang.Long, java.lang.Long)
	 */
	@Override
	public String getBindMobileByMeberCodeOperatorId(Long memberCode,
			Long operatorId) {
		Operator operator = null;
		if(operatorId>0L){
			 operator = this.getOperatorById(operatorId);
		}else{
			 operator = this.getAdminOperator(memberCode);
		}
		return operator!=null ? operator.getMobile() : null;

	}

	/* (non-Javadoc)
	 * @see com.pay.base.service.operator.OperatorServiceFacade#checkOperatorPaymentPwd(java.util.Map)
	 */
	@Override
	public Operator checkOperatorPaymentPwd(Map<String, String> hMap) {
		return this.operatorDAO.checkOperatorPaymentPwd(hMap) ;
	}

	

}
