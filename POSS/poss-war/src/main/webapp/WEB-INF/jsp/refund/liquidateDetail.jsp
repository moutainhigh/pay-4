<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" src="${ctx}/js/common.js"></script>
<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">充退复核详情</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form action="refund.liquidate.do?method=handerRefundLiquidateSingle" method="post" name="auditForm">
<input type="hidden" name="refundStatus" id="refundStatus"/>
<input type="hidden" name="workOrderKy" value="${mDto.workOrderKy}"/>
<input type="hidden" name="workflowKy"  value="${mDto.workflowKy}" />
<input type="hidden" name="handleType" id="handleType" value=""/>
<input type="hidden" name="requestUrl" value="refund.liquidate.do?method=entryRefundLiquidatePage"/>
	<div align="left" style="width: 80%">
		<font style="text-align: left;" color="blue"><b>会员信息</b></font>
	</div>
	<hr width="80%"/>
	<table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >会员号：</td>
	      <td class="border_top_right4">
	      	${mDto.memberCode}
	      </td>
	      <td align="right" class="border_top_right4" >会员姓名：</td>
	      <td class="border_top_right4">
	      	${mDto.memberName}
	      </td>
	    </tr>
	  	<tr class="trForContent1">
	      <td align="right" class="border_top_right4" >会员类型：</td>
	      <td class="border_top_right4">
	      	${mDto.memberType}
	      </td>
	      <td align="right" class="border_top_right4" >账户号：</td>
	      <td class="border_top_right4">
	      	${mDto.memberAcc}
	      </td>
	    </tr>
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >账户类型：</td>
	      <td class="border_top_right4">
	      	${mDto.memberAccType}
	      </td>
	      <td align="right" class="border_top_right4" >充退申请理由：</td>
	      <td class="border_top_right4">
	      	${mDto.applyReason}
	      </td>
	    </tr>
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >充退时间：</td>
	      <td class="border_top_right4">
	      	<fmt:formatDate value="${mDto.applyTime}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>
	      </td>
	      <td align="right" class="border_top_right4" >充退金额(元)：</td>
	      <td class="border_top_right4">
	      	<fmt:formatNumber value="${mDto.applyAmount}" pattern="#,##0.00"/>&nbsp;
	      </td>
	    </tr>
	    <tr class="trForContent1">
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
	<div align="left" style="width: 80%">
		<font style="text-align: left;" color="blue"><b>充值记录详细信息</b></font>
	</div> 
	<hr width="80%"/>  
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
	  		<tr>
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
	<div align="left" style="width: 80%">
		<font style="text-align: left;" color="blue"><b>风控操作记录</b></font>		 
		<hr width="80%"/>
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
				  		<tr>
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
		清算备注：<textarea rows="5" cols="60" name="refundRemark" 
							id="refundRemark" title="最多只能输入50个汉字或100个字符"></textarea>
	</div>
	<br/>
	<p/>
	<br/>
	<center>
      <c:choose>
		<c:when test="${1 eq mDto.batchStatus}">
			
		</c:when>
		<c:when test="${'4' eq mDto.workOrderStatus && 1 ne mDto.batchStatus}">
			<input type="button" onclick="handleRefundLiquidate(0,this);" class="button4" name="btnUntread" value="退 回"/>
		</c:when>
		<c:otherwise>			
			<input type="button" onclick="handleRefundLiquidate(0,this);" class="button4" name="btnUntread" value="退 回"/>
			<input type="button" onclick="handleRefundLiquidate(6,this);" class="button4" name="btnReject" value="拒 绝"/>			
		</c:otherwise>
	</c:choose>
      <input type="button" onclick="rollbackLiquidateQuery();" class="button4" name="btnRollback" value="返 回"/>
    </center>
</form>
 <script type="text/javascript">

 function handleRefundLiquidate(val,obj){
	 	var remark = $("#refundRemark").val();
		if("" != remark){
			if(!checkMaxLength("refundRemark",100)){
				return false;
			}
		}
		
		$("#refundStatus").val(val);
		if(6 == val){
			$("#handleType").val("flowEnd");
		}else{
			$("#handleType").val("rollback");
		}
		var truthBeTold = window.confirm("您确定【" + obj.value + "】提交吗?");
    	if (truthBeTold) {
    		document.auditForm.action="refund.liquidate.do?method=handerRefundLiquidateSingle";
    		document.auditForm.submit();
    	}else{
        	return;
    	}
	}

	function rollbackLiquidateQuery(){
		var url = 'refund.liquidate.do?method=entryRefundLiquidatePage';
		document.auditForm.action = url;
		document.auditForm.submit();
		//window.location.href = "refund.liquidate.do?method=entryRefundLiquidatePage";
	}

</script>
 