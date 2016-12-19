<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
<%--引入用于格式化页面的CSS文件--%>
<link rel="stylesheet" href="./css/main.css">
<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

<script language="javascript"><!--
//跳回编辑页面
function processSearch(id){
	location.href = "paymentChannelItemSearch.do";
}

//页面validate
$(document).ready(function(){

	//注册validate函数
	jQuery.validator.addMethod("isValidName", function(value, element) {
		  value = jQuery.trim(value);
		  var length = value.length;
		  return this.optional(element) || (/[\u4e00-\u9fa5a-zA-z0-9]$/.test(value));    
		}, "请输入字母、汉字或数字组合!"); 
	jQuery.validator.addMethod("isURL", function(value, element) {
		  value = jQuery.trim(value);
		  var length = value.length;
		  return this.optional(element) || (/^[a-zA-z]+:\/\/[^\s]*$/.test(value));    
		}, "请输入正确的URL!"); 
	
	$("#paymentChannelItemFormBean").validate({
		rules: { 
			itemName:{
				required:true,
				rangelength:[0,64],
				isValidName:true
				},
			alias:{
				required:true,
				rangelength:[0,8],
				isValidName:true
				},
			description:{
				required:true,
				rangelength:[0,128],
				isValidName:true
				},
			paymentCatagory:{
				required:true,
				rangelength:[0,32],
				isValidName:true
				},
			orgcode:{
				required:true,
				rangelength:[0,8],
				isValidName:true
				},
			serviceUrl:{
				required:true,
				rangelength:[0,256],
				isURL:true
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
			status:"required",
			paymentChannelID:"required"
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
		<div align="center"><font class="titletext"> 支 付 通 道 修 改 </font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form id="paymentChannelItemFormBean" name="paymentChannelItemFormBean" action="paymentChannelItemEdit.do" method="post" >

	<input type="hidden" name="id" value="${paymentChannelItemInit.id}"/>
	<table class="border_all2" width="95%" border="0" cellspacing="0"
		cellpadding="1" align="center">	

			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >支付通道名称：</td>
				<td class="border_top_right4" align="left" >
					<input type="text" id="itemName" name="itemName"  value="${paymentChannelItemInit.itemName}"/>
				</td>
				<td class="border_top_right4" align="right" >支付通道别名：</td>
				<td class="border_top_right4" align="left" >
					<input type="text" disabled="disabled" name="alias" value="${paymentChannelItemInit.alias}"/>
				</td>
			</tr>		
			
			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >支付通道描述：</td>
				<td class="border_top_right4" align="left" >
					<input type="text" id="description" name="description" value="${paymentChannelItemInit.description}"/>
				</td>
				<td class="border_top_right4" align="right" >支付通道种类：</td>
				<td class="border_top_right4" align="left" >
					<input type="text" id="paymentCatagory" name="paymentCatagory" value="${paymentChannelItemInit.paymentCatagory}"/>
				</td>
			</tr>		
			
			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >机构代码：</td>
				<td class="border_top_right4" align="left" >
					<input type="text" id="orgcode" name="orgcode" value="${paymentChannelItemInit.orgcode}"/>
				</td>
				<td class="border_top_right4" align="right" >ServiceURL：</td>
				<td class="border_top_right4" align="left" >
					<input type="text" id="serviceUrl" name="serviceUrl" value="${paymentChannelItemInit.serviceUrl}" />
				</td>
			</tr>		
			
			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >支付通道状态：</td>
				<td class="border_top_right4" align="left" >
					<select id="status" name="status" size="1">
						<c:forEach items="${ChannelStatusList}" var="ChannelStatusStatus" >
							<option  value="${ChannelStatusStatus.code}"  
								<c:if test="${ChannelStatusStatus.code == paymentChannelItemInit.status }"> selected="selected" </c:if>   
							>
								${ChannelStatusStatus.description}   
							</option>
						</c:forEach>
					</select>	
				</td>			
				<td class="border_top_right4" align="right" >所属支付渠道：</td>
				<td class="border_top_right4" align="left" >
					<select id="paymentChannelID" name="paymentChannelID" size="1">
						<c:forEach items="${paymentChannelDTOList}" var="paymentChannelStatus" >
							<option value="${paymentChannelStatus.id}" 
								<c:if test="${paymentChannelStatus.id == paymentChannelItemInit.paymentChannelID}">selected="selected" </c:if>
							>
								 ${paymentChannelStatus.name} 
							 </option>
						</c:forEach>
					</select>
				</td>							
			</tr>			
			
			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >排列序号：</td>
				<td class="border_top_right4" align="left" >
					<input type="text" id="serialNo" name="serialNo" value="${paymentChannelItemInit.serialNo}"/>
				</td>
				<td class="border_top_right4" align="right" >操 作 员：</td>
				<td class="border_top_right4" align="left" >
					<input type="text" value="${userName}" disabled="disabled" >
					<input type="hidden"  id="operator" name="operator" value="${userName}" />
				</td>
			</tr>		
			
			<tr class="trForContent1">		
				<td class="border_top_right4" colspan="6" align="center">
					<input type="submit" value="确认修改" >
					<input type="button" value="返回 "  onClick="javascript:processSearch(${paymentChannelItemInit.id})">
				</td>
			</tr>
	</table>	
</form>

</body>

