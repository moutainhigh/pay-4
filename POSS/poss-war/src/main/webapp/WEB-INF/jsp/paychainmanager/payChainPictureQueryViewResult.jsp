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
	
	function pictureManagerDetail(url){
		parent.addMenu("支付链图片审核",url);
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

		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">收款链接生成时间</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">过期时间</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">收款链接编号</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">商户名称</font> </a></td>		
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">收款名称</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">收款描述</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">图片审核状态</font> </a></td>		
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">图片审核通过时间</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">图片审核拒绝时间</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">操作</font> </a></td>

	</tr>
	<c:forEach items="${page.result}" var="pictureManager" varStatus = "pictureManagerStatus">
	<c:choose>
       <c:when test="${pictureManagerStatus.index%2==0}">
             <tr class="trForContent1">
       </c:when>
       <c:otherwise>
             <tr class="trForContent2">
       </c:otherwise>
	</c:choose>
		<td class="border_top_right4" align="center" nowrap>${pictureManager.strCreateDate}&nbsp;</td>
		<td class="border_top_right4" align="center" nowrap>${pictureManager.strOverdueDate}&nbsp;</td>		
		<td class="border_top_right4" align="center" nowrap>${pictureManager.payChainNumber}&nbsp;</td>
		<td class="border_top_right4" align="center" nowrap style="white-space:pre-wrap;word-wrap:break-word;word-break:break-all;" width="80">${pictureManager.zhName}&nbsp;</td>
		<td class="border_top_right4" align="center" nowrap style="white-space:pre-wrap;word-wrap:break-word;word-break:break-all;" width="170">${pictureManager.payChainName}&nbsp;</td>
		<td class="border_top_right4" align="center" nowrap  style="white-space:pre-wrap;word-wrap:break-word;word-break:break-all;" width="250">${pictureManager.descriptions}&nbsp;</td>
		<td class="border_top_right4" align="center" nowrap>		<c:if test="${pictureManager.pictureStatus==0}">
				未审核
			</c:if>
		 <c:if test="${pictureManager.pictureStatus==1}">
				审核通过
			</c:if>
			<c:if test="${pictureManager.pictureStatus==2}">
				审核拒绝
			</c:if>&nbsp;</td>		
		<td class="border_top_right4" align="center" nowrap><c:if test="${pictureManager.pictureStatus==1}">${pictureManager.updateTime}</c:if>&nbsp;</td>
		<td class="border_top_right4" align="center" nowrap><c:if test="${pictureManager.pictureStatus==2}">${pictureManager.updateTime}</c:if>&nbsp;</td>	
		<td class="border_top_right4" align="center" nowrap>
				<a href="javascript:pictureManagerDetail('picturePayChainManager.do?method=queryDetail&pictureOwnerId=${pictureManager.pictureOwnerId}');">查看</a>&nbsp;				
		</td>
		</tr>
	</c:forEach>
	
</table>
	
</form>
<li:pagination methodName="pictureManagerQuery" pageBean="${page}" sytleName="black2"/>
</body>

