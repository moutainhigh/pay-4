<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
function list(pageNo,totalCount) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#mainfromId").serialize() +  "&pageNo=" + pageNo + "&totalCount=" + totalCount;
	$.ajax({
		type: "POST",
		url: "${ctx}/refundOrder.do?method=list",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 
</script>
<form action="" method="post" name="listFrom">
</form>

<table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1" align="center">
	<thead>
		<tr>
			<th class="item15">系统交易号</th>
			<th class="item15">会员号</th>
			<th class="item15">商户订单号</th>
			<th class="item15">订单金额</th>
			<th class="item15">可退金额</th>
			<th class="item15">交易币种</th>
			<th class="item10">状态</th>
			<th class="item10">创建时间</th>
			<th class="item10">完成时间</th>
			<th class="item30">备注</th>
			<th class="item30">返回码</th>
			<th class="item30">返回消息</th>
			<th class="item10">操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${list}" var="order" varStatus="index">
		<c:choose>
	       <c:when test="${index.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
			<td><span></span>${order.tradeOrderNo}<span></span></td>
			<td>${order.partnerId}</td>
			<td>${order.orderId}</td>
			<td> <fmt:formatNumber type="number"   value="${order.orderAmount/1000}" /> </td>
			<td> <fmt:formatNumber type="number"   value="${order.refundAmount/1000}" /> </td>
			<td>${order.currencyCode}</td>
			<td>
				<!--0：冻结1：正常2：待审核3：审核未通过4：已删除  --> 
				<c:if test="${order.status=='0'}">
					交易未付款
				</c:if>
				<c:if test="${order.status=='1'}">
					交易已关闭
				</c:if>
				<c:if test="${order.status=='2'}">
					交易已付款
				</c:if>
				<c:if test="${order.status=='3'}">
					交易已退款
				</c:if>
				<c:if test="${order.status=='4'}">
					交易已成功
				</c:if>					
				<c:if test="${order.status=='5'}">
					交易已失败
				</c:if>		
			</td>
			<td><date:date value="${order.createDate}"/></td>
			<td><date:date value="${order.completeDate}"/></td>
			<td align="left">${order.goodsName}</td>
			<td align="left">${order.respCode}</td>
			<td align="left">${order.respMsg}</td>
			<td>
			  <c:if test="${order.status=='4' || order.status =='3'}">
			       <c:if test="${order.refundAmount>0}">
			          <a class="blues" href="${ctx}/refundOrder.do?method=applyRefund&serialNo=${order.tradeOrderNo}&partnerId=${order.partnerId}">退款</a>
			       </c:if>
			  </c:if>
			</td>
		</tr>
		</c:forEach>

	</tbody>
	
</table>
<li:pagination methodName="list" pageBean="${page}" sytleName="black2"/>