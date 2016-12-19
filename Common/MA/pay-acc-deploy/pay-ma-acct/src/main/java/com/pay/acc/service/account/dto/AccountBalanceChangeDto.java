/**
 * 
 */
package com.pay.acc.service.account.dto;

import java.io.Serializable;
import java.util.List;

/**
 * @author wolf_huang 
 *
 * @date 2010-9-21
 */
public class AccountBalanceChangeDto implements Serializable{
	
	/**
	 * 余额变化明细列表信息
	 */
	private List<AcctDetailBalanceDto> acctDetailBalanceDtos;
	
	/**
	 * pe 记账 dto
	 */
	private ChargeUpPEDto chargeUpPEDto;

	/**
	 * @return the acctDetailBalanceDtos
	 */
	public List<AcctDetailBalanceDto> getAcctDetailBalanceDtos() {
		return acctDetailBalanceDtos;
	}

	/**
	 * @param acctDetailBalanceDtos the acctDetailBalanceDtos to set
	 */
	public void setAcctDetailBalanceDtos(List<AcctDetailBalanceDto> acctDetailBalanceDtos) {
		this.acctDetailBalanceDtos = acctDetailBalanceDtos;
	}

	/**
	 * @return the chargeUpPEDto
	 */
	public ChargeUpPEDto getChargeUpPEDto() {
		return chargeUpPEDto;
	}

	/**
	 * @param chargeUpPEDto the chargeUpPEDto to set
	 */
	public void setChargeUpPEDto(ChargeUpPEDto chargeUpPEDto) {
		this.chargeUpPEDto = chargeUpPEDto;
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#toString()
	 */
	@Override
	public String toString() {
		return "AccountBalanceChangeDto [acctDetailBalanceDtos=" + acctDetailBalanceDtos + ", chargeUpPEDto=" + chargeUpPEDto + "]";
	}

	/* (non-Javadoc)
	 * @see java.lang.Object#hashCode()
	 */
	@Override
	public int hashCode() {
		final int prime = 31;
		int result = 1;
		result = prime * result + ((acctDetailBalanceDtos == null) ? 0 : acctDetailBalanceDtos.hashCode());
		result = prime * result + ((chargeUpPEDto == null) ? 0 : chargeUpPEDto.hashCode());
		return result;
	}

	
	
	

}
