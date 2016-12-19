package com.pay.poss.service.ordercenter.fundin.impl.assignProfit;

import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.exception.MaAccountQueryUntxException;
import com.pay.acc.exception.MaMemberQueryException;
import com.pay.acc.service.member.dto.MemberBaseInfoBO;
import com.pay.inf.dao.Page;
import com.pay.poss.dao.ordercenter.fundin.assignProfit.OrderCenterAssignProfitDao;
import com.pay.poss.service.ordercenter.common.AbstractOrderCenterCommonService;
import com.pay.poss.service.ordercenter.dto.detail.OrderDetailDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterQueryDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterResultDTO;

/**
 * 
 * <p>查询网关分账结果列表</p>
 * @author Andy.Zhao
 * @since 2010-11-10
 * @see
 */
public class OrderCenterAssignProfitServiceImpl extends AbstractOrderCenterCommonService{

	private static Log logger = LogFactory.getLog(OrderCenterAssignProfitServiceImpl.class);
	private final static Integer ACC_TYPE_RMB = 10;
	private OrderCenterAssignProfitDao orderCenterAssignProfitDao;

	public void setOrderCenterAssignProfitDao(
			OrderCenterAssignProfitDao orderCenterAssignProfitDao) {
		this.orderCenterAssignProfitDao = orderCenterAssignProfitDao;
	}

	@Override
	public Map<String, Page<OrderCenterResultDTO>> queryResultList(
			Page<OrderCenterResultDTO> page, OrderCenterQueryDTO dto) {
		Map<String,Page<OrderCenterResultDTO>> map = new HashMap<String,Page<OrderCenterResultDTO>>();
		map.put("page", orderCenterAssignProfitDao.queryAssignProfitResultList(page, dto));
		return map;
	}	
		
	@Override
	public OrderDetailDTO queryOrderDetail(OrderCenterQueryDTO queryDto) {
		
		OrderDetailDTO orderDetailDTO = null;
		try {
			Long primaryKy = Long.parseLong(queryDto.getOrderKy());//系统流水号
			orderDetailDTO = orderCenterAssignProfitDao.queryOrderDetail(primaryKy);
			
			Long payerMemberCode = Long.parseLong(StringUtils.isEmpty(queryDto.getPayerMemberCode()) ? "0" : queryDto.getPayerMemberCode() );
			Long payeeMemberCode = Long.parseLong(StringUtils.isEmpty(queryDto.getPayeeMemberCode()) ? "0" : queryDto.getPayeeMemberCode());

			//现在只有人民币账户 10 ，之后如果有修改这里需要变动
			Integer payerAccType = ACC_TYPE_RMB;//Integer.parseInt(StringUtils.isEmpty(queryDto.getPayerAccType()) ? "10" : queryDto.getPayerAccType());
			Integer payeeAccType = ACC_TYPE_RMB;//Integer.parseInt(StringUtils.isEmpty(queryDto.getPayeeAccType()) ? "10" : queryDto.getPayeeAccType());
			
			MemberBaseInfoBO payerBO = memberQueryService.queryMemberBaseInfoByMemberCode(payerMemberCode);
			MemberBaseInfoBO payeeBO = memberQueryService.queryMemberBaseInfoByMemberCode(payeeMemberCode);
			AcctAttribDto payerAcct = accountQueryService.doQueryAcctAttribNsTx(payerMemberCode,payerAccType);
			AcctAttribDto payeeAcct = accountQueryService.doQueryAcctAttribNsTx(payeeMemberCode,payeeAccType);
			
//			orderDetailDTO.setBankAcct(String);  //银行卡号
//			orderDetailDTO.setBankBranch(String);  //开户行
//			orderDetailDTO.setBankName(String);  //银行名称
//			orderDetailDTO.setCity(String);  //城市
//			orderDetailDTO.setFee(BigDecimal);  //费用
//			orderDetailDTO.setGoodsName(String);  // 商品名称
//			orderDetailDTO.setLiquidateBatchKy(String);  //清算批次号
//			orderDetailDTO.setMemberCode(String);  //会员号
//			orderDetailDTO.setOrderAccount(BigDecimal);  //交易金额
//			orderDetailDTO.setOrderCreateTime(Date);  // 订单创建时间
//			orderDetailDTO.setOrderEndTime(Date);  //订单结束时间
//			orderDetailDTO.setOrderId(String);  //商家订单号
//			orderDetailDTO.setOrderStatus(String);  //交易状态
//			orderDetailDTO.setOrderType(String);  //订单类型
//			orderDetailDTO.setOrderWebsite(String);  //交易网址
			orderDetailDTO.setPayeeAcct( (null!=payeeAcct) ? payeeAcct.getAcctCode() : "");  //收款人账户号
			orderDetailDTO.setPayeeAcctType(queryDto.getPayeeAccType());  //收款人账户类型
			orderDetailDTO.setPayeeId((null!=payeeBO) ? payeeBO.getLoginName() : "");  //收款人用户标识
			orderDetailDTO.setPayeeKy(payeeMemberCode.toString());  //收款人用户号
			orderDetailDTO.setPayeeLevel((null!=payeeAcct) ? payeeAcct.getAcctLevel().toString():"");  //收款人用户等级
			orderDetailDTO.setPayeeName((null!=payeeBO) ? payeeBO.getName():"");  //收款人姓名
			orderDetailDTO.setPayerAcct((null!=payerAcct) ? payerAcct.getAcctCode():"");  //付款人账户			
			orderDetailDTO.setPayerAcctType(queryDto.getPayerAccType());  //付款人账户类型
			orderDetailDTO.setPayerId((null!=payerBO) ? payerBO.getLoginName(): "");  //付款人用户标识
//			orderDetailDTO.setPayerIp(String);  //付款人IP
			orderDetailDTO.setPayerKy(payerMemberCode.toString());  //付款人用户号
			orderDetailDTO.setPayerLevel((null!=payerAcct) ?payerAcct.getAcctLevel().toString():"");  //付款人用户等级
			orderDetailDTO.setPayerName((null!=payerBO) ? payerBO.getName(): "");  //付款人用户 姓名
//			orderDetailDTO.setPayment();  //支付方式
//			orderDetailDTO.setProvinces(String);  //省份
//			orderDetailDTO.setRemarks(String);  //留言
			
		} catch (MaMemberQueryException e) {
			logger.error(e.getMessage(), e);
		}catch (MaAccountQueryUntxException e) {
			logger.error(e.getMessage(), e);
		}
		return orderDetailDTO;
	}
	
	@Override
	public Map<String, Object> queryOrderRelation(OrderCenterQueryDTO queryDto) {
		Map<String,Object> map = new HashMap<String,Object>();
		map.put("relationList", orderCenterAssignProfitDao.queryAssignProfitRelationList(queryDto));
		return map;
	}

}
