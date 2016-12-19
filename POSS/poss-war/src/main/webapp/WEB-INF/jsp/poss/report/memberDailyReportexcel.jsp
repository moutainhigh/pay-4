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
		  	<th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" >日期</th>
		  	<th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" >用户类型</th>
		  	<th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" >会员编号</th>
		  	<th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" >会员名称</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" >是否包量 </th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" >入款流量（元）</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" >出款流量（元）</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" >入款手续费收入（元）</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" >出款手续费收入（元）</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" >入款成本（元）</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" >出款成本（元）</th>
		  </tr>
	</thead>
			
	<tbody>
		<c:forEach items="${result}" var="dto">
		 <tr class="trForContent1 tr_hover">
	  	 <td class="excel_txt" style="border-right:2px solid #aaa" >
	  		<fmt:formatDate value="${dto.reportDate}" type="date"/>
	     </td>
	     <td class="excel_txt" style="border-right:2px solid #aaa">${dto.memberTypeStr}</td>
	     <td class="excel_txt" style="border-right:2px solid #aaa">${dto.memberCode}</td>
	     <td class="excel_txt" style="border-right:2px solid #aaa">${dto.name}</td>
	     <td class="excel_txt" style="border-right:2px solid #aaa">${dto.isPackageStr}</td>
	     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'">
	     	<fmt:formatNumber value="${dto.fiFlow/1000}" pattern="0.00"/>
	     </td>
	     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'">
	     	<fmt:formatNumber value="${dto.foFlow/1000}" pattern="0.00"/>
	     </td>
	     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'">
	     	<fmt:formatNumber value="${dto.fiFee/1000}" pattern="0.00"/>
	     </td>
	     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'">
	     	<fmt:formatNumber value="${dto.foFee/1000}" pattern="0.00"/>
	     </td>
	     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'">
	     	<fmt:formatNumber value="${dto.fiCost/1000}" pattern="0.00"/>
	     </td>
	     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'">
	     	<fmt:formatNumber value="${dto.foCost/1000}" pattern="0.00"/>
	     </td>
	  	</tr>
		</c:forEach>
	</tbody>
	</table>
</body>

