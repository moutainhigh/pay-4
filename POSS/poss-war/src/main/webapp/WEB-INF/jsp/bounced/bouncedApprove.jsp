<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%@page import="java.util.Date"%>
<div id="queryDiv" class="listFence">
<h2 class="panel_title">拒付处理审核</h2>
<form action="" method="post" id="initForm">
	<table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">
		<tr class="trForContent1">
			<td class="border_top_right4">批次号：<input type="text"
				name="batchNo" id="batchNo" /></td>
			<td class="border_top_right4">商户会员号：<input type="text"
				name="partnerId" id="partnerId" /></td>
			<td class="border_top_right4">渠道订单号：<input type="text"
				name="channelOrderNo" id="channelOrderNo" /></td>
			<td class="border_top_right4">商户订单号：<input type="text"
				name="orderNo" id="orderNo" /></td>	
			<td class="border_top_right4">拒付订单号：<input type="text"
				name="orderId" id="orderId" /></td>	
      	</tr>	
		<tr class="trForContent1">
		<td class='border_top_right4' width='24%' align='left'>渠道： <select id="orgCode" name="orgCode" required="required">
					<option value="" selected>--请选择--</option>
					<option value="10076001">中银卡司</option>
					<option value="10079001">中银MOTO</option>
					<option value="10080001">中银MIGS</option>
					<option value="10003001">中国银行</option>
					<option value="10002001">农业银行</option>
					<option value="10075001">CREDORAX</option>
					<option value="10077001">Adyen</option>
					<option value="10077002">Belto</option>
					<option value="10077003">Cashu</option>
					<option value="99999999">内部调单</option>
			</select></td>
			<td class='border_top_right4' align='left'>拒付类型：<select
					id='bouncedType' name='bouncedType'>
						<option value='' selected>---请选择---</option>
						<option value='0'>拒付</option>
						<option value='1'>银行调单</option>
						<option value='2'>内部调单</option>
				</select></td>
			<td class='border_top_right4' align='left'>拒付状态：<select
					id='status' name='status'>
						<option value='' selected>---请选择---</option>
						<option value='3'>申诉失败待复核</option>
						<option value='4'>申诉成功待复核</option>
				</select></td>
      		<td class="border_top_right4" colspan="2">登记时间：
	      	<input class="Wdate" type="text" id="beginCreateDate" name="beginCreateDate" value='<fmt:formatDate value="${todayDate}" type="date" pattern="yyyy-MM-dd"/>'  onClick="WdatePicker()">
	      	至
	      	<input class="Wdate" type="text" id="endCreateDate" name="endCreateDate" value='<fmt:formatDate value="${todayDate}" type="date" pattern="yyyy-MM-dd"/>'  onClick="WdatePicker()">
      		</td> 
      	</tr>	
		<tr class="trForContent1">
			<td align="center" class="border_top_right4" colspan="5"><input
				type="button" onclick="javascript:query();" name="submitBtn" value="查  询"
				class="button2"></td>
		</tr>
	</table>
</form>
<c:if test="${not empty errorMsg}">
	<li style="color: red">${errorMsg }</li>
</c:if>

<div id="resultListDiv" class="listFence"></div>
</div>
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
			url : "bounced-register.do?method=bouncedApproveList",
			data : pars,
			success : function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
		//document.getElementById('initForm').submit();
	}
</script>