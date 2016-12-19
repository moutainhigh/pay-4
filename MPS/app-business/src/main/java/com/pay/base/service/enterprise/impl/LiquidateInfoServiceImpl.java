/*
 * pay.com Inc.
 * Copyright (c) 2006-2010 All Rights Reserved.
 */
package com.pay.base.service.enterprise.impl;

import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.pay.app.facade.bankacct.BankAcctServiceFacade;
import com.pay.base.dao.enterprise.LiquidateInfoDAO;
import com.pay.base.model.LiquidateInfo;
import com.pay.base.service.enterprise.LiquidateInfoService;
import com.pay.base.service.member.MemberRelationService;
import com.pay.inf.exception.AppException;
import com.pay.util.DESUtil;

/**
 * 企业会员结算信息服务
 * @author zhi.wang
 * @version $Id: LiquidateInfoServiceImpl.java, v 0.1 2010-10-12 上午10:45:21 zhi.wang Exp $
 */
public class LiquidateInfoServiceImpl implements LiquidateInfoService {

    private LiquidateInfoDAO liquidateInfoDAO;
    private BankAcctServiceFacade bankAcctServiceFacade;
    private MemberRelationService memberRelationService;

    
    public void setBankAcctServiceFacade(BankAcctServiceFacade bankAcctServiceFacade) {
		this.bankAcctServiceFacade = bankAcctServiceFacade;
	}

	/**
	 * @param memberRelationService the memberRelationService to set
	 */
	public void setMemberRelationService(MemberRelationService memberRelationService) {
		this.memberRelationService = memberRelationService;
	}


	/** 
     * @param memberCode
     * @return
     * @see com.pay.base.service.enterprise.LiquidateInfoService#getByMemberCode(long)
     */
    @Override
    public List<LiquidateInfo> getByMemberCode(long memberCode) {
        List<LiquidateInfo> list =  liquidateInfoDAO.getByMemberCode(memberCode);
        if(list != null && list.size() > 0){
            for (LiquidateInfo liquidateInfo : list) {
                if(StringUtils.isNotBlank(liquidateInfo.getBankAcct())){
                    // 解密银行账号
                    String benkAcct = DESUtil.decrypt(liquidateInfo.getBankAcct());
                    liquidateInfo.setDecryptBankAcct(benkAcct);
                    String str = StringUtils.right(benkAcct, 4);
                    liquidateInfo.setEndBankAcct(StringUtils.repeat("*", benkAcct.length()-4)+str);
                }
                //设置大银行名称
                liquidateInfo.setBigBankName(bankAcctServiceFacade.getBankNameByCode(liquidateInfo.getBankId())); 
            }
        }
        return list;
    }
    
    
    
    public void setLiquidateInfoDAO(LiquidateInfoDAO liquidateInfoDAO) {
        this.liquidateInfoDAO = liquidateInfoDAO;
    }

	@Override
	public boolean saveOrUpdate(LiquidateInfo liquidateInfo) {
		//判断是否是更新
		if(liquidateInfo.getLiquidateId() != null && liquidateInfo.getLiquidateId()>0){
			return liquidateInfoDAO.updateLiquidateInfo(liquidateInfo) == 1;
		}
		return liquidateInfoDAO.createLiquidateInfo(liquidateInfo)>0;
	}



	@Override
	public Integer addOrUpdateCorpBankAcctRnTx(LiquidateInfo liquidateInfo,
			Long memberCode, String cardNo) throws AppException {
		
		Integer result = 0; //默认失败
		//判断 如果不是更新
		if(liquidateInfo.getLiquidateId()==null || liquidateInfo.getLiquidateId()<= 0 ){
				//新添加时要判断是否
				//判断绑定的卡的数量
				int cardNoCount = liquidateInfoDAO.getCountByMemberCode(memberCode);
				if (cardNoCount >= 10) {
					throw new AppException("绑定卡的数量不能大于10张 ");
				}
				
				if(liquidateInfo != null)
				{
					//判断卡号是否已经绑定
					List<LiquidateInfo> daoList= liquidateInfoDAO.getByMemberCode(memberCode);
					if(daoList != null && daoList.size() > 0)
					{			
						for(int i=0;i<daoList.size();i++)
						{
							LiquidateInfo banks = (LiquidateInfo)daoList.get(i);
							String decryptBankNo = DESUtil.decrypt(banks.getBankAcct());
							if(decryptBankNo != null)
							{
								if(decryptBankNo.equals(cardNo))
								{ 	 	
									return 2;
								}
							}
						}
					}
			}
		}
		//更新
		//判断是否可以更新
		else{
			LiquidateInfo forUpdateLi  = liquidateInfoDAO.getById( liquidateInfo.getLiquidateId());
			int hasError = validateEditCard(String.valueOf(forUpdateLi.getMemberCode()), forUpdateLi.getLiquidateId(),liquidateInfo.getBankAcct());
			if(hasError == 1){
				return 2;//卡片如果存在返回的是2
			}
		}
				//加密卡号
		Integer mode = liquidateInfoDAO.getAccountMode(memberCode);
		liquidateInfo.setMemberCode(memberCode);
		liquidateInfo.setAccountMode(mode);
		liquidateInfo.setBankAcct(DESUtil.encrypt(liquidateInfo.getBankAcct()));
		boolean isSaveed =  this.saveOrUpdate(liquidateInfo); 
		if(isSaveed)
			return 1;
		return result;
	}



	@Override
	public boolean removeCorpAcctByMemberCodeAndId(String memberCode,Long id) {
		return 1 == liquidateInfoDAO.removeByMemberCodeAndId(memberCode, id);
	}



	@Override
	public LiquidateInfo getById(long id) {
		LiquidateInfo info = liquidateInfoDAO.getById(id);
//		info.setBankAcct(DESUtil.decrypt(info.getBankAcct()));
//		//可以编辑的条件是要么是自己，要么是父子关系的
//		if(info.getMemberCode().longValue() == Long.valueOf(memberCode) 
//				|| memberRelationService.isFatherAndSon(info.getMemberCode(), Long.valueOf(memberCode))){
//			return info;
//		}
		return info;
	}



	@Override
	public boolean setDefaultCordAcctRnTx(String memberCode, Long id) {
		
		LiquidateInfo li = liquidateInfoDAO.getById(id);
		//判断是否是自己的卡,要么是父子关系的
		if(li.getMemberCode().longValue() != Long.valueOf(memberCode) 
				|| memberRelationService.isFatherAndSon(li.getMemberCode(), Long.valueOf(memberCode))){
			return false;
		}
		//更新所有状态为非默认
		int v1 =   liquidateInfoDAO.updateStatus(li.getMemberCode()+"", LiquidateInfo.NOT_DEFAULT_VALUE);
		if(v1==0){
			return false;
		}
		//更新要设置的id号为默认
		int v2 =   liquidateInfoDAO.updateStatus(li.getMemberCode()+"",id,LiquidateInfo.DEFAULT_VALUE);
		return v2 == 1;
	}



	@Override
	public boolean setNotDefaultCordAcctRnTx(String memberCode, Long id) {
		LiquidateInfo li = liquidateInfoDAO.getById(id);
		//判断是否是自己的卡,要么是父子关系的
		if(li.getMemberCode().longValue() != Long.valueOf(memberCode)
				|| memberRelationService.isFatherAndSon(li.getMemberCode(), Long.valueOf(memberCode))){
			return false;
		}
		//更新所有状态为非默认
		//更新要设置的id号为非默认
		int v2 =   liquidateInfoDAO.updateStatus(li.getMemberCode()+"",id,LiquidateInfo.NOT_DEFAULT_VALUE);
		return v2 == 1;


	}


	@Override
	public int validateEditCard(String memberCode,Long id,String cardNo) {
		
		List<LiquidateInfo> list = getByMemberCode(Long.valueOf(memberCode));
		//比较卡片是否已经存在了
		for (LiquidateInfo liquidateInfo : list) {
			if(id.longValue()!= liquidateInfo.getLiquidateId().longValue()){
				if(cardNo.equals(liquidateInfo.getDecryptBankAcct())){
					return 1;
				}
			}
				
		}
		
		return 0;
	}

	//	去查询一共绑定了几张银行卡
	@Override
	public int getCountByMemberCode(Long memberCode) {
		return liquidateInfoDAO.getCountByMemberCode(memberCode);
	}
    
	
	public boolean hasPromisson(Long sessionMebmerCode,Long paramMemberCode){
		if(sessionMebmerCode.longValue() == paramMemberCode ){
			return true;
		}
		boolean is =  memberRelationService.isFatherAndSon(paramMemberCode, sessionMebmerCode);
		return is;
	}
	/***
	 * 查询历史绑定的银行卡
	 */
	@Override
	public List<LiquidateInfo> queryBankCardList(String memberCode) {
		List<LiquidateInfo> liquidateInfos = liquidateInfoDAO.getByMemberCode(Long.parseLong(memberCode));
		return liquidateInfos;
	}
}
