<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
 
<head>
    待审核退款订单列表
</head>
	<script type="text/javascript">
		function processDo(id){
			location.href = "${ctx}/fundout-withdraw-withdrawrefund.do?method=refundOrderdetail&id=" + id;
		}
		$(document).ready(function(){
			$("#choiceAll").click(function(){
				if($(this).attr("checked")==true){
					$("#tb input").each(function(index){
						$(this).attr("checked",true);
					});
				}else{
					$("#tb input").each(function(index){
						$(this).attr("checked",false);
					});
				}
			});

			$("#pass,#repulse").click(function(){
				if(checkChoice()){
					var ids="";
					var cs=document.getElementsByName("choice");
					for(var i=0;i<cs.length;i++){
						if(cs[i].checked==true){
							ids+=cs[i].value+",";
						}
					}
					var remarks=$("#remarks").val();
					if(null==remarks||''==remarks){
						alert("请输入批量原因再提交！");
						return;
					}
					remarks=encodeURIComponent(remarks);
					$("#pass").attr("disabled","disabled");
					$("#repulse").attr("disabled","disabled");
					var url="${ctx}/fundout-withdraw-withdrawrefund.do?method=batchVerify"+"&ids="+ids
							+"&uuidVerify=${sessionScope.uuidVerify}"+"&remarks="+remarks;
					if($(this).attr("id")=="pass"){
						url+="&flag=yes";
					} else {
						url+="&flag=no";
					}
					location.href=url;
				}
			});
		});

		function checkChoice(){
			var cnt=0;
			$("#tb input").each(function(index){
				if($(this).attr("type")=="checkbox" && $(this).attr("checked")==true){
					cnt++;
				}
			});
			if(cnt==0){
				alert("请至少选择一条");
				return false;
			}
			return true;
		}
	</script>

<body>


	<table id="withdrawTable" align="center" class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
	<thead>
		<tr>
			<th><input type="checkbox" id="choiceAll"/>选择</th>
			<th>退款流水号</th>
			<th>交易流水号</th>
			<th>银行名称</th>
			<th>开户行名称</th>
			<th>银行账户</th>
			<th>汇款金额（元）</th>
			<th>收款人</th>
			<th>银行流水号</th>
			<th>业务类型</th>
			<th>详情</th>
		</tr>
	</thead>
	<tbody id="tb">
		<c:forEach items="${page.result}" var="order" varStatus = "personalStatus">
		<c:choose>
	       <c:when test="${personalStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
				<td><input type="checkbox" name="choice" id="choice" value="${order.refundOrderId}"/></td>
				<td>${order.refundOrderId}</td>
				<td>${order.orderId}</td>
				<td>${order.payeeBankName}</td>
				<td>${order.payeeOpeningBankName}</td>
				<td>${order.payeeBankAcctCode}</td>
				<td><fmt:formatNumber value="${order.orderAmount * 0.001}" pattern="#,##0.00"/></td>
				<td>${order.payeeName}</td>
				<td>${order.bankOrderId==null?'':order.bankOrderId}</td>
				<td>
				<c:choose>
					<c:when test="${order.orderType==0}">提现</c:when>
					<c:when test="${order.orderType==3}">付款到银行</c:when>
					<c:when test="${order.orderType==4}">批量付款到银行</c:when>
				</c:choose>
				</td>
				<td>
					<a href="javascript:void(0)" onclick="processDo('${order.refundOrderId}')">查看</a>
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/> 
<br>
<table class="" align="center" border="0" cellpadding="0" cellspacing="1">
		<tr>
		<td align="right">批量原因：</td>
		<td><textarea rows="5" cols="60" name="remarks" id="remarks"></textarea></td>
		</tr>
		<tr><td colspan="2" align="center">
		<input id="pass" type="button" value="批量通过"/>&nbsp;&nbsp;&nbsp;&nbsp;
<input id="repulse" type="button" value="批量拒绝"/>
</td></tr>
		
	</table>
<!-- 批量原因：<textarea rows="5" cols="60" name="remarks" id="remarks"></textarea>
<br><br>
<input id="pass" type="button" value="批量通过"/>&nbsp;&nbsp;&nbsp;&nbsp;
<input id="repulse" type="button" value="批量拒绝"/>
<br> -->
</body>

