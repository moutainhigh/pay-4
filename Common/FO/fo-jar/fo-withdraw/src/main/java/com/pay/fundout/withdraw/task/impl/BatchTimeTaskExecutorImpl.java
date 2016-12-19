package com.pay.fundout.withdraw.task.impl;


import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.springframework.core.task.TaskExecutor;

import com.pay.fundout.withdraw.common.helper.QuartzRunInfoBusiType;
import com.pay.fundout.withdraw.dto.quartzruninfo.QuartzRunInfoDTO;
import com.pay.fundout.withdraw.dto.timeconfig.BatchTimeConfigDTO;
import com.pay.fundout.withdraw.quartz.batch.BatchTimeGenerationService;
import com.pay.fundout.withdraw.service.quartzruninfo.QuartzRunInfoService;
import com.pay.fundout.withdraw.service.timeconfig.BatchTimeConfigService;
import com.pay.fundout.withdraw.task.BatchTimeTaskExecutor;
import com.pay.poss.base.util.log.LogUtil;
import com.pay.poss.base.util.log.OPSTATUS;

 /**
  * 
  * @author Sean_yi
  * @createtime 2010-12-14
  * @filename BatchTimeTaskExecutorImpl.java
  * @version 1.0
  */

public class BatchTimeTaskExecutorImpl implements BatchTimeTaskExecutor {
	
	//批次生成时间配置表 状态为有效字段
	private static final Integer STATUS = new Integer(1);
	
	private BatchTimeConfigService batchTimeConfigService;
	
	private QuartzRunInfoService quartzRunInfoService;
	
	private BatchTimeGenerationService batchTimeGenerationService;
	
	private TaskExecutor taskExecutor;
	
	private String  quartzRunSpan;
		
	public void setBatchTimeConfigService(
			BatchTimeConfigService batchTimeConfigService) {
		this.batchTimeConfigService = batchTimeConfigService;
	}
	
	public void setQuartzRunInfoService(QuartzRunInfoService quartzRunInfoService) {
		this.quartzRunInfoService = quartzRunInfoService;
	}

	public void setBatchTimeGenerationService(
			BatchTimeGenerationService batchTimeGenerationService) {
		this.batchTimeGenerationService = batchTimeGenerationService;
	}

	public void setQuartzRunSpan(String quartzRunSpan) {
		this.quartzRunSpan = quartzRunSpan;
	}
	
	public void setTaskExecutor(TaskExecutor taskExecutor) {
		this.taskExecutor = taskExecutor;
	}
	
	@Override
	public void startExecutorTask() {
		LogUtil.info(BatchTimeTaskExecutorImpl.class, "运行自动生成批次", OPSTATUS.START, "", "运行开始");

		// 查询所有 批次生成时间配置表的 有效数据
		List<BatchTimeConfigDTO> dtos = batchTimeConfigService.getBatchTimeConfig(STATUS);
		if(dtos == null || dtos.size() <= 0){
			return;
		}
		for(BatchTimeConfigDTO dto : dtos){
			Date currentDate = new Date();
			Calendar calendar = Calendar.getInstance();
			calendar.setTime(currentDate);
			Integer weekDay = Integer.valueOf(calendar.get(Calendar.DAY_OF_WEEK));//获取周几
			//将规则转化为map
			Map<Integer, Integer> weekMap = getWeekMap(dto.getWeekDayList());
			Integer temp = weekMap.get(weekDay);
			if(null == temp){//不是当天运行的直接退出
				continue;
			}
			if(dto.getTimeType() == 1){//定时
				Date tempDate = this.getDateTime(dto.getSpecialPoint()); //执行时间
				//设置quartz上次执行时间
				Calendar lastRunDate = calendar;
				lastRunDate.set(Calendar.MINUTE, lastRunDate.get(Calendar.MINUTE)-Integer.valueOf(quartzRunSpan));
				
				//如果时间点落在上次运行结束和本次运行之间，则执行
				if(tempDate.before(currentDate) && tempDate.after(lastRunDate.getTime())){
					this.allowsExecute(dto.getSequenceId());
				}
			}else{//按时间间隔
				QuartzRunInfoDTO quartzinfo = quartzRunInfoService.getQuartzRunInfoByBussiType(dto.getSequenceId(), QuartzRunInfoBusiType.BATCHTIMECONFIG.getValue());
				if(quartzinfo == null){
					createQuartzInfo(dto.getSequenceId());
					continue;
				}
				//验证按时间片
				if(validateTimePoint(dto,currentDate,quartzinfo.getLastRunTime())){
					this.allowsExecute(dto.getSequenceId());
					this.updateQuartzInfo(quartzinfo.getSequenceId(), 1);
				}
			}
		}
		LogUtil.info(BatchTimeTaskExecutorImpl.class, "运行自动生成批次", OPSTATUS.SUCCESS, "", "运行结束");
	}
	
	/**
	 * 运行TASK信息
	 * @param timeId
	 */
	private void allowsExecute(Long timeId){
		BatchTimeTask task = new BatchTimeTask();
		task.setBatchTimeGenerationService(batchTimeGenerationService);
		task.setTimeId(timeId);
		taskExecutor.execute(task);
	}
	
	/**
	 * 验证按时间片执行
	 * @param dto
	 * @param currentDate
	 * @param lastrunTime
	 */
	private boolean validateTimePoint(BatchTimeConfigDTO dto,Date currentDate,Date lastrunTime){
		Date startDate = this.getDateTime(dto.getStartTimePoint());
		Date endDate = this.getDateTime(dto.getEndTimePoint());
		//但前时间大于开始时间并且小于结束时间
		if(startDate.before(currentDate) && endDate.after(currentDate)){
			Calendar tempcalendar = Calendar.getInstance();
			tempcalendar.setTime(lastrunTime);
			tempcalendar.set(Calendar.MINUTE, tempcalendar.get(Calendar.MINUTE)+Integer.valueOf(dto.getTimeSpan()));
			if(tempcalendar.getTime().before(currentDate)){
				return true;
			}
		}
		return false;
	}
	
	
	private void updateQuartzInfo(Long sequenceId,Integer status){
		QuartzRunInfoDTO dto = new QuartzRunInfoDTO();
		dto.setSequenceId(sequenceId);
		dto.setStatus(status);
		dto.setLastRunTime(new Date());
		dto.setUpdateDate(new Date());
		quartzRunInfoService.updateQuartzRunInfo(dto);
	}
	
	/**
	 * 无QUARTZ对象自动创建
	 * @param fkId
	 * @return
	 */
	private void createQuartzInfo(Long fkId){
		QuartzRunInfoDTO dto = new QuartzRunInfoDTO();
		dto.setBusiType(QuartzRunInfoBusiType.BATCHTIMECONFIG.getValue());
		dto.setFkId(fkId);
		dto.setStatus(1);
		dto.setCreateDate(new Date());
		dto.setLastRunTime(new Date());
		dto.setUpdateDate(new Date());
		quartzRunInfoService.createQuartzRunInfo(dto);
	}
	
	/**
	 * 将weekDayList转化为周日到周六
	 * @param weekDayList
	 * @return
	 */
	private Map<Integer, Integer> getWeekMap(String weekDayList){
		Map<Integer, Integer> weekMap = new HashMap<Integer, Integer>();
		for(int i=0;i<weekDayList.length();i++){
			char c = weekDayList.charAt(i);
			if(c == '1'){
				weekMap.put(Integer.valueOf(i+1),Integer.valueOf(i+1));
			}
		}
		return weekMap;	
	}
	
	
	/**
	 * 获得时间点
	 * @param timeStr
	 * @return
	 */
	private Date getDateTime(String timeStr){
		Integer time[] = this.getTime(timeStr);
		Integer hh = time[0];
		Integer mi = time[1];
		Integer ss = time[2];
		//规则定义执行时间
		Date currentDate = new Date();
		Calendar tempDate = Calendar.getInstance();
		tempDate.setTime(currentDate);
		tempDate.set(Calendar.HOUR_OF_DAY, hh == null ? 0 : hh);
		tempDate.set(Calendar.MINUTE, mi == null ? 0 : mi);
		tempDate.set(Calendar.SECOND, ss == null ? 0 : ss);
		tempDate.set(Calendar.MILLISECOND, 0);
		return tempDate.getTime();
	}
	
	/**
	 * 通过传入的时间转换为一个数组 HH:MI:SS
	 * @param time
	 * @return
	 */
	private Integer[] getTime(String time){
		Integer reTime[] = new Integer[3];
		String tempTime[] = time.split(":");
		for(int i=0;i<tempTime.length;i++){
			int temp = Integer.valueOf(tempTime[i]);
			reTime[i]= temp;
		}
		return reTime;
	}
}
