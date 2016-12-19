<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
	<script type="text/javascript">
		function processSearch(){
			var userSearchForm = $("#userSearchForm");
			userSearchForm.submit();
		}
		function processEdit(id){
			location.href = "user.do?method=updateInit&id=" + id;
		}
		function processReturn(){
			location.href = "refund.handwork.do";
		}
		//id的全选或全部取消
		function selectAll(obj) {
			if($("#checkAll").attr("checked")){
				$("input[name='choose']").each(function(){
					this.checked = true;
				});
			} else {
				$("input[name='choose']").each(function() {
					this.checked = false;
				});
			}
		}
	</script>
</head>

<body>
	<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
		<tr>
			<td height="18">
				<div align="center">
					<font class="titletext">手工生成批次成,请记住批次号</font>
				</div>
			</td>
		</tr>
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
	</table>
		
	<table id="successTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		<thead> 
			<tr> 
				<th>批次号</th>     
				<th>批次名称</th>     
				<th>总比数</th>     
				<th>总金额</th>
				<th>产生时间</th>
			</tr> 
		</thead> 
		<tbody>
			<tr>
				<td>${batchNum}</td>     
				<td>${batchName}</td>     
				<td>${totalCount}</td> 
				<td><fmt:formatNumber value="${totalAmount /10*0.01}" pattern="#,##0.00" /></td>
				<td>${updatetime}</td>
			</tr>
		</tbody> 
	</table>
	
	<table>
		<tr>
			<td>
				<input type="button" name="but" value="返回查询" onclick="processReturn();">
			</td>
		</tr>
	</table>
	
</body>

