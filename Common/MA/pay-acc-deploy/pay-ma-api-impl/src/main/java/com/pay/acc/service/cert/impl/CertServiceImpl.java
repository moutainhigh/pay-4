package com.pay.acc.service.cert.impl;

import javax.naming.directory.SearchResult;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.cert.model.CertManage;
import com.pay.acc.cert.model.MemberCert;
import com.pay.acc.cert.model.MemberCertLog;
import com.pay.acc.cert.service.CertManageService;
import com.pay.acc.cert.service.MemberCertLogService;
import com.pay.acc.cert.service.MemberCertService;
import com.pay.acc.member.exception.ErrorExceptionEnum;
import com.pay.acc.service.cert.CertService;
import com.pay.acc.service.cert.dto.MemberCertLogDto;
import com.pay.acc.service.member.dto.ApplyCertRequest;
import com.pay.acc.service.member.dto.ApplyCertResponse;
import com.pay.acc.service.member.dto.CertResultDto;

public class CertServiceImpl implements CertService {
	private static final Log logger = LogFactory.getLog(CertServiceImpl.class);

	private MemberCertService memberCertService;

	//private CFCACertService cfcaCertService;
	
	private CertManageService certManageService;
	
	private MemberCertLogService memberCertLogService;
	
	/* (non-Javadoc)
	 * @see com.pay.acc.service.cert.impl.CertService#applyCert(com.pay.acc.service.member.dto.ApplyCertRequest, java.lang.String, java.lang.String)
	 */
	public CertResultDto applyCert(ApplyCertRequest applyCertRequest,String email,String engName,
			Long memberCode,Long operatorId){
		CertResultDto certResultDto=new CertResultDto();
		//CertApplyDto cp=new CertApplyDto();
		
//		cp.setTelNo(applyCertRequest.getMobile());
//		cp.setIdTypeCode(applyCertRequest.getIdCardTypeNum());
//		cp.setAddress(applyCertRequest.getUsePlace());
//		cp.setRealName(applyCertRequest.getRealName());
//		cp.setCerNo(applyCertRequest.getIdCardNo());
//		cp.setEmail(email);
//		cp.setEngName(engName);
//		CfCaResultDto<ReturnData> cfcaResult=cfcaCertService.certApply(cp);
//		if(cfcaResult.getResultStatus()==CfCaConstant.SUCCESS){
//			certResultDto.setResultBool(true);
//			ReturnData rd=cfcaResult.getObject();
//			ApplyCertResponse applyCertResponse=new ApplyCertResponse(rd.getDn(), rd.getAuthCode(), rd.getRefNo());
//			certResultDto.setResultObj(applyCertResponse);
//			
//			/* 记录数据 */
//			MemberCert mc = memberCertService.queryMemberCert(memberCode,operatorId);
//			if(mc != null){
//				mc.setStatus(MemberCert.StatusEnum.APPLY.getValue());
//				mc.setAuthCode(applyCertResponse.getAuthCode());
//				mc.setRefNo(applyCertResponse.getRefNo());
//				mc.setUserDn(applyCertResponse.getDn());
//				memberCertService.modifyMemberCert(mc);
//			}else{
//				mc = new MemberCert();
//				mc.setMemberCode(memberCode);
//				mc.setOperatorId(operatorId);
//				mc.setAuthCode(applyCertResponse.getAuthCode());
//				mc.setRefNo(applyCertResponse.getRefNo());
//				mc.setUserDn(applyCertResponse.getDn());
//				mc.setStatus(MemberCert.StatusEnum.APPLY.getValue());
//				memberCertService.createMemberCert(mc);
//			}
//
//			CertManage cm = certManageService.queryTempCertManage(memberCode,operatorId,applyCertRequest.getMachineIdentifier());
//			if(cm != null){
//				cm.setStatus(CertManage.StatusEnum.TEMP.getValue());
//				cm.setMachineId(applyCertRequest.getMachineIdentifier());
//				cm.setStatus(CertManage.StatusEnum.TEMP.getValue());
//				cm.setUsePlace(applyCertRequest.getUsePlace());
//				cm.setUserDn(applyCertResponse.getDn());
//				certManageService.modifyCerManage(cm);
//			}else{
//				cm = new CertManage();
//				cm.setMemberCode(memberCode);
//				cm.setOperatorId(operatorId);
//				cm.setMachineId(applyCertRequest.getMachineIdentifier());
//				cm.setStatus(CertManage.StatusEnum.TEMP.getValue());
//				cm.setUsePlace(applyCertRequest.getUsePlace());
//				cm.setUserDn(applyCertResponse.getDn());
//				certManageService.createCerManage(cm);
//			}
//		}else{
//			certResultDto.setResultBool(false);
//			certResultDto.setErrCode(cfcaResult.getErrorCode());
//			certResultDto.setErrMsg(cfcaResult.getErrorMsg());
//		}
		return certResultDto;
	}
	
	/* (non-Javadoc)
	 * @see com.pay.acc.service.cert.impl.CertService#certSign(com.pay.acc.service.member.dto.ApplyCertResponse, java.lang.String)
	 */
	public CertResultDto  certSign(String pkcs10,Long memberCode,Long operatorId){
		CertResultDto certResultDto=new CertResultDto();
//		MemberCert mc = memberCertService.queryMemberCert(memberCode,operatorId);
//		if(mc != null){
//			CfCaResultDto<ReturnData> cfcaResult=cfcaCertService.certSign(mc.getRefNo(), mc.getAuthCode(), pkcs10);
//			if(cfcaResult.getResultStatus()==CfCaConstant.SUCCESS){
//				certResultDto.setResultBool(true);
//				ReturnData rd=cfcaResult.getObject();
//				ApplyCertResponse applyCertResponse=new ApplyCertResponse(mc.getUserDn(), mc.getAuthCode(), mc.getRefNo());
//				applyCertResponse.setSignCertPem(rd.getSignCertPem());
//				certResultDto.setResultObj(applyCertResponse);
//				
//				mc.setStatus(MemberCert.StatusEnum.MAKECERT.getValue());
//				mc.setCertCode(applyCertResponse.getSignCertPem());
//				memberCertService.modifyMemberCert(mc);
//				
//			}else{
//				certResultDto.setResultBool(false);
//				certResultDto.setErrCode(cfcaResult.getErrorCode());
//				certResultDto.setErrMsg(cfcaResult.getErrorMsg());
//			}
//		
//		}
		return certResultDto;
	}
	
	
	
	/* (non-Javadoc)
	 * @see com.pay.acc.service.cert.impl.CertService#doMemberCertRntx(com.pay.acc.service.member.dto.ApplyCertRequest, com.pay.acc.service.member.dto.ApplyCertResponse, java.lang.Long, java.lang.Long)
	 */
	public boolean doMemberCertRntx(String machineIdentifier,Long memberCode,Long operatorId){
		/*
		MemberCert mc=new MemberCert();
		CertManage cm=new CertManage();
		mc.setMemberCode(memberCode);
		mc.setOperatorId(operatorId);
		mc.setAuthCode(applyCertResponse.getAuthCode());
		mc.setRefNo(applyCertResponse.getRefNo());
		mc.setUserDn(applyCertResponse.getDn());
		mc.setStatus(MemberCert.StatusEnum.VALID.getValue());
		memberCertService.createMemberCert(mc);
		cm.setMemberCode(memberCode);
		cm.setOperatorId(operatorId);
		cm.setMachineId(applyCertRequest.getMachineIdentifier());
		cm.setStatus(CertManage.StatusEnum.VALID.getValue());
		cm.setUsePlace(applyCertRequest.getUsePlace());
		cm.setUserDn(applyCertResponse.getDn());
		certManageService.createCerManage(cm);
		*/
		boolean u1=false;
		boolean u2=false;
		MemberCert mc = memberCertService.queryMemberCert(memberCode,operatorId);
		if(mc != null){
			mc.setStatus(MemberCert.StatusEnum.VALID.getValue());
			u1=memberCertService.modifyMemberCert(mc);
		}
		
		CertManage cm = certManageService.queryTempCertManage(memberCode,operatorId,machineIdentifier);
		if(cm != null){
			cm.setStatus(CertManage.StatusEnum.VALID.getValue());
			u2=certManageService.modifyCerManage(cm);
		}
		
		return (u1 && u2);
	}
	
	@Override
	public boolean removeCertPlaceRntx(Long certManageId) {
		
		return certManageService.logicDeleteById(certManageId);
	}

	@Override
	public boolean disableCertOfUserRntx(Long memberCode, Long operatorId) {
		boolean bol = false;
//		MemberCert cert = memberCertService.queryMemberCert(memberCode, operatorId);
//		if(cert != null){
//			//注销本地
//			bol = memberCertService.disableMemberCertByDn(cert.getUserDn());
//			if(bol){
//				//删除安装地点所有证书
//				certManageService.logicDeleteAll(memberCode, operatorId);
//				//注销cfca 服务器
//				SearchResult result = cfcaCertService.searchCerByRefNo(cert.getRefNo());
//				if(result != null && cert.getUserDn().equals(result.getDn())){
//					CerStatusEnum certStatus = CerStatusEnum.getByCode(result.getCertStatusCode());
//					
//					if(CerStatusEnum.ACTIVATION == certStatus){
//						CfCaResultDto<ReturnData> cfcaDto = cfcaCertService.certRevoke(result.getDn());
//						if(cfcaDto.getResultStatus() == CfCaConstant.FAILURE &&
//							ErrorExceptionEnum.TIMEOUT_ERROR.getErrorCode().equals(cfcaDto.getErrorCode())){
//							//如果超时了,报警。
//							logger.warn("调用证书吊销超时:" + result.toString());
//						}
//					}else if (null != certStatus){
//						logger.info("调用CFCA证书服务查询:dn="+result.getDn()+",状态为:"+certStatus.getDes());
//					}
//				}
//			}
//		}
		return bol;
		
	}
	
	public ApplyCertResponse getValidMmeberCert(Long memberCode,Long operatorId){
		ApplyCertResponse applyCertResponse=null;
		MemberCert  mc=memberCertService.queryMemberCert(memberCode,operatorId);
		if(mc!=null && mc.getStatus().equals(MemberCert.StatusEnum.VALID.getValue())){
			applyCertResponse=new ApplyCertResponse(mc.getUserDn(), mc.getAuthCode(), mc.getRefNo());
		}
		
		return applyCertResponse;
		
	}
	
	
	public ApplyCertResponse getMmeberCert(Long memberCode,Long operatorId){
		ApplyCertResponse applyCertResponse=null;
		MemberCert  mc=memberCertService.queryMemberCert(memberCode,operatorId);
		if(mc!=null && (mc.getStatus().equals(MemberCert.StatusEnum.VALID.getValue()) || mc.getStatus().equals(MemberCert.StatusEnum.MAKECERT.getValue()))){
			applyCertResponse=new ApplyCertResponse(mc.getUserDn(), mc.getAuthCode(), mc.getRefNo(),mc.getStatus(),mc.getCertCode());
		}
		
		return applyCertResponse;
		
	}
	
	public boolean isSignCert(Long memberCode,Long operatorId){
		
		MemberCert  mc=memberCertService.queryMemberCert(memberCode,operatorId);
		if(mc!=null && mc.getStatus().equals(MemberCert.StatusEnum.MAKECERT.getValue())){
			return true;
		}
		
		return false;
		
	}

	@Override
	public Long createMemberCertLogRntx(MemberCertLogDto dto) {
		MemberCertLog model = new MemberCertLog();
		model.setAuthCode(dto.getAuthCode());
		model.setMemberCode(dto.getMemberCode());
		model.setOperatorId(dto.getOperatorId());
		model.setRefNo(dto.getRefNo());
		model.setSerialNo(dto.getSerialNo());
		model.setStatus(dto.getStatus());
		model.setStep(dto.getStep());
		model.setUserDn(dto.getUserDn());
		
		return memberCertLogService.createMemberCertLog(model);
	}

	@Override
	public Long getLogSerialNo() {
		
		return memberCertLogService.getSerialNo();
	}
	
	
	@Override
	public Long createCertOperateLog(Long memberCode, Long operatorId,
			boolean status, int step) {
		try {
			MemberCert mermberCert = memberCertService.queryMemberCert(memberCode,	operatorId);

			MemberCertLogDto memberCertLogDto = new MemberCertLogDto();
			memberCertLogDto.setMemberCode(memberCode);
			memberCertLogDto.setOperatorId(operatorId);
			memberCertLogDto.setRefNo(mermberCert.getRefNo());
			memberCertLogDto.setAuthCode(mermberCert.getRefNo());
			memberCertLogDto.setUserDn(mermberCert.getUserDn());
			memberCertLogDto.setStatus(status ? MemberCertLog.StatusEnum.SUCCESS
					.getValue() : MemberCertLog.StatusEnum.FAILURE.getValue());
			memberCertLogDto.setStep(step);
			memberCertLogDto.setSerialNo(getLogSerialNo());
			return createMemberCertLogRntx(memberCertLogDto);
		} catch (Exception e) {
			logger.error("证书操作日志记录失败",e);
			//日志记录不能影响外部事务
			return null;
		}
	}

	public void setMemberCertService(MemberCertService memberCertService) {
		this.memberCertService = memberCertService;
	}

//	public void setCfcaCertService(CFCACertService cfcaCertService) {
//		this.cfcaCertService = cfcaCertService;
//	}

	public void setCertManageService(CertManageService certManageService) {
		this.certManageService = certManageService;
	}

	public void setMemberCertLogService(MemberCertLogService memberCertLogService) {
		this.memberCertLogService = memberCertLogService;
	}

}
