<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
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
}
</style>
<script type="text/javascript">
		$(document).ready(function(){
			 $("#userTable").tablesorter({
				 headers: {
				 	5: {sorter: false}
				 }});      
		});
</script>
<title>科目余额明细下载</title>
</head>
<body>

<c:if test='${error != null}'> 
	<font color="#FF0000">${error}</font>
</c:if>


<c:if test='${error == null}'>

<c:if test="${!empty page.result}">
	
	<a href="${ctx}/if_poss_query/subjectBalanceDetailQuery.do?method=excDownLoad"></a>
</c:if>

<div class="tableList">

<c:if test='${balanceInfo != null}'>
	<font color="#FF0000">科目账户：</font>${balanceInfo.acctCode} &nbsp; &nbsp;
	<font color="#FF0000">科目名称：</font>${balanceInfo.acctName} &nbsp; &nbsp;
    </br>
	<font color="#FF0000">期初借方余额：</font>${balanceInfo.beginningDrBalance}元 &nbsp; &nbsp;
	<font color="#FF0000">期初贷方余额：</font>${balanceInfo.beginningCrBalance}元 &nbsp; &nbsp;
	<font color="#FF0000">期末借方余额：</font>${balanceInfo.endingDrBalance}元 &nbsp; &nbsp;
	<font color="#FF0000">期末贷方余额：</font>${balanceInfo.endingCrBalance}元 &nbsp; &nbsp;
	<font color="#FF0000">本期借方发生额：</font>${balanceInfo.drAmount}元 &nbsp; &nbsp;
	<font color="#FF0000">本期贷方发生额：</font>${balanceInfo.crAmount}元 &nbsp; &nbsp;
</c:if>


<table class="tablesorter" border="0" cellpadding="0" cellspacing="1" >
<thead> 
  <tr class="tabT">
    <th class="tabTitle" scope="col">科目账户</th>
     <th class="tabTitle" scope="col">关联交易号</th>
     <th class="tabTitle" scope="col">金额(元)</th>
    <th class="tabTitle" scope="col">借贷</th>
    <th class="tabTitle" scope="col">记账时间</th>
    
  </tr>
  	</thead> 
  	
  <c:forEach items="${page.result}" var="entry" varStatus = "personalStatus">
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
      <td>&nbsp;${entry.value}</td>
      <td>&nbsp;
      <c:if test='${entry.crdr == "1"}'> 借</c:if>
      <c:if test='${entry.crdr == "2"}'> 贷</c:if>	     
	     </td>
	     
		     
     <td>&nbsp;<fmt:formatDate value="${entry.postDate}" type="both"/> </td>
     
  	</tr>
  </c:forEach>
  
</table>
<li:pagination methodName="userQuery" pageBean="${page}" sytleName="black2"/>


</div>



</c:if>
</body>
</html>
