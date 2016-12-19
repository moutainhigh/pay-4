package com.pay.acc.member.dao;
/**
 * @author lei.jiangl 
 * @version 
 * @data 2010-11-15 下午02:15:21
 */
import com.pay.acc.member.model.Acct;
import com.pay.acc.member.model.AcctAttrib;
import com.pay.acc.member.model.IndividualInfo;
import com.pay.acc.member.model.Member;
import com.pay.inf.dao.BaseDAO;

public interface MemberCreateTempDAO extends BaseDAO<Object>{
	
	
	public Member createMember(Member member);
	
	public void createAcct(Acct acct);

	public Long createAcctAttrib(AcctAttrib acctAttrib);
	
	public IndividualInfo createIndividualInfo(IndividualInfo individualInfo);
	
	public String getAccCodeById(Long acctAttrId);
	
	public void createMemberProduct(Long memberCode);
}
