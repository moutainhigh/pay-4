/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.app.service.operator;

import java.util.List;

import com.pay.app.facade.dto.ResultDto;
import com.pay.app.model.OperatorMenu;
import com.pay.inf.exception.AppException;

/**
 * 操作员菜单权限
 * @author wangzhi
 * @version $Id: OperatorMenuService.java, v 0.1 2010-9-25 上午11:26:02 wangzhi Exp $
 */
public interface OperatorMenuService {
    
    /**
     * 创建操作员菜单权限
     * @param operatorId
     * @param menuIds
     * @return
     * @throws AppException
     */
    public ResultDto createOperatorMenu(long operatorId,List<Integer> menuIds) throws AppException;
    
    /**
     * 更新操作员菜单权限
     * @param menuIds
     * @param operatorId
     * @return
     */
    public int updateByOperatorId(List<Integer> menuIds, long operatorId);
    
    /**
     * 根据主键获取菜单权限
     * @param opMenuId
     * @return
     */
    public OperatorMenu getByOpMenuId(long opMenuId);
    
    /**
     * 根据操作员ID获取菜单权限
     * @param operatorId
     * @return
     */
    public OperatorMenu getByOperateId(long operatorId);
}
