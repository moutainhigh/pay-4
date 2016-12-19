<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="poss" prefix="li" %>

<head>
<link rel="stylesheet" href="${ctx}/css/jq.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="${ctx}/css/style.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="./css/main.css">
<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>


<script language="javascript">
function appealBaseDataDelete(baseDataId){
	if(confirm("确定要删除吗?")){
		document.getElementById('baseDataId').value=baseDataId;
		this.appealBaseDataQuery();
		document.getElementById('baseDataId').value='';
	}
}
</script>
</head>

<body>
<c:if test="${sign!=null}">
<br>
	<center>
		<font color="red">${sign}</font>
	</center>
<br><br>
</c:if>
	<table class="border_all2" width="95%" border="0" cellspacing="0"
		cellpadding="1" align="center">
		<tr class="trbgcolorForTitle" align="center" valign="middle">
		
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">数据标识</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">数据名称</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">数据类型</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">操作</font> </a></td>	
		</tr>
		<c:forEach items="${page.result}" var="baseData" varStatus = "baseDataStatus">
		<c:choose>
	       <c:when test="${baseDataStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
				<td class="border_top_right4" align="center" nowrap>${baseData.code}&nbsp;</td>
				<td class="border_top_right4" align="center" nowrap>${baseData.name}&nbsp;</td>
				<td class="border_top_right4" align="center" nowrap>${baseData.type}&nbsp;</td>
				<td class="border_top_right4" align="center" nowrap>
					<a  href="javascript:appealBaseDataDelete('${baseData.id}')">
						删除
					</a>
				</td>
			</tr>
		</c:forEach>
	</table>	


<li:pagination methodName="appealBaseDataQuery" pageBean="${page}" sytleName="black2"/>



</body>

