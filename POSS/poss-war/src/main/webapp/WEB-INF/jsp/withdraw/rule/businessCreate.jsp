<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%--引入用于格式化页面的CSS文件--%>
<link rel="stylesheet" href="./css/main.css">
</head>
<body>
	<div>
	<div>新增出款业务：</div>
	<form name="" method="post" action="${ctx}/rulemanage.do?method=createBusiness">
		<div>名称:<input type="text" name="businessName"></input></div>
		<div>状态:		<select name="status">
			<option value="0">0</option>
			<option value="1" selected>1</option>
		</select></div>
		<div><input type="submit" value="新增"></input></div>
	</form>
	</div>
</body>
</html>