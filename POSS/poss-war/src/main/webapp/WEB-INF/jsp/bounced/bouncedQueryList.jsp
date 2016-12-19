<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
</script>
<div id="resultListDiv" class="listFence"  style="display: none">
	<form action="" id="detailForm">
	</form>
</div>

<div id="infoLoadingDiv" title="加载信息"
	style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中,
	请稍候...
</div>
<body>
<table id="userTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead> 
		<tr >
		<th><input type="checkbox" name="allRefundInfos" id="allRefundInfos" onclick="checkAll(this);"/>选择</th>
			<th >批次号</th>
			<th >拒付订单号</th>
			<th >商户会员号</th>
			<th >渠道订单号</th>
			<th >档案号</th>
			<th >交易日期</th>
			<th >交易金额</th>
			<th >支付金额</th>
			<th >拒付类型</th>
			<th >登记时间</th>
			<th >拒付金额</th>
			<th >拒付币种</th>
			<th >拒付状态</th>
			<th >最晚回复日期</th>
			<th >拒付原因</th>
			<th >备注</th>
			<th >操作</th>
		</tr>
	</thead>
	<tbody> 
		<c:forEach items="${result}" var="dto" varStatus="status">
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
					value="${dto.orderId},${dto.status}"/>
			</td>		
				<td>${dto.batchNo}</td>
				<td>${dto.orderId}</td>
				<td>${dto.memberCode}</td>
				<td>${dto.channelOrderId}</td>
				<td>${dto.refNo}</td>
				<td><date:date value="${dto.tradeDate}"/></td>
				<td><fmt:formatNumber value="${dto.tradeAmount/1000}"
						pattern="#,##0.00" />${dto.tranCurrencyCode}</td>
				<td><fmt:formatNumber value="${dto.payAmount/1000}"
						pattern="#,##0.00" />${dto.currencyCode}</td>
				<td>
					<c:choose>
					<c:when test="${dto.cpType=='0'}">拒付</c:when>
					<c:when test="${dto.cpType=='1'}">银行调单</c:when>
					<c:when test="${dto.cpType=='2'}">内部调单</c:when>
					</c:choose>
				</td>
				<td><date:date value="${dto.createDate}"/></td>
				<td><fmt:formatNumber value="${dto.bouncedAmount/1000}"
						pattern="#,##0.00" /></td>
				<td>${dto.cpCurrencyCode}</td>
				<td>
					<c:choose>
					<c:when test="${dto.status=='0'}">未处理</c:when>
					<c:when test="${dto.status=='1'}">已上传资料</c:when>
					<c:when test="${dto.status=='2'}">已递交银行</c:when>
					<c:when test="${dto.status=='3'}">申诉失败待复核</c:when>
					<c:when test="${dto.status=='4'}">申诉成功待复核</c:when>
					<c:when test="${dto.status=='5'}">申诉失败</c:when>
					<c:when test="${dto.status=='6'}">申诉成功</c:when>
					<c:when test="${dto.status=='7'}">不申诉</c:when>
					</c:choose>
				</td>
				<td><date:date value="${dto.latestAnswerDate}"/></td>
				<td>${dto.reasonCode}</td>			
				<td>${dto.remark}</td>			
				<td>
					<input id="detail" type="button" onclick="queryDetail('${dto.orderId}');" value="查 看" class="button2" />
				</td>
			</tr>
		</c:forEach>
	</tbody>
</table>
<br/>

<table class="" align="center" border="0" cellpadding="0" cellspacing="1">
		<tr>
		<td align="right">批量操作备注：</td>
		<td><textarea rows="5" cols="60" name="remark" id="remark" title="最多只能输入50个汉字或100个字符"></textarea></td>
		</tr>
		
	</table>
</form>
<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>
<br/>
<center>
   <input type="button" onclick="handerAuditBatch(2,this);" class="button2" value="已递交银行"/>
   <input type="button" onclick="handerAuditBatch(4,this);" class="button2" value="申诉成功"/>
   <input type="button" onclick="handerAuditBatch(3,this);" class="button2" value="申诉失败"/>
 </center>
</body>

<script type="text/javascript" language="javascript">
function query(pageNo, totalCount, pageSize) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#initForm").serialize() + "&pageNo=" + pageNo
			+ "&totalCount=" + totalCount + "&pageSize=" + pageSize;
	$.ajax({
		type : "POST",
		url : "bounced-register.do?method=bouncedQueryList",
		data : pars,
		success : function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
	//document.getElementById('initForm').submit();
}
function checkAll(obj){
	$("input[name='refundInfos']").each(function(){
        $(this).attr('checked',obj.checked);
    });
}
function handerAuditBatch(status,obj,pageNo,totalCount,pageSize){
	if($("input[name='refundInfos']:checked").size() == 0){
		alert("请您先选择拒付记录再进行提交!");
		return false;
	}
	var flag = true;
	if(2 == status){
		$("input[name='refundInfos']:checked").each(function(index){
			array = this.value.split(",");
			if('1' != array[1]){
				alert("资料未上传不能递交银行!");
				flag = false;
				return false;
			}
		});
	}
	if(4 == status){
		$("input[name='refundInfos']:checked").each(function(index){
			array = this.value.split(",");
			if('2' != array[1]){
				alert("资料未递交银行!");
				flag = false;
				return false;
			}
		});
	}
	if(3 == status){
		$("input[name='refundInfos']:checked").each(function(index){
			array = this.value.split(",");
			if('2' != array[1]){
				alert("资料未递交银行!");
				flag = false;
				return false;
			}
		});
	}
	if(!flag){
		return false;
	}
	if(confirm("您是否确定【" + obj.value + "】提交?")){
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
		
		var remark = $("#remark").val();
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount +
					"&handleDatas=" + params + "&status=" + status  
					+ "&pageSize=" + pageSize
					+"&remark=" + remark;
		$.ajax({
			type: "POST",
			url: "bounced-register.do?method=handerAuditBatch",
			data: pars,
			success: function(result) {
				alert("处理成功！");
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		}); 
	}else{
		return false;
	}
}
function queryDetail(orderId){
	 var pars="&orderId=" + orderId;
	$.ajax({
		type: "POST",
		url: "bounced-register.do?method=queryDetail",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#queryDiv').html(result);
		}
	});  
	//document.detailForm.action="bounced-register.do?method=queryDetail&orderId=" + orderId;
	//document.detailForm.submit();
} 


</script>