<%@ page contentType="text/html;charset=UTF-8" language="java"%> 
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="js/common.js"></script>
<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
			<div align="center"><font class="titletext">查询消费卡渠道成本费率</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form action="" method="post" id="mainfromId" name="mainfrom">
	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">支付渠道：</td>
			<td class="border_top_right4">
				<select name="paymentChannelId" id="paymentChannelId">
					<option value="">--请选择--</option>
					<c:forEach items="${channels}" var="channel">
						<option value="${channel.id}">${channel.description}</option>
					</c:forEach>
				</select>
			</td>
			<td class=border_top_right4 align="right">渠道项目：</td>
			<td class="border_top_right4">
				<select name="paymentChannelItemId" id="paymentChannelItemId">
					<option value="">--请选择--</option>
					<c:forEach items="${channelItems}" var="item">
						<option value="${item.id}">${item.itemName}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">原始码：</td>
			<td class="border_top_right4">
				<input type="text" name="orgCode" />
			</td>
			<td class=border_top_right4 align="right">费率：</td>
			<td class="border_top_right4">
				<input type="text" name="orgCostRate" /> 
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">状态：</td>
			<td class="border_top_right4" colspan="3">
				<select name="disableFlag">
					<option value="1">启用</option>
					<option value="0">禁用</option>
				</select>
			</td>
		</tr>
		<tr class="trForContent1">
			<td align="center" class="border_top_right4" colspan="4">
				<input type="button" class="button2" name="executorsearchName" value="查 询" onclick="querysearch();"/>
				<input type="button" class="button2" name="executorsearchName" value="清 空" onclick="resetForm();"/>
			</td>
		</tr>
	</table>
</form>
<div id="resultListDiv" class="listFence"></div>
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>    
<script type="text/javascript">
	$(document).ready(function(){
		querysearch ();
	});

	function querysearch (pageNo, totalCount, pageSize) {
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize();
		var url = "channelrate.htm?method=search";
		if(null!=pageNo){
			url+="&pageNo="+pageNo+"&totalCount="+totalCount+"&pageSize="+pageSize;
		}
		$.ajax({
			type: "POST",
			url: url,
			data: pars,
			success: function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
	}

	function resetForm(){
		document.forms[0].reset();
		$("#paymentChannelId").val("");
		$("#paymentChannelItemId").val("");
		$("#orgCode").val("");
		$("#orgCostRate").val("");
		$("#disableFlag").val("1");
	}
</script>