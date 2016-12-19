package com.pay.rm.service.service.facade;

import java.util.List;
import java.util.Map;

import com.pay.rm.service.dto.rmlimit.businesslimitcustom.RcBusinessLimitCustomDTO;
import com.pay.rm.service.model.rmlimit.businesslimit.RcBusinessLimit;
import com.pay.rm.service.model.rmlimit.businesslimitcustom.RcBusinessLimitCustom;
import com.pay.rm.service.model.rmlimit.innerlimit.RcInnerLimit;
import com.pay.rm.service.model.rmlimit.mcclimit.RcMccLimit;
import com.pay.rm.service.model.rmlimit.userlimit.RcUserLimit;

public interface RCLimitService {

	/**
	 * 
	 * @param map
	 * @return
	 */
	public List<RcBusinessLimit> searchBusinessLimit(Map<String, String> map);

	/**
	 * 
	 * @param map
	 * @return
	 */
	public List<RcUserLimit> searchUserLimit(Map<String, String> map);

	/**
	 * 
	 * @param map
	 * @return
	 */
	public List<RcInnerLimit> searchSysLimit(Map<String, String> map);

	/**
	 * 
	 * @param dto
	 */
	public RcMccLimit getMCCLimit(Long id);

	/**
	 * 
	 * @param dto
	 * @return
	 */
	public long createBusinessLimitCustom(RcBusinessLimitCustomDTO dto);

	public List<RcBusinessLimitCustom> searchBusinessLimitCustom(
			Map<String, String> map);

	/**
	 * 商户号
	 * 
	 * @param businessId
	 * @return
	 */
	public boolean deleteBusinessLimitCustom(long businessId);

}
