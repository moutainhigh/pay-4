<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	$(document).ready(function(){
		$("#btn").click(function(){
			if(checkInfo()){
				<c:if test="${edit== '1'}">
				var	url="${ctx}/paymentChannel.htm?method=update&id=${channel.id}";
				</c:if>
				<c:if test="${add== '1'}">
					var status=$("#status").val();
					if(status!=1){
						alert("不能添加禁用的渠道！！！");
						return;
					}
					var url="${ctx}/paymentChannel.htm?method=add";
				</c:if>
				var data= $("#frm").serialize();
				$.post(url,data,function(res){
					alert('操作成功');
					window.location.href="${ctx}/paymentChannel.htm";
				});
			}
		});
	});

	function checkInfo(){
		var code = $("#code").val();
		var name = $("#name").val();
	/* 	var rate = $("#rate").val(); */
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
		/* if(rate == ""){
			alert("渠道费率不能为空");
			$("#rate").focus();
			return false;
		} */
		return true;
	}
</script>

<h2 class="panel_title"><c:if test="${add== '1'}">
					<font class="titletext">支付渠道新增</font>
				</c:if>
				<c:if test="${edit== '1'}">
					<font class="titletext">支付渠道修改</font>
				</c:if></h2>
<form action="${ctx}/paymentchannelconfig.htm?method=add" method="post" id="frm">
	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">渠道Code：</td>
			<td class="border_top_right4">
				<input type="text" name="code" id="code" value="${channel.code }" />
			</td>
			<td class=border_top_right4 align="right">渠道名称：</td>
			<td class="border_top_right4">
				<input type="text" name="name" id="name" value="${channel.name }" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">渠道说明：</td>
			<td class="border_top_right4">
				<input type="text" name="description" id="description" value="${channel.description }" />
			</td>
	<%-- 		<td class=border_top_right4 align="right">渠道费率：</td>
			<td class="border_top_right4">
				<input type="text" name="rate" id="rate" value="${channel.rate }" />
			</td> --%>
			<td class=border_top_right4 align="right">状态：</td>
			<td class="border_top_right4">
				<select name="status" id="status">
					<option value="1" <c:if test="${channel.status== '1'}">selected</c:if>>启用</option>
					<option value="0" <c:if test="${channel.status== '0'}">selected</c:if>>禁用</option>
				</select>
			</td>
		</tr>
	<%-- 	<tr class="trForContent1">
			<td class=border_top_right4 align="right">渠道分类：</td>
			<td class="border_top_right4">
				<select name="paymentChannelCategory">
					<c:forEach items="${categoryList}" var="category">
					<option value="${category.id }" <c:if test="${channel.paymentChannelCategory== category.id}">selected</c:if>>${category.description }</option>
					</c:forEach>
				</select>
			</td>
			
		</tr> --%>
		<tr class="trForContent1">
			<td align="center" colspan="4"  class=border_top_right4>
				<input id="btn" type="button" class="button2" value="保存">
				<input type="button" class="button2" onclick="window.history.go(-1);" value="返回">
			</td>
		</tr>
	</table>
</form>