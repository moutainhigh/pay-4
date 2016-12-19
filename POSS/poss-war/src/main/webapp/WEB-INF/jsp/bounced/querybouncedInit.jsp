<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%@page import="java.util.Date"%>
<h2 class="panel_title">拒付登记</h2>
<form action="" method="post" id="initForm">
	<table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">网关订单号：</td>
			<td class="border_top_right4"><input type="text"
				name="tradeOrderNo" id="tradeOrderNo" /></td>
			<td class=border_top_right4 align="right">渠道订单号：</td>
			<td class="border_top_right4"><input type="text"
				name="channelOrderNo" id="channelOrderNo" /></td>
			<td class=border_top_right4 align="right">银行卡号：</td>
			<td class="border_top_right4"><input type="text"
				name="cardNo" id="cardNo" /></td>	
			<td class=border_top_right4 align="right">授权码：</td>
			<td class="border_top_right4"><input type="text"
				name="authorisation" id="authorisation" /></td>	
			<td align="right" class="border_top_right4">交易时间：</td>
      		<td class="border_top_right4">
	      	<input class="Wdate" type="text" id="tranDate" name="tranDate" value='<fmt:formatDate value="${tranDate}" type="date" pattern="yyyy-MM-dd"/>'  onClick="WdatePicker()">
      		</td> 

		<tr class="trForContent1">
			<td align="center" class="border_top_right4" colspan="10"><input
				type="button" onclick="query();" name="submitBtn" value="查  询"
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
		var reg = /^[0-9]*$/;
		var tradeOrderNo = $("#tradeOrderNo").val();
		var channelOrderNo = $("#channelOrderNo").val();
		var cardNo = $("#cardNo").val();
		var tranDate = $("#tranDate").val();
		var authorisation = $("#authorisation").val();
		
		if (!reg.test(tradeOrderNo) && tradeOrderNo != '') {
			alert('网关订单号格式错误');
			return false;
		}
		if (!reg.test(cardNo) && cardNo != '') {
			alert('卡号格式错误');
			return false;
		}
		if (!reg.test(channelOrderNo) && channelOrderNo != '') {
			alert('渠道订单号格式错误');
			return false;
		}

		if(channelOrderNo == ''&& cardNo=='' && tradeOrderNo=='' && tranDate=='' && authorisation=='' )
		{
			alert('请输入查询条件！');
			return false;
		}	
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#initForm").serialize() + "&pageNo=" + pageNo
				+ "&totalCount=" + totalCount + "&pageSize=" + pageSize;
		$.ajax({
			type : "POST",
			url : "bounced-register.do?method=query",
			data : pars,
			success : function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
	}

</script>