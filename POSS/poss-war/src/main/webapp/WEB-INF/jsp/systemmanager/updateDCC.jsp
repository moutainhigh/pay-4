<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>修改企 业 DCC 配 置</title>

<link rel="stylesheet" href="./css/main.css">
<script src="./js/common.js"></script>
<script src="${ctx}/poss/js/jquery/js/jquery-1.4.2.min.js"
	type="text/javascript"></script>
<script type="text/javascript">
	function checkNum(obj) {
		//检查是否是非数字值
		if (isNaN(obj.value)) {
			obj.value = "";
		}
	}
	function back() {
		window.location.replace("./dcc_configuration.do?method=index");
	}
	
	function updateMarkUp(obj) {
		var markup=obj.value;//获取markup
		if(markup && markup.indexOf(".")==-1){
			var index;
			var zeroCount=0; 
			for(var i=0;i<markup.length;i++){
				if(markup.charAt(i)!="0"){
					index=i;
					break;
				}else{
					zeroCount++;	
				}
			}
			if(zeroCount==markup.length){
				$("#markUp").val("");
				$("#markup").attr("value", "");
			}else if(index){
				$("#markUp").val(markup.substr(index));
				$("#markup").attr("value", markup.substr(index));
			}
		}else if(markup && markup.indexOf(".")>-1){
			var left=markup.split(".")[0];
			var right=markup.split(".")[1];
			var zeroCount=0;  
			for(var i=0;i<left.length;i++){
				if(left.charAt(i)=="0"){
					zeroCount++;
				}else{
					break;
				}
			}
			if(zeroCount==left.length){
				left=left.substr(zeroCount-1);
				markup=left+"."+right;
				$("#markUp").val(left+"."+right);
				$("#markup").attr("value",left+"."+right);
			}else if(zeroCount>0){
				left=left.substr(zeroCount);
				markup=left+"."+right;
				$("#markUp").val(left+"."+right);
				$("#markup").attr("value",left+"."+right);
			}
			if(right.length>2){
				markup=markup.substring(0,markup.indexOf(".")+3);
				$("table tr td input[name=markup]").attr("value",markup);
				$("#markUp").val(markup);
				$("#markup").attr("value",markup);
			}
		}
		$("#markup").attr("value",markup);
	}
	function updateDCC() {
		$("#updateForm").submit();
	}
</script>
</head>
<body>
	<h2 class="panel_title">修改企业DCC配置</h2>
	<h2>${info}</h2>
	<form id="updateForm" action="dcc_configuration.do" method="post">
		
		
		
		<input type="hidden" name="partnerId" value=${dccConfig.partnerId} >
		<input type="hidden" id="markup" name="markup" > 
		<input type="hidden" name="partnerName" value=${dccConfig.partnerName}>
		<input type="hidden" name="id" value=${dccConfig.id}>
		 <input type="hidden" name="method" value="DCCConfigurationEdit"> 
			 
		<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" id="edcTable" align="center">
			<tr class="trForContent1">
			<td align="center" class="border_top_right4"> 会员号:<span id="partnerId">${dccConfig.partnerId}</span></td>
			<td align="center" class="border_top_right4"> 商户名称: <span id="partnerName">${dccConfig.partnerName}</span></td>
		</tr>
			<tr class="trForContent1">
				<td align="center" style="width: 150px;" class="border_top_right4">币种</td>
				<td align="center" style="width: 150px;" class="border_top_right4">markup</td>
			</tr>
			<tr class="trForContent1">
				<td align="center" class="border_top_right4" id="currencyCode">
					<input type="text" value=${dccConfig.currencyCode}>			
					<input type="hidden" name="currencyCode" value=${dccConfig.currencyCode}>			
				</td>
				<td align="center" class="border_top_right4">
				<input type="text" id="markUp" onblur="updateMarkUp(this);" 
					onkeyup="checkNum(this);" value=${dccConfig.markUp}> <font
					size="3.5">%</font></td>
			</tr>
			<tr class="trForContent1">
				<td align="center" class="border_top_right4" colspan="2"><input align="middle" type="button"
					onClick="updateDCC();" value="确定" class="button2">
					<input align="middle" type="button" onClick="back();" value="返回"
					class="button2"></td>
			</tr>
		</table>
	</form>
</body>
</html>