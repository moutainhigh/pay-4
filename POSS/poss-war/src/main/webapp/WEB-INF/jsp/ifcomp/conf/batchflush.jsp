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
		
		$("#businessCreateBtn").click(function(){
			if(!validate()){
				return false;
			}
			$("#businessCreateBtn").disabled = true;
			$("#businessForm").submit();
		});
		
		var msg = "${msg}";
		if('' != msg) alert(msg);
	});
	
	function validate() {
		var key = $.trim($("#key").val());
		var groupCode = $.trim($("#groupCode").val());
		var msg = '';
		if(key != '' && groupCode != ''){
			msg = "您确定要刷新键为：【"+key+"】，分组编码为：【"+groupCode+"】的缓存吗？";
		}else if(key != '' && groupCode == ''){
			msg = "您确定要刷新键为：【" + key + "】，无分组的缓存吗？" 
		}else if(key == '' && groupCode != ''){
			msg = "您确定要刷新分组编码为：【"+groupCode+"】下所有缓存吗？";
		}else{
			msg = "您确定要刷新系统中所有缓存吗？";
		}
		if(confirm(msg)){
			return true;
		}
		return false;
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
					<font class="titletext">刷新缓存中的配置</font>
				</div>
			</td>
		</tr>
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
	</table>


	<form id="businessForm" method="post" action="${ctx}/if_comp/if_config_action.do?method=doBatchFlush">
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