package com.pay.pe.manualbooking.dao.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.pe.helper.CRDRType;
import com.pay.pe.manualbooking.dao.VouchBuilder;
import com.pay.pe.manualbooking.dao.VouchDataReader;
import com.pay.pe.manualbooking.dto.VouchDataDetailDto;
import com.pay.pe.manualbooking.dto.VouchDataDto;
import com.pay.pe.manualbooking.exception.VouchDataBuildException;


/**
 * 手工记帐申请Excel构建器
 */
public class XlsVouchBuilderImpl implements VouchBuilder {
	private static final Log LOG = LogFactory.getLog(XlsVouchBuilderImpl.class);
	/**
	 * 手工记账数据读取器
	 */
	private VouchDataReader vouchDataReader;
	
	/**
	 * 自动关闭资源
	 */
	private boolean autoClose;
	
	public XlsVouchBuilderImpl() {
		autoClose = true;
	}
	
	public XlsVouchBuilderImpl(VouchDataReader vouchDataReader) {
		this();
		this.vouchDataReader = vouchDataReader;
	}
	
	public XlsVouchBuilderImpl(VouchDataReader vouchDataReader, boolean autoClose) {
		this(vouchDataReader);
		this.autoClose = autoClose;
	}
	
	public void setVouchDataReader(VouchDataReader vouchDataReader) {
		this.vouchDataReader = vouchDataReader;
	} 
	
	public boolean isAutoClose() {
		return autoClose;
	}
	
	protected void doBeforeBuild() throws VouchDataBuildException {
		LOG.info("Start");
		try {
			this.vouchDataReader.read();
		} catch (IOException e) {
			LOG.debug("Do before build fails!", e);
			throw new VouchDataBuildException("doBeforeBuild fails", e);
		}
		LOG.info("End");
	}
	
	public VouchDataDto buildVouch() throws VouchDataBuildException {
		doBeforeBuild();
		VouchDataDto vouchDataDto = doBuild();
		doAfterBuild();
		return vouchDataDto;
	}
	
	protected void doAfterBuild() throws VouchDataBuildException {
		LOG.info("Start");
		if (autoClose) {
			try {
				this.vouchDataReader.close();
			} catch (IOException e) {
				LOG.debug("Do after build fails!", e);
				throw new VouchDataBuildException("doAfterBuild fails", e);
			}
		}
		LOG.info("End");
	}

	protected VouchDataDto doBuild() {
		LOG.info("Start");
		List<VouchDataDetailDto> vouchDetails = new ArrayList<VouchDataDetailDto>();
		
		String accountCode;
		String accountName;
		String remark;
		String crAmount;
		String drAmount;
		
		while (vouchDataReader.next()) {
			accountCode = vouchDataReader.getAccountCode();
			if (accountCode != null) {
				accountCode = accountCode.trim();
			}
			
			accountName = vouchDataReader.getAccountName();
			if (accountName != null) {
				accountName = accountName.trim();
			}
			
			remark = vouchDataReader.getVouchDetailRemark();
			if (remark != null) {
				remark = remark.trim();
			}
			
			crAmount = vouchDataReader.getCrAmount();
			if (crAmount != null) {
				crAmount = crAmount.trim();
			}
			
			drAmount = vouchDataReader.getDrAmount();
			if (drAmount != null) {
				drAmount = drAmount.trim();
			}
			
			if (StringUtils.isEmpty(accountCode) 
					&& StringUtils.isEmpty(accountName)
					&& StringUtils.isEmpty(remark)
					&& StringUtils.isEmpty(crAmount)
					&& StringUtils.isEmpty(drAmount)) {
				continue;
			}
			
			Integer crdr = null;
			VouchDataDetailDto vouchDetail = null;
			if (StringUtils.isNotEmpty(crAmount) && StringUtils.isEmpty(drAmount)) {
				crdr = CRDRType.DEBIT.getValue();
				vouchDetail = new VouchDataDetailDto(accountCode, crdr, crAmount, accountName, remark);
				crAmount = vouchDetail.getAmount();
			}
			if (StringUtils.isEmpty(crAmount) && StringUtils.isNotEmpty(drAmount)) {
				crdr = CRDRType.CREDIT.getValue();
				vouchDetail = new VouchDataDetailDto(accountCode, crdr, drAmount, accountName, remark);
				drAmount = vouchDetail.getAmount();
			}
			vouchDetails.add(vouchDetail);
		}
		
		VouchDataDto vouchData = new VouchDataDto();
		vouchData.setVouchDataDetails(vouchDetails);
		
		LOG.info("End");
		return vouchData;
	}
}
