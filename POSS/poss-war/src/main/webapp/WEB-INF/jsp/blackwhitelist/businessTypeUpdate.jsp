<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>

<link rel="stylesheet" href="./css/main.css">
<script src="./js/common.js"></script>

<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";

function closePage() {
	var url="businessTypeAdd.do?method=businessTypeCteateView";
	parent.closePage(url);
}
function submitSave() {
	var businessName = document.getElementById('businessName').value ;
	var businessType = document.getElementById('businessType').value ;

	//var businessTypeId = document.getElementById('businessTypeId').value ;
	var businessTypeIdFlag = document.getElementById('businessTypeIdFlag').value;
	var remark = document.getElementById('remark').value;
	var reg = /^\d+$/;
	
	if("" == trim(businessName) ){
		alert("业务描述不能为空");
		return;
	}
	if("" == trim(businessType)){
		alert("请选择业务类型");
		return;
	}
	
	if(remark.length > 50){
		alert("备注内容长度过长");
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
		<div align="center"><font class="titletext">修  改  业  务  类   型</font></div>
		</td>
	</tr>
	<c:if test="${message!=null}">
		<tr>
			<td height="2" ><font color="red" >${message}</font></td>
		</tr>
	</c:if>
</table> --%>
<h2 class="panel_title">修改业务类型</h2>
<c:if test="${message!=null}">
		<p>
			<font color="red" >${message}</td>
		</p>
	</c:if>

<form id="businessTypeForm" name="businessTypeForm" method="post" action="businessTypeUpdate.do">
	<input type="hidden" name="method" value="updateSave"/>
	<input type="hidden" id="businessTypeIdFlag" name="businessTypeIdFlag" value=""/> 
	<input type="hidden" name="businessTypeId" value="${dto.businessTypeId}"/> 
	<table class="border_all2" width="700" border="1" cellspacing="0" cellpadding="0" align="center">
		<tr class="trForContent1">
			<td class="border_top_right4"  align="right" >业务描述：<font color="red" >*</font></td>
			<td class="border_top_right4"  align="left">
				<input type="text" id="businessName" name="businessName" maxlength= "50" value="${dto.businessName}" style="width: 160px;"/>
			</td>
		</tr>

		<tr class="trForContent1" >
			<td class="border_top_right4"  align="right">业务类型：<font color="red" >*</font></td>
				<td class="border_top_right4"  align="left">
					<select name="businessType" id="businessType">
					    <option value="" >请选择</option>
						<option value="1" <c:if test="${dto.businessType==1}">selected</c:if> >FO</option>
						<option value="2" <c:if test="${dto.businessType==2}">selected</c:if> >FI</option>
						<option value="3" <c:if test="${dto.businessType==3}">selected</c:if> >MA</option>
						<option value="4" <c:if test="${dto.businessType==4}">selected</c:if> >APP</option>
					</select>
				</td>
		</tr>
	<!--  
		<tr class="trForContent1">
			<td class="border_top_right4"  align="right" >业务类型ID：<font color="red" >*</font></td>
			<td class="border_top_right4"  align="left">
				<input type="text" name="businessTypeId" id="businessTypeId" maxlength="4" value="${dto.businessTypeId}"  style="width: 60px;"/>
				<font color="red" >注：各组开头分别为FO(100)FI(200)MA(300)APP(400)</font>
			</td>
		</tr>
	-->
		<tr class="trForContent1">
			<td class="border_top_right4"  align="right">是否有效：</td>
			<td class="border_top_right4"  align="left">
				<select name="status">
					<option value="1" <c:if test="${dto.status==1}">selected</c:if> >有效</option>
					<option value="0" <c:if test="${dto.status==0}">selected</c:if> >无效</option>
				</select>
			</td>
		</tr>

		<tr class="trForContent1">
			<td class="border_top_right4"  align="right">备注：</td>
			<td class="border_top_right4"  align="left"><textarea rows="7" cols="50" id="remark"  name="remark" >${dto.remark}</textarea></td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" colspan="2" align="center">
				<input type = "button"  onclick="javascript:submitSave();" value="保存">
				<input type = "button"  onclick="javascript:closePage();" value="关闭">
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

