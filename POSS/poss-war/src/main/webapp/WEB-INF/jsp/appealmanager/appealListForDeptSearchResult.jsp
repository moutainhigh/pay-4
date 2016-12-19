<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="poss" prefix="li" %>

<head>
<link rel="stylesheet" href="${ctx}/css/jq.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="${ctx}/css/style.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="./css/main.css">
<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>


<script language="javascript">
function addMenu(url){
	parent.addMenu("申诉信息",url);
	
}
</script>
</head>

<body>

	<table class="border_all2" width="95%" border="0" cellspacing="0"
		cellpadding="1" align="center">
		<tr class="trbgcolorForTitle" align="center" valign="middle">
		
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">申诉号</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">客户姓名</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">发生时间</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">来电号码</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">联系号码</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">联系邮箱</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">当前状态</font> </a></td>	
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">受理时间</font></a></td>	
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">操作</font></a></td>			
		</tr>
		<c:forEach items="${page.result}" var="appeal" varStatus = "appealStatus">
		<c:choose>
	       <c:when test="${appealStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
				<td class="border_top_right4" align="center" nowrap>${appeal.appealCode}&nbsp;</td>
				<td class="border_top_right4" align="center" nowrap>${appeal.customerName}&nbsp;</td>
				<td class="border_top_right4" align="center" nowrap>${fn:substring(appeal.occurTime, 0, 10)}&nbsp;</td>
				<td class="border_top_right4" align="center" nowrap>${appeal.callPhone}&nbsp;</td>
				<td class="border_top_right4" align="center" nowrap>${appeal.linkPhone}&nbsp;</td>
				<td class="border_top_right4" align="center" nowrap>${appeal.linkEmail}&nbsp;</td>
				<td class="border_top_right4" align="center" nowrap>
					<c:forEach items="${appealStatusList}" var="appealStatus">
						<c:if test="${appealStatus.code == appeal.appealStatusCode}">
							${appealStatus.name}
						</c:if>
					</c:forEach>&nbsp;
				</td>
				<td class="border_top_right4" align="center" nowrap>${appeal.createTime}&nbsp;</td>
				<td class="border_top_right4" align="center" nowrap>
					<a href="javascript:addMenu('appealInfoForDeptSearch.do?appealId=${appeal.appealId}');">查看</a>
				</td>
			</tr>
		</c:forEach>
	</table>	


<li:pagination methodName="appealQuery" pageBean="${page}" sytleName="black2"/>



</body>

