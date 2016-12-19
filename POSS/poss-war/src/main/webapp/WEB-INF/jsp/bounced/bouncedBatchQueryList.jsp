<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
function closewin() {
	$('#resultListDiv').empty();
	$('#resultListDiv').dialog("close");
}
</script>
<div id="resultListDiv" class="listFence"  style="display: none">
	<form action="" id="channelCurrencyForm" name="channelCurrencyForm">
	<table  id="editChannelTable" class="tablesorter" width="50%" height="50%" border="0" cellpadding="0" cellspacing="1" >
	<tr><td>登记成功已匹配${status1Count}笔</td></tr>
	<tr><td>登记异常无匹配${status0Count}笔</td></tr>
	<tr><td>登记异常退款中${status2Count}笔</td></tr>
	<tr><td>登记异常匹配多笔${status3Count}笔</td></tr>
	<tr><td>登记异常未清算${status4Count}笔</td></tr>
	<tr><td>登记异常匹配异常${status5Count}笔</td></tr>
	<input id="close" type="button" onclick="javascript:closewin();" value="确定" class="button2" />
	</table>
	</form>
</div>

<div id="infoLoadingDiv" title="加载信息"
	style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中,
	请稍候...
</div>
<body>
<form action="bounced-register.do" enctype="multipart/form-data" method="post" id="batchForm" name="batchForm">
<table id="userTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead> 
		<tr >
			<th><input type="checkbox" name="allRefundInfos" id="allRefundInfos" onclick="checkAll(this);"/>选择</th>
			<th >批次号</th>
			<th >渠道订单号</th>
			<th >档案号</th>
			<th >银行卡号</th>
			<th >授权码</th>
			<th >交易日期</th>
			<th >拒付类型</th>
			<th >已退金额</th>
			<th >退款中金额</th>
			<th >已拒付金额</th>
			<th >可拒付金额</th>
			<th >拒付中金额</th>
			<th >匹配状态</th>
			<th >拒付金额</th>
			<th >登记时间</th>
			<th >登记状态</th>
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
				<input name="partnerId" type="hidden"  value="${dto.partnerId}">
				<input name="tradeOrderNo" type="hidden"  value="${dto.tradeOrderNo}">
				<td >
				<input type="checkbox" name="refundInfos" 
					value="${dto.id}"/>
				</td>
				<td>${dto.batchNo}</td>
				<td>${dto.channelOrderNo}</td>
				<td>${dto.refNo}</td>
				<td>${dto.cardholderCardno}</td>
				<td>${dto.authorisation}</td>
				<td><date:date value="${dto.tranDate}"/></td>
				<td>
					<c:choose>
					<c:when test="${dto.bouncedType=='0'}">拒付</c:when>
					<c:when test="${dto.bouncedType=='1'}">银行调单</c:when>
					<c:when test="${dto.bouncedType=='2'}">内部调单</c:when>
					</c:choose>
				</td>
				<td><fmt:formatNumber value="${dto.overRefundAmount/1000}"
						pattern="#,##0.00" /></td>
				<td><fmt:formatNumber value="${dto.doingRefundAmount/1000}"
						pattern="#,##0.00" /></td>
				<td><fmt:formatNumber value="${dto.overBouncedAmount/1000}"
						pattern="#,##0.00" /></td>
				<td><fmt:formatNumber value="${dto.canBouncedAmount/1000}"
						pattern="#,##0.00" /></td>
				<td><fmt:formatNumber value="${dto.doingBouncedAmount/1000}"
						pattern="#,##0.00" /></td>		
				<td>
					<c:choose>
					<c:when test="${dto.status=='0'}">无匹配</c:when>
					<c:when test="${dto.status=='1'}">已匹配</c:when>
					<c:when test="${dto.status=='2'}">退款中</c:when>
					<c:when test="${dto.status=='3'}">匹配多笔</c:when>
					<c:when test="${dto.status=='4'}">未清算</c:when>
					<c:when test="${dto.status=='5'}">匹配异常</c:when>
					<c:when test="${dto.status=='6'}">可删除</c:when>
					</c:choose>
				</td>
				<td><fmt:formatNumber value="${dto.bankAmount/1000}"
						pattern="#,##0.00" /></td>
				<td><date:date value="${dto.createDate}"/></td>	
				<td>
					<c:choose>
					<c:when test="${dto.registerFlag=='0'}">未登记</c:when>
					<c:when test="${dto.registerFlag=='1'}">已登记</c:when>
					<c:when test="${dto.registerFlag=='2'}">已删除</c:when>
					</c:choose>
				</td>
				<td>${dto.remark}</td>			
				<td>
					<c:choose>
					<c:when test="${dto.registerFlag=='1'}">
					<input id="detail" type="button" onclick="javascript:detail1('${dto.id}');" value="查看" class="button2" />
					</c:when>
					<c:when test="${dto.status=='0' || dto.status=='1'}">
					<input id="delete1" type="button" onclick="javascript:delolictr2(this);" value="删除" class="button2" />
					</c:when>
					<c:when test="${dto.status=='2' && dto.doingRefundAmount>0}">退款中</c:when>
					<c:when test="${dto.status=='4'}">未清算或清算失败</c:when>
					<c:when test="${dto.status=='3' || dto.status=='5' || dto.doingRefundAmount==0}">
				<input id="delete2" type="button" onclick="javascript:delolictr1(this);" value="删除" class="button2" />
				<input id="download" type="button" onclick="javascript:register(this,'${dto.orgCode}','${dto.doingRefundAmount}','${dto.settlementFlg}',
				'${dto.payAmount}','${dto.transferRate}','${dto.transferCurrencyCode}',
				'${dto.cardOrg}','${dto.orderAmount}'
				,'${dto.settlementCurrencyCode}','${dto.settlementRate}'
				,'${dto.orderId}','${dto.currencyCode}','${dto.doingBouncedAmount}',
				'${dto.floatValue}','${dto.remark}','${dto.overBouncedAmount}','${dto.assureSettlementFlg}'
				,'${dto.merchantCode}','${dto.checkFlag}','${dto.bouncedRemark}');" value="登记" class="button2" />
			    </c:when>
					</c:choose>
				</td>
			</tr>
		</c:forEach>
		<tr>
		<center>
  		 
 		</center>
 		</tr>
	</tbody>
</table>
</form>
</body>
<p style="text-align:center;"><input type="button" onclick="handerLoadBatch(this);" class="button2" value="批量登记"/>
   		<input type="button" onclick="handerDeleteBatch(0,this);" class="button2" value="批量删除"/></p>
<li:pagination methodName="query" pageBean="${page}" sytleName="black2"/>
<script type="text/javascript" language="javascript">
function detail1(id){
	 var pars="&id=" + id;
	$.ajax({
		type: "POST",
		url: "bounced-register.do?method=bouncedResultDetail",
		data: pars,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#queryDiv').html(result);
		}
	});  
	//document.detailForm.action="bounced-register.do?method=queryDetail&orderId=" + orderId;
	//document.detailForm.submit();
} 
function checkAll(obj){
	$("input[name='refundInfos']").each(function(){
        $(this).attr('checked',obj.checked);
    });
}
function handerLoadBatch(obj,pageNo,totalCount,pageSize){
	if($("input[name='refundInfos']:checked").size() == 0){
		alert("请您先选择拒付记录再进行提交!");
		return false;
	}
	var flag = true;
	if(confirm("您是否确定批量登记?")){
		//组装参数
		var params = "";
		var size = $("input[name='refundInfos']:checked").size();
		$("input[name='refundInfos']:checked").each(function(index){
				if(index < (size - 1)){
					params += this.value + "@@";
				}else{
					params += this.value;
				}
			});
		
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount +
					"&handleDatas=" + params+ "&pageSize=" + pageSize;
		document.batchForm.action="bounced-register.do?method=handerLoadBatchApp"+pars;
		document.batchForm.submit();
		/*  $.ajax({
			type: "POST",
			url: "bounced-register.do?method=handerLoadBatchApp",
			data: pars,
			success: function(result) {
				alert("批量处理成功！");
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});   */
	}else{
		return false;
	}
}
function handerDeleteBatch(obj,pageNo,totalCount,pageSize){
	if($("input[name='refundInfos']:checked").size() == 0){
		alert("请您先选择拒付记录再进行提交!");
		return false;
	}
	var flag = true;
	if(confirm("您是否确定批量删除?")){
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
					"&handleDatas=" + params+ "&pageSize=" + pageSize;
		$.ajax({
			type: "POST",
			url: "bounced-register.do?method=handerDeleteBatchApp",
			data: pars,
			success: function(result) {
				alert("批量处理成功！");
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		}); 
	}else{
		return false;
	}
}
function query(pageNo, totalCount, pageSize) {
	$('#infoLoadingDiv').dialog('open');
	var pars = $("#initForm").serialize() + "&pageNo=" + pageNo
			+ "&totalCount=" + totalCount + "&pageSize=" + pageSize;
	$.ajax({
		type : "POST",
		url : "bounced-register.do?method=batchRegisterQuery",
		data : pars,
		success : function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#resultListDiv').html(result);
		}
	});
}
function delolictr2(ele){
	if (confirm("确认删除?")) {
		$(ele).parent("td").parent("tr").remove(); 
		} 
}
function delolictr1(ele){
	if (confirm("确认删除?")) {
		$(ele).parent("td").parent("tr").remove(); 
		} 
}

function save(){
 var data=[];
 
var partnerId=$("#partnerId").val();
var refNo=$("#refNo").val();
//$("#channelCurrencyForm").submit();
}

function register(obj,orgCode,doingRefundAmount,settlementFlg,payAmount,transferRate,
		transferCurrencyCode,cardOrg,orderAmount,settlementCurrencyCode,settlementRate,
		orderId,currencyCode,doingBouncedAmount,floatValue,remark,overBouncedAmount,
		assureSettlementFlg,merchantCode,checkFlag,bouncedRemark) {
	var html=$(obj).parent().parent();
	var partnerId=html.find("input:eq(0)").val();
	var tradeOrderNo=html.find("input:eq(1)").val();
	var channelOrderNo=html.find("td:eq(2)").text();
	var refNo=html.find("td:eq(3)").text();
	var batchNo=html.find("td:eq(1)").text();
	var cardholderCardno=html.find("td:eq(4)").text();
	var authorisation=html.find("td:eq(5)").text();
	var tranDate=html.find("td:eq(6)").text();
	var canBouncedAmount=html.find("td:eq(11)").html();
	if(authorisation=="" && (orgCode=="10002001" || orgCode=="10080001" || orgCode=="10076001"))
	{
		alert("授权码不能为空！");
		return false;
	}
	if(cardOrg=="" || cardOrg=="null")
	{
		alert("没有查到卡组织！");
		return false;
	}
	var pars = "partnerId=" + partnerId+ "&tradeOrderNo=" + tradeOrderNo
	+ "&channelOrderNo=" + channelOrderNo
	+ "&authorisation=" + authorisation
	+ "&cardholderCardno=" + cardholderCardno
	+ "&orgCode=" + orgCode
	+ "&canBouncedAmount=" + canBouncedAmount
	+ "&cardholderCardno=" + cardholderCardno
	+ "&orderAmount=" + orderAmount
	+ "&payAmount=" + payAmount
	+ "&transferRate=" + transferRate
	+ "&cardOrg=" + cardOrg
	+ "&tranDate=" + tranDate
	+ "&settlementCurrencyCode=" + settlementCurrencyCode
	+ "&settlementRate=" + settlementRate
	+ "&transferCurrencyCode=" + transferCurrencyCode
	+"&orderId="+orderId
	+"&currencyCode="+currencyCode
	+"&floatValue="+floatValue
	+"&overBouncedAmount="+overBouncedAmount
	+"&remark="+remark
	+"&assureSettlementFlg="+assureSettlementFlg
	+"&merchantCode="+merchantCode
	+"&checkFlag="+checkFlag
	+"&bouncedRemark="+bouncedRemark
	+"&doingBouncedAmount="+doingBouncedAmount
	;
	 if(settlementFlg=='0' || doingRefundAmount>0 || settlementFlg=='2')
		{
		 alert("未清算、清算失败和退款中不能拒付！");
		 return false;
		} 
	var url="bounced-register.do?method=register&"+pars;
	parent.addMenu("拒付登记",url);
}
	function register1(ele){
		 $('#editChannelTable').empty();
		var html=$(ele).parent().parent();
		var partnerId=html.find("input:eq(0)").val();
		var tradeOrderNo=html.find("td:eq(0)").text();
		var channelOrderNo=html.find("td:eq(1)").text();
		var cardholderCardno=html.find("td:eq(2)").text();
		var authorisation=html.find("td:eq(3)").text();
		var tranDate=html.find("td:eq(4)").text();
		var orgCode=html.find("td:eq(5)").html();
		var canBouncedAmount=html.find("td:eq(10)").html();
		$("#editChannelTable").append("<tr class='trForContent1'><td  class='border_top_right4' width='24%' align='left' >商户会员号:</td><td class='border_top_right4' width='24%' align='right' ><input  type='text'  id='partnerId' name='partnerId' value="+partnerId+" ></td><td class='border_top_right4' width='24%' align='left' >网关订单号:</td><td class='border_top_right4' width='24%' align='right' >"+tradeOrderNo+"</td></tr>");
		$("#editChannelTable").append("<tr class='trForContent1'><td class='border_top_right4' width='24%' align='left' >档案号:</td><td class='border_top_right4' width='24%' align='right' >"+
		"<input  type='text'  id='refNo' name='refNo'></td><td class='border_top_right4' width='24%' align='left' >交易日期</td><td class='border_top_right4' width='24%' align='right' >"+tranDate+"</td></tr>");
		$("#editChannelTable").append("<tr class='trForContent1'><td class='border_top_right4' width='24%' align='left' >批次号:</td><td class='border_top_right4' width='24%' align='right' ><input  type='text'  id='batchNo' name='batchNo'></td><td class='border_top_right4' width='24%' align='left' >银行卡</td><td class='border_top_right4' width='24%' align='right' >"+cardholderCardno+"</td></tr>");
		$("#editChannelTable").append("<tr class='trForContent1'><td class='border_top_right4' width='24%' align='left' >授权码:</td><td class='border_top_right4' width='24%' align='right' >"+authorisation+"</td><td class='border_top_right4' width='24%' align='left' >渠道:</td><td class='border_top_right4' width='24%' align='right' >"+orgCode+"</td></tr>");
		$("#editChannelTable").append("<tr class='trForContent1'>"+
		"<td class='border_top_right4' width='24%' align='left' >拒付时间:</td>"+
		"<td class='border_top_right4' width='24%' align='right' >"+
		"<input class='Wdate' type='text' id='createDate' name='createDate' value='<fmt:formatDate value='${todayDate}' type='date' pattern='yyyy-MM-dd'/>'  onClick='WdatePicker()'></td>"+
		"<td class='border_top_right4' width='24%' align='left' >可拒付金额</td>"+
		"<td class='border_top_right4' width='24%' align='right' >"+canBouncedAmount+"</td></tr>");
		$("#editChannelTable").append("<tr class='trForContent1'>"+
				"<td class='border_top_right4' width='24%' align='left' >银行拒付币种:</td>"+
				"<td class='border_top_right4' width='24%' align='right' >"+
				"<input  type='text'  id='bankCurrencyCode' name='bankCurrencyCode'></td>"+
				"<td class='border_top_right4' width='24%' align='left' >银行拒付金额</td>"+
				"<td class='border_top_right4' width='24%' align='right' ><input  type='text'  id='bankAmount' name='bankAmount'></td></tr>");
		$("#editChannelTable").append("<tr class='trForContent1'><td class='border_top_right4' width='24%' align='left' >拒付原因:</td>"+
				"<td class='border_top_right4' width='24%' align='right' >"+
				"<input  type='text'  id='bankCurrencyCode' name='bankCurrencyCode'></td>"+
				"<td class='border_top_right4' width='24%' align='left' >显示原因</td>"+
				"<td class='border_top_right4' width='24%' align='right' ><input  type='text'  id='bankAmount' name='bankAmount'></td></tr>");
		$("#editChannelTable").append("<tr class='trForContent1'><td class='border_top_right4' width='24%' align='left' >最晚回复时间:</td><td class='border_top_right4' width='24%' align='right' ><input class='Wdate' type='text' id='tranDate' name='tranDate' value='<fmt:formatDate value='${todayDate}' type='date' pattern='yyyy-MM-dd'/>'  onClick='WdatePicker()'></td>"+
		"<td class='border_top_right4' width='24%' align='left' >拒付类型</td>"+
				"<td class='border_top_right4' align='left' >"+
				"<select id='bouncedType' name='bouncedType' >"+
				"<option value='' selected >---请选择---</option>"+
				"<option value='0' >拒付</option>"+
				"<option value='1' >调单</option>"+
				"</select></td></tr>");
		$("#editChannelTable").append("<tr class='trForContent1' colspan='4' ><td colspan='4'>操作备注：<textarea rows='4'  cols='85' name='remark' id='remark'>000</textarea></td></tr>");
		
		$('#editChannelDiv').dialog({
			position : "top",
			width : 800,
			modal : true,
			title : '拒付登记',
			height : 600,
			overlay : {
				opacity : 0.5,
				background : "black",
				overflow : 'auto'
			}
		}); 
	} 
</script>