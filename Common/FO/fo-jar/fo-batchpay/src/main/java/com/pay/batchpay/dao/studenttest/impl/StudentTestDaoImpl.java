/**
 * 
 */
package com.pay.batchpay.dao.studenttest.impl;

import java.util.List;

import com.pay.batchpay.dao.studenttest.StudentTestDao;
import com.pay.batchpay.dto.StudentTest;
import com.pay.inf.dao.impl.BaseDAOImpl;

/**
 * @author PengJiangbo
 *
 */
public class StudentTestDaoImpl extends BaseDAOImpl<StudentTest> implements
		StudentTestDao {

	public void insertUser(StudentTest userTest) {
		getSqlMapClientTemplate().insert(getNamespace().concat("insertUserTest"), userTest); 
	}

}
