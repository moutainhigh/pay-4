/*
 * Copyright (c) 2004-2013 pay.com . All rights reserved. 
 */
package com.pay.app.service.operator;

import java.util.List;

import com.pay.app.facade.dto.ResultDto;
import com.pay.app.model.OperatorExtLoginInfo;
import com.pay.app.model.TOperator;
import com.pay.inf.exception.AppException;

/**
 * 操作员信息服务
 * 
 * @author wangzhi
 * @version
 * @data 2010-9-20 下午07:55:23
 * 
 */
public interface OperatorServiceFacade {

    /**
     * 创建操作员
     * @param operator
     */
    public ResultDto createOperator(TOperator operator) throws AppException;

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
     * 根据操作员登录名获取有效状态(0创建，1正常，2冻结)的操作员信息<br>
     * 不包括管理员类型的操作员。因为每个企业会员都有一个默认admin登录名的操作员
     * @param identity
     * @param memberCode
     * @return
     */
    public TOperator getByIdentity(String identity);

    /**
     * 根据会员号(管理员)获取操作员信息列表(分页处理)
     * @param memberCode
     * @param curPageNo
     * @param pageSize
     * @return
     */
    public List<TOperator> getOperatorInfo(long memberCode, int curPageNo, int pageSize);

    /**
     * 根据会员号获取操作员基本信息和登录信息的列表(分页处理)
     *
     * @param memberCode
     * @param curPageNo
     * @param pageSize
     * @param operatorName
     * @param startTime
     * @param endTime
     * @param status
     * @return
     */
    public List<OperatorExtLoginInfo> getOperatorLoginInfo(long memberCode, int curPageNo,
                                                           int pageSize, String operatorName,
                                                           String startTime, String endTime,
                                                           String status,int securityLvl,int scale);

    /**
     * 根据会员号获取操作员基本信息和登录信息的总记录总数（分页处理）
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
     * 创建操作员和操作员功能权限 
     * @param operator
     * @param menuIds
     * @return
     * @throws AppException
     */
    public ResultDto createOperatorMenuRnTx(TOperator operator, List<Integer> menuIds)
                                                                                      throws AppException;

    /**
     * 修改操作员基本信息和操作员功能权限 
     * @param operator
     * @param menuIds
     * @return
     * @throws AppException
     */
    public ResultDto modifyOperatorMenuRnTx(TOperator operator, List<Integer> menuIds)
                                                                                      throws AppException;

    /**
     * 根据会员号查询操作员总记录数
     * @param memberCode
     * @return
     */
    public int queryCountByMemberCode(long memberCode);
    
    /**
     * 根据会员号获取所有操作员姓名
     * @param memberCode
     * @return
     */
    public List<String> getOperatorNameByMemCode(long memberCode);
}
