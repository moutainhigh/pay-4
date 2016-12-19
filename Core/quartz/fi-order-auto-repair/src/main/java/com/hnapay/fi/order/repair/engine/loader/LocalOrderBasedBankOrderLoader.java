/**
 * LocalOrderBasedBankOrderLoader.java 
 * 海航集团新生支付
 */
package com.hnapay.fi.order.repair.engine.loader;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import com.hnapay.fi.dto.batchrepair.api.LocalOrderDTO;
import com.hnapay.fi.service.batchrepair.api.LocalOrderQueryServiceApi;

/**
 * 基于本地订单的银行订单加载器。
 * latest modified time : 2011-8-24 下午5:21:29
 * @author bigknife
 */
public class LocalOrderBasedBankOrderLoader extends SingleBankOrderLoader {
	private boolean supportManyLocalOrders = false;
	private int maxLocalOrdersPerRequest = 1;
	/**
	 * 本地订单转换到查询接口参数（统一使用Map接口）
	 * latest modified time : 2011-8-26 上午10:45:24
	 * @author bigknife
	 */
	public interface LocalOrderTransform{
		/**
		 * 将单笔本地订单转换成一个查询参数
		 * @param localOrder
		 * @return
		 */
		Map<String, String> toQueryParam(LocalOrderDTO localOrder);
		
		/**
		 * 将多笔本地订单转换成一个查询参数
		 * @param localOrders
		 * @return
		 */
		Map<String, String> toQueryParam(List<LocalOrderDTO> localOrders);
	}
	
	private LocalOrderQueryServiceApi localOrderQueryService;
	private long timePassed = 30 * 60 * 1000; // 默认查30分钟的历史数据
	private long timeIngored = 0; //忽略最近n毫秒内的订单，有可能是用户正在操作的
	private LocalOrderTransform paramTransform;
	
	
	/**
	 * @param supportManyLocalOrders the supportManyLocalOrders to set
	 */
	public void setSupportManyLocalOrders(boolean supportManyLocalOrders) {
		this.supportManyLocalOrders = supportManyLocalOrders;
	}

	/**
	 * @param maxLocalOrdersPerRequest the maxLocalOrdersPerRequest to set
	 */
	public void setMaxLocalOrdersPerRequest(int maxLocalOrdersPerRequest) {
		this.maxLocalOrdersPerRequest = maxLocalOrdersPerRequest;
	}

	/**
	 * @param timeIngored the timeIngored to set
	 */
	public void setTimeIngored(long timeIngored) {
		this.timeIngored = timeIngored;
	}

	/**
	 * @param paramTransform the paramTransform to set
	 */
	public void setParamTransform(LocalOrderTransform paramTransform) {
		this.paramTransform = paramTransform;
	}


	/**
	 * @param timePassed the timePassed to set 单位毫秒
	 */
	public void setTimePassed(long timePassed) {
		this.timePassed = timePassed;
	}

	/**
	 * @param localOrderQueryService the localOrderQueryService to set
	 */
	public void setLocalOrderQueryService(
			LocalOrderQueryServiceApi localOrderQueryService) {
		this.localOrderQueryService = localOrderQueryService;
	}

	/* (non-Javadoc)
	 * @see com.hnapay.fi.order.repair.engine.loader.SingleBankOrderLoader#loadManyParameterMaps()
	 */
	@Override
	protected final List<Map<String, String>> loadManyParameterMaps() {
		Date end = new Date();
		Date start = new Date(end.getTime() - timePassed);
		end = new Date(end.getTime() - timeIngored);
		List<LocalOrderDTO> localOrderList = localOrderQueryService.queryLocalOrderToRepair(getChannel(), start, end);
		if(localOrderList != null && !localOrderList.isEmpty()){
			List<Map<String, String>> result = new ArrayList<Map<String,String>>();
			//对于单笔批量，应该将多个订单转换成一个参数
			if(supportManyLocalOrders){
				//支持单笔批量
				int resultSize = (int) Math.ceil( ((double)localOrderList.size() / (double)maxLocalOrdersPerRequest));
				for(int j = 0; j < resultSize; j ++){
					List<LocalOrderDTO> orderList = new ArrayList<LocalOrderDTO>();
					for(int i = 0; i < maxLocalOrdersPerRequest; i ++){
						int idx = j * maxLocalOrdersPerRequest + i;
						if(idx > localOrderList.size()-1){
							break;
						}
						LocalOrderDTO lod = localOrderList.get(idx);
						orderList.add(lod);
					}
					
					Map<String, String> map = paramTransform.toQueryParam(orderList);
					result.add(map);
				}
				return result;
			}else{
				for(LocalOrderDTO order : localOrderList){
					Map<String, String> map = paramTransform.toQueryParam(order);
					result.add(map);
				}
				return result;
			}
		}
		return null;
	}
}
