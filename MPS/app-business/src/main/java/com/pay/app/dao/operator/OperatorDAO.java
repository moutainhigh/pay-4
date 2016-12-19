/*
 * Copyright © 2004-2013 pay.com . All rights reserved. 
 */
package com.pay.app.dao.operator;

import java.util.List;

import com.pay.app.model.OperatorExtLoginInfo;
import com.pay.app.model.TOperator;

/**
 * 操作员信息DAO
 * 
 * @author wangzhi
 * @version
 * @data 2010-9-20 下午06:22:14
 * 
 */
public interface OperatorDAO {

    /**
     * 创建操作员
     * @param operator
     */
    public void createOperator(TOperator operator);

    /**
     * 根据操作ID获取操作员信息
     * @param operatorId
     * @return
     */
    public TOperator getOperatorById(long operatorId);

    /**
     * 根据操作员登录名和会员号获取有效状态(0创建，1正常，2冻结)的操作员信息
     * @param identity
     * @param memberCode
     * @return
     */
    public TOperator getByIdentityMemCode(String identity, long memberCode);

    /**
     * 根据操作员登录名获取有效状态(0创建，1正常，2冻结)的操作员信息
     * @param identity
     * @param memberCode
     * @return
     */
    public TOperator getByIdentity(String identity);

    /**
     * 根据会员号(管理员)获取操作员信息列表（分页处理）
     *
     * @param memberCode
     * @param endNum
     * @param beginNum
     * @return
     */
    public List<TOperator> getOperatorInfo(long memberCode, Integer endNum, Integer beginNum);

    /**
     * 根据会员号获取操作员基本信息和登录信息的列表（分页处理）
     *
     * @param memberCode
     * @param endNum
     * @param beginNum
     * @param operatorName
     * @param startTime
     * @param endTime
     * @param status
     * @return
     */
    public List<OperatorExtLoginInfo> getOperatorLoginInfo(long memberCode, Integer endNum,
                                                           Integer beginNum, String operatorName,
                                                           String startTime, String endTime, 
                                                           String status);

    /**
     * 根据会员号获取操作员基本信息和登录信息的总数（分页处理）
     *
     * @param memberCode
     * @param operatorName
     * @param startTime
     * @param endTime
     * @param status
     * @return
     */
    public int getOperatortLoginCount(long memberCode,String operatorName,
                                      String startTime, String endTime,
                                      String status);

    /**
     * 更新操作员基本信息
     * @param operator
     * @return
     */
    public int updateOperator(TOperator operator);

    /**
     * 根据操作员ID更新操作员状态
     * @param operatorId
     * @param status
     * @return
     */
    public int updateOperatorStatus(long operatorId, int status);

    /**
     * 根据会员号查询操作员总记录数
     * @param memberCode
     * @return
     */
    public int queryCountByMemberCode(long memberCode);

    /** 操作员登录
     * @param loginName
     * @param loginPwd
     * @return
     * @throws  
     */
    public TOperator getByLogin(String identity, String loginPwd, Long memberCode);
    
    /**
     * 根据会员号获取所有操作员姓名
     * @param memberCode
     * @return
     */
    public List<String> getOperatorNameByMemCode(long memberCode);
}
