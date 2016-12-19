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
	window.location="advertisePhotoManage.do?method=up&sort="+sort+"&id="+id+"&targets="+targets;
} 

function changeLocationDown(sort,id,targets){
	$("#changeLocation"+id).removeAttr("onclick");
	window.location="advertisePhotoManage.do?method=down&sort="+sort+"&id="+id+"&targets="+targets;
}

function delAdvertisePhoto(id,sort,targets){
	if(window.confirm("确定删除吗?")){
		window.location="advertisePhotoManage.do?method=delAdvertisePhoto&id="+id+"&sort="+sort+"&targets="+targets;
	}else{
		return false;
	}
}


function openwinPic(url){ 
	//window.open("advertiseManage.do?method=pictureView&id=");
	window.open(url,'','width=720,height=280');
	return false;
}


</script>
</head>

<body>
<h2 class='panel_title'>首页合作商户图片列表</h2>
<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	<tr class="trbgcolorForTitle" align="center" valign="middle">
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">标题</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">是否有效</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">排序</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">有效期</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">移动</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">操作</font> </a></td>
	</tr>
	<c:forEach items="${advertiseList}" var="adv" varStatus = "advStatus">
	<c:choose>
       <c:when test="${advStatus.index%2==0}">
             <tr class="trForContent1">
       </c:when>
       <c:otherwise>
             <tr class="trForContent2">
       </c:otherwise>
	</c:choose>			
			<td class="border_top_right4" align="center" nowrap>${adv.title}&nbsp;</td>
			<td class="border_top_right4" align="center" nowrap>
				<c:if test="${adv.available==0}">无效&nbsp;</c:if>
				<c:if test="${adv.available==1}">有效&nbsp;</c:if>
			</td>
			<td class="border_top_right4" align="center" nowrap>&nbsp;${adv.sort}
			</td>
			<td class="border_top_right4" align="center" nowrap>&nbsp;
			<fmt:formatDate value="${adv.starttime}" pattern="yyyy-MM-dd HH:mm:ss"/>~
			<fmt:formatDate value="${adv.endtime}" pattern="yyyy-MM-dd HH:mm:ss"/>
			</td>
			<td class="border_top_right4" align="center" nowrap>&nbsp;
				<c:if test="${adv.sort!=1}"><a href="javascript:void(0);" id="changeLocation${adv.id}" onclick="changeLocationUp('${adv.sort}','${adv.id}','${adv.targets}');" ><img src="images/up.png" width="16" height="16" border="0"></a>&nbsp;</c:if>
				<c:if test="${adv.sort!=locationCode}"><a href="javascript:void(0);" id="changeLocation${adv.id}" onclick="changeLocationDown('${adv.sort}','${adv.id}','${adv.targets}');"><img src="images/down.png" width="16" height="16" border="0"></a>&nbsp;</c:if>
			</td>
			<td class="border_top_right4" align="center" nowrap>
				<a href="advertisePhotoManage.do?method=getAdvertisePhoto&id=${adv.id}&sort=${adv.sort}&targets=${adv.targets}" >编辑 </a>&nbsp;&nbsp;
				<a href="javascript:void(0);" onclick="delAdvertisePhoto('${adv.id}','${adv.sort}','${adv.targets}');">删除 </a>&nbsp;&nbsp;
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
			<a href="advertisePhotoManage.do?method=initAdvertisePhoto" >新增首页合作商户图片 </a>
		</td>
	</tr>
</table>
</body>

