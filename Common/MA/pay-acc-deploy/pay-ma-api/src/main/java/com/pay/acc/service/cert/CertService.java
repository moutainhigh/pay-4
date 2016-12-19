package com.pay.acc.service.cert;

import com.pay.acc.service.cert.dto.MemberCertLogDto;
import com.pay.acc.service.member.dto.ApplyCertRequest;
import com.pay.acc.service.member.dto.ApplyCertResponse;
import com.pay.acc.service.member.dto.CertResultDto;

public interface CertService {

	/**
	 * 证书申请
	 * @param applyCertRequest
	 * @param email
	 * @param engName
	 * @return
	 */
	public abstract CertResultDto applyCert(ApplyCertRequest applyCertRequest,
			String email, String engName,Long memberCode,Long operatorId);

	/**
	 * 制证
	 * 
	 * @param pkcs10
	 * @return
	 */
	public CertResultDto  certSign(String pkcs10,Long memberCode,Long operatorId);

	/**
	 * 证书申请并绑定会员
	 * @param applyCertRequest
	 * @param applyCertResponse
	 * @param memberCode
	 * @param operatorId
	 */
	public abstract boolean doMemberCertRntx(String machineIdentifier, Long memberCode,
			Long operatorId);
	
	/**
	 * 删除证书使用地点
	 * @param certManageId
	 * @return 成功 失败
	 */
	public abstract boolean removeCertPlaceRntx(Long certManageId);
	
	/**
	 * 用户注销证书（证书用户变成非证书用户）
	 * @param memberCode
	 * @param operatorId
	 * @return 成功 失败
	 */
	public abstract boolean disableCertOfUserRntx(Long memberCode, Long operatorId);
	
	/**
	 * 生成日志
	 * @param dto
	 * @return
	 */
	public abstract Long createMemberCertLogRntx(MemberCertLogDto dto);
	
	/**
	 * 生成日志流水号
	 * @return 日志id
	 */
	public abstract Long getLogSerialNo();
	
	
	/**
	 * 生成证书操作日志，针对 删除，注销，备份，导入。不针对制证
	 * @param memberCode
	 * @param operatorId
	 * @param status
	 * @param step 
	 * @see com.pay.acc.cert.enums.StepEnum
	 * @return
	 */
	public abstract Long createCertOperateLog(Long memberCode, Long operatorId,
			boolean status, int step);
	
	/**
	 * 查询会员有效绑定证书信息
	 * @param memberCode
	 * @param operatorId
	 * @return
	 */
	public ApplyCertResponse getValidMmeberCert(Long memberCode,Long operatorId);
	
	/**
	 * 查询会员绑定证书信息
	 * @param memberCode
	 * @param operatorId
	 * @return
	 */
	public ApplyCertResponse getMmeberCert(Long memberCode,Long operatorId);
	/**
	 * 判断是否为制证状态
	 * @param memberCode
	 * @param operatorId
	 * @return
	 */
	public boolean isSignCert(Long memberCode,Long operatorId);

}