package com.pay.fundout.withdraw.service.autorisk;

import java.math.BigDecimal;
import java.util.Date;
import java.util.List;

import com.pay.fundout.withdraw.dto.autorisk.IpDto;
import com.pay.fundout.withdraw.dto.autorisk.MemBerDto;
import com.pay.fundout.withdraw.dto.autorisk.TransSumDto;
import com.pay.fundout.withdraw.dto.autorisk.TransWebsiteDto;
import com.pay.inf.dao.Page;

/**
 * 根据会员号查询各种配置数据接口，主要都是自动过风控一些相关的fo，ma，fi等地方的接口，sql统一写在fo这边
 * 
 * @author meng.li
 * 
 */
public interface AutoRiskInterfaceService {

	/**
	 * 根据会员号、起止时间查询登录失败次数
	 * @param memberCode 会员号
	 * @param beginDate 起始时间
	 * @param endDate 结束时间
	 * @return 登录失败次数
	 */
	int queryLoginFailNum(Long memberCode, Date beginDate, Date endDate);
	
	/**
	 * 根据会员号、起止时间查询登录IP是否关联其他账号
	 * @param memberCode 会员号
	 * @param beginDate 起始时间
	 * @param endDate 结束时间
	 * @return 是否关联其他账户，true表示关联，false表示不关联
	 */
	boolean isIpConnected(Long memberCode, Date beginDate, Date endDate);
	
	/**
	 * 根据会员号查询出款次数
	 * @param memberCode 会员号
	 * @return 出款次数
	 */
	int queryFundOutTimes(Long memberCode);
	
	/**
	 * 查询是否第一次批量出款
	 * @param memberCode 会员号
	 * @param memberCode 批次号
	 * @return 批量出款次数，0表示第一次，其他表示不是
	 */
	int queryBatchFundOutTimes(Long memberCode, Long parentOrderid);
	
	/**
	 * 根据会员号、起止时间查询收款域名数量
	 * @param memberCode 会员号
	 * @param beginDate 起始时间
	 * @param endDate 结束时间
	 * @return 收款域名数量
	 */
	int queryFundInNums(Long memberCode, Date beginDate, Date endDate);
	
	/**
	 * 根据会员号、起止时间查询最大收款金额
	 * @param memberCode 会员号
	 * @param beginDate 起始时间
	 * @param endDate 结束时间
	 * @return 最大收款金额
	 */
	BigDecimal queryFundInMaxAmount(Long memberCode, Date beginDate, Date endDate);
	
	/**
	 * 根据会员号、起止时间查询总收款金额
	 * @param memberCode
	 * @param beginDate
	 * @param endDate
	 * @return
	 */
	BigDecimal queryFundInAmount(Long memberCode, Date beginDate, Date endDate);
	
	/**
	 * 根据个人会员号查询个人会员明细信息
	 * @param memberCode 个人会员号
	 * @return 会员信息实体
	 */
	MemBerDto queryIndividualInfo(Long memberCode);
	
	/**
	 * 根据企业会员号查询企业会员明细信息
	 * @param memberCode 企业会员号
	 * @return 会员信息实体
	 */
	MemBerDto queryEnterpriseInfo(Long memberCode);

	/**
	 * 根据会员号查找关联IP实体
	 * @param page 分页信息
	 * @param memberCode 会员号
	 * @return Ip信息实体
	 */
	Page<IpDto> queryIpInfo(Page<IpDto> page, Long memberCode);
	
	/**
	 * 根据会员号查找最近12个月交易汇总信息
	 * @param memberCode 会员号
	 * @return 汇总信息列表
	 */
	List<TransSumDto> queryTransSumInfo(Long memberCode);
	
	/**
	 * 根据会员号查找交易网址
	 * @param memberCode 会员号
	 * @return 交易网址统计列表
	 */
	List<TransWebsiteDto> queryTransWebsiteInfo(Long memberCode);
}
