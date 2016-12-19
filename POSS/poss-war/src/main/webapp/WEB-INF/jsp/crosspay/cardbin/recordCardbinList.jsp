<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<form action="" method="post" name="listFrom"></form>

<table align="center" class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
	<thead>
		<tr class="" align="center" valign="middle">
			<th class=""  >商户会员号</th>
			<th class=""  >卡号1</th>
			<th class=""  >卡号2</th>
			<th class=""  >类型</th>
			<th class=""  >创建时间</th>
			<th class=""  >操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${rateList.result}" var="cardFilter"
			varStatus="index">
			<c:choose>
	       <c:when test="${index.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>		

				<td class="" align="left" >${cardFilter.partnerId}&nbsp;</td>
				<td class="" align="left" >${cardFilter.startCardno}&nbsp;</td>
				<td class="" align="left" >${cardFilter.endCardNo}&nbsp;</td>
				<td class="" align="left" >
				<c:if test="${cardFilter.cardFilterType == 0}">卡号</c:if>
				 <c:if test="${cardFilter.cardFilterType == 1}">卡段</c:if>&nbsp;</td>
				<td class="" align="left" ><fmt:formatDate value="${cardFilter.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;</td>
				<td class="" align="left" ><a href="#" onclick="removeCardbin('${cardFilter.id}');">删除</td>
			</tr>
		</c:forEach>
	</tbody>
</table>

<script type="text/javascript">
	function removeCardbin(id) {
		$.ajax({
			type : "POST",
			url : "${ctx}/cardbin/cardfilter.do?method=delete&id=" + id,
			success : function(result) {
				alert('操作成功');
				queryData();
			}
		});
	}
</script>