<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
function order(pageNo,totalCount) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#mainfromId").serialize() +  "&pageNo=" + pageNo + "&totalCount=" + totalCount;
	$.ajax({
		type: "POST",
		url: "${ctx}/paymentOrderQuery.do?method=list",
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
			<th class="item15">网关交易号</th>
			<th class="item15">商户号</th>
			<th class="item10">状态</th>
			<th class="item10">金额</th>
			<th class="item10">币种</th>
			<th class="item10">渠道</th>
			<th class="item10">创建时间</th>
			<th class="item10">完成时间</th>
		<!-- 	<th class="item10">操作</th> -->
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
			<td>${order.paymentOrderNo}</td>
			<td>${order.tradeOrderNo}</td>
			<td>${order.payee}</td>
			<td>
				<!--0：冻结1：正常2：待审核3：审核未通过4：已删除  --> 
				<c:if test="${order.status=='0'}">
					支付中
				</c:if>
				<c:if test="${order.status=='1'}">
					支付成功
				</c:if>
				<c:if test="${order.status=='2'}">
					支付失败
				</c:if>
			</td>
			<td> <fmt:formatNumber type="number"   value="${order.paymentAmount/1000}" /> </td>
			<td>${order.currencyCode}</td>
			<td>${order.orgCode}</td>
			<td><date:date value="${order.createDate}"/></td>
			<td><date:date value="${order.completeDate}"/></td>
			<!-- <td><a href="javaScript:;" onclick="">详情</a></td> -->
		</tr>
		</c:forEach>
		
	</tbody>
</table>
<li:pagination methodName="order" pageBean="${page}" sytleName="black2"/>