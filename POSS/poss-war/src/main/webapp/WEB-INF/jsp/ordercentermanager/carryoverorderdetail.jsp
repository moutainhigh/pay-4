<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<link type="text/css" href="${ctx}/js/jquery/css/tabs/tabs.css" rel="stylesheet" />
		<style type="text/css">
			.border_div_td {
				font-size: 12px;
				width: 80px;
				line-height: 20px;
				color: #333333;
			}
		</style>
	</head>
	<body>
		<div class="demo" style="width: 80%">
			<div id="tabs">
				<ul>
					<li>结转订单详细</li>
				</ul>
				<div id="tabs-1">
					<div>
						<table width="80%" border="1">
							<tr>
								<td align="right" class="border_div_td">实际支付成功金额:</td>
								<td><fmt:formatNumber value="${successAmount * 0.001}" pattern="#,##0.00"/>（元）</td>
								<td align="right" class="border_div_td">结转充值金额:</td>
								<td><fmt:formatNumber value="${surplusAmount * 0.001}" pattern="#,##0.00"/>（元）</td>
							</tr>
							<tr>
								<td align="right" class="border_div_td">结转手续费金额:</td>
								<td colspan="3"><fmt:formatNumber value="${convertFee * 0.001}" pattern="#,##0.00"/>（元）</td>
							</tr>
						</table>
					</div>
				</div>
			</div>
		</div>
	</body>
</html>
