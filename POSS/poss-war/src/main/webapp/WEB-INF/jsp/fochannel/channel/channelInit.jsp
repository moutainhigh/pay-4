<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>渠道查询</title>
<script type="text/javascript">
	var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";

	function query(pageNo,totalCount,pageSize) {
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#frm").serialize()+ "&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
			$.ajax({
				type: "POST",
				url: "${ctx}/fundoutChannel.htm?method=queryChannelList",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});		
	}

	$(document).ready(function(){
		query();

		$("#btn").click(function(){
			query();
		});
	});
	
	function add(){
		window.location.href='${ctx}/fundoutChannel.htm';
	}
</script>
	</head>
	
	<h2 class="panel_title">渠道查询</h2>
	<form id="frm" method="post" action="#">
		<table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">
					   
			<tr class="trForContent1">
				<td class=border_top_right4 align="right" >出款渠道名称：</td>
				<td class=border_top_right4>
					<input type="text" name="channelName"/>
				</td>
			</tr>
			<tr class="trForContent1">
				<td width="50%" class=border_top_right4 align="right" >出款渠道：</td>
				<td class=border_top_right4>
					<select id="bankId" id="bankId">
						<option value="">--请选择--</option>
						<c:forEach items="${channels}" var="channel">
							<option value="${channel.bankId }">${channel.channelName}</option>
						</c:forEach>
					</select>
				</td>
			</tr>
			<tr class="trForContent1">
				<td class=border_top_right4 align="right" >出款方式：</td>
				<td class=border_top_right4>
					<li:codetable fieldName="modeCode" onChange="changeEnterprise();" style="select" attachOption="true" codeTableId="fundOutModeTable"></li:codetable>
				</td>
			</tr>
			<tr class="trForContent1">
				<td class=border_top_right4 align="right" >状态：</td>
				<td class=border_top_right4>
					<li:select name="status" itemList="${statusList}" />
				</td>
			</tr>
			<tr class="trForContent1">
				<td class=border_top_right4 colspan="2" align="center">
					<input class="button2" type="button" value="添加渠道" id="btn_add" onClick="add()"/>
					<input class="button2" type="button" value="查询" id="btn"/>
				</td>
			</tr>
		</table>
	</form>
	<div id="resultListDiv" class="listFence"></div>
	<div id="deleteRoleDiv" title="删除用户信息"></div>
	<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
	<div id="infoLoadingDiv" title="加载信息"
		style="display: none; width: 200px; padding-top: 30px; height: 70px;">
		<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中,
		请稍候...
	</div>
</html>