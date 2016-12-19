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
	var featureId = $("#featureId").val();
	var url="${ctx}/websiteFeature.do?method=featureEditView&featureId="+featureId;
	parent.closePage(url);
}
</script>
</head>

<body>

<!-- <table width="700" border="0" cellspacing="0" cellpadding="0" align="center" height="21" >
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">WEBSITE 权  限   编   辑      </font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
<h2 class="panel_title">WEBSITE权限编辑</h2>


<form id="websiteFeature" name="websiteFeature" method="post" action="websiteFeature.do">
	<input type="hidden" name="featureId" id="featureId" value="${feature.featureId}"/>
	<input type="hidden" name="hiddenSecurLevel" value="${feature.securLevel}"/>
	<input type="hidden" name="hiddenAppScale" value="${feature.appScale}"/>
	<input type="hidden" name="method" value="editSaveFeature"/>
	
	<table class="border_all2" width="700" border="1" cellspacing="0" cellpadding="0" align="center">
		<tr class="trForContent1">
			<td class="border_top_right4"  align="right" >权限名称：</td>
			<td class="border_top_right4"  align="left">
				<input type="text" id="name" name="name" maxlength= "32" value="${feature.name}" readonly="readonly" style="width: 260px;"/>
			</td>
		</tr>

		<tr class="trForContent1">
		<td class="border_top_right4"  align="right">权限级别：</td>
			<td class="border_top_right4"  align="left">
				<select name="securLevel" disabled="true">
					<option value="1" <c:if test="${feature.securLevel==1}">selected</c:if> >口令卡用户非口令卡登录</option>
					<option value="2" <c:if test="${feature.securLevel==2}">selected</c:if> >普通登录</option>
					<option value="3" <c:if test="${feature.securLevel==3}">selected</c:if> >口令卡登录</option>
					<option value="4" <c:if test="${feature.securLevel==4}">selected</c:if> >数字证书</option>
					<option value="5" <c:if test="${feature.securLevel==5}">selected</c:if> >U-盾</option>
					<option value="6" <c:if test="${feature.securLevel==6}">selected</c:if> >交易中心管理员</option>
					<option value="7" <c:if test="${feature.securLevel==7}">selected</c:if> >交易商</option>
				</select>
			</td>
		</tr>	
		<tr class="trForContent1">
			<td class="border_top_right4"  align="right">权限类型：</td>
			<td class="border_top_right4"  align="left">
				<select name="appScale" disabled="true">
					<option value="2" <c:if test="${feature.appScale==2}">selected</c:if> >企业</option>
					<option value="3" <c:if test="${feature.appScale==3}">selected</c:if> >个人</option>
					<option value="10" <c:if test="${fDto.appScale==10}">selected</c:if> >交易中心</option>
				</select>
			</td>
		</tr>
	
		<tr class="trForContent1">
			<td class="border_top_right4"  align="right">是否有效：</td>
			<td class="border_top_right4"  align="left">
				<select name="status">
					<option value="1" <c:if test="${feature.status==1}">selected</c:if> >有效</option>
					<option value="2" <c:if test="${feature.status==2}">selected</c:if> >删除</option>
					<option value="0" <c:if test="${feature.status==0}">selected</c:if> >无效</option>
				</select>
			</td>
		</tr>
	
	
		<tr class="trForContent1">
			<td class="border_top_right4"  align="right">备注：</td>
			<td class="border_top_right4"  align="left"><textarea rows="7" cols="30" name="description">${feature.description}</textarea></td>
		</tr>
		<tr class="trForContent1">
			<td colspan="2" align="center">
				<input type="submit" class="button2" value="保存">	
				<input type = "button" class="button2"  onclick="javascript:closePage();" value="关闭">
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

