package com.pay.poss.controller.fi.channel;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.poss.client.ChannelClientService;
import com.pay.poss.dto.PaymentChannelItemDto;
import com.pay.poss.security.util.SessionUserHolderUtil;

/**
 * 默认通道管理
 * 
 * @author sandy
 *
 */
public class DefaultChannelItemController extends MultiActionController {

	private final Log logger = LogFactory
			.getLog(DefaultChannelItemController.class);

	private ChannelClientService channelClientService;
	private String indexView;
	private String resultView;
	private String itemView;

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

		List resultList = channelClientService
				.queryDefaultChannelItem(paymentChannelItem);
		return new ModelAndView(resultView).addObject("resultList", resultList);
	}

	/**
	 * 配置商户渠道
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView setItem(HttpServletRequest request,
			HttpServletResponse response,
			PaymentChannelItemDto paymentChannelItem) {

		// 获取所有开启的通道
		List alllist = channelClientService
				.queryChannelItem(paymentChannelItem);
		// 获取会员已开通的通道
		List hasList = channelClientService
				.queryDefaultChannelItem(paymentChannelItem);

		ModelAndView view = new ModelAndView(itemView);
		view.addObject("hasList", hasList);
		view.addObject("alllist", alllist);
		return view.addObject("paymentChannelItem", paymentChannelItem);
	}

	/**
	 * 配置商户渠道
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws IOException
	 */
	public ModelAndView setItemUpdate(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String memberType = request.getParameter("memberType");
		String paymentType = request.getParameter("paymentType");
		String ids[] = request.getParameterValues("funcno");
		String operator = SessionUserHolderUtil.getLoginId();
		String result = channelClientService.configDefaultChannelItem(
				memberType, paymentType, ids, operator);
		if (null == result) {
			response.getWriter().print(1);
		} else {
			logger.error("response str:" + result);
			response.getWriter().print(0);
		}

		return null;
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView removeDefaultConfig(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String paymentChannelItemId = request
				.getParameter("paymentChannelItemId");
		List<String> paymentChannelItemIds = new ArrayList<String>();
		paymentChannelItemIds.add(paymentChannelItemId);
		String paymentType = request.getParameter("paymentType");
		String memberType = request.getParameter("memberType");
		String result = channelClientService.delDefaultChannelItem(memberType,
				paymentChannelItemIds, paymentType);
		if (null == result) {
			response.getWriter().print(1);
		} else {
			response.getWriter().print(result);
		}
		return null;
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

	public void setItemView(String itemView) {
		this.itemView = itemView;
	}

}
