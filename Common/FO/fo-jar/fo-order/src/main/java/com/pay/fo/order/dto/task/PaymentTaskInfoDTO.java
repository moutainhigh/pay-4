package com.pay.fo.order.dto.task;

import java.util.Date;

public class PaymentTaskInfoDTO {
    /**
     * 流水号
     */
    private Long sequenceId;

   /**
    * 任务批次号
    * 单笔付款时，赋值为订单号；批量付款时，赋值为业务批次号
    */
    private String taskBatchNo;

    /**
     * 付款方会员号
     */
    private Long memberCode;

    /**
     * 任务类型
     * 1-付款到账户
     * 2-批付到账户
     * 3-付款到银行
     * 4-批付到银行
     */
    private Integer taskType;

    /**
     * 任务执行时间
     */
    private Date excuteDate;

    /**
     * 创建日期
     */
    private Date createDate;

    /**
     * 更新日期
     */
    private Date updateDate;

    /**
     * 状态
     * 0-初始化
     * 1-待执行
     * 2-执行完成
     * 3-任务关闭
     */
    private Integer status;

	public Long getSequenceId() {
		return sequenceId;
	}

	public void setSequenceId(Long sequenceId) {
		this.sequenceId = sequenceId;
	}

	public String getTaskBatchNo() {
		return taskBatchNo;
	}

	public void setTaskBatchNo(String taskBatchNo) {
		this.taskBatchNo = taskBatchNo;
	}

	public Long getMemberCode() {
		return memberCode;
	}

	public void setMemberCode(Long memberCode) {
		this.memberCode = memberCode;
	}

	public Integer getTaskType() {
		return taskType;
	}

	public void setTaskType(Integer taskType) {
		this.taskType = taskType;
	}

	public Date getExcuteDate() {
		return excuteDate;
	}

	public void setExcuteDate(Date excuteDate) {
		this.excuteDate = excuteDate;
	}

	public Date getCreateDate() {
		return createDate;
	}

	public void setCreateDate(Date createDate) {
		this.createDate = createDate;
	}

	public Date getUpdateDate() {
		return updateDate;
	}

	public void setUpdateDate(Date updateDate) {
		this.updateDate = updateDate;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}

}