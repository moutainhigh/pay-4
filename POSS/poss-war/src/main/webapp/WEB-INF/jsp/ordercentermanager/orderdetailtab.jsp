<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
	<link type="text/css" href="${ctx}/js/jquery/css/tabs/tabs.css" rel="stylesheet" />
	<style type="text/css">
		.border_div_td {
			font-size: 12px;
			width:80px;
			line-height: 20px;
			color: #333333;
		}
	</style>
	
	<script type="text/javascript">
	$(function() {
		$("#tabs").tabs({
			ajaxOptions: {
				error: function(xhr, status, index, anchor) {
					$(anchor.hash).html("加载Tab页签失败!");
				}
			}
		});

		 $("#tabsTable-1").tablesorter({
			 headers: {
			 	3:{sorter: false}
		 	}});
		 	 
		 var table2 = $('#tabsTable-2 tr td').eq(0).html();
		 if(table2 != null){
			 $("#tabsTable-2").tablesorter({ 
			        sortList: [[0,0]] 
			 });
		 }
		 $("#tabsTable-3").tablesorter({
			 headers: {
			 	2:{sorter: false}
			}});  
	});

	function showUrl(menu,Url){
	    parent.addMenu(menu,Url);
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
						<td align="right" class="border_div_td">用户号:</td>
						<td>${orderDetailDTO.payerKy}&nbsp;</td>
						<td align="right" class="border_div_td">用户姓名:</td>
						<td>${orderDetailDTO.payerName}&nbsp;</td>
					</tr>
					<tr>
						<td align="right" class="border_div_td">用户等级:</td>
						<td>
							<c:if test="${orderDetailDTO.payerLevel == '100'}">
								个人会员
							</c:if>						
							<c:if test="${orderDetailDTO.payerLevel == '200'}">
								普通企业会员
							</c:if>
							<c:if test="${orderDetailDTO.payerLevel == '201'}">
								中小型企业客户
							</c:if>
							<c:if test="${orderDetailDTO.payerLevel == '202'}">
								大企业客户
							</c:if>
							<c:if test="${orderDetailDTO.payerLevel == '203'}">
								集团企业客户
							</c:if>
							<c:if test="${orderDetailDTO.payerLevel == '204'}">
								超大型企业客户
							</c:if>																										
						</td>
						<td align="right" class="border_div_td">用户标识:</td>
						<td>${orderDetailDTO.payerId}&nbsp;</td>
					</tr>
					<tr>
						<td align="right" class="border_div_td">付款账户类型:</td>
						<td>
							<c:choose>
								<c:when test="${orderDetailDTO.payerAcctType == null || orderDetailDTO.payerAcctType == ''}">&nbsp;</c:when>
								<c:otherwise><li:select showStyle="desc" name="" itemList="${accountTypes}" selected="${orderDetailDTO.payerAcctType}"/>&nbsp;</c:otherwise>
							</c:choose>
						</td>
						<td align="right" class="border_div_td">付款账户号:</td>
						<td>${orderDetailDTO.payerAcct}&nbsp;</td>
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
						<td align="right" class="border_div_td">用户号:</td>
						<td>${orderDetailDTO.payeeKy}&nbsp;</td>
						<td align="right" class="border_div_td">用户姓名:</td>
						<td>${orderDetailDTO.payeeName}&nbsp;</td>
					</tr>
					<tr>
						<td align="right" class="border_div_td">用户等级:</td>
						<td>
							<c:if test="${orderDetailDTO.payeeLevel == '100'}">
								个人会员
							</c:if>						
							<c:if test="${orderDetailDTO.payeeLevel == '200'}">
								普通企业会员
							</c:if>
							<c:if test="${orderDetailDTO.payeeLevel == '201'}">
								中小型企业客户
							</c:if>
							<c:if test="${orderDetailDTO.payeeLevel == '202'}">
								大企业客户
							</c:if>
							<c:if test="${orderDetailDTO.payeeLevel == '203'}">
								集团企业客户
							</c:if>
							<c:if test="${orderDetailDTO.payeeLevel == '204'}">
								超大型企业客户
							</c:if>
						</td>
						<td align="right" class="border_div_td">用户标识:</td>
						<td>${orderDetailDTO.payeeId}&nbsp;</td>
					</tr>
					<tr>
						<td align="right" class="border_div_td">收款账户类型:</td>
						<td>
							<c:choose>
								<c:when test="${orderDetailDTO.payeeAcctType == null || orderDetailDTO.payeeAcctType == ''}">&nbsp;</c:when>
								<c:otherwise><li:select showStyle="desc" name="" itemList="${accountTypes}" selected="${orderDetailDTO.payeeAcctType}"/>&nbsp;</c:otherwise>
							</c:choose>
						</td>
						<td align="right" class="border_div_td">收款账户号:</td>
						<td>${orderDetailDTO.payeeAcct}&nbsp;</td>
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
						<td>${orderDetailDTO.orderId}&nbsp;</td>
						<td align="right" class="border_div_td">订单类型:</td>
						<td><li:select showStyle="desc" name="orderType" itemList="${orderStatus}" selected="${orderDetailDTO.orderType}"/>&nbsp;</td>
					</tr>
					<tr>
						<td align="right" class="border_div_td">订单创建时间:</td>
						<td><fmt:formatDate value="${orderDetailDTO.orderCreateTime}" type="both"/>&nbsp;</td>
						<td align="right" class="border_div_td">订单结束时间:</td>
						<td><fmt:formatDate value="${orderDetailDTO.orderEndTime}" type="both"/>&nbsp;</td>
					</tr>
					<tr>
						<td align="right" class="border_div_td">支付方式:</td>
						<td>${orderDetailDTO.payment}&nbsp;</td>
						<td align="right" class="border_div_td">交易状态:</td>
						<td>
							<c:choose>
								<c:when test="${'' eq orderDetailDTO.orderStatus || null == orderDetailDTO.orderStatus}">&nbsp;</c:when>
								<c:otherwise><li:select showStyle="desc" name="" itemList="${tradeStatus}" selected="${orderDetailDTO.orderStatus}"/>&nbsp;</c:otherwise>
							</c:choose>
						</td>
					</tr>
					<tr>
						<td align="right" class="border_div_td">交易金额:</td>
						<td><fmt:formatNumber value="${orderDetailDTO.orderAccount * 0.001}" pattern="#,##0.00"/>（元）&nbsp;</td>
						<td align="right" class="border_div_td">付款方费用:<br>收款方费用:</td>
						<td>
							<fmt:formatNumber value="${orderDetailDTO.payerFee * 0.001}" pattern="#,##0.00"/>（元）<br><br>
							<fmt:formatNumber value="${orderDetailDTO.payeeFee * 0.001}" pattern="#,##0.00"/>（元）
						</td>
					</tr>
					<c:if test="${orderCenterQueryDTO.paymentCatagory == 11}">
						<tr>
							<td align="right" class="border_div_td">消费卡卡种:</td>
							<td>${carryOverOrderMap.cardType}&nbsp;</td>
							<td align="right" class="border_div_td">所选卡面值:</td>
							<td><fmt:formatNumber value="${carryOverOrderMap.amountType * 0.001}" pattern="#,##0.00"/>（元）</td>
						</tr>
						<tr>
							<td align="right" class="border_div_td">卡号:</td>
							<td>${carryOverOrderMap.cardNo}&nbsp;</td>
							<td align="right" class="border_div_td">备注:</td>
							<td>
								<c:if test="${!empty carryOverOrderMap}">
									结转订单号为<a style="color: blue;" href="javascript:showUrl('结转订单详情', '${ctx}/fo-ordercentermanager-list.htm?method=processCarryOverOrderDetail&successAmount=${orderDetailDTO.orderAccount}&surplusAmount=${carryOverOrderMap.surplusAmount}&convertFee=${carryOverOrderMap.convertFee}')">${carryOverOrderMap.otherNo}</a>，请点击查看详情
								</c:if>
								&nbsp;
							</td>
						</tr>
					</c:if>
					<tr>
						<td align="right" class="border_div_td">商品名称:</td>
						<td>${orderDetailDTO.goodsName}&nbsp;</td>
						<td align="right" class="border_div_td">付款人IP:</td>
						<td>${orderDetailDTO.payerIp}&nbsp;</td>
					</tr>
					<tr>
						<td align="right" class="border_div_td">交易网址:</td>
						<td colspan="3">${orderDetailDTO.orderWebsite}&nbsp;</td>
					</tr>
					<tr>
						<td align="right" class="border_div_td">失败原因:</td>
						<td colspan="3"><c:if test="${!empty orderDetailDTO.failedReason}">${orderDetailDTO.failedReason}</c:if></td>
					</tr>					
				</tbody> 
			</table>
		</div>
		
		<div>
			<table width="80%" border="1">
				<thead> 
					<tr>    
						<th colspan="10">后台操控记录</th>     
					</tr> 
				</thead>
			<c:forEach items="${orderDetailDTO.wfHisList}" var="his"> 
				<tbody>
			<tr>
				<td width="9%">节点名称：</td>     
				<td width="9%">${his.nodeName}&nbsp;</td>
				<td width="9%">操作员：</td>     
				<td width="9%">${his.assignee}&nbsp;</td>
				<td width="9%">操作状态：</td>     
				<td width="9%">${his.handleStatus}&nbsp;</td>
				<td width="9%">操作时间：</td>     
				<td  width="15%"><fmt:formatDate value="${his.createTime}" type="both"/>&nbsp;</td>
				<td width="9%">操作备注：</td>     
				<td>${his.taskMessage}&nbsp;</td>
				     
			</tr>
				</tbody> 
			</c:forEach>
			</table>
		</div>
		
		<div>
			<table width="80%" border="1">
				<thead> 
					<tr>    
						<th colspan="4">订单独特信息</th>     
					</tr> 
				</thead>
				<tbody>
					<tr>
						<td align="right" class="border_div_td" >收款人姓名:</td>
						<td>${orderDetailDTO.payeeName}&nbsp;</td>
						<td align="right" class="border_div_td" >订单类型:</td>
						<td><li:select showStyle="desc" name="orderType" itemList="${orderStatus}" selected="${orderDetailDTO.orderType}"/>&nbsp;</td>
					</tr>
					<tr>
						<td align="right" class="border_div_td">银行卡号:</td>
						<td>${orderDetailDTO.bankAcct}&nbsp;</td>
						<td align="right" class="border_div_td">银行名称:</td>
						<td><li:select showStyle="desc" name="" itemList="${bankList}" selected="${orderDetailDTO.bankName}"/>&nbsp;</td>
					</tr>
					<tr>
						<td align="right" class="border_div_td">开户行:</td>
						<td>${orderDetailDTO.bankBranch}&nbsp;</td>
						<td align="right" class="border_div_td">省份:</td>
						<td>${orderDetailDTO.provinces}&nbsp;</td>
					</tr>
					<tr>
						<td align="right" class="border_div_td">城市:</td>
						<td>${orderDetailDTO.city}&nbsp;</td>
						<td align="right" class="border_div_td">付款方费用:<br>收款方费用:</td>
						<td><fmt:formatNumber value="${orderDetailDTO.payerFee * 0.001}" pattern="#,##0.00"/>（元）<br><br>
							<fmt:formatNumber value="${orderDetailDTO.payeeFee * 0.001}" pattern="#,##0.00"/>（元）</td>
					</tr>
					<tr>
						<td align="right" class="border_div_td">批次号:</td>
						<td>${orderDetailDTO.liquidateBatchKy}&nbsp;</td>
						<td align="right" class="border_div_td">留言:</td>
						<td>${orderDetailDTO.remarks}&nbsp;</td>
					</tr>
					<c:if test="${orderDetailDTO.payment == 0}">
						<tr>
							<td align="right" class="border_div_td">银企直联出款银行:</td>
							<td><li:select showStyle="desc" name="" itemList="${bankList}" selected="${orderDetailDTO.channelBank}"/>&nbsp;</td>
							<td align="right" class="border_div_td">银企直联交易状态:</td>
							<td>
								<c:choose>
									<c:when test="${12 == orderDetailDTO.workOrderStatus}">出款成功&nbsp;</c:when>
									<c:when test="${10 == orderDetailDTO.workOrderStatus}">出款失败&nbsp;</c:when>
									<c:otherwise>出款处理中&nbsp;</c:otherwise>
								</c:choose>
							</td>
						</tr>
						<tr>
							<td align="right" class="border_div_td">银企直联失败原因:</td>
							<td colspan="3"><c:if test="${!empty orderDetailDTO.failedReason}">${orderDetailDTO.failedReason}</c:if></td>
						</tr>
					</c:if>
				</tbody> 
			</table>
		</div>
	</div>
	
	<!-- div id="tabs-2" style="width: 80%">
		<div>
			<table id="tabsTable-1" width="80%" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
				<thead> 
					<tr>     
						<th>凭证号</th>     
						<th>账号</th>
						<th>借贷标志</th>
						<th>金额</th> 
						<th>创建时间</th>
						<th>状态</th>
						<th>分录号</th>
					</tr> 
				</thead> 
				<tbody>
					<c:forEach items="${entrieList}" var="entrie">
						<tr>     
							<td>${entrie.certificateNo}</td>
							<td>${entrie.accoutCode}</td>
							<td>
								<c:choose>
									<c:when test="${entrie.drMark == 1}">借</c:when>
									<c:when test="${entrie.drMark == 2}">贷</c:when>
									<c:otherwise>${entrie.drMark}</c:otherwise>
								</c:choose>
							</td>
							<td><fmt:formatNumber value="${entrie.amount * 0.001}" pattern="#,##0.00"/>&nbsp;</td>
							<td><fmt:formatDate value="${entrie.createDate}" type="both"/></td>
							<td>
								<c:choose>
									<c:when test="${entrie.status == 1}">已记账</c:when>
									<c:when test="${entrie.status == 0}">未记账</c:when>
									<c:otherwise>${entrie.status}</c:otherwise>
								</c:choose>
							</td>
							<td>${entrie.entrieNo}</td>
						</tr>
					</c:forEach>
				</tbody> 
			</table>
		</div>
	</div>
	
	<div id="tabs-3" style="width: 80%">
		<div>
			<table id="tabsTable-2" width="80%" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
			<thead> 
				<tr>     
					<th>时间</th>     
					<th>操作员号</th>
					<th>节点</th>
					<th>操作</th> 
					<th>备注</th>
				</tr> 
			</thead> 
			<tbody>
				<c:forEach items="${historyList}" var="history">
					<tr>  
						<td><fmt:formatDate value="${history.createTime}" type="both"/></td>   
						<td>${history.assignee}</td>
						<c:choose>
							<c:when test="${'audit' eq history.nodeName}">
								<td>审核</td>
								<td>
									<c:choose>
										<c:when test="${'0' eq history.handleStatus}">
											审核通过
										</c:when>
										<c:when test="${'1' eq history.handleStatus}">
											审核拒绝
										</c:when>
										<c:when test="${'2' eq history.handleStatus}">
											审核滞留
										</c:when>
										<c:when test="${'9' eq history.handleStatus}">
											流程跳转修复
										</c:when>
										<c:otherwise>
											${history.handleStatus}
										</c:otherwise>
									</c:choose>
								</td>
							</c:when>
							<c:when test="${'reAudit' eq history.nodeName}">
								<td>复核</td>
								<td>
									<c:choose>
										<c:when test="${'0' eq history.handleStatus}">
											复核同意
										</c:when>
										<c:when test="${'1' eq history.handleStatus}">
											复核退回
										</c:when>
										<c:when test="${'9' eq history.handleStatus}">
											流程跳转修复
										</c:when>
										<c:otherwise>
											${history.handleStatus}
										</c:otherwise>
									</c:choose>
								</td>
							</c:when>
							<c:when test="${'liquidate' eq history.nodeName}">
								<td>清算</td>
								<td>
									<c:choose>
										<c:when test="${'0' eq history.handleStatus}">
											清算拒绝
										</c:when>
										<c:when test="${'1' eq history.handleStatus}">
											清算退回
										</c:when>
										<c:when test="${'2' eq history.handleStatus}">
											完成
										</c:when>
										<c:when test="${'9' eq history.handleStatus}">
											流程跳转修复
										</c:when>
										<c:otherwise>
											${history.handleStatus}
										</c:otherwise>
									</c:choose>
								</td>
							</c:when>
							<c:when test="${'tempTask' eq history.nodeName}">
								<td>审核滞留</td>
								<td>
									<c:choose>
										<c:when test="${'0' eq history.handleStatus}">
											审核通过
										</c:when>
										<c:when test="${'1' eq history.handleStatus}">
											审核拒绝
										</c:when>
										<c:when test="${'9' eq history.handleStatus}">
											流程跳转修复
										</c:when>
										<c:otherwise>
											${history.handleStatus}
										</c:otherwise>
									</c:choose>
								</td>
							</c:when>
							<c:otherwise>
									<td>${history.nodeName}</td>
									<td>${history.handleStatus}</td>
							</c:otherwise>
						</c:choose>
						<td>${history.taskMessage}</td>
					</tr>
				</c:forEach>
			</tbody> 
		</table>
		</div>
	</div>
	
	<div id="tabs-4" style="width: 80%">
		<div>
			<table id="tabsTable-3" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
				<thead> 
					<tr>     
						<th>交易订单号</th>     
						<th>订单类型</th>
						<th>订单金额</th>
						<th>订单状态</th> 
						<th>订单时间</th>
					</tr> 
				</thead> 
				<tbody>
					<c:forEach items="${relationList}" var="relation">
						<tr>     
							<td>${relation.orderKy}</td>
							<td><li:select showStyle="desc" name="" itemList="${orderStatus}" selected="${relation.orderType}"/>&nbsp;</td>
							<td><fmt:formatNumber value="${relation.orderAmount * 0.001}" pattern="#,##0.00"/>&nbsp;</td>
							<td>
								<c:if test="${ relation.orderStatus == 119 || relation.orderStatus == '119'  }"> 已付款待完成 &nbsp;</c:if>
								<c:if test="${ relation.orderStatus != 119 && relation.orderStatus != '119' }">
									<li:select showStyle="desc" name="" itemList="${tradeStatus}" selected="${relation.orderStatus}"/>&nbsp;
								</c:if>
							</td>
							<td><fmt:formatDate value="${relation.orderDate}" type="both"/>&nbsp;</td>
						</tr>
					</c:forEach>
				</tbody> 
			</table>
		</div>
	</div-->
</div>
</div>
</body>
</html>
