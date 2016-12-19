<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>
 <style type="text/css"> 
.excel_txt {
	padding-top: 1px;
	padding-right: 1px;
	padding-left: 1px;
	mso-ignore: padding;
	color: black;
	font-size: 11.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: 宋体;
	mso-generic-font-family: auto;
	mso-font-charset: 134;
	mso-number-format: "\@";
	text-align: general;
	vertical-align: middle;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: nowrap;
}
</style> 
<table id="userTable" class="tablesorter" border="1" cellpadding="0" cellspacing="1">
	<thead> 
		<tr >
			<th>序号</th>
			<th>交易流水号</th>
			<th>会员号</th>
			<th>会员名称</th>
			<th>银行名称</th>
			<th>开户行名称</th>
			<th>银行账户</th>
			<th>汇款金额(元)</th>
			<th>收款人姓名</th>
			<th>交易备注</th>
			<th>交易状态</th>
			<th>失败原因</th>
			<th>复核结果</th>
		</tr>
	</thead>
	<tbody> 
		<c:forEach items="${result}" var="dto" varStatus="status">
		<input type="hidden" value="${dto.amount}" id="chooseAmount${status.count}" />
			<tr>
				<td ><c:out value="${status.count}" /></td>
				<td class="excel_txt">${dto.sequenceId}</td>
				<td>${dto.memberCode}</td>
				<td>${dto.memberName}</td>
				<td>${dto.bankName}</td>
				<td>${dto.bankBranch}</td>
				<td class="excel_txt">${dto.bankAcct}</td>
				<td><fmt:formatNumber value="${dto.amount/1000}" pattern="#,##0.00"  />&nbsp;</td>
				<td>${dto.accountName}</td>
				<td>${dto.orderRemarks}</td>
				<td>
					<c:if test="${ dto.status == 14}"> 出款失败 </c:if>
					<c:if test="${ dto.status == 12}"> 出款成功 </c:if>
					<c:if test="${ dto.status == 13}"> 出款处理中 </c:if>
				</td>
				<td>${dto.failReason}</td>
				<td>${dto.reAudit}</td>
			</tr>
		</c:forEach>
	</tbody>
		<tr align="left">
			<td colspan="15">交易总金额<span id="chooseAmount"><fmt:formatNumber value="${countAmount/1000 }" pattern="#,##0.00"  /></span>元,共<span id="chooseCount">${pageSize }</span>笔</td>
		</tr>
</table>
