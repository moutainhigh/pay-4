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
	var featureId = document.getElementById("featureId").value;
	var appScale = document.getElementById("appScale").value;
	var url="${ctx}/websiteFeature.do?method=authorizeView&featureId="+featureId+"&appScale="+appScale;
	parent.closePage(url);
}

</script>
</head>

<body>

<%-- <table width="700" border="0" cellspacing="0" cellpadding="0" align="center" height="21" >
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">WEBSITE ${feature.name}授权      </font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> --%>
<h2 class="panel_title">WEBSITE ${feature.name}授权</h2>


<form id="websiteFeature" name="websiteFeature" method="post" action="websiteFeature.do">
	<input type="hidden" id="method" name="method" value="authorizeSave"/>
	<input type="hidden" id="featureId" name="featureId" value="${feature.featureId}"/>
	<input type="hidden" id="appScale" name="appScale" value="${appScale}"/>
	<input type="hidden" id="securLevel" name="securLevel" value="${feature.securLevel}" />
	<table class="border_all2" width="700" border="1" cellspacing="0" cellpadding="0" align="center">
		<tr class="trForContent1">
			<td class="border_top_right4"  align="center" >权限名称</td>
			<td class="border_top_right4"  align="center" >
			${feature.name}
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4"  align="center" >菜单</td>
			<td class="border_top_right4"  align="center" >
				<table cellpadding="0" cellspacing="0" width="100%">
					<c:forEach items="${pwList}" var="p">
						<tr class="trForContent1">
							<td>
								<input name="menuId" value="${p.id}" type="checkbox" <c:if test="${p.checked}">checked="checked"</c:if> />+${p.menuName}
							</td>
						</tr>
						<tr class="trForContent1">
							<td>
								<c:forEach items="${p.childlist}" var="pc">
									&nbsp;&nbsp;<input name="menuId" value="${pc.id}" type="checkbox" <c:if test="${pc.checked}">checked="checked"</c:if> />${pc.menuName}
									<c:if test="${pc.displayFlag==0}"><font color="red">(不显示)</font></c:if>
								</c:forEach>
							</td>
						</tr>
					</c:forEach>
				</table>
			</td>
		</tr>
		<tr class="trForContent1">
			
			<td class="border_top_right4" colspan="2" align="center" >
				<input type="submit"  class="button2" value="提交"/>
				<input type = "button"  class="button2" onclick="javascript:closePage();" value="关闭">
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

