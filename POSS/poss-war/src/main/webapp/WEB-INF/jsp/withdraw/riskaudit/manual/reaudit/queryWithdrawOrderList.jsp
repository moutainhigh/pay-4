<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
	<script type="text/javascript">
	$(document).ready(function(){
		 $("#userTable").tablesorter({
			 headers: {
			 	0: {sorter: false},
			 	9: {sorter: false}
			 }});
	});	
	function sendAudit(key){
		if(0 == $("input[name=choose]:checked").size()){
			$.fo.alert('请您选择记录以后再进行提交!');
			return false;
		}
		
		if(checkStatus()){
			$('#infoLoadingDiv').dialog('open');
			var pars = $("#orderForm").serialize() + "&auditStatus=" + key ;
			$.ajax({
				type: "POST",
				url: "${ctx}/withdrawResManualReAudit.do?method=audit",
				data: pars,
				success: function(result) {
				 	var msg = eval('('+result+')');
					if(msg.isSuccess==true){
						$('#infoLoadingDiv').dialog('close');
						withdrawQuery();
						$.fo.alert('操作成功!');	
					}else{
						$('#infoLoadingDiv').dialog('close');
						$.fo.alert(msg.sequenceId+'人工复核提交失败');	
					}
				}
			});
		}else
			alert("输入字数过多,只能输入50个字");
	}
	
	//id的全选或全部取消
		//id的全选或全部取消
		function selectAll() {
			if($("#checkAll").attr("checked")){
				$("input[name='choose']").each(function(){
					this.checked = true;
				});
			} else {
				$("input[name='choose']").each(function() {
					this.checked = false;
				});
			}
			sumVal();
		}
		//取消一个选择单位，去掉全选框的勾
		function selectAllcheckBoxunchecked(obj){
		  if(!obj.checked){
			  	$("#checkAll").attr("checked",false);
			  }
		  sumVal();
		}

		function sumVal(){
			var amount = 0;
			$("input[name='choose']").each(function(){
				if(this.checked){
					amount = amount + parseFloat($(this).attr("val"));
				}
			});
			amount = amount.toFixed(2);
			$("#divAmount").html(fmoney(amount,2));
		}
		
		function fmoney(s, n)   
		{   
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

		function checkStatus(){
			var res = document.getElementById("reAuditBackReason").value;
			if(res.length>50)	
				return false;
			else
				return true;
		}

	</script>
</head>

<body>
	<form id="orderForm">
	<input type="hidden" name="failReason" value=""/>
	<table id="userTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		<thead> 
			<tr>     
				<th>
					选择<br>
					<input type="checkbox" name="checkAll" id="checkAll" onclick="selectAll();" />全选/反选
				</th>
				<th>交易流水号</th>
				<th>银行名称</th>
				<th>开户行名称</th>
				<th>银行账户</th>
				<th>汇款金额(元)</th>
				<th>收款人</th>
				<th>交易备注</th>
				<th>状态</th>
				<th>失败原因</th>
			</tr> 
		</thead> 
		<tbody>
			<c:forEach items="${page.result}" var="orderDto" varStatus = "personalStatus">
		<c:choose>
	       <c:when test="${personalStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>    
				<td>
				<input type="checkbox" val="${orderDto.amount/1000}" value="${orderDto.workOrderky}" id="choose" name="choose" onclick="selectAllcheckBoxunchecked(this);" /> 
				</td>     
				<td>${orderDto.sequenceId}</td>     
				<td><li:code2name itemList="${withdrawBankList}" selected="${orderDto.bankKy}"/></td>
				<td>${orderDto.bankBranch}</td>     
				<td>${orderDto.bankAcct}</td> 
				<td><fmt:formatNumber value="${orderDto.amount/1000}" pattern="#,##0.00"  /></td>
				<td>${orderDto.accountName}</td>     
				<td>${orderDto.orderRemarks}</td>
				<td>${orderDto.statusStr}</td>
				<td>${orderDto.auditFailReason}</td>
			</tr>
			</c:forEach>
		</tbody> 
	</table>
	<p align="center">
		汇款金额：<label id="divAmount">0</label>
		</p>
<p align="center">
批量退回原因:<input type="text" name="reAuditBackReason" id="reAuditBackReason" value=""/>
	</p>
	</form>
	<li:pagination methodName="withdrawQuery" pageBean="${page}" sytleName="black2"/>
	<table align="center">
		<tr>
			<td align="center">
				<input type="button" onclick="sendAudit(1);" name="submitBtn" value="同意" class="button2">
				<input type="button" onclick="sendAudit(0);" name="submitBtn" value="退回" class="button2">
				&nbsp;&nbsp;&nbsp;
			</td>
		</tr>
	</table>
</body>

