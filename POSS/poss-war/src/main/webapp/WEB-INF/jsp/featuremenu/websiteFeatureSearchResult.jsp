<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="poss" prefix="li" %>



<head>
<link rel="stylesheet" href="${ctx}/css/jq.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="${ctx}/css/style.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="./css/main.css">
<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>
<script src="./js/common.js"></script>
<script language="javascript">

function editFeature(featureId,appScale){
	var url = "${ctx}/websiteFeature.do?method=featureEditView&featureId="+featureId;
	parent.addMenu("编辑权限",url);
}
function authorize(featureId,appScale){
	var url = "${ctx}/websiteFeature.do?method=authorizeView&featureId="+featureId+"&appScale="+appScale;
	parent.addMenu("角色授权",url);
}

</script>
</head>

<body>
<!-- <table width="95%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" >
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">WEBSITE  权  限  列  表 </font></div>
		</td>
	</tr>
	
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
	<td height="18">
	安全级别，默认2,0不可登录,1口令卡用户非口令卡登录--仅查询，限制交易和个人信息查询,2普通登录--限制交易额度,风控建议均为1000元,3口令卡登录,4数字证书,5u盾
	</td>
	<br><br><br>
	</tr>
</table> -->
<h2 class="panel_title">WEBSITE权限列表</h2>
<p>安全级别，默认2,0不可登录,1口令卡用户非口令卡登录--仅查询，限制交易和个人信息查询,2普通登录--限制交易额度,风控建议均为1000元,3口令卡登录,4数字证书,5u盾</p>
<table class="tablesorter" width="95%" border="0" cellspacing="1"
	cellpadding="0" align="center">
	<input type="hidden" name="tess" value="${menuList.parentId}"/>
	<thead>
		<tr class="" align="center" valign="middle">
			<th class="header"  nowrap>权限名称</th>
			<th class="header"  nowrap>是否有效</th>
			<th class="header"  nowrap>级别</th>
			<th class="header"  nowrap>所属</th>
			<th class="header"  nowrap>备注</th>
			<th class="header"  nowrap>操作</th>
		</tr>
	</thead>
	<c:forEach items="${featureList}" var="featureList" varStatus = "personalStatus">
	<c:choose>
       <c:when test="${personalStatus.index%2==0}">
             <tr class="trForContent1">
       </c:when>
       <c:otherwise>
             <tr class="trForContent2">
       </c:otherwise>
	</c:choose>			
			<td class="" align="center" nowrap>${featureList.name}&nbsp;</td>
			<td class="" align="center" nowrap>
				<c:if test="${featureList.status==0}">无效&nbsp;</c:if>
				<c:if test="${featureList.status==1}">有效&nbsp;</c:if>
				<c:if test="${featureList.status==2}">删除&nbsp;</c:if>
			</td>
			<td class="" align="center" nowrap>&nbsp;${featureList.securLevel}
			</td>
		
			<td class="" align="center" nowrap>&nbsp;
				<c:if test="${featureList.appScale==2}">企业&nbsp;</c:if>
				<c:if test="${featureList.appScale==3}">个人&nbsp;</c:if>
				<c:if test="${featureList.appScale==4}">个人卖家&nbsp;</c:if>
				<c:if test="${featureList.appScale==10}">交易中心&nbsp;</c:if>
			</td>
			<td class="" align="center" nowrap>${featureList.description}&nbsp;</td>
			<td class="" align="center" nowrap>
				<a href="javascript:void(0);" onclick="editFeature('${featureList.featureId}');">编辑 </a>&nbsp;&nbsp;
				 	 <!-- 曾锦要求如果级别为1(口令卡用户非口令卡登录) 则授权的时候就取菜单为业务级别的菜单(5)-->
				     <c:if test="${featureList.securLevel==1 && featureList.appScale!=4}">
				      <a href="javascript:void(0);" onclick="authorize('${featureList.featureId}','5');">授权</a>
				    </c:if>
				      <!-- 要求如果类型为4(个人卖家) 则授权的时候就取菜单为个人卖家级别的菜单(7)-->
				     <c:if  test="${featureList.appScale==4}">
				      <a href="javascript:void(0);" onclick="authorize('${featureList.featureId}','7');">授权</a>
				     </c:if> 
				     <c:if test="${(featureList.appScale==2 || featureList.appScale==3 || featureList.appScale==10) && featureList.securLevel!=1}">
				      <a href="javascript:void(0);" onclick="authorize('${featureList.featureId}','${featureList.appScale}');">授权</a>
				     </c:if>
			</td>
		</tr>
	</c:forEach>
</table>
</body>

