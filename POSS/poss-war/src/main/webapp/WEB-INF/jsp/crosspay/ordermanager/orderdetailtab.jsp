<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<link type="text/css" href="${ctx}/js/jquery/css/tabs/tabs.css"
	rel="stylesheet" />
<style type="text/css">
.border_div_td {
	font-size: 12px;
	width: 80px;
	line-height: 20px;
	color: #333333;
}
</style>

<script type="text/javascript">
	$(function() {
		$("#tabs").tabs({
			ajaxOptions : {
				error : function(xhr, status, index, anchor) {
					$(anchor.hash).html("加载Tab页签失败!");
				}
			}
		});

		$("#tabsTable-1").tablesorter({
			headers : {
				3 : {
					sorter : false
				}
			}
		});

		var table2 = $('#tabsTable-2 tr td').eq(0).html();
		if (table2 != null) {
			$("#tabsTable-2").tablesorter({
				sortList : [ [ 0, 0 ] ]
			});
		}
		$("#tabsTable-3").tablesorter({
			headers : {
				2 : {
					sorter : false
				}
			}
		});
	});

	function showUrl(menu, Url) {
		parent.addMenu(menu, Url);
	}
</script>
</head>
<body>
	<!--<a href="${ctx}/fo-ordercentermanager-list.htm?orderType=${orderCenterQueryDTO.orderType}">返回订单查询</a>-->
	<div class="demo" style="width: 80%">
		<div id="tabs">
			<ul>
				<li><a href="#tabs-1">订单详细</a></li>
				<!-- li><a href="#tabs-2">分录查询</a></li>
		<li><a href="#tabs-3">工单历史</a></li>
		<li><a href="#tabs-4">关联订单</a></li-->
			</ul>
			<div id="tabs-1">
				<div>
					<table width="80%" border="1">
						<thead>
							<tr>
								<th colspan="4">付款人信息</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td align="right" class="border_div_td">付款人名称:</td>
								<td>${result.shipping_phone}&nbsp;</td>
								<td align="right" class="border_div_td">付款人地址:</td>
								<td>${result.address}&nbsp;</td>
							</tr>
							<tr>
								<td align="right" class="border_div_td">付款人电话:</td>
								<td>${result.shipping_fullname}&nbsp;</td>
								<td align="right" class="border_div_td">付款人邮编:</td>
								<td>${result.shipping_zip}&nbsp;</td>
							</tr>
							<tr>
								<td align="right" class="border_div_td">付款人邮箱:</td>
								<td>${result.shipping_mail}&nbsp;</td>
							</tr>
						</tbody>
					</table>
				</div>

				<div>
					<table width="80%" border="1">
						<thead>
							<tr>
								<th colspan="4">收款人信息</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td align="right" class="border_div_td">商户号:</td>
								<td>${result.partner_id}&nbsp;</td>
								<td align="right" class="border_div_td">收款姓名:</td>
								<td>${result.payee_name}&nbsp;</td>
							</tr>
							<tr>
								<td align="right" class="border_div_td">收款账户:</td>
								<td>${result.payee}&nbsp;</td>
							</tr>
						</tbody>
					</table>
				</div>

				<div>
					<table width="80%" border="1">
						<thead>
							<tr>
								<th colspan="4">订单信息</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td align="right" class="border_div_td">商家订单号:</td>
								<td>${result.order_id}&nbsp;</td>
								<td align="right" class="border_div_td">交易号:</td>
								<td>${result.trade_order_no}&nbsp;</td>
							</tr>
							<tr>
								<td align="right" class="border_div_td">交易域名:</td>
								<td>${result.trade_url}&nbsp;</td>
								<td align="right" class="border_div_td">交易IP:</td>
								<td></td>
							</tr>
							<tr>
								<td align="right" class="border_div_td">订单类型:</td>
								<td>${ .trade_type}&nbsp;</td>
								<td align="right" class="border_div_td">订单创建时间:</td>
								<td><fmt:formatDate value="${result.create_date}"
										type="both" />&nbsp;</td>
							</tr>
							<tr>
								<td align="right" class="border_div_td">订单结束时间:</td>
								<td><fmt:formatDate value="${result.update_date}"
										type="both" />&nbsp;</td>
								<td align="right" class="border_div_td">交易状态:</td>
								<td><c:if test="${result.orderstatus=='4'}">交易成功</c:if> <c:if
										test="${result.orderstatus=='2'}">交易成功</c:if> <c:if
										test="${result.orderstatus=='3'}">退款</c:if> <c:if
										test="${result.orderstatus=='1'}">进行中</c:if> <c:if
										test="${result.frozenStatus=='1'}">冻结</c:if> <c:if
										test="${result.frozenStatus=='0'}">交易成功</c:if> <c:if
										test="${result.refuseStatus=='1'}">拒付</c:if> <c:if
										test="${result.refuseStatus=='2'}">交易成功</c:if></td>
							</tr>
							<tr>
								<td align="right" class="border_div_td">商品名称:</td>
								<td>${result.goods_name}&nbsp;</td>
								<td align="right" class="border_div_td">交易金额（外币）</td>
								<td><fmt:formatNumber
										value="${result.order_amount * 0.001}" pattern="#,##0.00" /></td>
							</tr>
							<tr>
								<td align="right" class="border_div_td">交易金额（人民币）:</td>
								<td><fmt:formatNumber
										value="${result.ext_order_info_8 * 0.001}" pattern="#,##0.00" />（元）</td>
								<td align="right" class="border_div_td">运费:</td>
								<td></td>
							</tr>
						</tbody>
					</table>
				</div>
				<div>
					<table width="80%" border="1">
						<thead>
							<tr>
								<th colspan="4">运单信息</th>
							</tr>
						</thead>
						<tbody>
							<tr>
								<td align="right" class="border_div_td">快递公司名称:</td>
								<td>${result.express_com}&nbsp;</td>
								<td align="right" class="border_div_td">运单查询地址:</td>
								<td><
								<td>${result.query_url}&nbsp;</td>
								</td>
							</tr>
							<tr>
								<td align="right" class="border_div_td">运单单号:</td>
								<td>${result.tracking_no}&nbsp;</td>
							</tr>
						</tbody>
					</table>
				</div>
			</div>
		</div>
	</div>
</body>
</html>
