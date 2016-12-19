<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="li_new" uri="page"%>

<table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
		<tr>
			<th>批次号</th>
			<th>提交日期</th>
			<th>会员号</th>
			<th>卡数量</th>
			<th>卡组织</th>
			<th>交易成功笔数</th>
			<th>交易开始时间</th>
			<th>交易结束时间</th>
			<th>预计交易时长(时)</th>
			<th>操作员</th>
			<th>状态</th>
			<th>操作</th>
		</tr>
	</thead>
	
	<tbody>
		<c:forEach items="${automaticTrades}" var="dto" varStatus="index">
			<c:choose>
	       <c:when test="${index.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
				<td>
					${dto.batchNumber}
				</td>
				<td>
				<fmt:formatDate value="${dto.subDate }" type="both"/>
				</td>
				<td>
					${dto.partnerId}
				</td>
				<td>
					${dto.cardCount }
				</td>
				<td>
					${dto.cardOrg}
				</td>
				<td>
					${dto.succesCount}
				</td>
				<td>
					<fmt:formatDate value="${dto.startDate}" type="both"/> 
				</td>
				<td>
					<fmt:formatDate value="${dto.endDate}" type="both"/>
				</td>
				<td>
					${dto.estimatedTime}
				</td>
				<td>
					${dto.operator}
				</td>
				<td>
					<c:if test="${dto.status==1}">
						进行中
					</c:if>            
					<c:if test="${dto.status==2}">
						已完成
					</c:if>            
				</td>
				<td>
					<a href="manualTransSub.do?method=tradeQuery&batchNumber=${dto.batchNumber}">交易查询</a>&nbsp;&nbsp;
					<c:if test="${dto.status==1}">
					<a href="manualTransSub.do?method=tradeShutdown&batchNumber=${dto.batchNumber}">停止</a>
					</c:if>  
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

 <script type="text/javascript">
	function query(pageNo,totalCount,pageSize) {
		var pars = $("#mainfrom").serialize()+"&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
		//alert(pars);
		$.ajax({
			type: "POST",
			url: "${ctx}/manualTransSub.do?method=queryManualTran",
			data: pars,
			success: function(result) {
				$('#resultListDiv').html(result);
			}
		});
	}

	
</script>
<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>