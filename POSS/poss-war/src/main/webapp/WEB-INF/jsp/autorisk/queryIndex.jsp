<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
	<script type="text/javascript">
		function search(){
			var memberCode = $("#memberCode").val();
			if($.trim(memberCode).length < 1){
				alert("会员号不能为空");
				return;
			}else if(isNaN(memberCode)){
				alert("会员号只能是数字");
				return;
			}
			
			$('#infoLoadingDiv').dialog('open');
			var pars = $("#mainfromId").serialize();
			$.ajax({
				type: "POST",
				url: "context_fundout_checklog.controller.htm?method=query",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
		}

		function searchIp(pageNo, totalCount, pageSize){
			$('#infoLoadingDiv').dialog('open');
			var pars = "code=" + $("#code").val() + "&pageNo=" + pageNo + "&totalCount=" + totalCount+"&pageSize=" + pageSize;
			$.ajax({
				type: "POST",
				url: "context_fundout_checklog.controller.htm?method=searchIp",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#ipDiv').html(result);
				}
			});
		}
	</script>
</head>

<body>
	<div>
		<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21" style="">
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
			<tr>
				<td height="18">
					<div align="center"><font class="titletext">查询账户综合信息</font></div>
				</td>
			</tr>
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
		</table>
		<form action="" method="post" id="mainfromId">
			<table class="border_all2" border="0" cellspacing="0" cellpadding="1" align="center">
				<tr class="trForContent1">
					<td align="right" class="border_top_right4">会员号：</td>
					<td class="border_top_right4">
						<input name="memberCode" id="memberCode" />
					</td>
<!--					<td align="right" class="border_top_right4">会员名称：</td>-->
<!--					<td class="border_top_right4">-->
<!--						<input name="memberName" id="memberName" />-->
<!--					</td>-->
				</tr>
				<tr class="trForContent1">
					<td align="center" class="border_top_right4" colspan="4">
						<input type="button" name="butSubmit" id="butSubmit1" value="查  询" class="button2" onclick="search();">
					</td>
				</tr>
			</table>
		</form>
	</div>
	
	<div id="resultListDiv" class="listFence"></div>

	<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
		<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中,请稍候...
	</div>
</body>

