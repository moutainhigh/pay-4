<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
<%--引入用于格式化页面的CSS文件--%>
<link rel="stylesheet" href="./css/main.css">
<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

<script language="javascript">
//跳回编辑页面
function processSearch(id){
	location.href = "paymentChannelSearch.do";
}

</script>
<script language="javascript"><!--

//页面validate
$(document).ready(function(){

	//注册validate函数
	jQuery.validator.addMethod("isValidName", function(value, element) {
		  value = jQuery.trim(value);
		  var length = value.length;
		  return this.optional(element) || (/[\u4e00-\u9fa5a-zA-z0-9]$/.test(value));    
		}, "请输入字母、汉字或数字组合!"); 

	
	//为paymentChannelFormBean注册validate函数
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
		<div align="center"><font class="titletext"> 支 付 渠 道 修 改 </font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>

<form id="paymentChannelFormBean" name="paymentChannelFormBean" action="paymentChannelEdit.do" method="post" >
	<input type="hidden" name="channelID" value="${paymentChannelInit.id}"/>
	<table class="border_all2" width="95%" border="0" cellspacing="0"
		cellpadding="1" align="center">		
			
			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >渠道名称：</td>
				<td class="border_top_right4" align="left" >
					<input type="text" id="channelName" name="channelName" value="${paymentChannelInit.name}"/>
				</td>
				<td class="border_top_right4" align="right" >渠道描述：</td>
				<td class="border_top_right4" align="left" >
					<input type="text" id="channelDescription" name="channelDescription" value="${paymentChannelInit.description}"/>
				</td>
			</tr>		
				
			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >排列序号：</td>
				<td class="border_top_right4" align="left" >
					<input type="text" id="serialNo" name="serialNo" value="${paymentChannelInit.serialNo}"/>
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
						<c:forEach items="${ChannelStatusList}" var="ChannelStatusStatus" >
							<option value="${ChannelStatusStatus.code}"	
								<c:if test="${ChannelStatusStatus.code == paymentChannelInit.status}"> selected="selected" </c:if> 	
								>
								${ChannelStatusStatus.description}
							</option>
						</c:forEach>
					</select>	
				</td>		
				<td class="border_top_right4" align="right" >&nbsp; </td>
				<td class="border_top_right4" align="left" >&nbsp; </td>								
			</tr>			
							
			<tr class="trForContent1">		
				<td class="border_top_right4" colspan="6" align="center">
					<input type="submit" value="确认修改" >
					<input type="button" value="返回 "  onClick="javascript:processSearch(${paymentChannelInit.id})">
				</td>
			</tr>
	</table>
</form>


<div id="resultListDiv" class="listFence"></div>		
<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>
</body>

