/*
 * Copyright (c) 2004-2013 pay.com . All rights reserved. 
 */
package com.pay.base.service.operator;

import java.util.List;
import java.util.Map;

import com.pay.base.dto.ResultDto;
import com.pay.base.model.Operator;
import com.pay.base.model.OperatorExtLoginInfo;
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
    public ResultDto createOperator(Operator operator) throws AppException;

    /**
     * 根据操作ID获取操作员信息
     * @param operatorId
     * @return
     */
    public Operator getOperatorById(long operatorId);

    /**
     * 根据操作员登录名和会员号获取有效状态(0创建，1正常，2冻结)的操作员信息
     * @param identity
     * @param memberCode
     * @return
     */
    public Operator getByIdentityMemCode(String identity, long memberCode);

    /**
     * 根据操作员登录名获取有效状态(0创建，1正常，2冻结)的操作员信息<br>
     * 不包括管理员类型的操作员。因为每个企业会员都有一个默认admin登录名的操作员
     * @param identity
     * @param memberCode
     * @return
     */
    public Operator getByIdentity(String identity);

    /**
     * 根据会员号(管理员)获取操作员信息列表(分页处理)
     * @param memberCode
     * @param curPageNo
     * @param pageSize
     * @return
     */
    public List<Operator> getOperatorInfo(long memberCode, int curPageNo, int pageSize);

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
                                                           String status);

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
    public int getOperatortLoginCount(long memberCode, String operatorName, String startTime,
                                      String endTime, String status);

    /**
     * 更新操作员基本信息
     * @param operator
     * @return
     */
    public int updateOperator(Operator operator);

    /**
     * 根据操作员ID和memberCode更新操作员状态
     *
     * @param memberCode
     * @param operatorId
     * @param status
     * @return
     */
    public int updateOperatorStatus(long memberCode, long operatorId, int status);

    /**
     * 更改操作员登录密码
     *
     * @param pwd
     * @param operatorId
     * @return
     */
    public boolean updateOperatorPWD(String password,Long operatorId,Long memberCode);
    
    
    public boolean updateOperatorPayPWD(Long memberCode ,Long operatorId, String payPassWord);
    /**
     * 创建操作员和操作员功能权限 
     * @param operator
     * @param menuIds
     * @return
     * @throws AppException
     */
    public ResultDto createOperatorMenuRnTx(Operator operator, List<Integer> menuIds)
                                                                                     throws AppException;

    /**
     * 修改操作员基本信息和操作员功能权限 
     * @param operator
     * @param menuIds
     * @return
     * @throws AppException
     */
    public ResultDto modifyOperatorMenuRnTx(Operator operator, List<Integer> menuIds)
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

    /**
     * 获取操作员最后登录信息
     *
     * @param memberCode
     * @param curPageNo
     * @param pageSize
     * @return
     */
    public List<OperatorExtLoginInfo> getOperatorInfoForEndLog(long memberCode, int curPageNo,
                                                               int pageSize);
    
    /**
     * 获取操作员最后登录信息记录条数
     *
     * @param memberCode
     * @return
     */
    public int getOperatorCountForEndLog(long memberCode);
    /**
     * 获取管理员
     * @param memberCode
     * @return
     */
    public Operator getAdminOperator(Long memberCode);
    
    
    /**
     * 查询操作员绑定的手机 号
     * @param memberCode
     * @param operatorId
     * @return mobile/null
     */
    public String getBindMobileByMeberCodeOperatorId(Long memberCode,Long operatorId);
    
    /**
     * 校验操作员的支付密码
     * @param hMap
     * @return
     */
    Operator checkOperatorPaymentPwd(Map<String, String> hMap) ;
}
