/**
 * 
 */
package com.pay.fundout.service.ma.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.BeanUtils;

import com.pay.acc.service.member.dto.BankCardBindBO;
import com.pay.fundout.service.ma.BankCardBindFacadeService;
import com.pay.fundout.service.ma.BankCardBindInfo;
import com.pay.poss.service.adapter.AbstractExternalAdapter;
import com.pay.util.MD5Util;

/**
 * @author NEW
 *
 */
public class BankCardBindFacadeServiceImpl extends AbstractExternalAdapter implements BankCardBindFacadeService {

	
	@Override
	public List<BankCardBindInfo> getBankCardBindInfoList(long memberCode) {
		List<BankCardBindInfo> results = new ArrayList<BankCardBindInfo>();
		BankCardBindInfo info = null;
		try {
			List<BankCardBindBO> bankList = bankCardBindService.doQueryBankCardBindInfoForVerifyNsTx(Long.valueOf(memberCode));
			if(null == bankList || bankList.isEmpty()){
				return results;
			}
			for (BankCardBindBO bankCardBindBO : bankList) {
				if(bankCardBindBO.getAuditStatus() == 102){
					info = new BankCardBindInfo();
					BeanUtils.copyProperties(bankCardBindBO, info);
					info.setBankName(bankService.getBankById(info.getBankId()));
					info.setValidateCode(MD5Util.md5Hex(info.toString()));
					results.add(info);
				}
			}
		} catch (Throwable e) {
			log.error("call bankCardBindService.doQueryBankCardBindInfoForVerifyNsTx failed", e);
		}
		return results;
	}
	


}
