<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>EDC默认markup</title>

<link rel="stylesheet" href="./css/main.css">
<script src="./js/common.js"></script>
<script src="${ctx}/poss/js/jquery/js/jquery-1.4.2.min.js"
	type="text/javascript"></script>
<script type="text/javascript">
  var rowIdex=1;
	$(function() {
		$.ajax({
			type : "POST",
			dataType : "json",
			url : "./dcc_configuration.do?method=saveDcc&flag=7",
			success : function(result) {
				//$('#resultListDiv').html(result);
				for (var i = 0; i < result.length; i++) {
					var cc = result[i].currencyCode;
					$("#currencyCode").append("<input name='currencyCode' type='checkbox'  value="+cc+">"+cc+"</input>");
				}
			}
		});
	});
	function addTable() {
		rowIndex++;
		$("#edcTable").append("<tr>"+$("#edcTable tr:last").html()+"</tr>")
		$("#edcTable tr:last").find("td>input[type='button']")[0].id="btn"+rowIndex;
	}
	function checkNum(obj) {
		//检查是否是非数字值
		if (isNaN(obj.value)) {
			obj.value = "";
		}
	}
	function allCurrencyCode(ele){
		var eleId = $(ele)[0].id;
		var index = eleId.substr(eleId.length-1);
		//$("#edcTable tr:eq("+index+")").find("input:checkbox").attr("checked","true");
		 var checkbox=$("#edcTable tr:eq("+index+")").find("input:checkbox").is(':checked');
		 if(checkbox){
			 $("#edcTable tr:eq("+index+")").find("input:checkbox").removeAttr("checked");
		 }else{
			 $("#edcTable tr:eq("+index+")").find("input:checkbox").attr("checked","true");
		 }
	}	
	function decimals(obj){
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
				$("table tr td input[name=markup]").attr("value","");
			}else if(index){
				$("table tr td input[name=markup]").attr("value",markup.substr(index));
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
				$("table tr td input[name=markup]").attr("value",left+"."+right);
			}else if(zeroCount>0){
				left=left.substr(zeroCount);
				markup=left+"."+right;
				$("table tr td input[name=markup]").attr("value",left+"."+right);
			}
			if(right.length>2){
				markup=markup.substring(0,markup.indexOf(".")+3);
				$("table tr td input[name=markup]").attr("value",markup);
			}
		}
		}
</script>
</head>
<body>
	<!-- <table width="25%" border="0" cellspacing="0" cellpadding="0"
		align="center" height="21">
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
		<tr>
			<td height="18">
				<div align="center">
					<font class="titletext">EDC 默 认 markup</font>
				</div>
			</td>
		</tr>
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
	</table>
	<br>
	<br>
	<br> -->
	<h2 class="panel_title">EDC默认markup</h2>
	<h2>${info}</h2>
	<form action="dcc_configuration.do" method="post">
		<table id="edcTable" width="70%" border="1" cellspacing="1" cellpadding="0" align="center">
			<tr class="trForContent1">
				<td align="center" style="width: 150px;" class="border_top_right4">币种</td>
				<td align="center" style="width: 150px;" class="border_top_right4">markup</td>
				<td align="center" style="width: 150px;" class="border_top_right4"></td>
			</tr>
			<tr class="trForContent1">
				<td align="center" id="currencyCode">
					<input type="button" name="currencyCode"  id="btn1" onclick="allCurrencyCode(this);" value="全选/不"/>			
				</td>
				<td align="center" class="border_top_right4">
				<input type="text" name="mark_up" style="width: 150px;"onblur="decimals(this)" onkeyup="checkNum(this);">
					<font size="3.5">%</font></td>
				<td align="center" class="border_top_right4"><img
					src="hnastyle/images/add.png" onclick="addTable();" /></td>
			</tr>
			<tr class="trForContent1"><td align="center" colspan="2"><input type="button" name="submitBtn" value="保存" class="button2"></td></tr>
		</table>
		
	</form>
</body>
</html>