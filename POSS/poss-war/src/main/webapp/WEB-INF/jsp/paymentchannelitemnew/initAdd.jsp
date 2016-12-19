<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	$(document).ready(function(){
		$("#btn").click(function(){
			if(checkInfo()){
				<c:if test="${edit== '1'}">
				var url="${ctx}/paymentChannelItemNew.htm?method=update&id=${channel.id}";
				</c:if>
				<c:if test="${add== '1'}">
				var url="${ctx}/paymentChannelItemNew.htm?method=add";
				</c:if>
				var data= $("#frm").serialize();
				$.post(url,data,function(res){
					if(2==res){
						alert('通道别名已存在!');
					}else{
						alert('操作成功');
						window.location.href="${ctx}/paymentChannelItemNew.htm?method=initSearch";
					}
					
				});
			}
		});
	});

	function checkInfo(){
		var code = $("#code").val();
		var name = $("#name").val();
		var rate = $("#rate").val();
		if(code == ""){
			alert("渠道code不能为空");
			$("#code").focus();
			return false;
		}
		if(name == ""){
			alert("渠道名称不能为空");
			$("#name").focus();
			return false;
		}
		if(rate == ""){
			alert("渠道费率不能为空");
			$("#rate").focus();
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
				<c:if test="${add== '1'}">
					<font class="titletext">支付通道新增</font>
				</c:if>
				<c:if test="${edit== '1'}">
					<font class="titletext">支付通道修改</font>
				</c:if>
			</div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form action="${ctx}/paymentchannelconfig.htm?method=add" method="post" id="frm">
	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">通道名称：</td>
			<td class="border_top_right4">
				<input type="text" name="itemName" id="itemName" value="${channel.itemName }" />
			</td>
			<td class=border_top_right4 align="right">通道别名：</td>
			<td class="border_top_right4">
				<input type="text" name="alias" id="alias" value="${channel.alias }" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">通道说明：</td>
			<td class="border_top_right4">
				<input type="text" name="description" id="description" value="${channel.description }" />
			</td>
			<td class=border_top_right4 align="right">状态：</td>
			<td class="border_top_right4" colspan="3">
				<select name="status">
					<option value="1" <c:if test="${channel.status== '1'}">selected</c:if>>启用</option>
					<option value="0" <c:if test="${channel.status== '0'}">selected</c:if>>禁用</option>
				</select>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">排序：</td>
			<td class="border_top_right4">
				<input type="text" name="serialNo" id="serialNo" value="${channel.serialNo }" />
			</td>
			<td class=border_top_right4 align="right">机构代码：</td>
			<td class="border_top_right4" colspan="3">
				<input type="text" name="orgcode" id="orgcode" value="${channel.orgcode }" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">通道服务地址：</td>
			<td class="border_top_right4">
				<input type="text" name="serviceUrl" id="serviceUrl" value="${channel.serviceUrl }" />
			</td>
			<td class=border_top_right4 align="right">所属渠道：</td>
			<td class="border_top_right4" colspan="3">
				<select name="paymentChannelNewId">
					<option value="">请选择</option>
					<c:forEach var="channel2" items="${paymentChannelList}" varStatus="v">
						<option value="${channel2.id}" <c:if test="${channel2.id== channel.paymentChannelNewId}">selected</c:if>>${channel2.name}</option>
					</c:forEach>
				</select>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">银行图标样式：</td>
			<td class="border_top_right4">
				<select name="labelClass" id="labelClass">
					<option value="">请选择</option>
					<option value="icbc" <c:if test="${channel.labelClass == 'icbc'}">selected='selected'</c:if>>中国工商银行</option>
					<option value="agricultural" <c:if test="${channel.labelClass == 'agricultural'}">selected='selected'</c:if>>中国农业银行</option>
					<option value="construction" <c:if test="${channel.labelClass == 'construction'}">selected='selected'</c:if>>中国建设银行</option>
					<option value="communications" <c:if test="${channel.labelClass == 'communications'}">selected='selected'</c:if>>交通银行</option>
					<option value="merchants" <c:if test="${channel.labelClass == 'merchants'}">selected='selected'</c:if>>招商银行</option>
					<option value="minsheng" <c:if test="${channel.labelClass == 'minsheng'}">selected='selected'</c:if>>民生银行</option>
					<option value="post" <c:if test="${channel.labelClass == 'post'}">selected='selected'</c:if>>中国邮政储蓄银行</option>
					<option value="china" <c:if test="${channel.labelClass == 'china'}">selected='selected'</c:if>>中国银行</option>
					<option value="china_citic" <c:if test="${channel.labelClass == 'china_citic'}">selected='selected'</c:if>>中信银行</option>
					<option value="everbright" <c:if test="${channel.labelClass == 'everbright'}">selected='selected'</c:if>>光大银行</option>
					<option value="huaxia" <c:if test="${channel.labelClass == 'huaxia'}">selected='selected'</c:if>>华夏银行</option>
					<option value="guangfa" <c:if test="${channel.labelClass == 'guangfa'}">selected='selected'</c:if>>广发银行</option>
					<option value="industrial" <c:if test="${channel.labelClass == 'industrial'}">selected='selected'</c:if>>兴业银行</option>
					<option value="spd" <c:if test="${channel.labelClass == 'spd'}">selected='selected'</c:if>>浦东发展银行</option>
					<option value="bjb" <c:if test="${channel.labelClass == 'bjb'}">selected='selected'</c:if>>北京银行</option>
				</select>
			</td>
			<td class=border_top_right4 align="right">所属类别：</td>
			<td class="border_top_right4" colspan="3">
				<select name="paymentCatagory">
					<option value="">请选择</option>
					<c:forEach var="category" items="${categoryList}" varStatus="v">
						<option value="${category.id}" <c:if test="${category.id== channel.paymentCatagory}">selected</c:if>>${category.description}</option>
					</c:forEach>
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