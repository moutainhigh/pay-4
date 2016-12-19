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
		<div align="center"><font class="titletext">充退审核详情</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
<h2 class="panel_title">充退审核详情</h2>
<form action="refund.reaudit.do?method=handerRefundReAuditSingle" method="post" name="auditForm">
<input type="hidden" name="refundStatus" id="refundStatus"/>
<input type="hidden" name="workOrderKy" value="${mDto.workOrderKy}"/>
<input type="hidden" name="workflowKy"  value="${mDto.workflowKy}" />
<input type="hidden" name="wstatus" value="${mDto.workOrderStatus}"/>
<input type="hidden" name="handleType" id="handleType"/>
<input type="hidden" name="requestUrl" value="refund.reaudit.do?method=entryRefundReAuditPage"/>
	
	<table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">
		<thead><tr class="trForContent1"><th colspan="7"><font style="text-align: center;" color="blue"><b>会员信息</b></font></th></tr></thead>
	    <tr class="trForContent2">
	      <td align="right" class="border_top_right4" >会员号：</td>
	      <td class="border_top_right4">
	      	${mDto.memberCode}
	      </td>
	      <td align="right" class="border_top_right4" >会员姓名：</td>
	      <td class="border_top_right4">
	      	${mDto.memberName}
	      </td>
	    </tr>
	  	<tr class="trForContent2">
	      <td align="right" class="border_top_right4" >会员类型：</td>
	      <td class="border_top_right4">
	      	${mDto.memberType}
	      </td>
	      <td align="right" class="border_top_right4" >账户号：</td>
	      <td class="border_top_right4">
	      	${mDto.memberAcc}
	      </td>
	    </tr>
	    <tr class="trForContent2">
	      <td align="right" class="border_top_right4" >账户类型：</td>
	      <td class="border_top_right4">
	      	${mDto.memberAccType}
	      </td>
	      <td align="right" class="border_top_right4" >充退申请理由：</td>
	      <td class="border_top_right4">
	      	${mDto.applyReason}
	      </td>
	    </tr>
	    <tr class="trForContent2">
	      <td align="right" class="border_top_right4" >充退时间：</td>
	      <td class="border_top_right4">
	      	<fmt:formatDate value="${mDto.applyTime}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>
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
	      <td align="right" class="border_top_right4" >交易IP地址：</td>
	      <td class="border_top_right4">
	      	${mDto.operatorIp}&nbsp;
	      </td>
	    </tr> 
	</table>
	<br/>
	<table class="border_all2" width="100%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
		<thead><tr class="trForContent1"><th colspan="7"><font style="color:blue;"><b>充值记录详细信息</b></font></th></tr></thead>
		</table>
	<table id="detailTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
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
				<td>
					<li:select name="bankCode" itemList="${bankList}" selected="${dDto.rechargeBank}" showStyle="desc" />
				</td>
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
		<td align="right">复核备注：</td>
		<td><textarea rows="5" cols="60" name="refundRemark" id="refundRemark" title="最多只能输入50个汉字或100个字符"></textarea></td>
		</tr>
		</table>
	</div>

	 <center>
      <input type="button" onclick="haderRefundReAudit(4,this);" class="button2" name="btnSuccess" value="同意初审"/>
      <input type="button" onclick="haderRefundReAudit(5,this);" class="button2" name="btnReject" value="驳回初审"/>
      <input type="button" onclick="rollbackReAuditQuery();" class="button2" name="btnRollback" value="返 回"/>
    </center>
</form>
 <script type="text/javascript">

 	function haderRefundReAudit(val,obj){
 		var remark = $("#refundRemark").val();
		if("" != remark){
			if(!checkMaxLength("refundRemark",100)){
				return false;
			}
		}
		
		var truthBeTold = window.confirm("您确定【" + obj.value + "】提交吗?");
    	if (truthBeTold) {
    		$("#refundStatus").val(val);
    		if(4 == val){
    			$("#handleType").val("agree");
    		}else{
    			$("#handleType").val("rollback");
    		}
    		document.auditForm.action="refund.reaudit.do?method=handerRefundReAuditSingle";
    		document.auditForm.submit();
    	}else{
        	return;
    	}
	}

	function rollbackReAuditQuery(){
		var url = 'refund.reaudit.do?method=entryRefundReAuditPage';
		document.auditForm.action = url;
		document.auditForm.submit();
		//window.location.href = "refund.reaudit.do?method=entryRefundReAuditPage";
	}
</script>
 