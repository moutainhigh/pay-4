<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<%--引入用于格式化页面的CSS文件--%>
<link rel="stylesheet" href="./css/main.css">
</head>
<body>
	<div>
	<div>新增出款规则：</div>
	<form name="" method="post" action="${ctx}/rulemanage.do?method=createRule">
		<div>目的 银行:	
		<select name="toBankCode">	
			<c:forEach items="${banklist}" var="bank">
				<option value="${bank.sequenceId}">${bank.typeName}-${bank.businessName}-${bank.bankName}</option>
			</c:forEach>
		</select></div>
		<div>出款银行:	
		<select name="withdrawBankId">
			<c:forEach items="${banklist}" var="bank">
				<option value="${bank.sequenceId}">${bank.typeName}-${bank.businessName}-${bank.bankName}</option>
			</c:forEach>
		</select></div>
		<div>单笔最小出款金额(元)：<input type="text" name="sinMinValue"></input></div>
		<div>单笔最大出款金额(元)：<input type="text" name="sinMaxValue"></input></div>
		<div>交易类型:
		<select name="withdrawType">
			<option value="1" selected>对公 </option>
			<option value="2">对私 </option>
		</select></div>
		<div>银行账户类型:<select name="bankAcctType">
			<option value="0" selected>借记卡 </option>
			<option value="1" >存折</option>
			<option value="2">信用卡</option>
		</select></div>
		<div>优先级:		<select name="priority">
			<option value="1" selected>1</option>
			<option value="2">2</option>
			<option value="3">3</option>
			<option value="4">4</option>
			<option value="5">5</option>
		</select></div>
		<div>状态:		
		<select name="status">
			<option value="0">0</option>
			<option value="1" selected>1</option>
		</select></div>
		<div><input type="submit" value="新增"></input></div>
	</form>
	</div>
</body>
</html>