<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>

<link rel="stylesheet" href="./css/main.css">
<script src="./js/common.js"></script>

<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>
<script type="text/javascript" src="${ctx}/fckeditor/fckeditor.js"></script>
<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";

function limtTextArea() {   
    $("textarea[maxlength]").bind('input propertychange', function() {   
        var maxLength = $(this).attr('maxlength');   
        if ($(this).val().length > maxLength) {   
            return false;
              
        }   
    })   
}
window.onload = function()
{
	var sBasePath = "<%=request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + request.getContextPath() + "/fckeditor/" %>"
	var oFCKeditor = new FCKeditor('message') ;
	oFCKeditor.BasePath	= sBasePath ;
	oFCKeditor.ReplaceTextarea() ;
}

function closePage() {
	var announcementId = document.getElementById('announcementId').value;
	//如果announcementId无值则关闭添加公告页面，否则关闭编辑公告页面
	if("" == announcementId){
		parent.closePage('websiteAnnouncement.do?method=createAnnouncementView');
	}else{
		parent.closePage('websiteAnnouncement.do?method=createAnnouncementView&announcementId='+announcementId);
	}
}

function submitSave() {
	var subject = document.getElementById('subject').value;
	var message = document.getElementById('message').value;
	var displaysort = document.getElementById('displaysort').value;
	var message = document.getElementById('message').value;

	if("" == trim(subject)){
		alert("请输入主题");
		return;
	}

	if("" == trim(displaysort)){
		alert("请输入显示顺序");
		return;
	}
	var str = /^[0-9]*[1-9][0-9]*$/;
	if(str.test(displaysort)==false){
		document.getElementById('displaysort').value="1";
		return;
	}
	if(message.length > 1024){
		alert("公告内容长度过长");
		return;
	}

	document.getElementById('announcementCreate').submit();
}
function trim(str){ //删除左右两端的空格
	return str.replace(/(^\s*)|(\s*$)/g, "");
}
</script>
</head>

<body>

<h2>WEBSITE添加公告</h2>

<form id="announcementCreate" name="announcementCreate" method="post" action="websiteAnnouncement.do">
	<input type="hidden" name="method" value="createSaveAnnouncement"/>
	<input type="hidden" id="announcementId" name="announcementId" value="${result.announcementId}"/>
	<table class="border_all2" width="900"  border="1" cellspacing="0" cellpadding="0" align="center">
		<tr class="trForContent1">
			<td class="border_top_right4"  align="right" >公告主题：</td>
			<td class="border_top_right4"  align="left">
				<input type="text" id="subject"  name="subject" maxlength= "30" value="${result.subject}" style="width: 620px;"/>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4"  align="right">公告内容：</td>
			<td class="border_top_right4"  align="left">
				<textarea rows="40" cols="100" id="message" name="message" >${result.message}</textarea>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4"  align="right">显示顺序：</td>
			<td class="border_top_right4"  align="left">
				<input type="text" id="displaysort"  name="displaysort" maxlength="2" value="${result.displaysort}" style="width: 20px;"/>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4"  colspan="2" align="center">
				<!-- 
				<input type="submit" value="保存">	
				 -->
				<input type = "button"  onclick="javascript:submitSave();" value="保存">
				<input type = "button"  onclick="javascript:closePage();" value="关闭">
			</td>
		</tr>
	</table>
</form>
		<div id="resultListDiv" class="listFence"></div>		
		<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
		<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
		</div>

</body>

