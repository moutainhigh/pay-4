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
			location.href = "paymentChannelItemEdit.do?id=" + id;
		}
	</script>
</head>

<body>
	<br/><br/>
	<table class="border_all2" width="95%" border="0" cellspacing="0"
		cellpadding="1" align="center">
		<tr class="trbgcolorForTitle" align="center" valign="middle">
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">所属渠道</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">通道种类</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">通道名称</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">通道别名</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">通道状态</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">机构码</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">通道描述</font> </a></td>			
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">排列序号</font> </a></td>		
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">ServiceURL</font> </a></td>		
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">操作员</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
				color="#FFFFFF">操作</font> </a></td>
		</tr>
		<c:forEach items="${paymentChannelItemDTOList}" var="paymentChannelItem" varStatus = "paymentChannelItemDTOListStatus">
			<c:choose>
				<c:when test="${paymentChannelItemDTOListStatus.index%2==0}">
				      <tr class="trForContent1">
				</c:when>
				<c:otherwise>
				      <tr class="trForContent2">
				</c:otherwise>
			</c:choose>	
				<td class="border_top_right4" align="center" nowrap>
					<c:forEach items="${paymentChannelDTOList}" var="paymentChannelStatus" >
						<c:if test="${paymentChannelStatus.id == paymentChannelItem.paymentChannelID }">${paymentChannelStatus.name} </c:if>
					</c:forEach>&nbsp;
				</td>
				<td class="border_top_right4" align="center" nowrap>${paymentChannelItem.paymentCatagory}&nbsp;</td>
				<td class="border_top_right4" align="center" nowrap>${paymentChannelItem.itemName}&nbsp;</td>
				<td class="border_top_right4" align="center" nowrap>${paymentChannelItem.alias}&nbsp;</td>
				<td class="border_top_right4" align="center" nowrap>
					<c:forEach items="${ChannelStatusList}" var="channelStatus" >
						<c:if test="${channelStatus.code == paymentChannelItem.status }">${channelStatus.description} </c:if>
					</c:forEach>&nbsp;
				</td>
				<td class="border_top_right4" align="center" nowrap>${paymentChannelItem.orgcode}&nbsp;</td>
				<td class="border_top_right4" align="center" nowrap>${paymentChannelItem.description}&nbsp;</td>
				<td class="border_top_right4" align="center" nowrap>${paymentChannelItem.serialNo}&nbsp;</td>
				<td class="border_top_right4" align="center" nowrap>${paymentChannelItem.serviceUrl}&nbsp;</td>
				<td class="border_top_right4" align="center" nowrap>${paymentChannelItem.operator}&nbsp;</td>
				 
				<td class="border_top_right4" align="center" nowrap>
					<a href="javascript:processEdit('${paymentChannelItem.id}')">&nbsp;编辑&nbsp;</a>					
				</td>
			</tr>
		</c:forEach>
	</table>
	<br/><br/><br/>
<!--	<li:pagination methodName="paymentChannelQuery" pageBean="${page}" sytleName="black2"/>-->
</body>

