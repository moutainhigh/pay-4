<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%
response.setHeader("Pragma", "No-cache"); 	
response.setHeader("Cache-Control", "no-cache"); 	
response.setDateHeader("Expires", 0); 
%>
<script type="text/javascript">
<!--
	$(function(){
		$("#backBtn").click(function(){
			window.location = "${ctx}/rm-blackandwhite-blacklist.htm";
		});

		$("#submitBtn").click(function(){
			if(!validate())
				return false;
		});
		
		var msg = "${msg}";
		if('' != msg)
			alert(msg);

		function validate() {
			var business_name = $.trim($("#business_name").val());//公司全称
			var true_name = $.trim($("#true_name").val());//用户名称
			var business_id = $.trim($("#business_id").val());//商户ID
			var bank_account = $.trim($("#bank_account").val());//绑定银行账号

			if('' == business_name){
				alert("公司全称为必填项!");
				return false;
			}
			if('' == true_name){
				alert("用户名称为必填项!");
				return false;
			}
			if('' == business_id){
				alert("商户ID为必填项!");
				return false;
			}
			if('' == bank_account){
				alert("绑定银行账号为必填项!");
				return false;
			}
			if(!s_isNumber(business_id)){
				alert("商户ID必须为数字!");
				return false;
			}
			if(!s_isNumber(bank_account)){
				alert("绑定银行账号必须为数字!");
				return false;
			}
			return true;
		}
	});
//-->
</script>
<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">添加黑名单</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>

<form action="rm-blackandwhite-blacklist.htm?method=blacklistCreate" method="post" id="whiteListAddSave" name="whiteListAddSave">
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
    <tr class="trForContent1">
   	  	<td class=border_top_right4 align="right" >公司全称：</td>
      	<td class="border_top_right4" >
        	<input type="text"  name="business_name" id="business_name" />
      	</td>
    </tr>
    <tr class="trForContent1">
   	  	<td class=border_top_right4 align="right" >用户名称：</td>
      	<td class="border_top_right4" >
        	<input type="text"  name="true_name" id="true_name" />
      	</td>
    </tr>
  	<tr class="trForContent1">
   	  	<td class=border_top_right4 align="right" >商户ID：</td>
      	<td class="border_top_right4" >
        	<input type="text"  name="business_id" id="business_id" />
      	</td>
    </tr>
     	<tr class="trForContent1">
   	  	<td class=border_top_right4 align="right" >绑定银行账号：</td>
      	<td class="border_top_right4" >
        	<input type="text"  name="bank_account" id="bank_account" />
      	</td>
    </tr>
    
    <tr class="trForContent1">
      <td align="center" class="border_top_right4" colspan="4">
      <input type="submit"  name="submitBtn" id="submitBtn" value="添 加" class="button2">
      <input type="button"  id="backBtn" value="返回" class="button2">
      </td>
    </tr>
  </table>
 </form>