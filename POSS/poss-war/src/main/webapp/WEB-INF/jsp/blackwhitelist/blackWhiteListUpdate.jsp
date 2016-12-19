<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>

<link rel="stylesheet" href="./css/main.css">
<script src="./js/common.js"></script>
<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";


function closePage() {
	var listType = $("#listType").val();
	if(listType==1){
		window.location.href="${ctx}/whiteListSearch.do?method=blackWhiteListSearchView&listType="+listType;
	}
	window.location.href="${ctx}/blackWhiteListSearch.do?method=blackWhiteListSearchView&listType="+listType;
}
function checkIsExsit() {	
	if(!confirm("您确认要修改该条记录吗？")) {
		return false;
	}
	var content = document.getElementById('content').value ;
	var listType = document.getElementById('listType').value ;
	var businessTypeId = document.getElementById('businessTypeId').value ;
	var businessTypeIdFlag = document.getElementById('businessTypeIdFlag').value;
	var memberCodeFlag = document.getElementById('memberCodeFlag').value;
	var partType = document.getElementById('partType').value;
	
	var regCardNo = /^\d+$/;
	var regEmail=/^[\w\-\.]+@[\w\-\.]+(\.\w+)+$/;
	var regIp=/^(?:(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)\.){3}(?:25[0-5]|2[0-4][0-9]|[01]?[0-9][0-9]?)$/;
	
	if("" == trim(content)&&(businessTypeId!=11&&businessTypeId!=10)){
		alert("内容不能为空");
		return false;
	}
	
	if(businessTypeId==1){
		if(!regCardNo.test(content)){
			alert("卡号格式不正确");
			$("#content").val('');
			return false;
		}
	}
	
	if(businessTypeId==2&&partType==1){
		if(!regEmail.test(content)){
			alert("邮箱格式不正确");
			$("#content").val('');
			return false;
		}
	}
	
	if(businessTypeId==3&&partType==1){
		if(!regIp.test(content)){
			alert("IP格式不正确");
			$("#content").val('');
			return false;
		}
	}
	
	if(businessTypeId==10){
		var value1=$("#value1").val();
		var value2=$("#value2").val();
		
		if(""==value1){
			alert("请传入区段起始值");
			return false;
		}else if(!regCardNo.test(value1)){
			alert("区段起始值格式不正确");
			$("#value1").val('');
			return false;
		}
		
		if(""==value2){
			alert("请传入区段截止值");
			return false;
		}else if(!regCardNo.test(value2)){
			alert("区段截止值格式不正确");
			$("#value2").val('');
			return false;
		}
	}
	
	if(businessTypeId==11){
		var value1=$("#value1").val();
		var value2=$("#value2").val();
		
		if(""==value1){
			alert("请传入区段起始值");
			return false;
		}else if(!regIp.test(value1)){
			alert("区段起始值格式不正确");
			$("#value1").val('');
			return false;
		}
		
		if(""==value2){
			alert("请传入区段截止值");
			return false;
		}else if(!regIp.test(value2)){
			alert("区段截止值格式不正确");
			$("#value2").val('');
			return false;
		}
	}
	
	submitSave();
}
function submitSave() {
	document.getElementById('businessTypeForm').submit();
}
function trim(str){ //删除左右两端的空格
	return str.replace(/(^\s*)|(\s*$)/g, "");
}

$(function(){
	var value=$("#businessTypeId").children('option:selected').val();
	if(value==10||value==11){
		$("#quduan").show();
		$("#tr_content").hide();
	}
	
	$("#businessTypeId").change(function(){
		var value=$(this).children('option:selected').val();
		if(value==10||value==11){
			$("#quduan").show();
			$("#tr_content").hide();
			$("#partType").val("4");
		}else if(value==8||value==9){
			$("#partType").val("1");
		}else{
			$("#quduan").hide();
			$("#tr_content").show();
			$("#partType").val("1");
		}
	});
	
	$("#partType").change(function(){
		var value=$(this).children('option:selected').val();
		var businessTypeId = $("#businessTypeId").children('option:selected').val();
		if(value==4&&businessTypeId!=10&&businessTypeId!=11){
			alert("该业务类型不支持区段匹配模式");
			$(this).val("1");
		}else if(businessTypeId==10||businessTypeId==11){
			$(this).val("4");
		}else if(businessTypeId==8||businessTypeId==9){
			$(this).val("1");
		}
	});
});
</script>
</head>

<body >
 
<%-- <table width="700" border="0" cellspacing="0" cellpadding="0" align="center" height="21" >
	<tr>
		<td height="2" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">
		    <c:if test="${listType==2}">
		                    黑名单修改
		   </c:if>
		   <c:if test="${listType==1}">
		                    白名单修改
		   </c:if>
		</font></div>
		</td>
	</tr>
	<c:if test="${message!=null}">
		<tr>
			<td height="2" ><font color="red" >${message}</font></td>
		</tr>
	</c:if>
</table> --%>
<h2 class="panel_title"> <c:if test="${listType==2}">
		                    黑名单修改
		   </c:if>
		   <c:if test="${listType==1}">
		                    白名单修改
		   </c:if></h2>
		   <c:if test="${message!=null}">
		<p>
			<font color="red" >${message}</font>
		</p>
	</c:if>


<form id="businessTypeForm" name="businessTypeForm" method="post" <c:if test="${listType==2}">action="blackWhiteListUpdateSave.do"</c:if>
<c:if test="${listType==1}">action="whiteListUpdateSave.do"</c:if>>
	<input type="hidden" name="method" value="updateBlackWhiteListSave"/>
	<input type="hidden" name="businessTypeIdFlag" id="businessTypeIdFlag" value=""/>
	<input type="hidden" name="memberCodeFlag" id="memberCodeFlag" value=""/>
	
	<table class="border_all2" width="700" border="1" cellspacing="0" cellpadding="0" align="center">
		<tr class="trForContent1">
			<td class="border_top_right4"  align="right" >业务类型：<font color="red" >*</font></td>
			<td class="border_top_right4"  align="left">
				<select name="businessTypeId" id="businessTypeId">
				    <option value="1" <c:if test="${dto.businessTypeId==1}">selected</c:if> >卡号</option>
				    <option value="2" <c:if test="${dto.businessTypeId==2}">selected</c:if> >邮箱</option>
				    <option value="3" <c:if test="${dto.businessTypeId==3}">selected</c:if> >ＩＰ</option>
				    <option value="4" <c:if test="${dto.businessTypeId==4}">selected</c:if> >国家</option>
				    <option value="8" <c:if test="${dto.businessTypeId==8}">selected</c:if> >收货地址</option>
				    <option value="9" <c:if test="${dto.businessTypeId==9}">selected</c:if> >账单地址</option>
				    <option value="5" <c:if test="${dto.businessTypeId==5}">selected</c:if> >MAC地址</option>
				    <option value="6" <c:if test="${dto.businessTypeId==6}">selected</c:if> >手机号码</option>
				    <option value="7" <c:if test="${dto.businessTypeId==7}">selected</c:if> >证件号码</option>
				    <option value="10" <c:if test="${dto.businessTypeId==10}">selected</c:if> >卡Bin段</option>
				    <option value="11" <c:if test="${dto.businessTypeId==11}">selected</c:if> >IP地址段</option>
				</select>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4"  align="right">类别：<font color="red" >*</font></td>
			<td class="border_top_right4"  align="left">
				<select name="partType" id="partType">
				    <option value="1" <c:if test="${dto.partType==1}">selected</c:if> >全匹配</option>
				    <option value="2" <c:if test="${dto.partType==2}">selected</c:if> >部分匹配</option>
				    <option value="3" <c:if test="${dto.partType==3}">selected</c:if> >包含</option>
				    <option value="4" <c:if test="${dto.partType==4}">selected</c:if> >区段</option>
				</select>
				<input type="hidden" id="listType" name="listType" value="${listType}"/>
				<input type="hidden" id="approvalType" name="approvalType" value="${dto.approvalType}"/>
				<input type="hidden" id="id" name="id" value="${dto.id}"/>
			</td>
		</tr>
		<tr style="display:none;" class="trForContent1" id="quduan">
            <td class="border_top_right4"  align="right" >区段值：<font color="red" >*</font></td>
			<td class="border_top_right4"  align="left">
				<input type="text" id="value1" name="value1" value="${dto.value1}" style="width: 160px; padding: 0px"/>至
				<input type="text" id="value2" name="value2" value="${dto.value2}" style="width: 160px; padding: 0px"/>
			</td>
		</tr>
		<tr class="trForContent1" id="tr_content">
			<td class="border_top_right4"  align="right" >内容：<font color="red" >*</font></td>
			<td class="border_top_right4"  align="left">
				<input type="text" id="content" name="content" value="${dto.content}" style="width: 160px; padding: 0px"/>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4"  align="right" >备注：<c:if test="${dto.listType==2}"><font color="red" >*</font></c:if></td>
			<td class="border_top_right4"  align="left">
				<input type="text" id="remark" name="remark" value="${dto.remark}" style="width: 160px; padding: 0px"/>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" colspan="2" align="center">
				<input type = "button" class="button2" onclick="javascript:checkIsExsit();" value="保存">
				<input type = "button" class="button2"  onclick="javascript:closePage();" value="返回">
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

