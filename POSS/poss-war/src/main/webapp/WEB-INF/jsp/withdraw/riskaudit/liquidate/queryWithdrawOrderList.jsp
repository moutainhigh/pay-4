<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
	<script type="text/javascript">
	$(function(){
		$("[name='inBlance']").each(function(){
				var idName = $(this).attr("id");
				var memberCode = $(this).attr("dm");
				var amount = $(this).attr("da");
				var memberAccType = $(this).attr("dmat");
				queryBlance(idName,memberCode,amount,memberAccType);
			});
});

	function queryBlance(idName,memberCode,amount,memberAccType) {
		
		//var urls = "${ctx}/fundout-withdraw-generatebatch.do?method=getInbalanceAjax&memberCode="+memberCode+"&amount="amount+"&memberAccType="+memberAccType;
		var urls = "${ctx}/fundout-withdraw-generatebatch.do?method=getInbalanceAjax&memberCode="+memberCode+"&amount="+amount+"&memberAccType="+memberAccType;
		$.post(urls,function(d){
			$("#"+idName+"").html(d);
		});	
	}
	
	$(document).ready(function(){
		 $("#userTable").tablesorter({
			 headers: {
			 	0: {sorter: false},
			 	12: {sorter: false}
			 }});
	});	
	var num=0;
	function sendAudit(key,obj){
		btnDisabledSetTrue();
		checkSelected();
		if(num>0){
			var res = checkStatus();
			if(res==false){
				alert('已出批次,不能拒绝或者退回!');
				btnDisabledSetFalse();
				return false;
			}

			var auditRemark = $.trim($("#auditRemark").val());
			if(!s_validateStrLength(auditRemark,2,100)){
				$.fo.alert('批量操作备注最少2个字符,最大不超过50个汉字!',{t:'3',close:function(){}});
				btnDisabledSetFalse();	
				return false;
			}
				
			$('#infoLoadingDiv').dialog('open');
			var pars = $("#orderForm").serialize() + "&auditStatus=" + key ;
			$.ajax({
				type: "POST",
				url: "${ctx}/withdrawLiquidateAudit.do?method=audit",
				data: pars,
				success: function(result) {
				 	var msg = eval('('+result+')');
					if(msg.isSuccess==true){
						$('#infoLoadingDiv').dialog('close');
						withdrawQuery();
						$.fo.alert('操作成功！');
					}else{
						$('#infoLoadingDiv').dialog('close');
						alert(msg.sequenceId+'清结算提交失败!');	
						btnDisabledSetFalse();
					}
				}
			});
		}else{
			$.fo.alert('请选择记录!',{t:'3',close:function(){}});
			btnDisabledSetFalse();
		}
	}
	
		//id的全选或全部取消
		function selectAll() {
			if($("#checkAll").attr("checked")){
				$("input[name='wkKey']").each(function(){
					this.checked = true;
				});
				var flag=true;
				$("input[name$='_s']").each(function(){
					if(this.value=="4"){
						flag=false;
					}
				});
				if(!flag){
					showMsg();
				} else {
					hideMsg();
				}
			} else {
				$("input[name='wkKey']").each(function() {
					this.checked = false;
					hideMsg();
				});
			}
			setDesc();
		}
		//取消一个选择单位，去掉全选框的勾
		function selectAllcheckBoxunchecked(obj,status){
			if(!obj.checked){
		 		$("#checkAll").attr("checked",false);
		 		var flag=true;
				$("input[name$='wkKey']").each(function(){
					if(this.checked && $("input[name='"+this.value+"_s']").val()=="4"){
						flag=false;
					}
				});
				if(!flag){
					showMsg();
				} else {
					hideMsg();
				}
		   } else {
			   var flag=true;
			   $("input[name$='wkKey']").each(function(){
					if(this.checked && $("input[name='"+this.value+"_s']").val()=="4"){
						flag=false;
					}
				});
				if(!flag){
					showMsg();
				} else {
					hideMsg();
				}
			}
			setDesc();
		}

		function setDesc() {
			var count = 0;
			var amount = 0;
			$("input[type=checkbox][name='wkKey']:checked").each(function(i){
				count = i+1;
				amount += parseFloat($(this).attr("data-a"));
			});
			$("#chooseAmount").text(fmoney(amount/1000,2));
			$("#chooseCount").text(count);
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

		function showMsg(){
			 $("#infoMsg").show();
			 $("#infoMsg").html("已经通过风控复核,不允许拒绝");
			 $("#refusedBtn").attr("disabled","disabled");
		}

		function hideMsg(){
			 $("#infoMsg").hide();
			 $("#infoMsg").html("");
			 $("#refusedBtn").attr("disabled","");
		}
		
		function checkStatus(){
			var res = false;
			for(var i=0;i <orderForm.elements.length;i++){
				  var element = orderForm.elements[i];
			      if(element.type == "checkbox" && element.checked==true && element.name!="checkAll"){
				      num++;
				      var status = document.getElementById(element.value+"_s").value;
	                  var batchStatus = document.getElementById(element.value+"_bs").value;
			    	  if(status>=4){
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
			parent.addTabMenu('查看复核数据详细',detailUrl,workflowKy+"liquidate");
		}
	</script>
</head>

<body>
	<form id="orderForm">
	<li id="infoMsg" style="color: red;display: none">
		</li>
	<table id="userTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		<thead> 
			<tr>     
				<th>
					选择<br>
					<input type="checkbox" name="checkAll" id="checkAll" onclick="selectAll();" />全选/反选
				</th>     
				<th>会员号</th>
				<th >会员名称</th>     
				<th>账户类型</th>
				<th >结算周期</th>
				<th >是否垫资</th>          
				<th>交易流水号</th>
				<th>银行名称</th>
				<th>收款人</th>
				<th>银行账户</th>
				<th>汇款金额（元）</th>
				<th>入款金额（元）</th>
				<th>省份</th>
				<th>城市</th>
				<th>交易时间</th>
				<th>状态</th>
				<th>详情</th>  
			</tr> 
		</thead> 
		<tbody id="tb">
			<c:forEach items="${page.result}" var="orderDto" varStatus="status">
			<c:set var="preStr" value="K${orderDto.sequenceId}"></c:set>
			<c:choose>
	       <c:when test="${status.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>    
				<td>
				<input type="hidden" id="${orderDto.workOrderky}_bs" name="${orderDto.workOrderky}_bs" value="${orderDto.batchStatus}"/>
				<input type="hidden" id="${orderDto.workOrderky}_s" name="${orderDto.workOrderky}_s" value="${orderDto.status}"/>
				<input type="checkbox" name="wkKey" value="${orderDto.workOrderky}" data-a="${orderDto.amount}" id="wkKey" onclick="selectAllcheckBoxunchecked(this,'${orderDto.status}');" /> 
				</td>     
				<td>${orderDto.memberCode}</td>
				<td>${orderDto.memberName}</td>     
				<td>${orderDto.memberAccTypeStr}</td>
				<td><li:code2name itemList="${accountModeList}" selected="${orderDto.accountMode}"/></td>
				<td>
					<c:choose>
					   <c:when test="${orderDto.isLoaning==1}">
					   		是
					   </c:when>
					   <c:otherwise>
					   		否
					   </c:otherwise>
					</c:choose>
				</td>     
				<td>${orderDto.sequenceId}</td> 
				<td><li:code2name itemList="${withdrawBankList}" selected="${orderDto.bankKy}"/></td>
				<td>${orderDto.accountName}</td>
				<td>${orderDto.bankAcct}</td>
				<td><fmt:formatNumber value="${orderDto.amount/1000}" pattern="#,##0.00"  /></td>
				<!-- <td>
					<fmt:formatNumber value="${inBalanceMap[preStr]*0.001}" pattern="#,##0.00" />
				</td> -->
				<td id="I${status.count}" name="inBlance" da="${orderDto.amount}" dm="${orderDto.memberCode}" dmat="${orderDto.memberAccType}"></td>
				<td>${orderDto.bankProvinceStr}</td>
				<td>${orderDto.bankCityStr}</td>
				<td><fmt:formatDate value="${orderDto.webAuditTime}" type="both"/></td>
				<td>${orderDto.statusStr}</td>
				<td><a href="javascript:showDetail('${ctx}/withdrawLiquidateAudit.do?method=showAuditOrderDetail&orderId=${orderDto.sequenceId}&workOrderKy=${orderDto.workOrderky}&nodeId=2','${orderDto.sequenceId}')">查看</a></td>
				<!--<td><a href="${ctx}/withdrawLiquidateAudit.do?method=showAuditOrderDetail&orderId=${orderDto.sequenceId}&workFlowId=${orderDto.workflowKy}&nodeId=2">查看</a></td>--> 
			</tr>
			</c:forEach>
		</tbody>
		<tr class="trForContent1" align="center">
			<td colspan="17">选中金额<span id="chooseAmount">0</span>元,共<span id="chooseCount">0</span>笔</td>
		</tr> 
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
				<input type="button" onclick="sendAudit(1);" name="submitBtn" id="btn1" value="退    回" class="button2">	
				<input type="button" id="refusedBtn" onclick="sendAudit(0);" id="btn2" 
			name="submitBtn" value="拒   绝" class="button2">	
			</td>
		</tr>
		
	</table>
		
</body>

