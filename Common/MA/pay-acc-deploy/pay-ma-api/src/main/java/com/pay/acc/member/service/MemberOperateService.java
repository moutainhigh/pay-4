package com.pay.acc.member.service;

import java.util.List;

import com.pay.acc.member.dto.MemberOperateDto;
import com.pay.acc.member.dto.OperatorDto;
import com.pay.acc.member.memberenum.MemberOperateTypeEnum;

public interface MemberOperateService {

	/**
	 * @param memberOperate
	 */
	public void insert(MemberOperateDto memberOperate);

	/**
	 * @param memberOperate
	 */
	public void update(MemberOperateDto memberOperate);

	/**
	 * 查询登陆操作
	 * 
	 * @param memberCode
	 * @param type
	 * @return
	 */
	public MemberOperateDto queryMemberOperate(Long memberCode, int type);

	/**
	 * 查询支付操作
	 * 
	 * @param memberCode
	 * @param type
	 * @return
	 */
	public MemberOperateDto queryMemberOperate(Long memberCode, int type,
			Integer accType);

	/**
	 * 判断是否已经过了锁定的时间，然后清除错误的次数
	 * 
	 * @param memberOperate
	 * @param outTime
	 * @return
	 */
	public boolean cleanFailTime(MemberOperateDto memberOperate, int outTime);

	/**
	 * @param memberOperate
	 * @return
	 */
	public MemberOperateDto createMemberOperate(Long memberCode, Long type,
			Integer accType);

	public MemberOperateDto getMemberOperate(long memberCode,
			MemberOperateTypeEnum mote);

	public List<MemberOperateDto> queryAccountMemberOperate(Long memberCode,
			int type);

	public OperatorDto queryOperatorByOperatorId(Long operatorId);

	/**
	 * 根据会员号和操作员标识查询操作员信息
	 * 
	 * @param memberCode
	 * @param identity
	 * @return
	 */
	public OperatorDto queryOperatorByMemberCode(Long memberCode,
			String identity);

	/**
	 * 根据会员号判断（半小时内错误登录次数是否大于等于3次）
	 * 
	 * @param memberCode
	 * @return true（超出3次）/false
	 */
	public boolean isErrLoginWarning(Long memberCode);
}
