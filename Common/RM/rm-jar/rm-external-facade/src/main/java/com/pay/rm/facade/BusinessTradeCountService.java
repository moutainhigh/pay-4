package com.pay.rm.facade;

import java.util.List;
import java.util.Map;

import com.pay.rm.facade.dto.BusinessTradeCountDTO;


/**
 * 商户交易记录
 * 次数统计
 * 额度统计
 * @author peiyu.yang
 *
 */
public interface BusinessTradeCountService {
	List<BusinessTradeCountDTO>  getBusinessTradeCountDTO(BusinessTradeCountDTO countDTO);
	BusinessTradeCountDTO  getBusinessTradeCountDTO(Map<String,Object> params);
	BusinessTradeCountDTO  createTradeCountDTO(BusinessTradeCountDTO countDTO);
	boolean updateBTC(Map<String,Object> params);
}
