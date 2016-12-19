<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	$(document).ready(function(){
		$("#btn").click(function(){
				<c:if test="${edit== '1'}">
				var	url="${ctx}/defaultChannelItem.htm?method=update&id=${channel.id}";
				</c:if>
				<c:if test="${add== '1'}">
				var url="${ctx}/defaultChannelItem.htm?method=add";
				</c:if>
				var data= $("#frm").serialize();
				$.post(url,data,function(res){
					if(res==1) {
						
					alert('操作成功');
					window.location.href="${ctx}/defaultChannelItem.htm?method=initSearch";
					}else {
						alert('该默认通道已存在');
					}
				});
		});
	});

</script>

<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
			<div align="center">
				<c:if test="${add== '1'}">
					<font class="titletext">默认通道新增</font>
				</c:if>
				<c:if test="${edit== '1'}">
					<font class="titletext">默认通道修改</font>
				</c:if>
			</div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form action="${ctx}/defaultChannelItem.htm?method=add" method="post" id="frm">
	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">收款类型：</td>
			<td class="border_top_right4">
				<select name="paymentType">
					<option value="1" <c:if test="${channel.paymentType== '1'}">selected</c:if>>充值</option>
					<option value="2" <c:if test="${channel.paymentType== '2'}">selected</c:if>>支付</option>
					<option value="3" <c:if test="${channel.paymentType== '3'}">selected</c:if>>直连</option>
				</select>
			</td>
			<td class=border_top_right4 align="right">通道名称：</td>
			<td class="border_top_right4">
				<select name="channelItemId">
					<c:forEach var="channel2" items="${itemList}" varStatus="v">
						<option value="${channel2.id}" <c:if test="${channel2.id== channel.channelItemId}">selected</c:if>>${channel2.itemName }</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">会员类型：</td>
			<td class="border_top_right4" colspan="1">
				<select name="memberType">
					<option value="2" <c:if test="${channel.memberType== '2'}">selected</c:if>>企业</option>
					<option value="1" <c:if test="${channel.memberType== '1'}">selected</c:if>>个人</option>
				</select>
			</td>
		</tr>
		<tr class="trForContent1">
			<td align="center" colspan="4">
				<input id="btn" type="button" class="button2" value="保存">
				<input type="button" class="button2" onclick="window.history.go(-1);" value="返回">
			</td>
		</tr>
	</table>
</form>