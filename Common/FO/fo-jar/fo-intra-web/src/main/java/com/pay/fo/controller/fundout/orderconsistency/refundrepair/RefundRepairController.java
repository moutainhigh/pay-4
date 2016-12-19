
package com.pay.fo.controller.fundout.orderconsistency.refundrepair;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.lang.StringUtils;
import org.springframework.security.Authentication;
import org.springframework.security.context.SecurityContextHolder;
import org.springframework.web.servlet.ModelAndView;

import com.pay.fundout.withdraw.dto.WithdrawOrderAppDTO;
import com.pay.fundout.withdraw.dto.operatorlog.OperatorlogDTO;
import com.pay.fundout.withdraw.service.operatorlog.OperatorlogService;
import com.pay.fundout.withdraw.service.order.WithdrawOrderService;
import com.pay.fundout.withdraw.service.orderconsistency.BackFundMentOrderRepairService;
import com.pay.poss.base.controller.AbstractBaseController;
import com.pay.poss.security.util.SessionUserHolderUtil;
import com.pay.util.StringUtil;

/**
 * 
 * @author Sean_yi
 * @createtime 2010-12-30
 * @filename RefundRepairController.java
 * @version 1.0
 */
public class RefundRepairController extends AbstractBaseController {

	private BackFundMentOrderRepairService backFundMentOrderRepairService;
	
	private WithdrawOrderService withdrawOrderService;
	
	private OperatorlogService operatorlogService;

	public void setBackFundMentOrderRepairService(
			BackFundMentOrderRepairService backFundMentOrderRepairService) {
		this.backFundMentOrderRepairService = backFundMentOrderRepairService;
	}
	
	public void setWithdrawOrderService(WithdrawOrderService withdrawOrderService) {
		this.withdrawOrderService = withdrawOrderService;
	}
	
	public void setOperatorlogService(OperatorlogService operatorlogService) {
		this.operatorlogService = operatorlogService;
	}
	
	/**
	 * 根据条件查询112 但没有生成退款订单记录
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView index(HttpServletRequest request, HttpServletResponse response) throws Exception {
		return new ModelAndView(URL("refundRepairInit"));
	}
	
	public ModelAndView repairList(HttpServletRequest request, HttpServletResponse response) throws Exception {
		Map<String, Object> map = new HashMap<String, Object>();
		String sequenceId = StringUtil.null2String(request.getParameter("sequenceId"));
		String startDate = StringUtil.null2String(request.getParameter("startDate"));
		String endDate = StringUtil.null2String(request.getParameter("endDate"));
		if(StringUtils.isNotEmpty(sequenceId)){
			map.put("sequenceId", sequenceId);
		}
		if(!StringUtil.isEmpty(startDate)){
//			Date start = DateUtil.parse("yyyy-MM-dd", startDate);
			map.put("startDate",startDate);
		}
		if(!StringUtil.isEmpty(endDate)){
//			Date end = DateUtil.parse("yyyy-MM-dd", endDate);
			map.put("endDate", endDate);
		}
	 	List<WithdrawOrderAppDTO> list = backFundMentOrderRepairService.getNoBackFundMentOrderData(map);
		return new ModelAndView(URL("refundRepairList")).addObject("list", list);
	}

	/**
	 * 
	 * @param request
	 * @param response
	 * @return
	 * @throws Exception
	 */
	public ModelAndView repair(HttpServletRequest request, HttpServletResponse response) throws Exception {
		String sequenceId = StringUtil.null2String(request.getParameter("sequenceId"));
		Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
		String operator = SessionUserHolderUtil.getLoginId();
		
		WithdrawOrderAppDTO dto = null;
		if(!StringUtil.isEmpty(sequenceId)){
			dto =withdrawOrderService.getWithdrawOrder(Long.valueOf(sequenceId));
		}
		boolean  flage = backFundMentOrderRepairService.repairBackFundMentOrder(dto);
		String msg = "修复成功";
		if(!flage){
			msg ="修复失败";
		}
		this.saveLog(operator, sequenceId);
		return new ModelAndView(URL("refundRepairInit")).addObject("message", msg);
	}

	private void saveLog(String operator ,String busiId){
		OperatorlogDTO operDto = new OperatorlogDTO();
		operDto.setLogType(8);
		operDto.setLogTypeDesc("出款退款失败未生成退款订单修复");
		operDto.setBusiOrderId(busiId);
		operDto.setMark("原订单号  = " + busiId );
		operDto.setOperator(operator);
		operDto.setCreationDate(new Date());
		operatorlogService.saveOperatorLog(operDto);
	}
	
}
