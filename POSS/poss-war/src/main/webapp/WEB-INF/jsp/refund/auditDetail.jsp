<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" src="${ctx}/js/common.js"></script>
<!-- <table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">充退详情</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
<h2 class="panel_title">充退详情</h2>
<form action="refund.manage.do?method=handerRefundAuditSingle" method="post" name="auditForm">
<input type="hidden" name="refundStatus" id="refundStatus"/>
<input type="hidden" name="workOrderKy" value="${mDto.workOrderKy}"/>
<input type="hidden" name="workflowKy"  value="${mDto.workflowKy}" />
<input type="hidden" name="requestUrl" value="refund.manage.do?method=entryRefundAuditPage"/>
<input type="hidden" name="tempStatus" value="${mDto.workOrderStatus}"/>
<input type="hidden" name="handleType" id="handleType" value=""/>
	<div align="left" style="width: 80%">
		
	</div>
	
	<table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
		<thead><tr class="trForContent1"><th colspan="4"><font style="text-align: left;" color="blue"><b>会员信息</b></font></th></tr></thead>
	    <tr class="trForContent2">
	      <td align="right" class="border_top_right4" >会员号：</td>
	      <td class="border_top_right4">
	      	${mDto.memberCode}&nbsp;
	      </td>
	      <td align="right" class="border_top_right4" >会员姓名：</td>
	      <td class="border_top_right4">
	      	${mDto.memberName}&nbsp;
	      </td>
	    </tr>
	  	<tr class="trForContent2">
	      <td align="right" class="border_top_right4" >会员类型：</td>
	      <td class="border_top_right4">
	      	${mDto.memberType}&nbsp;
	      </td>
	      <td align="right" class="border_top_right4" >账户号：</td>
	      <td class="border_top_right4">
	      	${mDto.memberAcc}&nbsp;
	      </td>
	    </tr>
	    <tr class="trForContent2">
	      <td align="right" class="border_top_right4" >账户类型：</td>
	      <td class="border_top_right4">
	      	${mDto.memberAccType}&nbsp;
	      </td>
	    <td align="right" class="border_top_right4" >交易IP地址：</td>
	      <td class="border_top_right4">
	      	${mDto.operatorIp}&nbsp;
	      </td>
	    </tr>
	    <tr class="trForContent2">
	      <td align="right" class="border_top_right4" >充退时间：</td>
	      <td class="border_top_right4">
	      	<fmt:formatDate value="${mDto.applyTime}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>&nbsp;
	      </td>
	      <td align="right" class="border_top_right4" >充退金额(元)：</td>
	      <td class="border_top_right4">
	      	<fmt:formatNumber value="${mDto.applyAmount}" pattern="#,##0.00"/>&nbsp;
	      </td>
	    </tr>
	    <tr class="trForContent2">
	      <td align="right" class="border_top_right4" >会员等级：</td>
	      <td class="border_top_right4">
	      	${mDto.memberLevel}&nbsp;
	      </td>
	      <td align="right" class="border_top_right4" colspan="2" >&nbsp;</td>
	    </tr> 
	</table>
	<br/>
	<!-- <div align="left" style="width: 80%">
		<font style="text-align: left;" color="blue"><b>充值记录详细信息</b></font>
	</div>  -->
	<table class="border_all2" width="100%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
		<thead><tr class="trForContent1"><th colspan="7"><font style="color:blue;"><b>充值记录详细信息</b></font></th></tr></thead>
		</table>
	<table id="detailTable" width="80%" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	  <thead> 
		<tr>
			<th >充值流水号</th>
			<th >充值银行流水号</th>
			<th>银行名称</th>
			<th >充退金额（元）</th>
			<th>充值时间</th>
			<th>状态</th>
			<th>充退批注</th>
		</tr>
	  </thead>
	  <tbody> 
	  	<c:forEach items="${mDto.listDetails}" var="dDto" varStatus="status">
	  		<c:choose>
	       <c:when test="${status.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
				<td >${dDto.rechargeOrderSeq}</td>
				<td >${dDto.rechargeBankOrder}</td>
				<td><li:select name="bankCode" itemList="${bankList}" selected="${dDto.rechargeBank}" showStyle="desc" /></td>
				<td ><fmt:formatNumber value="${dDto.applyAmount}" pattern="#,##0.00"/>&nbsp;</td>
				<td ><fmt:formatDate value="${dDto.rechargeTime}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/></td>
				<td >
					<c:choose>
						<c:when test="${'101' eq dDto.status}">
							充退处理中
						</c:when>
						<c:when test="${'111' eq dDto.status}">
							充退成功
						</c:when>
						<c:when test="${'112' eq dDto.status}">
							充退失败
						</c:when>
						<c:otherwise>
							${dDto.status}
						</c:otherwise>
					</c:choose>
				</td>
				<td>${dDto.applyRemark}</td>
			</tr>
	  	</c:forEach>
	  </tbody>
	</table>
	<br/>
	<!-- <div align="left" style="width: 80%">
		<font style="text-align: left;" color="blue"><b>风控操作记录</b></font>
		 
		</div> -->
		<table class="border_all2" width="100%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
		<thead><tr class="trForContent1"><th colspan="7"><font style="text-align: center;" color="blue"><b>风控操作记录</b></font></th></tr></thead>
		</table>
		<c:if test="${not empty operattionLogs}">
			<table id="logTable" width="80%" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
			  <thead> 
				<tr>
					<th >操作员</th>
					<th >操作状态</th>
					<th>操作时间</th>
					<th >操作备注</th>
				</tr>
			  </thead>
			  <tbody> 
			  	<c:forEach items="${operattionLogs}" var="logInfo" varStatus="status">
				  		<c:choose>
	       <c:when test="${status.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
							<td >${logInfo.operator}</td>
							<td >
								<c:choose>
									<c:when test="${'0' eq logInfo.state}">
										工单初始
									</c:when>
									<c:when test="${'1' eq logInfo.state}">
										审核通过
									</c:when>
									<c:when test="${'2' eq logInfo.state}">
										审核拒绝
									</c:when>
									<c:when test="${'3' eq logInfo.state}">
										审核滞留
									</c:when>
									<c:when test="${'4' eq logInfo.state}">
										复核通过
									</c:when>
									<c:when test="${'5' eq logInfo.state}">
										复核拒绝
									</c:when>
									<c:otherwise>
										清算拒绝
									</c:otherwise>
								</c:choose>			
							</td>
							<td><fmt:formatDate value="${logInfo.operationTime}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/></td>
							<td >${logInfo.remark}</td>
						</tr>
			  	</c:forEach>
			  </tbody>
			</table>
		</c:if>
		<table class="" align="center" border="0" cellpadding="0" cellspacing="1">
		<tr>
		<td align="right">审核备注：</td>
		<td><textarea rows="4" cols="45" name="auditRemark" id="auditRemark" ></textarea></td>
		</tr>
		
	</table>
		
	</div>
	<br/>
	<p/>
	<center>
      <input type="button" name="btnSuccess" onclick="haderRefundAudit(1,this);" class="button2" value="审核通过"/>
      <input type="button" name="btnReject" onclick="haderRefundAudit(2,this);" class="button2" value="审核拒绝"/>
      <input type="button" name="btnResort" onclick="haderRefundAudit(3,this);" class="button2"
      	 value="审核滞留" <c:if test="${'3' eq mDto.workOrderStatus}">disabled</c:if>/>
      <input type="button" name="btnRollback" onclick="rollbackAuditQuery();" class="button2" value="返  回"/>
    </center>
</form>
 <script type="text/javascript">

 	function haderRefundAudit(val,obj){
		var remark = $("#refundRemark").val();
		if("" != remark){
			if(!checkMaxLength("refundRemark",100)){
				return false;
			}
		}
		var truthBeTold = window.confirm("您是否确定【" + obj.value + "】提交?");
    	if (truthBeTold) {
    		$("#refundStatus").val(val);
    		if(3 == val){
				$("#handleType").val("to_tempTask");
    		}else{
				$("#handleType").val("to_reAssignTask");
    		}
    		document.auditForm.submit();
    	}else{
        	return false;
    	}
	}

	function rollbackAuditQuery(){
		var url = 'refund.manage.do?method=entryRefundAuditPage';
		document.auditForm.action = url;
		document.auditForm.submit();
	}
</script>
 