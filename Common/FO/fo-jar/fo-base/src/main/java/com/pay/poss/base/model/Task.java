package com.pay.poss.base.model;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

import org.apache.commons.lang.StringUtils;

import com.pay.poss.base.common.Constants;
import com.pay.poss.base.schedule.task.ITaskHandler;
import com.pay.util.DateUtil;

/**
 * 工作任务对象
 * 
 * @author hlv
 * @lastModify Feb 8, 2010
 * @version 0.8
 * 
 */
public class Task implements Runnable {

	private Long scheduleKY;
	private String callSeq; // 流水
	private String callTime; // 时间戳
	private String callType;// 任务类型

	private String execDate;// 日期
	private Integer execWeek; // 星期
	private String execTime;// 时间

	private String busiCode;// 规则号
	private Integer busiPriority;// 优先级
	private String busiParam;// 其它参数
	private Integer status; // 状态
	private String rawMsg; //
	private int type;

	private String batchNum;// 批次号
	private String newBatchNum;
	private String batchName;

	private List<String> manualWorkorder = new ArrayList<String>();
	
	private List<BatchFileRecord> batchFileRecords=new ArrayList<BatchFileRecord>();

	public List<BatchFileRecord> getBatchFileRecords() {
		return batchFileRecords;
	}

	public void setBatchFileRecords(List<BatchFileRecord> batchFileRecords) {
		this.batchFileRecords = batchFileRecords;
	}

	private ITaskHandler taskHandler; // 任务处理器

	public String getCallSeq() {
		return callSeq;
	}

	public void setCallSeq(String callSeq) {
		this.callSeq = callSeq;
	}

	public String getExecTime() {
		return execTime;
	}

	public int getBusiPriority() {
		return busiPriority;
	}

	public void setBusiPriority(int busiPriority) {
		this.busiPriority = busiPriority;
	}

	public Long getScheduleKY() {
		return scheduleKY;
	}

	public void setScheduleKY(Long scheduleKY) {
		this.scheduleKY = scheduleKY;
	}

	public String getCallType() {
		return callType;
	}

	public Task(String rawMsg, String type) {
		this.rawMsg = rawMsg;
		this.callType = type;
	}

	public String getBatchName() {
		return batchName;
	}

	public void setBatchName(String batchName) {
		this.batchName = batchName;
	}

	public String getCallTime() {
		return callTime;
	}

	public void setCallTime(String callTime) {
		this.callTime = callTime;
		if (StringUtils.isEmpty(callTime)) {
			Date date = Calendar.getInstance().getTime();
			this.execDate = DateUtil.dateToStr(date, "yyyy-MM-dd");
			this.execTime = DateUtil.dateToStr(date, "HH:mm:ss");
			this.execWeek = DateUtil.getWeek(this.execDate);
		} else {
			Date date = DateUtil.strToDate(callTime, "yyyyMMddHHmmssSSS");
			this.execDate = DateUtil.dateToStr(date, "yyyy-MM-dd");
			this.execTime = DateUtil.dateToStr(date, "HH:mm:ss");
			this.execWeek = DateUtil.getWeek(this.execDate);
		}
	}

	public Integer getExecWeek() {
		return execWeek;
	}

	public int getStatus() {
		return status;
	}

	public void setStatus(int status) {
		this.status = status;
	}

	public String getExecDate() {
		return execDate;
	}

	public String getRawMsg() {
		return rawMsg;
	}

	public String getBusiCode() {
		return busiCode;
	}

	public void setBusiCode(String busiCode) {
		this.busiCode = busiCode;
	}

	public String getBusiParam() {
		return busiParam;
	}

	public void setBusiParam(String busiParam) {
		this.busiParam = busiParam;
	}

	public void setTaskHandler(ITaskHandler taskHandler) {
		this.taskHandler = taskHandler;
	}

	public String getBatchNum() {
		return batchNum;
	}

	public void setBatchNum(String batchNum) {
		this.batchNum = batchNum;
	}

	public String getNewBatchNum() {
		return newBatchNum;
	}

	public void setNewBatchNum(String newBatchNum) {
		this.newBatchNum = newBatchNum;
	}

	public int getType() {
		return type;
	}

	public void setType(int type) {
		this.type = type;
	}

	public List<String> getManualWorkorder() {
		return manualWorkorder;
	}

	public void addManualWorkorder(List<String> newManualWorkorder) {
		manualWorkorder.addAll(newManualWorkorder);
		type = Constants.BATCH_RULE_MANUAL;
	}

	public String toString() {
		return "POSS TASK [callSeq:" + callSeq + "-batchNum:" + batchNum + "-newBatchNum:" + newBatchNum + "-innerType:" + type + "-busiCode:" + busiCode + "-rawMsg:" + rawMsg + "]";
	}

	@Override
	public void run() {
		try{
			this.taskHandler.execute(this);
		}catch(Exception e){
			e.printStackTrace();
		}
		
	}
}
