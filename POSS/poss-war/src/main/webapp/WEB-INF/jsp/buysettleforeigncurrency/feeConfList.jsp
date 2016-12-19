<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<body>
		<table class="tablesorter" border="0" cellpadding="0" cellspacing="1">
			<thead>
				<tr>
					<th>会员号</th>
					<th>保底值</th>
					<th>固定手续费</th>
					<th>百分比手续费</th>
					<th>封顶值</th>
					<th>创建时间</th>
					<th>更新时间</th>
					<th>操作员</th>
					<th>操作</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="bs" items="${result}" varStatus="v">
					<c:choose>
	       <c:when test="${v.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>	
						<td>${bs.data.partnerId}</td>
						<td>
							${bs.data.minimumValue/1000}&nbsp;${bs.data.currencyCode}
						</td>
						<td>
							${bs.data.fixedFee/1000}&nbsp;${bs.data.currencyCode}
						</td>
						<td>
							${bs.data.percentageFee/100}%&nbsp;
						</td>
						<td>
							${bs.data.capValue/1000}&nbsp;${bs.data.currencyCode}
						</td>
						<td>
							${bs.data.createDateS}
						</td>
						<td>
							${bs.data.updateDateS}
						</td>
						<td>
							${bs.data.operator}
						</td>
						<td>
						<a href="#"  onclick="update('${bs.data.partnerId}','${bs.data.minimumValue/1000}','${bs.data.fixedFee/1000}','${bs.data.percentageFee/100}','${bs.data.capValue/1000}','${bs.data.status}','${bs.data.id}');">修改</a>
							<a href="#" onclick="del(${bs.data.id},${bs.data.status })">删除</a>
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>	
		<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>
	</body>
</html>
	<script type="text/javascript">
	function del(id,status){
		if (!confirm("确认删除？")) {
			return;
		 }
		window.location.href="${ctx}/settleForeignCurrencyFeeConf.do?method=delete&id="+id+"&status="+status;
	}
	
	function query(pageNo, totalCount){
		var pars = $("#mainfrom").serialize() + "&pageNo=" + pageNo
		+ "&totalCount=" + totalCount;
		$.ajax({
			type : "POST",
			url : "./settleForeignCurrencyFeeConf.do?method=list",
			data : pars,
			success : function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
	}
</script>