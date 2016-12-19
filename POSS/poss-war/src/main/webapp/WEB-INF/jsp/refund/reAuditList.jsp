<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
 
 <form action="" method="post" name="detailForm">
 	<input type="hidden" name="refundSeq"/>
 	<input type="hidden" name="workorderKy"/>
 </form>
 
<form name="manyForm" method="post">
<table id="detailTable" width="85%" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
		<tr>
			<th><input type="checkbox" name="allRefundInfos" id="allRefundInfos" onclick="checkAll(this);"/>选择</th>
			<th>会员号</th>
			<th>会员姓名</th>
			<th>商户名称</th>
			<th>网关订单号</th>
			<th>银行渠道</th>
			<th>账户类型</th>
			<th>充退流水号</th>
			<th>充退金额</th>
			<th>币种</th>
			<th>充退时间</th>
			<th>审核状态</th>
			<th>退款失败原因</th>
			<th>详情</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${page.result}" var="mDto" varStatus="status">
		<c:choose>
	       <c:when test="${status.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
			<td >
				<input type="checkbox" name="refundInfos" 
					value="${mDto.refundWorkorder.workorderKy},${mDto.refundWorkorder.workflowKy},${mDto.refundWorkorder.status},${mDto.refundOrderM.tradeOrderNo},${mDto.refundWorkorder.remark}"/>
			</td>
			<td >${mDto.refundOrderM.memberCode}</td>
			<td >${mDto.refundOrderM.memberName}</td>
			<td >${mDto.refundOrderM.payeeName}
			</td>
			<td>${mDto.refundOrderM.tradeOrderNo}</td>
			<td>
			<c:choose>
					<c:when test="${mDto.refundOrderM.orgCode=='0000000'}">测试通道</c:when>
					<c:when test="${mDto.refundOrderM.orgCode=='10076001'}">中银卡司</c:when>
					<c:when test="${mDto.refundOrderM.orgCode=='10075001'}">CREDORAX</c:when>
					<c:when test="${mDto.refundOrderM.orgCode=='10003001'}">中国银行</c:when>
					<c:when test="${mDto.refundOrderM.orgCode=='10002001' }">农业银行</c:when>
					<c:when test="${mDto.refundOrderM.orgCode=='10079001' }">中银MOTO</c:when>
					<c:when test="${mDto.refundOrderM.orgCode=='10080001' }">中银MIGS</c:when>
					<c:when test="${mDto.refundOrderM.orgCode=='10078001' }">农行CTV</c:when>
					<c:when test="${mDto.refundOrderM.orgCode=='10077001' }">Adyen</c:when>
					<c:when test="${mDto.refundOrderM.orgCode=='10077002' }">Belto</c:when>
					<c:when test="${mDto.refundOrderM.orgCode=='10077003' }">Cashu</c:when>
					<c:when test="${mDto.refundOrderM.orgCode=='10081001' }">ct_boleto</c:when>
					<c:when test="${mDto.refundOrderM.orgCode=='10081002' }">ct_safetyPay</c:when>
					<c:when test="${mDto.refundOrderM.orgCode=='10081003' }">ct_DebitCard</c:when>
					<c:when test="${mDto.refundOrderM.orgCode=='10081004' }">ct_sofortbanking</c:when>
					<c:when test="${mDto.refundOrderM.orgCode=='10081005' }">ct_eps</c:when>
					<c:when test="${mDto.refundOrderM.orgCode=='10081006' }">ct_giropay</c:when>
					<c:when test="${mDto.refundOrderM.orgCode=='10081007' }">ct_pagDebitCard</c:when>
					<c:when test="${mDto.refundOrderM.orgCode=='10081008' }">ct_PagBrasilOTF</c:when>
					<c:when test="${mDto.refundOrderM.orgCode=='10081009' }">ct_poli</c:when>
					<c:when test="${mDto.refundOrderM.orgCode=='10081010' }">ct_przelewy24</c:when>
					<c:when test="${mDto.refundOrderM.orgCode=='10081011' }">ct_qiwi</c:when>
					<c:when test="${mDto.refundOrderM.orgCode=='10081012' }">ct_sepa</c:when>
					<c:when test="${mDto.refundOrderM.orgCode=='10081013' }">ct_teringso</c:when>
					<c:when test="${mDto.refundOrderM.orgCode=='10081014' }">ct_trustPay</c:when>
					<c:when test="${mDto.refundOrderM.orgCode=='10081015' }">ct_ideal</c:when>
					<c:when test="${mDto.refundOrderM.orgCode=='10081016' }">前海万融</c:when>
					<c:otherwise>未知机构</c:otherwise>
			</c:choose></td>
			<td >
				<c:choose>
					<c:when test="${'10' eq mDto.refundOrderM.memberAccType}">
						人民币
					</c:when>
					<c:otherwise>
						${mDto.refundOrderM.memberAccType}
					</c:otherwise>
				</c:choose>
			</td>
			<td>${mDto.refundOrderM.orderKy}</td>
			<td > 
				<fmt:formatNumber value="${mDto.refundOrderM.applyAmount}" pattern="#,##0.00"/>&nbsp;
			</td>
			<td>${mDto.refundOrderM.currencyCode}</td>
			<td > 
				<fmt:formatDate value="${mDto.refundOrderM.applyTime}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>
			</td>
			<td>
				<c:choose>
					<c:when test="${'1' eq mDto.refundWorkorder.status}">
						审核通过
					</c:when>
					<c:when test="${'2' eq mDto.refundWorkorder.status}">
						审核拒绝
					</c:when>
					<c:when test="${'0' eq mDto.refundWorkorder.status}">
						初始
					</c:when>
					<c:otherwise>
						${mDto.refundWorkorder.status}
					</c:otherwise>
				</c:choose>
			</td>
			<td id="remark">
				${mDto.refundWorkorder.remark}
			</td>
			<td >
				<a href="#" onclick="queryDetail('${mDto.refundOrderM.orderKy}','${mDto.refundWorkorder.workorderKy}');">
					查看
				</a>
			</td>
		</tr>
	</c:forEach>
	</tbody>
</table>
<br/>
<table class="" align="center" border="0" cellpadding="0" cellspacing="1">
		<tr>
		<td align="right">批量操作备注：</td>
		<td><textarea rows="5" cols="60" name="auditRefundRemark" id="auditRefundRemark" title="最多只能输入50个汉字或100个字符"></textarea></td>
		</tr>
		
	</table>
</form>

<li:pagination methodName="submitByHref" pageBean="${page}" sytleName="black2"/>

<br/>
<center>
    <input type="button" onclick="handerRefundReAuditBatch(4,this);" class="button2" value="同意初审"/>
    <input type="button" onclick="handerRefundReAuditBatch(5,this);" class="button2" value="驳回初审"/>
</center>	 

<script type="text/javascript">
function handerRefundReAuditBatch(val,obj,pageNo,totalCount,pageSize){
	if($("input[name='refundInfos']:checked").size() == 0){
		alert("请您先选择充退记录再进行提交!");
		return false;
	}
	
	if(confirm("您是否确定【" + obj.value + "】提交?")){
		var handleType = "";
		if(4 == val){
			handleType = "agree";
		}else{
			handleType = "rollback";
		}
		//组装参数
		var params = "";
		var size = $("input[name='refundInfos']:checked").size();
		$("input[name='refundInfos']:checked").each(function(index){
				if(index < (size - 1)){
					params += this.value + "##";
				}else{
					params += this.value;
				}
			});
		var remark = $("#auditRefundRemark").val();
		if(val==5){
		  var	param=params.split("##");
			for(var i=0;i<param.length;i++){
				var	remarks=param[i].split(",");
					if($.trim(remarks[remarks.length-1]) == "订单已拒付"){
						alert("有存在已拒付的退款订单，不允许退回");
						return ;
					}
			} 
		}
		var para="";
		 var param=params.split("##");
		 for(var i=0;i<param.length;i++){
			 	var remarks=param[i].split(",");
      				remarks.pop();
      			for(var y=0;y<remarks.length;y++){
					if(y==remarks.length-1){
						para +=remarks[y];
					}else{
						para +=remarks[y]+",";
					}
				}
				if(i==param.length-1){
	      			para;
				}else{
					para+="##";
				}
		 } 
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount +
					"&handleDatas=" + para + "&handlestatus=" + val + "&handleType=" + handleType + "&pageSize=" + pageSize
					+"&startTime=" +'<fmt:formatDate value="${webQueryRefundDTO.startTime}" type="date"/>'
					+"&endTime=" +'<fmt:formatDate value="${webQueryRefundDTO.endTime}" type="date"/>'
					+"&auditRefundRemark=" +remark;
		$.ajax({
			type: "POST",
			url: "chargeBackQuery.do?method=handerRefundReAuditBatch",
			data: pars,
			success: function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
	}else{
		return false;
	}
}

function queryDetail(refundMKy,workorderKy){
	$("input[name='refundSeq']").val(refundMKy);
	$("input[name='workorderKy']").val(workorderKy);
	document.detailForm.action="refund.reaudit.do?method=initReAuditDetail";
	document.detailForm.submit();
}

function checkAll(obj){
	$("input[name='refundInfos']").each(function(){
        $(this).attr('checked',obj.checked);
    });
}
</script>
