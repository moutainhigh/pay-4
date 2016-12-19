<%@ page language="java" contentType="text/html; charset=utf-8"
    pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>企业DCC配置详情</title>
<script type="text/javascript">
function back(){
	window.location.replace("./dcc_configuration.do?method=index");
}
</script>
</head>
<body>
	<h3>企业DCC配置详情</h3><br>
		<form>
		&nbsp;&nbsp;
			会员号&nbsp;:<span id="partnerId">${dccConfig.partnerId}</span>
					<br>
					<br>
		&nbsp;&nbsp;		
			商户名称:
						<span id="partnerName">${dccConfig.partnerName}</span>
		<br> <br> <br>
		<table width="70%" border="1" cellspacing="1" cellpadding="0"
			align="center" id="edcTable">
			<tr class="trForContent1">
				<td align="center" style="width: 150px;" class="border_top_right4">币种</td>
				<td align="center" style="width: 150px;" class="border_top_right4">markup</td>
			</tr>
			<tr class="trForContent1">
			<td align="center">
				<span id="currencyCode">${dccConfig.currencyCode}</span>
			</td>
			<td align="center" class="border_top_right4">
				<span >${dccConfig.markUp}</span> <font  size="3.5" >%</font>
			</td>
		</tr>
		</table>
		
		
		<br>
		<br>
		<table align="center">
		<tr>
		 <td align="center">
				<input align="middle" type="button" onClick="back();" value="返回" class="button2">&nbsp;&nbsp;
		</td>
		</tr>
		</table>
	</form>
</body>
</html>