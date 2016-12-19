<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">批量导入拒付</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form action="${ctx}/chargeBackImport.do?method=importFile" method="post" name="addFrom" id="addFrom" onsubmit="return importFile()" enctype="multipart/form-data">
<table class="border_all2" width="500" border="0" cellspacing="0" cellpadding="3" align="center">
	<tr class="trForContent1">
		<td align="right" class="border_top_right4" nowrap="nowrap">渠道：</td>	
		<td class="border_top_right4" align="left" style="width: 100px;">
			<select id="orgCode" name="orgCode">
				<option value="" selected>---请选择---</option>
				<c:forEach var="channel" items="${paymentChannelItems}" varStatus="v">
					<option value="${channel.orgCode}">${channel.name}</option>
				</c:forEach>
			</select>
		</td>
		<td align="right" class="border_top_right4" nowrap="nowrap">拒付类型：</td>	
		<td class="border_top_right4" align="left" style="width: 100px;">
			<select name="cpType" id="cpType">
	      		<option value="">请选择</option>
	      		<option value="1">内部调查单</option>
	      		<option value="2">银行调查单</option>
	      		<option value="3" selected="selected">拒付单</option>
	      	</select>
		</td>
		<td align="right" class="border_top_right4" style="width: 80px;" nowrap="nowrap">拒付文件：</td>
		<td class="border_top_right4" align="left" style="width: 100px;">
			<input type="file" id="file" name="file">
		</td>
	</tr>
	
	<tr class="trForContent1">
		<td class="border_top_right4" colspan="6" align="center">
			<input type="submit" name="butSubmit" value="确认导入" class="button2">
			<input type="button" name="butSubmit" value="模版下载" class="button2" onclick="download();">
		</td>
	</tr>
	
</table>
</form>

<script>

function download(){
	window.location.href="${ctx}/chargeBackImport.do?method=downloadTemplate";
}

function importFile() {
	
	var orgCode = $('#orgCode').val();
	var cpType = $('#cpType').val();
	var file = $('#file').val();
	
	if('' == orgCode){
		alert('请选择渠道');
		$('#orgCode').focus();
		return false;
	}
	
	if('' == cpType){
		alert('请选择类别');
		$('#cpType').focus();
		return false;
	}
	

	if($.trim(file) == '')
	{
		alert("请选择文件");
		return false;
	}

		return true;
	}
</script>