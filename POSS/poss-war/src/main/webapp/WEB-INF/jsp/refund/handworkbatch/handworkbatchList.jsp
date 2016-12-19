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
		function processAdd(){
			location.href = "userAdd.do";
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


		function processHandwork (){
			if(0 == $(":checkbox[name=choose]:checked").size()){
				alert("请您至少一条记录进行批次文件生成!");
				return false;
			}else{
					var url = "${ctx}/refund.handwork.do?method=handwork";
					document.handWorkFrom.action=url;
					document.handWorkFrom.submit();
				}
		}
		
	</script>
</head>

<body>
	<form name="handWorkFrom" id="checkWorkorderKy" action="${ctx}/refund.handwork.do?method=handwork"  method="post">
	<table id="batchTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		<thead> 
			<tr> 
				<th><input type="checkbox" name="checkAll" id="checkAll" onclick="selectAll(this);" />全选/反选</th>   
				<th>交易流水号</th>     
				<th>充退金额（元）</th>     
				<th>会员姓名</th>
				<th>交易备注</th>
				<th>状态</th>
			</tr> 
		</thead> 
		<tbody>
			<c:forEach items="${page.result}" var="batch" varStatus = "personalStatus">
		<c:choose>
	       <c:when test="${personalStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>	
				<td><input type="checkbox" value="${batch.workorderKy}" name="choose" id="choose"/></td> 
				<td>${batch.orderKy}</td>     
				<td><fmt:formatNumber value="${batch.applyAmountLong / 1000}" pattern="#,##0.00"  /></td> 
				<td>${batch.memberName}</td>
				<td>${batch.applyRemark}</td>
				<td>${batch.statusDesc}</td>
			</tr>
			</c:forEach>
		</tbody> 
	</table>
	<li:pagination methodName="businessQuery" pageBean="${page}" sytleName="black2"/>
	
	<table width="100%">
		<tr>
			<td align="center">
				<input type="button" name="but" class="button2" value="生成批次" onclick="processHandwork();">
				<!--<input type="submit" name="but" value="生成批次">-->
			</td>
		</tr>
	</table>
	</form>
</body>



