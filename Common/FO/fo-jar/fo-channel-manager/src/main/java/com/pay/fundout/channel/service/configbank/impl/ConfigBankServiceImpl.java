 /** @Description 
 * @project 	fo-channel-manager
 * @file 		ConfigBankServiceImpl.java 
 * Copyright (c) 2006-2010 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-11-2		Henry.Zeng			Create 
*/
package com.pay.fundout.channel.service.configbank.impl;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.BeanUtils;

import com.pay.fundout.channel.dao.configbank.ConfigBankDAO;
import com.pay.fundout.channel.dto.configbank.FundoutConfigBankDTO;
import com.pay.fundout.channel.model.channel.ChannelObject;
import com.pay.fundout.channel.model.channel.ChannelRequest;
import com.pay.fundout.channel.model.configbank.FundoutConfigBank;
import com.pay.fundout.channel.sendmq.SendChannelStatusToAppFacadeService;
import com.pay.fundout.channel.service.channel.ChannelService;
import com.pay.fundout.channel.service.configbank.ConfigBankService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.service.inf.input.BankInfoFacadeService;

/**
 * <p></p>
 * @author Henry.Zeng
 * @since 2010-11-2
 * @see 
 */
public class ConfigBankServiceImpl implements ConfigBankService {

	private SendChannelStatusToAppFacadeService sendChannelStatusToAppFacadeService;
	protected transient Log log = LogFactory.getLog(getClass());
	
	private transient ConfigBankDAO configBankDAO;
	
	protected BankInfoFacadeService bankInfoFacadeService;
	
	private ChannelService channelService;
	
	public void setChannelService(ChannelService channelService) {
		this.channelService = channelService;
	}

	public void setBankInfoFacadeService(final BankInfoFacadeService param) {
		this.bankInfoFacadeService = param;
	}
	
	public void setConfigBankDAO(final ConfigBankDAO configBankDAO) {
		this.configBankDAO = configBankDAO;
	}
	public void setSendChannelStatusToAppFacadeService(
			SendChannelStatusToAppFacadeService sendChannelStatusToAppFacadeService) {
		this.sendChannelStatusToAppFacadeService = sendChannelStatusToAppFacadeService;
	}
	@Override
	public Map<String, Object> addConfigBank(FundoutConfigBankDTO dto) {
		Map<String, Object> map = new HashMap<String, Object>();
		try{
			FundoutConfigBank model = new FundoutConfigBank();
			BeanUtils.copyProperties(dto, model);
			long configId = configBankDAO.addConfigBank(model);
			map.put("configId", configId);
			map.put("info", "添加配置目的银行与出款银行成功,配置名称:【"+configId+"="+dto.getConfigName()+"】");
		}catch (Exception e) {
			log.error(e.getMessage(),e);
			map.put("info", "添加配置目的银行与出款银行失败,配置名称:"+dto.getConfigName());
		}
		return map;
	}

	@Override
	public int modifyConfigBankRdTx(FundoutConfigBankDTO dto) {
		FundoutConfigBank model = new FundoutConfigBank();
		BeanUtils.copyProperties(dto, model);
		int result = configBankDAO.modifyConfigBank(model);
		if(1 == result){
			//String:bankCode(银行机构号)
			//String:flag（是否有效,1-有效，0-无效）
			HashMap<String,String> params = new HashMap<String,String>();
			params.put("bankCode", String.valueOf(model.getTargetBankId()));
			params.put("flag", String.valueOf(model.getStatus()));
			
			sendChannelStatusToAppFacadeService.sendMq2App(params);
		}
		return result;
	}

	@SuppressWarnings("unchecked")
	@Override
	public Map<String, Object> queryConfigBank(Page<FundoutConfigBankDTO> page,
			FundoutConfigBankDTO dto) {
		
		Map<String, Object> resultMap = new HashMap<String, Object>();
		
		FundoutConfigBank model = new FundoutConfigBank();
		
		BeanUtils.copyProperties(dto,model);
		
		Page<FundoutConfigBank> pageModel = new Page<FundoutConfigBank>();
		
		PageUtils.setServicePage(pageModel, page );
		
		pageModel = configBankDAO.queryConfigBank(pageModel, model);
		
		FundoutConfigBankDTO fundoutConfigBankDTO = new FundoutConfigBankDTO();
		
		page.setResult((List<FundoutConfigBankDTO>) PageUtils.changePageList(pageModel.getResult(), fundoutConfigBankDTO, null));
		
		PageUtils.setServicePage(page,pageModel);
		
		resultMap.put("page", page);
		
		return resultMap;
	}

	@Override
	public FundoutConfigBankDTO queryConfigBankById(Long configId) {
		FundoutConfigBankDTO fundoutConfigBankDTO = new FundoutConfigBankDTO();
		BeanUtils.copyProperties(configBankDAO.queryConfigBankById(configId), fundoutConfigBankDTO );
		 return fundoutConfigBankDTO;
	}

	@Override
	public String queryFundOutBank2Withdraw(Map<String, Object> params) {
		String targetBankId = String.valueOf(params.get("targetBankId"));
		String mode = String.valueOf(params.get("foMode"));
		String productCode = String.valueOf(params.get("fobusiness"));
		ChannelRequest request = new ChannelRequest();
		if(StringUtils.isNotBlank(productCode)){
			request.setProductCode(productCode);
		}
		if(StringUtils.isNotBlank(mode)){
			request.setChannelFlag(Integer.valueOf(mode));
		}
		if(StringUtils.isNotBlank(targetBankId)){
			request.setTargetBank(targetBankId);
		}
		ChannelObject object = channelService.getChannelObject(request);
		if(object != null){
			return object.getBankId() == null ? "" : object.getBankId().toString();
		}
		return null;
	}

	@Override
	public List<FundoutConfigBank> queryConfigBank(FundoutConfigBank pojo) {
		return configBankDAO.queryConfigBank(pojo);
	}

	@Override
	public List<Map<String, String>> getAllConfigBankList() {
		List<Map<String, String>> bankList = this.bankInfoFacadeService.getWithdrawBankList();
		List<String> list = configBankDAO.getAllConfigBankList();
		Map<String, String> bankMap = new HashMap<String, String>();
		for(Map<String, String> map : bankList){
			bankMap.put(map.get("value"), map.get("text"));
		}
		List<Map<String, String>> allBankList = new ArrayList<Map<String, String>>();
		for (String obj : list) {
			String bankName = bankMap.get(String.valueOf(obj));
			Map<String, String> map = new HashMap<String, String>();
			map.put("text", bankName);
			map.put("value", String.valueOf(obj));
			allBankList.add(map);
		}
		return allBankList;
	}
	@Override
	public boolean delConfigBankRdTx(Long configId) {
		
		return configBankDAO.delete(configId);
	}

}
