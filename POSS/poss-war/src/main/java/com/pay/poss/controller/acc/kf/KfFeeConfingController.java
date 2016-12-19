package com.pay.poss.controller.acc.kf;

import java.math.BigDecimal;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;

import com.pay.acc.member.dto.EnterpriseBaseDto;
import com.pay.acc.member.service.EnterpriseBaseService;
import com.pay.acct.crossBorderPay.model.KfFeeConfig;
import com.pay.acct.crossBorderPay.service.KfFeeConfigService;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.poss.security.util.SessionUserHolderUtil;

/**
 * 跨境手续费设置
 * 
 * @Date 2016年8月24日13:59:24
 * @author delin
 *
 */
public class KfFeeConfingController extends MultiActionController {
	
	private String index;

	private String list;

	private KfFeeConfigService kfFeeConfigService;
	
	private EnterpriseBaseService enterpriseBaseService;

	public void setKfFeeConfigService(KfFeeConfigService kfFeeConfigService) {
		this.kfFeeConfigService = kfFeeConfigService;
	}

	public ModelAndView index(HttpServletRequest request,
			HttpServletResponse response) {
		return new ModelAndView(index);
	}

	/**
	 * 删除
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView delete(HttpServletRequest request,
			HttpServletResponse response) {
		String feeConfigNo = request.getParameter("feeConfigNo");
		kfFeeConfigService.deleteKfFeeConfig(feeConfigNo);
		return new ModelAndView(index);
	}

	/**
	 * 添加
	 * 
	 * @param request
	 * @param response
	 * @param feeConfig
	 * @return
	 */
	public ModelAndView add(HttpServletRequest request,
			HttpServletResponse response) {
		KfFeeConfig feeConfig = request(request);
		EnterpriseBaseDto queryEnterpriseBaseByMemberCode = enterpriseBaseService.queryEnterpriseBaseByMemberCode(feeConfig.getPartnerId());
		if (queryEnterpriseBaseByMemberCode == null) {
			return new ModelAndView(index).addObject("error", "该会员号不存在！！！");
		}
		
		Map<String, String> param = new HashMap<String, String>();
		Page<KfFeeConfig> page = PageUtils.getPage(request);
		param.put("partnerId", String.valueOf(feeConfig.getPartnerId()));
		List<KfFeeConfig> resultList = kfFeeConfigService.findKfFeeConfig(
				param, page);
		if (resultList != null && resultList.size() > 0) {
			return new ModelAndView(index).addObject("error", "该配置已存在！！！");
		}
		feeConfig.setCreateDate(new Date());
		feeConfig.setUpdateDate(feeConfig.getCreateDate());
		kfFeeConfigService.add(feeConfig);
		return new ModelAndView(index);
	}

	/**
	 * 修改
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView update(HttpServletRequest request,
			HttpServletResponse response) {
		String feeConfigNo=request.getParameter("feeConfigNo");
		KfFeeConfig feeConfig = request(request);
		feeConfig.setFeeConfigNo(Long.valueOf(feeConfigNo));
		feeConfig.setUpdateDate(new Date());
		kfFeeConfigService.update(feeConfig);
		return new ModelAndView(index);
	}

	/**
	 * 封装参数
	 * 
	 * @param request
	 * @return
	 */
	public KfFeeConfig request(HttpServletRequest request) {
		Long capValue = new BigDecimal(request.getParameter("capValue")).multiply(BigDecimal.valueOf(1000)).longValue();
		Long fixedFee =new BigDecimal(request.getParameter("fixedFee")).multiply(BigDecimal.valueOf(1000)).longValue();
		Long minimumValue = new BigDecimal(request.getParameter("minimumValue")).multiply(BigDecimal.valueOf(1000)).longValue();
		Long partnerId = Long.valueOf(request.getParameter("partnerId"));
		Long percentageFee = new BigDecimal(request.getParameter("percentageFee")).multiply(BigDecimal.valueOf(100)).longValue();
		Long smallBaselin = new BigDecimal(request.getParameter("smallBaselin")).multiply(BigDecimal.valueOf(1000)).longValue();
		Long smallServiceFee =new BigDecimal(request.getParameter("smallServiceFee")).multiply(BigDecimal.valueOf(1000)).longValue();
		String operator = SessionUserHolderUtil.getLoginId();
		return new KfFeeConfig(capValue, fixedFee, minimumValue, operator, partnerId, percentageFee, smallBaselin, smallServiceFee);
	}

	/**
	 * 查询
	 * 
	 * @param request
	 * @param response
	 * @return
	 */
	public ModelAndView list(HttpServletRequest request,
			HttpServletResponse response) {
		String partnerId = request.getParameter("partnerId");
		String beginCreateDate = request.getParameter("beginCreateDate");
		String endCreateDate = request.getParameter("endCreateDate");
		String beginUpdateDate = request.getParameter("beginUpdateDate");
		String endUpdateDate = request.getParameter("endUpdateDate");
		Page<KfFeeConfig> page = PageUtils.getPage(request);
		Map<String, String> paraMap = new HashMap<String, String>();
		paraMap.put("partnerId", partnerId);
		paraMap.put("beginCreateDate", beginCreateDate);
		paraMap.put("endCreateDate", endCreateDate);
		paraMap.put("beginUpdateDate", beginUpdateDate);
		paraMap.put("endUpdateDate", endUpdateDate);
		List<KfFeeConfig> resultList = kfFeeConfigService.findKfFeeConfig(
				paraMap, page);
		return new ModelAndView(list).addObject("resultList", resultList)
				.addObject("page", page);
	}

	public void setIndex(String index) {
		this.index = index;
	}

	public void setList(String list) {
		this.list = list;
	}

	public void setEnterpriseBaseService(EnterpriseBaseService enterpriseBaseService) {
		this.enterpriseBaseService = enterpriseBaseService;
	}

}
