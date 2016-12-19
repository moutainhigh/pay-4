<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
	<script type="text/javascript">
	$(document).ready(function(){
		 $("#userTable").tablesorter({
			 headers: {
			 	5: {sorter: false}
			 }});
		 
		//$("#dialog").dialog();
	
		//var msg = "<p>您处理100笔,成功80笔,失败20笔</p><table border='1' align='center'><thead><tr> <th>交易流水号</th><th>处理失败原因</th></tr> </thead><tr><td>aaaaaaaaaa</td><td>bbbbbbbbbb</td></tr></table>";
	});	
	
	function repair(){
		var batchNum = $("#batchNum").val();
		var payerMember = $("#payerMember").val();

		$('#infoLoadingDiv').dialog('open');
		var pars = $("#orderForm").serialize() + "&batchNum=" + batchNum + "&payerMember=" + payerMember ;
		$.ajax({
			type: "POST",
			url: "${ctx}/consistent.masspay2acct.htm?method=repairMassPay2AcctSubOrder",
			data: pars,
			success: function(result) {
			 	var msg = eval('('+result+')');
				if(msg.isSuccess==true){
					$('#infoLoadingDiv').dialog('close');
					$.fo.alert(msg.reason);	
					//btnDisabledSetFalse();
					consMassPay2AcctQuery();
				}else{
					$('#infoLoadingDiv').dialog('close');
					//var msg = "<p>您处理100笔,成功80笔,失败20笔</p><table border='1' align='center'><thead><tr><th>交易流水号</th><th>处理失败原因</th></tr></thead><tr><td>aaaaaaaaaa</td><td>bbbbbbbbbb</td></tr></table>";
					$.fo.alert(msg.sequenceId+'补单失败');	
					//alert(msg.sequenceId+'审核提交失败');
					//btnDisabledSetFalse();
				}
			}
		});
		
	}
	
	

		
		
	</script>
</head>

<body>
	<!--
	<div id="dialog" title="Basic dialog">
		<p>您处理100笔,成功80笔,失败20笔</p>
		<table border="1" align="center">
			<thead> 
				<tr> 
					<th>交易流水号</th>    
					<th>处理失败原因</th>
				</tr> 
			</thead>
			<c:forEach items="${page.result}" var="orderDto"> 
				<tr>
					<td>aaaaaaaaaa</td>
					<td>bbbbbbbbbb</td>
				</tr>
			</c:forEach>
		</table>
	</div>
	-->
	<form id="orderForm">
	<input type="hidden" name="batchNum" id="batchNum" value="${batchNum}">
	<input type="hidden" name="payerMember" id="payerMember" value="${payerMember}">
	<table id="userTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		<thead> 
			<tr>
				<th>批次号</th>
				<th>付款人账号</th>
				<th>付款人名称</th> 
				<th>收款人账号</th> 
				<th>收款人名称</th>
				<th>金额</th>
				<th>批次文件号</th>
				<th>备注</th>
			</tr> 
		</thead> 
		<tbody>
			<c:forEach items="${page.result}" var="record"> 
			<tr>     
				<td>${record.businessBatchNo}</td>
				<td>${record.payerLoginName}</td>
				<td>${record.payerName}</td>
				<td>${record.payeeLoginName}</td>
				<td>${record.payeeName}</td>        
				<td>${record.amountStr}</td>
				<td>${record.fileKy}</td> 
				<td>${record.remark}</td> 
			</tr>
			</c:forEach>
		</tbody> 
	</table>
	</form>
	<li:pagination methodName="consMassPay2AcctQuery" pageBean="${page}" sytleName="black2"/>
	<table align="center">
		<tr>
			<td align="center">
					<input type="button" onclick="javascript:repair();" name="submitBtn" id="btn1" value="补  单" class="button4">
				</td>
		</tr>
	</table>
</body>

