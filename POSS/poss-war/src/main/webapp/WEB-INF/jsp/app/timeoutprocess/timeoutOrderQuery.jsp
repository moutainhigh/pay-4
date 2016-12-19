<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>超时订单查询</title>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<style>
.input_txt{ padding:5px 3px;}

.middle_select{
height: 4em;
line-height: 4em;
overflow: hidden;
} 

</style>
</head>
<body>
<c:if test="${errorMsg != null}">
<p>
${errorMsg}
</p>
</c:if>
<p>
<table width="599" height="264" align="left">
<form action="${ctx}/timeoutprocess.do?method=query" method="post">
<tr>
<td width="265" align="right">
产品类型：
</td>
<td width="322" align="left">
<select name="billType" id="billType" onchange="changeVal(this.value);" style="height: 25px;" class="middle_select">
<c:forEach items="${billTypes}" var="billType">
<option value="${billType.value}" class="input_txt">${billType.desc}</option>
</c:forEach>	
</select>
</td>
</tr>
<tr>
<td align="right">
手机号：
</td>
<td align="left">
<input type="text" id="mobileNo" name="mobileNo" value="" class="input_txt"/></td>
</tr>
<tr>
<td align="right">
网关交易流水号：
</td>
<td align="left">
<input type="text" name="gatewayTradeNo" id="gatewayTradeNo" value="" class="input_txt"/></td>
</tr>
<tr>
<td align="right">
起始时间：
</td>
<td align="left">
<li:dateTime id="startDate" width="120" onfocusId="endDate"/></td>
</tr>
<tr>
<td align="right">
结束时间：
</td>
<td align="left">
<li:dateTime width="120" id="endDate" />
</td>

</tr>
<tr id="payeeMemberId">
<td align="right">
收款方账号：
</td>
<td align="left">
<input type="text" id="payeeIdcontent" name="payeeIdcontent" value="" class="input_txt"/>
</td>
</tr>
<tr id="payerMemberId">
<td align="right">
付款方账号：
</td>
<td align="left">
<input type="text" id="payerIdcontent" name="payerIdcontent" value="" class="input_txt"/>
</td>
</tr>
<tr>
<td colspan="2" align="center">
<input type="button" id="command" value="查询"/>
<input type="reset" value="清空"/>
</td>
</tr>
</form>
<tr>
<td colspan="2">
<div id="displayResult"/>
</td>
</tr>
</table>
</p>
<p>

</p>
<script type="text/javascript">


function changeVal(val){

	var payeeMemberId = document.getElementById('payeeMemberId');
	var payerMemberId = document.getElementById('payerMemberId');

	if(val == 12){
		payeeMemberId.style.display = 'none';
		payerMemberId.style.display = 'none';
	}else{
		payeeMemberId.style.display = '';
		payerMemberId.style.display = '';
	}
}


$('#command').click(function(){
	 $.ajax({ 
		 type: "POST", 
		 url: "${ctx}/timeoutprocess.do?method=query", 
		 data: "billType="+$('#billType').val() + "&mobileNo="+$('#mobileNo').val()+"&gatewayTradeNo="+$('#gatewayTradeNo').val()+
		 "&payeeAccountNo="+$('#payeeAccountNo').val()+"&payerIdcontent="+$('#payerIdcontent').val()+"&startDate="+$('#startDate').val()+"&endDate="+$('#endDate').val(),
		 success: function(msg){
			 	$('#displayResult').html(msg);
			 }
		 }); 
});
</script>
</body>
</html>