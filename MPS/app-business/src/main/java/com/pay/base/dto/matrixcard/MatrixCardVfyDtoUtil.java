package com.pay.base.dto.matrixcard;

import org.springframework.beans.BeanUtils;

import com.pay.base.common.helper.matrixcard.MatrixCardToken;
import com.pay.base.common.helper.matrixcard.MatrixCardVfyRequest;
import com.pay.base.model.matrixcard.MatrixCardVfy;
import com.pay.inf.dto.MutableDto;

public class MatrixCardVfyDtoUtil implements MutableDto {

	public Class<MatrixCardVfyDto> getDtoClass() {
		return MatrixCardVfyDto.class;
	}

	public Class<MatrixCardVfy> getModelClass() {
		return MatrixCardVfy.class;
	}

	/**
	 * dto转model
	 * 
	 * @param dto
	 * @return
	 */
	public static final MatrixCardVfy createModel(MatrixCardVfyDto dto) {
		MatrixCardVfy model = new MatrixCardVfy();
		BeanUtils.copyProperties(dto, model);

		return model;
	}

	/**
	 * model转dto
	 * 
	 * @param model
	 * @return
	 */
	public static final MatrixCardVfyDto createDto(MatrixCardVfy model) {
		MatrixCardVfyDto dto = new MatrixCardVfyDto();
		BeanUtils.copyProperties(model, dto);
		return dto;
	}

	/**
	 * 
	 * @param mcVfyRequest
	 * @return
	 */
	public static final MatrixCardVfy createModel(MatrixCardVfyRequest mcVfyRequest) {
		MatrixCardVfy vfy = new MatrixCardVfy();
		vfy.setRequestId(mcVfyRequest.getRequestId());
		vfy.setRequestDate(mcVfyRequest.getRequestDate());
		vfy.setRequestIp(mcVfyRequest.getRequestIp());
		vfy.setRequestMemberCode(mcVfyRequest.getRequestMemberCode());
		vfy.setRequestType(mcVfyRequest.getRequestType());
		vfy.setRequestUid(mcVfyRequest.getRequestUid());
		vfy.setTransId(mcVfyRequest.getTransId());
		vfy.setVfyType(mcVfyRequest.getVfyType());
		return vfy;
	}

	/**
	 * 
	 * @param vfy
	 * @return
	 */
	public static final MatrixCardVfyRequest createVfyRequestDto(MatrixCardVfy vfy) {
		MatrixCardVfyRequest req = new MatrixCardVfyRequest();
		req.setRequestDate(vfy.getRequestDate());
		req.setRequestId(vfy.getRequestId());
		req.setRequestIp(vfy.getRequestIp());
		req.setRequestMemberCode(vfy.getRequestMemberCode());
		req.setRequestType(vfy.getRequestType());
		req.setRequestUid(vfy.getRequestUid());
		req.setVfyType(vfy.getVfyType());
		return req;
	}

	/**
	 * 
	 * @param model
	 * @return
	 */
	public static MatrixCardToken createMatrixCardTokenDto(MatrixCardVfy model) {
		MatrixCardToken token = new MatrixCardToken();
		token.setToken(model.getToken());
		if (model.getRequestId() != null) {
			token.setRequestId(model.getRequestId().toString());
		}
		token.setXy0(model.getXy0());
		token.setXy1(model.getXy1());
		token.setXy2(model.getXy2());
		token.setValue0(model.getValue0());
		token.setValue1(model.getValue1());
		token.setValue2(model.getValue2());
		token.setRequestDate(model.getRequestDate());

		return token;
	}

	/**
	 * 
	 * @param dto
	 * @return
	 */
	public static MatrixCardToken createMatrixCardTokenDto(MatrixCardVfyDto dto) {
		MatrixCardToken token = new MatrixCardToken();
		token.setToken(dto.getToken());
		if (dto.getRequestId() != null) {
			token.setRequestId(dto.getRequestId().toString());
		}
		token.setXy0(dto.getXy0());
		token.setXy1(dto.getXy1());
		token.setXy2(dto.getXy2());
		token.setValue0(dto.getValue0());
		token.setValue1(dto.getValue1());
		token.setValue2(dto.getValue2());
		token.setRequestDate(dto.getRequestDate());

		return token;
	}
}
