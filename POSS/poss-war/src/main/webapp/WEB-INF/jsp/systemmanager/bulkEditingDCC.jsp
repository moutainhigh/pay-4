<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
	<%@ include file="/common/taglibs.jsp"%>
	<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>修改DCC配置</title>
<script src="${ctx}/poss/js/jquery/js/jquery-1.4.2.min.js"
	type="text/javascript"></script>
<script type="text/javascript">
var rowIndex=1;
	function addTable(){
		rowIndex++;
		$("#edcTable").append("<tr class='trForContent1'>"+$("#edcTable tr:last").html()+"</tr>")
		$("#edcTable tr:last").find("td>input[type='button']")[0].id="btn"+rowIndex;
	}
	var json="";
	function submitSave() {
		$('table tr').each(function(index,tr){
			if(index!=0 ||index!=$("#edcTable tr").length){
				$(this).find("td input:checked").each(function(){
					json+=this.value+":";
					
				});
					$(this).find("input[name=markup]").each(function(){
						json+=this.value+"&";
					});
			}
		});
		$('#searchForm').append("<input type='hidden' name='json' value="+json+" >");
		document.getElementById('searchForm').submit();
	}
	function checkNum(obj) {
	    //检查是否是非数字值
	    if (isNaN(obj.value)) {
	        obj.value = "";
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
	function back(){
		window.location.replace("./dcc_configuration.do?method=index");
	}
	function allCurrencyCode(ele){
		var eleId = $(ele)[0].id;
		var index = eleId.substr(eleId.length-1);
		//$("#edcTable tr:eq("+index+")").find("input:checkbox").attr("checked","true");
		 var checkbox=$("#edcTable tr:eq("+index+")").find("input:checkbox").is(':checked');
		 if(checkbox){
			var checkboxAll= $("#edcTable tr:eq("+index+")").find("input:checkbox");
				///checkboxAll.atrr("value","反选")
				checkboxAll.removeAttr("checked")
		 }else{
			 var checkboxAll=$("#edcTable tr:eq("+index+")").find("input:checkbox");
		 		///checkboxAll.attr("value","全选");
		 		checkboxAll.attr("checked","true")
		 }
	}	
	
</script>
</head>
<body>
	<h2 class="panel_title">修改DCC配置</h2>
	
	<form action="dcc_configuration.do" method="post" id="searchForm">
		<input type="hidden" name="method" value="bulkUpdateDCC" />
		<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" id="edcTable" align="center">
			<tr class="trForContent1">
				<td class="border_top_right4" align="center" style="width: 150px;" class="">币种</td>
				<td class="border_top_right4" align="center" style="width: 150px;" class="">markup</td>
				<td class="border_top_right4" align="center" style="width: 150px;" class=""></td>
			</tr>
		
			<%-- <c:forEach items="${dcclist}" var="dcc">
				<tr class="trForContent1">
				<td  id="currencyCode">
				&nbsp;&nbsp;<input type="button" name="currencyCode" onclick="allCurrencyCode();" value="全选"/>
				<c:forEach items="${dcc.value}" var="cc">
					${cc} <input  name='currencyCode' type='checkbox' checked="checked">&nbsp;
				</c:forEach>
				</td>
				<td align="center" class="border_top_right4">
				<input type="text" style="width: 150px;" onkeyup="checkNum(this);" name="markup" value=${dcc.key}>
				<font size="3.5">%</font>
				</td>
				<td align="center" class="border_top_right4"><img src="hnastyle/images/add.png" onclick="addTable();" /></td> 
				</tr>
			</c:forEach> --%>
 			<tr class="trForContent1">
				<td class="border_top_right4" align="center"> 
 					<input type="button" id="btn1"  onclick="allCurrencyCode(this);" value="全选/不" /> 
					<c:forEach items="${dcclist}" var="dcc">
					<input type="hidden" name="partnerId" value=${dcc.partnerId}>
					<input name='currencyCode' type='checkbox' value=${dcc.currencyCode}>${dcc.currencyCode}
				</c:forEach>
				<td align="center" class="border_top_right4"> 
 				<input type="text" onkeyup="checkNum(this);" onblur="decimals(this)" name="markup">
				<font size="3.5">%</font></td> 
				<td align="center" class="border_top_right4"><img 
					src="hnastyle/images/add.png" onclick="addTable();" /></td> 
			</tr> 
		
		</table>
		<table class="border_all2" width="80%"  border="0" cellspacing="0" cellpadding="1" align="center">
			<tr class="trForContent1">
				<td class="border_top_right4" colspan="3" align="center"><input align="middle" type="button"
					value="修改" class="button2" onclick="javascript:submitSave();">&nbsp;&nbsp;
					<input align="middle" type="button" onClick="back();" value="返回"
					class="button2">&nbsp;&nbsp;</td>
			</tr>
		</table>
	</form>
</body>
</html>