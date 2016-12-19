<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>批量文件验证结果</title>
		<script type="text/javascript">
			$(document).ready(function(){
				$("#ma").click(function(){
					if($(this).html()=="查看详细"){
						$("#errorList").css("display","");
						$(this).html("收起");
					} else {
						$("#errorList").css("display","none");
						$(this).html("查看详细");
					}
				});

				$("#submitBtn").click(function(){
					alert("dd");
					$("#frm").submit();
				});
			});
		</script>
	</head>
	
	<body>
		<div style="text-align: left;position: absolute;left:50px;top:50px;width: 60%;">
			<h3>批量付款到系统账户</h3>
			
			<hr color="gray" noshade="noshade">
			<span style="float: right;">
				1.上传批量文件--><font color="red">2.确认付款信息</font>-->3.等待付款到帐
			</span><br>
			<hr color="gray" noshade="noshade">
			<div style="border: 2px solid orange;padding: 4px;">
				请确定您上传的付款信息，如有核对错误的情况，系统建议您： <br></br>
				点击上一步，修改付款文件后重新上传<br></br>
				点击确认付款，只对核对正确的支付记录进行付款
			</div>
			<br><br>
			<table cellpadding="5" style="width: 90%;">
				<tr>
					<td style="width: 50%">业务参考号：${importFile.batchNum}</td>
					<td style="width: 50%">账户类型：${importFile.payAmountName}</td>
				</tr>
				<tr>
					<td>申请支付笔数：${importFile.totalCount}笔</td>
					<td>核对正确笔数：${importFile.orderCount}笔</td>
				</tr>
			</table><br>
			 核对错误笔数：${importFile.errorCount}笔 &nbsp;&nbsp;&nbsp;     
			<span id="ma" style="color: blue; cursor: pointer;text-decoration: underline">查看详细</span><br>
			<span style="float: right;"><a href="${ctx}/downloadFileController.do?method=downloadImportRecordList">导出EXCEL</a></span>
			<div id="errorList" style="display: none;">
				<table border="1" cellpadding="4" cellspacing="0" style="width:100%;">
					<tr>
						<th>姓名</th>
						<th>收款账户</th>
						<th>金额</th>
						<th>备注</th>
						<th>无法付款原因</th>
					</tr>
					<c:forEach var="record" items="${resultList}">
						<c:if test="${fn:length(record.errorTips)>0}">
							<tr>
								<td>${record.payeeName}</td>
								<td>${record.payeeMemberName}</td>
								<td>${record.amountString}</td>
								<td>${record.remark}</td>
								<td>${record.errorTips}</td>
							</tr>
						</c:if>
					</c:forEach>
				</table>
			</div>
			<br>
			<span style="font-size:medium">实际支付金额：</span>
			<span style="color: red;font-size:medium"><fmt:formatNumber value="${importFile.payMoney}" pattern="0.00"/></span>
			<span style="font-size:medium">元</span>
			(申请支付金额 ：${importFile.applyMoney}元) 
			<br><br>
			<hr color="black" noshade="noshade">
			<br><br>
			如果您需要重新修改付款文件，点击
			<a href="toUploadFile.do">返回重新上传</a><br><br>
			<form action="${ctx}/batchPayToAccountController.do?method=create" id="frm" method="post">
				支付密码： <input type="password" name="payPassword" id="payPassword"></input>
				<a href="">忘记密码？</a><br><br>
				<input type="button" value="确认支付" id="submitBtn"></input>
			</form>
		</div>
	</body>
</html>