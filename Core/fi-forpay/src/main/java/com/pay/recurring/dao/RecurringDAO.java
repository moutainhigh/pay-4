package com.pay.recurring.dao;

import java.util.Map;

public interface RecurringDAO {

	void createRecurring(Map<String, String> data);

	void cancelRecurring(Map<String, String> data);

	void createRecurringCancel(Map<String, String> data);

	void cancelPostponeRecurring(Map<String, String> data);

}
