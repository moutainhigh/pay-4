<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
	<script type="text/javascript">
		//返回结果列表页内排序
		$(document).ready(function(){
			 $("#applyTable").tablesorter({
				 headers: {
				 	0: {sorter: false},
				 	8: {sorter: false}
				 }});      
		});
		//申请调账
		function processApply(id,reviseStatus){
			location.href = "fo_rc_foreconcileapplycontroller.do?method=manualReconciliationApply&id=" + id +"&reviseStatus="+reviseStatus;
		}
		//查看
		function processDetailed(id){
			location.href = "fo_rc_foreconcileapplycontroller.do?method=applyDetail&id=" + id;
		}
		//id的全选或全部取消
		function selectAll(obj) {
			if($("#checkAll").attr("checked")){
				$("input[name='choose']").each(function(){
					this.checked = true;
				});
				$("#batchSubmit").attr("disabled","");
			} else {
				$("input[name='choose']").each(function() {
					this.checked = false;
				});
				$("#batchSubmit").attr("disabled","disabled");
			}
		}
		//批量提交
		$(function(){
			$("input[name='choose']").click(function(){
				$("input[name='choose']").each(function(){
					if(this.checked == true){
						$("#batchSubmit").attr("disabled","");
						return false;
					}else{
						$("#batchSubmit").attr("disabled","disabled");
					}
				});
			});
			
			$("#batchSubmit").click(function(){
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
						url: "${ctx}/fo_rc_foreconcileapplycontroller.do?method=batchApplySubmit",
						data: pars,
						success: function(result) {
							$("p").show("slow");
							$("p").html(result);
							applyQuery();
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
	<table id="applyTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		<thead> 
			<tr> 
				<th><input type="checkbox" name="checkAll" id="checkAll" onclick="selectAll(this);" /></th>       
				<th>银行名称</th>
				<th>出款业务</th> 
				<th>交易流水号</th> 
				<th>交易金额</th> 
				<th>交易日期</th> 
				<th>对账状态</th> 
				<th>调账状态</th> 
				<th colspan="2">操作</th>  
			</tr> 
		</thead> 
		<tbody>
			<c:forEach items="${page.result}" var="rc"> 
			<tr> 
				<td width="10px"><c:if test="${rc.reviseStatus == 0 || rc.reviseStatus == 3}"><input type="checkbox" value="${rc.resultId}" name="choose" id="choose"/></c:if></td>   
				<td>
				<li:codetable selectedValue="${rc.withdrawBankId}" style="desc" attachOption="true" codeTableId="fundOutBankTable" />
				</td>
				<td>
				<li:codetable selectedValue="${rc.withdrawBusiType}" style="desc" attachOption="true" codeTableId="fundOutBusinessTable"></li:codetable>
				</td>     
				<td>${rc.transactionNumber}</td>     
				<td><fmt:formatNumber value="${rc.tradeAmount*0.001}" pattern="#,##0.00"  /></td> 
				<td><fmt:formatDate value="${rc.tradeTime}" type="date"/></td>
				<td><li:code2name itemList="${busiFlagList}" selected="${rc.busiFlag}"/></td>
				<td><li:code2name itemList="${reviseStatusList}" selected="${rc.reviseStatus}"/></td>
				<td width="50px"><c:if test="${rc.reviseStatus == 0 || rc.reviseStatus == 3}"><a href="javascript:processApply('${rc.resultId}','${rc.reviseStatus}')">调账申请</a></c:if></td> 
				<td width="50px"><a href="javascript:processDetailed('${rc.resultId}')">查看</a></td>  
			</tr>
			</c:forEach>
		</tbody>
	</table>
	<li:pagination methodName="applyQuery" pageBean="${page}" sytleName="black2"/>
	<table>
		<tr>
			<td>调账理由</td>
			<td><textArea id="reason" name="reason" cols="40" rows="5" maxlength="200"></textArea></td>
		</tr>
		<tr>
			<td colspan="2" align="center">
				<input type="button" name="batchSubmit" id="batchSubmit" disabled="disabled" value="批量提交" class="button4"/>
			</td>
		</tr>
	</table>
</body>

