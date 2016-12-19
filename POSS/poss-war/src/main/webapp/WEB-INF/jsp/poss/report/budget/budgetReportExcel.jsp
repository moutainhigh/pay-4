<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<head><meta charset=utf-8 />
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
</head>
<body>
	<table width="800" border="1">
	<thead>
	  <tr class="tabT">
	  	<th class="tabTitle" align="center" scope="col">交易日期</th>
	  	<th class="tabTitle" align="center" scope="col">分子公司</th>
	  	<th class="tabTitle" align="center" scope="col">收入(元)</th>
	    <th class="tabTitle" align="center" scope="col">交易量(元)</th>
	  	<th class="tabTitle" align="center" scope="col">银行手续费成本(元)</th>
	  </tr>
	</thead>
			
	<tbody>
		<c:forEach items="${result}" var="dto">
		 <tr class="trForContent1 tr_hover">
		  	 <td class="excel_txt">${dto.txnDate}</td>
		  	 <td class="excel_txt">${dto.innerMemberName}</td>
		     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'"><fmt:formatNumber value="${dto.income/1000}" pattern="0.00"/></td>
		     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'" ><fmt:formatNumber value="${dto.txnAmount/1000}" pattern="0.00"/></td>
		     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'" ><fmt:formatNumber value="${dto.bankFee/1000}" pattern="0.00"/></td>
		 </tr>
		</c:forEach>
	</tbody>
	</table>
</body>

