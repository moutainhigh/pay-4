<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="li_new" uri="page"%>
<h2 class="panel_title">提现申请</h2>
<form action="" method="post" id="mainfromId">
	<table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	    <tr class="trForContent1">
	      
	      <td align="right" class="border_top_right4" >商户号：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="partnerId" name="partnerId">
	      </td>
	      
	       <td align="right" class="border_top_right4" >提现日期：</td>
	      <td class="border_top_right4">
	      	<input class="Wdate" type="text" id="sDate" name="sDate"  onClick="WdatePicker()">-
	      	<input class="Wdate" type="text" id="eDate" name="eDate"  onClick="WdatePicker()">
	      </td>
	      
	     </tr>
	    <tr class="trForContent1">
	      <td align="center" class="border_top_right4" colspan="4">
	      <input type="button"  name="butSubmit" value="查询" class="button2" onclick="search();">
	      </td>
	    </tr>
   </table>
</form>
 
<div id="resultListDiv" class="listFence"></div>

<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>   
	 
  <script type="text/javascript">
	/* $(document).ready(function(){
		search();
	});  */
	
	function search(pageNo,totalCount,pageSize) {
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize()+"&pageNo=" + pageNo + "&totalCount=" + totalCount+"&pageSize="+pageSize;
		$.ajax({
			type: "POST",
			url: "${ctx}/crosspay/withdrawalsApproval.do?method=search",
			data: pars,
			success: function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
	}
	
	function approval(id){
		if(window.confirm("确定要审核该信息吗?"))
		{
			$.ajax({
				type: "POST",
				url: "${ctx}/crosspay/withdrawalsApproval.do?method=approval",
				data: {"id":id},
				success: function(result) {
					$('#resultListDiv').html(result);
				}
			});  
		}
	}
	
	function complete(id){
		if(window.confirm("确定该信息是提现完成吗?"))
		{
			$.ajax({
				type: "POST",
				url: "${ctx}/crosspay/withdrawalsApproval.do?method=complete",
				data: {"id":id},
				success: function(result) {
					$('#resultListDiv').html(result);
				}
			});  
		}
	}
  </script>