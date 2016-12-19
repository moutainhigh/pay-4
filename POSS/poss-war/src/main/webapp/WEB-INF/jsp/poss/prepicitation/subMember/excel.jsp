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
	    <th class="tabTitle" align="center">日期</th>
	  	<th class="tabTitle" align="center">会员类型</th>
	  	<th class="tabTitle" align="center">会员编号</th>
	  	<th class="tabTitle" align="center">会员名称</th>
		<th class="tabTitle" align="center">所属分子公司</th>
	    <th class="tabTitle" align="center">会员等级</th>
	    <th class="tabTitle" align="center">实际入款量（元）</th>
	    <th class="tabTitle" align="center">出款量（元）</th>
	    <th class="tabTitle" align="center">资金沉淀量（元）</th>	    
	  </tr>
	</thead>
			
	<tbody>
		<c:forEach items="${result}" var="dto">
		 <tr class="trForContent1 tr_hover">
		  	<td class="excel_txt">${dto.transDate}</td>
		    <td class="excel_txt">
		      <c:choose>
		    	 <c:when test="${dto.memberType == '1'}">    
		    	 	个人
		    	 </c:when>
		    	 <c:when test="${dto.memberType == '2'}"> 
		    		 企业
		    	 </c:when>
		     </c:choose>
		    </td>
		    <td class="excel_txt">${dto.memberNo}</td>
		    <td class="excel_txt">${dto.memberName}</td>
		    <c:choose>
		       <c:when test="${dto.innerName == null}">    
				    <td align="center">&nbsp;/</td>		    	 	
		       </c:when>
		       <c:otherwise>
				    <td class="excel_txt">
				    	${dto.innerName} 
				     </td>
		       </c:otherwise>
		    </c:choose> 
		    <td class="excel_txt">${dto.serviceLevelName} </td>			  	 
		     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'"><fmt:formatNumber value="${dto.realFiAmount  / 1000}" pattern="0.00"/></td>
		     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'" ><fmt:formatNumber value="${dto.realFoAmount  / 1000}" pattern="0.00"/></td>
		     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'" ><fmt:formatNumber value="${dto.cpAmount  / 1000}" pattern="0.00"/></td>
		 </tr>
		</c:forEach>
	</tbody>
	</table>
</body>

