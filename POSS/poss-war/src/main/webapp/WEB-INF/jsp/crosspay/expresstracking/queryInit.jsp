<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="li_new" uri="page"%>
<h2 class="panel_title">运单管理</h2>
<form action="" method="post" id="mainfromId">
	<table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >订单号：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="orderId" name="orderId" value="${expressTracking.orderId }">
	      </td>
	      <td align="right" class="border_top_right4" >快递公司：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="expressCom" name="expressCom" value="${expressTracking.expressCom}">
	      </td>
	     </tr>
	     <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >运单状态：</td>
	      <td class="border_top_right4">
	      	<select id="status" name="status">
	      		<option value="">请选择</option>
	      		<option value="0" <c:if test="${expressTracking.status == '0'}">selected</c:if>>未上传运单</option>
	      		<option value='1' <c:if test="${expressTracking.status == '1'}">selected</c:if>>待审核</option>
				<option value='2' <c:if test="${expressTracking.status == '2'}">selected</c:if>>审核通过</option>
				<option value='3' <c:if test="${expressTracking.status == '3'}">selected</c:if>>审核未通过</option>
	      	</select>
	      </td>
	      <td align="right" class="border_top_right4" >快递单号：</td>
	      <td class="border_top_right4">
	      	<input type="text" id="trackingId" name="trackingNo" value="${expressTracking.trackingNo}">
	      </td>
	     </tr>
	<tr class="trForContent1">
      	<td align="right" class="border_top_right4">运单上传起止日期：</td>
      	<td class="border_top_right4">
	      	<input class="Wdate" type="text" id="uploadBeginTime"  name="uploadBeginTime" value='<fmt:formatDate value="${expressTracking.uploadBeginDate}" type="date"/>'  onClick="WdatePicker({minDate:'%y-%M-{%d-30}',maxDate:'#F{$dp.$D(\'uploadBeginTime\')}'})">
	        	～
			<input class="Wdate" type="text" id="uploadEndTime" name="uploadEndTime"  value='<fmt:formatDate value="${expressTracking.uploadEndDate}" type="date"/>' onClick="WdatePicker({minDate:'#F{$dp.$D(\'uploadStartTime\')}',maxDate:'%y-%M-%d'})">
      	</td>
      	<td class=border_top_right4 align="right" >会员号：</td>
      	<td class="border_top_right4" >
      		<input type="text" id="partnerId" name="partnerId" value="${expressTracking.partnerId}">
      	</td>
    </tr>
	    <tr class="trForContent1">
	      <td align="center" class="border_top_right4" colspan="4">
	      <input type="button"  name="butSubmit" value="查询" class="button2" onclick="search();">
	      <input type="button"  value="批量操作" class="button2" onclick="bacthRev();">
	      </td>
	    </tr>
   </table>
</form>

<c:if test="${not empty messageCode}">
	<font color="red"><b>操作成功！</b></font>
</c:if>
 
<div id="resultListDiv" class="listFence"></div>

<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>   

<div id="expresstrackDiv" style="display: none">
	<form action="expresstracking.do" id="expresstrackForm" method="post" >
		<input type="text" name="method" value="bacthReviewed">
		<input type="text" name="orderId" id="orderId1">
		<input type="text" name="expressCom" id="expressCom1">
		<input type="text" name="status" id="status1">
		<input type="text" name="trackingNo" id="trackingNo1">
		<input type="text" name="uploadBeginTime" id="uploadBeginTime1">
		<input type="text" name="uploadEndTime" id="uploadEndTime1">
		<input type="text" name="partnerId" id="partnerId1">
		<input type="text" name="ids" id="ids">
	</form>
</div>	 
 
  <script type="text/javascript">
	$(document).ready(function(){
		search();
	});  
	
	function search(pageNo,totalCount,pageSize) {
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize() + "&pageNo=" + pageNo
		+ "&totalCount=" + totalCount;
		$.ajax({
			type: "POST",
			url: "${ctx}/expresstracking.do?method=list",
			data: pars,
			success: function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
	}
	
	function toAudit(tradeOrderNo,trackingNo,expressCom,queryUrl,orderId){
		window.location.href="${ctx}/expresstracking.do?method=toAudit" + "&tradeOrderNo="+tradeOrderNo +"&trackingNo="+trackingNo+"&expressCom="+expressCom+"&queryUrl="+queryUrl+"&orderId="+orderId;
	}
	
	function bacthRev(){
		var ids="";
		 $("input[type='checkbox']").each(function(){
			 if(this.checked){
				if($(this).val()!='on'){
					ids+=$(this).val()+",";							
				}
			};
		 })
		 	if(ids==''){
				alert("请选择需要审核的运单！");
				return;
			}
			// var pars = $("#mainfromId").serialize()+"&ids="+ids;
			 //window.location.href="${ctx}/expresstracking.do?method=bacthReviewed&ids="+ids+"&"+pars;
	
		var orderId=$("#orderId").val();
		var expressCom=$("#expressCom").val();
		var status=$("#status").val();
		var trackingNo=$("#trackingId").val();
		var uploadBeginTime=$("#uploadBeginTime").val()
		var uploadEndTime=$("#uploadEndTime").val();
		var partnerId=$("#partnerId").val();
			 
		$("#orderId1").val(orderId);
		$("#expressCom1").val(expressCom);
		$("#status1").val(status);
		$("#trackingNo1").val(trackingNo);
		$("#uploadBeginTime1").val(uploadBeginTime);
		$("#uploadEndTime1").val(uploadEndTime);
		$("#partnerId1").val(partnerId);
		$("#ids").val(ids);
		$("#expresstrackForm").submit();
	}
	
  </script>