<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">对账结果明细</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
 <form action="" method="post" id="mainfromId">
 	<input type="hidden"   name="busiFlag"  value="${busiFlag }" />
 	<input type="hidden"   name="allCount"  value="${allCount }" />
 	<input type="hidden"   name="countFlag" value="${countFlag}"  />
 	<input type="hidden"   name="withdrawBankId"  value="${withdrawBankId }" />
 	<input type="hidden" name="startDate" value='<fmt:formatDate value="${startDate}" type="date"/>'  >
	<input type="hidden" name="endDate"  value='<fmt:formatDate value="${endDate}" type="date"/>' >
 </form>
 
<div id="resultListDiv" class="listFence"></div>

  <div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
	</div>    
  <script type="text/javascript">
	
	$(document).ready(function(){query();}); 
	function query(pageNo,totalCount,pageSize) {
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize()+ "&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
			$.ajax({
				type: "POST",
				url: "fo-rc-queryreconcile.do?method=queryResultDetail",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
	}
	
  </script>