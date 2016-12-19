<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
	<script type="text/javascript">
		//审核通过
		function processPass(){
			document.auditFrom.action = "fo_rc_foreconcileauditcontroller.do?method=passSubmit";
			document.auditFrom.submit();
		}
		//审核驳回
		function processReject(){
			document.auditFrom.action = "fo_rc_foreconcileauditcontroller.do?method=rejectSubmit";
			document.auditFrom.submit();
		}
		//返回
		function processBack(){
			location.href = "fo_rc_foreconcilelogcontroller.do?method=querylogInit";
		}
	</script>
</head>

<body>
	<form method="post" id="auditFrom" name="auditFrom">
	<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
		<tr>
			<td height="18">
				<div align="center">
					<font class="titletext">日志详细</font>
				</div>
			</td>
		</tr>
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
	</table>
	<br>
	
	<table class="border_all2" width="55%" border="0" cellspacing="0" cellpadding="3" align="center">
		<tr class="trbgcolor10">
			<td class="border_top_right4">银行名称:</td>
			<td class="border_top_right4">
				<input type="hidden" name="RESULT_ID" value="${rc.resultId}"/>
				<input type="hidden" name="APPLY_ID" value="${rc.applyId}"/>
			<li:codetable  selectedValue="${rc.withdrawBankId}" style="desc" attachOption="true" codeTableId="fundOutBankTable"></li:codetable>
			</td>
		</tr>
		<tr class="trbgcolor10">
			<td class="border_top_right4">出款业务:</td>
			<td class="border_top_right4">
				<li:codetable selectedValue="${rc.withdrawBusiType}" style="desc" attachOption="true" codeTableId="fundOutBusinessTable" />
			</td>
		</tr>
		<tr class="trbgcolor10">
			<td class="border_top_right4">对账状态:</td>
			<td class="border_top_right4"><li:code2name itemList="${busiFlagList}" selected="${rc.busiFlag}"/></td>
		</tr>
		<tr class="trbgcolor10">
			<td class="border_top_right4">交易号:</td>
			<td class="border_top_right4"><input type="hidden" name="TRADE_SEQ" value="${rc.transactionNumber}"/>${rc.transactionNumber}</td>
		</tr>
		<tr class="trbgcolor10">
			<td class="border_top_right4">交易金额:</td>
			<td class="border_top_right4"><fmt:formatNumber value="${rc.tradeAmount*0.001}" pattern="#,##0.00"  /></td>
		</tr>
		<tr class="trbgcolor10">
			<td class="border_top_right4">交易时间:</td>
			<td class="border_top_right4"><fmt:formatDate value="${rc.tradeTime}" type="both"/></td>
		</tr>
		<tr class="trbgcolor10">
			<td class="border_top_right4">银行处理日期:</td>
			<td class="border_top_right4"><fmt:formatDate value="${rc.bankProcessDate}" type="date"/></td>
		</tr>
		
	</table>
	
	 <table id="" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		<thead>
			<tr>
					<th >序号</th>
					<th >操作时间</th>
					<th >操作人</th>
					<th >操作行为</th>
					<th >操作理由</th>
			</tr>
	     </thead>
	     <tbody>	
	     <c:if test="${not empty reconcileFlows}">
	     		<c:forEach items="${reconcileFlows}" var="dto" varStatus="status">
					<tr >
						<td ><c:out value="${status.count}" /></td>
						<td ><fmt:formatDate value="${dto.processTime}" type="both"/></td>
						<td >${dto.opertor}</td>
						<td >
							<li:code2name itemList="${reviseStatusList}" selected="${dto.processStatus}"/>
						</td>
						<td >
							${dto.processRemarks}
						</td>
					</tr>
				</c:forEach>
			
     		</c:if>  
     		</tbody>
	   	  	</table>
	     <div align="center" >  
	        <input type="button" name="" value="返 回" onclick="processBack();" class="button2"/>
	    </div>
	</form>
</body>

