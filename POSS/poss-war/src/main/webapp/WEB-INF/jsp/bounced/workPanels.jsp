<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>


<body>
<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
			<div align="center">
				<font class="titletext">代办事项</font>
			</div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<br>
<br><br><br>
<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
	</tr>
	<tr>
		<td height="18">
		<div align="center">
			 	<input style="height: 120px;width: 120px" type="button" value="待处理申诉" onclick="pending(1,2);">&nbsp;&nbsp;
			 <font	size="8px">	${pendingCount}</font>
			 	 <br><br>
				<input style="height : 120px;width: 120px" type="button" value="拒付处理复核" onclick="pendingReview(3,4);">&nbsp;&nbsp;
			<font	size="8px">	${pendingReviewCount}</font>
		</div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
</body>

<script type="text/javascript">
	function pending(obj,status){
		window.location.href="${ctx}/pending-bounced-register.do?method=bouncedQuery&status="+obj+","+status;		
	}
	function pendingReview(obj,status){
		window.location.href="${ctx}/pendingReview-bounced-register.do?method=bouncedQuery&status="+obj+","+status;
	}
</script>