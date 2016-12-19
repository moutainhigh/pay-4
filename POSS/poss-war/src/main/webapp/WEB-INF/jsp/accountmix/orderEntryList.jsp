<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<script type="text/javascript">
		$(document).ready(function(){
			 $("#userTable").tablesorter({
				 headers: {
				 	5: {sorter: false}
				 }});      
		});
</script>
</head>
<body>


<c:if test='${error != null}'> 
	<font color="#FF0000">${error}</font>
</c:if>


<c:if test='${error == null}'>


<div class="tableList">




<table class="tablesorter" border="0" cellpadding="0" cellspacing="1" >
<thead> 
  <tr class="tabT">
    <th class="tabTitle" scope="col">科目账户</th>
    <th class="tabTitle" scope="col">关联订单号</th>
    <th class="tabTitle" scope="col">交易号</th>
    <th class="tabTitle" scope="col">币种</th>
    <th class="tabTitle" scope="col">金额(元)</th>
    <th class="tabTitle" scope="col">支付服务码</th>
    <th class="tabTitle" scope="col">借贷</th>
    <th class="tabTitle" scope="col">建立时间</th>
    <th class="tabTitle" scope="col">记账时间</th>
  </tr>
  	</thead> 
  	
  <c:forEach items="${resultList}" var="entry" varStatus = "personalStatus">
		<c:choose>
	       <c:when test="${personalStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>  
    <td>&nbsp;${entry.acctCode}</td>
     <td>&nbsp;${entry.relatedOrderId}</td>
     <td>&nbsp;${entry.orderId}</td>
     <td>&nbsp;${entry.currencyCode}</td>
     <td>&nbsp;${entry.value}</td>
     <td>&nbsp;${entry.psCode}</td>
     <td>&nbsp;
      <c:if test='${entry.crdr == "1"}'> 借</c:if>
      <c:if test='${entry.crdr == "2"}'> 贷</c:if>	     
	 </td>
     <td>&nbsp;<fmt:formatDate value="${entry.createdate}" type="both"/> </td>
     <td>&nbsp;<fmt:formatDate value="${entry.postDate}" type="both"/> </td>
     
  	</tr>
  </c:forEach>
</table>

</div>
</c:if>
</body>
</html>
