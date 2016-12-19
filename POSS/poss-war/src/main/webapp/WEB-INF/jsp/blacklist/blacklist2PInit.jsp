<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html>
	<head>
		<title>个人名单查询</title>
		<script type="text/javascript">
			var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
			$(document).ready(function(){query();});
			function query(pageNo,totalCount,pageSize) {
				$('#infoLoadingDiv').dialog('open');
				var pars = $("#form1").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount+"&pageSize="+pageSize;
				$.ajax({
					type: "POST",
					url: "${ctx}/blacklist.do?method=blacklist2PList",
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
		<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21"></table>
		
		<form id="form1">
		<table class="searchTermTable">
			<tr>
				<td class="textRight">姓名：</td>
				<td class="textLeft"><input type="text" name="xm" id="xm" style="width: 150px;" /></td>
				<td class="textRight">身份证号：</td>
				<td class="textLeft"><input type="text" name="gmsfhm" id="gmsfhm" style="width: 150px;" /></td>
				<td class="textRight">手机号：</td>
				<td class="textLeft"><input type="text" name="sjhm" id="sjhm" style="width: 150px;" /></td>
			</tr>
			<tr>
				<td class="textRight">录黑途径：</td>
				<td class="textLeft"><input type="text" name="lhtj" id="lhtj" style="width: 150px;" /></td>
				<td class="textRight">银行卡号：</td>
				<td class="textLeft"><input type="text" name="yhkh" id="yhkh" style="width: 150px;" /></td>
				<td class="textRight">邮箱：</td>
				<td class="textLeft"><input type="text" name="email" id="email" style="width: 150px;" /></td>
		     	
			</tr>
			<tr>
				<td class="textRight">名单类型：</td>
				<td class="textLeft">
					<select name="sjzt" id="sjzt">
						<option value="1">黑名单</option>
						<option value="2">灰名单</option>
					</select>
				</td>
				<td class="textRight">状态：</td>
				<td class="textLeft">
					<select name="status" id="status">
						<option value="">全部</option>
						<option value="0">正常</option>
						<option value="2">新建待审核</option>
						<option value="3">修改待审核</option>
						<option value="4">删除待审核</option>
						<option value="5">上传待审核</option>
						<option value="1">审核拒绝</option>
					</select>
				</td>
				<td class="textRight"></td>
				<td class="textLeft" >
					<a href="#" class="dialog_link ui-state-default ui-corner-all" onclick="query()">
						<span class="ui-icon ui-icon-search"></span>&nbsp;查&nbsp;&nbsp;询&nbsp;
					</a>&nbsp;&nbsp;&nbsp;
					<a href="${ctx}/blacklist.do?method=addBlacklist2PInit" class="dialog_link ui-state-default ui-corner-all">
						<span class="ui-icon ui-icon-newwin"></span>&nbsp;新增个人名单&nbsp;
					</a>&nbsp;&nbsp;&nbsp;
				</td>
			</tr>
		</table>
		</form>
		
		<div id="resultListDiv" class="listFence"></div>
		<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
		</div>
	</body>
</html>
		
