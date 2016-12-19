<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
	<script type="text/javascript">
		function processBack(){
			location.href = "context_fundout_checklog.controller.htm";
		}

		function submitCheck(){
			var checkRemark = $("#checkRemark").val();
			if($.trim(checkRemark).length < 1){
				alert("审核备注不能为空");
				$("#checkRemark").focus();
				return;
			}
			var url = "${ctx}/context_fundout_checklog.controller.htm";
			var data = "method=ajaxSubmit&recordNos=" + $("#recordNos").val() + "&checkRemark=" + $("#checkRemark").val();
			$.post(url, data, function(res){
				if(res == "yes"){
					alert("审核成功");
					$("#butSubmit1").attr("disabled", true);
				}else{
					alert("审核失败");
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
	<jsp:include page="detail.jsp" />
	<div>
		<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
			<tr>
				<td height="18">
					<div>
						<font class="titletext">待审队列</font>
					</div>
				</td>
			</tr>
		</table>
		<br>
		<table class="tablesorter" border="0" cellpadding="0" cellspacing="1">
			<thead>
				<tr>
					<th>进入日期</th>
					<th>交易号</th>
					<th>规则号</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="check" items="${checkList}" varStatus="v">
					<tr>
						<td>
							<fmt:formatDate value="${check.createDate}" pattern="yyyy-MM-dd HH:mm:ss" />
						</td>
						<td>
							${check.orderNo}
						</td>
						<td>
							${check.ruleCode}
						</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
	</div>
	<div>
		<form action="" method="post" id="mainfromId">
			<input type="hidden" name="recordNos" id="recordNos" value="${recordNos}" />
			<table class="border_all2" border="0" cellspacing="0" cellpadding="1" align="center">
				<tr class="trForContent1">
					<td align="right" class="border_top_right4">审核备注：</td>
					<td class="border_top_right4">
						<textarea name="checkRemark" id="checkRemark" rows="10" cols="50"></textarea>
					</td>
				</tr>
				<tr class="trForContent1">
					<td align="center" class="border_top_right4" colspan="2">
						<input type="button" name="butSubmit" id="butSubmit1" value="提  交" class="button2" onclick="submitCheck();">
						&nbsp;&nbsp;<input type="button" name="butSubmit" id="butSubmit2" value="返  回" class="button2" onclick="processBack();">
					</td>
				</tr>
			</table>
		</form>
	</div>
</body>

