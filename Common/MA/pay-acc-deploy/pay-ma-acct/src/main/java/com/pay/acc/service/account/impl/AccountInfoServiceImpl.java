package com.pay.acc.service.account.impl;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.util.StringUtils;

import com.pay.acc.acct.dto.AcctDto;
import com.pay.acc.acct.exception.AcctServiceException;
import com.pay.acc.acct.exception.AcctServiceUnkownException;
import com.pay.acc.acct.service.AcctService;
import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.acctattrib.exception.AcctAttribException;
import com.pay.acc.acctattrib.exception.AcctAttribUnknowException;
import com.pay.acc.acctattrib.service.AcctAttribService;
import com.pay.acc.common.MaConstant;
import com.pay.acc.common.config.VerifyPasswordConfig;
import com.pay.acc.common.utils.PayMaApiUtils;
import com.pay.acc.commons.ConstantHelper;
import com.pay.acc.constant.AcctStatusEnum;
import com.pay.acc.constant.MemberInfoStatusEnum;
import com.pay.acc.exception.MaMemberException;
import com.pay.acc.exception.MaMemberQueryException;
import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.dto.MemberInfoDto;
import com.pay.acc.member.dto.MemberOperateDto;
import com.pay.acc.member.dto.OperatorDto;
import com.pay.acc.member.exception.MemberException;
import com.pay.acc.member.exception.MemberUnknowException;
import com.pay.acc.member.memberenum.MemberOperateTypeEnum;
import com.pay.acc.member.service.MemberOperateService;
import com.pay.acc.member.service.MemberService;
import com.pay.acc.operatelog.model.OperateLog;
import com.pay.acc.operatelog.service.OperateLogService;
import com.pay.acc.service.account.AccountInfoService;
import com.pay.acc.service.account.constantenum.AcctLockTypeEnum;
import com.pay.acc.service.account.constantenum.OperateTypeEnum;
import com.pay.acc.service.account.dto.MaResultDto;
import com.pay.acc.service.account.dto.VerifyResultDto;
import com.pay.acc.service.cert.CertQueryService;
import com.pay.acc.service.errorenum.ErrorExceptionEnum;
import com.pay.inf.service.IMessageDigest;
import com.pay.util.BeanConvertUtil;
import com.pay.util.StringUtil;

public class AccountInfoServiceImpl implements AccountInfoService {

	private AcctService acctService;
	private AcctAttribService acctAttribService;
	private MemberService memberService;
	private IMessageDigest iMessageDigest;
	private MemberOperateService memberOperateService;
	private OperateLogService operateLogService;
	private CertQueryService certQueryService;
	//private SecurityService  securityService;
	private String  pfxFile; //私钥文件地址
	private String  pfxPwd;//私钥密码
	public static final String admin = "admin";
	private Log log = LogFactory.getLog(AccountInfoServiceImpl.class);
	
	@Override
    public void addOperateLog(Long type,String objectCode,String actionUrl)throws MaMemberQueryException {
		try{
			OperateLog operateLog=new OperateLog();
			operateLog.setObjectCode(objectCode);	
			//登陆名，登陆IP现在默认
			operateLog.setLoginName("system");
			operateLog.setLoginIp("127.0.0.1");
			operateLog.setType(type);
			operateLog.setActionUrl(actionUrl);
			operateLog.setBrowserVer(null);
			operateLogService.insertOperateLog(operateLog);	    
		}catch(Exception e){
			throw new MaMemberQueryException(e);
		}
    }
	
	@Override
	public MaResultDto doVerifyPayPassword(long memberCode, int accountType, String payPwd,Long operatorId) {
			return  doVerifyOperatorPayPassword(memberCode,accountType,payPwd,operatorId,true);
	}
	
	@Override
	public MaResultDto doVerifyPayPassword(long memberCode, int accountType, String payPwd,Long operatorId,boolean isVerificationCertUser){
			return  doVerifyOperatorPayPassword(memberCode,accountType,payPwd,operatorId,isVerificationCertUser);
	}
	
	@Override
	public MaResultDto doVerifyPayPassword(String loginName,
			String operatorIdentity, int accountType, String payPwd) {

		MaResultDto dto = checkParameter(loginName,operatorIdentity,payPwd);
		if(!dto.isResultBool()){
			return dto;
		}
		
		//(1) 根据loginName 查询会员是否存在
		//(2) 根据loginName 与  operatorName查询操作员是否存在
		//调用验证支付密码
		MemberDto member = memberService.queryMemberByLoginName(loginName);
		if(member == null){
			dto = new MaResultDto();
			dto.setResultBool(false);
			dto.setResultStatus(MaConstant.MEMBER_NOT_EXISTS);
			return dto;
		}
		OperatorDto operator = memberOperateService.queryOperatorByMemberCode(member.getMemberCode(),operatorIdentity);
		if(operator == null){
			dto = new MaResultDto();
			dto.setResultBool(false);
			dto.setResultStatus(MaConstant.OPERATOR_NOT_EXISTS);
			return dto;
		}
		Long operatorId = operator.getOperatorId();
		if(admin.equals(operator.getName())){
			operatorId = null;
		}
		dto = doVerifyPayPassword(member.getMemberCode(),accountType,payPwd,operatorId);
		
		if(dto.getResultStatus() == MaConstant.SECCESS){
			MemberInfoDto memberDto = BeanConvertUtil.convert(MemberInfoDto.class, member);
			memberDto.setIdentity(operator.getIdentity());
			memberDto.setName(operator.getName());
			memberDto.setPayPassWord(payPwd);
			memberDto.setOperatorId(operatorId);
			if(!admin.equals(operator.getName())){
				memberDto.setLoginPwd(operator.getPayPassWord());
			}
			dto.setObject(memberDto);
		}
		return dto;
	}

	
	
	private AcctDto checkAcct(Long memberCode,Integer accountType) throws MaMemberQueryException{
		// 查询账户是否存在
		AcctDto bcctDto = null;
		try {
			bcctDto = acctService.queryAcctByMemberCodeAndAcctTypeId(memberCode, accountType);
		}
		catch (AcctServiceException e) {
			log.info(e.getMessage(), e);
			throw new MaMemberQueryException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter", e);
		}
		catch (AcctServiceUnkownException e) {
			log.error("unknow error", e);
			throw new MaMemberQueryException(ErrorExceptionEnum.UNKNOW_ERROR, "unknow error", e);
		}
		if (null == bcctDto) {
			// 账户不存在
			log.info("会员号[" + memberCode + "]的账户不存在 ");
			throw new MaMemberQueryException(ErrorExceptionEnum.ACCT_NON_EXIST_ERROR, "会员号[" + memberCode + "]的账户不存在 ");
		}
		return bcctDto;
	}
	
	private AcctAttribDto checkAcctAttrib(String acctCode) throws MaMemberQueryException{
		// 查询账户属性是否存在
		AcctAttribDto acctAttribDto = null;
		try {
			 acctAttribDto = acctAttribService.queryAcctAttribWithAcctCode(acctCode);
		}
		catch (AcctAttribException e) {
			log.info(e.getMessage(), e);
			throw new MaMemberQueryException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter", e);
		}
		catch (AcctAttribUnknowException e) {
			log.error("unknow error", e);
			throw new MaMemberQueryException(ErrorExceptionEnum.UNKNOW_ERROR, "unknow error", e);
		}
		if (null == acctAttribDto) {
			// 账户属性不存在
			log.info("账户号[" + acctCode + "]的账户属性不存在");
			throw new MaMemberQueryException(ErrorExceptionEnum.ACCT_ATTRI_NO_EXIST, "账户号[" + acctCode + "]的账户属性不存在");
		}
		return acctAttribDto;
	}
	
	//验证是否后台锁定
	private MemberOperateDto checkMemberOperate(Long memberCode,int memberOperacteType,Integer acctType ,AcctAttribDto acctAttribDto,boolean isMemberCode,MaResultDto resultDto){
		MemberOperateDto memberOperate = this.memberOperateService.queryMemberOperate(memberCode, memberOperacteType,acctType);		
		int countFreeze=acctAttribService.countAcctAttribFreeze(acctAttribDto.getAcctCode());
		String acctCode=acctAttribDto.getAcctCode();
		if(AcctLockTypeEnum.FREEZE_OUT.getLockValue()==acctAttribDto.getAllowOut().intValue()){//如果账户状态已止入
			VerifyResultDto verifyDto=new VerifyResultDto();
			java.util.Calendar cal=java.util.Calendar.getInstance();					
			if(countFreeze > 0 ){//判断是否后台止出,返回错误信息
				log.info("支付密码验证 ---:acctCode:"+acctCode +" -- 后台止出，直接返回锁定信息");
				verifyDto.setLeavingMinute(VerifyPasswordConfig.getInstance().getPayFrozenMinute());
				resultDto.setResultStatus(MaConstant.ERROR_AND_LOCK);
				resultDto.setObject(verifyDto);
			}
			if(null!=memberOperate){
					long leavingMinute= VerifyPasswordConfig.getInstance().getPayFrozenMinute()-(cal.getTimeInMillis() - memberOperate.getUpdateTime().getTime())/1000/60;			
					if(leavingMinute > 0 ){//判断是否已超过了解锁的时间,是则验证支付密码是否匹配，否则直接返回错误信息
						log.info("支付密码验证 ---:acctCode:"+acctCode +" -- 还没到解锁时间，直接返回锁定信息");
						verifyDto.setLeavingMinute(new Long(leavingMinute).intValue());
						resultDto.setResultStatus(MaConstant.ERROR_AND_LOCK);
						resultDto.setObject(verifyDto);
					}	
			}else{//会员账户操作记录表不存在，异常情况(正常情况下，不会有这样的问题)
				log.info("支付密码验证 ---:acctCode:"+acctCode +" -- 会员账户操作记录表不存在，异常情况");
				resultDto.setResultStatus(MaConstant.ERROR_AND_LOCK);
			}
		}
		if(!isMemberCode){
			this.validateOperateLock(resultDto, memberOperate, acctAttribDto);
		}
		return memberOperate;
	}
	
	private MaResultDto validateOperateLock(MaResultDto resultDto,MemberOperateDto memberOperate,AcctAttribDto acctAttribDto){
		boolean cleanFlag=false;
		if(null!=memberOperate){
			 cleanFlag=this.memberOperateService.cleanFailTime(memberOperate, VerifyPasswordConfig.getInstance().getPayFrozenMinute());
		}		
		if(!cleanFlag && null!=memberOperate){
			Long failTime=memberOperate.getFailTime();
			if(failTime>=VerifyPasswordConfig.getInstance().getPayTotalCount()){
				VerifyResultDto verifyDto=new VerifyResultDto();			
				log.info("支付密码验证 ---:acctCode:"+acctAttribDto.getAcctCode() +" -- 操作员错误次数过"+VerifyPasswordConfig.getInstance().getPayTotalCount()+"次，锁定");
				verifyDto.setLeavingMinute(VerifyPasswordConfig.getInstance().getPayFrozenMinute());
				resultDto.setResultStatus(MaConstant.ERROR_AND_LOCK);
				resultDto.setObject(verifyDto);
			}
		}
		return resultDto;
	}
	
	private int updateAcctFreezeAllowOutStatus(MemberOperateDto memberOperate ,int allowOut,String acctCode,Long memberCode) throws AcctAttribException, AcctAttribUnknowException, MaMemberQueryException{
		if (null != memberOperate) {// 判断是否存在此操作员的记录				
			boolean cleanFlag=this.memberOperateService.cleanFailTime(memberOperate, VerifyPasswordConfig.getInstance().getPayFrozenMinute());
			if(cleanFlag && allowOut==AcctLockTypeEnum.FREEZE_OUT.getLockValue().intValue()){//当需要清0，且清0成功,账户为止出，则直接解锁，并且将状态止出状态改为1
				acctAttribService.updateAcctFreezeAllowOutStatusWithAcctCode(acctCode, AcctLockTypeEnum.UNFREEZE_OUT.getLockValue());
				allowOut=AcctLockTypeEnum.UNFREEZE_OUT.getLockValue();
				//记录解止出的操作日志
				this.addOperateLog(Long.valueOf(OperateTypeEnum.UNFREEZE_OUT.getCode()),String.valueOf(memberCode), OperateTypeEnum.UNFREEZE_OUT.getMessage());
				log.info("支付密码验证 ---:acctCode:"+acctCode +" -- 已经超过2小时，解锁成功");
			}
		}
	 return allowOut; 
	}
	
	//得到加密后的密码
	private String  getPayPassWord(boolean isVerificationCertUser,Long memberCode,Long operatorId,String payPwd) throws MaMemberQueryException{
		//isVerificationCertUser：true需要:false　判断是否需要验证证书支付密码
		boolean isCertUser=false;
		//	如果是证书用户，则验证支付密码的方法不一样。
//		if(isVerificationCertUser){
//			isCertUser=certQueryService.isCertUser(memberCode, operatorId);		
//		}	
		// 密码加密
		try {
			if(isCertUser){				
				//验证签名
				//payPwd=securityService.p7VerifySignMessage(payPwd);
				//解密PKCS#7标准的加密数据(数字信封)
				//payPwd=securityService.openEnvelopedMessageByFile(payPwd,pfxFile, pfxPwd);					
			}
			return iMessageDigest.genMessageDigest(payPwd.getBytes());
		}
		catch (Exception e) {
			log.error("SHAMessageDigest is error");
			throw new MaMemberQueryException(ErrorExceptionEnum.SHA_MESSAGE_DIGEST_ERROR, "SHAMessageDigest is error", e);
		}
	}
	
	///验证两个密码是否匹配
	private boolean verifytPayPassWord(Long memberCode,Long operatorId,String payPwd,AcctAttribDto acctAttribDto) throws MaMemberQueryException{		
		boolean verifyResult = false;// 默认失败
		try {
			if(operatorId!=null && operatorId>0){//操作员
				OperatorDto operator=memberOperateService.queryOperatorByOperatorId(operatorId);
				if(operator!=null && operator.getStatus()==1){
					verifyResult = payPwd.equals(operator.getPayPassWord()); 
				}
			}else{
				verifyResult = 	payPwd.equals(acctAttribDto.getPayPwd()); 
			}
			return verifyResult;
		}
		catch (Exception e) {
			log.error("member verify pay password error",e);
			throw new MaMemberQueryException(ErrorExceptionEnum.MEMBER_VERIFY_PASSWORD_ERROR, "member verify pay  password error");
		}
	}
	
	//支付密码验证失败返回的信息
	private MaResultDto verifyFailure(Long operatorId,int memberOperacteType,Integer acctType,MemberOperateDto memberOperate,AcctDto bcctDto ,boolean isMemberCode,MaResultDto resultDto) throws AcctAttribException, AcctAttribUnknowException, MaMemberQueryException{
		VerifyResultDto verifyDto=new VerifyResultDto();
		int leavingTime=VerifyPasswordConfig.getInstance().getPayTotalCount();
		if (null == memberOperate) {// 初始化memberOperate对象
			memberOperate = memberOperateService.createMemberOperate(operatorId, new Long(memberOperacteType), acctType);
			leavingTime=leavingTime-memberOperate.getFailTime().intValue();
			this.memberOperateService.insert(memberOperate);			
			verifyDto.setLeavingMinute(VerifyPasswordConfig.getInstance().getPayFrozenMinute());					
		}else {
			Long failTime=memberOperate.getFailTime();
			if (null == failTime) {
				failTime = 1L;
			}
			else {
				failTime = failTime + 1;
			}
			leavingTime =leavingTime-failTime.intValue() ;
			if(leavingTime>=0){//当错误次数没有达到总的次数时，更新
				memberOperate.setFailTime(failTime);
				this.memberOperateService.update(memberOperate);	
			}
			java.util.Calendar cal=java.util.Calendar.getInstance();
			long leavingMinute=0;
			if(failTime==1){
				leavingMinute= VerifyPasswordConfig.getInstance().getPayFrozenMinute();
			}else{
				leavingMinute=  VerifyPasswordConfig.getInstance().getPayFrozenMinute()-(cal.getTimeInMillis() - memberOperate.getUpdateTime().getTime())/1000/60;
			}			
			verifyDto.setLeavingMinute(new Long(leavingMinute).intValue());
		}
		if(leavingTime==0){//锁定账户，并将返回值设为3，解锁时间就为当前设置的时间
//			resultDto.setResultStatus(MaConstant.ERROR_AND_LOCK);
//			verifyDto.setLeavingMinute( VerifyPasswordConfig.getInstance().getPayFrozenMinute());
//			resultDto.setObject(verifyDto);
//			log.info("支付密码验证 ---:acctCode:"+bcctDto.getAcctCode() +" --账户锁定成功");
//			return resultDto;			
			resultDto.setResultStatus(MaConstant.ERROR_AND_LOCK);
			verifyDto.setLeavingMinute(VerifyPasswordConfig.getInstance().getPayFrozenMinute());
			resultDto.setObject(verifyDto);
			if(isMemberCode){
				//将账户状态置为止出
				acctAttribService.updateAcctFreezeAllowOutStatusWithAcctCode(bcctDto.getAcctCode(), AcctLockTypeEnum.FREEZE_OUT.getLockValue());
				//记录止出的操作日志
				this.addOperateLog(Long.valueOf(OperateTypeEnum.FREEZE_OUT.getCode()),String.valueOf(operatorId), OperateTypeEnum.FREEZE_OUT.getMessage());
			}			
			log.info("支付密码验证 ---:acctCode:"+bcctDto.getAcctCode() +" --账户锁定成功");
			return resultDto;
		}else if(leavingTime < 0){
			leavingTime=0;
			verifyDto.setLeavingTime(leavingTime);
			verifyDto.setTotalTime( VerifyPasswordConfig.getInstance().getPayTotalCount());
			resultDto.setObject(verifyDto);
			resultDto.setResultStatus(MaConstant.ERROR_AND_LOCK);
			return resultDto;
		}
		verifyDto.setLeavingTime(leavingTime);
		verifyDto.setTotalTime( VerifyPasswordConfig.getInstance().getPayTotalCount());
		resultDto.setObject(verifyDto);
		resultDto.setResultStatus(MaConstant.ERROR_NOT_LOCK);
		resultDto.setErrorCode(ErrorExceptionEnum.MEMBER_VERIFY_PASSWORD_ERROR.getErrorCode());
		resultDto.setErrorMsg(ErrorExceptionEnum.MEMBER_VERIFY_PASSWORD_ERROR.getMessage());
		return resultDto;
	}
	
	
	private MaResultDto doVerifyOperatorPayPassword(long memberCode, int accountType, String payPwd,Long operatorId,boolean isVerificationCertUser){
		MaResultDto resultDto = new MaResultDto();
		try {			
			// 验证参数
			if(!this.checkParameter(memberCode, accountType, payPwd)){
				 resultDto.setResultStatus(MaConstant.PAY_PASSWORD_NOT_EXISTS);//密码为空
				 return resultDto;
			 }
			 this.checkMember(memberCode);
			 AcctDto bcctDto =checkAcct(memberCode, accountType);
			AcctAttribDto acctAttribDto=this.checkAcctAttrib(bcctDto.getAcctCode());				
			int memberOperacteType=0;
			Long operateMemberCode=null;
			boolean isMemberCode=false;
			
			if(null!=operatorId && operatorId>0){
				 operateMemberCode=operatorId;
				 memberOperacteType=MemberOperateTypeEnum.OPERATOR_PAY_PWD.getCode();
			}else{
				 operateMemberCode=memberCode;
				 memberOperacteType=MemberOperateTypeEnum.PAY_PWD.getCode();
				 isMemberCode=true;
			}
			MemberOperateDto memberOperate=this.checkMemberOperate(operateMemberCode,memberOperacteType , acctAttribDto.getAcctType(), acctAttribDto, isMemberCode, resultDto);
			if(resultDto.getResultStatus()>0){
				return resultDto;
			}
			int allowOut=10;
			if(null==operatorId || operatorId==0){
				allowOut =this.updateAcctFreezeAllowOutStatus(memberOperate, acctAttribDto.getAllowOut(), bcctDto.getAcctCode(), memberCode);
			}
			//得到加密的密码
			payPwd=getPayPassWord(isVerificationCertUser, memberCode, operatorId, payPwd);
			//验证两个密码是否匹配
			boolean verifyResult =this.verifytPayPassWord(memberCode, operatorId, payPwd, acctAttribDto);			
			// 验证不成功
			if (!verifyResult) {
					return	this.verifyFailure(operateMemberCode, memberOperacteType, acctAttribDto.getAcctType(), memberOperate, bcctDto, isMemberCode,resultDto);
			}else {
				if (null != memberOperate) {
					memberOperate.setFailTime(0L);
					memberOperateService.update(memberOperate);
					resultDto.setResultStatus(MaConstant.SECCESS);
					if(null==operatorId ||  operatorId==0){
						if(allowOut==AcctLockTypeEnum.FREEZE_OUT.getLockValue()){//如果账户止入，则解锁
							acctAttribService.updateAcctFreezeAllowOutStatusWithAcctCode(bcctDto.getAcctCode(), AcctLockTypeEnum.UNFREEZE_OUT.getLockValue());
						}
					}
				}else{
					resultDto.setResultStatus(MaConstant.SECCESS);
				}
			}
		}
		catch (MaMemberQueryException e) {
			resultDto.setResultStatus(MaConstant.ACCOUNT_NOT_EXISTS);//出现异常
			resultDto.setErrorCode(e.getErrorEnum().getErrorCode());
			resultDto.setErrorMsg(e.getErrorEnum().getMessage());
		} catch (AcctAttribException e) {
			resultDto.setResultStatus(MaConstant.ACCOUNT_NOT_EXISTS);//出现异常
			resultDto.setErrorCode(ErrorExceptionEnum.ACCT_NON_EXIST_ERROR.getErrorCode());
			resultDto.setErrorMsg(ErrorExceptionEnum.ACCT_NON_EXIST_ERROR.getMessage());
        }
        catch (AcctAttribUnknowException e) {
			resultDto.setResultStatus(MaConstant.ACCOUNT_NOT_EXISTS);//出现异常
			resultDto.setErrorCode(ErrorExceptionEnum.ACCT_NON_EXIST_ERROR.getErrorCode());
			resultDto.setErrorMsg(ErrorExceptionEnum.ACCT_NON_EXIST_ERROR.getMessage());
        }
		return resultDto;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.acc.service.account.AccountInfoService#doResetPayPwdRnTx (long, int, java.lang.String)
	 */
	public int doResetPayPwdRnTx(long memberCode, int acctType, String newPayPwd) throws MaMemberException {
		// 初始化返回值: 失败
		// int result=SET_FAILED;
		// 验证参数
		this.checkQueryParameter(memberCode, acctType, newPayPwd);
		this.checkQueryMember(memberCode);

		// 查询账户是否存在
		AcctDto bcctDto = this.checkAcctDto(memberCode, acctType);
		if (null == bcctDto) {
			// 账户不存在
			log.error("会员号[" + memberCode + "]的账户不存在 ");
			throw new MaMemberException(ErrorExceptionEnum.ACCT_NON_EXIST_ERROR, "会员号[" + memberCode + "]的账户不存在");
			// 需要判断用户是否有效，如果该用户无效或者冻结，就不能进行密码的设置
		}
		if (null == bcctDto.getStatus() || bcctDto.getStatus().intValue() == AcctStatusEnum.INVALID.getCode()) {
			log.info("会员号为[" + memberCode + "]的账户无效");
			throw new MaMemberException(ErrorExceptionEnum.ACCT_INVALID_ERROR, "会员号为[" + memberCode + "]的账户无效");
		}

		// 查询账户属性是否存在
		AcctAttribDto acctAttribDto = this.checkAcctAttribDto(bcctDto.getAcctCode());
		if (null == acctAttribDto) {
			// 账户属性不存在
			log.error("会员号[" + memberCode + "]的账户属性不存在");
			throw new MaMemberException(ErrorExceptionEnum.ACCT_ATTRI_NO_EXIST, "会员号[" + memberCode + "]的账户属性不存在");
		}
		if (acctAttribDto.getFrozen().intValue() != ConstantHelper.ACCT_FREEZE_STATUS) {
			log.info("会员号为[" + memberCode + "]的账户被冻结");
			throw new MaMemberException(ErrorExceptionEnum.ACCT_FROZEN_ERROR, "会员号为[" + memberCode + "]的账户被冻结");
		}

		// 密码加密
		try {
			newPayPwd = iMessageDigest.genMessageDigest(newPayPwd.getBytes());
		}
		catch (Exception e) {
			log.error("SHAMessageDigest is error");
			throw new MaMemberException(ErrorExceptionEnum.SHA_MESSAGE_DIGEST_ERROR, "SHAMessageDigest is error", e);
		}

		boolean updateResult = false;// 默认失败
		try {
			updateResult = acctAttribService.resetAcctAttribPwd(bcctDto.getAcctCode(), newPayPwd);
		}
		catch (AcctAttribException e) {
			log.error(e.getMessage(), e);
			throw new MaMemberException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter", e);
		}
		catch (AcctAttribUnknowException e) {
			log.error("unknow error", e);
			throw new MaMemberException(ErrorExceptionEnum.UNKNOW_ERROR, "unknow error", e);
		}

		// 设置不成功
		if (!updateResult) {
			log.error("memeber update password error");
			throw new MaMemberException(ErrorExceptionEnum.MEMBER_UPDATE_PASSWORD, "memeber update password error");
		}

		return SET_PASSWORD_SUCCEED;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see com.pay.acc.service.account.AccountInfoService#doVerifyPayPasswordNsTx (long, int, java.lang.String)
	 */
	public int doVerifyPayPasswordNsTx(long memberCode, int accountType, String payPwd) throws MaMemberQueryException {

		// 验证参数
		this.checkParameter(memberCode, accountType, payPwd);
		this.checkMember(memberCode);

		// 查询账户是否存在
		AcctDto bcctDto = null;
		try {
			bcctDto = acctService.queryAcctByMemberCodeAndAcctTypeId(memberCode, accountType);
		}
		catch (AcctServiceException e) {
			log.error(e.getMessage(), e);
			throw new MaMemberQueryException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter", e);
		}
		catch (AcctServiceUnkownException e) {
			log.error("unknow error", e);
			throw new MaMemberQueryException(ErrorExceptionEnum.UNKNOW_ERROR, "unknow error", e);
		}
		if (null == bcctDto) {
			// 账户不存在
			log.error("会员号[" + memberCode + "]的账户不存在 ");
			throw new MaMemberQueryException(ErrorExceptionEnum.ACCT_NON_EXIST_ERROR, "会员号[" + memberCode + "]的账户不存在 ");
		}

		// 查询账户属性是否存在
		AcctAttribDto acctAttribDto = null;
		try {
			acctAttribDto = acctAttribService.queryAcctAttribWithAcctCode(bcctDto.getAcctCode());
		}
		catch (AcctAttribException e) {
			log.error(e.getMessage(), e);
			throw new MaMemberQueryException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter", e);
		}
		catch (AcctAttribUnknowException e) {
			log.error("unknow error", e);
			throw new MaMemberQueryException(ErrorExceptionEnum.UNKNOW_ERROR, "unknow error", e);
		}
		if (null == acctAttribDto) {
			// 账户属性不存在
			log.error("会员号[" + memberCode + "]的账户属性不存在");
			throw new MaMemberQueryException(ErrorExceptionEnum.ACCT_ATTRI_NO_EXIST, "会员号[" + memberCode + "]的账户属性不存在");
		}

		// 密码加密
		try {
			payPwd = iMessageDigest.genMessageDigest(payPwd.getBytes());
		}
		catch (Exception e) {
			log.error("SHAMessageDigest is error");
			throw new MaMemberQueryException(ErrorExceptionEnum.SHA_MESSAGE_DIGEST_ERROR, "SHAMessageDigest is error", e);
		}

		boolean verifyResult = false;// 默认失败
		try {
			verifyResult = payPwd.equals(acctAttribDto.getPayPwd()); // 匹配
		}
		catch (Exception e) {
			log.error("member verify pay password error");
			throw new MaMemberQueryException(ErrorExceptionEnum.MEMBER_VERIFY_PASSWORD_ERROR, "member verify pay  password error");
		}

		// 验证不成功
		if (!verifyResult) {
			log.error("member verify password error");
			throw new MaMemberQueryException(ErrorExceptionEnum.MEMBER_VERIFY_PASSWORD_ERROR, "member verify password error");
		}
		return VERIFY_PASSWORD_SUCCEED;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @seecom.pay.acc.service.account.AccountInfoService# doVerifySecurityQuestionNsTx(long, int, java.lang.String)
	 */
	public int doVerifySecurityQuestionNsTx(long memberCode, int securQuestionId, String answer) throws MaMemberQueryException {

		// 初始化返回值: 失败
		// 验证参数
		this.checkSecurityQuestionParameter(memberCode, securQuestionId, answer);
		// 验证会员信息
		MemberDto member = this.checkMember(memberCode);

		// 安全问题加密
		try {
			answer = iMessageDigest.genMessageDigest(answer.getBytes());
		}
		catch (Exception e) {
			log.error("SHAMessageDigest is error");
			throw new MaMemberQueryException(ErrorExceptionEnum.SHA_MESSAGE_DIGEST_ERROR, "SHAMessageDigest is error", e);
		}

		boolean verifyResult = false;// 默认失败
		try {
			verifyResult = answer.equals(member.getSecurityAnswer()) && (null != member.getSecurityQuestion() && securQuestionId == member.getSecurityQuestion().intValue());
		}
		catch (Exception e) {
			log.error("member verify security question error");
			throw new MaMemberQueryException(ErrorExceptionEnum.MEMBER_VERIFY_QUESTION_ERROR, "member verify security question error");
		}
		// 验证不成功
		if (!verifyResult) {
			log.error("member verify security question error");
			throw new MaMemberQueryException(ErrorExceptionEnum.MEMBER_VERIFY_QUESTION_ERROR, "member verify security question error");
		}
		return VERIFY_QUESTION_SUCCEED;
	}

	// 查询账户是否存在
	private AcctDto checkAcctDto(Long memberCode, Integer acctType) throws MaMemberException {

		AcctDto acctDto = null;
		try {
			acctDto = acctService.queryAcctByMemberCodeAndAcctTypeId(memberCode, acctType);
		}
		catch (AcctServiceException e) {
			log.error(e.getMessage(), e);
			throw new MaMemberException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter", e);
		}
		catch (AcctServiceUnkownException e) {
			log.error("unknow error", e);
			throw new MaMemberException(ErrorExceptionEnum.UNKNOW_ERROR, "unknow error", e);
		}

		return acctDto;
	}

	// 查询账户属性是否存在
	private AcctAttribDto checkAcctAttribDto(String acctCode) throws MaMemberException {

		AcctAttribDto acctAttribDto = null;
		try {
			acctAttribDto = acctAttribService.queryAcctAttribWithAcctCode(acctCode);
		}
		catch (AcctAttribException e) {
			log.error(e.getMessage(), e);
			throw new MaMemberException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter", e);
		}
		catch (AcctAttribUnknowException e) {
			log.error("unknow error", e);
			throw new MaMemberException(ErrorExceptionEnum.UNKNOW_ERROR, "unknow error", e);
		}

		return acctAttribDto;
	}

	// 验证参数
	private boolean checkQueryParameter(Long memberCode, Integer accountType, String newPayPwd) throws MaMemberException {
		if (null == memberCode || memberCode.longValue() <= 0) {
			log.error(" invaild parameter , memberCode is null or  invaild! ");
			throw new MaMemberException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter , memberCode is null or invaild ! ");
		}
		else if (null == accountType || accountType.intValue() <= 0) {
			log.error(" invaild parameter , accountType is null or  invaild! ");
			throw new MaMemberException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter , accountType is null or  invaild! ");
		}
		else if (null == newPayPwd || !StringUtils.hasText(newPayPwd)) {
			log.error(" invaild parameter , newPayPwd is null or  invaild! ");
			throw new MaMemberException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter , newPayPwd is null or  invaild! ");
		}
		return true;

	}

	// 验证参数
	private boolean checkParameter(Long memberCode, Integer accountType, String payPwd) throws MaMemberQueryException {
		if (null == memberCode || memberCode.longValue() <= 0) {
			log.info(" invaild parameter , memberCode is null or  invaild! ");
			throw new MaMemberQueryException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter , memberCode is null or  invaild! ");
		}
		else if (null == accountType || accountType.intValue() <= 0) {
			log.info(" invaild parameter , accountType is null or  invaild! ");
			throw new MaMemberQueryException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter , accountType is null or  invaild! ");
		}
		else if (null == payPwd || !StringUtils.hasText(payPwd)) {
			log.info(" invaild parameter , newPayPwd is null or  invaild! ");
			//throw new MaMemberQueryException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter , payPwd is null or  invaild! ");
		}
		return true;

	}

	// 验证参数
	private boolean checkSecurityQuestionParameter(Long memberCode, Integer securQuestionId, String answer) throws MaMemberQueryException {
		if (null == memberCode || memberCode.longValue() <= 0) {
			log.error(" invaild parameter , memberCode is null or  invaild! ");
			throw new MaMemberQueryException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter , memberCode is null or  invaild! ");
		}
		else if (null == securQuestionId || securQuestionId.intValue() <= 0) {
			log.error(" invaild parameter , securQuestionId is null or  invaild! ");
			throw new MaMemberQueryException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter , securQuestionId is null or  invaild! ");
		}
		else if (null == answer || !StringUtils.hasText(answer)) {
			log.error(" invaild parameter , answer is null or  invaild! ");
			throw new MaMemberQueryException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter , answer is null or  invaild! ");
		}
		return true;

	}

	// 验证参数
	private boolean checkResetSecurityQuestionParameter(Long memberCode, Integer securQuestionId, String answer) throws MaMemberException {
		if (null == memberCode || memberCode.longValue() <= 0) {
			log.error(" invaild parameter , memberCode is null or  invaild! ");
			throw new MaMemberException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter , memberCode is null or  invaild! ");
		}
		else if (null == securQuestionId || securQuestionId.intValue() <= 0) {
			log.error(" invaild parameter , securQuestionId is null or  invaild! ");
			throw new MaMemberException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter , securQuestionId is null or  invaild! ");
		}
		else if (null == answer || !StringUtils.hasText(answer)) {
			log.error(" invaild parameter , answer is null or  invaild! ");
			throw new MaMemberException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter , answer is null or  invaild! ");
		}
		return true;

	}

	// 会员验证参数
	private MemberDto checkQueryMember(Long memberCode) throws MaMemberException {
		if (null == memberCode || memberCode.longValue() <= 0) {
			log.error(" invaild parameter , memberCode is null or  invaild! ");
			throw new MaMemberException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter , memberCode is null or  invaild! ");
		}
		// else if (memberCode < MaConstant.CHECK_MEMBER_CODE) {
		// log.error("member code error");
		// throw new MaMemberException(ErrorExceptionEnum.MEMBERCODE_ERROR,
		// "member code error ");
		// }
		// 验证会员是否激活
		MemberDto memberDto = null;
		try {
			memberDto = this.memberService.queryMemberWithMemberCode(memberCode);
		}
		catch (MemberException e) {
			log.error(e.getMessage(), e);
			throw new MaMemberException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter", e);
		}
		catch (MemberUnknowException e) {
			log.error("unknow error", e);
			throw new MaMemberException(ErrorExceptionEnum.UNKNOW_ERROR, "unknow error", e);
		}
		if (memberDto == null) {
			log.error("不存在会员号为[" + memberCode + "]的会员");
			throw new MaMemberException(ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR, "不存在会员号为[" + memberCode + "]的会员");
		}
		if (null == memberDto.getStatus() || memberDto.getStatus().intValue() != MemberInfoStatusEnum.NORMAL.getCode()) {
			log.info("会员号为[" + memberCode + "]处于非" + MemberInfoStatusEnum.NORMAL.getDescription());

			throw new MaMemberException(ErrorExceptionEnum.MEMBER_INVALID, "会员号为[" + memberCode + "]处于非" + MemberInfoStatusEnum.NORMAL.getDescription());
		}
		return memberDto;

	}

	// 会员验证参数
	private MemberDto checkMember(Long memberCode) throws MaMemberQueryException {
		if (null == memberCode || memberCode.longValue() <= 0) {
			log.info(" invaild parameter , memberCode is null or  invaild! ");
			throw new MaMemberQueryException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter , memberCode is null or  invaild! ");
		}
		// else if (memberCode < MaConstant.CHECK_MEMBER_CODE) {
		// log.error("member code error");
		// throw new MaMemberQueryException(ErrorExceptionEnum.MEMBERCODE_ERROR,
		// "member code error");
		// }
		// 验证会员是否激活
		MemberDto memberDto = null;
		try {
			memberDto = this.memberService.queryMemberWithMemberCode(memberCode);
		}
		catch (MemberException e) {
			log.info(e.getMessage(), e);
			throw new MaMemberQueryException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter", e);
		}
		catch (MemberUnknowException e) {
			log.info("unknow error", e);
			throw new MaMemberQueryException(ErrorExceptionEnum.UNKNOW_ERROR, "unknow error", e);
		}
		if (memberDto == null) {
			log.info("不存在会员号为[" + memberCode + "]的会员");
			throw new MaMemberQueryException(ErrorExceptionEnum.MEMBER_NON_EXIST_ERROR, "不存在会员号为[" + memberCode + "]的会员");
		}
		if (null == memberDto.getStatus() || memberDto.getStatus().intValue() != MemberInfoStatusEnum.NORMAL.getCode()) {

			log.info("会员号为[" + memberCode + "]处于非" + MemberInfoStatusEnum.NORMAL.getDescription());

			throw new MaMemberQueryException(ErrorExceptionEnum.MEMBER_INVALID, "会员号为[" + memberCode + "]处于非" + MemberInfoStatusEnum.NORMAL.getDescription());
		}
		return memberDto;

	}

	@Override
	public boolean doQueryIsHavePayPwd(Long memberCode, Integer acctType) throws MaMemberQueryException, MaMemberException {
		log.debug("验证会员号为 :" + memberCode + "的会员信息");
		this.checkMember(memberCode);
		log.debug("验证会员号为：" + memberCode + " 的账户是否正常");
		AcctDto acctDto = this.checkAcctDto(memberCode, acctType);

		if (acctDto.getStatus() == AcctStatusEnum.INVALID.getCode()) {
			log.info("会员号[" + memberCode + "]的账户无效");
			throw new MaMemberException(ErrorExceptionEnum.ACCT_INVALID_ERROR, "会员号[" + memberCode + "]的账户无效");
		}
		log.debug("对于：" + memberCode + "组装会员账户号");
		Long memberAcctCode = PayMaApiUtils.convertAcctCode(memberCode, acctType);
		log.debug("验证会员号：" + memberCode + "属性");
		AcctAttribDto acctAttribDto = this.handlerAcctAttribWithMemberCode(memberAcctCode);

		log.debug("该会员号：" + memberCode + "对应的账户类型的属性信息为：" + acctAttribDto.toString());
		if (acctAttribDto.getPayPwd() != null && StringUtils.hasText(acctAttribDto.getPayPwd())) {
			return true;
		}
		return false;
	}

	private AcctAttribDto handlerAcctAttribWithMemberCode(Long memberAcctCode) throws MaMemberQueryException {
		AcctAttribDto acctAttribDto = null;
		try {
			acctAttribDto = this.acctAttribService.queryAcctAttribWithMemberAcctCode(memberAcctCode);
		}
		catch (AcctAttribException e) {
			log.error(e.getMessage(), e);
			throw new MaMemberQueryException(ErrorExceptionEnum.INVAILD_PARAMETER, "invaild parameter", e);
		}
		catch (AcctAttribUnknowException e) {
			log.error("unknow error", e);
			throw new MaMemberQueryException(ErrorExceptionEnum.UNKNOW_ERROR, "unknow error", e);
		}
		if (acctAttribDto == null) {
			log.error("对于会员号[" + memberAcctCode + "]不存在属性");
			throw new MaMemberQueryException(ErrorExceptionEnum.ACCT_ATTRI_NO_EXIST, "对于会员号[" + memberAcctCode + "]不存在属性");
		}
		return acctAttribDto;
	}

	private MaResultDto checkParameter(String loginName,String operatorIdentity,String payPwd){
		
		MaResultDto dto = new MaResultDto();
		if(StringUtil.isEmpty(loginName)){
			dto.setResultStatus(MaConstant.LOGINNAME_NOT_EXISTS);
			dto.setResultBool(false);
			return dto;
		}
		if(StringUtil.isEmpty(operatorIdentity)){
			dto.setResultStatus(MaConstant.OPERATORNAME_NOT_EXISTS);
			dto.setResultBool(false);
			return dto;
		}
		if(StringUtil.isEmpty(payPwd)){
			dto.setResultStatus(MaConstant.PAY_PASSWORD_NOT_EXISTS);
			dto.setResultBool(false);
			return dto;
		}
		dto.setResultBool(true);
		return dto;
		
	}

	@Override
	public String decryptCertPwd(String encryptPwd) {
		
		try {
			//验证签名
			//String pwd=securityService.p7VerifySignMessage(encryptPwd);
			//解密PKCS#7标准的加密数据(数字信封)
			//return securityService.openEnvelopedMessageByFile(pwd,pfxFile, pfxPwd);
		} catch (Exception e) {
			log.warn("解密密码出错：",e);
		}
		return null;
	}

	public AcctService getAcctService() {
		return acctService;
	}

	public void setAcctService(AcctService acctService) {
		this.acctService = acctService;
	}

	public AcctAttribService getAcctAttribService() {
		return acctAttribService;
	}

	public void setAcctAttribService(AcctAttribService acctAttribService) {
		this.acctAttribService = acctAttribService;
	}

	public MemberService getMemberService() {
		return memberService;
	}

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

	public IMessageDigest getiMessageDigest() {
		return iMessageDigest;
	}

	public void setiMessageDigest(IMessageDigest iMessageDigest) {
		this.iMessageDigest = iMessageDigest;
	}

	public void setMemberOperateService(MemberOperateService memberOperateService) {
		this.memberOperateService = memberOperateService;
	}
	public void setOperateLogService(OperateLogService operateLogService) {
    	this.operateLogService = operateLogService;
    }


	public void setCertQueryService(CertQueryService certQueryService) {
		this.certQueryService = certQueryService;
	}
	

	public void setPfxFile(String pfxFile) {
		this.pfxFile = pfxFile;
	}

	public void setPfxPwd(String pfxPwd) {
		this.pfxPwd = pfxPwd;
	}

//	public void setSecurityService(SecurityService securityService) {
//		this.securityService = securityService;
//	}
}
