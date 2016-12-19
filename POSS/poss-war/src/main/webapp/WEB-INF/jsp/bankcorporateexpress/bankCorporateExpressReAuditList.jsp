<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" language="javascript">
 	//复核通过
	function reAuditPass(){
		var num=0;
		var sequenceId = "";
		$("input[name='choose']").each(function(){
			if(this.checked == true){
				num++;
				sequenceId+=this.value+",";
			}
		});
		if(num<=0){
			alert("请选择数据");
			return false;
		}
		$("#sequenceList").val(sequenceId);
		
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#nextSetpFrom").serialize();
		$.ajax({
			type: "POST",
			url: "${ctx}/bankcorporateexpress.htm?method=reAuditPass",
			data: pars,
			success: function(result) {
				if(result=='success'){
					$('#infoLoadingDiv').dialog('close');
					bankCorporateExpressFailQuery();
					$.fo.alert('操作成功！');
				}else{
					$('#infoLoadingDiv').dialog('close');
					alert(msg.sequenceId+'审核提交失败');	
				}
			}
		});
	}
 	
	//复核拒绝
	function reAuditReject(){
		var num=0;
		var sequenceId = "";
		$("input[name='choose']").each(function(){
			if(this.checked == true){
				num++;
				sequenceId+=this.value+",";
			}
		});
		if(num<=0){
			alert("请选择数据");
			return false;
		}
		$("#sequenceList").val(sequenceId);
		
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#nextSetpFrom").serialize();
		$.ajax({
			type: "POST",
			url: "${ctx}/bankcorporateexpress.htm?method=reAuditReject",
			data: pars,
			success: function(result) {
				if(result=='success'){
					$('#infoLoadingDiv').dialog('close');
					bankCorporateExpressFailQuery();
					$.fo.alert('操作成功！');
				}else{
					$('#infoLoadingDiv').dialog('close');
					alert(msg.sequenceId+'审核提交失败');	
				}
			}
		});
	}
	
	function setDesc() {
		var count = 0;
		var amount = 0;
		$("input[type=checkbox][name='choose']:checked").each(function(i){
			count = i+1;
			amount += parseFloat($(this).attr("data-a"));
		});
		$("#chooseAmount").text(fmoney(amount/1000,2));
		$("#chooseCount").text(count);
	}
	
	function fmoney(s, n) {   
	   n = n > 0 && n <= 20 ? n : 2;   
	   s = parseFloat((s + "").replace(/[^\d\.-]/g, "")).toFixed(n) + "";   
	   var l = s.split(".")[0].split("").reverse(),   
	   r = s.split(".")[1];   
	   t = "";   
	   for(i = 0; i < l.length; i ++ )   
	   {   
	      t += l[i] + ((i + 1) % 3 == 0 && (i + 1) != l.length ? "," : "");   
	   }   
	   return t.split("").reverse().join("") + "." + r;   
	} 

	//id的全选或全部取消
	function selectAll() {
		if($("#checkAll").attr("checked")){
			$("input[name='choose']").attr("checked",true);
		} else {
			$("input[name='choose']").attr("checked",false);
		}
		setDesc();
	}
	//取消一个选择单位，去掉全选框的勾
	function selectAllcheckBoxunchecked(obj){
	  if(!obj.checked){
		  $("#checkAll").attr("checked",false);
		  }
		setDesc();
	}
	$(document).ready(function(){
		 $("#userTable").tablesorter({
			 headers: {
			 	0: {sorter: false},
			 	1: {sorter: false},
			 	13: {sorter: false}
			 }});
	});	
</script>

 <form action="" method="post"  name="nextSetpFrom" id="nextSetpFrom">
 	<input type="hidden"  name="sequenceList" id="sequenceList"/>
 	<input type="hidden"  name="busiType" value="${busiType}"/>
 </form>
 
<table id="userTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead> 
		<tr >
			<th>选择<input type="checkbox" name="checkAll" id="checkAll" onclick="selectAll();" /> </th>
			<th>序号</th>
			<th>交易流水号</th>
			<th>会员号</th>
			<th>会员名称</th>
			<th>银行名称</th>
			<th>开户行名称</th>
			<th>银行账户</th>
			<th>汇款金额(元)</th>
			<th>收款人姓名</th>
			<th>交易备注</th>
			<th>交易状态</th>
			<th>失败原因</th>
			<th>复核结果</th>
		</tr>
	</thead>
	<tbody> 
		<c:forEach items="${page.result}" var="dto" varStatus="status">
		<input type="hidden" value="${dto.amount}" id="chooseAmount${status.count}" />
			<tr>
				<td><input type="checkbox" value="${dto.workOrderky}" data-a="${dto.amount}" name="choose" onclick="selectAllcheckBoxunchecked(this);" /></td>
				<td ><c:out value="${status.count}" /></td>
				<td >${dto.sequenceId}</td>
				<td>${dto.memberCode}</td>
				<td>${dto.memberName}</td>
				<td>${dto.bankName}</td>
				<td>${dto.bankBranch}</td>
				<td>${dto.bankAcct}</td>
				<td><fmt:formatNumber value="${dto.amount/1000}" pattern="#,##0.00"  />&nbsp;</td>
				<td>${dto.accountName}</td>
				<td>${dto.orderRemarks}</td>
				<td>
					<c:if test="${ dto.status == 14}"> 出款失败 </c:if>
					<c:if test="${ dto.status == 12}"> 出款成功 </c:if>
					<c:if test="${ dto.status == 13}"> 出款处理中 </c:if>
					<c:if test="${ dto.status == 15}"> 审核确认失败 </c:if>
				</td>
				<td>${dto.failReason}</td>
				<td>${dto.reAudit}</td>
			</tr>
		</c:forEach>
	</tbody>
	
	<c:if test="${page.totalCount > 0}">
		<tr align="left">
			<td colspan="15">选中金额<span id="chooseAmount">0</span>元,共<span id="chooseCount">0</span>笔</td>
		</tr>
		<tr align="center">
			<td colspan="15">
				<input id="download" type="button" onclick="reAuditPass();" value="同意" class="button2" />
				<input id="download" type="button" onclick="reAuditReject();" value="拒绝" class="button2" />
			</td>
		</tr>
	</c:if>
</table>
<li:pagination methodName="bankCorporateExpressFailQuery" pageBean="${page}" sytleName="black2"/>
