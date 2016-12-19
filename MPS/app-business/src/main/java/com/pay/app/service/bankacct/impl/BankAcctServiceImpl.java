/**
 * 
 */
package com.pay.app.service.bankacct.impl;


import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.service.member.MemberVerifyService;
import com.pay.acc.service.member.dto.MemberVerifyResult;
import com.pay.app.dao.bankacct.BankAcctDAO;
import com.pay.app.model.BankAcct;
import com.pay.app.service.bankacct.BankAcctService;
import com.pay.base.model.LiquidateInfo;
import com.pay.lucene.dto.SearchParamInfoDTO;
import com.pay.lucene.dto.SearchResultInfoDTO;
import com.pay.lucene.service.LuceneService;
import com.pay.poss.base.exception.PossException;
import com.pay.util.DESUtil;


/**
 * @author sunny.ying
 *
 */
public class BankAcctServiceImpl  implements BankAcctService{
	
	private final Log log = LogFactory.getLog(BankAcctServiceImpl.class);
	/**
	 * 银行卡已经存在
	 */
	private static final int CREATE_FAIL_BAKD_ACCT_IS_EXIST = 2;
	
	/**
	 * 银行卡超过10张
	 */
	private static final int CREATE_FAIL_MEMBERCODE_CARD_NUMBER_COUNT_ERROR = 3;	
	
	/**
	 * 更新失败
	 */
	private static final int update_result= 0;
	/**
	 * 输入的卡号已存在
	 */
	private static final int cardExits= 2;
	/**
	 * 输入的卡号不存在
	 */
	private static final int no_cardExits= 1;
	/**
	 * 银行卡添加成功
	 */
	private static final Integer success = 1;
	
	private static final Integer bank_result_size = 300;
	
	/**
	 * 银行卡是否已经超过10张
	 */
	private static final Integer max_bank_card_nums = 10;
	
	private BankAcctDAO bankAcctDAO;
	
	private MemberVerifyService memberVerifyService;
	
	private LuceneService luceneService;


	public int getUserBankAcctNum(String memberCode){
		return bankAcctDAO.getUserBankAcctNum(memberCode);
	}

	public void setBankAcctDAO(BankAcctDAO bankAcctDAO) {
		this.bankAcctDAO = bankAcctDAO;
	}


	public void setMemberVerifyService(MemberVerifyService memberVerifyService) {
		this.memberVerifyService = memberVerifyService;
	}

	public void setLuceneService(LuceneService luceneService) {
		this.luceneService = luceneService;
	}

	

	public List<SearchResultInfoDTO> getBankAcctFO(String bankName,
			String provinceName, String cityName, String keyWord){
		if(StringUtils.isEmpty(bankName) || StringUtils.isEmpty(provinceName) || StringUtils.isEmpty(cityName)){
			return null;
		}
		SearchParamInfoDTO param = new SearchParamInfoDTO();
		param.setResultSize(bank_result_size);
		param.setBankName(bankName);
		param.setProvinceName(provinceName);
		param.setCityName(cityName);
		param.setKeyWord(keyWord);
		try {
			return luceneService.searchUnionBankCodeInfo(param);
		} catch (PossException e) {
			log.error(e.getMessage());
			return null;
		}
	}
	
	public boolean getMemberVerifyState(String memberCode) {
		if(memberCode != null && !"".equals(memberCode)){
			try {
				MemberVerifyResult memberVerifyResult = memberVerifyService.QueryMemberVerifyByMemberCode(Long.parseLong(memberCode));
				return memberVerifyResult.isVerify();
			} catch (NumberFormatException e) {
				log.error(e.getMessage());
				return false;
			} catch (Exception e) {
				log.error(e.getMessage());
				return false;
			}
		}
		return false;
	}
	
	
	public int editBankAcct(BankAcct bankAcct,String memberCode) {
		BankAcct bankcard=bankAcctDAO.getBankAcctById(bankAcct.getId());
		if(bankcard != null)
		{
			return bankAcctDAO.editBankAcct(bankAcct);
		}
		else
		{
			return update_result;
		}
	}
	
	
	/**
	 * 判断绑定的银行卡号是否已经存在
	 * @author Sunny Ying
	 * @param bankAcct
	 * @param memberCode
	 * @throw null
	 * @return int
	 */
	public int getMemberBankCard(BankAcct bankAcct,String memberCode){
		Integer result = 1; //成功
		if(bankAcct != null)
		{
			String bankNo = bankAcct.getBankAcctId();
			List<BankAcct> daoList= bankAcctDAO.getBankAcctByCode(Long.parseLong(memberCode));
			if(daoList != null && daoList.size() > 0)
			{			
				for(int i=0;i<daoList.size();i++)
				{
					BankAcct banks = (BankAcct)daoList.get(i);
					String decryptBankNo = banks.getBankAcctId();//DESUtil.decrypt(banks.getBankAcctId());
//					if(decryptBankNo != null)
//					{
						if(decryptBankNo.equals(bankNo))
						{
							result = CREATE_FAIL_BAKD_ACCT_IS_EXIST;
							return result;
						}
//					}
//					else{
//						return result;
//					}
				}
			}
		}
		return result;
	}

	public BankAcct getBankAcctById(Long id) {
		BankAcct bankAcct =  bankAcctDAO.getBankAcctById(id);
		if(bankAcct!=null){
			bankAcct.setBankAcctId(DESUtil.decrypt(bankAcct.getBankAcctId()));
		}
		return bankAcct;
	}


	public int removeBankAcctById(Long id) {
		BankAcct bankcard=bankAcctDAO.getBankAcctById(id);
		if(bankcard != null)
		{
			return bankAcctDAO.removeBankAcctById(id);
		}
		else
		{
			return update_result;
		}
	}
	
	public BankAcct queryBankAcct(BankAcct bankAcct) {
		return bankAcctDAO.queryBankAcct(bankAcct);
	}
	
	public List<BankAcct> getBankAcctByCode(String memberCode) {
		List<BankAcct> daoList= bankAcctDAO.getBankAcctByCode(Long.parseLong(memberCode));

		List<BankAcct> bankAcctList=new ArrayList<BankAcct>();
		if(daoList != null && daoList.size() > 0)
		{			
			for(int i=0;i<daoList.size();i++)
			{
				BankAcct bankAcct=new BankAcct();
				BankAcct bank=new BankAcct();
				bankAcct=(BankAcct)daoList.get(i);
				bank.setBankAcctId(DESUtil.decrypt(bankAcct.getBankAcctId()));
				bank.setBankId(bankAcct.getBankId());
				bank.setBranchBankName(bankAcct.getBranchBankName());
				bank.setCity(bankAcct.getCity());
				bank.setCreationDate(bankAcct.getCreationDate());
				bank.setId(bankAcct.getId());
				bank.setIsPrimaryBankacct(bankAcct.getIsPrimaryBankacct());
				bank.setMemberCode(bankAcct.getMemberCode());
				bank.setName(bankAcct.getName());
				bank.setProvince(bankAcct.getProvince());
				bank.setStatus(bankAcct.getStatus());
				bankAcctList.add(bank);
			}
		}
		return bankAcctList;
	}
	/*
	 * @author Rainy xiaodai 
	 * (non-Javadoc)
	 * @see com.pay.app.service.bankacct.BankAcctService#queryBankAcctByMemberCode(java.lang.String)
	 */
	@Override
	public List<LiquidateInfo> queryBankAcctByMemberCode(String memberCode) {

		List<LiquidateInfo> daoList = bankAcctDAO
				.queryBankAcctByMemberCode(Long.parseLong(memberCode));
		
		
		
		
		return daoList;
	}

	
	public Integer addOrUpdateBankAcct(BankAcct bankAcct,String memberCode){
		
		
			//如果id号为空则是新增加 
			if(bankAcct.getId()==null || bankAcct.getId()<= 0 ){
				Integer result = 0; //默认失败
				List<BankAcct> daoList= bankAcctDAO.getBankAcctByCode(Long.parseLong(memberCode));
				/**
				 * 判断绑定的银行卡号是否已经存在
				 */
				if(daoList != null && daoList.size() > 0)
				{			
					for(int i=0;i<daoList.size();i++)
					{
						BankAcct banks = (BankAcct)daoList.get(i);
						String decryptBankNo = DESUtil.decrypt(banks.getBankAcctId());
						if(decryptBankNo != null)
						{
							if(decryptBankNo.equals(bankAcct.getBankAcctId()))
							{
								result = CREATE_FAIL_BAKD_ACCT_IS_EXIST;
								return result;
							}
						}
						else{
							return result;
						}
					}
				}
				
			Integer bankCardNum = bankAcctDAO.getUserBankAcctNum(bankAcct
					.getMemberCode().toString());
					if(bankCardNum >= max_bank_card_nums)
					{
						result=CREATE_FAIL_MEMBERCODE_CARD_NUMBER_COUNT_ERROR;
					}
					else
					{
						bankAcct.setBankAcctId(DESUtil.encrypt(bankAcct.getBankAcctId()));
						bankAcctDAO.addBankAcct(bankAcct);
						result=success;
					}
					return result;
			}
			
			//更新
			//判断是否可以更新
			int hasError = validateEditCard(memberCode, bankAcct.getId(),bankAcct.getBankAcctId());
			if(hasError == 1){
				return CREATE_FAIL_BAKD_ACCT_IS_EXIST;
			}
			bankAcct.setBankAcctId(DESUtil.encrypt(bankAcct.getBankAcctId()));
			return bankAcctDAO.editBankAcct(bankAcct);
			
			
	
	}
	
	@Override
	public int validateEditCard(String memberCode,Long id,String cardNo) {
		
		List<BankAcct> list = getBankAcctByCode(memberCode);
		//比较卡片是否已经存在了
		for (BankAcct bankAcct : list) {
			if(id.longValue() != bankAcct.getId().longValue()){
				if(cardNo.equals(bankAcct.getBankAcctId())){
					return 1;
				}
			}
		}
		return 0;
	}

	@Override
	public boolean setDefaultAcctRnTx(String memberCode, Long id) {
		BankAcct li = getBankAcctById(id);
		//判断是否是自己的卡
		if(li.getMemberCode().longValue() != Long.valueOf(memberCode)){
			return false;
		}
		//更新所有状态为非默认
		int v1 =   bankAcctDAO.updateStatus(memberCode, BankAcct.NOT_DEFAULT_VALUE);
		if(v1==0){
			return false;
		}
		//更新要设置的id号为默认
		int v2 =   bankAcctDAO.updateStatus(memberCode,id,BankAcct.DEFAULT_VALUE);
		return v2 == 1;
	}

	@Override
	public boolean setNotDefaultAcctRnTx(String memberCode, Long id) {
		BankAcct li = getBankAcctById(id);
		//判断是否是自己的卡
		if(li.getMemberCode().longValue() != Long.valueOf(memberCode)){
			return false;
		}
		//更新所有状态为非默认
		//更新要设置的id号为非默认
		int v2 =   bankAcctDAO.updateStatus(memberCode,id,LiquidateInfo.NOT_DEFAULT_VALUE);
		return v2 == 1;

	}


}
