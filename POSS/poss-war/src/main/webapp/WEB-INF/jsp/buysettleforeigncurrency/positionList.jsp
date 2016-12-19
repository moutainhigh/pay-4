<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<body>
		<table class="tablesorter" border="0" cellpadding="0" cellspacing="1">
			<thead>
				<tr>
					<th>调拨流水号</th>
					<th>申请时间</th>
					<th>调出账户</th>
					<th>调出账户可购汇余额 </th>
					<th>调出金额</th>
					<th>调入账户</th>
					<th>调入账户可购汇余额</th>
					<th>调入金额</th>
					<th>审核状态</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="po" items="${list}" varStatus="v">
					<c:choose>
	       <c:when test="${v.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
						<td>${po.allotSequence}</td>
						<td>${po.createDate}</td>
						<td>${po.calloutAccount}</td>
						<td><fmt:formatNumber type="number" value="${po.calloutBuyAmount/1000}" maxFractionDigits="2"/> ${po.calloutCurrencyCode}</td>
						<td><fmt:formatNumber type="number" value="${po.calloutAmount/1000}" maxFractionDigits="2"/>  ${po.calloutCurrencyCode}</td>
						<td>${po.callinAccount}</td>
						<td><fmt:formatNumber type="number" value="${po.callinBuyAmount/1000}" maxFractionDigits="2"/> ${po.callinCurrencyCode }</td>
						<td><fmt:formatNumber type="number" value="${po.callinAmount/1000}" maxFractionDigits="2"/>${po.callinCurrencyCode }</td>
						<td>
							<c:choose>
							<c:when test="${po.status=='0'}">待审核</c:when>
							<c:when test="${po.status=='1'}">审核通过</c:when>
							<c:when test="${po.status=='2'}">审核拒绝</c:when>
						</c:choose>	
						</td>
						<td>
							<c:if test="${po.status=='0'}">
								<a href="#"  onclick="update(1,'${po.allotSequence}')">通过</a>
								<a href="#" onclick="update(2,'${po.allotSequence}')">拒绝</a>
							</c:if>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>	
		<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>
	</body>
</html>
<script type="text/javascript">

	function update(status,allotSequence){
		window.location.href="${ctx}/positionAllocaAudit.do?method=update&allotSequence="+allotSequence+"&status="+status;
	}
	
	function query(pageNo, totalCount){
		var pars = $("#mainfrom").serialize() + "&pageNo=" + pageNo
		+ "&totalCount=" + totalCount;
		$.ajax({
			type : "POST",
			url : "./positionAllocaAudit.do?method=list",
			data : pars,
			success : function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
	}
</script>