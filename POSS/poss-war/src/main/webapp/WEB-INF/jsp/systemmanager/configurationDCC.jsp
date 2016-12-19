<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>企业DCC默认配置</title>
<script src="${ctx}/poss/js/jquery/js/jquery-1.4.2.min.js"
	type="text/javascript"></script>
<script type="text/javascript">
	var rowIndex=1;
	$(function() {
		$.ajax({
			type : "POST",
			dataType : "json",
			url : "./dcc_configuration.do?method=saveDcc&flag=7",
			success : function(result) {
				//$('#resultListDiv').html(result);
				for (var i = 0; i < result.length; i++) {
					var cc = result[i].currencyCode;
					$("#currencyCode").append("<input name='currencyCode' type='checkbox' value="+cc+">"+cc+"</input>");
				}
			}
		});
	});
	function addTable() {
		rowIndex++;
		$("#edcTable").append("<tr class='trForContent1'>"+$("#edcTable tr:last").html()+"</tr>")
		$("#edcTable tr:last").find("td>input[type='button']")[0].id="btn"+rowIndex;
	}
	function checkNum(obj) {
		//检查是否是非数字值
		if (isNaN(obj.value)) {
			obj.value = "";
		}
	}
	function decimals(obj){
		//alert(obj.value);
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
	function back() {
		window.location.replace("./dcc_configuration.do?method=index");
	}
	var json="";
	function submitSave() {
		
		$('table tr').each(function(index,tr){
			if(index!=0 ||index!=$("#edcTable tr").length){
				
				$(this).find("td input:checked").each(function() {
					json += this.value + ":";

				});
				$(this).find("input[name=markup]").each(function() {
					json += this.value + "&";
				});
			}
		});
		$('#searchForm').append("<input type='hidden' name='json' value="+json+" >");
		document.getElementById('searchForm').submit();
		//alert("保存成功！！！")
	}
	function allCurrencyCode(ele){
		var eleId = $(ele)[0].id;
		var index = eleId.substr(eleId.length-1);
	///	$("#edcTable tr:eq("+index+")").find("input:checkbox").attr("checked","true");
		 var checkbox=$("#edcTable tr:eq("+index+")").find("input:checkbox").is(':checked');
		 if(checkbox){
			 $("#edcTable tr:eq("+index+")").find("input:checkbox").removeAttr("checked");
		 }else{
			 $("#edcTable tr:eq("+index+")").find("input:checkbox").attr("checked","true");
		 }
	}	
</script>
</head>
<body>
	<h2 class="panel_title">设置企业DCC默认配置</h2>
	<h2>${info}</h2>	
	<form action="dcc_configuration.do" method="post" id="searchForm">
		<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" id="edcTable" align="center">
			<tr class="trForContent1">
				<td align="center" style="width: 150px;" class="border_top_right4">币种</td>
				<td align="center" style="width: 150px;" class="border_top_right4">markup</td>
				<td align="center" style="width: 150px;" class="border_top_right4"></td>
			</tr>
			<tr class="trForContent1">
				<td align="center" class="border_top_right4" id="currencyCode">
					<input type="button" name="currencyCode" id="btn1" onclick="allCurrencyCode(this);" value="全选/不"/>			
				</td>
				<td align="center" class="border_top_right4">
				<input type="hidden" name="method" value="SettingsDCC"> 
					<input type="text" name="markup"  onkeyup="checkNum(this);" onblur="decimals(this);" > 
					<font size="3.5">%</font></td>
				<td align="center" class="border_top_right4"><img
					src="hnastyle/images/add.png" onclick="addTable();" /></td>
			</tr>
			
		</table>
		<table class="border_all2" width="80%"  border="0" cellspacing="0" cellpadding="1" align="center">
			<tr class="trForContent1">
				<td class="border_top_right4" colspan="3" align="center"><input align="middle" type="button"
					value="保存" class="button2" onclick="javascript:submitSave();">&nbsp;&nbsp;
					<input align="middle" type="button" onClick="back();" value="返回"
					class="button2">&nbsp;&nbsp;</td>
			</tr>
		</table>
		
	</form>
</body>
</html>