package com.pay.base.service.member.impl;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.base.dao.member.MemberOperateDAO;
import com.pay.base.model.MemberOperate;
import com.pay.base.service.member.MemberOperateService;

public class MemberOperateServiceImpl implements MemberOperateService {
    private static final Log      logger = LogFactory.getLog(MemberOperateServiceImpl.class);
    
    private MemberOperateDAO memberOperateDAO;

    private SimpleDateFormat sdf=new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    
    /***************************************************************
     * 描     述：
     * 方法名:	queryEpLastLoginTime (MemberOperateServiceImpl) 
     * (non-Javadoc)
     * @see com.pay.base.service.member.MemberOperateService#queryEpLastLoginTime(java.lang.Long, java.lang.Long)
     * @param memberCode
     * @param operatorId
     * @return
     */
    public String queryEpLastLoginTime(Long memberCode,Long operatorId){
        
        Date nowDate=new Date();
        String lastLoginTime=sdf.format(nowDate);
        if(operatorId!=null && memberCode!=null){
            try {
                MemberOperate mo= memberOperateDAO.findMemberOperateByOperatorId(operatorId,memberCode);
                if(mo==null){
                    mo=new MemberOperate();
                    mo.setLastLoginTime(nowDate);
                    mo.setOperatorId(String.valueOf(operatorId));
                    mo.setMemberCode(memberCode);
                    memberOperateDAO.createMemberOperate(mo);
                }else{
                    lastLoginTime=sdf.format(mo.getLastLoginTime());
                    memberOperateDAO.updateMemberOperate(mo.getId());
                }
            } catch (Exception e) {
                logger.error("MemberOperateServiceImpl.queryEpLastLoginTime throws error",e);
            }
        }
            
        
        return lastLoginTime;
    } 
    
    
    /***************************************************************
     * 描     述：
     * 方法名:	getMemberOperateDAO (MemberOperateServiceImpl) 
     * (non-Javadoc)
     * @see com.pay.base.service.member.MemberOperateService#getMemberOperateDAO()
     * @return
     */
    public MemberOperateDAO getMemberOperateDAO() {
        return memberOperateDAO;
    }

    /***************************************************************
     * 描     述：
     * 方法名:	setMemberOperateDAO (MemberOperateServiceImpl) 
     * (non-Javadoc)
     * @see com.pay.base.service.member.MemberOperateService#setMemberOperateDAO(com.pay.base.dao.member.MemberOperateDAO)
     * @param memberOperateDAO
     */
    public void setMemberOperateDAO(MemberOperateDAO memberOperateDAO) {
        this.memberOperateDAO = memberOperateDAO;
    }
}
