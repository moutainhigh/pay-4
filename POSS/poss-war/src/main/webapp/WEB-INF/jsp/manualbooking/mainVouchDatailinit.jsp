<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/js/common.js"></script>


<html>
<head>
<script type="text/javascript">
			var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";

				function isRightDateFormat(date) {
				if(date==null||date.length==0){
					return true;
				}
				var isRight = date.match(new RegExp("^(\\d{4})([-]?)(\\d{1,2})([-]?)(\\d{1,2})$"));
				if (isRight== null) {
					return false;
				}
				return true;
			}

			function userQuery(pageNo,totalCount) {
				var dateFrom = document.getElementById("dateFrom").value;
			  	if (!isRightDateFormat(dateFrom)) {
			  		alert("开始日期格式错误！应为YYYY-MM-DD.");
			  		return ;
			  	}
			  	
			  	var dateTo = document.getElementById("dateTo").value;
			  	if (!isRightDateFormat(dateTo)) {
			  		alert("结束日期格式错误！应为YYYY-MM-DD.");
			  		return ;
			  	}
			  	
			  	if (dateTo.length!=0&&dateFrom.length!=0&&isRightDateFormat(dateFrom)&&isRightDateFormat(dateTo)) {
			  		if(dateFrom>dateTo){
						alert("起始时间不应晚于结束时间");
						return;
					}
			  	}


			  	
				$('#infoLoadingDiv').dialog('open');
				
				var pars = $("#userSearchForm").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount;
				$.ajax({
					type: "POST",
					url: "${ctx}/manualbooking/vouchDatailInit.do?method=queryInfo",
					data: pars,
					success: function(result) {
						$('#infoLoadingDiv').dialog('close');
						$('#resultListDiv').html(result);
					}
				});
			}
			
			$(document).ready(function(){userQuery();});
			
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
					<font class="titletext">记账凭证查询</font>
				</div>
			</td>
		</tr>
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
	</table>
		
	<form id="userSearchForm">
		<table class="searchTermTable">
			<tr >
				
				<td class="textRight">科目编号：</td>
				<td class="textLeft"><input type="text" name="accountCode" style="width: 150px;" /></td>
				
				<td class="textRight">账号名称：</td>
				<td class="textLeft"><input type="text" name="accountName" style="width: 150px;" /></td>
				
				
				<td align="right" >时间范围：</td>
	      		<td >
			      	<input class="Wdate" type="text" id="dateFrom" name="dateFrom" value=''  onClick="WdatePicker()">
			        	～
					<input class="Wdate" type="text" id="dateTo" name="dateTo"  value='' onClick="WdatePicker()">
	      		</td>
	      		
	      		
				<td class="textRight">
					<a href="#" class="dialog_link ui-state-default ui-corner-all" onclick="userQuery()">
						<span class="ui-icon ui-icon-search"></span>&nbsp;查&nbsp;&nbsp;询&nbsp;
					</a>&nbsp;&nbsp;&nbsp;
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