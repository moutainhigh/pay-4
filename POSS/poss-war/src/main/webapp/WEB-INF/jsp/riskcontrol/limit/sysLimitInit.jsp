<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
response.setHeader("Pragma", "No-cache"); 	
response.setHeader("Cache-Control", "no-cache"); 	
response.setDateHeader("Expires", 0); 
%> 
<head>
	<script type="text/javascript">
		function searchSysLimit(pageNo,totalCount,pageSize) {
			$('#infoLoadingDiv').dialog('open');
			var pars = $("#searchForm").serialize()+ "&pageNo=" + pageNo + "&totalCount=" + totalCount+"&pageSize=" + pageSize;

			$.ajax({
				type: "POST",
				url: "${ctx}/rm_limit_syslimit.htm?method=searchSysLimits",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
		}
		$(function(){searchSysLimit();});
	</script>
</head>

<body>
		<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
		<tr>
			<td height="18">
				<div align="center">
					<font class="titletext">直营限额查询</font>
				</div>
			</td>
		</tr>
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
	   </table>
	   
	   <form id="searchForm">
		<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
			<tr class="trForContent1">
				<td align="right" class="border_top_right4">流水号：</td>
				<td class="border_top_right4">
					<input type="text" name="sequenceId" style="width: 150px;" />
				</td>
				<td align="right" class="border_top_right4">直营业务：</td>
				<td class="border_top_right4">				
				<select name="sysBusiness">
					<option value="" selected>请选择</option>
					<c:forEach items="${businesslist}" var="business">
						<c:if test="${business.businessType ==2}">
						<option value="${business.businessId}">${business.businessName}</option></c:if>
					</c:forEach>
				</select></td>
				<td align="right" class="border_top_right4">用户等级：</td>
				<td class="border_top_right4">
				<select name="userLevel">
					<option value="" selected>选择用户等级</option>
					<c:forEach items="${userlevellist}" var="userlevel">
						<option value="${userlevel.userLevel}">${userlevel.levelName}</option>
					</c:forEach>
				</select>
				</td>
				<td align="right" class="border_top_right4">状态：</td>
				<td class="border_top_right4">
					<select name="status">
						<option value="0">无效</option>
						<option value="1" selected>有效</option>
					</select>
				</td>
				<td align="center" class="border_top_right4" colspan="4">
	      			<input type="button" onclick="searchSysLimit();" name="submitBtn" value="查  询" class="button2">
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

