<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>

<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";

$(document).ready(
		function(){
			indexQuery();
		}
);

function indexQuery(pageNo,totalCount,pageSize) {
	
	var memberCode = $("#partnerId").val();
	if(memberCode.length >0 && !(/\d{1,20}/.test(memberCode))){
		alert("商户号必须是数字");
		return false;
	}
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#searchFormBean").serialize() +"&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
	$.ajax({
		type: "POST",
		url: "${ctx}/conreg.do?method=searchConreg",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
} 

</script>
</head>

<body>

<table width="35%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" >
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">联合注册查询</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	
</table>

	<form id="searchFormBean">
		<table class="searchTermTable" class="inputTable" style="width:80%" >
			<tr>
				<th >交易流水号：<input type="text" id="conregOrderNo" name="conregOrderNo" style="width: 150px;" maxlength="20"/></th>
				<th >商户号：<input type="text" id="partnerId" name="partnerId" style="width: 150px;" maxlength="20"/></th>
				<th >状态：

					<select name="status">
						<option value="" selected="selected">全部</option>
						<option value="0">初始化</option>
						<option value="1">认证成功</option>
						<option value="2">认证失败</option>
					</select>
				</th>
			</tr>
			<tr>
				<th >身份证号码：<input type="text" id="idNumber" name="idNumber" style="width: 150px;" maxlength="18"/></th>
				<th >姓名：<input type="text" id="realName" name="realName" style="width: 150px;" maxlength="20"/></th>
			</tr>
			<tr ><td height="10"></td>
			</tr>
			<tr>
					<td  colspan="4" style="text-align: center;  ">
					<a href="#" class="dialog_link ui-state-default ui-corner-all" onclick="indexQuery()">
						<span class="ui-icon ui-icon-search"></span>&nbsp;查&nbsp;&nbsp;询&nbsp;
					</a>&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
		</table>
		</form>

<div id="resultListDiv" class="listFence"></div>		
<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>

</body>

