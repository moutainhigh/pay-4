package com.pay.poss.base.schedule.task;

import java.util.Set;

import com.pay.poss.base.model.Task;

public interface ITaskHandler {

	public String execute(Task task);

	// public String getHandleType();
	public Set<String> getAllowTaskTypes();
}
