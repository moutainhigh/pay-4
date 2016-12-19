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
function memberDetail(url){
	window.open("${ctx}"+url);
}
</script>

</head>

<body>
<form>

<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">

	<tr class="trbgcolorForTitle" align="center" valign="middle">
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">支付平台账户名</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">姓名</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">手机号码</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">关联来源</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">门店编号</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">门店名称</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">操作</font> </a></td>

	</tr>
	<c:forEach items="${page.result}" var="relationMember" varStatus = "relationMemberStatus">
	
	<c:choose>
       <c:when test="${relationMemberStatus.index%2==0}">
             <tr class="trForContent1">
       </c:when>
       <c:otherwise>
             <tr class="trForContent2">
       </c:otherwise>
	</c:choose>	
			
		<td class="border_top_right4" align="center" nowrap style="white-space:pre-wrap;word-wrap:break-word;word-break:break-all;" width="200">${relationMember.loginName}&nbsp;</td>
		<td class="border_top_right4" align="center" nowrap>${relationMember.name}&nbsp;</td>
		<td class="border_top_right4" align="center" nowrap>${relationMember.mobile}&nbsp;</td>
				
		<td class="border_top_right4" align="center" nowrap>	<c:if test="${relationMember.relationType=='2'}">利安代扣&nbsp;</c:if></td>
		<td class="border_top_right4" align="center" nowrap>${relationMember.value1}&nbsp;</td>
		<td class="border_top_right4" align="center" nowrap>${relationMember.value2}&nbsp;</td>
		<td class="border_top_right4" align="center" nowrap>
		<a href="javascript:memberDetail('/personalMemberInfoDetail.do?memberCode=${relationMember.sonMemberCode}');">查看详情</a>&nbsp;
		</td>
		</tr>
	</c:forEach>
	
</table>
	
</form>
<li:pagination methodName="memberRelationManagerQuery" pageBean="${page}" sytleName="black2"/>
</body>

