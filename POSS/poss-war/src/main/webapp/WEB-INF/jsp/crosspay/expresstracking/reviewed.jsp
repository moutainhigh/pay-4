<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<h2 class="panel_title">运单审核</h2>
	<form action="" id="checkStatusForm">
	<input name="orderId" id="orderId" type='hidden' value="${expressTracking.orderId }"/>
	<input name="expressCom" id="expressCom" type='hidden' value="${expressTracking.expressCom}"/>
	<input name="status" id="status" type='hidden' value="${expressTracking.status}"/>
	<input name="trackingNo" id="trackingNo" type='hidden' value="${expressTracking.trackingNo}"/>
	<input name="uploadBeginTime" id="uploadBeginTime" type='hidden' value="${expressTracking.uploadBeginTime}"/>
	<input name="uploadEndTime" id="uploadEndTime" type='hidden' value="${expressTracking.uploadEndTime}"/>
	<input name="partnerId" id="partnerId" type='hidden' value="${expressTracking.partnerId}"/>
	<input name="ids" id="ids" type='hidden' value="${ids}"/>
	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
		
		<tr class="trForContent1">
			<td class="border_top_right4"  align="right" width="50%">本次批量操作数量:</td>
			<td class="border_top_right4" >
				<input name="batchCount" id="batchCount" readonly="readonly" value="${count}"/></td>
			</tr>
			<tr class="trForContent1">
			<td class="border_top_right4"  align="right">审     核:</td>
			<td class="border_top_right4" >
				<select id="status" name="status1">
							<option value="">请选择</option>
							<option value='2'>审核通过</option>
							<option value='3'>审核未通过</option>
				</select>
	      	</td>
	      	</tr>
			<tr class="trForContent1">
			<td class="border_top_right4"  align="right"> 备注:</td>
			<td class="border_top_right4" ><textarea cols="20" rows="6" id="remark" name="remark"></textarea></td>
			</tr>
			<tr class="trForContent1">
				<td class="border_top_right4"  colspan="2" align="center">
					<input type="button"  value="确定"  onclick="reviewedStatus();">
					<input type="button" value="返回"  onclick="toIndex();">
				</td>
			</tr>
	</table>
	</form>
	
<c:if test="${not empty messageCode}">
	<font color="red"><b>操作成功！</b></font>
</c:if>
	<div id="expresstrackDiv" style="display: none">
	<form action="expresstracking.do" id="expresstrackForm" method="post" >
		<!-- <input type="text" name="method" value="bacthReviewed"> -->
		<input type="text" name="orderId" id="orderId1">
		<input type="text" name="expressCom" id="expressCom1">
		<input type="text" name="status" id="status1">
		<input type="text" name="trackingNo" id="trackingNo1">
		<input type="text" name="uploadBeginTime" id="uploadBeginTime1">
		<input type="text" name="uploadEndTime" id="uploadEndTime1">
		<input type="text" name="partnerId" id="partnerId1">
	<!-- 	<input type="text" name="ids" id="ids"> -->
	</form>
</div>	 
  <script type="text/javascript">
  
	function toIndex(){
		var orderId=$("#orderId").val();
		var expressCom=$("#expressCom").val();
		var status=$("#status").val();
		var trackingNo=$("#trackingNo").val();
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
	//	$("#ids").val(ids);
		$("#expresstrackForm").submit();
	/* 	var pars = $("#checkStatusForm").serialize();
		window.location.href="${ctx}/expresstracking.do?"+pars; */
	}
	
	 function reviewedStatus() {
			 var pars = $("#checkStatusForm").serialize();
			$.ajax({
				type: "POST",
				url: "${ctx}/expresstracking.do?method=updateBacthSiteSetStatus",
				data: pars,
				success: function(result) {
					if(result == 1){
						alert("审核成功！");
						window.location.href="${ctx}/expresstracking.do";
					}else{
						alert("审核失败!");
						window.location.href="${ctx}/expresstracking.do";
					}
				}
			});
	}
  </script>