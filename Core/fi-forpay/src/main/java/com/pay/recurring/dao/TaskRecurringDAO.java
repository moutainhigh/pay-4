package com.pay.recurring.dao;

import java.util.List;

import com.pay.recurring.model.RecurringQue;

public interface TaskRecurringDAO {

	void crateRecurringQue();

	List<RecurringQue> findRecurringQue();

	void updateRecurring(RecurringQue resultMap);

	void delRecurringQue(RecurringQue resultMap);

	void updateRecurringQue(RecurringQue resultMap);

	Integer findMaxFailedTimes(RecurringQue resultMap);

	Integer findFailedTimes(RecurringQue resultMap);

	void updateFailedRecurring(RecurringQue resultMap);

	void updateSingleFailedRecurringQue(RecurringQue resultMap);

}
