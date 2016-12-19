<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" ></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">工作流创建与删除</font></div>
		</td>
	</tr>
	<tr>
		<td height="1"></td>
	</tr>
</table>
<form id="form" method="post">
	<table width="75%" border="0" cellspacing="0" cellpadding="0"
		align="center" height="21" style="">
		<tr>
			<td height="10" >
				<input id="createaddress"  name="createaddress"  value="" type="text">
			</td>
			<td height="10" >
				<a href="javascript:processcreate()">创建</a></td>
				</td>
			<td height="10" >
			         执行 结果为：${createresult}
			</td>
		</tr>
		<tr>
			<td height="10" >
				<input id="dropaddress"  name="dropaddress"  value="" type="text">
			</td>
			<td height="10" >
				<a href="javascript:processdelete()">删除</a>
			</td>
			<td height="10" >
			         执行 结果为：${dropresult}
			</td>
		</tr>
	</table>
</form>
<script type="text/javascript">
   function processcreate(){
	   location.href = "refund.workflow.do?method=createWF";
   }
   function processdelete(){
	   var pid=document.getElementById('dropaddress').value;
	   location.href = "refund.workflow.do?method=dropWF&dropid="+pid;
   }
</script>
