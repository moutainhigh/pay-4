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

function changeLocationUp(sort,id,targets){
	$("#changeLocation"+id).removeAttr("onclick");
	window.location="advertiseManage.do?method=up&sort="+sort+"&id="+id+"&targets="+targets;
} 

function changeLocationDown(sort,id,targets){
	$("#changeLocation"+id).removeAttr("onclick");
	window.location="advertiseManage.do?method=down&sort="+sort+"&id="+id+"&targets="+targets;
}

function delAdvertise(id,sort,targets){
	if(window.confirm("确定删除吗?")){
		window.location="advertiseManage.do?method=delAdvertise&id="+id+"&sort="+sort+"&targets="+targets;
	}else{
		return false;
	}
}

function openwinPic(url){ 
	window.open(url,'','width=720,height=280');
	return false;
}

</script>
</head>

<body>
<h2 class="panel_title">
<c:if test="${targets!=2}">个人首页幻灯片广告列表</c:if>
		<c:if test="${targets==2}">企业首页幻灯片广告列表</c:if></h2>
<%--
<form action="advertiseManage.do?method=advertiseList" method="post" name="advertisesSearch" id="advertisesSearch">

<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	<tr class="trForContent1">
		<td  class="" align="right" >广告位置：</td>
		<td class=""align="left" >
			<select name="targets">
					<option <c:if test="${targets==1}">selected</c:if> value="1" >个人首页幻灯片广告</option>
					<option <c:if test="${targets==2}">selected</c:if> value="2">企业首页幻灯片广告</option>
			</select>
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			<input type="submit" id="submitBtn" class="button2" value="检索">
		</td>
	</tr>
</table>
</form> --%>

<table align="center" class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
	<thead><tr class="" align="center" valign="middle">
		<th class=""  >标题</th>
		<th class=""  >是否有效</th>
		<th class=""  >排序</th>
		<th class=""  >有效期</th>
		<th class=""  >移动</th>
		<th class=""  >操作</th>
	</tr>
	</thead>
	<c:forEach items="${advList}" var="adv" varStatus = "advStatus">
	<c:choose>
       <c:when test="${advStatus.index%2==0}">
             <tr class="trForContent1">
       </c:when>
       <c:otherwise>
             <tr class="trForContent2">
       </c:otherwise>
	</c:choose>			
			<td class="" align="center" >${adv.title}&nbsp;</td>
			<td class="" align="center" >
				<c:if test="${adv.available==0}">无效&nbsp;</c:if>
				<c:if test="${adv.available==1}">有效&nbsp;</c:if>
			</td>
			<td class="" align="center" >&nbsp;${adv.sort}
			</td>
			<td class="" align="center" >&nbsp;
			<fmt:formatDate value="${adv.starttime}" pattern="yyyy-MM-dd HH:mm:ss"/>~
			<fmt:formatDate value="${adv.endtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</td>
			<td class="" align="center" >&nbsp;
			<c:if test="${targets==1}">
				<c:if test="${adv.sort!=1}"><a href="javascript:void(0);" id="changeLocation${adv.id}" onclick="changeLocationUp('${adv.sort}','${adv.id}',1);" ><img src="images/up.png" width="16" height="16" border="0"></a>&nbsp;</c:if>
				<c:if test="${adv.sort!=locationCode}"><a href="javascript:void(0);" id="changeLocation${adv.id}" onclick="changeLocationDown('${adv.sort}','${adv.id}',1);"><img src="images/down.png" width="16" height="16" border="0"></a>&nbsp;</c:if>
			</c:if>
			<c:if test="${targets==2}">
				<c:if test="${adv.sort!=1}"><a href="javascript:void(0);" id="changeLocation${adv.id}" onclick="changeLocationUp('${adv.sort}','${adv.id}',2);" ><img src="images/up.png" width="16" height="16" border="0"></a>&nbsp;</c:if>
				<c:if test="${adv.sort!=locationCode}"><a href="javascript:void(0);" id="changeLocation${adv.id}" onclick="changeLocationDown('${adv.sort}','${adv.id}',2);"><img src="images/down.png" width="16" height="16" border="0"></a>&nbsp;</c:if>
			</c:if>
			</td>
			<td class="" align="center" >
				<c:if test="${targets==1}">
					<a href="advertiseManage.do?method=getAdvertise&id=${adv.id}&sort=${adv.sort}&targets=1" >编辑 </a>&nbsp;&nbsp;
					<a href="javascript:void(0);" onclick="delAdvertise('${adv.id}','${adv.sort}',1);">删除 </a>&nbsp;&nbsp;
				</c:if>
				<c:if test="${targets==2}">
					<a href="advertiseManage.do?method=getAdvertise&id=${adv.id}&sort=${adv.sort}&targets=2" >编辑 </a>&nbsp;&nbsp;
					<a href="javascript:void(0);" onclick="delAdvertise('${adv.id}','${adv.sort}',2);">删除 </a>&nbsp;&nbsp;
				</c:if>
				<a href="javascript:void(0);" onclick="openwinPic('${adv.imgpath}');">查看图片</a>&nbsp;&nbsp;
			</td>
		</tr>
	</c:forEach>
</table>
<br>
<br>
<table width="90%">
	<tr>
		<td align="right">
		<c:if test="${targets==1}"><a href="advertiseManage.do?method=initAdvertise&targets=1" >新增个人首页幻灯片广告 </a></c:if>
		<c:if test="${targets==2}"><a href="advertiseManage.do?method=initAdvertise&targets=2" >新增企业首页幻灯片广告 </a></c:if>
		</td>
	</tr>
</table>
</body>

