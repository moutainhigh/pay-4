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
	  	<th>
			<input type="checkbox" value="0" name="checkAll" id="checkAll" onclick="selectAll();" />全选
		</th>
	  	<th class="tabTitle" scope="col">交易号</th>
	    <th class="tabTitle" scope="col">网关名称</th>
	    <th class="tabTitle" scope="col">商户账号</th>
	    <th class="tabTitle" scope="col">银行订单号</th>
	    <th class="tabTitle" scope="col">商家订单号</th>
	    <th class="tabTitle" scope="col">订单金额(元)</th>
	    <th class="tabTitle" scope="col">退款金额(元)</th>
	    <th class="tabTitle" scope="col">手续费</th>
	    <th class="tabTitle" scope="col">原交易时间</th>
	    <th class="tabTitle" scope="col">订单结束时间</th>
	  </tr>
	</thead> 
	  <c:forEach items="${page.result}" var="dto">
	  <tr>
	  	<td>
			<input type="checkbox" name="wkKey" value="${dto.orderAmount}" id="id" name="choose" onclick="selectAllcheckBoxunchecked(this);" /> 
		</td>
	  	 <td>&nbsp;${dto.sequenceId}</td> 
	     <td>&nbsp;${dto.gatewayName}</td>
	     <td>&nbsp;${dto.partnerId}</td>
	     <td>&nbsp;${dto.serialNo}</td>
	     <td>&nbsp;${dto.orderId}</td>
	     <td>&nbsp;<fmt:formatNumber value="${dto.orderAmount/1000}" pattern="#,##0.00"/></td>
	     <td>&nbsp;<fmt:formatNumber value="${dto.refundAmount/1000}" pattern="#,##0.00"/></td>
	     <td>&nbsp;<fmt:formatNumber value="${dto.fee/1000}" pattern="#,##0.00"/></td>
		 <td>&nbsp; ${dto.createDate} </td>
	     <td>&nbsp; ${dto.updateDate} </td>
	  	</tr>
	  </c:forEach>
	</table>
	<div id="showSum"> 选中订单金额： 0.00</div>
	<li:pagination methodName="userQuery" pageBean="${page}" sytleName="black2"/>
	</div>
	</c:if>
</body>
</html>
