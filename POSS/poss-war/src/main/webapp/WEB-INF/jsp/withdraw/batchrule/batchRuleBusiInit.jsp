<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>批次规则业务查询</title>
		<script type="text/javascript">
			var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";

			function query(pageNo,totalCount,pageSize) {
				$('#infoLoadingDiv').dialog('open');
				var pars = $("#frm").serialize()+ "&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
					$.ajax({
						type: "POST",
						url: "${ctx}/batchRuleConfigController.do?method=queryBusiList",
						data: pars,
						success: function(result) {
							$('#infoLoadingDiv').dialog('close');
							$('#resultListDiv').html(result);
						}
					});		
			}

			function deleteBusi(seqId){
				var url = "${ctx}/batchRuleConfigController.do?method=deleteBusi";
				var pars = "id="+seqId;
				$.post(url,pars,function(res){
					if(res=="ok"){
						alert("操作已成功");
					} else {
						alert("操作失败");
					}
					query();
				});
			}

			$(document).ready(function(){
				query();

				$("#btn").click(function(){
					query();
				});
			});
		</script>
	</head>
	<table width="25%" border="0" cellspacing="0" cellpadding="0"
		align="center" height="21">
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
		<tr>
			<td height="18">
			<div align="center"><font class="titletext">查询批次规则业务</font></div>
			</td>
		</tr>
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
	</table>
	<form id="frm" method="post" action="#">
		<table width="600">
			<tr>
				<td class="textRight">批次规则名称：</td>
				<td class="textLeft">
					<select name="ruleConfigId" id="ruleConfigId">
						<option value="-1">请选择</option>
						<c:forEach var="ruleConfig" items="${ruleConfigList}">
							<option value="${ruleConfig.sequenceId}">${ruleConfig.batchRuleDesc}</option>
						</c:forEach>
					</select>
				</td>
				<td><input style="width: 100px;" type="button" value="查询" id="btn"/></td>
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