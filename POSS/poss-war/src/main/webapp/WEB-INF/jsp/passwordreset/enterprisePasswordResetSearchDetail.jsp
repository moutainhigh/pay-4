<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<head>

<link rel="stylesheet" href="./css/main.css">
<script src="./js/common.js"></script>

<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

<script language="javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";

function closePage() {
	var id = $('#id').val();
	var url="enterpriseSearchDetail.do?method=enterpriseSearchDetail&id="+id;
	parent.closePage(url);
}

function submitSave() {
	document.getElementById('enterpriseSearchDetailSave').submit();
}
function trim(str){ //删除左右两端的空格
	return str.replace(/(^\s*)|(\s*$)/g, "");
}
function viewmypic(mypic,imgfile) {   
	if (imgfile.value){  
		mypic.src=imgfile.value; 
		mypic.style.display="";        
		mypic.border=1;        
	}        
}  

</script>
</head>

<body>
<h2 class="panel_title">企业会员重置密码详情</h2>
<table width="80%" border="0" cellspacing="0" cellpadding="0" align="center" height="21" >
	<!-- <tr>
		<td width="50%" class="border_top_right4" height="2" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td width="50%" class="border_top_right4" height="18">
		<div align="center"><font class="titletext">企 业 会 员 重 置 密 码 详 情  </font></div>
		</td>
	</tr> -->
	<c:if test="${message!=null}">
		<tr>
			<td width="50%" class="border_top_right4" height="2" ><font color="red" >${message}</font></td>
		</tr>
	</c:if>
	<!--  
		<c:if test="${resultDto.status !=1}">
			<tr>
				<td width="50%" class="border_top_right4" height="2" ><font color="red" >该记录已经初审过,不能再进行初审操作</font></td>
			</tr>
		</c:if>
	-->
</table>


<form id="enterpriseSearchDetailSave" name="enterpriseSearchDetailSave" method="post" action="enterpriseSearchDetailSave.do">
	<input type="hidden" id ="id" name="id" value="${resultDto.id}"/>
	<input type="hidden" name="method" value="enterpriseSearchDetailSave"/>
	<table class="border_all2" width="80%" border="0" cellspacing="0"cellpadding="1" align="center">	
	<tr class="trForContent1">
		<td width="50%" class="border_top_right4"  align="right" >会员信息：</td>
		<td width="50%" class="border_top_right4" align="left" >&nbsp;</td>
	</tr>
	<tr class="trForContent1">
		<td width="50%" class="border_top_right4" align="right" >申请单编号：</td>
		<td width="50%" class="border_top_right4" align="left" >
			<input	type="text" id="formNumber" name="formNumber" value="${resultDto.formNumber}" maxlength= "17" disabled ="disabled">
		</td>
	</tr>	
	<tr class="trForContent1">
		<td width="50%" class="border_top_right4" align="right" >会员登录号：</td>
		<td width="50%" class="border_top_right4" align="left" >
			<input	type="text" id="loginName" name="loginName" value="${resultDto.loginName}" maxlength= "17" disabled ="disabled">
		</td>
	</tr>	
	<tr class="trForContent1">
		<td width="50%" class="border_top_right4" align="right" >会员中文名：</td>
		<td width="50%" class="border_top_right4" align="left" >
			<input	type="text" id="memberName" name="memberName" value="${resultDto.memberName}" maxlength= "8" disabled ="disabled">
		</td>
	</tr>	
	<tr class="trForContent1">
		<td width="50%" class="border_top_right4" align="right" >经办人姓名：</td>
		<td width="50%" class="border_top_right4" align="left" >
			<input	type="text" id="proposerName" name="proposerName" value="${resultDto.proposerName}" maxlength= "8" disabled ="disabled">
		</td>
	</tr>	
	<tr class="trForContent1">
		<td width="50%" class="border_top_right4" align="right" >经办人身份证：</td>
		<td width="50%" class="border_top_right4" align="left" >
			<input	type="text" id="idcard" name="idcard" value="${resultDto.idcard}" maxlength= "18" disabled ="disabled">
		</td>
	</tr>	
	<%-- <tr class="trForContent1">
		<td width="50%" class="border_top_right4" align="right" >经办人联系电话：</td>
		<td width="50%" class="border_top_right4" align="left" >
			<input	type="text" id="mobile" name="mobile" value="${resultDto.mobile}" maxlength= "11" disabled ="disabled">
		</td>
	</tr> --%>	
	<c:if test="${resultDto.proposerObverseUrl!=null}">
		<tr class="trForContent1">
			<td width="50%" class="border_top_right4" align="right" >经办人身份证正面：</td>
			<td width="50%" class="border_top_right4" align="left" >
				 <img src="enterpriseProposerObverseUrl.do?method=proposerObverseUrl&proposerObverseUrl=${resultDto.proposerObverseUrl}" width="400" height="260"  />
			</td>
		</tr>
	</c:if>
	<c:if test="${resultDto.proposerReverseurl!=null}">
		<tr class="trForContent1">
			<td width="50%" class="border_top_right4" align="right" >经办人身份证背面：</td>
			<td width="50%" class="border_top_right4" align="left" >
				 <img src="enterpriseProposerReverseurl.do?method=proposerReverseurl&proposerReverseurl=${resultDto.proposerReverseurl}"   width="400" height="260"/>
			</td>
		</tr>
	</c:if>	
	<c:if test="${resultDto.legalObverseUrl!=null}">
		<tr class="trForContent1">
			<td width="50%" class="border_top_right4" align="right" >法人身份证正面：</td>
			<td width="50%" class="border_top_right4" align="left" >
	  			<img src="enterpriseLegalObverseUrl.do?method=legalObverseUrl&legalObverseUrl=${resultDto.legalObverseUrl}" width="400" height="260"/>
			</td>
		</tr>
	</c:if>	
	<c:if test="${resultDto.legalReverseUrl!=null}">
		<tr class="trForContent1">
			<td width="50%" class="border_top_right4" align="right" >法人身份证背面：</td>
			<td width="50%" class="border_top_right4" align="left" >
				<img src="enterpriseLegalReverseUrl.do?method=legalReverseUrl&legalReverseUrl=${resultDto.legalReverseUrl}" width="400" height="260"/>
			</td>
		</tr>
	</c:if>	
	<c:if test="${resultDto.licenceUrl!=null}">
		<tr class="trForContent1">
			<td width="50%" class="border_top_right4" align="right" >公司营业执照正本复印件：</td>
			<td width="50%" class="border_top_right4" align="left" > 
				<img src="enterpriseLicenceUrl.do?method=licenceUrl&licenceUrl=${resultDto.licenceUrl}" width="400" height="260"/>
			</td>
		</tr>
	</c:if>	
	<tr class="trForContent1">
		<td width="50%" class="border_top_right4" align="right" >申请表影像：</td>
		<td width="50%" class="border_top_right4" align="left" >
			<img src="enterpriseFormUrl.do?method=formUrl&formUrl=${resultDto.formUrl}" width="400" height="260"/>
		</td>
	</tr>

	<tr class="trForContent1">
			<td width="50%" class="border_top_right4" align="right" >备注：</td>
			<td width="50%" class="border_top_right4" align="left" >
				<textarea rows="7" cols="30" id="remark" name="remark" <c:if test="${resultDto.status!=1}">disabled ="disabled"</c:if>>${resultDto.remark}</textarea>
			</td>
	</tr>
	<tr class="trForContent1">
		<td width="50%" class="border_top_right4" colspan="2" align="center">
			<c:if test="${resultDto.status==1}">
				<input type = "button" class="button2" onclick="javascript:submitSave();" value="初审确认">
			</c:if>
			<input type = "button" class="button2" onclick="javascript:closePage();" value="关闭">
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

