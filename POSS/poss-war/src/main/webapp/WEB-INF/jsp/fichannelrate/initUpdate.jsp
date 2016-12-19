<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	$(document).ready(function(){
		$("#btn").click(function(){
			if(checkInfo()){
				$("#frm").submit();
			}
		});

		$("#btn2").click(function(){
			document.forms[0].action="channelrate.htm?method=initSearch";
			document.forms[0].submit();
		});
	});

	function checkInfo(){
		var reg1 = /^\d{1,4}$/;
		var orgCostRate = $("#orgCostRate").val();
		if(orgCostRate == "" || !(reg1.test(orgCostRate))){
			alert("费率不能为空并且必须是1-4位正整数");
			$("#orgCostRate").focus();
			return false;
		}
		return true;
	}
</script>

<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
			<div align="center">
				<font class="titletext">消费卡渠道成本费率修改</font>
			</div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form action="${ctx}/channelrate.htm?method=update" method="post" id="frm">
	<input type="hidden" name="id" id="id" value="${dto.id}" />
	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">支付渠道：</td>
			<td class="border_top_right4">
				<c:forEach items="${channels}" var="channel">
					<c:if test="${dto.paymentChannelId == channel.id}">${channel.description}</c:if>
				</c:forEach>
			</td>
			<td class=border_top_right4 align="right">渠道项目：</td>
			<td class="border_top_right4">
				<c:forEach items="${channelItems}" var="item">
					<c:if test="${dto.paymentChannelItemId == item.id}">${item.itemName}</c:if>
				</c:forEach>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">原始码：</td>
			<td class="border_top_right4">
				${dto.orgCode}
			</td>
			<td class=border_top_right4 align="right">费率：</td>
			<td class="border_top_right4">
				<input type="text" name="orgCostRate" id="orgCostRate" value="${dto.orgCostRate}" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">状态：</td>
			<td class="border_top_right4" colspan="3">
				<c:if test="${dto.disableFlag == '1'}">启用</c:if>
				<c:if test="${dto.disableFlag == '0'}">启用</c:if>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">备注：</td>
			<td class="border_top_right4" colspan="3">
				<input type="text" name="remark" size="80" value="${dto.remark}" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td align="center" colspan="4">
				<input id="btn" type="button" class="button2" value="修改" />
				<input id="btn2" type="button" class="button2" value="返回" />
			</td>
		</tr>
		<c:if test="${not empty info}">
			<li style="color: red;">${info}</li>
		</c:if>
	</table>
</form>