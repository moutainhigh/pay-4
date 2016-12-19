<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<body>
		<table class="tablesorter" border="0" cellpadding="0" cellspacing="1" >
			<thead>
				<tr>
					<th>会员号</th>
					<th>保底值</th>
					<th>固定手续费</th>
					<th>百分比手续费</th>
					<th>封顶值</th>
					<th>小额服务费基线</th>
					<th>小额服务费</th>
					<th>创建时间</th>
					<th>更新时间</th>
					<th>操作员</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="acc" items="${resultList}" varStatus="v">
					<c:choose>
	       <c:when test="${v.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
						<td>${acc.partnerId}</td>
						<td>		
									<fmt:formatNumber type="number" value="${acc.minimumValue/1000}" maxFractionDigits="2"/>  CNY
						</td>
						<td><fmt:formatNumber type="number" value="${acc.fixedFee/1000}" maxFractionDigits="2"/>   CNY</td>
						<td><fmt:formatNumber type="number" value="${acc.percentageFee/100}" maxFractionDigits="2"/> %</td>
						<td><fmt:formatNumber type="number" value="${acc.capValue/1000}" maxFractionDigits="2"/> CNY</td>
						<td><fmt:formatNumber type="number" value="${acc.smallBaselin/1000}" maxFractionDigits="2"/>  CNY</td>
						<td><fmt:formatNumber type="number" value="${acc.smallServiceFee/1000}" maxFractionDigits="2"/>  CNY</td>
						<td><fmt:formatDate value="${acc.createDate}" pattern="yyyy-MM-dd"/></td>
						<td><fmt:formatDate value="${acc.updateDate}" pattern="yyyy-MM-dd"/></td>
						<td>${acc.operator}</td>
						<td>
							<a href="#" onclick="deleteConfig('${acc.feeConfigNo}');">删除</a>
							<a href="#"  onclick="updateConfig('${acc.partnerId}','${acc.minimumValue/1000}','${acc.fixedFee/1000}','${acc.percentageFee/100}','${acc.capValue/1000}','${acc.smallBaselin/1000}','${acc.smallServiceFee/1000}','${acc.feeConfigNo }');"   >修改</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>	
		<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>
	</body>
</html>
<script type="text/javascript">

function query(pageNo, totalCount){
	var pars = $("#mainfrom").serialize() + "&pageNo=" + pageNo
	+ "&totalCount=" + totalCount;
	$.ajax({
		type : "POST",
		url : "./feeConfigKF.do?method=list",
		data : pars,
		success : function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
}
</script>