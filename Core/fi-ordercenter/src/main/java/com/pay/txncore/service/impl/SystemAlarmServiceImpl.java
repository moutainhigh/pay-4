package com.pay.txncore.service.impl;

import com.pay.fi.commons.SystemAlarmStatusEnum;
import com.pay.txncore.dao.SystemAlarmDAO;
import com.pay.txncore.dto.SystemAlarmDTO;
import com.pay.txncore.model.SystemAlarm;
import com.pay.txncore.service.SystemAlarmService;

public class SystemAlarmServiceImpl implements  SystemAlarmService {

	private SystemAlarmDAO systemAlarmDAO ;
	
	@Override
	public void recordAlarmService(SystemAlarmDTO alarmDTO) {
		SystemAlarm systemAlarm = new SystemAlarm();
		systemAlarm.setAlarmContent(alarmDTO.getAlarmContent());
		systemAlarm.setErrorDesc(alarmDTO.getErrorDesc());
		systemAlarm.setRemark(alarmDTO.getRemark());
		systemAlarm.setStatus(SystemAlarmStatusEnum.CREATE.getCode());

		systemAlarmDAO.create(systemAlarm);
	}

	public void setSystemAlarmDAO(SystemAlarmDAO systemAlarmDAO) {
		this.systemAlarmDAO = systemAlarmDAO;
	}

}
