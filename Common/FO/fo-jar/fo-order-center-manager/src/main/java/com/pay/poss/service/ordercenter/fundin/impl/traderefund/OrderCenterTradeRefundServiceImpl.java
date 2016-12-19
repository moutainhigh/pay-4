package com.pay.poss.service.ordercenter.fundin.impl.traderefund;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.acc.acctattrib.dto.AcctAttribDto;
import com.pay.acc.exception.MaAccountQueryUntxException;
import com.pay.acc.exception.MaMemberQueryException;
import com.pay.acc.service.member.dto.MemberBaseInfoBO;
import com.pay.inf.dao.Page;
import com.pay.pe.dto.AccountEntryDTO;
import com.pay.pe.service.PEService;
import com.pay.poss.dao.ordercenter.fundin.traderefund.OrderCenterTradeRefundDao;
import com.pay.poss.dto.fi.RefundOnlineDTO;
import com.pay.poss.service.gateway.RefundOnlineServiceFacade;
import com.pay.poss.service.ordercenter.common.AbstractOrderCenterCommonService;
import com.pay.poss.service.ordercenter.dto.detail.OrderDetailDTO;
import com.pay.poss.service.ordercenter.dto.entrie.OrderCenterEntrieDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterQueryDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterResultDTO;
import com.pay.util.StringUtil;

/**
 * 
 * <p>查询网关退款订单结果列表</p>
 * @author Andy.Zhao
 * @since 2010-11-10
 * @see
 */
public class OrderCenterTradeRefundServiceImpl extends AbstractOrderCenterCommonService{

	private final static Integer ACC_TYPE_RMB = 10;
	private static Log logger = LogFactory.getLog(OrderCenterTradeRefundServiceImpl.class);
	private OrderCenterTradeRefundDao orderCenterTradeRefundDao;
	private PEService peService;
	private RefundOnlineServiceFacade refundOnlineServiceFacade;
	
	public void setRefundOnlineServiceFacade(
			RefundOnlineServiceFacade refundOnlineServiceFacade) {
		this.refundOnlineServiceFacade = refundOnlineServiceFacade;
	}
	public void setPeService(PEService peService) {
		this.peService = peService;
	}
	public void setOrderCenterTradeRefundDao(
			final OrderCenterTradeRefundDao param) {
		this.orderCenterTradeRefundDao = param;
	}

	@Override
	public Map<String, Page<OrderCenterResultDTO>> queryResultList(
			Page<OrderCenterResultDTO> page, OrderCenterQueryDTO dto) {
		Map<String,Page<OrderCenterResultDTO>> map = new HashMap<String,Page<OrderCenterResultDTO>>();
		map.put("page", orderCenterTradeRefundDao.queryTradeRefundResultList(page, dto));
		return map;
	}	
		
	@Override
	public OrderDetailDTO queryOrderDetail(OrderCenterQueryDTO queryDto) {
		
		OrderDetailDTO orderDetailDTO = null;
		try {
			Long primaryKy = Long.parseLong(queryDto.getOrderKy());//系统流水号
			orderDetailDTO = orderCenterTradeRefundDao.queryOrderDetail(primaryKy);
			
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
			orderDetailDTO.setPayeeLevel((null!=payeeBO) ? payeeBO.getServiceLevelCode()+"":"");  //收款人用户等级
			orderDetailDTO.setPayeeName((null!=payeeBO) ? payeeBO.getName():"");  //收款人姓名
			orderDetailDTO.setPayerAcct((null!=payerAcct) ? payerAcct.getAcctCode():"");  //付款人账户			
			orderDetailDTO.setPayerAcctType(queryDto.getPayerAccType());  //付款人账户类型
			orderDetailDTO.setPayerId((null!=payerBO) ? payerBO.getLoginName(): "");  //付款人用户标识
//			orderDetailDTO.setPayerIp(String);  //付款人IP
			orderDetailDTO.setPayerKy(payerMemberCode.toString());  //付款人用户号
			orderDetailDTO.setPayerLevel((null!=payerBO) ?payerBO.getServiceLevelCode()+"":"");  //付款人用户等级
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
		map.put("relationList", orderCenterTradeRefundDao.queryRefundRelationList(queryDto));
		return map;
	}

	@Override
	public Map<String, Object> queryOrderEntrie(OrderCenterQueryDTO queryDto) {
		if(logger.isInfoEnabled()){
			logger.info("queryOrderEntrie() :"+queryDto);
		}
		
		List<OrderCenterEntrieDTO> entrieList = new ArrayList<OrderCenterEntrieDTO>();
		//此处需要调用 PE 接口取得分录列表		
		List<AccountEntryDTO> peEntryResultList = new ArrayList<AccountEntryDTO>();

		//网关退款交易类型的查询DTO的OrderKy是refundOrderInfoId
		if( !StringUtil.isNull(queryDto.getOrderKy())){

			List<RefundOnlineDTO> roList = refundOnlineServiceFacade.findByRefundOrderInfoId(queryDto.getOrderKy());

			//退款的分录号是refund_online_id.
			//String peKy=queryDto.getOrderKy();	
			String peKy ;
			for(RefundOnlineDTO ro : roList){
				peKy = "" + ro.getRefundOnlineId();
				peEntryResultList = peService.getAccountEntryByOrderId(peKy);
				
				for(AccountEntryDTO peDto:peEntryResultList){
					OrderCenterEntrieDTO entrie = new OrderCenterEntrieDTO();
					entrie.setAccoutCode(peDto.getAcctcode());
					entrie.setAmount(new BigDecimal(peDto.getValue()));
					entrie.setCertificateNo(peDto.getVouchercode().toString());
					entrie.setCreateDate(peDto.getTransactiondate());
					entrie.setDrMark(peDto.getCrdr().toString());
					entrie.setStatus(peDto.getStatus().toString());
					entrie.setEntrieNo(peDto.getEntrycode().toString());
					entrieList.add(entrie);
				}
			}
		}

		Map<String,Object> result = new HashMap<String,Object>();
		result.put("entrieList", entrieList);

		if(logger.isInfoEnabled()){
			logger.info("result :"+result);
		}
		return result;
	}
}
