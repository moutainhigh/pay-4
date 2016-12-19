package com.pay.recurring.service;

import java.util.List;

import com.pay.recurring.model.RecurringQue;

public interface TaskRecurringService {

	void crateRecurringQue();

	List<RecurringQue> findRecurringQue();

	void updateRecurring(RecurringQue recurringQue2);

	void delRecurringQue(RecurringQue recurringQue2);

	void updateRecurringQue(RecurringQue recurringQue2);

	Boolean compareFailedTimes(RecurringQue recurringQue2);

	void updateFailedRecurring(RecurringQue recurringQue2);

	void updateSingleFailedRecurringQue(RecurringQue recurringQue2);

}
