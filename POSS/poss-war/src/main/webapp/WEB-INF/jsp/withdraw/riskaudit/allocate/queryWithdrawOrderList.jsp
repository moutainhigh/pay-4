<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
	<script type="text/javascript">
	$(document).ready(function(){
		 $("#userTable").tablesorter({
			 headers: {
			 	0: {sorter: false},
			 	12: {sorter: false}
			 }});
	});	
	
	function allocate(){
		var num = checkChecked();
		if(num>0){
			var res = checkStatus();
			if(res==true){
				var sel = document.getElementById("operatorSel");
				if(sel.value=="")
					alert("清选择操作员");
				else{
					var userId = sel.value;
					var auditStatus = document.getElementById("auditStatus").value;
					var wkKey = document.getElementById("wkKey").value;
					$('#infoLoadingDiv').dialog('open');
					$.ajax({
						type: "POST",
						url: "${ctx}/withdrawAllocate.do?method=allocate&userId="+userId+"&auditStatus="+auditStatus+"&wkKey="+wkKey,
						//data: pars,
						success: function(result) {
							if(result=='success'){
								$('#infoLoadingDiv').dialog('close');
								withdrawQuery();
							}else{
								$('#infoLoadingDiv').dialog('close');	
								alert('任务分配失败');
							}
						}
					});
				}	
			}else
				alert("所选数据状态不一致");
		}else
			alert("请选择数据");
	}
	
		//id的全选或全部取消
		function selectAll() {
			if($("#checkAll").attr("checked")){
				$("input[name='processKey']").each(function(){
					this.checked = true;
				});
			} else {
				$("input[name='processKey']").each(function() {
					this.checked = false;
				});
			}
			loadOperator();
		}
		//取消一个选择单位，去掉全选框的勾
		function selectAllcheckBoxunchecked(obj){
		  if(!obj.checked){
			  $("#checkAll").attr("checked",false);
			  }
		  loadOperator();
		}

		function checkStatus(){
			var status = '';
			var num = 0;
			var wkKey = document.getElementById("wkKey");
			wkKey.value='';
			for(var i=0;i <orderForm.elements.length;i++){ 
				  var element = orderForm.elements[i];
			      if(element.type == "checkbox" && element.checked==true && element.name!="checkAll"){
			    	  num++;
			    	  if(element.value.length>9){
						if(wkKey.value == '')
							wkKey.value = element.value;
						else
					    	wkKey.value += '::' + element.value;
			    	 } 
			          if(status=='')
				         status = document.getElementById(element.value).value;//status变量记录的是选择的第一条记录的状态
							//alert('i=0\t'+status);
			          else{//之前的判断是状态必须全部相等，现在改为0 3一组，1 2一组均可往下走
				          if(status=='0' || status=='3'){
				        	  if(document.getElementById(element.value).value!=0 && document.getElementById(element.value).value!=3){
				        		  wkKey.value = '';
								  return false;
					          }
					      }
				          if(status=='1' || status=='2'){
				        	  if(document.getElementById(element.value).value!=1 && document.getElementById(element.value).value!=2){
				        		  wkKey.value = '';
								  return false;
					          }
					      }
						  /*if(status!=document.getElementById(element.value).value){
							 // alert('i='+i+'\t'+status+'\t'+document.getElementById(element.value).value);
							 wkKey.value = '';
							 return false;	
							}*/		
				      }   
			      } 
			}
			document.getElementById("auditStatus").value=status;
			return true;
		}
		
		function loadOperator(){
			var num = checkChecked();
			if(num>0){
				var res = checkStatus();
				if( res==true){
					var pars = "auditStatus=" + $('#auditStatus').val();
					$.ajax({
						type: "POST",
						url: "${ctx}/withdrawAllocate.do?method=loadUserByRole",
						data: pars,
						success: function(result) {
							$('#operatorSel').html(result);
						}});
				}else
					alert("所选数据状态不一致");
				}
		}

		function checkChecked(){
			var num = 0;
			for(var i=0;i <orderForm.elements.length;i++){ 
				var element = orderForm.elements[i];
			      if(element.type == "checkbox" && element.checked==true && element.name!="checkAll"){
			    	  num++; 
			      } 
			}
			return num;
		}

		function showDetail(detailUrl,workflowKy){
			parent.addTabMenu('查看出款任务分配详细',detailUrl,workflowKy+"allocate");
		}

	</script>
</head>
<body>
	<form id="orderForm" name="orderForm">
	<input type="hidden" name="auditStatus" id="auditStatus" value=""/>
	<input type="hidden" name="wkKey" id="wkKey" value=""/>
	<table id="userTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		<thead> 
			<tr>     
				<th>
					选择<br>
					<input type="checkbox" name="checkAll" id="checkAll" onclick="selectAll();" />全选/反选
				</th>     
				<th>会员号</th>     
				<th>账户类型</th>       
				<th>交易流水号</th>
				<th>银行名称</th>
				<th>收款人</th>
				<th>银行账户</th>
				<th>汇款金额(元)</th>
				<th>省份</th>
				<th>城市</th>
				<th>交易时间</th>
				<th>状态</th>
				<th colspan="2">详情</th>  
			</tr> 
		</thead> 
		<tbody>
			<c:forEach items="${page.result}" var="orderDto"> 
			<tr>     
				<td>
				<input type="checkbox" name="processKey" value="${orderDto.workflowKy}" id="id" name="choose" onclick="selectAllcheckBoxunchecked(this);" /> 
				<input type="hidden" name="${orderDto.workflowKy}" id="${orderDto.workflowKy}" value="${orderDto.status}"/>
				</td>     
				<td>${orderDto.memberCode}</td>     
				<td>${orderDto.memberAccTypeStr}</td>          
				<td>${orderDto.sequenceId}</td> 
				<!-- <td>${orderDto.bankKy}</td> -->
				<td><li:code2name itemList="${withdrawBankList}" selected="${orderDto.bankKy}"/></td>
				<td>${orderDto.accountName}</td>
				<td>${orderDto.bankAcct}</td>
				<td><fmt:formatNumber value="${orderDto.amount/1000}" pattern="#,##0.00"  /></td>
				<td>${orderDto.bankProvinceStr}</td>
				<td>${orderDto.bankCityStr}</td>
				<td><fmt:formatDate value="${orderDto.createTime}" type="date"/></td>
				<td>${orderDto.statusStr}</td>
				<td><a href="javascript:showDetail('${ctx}/withdrawAllocate.do?method=showAuditOrderDetail&orderId=${orderDto.sequenceId}&workFlowId=${orderDto.workflowKy}&nodeId=2','${orderDto.sequenceId}')">查看</a></td>
			</tr>
			</c:forEach>
		</tbody> 
	</table>
	<li:pagination methodName="withdrawQuery" pageBean="${page}" sytleName="black2"/>
	</form>
	<table align="center">
	
		<tr>
			<td>
				选择操作员:&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
			</td>
			<td align="center">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
					<select name="operatorSel" style="width: 150px;" id="operatorSel" />
					&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
				</td>
				
		</tr>
		<tr>
			<td align="center" colspan="2">
				<input type="button" onclick="allocate();" name="submitBtn" value="分   配" class="button2">	
			</td>
		</tr>
	</table>
</body>

