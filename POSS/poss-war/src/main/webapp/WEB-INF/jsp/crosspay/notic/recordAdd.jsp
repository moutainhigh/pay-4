<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">添加或修改公告</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form action="" method="post" name="addFrom"><c:if
	test="${result != null}">
	<input type="hidden" name="id" value="${result.id}" />
</c:if>
<table class="border_all2" width="500" border="0" cellspacing="0" cellpadding="3" align="center">
	<tr class="trForContent1">	
		<td class="border_top_right4" align="right">公告标题：</td>
		<td class="border_top_right4" align="left">
		<input type="text" id="title" name="title" value="<c:if test='${result != null}'>${result.title}</c:if>">
		</td>
	</tr>
	<tr class="trForContent1">
		<td class="border_top_right4" align="right">公告内容：</td>
		<td class="border_top_right4" align="left">
			<textarea id="content" name="content" rows="10" cols="50"><c:if test='${result != null}'>${result.content}</c:if></textarea>
		</td>
	</tr>
	<tr class="trForContent1">
		<td class="border_top_right4" colspan="2" align="center">
			<input type="button" name="butSubmit" value="保 存" class="button2" onclick="add();">
			<input type="button" name="back" value="取 消" class="button2" onclick="javascript:history.back(-1);">
		</td>
	</tr>
</table>
</form>
<script>

function add(){
	var d = document.forms[0];
	if('' == d.title.value){
		alert("公告标题不能为空");
		return;
	}
	if('' == d.content.value){
		alert("公告内容不能为空");
		return;
	}
	<c:if test="${result != null}">
		d.action = "${ctx}/crosspay/noticinfo.do?method=update";
	</c:if>

	<c:if test="${result == null}">
		d.action = "${ctx}/crosspay/noticinfo.do?method=add";
	</c:if>
	d.submit();
}
</script>