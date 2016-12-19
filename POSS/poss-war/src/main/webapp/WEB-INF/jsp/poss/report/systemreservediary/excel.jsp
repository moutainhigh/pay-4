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
	  	<th class="tabTitle" align="center" scope="col">日期</th>
	    <th class="tabTitle" align="center" scope="col">企业会员账户余额(元)</th>
	    <th class="tabTitle" align="center" scope="col">个人会员账户余额(元)</th>
	    <th class="tabTitle" align="center" scope="col">应收未收金额(元)</th>
	    <th class="tabTitle" align="center" scope="col">应付未付金额(元)</th>
	    <th class="tabTitle" align="center" scope="col">应退未退金额(元)</th>
	    <th class="tabTitle" align="center" scope="col">系统备付金余额(元)</th>
	  </tr>
	</thead>
			
	<tbody>
		<c:forEach items="${result}" var="dto">
		 <tr class="trForContent1 tr_hover">
		  	 <td class="excel_txt">${dto.countDateStr}</td>
		     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'"><fmt:formatNumber value="${dto.enterpriseAccBalance/1000}" pattern="0.00"/></td>
		     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'" ><fmt:formatNumber value="${dto.individualAccBalance/1000}" pattern="0.00"/></td>
		     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'" ><fmt:formatNumber value="${dto.uncollectedAmt/1000}" pattern="0.00"/></td>
		 	 <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'"><fmt:formatNumber value="${dto.unpaidAmt/1000}" pattern="0.00"/></td>
		     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'" ><fmt:formatNumber value="${dto.noRefundAmount/1000}" pattern="0.00"/></td>
		     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'" ><fmt:formatNumber value="${dto.systemReserveBalance/1000}" pattern="0.00"/></td>
		 </tr>
		</c:forEach>
	</tbody>
	</table>
</body>

