package com.pay.poss.controller.fi.channel;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.pay.acc.member.dto.EnterpriseBaseDto;
import com.pay.acc.member.service.EnterpriseBaseService;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.member.dto.MemberDto;
import com.pay.acc.member.service.MemberService;
import com.pay.poss.client.ChannelClientService;
import com.pay.poss.dto.PaymentChannelItemConfigDto;
import com.pay.poss.dto.PaymentChannelItemDto;
import com.pay.poss.security.util.SessionUserHolderUtil;

/**
 * 入款渠道管理(新)
 * 
 * @author sandy
 *
 */
public class MerchantConfigController extends MultiActionController {

	private ChannelClientService channelClientService;
	private EnterpriseBaseService enterpriseBaseService;
	private MemberService memberService;
	private final Log logger = LogFactory
			.getLog(MerchantConfigController.class);
	private String indexView;
	private String resultView;
	private String itemView;

	public void setEnterpriseBaseService(EnterpriseBaseService enterpriseBaseService) {
		this.enterpriseBaseService = enterpriseBaseService;
	}

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
				.queryMemberChannelItem(paymentChannelItem);
		return new ModelAndView(resultView).addObject("resultList", resultList);
	}
	
	public ModelAndView setPriority(HttpServletRequest request,
			HttpServletResponse response) throws Exception{
		PaymentChannelItemConfigDto paymentChannelItemConfigDto = new PaymentChannelItemConfigDto();
		paymentChannelItemConfigDto.setId(request.getParameter("paymentChannelItemConfigId"));
		paymentChannelItemConfigDto.setChannelPriority(request.getParameter("priority"));
		String result = channelClientService.updatePaymentChannelItemConfig(paymentChannelItemConfigDto);
		if (null == result) {
			response.getWriter().print(1);
		} else {
			logger.error("response str:" + result);
			response.getWriter().print(0);
		}
		return null;
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
		EnterpriseBaseDto enterpriseBaseDto = enterpriseBaseService.queryEnterpriseBaseByMemberCode(Long.parseLong(paymentChannelItem.getMemberCode()));
		String merchantCode = String.valueOf(enterpriseBaseDto.getMerchantCode());
		// 获取所有开启的通道
		List alllist = channelClientService
				.queryChannelItem(paymentChannelItem);
		// 获取会员已开通的通道
		List hasList = channelClientService
				.queryMemberChannelItem(paymentChannelItem);

		ModelAndView view = new ModelAndView(itemView);
		view.addObject("hasList", hasList);
		view.addObject("alllist", alllist);
		view.addObject("memberType", merchantCode.startsWith("500")?"500":"800");
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

		String memberCode = request.getParameter("memberCode");
		String paymentType = request.getParameter("paymentType");
		String ids[] = request.getParameterValues("funcno");
		String operator = SessionUserHolderUtil.getLoginId();
		String auto = request.getParameter("auto");
		String result = channelClientService.configMemberChannelItem(
				memberCode, paymentType, ids, operator,auto);
		if (null == result) {
			response.getWriter().print(1);
		} else {
			logger.error("response str:" + result);
			response.getWriter().print(0);
		}

		return null;
	}

	/**
	 * 判断会员是否存在
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView checkMember(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String memberCode = request.getParameter("memberCode");
		MemberDto member = memberService.queryMemberByMemberCode(Long
				.valueOf(memberCode.trim()));
		if (null != member) {
			response.getWriter().print(1);
		} else {
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
	public ModelAndView removeMerchantConfig(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		String memberCode = request.getParameter("memberCode");
		String paymentChannelItemId = request
				.getParameter("paymentChannelItemId");
		String paymentType = request.getParameter("paymentType");
		String result = channelClientService.delMemberChannelItem(memberCode,
				paymentChannelItemId, paymentType);
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

	public void setMemberService(MemberService memberService) {
		this.memberService = memberService;
	}

}
