<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
	<script type="text/javascript">
	
		$(function(){
			$("input[name='choose']").click(function(){
				$("input[name='choose']").each(function(){
					if(this.checked == true){
						$("#butConfirm").attr("disabled","");
						$("#butBackspacing").attr("disabled","");
						return false;
					}else{
						$("#butConfirm").attr("disabled","disabled");
						$("#butBackspacing").attr("disabled","disabled");
					}
				});
			});

			$("#butConfirm").click(function(){
				var ids = '';
				var size = $("input[name='choose']:checked").size();
				$("input[name='choose']:checked").each(function(index){
					if(index != (size - 1)){
						ids += this.value + ",";
					}else{
						ids += this.value;
					}
				});
				if(ids != ''){
					$('#infoLoadingDiv').dialog('open');
					var pars = "ids=" + ids;
					$.ajax({
						type: "POST",
						url: "${ctx}/refund.processresult.do?method=processresultConfirm",
						data: pars,
						success: function(result) {
							$('#infoLoadingDiv').dialog('close');
							//$("p").show("slow");
							//$("p").html(result);
							$('#resultListDiv').html(result);
							//checkRefundBatchQuery();
						}
					});
				}else{
					alert("您没有选中任何记录!");
				}
			});
			
			$("#butBackspacing").click(function(){
				var ids = '';
				var size = $("input[name='choose']:checked").size();
				$("input[name='choose']:checked").each(function(index){
					if(index != (size - 1)){
						ids += this.value + ",";
					}else{
						ids += this.value;
					}
				});
				if(ids != ''){
					$('#infoLoadingDiv').dialog('open');
					var pars = "ids=" + ids;
					$.ajax({
						type: "POST",
						url: "${ctx}/refund.processresult.do?method=processresultBackspacing",
						data: pars,
						success: function(result) {
							$('#infoLoadingDiv').dialog('close');
							//$("p").show("slow");
							//$("p").html(result);
							//checkRefundBatchQuery();
							$('#resultListDiv').html(result);
						}
					});
				}else{
					alert("您没有选中任何记录!");
				}
			});
		});
		
		//id的全选或全部取消
		function selectAll(obj) {
			if($("#checkAll").attr("checked")){
				$("input[name='choose']").each(function(){
					this.checked = true;
				});
				$("#butConfirm").attr("disabled","");
				$("#butBackspacing").attr("disabled","");
			} else {
				$("input[name='choose']").each(function() {
					this.checked = false;
				});
				$("#butConfirm").attr("disabled","disabled");
				$("#butBackspacing").attr("disabled","disabled");
			}
		}
	</script>
</head>
<p style="display: none;color: red;"></p>
<body>
	<table id="batchTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		<thead> 
			<tr> 
				<th><input type="checkbox" name="checkAll" id="checkAll" onclick="selectAll(this);" />全选/反选</th>   
				<th>充退流水号</th>     
				<th>充退交易号</th>   
				<th>银行名称</th>     
				<th>充退金额（元）</th>     
				<th>会员姓名</th>
				<th>交易备注</th>
				<th>状态</th>
				<th>失败原因</th>
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
				<td>
					<c:choose>
						<c:when test="${102 eq batch.status}">
							<input type="checkbox" value="${batch.detailKy}" name="choose" id="choose"/>
						</c:when>
						<c:when test="${103 eq batch.status}">
							<input type="checkbox" value="${batch.detailKy}" name="choose" id="choose"/>
						</c:when>
						<c:otherwise>
							
						</c:otherwise>
					</c:choose>
				</td>
				<td>${batch.orderKy}</td>     
				<td>${batch.detailKy}</td>     
				<td>
				<li:select name="bankCode" itemList="${bankList}" selected="${batch.rechargeBank}" showStyle="desc" /></td>     
				<td><fmt:formatNumber value="${batch.applyAmount}" pattern="#,##0.00"  /></td> 
				<td>${batch.memberName}</td>
				<td>${batch.applyRemark}</td>
				<td>
					<c:choose>
						<c:when test="${101 eq batch.status}">
							充退处理中
						</c:when>
						<c:when test="${102 eq batch.status}">
							人工审核成功
						</c:when>
						<c:when test="${103 eq batch.status}">
							人工审核失败
						</c:when>
						<c:when test="${111 eq batch.status}">
							充退成功
						</c:when>
						<c:when test="${112 eq batch.status}">
							充退失败
						</c:when>
						<c:otherwise>
							${batch.status}
						</c:otherwise>
					</c:choose>
				</td>
				<td>${batch.cause}</td>
			</tr>
			</c:forEach>
		</tbody> 
	</table>
	<li:pagination methodName="checkRefundBatchQuery" pageBean="${page}" sytleName="black2"/>
	<table width="100%">
		<tr>
			<td align="center">
				<input type="button" name="butConfirm" id="butConfirm" disabled="disabled" value="确认" class="button2"/>
				<input type="button" name="butBackspacing" id="butBackspacing" disabled="disabled" value="退回" class="button2"/>
			</td>
			<td>
				
			</td>
		</tr>
	</table>
</body>

