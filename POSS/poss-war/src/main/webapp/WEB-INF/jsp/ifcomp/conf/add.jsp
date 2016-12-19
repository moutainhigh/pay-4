<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%
response.setHeader("Pragma", "No-cache"); 	
response.setHeader("Cache-Control", "no-cache"); 	
response.setDateHeader("Expires", 0); 
%>
 
<html>
<head>
	<script type="text/javascript">
	$(function(){
		
		var validateKey = function(){
			var groupCode = $.trim($("#groupCode").val());
			var key = $.trim($("#key").val());
			$("#businessCreateBtn").disabled = true;
			if('' != key){
				var pars = "key=" + key;
				if(groupCode != ''){
					pars += "&groupCode=" + groupCode;
				}
				$.ajax({
					type: "POST",
					url: "${ctx}/if_comp/if_config_action.do?method=checkKey",
					data: pars,
					success: function(result) {
						if (result == 'true') {
							alert("对应的配置已经存在！");
							$("#businessCreateBtn").disabled = false;
						}else{
							$("#businessForm").submit();
						}
					}
				});
			}
		};
		
		$("#businessCreateBtn").click(function(){
			if(!validate()){
				return false;
			}
			validateKey();
		});
		
		var msg = "${msg}";
		if('' != msg) alert(msg);
	});
	function validate() {
		var key = $.trim($("#key").val());//业务ID
		var value = $.trim($("#value").val());//业务名称
		if(key == ''){
			alert("键为必填项!");
			return false;
		}
		if(value == ''){
			alert("值为必填项!");
			return false;
		}
		return true;
	}
	</script>
</head>
<body>
<br>

	<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
		<tr>
			<td height="18">
				<div align="center">
					<font class="titletext">新增动态配置</font>
				</div>
			</td>
		</tr>
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
	</table>


	<form id="businessForm" method="post" action="${ctx}/if_comp/if_config_action.do?method=doAdd">
	<table class="border_all2" width="500" border="0" cellspacing="0" cellpadding="3" align="center">
		<tr class="trbgcolor10">
			<td class="border_top_right4" style="width:200px;">分组编码</td>
			<td class="border_top_right4" style="width:300px;">
				<input style="width:90%" type="text" name="groupCode" id="groupCode" value="" />
			</td>
		</tr>
		<tr>
			<td class="border_top_right4" style="width:200px;">键:</td>
			<td class="border_top_right4" style="width:300px;">
				 <input style="width:90%" type="text" name="key" id="key" value=""/>
			</td>
		</tr>
		<tr>
			<td class="border_top_right4" style="width:200px;">值:</td>
			<td class="border_top_right4" style="width:300px;">
				<input style="width:90%" type="text" name="value" id="value" value=""/>
			</td>
		</tr>
	</table>
	<br>
	<table class="border_all4" width="75%" border="0" cellspacing="0" cellpadding="0" align="center" id="buttonDisplay">
			<tr class="trbgcolor6" align="center">
				<td>
				 	<button  name="businessCreateBtn" id="businessCreateBtn" >提  交</button>
				</td>
			</tr>
	  </table>
	</form>
</body>
</html>