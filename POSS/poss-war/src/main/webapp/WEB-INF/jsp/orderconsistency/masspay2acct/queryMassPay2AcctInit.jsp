<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@page import="java.util.Date"%>
<html>
	<head>
		<title>批量付款到银行补单管理</title>
		<script type="text/javascript">
			var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
			
			function consMassPay2AcctQuery(pageNo,totalCount,pageSize) {
				
				var startDate = $("#startDate").val();
				var endDate = $("#endDate").val();
				var batchNum = $("#batchNum").val();
				var payerMember = $("#payerMember").val();

				if('' == startDate){
					$.fo.alert("起始时间不能为空!");
					return;
				}
				if('' == endDate){
					$.fo.alert("结束时间不能为空!");
					return;
				}

				if('' == batchNum){
					$.fo.alert("批次号不能为空!");
					return;
				}
				if('' == payerMember){
					$.fo.alert("付款会员不能为空!");
					return;
				}
				
				$('#infoLoadingDiv').dialog('open');
				var pars = $("#userSearchForm").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
				$.ajax({
					type: "POST",
					url: "${ctx}/consistent.masspay2acct.htm?method=search",
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
		<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
			<tr>
				<td height="18">
					<div align="center">
						<font class="titletext">批量付款到账户未生成子订单明细列表</font>
					</div>
				</td>
			</tr>
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
		</table>
		
		<form id="userSearchForm" name="userSearchForm" method="post" action="">
		<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >批次号：</td>
				<td class="border_top_right4"><input type="text" name="batchNum" id="batchNum" style="width: 150px;" /></td>
				<td class="border_top_right4" align="right" >付款会员号：</td>
				<td class="border_top_right4"><input type="text" name="payerMember" id="payerMember" style="width: 150px;" /></td>
			</tr>
			<tr class="trForContent1">
	   	  	<td class=border_top_right4 align="right" >交易时间：</td>
	      	<td class="border_top_right4" colspan="3" align="left">
	        	<input type="text" name="startDate" id="startDate" value="${startDate}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'%y-%M-{%d-30}',maxDate:'#F{$dp.$D(\'endDate\');}'})" class="Wdate"/>
	      	至
	        	<input type="text" name="endDate" id="endDate" value="${endDate}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'startDate\');}',maxDate:'%y-%M-%d'})" class="Wdate"/>
	     	</td>
	    </tr>
			<tr class="trForContent1">
				<td class="border_top_right4" align="center" colspan="4">
					<input type="button" onclick="consMassPay2AcctQuery()" name="submitBtn" value="查   询" class="button2">
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
		
