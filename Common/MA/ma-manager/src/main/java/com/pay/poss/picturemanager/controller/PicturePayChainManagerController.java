package com.pay.poss.picturemanager.controller;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.base.common.SessionUserUtils;
import com.pay.poss.picturemanager.dto.PayChainPictureDto;
import com.pay.poss.picturemanager.service.PictureManagerService;
import com.pay.poss.security.model.SessionUserHolder;
import com.pay.util.DateUtil;

/**
 * @Title: PayChainPictureManagerController.java
 * @Package com.pay.poss.picturemanager.controller
 * @Description: 支付链图片管理
 * @author cf
 * @date 2011-12-28 下午2:50:11
 * @version V1.0
 */
public class PicturePayChainManagerController extends MultiActionController {

	private String queryView;
	private String listView;
	private String queryDetail;

	private PictureManagerService pictureManagerService;

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) throws Exception {

		// 指定当前时间为最大值
		String maxDate = DateUtil.formatDateTime("yyyy-MM-dd HH:mm");
		String suffer = " 00:00";
		// 最大的结束时间，企业为maxDate + 1 天

		Map<String, Object> model = new HashMap<String, Object>();

		String startTime = DateUtil.skipDate(maxDate, -3) + suffer;
		String endTime = maxDate;

		model.put("startDate", startTime);
		model.put("endDate", endTime);

		// startDate
		// 此处处理日期显示

		return new ModelAndView(queryView, model);
	}

	@SuppressWarnings("unchecked")
	public ModelAndView queryList(HttpServletRequest request,
			HttpServletResponse response, PayChainPictureDto dto)
			throws Exception {
		Page page = PageUtils.getPage(request);
		List<PayChainPictureDto> listDto = pictureManagerService
				.queryPictureManagerByCondition(dto, page);
		page.setResult(listDto);
		ModelAndView view = new ModelAndView(listView);
		view.addObject("page", page);
		return view;
	}

	public ModelAndView updatePictureStatus(HttpServletRequest request,
			HttpServletResponse response, PayChainPictureDto dto)
			throws IOException {
		response.setContentType("text/plain;charset=UTF-8");
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = null;
		out = response.getWriter();
		boolean updateFlag = pictureManagerService.updatePictureStatus(dto);
		out.print(updateFlag ? "1" : "0");
		out.flush();
		out.close();
		return null;
	}

	public ModelAndView queryDetail(HttpServletRequest request,
			HttpServletResponse response) throws Exception {
		String pictureOwner = request.getParameter("pictureOwnerId");
		Integer pictureOwnerId = Integer.parseInt(pictureOwner);
		SessionUserHolder user = SessionUserUtils.getUserInfo(request);
		List<PayChainPictureDto> listDto = pictureManagerService
				.queryPictureManagerByPayChainNumber(pictureOwnerId);
		PayChainPictureDto dto = null;
		if (null != listDto && listDto.size() > 0) {
			dto = listDto.get(0);
		}
		if (dto.getPictureStatus() == 0) {
			dto.setUpdatedBy(user.getUsername());
		}
		//String value = ConfigReader.get("picturePath", "MA");

		// 2:操作完后回刷页面
		return new ModelAndView(queryDetail)
				.addObject("PictureDtoList", listDto)
				//.addObject("pictureDto", dto).addObject("picturePath", value)
		// .addObject("page", page)
		;
	}

	public String getListView() {
		return listView;
	}

	public void setListView(String listView) {
		this.listView = listView;
	}

	public String getQueryView() {
		return queryView;
	}

	public void setQueryView(String queryView) {
		this.queryView = queryView;
	}

	public void setQueryDetail(String queryDetail) {
		this.queryDetail = queryDetail;
	}

	public void setPictureManagerService(
			PictureManagerService pictureManagerService) {
		this.pictureManagerService = pictureManagerService;
	}

}
