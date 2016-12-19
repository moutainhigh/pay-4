/**
 * 
 */
package com.pay.fo.order.service.base.impl;

import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.fo.order.dto.base.FundoutOrderDTO;
import com.pay.fo.order.dto.base.OrderInfoDTO;
import com.pay.fo.order.service.base.FundoutOrderProcessService;
import com.pay.fo.order.service.base.FundoutOrderService;
import com.pay.fo.order.service.base.OrderInfoService;
import com.pay.fundout.channel.service.configbank.ConfigBankService;
import com.pay.inf.dto.CityDTO;
import com.pay.inf.dto.ProvinceDTO;
import com.pay.inf.exception.AppException;
import com.pay.poss.service.inf.input.ProvinceCityFacadeService;
import com.pay.util.StringUtil;

/**
 * @author NEW
 *
 */
public class FundoutOrderProcessServiceImpl implements FundoutOrderProcessService {
	
	protected Log log = LogFactory.getLog(getClass());
	
	/**
	 * 出款订单服务对象
	 */
	private FundoutOrderService fundoutOrderService;
	/**
	 * 订单概要信息服务对象
	 */
	private OrderInfoService orderInfoService;
	
	/**
	 * 出款行配置服务类
	 */
	private ConfigBankService configBankService;
	
	private ProvinceCityFacadeService provinceCityFacadeService;
	

	/* (non-Javadoc)
	 * @see com.pay.fo.order.service.pay2bank.Pay2BankOrderService#createOrderRnTx(com.pay.fo.order.dto.base.FundoutOrderDTO)
	 */
	@Override
	public Long createOrderRnTx(FundoutOrderDTO foOrder) throws AppException {
		Long orderId = null;
		try{
			
			Map<String,Object> map = new HashMap<String,Object>();
			//目的银行编号
			map.put("targetBankId", foOrder.getPayeeBankCode());
			//出款方式
			map.put("foMode", foOrder.getFundoutMode());
			//出款业务
			map.put("fobusiness", foOrder.getOrderType());
			
			String fundoutBank = configBankService.queryFundOutBank2Withdraw(map);
			if(StringUtil.isNull(fundoutBank)){
				String errorMsg = "暂不支持"+foOrder.getPayeeBankName()+"出款";
				throw new RuntimeException(errorMsg);
			}
			foOrder.setFundoutBankCode(fundoutBank);
			
			if(!StringUtil.isNull(foOrder.getPayeeBankProvince())&&StringUtil.isNull(foOrder.getPayeeBankProvinceName())){
				ProvinceDTO province = provinceCityFacadeService.getProvince(Integer.valueOf(foOrder.getPayeeBankProvince()));
				if(province!=null){
					foOrder.setPayeeBankProvinceName(province.getProvincename());
				}else{
					foOrder.setPayeeBankProvinceName(foOrder.getPayeeBankProvince());
				}
				
			}
			
			if(!StringUtil.isNull(foOrder.getPayeeBankCity())&&StringUtil.isNull(foOrder.getPayeeBankCityName())){
				CityDTO city = provinceCityFacadeService.getCity(Integer.valueOf(foOrder.getPayeeBankCity()));
				if(city!=null){
					foOrder.setPayeeBankCityName(city.getCityname());
				}else{
					foOrder.setPayeeBankCityName(foOrder.getPayeeBankCity());
				}
				
			}
			
			foOrder.setUpdateDate(foOrder.getCreateDate());
			orderId = fundoutOrderService.createOrder(foOrder);
			if(null==orderId){
				throw new RuntimeException("未获取到出款订单号");
			}
			OrderInfoDTO order = new OrderInfoDTO();
		    order.setOrderId(orderId);
		    order.setOrderType(foOrder.getOrderType());
		    order.setOrderStatus(foOrder.getOrderStatus());
		    order.setTradeType(foOrder.getTradeType());
		    order.setTradeAlias(foOrder.getTradeAlias());
		  
		    order.setPayerLoginName(foOrder.getPayerLoginName());
		    order.setPayerMemberCode(foOrder.getPayerMemberCode());
		    order.setPayerName(foOrder.getPayerName());

		    order.setPayeeName(foOrder.getPayeeName());
		  
		    order.setOrderAmount(foOrder.getOrderAmount());
		    order.setIsPayerPayFee(foOrder.getIsPayerPayFee());
		    order.setFee(foOrder.getFee());
		    order.setRealoutAmount(foOrder.getRealoutAmount());
		    order.setRealpayAmount(foOrder.getRealpayAmount());
		  
		    order.setCreateDate(foOrder.getCreateDate());
		    order.setUpdateDate(foOrder.getUpdateDate());
		    order.setPriority(foOrder.getPriority());
		    order.setPaymentReason(foOrder.getPaymentReason());
		   
		  
		    orderInfoService.createOrder(order);
		}catch(Throwable e){
			log.error(e.getMessage(),e);
			throw new AppException("存储出款订单、概要订单信息异常");
		}
		return orderId;
	}
	
	
	


	/* (non-Javadoc)
	 * @see com.pay.fo.order.service.pay2bank.Pay2BankOrderService#updateOrderStatusRdTx(com.pay.fo.order.dto.base.FundoutOrderDTO, int)
	 */
	@Override
	public boolean updateOrderStatusRdTx(FundoutOrderDTO order, int oldStatus) throws AppException {
		try{
			FundoutOrderDTO upOrder = new FundoutOrderDTO();
		    upOrder.setOrderId(order.getOrderId());
		    upOrder.setOrderStatus(order.getOrderStatus());
		    upOrder.setFailedReason(order.getFailedReason());
		    upOrder.setUpdateDate(order.getUpdateDate());
		    
		    
		    if(fundoutOrderService.updateOrderStatus(upOrder, oldStatus)){
		    	OrderInfoDTO upOrderInfo = new OrderInfoDTO();
		    	upOrderInfo.setOrderId(order.getOrderId());
		    	upOrderInfo.setOrderStatus(order.getOrderStatus());
		    	upOrderInfo.setFailedReason(order.getFailedReason());
		    	upOrderInfo.setUpdateDate(order.getUpdateDate());
		    	if(!orderInfoService.updateOrderStatus(upOrderInfo, oldStatus)){
		    		throw new RuntimeException("更新订单概要信息状态异常");
		    	}
		    	return true;
		    }else{
	    		throw new RuntimeException("更新出款订单状态异常");
		    }
		}catch(Throwable e){
			log.error(e.getMessage(),e);
			throw new AppException("更新出款订单、概要订单信息异常");
		}
	}

	/* (non-Javadoc)
	 * @see com.pay.fo.order.service.pay2bank.Pay2BankOrderService#getOrder(java.lang.Long)
	 */
	@Override
	public FundoutOrderDTO getOrder(Long orderId) {
		return (FundoutOrderDTO) fundoutOrderService.getOrder(orderId);
	}

	/**
	 * @param fundoutOrderService the fundoutOrderService to set
	 */
	public void setFundoutOrderService(FundoutOrderService fundoutOrderService) {
		this.fundoutOrderService = fundoutOrderService;
	}

	/**
	 * @param orderInfoService the orderInfoService to set
	 */
	public void setOrderInfoService(OrderInfoService orderInfoService) {
		this.orderInfoService = orderInfoService;
	}





	/**
	 * @param configBankService the configBankService to set
	 */
	public void setConfigBankService(ConfigBankService configBankService) {
		this.configBankService = configBankService;
	}





	/**
	 * @param provinceCityFacadeService the provinceCityFacadeService to set
	 */
	public void setProvinceCityFacadeService(
			ProvinceCityFacadeService provinceCityFacadeService) {
		this.provinceCityFacadeService = provinceCityFacadeService;
	}
	
	

}
