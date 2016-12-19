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
function doDelete(url){

	if(confirm("确定要删除该条关联信息吗 ?"))
	{
		window.location.href = "${ctx}"+url;
	}
}
function toUpdate(url){
	window.location.href = "${ctx}"+url;
}
</script>

</head>

<body>
<form>

<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">

	<tr class="trbgcolorForTitle" align="center" valign="middle">
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">销售人员姓名</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">销售人员用户名</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">销售主管姓名</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">销售主管用户名</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">录入日期</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">操作</font> </a></td>

	</tr>
	<c:forEach items="${page.result}" var="userRelation" varStatus = "userRelationStatus">
	
	<c:choose>
       <c:when test="${userRelationStatus.index%2==0}">
             <tr class="trForContent1">
       </c:when>
       <c:otherwise>
             <tr class="trForContent2">
       </c:otherwise>
	</c:choose>	
			
		<td class="border_top_right4" align="center" nowrap style="white-space:pre-wrap;word-wrap:break-word;word-break:break-all;" width="200">${userRelation.name}&nbsp;</td>
		<td class="border_top_right4" align="center" nowrap>${userRelation.loginId}&nbsp;</td>
		<td class="border_top_right4" align="center" nowrap>${userRelation.pname}&nbsp;</td>
		<td class="border_top_right4" align="center" nowrap>
		<c:if test="${userRelation.ploginId != 'root'}">
			${userRelation.ploginId}
		</c:if>
		&nbsp;</td>
		<td class="border_top_right4" align="center" nowrap>${userRelation.createDate}&nbsp;</td>
		<td class="border_top_right4" align="center" nowrap>
		<a href="javascript:toUpdate('/userrelation/userRelationQuery.do?method=toUpdatePage&&id=${userRelation.id}');">修改</a>&nbsp;
		<a href="javascript:doDelete('/userrelation/userRelationQuery.do?method=doDelete&&id=${userRelation.id}');">删除</a>&nbsp;
		</td>
		</tr>
	</c:forEach>
	
</table>
	
</form>
<li:pagination methodName="userRelationQuery" pageBean="${page}" sytleName="black2"/>
</body>

