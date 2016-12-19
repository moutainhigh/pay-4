<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<html>
<body>

<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">修改用户关联关系</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>


<form action="" method="post" name="addFrom">
<input type="hidden" name="id" value="${relationDto.id}">
<input type="hidden" name="lv" value="${relationDto.lv}">
<input type="hidden" name="rv" value="${relationDto.rv}">
<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
	
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >销售人员姓名：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" name="name" value="${relationDto.name}"/>
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >销售主管姓名：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" name="pname" value="${relationDto.pname}"/>
				<font color="red">*</font>	
			</td>
			
		</tr>	
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >销售人员用户名：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" name="loginId" style="background-color: gray;" readonly="readonly" value="${relationDto.loginId}"/>
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >销售主管用户名：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" name="ploginId" value="${relationDto.ploginId}"/>
				<font color="red">*</font>	
			</td>
			
		</tr>	
		<tr>
			<td colspan="4" align="center">
			<input type="button"  name="butSubmit" value="确 定" class="button2" onclick="add();">
			<input type="button"  name="butSubmit" value="重 置" class="button2" onclick="reset();">
			<input type="button"  name="butSubmit" value="返 回" class="button2" onclick="back();">
		</td>
	</tr>
</table>
</form>
</body>
<script>
$(function(){
	var s = '${msg}';
	if(s != '')
	alert(s);
});
function add(){

	var d = document.forms[0];
	if('' == d.name.value){
		alert("销售人员姓名不能为空！");
		return;
	}
	if('' == d.pname.value){
		alert("销售主管姓名不能为空！");
		return;
	}
	if('' == d.loginId.value){
		alert("销售人员用户名不能为空！");
		return;
	}
	if('' == d.ploginId.value){
		alert("销售主管用户名不能为空！");
		return;
	}
	

	d.action = "${ctx}/userrelation/userRelationQuery.do?method=doUpdate";

	d.submit();
}

	function reset(){
		var d = document.forms[0];
		d.name.value ="";
		d.pname.value = "";
		d.loginId.value ="";
		d.ploginId.value = "";
	}
	function back(){
		window.location.href="${ctx}/userrelation/userRelationQuery.do";
	}
</script>
</htm>