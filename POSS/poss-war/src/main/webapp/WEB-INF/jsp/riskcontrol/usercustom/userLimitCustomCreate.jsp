<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/rmtaglibs.jsp"%>
<%
response.setHeader("Pragma", "No-cache"); 	
response.setHeader("Cache-Control", "no-cache"); 	
response.setDateHeader("Expires", 0); 
%>
<html>
<head> 
	<script type="text/javascript">
		$(function(){
			$("#businessLimitCustomCreateBtn").click(function(){
				if(!validate())
					return false;
			});
			
			var msg = "${msg}";
			if('' != msg)
				alert(msg);
		});
		function validate() {
			//var mcc = $.trim($("#mcc").val());//mcc
			var memberCode = $.trim($("#memberCode").val());//会员号
			var singleLimit = $.trim($("#singleLimit").val());//单笔限额
			var dayLimit = $.trim($("#dayLimit").val());//每日限额
			var monthLimit = $.trim($("#monthLimit").val());//每月限额
			var dayTimes = $.trim($("#dayTimes").val());//每日次数
			var monthTimes = $.trim($("#monthTimes").val());//每月次数
		//	var batchAccounts = $.trim($("#batchAccounts").val());//转出账户数
			
			if(memberCode == ''){
				alert("会员号为必填项!");
				return false;
			}
			if (!s_validateStrLength(memberCode, 2, 20)) {
				alert("会员号应在2~20个字符之间!");
				return false;
			}
	//		if('' == mcc){
	//			alert("MCC编码为必填项!");
	//		}
			if('' == singleLimit){
				alert("单笔限额为必填项!");
				return false;
			}
			if('' == dayLimit){
				alert("每日限额为必填项!");
				return false;
			}
			if('' == monthLimit){
				alert("每月限额为必填项!");
				return false;
			}
			if('' == dayTimes){
				alert("每日次数为必填项!");
				return false;
			}
			if('' == monthTimes){
				alert("每月次数为必填项!");
				return false;
			}

			if(!s_isNumber(memberCode)){
				alert("会员号必须为数字!");
				return false;
			}
			if(!s_isNumber(singleLimit)){
				alert("单笔限额必须为数字!");
				return false;
			}
			if(!s_isNumber(dayLimit)){
				alert("每日限额必须为数字!");
				return false;
			}
			if(!s_isNumber(monthLimit)){
				alert("每月限额必须为数字!");
				return false;
			}
			if(!s_isNumber(dayTimes)){
				alert("每日次数必须为数字!");
				return false;
			}
			if(!s_isNumber(monthTimes)){
				alert("每月次数必须为数字!");
				return false;
			}

			if(parseInt(singleLimit)>parseInt(dayLimit)||parseInt(singleLimit)>parseInt(monthLimit)||parseInt(dayLimit)>parseInt(monthLimit)){
				alert("必须符合单笔不大于每日不大于每月限额");	
				return false;
			}

			if(parseInt(dayTimes)>parseInt(monthTimes)){
				alert("每日次数不能大于每月次数");
				return false;
			}	
			if(singleLimit<0){
				alert("单笔限额必须大于零!");
				return false;
			}
			if(dayLimit<0){
				alert("每日限额必须大于零!");
				return false;
			}
			if(monthLimit<0){
				alert("每月限额必须大于零!");
				return false;
			}
			if(dayTimes<0){
				alert("每日次数必须大于零!");
				return false;
			}
			if(monthTimes<0){
				alert("每月次数必须大于零!");
				return false;
			}
			return true;
		}
	</script>
</head>
<body>
	<!-- <table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
		<tr>
			<td height="18">
				<div align="center">
					<font class="titletext">新增用户定制限额</font>
				</div>
			</td>
		</tr>
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
	</table> -->
	<h2 class="panel_title">新增用户定制限额</h2>
	
	<form method="post" action="${ctx}/rm_limit_userlimitcustom.htm?method=businessLimitCustomCreate">
	<table class="border_all2" width="75%" border="0" cellspacing="0" cellpadding="3" align="center">
		<tr class="trForContent1">
			<td align="right" class="border_top_right4">会员号:</td>
			<td class="border_top_right4">
				<input type="text" name="memberCode" id="memberCode" maxlength="19"/>
			</td>
			<td align="right" class="border_top_right4">业务类型:</td>
			<td class="border_top_right4">
				<li:codetable style="select" fieldName="businessType" attachOption="false" codeTableId="rmPersonBusiTypeTable" />
			</td>
		</tr>

		<tr class="trForContent1">
			<td align="right" class="border_top_right4">单笔限额:</td>
			<td class="border_top_right4">
				<input type="text" name="singleLimit" id="singleLimit" maxlength="19"/>
			</td>
			<td align="right" class="border_top_right4">每日限额:</td>
			<td class="border_top_right4">
				<input type="text" name="dayLimit" id="dayLimit" maxlength="19"/>
			</td>
		</tr>
		<tr class="trForContent1">
			<td align="right" class="border_top_right4">每月限额:</td>
			<td class="border_top_right4">
				<input type="text" name="monthLimit" id="monthLimit" maxlength="19"/>
			</td>
			<td align="right" class="border_top_right4">每日次数:</td>
			<td class="border_top_right4">
				<input type="text" name="dayTimes" id="dayTimes" maxlength="8"/>
			</td>
		</tr>
		<tr class="trForContent1">
			<td align="right" class="border_top_right4">每月次数:</td>
			<td class="border_top_right4">
				<input type="text" name="monthTimes" id="monthTimes" maxlength="8"/>
			</td>
			<td align="right" class="border_top_right4">状态:</td>
			<td class="border_top_right4" colspan="3">
				<select name="status">
					<option value="0" >无效</option>
					<option value="1" selected>有效</option>
				</select>
			</td>
		</tr>
		<tr class="trForContent1" align="center">
				<td class="border_top_right4" colspan="4">
					<input type="hidden" name="batchAccounts" value="0"/>
					<input type="hidden" name="mcc" value="0"/>
				 	<input type="submit" name="Submit" class="button2" id="businessLimitCustomCreateBtn" value="提交">
				</td>
			</tr>

	</table>
	<!-- <br>
	<table class="border_all4" width="75%" border="0" cellspacing="0" cellpadding="0" align="center" id="buttonDisplay">
			<tr class="trForContent1" align="center">
				<td class="border_top_right4" colspan="4">
					<input type="hidden" name="batchAccounts" value="0"/>
					<input type="hidden" name="mcc" value="0"/>
				 	<input type="submit" name="Submit" id="businessLimitCustomCreateBtn" value="提交">
				</td>
			</tr>
	  </table> -->
	</form>
	
</body>
</html>