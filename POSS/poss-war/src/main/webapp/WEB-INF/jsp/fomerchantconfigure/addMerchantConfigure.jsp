<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
			<div align="center"><font class="titletext">新增付款API商户配置</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>

<script type="text/javascript">

	function goSubmit(){
		document.mainfrom.action="context_fundout_merchantconfigure.controller.htm?method=add";

		if($("input[name='merchantCode']").val() == ""){
			alert("请填写商户编号");
			return false;
		}
		
		if($("input[name='publicKey']").val() == ""){
			alert("请填写商户公钥!");
			return false;
		}
		
		var url="${ctx}/context_fundout_merchantconfigure.controller.htm";
		var data="method=checkRepeat&merchantCode=" + $("#merchantCode").val();
		$.post(url, data, function(res){
			if(res == "repeat"){
				alert("该商户编号对应的记录已存在，请重新填写");
			} else if(res == "yes"){
				document.mainfrom.submit();
			}
		});
	}
</script>

<form action="" method="post" name="mainfrom">
<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
	<tr class="trForContent1">
		<td align="right" class="border_top_right4">商户编号：</td>
		<td class="border_top_right4">
			<input type="text" name="merchantCode" id="merchantCode" value="" />
		</td>
	</tr>
	<tr class="trForContent1">
		<td align="right" class="border_top_right4">授权商户提交地址：</td>
		<td class="border_top_right4">
			<input type="text" name="authorizeAddress" value="" />
		</td>
	</tr>
	<tr class="trForContent1">
		<td class=border_top_right4 align="right">异步通知地址：</td>
		<td class="border_top_right4">
			<input type="text" name="notifyUrl" value="" />
		</td>
	</tr>
	<tr class="trForContent1">
		<td class=border_top_right4 align="right">商户公钥：</td>
		<td class="border_top_right4">
			<input type="text" name="publicKey" value="" />
		</td>
	</tr>
	<tr class="trForContent1">
		<td class=border_top_right4 align="right">是否异步通知：</td>
		<td class="border_top_right4">
			<select name="notifyFlag">
				<option value="0">不需要</option>
				<option value="1">需要</option>
			</select>
		</td>
	</tr>
	<tr class="trForContent1">
		<td class=border_top_right4 align="right">状态：</td>
		<td class="border_top_right4">
			<select name="status">
				<option value="1">正常</option>
				<option value="0">关闭</option>
			</select>
		</td>
	</tr>
	<tr class="trForContent1">
		<td align="center" colspan="2"><input type="button"
			onclick="goSubmit();" class="button2" value="新  增"></td>
	</tr>
	<c:if test="${not empty info}">
		<li style="color: red;">${info }</li>
	</c:if>
</table>
</form>
