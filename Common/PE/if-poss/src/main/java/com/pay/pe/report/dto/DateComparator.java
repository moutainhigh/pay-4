package com.pay.pe.report.dto;

import java.util.Comparator;

public class DateComparator implements Comparator<Object> {

	@Override
	public int compare(Object arg0, Object arg1) {
		SumaryResuleDTO re1=(SumaryResuleDTO)arg0;
		SumaryResuleDTO re2=(SumaryResuleDTO)arg1;
		int flag=re2.getCreateDate().compareTo(re1.getCreateDate());
		return flag;
	}
	
	
	

}
