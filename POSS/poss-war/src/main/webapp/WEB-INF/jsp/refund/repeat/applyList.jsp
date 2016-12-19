<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ page import="java.math.BigDecimal"%>

<c:if test="${not empty err1}">
	<font color="red"><b>${err1 }</b></font>
</c:if>
<c:if test="${empty err1}">
	会员相关信息
	<table id="detailTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		<thead>
			<tr>
				<th>登录名</th>
				<th>会员号</th>
				<th>账户余额(元)</th>
				<th>会员类型</th>
				<th>账户类型</th>
				<th>会员姓名</th>
				<th>会员等级</th>
			</tr>
		</thead>
		<tbody>
			<tr>
				<td > ${memberInfo.userId} </td>
				<td > ${memberInfo.memberNo}</td>
				<td > <font color="red"><b><fmt:formatNumber value="${balance/1000 }" pattern="#,##0.00"/></b></font></td>
				<td > ${memberInfo.memberType}</td>
				<td > ${memberInfo.accountType}</td>
				<td > ${memberInfo.userName}</td>
				<td > ${memberInfo.levelCode}</td>
			</tr>
		</tbody>
	</table>
	 
	<form name="manyForm" method="post">
		<input type="hidden" name="startTime" value='<fmt:formatDate value="${webQueryRefundDTO.startTime}" type="date"/>' >
		<input type="hidden" name="endTime"  value='<fmt:formatDate value="${webQueryRefundDTO.endTime}" type="date"/>' >
		<input type="hidden" name="rechargeSeq" value="${webQueryRefundDTO.rechargeSeq }">
		<input type="hidden" name="balance" value="${balance }">
		<input type="hidden" name="acctCode" value="${acctCode }">
		<input type="hidden" name="userId" value="${memberInfo.userId}">
		<input type="hidden" name="account" value="${memberInfo.memberNo}">
		<input type="hidden" name="memberType" value="${memberInfo.memberType}">
		<input type="hidden" name="accountType" value="${memberInfo.accountType}">
		<input type="hidden" name="userName" value="${memberInfo.userName}">
		<input type="hidden" name="levelCode" value="${memberInfo.levelCode}">
	重复支付订单列表
	<table id="refundInfoTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
		<thead>
			<tr>
				<th>
					选择
					<input type="checkbox" name="allCheck" id="allCheck"> 
				</th>
				<th>序号</th>
				<th>商户订单号</th>
				<th>交易时间</th>
				<th>支付平台交易流水号</th>
				<th>充值流水号</th>
				<th>银行流水号</th>
				<th>交易金额</th>
				<th>银行名称</th>
				<th>订单状态</th>
				<th>退款类型</th>
				<th>备注</th>
			</tr>
		</thead>
		<tbody>
		<c:forEach items="${page.result}" var="rechargeDto" varStatus="status">
			<tr>
				<td >
					<c:if test="${rechargeDto.flag}">
					<input type="checkbox" name="ifRefund" value="${status.count-1}">
					</c:if>
					<input type="hidden" name="rechargeOrderSeq" value="${rechargeDto.rechargeOrderSeq}">
					<input type="hidden" name="rechargeTime" value='<fmt:formatDate value="${rechargeDto.rechargeTime}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>'>
					<input type="hidden" name="rechargeAmount" value="${rechargeDto.rechargeAmount}">
					<input type="hidden" name="rechargeBank" value="${rechargeDto.rechargeBank}">
					<input type="hidden" name="rechargeBankSeq" value="${rechargeDto.rechargeBankSeq}">
					<input type="hidden" name="rechargeBankOrder" value="${rechargeDto.rechargeBankOrder}">
					<input type="hidden" name="rechargeStatus" value="${rechargeDto.rechargeStatus}">
					<input type="hidden" name="applyMax" value="${rechargeDto.applyMax}">
					<input type="hidden" name="rechargeChannel" value="${rechargeDto.rechargeChannel}">
					<input type="hidden" name="depositTypeName" value="${rechargeDto.depositTypeName}">
					
				</td>
				<td ><c:out value="${status.count}" /></td>
				<td > ${rechargeDto.merchantOrderId} </td>
				<td > 
					<fmt:formatDate value="${rechargeDto.paymentDate}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>
				</td>
				<td > ${rechargeDto.tradeOrderNo} </td>
				<td > ${rechargeDto.rechargeOrderSeq} </td>
				<td > ${rechargeDto.rechargeBankOrder}</td>
				<td > 
					<input type="text"  readonly name="applyAmount" id="applyAmount" value='<fmt:formatNumber value="${rechargeDto.rechargeAmountLong/1000}" pattern="#,##0.00"/>'  />
					&nbsp;
				</td>
				<td > ${rechargeDto.rechargeBankName}</td>
				<td > 
					重复支付
				</td>
				<td>
					<c:if test="${rechargeDto.refundType == 1}">充值退款</c:if><c:if test="${rechargeDto.refundType == 2}">网关交易退款</c:if>
				</td>
				<td > 
					<input type="text" style="height: 50" name="applyReason" value="重复支付申请充退"  />
				</td>
			</tr>
		</c:forEach>
		</tbody>
	</table>
	</form>
	
	<li:pagination methodName="submitByHref" pageBean="${page}" sytleName="black2"/>

	<table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
		<tr  align="center" >
		    <td align="center" colspan="3">
		      <input type="button" onclick="haderRefundApply();" class="button4" value="申请充退">
		    </td>
		 </tr>
	</table>
</c:if>




<script type="text/javascript"><!--
	$(function(){

		$("#refundInfoTable").tablesorter({
			 headers: {
		 		0: {sorter: false},
		 		1: {sorter: false},
		 		9: {sorter: false},
		 		10: {sorter: false},
		 		11: {sorter: false},
		 		12: {sorter: false}
		 	}
		});   
		 
	    $("#allCheck").click(function(){ 
	    	$(':checkbox[name=ifRefund]').each(function() {
				this.checked = $("#allCheck").attr("checked");
				var currentNode=$(this).parent().parent().find("select,:text,:hidden");
				currentNode.attr("disabled",!$(this).attr("checked"));
			 });
	    }); 
	    
	    $('input[type=checkbox]').click(function(){
			var currentNode=$(this).parent().parent().find("select,:text,:hidden");
			currentNode.attr("disabled",!$(this).attr("checked"));
		});
	
	    $('#refundInfoTable').find('select,:text,:hidden').attr('disabled','disabled');
		$('#refundInfoTable').find(':checkbox').attr('checked',false);
		$(':text[name=applyAmount]').attr('title','输入的充退金额只能保留两位小数(##.00)!');
	}); 
	
	function haderRefundApply(){
		if(0 == $(":checkbox[name=ifRefund]:checked").size()){
			alert("请您先选择充值记录后再进行充退申请!");
			return false;
		}

		var flag = true;
		var allAmount = 0;
		$(':checkbox[name=ifRefund]:checked').each(function(i){
			$(this).parent().parent().find(":text[name=applyAmount]").each(function(j){
					allAmount = parseFloat(allAmount)+parseFloat(this.value.replace(/[,'"]/g,""));
				});
			$(this).parent().parent().find(":text[name=applyReason]").each(function(j){
				if(null==this.value || 0 == this.value.length){
					alert("请您输入申请理由后再进行充退申请!");
					flag = false;
					return flag;
				}
				if(this.value.length > 50){
					alert("申请理由不得超过50个汉字!");
					flag = false;
					return flag;
				}
			});
		});

		var balance = '<fmt:formatNumber value="${balance/1000 }" pattern="#,##0.00"/>';
		balance = balance.replace(/[,'"]/g,"");
		if(parseFloat(balance) < parseFloat(allAmount)){
			alert('申请充退总金额大于余额!');
			flag = false;
			return flag;
		}
		if(!flag){
			return false;
		}
		document.manyForm.action="refund.manage.do?method=handerRepeatApply";
		document.manyForm.submit();
	}

--></script>