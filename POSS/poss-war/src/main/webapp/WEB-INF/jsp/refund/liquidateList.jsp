<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
 
 <form action="" method="post" name="detailForm">
 	<input type="hidden" name="refundSeq"/>
 	<input type="hidden" name="workorderKy"/>
 </form>
 
<c:if test="${not empty err1}">
	<font color="red"><b>${err1 }</b></font>
</c:if>
<c:if test="${empty err1}">
<form name="manyForm" method="post">
<table id="detailTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead>
		<tr>
			<th><input type="checkbox" name="allRefundInfos" id="allRefundInfos" onclick="checkAll(this);"/>选择</th>
			<th>会员号</th>
			<th>会员姓名</th>
			<th>账户类型</th>
			<th>充退流水号</th>
			<th>充退金额（元）</th>
			<th>充退时间</th>
			<th>复核状态</th>
			<th>详情</th>
		</tr>
	</thead>
	<tbody>
	<c:forEach items="${page.result}" var="mDto" varStatus="status">
		<tr>
			<td >
				<input type="hidden" name="batchStatusTemp" id="${mDto.refundWorkorder.workorderKy}"
						value="${mDto.refundWorkorder.batchStatus}"/>
				<input type="checkbox" name="refundInfos" 
					value="${mDto.refundWorkorder.workorderKy},${mDto.refundWorkorder.workflowKy},${mDto.refundWorkorder.status}"/>
				
			</td>
			<td >${mDto.refundOrderM.memberCode}</td>
			<td >${mDto.refundOrderM.memberName}</td>
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
			<td > 
				<fmt:formatDate value="${mDto.refundOrderM.applyTime}" type="date" pattern="yyyy-MM-dd HH:mm:ss"/>
			</td>
			<td>
				<c:choose>
					<c:when test="${'4' eq mDto.refundWorkorder.status}">
						复核同意
					</c:when>
					<c:when test="${'5' eq mDto.refundWorkorder.status}">
						复核拒绝
					</c:when>
					<c:otherwise>
						${mDto.refundWorkorder.status}			
					</c:otherwise>
				</c:choose>
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
</form>

<li:pagination methodName="submitByHref" pageBean="${page}" sytleName="black2"/>

<br/>
<center>
  <input type="button" onclick="handerRefundLiquidateBatch(6,this);" class="button2" value="拒绝"/>
  <input type="button" onclick="handerRefundLiquidateBatch(0,this);" class="button2" value="退回"/>
</center>

</c:if>

<script type="text/javascript">
function handerRefundLiquidateBatch(val,obj,pageNo,totalCount,pageSize){
	if($("input[name='refundInfos']:checked").size() == 0){
		alert("请您先选择充退记录再进行提交!");
		return false;
	}

	var array;
	var flag = true;
	var batchStatus = "";
	$("input[name='refundInfos']:checked").each(function(index){
		array = this.value.split(",");
		batchStatus = $("#" + array[0]).val();
		if("1" == batchStatus){
			alert("该记录已出批次,不能再进行清算【" + obj.value + "】,请您确认后再操作!");
			flag = false;
			return false;
		}
		if(0 != val && "4" == array[2]){
			alert("充退记录状态为【复核同意】,不能再进行清算【拒绝】,请您确认后再操作!");
			flag = false;
			return false;
		}
	});
	if(!flag){
		return false;
	}
	
	if(confirm("您是否确认【" + obj.value + "】提交?")){

		var handleType = "";
		if(6 == val){
			handleType = "flowEnd";
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
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount +
					"&handleDatas=" + params + "&handleStatus=" + val + "&handleType=" + handleType + "&pageSize=" + pageSize
					+"&startTime=" +'<fmt:formatDate value="${webQueryRefundDTO.startTime}" type="date"/>'
					+"&endTime=" +'<fmt:formatDate value="${webQueryRefundDTO.endTime}" type="date"/>';
		$.ajax({
			type: "POST",
			url: "refund.liquidate.do?method=handerRefundLiquidateBatch",
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
	document.detailForm.action = "refund.liquidate.do?method=initLiquidateDetail";
	document.detailForm.submit();
}

function checkAll(obj){
	$("input[name='refundInfos']").each(function(){
        $(this).attr('checked',obj.checked);
    });
}
</script>
