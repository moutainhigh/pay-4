package com.pay.poss.memberrelation.service.impl;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.poss.memberrelation.dto.RelationDataDto;
import com.pay.poss.memberrelation.exception.RelationDataBuildException;
import com.pay.poss.memberrelation.service.RelationBuilder;
import com.pay.poss.memberrelation.service.RelationDataReader;


/**
 * 关联名单Excel构建器
 */
public class XlsRelationBuilderImpl implements RelationBuilder {
	private static final Log LOG = LogFactory.getLog(XlsRelationBuilderImpl.class);
	/**
	 * 关联名单数据读取器
	 */
	private RelationDataReader vouchDataReader;
	
	/**
	 * 自动关闭资源
	 */
	private boolean autoClose;
	
	public XlsRelationBuilderImpl() {
		autoClose = true;
	}
	
	public XlsRelationBuilderImpl(RelationDataReader vouchDataReader) {
		this();
		this.vouchDataReader = vouchDataReader;
	}
	
	public XlsRelationBuilderImpl(RelationDataReader vouchDataReader, boolean autoClose) {
		this(vouchDataReader);
		this.autoClose = autoClose;
	}
	
	public void setVouchDataReader(RelationDataReader vouchDataReader) {
		this.vouchDataReader = vouchDataReader;
	} 
	
	public boolean isAutoClose() {
		return autoClose;
	}
	
	protected void doBeforeBuild() throws RelationDataBuildException {
		LOG.info("Start");
		try {
			this.vouchDataReader.read();
		} catch (IOException e) {
			LOG.debug("Do before build fails!", e);
			throw new RelationDataBuildException("doBeforeBuild fails", e);
		}
		LOG.info("End");
	}
	
	public List<RelationDataDto> buildRelationList() throws RelationDataBuildException {
		doBeforeBuild();
		List<RelationDataDto> relationDataDtoList = doBuild();
		doAfterBuild();
		return relationDataDtoList;
	}
	
	protected void doAfterBuild() throws RelationDataBuildException {
		LOG.info("Start");
		if (autoClose) {
			try {
				this.vouchDataReader.close();
			} catch (IOException e) {
				LOG.debug("Do after build fails!", e);
				throw new RelationDataBuildException("doAfterBuild fails", e);
			}
		}
		LOG.info("End");
	}

	protected List<RelationDataDto> doBuild() {
		LOG.info("Start");
		List<RelationDataDto> vouchDetails = new ArrayList<RelationDataDto>();
		
		String fatherMemberCode;
		String relationStoreNumbers;
		String relationStoreAddress;
		String relationStoreName;
		String relationMobileNo;
		String amountEmail;

		while (vouchDataReader.next()) {
			fatherMemberCode = vouchDataReader.getFatherMemberCode();
			if (fatherMemberCode != null) {
				fatherMemberCode = fatherMemberCode.trim();
			}
			
			relationStoreNumbers = vouchDataReader.getRelationStoreNumbers();
			if (relationStoreNumbers != null) {
				relationStoreNumbers = relationStoreNumbers.trim();
			}
			
			relationStoreAddress = vouchDataReader.getRelationStoreAddress();
			if (relationStoreAddress != null) {
				relationStoreAddress =relationStoreAddress.trim();
			}
			
			relationStoreName = vouchDataReader.getRelationStoreName();
			if (relationStoreName != null) {
				relationStoreName = relationStoreName.trim();
			}
			
			relationMobileNo = vouchDataReader.getRelationMobileNo();
			if (relationMobileNo != null) {
				relationMobileNo = relationMobileNo.trim();
			}
			
			amountEmail = vouchDataReader.getAmountEmail();
			if (amountEmail != null) {
				amountEmail = amountEmail.trim();
			}
			
			if (StringUtils.isEmpty(fatherMemberCode) 
					&& StringUtils.isEmpty(relationStoreNumbers)
					&& StringUtils.isEmpty(relationStoreAddress)
					&& StringUtils.isEmpty(relationStoreName)
					&& StringUtils.isEmpty(amountEmail)) {
				continue;
			}
			
			RelationDataDto dto=new RelationDataDto(fatherMemberCode, relationStoreNumbers, relationStoreAddress, relationStoreName, relationMobileNo, amountEmail);
			vouchDetails.add(dto);
		}
	
		
		LOG.info("End");
		return vouchDetails;
	}
}
