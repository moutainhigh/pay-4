package com.pay.poss.service.inf.input.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import com.pay.inf.dto.Bank;
import com.pay.inf.service.BankService;
import com.pay.poss.service.inf.input.BankInfoFacadeService;

public class BankInfoFacadeServiceImpl implements BankInfoFacadeService {
	private BankService bankService;
	/**
	 * @param bankService the bankService to set
	 */
	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}

	@Override
	public List<Map<String, String>> getWithdrawBankList() {
		List<Map<String,String>> withdrawBankList = new ArrayList<Map<String,String>>();
		
		List<Bank> bankList = bankService.getWithdrawBanks();
		
		for(Bank bank : bankList){
			Map<String,String> bankMap = new HashMap<String, String>();
			bankMap.put("text", bank.getBankName());
			bankMap.put("value", String.valueOf(bank.getBankId()));
			withdrawBankList.add(bankMap);
		}
		return withdrawBankList;
	}

	public List<Bank> getWithdrawBanks(){
		return bankService.getWithdrawBanks();
	}
	@Override
	public String getBankNameById(String id) {
		return this.bankService.getBankById(id);
	}

	@Override
	public List<Map<String, String>> getAllBankList() {
		List<Bank> bankList = bankService.getAllBankList();
		List<Map<String,String>> allBankList = new ArrayList<Map<String,String>>();
		for(Bank bank : bankList){
			Map<String,String> bankMap = new HashMap<String, String>();
			bankMap.put("text", bank.getBankName());
			bankMap.put("value", String.valueOf(bank.getBankId()));
			allBankList.add(bankMap);
		}
		return allBankList;
	}

}
