<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
 <form action="" method="post" name="resultForm">
 	
 </form>
<body>
不匹配交易列表
<table id="userTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead> 
		<tr>     
				<th>序号</th>     
				<th>交易号</th>     
				<th>银行名称</th>     
				<th>开户行名称</th>     
				<th>银行账户</th>     
				<th>汇款金额</th>
				<th>收款人</th>
				<th>交易备注</th>
				<th><font color="red"> 银行订单号</font></th>
				<th><font color="red"> 银行账户</font></th>
				<th><font color="red"> 汇款金额</font></th>
				<th><font color="red"> 收款人</font></th>
				<th>原因</th>
			</tr> 
	
	</thead>
	<tbody>
	<c:forEach items="${page.result}" var="dto" varStatus="status">
		<tr >
			<td ><c:out value="${status.count}" /></td>
			<td >${dto.rightOrderSeq}</td>
			<td >
				 <li:codetable  selectedValue="${dto.bankCode}" style="desc" attachOption="true" codeTableId="fundOutBankTable"></li:codetable>
			</td>
			<td >${dto.rightBankBranch}</td>
			<td >${dto.rightBankAcct}</td>
			<td >
				<fmt:formatNumber value="${dto.rightAmount/1000}" pattern="#,##0.00"  />
			</td>
			<td >${dto.rightMemberName }</td>
			<td >${dto.leftRemark }</td>
			<td ><font color="red">${dto.leftBackSeq }</font></td>
			<td ><font color="red">${dto.leftBankAcct }</font></td>
			<td ><font color="red">
				<fmt:formatNumber value="${dto.leftAmount/1000}" pattern="#,##0.00"  />
			</font></td>
			<td ><font color="red">${dto.leftMemberName }</font></td>
			<td >${dto.leftFailureReason}</td>
		</tr>
	</c:forEach>
	</tbody> 
</table>
<li:pagination methodName="queryDisMatch" pageBean="${page}" sytleName="black2"/>
</body>
