/**
 * 
 */
package com.pay.batchpay.service.studenttest.impl;


import com.pay.batchpay.dao.studenttest.StudentTestDao;
import com.pay.batchpay.dto.StudentTest;
import com.pay.batchpay.service.studenttest.StudentTestService;


/**
 * @author PengJiangbo
 *
 */
public class StudentTestServiceImpl implements StudentTestService {

	private StudentTestDao studentTestDao ;
	
	/**
	 * 新增
	 */
	public void insertUser(StudentTest userTest) {
		this.studentTestDao.insertUser(userTest);
	}

	//=============================setter=========
	public void setStudentTestDao(StudentTestDao studentTestDao) {
		this.studentTestDao = studentTestDao;
	}
	
}
