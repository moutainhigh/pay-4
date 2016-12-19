<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
			<div align="center"><font class="titletext">修改出款业务</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form action="context_fundout_foproduct.controller.htm?method=modifyFundoutProductInfo" method="post" name="mainfrom">
	<input type="hidden" name="code" value="${dto.code}" />
	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
		<tr class="trForContent1">
			<td align="right" class="border_top_right4">产品Code：</td>
			<td class="border_top_right4">${dto.code}</td>
		</tr>
		<tr class="trForContent1">
			<td align="right" class="border_top_right4">业务名称：</td>
			<td class="border_top_right4">${dto.name}</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">状态：</td>
			<td class="border_top_right4"><li:select name="status" selected="${dto.status}" itemList="${statusList}" /></td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">备注：</td>
			<td class="border_top_right4"><textarea name="mark" rows="5" cols="20">${dto.mark }</textarea></td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right"><input type="submit" id="submitBtn" class="button2" value="确  认"></td>
			<td class=border_top_right4 align="left"><input type="button" onclick="goBack();" class="button2" value="返  回"></td>
		</tr>
	</table>
</form>
<script type="text/javascript">
	function goBack() {
		document.mainfrom.action="context_fundout_foproduct.controller.htm?method=initSearch";
		document.mainfrom.submit();
	}
</script>