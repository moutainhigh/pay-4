<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">
$(function(){
         $("#pageUrl").click(function(){
			 document.sendForm.action = $("#url_1").html();
			 if(confirm("确定要发送前台通知吗？")) {
				 $("#noticeType").val('2');
				 document.sendForm.submit();
				 document.recordform.submit();
			 }
         });
		 $("#bgUrl").click(function(){
			 document.sendForm.action = $("#url_2").html();
			 if(confirm("确定要发送后台通知吗？")) {
			 $("#noticeType").val('1');
			 document.sendForm.submit();
			 document.recordform.submit();
			 }
         });
     });
</script>

<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">重发通知</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>

<table width="30%" border="1" cellspacing="0" cellpadding="0"
	align="center" height="21">
	<tr><th colspan="3" align="center">该笔订单的重发记录</th></tr>
	<tr><th>操作员</th><th>时间</th><th>通知类型</th></tr>
	<c:if test="${empty recordList}">
	<tr><td colspan="3">暂没有重发记录</td></tr>
	</c:if>
<c:if test="${not (empty recordList)}">
<c:forEach items="${recordList}" var="record" >
<tr>
<td><c:out value='${record.operator}' /></td><td><c:out value='${record.time}' /></td><td><c:if test="${record.noticeType == '1'}">后台</c:if><c:if test="${record.noticeType == '2'}">前台</c:if>通知</td></tr>
</c:forEach>
</c:if>
</table>
<form name="recordform" action="${ctx}/sendNotice.htm?method=record" method="post">
<input type="hidden" name="tradeOrderNo" value="<c:out value='${param.tradeOrderNo}'/>">
<input type="hidden" name="noticeType" value="" id="noticeType">
</form>

<table border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
<form name="sendForm" action="" method="post" target="_blank">
<tr><td colspan='3'>请选择发送通知的类型：</td></tr>
<c:if test="${not (empty map.pageUrl)}">
<tr>
<td>
前台通知地址：</td>
<td id="url_1"><c:out value='${map.pageUrl}'/></td>
<td><input type="button" id="pageUrl" value="点击发送"></td>
</tr>
</c:if>
<c:if test="${not (empty map.bgUrl)}">
<tr>
<td>后台通知地址：</td>
<td id="url_2"><c:out value='${map.bgUrl}'/></td>
<td><input type="button" id="bgUrl" value="点击发送"></td>
</tr>
</c:if>
<tr><td colspan='3'><br></td></tr>
<c:forEach items="${map}" var="mymap" >
<input type="hidden" name="<c:out value='${mymap.key}' />" value="<c:out value='${mymap.value}' />">
</c:forEach>
</form>
<tr><td colspan='3'>注：商户通知中，如果没有配置前台通知权限，则此处无法发送前台通知，默认都能发送后台通知</td></tr>
</table>

<div id="resultListDiv" class="listFence"></div>