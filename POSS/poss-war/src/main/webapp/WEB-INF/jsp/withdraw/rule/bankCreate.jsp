<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%--引入用于格式化页面的CSS文件--%>
<link rel="stylesheet" href="./css/main.css">
</head>
<body>
	<div>
	<div>新增出款银行：</div>
	<form name="" method="post" action="${ctx}/rulemanage.do?method=createBank">
		<div>名称:<input type="text" name="bankName"></input></div>
		<div>英文简称:<input type="text" name="bankCode"></input></div>
		<div>出款方式:<select name="withdrawTypeId">
		<c:forEach items="${typelist}" var="type">
			<option value="${type.sequenceId}">${type.typeName}</option>
			</c:forEach>
		</select></div>
		<div>出款业务:<select name="withdrawBusiId">
		<c:forEach items="${businesslist}" var="business">
			<option value="${business.sequenceId}">${business.businessName}</option>
			</c:forEach>
		</select></div>		
		<div>状态:		<select name="status">
			<option value="0">0</option>
			<option value="1" selected>1</option>
		</select></div>
		<div><input type="submit" value="新增"></input></div>
	</form>
	</div>
</body>
</html>