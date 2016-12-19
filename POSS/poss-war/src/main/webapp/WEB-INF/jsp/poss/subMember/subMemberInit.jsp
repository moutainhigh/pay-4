<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/js/common.js"></script>

<html>
<head>
<title>商户信息维护</title>
<script type="text/javascript">
		function userQuery(pageNo,totalCount,pageSize){
		  	$('#infoLoadingDiv').dialog('open');
			var pars = $("#form1").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount+ "&pageSize=" + pageSize;
			$.ajax({
				type: "POST",
				url: "${ctx}/report/subMember.do?method=query&parentId=${memberToView.memberCode}",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#paginationResult').html(result);
				}
			});
        }

		$(document).ready(function(){userQuery();});
</script>
</head>

<body>
	<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
			<tr>
				<td height="18">
					<div align="center">
						<font class="titletext">商户信息维护</font>
					</div>
				</td>
			</tr>
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
	</table>



<form action="" method="post" id="form1" name="form1">
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
    <tr class="trForContent1">
    	<td align="right" class="border_top_right4">分子公司名称：</td>
      	<td class="border_top_right4">
	    	<input type="text" name="memberName" maxlength="60" readonly="readonly" style="color: gray;background-color: #ccc" value="${memberToView.memberName}">
	    </td>
      	<td class=border_top_right4 align="right" >会员编号：</td>
      	<td class="border_top_right4" >
        	<input type="text" name="memberCode" readonly="readonly" style="color: gray;background-color: #ccc" value="${memberToView.memberCode}"> 
      	</td>
    </tr>
    
    <tr class="trForContent1">
      <td align="center" class="border_top_right4" colspan="4">
      	<input type="button" onclick="javascript:window.location.href='${ctx}/report/subMember.do?method=addInit&id=${memberToView.sequenceId}'" name="submitBtn" value="新增商户" class="button2">&nbsp;&nbsp;&nbsp;
      	<input type="button" onClick="javascript:window.location.href='${ctx}/report/innerMember.do';" name="submitBtn" value="返 回" class="button2">
      </td>
    </tr>
  </table>
  <c:if test="${not empty message}">
 	<div>
		<li style="color: red;">${message}</li>
	</div>
  </c:if>
</form>
 
<div align="center" id="paginationResult"></div>
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>
<p>&nbsp;</p>
</body>
</html>
