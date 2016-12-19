package com.pay.poss.service.ordercenter.fundout.impl.backfundout;

import java.util.List;
import java.util.Map;

import javax.annotation.Resource;

import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.testng.AbstractTestNGSpringContextTests;
import org.testng.annotations.Test;

import com.pay.inf.dao.Page;
import com.pay.poss.service.ordercenter.OrderCenterCommonService;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterQueryDTO;
import com.pay.poss.service.ordercenter.dto.list.OrderCenterResultDTO;
import com.pay.poss.service.ordercenter.dto.relationorder.OrderRelationDTO;

@ContextConfiguration(locations = { "classpath*:config/env/test-dataAccessContext.xml", "classpath*:context/**/*.xml" })
public class OrderCenterBackFundoutServiceImplTest extends AbstractTestNGSpringContextTests {
	@Resource(name = "context_fundout_ordercenterbackfundoutservice")
	private OrderCenterCommonService backFundService;

	@Test
	public void testQueryOrderDetail() {
		OrderCenterQueryDTO query = buildQuery();
		query.setOrderKy("2001011231521005859");
		query.setPayerMemberCode("10000000354");
		query.setPayeeMemberCode("10000000114");
		System.out.println(backFundService.queryOrderDetail(query));
	}

	@Test
	public void testQueryOrderRelation() {
		OrderCenterQueryDTO query = buildQuery();
		// 查询提现
		query.setBusiType("19");
		query.setOrderSrc("2001011221600005761");
		Map result = backFundService.queryOrderRelation(query);
		List<OrderRelationDTO> t1 = (List<OrderRelationDTO>) result.get("relationList");
		for (OrderRelationDTO orderCenterResultDTO : t1) {
			System.out.println(orderCenterResultDTO);
		}
		// 查询充退
		query.setBusiType("16");
		query.setOrderSrc("2001011201713005748");
		result = backFundService.queryOrderRelation(query);
		t1 = (List<OrderRelationDTO>) result.get("relationList");
		for (OrderRelationDTO orderCenterResultDTO : t1) {
			System.out.println(orderCenterResultDTO);
		}
		// 查询付款到账户
		query.setBusiType("20");
		query.setOrderSrc("2001011301430006226");
		result = backFundService.queryOrderRelation(query);
		t1 = (List<OrderRelationDTO>) result.get("relationList");
		for (OrderRelationDTO orderCenterResultDTO : t1) {
			System.out.println(orderCenterResultDTO);
		}
	}

	@Test
	public void testQueryResultList() {
		Map<String, Page<OrderCenterResultDTO>> result = backFundService.queryResultList(buildPage(), buildQuery());
		System.out.println(result);
	}

	private Page<OrderCenterResultDTO> buildPage() {
		Page<OrderCenterResultDTO> result = new Page<OrderCenterResultDTO>();
		result.setPageSize(10);
		result.setPageNo(1);
		return result;
	}

	private OrderCenterQueryDTO buildQuery() {
		OrderCenterQueryDTO result = new OrderCenterQueryDTO();
		result.setOrderKy("2001011261847006080");
		result.setOrderType("1014");
		return result;
	}

}
