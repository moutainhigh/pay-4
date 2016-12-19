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
function updateSignMessage(signMessageId){
	var url="signMessageUpdate.do?method=updateView&signMessageId="+signMessageId;
	parent.addMenu("频道修改",url);
}
function deleteSignMessage(signMessageId){
	var url="signMessageDelete.do?method=deleteSignMessage&signMessageId="+signMessageId;
	location.href = url;
}
function histroySearch(loginName){
	var url="enterpriseListForInfo.do?merchantEmail="+loginName;
	parent.addMenu("基本信息",url);
}

</script>
</head>

<body>
<form>

<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	<!-- 
		<c:if test="${show=='YES'}">
			<tr > 
				<td height="2" ><font color="red" >操作成功</font></td>
			</tr>
		</c:if>
	 -->
	<tr class="trbgcolorForTitle" align="center" valign="middle">
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">创建日期</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">频道名称</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">频道邮箱</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">频道负责人</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">频道负责人电话</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">创建者</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">操作</font> </a></td>

	</tr>
	<c:forEach items="${page.result}" var="signMessage" varStatus = "personalStatus">
	<c:choose>
       <c:when test="${personalStatus.index%2==0}">
             <tr class="trForContent1">
       </c:when>
       <c:otherwise>
             <tr class="trForContent2">
       </c:otherwise>
	</c:choose>			
		<td class="border_top_right4" align="center" nowrap>${signMessage.strGmtCreate}&nbsp;</td>
		<td class="border_top_right4" align="center" nowrap>${signMessage.departmentName}&nbsp;</td>
		<td class="border_top_right4" align="center" nowrap>${signMessage.email}&nbsp;</td>
		<td class="border_top_right4" align="center" nowrap>${signMessage.departmentPrincipal}&nbsp;</td>
		<td class="border_top_right4" align="center" nowrap>${signMessage.principalMobile}&nbsp;</td>
		<td class="border_top_right4" align="center" nowrap>${signMessage.creator}&nbsp;</td>
		<td class="border_top_right4" align="center" nowrap>
			<a href="javascript:void(0);" onclick="updateSignMessage('${signMessage.signMessageId}');">&nbsp;&nbsp;修改&nbsp;</a>
			<a href="javascript:void(0);" onclick="deleteSignMessage('${signMessage.signMessageId}');">&nbsp;&nbsp;删除&nbsp;</a>
		</td>
		</tr>
	</c:forEach>
</table>
</form>
<li:pagination methodName="signMessageSearch" pageBean="${page}" sytleName="black2"/>
</body>

