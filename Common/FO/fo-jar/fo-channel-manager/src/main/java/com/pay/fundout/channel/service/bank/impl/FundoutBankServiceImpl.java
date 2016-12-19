/** @Description 
 * @project 	fo-channel-manager
 * @file 		FundoutBankServiceImpl.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-10-27		Henry.Zeng			Create 
 */
package com.pay.fundout.channel.service.bank.impl;

import java.util.List;

import org.springframework.beans.BeanUtils;

import com.pay.fundout.channel.dao.bank.FundoutBankDAO;
import com.pay.fundout.channel.dto.bank.FundoutBankDTO;
import com.pay.fundout.channel.model.bank.FundoutBank;
import com.pay.fundout.channel.service.bank.FundoutBankService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.inf.service.BankService;
import com.pay.poss.base.exception.PossUntxException;
import com.pay.poss.base.exception.enums.ExceptionCodeEnum;

/**
 * <p>
 * </p>
 * 
 * @author Henry.Zeng
 * @since 2010-10-27
 * @see
 */
public class FundoutBankServiceImpl implements FundoutBankService {

	private FundoutBankDAO fundoutBankDAO;
	private BankService bankService;

	public void setFundoutBankDAO(final FundoutBankDAO fundoutBankDAO) {
		this.fundoutBankDAO = fundoutBankDAO;
	}

	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}

	@Override
	public long addFundoutBankInfo(FundoutBankDTO dto) throws PossUntxException {
		try {
			FundoutBank model = new FundoutBank();
			BeanUtils.copyProperties(dto, model);
			return fundoutBankDAO.addFundoutBankInfo(model);
		} catch (Exception e) {
			throw new PossUntxException("bankId is already ",
					ExceptionCodeEnum.DATA_ACCESS_EXCEPTION, e);
		}
	}

	@SuppressWarnings("unchecked")
	@Override
	public Page<FundoutBankDTO> queryFundoutBankPage(Page<FundoutBankDTO> page,
			FundoutBankDTO dto) {
		FundoutBank model = new FundoutBank();
		BeanUtils.copyProperties(dto, model);

		Page<FundoutBank> pageModel = new Page<FundoutBank>();

		PageUtils.setServicePage(pageModel, page);

		pageModel = fundoutBankDAO.queryFundoutBankInfos(pageModel, model);

		FundoutBankDTO bankDTO = new FundoutBankDTO();

		page.setResult((List<FundoutBankDTO>) PageUtils.changePageList(
				pageModel.getResult(), bankDTO, null));

		PageUtils.setServicePage(page, pageModel);

		return page;
	}

	@Override
	public int updateFundoutBankInfo(FundoutBankDTO dto) {
		FundoutBank model = new FundoutBank();
		BeanUtils.copyProperties(dto, model);
		return fundoutBankDAO.updateFundoutBankInfo(model);
	}

	@Override
	public FundoutBankDTO queryFundoutBank(String bankId) {
		FundoutBankDTO bank = new FundoutBankDTO();
		String bankName = bankService.getBankById(bankId);
		bank.setBankName(bankName);
		return bank;
	}

}
