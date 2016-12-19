package com.pay.acc.member.service.impl;

import java.util.Random;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.member.dao.MemberCreateTempDAO;
import com.pay.acc.member.dao.MemberDAO;
import com.pay.acc.member.dto.ResultDto;
import com.pay.acc.member.exception.ErrorExceptionEnum;
import com.pay.acc.member.model.Acct;
import com.pay.acc.member.model.AcctAttrib;
import com.pay.acc.member.model.IndividualInfo;
import com.pay.acc.member.model.Member;
import com.pay.acc.member.service.MemberCreateTempService;
import com.pay.inf.exception.AppException;
import com.pay.inf.service.IMessageDigest;
/**
 * @author lei.jiangl 
 * @version 
 * @data 2010-11-15 下午12:26:34
 */
public class MemberCreateTempServiceImpl implements MemberCreateTempService {
	
	private MemberCreateTempDAO memberCreateTempDAO;
	private MemberDAO memberDAO;
	private IMessageDigest iMessageDigest;
	
	private final String STR="abcdefghijklmnopqrstuvwxyz0123456789";
	private final String VAL="TEMP_VALUE";
	
	private Log log = LogFactory.getLog(MemberCreateTempServiceImpl.class);
	
	@Override
	public ResultDto createTempMember(String loginName) throws AppException {
		ResultDto resultDto = new ResultDto();
		String autoPwd = this.getRandomString();
		//add member
		if(loginName==null || loginName.trim()==null){
			log.error("#############登录名不能为空##########");
			throw new AppException(ErrorExceptionEnum.MEMBER_LOGIN_NAME_EMPTY.getMessage());
		}
		Member newMember=this.setMemberValue(loginName,autoPwd,5);
		loginName=loginName.trim().toLowerCase();
		if(newMember==null){
			log.error("#############登录名必须为手机号或者邮箱地址##########");
			throw new AppException(ErrorExceptionEnum.MEMBER_LOGIN_NAME_ERROR.getMessage());
		}
		Member memberOld=memberDAO.queryMemberByLoginName(loginName);
		if(memberOld!=null){
			log.error("#############登录名"+memberOld.getLoginName()+"已经存在##########");
			throw new AppException(ErrorExceptionEnum.MEMBER_EXIST_ERROR.getMessage());
		}
		
		Member member = memberCreateTempDAO.createMember(newMember);
		Long mc = member.getMemberCode();
		//add  acctattrib
		Long acctAttrId = memberCreateTempDAO.createAcctAttrib(this.setAcctAttribValue(mc,null));
		String acctCode = memberCreateTempDAO.getAccCodeById(acctAttrId);
		//add acct
		memberCreateTempDAO.createAcct(this.setAcctValue(mc,acctCode));
		//add individualInfo
		memberCreateTempDAO.createIndividualInfo(this.setIndividualInfoValue(mc,loginName));
		//add member product
		memberCreateTempDAO.createMemberProduct(mc);
		log.info("create temp member:set memberProduct value done.");
		
		resultDto.setLoginName(member.getLoginName());//登录名
		resultDto.setMemberCode(member.getMemberCode());//会员号
		resultDto.setAcctCode(acctCode);//账户号
		resultDto.setTempAutoPwd("");//a-z+1-9 8位随机密码
		return resultDto;
	}
	
	@Override
	public boolean checkMpMemberLoginName(String loginName) {
		Member memberOld=memberDAO.queryMemberByLoginName(loginName);
		if(memberOld!=null){
			return false;
		
		}
		
		return true;
	}
	
	@Override
	public ResultDto createMpMember(String loginName,String realName,String loginPwd,String payPwd) throws AppException {
		ResultDto resultDto = new ResultDto();
		String autoPwd = loginPwd;
		//add member
		if(loginName==null || loginName.trim()==null){
			log.error("#############手机号不能为空##########");
			throw new AppException(ErrorExceptionEnum.MEMBER_LOGIN_NAME_EMPTY.getMessage());
		}
		Member newMember=this.setMemberValue(loginName,autoPwd,0);
		loginName=loginName.trim().toLowerCase();
		if(newMember==null){
			log.error("#############登录名必须为手机号##########");
			throw new AppException(ErrorExceptionEnum.MEMBER_LOGIN_NAME_MOBILE_ERROR.getMessage());
		}
		
		if(StringUtils.isBlank(realName) ||  realName.length()>50){
			throw new AppException(ErrorExceptionEnum.MEMBER_REALNAME_LENGTH_ERROR.getMessage());
		}
		
		if(StringUtils.isBlank(loginPwd) || loginPwd.length()<8 || loginPwd.length()>32){
			throw new AppException(ErrorExceptionEnum.MEMBER_LOGINPWD_LENGTH_ERROR.getMessage());
		}
		
		if(StringUtils.isBlank(payPwd) || payPwd.length()<8 || payPwd.length()>32){
			throw new AppException(ErrorExceptionEnum.MEMBER_PAYPWD_LENGTH_ERROR.getMessage());
		}
		
		Member memberOld=memberDAO.queryMemberByLoginName(loginName);
		if(memberOld!=null){
			log.error("#############登录名"+memberOld.getLoginName()+"已经存在##########");
			throw new AppException(ErrorExceptionEnum.MEMBER_MOBILE_EXIST_ERROR.getMessage());
		}
		
		Member member = memberCreateTempDAO.createMember(newMember);
		Long mc = member.getMemberCode();
		//add  acctattrib
		System.out.println("payPwd========================="+payPwd);
		Long acctAttrId = memberCreateTempDAO.createAcctAttrib(this.setAcctAttribValue(mc,payPwd));
		String acctCode = memberCreateTempDAO.getAccCodeById(acctAttrId);
		//add acct
		memberCreateTempDAO.createAcct(this.setAcctValue(mc,acctCode));
		//add individualInfo
		memberCreateTempDAO.createIndividualInfo(this.setIndividualInfoValue(mc,realName));
		//add member product
		memberCreateTempDAO.createMemberProduct(mc);
		log.info("create temp member:set memberProduct value done.");
		
		resultDto.setLoginName(member.getLoginName());//登录名
		resultDto.setMemberCode(member.getMemberCode());//会员号
		resultDto.setAcctCode(acctCode);//账户号
		resultDto.setTempAutoPwd("");//a-z+1-9 8位随机密码
		return resultDto;
	}
	
	/**
	 *设置MEMBER
	 * @param PhoneNo
	 * @param pwd 
	 * @return Member
	 * @throws AppException
	 */
	private Member setMemberValue(String loginName,String pwd,int status) throws AppException{
		log.info("create temp member:set member value start.");
		Member cm = new Member();
		int loginType=1;
		if(this.checkPhone(loginName)){
			loginType=1;
		}else if(this.checkEmail(loginName)){
			loginType=2;
		}else{
			log.error("loginName is not email and phone");
			return null;
		}
		
		cm.setType(1);
		cm.setServiceLevelCode(100);
		//cm.setGreeting(VAL);
		cm.setStatus(status);
		cm.setSecurityQuestion(0);
		cm.setSecurityAnswer(VAL);
		cm.setLoginType(loginType);
		cm.setLoginName(loginName);
		try {
			cm.setLoginPwd(iMessageDigest.genMessageDigest(pwd.getBytes()));
		} catch (Exception e) {
			log.error(e);
		}
		log.info("create temp member:set member value done.");
		return cm;
	}
	
	
	/**
	 *设置Acct
	 * @param memberCode
	 * @param acctCode
	 * @return Acct
	 * @throws AppException
	 */
	private Acct setAcctValue(Long memberCode,String acctCode)throws AppException {
		log.info("create temp member:set acct value start.");
		Acct acct = new Acct();
		acct.setAcctCode(acctCode);
		acct.setMemberCode(memberCode);
		log.info("create temp member:set acct value done.");
		return acct;
	}
	/**
	 * 设置AcctAttrib
	 * @param memberCode
	 * @return
	 * @throws AppException
	 */
	private AcctAttrib setAcctAttribValue(Long memberCode,String payPwd)throws AppException {
		log.info("create temp member:set acctattrib value start.");
		AcctAttrib acctAttrib = new AcctAttrib();
		acctAttrib.setMemberCode(memberCode);
		try {
			if(StringUtils.isNotBlank(payPwd))
				acctAttrib.setPayPwd(iMessageDigest.genMessageDigest(payPwd.getBytes()));
		} catch (Exception e) {
			log.error(e);
		}
		log.info("create temp member:set acctattrib value done.");
		return acctAttrib;
	}
	/**
	 * 设置IndividualInfo
	 * @param memberCode
	 * @return
	 * @throws AppException
	 */
	private IndividualInfo setIndividualInfoValue(Long memberCode,String name)throws AppException {
		log.info("create temp member:set individualInfo value start.");
		IndividualInfo individualInfo = new IndividualInfo();
		individualInfo.setMemberCode(memberCode);
		individualInfo.setName(name);
		
		log.info("create temp member:set individualInfo value done.");
		return individualInfo;
	}
	
	/**
	 * 生成随机8位密码
	 * @return
	 * @throws AppException
	 */
	private String getRandomString()throws AppException {
		log.info("create temp member:reandom pwd start.");
	    Random random = new Random();
	    StringBuffer sb = new StringBuffer();
	    for (int i = 0; i < 8; i++) {
	        int number = random.nextInt(STR.length());
	        sb.append(STR.charAt(number));
	    }
	    log.info("create temp member:reandom pwd done.");
	    return sb.toString();
	 }  

	public void setMemberCreateTempDAO(MemberCreateTempDAO memberCreateTempDAO) {
		this.memberCreateTempDAO = memberCreateTempDAO;
	}
	public void setiMessageDigest(IMessageDigest iMessageDigest) {
		this.iMessageDigest = iMessageDigest;
	}
	
	public void setMemberDAO(MemberDAO memberDAO) {
		this.memberDAO = memberDAO;
	}

	//=====================判断邮件email是否正确格式
	private  boolean checkEmail(String email) {
        boolean flag = false;
         String check = "^\\w+((-\\w+)|(\\.\\w+))*\\@[A-Za-z0-9]+((\\.|-)[A-Za-z0-9]+)*\\.[A-Za-z0-9]+$"; 
         Pattern p = Pattern.compile(check);
        Matcher m = p.matcher(email.trim());
        if (m.matches()) {
            flag = true;
        }
        return flag;
    }



	//=====================判断手机号phone是否正确格式
	private  boolean checkPhone(String phone){
        Pattern p =Pattern.compile("^(13|14|15|18)\\d{9}$");
        Matcher matcher = p.matcher(phone);
        if (matcher.matches()) {
            return true;
        }
        return false;
    }
	
//	public static void main(String [] args){
//	MemberCreateTempServiceImpl a = new MemberCreateTempServiceImpl();
//	System.out.println(a.getRandomString());
//}
}
