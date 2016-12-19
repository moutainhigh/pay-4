 /** @Description 
 * @project 	poss-refund
 * @file 		RefundWorkorder.java 
 * Copyright (c) 2006-2020 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-14		sunsea.li		Create 
*/
package com.pay.poss.refund.model;


import com.pay.inf.model.BaseObject;

/**对应充退工单表信息
 * @author sunsea_li
 *
 */
public class RefundWorkorder extends BaseObject {
	private static final long serialVersionUID = 5549549089444556991L;
	private Long workorderKy;//主键(工单号)
    private Long refundMKy;//主充退订单流水号
    private String workflowKy;//工作流实例ID
    private String batchNum;//批次号。自动跑批后将批次号更新到此字段，默认值为“SYS_0”
    private Integer batchStatus;//批次状态
									    /*0：未出批次
									    1：已出批次
									    2：批次废除*/

    private Integer status;//状态
							    /*0：工单初始
							    1：审核通过
							    2：审核拒绝
							    3：审核滞留
							    4：复核通过
							    5：复核拒绝
							    6：清算拒绝
							    7：完成*/
    private String remark; //原因
    
	public String getRemark() {
		return remark;
	}

	public void setRemark(String remark) {
		this.remark = remark;
	}

	public Long getWorkorderKy() {
		return workorderKy;
	}

	public void setWorkorderKy(Long workorderKy) {
		this.workorderKy = workorderKy;
	}

	public Long getRefundMKy() {
		return refundMKy;
	}

	public void setRefundMKy(Long refundMKy) {
		this.refundMKy = refundMKy;
	}

	public String getWorkflowKy() {
		return workflowKy;
	}

	public void setWorkflowKy(String workflowKy) {
		this.workflowKy = workflowKy;
	}

	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	public Integer getBatchStatus() {
		return batchStatus;
	}

	public void setBatchStatus(Integer batchStatus) {
		this.batchStatus = batchStatus;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}