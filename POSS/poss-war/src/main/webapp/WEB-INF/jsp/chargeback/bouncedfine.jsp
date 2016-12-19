<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="li_new" uri="page"%>
<h2 class="panel_title">拒付罚款查询</h2>
<form action="" method="post" id="mainfromId">
	<table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	    <tr class="trForContent1">
	    	<td class="border_top_right4" align="right" >会员号：</td>
			<td class="border_top_right4" align="left" >
				<input name="memberCode" id ="memberCode" >
			</td>
			<td align="right" class="border_top_right4"  >商户名称：</td>
	      	<td class="border_top_right4">
	      		<input name="memberName" id ="memberName" >
	     	 </td>
	     	 <td align="right" class="border_top_right4">拒付月份：</td>
      		<td class="border_top_right4">
      		
      		    <input class="Wdate" type="text" id="startTime" value="${startTime}" name="startTime"  onClick="WdatePicker({dateFmt:'yyyy-MM',maxDate:'#F{$dp.$D(\'endTime\')}'})">
				        	～
				<input class="Wdate" type="text" id="endTime" name="endTime" value="${endTime}" onClick="WdatePicker({dateFmt:'yyyy-MM',minDate:'#F{$dp.$D(\'startTime\')}'})">
      		</td>
	    </tr>
	    <tr class="trForContent1">
	      <td align="center" class="border_top_right4" colspan="8">
	      <input type="button"  name="butSubmit" value="查  询" class="button2" onclick="search();">
	      <input type="button"  name="butSubmit" value="余额不足的商户" class="button2" onclick="balance();">
	      </td>
	    </tr>
   </table>
</form>
 
<div id="resultListDiv" class="listFence"></div>

<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>   
  <script type="text/javascript">
  $(document).ready(function(){
		search();
	});  
	function search(pageNo,totalCount,pageSize) {
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize()+  "&pageNo=" + pageNo + "&totalCount=" + totalCount;
		$.ajax({
			type: "POST",
			url: "${ctx}/bouncedFine.do?method=list",
			data: pars,
			success: function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
	}
	
	function balance(){
		window.location.href = "${ctx}/bouncedFine.do?method=memberBalanceNotEnough";
	}
  </script>