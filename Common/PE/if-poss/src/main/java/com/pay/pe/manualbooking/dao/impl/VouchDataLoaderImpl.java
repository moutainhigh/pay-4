package com.pay.pe.manualbooking.dao.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.pe.manualbooking.dao.VouchBuilder;
import com.pay.pe.manualbooking.dao.VouchDataReader;
import com.pay.pe.manualbooking.dao.VouchValidator;
import com.pay.pe.manualbooking.dto.VouchDataDto;
import com.pay.pe.manualbooking.exception.VouchDataBuildException;
import com.pay.pe.manualbooking.exception.VouchDataInvalidException;
import com.pay.pe.manualbooking.exception.VouchValidateMessage;


/**
 *
 */
public class VouchDataLoaderImpl extends
		AbstractVouchDataLoader {
	
	private static final Log LOG = LogFactory.getLog(VouchDataLoaderImpl.class);
	/**
	 * 验证器
	 */
	private List<VouchValidator> validators = new ArrayList<VouchValidator>();
	
	/**
	 * 构建器
	 */
	private VouchBuilder builder;
	
	/**
	 * 手工记账申请
	 */
	private VouchDataDto vouchDataDto;
	
	public VouchDataLoaderImpl(List<VouchValidator> validators, VouchBuilder builder) {
		this.validators = validators;
		this.builder = builder;
	}
	
	@Override
	protected VouchDataDto loadVouchData() throws VouchDataBuildException {
		VouchDataDto vouchDataDto = builder.buildVouch();
		return vouchDataDto;
	}

	@Override
	protected VouchDataDto retriveVouchData() {
		return vouchDataDto;
	}

	@Override
	protected void validateVouchData(final VouchDataDto vouchDataDto) throws VouchDataInvalidException {
		LOG.info("Start");
		List<VouchValidateMessage> totalMessages = new ArrayList<VouchValidateMessage>();
		
		for (VouchValidator validator : validators) {
			List<VouchValidateMessage> messages = validator.validate(vouchDataDto);
			if (messages != null && !messages.isEmpty()) {
				totalMessages.addAll(messages);
			}
		}
		
		if (!totalMessages.isEmpty()) {
			LOG.debug("Vouch data is not correct!");
			throw new VouchDataInvalidException(totalMessages);
		}
		
		this.vouchDataDto = vouchDataDto;
		LOG.info("End");
	}
	
	public static VouchDataLoaderImpl getInstance(InputStream inputStream, List<VouchValidator> validators) {
		LOG.info("Start");
		
		VouchDataReader vouchDataReader = new VouchDataPoiReaderImpl(inputStream);
		
		VouchBuilder builder = new XlsVouchBuilderImpl(vouchDataReader);
		
		LOG.info("End");
		return new VouchDataLoaderImpl(validators, builder);
	}

}
