package com.pay.pe.manualbooking.service;

import java.util.List;
import java.util.Map;

import com.pay.inf.dao.Page;
import com.pay.pe.manualbooking.dto.VouchDataDetailSearchDto;
import com.pay.pe.manualbooking.dto.VouchDataDto;
import com.pay.pe.manualbooking.exception.VouchDataInconsistException;
import com.pay.pe.manualbooking.exception.VouchDataPostingException;
import com.pay.pe.manualbooking.model.VouchData;
import com.pay.pe.manualbooking.model.VouchDetailData;
import com.pay.pe.manualbooking.util.VouchDataTemplate;
import com.pay.pe.manualbooking.util.VouchDetailSearchCriteria;
import com.pay.pe.manualbooking.util.VouchSearchCriteria;


/**
 * 
 * 手工记账申请服务
 */
public interface VouchService {
	/**
	 * @return VouchDataTemplate
	 * 取得申请数据模板
	 */
	VouchDataTemplate getVouchDataTemplate();
	
	
	/**
	 * @param VouchDto
	 * @return VouchDataDto
	 * 创建手工记账申请
	 */
	VouchDataDto createVouchData(VouchDataDto VouchDto);
	
	/**
	 * @param vouchDataDto
	 * @return
	 * @throws VouchDataInconsistException
	 * @throws VouchDataPostingException
	 * 审核通过记帐申请，同时记账。
	 */
	VouchDataDto approveVouchData(VouchDataDto vouchDataDto) throws VouchDataInconsistException, VouchDataPostingException;
	
	/**
	 * @param vouchDataDto
	 * @return
	 * @throws VouchDataInconsistException
	 * 审核不通过记帐申请。
	 */
	VouchDataDto rejectVouchData(VouchDataDto vouchDataDto) throws VouchDataInconsistException;
	
	/**
	 * @param vouchDataDto
	 * @return
	 * @throws VouchDataInconsistException
	 * 取消记账申请
	 */
	VouchDataDto cancelVouchData(VouchDataDto vouchDataDto) throws VouchDataInconsistException;
	

	/**
	 * @param vouchId
	 * @return
	 * 根据物理主键，取得手工记账申请数据
	 */
	VouchDataDto getVouchDataById(Long vouchId);
	
	/**
	 * @param vouchCriteria
	 * @param page
	 * @return
	 * 根据查询条件，页号，查询符合条件的手工记帐申请
	 */
//	List<VouchDataDto> findVouch(VouchSearchCriteria vouchCriteria, Page page);
	List<VouchDataDto> findVouch(VouchSearchCriteria vouchCriteria);
	/**
	 * @param vouchDetailCriteria
	 * @param page
	 * @return
	 * 根据查询条件，页号，查询符合条件的手工记帐明细
	 */
//	List<VouchDataDetailSearchDto> findVouchDetail(VouchDetailSearchCriteria vouchDetailCriteria, Page page);
	List<VouchDataDetailSearchDto> findVouchDetail(VouchDetailSearchCriteria vouchDetailCriteria);
	/**
	 * @param vouchDetailCriteria
	 * @param page
	 * @return
	 * 查询
	 */
	public Page<VouchDetailData> search(Page<VouchDetailData> page,Map<String,String> map) throws Exception;
	
	/**
	 * @param VouchDto
	 * @return VouchDataDto
	 * 创建手工记账申请
	 */
	public void  createVouchDetail(VouchDetailData vdd);
	
	public VouchData  insertVouchData(VouchData vdd);
}
