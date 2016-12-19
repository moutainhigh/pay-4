<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="li_new" uri="page"%>

<table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
		<tr>
			<th>批次号</th>
			<th>系统交易号</th>
			<th>会员号</th>
			<th>商户订单号</th>
			<th>卡号</th>
			<th>订单金额</th>
			<th>可退金额</th>
			<th>交易币种</th>
			<th>交易状态</th>
			<th>创建时间</th>
			<th>完成时间</th>
			<th>商户返回码</th>
			<th>返回信息</th>
		</tr>
	</thead>
	
	<tbody>
		<c:forEach items="${tradeOrders}" var="dto" varStatus="index">
			<c:choose>
	       <c:when test="${index.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
				<td>
					<%-- <input type="hidden" name="id" value="${dto.id}"> --%>
						${dto.batchNumber}
				</td>
				<td>
					${dto.tradeOrderNo}
					
				</td>
				<td>
					${dto.partnerId}
				</td>
				<td>
					${dto.orderId}
				</td>
				<td>
					${dto.cardCode}
				</td>
				<td>
					${dto.orderAmount/1000}
				</td>
				<td>
					${dto.refundAmount/1000}
				</td>
				<td>
					${dto.currencyCode}
				</td>
				<td>
				<c:if test="${dto.statusList=='0'}">
					未付款
				</c:if>
				<c:if test="${dto.statusList=='1'}">
					支付失败
				</c:if>
				<c:if test="${dto.statusList=='2'}">
					交易已付款
				</c:if>
				<c:if test="${dto.statusList=='3'}">
					支付成功
				</c:if>
				<c:if test="${dto.statusList=='4'}">
					支付成功
				</c:if>					
				<c:if test="${dto.statusList=='5'}">
					支付失败
				</c:if>		
			</td>
				<td>
				<date:date value="${dto.createDate}"/>
				<%-- <fmt:formatDate value="${dto.createDate}" type="both"/>  --%>
				</td>
				<td>
				<date:date value="${dto.completeDate}"/>
				<%-- <fmt:formatDate value="${dto.completeDate }" type="both"/> --%> 
				</td>
				<td>
					${dto.respCode}
				</td>
				<td>
					${dto.respMsg}
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
 <!-- <div id="resultListDiv" class="listFence"></div> -->
 <script type="text/javascript">

 function find(pageNo,totalCount,pageSize) {
		var pars = $("#mainfromId").serialize()+"&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
		$.ajax({
			type: "POST",
			url: "${ctx}/manualTransSub.do?method=tradeQueryResult",
			data: pars,
			success: function(result) {
				$('#resultListDiv').html(result);
			}
		});
	}
	
</script>
<li:pagination methodName="find" pageBean="${page}" sytleName="black2"/>