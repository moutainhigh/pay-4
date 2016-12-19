<!DOCTYPE HTML>
<#import "/spring.ftl" as sp />
<html>
<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<style type="text/css">
.float{float:left;clear:right;}
</style>
<head>
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
	float:left;
	clear:right;
}
</style> 
<meta charset="utf-8">
<title>拒付欺诈比例报表</title>

</head>
<body>
<table width="5000px" border="1">
<div style="width:5000px; height:225px; overflow:hidden;">
<c:forEach items="${map}" var="m">
<div class="float" > 
<table id="userTable" class="tablesorter" border="1" cellpadding="0" cellspacing="1">

	<thead>
	<tr>
	<th colspan="2">日期</th>
	<th colspan="7" align="center">
	<c:if test="${m.key==enddate}">
	${date}
	</c:if>
	<c:if test="${m.key!=enddate}">
	${m.key}
	</c:if>
	</tr>
		<tr>
			<c:if test="${type=='0'}">
			<th >商户号</th>
			<th >商户名称</th>
			</c:if>
			<c:if test="${type=='1'}">
			<th >二级渠道号</th>
			<th >渠道类别</th>
			</c:if>
			<c:if test="${type=='3'}">
			<th >所属渠道</th>
			<th >渠道类别</th>
			</c:if>
			<th >拒付笔数</th>
			<th >总订单数</th>
			<th >拒付率</th>
			<th >欺诈金额</th>
			<th >总交易额</th>
			<th >欺诈金额比例</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${m.value}" var="dto" varStatus="status">
			<tr>
			<c:if test="${type=='0'}">
			<td class="excel_txt">${dto.partnerId}</td>
			<td>${dto.partnerName}</td>
			</c:if>
			<c:if test="${type=='1'}">
			<td class="excel_txt">${dto.merchantNo}</td>
			</c:if>
			<td>${dto.cardOrg}</td>
			<c:if test="${type=='3'}">
			<td>
					<c:choose>
					<c:when test="${dto.orgCode=='10076001'}">卡司</c:when>
					<c:when test="${dto.orgCode=='10079001'}">中银MOTO</c:when>
					<c:when test="${dto.orgCode=='10080001'}">中银</c:when>
					<c:when test="${dto.orgCode=='10003001'}">中国银行</c:when>
					<c:when test="${dto.orgCode=='10002001' }">农业银行</c:when>
					<c:when test="${dto.orgCode=='10075001'}">CREDORAX</c:when>
					<c:when test="${dto.orgCode=='10077001'}">Adyen</c:when>
					<c:when test="${dto.orgCode=='10077002'}">Belto</c:when>
					<c:when test="${dto.orgCode=='10077003'}">Cashu</c:when>
					<c:when test="${dto.orgCode=='10081016'}">前海万融</c:when>
					<c:otherwise>未知机构</c:otherwise>
					</c:choose>
			</td>
			<td>${dto.cardOrg}</td>
			</c:if>
				<td>${dto.bouncedCount}</td>
				<td>${dto.totalCount}</td>
				<td><fmt:formatNumber value="${dto.bouncedRate*100}"
						pattern="#,##0.00%" /></td>
				<td><fmt:formatNumber value="${dto.fraudAmount/1000}"
						pattern="#,##0.00" /></td>
				<td><fmt:formatNumber value="${dto.totalAmount/1000}"
						pattern="#,##0.00" /></td>
				<td><fmt:formatNumber value="${dto.fraudRate*100}"
						pattern="#,##0.00%" /></td>
			</tr>
		</c:forEach>
	</tbody>
</table>
</div>
</c:forEach>
</div>
</table>
</body>
</html>