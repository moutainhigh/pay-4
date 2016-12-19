<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
	<script type="text/javascript">
	$(document).ready(function(){
		 $("#userTable").tablesorter({
			 headers: {
			 	12: {sorter: false},
			 	13: {sorter: false}
			 }});
		 
		//$("#dialog").dialog();
	
		//var msg = "<p>您处理100笔,成功80笔,失败20笔</p><table border='1' align='center'><thead><tr> <th>交易流水号</th><th>处理失败原因</th></tr> </thead><tr><td>aaaaaaaaaa</td><td>bbbbbbbbbb</td></tr></table>";
	});	
	function sendAudit(key){
		if(0 == $("input[name=wkKey]:checked").size()){
			$.fo.alert('请您选择提现数据后再进行提交！');	
			return false;
		}
		var auditRemark = $.trim($("#auditRemark").val());
		if(!s_validateStrLength(auditRemark,2,50)){
			$.fo.alert('批量操作备注最少2个字符,最大不超过50个汉字！');	
			return false;
		}
		
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#orderForm").serialize() + "&auditStatus=" + key ;
		$.ajax({
			type: "POST",
			url: "${ctx}/withdrawAudit.do?method=audit",
			data: pars,
			success: function(result) {
			 	var msg = eval('('+result+')');
				if(msg.isSuccess==true){
					$('#infoLoadingDiv').dialog('close');
					withdrawQuery();
				}else{
					$('#infoLoadingDiv').dialog('close');
					//var msg = "<p>您处理100笔,成功80笔,失败20笔</p><table border='1' align='center'><thead><tr><th>交易流水号</th><th>处理失败原因</th></tr></thead><tr><td>aaaaaaaaaa</td><td>bbbbbbbbbb</td></tr></table>";
					$.fo.alert(msg.sequenceId+'审核提交失败');	
					//alert(msg.sequenceId+'审核提交失败');
				}
			}
		});
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
				      num++;alert(element.value);
			    	  if(element.value>=4){
						var batchStatus = document.getElementById(element.value).value;
						if(batchStatus!=0)
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

		function showUrl(menu,Url){
		    parent.addMenu(menu,Url);
		}

		function showDetail(detailUrl,workOrderKy){
			parent.addTabMenu('查看风控未处理数据详细',detailUrl,workOrderKy);
		}
	</script>
</head>

<body>
	总笔数：<font color="red"><b>${COUNTDATA}&nbsp;</b></font>笔 
	&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
	总金额：<font color="red"><b><fmt:formatNumber value="${AMOUNT*0.001}" pattern="#,##0.00"/>&nbsp;</b></font>元
	<form id="orderForm">
	<table id="userTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		<thead> 
			<tr>     
				<th>会员号</th> 
				<th>会员名称</th>
				<th>账户类型</th>
				<th>交易类型</th>
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
			<c:forEach items="${page.result}" var="orderDto" varStatus = "personalStatus">
		<c:choose>
	       <c:when test="${personalStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>	
				<td>${orderDto.memberCode}</td>
				<td>${orderDto.memberName}</td>        
				<td>${orderDto.memberAccTypeStr}</td>
				<td>${orderDto.busiTypeStr}</td>
				<td>${orderDto.sequenceId}</td> 
				<td><li:code2name itemList="${withdrawBankList}" selected="${orderDto.bankKy}"/></td>
				<td>${orderDto.accountName}</td>
				<td>${orderDto.bankAcct}</td>
				<td><fmt:formatNumber value="${orderDto.amount*0.001}" pattern="#,##0.00"  /></td>
				<td>${orderDto.bankProvinceStr}</td>
				<td>${orderDto.bankCityStr}</td>
				<td><fmt:formatDate value="${orderDto.webAuditTime}" type="both"/></td>
				<td>${orderDto.statusStr}</td>
				<td><a href="javascript:showDetail('${ctx}/withdrawreport.htm?method=showNoDisposeDetailInfo&orderId=${orderDto.sequenceId}','${orderDto.sequenceId}');">查看</a></td> 
			</tr>
			</c:forEach>
		</tbody> 
	</table>
	</form>
	<li:pagination methodName="withdrawQuery" pageBean="${page}" sytleName="black2"/>
</body>

