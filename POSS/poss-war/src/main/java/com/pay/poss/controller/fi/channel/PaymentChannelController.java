package com.pay.poss.controller.fi.channel;

import java.io.IOException;
import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.poss.client.ChannelClientService;
import com.pay.poss.security.model.SessionUserHolder;
import com.pay.poss.security.util.SessionUserHolderUtil;


/**
 * 入款渠道管理(新)
 * 
 * @author sandy
 *
 */
public class PaymentChannelController extends MultiActionController {

	private final Log logger = LogFactory
			.getLog(PaymentChannelController.class);

	private String indexView;
	private String resultView;
	private String initAdd;
	private String initUpdate;
	private ChannelClientService channelClientService;

	/**
	 * 获取渠道
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {

		return new ModelAndView(indexView);
	}

	/**
	 * 查询渠道
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView query(HttpServletRequest request,
			HttpServletResponse response) {

		String channelCode = request.getParameter("code");
		String channelName = request.getParameter("name");
		String status = request.getParameter("status");

		List result = channelClientService.queryPaymentChannel(channelCode,
				channelName, status,null);
		return new ModelAndView(resultView).addObject("resultList", result);
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
		ModelAndView view = new ModelAndView(initAdd);
		// List<PaymentChannelDTO> categoryList = paymentChannelService
		// .queryPaymentChannelList(null);
		view.addObject("add", "1");
		// view.addObject("categoryList", categoryList);
		return view;
	}

	public ModelAndView remove(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView view = new ModelAndView(indexView);
		String id=request.getParameter("id");
		String delChannelConfig = channelClientService.delPaymentChannel(id);	
		if(delChannelConfig !=null){
			return view.addObject("info",delChannelConfig);
		}
		return view.addObject("info", "删除成功！！");
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
			HttpServletResponse response) throws Exception {
		ModelAndView view = new ModelAndView(indexView);
		String name=request.getParameter("name");
		String code=request.getParameter("code");
		String description=request.getParameter("description");
		String loginId = SessionUserHolderUtil.getLoginId();
		String addPaymentChannel = channelClientService.addPaymentChannel(name,code,description,loginId);
		if(addPaymentChannel !=null){
			return view.addObject("info",addPaymentChannel);
		}
		return view.addObject("info", "添加成功！！");
	}

	/**
	 * 初始化修改页面
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView initUpdate(HttpServletRequest request,
			HttpServletResponse response) {
		ModelAndView view = new ModelAndView(initUpdate);
		long id = Long.parseLong(request.getParameter("id"));
		List result = channelClientService.queryPaymentChannel(null,null,null,id);
		view.addObject("edit", "1");
		return view.addObject("channel", result.get(0));
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
			HttpServletResponse response) throws IOException {
		ModelAndView view=new ModelAndView(indexView);
		String name=request.getParameter("name");
		String code=request.getParameter("code");
		String description=request.getParameter("description");
		String status=request.getParameter("status");
		String id=request.getParameter("id");
		String loginId = SessionUserHolderUtil.getLoginId();
		String updatePaymentChannel = channelClientService.updatePaymentChannel(name,code,description,loginId,status,id);
		if(updatePaymentChannel !=null){
			return view.addObject("info",updatePaymentChannel);
		}
		return view.addObject("info", "修改成功！！");
	}

	public void setChannelClientService(
			ChannelClientService channelClientService) {
		this.channelClientService = channelClientService;
	}

	public void setInitAdd(String initAdd) {
		this.initAdd = initAdd;
	}

	public void setInitUpdate(String initUpdate) {
		this.initUpdate = initUpdate;
	}

	public void setIndexView(String indexView) {
		this.indexView = indexView;
	}

	public void setResultView(String resultView) {
		this.resultView = resultView;
	}

}
