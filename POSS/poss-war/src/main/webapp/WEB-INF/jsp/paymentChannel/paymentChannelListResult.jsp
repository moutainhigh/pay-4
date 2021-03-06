
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="poss" prefix="li" %>
<head>
	<link rel="stylesheet" href="./css/main.css">
	<link rel="stylesheet" href="${ctx}/css/jq.css" type="text/css" media="print, projection, screen" />
	<link rel="stylesheet" href="${ctx}/css/style.css" type="text/css" media="print, projection, screen" />
	<link rel="stylesheet" href="./css/main.css">
	<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>
	<script src="./js/common.js"></script>
	<script type="text/javascript">
		function processEdit(id){
			location.href = "paymentChannelEdit.do?id=" + id;
		}
	</script>
</head>

<body>

	<table class="border_all2" width="95%" border="0" cellspacing="0"
		cellpadding="1" align="center">
		<tr class="trbgcolorForTitle" align="center" valign="middle">
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">渠道名称</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">渠道描述</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">渠道状态</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">排列序号</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">操作员</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">&nbsp;</font> </a></td>
		</tr>
		
		<c:forEach items="${resultMap.paymentChannelList}" var="paymentChannel" varStatus = "paymentChannelListStatus">
			<c:choose>
				<c:when test="${paymentChannelListStatus.index%2==0}">
				      <tr class="trForContent1">
				</c:when>
				<c:otherwise>
				      <tr class="trForContent2">
				</c:otherwise>
			</c:choose>	
				<td class="border_top_right4" align="center" nowrap>${paymentChannel.name}&nbsp;</td>
				<td class="border_top_right4" align="center" nowrap>${paymentChannel.description}&nbsp;</td>
				<td class="border_top_right4" align="center" nowrap>${paymentChannel.status}&nbsp;</td>
				<td class="border_top_right4" align="center" nowrap>${paymentChannel.serialNo}&nbsp;</td>
				<td class="border_top_right4" align="center" nowrap>${paymentChannel.operator}&nbsp;</td>
				<td class="border_top_right4" align="center" nowrap>
					<a href="javascript:processEdit('${paymentChannel.id}')">&nbsp;编辑&nbsp;</a>
				</td>
			</tr>
		</c:forEach>
	</table>
	
</body>

