package com.pay.poss.memberrelation.service.impl;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;

import com.pay.poss.memberrelation.dto.RelationDataDto;
import com.pay.poss.memberrelation.exception.RelationDataBuildException;
import com.pay.poss.memberrelation.exception.RelationDataInvalidException;
import com.pay.poss.memberrelation.exception.VouchRelationValidateMessage;
import com.pay.poss.memberrelation.service.RelationBuilder;
import com.pay.poss.memberrelation.service.RelationDataReader;
import com.pay.poss.memberrelation.service.RelationValidator;

/**
 *
 */
public class RelationDataLoaderImpl extends AbstractRelationDataLoader {

	private static final Log LOG = LogFactory
			.getLog(RelationDataLoaderImpl.class);
	/**
	 * 验证器
	 */
	private List<RelationValidator> validators = new ArrayList<RelationValidator>();

	/**
	 * 构建器
	 */
	private RelationBuilder builder;

	// /**
	// * 手工记账申请
	// */
	private List<RelationDataDto> RelationDataDto;

	public RelationDataLoaderImpl(List<RelationValidator> validators,
			RelationBuilder builder) {
		this.validators = validators;
		this.builder = builder;
	}

	@Override
	protected List<RelationDataDto> loadRelationData()
			throws RelationDataBuildException {
		List<RelationDataDto> RelationDataDto = builder.buildRelationList();
		return RelationDataDto;
	}

	@Override
	protected List<RelationDataDto> retriveRelationData() {
		return RelationDataDto;
	}

	@Override
	protected void validateRelationData(
			final List<RelationDataDto> RelationDataDto)
			throws RelationDataInvalidException {
		LOG.info("Start");
		List<VouchRelationValidateMessage> totalMessages = new ArrayList<VouchRelationValidateMessage>();

		for (RelationValidator validator : validators) {
			List<VouchRelationValidateMessage> messages = validator
					.validate(RelationDataDto);
			if (messages != null && !messages.isEmpty()) {
				totalMessages.addAll(messages);
			}
		}
		if (!totalMessages.isEmpty()) {
			LOG.debug("Relation data is not correct!");
			throw new RelationDataInvalidException(totalMessages);
		}

		this.RelationDataDto = RelationDataDto;
		LOG.info("End");
	}

	public static RelationDataLoaderImpl getInstance(InputStream inputStream,
			List<RelationValidator> validators) {
		LOG.info("Start");

		RelationDataReader RelationDataReader = new RelationDataPoiReaderImpl(
				inputStream);

		RelationBuilder builder = new XlsRelationBuilderImpl(RelationDataReader);

		LOG.info("End");
		return new RelationDataLoaderImpl(validators, builder);
	}

}
