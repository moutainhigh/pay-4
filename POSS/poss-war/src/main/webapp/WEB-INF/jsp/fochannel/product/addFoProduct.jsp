<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
			<div align="center">
				<font class="titletext">
					新增出款产品
				</font>
			</div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>

<script type="text/javascript">
	function goSubmit(){
		document.mainfrom.action="context_fundout_foproduct.controller.htm?method=addFoProduct";

		if($("input[name='code']").val() == ""){
			alert("请填写产品CODE!");
			return false;
		}
		
		if($("input[name='name']").val() == ""){
			alert("请填写产品名称!");
			return false;
		}
		document.mainfrom.submit();
	}
</script>

<form action="" method="post" name="mainfrom">
	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
		<tr class="trForContent1">
			<td align="right" class="border_top_right4">出款产品CODE：</td>
			<td class="border_top_right4">
				<input type="text"  name="code" value="" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td align="right" class="border_top_right4" >出款产品名称：</td>
			<td class="border_top_right4">
				<input type="text"  name="name" value="" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right" >产品状态：</td>
			<td class="border_top_right4" >
				<li:select name="status" itemList="${statusList}" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right" >备注：</td>
			<td class="border_top_right4" >
				<textarea name="mark" rows="5" cols="20"></textarea>
			</td>
		</tr>
		<tr class="trForContent1">
			<td align="center" colspan="2"><input type="button" onclick="goSubmit();" class="button2" value="新  增"></td>
		</tr>
		<c:if test="${not empty info}">
			<li style="color: red;">${info}</li>
		</c:if>
	</table>
</form>