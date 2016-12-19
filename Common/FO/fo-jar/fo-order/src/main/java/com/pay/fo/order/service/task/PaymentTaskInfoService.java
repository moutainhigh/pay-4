package com.pay.fo.order.service.task;

import java.util.Date;
import java.util.List;

import com.pay.fo.order.dto.task.PaymentTaskInfoDTO;

public interface PaymentTaskInfoService {
	/**
	 * 存储任务
	 * @param task
	 */
	void create(PaymentTaskInfoDTO task);
	/**
	 * 更新任务状态
	 * @param task
	 * @param oldStatus
	 * @return
	 */
	boolean update(PaymentTaskInfoDTO task,Integer oldStatus);
	/**
	 * 获取指定的任务信息
	 * @param taskBatchNo 任务批次号
	 * @param taskType    任务类型
	 * @param memberCode  付款方会员号
	 * @return
	 */
	PaymentTaskInfoDTO get(String taskBatchNo,Integer taskType,Long memberCode);
	/**
	 * 根据任务处理状态获取任务列表信息
	 * @param status
	 * @return
	 */
	List<PaymentTaskInfoDTO> getList(Integer status);
	
	/**
	 * 根据任务处理状态、执行时间（小于等于当前执行时间）获取任务列表信息
	 * @param status
	 * @return
	 */
	List<PaymentTaskInfoDTO> getList(Integer status,Date excuteDate);
}
