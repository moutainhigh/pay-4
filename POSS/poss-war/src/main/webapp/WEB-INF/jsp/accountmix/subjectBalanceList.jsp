<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<link rel="stylesheet" href="${ctx}/css/jq.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="${ctx}/css/style.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="./css/main.css">
<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>
<script src="./js/common.js"></script>
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

<c:if test="${!empty page.result}">
	<a href="${ctx}/if_poss_query/subjectBalanceQuery.do?method=downloadExcel">下载当前页面表格</a>
	<a href="${ctx}/if_poss_query/subjectBalanceQuery.do?method=downloadOverExcel">下载5000条记录内的表格</a>	
</c:if>

<div class="tableList">

<table class="tablesorter" border="0" cellpadding="0" cellspacing="1" >
<thead> 

<tr class="tabT">
    <th class="tabTitle" scope="col" rowspan="2" align="center">科目级别</th>
    <th class="tabTitle" scope="col" rowspan="2" align="center"">科目账户</th>
    <th class="tabTitle" scope="col" rowspan="2" align="center">科目名称</th>
    <th class="tabTitle" scope="col" colspan="2" align="center">期初</th>
    <th class="tabTitle" scope="col" colspan="2" align="center">本期发生</th>
    <th class="tabTitle" scope="col" colspan="2" align="center">期末</th>
   </tr>
   <tr>

    <th class="tabTitle" scope="col"  >借方余额(元)</th>
    <th class="tabTitle" scope="col"  >贷方余额(元)</th>
    <th class="tabTitle" scope="col" > 借方发生额(元)</th>
    <th class="tabTitle" scope="col"  >贷方发生额(元)</th>
    <th class="tabTitle" scope="col"  >借方余额(元)</th>
    <th class="tabTitle" scope="col"  >贷方余额(元)</th>

  </tr>


  	</thead>   
  	<c:forEach items="${page.result}" var="accountBalance" varStatus = "index">
	<c:choose>
       <c:when test="${index.index%2==0}">
             <tr class="trForContent1">
       </c:when>
       <c:otherwise>
             <tr class="trForContent2">
       </c:otherwise>
	</c:choose>	
      <c:if test="${index.count==1}">
             <td rowspan="${fn:length(page.result)}">&nbsp;
             <c:if test="${subjectLevel=='1'}">
           		 一级
             </c:if>
              <c:if test="${subjectLevel=='2'}">
           		二级
             </c:if>
             <c:if test="${subjectLevel=='3'}">
           		 三级
             </c:if>
             <c:if test="${subjectLevel=='4'}">
           		 四级
             </c:if>
             <c:if test="${subjectLevel=='5'}">
           		五级
             </c:if>
             </td>
      </c:if>
      
   	 <td>&nbsp;${accountBalance.acctCode}</td>
     <td>&nbsp;${accountBalance.acctName}</td>

     <td>&nbsp;${accountBalance.beginningDrBalance}</td>
     <td>&nbsp;${accountBalance.beginningCrBalance}</td>
     
     <td>&nbsp;${accountBalance.drAmount}</td>
     <td>&nbsp;${accountBalance.crAmount}</td>
     
     <td>&nbsp;${accountBalance.endingDrBalance}</td>
     <td>&nbsp;${accountBalance.endingCrBalance}</td>
       

  </tr>
  </c:forEach>
  
</table>

</div>



</c:if>

<li:pagination methodName="userQuery" pageBean="${page}" sytleName="black2"/>

</body>
</html>
