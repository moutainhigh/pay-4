package com.pay.poss.controller.fi.channel;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.inf.dto.Bank;
import com.pay.inf.service.BankService;
import com.pay.poss.client.ChannelClientService;
import com.pay.poss.controller.fi.commons.BankLabelClassEnum;
import com.pay.poss.controller.fi.commons.BankLabelUrlEnum;
import com.pay.poss.dto.PaymentChannelItemDto;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.CurrencyCodeEnum;

/**
 * 入款通道管理(新)
 * 
 * @author sandy
 *
 */
public class PaymentChannelItemController extends MultiActionController {

	private final Log logger = LogFactory
			.getLog(PaymentChannelItemController.class);
	private ChannelClientService channelClientService;
	private BankService bankService;
	private String indexView;
	private String resultView;
	private String addView;
	private String updateView;
	private String initSearch;

	/**
	 * 获取通道
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {

		List paymentCategorys = channelClientService.queryChannelCategory();
		List paymentChannels = channelClientService.queryPaymentChannel(null,
				null, null,null);
		return new ModelAndView(indexView).addObject("paymentCategorys",
				paymentCategorys).addObject("paymentChannels", paymentChannels);
	}

	/**
	 * 查询通道
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView query(HttpServletRequest request,
			HttpServletResponse response,
			PaymentChannelItemDto paymentChannelItem) {
		List result = channelClientService.queryChannelItem(paymentChannelItem);
		return new ModelAndView(resultView).addObject("resultList", result);
	}

	/**
	 * 验证通道别名唯一
	 * @param request
	 * @param response
	 * @param paymentChannelItem
	 * @return
	 */
	public void checkAlias(HttpServletRequest request,
			HttpServletResponse response,PaymentChannelItemDto paymentChannelItem){
		List result = channelClientService.queryChannelItem(paymentChannelItem);
		if(result !=null && result.size()>0){
			try {
				response.getWriter().print(1);
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * 初始化
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView initAdd(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView view = new ModelAndView(addView);

		List paymentCategorys = channelClientService.queryChannelCategory();
		List paymentChannels = channelClientService.queryPaymentChannel(null,
				null, null,null);
		List<Bank> banks = bankService.getDepositBanks();
		BankLabelUrlEnum[] bankLabelUrlEnum = BankLabelUrlEnum.values();
		CurrencyCodeEnum[] currencyCodeEnum = CurrencyCodeEnum.values();
		view.addObject("paymentCategorys", paymentCategorys);
		view.addObject("paymentChannels", paymentChannels);
		view.addObject("bankLabelClassEnum", bankLabelUrlEnum);
		view.addObject("currencyCodeEnum", currencyCodeEnum);
		view.addObject("banks", banks);
		return view;
	}

	/**
	 * 添加
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response,
			PaymentChannelItemDto paymentChannelItemDto) throws Exception {

		response.setCharacterEncoding("utf-8");

		String userId = SessionUserHolderUtil.getLoginId();
		paymentChannelItemDto.setOperator(userId);

		String result = channelClientService
				.addChannelItem(paymentChannelItemDto);

		if (null == result) {
			response.getWriter().print(1);
		} else {
			response.getWriter().print(result);
		}

		return null;
	}

	/**
	 * 初始化修改页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView initUpdate(HttpServletRequest request,
			HttpServletResponse response,
			PaymentChannelItemDto paymentChannelItem) {
		ModelAndView view = new ModelAndView(updateView);
		List list = channelClientService.queryChannelItem(paymentChannelItem);
		List paymentCategorys = channelClientService.queryChannelCategory();
		List paymentChannels = channelClientService.queryPaymentChannel(null,
				null, null,null);
		List<Bank> banks = bankService.getDepositBanks();
		BankLabelUrlEnum[] bankLabelUrlEnum = BankLabelUrlEnum.values();
		CurrencyCodeEnum[] currencyCodeEnum = CurrencyCodeEnum.values();
		view.addObject("paymentCategorys", paymentCategorys);
		view.addObject("paymentChannels", paymentChannels);
		view.addObject("bankLabelClassEnum", bankLabelUrlEnum);
		view.addObject("currencyCodeEnum", currencyCodeEnum);
		view.addObject("item", list.get(0));
		view.addObject("banks", banks);
		return view;
	}

	/**
	 * 修改渠道成本费率
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView update(HttpServletRequest request,
			HttpServletResponse response,
			PaymentChannelItemDto paymentChannelItemDto) throws IOException {

		response.setCharacterEncoding("utf-8");

		String userId = SessionUserHolderUtil.getLoginId();
		paymentChannelItemDto.setOperator(userId);

		String result = channelClientService
				.updateChannelItem(paymentChannelItemDto);

		if (null == result) {
			response.getWriter().print(1);
		} else {
			response.getWriter().print(result);
		}

		return null;
	}

	public ModelAndView remove(HttpServletRequest request,
			HttpServletResponse response) {
		// paymentChannelItemNewService.delete(pc);
		ModelAndView view = new ModelAndView(initSearch);
		return view;
	}

	public void setUpdateView(String updateView) {
		this.updateView = updateView;
	}

	public void setInitSearch(String initSearch) {
		this.initSearch = initSearch;
	}

	public void setChannelClientService(
			ChannelClientService channelClientService) {
		this.channelClientService = channelClientService;
	}

	public void setIndexView(String indexView) {
		this.indexView = indexView;
	}

	public void setResultView(String resultView) {
		this.resultView = resultView;
	}

	public void setAddView(String addView) {
		this.addView = addView;
	}

	public void setBankService(BankService bankService) {
		this.bankService = bankService;
	}
}
