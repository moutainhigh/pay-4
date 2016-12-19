<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	function save(){
		var code=$("#code").val();
		var name=$("#name").val();
		if(code == ""){
			alert("编号不能为空！！");
			return;
		}
		if(name == ""){
			alert("名称不能为空！！");
			return ;
		}
		$("#frm").submit();		
	}



</script>

<h2 class="panel_title">渠道类别新增</h2>
<form action="${ctx}/channelCategory.htm?method=add" method="post" id="frm">
	<table class="border_all2" width="58%" border="0" cellspacing="0" cellpadding="1" align="center">
		<tr class="trForContent1">
			<td width="50%" class=border_top_right4 align="right">编号：</td>
			<td class="border_top_right4">
				<input type="text" name="code" id="code" value=""  style="width:150px;"/><font color="red">*</font>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">名称：</td>
			<td class="border_top_right4">
				<input type="text" name="name" id="name" value=""  style="width:150px;"/><font color="red">*</font>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">描述：</td>
			<td class="border_top_right4">
				<input type="text" name="description" id="description" value=""  style="width:150px;"/>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="center" colspan="4">
				<input id="btn" type="button"  onclick="save();" class="button2" value="保存">
				<input type="button" class="button2" onclick="window.history.go(-1);" value="返回">
			</td>
		</tr>
	</table>
</form>