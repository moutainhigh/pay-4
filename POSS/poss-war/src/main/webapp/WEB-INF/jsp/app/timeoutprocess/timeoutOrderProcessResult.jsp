<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>超时订单查询</title>
</head>
<body>
<c:if test="${errorMsg != null}">
操作失败!
</c:if>
<c:if test="${errorMsg == null}">
操作成功!
</c:if>
<input type="button" onclick="javascript:window.location.href='${ctx}/timeoutprocess.do';" value="返回"/>
</body>

</html>
