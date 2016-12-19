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

		function exportFile(totalCount) {
			if(totalCount <= 0){
				alert("无结果集,不能下载！");
			}
			else{
				if(!validateQuery()){
					return false;
				}
				document.getElementById("form1").method="POST";
				document.getElementById("form1").action="${ctx}/suspectOrderSearch.do?method=download";
				document.getElementById("form1").submit();
			}
        }
</script>
</head>
<body>
	<c:if test='${error != null}'> 
		<font color="#FF0000">${error}</font>
	</c:if>
	<c:if test='${error == null}'>
	<div class="tableList">
	<a href="javascript:onClick=exportFile(${page.totalCount});">下载查询结果</a>
	<table class="tablesorter" border="0" cellpadding="0" cellspacing="1" >
	<thead>
	  <tr class="tabT">
	    <th class="tabTitle" scope="col">时间</th>
	    <th class="tabTitle" scope="col">金额</th>
	    <th class="tabTitle" scope="col">支付平台订单号</th>
	    <th class="tabTitle" scope="col">付款会员号</th>
	    <th class="tabTitle" scope="col">收款会员号</th>
	    <th class="tabTitle" scope="col">备注</th>
	    <th class="tabTitle" scope="col">下单IP</th>
	    <th class="tabTitle" scope="col">收银IP</th>
	    <th class="tabTitle" scope="col">下单Refer</th>
	    <th class="tabTitle" scope="col">收银Refer</th>
	  </tr>
	</thead> 
	  	
	  <c:forEach items="${page.result}" var="dto">
	  	<tr>  
		    <td>&nbsp;<fmt:formatDate value="${dto.createDate}" pattern="yyyy-MM-dd HH:mm:ss"/></td></td>
		    <td>&nbsp;<fmt:formatNumber value="${dto.orderAmount}" pattern="#,##0.00"/></td>
		    <td>&nbsp;${dto.tradeOrderNo}</td>
			<td>&nbsp;${dto.payer}</td>
		    <td>&nbsp;${dto.partnerId}</td>
		    <td>
		    	&nbsp;<c:if test="${dto.status eq 0}">请求IP在黑名单中</c:if>
			  	<c:if test="${dto.status eq 1}">IP校验失败</c:if>
				  <c:if test="${dto.status eq 2}">referer校验失败</c:if>
			 </td>
		    <td>&nbsp;${dto.requestIp}</td>
		    <td>&nbsp;${dto.payerIp}</td>
		    <td>&nbsp;${dto.partnerUrl}</td>
		    <td>&nbsp;${dto.requestUrl}</td>
	  	</tr>
	  </c:forEach>
	</table>
	</div>
	<li:pagination methodName="userQuery" pageBean="${page}" sytleName="black2"/>
	</c:if>
</body>
</html>
