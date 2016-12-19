package com.pay.base.dao.member;

import com.pay.base.model.MemberOperate;

public interface MemberOperateDAO {

    public abstract MemberOperate findMemberOperateByOperatorId(Long operatorId,Long memberCode);
    public abstract Long createMemberOperate(MemberOperate mo);

    public abstract int updateMemberOperate(Long id);

}