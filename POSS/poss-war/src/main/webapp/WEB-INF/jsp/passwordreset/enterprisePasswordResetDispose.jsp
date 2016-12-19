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
	var id = document.getElementById('id').value;
	var typeName =document.getElementById('typeName').value;
	var url="enterprisePasswordResetDispose.do?method=disposeView&id="+id+"&typeName="+typeName;
	parent.closePage(url);
}
function cancel() {
	var id = document.getElementById('id').value;
	var typeName =document.getElementById('typeName').value;
	var url="enterprisePasswordResetDispose.do?method=disposeView&id="+id+"&typeName="+typeName;
	parent.closePage(url);
}
function submitSaveConfirm(){
	checkRemark();
	document.getElementById('disposeFlag').value="confirm";
	document.getElementById('enterprisePasswordReset').submit();
}
function submitSaveRefuse(){
	checkRemark();
	document.getElementById('disposeFlag').value="refuse";
	document.getElementById('enterprisePasswordReset').submit();
}
function checkRemark(){
	if($('#remark').val()==""){
		alert("备注不能为空");
		return;
	}
	if($('#remark').val().length > 64){
		alert("备注内容长度过长");
		return;
	}
}

function trim(str){ //删除左右两端的空格
	return str.replace(/(^\s*)|(\s*$)/g, "");
}
 

</script>
</head>

<body>
<h2 class="panel_title">企 业 会 员 <c:if test="${typeName=='typeLogin'}">登 录</c:if><c:if test="${typeName=='typePay'}">支 付</c:if> 密 码 重 置  审 核</h2>
<table width="80%" border="0" cellspacing="0" cellpadding="0" align="center" height="21" >
	<%-- <tr>
		<td width="50%" height="2" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td width="50%" height="18">
		<div align="center">
		<font class="titletext">企 业 会 员 <c:if test="${typeName=='typeLogin'}">登 录</c:if><c:if test="${typeName=='typePay'}">支 付</c:if> 密 码 重 置  审 核
		
		</font>
		</div>
		</td>
	</tr> --%>
	<c:if test="${show=='YES'}">
		<tr>
			<td width="50%" height="2" ><font color="red" >审核已成功,请点击关闭按钮关闭本页面</font></td>
		</tr>
	</c:if>
	<c:if test="${show=='NO'}">
		<tr>
			<td width="50%" height="2" ><font color="red" >审核已拒绝,请点击关闭按钮关闭本页面</font></td>
		</tr>
	</c:if>
	<c:if test="${resultDto.status!=2 && show!='NO' && show!='YES'}">
		<tr>
			<td width="50%" height="2" ><font color="red" >该记录不是初审状态,不能操作！请点击关闭按钮关闭本页面</font></td>
		</tr>
	</c:if>
</table>


<form id="enterprisePasswordReset" name="enterprisePasswordReset" method="post" action="enterprisePasswordResetConfirm.do">
	<input type="hidden" id="memberName" name="memberName" value="${resultDto.memberName}"/>
	<input type="hidden" id="loginName" name="loginName" value="${resultDto.loginName}"/>
	<input type="hidden" id="typeName" name="typeName" value="${fn:escapeXml(typeName)}"/>
	<input type="hidden" id="mobile" name="mobile" value="${resultDto.mobile}"/>
	<input type="hidden" id="id"  name="id" value="${resultDto.id}"/>
	<input type="hidden" id="status" name="status" value="${resultDto.status}"/>
	<input type="hidden" id="method" name="method" value="dispose"/>
	<input type="hidden" id="disposeFlag" name="disposeFlag" value=""/>
	<table class="border_all2" width="80%" border="0" cellspacing="0"cellpadding="1" align="center">	
	
	
	
	<tr class="trForContent1">
		<td width="50%" class="border_top_right4" align="right" >会员登录名：</td>
		<td width="50%" class="border_top_right4" align="left" >
			${resultDto.loginName}&nbsp;
		</td>
	</tr>	
	<tr class="trForContent1">
		<td width="50%" class="border_top_right4" align="right" >会员中文名：</td>
		<td width="50%" class="border_top_right4" align="left" >
			${resultDto.memberName}&nbsp;
		</td>
	</tr>	
	<tr class="trForContent1">
			<td width="50%" class="border_top_right4" align="right" >备注：</td>
			<td width="50%" class="border_top_right4" align="left" >
				<textarea rows="7" cols="30" id="remark" name="remark" <c:if test="${resultDto.status!=2}">disabled ="disabled"</c:if>>${resultDto.remark}</textarea>
			</td>
	</tr>
	<tr class="trForContent1">
		<td width="50%" class="border_top_right4" colspan="3" align="center">
			<c:if test="${resultDto.status==2}">
				<input type = "button" class="button2" onclick="javascript:submitSaveConfirm();" value="审核确认"<c:if test="${show=='NO' || show=='YES'}">disabled ="disabled"</c:if>>
				<input type = "button" class="button2" onclick="javascript:submitSaveRefuse();" value="审核拒绝" <c:if test="${show=='NO' || show=='YES'}">disabled ="disabled"</c:if>>
			</c:if>	
				<input type = "button" class="button2" onclick="javascript:closePage();" value="关闭">
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

