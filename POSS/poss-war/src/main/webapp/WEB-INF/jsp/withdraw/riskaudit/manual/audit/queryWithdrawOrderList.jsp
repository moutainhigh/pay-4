<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
	<script type="text/javascript">
	function sendAudit(key){
		if(0 == $("input[name=choose]:checked").size()){
			$.fo.alert("请您选择记录以后再进行提交!");
			return false;
		}
		
		var res = checkStatus();
		if(res){
			$('#infoLoadingDiv').dialog('open');
			var pars = $("#orderForm").serialize() + "&auditStatus=" + key ;
			$.ajax({
				type: "POST",
				url: "${ctx}/withdrawResManualAudit.do?method=audit",
				data: pars,
				success: function(result) {
				 	var msg = eval('('+result+')');
					if(msg.isSuccess==true){
						$('#infoLoadingDiv').dialog('close');
						withdrawQuery();
						$.fo.alert("操作成功!");
					}else{
						$('#infoLoadingDiv').dialog('close');	
						$.fo.alert(msg.sequenceId+'人工审核提交失败');
					}
				}
			});
		}else
			$.fo.alert("输入字数过多,只能输入50个字");
	}
	
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
			var totalCount = 0;
			$("input[name='choose']").each(function(){
				if(this.checked){
					amount = amount + parseFloat($(this).attr("val"));
					totalCount += 1;
				}
			});
			amount = amount.toFixed(2);
			$("#divAmount").html(fmoney(amount,2));
			$("#divTotal").html(totalCount);
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
			for(var i=0;i <orderForm.elements.length;i++){
				  var element = orderForm.elements[i];
			      if(element.type == "checkbox" && element.checked==true && element.name!="checkAll"){
	                 var singleFailReason = document.getElementById(element.value).value;
			    	  if(singleFailReason.length>50){
						return false;
				      } 
			      } 
			}
			var batchFailReason = document.getElementById("batchFailReason").value;
			if(batchFailReason.length>50)
				return false;
			return true;
		}
		
	</script>
</head>

<body>
	<form id="orderForm" name="orderForm">
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
				<td>${orderDto.batchStatusStr}</td>
				
				<td><input type="text" id="${orderDto.workOrderky}" name="${orderDto.workOrderky}" value='' style="width: 150px;" ></td>
			</tr>
			</c:forEach>
		</tbody> 
	</table>
	<p align="center">
		汇款金额：<label id="divAmount">0</label>，汇款笔数：<label id="divTotal">0</label>
	</p>
	<p align="center">
		批量失败原因:<input type="text" id="batchFailReason" name="batchFailReason" value=""/>
	</p>
	</form>
	<li:pagination methodName="withdrawQuery" pageBean="${page}" sytleName="black2"/>
	<table align="center">
		<tr>
			<td align="center">
				<input type="button" onclick="sendAudit(1);" name="submitBtn" value="成功" class="button2">
				<input type="button" onclick="sendAudit(0);" name="submitBtn" value="失败" class="button2">	
			</td>
		</tr>
	</table>
</body>

