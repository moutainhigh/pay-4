<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>后台补记账</title>
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

<p>
<form action="${ctx}/ebill_manualAccounting.do?method=onSubmit" method="post" onsubmit="return check(this);">
<table width="507" height="143">
<tr>
<td width="203" align="right">
补记账订单号：</td>
<td width="292" align="left">
<input name="orderId" type="text" value="<c:if test='${orderId != null}'>${orderId}</c:if>" />
</td>
</tr>
<tr>
<td align="right">
补记账类型：</td>
<td align="left">
<select name="type">
	<option value="0">请选择</option>
	<option value="1">待转手续费补记账</option>
	<option value="2">销账成功手续费补记账</option>
	<option value="3">个人应用手续费退款</option>
	<option value="4">手机充值记账</option>
</select>
</td>
</tr>
<tr>
<td align="right">
原因描述：</td>
<td align="left">
 <textarea rows="3" cols="20" name="descrition">
 <c:if test='${descrition != null}'>${descrition}</c:if>
 </textarea>
 </td>
</tr>
<tr>
<td colspan="2">
  <div align="center">
    <input type="submit" value="提交" />
  </div></td>
</tr>
</table>
</form>
</p>
<script type="text/javascript">
function check(d){

	var orderId = d.orderId.value;
	if(orderId == ''){
		alert('请输入订单号！');
		return false;
	}

	var type = d.type.value;
	if(type == 0){
		alert('请选择补记账类型！');
		return false;
	}
	var desc = d.descrition.value;
	if(desc == ''){
		alert('请输入原因！');
		return false;
	}

	return true;
}

</script>
</body>
</html>