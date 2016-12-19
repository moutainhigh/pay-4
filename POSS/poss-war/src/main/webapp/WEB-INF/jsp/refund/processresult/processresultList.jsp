<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
	<script type="text/javascript">
		$(function(){
			$("input[name='choose']").click(function(){
				$("input[name='choose']").each(function(){
					if(this.checked == true){
						if('102' != $(this).siblings(":hidden").val()){
							$("#butSuccess").attr("disabled","");
						}
						if('103' != $(this).siblings(":hidden").val()){
							$("#butFailure").attr("disabled","");
						}						
						return false;
					}else{
						$("#butSuccess").attr("disabled","disabled");
						$("#butFailure").attr("disabled","disabled");
					}
				});
			});

			$("#butSuccess").click(function(){
				var ids = '';
				$("input[name=choose]:checked").each(function(){
					ids += this.value + ":";
				});
				if(ids != ''){
					$('#infoLoadingDiv').dialog('open');
					var pars = "ids=" + ids;
					$.ajax({
						type: "POST",
						url: "${ctx}/refund.processresult.do?method=processresultSuccess",
						data: pars,
						success: function(result) {
							$('#infoLoadingDiv').dialog('close');
							$("p").show("slow");
							$("p").html(result);
							refundDataQuery();
						}
					});
				}else{
					alert("您没有选中任何记录!");
				}
				
			});
			
			$("#butFailure").click(function(){
				var ids = '';
				if($("input[id='batchCause']").attr("value") == ""){
					$("input[name='choose']:checked").each(function(){
							var id = this.value;
							var sl = "cause"+id;
							var va = $("#"+ sl +"").val();
							if("" == va){
								ids = "";
								return false;
							}else{
								ids += id + "=" + va + ":";
							}
					});
				}else{
					$("input[name='choose']:checked").each(function(){
							var id = this.value;
							var va = $("#batchCause").val();
							ids += id + "=" + va + ":";
					});
				}
				
				if(ids != ''){
					$('#infoLoadingDiv').dialog('open');
					var pars = "ids=" + ids;
					$.ajax({
						type: "POST",
						url: "${ctx}/refund.processresult.do?method=processresultFailure",
						data: pars,
						success: function(result) {
							$('#infoLoadingDiv').dialog('close');
							$("p").show("slow");
							$("p").html(result);
							refundDataQuery();
						}
					});
				}else{
					alert("您必须填写失败原因!");
				}
			});
			
		});
		
		//id的全选或全部取消
		function selectAll(obj) {
			if($("#checkAll").attr("checked")){
				$("input[name='choose']").each(function(){
					this.checked = true;
				});
				$("#butSuccess").attr("disabled","");
				$("#butFailure").attr("disabled","");
			} else {
				$("input[name='choose']").each(function() {
					this.checked = false;
				});
				$("#butSuccess").attr("disabled","disabled");
				$("#butFailure").attr("disabled","disabled");
			}
		}
	</script>
</head>
<p style="display: none;color: red;"></p>
<body>
	总笔数：<font color="red"><b>${COUNT}&nbsp;</b></font>笔 
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
	总金额：<font color="red"><b><fmt:formatNumber value="${SUMAMOUNT}" pattern="#,##0.00"/>&nbsp;</b></font>元
	<br/>
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
					<c:if test="${101 eq batch.status}">
						<input type="checkbox" value="${batch.detailKy}" name="choose" id="choose"/>
						<input type="hidden" name="refundStatus" value="${batch.status}"/>
					</c:if>
				</td> 
				<td>${batch.orderKy}</td>     
				<td>${batch.detailKy}</td>     
				<td><li:select name="bankCode" itemList="${bankList}" selected="${batch.rechargeBank}" showStyle="desc" /></td>     
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
				<td><input type="text" name="${batch.detailKy}" id="cause${batch.detailKy}" value=""/></td>
			</tr>
			</c:forEach>
		</tbody> 
	</table>
	<li:pagination methodName="refundDataQuery" pageBean="${page}" sytleName="black2"/>
	<table align="center">
		<tr>
			<td>
				批量失败原因：
			</td>
			<td>
				<input type="text" name="batchCause" id="batchCause" size="50">
			</td>
		</tr>
		<tr>
			<td align="center" colspan="2">
				<input type="button" name="butSuccess" id="butSuccess" disabled="disabled" value="成 功" class="button2">
				<input type="button" name="butFailure" id="butFailure" disabled="disabled" value="失 败" class="button2">
			</td>
			
		</tr>
	</table>
</body>

