<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
	<script type="text/javascript">
		//返回结果列表页内排序
		$(document).ready(function(){
			 $("#auditTable").tablesorter({
				 headers: {
				 	0: {sorter: false},
				 	7: {sorter: false}
				 }});      
		});
		//审核
		function processAudit(id){
			location.href = "fo_rc_foreconcileauditcontroller.do?method=audit&id=" + id;
		}
		//id的全选或全部取消
		function selectAll(obj) {
			if($("#checkAll").attr("checked")){
				$("input[name='choose']").each(function(){
					this.checked = true;
				});
				$("#passBtn").attr("disabled","");
				$("#rejectBtn").attr("disabled","");
			} else {
				$("input[name='choose']").each(function() {
					this.checked = false;
				});
				$("#passBtn").attr("disabled","disabled");
				$("#rejectBtn").attr("disabled","disabled");
			}
		}
		//批量提交
		$(function(){
			$("input[name='choose']").click(function(){
				$("input[name='choose']").each(function(){
					if(this.checked == true){
						$("#passBtn").attr("disabled","");
						$("#rejectBtn").attr("disabled","");
						return false;
					}else{
						$("#passBtn").attr("disabled","disabled");
						$("#rejectBtn").attr("disabled","disabled");
					}
				});
			});

			//批量审核通过
			$("#passBtn").click(function(){
				var ids = '';
				var reason = $("#reason").val();
				$("input[name='choose']").each(function(){
					if(this.checked){
						var id = this.value;
						ids += id + ":";
					}
				});
				if(ids != ''){
					var pars = "ids=" + ids + "&reason=" + reason;
					$.ajax({
						type: "POST",
						url: "${ctx}/fo_rc_foreconcileauditcontroller.do?method=batchPassSubmit",
						data: pars,
						success: function(result) {
							$("p").show("slow");
							$("p").html(result);
							auditQuery();
						}
					});
				}else{
					alert("您没有选中任何记录!");
				}
				
			});

			//批量审核驳回
			$("#rejectBtn").click(function(){
				var ids = '';
				var reason = $("#reason").val();
				$("input[name='choose']").each(function(){
					if(this.checked){
						var id = this.value;
						ids += id + ":";
					}
				});
				if(ids != ''){
					var pars = "ids=" + ids + "&reason=" + reason;
					$.ajax({
						type: "POST",
						url: "${ctx}/fo_rc_foreconcileauditcontroller.do?method=batchRejectSubmit",
						data: pars,
						success: function(result) {
							$("p").show("slow");
							$("p").html(result);
							auditQuery();
						}
					});
				}else{
					alert("您没有选中任何记录!");
				}
				
			});
		});
	</script>
</head>
<p style="display: none;color: red;"></p>
<body>
	<table id="auditTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		<thead> 
			<tr> 
				<th><input type="checkbox" name="checkAll" id="checkAll" onclick="selectAll(this);" /></th>    
				<th>银行名称</th>
				<th>出款业务</th> 
				<th>交易流水号</th> 
				<th>交易金额</th> 
				<th>交易日期</th> 
				<th>对账状态</th> 
				<th>操作</th>  
			</tr> 
		</thead> 
		<tbody>
			<c:forEach items="${page.result}" var="rc"> 
			<tr>
				<td width="10px">
					<input type="checkbox" value="${rc.resultId},${rc.applyId}" name="choose" id="choose"/>
				</td>   
				<td>
				<li:codetable selectedValue="${rc.withdrawBankId}" style="desc" attachOption="true" codeTableId="fundOutBankTable" />
				</td>     
				<td>
					<li:codetable selectedValue="${rc.withdrawBusiType}" style="desc" attachOption="true" codeTableId="fundOutBusinessTable" />
				</td>     
				<td>${rc.transactionNumber}</td>     
				<td><fmt:formatNumber value="${rc.tradeAmount*0.001}" pattern="#,##0.00"/></td>     
				<td><fmt:formatDate value="${rc.tradeTime}" type="date"/></td> 
				<td><li:code2name itemList="${busiFlagList}" selected="${rc.busiFlag}"/></td>
				<td><a href="javascript:processAudit('${rc.resultId}')">审核</a></td> 
			</tr>
			</c:forEach>
		</tbody> 
	</table>
	<li:pagination methodName="auditQuery" pageBean="${page}" sytleName="black2"/>
	<table>
		<tr>
			<td>审核理由：</td>
			<td><textArea id="reason" maxlength="200" name="reason" cols="40" rows="5"></textArea></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<input type="button" name="passBtn" id="passBtn" disabled="disabled" value="批量审核通过" class="button4"/>
				<input type="button" name="rejectBtn" id="rejectBtn" disabled="disabled" value="批量审核驳回" class="button4"/>
			</td>
		</tr>
	</table>
</body>

