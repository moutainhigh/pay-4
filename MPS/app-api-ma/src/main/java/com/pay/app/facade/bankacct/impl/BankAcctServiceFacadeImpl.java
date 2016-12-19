/**
 * @create time 2010-9-16 -下午03:53:51
 * @description TODO
 * @project app-api-ma
 * @author & mail   sunny.ying@staff.pay.com
 */
package com.pay.app.facade.bankacct.impl;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.app.facade.bankacct.BankAcctServiceFacade;
import com.pay.cardbin.service.CardBinInfoFactoryService;
import com.pay.inf.dto.Bank;
import com.pay.inf.dto.CityDTO;
import com.pay.inf.dto.ProvinceDTO;
import com.pay.inf.service.BankService;
import com.pay.inf.service.CityService;
import com.pay.inf.service.ProvinceService;

/**
 * @author sunny.ying
 * 
 */
public class BankAcctServiceFacadeImpl implements BankAcctServiceFacade {

	private CardBinInfoFactoryService cardBinInfoFactoryService;

	private BankService bankService;
	private ProvinceService provinceService;
	private CityService cityService;

	private final Log log = LogFactory.getLog(BankAcctServiceFacadeImpl.class);

	public String getBankNameByCode(String bankCode) {
		return bankService.getBankById(bankCode);
	}

	public String getCityById(Integer cityCode) {
		CityDTO cityDTO = cityService.findByCityCode(cityCode);
		return cityDTO.getCityname();
	}

	public String getProvinceById(Integer provinceCode) {
		ProvinceDTO provinceDTO = provinceService.findById(provinceCode);
		return provinceDTO.getProvincename();
	}

	public List<CityDTO> getAllCity(Integer provinceId) {
		try {
			return cityService.findByProvinceId(provinceId);
		} catch (Exception e) {
			log.error("BankAcctServiceFacadeImpl getAllCity throws error:", e);
			return null;
		}
	}

	public List<ProvinceDTO> getAllProvince() {
		try {
			List<ProvinceDTO> provinceList = provinceService.findAll();
			if (provinceList != null && !provinceList.isEmpty()) {
				return provinceList;
			} else {
				return null;
			}
		} catch (Exception e) {
			log.error("BankAcctServiceFacadeImpl getAllProvince throws error:",
					e);
			return null;
		}
	}

	public List<Bank> getBindAllBankList(String param) {
		try {
			String cidverify = "cidverify";
			List<Bank> bankList = bankService.getWithdrawBanks();
			if (param.equals(cidverify)) {
				List<Bank> onlyBankList = new ArrayList<Bank>();
				String bank = "交通银行";
				if (bankList != null && !bankList.isEmpty()) {
					for (int i = 0; i < bankList.size(); i++) {
						if (bank.equals(bankList.get(i).getBankName())) {
							Bank banks = new Bank();
							banks.setBankId(bankList.get(i).getBankId());
							banks.setBankName(bank);
							onlyBankList.add(banks);
						}

					}
				}
				return onlyBankList;
			} else
				return bankList;

		} catch (Exception e) {
			log.error(
					"BankAcctServiceFacadeImpl getBindAllBankList throws error:",
					e);
			return null;
		}
	}

	public boolean isValidCardBin(String bankCode, String bankNumber) {
		try {
			return cardBinInfoFactoryService.isValidCardBin(
					bankCode, bankNumber);
		} catch (Exception e) {
			log.error("BankAcctServiceFacadeImpl isValidCardBin throws error:",
					e);
			return false;
		}
	}

	public boolean isDebitCard(String bankCode, String bankNumber) {
		try {
			return cardBinInfoFactoryService.isDebitCard(
					bankCode, bankNumber);
		} catch (Exception e) {
			log.error("BankAcctServiceFacadeImpl isDebitCard throws error:", e);
			return false;
		}
	}

	public boolean isCreditCard(String bankCode, String bankNumber) {
		try {
			return cardBinInfoFactoryService.isCreditCard(
					bankCode, bankNumber);
		} catch (Exception e) {
			log.error("BankAcctServiceFacadeImpl isCreditCard throws error:", e);
			return false;
		}
	}

	public int validCardNo(String bankCode, String bankNumber) {
		int result = 0;// 正常
		// if(!this.isValidCardBin(bankCode, bankNumber)){
		// result = 2;//卡号不正确
		// return result;
		// }
		if (this.isCreditCard(bankCode, bankNumber)) {
			result = 1;// 如果是信用卡则不通过
			return result;
		}
		return result;
	}

	public void setCardBinInfoFactoryService(
			CardBinInfoFactoryService cardBinInfoFactoryService) {
		this.cardBinInfoFactoryService = cardBinInfoFactoryService;
	}

	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}

	public void setProvinceService(ProvinceService provinceService) {
		this.provinceService = provinceService;
	}

	public void setCityService(CityService cityService) {
		this.cityService = cityService;
	}

}
