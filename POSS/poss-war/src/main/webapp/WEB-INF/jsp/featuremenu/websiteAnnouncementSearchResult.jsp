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
function deleteAnnouncement(announcementId){
	//alert(announcementId);
	 location.href="websiteAnnouncement.do?method=deleteAnnouncement&announcementId="+announcementId;
}

function updateAnnouncement(announcementId){
	//alert(announcementId);
	 var url = "websiteAnnouncement.do?method=createAnnouncementView&announcementId="+announcementId;
	 parent.addMenu("编辑公告",url);
}
function createAnnouncement(){
	 var url = "websiteAnnouncement.do?method=createAnnouncementView";
	 parent.addMenu("新增公告",url);
}
function sort(){
	var url = "websiteAnnouncement.do?method=announcementSortView";
	parent.addMenu("排序",url);
}
</script>
</head>

<body>
<tr class="trForContent2" align="left" >
	<input type = "button"  onclick="sort();" value="公告显示排序">
	<input type = "button"  onclick="createAnnouncement();" value="新增公告">
</tr>
<br>
<table align="center" class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
	<input type="hidden" name="announcementId" value="${announcementId}"/>
	
		
	<thead>
	<tr class="" align="center" valign="middle">
	<th class=""  >创建者</th>
	<th class=""  >主题</th>
	<th class=""  >操作</th>
	</tr>
	</thead>
	<c:forEach items="${page.result}" var="announcementList" varStatus = "personalStatus">
		<c:choose>
	       <c:when test="${personalStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>		
				<td class="" align="center" >${announcementList.author}&nbsp;</td>	
				<td class="" align="center" >${announcementList.subject}&nbsp;</td>
				<td class="" align="center" >
					<a href="javascript:void(0);" onclick="createAnnouncement();"><input class="button2" type="button" value="新增"></a>
					<a href="javascript:void(0);" onclick="updateAnnouncement('${announcementList.announcementId}');"><input class="button2" type="button" value="编辑"></a>
					<a href="javascript:void(0);" onclick="deleteAnnouncement('${announcementList.announcementId}');"><input class="button2" type="button" value="删除"></a>
				</td>
		</tr>
	</c:forEach>
</table>
<li:pagination methodName="websiteAnnouncementSearch" pageBean="${page}" sytleName="black2"/>
</body>

