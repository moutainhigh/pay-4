<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>后台出款订单成功置失败</title>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

</head>
<body>
<c:if test="${errorMsg != null}">
	<p align="left"><font color="red">
	<c:if test="${errorMsg == 'orderNotExists'}">订单不存在或者订单号非法！</c:if>
	<c:if test="${errorMsg == 'orderAccountingIsDone'}">订单已记账！</c:if>
</font></p>
</c:if>
操作成功！<br/>
<input type="button" onclick="back();" value="返回"/>
</p>
<script type="text/javascript">
function back(){
	window.location.href='${ctx}/ebill_channelOrderprocess.do';
}

</script>
</body>
</html>