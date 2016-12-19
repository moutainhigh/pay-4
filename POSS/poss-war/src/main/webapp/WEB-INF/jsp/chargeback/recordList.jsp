<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="li_new" uri="page"%>

<form id="orderForm">
<table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
		<tr>
			<th nowrap="nowrap" align="center">
				选择<br>
				<input type="checkbox" name="checkAll" id="checkAll" onclick="selectAll();" />全选/反选
			</th>
			<th>拒付订单号</th>
			<th>商户号</th>
			<th>会员号</th>
			<th>商户名称</th>
			<th>交易日期</th>
			<th>网关流水号</th>
			<th>渠道流水号</th>
			<th nowrap="nowrap">录入日期</th>
			<th nowrap="nowrap">类别</th>
			<th>原交易金额</th>
			<th>原交易币种</th>
			<th>拒付金额</th>
			<th>拒付币种</th>
			<th nowrap="nowrap">状态</th>
			<th nowrap="nowrap">操作</th>
		</tr>
	</thead>
	
	<tbody>
		<c:forEach items="${list}" var="dto" varStatus="index">
			<tr>
				<td nowrap="nowrap">
					<input type="checkbox" name="orderId" value="${dto.orderId}" id="id" name="choose" onclick="selectAllcheckBoxunchecked(this);" /> 
					<input type="hidden" name="${dto.orderId}" id="${dto.orderId}" value="${orderDto.status}"/>
				</td> 
				<td>${dto.orderId }</td>
				<td>
					${dto.merchantCode}
				</td>
				<td>
					${dto.memberCode}
				</td>
				<td>
					${dto.merchantName}
				</td>
				<td nowrap="nowrap">
					<date:date value="${dto.tradeDate}"/>
				</td>
				<td>
					${dto.tradeOrderNo}
				</td>
				<td>
					${dto.channelOrderId}
				</td>
				<td nowrap="nowrap">
					<date:date value="${dto.createDate}"/>
				</td>
				<td nowrap="nowrap">
					<c:if test="${dto.cpType == 1}">内部调查单</c:if>
					<c:if test="${dto.cpType == 2}">银行调查单</c:if>
					<c:if test="${dto.cpType == 3}">拒付单</c:if>
				</td>
				<td>
					${dto.tradeAmount/1000}
				</td>
				<td>
					${dto.currencyCode}
				</td>
				<td>
					${dto.settlementAmount/1000}
				</td>
				<td>
					${dto.settlementCurrencyCode}
				</td>
				<td nowrap="nowrap">
					<c:if test="${dto.status == 0}">初始</c:if>
					<c:if test="${dto.cpType == 1}">
						<c:if test="${dto.status == 1}">内部调查单审核通过</c:if>
						<c:if test="${dto.status == 2}">内部调查单审核拒绝</c:if>
					</c:if>
					<c:if test="${dto.cpType == 2}">
						<c:if test="${dto.status == 1}">银行调查单审核通过</c:if>
						<c:if test="${dto.status == 2}">银行调查单审核拒绝</c:if>
					</c:if>
					<c:if test="${dto.cpType == 3}">
						<c:if test="${dto.status == 1}">拒付审核通过</c:if>
						<c:if test="${dto.status == 2}">拒付审核拒绝</c:if>
					</c:if>
				</td>
				<td nowrap="nowrap">
					<c:if test="${dto.status == 0}">
					 <a href="${ctx}/chargeBackAudit.do?method=auditInit&orderId=${dto.orderId}">审核</a>
					</c:if>
					<c:if test="${dto.cpType == 1 && dto.status == 1}">
					 <a href="${ctx}/chargeBackAudit.do?method=auditInit&orderId=${dto.orderId}">审核</a>
					</c:if>
					<c:if test="${dto.cpType == 2 && dto.status == 1}">
					 <a href="${ctx}/chargeBackAudit.do?method=auditInit&orderId=${dto.orderId}">审核</a>
					</c:if>
				</td>
			</c:forEach>
	</tbody>
</table>
批量操作备注：<textarea rows="4" cols="45" name="auditMsg" id="auditMsg"></textarea>
</form>
<li:pagination methodName="withdrawQuery" pageBean="${page}" sytleName="black2"/>
<table align="center">
		<tr>
			<td align="center">
					<input type="button" onclick="sendAudit(1)" name="submitBtn" id="btn1" value="批量审核通过" class="button4">
					<input type="button" onclick="sendAudit(2)" name="submitBtn" id="btn2" value="批量审核拒绝" class="button4">
					<font color="red"><i>注：批量拒付类别请选择上面查询条件的拒付类别</i></font>
				</td>
		</tr>
	</table>
 <script type="text/javascript">
	 $(document).ready(function(){
		 $("#sorterTable").tablesorter({
			 headers: {
				0: {sorter: false},
				7: {sorter: false}
			}
		});
	 }); 

	//id的全选或全部取消
	//id的全选或全部取消
	function selectAll() {
		if($("#checkAll").attr("checked")){
			$("input[name='orderId']").each(function(){
				this.checked = true;
			});
		} else {
			$("input[name='orderId']").each(function() {
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
	
	function sendAudit(key,obj){
		btnDisabledSetTrue();
		if(0 == $("input[name=orderId]:checked").size()){
			$.fo.alert('请您选择数据后再进行提交！');	
			btnDisabledSetFalse();
			return false;
		}
		var auditRemark = $.trim($("#auditMsg").val());
		if(!s_validateStrLength(auditRemark,2,100)){
			$.fo.alert('批量操作备注最少2个字符,最大不超过50个汉字！');
			btnDisabledSetFalse();
			return false;
		}
		
		$('#infoLoadingDiv').dialog('open');
		var cpType = $('#cpType').val();
		var pars = $("#orderForm").serialize() + "&status=" + key + "&cpType=" + cpType;
		$.ajax({
			type: "POST",
			url: "${ctx}/chargeBackImport.do?method=audit",
			data: pars,
			success: function(result) {
				$('#infoLoadingDiv').dialog('close');
				if(result == 1){
					btnDisabledSetFalse();
					$.fo.alert('操作成功！');
				}else{
					alert('审核失败:' + result);	
					btnDisabledSetFalse();
				}
			}
		});
	}
	
	function btnDisabledSetTrue(){
		$("#btn1").attr("disabled",true);
		$("#btn2").attr("disabled",true);
	}

	function btnDisabledSetFalse(){
		$("#btn1").attr("disabled",false);
		$("#btn2").attr("disabled",false);
	}
</script>