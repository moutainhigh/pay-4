package com.pay.fundout.withdraw.dto.autorisk;

public class TransSumDto {

	/*
	 * 时间
	 */
	private String time;
	
	/*
	 * 月总入款金额
	 */
	private String toalInAmount;
	
	/*
	 * 月总退款金额
	 */
	private String totalOutAmount;
	
	/*
	 * 月总入款交易笔数
	 */
	private String totalInNums;
	
	/*
	 * 月平均单笔入款交易额
	 */
	private String averageInAmount;

	public String getTime() {
		return time;
	}

	public void setTime(String time) {
		this.time = time;
	}

	public String getToalInAmount() {
		return toalInAmount;
	}

	public void setToalInAmount(String toalInAmount) {
		this.toalInAmount = toalInAmount;
	}

	public String getTotalOutAmount() {
		return totalOutAmount;
	}

	public void setTotalOutAmount(String totalOutAmount) {
		this.totalOutAmount = totalOutAmount;
	}

	public String getTotalInNums() {
		return totalInNums;
	}

	public void setTotalInNums(String totalInNums) {
		this.totalInNums = totalInNums;
	}

	public String getAverageInAmount() {
		return averageInAmount;
	}

	public void setAverageInAmount(String averageInAmount) {
		this.averageInAmount = averageInAmount;
	}
	
}
