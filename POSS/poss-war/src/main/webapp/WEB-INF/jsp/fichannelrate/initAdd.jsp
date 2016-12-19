<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	$(document).ready(function(){
		$("#btn").click(function(){
			if(checkInfo()){
				var url="${ctx}/channelrate.htm";
				var data="method=checkRepeat&paymentChannelId="+$("#paymentChannelId").val()+"&paymentChannelItemId="+$("#paymentChannelItemId").val();
				$.post(url,data,function(res){
					if(res == "repeat"){
						alert("该支付渠道、渠道项目组合已存在");
					}else{
						$("#frm").submit();
					}
				});
			}
		});
	});

	function checkInfo(){
		var paymentChannelId = $("#paymentChannelId").val();
		var paymentChannelItemId = $("#paymentChannelItemId").val();
		var orgCode = $("#orgCode").val();
		var orgCostRate = $("#orgCostRate").val();
		if(paymentChannelId == ""){
			alert("请选择支付渠道");
			$("#paymentChannelId").focus();
			return false;
		}
		if(paymentChannelItemId == ""){
			alert("请选择渠道项目");
			$("#paymentChannelItemId").focus();
			return false;
		}
		if(orgCode == ""){
			alert("原始码不能为空");
			$("#orgCode").focus();
			return false;
		}
		if(orgCostRate == ""){
			alert("费率不能为空");
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
				<font class="titletext">渠道成本费率新增</font>
			</div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form action="${ctx}/channelrate.htm?method=add" method="post" id="frm">
	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">支付渠道：</td>
			<td class="border_top_right4">
				<select name="paymentChannelId" id="paymentChannelId">
					<option value="">--请选择--</option>
					<c:forEach items="${channels}" var="channel">
						<option value="${channel.id}" <c:if test="${dto.paymentChannelId == channel.id}">selected="selected"</c:if>>${channel.description}</option>
					</c:forEach>
				</select>
			</td>
			<td class=border_top_right4 align="right">渠道项目：</td>
			<td class="border_top_right4">
				<select name="paymentChannelItemId" id="paymentChannelItemId">
					<option value="">--请选择--</option>
					<c:forEach items="${channelItems}" var="item">
						<option value="${item.id}" <c:if test="${dto.paymentChannelItemId == item.id}">selected="selected"</c:if>>${item.itemName}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">原始码：</td>
			<td class="border_top_right4">
				<input type="text" name="orgCode" id="orgCode" value="${dto.orgCode}" />
			</td>
			<td class=border_top_right4 align="right">费率：</td>
			<td class="border_top_right4">
				<input type="text" name="orgCostRate" id="orgCostRate" value="${dto.orgCostRate}" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">状态：</td>
			<td class="border_top_right4" colspan="3">
				<select name="disableFlag">
					<option value="1" <c:if test="${dto.disableFlag == '1'}">selected="selected"</c:if> >启用</option>
					<option value="0" <c:if test="${dto.disableFlag == '0'}">selected="selected"</c:if>>禁用</option>
				</select>
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
				<input id="btn" type="button" class="button2" value="保存">
			</td>
		</tr>
		<c:if test="${not empty info}">
			<li style="color: red;">${info}</li>
		</c:if>
	</table>
</form>