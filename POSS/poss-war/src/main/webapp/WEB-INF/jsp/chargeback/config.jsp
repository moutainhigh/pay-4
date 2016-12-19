<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">拒付配置</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form action="" method="post" name="addFrom" id="addFrom">
<table class="border_all2" width="900" border="0" cellspacing="0" cellpadding="3" align="center">
	<tr class="trForContent1">
		<td align="right" class="border_top_right4">小于等于</td>	
		<td class="border_top_right4" align="left" style="width: 100px;">
			<input type="text" name="firstPercent" id="firstPercent" value="${firstPercent }" style="width: 80px;"/>%
		</td>
		<td align="left" class="border_top_right4" style="width: 80px;">收取</td>
		<td class="border_top_right4" align="left" style="width: 100px;">
			<input type="text" name="firstCost" id="firstCost" value="${firstCost }" style="width: 80px;"/>
		</td>
		<td align="left" class="border_top_right4" colspan="3">美元/笔</td>
	</tr>
	<tr class="trForContent1">
		<td align="right" class="border_top_right4" style="width: 80px;">大于</td>	
		<td class="border_top_right4" align="left">
			<input type="text" name="secondPercent" id="secondPercent" value="${secondPercent }" style="width: 80px;"/>%
		</td>
		<td class="border_top_right4" align="left">
			小于等于
		</td>
		<td class="border_top_right4" align="left">
			<input type="text" name="thirdPercent" id="thirdPercent" value="${thirdPercent }" style="width: 80px;"/>%
		</td>
		<td class="border_top_right4" align="left" style="width: 60px;">
			收取
		</td>
		<td class="border_top_right4" align="left" style="width: 100px;">
			<input type="text" name="secondCost" id="secondCost" value="${secondCost }" style="width: 80px;"/>
		</td>
		<td class="border_top_right4" align="left">
			美元/笔
		</td>
	</tr>
	<tr class="trForContent1">
		<td align="right" class="border_top_right4">大于</td>	
		<td class="border_top_right4" align="left">
			<input type="text" name="fourPercent" id="fourPercent" value="${fourPercent }" style="width: 80px;"/>%
		</td>
		<td class="border_top_right4" align="left" style="width: 100px;">
			收取
		</td>
		<td class="border_top_right4" align="left" style="width: 100px;">
			<input type="text" name="fourCost" id="fourCost" value="${fourCost }" style="width: 80px;"/>
		</td>
		<td class="border_top_right4" align="left" colspan="3">
			美元/笔
		</td>
	</tr>
	
	<tr class="trForContent1">
		<td class="border_top_right4" colspan="7" align="center">
			<input type="button" name="butSubmit" value="确认" class="button2" onclick="addOrUpdate();">
		</td>
	</tr>
	
</table>
<input type="hidden" name="id" id="id" value="${id}"/>
</form>

<script>

function isPirce(s){
    //s = trim(s);
    var p =/^[1-9](\d+(\.\d{1,2})?)?$/; 
    var p1=/^[0-9](\.\d{1,2})?$/;
    return p.test(s) || p1.test(s);
}

function addOrUpdate() {
	
	var firstPercent = $('#firstPercent').val();
	var firstCost = $('#firstCost').val();
	var secondPercent = $('#secondPercent').val();
	var thirdPercent = $('#thirdPercent').val();
	var secondCost = $('#secondCost').val();
	var fourPercent = $('#fourPercent').val();
	var fourCost = $('#fourCost').val();
	
	if(!isPirce(firstPercent)){
		alert('请输入合法的百分比');
		$('#firstPercent').focus();
		return;
	}
	if(!isPirce(firstCost)){
		alert('请输入合法的金额');
		$('#firstCost').focus();
		return;
	}
	if(!isPirce(secondPercent)){
		alert('请输入合法的百分比');
		$('#secondPercent').focus();
		return;
	}
	if(!isPirce(thirdPercent)){
		alert('请输入合法的百分比');
		$('#thirdPercent').focus();
		return;
	}
	if(!isPirce(secondCost)){
		alert('请输入合法的百分比');
		$('#secondCost').focus();
		return;
	}
	if(!isPirce(fourPercent)){
		alert('请输入合法的百分比');
		$('#fourPercent').focus();
		return;
	}
	if(!isPirce(fourCost)){
		alert('请输入合法的金额');
		$('#fourCost').focus();
		return;
	}
	
	var pars = $("#addFrom").serialize() ;
	$.ajax({
		type: "POST",
		url: "${ctx}/chargeBackConfig.do?method=addOrUpdate",
		data: pars,
		success: function(result) {
			if(result){
				alert('操作成功！');
			}else{
				alert('操作成失败！');
			}
		}
	});
}
</script>