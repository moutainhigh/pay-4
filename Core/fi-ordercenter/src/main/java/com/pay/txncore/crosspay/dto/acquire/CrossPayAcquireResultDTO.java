/**
 * 
 */
package com.pay.txncore.crosspay.dto.acquire;

import java.util.List;

import com.pay.txncore.dto.TradeOrderDTO;

/**
 * 收单结果参数
 * 
 * @author huhb
 *
 */
public class CrossPayAcquireResultDTO {

	// 请求数据原封
	private CrossPayAcquireRequestDTO requestDTO;

	// 网关订单信息
	private List<TradeOrderDTO> orderDtos;

	// 校验信息
	private String validateMsg;

	private String rate;

	public CrossPayAcquireRequestDTO getRequestDTO() {
		return requestDTO;
	}

	public void setRequestDTO(CrossPayAcquireRequestDTO requestDTO) {
		this.requestDTO = requestDTO;
	}

	public List<TradeOrderDTO> getOrderDtos() {
		return orderDtos;
	}

	public void setOrderDtos(List<TradeOrderDTO> orderDtos) {
		this.orderDtos = orderDtos;
	}

	public String getValidateMsg() {
		return validateMsg;
	}

	public void setValidateMsg(String validateMsg) {
		this.validateMsg = validateMsg;
	}

	@Override
	public String toString() {
		return "CrossPayAcquireResultDTO [orderDtos=" + orderDtos
				+ ", requestDTO=" + requestDTO + ", validateMsg=" + validateMsg
				+ ",rate=" + rate + "]";
	}

	public String getRate() {
		return rate;
	}

	public void setRate(String rate) {
		this.rate = rate;
	}

}
