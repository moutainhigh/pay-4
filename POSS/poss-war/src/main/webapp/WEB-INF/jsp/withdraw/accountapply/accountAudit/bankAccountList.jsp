<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
	<script type="text/javascript">
	$(document).ready(function(){
		 $("#userTable").tablesorter({
			 headers: {
			 	0: {sorter: false},
			 	6: {sorter: false}
			 }});
	});	
	function sendAudit(key,obj){
		btnDisabledSetTrue();
		if(0 == $("input[name=wkKey]:checked").size()){
			$.fo.alert('请您选择提现数据后再进行提交！');	
			btnDisabledSetFalse();
			return false;
		}
		var auditRemark = $.trim($("#auditRemark").val());
		if(!s_validateStrLength(auditRemark,2,100)){
			$.fo.alert('批量操作备注最少2个字符,最大不超过50个汉字！');
			btnDisabledSetFalse();
			return false;
		}
		
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#orderForm").serialize() + "&auditStatus=" + key ;
		$.ajax({
			type: "POST",
			url: "${ctx}/withdrawAccountAduit.do?method=audit",
			data: pars,
			success: function(result) {
			 	var msg = eval('('+result+')');
				if(msg.isSuccess==true){
					$('#infoLoadingDiv').dialog('close');
					btnDisabledSetFalse();
					bankAccountQuery();
					$.fo.alert('操作成功！');
				}else{
					$('#infoLoadingDiv').dialog('close');
					alert(msg.sequenceId+'审核提交失败');	
					btnDisabledSetFalse();
				}
			}
		});
	}
	
	function sendDelay(key,obj){
		btnDisabledSetTrue();
		if(0 == $("input[name=wkKey]:checked").size()){
			$.fo.alert('请您选择提现数据后再进行提交！');	
			btnDisabledSetFalse();
			return false;
		}
		var auditRemark = $.trim($("#auditRemark").val());
		if(!s_validateStrLength(auditRemark,2,100)){
			$.fo.alert('批量操作备注最少2个字符,最大不超过50个汉字！');
			btnDisabledSetFalse();
			return false;
		}
		
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#orderForm").serialize() + "&auditStatus=" + key ;
		$.ajax({
			type: "POST",
			url: "${ctx}/withdrawAudit.do?method=delay",
			data: pars,
			success: function(result) {
			 	var msg = eval('('+result+')');
				if(msg.isSuccess==true){
					$('#infoLoadingDiv').dialog('close');
					btnDisabledSetFalse();
					bankAccountQuery();
					$.fo.alert('操作成功！');
				}else{
					$('#infoLoadingDiv').dialog('close');
					alert(msg.sequenceId+'审核滞留提交失败');	
					btnDisabledSetFalse();
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

		function showDetail(detailUrl,workflowKy){
			parent.addTabMenu('查看待审核出款数据详细',detailUrl,workflowKy+"audit");
		}

		function btnDisabledSetTrue(){
			$("#btn1").attr("disabled",true);
			$("#btn2").attr("disabled",true);
			$("#btn3").attr("disabled",true);
		}

		function btnDisabledSetFalse(){
			$("#btn1").attr("disabled",false);
			$("#btn2").attr("disabled",false);
			$("#btn3").attr("disabled",false);
		}
		
	</script>
</head>

<body>
	<form id="orderForm">
	<table id="userTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		<thead> 
			<tr>     
				<th>
					选择<br>
					<input type="checkbox" name="checkAll" id="checkAll" onclick="selectAll();" />全选/反选
				</th>     
				<th>会员号</th> 
				<th>卡号</th>
				<th>开户人</th>
				<th>申请时间</th>
				<th>审核状态</th>
				<th>操作</th>
			</tr> 
		</thead> 
		<tbody>
			<c:forEach items="${liquidateInfos}" var="info" varStatus = "personalStatus">
		<c:choose>
	       <c:when test="${personalStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>    
				<td>
				<input type="checkbox" name="wkKey" value="${info.liquidateId }" id="id" onclick="selectAllcheckBoxunchecked(this);" /> 
				<input type="hidden" name="" id="" value=""/>
				</td>     
				<td>${info.memberCode}</td>
				<td>${info.bankAcct}</td>        
				<td>${info.acctName}</td>
				<td><fmt:formatDate value="${info.createDate }" pattern="yyyyMMdd HH:mm:ss"/> </td>        
				<td>
					<c:choose>
						<c:when test="${info.auditStatus == 101 }">
							待审核
						</c:when>
						<c:when test="${info.auditStatus == 102 }">
							审核通过
						</c:when>
						<c:when test="${info.auditStatus == 103 }">
							审核拒绝
						</c:when>
						<c:when test="${info.auditStatus == 104 }">
							滞留
						</c:when>
					</c:choose>
				</td>
				<td>
					<c:choose>
						<c:when test="${not empty info.dbRelativePath }">
							<a href="${info.dbRelativePath }">下载授权书</a>
						</c:when>
						<c:otherwise>
							<a href="javascript:void(0);">未上传委托授权书</a>
						</c:otherwise>
					</c:choose>
					
				</td><!-- javascript:void(0); -->
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
	<li:pagination methodName="bankAccountQuery" pageBean="${page}" sytleName="black2"/>
	<table align="center">
		<tr>
			<td align="center">
					<input type="button" onclick="sendAudit(102)" name="submitBtn" id="btn1" value="批量审核通过" class="button2">
					<input type="button" onclick="sendAudit(103)" name="submitBtn" id="btn2" value="批量审核拒绝" class="button2">
					<input type="button" onclick="sendAudit(104)" name="submitBtn" id="btn3" value="批量滞留" class="button2"><!-- sendDelay(104) -->
				</td>
		</tr>
	</table>
</body>

