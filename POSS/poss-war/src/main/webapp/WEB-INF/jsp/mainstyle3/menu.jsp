<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
		<title>MENU</title>
		<style type="text/css">
<!--
body {
	background-color: #4A8EC6;
	background-image: url(${ctx}/images/leftbg-1.jpg);
	margin-left: 0px;
	margin-top: 0px;
	margin-right: 0px;
	margin-bottom: 0px;
}
-->
</style>
		<link href="${ctx}/css/menu.css" rel="stylesheet" type="text/css">
		<script src="${ctx}/js/mainstyle1/menu.js"></script>
	</head>
	<body>
		<div class="topMenuImage">
			<img src="images/1.jpg" width="205" height="12">
		</div>
		${htmlMenu}
	</body>
</html>
