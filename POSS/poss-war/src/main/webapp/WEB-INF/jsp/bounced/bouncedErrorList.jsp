<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
function back() 
{ 
var url="bounced-register.do?method=batchinit";
parent.closePage(url);
} 
</script>
<body>
<table id="userTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead> 
		<tr >
			<th >序号</th>
			<th >档案号</th>
			<th >交易日期</th>
			<th >授权码</th>
			<th >银行卡</th>
			<th >拒付时间</th>
			<th >银行拒付金额</th>
			<th >银行拒付币种</th>
			<th >拒付原因</th>
			<th >最晚回复日期</th>
			<th >解析错误原因</th>
		</tr>
	</thead>
	<tbody> 
		<c:forEach items="${list}" var="dto" varStatus="status">
			<c:choose>
	       <c:when test="${status.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
				<td>${ status.index + 1}</td>
				<td>${dto.refNo}</td>
				<td>${dto.stranDate}</td>
				<td>${dto.authorisation}</td>
				<td>${dto.cardholderCardno}</td>
				<td>${dto.cpdDate}</td>
				<td>${dto.sbankAmount}</td>
				<td>${dto.bankCurrencyCode}</td>
				<td>${dto.reasonCode}</td>
				<td><date:date value="${dto.lastDate}"/></td>
				<td>${dto.remark}</td>
			</tr>
		</c:forEach>
	</tbody>
	<input id="back" type="button" onclick="javascript:history.back(-1);";" value="返回" class="button2" />
			   
</table>
</body>


<script type="text/javascript" language="javascript">
</script>