<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html>
	<head>
		<title>用户管理</title>
		<script type="text/javascript">
			var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
			
			function withdrawQuery(pageNo,totalCount,pageSize) {
				$('#infoLoadingDiv').dialog('open');
				var pars = $("#userSearchForm").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
				$.ajax({
					type: "POST",
					url: "${ctx}/withdrawResManualAudit.do?method=search",
					data: pars,
					success: function(result) {
						$('#infoLoadingDiv').dialog('close');
						$('#resultListDiv').html(result);
					}
				});
			}
			
			$(document).ready(function(){withdrawQuery();});

		</script>
	</head>

	<body>
		<h2 class="panel_title">查询出款批次数据</h2>
		
		<form id="userSearchForm">
		<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
			<tr class="trForContent1">
				<td class="textRight border_top_right4">批次号：</td>
				<td class="textLeft border_top_right4">
					<input  type="text"  name="batchNum" value='' style="width: 150px;" >
				</td>
				<td class="textRight border_top_right4">批次名称：</td>
				<td class="textLeft border_top_right4">
					<input  type="text"  name="batchName" value='' style="width: 150px;" >
				</td>
			</tr>
			<tr class="trForContent1"><!--
				<td class="textRight">交易号：</td>
				<td class="textLeft">
					<input  type="text"  name="tradeNo" value='' style="width: 150px;">
				</td>
				-->
				<td class="textRight border_top_right4">交易流水号：</td>
				<td class="textLeft border_top_right4">
					<input type="text"  name="sequenceId" value='' style="width: 150px;" >
				</td>
				<td class="textRight border_top_right4">银行名称：</td>
				 <td class="textLeft border_top_right4">
	        		<li:select name="bankKy" defaultStyle="true" itemList="${withdrawBankList}"/>
				</td>
			</tr>
				<tr class="trForContent1">
					<td class="textRight border_top_right4">业务类型：</td>
					<td class="textLeft border_top_right4">
					<select name="busiType" style="width: 150px;">
					<option value="">全部</option>
						<option value='0'>&nbsp;&nbsp;提现&nbsp;&nbsp;</option>
						<!--<option value='1'>批量出款</option>
						<option value='2'>信用卡还款</option>
						--><option value='3'>付款到银行卡</option>
						<option value='4'>批量付款到银行卡</option>
					</select>
					</td>
					<td class="textRight border_top_right4">&nbsp;</td>
				<td class="textLeft border_top_right4">
				&nbsp;
				</td>
				</tr>
			<tr class="trForContent1">
				<td class=" border_top_right4" align="center" colspan="4">
					<input type="button" onclick="withdrawQuery();" name="submitBtn" value="查    询" class="button2">	
					<!--
					<a href="#" class="dialog_link ui-state-default ui-corner-all">
						<span class="ui-icon ui-icon-newwin"></span>&nbsp;下&nbsp;&nbsp;载&nbsp;
					</a>
					 <a href="${ctx}/userAdd.do" class="dialog_link ui-state-default ui-corner-all">
						<span class="ui-icon ui-icon-newwin"></span>&nbsp;添加用户&nbsp;
					</a> -->
				</td>
			</tr>
		</table>
		</form>
		<div id="resultListDiv" class="listFence"></div>
		<div id="deleteRoleDiv" title="删除用户信息"></div>
		<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
		<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
		</div>
	</body>
</html>
		
