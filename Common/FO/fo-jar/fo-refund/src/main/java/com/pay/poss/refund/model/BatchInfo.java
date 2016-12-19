/** @Description 
 * @project 	poss-refund
 * @file 		BatchInfo.java 
 * Copyright © 2006-2020 pay Corporation. All rights reserved
 * @version     1.0
 * Date				Author			Changes
 * 2010-9-14		sunsea.li		Create 
 */
package com.pay.poss.refund.model;

/**对应批次信息表信息
 * @author sunsea_li
 *
 */
import java.util.Date;

import com.pay.inf.model.BaseObject;

public class BatchInfo extends BaseObject {
	private static final long serialVersionUID = 1289238845832833786L;
	private Long batchKy;// 主键
	private Long ruleKy;// 关联规则
	private String ruleName; //规则名称
	private String batchNum;// 批次号
	private Integer batchType;// 批次业务类型。1：提现 2：充退
	private String operators;// 操作人标识
	private Date updatetime;// 操作时间。默认当前系统时间
	private Integer status;// 状态。0：批次未生成 1：批次已生成 2：文件未生成 3：文件已生成 4：已废除 5：已下载 6：已导入 7：已确认导入
	private String batchName;//批次名称

	
	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public Long getBatchKy() {
		return batchKy;
	}

	public void setBatchKy(Long batchKy) {
		this.batchKy = batchKy;
	}

	public Long getRuleKy() {
		return ruleKy;
	}

	public void setRuleKy(Long ruleKy) {
		this.ruleKy = ruleKy;
	}
	
	public String getRuleName() {
		return ruleName;
	}

	public void setRuleName(String ruleName) {
		this.ruleName = ruleName;
	}

	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	public Integer getBatchType() {
		return batchType;
	}

	public void setBatchType(Integer batchType) {
		this.batchType = batchType;
	}

	public String getOperators() {
		return operators;
	}

	public void setOperators(String operators) {
		this.operators = operators;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public Integer getStatus() {
		return status;
	}

	public void setStatus(Integer status) {
		this.status = status;
	}
}