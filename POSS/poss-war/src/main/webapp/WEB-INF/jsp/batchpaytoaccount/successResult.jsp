<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<head>
		<title>成功页面</title>
	</head>
	
	<body>
		<div style="text-align: left;position: absolute;left:50px;top:50px;width: 60%;">
			批量付款到系统账户
			<span style="float: right;">
				1.上传批量文件-->2.确认付款信息--><font color="red">3.等待付款到帐</font>
			</span>
			<hr color="black" noshade="noshade" width="0">
			<span style="border: 2px solid orange;padding: 4px;">
				<h3>付款申请提交成功！</h3>
				系统正在处理您的申请。<br><br>
				所有付款处理完成后，系统会将结果明细发送至您的电子邮箱，请注意查收！
			</span>
			<br><br>
			
			批量付款信息
			<span style="float: right;width: 70%;">业务编号：${importFile.batchNum}</span>
			<br><br>
			<div style="border: gray 1px solid;width:90%">
				<table cellpadding="5">
					<tr>
						<td style="width: 50%">业务参考号：${importFile.batchNum}</td>
						<td style="width: 50%">账户类型：${importFile.payAmountName}</td>
					</tr>
					<tr>
						<td>申请支付笔数：${importFile.totalCount}笔</td>
						<td>
							错误笔数：${importFile.errorCount}笔
							<a href="${ctx}/batchPayToAccountController.do?method=errorDetail">查看详情</a>
						</td>
					</tr>
					<tr>
						<td colspan="2">
							核对正确笔数：<font color="red">${importFile.orderCount}</font>笔
						</td>
					</tr>
				</table>
			</div>
			<br><br>
			您可能需要：
			<br><br>
			<a href="${ctx}/toUploadFile.do">继续批量付款</a> | 
			<a href="${ctx}/batchPayToAccountController.do?method=viewRecord">查看批量付款记录</a> | 
			<a href="">返回企业账户首页</a>
		</div>
	</body>
</html>