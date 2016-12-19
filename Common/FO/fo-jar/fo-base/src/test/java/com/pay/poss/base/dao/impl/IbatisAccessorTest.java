package com.pay.poss.base.dao.impl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.inf.dao.BaseDAO;
import com.pay.poss.base.model.TestDbModel;

@ContextConfiguration(locations = {
		"classpath*:config/env/test-dataAccessContext.xml",
		"classpath*:context/**/*.xml" })
public class IbatisAccessorTest extends AbstractTestNGSpringContextTests {
	@Autowired
	private BaseDAO daoService;

	public TestDbModel createModel() {
		TestDbModel model = new TestDbModel();
		model.setRoleCode("test-hlv-11111");
		model.setRoleName("test-hlv-测试角色");
		model.setStatus(0);
		return model;
	}

	@Test
	public void testFindByCount() {
		int count = Integer.parseInt(String.valueOf(daoService
				.findObjectByCriteria("query-orderQuery.query-chargeOrder_COUNT",
						null)));
		System.out.println("==========FindByCount：" + count);
	}

	public void testFindByPage() {
		daoService.findByQuery("test.findPage", "test-hlv-");
	}

	// @Test
	public void testCreateExtend() {
		TestDbModel model = createModel();
		long roleKy = (Long) daoService.create("test.create", model);
		System.out.println("=======CreateExtend：" + roleKy);
	}

	// @Test
	public void testCreateBase() {
		// 不推荐使用
		// TestDbModel model=createModel();
		// long roleKy=daoService.create(model);
		// System.out.println("=======CreateBase："+roleKy);
	}

	/*
	 * @Test public void testFindByFilters() { Page<UserFormBean> page = new
	 * Page<UserFormBean>(); Map<String,String> map = new
	 * HashMap<String,String>(); //map.put("userId", "adminA");
	 * daoService.findByFilters("test.search", page, map); }
	 */
}
