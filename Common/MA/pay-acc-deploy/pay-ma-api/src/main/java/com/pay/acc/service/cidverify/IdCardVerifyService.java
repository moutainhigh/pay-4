package com.pay.acc.service.cidverify;

import java.sql.SQLException;

import com.pay.acc.member.dto.IdcVerifyDto;
import com.pay.acc.service.cidverify.dto.Cid2GovDTO;
import com.pay.acc.service.cidverify.dto.CidResult;
import com.pay.acc.service.member.dto.MemberVerifyDto;
import com.pay.inf.exception.AppException;

/**
 * @author lei.jiangl
 * @version
 * @data 2010-9-11 下午12:24:35 实名认证服务
 */
public interface IdCardVerifyService {

	/**
	 * 通过公安网认证进行实名认证
	 * 
	 * @throws Exception
	 */
	public CidResult cid2Gov(Cid2GovDTO dto) throws Exception;

	/**
	 * 国政通实名认证
	 * 
	 * @param name
	 * @param cardNo
	 * @return
	 * @throws Exception
	 */
	boolean validator(String name, String cardNo) throws Exception;

	/**
	 * 保存保存通过公安网认证的数据
	 * 
	 * @return IdcVerifyBase.id
	 */
	public Long saveCidVerify2Gov(MemberVerifyDto memberVerifyDto)
			throws Exception;

	/**
	 * 保存银行卡鉴权认证的数据
	 * 
	 * @return IdcVerifyBase.id
	 */
	public Long saveCidVerify2Bank(MemberVerifyDto memberVerifyDto)
			throws Exception;

	/**
	 * 通过银行进行卡鉴权
	 * 
	 * @author Sunny Ying
	 * @description TODO
	 * @param bankNo
	 *            银行卡号
	 * @param custName
	 *            用户姓名 可为空
	 * @param cardNo
	 *            身份证号码
	 * @throws Exception
	 * @return boolean
	 */
	public boolean cidVerifyByBank(String bankNo, String custName, String certNo)
			throws Exception;

	/**
	 * 在连公网之前预先给用户 认证中的状态
	 * 
	 * @author Sunny Ying
	 * @param memberCode
	 *            会员code
	 * @param name
	 *            姓名
	 * @param cardNo
	 *            身份证号码
	 * @throw null
	 * @return Long 公安网 id
	 */
	public Long addCidVerifyDefaultState(String memberCode, String name,
			String cardNo);

	/**
	 * 
	 * @author Sunny Ying
	 * @param idcId
	 *            主键 id
	 * @param status
	 *            状态
	 * @param xmlResult
	 *            公安网返回数据
	 * @throw null
	 * @return int
	 */
	public int editIdcVerify(Long idcId, int status, String xmlResult);

	/**
	 * 根据会员的编号查询用户的实名认证状态
	 * 
	 * @author Sunny Ying
	 * @param memberCode
	 * @throw null
	 * @return int
	 */
	public int getMemberVerifyState(Long memberCode);

	/**
	 * 更新用户的实名认证状态
	 * 
	 * @author Sunny Ying
	 * @param idcId
	 * @param status
	 * @throw null
	 * @return int
	 */
	public int updateMemberVerifyStatus(Long idcId, Integer status);

	/**
	 * 添加一条交易记录日志
	 * 
	 * @author Sunny Ying
	 * @param serialNo
	 *            交易流水号
	 * @param payType
	 *            交易类型
	 * @param status
	 *            交易状态
	 * @param recvAcct
	 *            收款方账户 借
	 * @param payAcct
	 *            付款方账户 代
	 * @param relatxSerialNo
	 *            关联原申请交易流水号
	 * @throw null
	 * @return Long
	 */
	public Long addTransLog(String serialNo, Integer payType, Integer status,
			String recvAcct, String payAcct, String relatxSerialNo,
			Long linkId, String gov_amount);

	/**
	 * 更新 TransLog表的 LinkId
	 * 
	 * @author Sunny Ying
	 * @param orderSerial
	 * @param baseId
	 * @throw NumberFormatException, SQLException
	 * @return int
	 */
	public int editTransLogForLinkId(String orderSerial, Long baseId)
			throws NumberFormatException, SQLException;

	/**
	 * 查询是否实名认证过
	 * 
	 * @param idCardNo
	 * @param name
	 * @return true
	 * @throws AppException
	 *             如果调用公安网异常公报Exception
	 * @author ddr 2013-3-15
	 */
	public boolean isCidVerified(String idCardNO, String name)
			throws AppException;

	/**
	 * 获取会员已认证信息
	 * 
	 * @param memberCode
	 * @return
	 */
	IdcVerifyDto queryVerifyInfo(Long memberCode);
}
