/**
 *  File: FundoutChannelController.java
 *  Description:
 *  Copyright 2010 -2010 pay Corporation. All rights reserved.
 *  2010-11-1     darv      Changes
 *  
 *
 */
package com.pay.fo.controller.channel;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.pay.fo.bankcorp.model.BankChannelConfig;
import com.pay.fundout.channel.dto.bank.FundoutBankDTO;
import com.pay.fundout.channel.dto.channel.FundoutChannelQueryDTO;
import com.pay.fundout.channel.model.bank.FundoutBank;
import com.pay.fundout.channel.model.channel.FundoutChannel;
import com.pay.fundout.channel.model.mode.FundoutMode;
import com.pay.fundout.channel.service.bank.FundoutBankService;
import com.pay.fundout.channel.service.channel.FundoutChannelService;
import com.pay.fundout.channel.service.mode.FoModeService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.inf.dto.Bank;
import com.pay.poss.security.util.SessionUserHolderUtil;

/**
 * @author darv
 * 
 */
public class FundoutChannelController extends AbstractChannelController {
	private FundoutChannelService fundoutChannelService;

	private FoModeService fundoutModeService;

	private FundoutBankService fundoutBankService;

	public void setFundoutModeService(FoModeService fundoutModeService) {
		this.fundoutModeService = fundoutModeService;
	}

	public void setFundoutBankService(FundoutBankService fundoutBankService) {
		this.fundoutBankService = fundoutBankService;
	}

	public void setFundoutChannelService(
			FundoutChannelService fundoutChannelService) {
		this.fundoutChannelService = fundoutChannelService;
	}

	/**
	 * 进入添加渠道页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		List<Bank> channels = bankInfoFacadeService.getWithdrawBanks();

		return new ModelAndView(URL("addChannel")).addObject("statusList",
				super.channelStatus).addObject("channels", channels);
	}

	/**
	 * 创建渠道
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView createChannel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String channelName = request.getParameter("channelName");
		String bankId = request.getParameter("bankId");
		String busiId = request.getParameter("busi");
		String modeId = request.getParameter("mode");
		String status = request.getParameter("status");
		String code = request.getParameter("code");
		String mark = request.getParameter("mark");

		String bankAcc = request.getParameter("bankAcc");
		String bankAccName = request.getParameter("bankAccName");
		String minRemindedBalance = request.getParameter("minRemindedBalance");
		String isSupportMultiple = request.getParameter("isSupportMultiple");
		String upperLimit = request.getParameter("upperLimit");
		String lowerLimit = request.getParameter("lowerLimit");
		String maxSupportItems = request.getParameter("maxSupportItems");
		String serverAddress = request.getParameter("serverAddress");
		String serverPort = request.getParameter("serverPort");
		String macKey = request.getParameter("macKey");

		List<Bank> channels = bankInfoFacadeService.getWithdrawBanks();
		FundoutChannel channel = new FundoutChannel();
		try {
			channel.setChannelName(channelName);
			// code不从页面传了，直接用银行号-出款方式表示
			channel.setCode(bankId + modeId);
			channel.setBankId(bankId);
			channel.setBusinessCode(busiId);
			channel.setModeCode(modeId);
			channel.setStatus(Long.valueOf(status));
			channel.setMark(mark);
			channel.setOperator(SessionUserHolderUtil.getLoginId());

			Long channelId = 0l;
			// 银企直连
			if ("0".equals(modeId)) {
				// BankChannelConfig
				BankChannelConfig bankChannelConfig = new BankChannelConfig();
				FundoutBankDTO fundoutBankDTO = fundoutBankService
						.queryFundoutBank(bankId);
				bankChannelConfig.setBankName(fundoutBankDTO.getBankName());
				bankChannelConfig.setBankAcc(bankAcc);
				bankChannelConfig.setBankAccName(bankAccName);
				if (StringUtils.isNotBlank(minRemindedBalance)) {
					bankChannelConfig.setMinRemindedBalance(Long
							.valueOf(minRemindedBalance));
				}
				bankChannelConfig.setIsSupportMultiple(Integer
						.valueOf(isSupportMultiple));
				if (StringUtils.isNotBlank(upperLimit)) {
					bankChannelConfig.setUpperLimit(Long.valueOf(upperLimit));
				}
				if (StringUtils.isNotBlank(lowerLimit)) {
					bankChannelConfig.setLowerLimit(Long.valueOf(lowerLimit));
				}
				if (StringUtils.isNotBlank(maxSupportItems)) {
					bankChannelConfig.setMaxSupportItems(Integer
							.valueOf(maxSupportItems));
				}
				bankChannelConfig.setServerAddress(serverAddress);
				if (StringUtils.isNotBlank(serverPort)) {
					bankChannelConfig
							.setServerPort(Integer.valueOf(serverPort));
				}
				bankChannelConfig.setMacKey(macKey);
				channelId = this.fundoutChannelService.createFundoutChannel(
						channel, bankChannelConfig);
			} else {
				channelId = this.fundoutChannelService
						.createFundoutChannel(channel);
			}
			return new ModelAndView(URL("addChannel"))
					.addObject(
							"info",
							"添加成功 对应的渠道编号是:【" + channelId + "="
									+ channel.getChannelName() + "】")
					.addObject("statusList", super.channelStatus)
					.addObject("channels", channels);
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView(URL("addChannel"))
					.addObject("info",
							"添加失败 对应的渠道编号是:【" + channel.getChannelName() + "】")
					.addObject("channels", channels)
					.addObject("statusList", super.channelStatus);
		}
	}

	/**
	 * 查询渠道列表
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView query(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		List<FundoutChannelQueryDTO> channels = fundoutChannelService
				.getFundoutChannelList();
		return new ModelAndView(URL("query")).addObject("statusList",
				super.channelStatus).addObject("channels", channels);
	}

	@SuppressWarnings("unchecked")
	public ModelAndView queryChannelList(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		Map params = new HashMap();
		String code = request.getParameter("code");
		String modeCode = request.getParameter("modeCode");
		String channelName = request.getParameter("channelName");
		String bankId = request.getParameter("bankId");
		String busiId = request.getParameter("businessId");
		String status = request.getParameter("status");
		if (code != null && code.trim().length() > 0) {
			params.put("code", code);
		}
		if (channelName != null && channelName.trim().length() > 0) {
			params.put("channelName", channelName);
		}
		if (bankId != null && bankId.length() > 0) {
			params.put("bankId", bankId);
		}
		if (modeCode != null && modeCode.length() > 0) {
			params.put("modeCode", modeCode);
		}
		if (busiId != null && busiId.length() > 0) {
			params.put("businessCode", busiId);
		}
		params.put("status", status);
		Page<FundoutChannelQueryDTO> page = PageUtils.getPage(request);
		page = this.fundoutChannelService.getFundoutChannelList(page, params);

		Map<String, String> bank = fundoutChannelService
				.getBankMap(fundoutChannelService
						.getFoBankList(new FundoutBank()));
		// Map<String, String> business =
		// fundoutChannelService.getBusinessMap(fundoutChannelService
		// .getFoBusinessList(new FundoutBusiness()));
		Map<String, String> mode = fundoutChannelService
				.getModeMap(fundoutChannelService
						.getFoModeList(new FundoutMode()));

		for (int i = 0; i < page.getResult().size(); i++) {
			page.getResult()
					.get(i)
					.setBankName(
							bank.get(page.getResult().get(i).getBankId()
									.toString()));
			// page.getResult().get(i).setBusinessName(business.get(page.getResult().get(i).getBusinessCode().toString()));
			page.getResult()
					.get(i)
					.setModeName(
							mode.get(page.getResult().get(i).getModeCode()
									.toString()));
		}
		return new ModelAndView(URL("queryChannelList"))
				.addObject("page", page);
	}

	/**
	 * 去修改页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView updatePage(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String channelId = request.getParameter("channelId");
		Map params = new HashMap();
		params.put("channelId", channelId);
		Page<FundoutChannelQueryDTO> page = PageUtils.getPage(request);
		page = this.fundoutChannelService.getFundoutChannelList(page, params);
		FundoutChannelQueryDTO channel = page.getResult().get(0);
		channel.setBankChannelConfig(fundoutChannelService
				.findBankChannelConfigByChannelCode(channel.getCode()));

		FundoutBankDTO fundoutBank = fundoutBankService
				.queryFundoutBank(channel.getBankId());
		channel.setBankName(null == fundoutBank ? "" : fundoutBank
				.getBankName());
		// channel.setBusinessName(fundoutBusinessService.queryFoBusinessInfoByCode(channel.getBusinessCode()).getName());
		channel.setModeName(fundoutModeService.queryFoModeInfoByCode(
				channel.getModeCode()).getName());

		return new ModelAndView(URL("updatePage"))
				.addObject("channel", channel).addObject("statusList",
						super.channelStatus);
	}

	/**
	 * 更新渠道
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView updateChannel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String channelId = request.getParameter("channelId");
		String status = request.getParameter("status");
		String mark = request.getParameter("mark");

		String bankAcc = request.getParameter("bankAcc");
		String bankAccName = request.getParameter("bankAccName");
		String minRemindedBalance = request.getParameter("minRemindedBalance");
		String isSupportMultiple = request.getParameter("isSupportMultiple");
		String upperLimit = request.getParameter("upperLimit");
		String lowerLimit = request.getParameter("lowerLimit");
		String maxSupportItems = request.getParameter("maxSupportItems");
		String serverAddress = request.getParameter("serverAddress");
		String serverPort = request.getParameter("serverPort");
		String macKey = request.getParameter("macKey");

		try {
			FundoutChannel channel = this.fundoutChannelService
					.getFundoutChannelById(Long.valueOf(channelId));
			channel.setStatus(Long.valueOf(status));
			channel.setMark(mark);
			if (channel.getModeCode().equals("1")) {
				this.fundoutChannelService.updateFundoutChannelById(channel);
			} else {
				BankChannelConfig bankChannelConfig = new BankChannelConfig();
				FundoutBankDTO fundoutBankDTO = fundoutBankService
						.queryFundoutBank(channel.getBankId());
				bankChannelConfig.setChannelCode(channel.getCode());
				bankChannelConfig.setBankName(fundoutBankDTO.getBankName());
				bankChannelConfig.setBankAcc(bankAcc);
				bankChannelConfig.setBankAccName(bankAccName);
				if (StringUtils.isNotBlank(minRemindedBalance)) {
					bankChannelConfig.setMinRemindedBalance(Long
							.valueOf(minRemindedBalance));
				}
				bankChannelConfig.setIsSupportMultiple(Integer
						.valueOf(isSupportMultiple));
				if (StringUtils.isNotBlank(upperLimit)) {
					bankChannelConfig.setUpperLimit(Long.valueOf(upperLimit));
				}
				if (StringUtils.isNotBlank(lowerLimit)) {
					bankChannelConfig.setLowerLimit(Long.valueOf(lowerLimit));
				}
				if (StringUtils.isNotBlank(maxSupportItems)) {
					bankChannelConfig.setMaxSupportItems(Integer
							.valueOf(maxSupportItems));
				}
				bankChannelConfig.setServerAddress(serverAddress);
				if (StringUtils.isNotBlank(serverPort)) {
					bankChannelConfig
							.setServerPort(Integer.valueOf(serverPort));
				}
				bankChannelConfig.setMacKey(macKey);
				this.fundoutChannelService.updateFundoutChannelById(channel,
						bankChannelConfig);
			}
			return new ModelAndView(URL("result")).addObject("result", "更新成功")
					.addObject("toUrl", "fundoutChannel.htm?method=query")
					.addObject("hrefStr", "返回查询页面");
		} catch (Exception e) {
			e.printStackTrace();
			return new ModelAndView(URL("result")).addObject("result", "更新失败")
					.addObject("toUrl", "fundoutChannel.htm?method=query")
					.addObject("hrefStr", "返回查询页面");
		}
	}

	/**
	 * 检查渠道名称是否重复 , 检查出款银行、出款方式的组合是否重复
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView checkChannel(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String channelName = request.getParameter("channelName");
		Map<String, String> params = new HashMap<String, String>();
		params.put("channelName", channelName);
		Page<FundoutChannelQueryDTO> page = PageUtils.getPage(request);
		page = this.fundoutChannelService.getFundoutChannelList(page, params);

		String bankId = request.getParameter("bankId");
		String modeId = request.getParameter("modeId");
		Map<String, String> params2 = new HashMap<String, String>();
		params2.put("bankId", bankId);
		params2.put("modeCode", modeId);
		Page<FundoutChannelQueryDTO> page2 = PageUtils.getPage(request);
		page2 = this.fundoutChannelService
				.getFundoutChannelList(page2, params2);

		String res = "yes";
		if (page.getResult().size() != 0) {
			res = "nameRepeat";
		} else if (page2.getResult().size() != 0) {
			res = "channelRepeat";
		}

		response.getWriter().print(res);
		response.getWriter().close();
		return null;
	}

	/**
	 * 根据银行号动态关联业务号
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	@SuppressWarnings("unchecked")
	public ModelAndView getbusiIdByBankId(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String bankId = request.getParameter("bankId");
		List busiList = fundoutChannelService.getbusiIdByBankId(bankId);
		StringBuffer buff = new StringBuffer();
		buff.append("<option value=\"\">--请选择--</option>");
		for (int i = 0; i < busiList.size(); i++) {
			FundoutChannelQueryDTO channel = (FundoutChannelQueryDTO) busiList
					.get(i);
			buff.append("<option value=\"");
			buff.append(channel.getBusinessCode());
			buff.append("\">");
			buff.append(channel.getBusinessName());
			buff.append("</option>");
		}
		response.setCharacterEncoding("utf-8");
		response.getWriter().print(buff.toString());
		response.getWriter().close();
		return null;
	}
}
