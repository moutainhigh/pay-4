<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/fitaglibs.jsp"%> 

<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">历史银行对账文件详情</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>

 <form action="" method="post" id="mainfromId">
 	<input type="hidden"   name="busiFlag"  value="${webQueryReconcileDTO.busiFlag }" />
 	<input type="hidden"   name="countFlag" value="${webQueryReconcileDTO.countFlag}"  />
 	<input type="hidden"   name="bankCode"  value="${webQueryReconcileDTO.bankCode}" />
 	<input type="hidden"   name="providerCode"  value="${webQueryReconcileDTO.providerCode}" />
 	<input type="hidden"   name="fileKy"  value="${fileKy}" />
 </form>
 

<div id="resultListDiv" class="listFence"></div>
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div> 

      
  <script type="text/javascript">
  	
  	$(document).ready(function(){queryDetail();}); 
	function queryDetail(pageNo,totalCount) {
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount;
			$.ajax({
				type: "POST",
				url: "reconcile.download.do?method=queryReconcileImportDetail",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
	}
	
  </script>