package com.pay.pe.dto;

import com.pay.inf.dto.AbstractDtoUtil;
import com.pay.inf.dto.Dto;
import com.pay.inf.dto.DtoUtil;
import com.pay.inf.model.Model;
import com.pay.pe.model.PaymentOrder;
import com.pay.util.BeanConvertUtil;

public class PaymentOrderDtoUtil extends AbstractDtoUtil implements DtoUtil {

	/**
	 * Default constructor.
	 */
	public PaymentOrderDtoUtil() {
		super();
	}

	/*
	 * (non-Javadoc)
	 */
	public Class getModelClass() {
		return PaymentOrder.class;
	}

	/*
	 * (non-Javadoc)
	 */
	public Class getDtoClass() {
		return PaymentOrderDto.class;
	}

	@Override
	public Model convert2Model(Dto dto) {
		
		PaymentOrder payment = new PaymentOrder();

		return BeanConvertUtil.convert(PaymentOrder.class, dto);
	}
}
