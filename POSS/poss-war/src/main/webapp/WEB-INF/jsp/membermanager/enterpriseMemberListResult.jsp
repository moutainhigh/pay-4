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

<script language="javascript">



function showUrl(menu,Url){	
	parent.addMenu(menu,Url);
}

</script>
</head>

<body>


<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	<tr class="trbgcolorForTitle" align="center" valign="middle">
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">用户名</font> </a></td>
		
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">企业名称</font> </a></td>
			<td class="border_right4" nowrap><a class="s03"><font
			color="#FFFFFF">网站名称</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">省份</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">城市</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">联系人</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">联系电话</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">状态</font> </a></td>
			<!--<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">日志信息查看</font> </a></td>
			--><td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">销售记录查看</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">充提记录查看</font> </a></td>
	</tr>
	<c:forEach items="${page.result}" var="member" varStatus = "memberStatus">
	<c:choose>
       <c:when test="${memberStatus.index%2==0}">
             <tr class="trForContent1">
       </c:when>
       <c:otherwise>
             <tr class="trForContent2">
       </c:otherwise>
	</c:choose>			
			<td class="border_top_right4" align="center" nowrap>${member.userName}&nbsp;</td>
			
			<td class="border_top_right4" align="center" nowrap>${member.businessName}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${member.website}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${member.province}&nbsp;</td>	
			<td class="border_top_right4" align="center" nowrap>${member.city}&nbsp;</td>	
			<td class="border_top_right4" align="center" nowrap>${member.contactPerson}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${member.contactPhone}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${member.statusString}&nbsp;</td>				
			<!--<td class="border_top_right4" align="center">				
				<a  href="javascript:showUrl('企业日志信息','businessLogList.do?memberCode=${member.memberCode}')">
					日志信息
				</a>			
			</td>
			--><td class="border_top_right4" align="center" nowrap>							
				<a  href="javascript:showUrl('企业销售记录','tradeList.do?memberCode=${member.memberCode}&userName=${member.userName}&memberType=1')">
					销售记录
				</a>
			</td>
			<td class="border_top_right4" align="center" nowrap>							
				
				<a  href="javascript:showUrl('企业充提记录','rechargePayoutsList.do?memberCode=${member.memberCode}&userName=${member.userName}')">
					充提记录
				</a>
			</td>
		</tr>
	</c:forEach>
</table>



<li:pagination methodName="memberQuery" pageBean="${page}" sytleName="black2"/>
</body>

