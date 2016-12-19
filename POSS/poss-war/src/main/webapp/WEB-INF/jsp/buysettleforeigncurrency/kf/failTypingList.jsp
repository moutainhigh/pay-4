<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
	<body>
		<table class="tablesorter" border="0" cellpadding="0" cellspacing="1">
			<thead>
				<tr>
					<th class="item15" width="100px;">
						选择/反选	<input type="checkbox" onclick="cheked()" id="check">
					</th>
					<th>批次号</th>
					<th>流水号</th>
					<th>创建时间</th>
					<th>商户订单号</th>
					<th>业务类型</th>
					<th>汇款金额</th>
					<th>是否合并付款</th>
					<th>汇率</th>
					<th>汇款费用</th>
					<th>付款金额</th>
					<th>付款人</th>
					<th>收款人</th>
					<th>出款状态</th>
					<th>更新时间</th>
					<th>系统操作员</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="acc" items="${resultList}" varStatus="v">
					<c:choose>
	       <c:when test="${v.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
						<td><input type="checkbox" value="${acc.detailNo};${acc.outStatus};${acc.batchNo};${acc.orderId}"> </td>
						<td>${acc.batchNo}</td>
						<td>${acc.detailNo}</td>
						<td>${acc.createDate}</td>
						<td>${acc.orderId}</td>
						<td>
							<c:choose>
								<c:when test="${acc.type=='1'}">货物贸易</c:when>
								<c:when test="${acc.type=='2'}">机票旅游</c:when>
								<c:when test="${acc.type=='3'}">酒店住宿</c:when>
								<c:when test="${acc.type=='4'}">留学教育</c:when>
							</c:choose>	
						</td>
						<td>${acc.remitAmount} ${acc.remitCurrencyCode}</td>
						<td>否</td>
						<td>${acc.rate}</td>
						<td>${acc.remitExpense} CNY</td>
						<td>${acc.payAmount} CNY</td>
						<td>${acc.drawee}</td>
						<td>${acc.cheques}</td>
						<td>
							<c:choose>
								<c:when test="${acc.outStatus=='-1'}">待付款</c:when>
								<c:when test="${acc.outStatus=='0'}">待出款</c:when>
								<c:when test="${acc.outStatus=='1'}">出款中</c:when>
								<c:when test="${acc.outStatus=='2'}">审核拒绝</c:when>
								<c:when test="${acc.outStatus=='3'}">出款成功</c:when>
								<c:when test="${acc.outStatus=='4'}">出款失败</c:when>
							</c:choose>	
						</td>
						<td>${acc.completeDate}</td>
						<td>${acc.operator}</td>
					</tr>
				</c:forEach>
			</tbody>
		</table>	
		<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>
	</body>
</html>

<table class="" align="center" border="0" cellpadding="0" cellspacing="1">
		<tr>
		<td align="right">出款失败原因：</td>
		<td><textarea rows="4" cols="45" name="auditRemark" id="auditRemark"></textarea></td>
		</tr>
		
	</table>
		<table align="center">
		<tr>
			<td align="center"> <!-- 出款状态 0: 待审核  1：审核通过  2：审核拒绝  3：出款成功   4：出款失败 -->
					<input type="button" onclick="sendAudit(1);" id="btn2" name="submitBtn" value="审核通过" class="button2">
					<input type="button" onclick="sendAudit(2);" id="btn1" name="submitBtn" value="审核拒绝" class="button2">
					<input type="button" onclick="sendAudit(3);" id="btn1" name="submitBtn" value="出款成功" class="button2">
					<input type="button" onclick="sendAudit(4);" id="btn2" name="submitBtn" value="出款失败" class="button2">
				</td>
		</tr>
	</table>
<script type="text/javascript">
function sendAudit(status){
		var op=""; 
		var detailNos="";
		var batchNos="";
		var re="";
		 $("input[type='checkbox']").each(function(){
			 if(this.checked){
				if($(this).val()!='on'){
					var split=$(this).val().split(";");
					if(split[1] == '0'){
						if(status == '3' || status == '4' ){
							alert("请先审核通过，在进行出款操作！！！");
							op="1" ;
						}
					}
					if(split[1] == '1'){
						if(status == '1' || status == '2' ){
							alert("审核通过，请不要进行无效操作！！！");
							op="1" ;
						}
					}
					if(split[1] == '2'){
						alert("审核已拒绝，请不要进行无效操作！！！");
						op="1" ;
					}
					if(split[1] == '3'){
						alert("出款已成功，请不要进行无效操作！！！");
						op="1" ;
					}
					if(split[1] == '4'){
						alert("出款已失败，请不要进行无效操作！！！");
						op="1" ;
					}
					detailNos+=split[0]+",";							
					batchNos+="\'"+split[2]+"\'"+",";	
					re+=$(this).val()+"#";
				}
			};
		 })
		 if(op == '1'){
			 return ;
		 }
		if(detailNos==''){
			alert("请选择需要审核的出款！");
			return;
		}
		if(status == '4'){
			//  groupName= java.net.URLDecoder.decode(groupName, "UTF-8");
			var auditRemark=$("#auditRemark").val();
			window.location.href="${ctx}/remitFailTyping.do?method=bacthReviewed&detailNos="+detailNos+"&status="+status+"&remark="+encodeURI(encodeURI(auditRemark))+"&batchNos="+batchNos+"&re="+re;
		}else{
			window.location.href="${ctx}/remitFailTyping.do?method=bacthReviewed&detailNos="+detailNos+"&status="+status+"&batchNos="+batchNos+"&re="+re;
		}
}

function query(pageNo, totalCount){
	var pars = $("#mainfrom").serialize() + "&pageNo=" + pageNo
	+ "&totalCount=" + totalCount;
	$.ajax({
		type : "POST",
		url : "./remitFailTyping.do?method=list",
		data : pars,
		success : function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
}
function cheked(){
	 if($("#check").attr("checked")==true){
		 $("input[type='checkbox']").each(function(){
			 this.checked=true;
		 })
	 }else{
		 $("input[type='checkbox']").each(function(){
			 this.checked=false;
		 })
	 }
}
</script>