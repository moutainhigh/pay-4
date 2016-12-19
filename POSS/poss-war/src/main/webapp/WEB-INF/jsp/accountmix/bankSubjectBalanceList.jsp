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

<c:if test="${!empty resultList}">
	<a href="${ctx}/if_poss_query/bankSubjectBalanceQuery.do?method=downloadExcel">下载此表格</a>
</c:if>

<div class="tableList">

<table class="tablesorter" border="0" cellpadding="0" cellspacing="1" >
<thead> 
  <tr class="tabT">
    <th class="tabTitle" scope="col">渠道行（全称）</th>    
    <th class="tabTitle" scope="col">出账金额(元)</th>
    <th class="tabTitle" scope="col">银行余额(元)</th>
  </tr>
  	</thead> 
  	
  <c:forEach items="${resultList}" var="accountBalance">
  <tr>     
  
   	 <td>&nbsp;${accountBalance.acctName}</td>
     <td>&nbsp;${accountBalance.crSubDr}</td>
     <td>&nbsp;${accountBalance.endingDrBalance}</td>
     
     
  </tr>
  </c:forEach>
  
</table>

</div>



</c:if>
</body>
</html>
