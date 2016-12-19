<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
	<title></title>
		<script type="text/javascript">
			var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
			function query(pageNo,totalCount,pageSize) {
				$('#infoLoadingDiv').dialog('open');
				var pars = $("#withdrawbackForm").serialize()+ "&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
					$.ajax({
						type: "POST",
						url: "${ctx}/userbindinfo.htm?method=query",
						data: pars,
						success: function(result) {
							$('#infoLoadingDiv').dialog('close');
							$('#resultListDiv').html(result);
						}
					});		
			}
			$(function(){
				query();
				$("#btn").click(function(){
					query();
				});
			});
		</script>
	</head>
<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
			<tr>
				<td height="18">
					<div align="center">
						<font class="titletext">绑定用户管理</font>
					</div>
				</td>
			</tr>
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
</table>
<form id="withdrawbackForm" method="post"  action="fundout.withdraw.refund.order.do?method=search">
		<table class="searchTermTable">
			<tr>
				<td class="textRight"> 手机号码：</td>
			   	<td class="textLeft"><input type="text" id ="mobile"   name="mobile" style="width: 150px;" /></td>
	   			<td class="textRight"> 身份证号：</td>
			   	<td class="textLeft"><input type="text" id ="idNo"   name="idNo" style="width: 150px;" /></td>
		   </tr>
		   <tr>
	   			<td class="textRight"> 姓      名：</td>
			   	<td class="textLeft"><input type="text" id ="name"   name="name" style="width: 150px;" /></td>	
	   			<td class="textRight" colspan="2"></td>
		   	</tr>
		   	<tr>
			<td class="submit" align="center"   >
			<input style="width: 100px;" type="button" value="查询" id="btn"/>
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