<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%@page import="java.util.Date"%>
<h2 class="panel_title">拒付批次查询</h2>
<form action="bounced-register.do?method=batchQuery" method="post" id="initForm">
	<table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">
		<tr class="trForContent1">
			<td class="border_top_right4">批次号：<input type="text"
				name="batchNo" id="batchNo" /></td>
			<td class="border_top_right4">登记时间：
	      	<input class="Wdate" type="text" id="beginCreateDate" name="beginCreateDate" value='<fmt:formatDate value="${todayDate}" type="date" pattern="yyyy-MM-dd"/>'  onClick="WdatePicker()">
	      	至
	      	<input class="Wdate" type="text" id="endCreateDate" name="endCreateDate" value='<fmt:formatDate value="${todayDate}" type="date" pattern="yyyy-MM-dd"/>'  onClick="WdatePicker()">
      		</td> 
			<td class="border_top_right4">最晚回复日：
	      	<input class="Wdate" type="text" id="beginLastDate" name="beginLastDate" value='<fmt:formatDate value="${todayDate}" type="date" pattern="yyyy-MM-dd"/>'  onClick="WdatePicker()">
	      	至
	      	<input class="Wdate" type="text" id="endLastDate" name="endLastDate" value='<fmt:formatDate value="${todayDate}" type="date" pattern="yyyy-MM-dd"/>'  onClick="WdatePicker()">
      		</td> 
      		<td class="border_top_right4">操作员：<input type="text"
				name="batchNo" id="batchNo" /></td>
      	</tr>	
		<tr class="trForContent1">
			<td align="center" class="border_top_right4" colspan="4"><input
				type="button" onclick="javascript:query();" name="submitBtn" value="查  询"
				class="button2"></td>
		</tr>
	</table>
</form>
<c:if test="${not empty errorMsg}">
	<li style="color: red">${errorMsg }</li>
</c:if>
<div id="resultListDiv" class="listFence"></div>
<div id="infoLoadingDiv" title="加载信息"
	style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中,
	请稍候...
</div>
<script type="text/javascript">
	$(document)
			.ready(
					function() {
						$('[data]')
								.keypress(
										function(e) {
											if (e.which == 8 || e.which == 0)
												return true;
											if (((e.which >= 45 && e.which <= 57)
													&& e.ctrlKey == false && e.shiftKey == false)
													|| e.which == 0
													|| e.which == 8) {
												if (e.which == 45) {
													return false;
												}
												return true;
											}
											return false;
										}).bind("paste", function() {
									return false;
								}).css({
									'imeMode' : "disabled",
									'-moz-user-select' : "none"
								});
					});

	function query(pageNo, totalCount, pageSize) {
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#initForm").serialize() + "&pageNo=" + pageNo
				+ "&totalCount=" + totalCount + "&pageSize=" + pageSize;
		$.ajax({
			type : "POST",
			url : "bounced-register.do?method=batchQuery",
			data : pars,
			success : function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
		//document.getElementById('initForm').submit();
	}

</script>