
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
			color="#FFFFFF">eMail</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">手机号码</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">姓名</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">证件类型</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">证件号码</font> </a></td>
			<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">用户状态</font> </a></td>
			<!--<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">日志信息查看</font> </a></td>
			-->
			<td class="border_right4"  nowrap><a class="s03"><font
			color="#FFFFFF">消费记录查看</font> </a></td>
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
			<td class="border_top_right4" align="center" nowrap>${member.email}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${member.moblie}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${member.realName}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${member.certType}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${member.certNo}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>${member.statusString}&nbsp;</td>						
			<!--<td class="border_top_right4" align="center">				
				<a  href="javascript:showUrl('个人日志信息','personalLogList.do?memberCode=${member.memberCode}')">
					日志信息
				</a>			
			</td>
			-->
			<td class="border_top_right4" align="center">							
				<a  href="javascript:showUrl('个人消费信息','tradeList.do?memberCode=${member.memberCode}&userName=${member.userName}&memberType=0')">
					消费记录
				</a>
			</td>
			<td class="border_top_right4" align="center">							
				
				<a  href="javascript:showUrl('个人充提记录','rechargePayoutsList.do?memberCode=${member.memberCode}&userName=${member.userName}')">
					充提记录
				</a>
			</td>
			
		</tr>
	</c:forEach>
</table> 

<li:pagination methodName="memberQuery" pageBean="${page}" sytleName="black2"/>
</body>

