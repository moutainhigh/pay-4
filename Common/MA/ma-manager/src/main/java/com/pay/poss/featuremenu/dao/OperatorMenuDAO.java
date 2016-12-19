/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.poss.featuremenu.dao;

import com.pay.poss.featuremenu.model.OperatorMenu;





/**
 *
 * @author wangzhi
 * @version $Id: OperatorMenuDAO.java, v 0.1 2010-9-25 上午11:09:24 wangzhi Exp $
 */
public interface OperatorMenuDAO {
    
    /**
     * 创建操作员菜单权限
     * @param operatorMenu
     * @return
     */
    public void createOperatorMenu(OperatorMenu operatorMenu);
    
    /**
     * 更新操作员菜单权限
     * @param menuArray
     * @param operatorId
     * @return
     */
    public int updateByOperatorId(String menuArray,long operatorId);
    
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
