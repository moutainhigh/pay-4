<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
	<script type="text/javascript">
	$(document).ready(function(){
		 $("#userTable").tablesorter({
			 headers: {
			 	0: {sorter: false},
			 	16: {sorter: false}
			 }});
	});	
	
	var num=0;
	function sendAudit(key,obj){
		btnDisabledSetTrue();
		checkSelected();
		if(num>0){
		if(key==1){
				var res = checkStatus();
				if(res==false){
					$.fo.alert('已出批次,不能回退！');
					btnDisabledSetFalse();
					return false;
				}	
			}

			var auditRemark = $.trim($("#auditRemark").val());
			if(!s_validateStrLength(auditRemark,2,100)){
				$.fo.alert('批量操作备注最小2个字符,最大不超过50个汉字！');	
				btnDisabledSetFalse();
				return false;
			}
		
			$('#infoLoadingDiv').dialog('open');
			var pars = $("#orderForm").serialize() + "&auditStatus=" + key ;
			$.ajax({
				type: "POST",
				url: "${ctx}/withdrawSecondAudit.do?method=audit",
				data: pars,
				success: function(result) {
				 	var msg = eval('('+result+')');
					if(msg.isSuccess){
						$('#infoLoadingDiv').dialog('close');
						withdrawQuery();
						$.fo.alert('操作成功！');
					}else{
						$('#infoLoadingDiv').dialog('close');
						alert(msg.sequenceId+'复核提交失败');
						btnDisabledSetFalse();
					}
				}
			});
		}else{
			$.fo.alert('请选择提现记录！');
			btnDisabledSetFalse();
		}
	}
	
	//id的全选或全部取消
		//id的全选或全部取消
		function selectAll() {
			if($("#checkAll").attr("checked")){
				$("input[name='wkKey']").each(function(){
					this.checked = true;
				});
			} else {
				$("input[name='wkKey']").each(function() {
					this.checked = false;
				});
			}
		}
		//取消一个选择单位，去掉全选框的勾
		function selectAllcheckBoxunchecked(obj){
		  if(!obj.checked){
			  $("#checkAll").attr("checked",false);
			  }
		}

		function checkStatus(){
			var res = false;
			for(var i=0;i <orderForm.elements.length;i++){ 
				  var element = orderForm.elements[i];
			      if(element.type == "checkbox" && element.checked==true && element.name!="checkAll"){
				      num++;
			    	  if(element.value>=4){
						var batchStatus = document.getElementById(element.value).value;
						if(batchStatus==1)
							res = false;
						else
							res = true;
						
				      }   
			      } 
			}
			return res;
		}

		function checkSelected(){
			num = 0;
			for(var i=0;i <orderForm.elements.length;i++){ 
				var element = orderForm.elements[i];
			      if(element.type == "checkbox" && element.checked==true && element.name!="checkAll"){
			    	  num++; 
			      } 
			}
			return num;
		}

		function btnDisabledSetTrue(){
			$("#btn1").attr("disabled",true);
			$("#btn2").attr("disabled",true);
		}

		function btnDisabledSetFalse(){
			$("#btn1").attr("disabled",false);
			$("#btn2").attr("disabled",false);
		}
		function showDetail(detailUrl,workflowKy){
			parent.addTabMenu('查看待复核出款数据详细',detailUrl,workflowKy+"secondAudit");
		}
	</script>
</head>

<body>
	<form id="orderForm" name="orderForm">
	<table id="userTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		<thead> 
			<tr>     
				<th>
					选择<br>
					<input type="checkbox" name="checkAll" id="checkAll" onclick="selectAll();" />全选/反选
				</th>     
				<th>会员号</th>
				<th>会员名称</th>
				<th>交易类型</th>     
				<th>账户类型</th>  
				<th>商户风控等级</th>
				<th>结算周期</th>      
				<th>交易流水号</th>
				<th>银行名称</th>
				<th>收款人</th>
				<th>银行账户</th>
				<th>汇款金额</th>
				<th>省份</th>
				<th>城市</th>
				<th>交易时间</th>
				<th>状态</th>
				<th colspan="2">详情</th>  
			</tr> 
		</thead> 
		<tbody>
			<c:forEach items="${page.result}" var="orderDto" varStatus = "personalStatus">
			<input type="hidden" name="${orderDto.workOrderky}" id="${orderDto.workOrderky}" value="${orderDto.batchStatus}"/>
			<c:choose>
	       <c:when test="${personalStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>     
				<td>
				<input type="checkbox" name="wkKey" value="${orderDto.workOrderky}" id="id" name="choose" onclick="selectAllcheckBoxunchecked(this);" /> 
				</td>     
				<td>${orderDto.memberCode}</td>
				<td>${orderDto.memberName}</td>  
				<td>${orderDto.busiTypeStr}</td>       
				<td>${orderDto.memberAccTypeStr}</td>
				<td><li:code2name itemList="${riskLeveCodeList}" selected="${orderDto.riskLeveCode}"/></td>        
				<td><li:code2name itemList="${accountModeList}" selected="${orderDto.accountMode}"/></td>
				<td>${orderDto.sequenceId}</td> 
				<td><li:code2name itemList="${withdrawBankList}" selected="${orderDto.bankKy}"/></td>
				<td>${orderDto.accountName}</td>
				<td>${orderDto.bankAcct}</td>
				<td><fmt:formatNumber value="${orderDto.amount/1000}" pattern="#,##0.00"  /></td>
				<td>${orderDto.bankProvinceStr}</td>
				<td>${orderDto.bankCityStr}</td>
				<td><fmt:formatDate value="${orderDto.webAuditTime}" type="both"/></td>
				<td>${orderDto.statusStr}</td>
				<td><a href="javascript:showDetail('${ctx}/withdrawSecondAudit.do?method=showAuditOrderDetail&orderId=${orderDto.sequenceId}&workOrderKy=${orderDto.workOrderky}&nodeId=2','${orderDto.sequenceId}')">查看</a></td>
				<!--<td><a href="${ctx}/withdrawSecondAudit.do?method=showAuditOrderDetail&orderId=${orderDto.sequenceId}&workFlowId=${orderDto.workflowKy}&nodeId=2">查看</a></td>--> 
			</tr>
			</c:forEach>
		</tbody> 
	</table>
	<table class="" align="center" border="0" cellpadding="0" cellspacing="1">
		<tr>
		<td align="right">批量操作备注：</td>
		<td><textarea rows="4" cols="45" name="auditRemark" id="auditRemark"></textarea></td>
		</tr>
		
		</table>
	</form>
	<li:pagination methodName="withdrawQuery" pageBean="${page}" sytleName="black2"/>
	<table align="center">
		<tr>
			<td align="center">
					<input type="button" onclick="sendAudit(0);" id="btn1" name="submitBtn" value="同意" class="button2">
					<input type="button" onclick="sendAudit(1);" id="btn2" name="submitBtn" value="退回" class="button2">
				</td>
		</tr>
	</table>
</body>

