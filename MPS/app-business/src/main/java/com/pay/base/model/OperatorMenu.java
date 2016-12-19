/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.base.model;

import java.util.Date;

/**
 * 操作员菜单
 * @author wangzhi
 * @version $Id: OperatorMenu.java, v 0.1 2010-9-25 上午10:47:42 wangzhi Exp $
 */
public class OperatorMenu {
    /** 主键 */
    private long opMenuId;
    /** 操作员ID */
    private long operateId;
    /** 功能权限ID(多个以逗号分隔)*/
    private String menuArray;
    /** 创建时间 */
    private Date createDate;
    /** 修改时间 */
    private Date updateDate;
    
    public long getOpMenuId() {
        return opMenuId;
    }
    public void setOpMenuId(long opMenuId) {
        this.opMenuId = opMenuId;
    }
    public long getOperateId() {
        return operateId;
    }
    public void setOperateId(long operateId) {
        this.operateId = operateId;
    }
    public String getMenuArray() {
        return menuArray;
    }
    public void setMenuArray(String menuArray) {
        this.menuArray = menuArray;
    }
    public Date getCreateDate() {
        return createDate;
    }
    public void setCreateDate(Date createDate) {
        this.createDate = createDate;
    }
    public Date getUpdateDate() {
        return updateDate;
    }
    public void setUpdateDate(Date updateDate) {
        this.updateDate = updateDate;
    }
    
    
}
