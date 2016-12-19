<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">修改付款API商户配置</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form action="context_fundout_merchantconfigure.controller.htm?method=modifyMerchantConfigure" method="post" name="mainfrom">
	<input name="id" id="id" type="hidden" value="${dto.id }" />
	 <table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >商户编号：</td>
	      <td class="border_top_right4">
	      	<input type="text" name="merchantCode" id="merchantCode" value="${dto.merchantCode}" />
	      </td>
	     </tr>
	    <tr class="trForContent1">
		<td align="right" class="border_top_right4">授权商户提交地址：</td>
		<td class="border_top_right4">
			<input type="text" name="authorizeAddress" value="${dto.authorizeAddress}" />
		</td>
	</tr>
	<tr class="trForContent1">
		<td class=border_top_right4 align="right">异步通知地址：</td>
		<td class="border_top_right4">
			<input type="text" name="notifyUrl" value="${dto.notifyUrl}" />
		</td>
	</tr>
	<tr class="trForContent1">
		<td class=border_top_right4 align="right">商户公钥：</td>
		<td class="border_top_right4">
			<input type="text" name="publicKey" value="${dto.publicKey}" />
		</td>
	</tr>
	<tr class="trForContent1">
		<td class=border_top_right4 align="right">是否异步通知：</td>
		<td class="border_top_right4">
			<select name="notifyFlag">
				<option <c:if test="${dto.notifyFlag == 0}">selected="selected"</c:if> value="0">不需要</option>
				<option <c:if test="${dto.notifyFlag == 1}">selected="selected"</c:if> value="1">需要</option>
			</select>
		</td>
	</tr>
	<tr class="trForContent1">
		<td class=border_top_right4 align="right">状态：</td>
		<td class="border_top_right4">
			<select name="status">
				<option <c:if test="${dto.status == 1}">selected="selected"</c:if> value="1">正常</option>
				<option <c:if test="${dto.status == 0}">selected="selected"</c:if> value="0">关闭</option>
			</select>
		</td>
	</tr>
	     <tr class="trForContent1">
	      <td class=border_top_right4 align="right">
	      	 <input type="button" id="submitBtn" class="button2" value="确  认" onclick="goSubmit();">
	      </td>
	      <td class=border_top_right4 align="left">
	      	 <input type="button" onclick="goBack();"  class="button2" value="返  回">
	      </td>
	    </tr>
	  </table>
	  
</form>

 <script type="text/javascript">
	
	function goBack() {
		document.mainfrom.action="context_fundout_merchantconfigure.controller.htm?method=initSearch";
		document.mainfrom.submit();
	}

	function goSubmit(){
		if($("input[name='merchantCode']").val() == ""){
			alert("请填写商户编号");
			return false;
		}
		
		if($("input[name='publicKey']").val() == ""){
			alert("请填写商户公钥!");
			return false;
		}
		var url="${ctx}/context_fundout_merchantconfigure.controller.htm";
		var data="method=checkRepeat&merchantCode=" + $("#merchantCode").val() + "&id=" + $("#id").val();
		$.post(url, data, function(res){
			if(res == "repeat"){
				alert("该商户编号对应的记录已存在，请重新填写");
			} else if(res == "yes"){
				document.mainfrom.submit();
			}
		});
	}
	
  </script>
 