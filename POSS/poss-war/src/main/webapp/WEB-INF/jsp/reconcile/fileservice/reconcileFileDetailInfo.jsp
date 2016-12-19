<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">查看银行对账文件详细信息</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<br/>

<div id="resultListDiv" class="listFence"></div> 
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息处理中, 请稍候...
</div>
<form name="detailInfoForm" id="detailInfoFormId" method="POST">
	<input type="hidden" id="fileId" name="fileId" value="${fileId}"/>
 	<input type="hidden" id="busiDate" name="busiDate" value="${busiDate}"/>
 	<input type="hidden" id="orgCode" name="orgCode" value="${orgCode}"/>
 	<input type="hidden" id="status" name="status" value="${status}"/>
</form>

<script type="text/javascript">

$(document).ready(function(){submitByHref();});
	function submitByHref(pageNo,totalCount,pageSize) {		
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#detailInfoFormId").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount+"&pageSize="+pageSize;
			$.ajax({
				type: "POST",
				url: "fo_rc_queryreconcile.do?method=queryUploadFileDetail",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
	}

</script>
