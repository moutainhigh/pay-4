package com.pay.pe.manualbooking.dto;

import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.pe.manualbooking.model.VouchData;
import com.pay.pe.manualbooking.model.VouchDetailData;


/**
 * 手工记账申请数据转换帮助类
 */
public final class VouchDataDtoUtil {
     private static final Log LOG = LogFactory.getLog(VouchDataDtoUtil.class);
	
	/**
	 * @param dto
	 * @return
	 * 将手工记账数据传输对象转换成手工记账域对象
	 */
	public static VouchData convert2VouchData(final VouchDataDto dto) {
		LOG.info("Start");
		VouchData model = new VouchData();
		model.setAccountingDate(dto.getAccountingDate());
		model.setApplicationCode(dto.getApplicationCode());
		model.setAuditDate(dto.getAuditDate());
		model.setAuditor(dto.getAuditor());
		model.setCreateDate(dto.getCreateDate());
		model.setCreator(dto.getCreator());
		model.setRemark(dto.getRemark());
		model.setStatus(dto.getStatus());
		model.setVersion(dto.getVersion());
		model.setVouchCode(dto.getVouchCode());
		model.setVouchDataId(dto.getVouchDataId());
		
		List<VouchDataDetailDto> detailDtos = dto.getVouchDataDetails();
		VouchDetailData detail = null;
		for (VouchDataDetailDto detailDto : detailDtos) {
			detail = new VouchDetailData();
			detail.setAccountName(detailDto.getAccountName());
			detail.setAccountCode(detailDto.getAccountCode());
			detail.setAmount(Double.valueOf(detailDto.getAmount()));
			detail.setCrdr(detailDto.getCrdr());
			detail.setRemark(detailDto.getRemark());
			if (detailDto.getVouchDetailId() != null) {
				detail.setVouchDetailId(detailDto.getVouchDetailId());
			}
			
			model.addVouchDetail(detail);
		}
		
		LOG.info("End");
		return model;
	}
	
	/**
	 * @param vouchData
	 * @return
	 * 将手工记账域对象转换成手工记账数据传输对象
	 */
	public static VouchDataDto convert2VouchDataDto(final VouchData vouchData) {
		LOG.info("Start");
		VouchDataDto dto = new VouchDataDto();
		dto.setAccountingDate(vouchData.getAccountingDate());
		dto.setApplicationCode(vouchData.getApplicationCode());
		dto.setAuditDate(vouchData.getAuditDate());
		dto.setAuditor(vouchData.getAuditor());
		dto.setCreateDate(vouchData.getCreateDate());
		dto.setCreator(vouchData.getCreator());
		dto.setRemark(vouchData.getRemark());
		dto.setStatus(vouchData.getStatus());
		dto.setVersion(vouchData.getVersion());
		dto.setVouchCode(vouchData.getVouchCode());
		dto.setVouchDataId(vouchData.getVouchDataId());
		
		List<VouchDataDetailDto> detailDtos = new ArrayList<VouchDataDetailDto>();
		VouchDataDetailDto detailDto = null;
		for (VouchDetailData detail : vouchData.getVouchDetails()) {
			detailDto = new VouchDataDetailDto();
			detailDto.setAccountName(detail.getAccountName());
			detailDto.setAccountCode(detail.getAccountCode());
			detailDto.setAmount(detail.getAmount() + "");
			detailDto.setCrdr(detail.getCrdr());
			detailDto.setRemark(detail.getRemark());
			detailDto.setVouchDetailId(detail.getVouchDetailId());
			
			detailDtos.add(detailDto);
		}
		
		dto.setVouchDataDetails(detailDtos);
		LOG.info("End");
		return dto;
	}
	
	/**
	 * @param vouchDatas
	 * @return
	 * 将手工记账域对象列表转换成手工记账数据传输对象列表
	 */
	public static List<VouchDataDto> convert2VouchDataDtos(final List<VouchData> vouchDatas) {
		LOG.info("Start");
		List<VouchDataDto> dtos = new ArrayList<VouchDataDto>();
		VouchDataDto dto = null;
		for (VouchData vouchData : vouchDatas) {
			dto = convert2VouchDataDto(vouchData);
			dtos.add(dto);
		}
		LOG.info("End");
		return dtos;
	}
}
	
	

