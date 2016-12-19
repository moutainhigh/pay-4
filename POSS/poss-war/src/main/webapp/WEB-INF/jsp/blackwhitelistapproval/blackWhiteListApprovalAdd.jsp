<%@ page language="java" contentType="text/html; charset=utf-8"	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>

<link rel="stylesheet" href="./css/main.css">
<script src="./js/common.js"></script>
<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";


function closePage() {
	window.location.href="${ctx}/blackWhiteListSearch.do?method=blackWhiteListSearchView";
}
function checkIsExsit() {
	$.ajax({
		type: "POST",
		url: "${ctx}/blackWhiteListAddSave.do?method=checkIsExsit",
		data: $("form").serialize(),
		success: function(result) {
			if(result == 1){
				alert("相同的配置已经存在，请修改后再提交");
			} else {
				submitSave();
			}
		}
	});
}
function submitSave() {
	var content = document.getElementById('content').value ;
	var listType = document.getElementById('listType').value ;
	var businessTypeId = document.getElementById('businessTypeId').value ;
	var businessTypeIdFlag = document.getElementById('businessTypeIdFlag').value;
	var memberCodeFlag = document.getElementById('memberCodeFlag').value;
	
	var reg = /^\d+$/;
	
	if("" == trim(content) ){
		alert("内容不能为空");
		return;
	}

	if("" == trim(listType)){
		alert("请选择名单类型");
		return;
	}
	if("" == trim(businessTypeId)){
		alert("业务类型ID不能为空");
		return;
	}
	if(!reg.test($('#businessTypeId').val())){
		alert("业务类型ID只能为数字");
		return;
	}

	document.getElementById('businessTypeForm').submit();
}
function trim(str){ //删除左右两端的空格
	return str.replace(/(^\s*)|(\s*$)/g, "");
}
</script>
</head>

<body >
 
<%-- <table width="700" border="0" cellspacing="0" cellpadding="0" align="center" height="21" >
	<tr>
		<td height="2" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">添   加  黑    名  单</font></div>
		</td>
	</tr>
	<c:if test="${message!=null}">
		<tr>
			<td height="2" ><font color="red" >${message}</font></td>
		</tr>
	</c:if>
</table> --%>
<h2 class="panel_title">添加黑名单</h2>
<c:if test="${message!=null}">
		<p>
			<font color="red" >${message}</font>
		</p>
	</c:if>
<form id="businessTypeForm" name="businessTypeForm" method="post" action="blackWhiteListAddSave.do">
	<input type="hidden" name="method" value="blackWhiteListAddSave"/>
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
			<td class="border_top_right4"  align="right">类别：</td>
			<td class="border_top_right4"  align="left">
				<select name="partType" id="partType">
				    <option value="1" <c:if test="${dto.partType==1}">selected</c:if> >全匹配</option>
				    <option value="2" <c:if test="${dto.partType==2}">selected</c:if> >部分匹配</option>
				    <option value="3" <c:if test="${dto.partType==3}">selected</c:if> >包含</option>
				</select>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4"  align="right" >内容：<font color="red" >*</font></td>
			<td class="border_top_right4"  align="left">
				<input type="text" id="content" name="content" value="" style="width: 160px; padding: 0px"/>
				<input type="hidden" id="listType" name="listType" value="2"/>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4"  align="right" >备注：<font color="red" >*</font></td>
			<td class="border_top_right4"  align="left">
				<input type="text" id="remark" name="remark" value="" style="width: 160px; padding: 0px"/>
			</td>
		</tr>
		<tr class="trForContent1">
			<td colspan="2" align="center">
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

