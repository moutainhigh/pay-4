package com.pay.controller.fo.query;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.web.servlet.ModelAndView;

import com.pay.acc.member.dto.EnterpriseBaseDto;
import com.pay.acc.member.service.EnterpriseBaseService;
import com.pay.acc.service.account.AccountQueryService;
import com.pay.acc.service.account.constantenum.AcctTypeEnum;
import com.pay.acc.service.account.dto.BalancesDto;
import com.pay.app.base.api.annotation.OperatorPermission;
import com.pay.app.base.common.pagination.PageUtil;
import com.pay.app.base.session.LoginSession;
import com.pay.app.base.session.SessionHelper;
import com.pay.base.model.AcctInfo;
import com.pay.base.service.acct.AcctService;
import com.pay.fundout.dto.OrderCondition;
import com.pay.fundout.dto.OrderInfo;
import com.pay.fundout.dto.OrderInfoDetail;
import com.pay.inf.comm.PageUtils;
import com.pay.inf.dao.Page;
import com.pay.util.DateUtil;
import com.pay.util.StringUtil;

/**
 * @Title: QueryCompositionControllerImpl.java
 * @Package com.pay.gateway.controller.queryhistory.impl
 * @Description: 充提查询实现
 * @author Gavin_Song()
 * @date 2010-9-25 下午06:16:03
 * @version V1.0
 */
public class WithdrawOrderQueryController extends AbstractQueryController {

	private Integer dealType;
	private String detailView;
	private String indexView;
	private String excelView ;
	private EnterpriseBaseService enterpriseBaseService;
	private AcctService acctService;

	private AccountQueryService accountQueryService;
	public void setAccountQueryService(AccountQueryService accountQueryService) {
		this.accountQueryService = accountQueryService;
	}
	public void setEnterpriseBaseService(EnterpriseBaseService enterpriseBaseService) {
		this.enterpriseBaseService = enterpriseBaseService;
	}
	/**
	 * 默认转发地址
	 * 
	 * @param req
	 * @param resp
	 * @param view
	 * @return
	 */
	//20160423 Mack MPS3期 去掉注解   @OperatorPermission(operatPermission = "OPERATOR_FO_CHARGEWITHDRAW")
	public ModelAndView index(final HttpServletRequest request,
			final HttpServletResponse resp, final OrderCondition condition) {

		String startTime = condition.getStartTime();
		String endTime = condition.getEndTime();
		//交易号
		String dealId = condition.getDealId();
		//交易状态
		Integer dealType = condition.getDealType();
		
		String orderStatus = StringUtil.null2String(request.getParameter("orderStatus")) ;
		
		// OrderCondition condition = new OrderCondition();
		String memeberCode = SessionHelper.getMemeberCodeBySession();
		condition.setMemberCode(memeberCode);
		EnterpriseBaseDto code = enterpriseBaseService
				.queryEnterpriseBaseByMemberCode(Long.valueOf(memeberCode));
		condition.setDealType(dealType);
		// bind(request, condition, "queryCondition", null);
		String pager_offset = request.getParameter("pager_offset");
		Page<OrderInfo> page = PageUtils.getPage(request);
		page.setPageNo(pager_offset);
		Map<String, Object> resultMap = commonQueryService.queryOrderReturnMap(
				page, condition);
		List<BalancesDto> balancesDtos = accountQueryService
				.doQueryBalancesBasicNsTx(Long.valueOf(condition.getMemberCode()));
		for (BalancesDto balancesDto : balancesDtos) {
			   String acctName = balancesDto.getAcctName();
			   balancesDto.setAcctName(acctName.replaceAll("基本结算", ""));
		}
		
		PageUtil pageResult = new PageUtil(page.getPageNo(),
				page.getPageSize(), page.getTotalCount());// 分页处理
		List<OrderInfo> result = page.getResult();
		for (OrderInfo orderInfo : result) {
			String orderTime = orderInfo.getOrderTime();
			String[] split = orderTime.split(" ");
			orderInfo.setOrderTime(split[0]);
		}
		resultMap.put("pu", pageResult);
		resultMap.put("queryCondition", condition);
		resultMap.put("dealType", dealType);

		if (StringUtil.isEmpty(startTime)) {
			startTime = new SimpleDateFormat("yyyy-MM-dd 00:00")
					.format(DateUtil.skipDateTime(new Date(), -2));
		}
		if (StringUtil.isEmpty(endTime)) {
			endTime = new SimpleDateFormat("yyyy-MM-dd 00:00").format(DateUtil
					.skipDateTime(new Date(), 1));
		}
		resultMap.put("startTime", startTime);
		resultMap.put("accts", balancesDtos);
		resultMap.put("endTime", endTime);
		resultMap.put("dealId", dealId);
		resultMap.put("dealType", dealType==null?"":String.valueOf(dealType));
		resultMap.put("orderStatus", orderStatus);
		resultMap.put("memeberName", code.getZhName());
		resultMap.put("memeberCode", memeberCode);
		//2016-04-20 Mack add return payerAcctCode
		String payerAcctCode =  condition.getPayerAcctCode();
		resultMap.put("payerAcctCode", payerAcctCode);
		//Mack add end
		return new ModelAndView(indexView, resultMap);
	}

	/**
	 * excel下载
	 * @param request
	 * @param resp
	 * @param condition
	 * @return
	 * @throws UnsupportedEncodingException 
	 */
	public ModelAndView excelView(final HttpServletRequest request,
			final HttpServletResponse resp, final OrderCondition condition) throws UnsupportedEncodingException {
		String startTime = condition.getStartTime();
		String endTime = condition.getEndTime();
		// OrderCondition condition = new OrderCondition();
		condition.setMemberCode(SessionHelper.getMemeberCodeBySession());
		condition.setDealType(dealType);
		List<OrderInfo> queryOrdersByCondition = commonQueryService.queryOrdersByCondition(condition) ;
		for (OrderInfo orderInfo : queryOrdersByCondition) {
			if(orderInfo.getBalance()!=null){
				BigDecimal balance = new BigDecimal(orderInfo.getBalance()).divide(new BigDecimal(1000)).setScale(3,BigDecimal.ROUND_HALF_UP);
				orderInfo.setBalanceStr(balance.toString());
			}
			orderInfo.setCurrencyCode(AcctTypeEnum.getAcctCurrencyByCode(Integer.valueOf(orderInfo.getPayerAcctType())));//获取账户币种
		}
		Map<String, Object> resultMap = new HashMap<String, Object>() ;
		resultMap.put("listRsult", queryOrdersByCondition) ;
		resultMap.put("queryCondition", condition);
		resultMap.put("nowDate", new Date());

		if (StringUtil.isEmpty(startTime)) {
			startTime = new SimpleDateFormat("yyyy-MM-dd 00:00")
					.format(DateUtil.skipDateTime(new Date(), -3));
		}
		if (StringUtil.isEmpty(endTime)) {
			endTime = new SimpleDateFormat("yyyy-MM-dd 00:00").format(DateUtil
					.skipDateTime(new Date(), 1));
		}
		ModelAndView mv = new ModelAndView(excelView, resultMap);
		String payerAcctName = condition.getPayerAcctName();
		//ddl add payerAcctName 字段
		if(StringUtils.isNotEmpty(payerAcctName)){
			mv.addObject("payerAcctName", payerAcctName);
		}
		resultMap.put("startTime", startTime);
		resultMap.put("endTime", endTime);
		
		resp.setHeader("Expires", "0");
		resp.setHeader("Pragma" ,"public");
		resp.setHeader("Cache-Control","must-revalidate, post-check=0, pre-check=0");
		resp.setHeader("Cache-Control", "public");

		resp.setContentType("application/vnd.ms-excel;charset=utf-8");
		resp.setHeader("Content-Disposition", "attachment;filename="
				+ new String(("提现查询明细.xls").getBytes("UTF-8"),
						"ISO8859_1"));
		return mv ;
		//return new ModelAndView(excelView, resultMap);
	}
	/**
	 * 
	 * @param request
	 * @param resp
	 * @return
	 */
	public ModelAndView detail(final HttpServletRequest request,
			final HttpServletResponse resp) {
		LoginSession loginSession = SessionHelper.getLoginSession();
		String memberCode = loginSession.getMemberCode();
		String dealId = request.getParameter("dealId");
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("dealId", dealId);
		map.put("memberCode", memberCode);
		OrderInfoDetail orderDetail = commonQueryService
				.queryOrderDetailByOrderId(map);
		if (orderDetail != null) {
			String payeeBeankNo = orderDetail.getPayeeBankNo();
			orderDetail
					.setPayeeBankNo(StringUtils.isNotEmpty(payeeBeankNo) ? payeeBeankNo
							.substring(payeeBeankNo.length() - 4) : "");
		}
		//获取商户名下所有的交易账户
	    List<AcctInfo> acctList =null;
	    Map<String,List<AcctInfo>> acctMap = acctService.getAcctInfByMemberCode(Long.valueOf(memberCode));
	    if(acctMap!=null&&acctMap.containsKey("all")){
	    	 acctList = acctMap.get("all");
	    	 //model.put("acctList",acctList);
	    	 for(AcctInfo acctInfo : acctList){
	    		 String acctCode = acctInfo.getAcctCode() ;
	    		 if(StringUtils.isNotEmpty(acctCode)){
	    			 if(acctCode.equals(orderDetail.getPayerAcctCode())){
	    				 orderDetail.setPayerAcctName(acctInfo.getAcctName());
	    			 }
	    		 }
	 	     }
	    }
		return new ModelAndView(detailView).addObject("detail", orderDetail);
	}

	public void setIndexView(final String indexView) {
		this.indexView = indexView;
	}

	public void setDetailView(final String detailView) {
		this.detailView = detailView;
	}

	public void setDealType(final Integer dealType) {
		this.dealType = dealType;
	}

	public void setExcelView(final String excelView) {
		this.excelView = excelView;
	}

	public void setAcctService(final AcctService acctService) {
		this.acctService = acctService;
	}
	
}
