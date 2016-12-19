<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>关联商户</title>
	</head>
	
	<body>
		<form action="" method="post">
		<div style="text-align: left;position: absolute;left:50px;top:50px;">
			<h3>关联商户</h3><br>
			批次编号：<input type="text" name="batchRuleId" id="batchRuleId"><br><br>
			批次名称：<input type="text" name="batchRuleDesc" id="batchRuleDesc"><br><br>
			包含商户配置项：
			<select name="memberSet">
				<option value="all">不限商户</option>
			</select>
			<br><br>
			选择商户名单：<input type="checkbox" name="choiceMember">紧急商户名单</input><br><br>
			过滤商户配置项：
			<select name="filterMemberSet">
				<option value="no">不过滤商户名单</option>
			</select>
			<br><br>
			过滤商户名单：<input type="checkbox" name="filterMember">紧急商户名单</input><br><br>
			<input type="button" value="保存商户名单"/>
		</div>
		</form>
	</body>
</html>