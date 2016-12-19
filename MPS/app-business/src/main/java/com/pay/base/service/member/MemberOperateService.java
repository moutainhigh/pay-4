package com.pay.base.service.member;



public interface MemberOperateService {

    public abstract String queryEpLastLoginTime(Long memberCode, Long operatorId);

}