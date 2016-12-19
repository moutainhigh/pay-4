<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
<%--引入用于格式化页面的CSS文件--%>
<link rel="stylesheet" href="./css/main.css">
<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

<script language="javascript"><!--

//页面validate
$(document).ready(function(){

	//注册validate函数
	jQuery.validator.addMethod("isValidName", function(value, element) {
		  value = jQuery.trim(value);
		  var length = value.length;
		  return this.optional(element) || (/[\u4e00-\u9fa5a-zA-z0-9]$/.test(value));    
		}, "请输入字母、汉字或数字组合!"); 
	
	$("#paymentChannelFormBean").validate({
		rules: { 
			channelName:{
				required:true,
				rangelength:[0,64],
				isValidName:true
				},
			channelDescription:{
				required:true,
				rangelength:[0,128],
				isValidName:true
				},
			serialNo:{
				required:true,
				digits:true,
				rangelength:[0,10]
				},
			operator:{
				required:true,
				rangelength:[0,32]
				},
			channelStatus:"required"
		}
	});
});

--></script>
</head>

<body>
	<br/><br/>
<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext"> 新 增 支 付 渠 道 </font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form id="paymentChannelFormBean" name="paymentChannelFormBean" action="paymentChannelAddtion.do" method="post">
	<table class="border_all2" width="95%" border="0" cellspacing="0"
		cellpadding="1" align="center">		
			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >渠道名称：</td>
				<td class="border_top_right4" align="left" >
					<input type="text" id="channelName" name="channelName" >
				</td>
				<td class="border_top_right4" align="right" >渠道描述：</td>
				<td class="border_top_right4" align="left" >
					<input type="text" id="channelDescription" name="channelDescription">
				</td>
			</tr>		
			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >排列序号：</td>
				<td class="border_top_right4" align="left" >
					<input type="text" id="serialNo" name="serialNo">
				</td>		
				
				<td class="border_top_right4" align="right" >操 作 员：</td>
				<td class="border_top_right4" align="left" >
					<input type="text" value="${userName}" disabled="disabled" >
					<input type="hidden"  id="operator" name="operator" value="${userName}" />
				</td>		
				
			</tr>		
			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >渠道状态：</td>
				<td class="border_top_right4" align="left" >
					<select id="channelStatus" name="channelStatus" size="1">
						<option value="" selected>---请选择---</option>
						<c:forEach items="${ChannelStatusList}" var="ChannelStatusStatus" >
							<option value="${ChannelStatusStatus.code}" >${ChannelStatusStatus.description}</option>
						</c:forEach>
					</select>	
				</td>		
				<td class="border_top_right4" align="right" >&nbsp; </td>
				<td class="border_top_right4" align="left" >&nbsp; </td>								
			</tr>			
			<tr class="trForContent1">		
				<td class="border_top_right4" colspan="6" align="center">
					<input type="submit" value="确认新增" >
				</td>
			</tr>
	</table>
</form>


</body>

