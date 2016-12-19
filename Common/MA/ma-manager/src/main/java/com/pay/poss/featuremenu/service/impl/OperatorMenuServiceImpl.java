/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.poss.featuremenu.service.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.inf.exception.AppException;
import com.pay.poss.featuremenu.dao.OperatorMenuDAO;
import com.pay.poss.featuremenu.dto.ResultDto;
import com.pay.poss.featuremenu.model.OperatorMenu;
import com.pay.poss.featuremenu.service.OperatorMenuService;

/**
 *
 * @author wangzhi
 * @version $Id: OperatorMenuServiceImpl.java, v 0.1 2010-9-25 上午11:26:43 wangzhi Exp $
 */
public class OperatorMenuServiceImpl implements OperatorMenuService {

    private static final Log logger = LogFactory.getLog(OperatorMenuServiceImpl.class);

    private OperatorMenuDAO  operatorMenuDAO;

    /**
     * 
     * @param operatorId
     * @param menuIds
     * @return
     * @throws AppException
     * @see com.pay.app.service.operator.OperatorMenuService#createOperatorMenu(long, java.util.List)
     */
    @Override
    public ResultDto createOperatorMenu(long operatorId, List<Integer> menuIds) throws AppException{
        ResultDto result = new ResultDto();
        if (operatorId > 0 && menuIds != null && menuIds.size() > 0) {
            OperatorMenu operatorMenu = new OperatorMenu();
            StringBuffer menuArray = new StringBuffer();
            for (Integer integer : menuIds) {
                if (StringUtils.isNotBlank(menuArray.toString())) {
                    menuArray.append(",").append(integer);
                } else {
                    menuArray.append(integer);
                }
            }
            operatorMenu.setMenuArray(menuArray.toString());
            operatorMenu.setOperateId(operatorId);
            try{
                operatorMenuDAO.createOperatorMenu(operatorMenu);
            }catch(Exception ex){
              logger.error("创建操作员菜单权限异常!operatorId="+operatorId+"menuIds="+menuIds,ex);
              result.setErrorMsg("创建操作员菜单权限异常");
              throw new AppException();
            }
        } else {
            logger.error("创建操作员菜单权限时，参数不规范！operatorId=" + operatorId);
            result.setErrorMsg("创建操作员菜单权参数不规范");
        }
        return result;
    }

    /**
     * 
     * @param opMenuId
     * @return
     * @see com.pay.app.service.operator.OperatorMenuService#getByOpMenuId(long)
     */
    @Override
    public OperatorMenu getByOpMenuId(long opMenuId) {
        return operatorMenuDAO.getByOpMenuId(opMenuId);
    }

    /**
     * 
     * @param operatorId
     * @return
     * @see com.pay.app.service.operator.OperatorMenuService#getByOperateId(long)
     */
    @Override
    public OperatorMenu getByOperateId(long operatorId) {
        return operatorMenuDAO.getByOperateId(operatorId);
    }

    /**
     * 
     * @param menuIds
     * @param operatorId
     * @return
     * @see com.pay.app.service.operator.OperatorMenuService#updateByOperatorId(java.util.List, long)
     */
    @Override
    public int updateByOperatorId(List<Integer> menuIds, long operatorId) {
        if (operatorId > 0 && menuIds != null && menuIds.size() > 0) {
            StringBuffer menuArray = new StringBuffer();
            for (Integer integer : menuIds) {
                if (StringUtils.isNotBlank(menuArray.toString())) {
                    menuArray.append(",").append(integer);
                } else {
                    menuArray.append(integer);
                }
            }
            return operatorMenuDAO.updateByOperatorId(menuArray.toString(), operatorId);
        } else {
            logger.error("更新操作员菜单权限时，参数不规范！operatorId=" + operatorId);
            return 0;
        }
    }

    public void setOperatorMenuDAO(OperatorMenuDAO operatorMenuDAO) {
        this.operatorMenuDAO = operatorMenuDAO;
    }

}
