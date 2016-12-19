<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="li_new" uri="page"%>
<%@ page import="com.hnapay.fundout.util.AmountUtils"%>

<!-- 
<form action="" method="post" name="listFrom">
</form>
 -->
<table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
		<tr>
			<th>商户号</th>
			<th>提现日期</th>
			<th>提现金额</th>
			<th>提现状态</th>
			<th>结算日期</th>
			<th>操作</th>
		</tr>
	</thead>
	
	<tbody>
		<c:forEach items="${page.result}" var="dto" varStatus="index">
			<c:choose>
	       <c:when test="${index.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
				<td>
					${dto.partnerId}
				</td>
				<td>
					<fmt:formatDate value="${dto.createDate}" type="date"/>
				</td>
				<td>
					${dto.amount}
				</td>
				<td>
					${dto.statusName}
				</td>
				<td>
					<fmt:formatDate value="${dto.clearDate}" type="date"/>
				</td>
				
				<td>
					<c:if test="${dto.status ==1}"><a href="javascript:approval('${dto.id }')">审核</a> &nbsp;&nbsp;&nbsp;
					</c:if>
					<c:if test="${dto.status ==3}">
					<a href="javascript:complete('${dto.id }')">完成</a>	
					</c:if>
				</td>
			</c:forEach>
	</tbody>
</table>

<li_new:page methodName="search" pageBean="${page}" sytleName="black2"/>
 <script type="text/javascript">
	 $(document).ready(function(){
		 $("#sorterTable").tablesorter({
			 headers: {
				0: {sorter: false},
				5: {sorter: false}
			}
		});
	 }); 
	
</script>