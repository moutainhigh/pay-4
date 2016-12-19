<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">

</script>
<div id="editChannelDiv" style="display: none">
	<form action="./channel_currency_management.do" id="channelCurrencyForm">
		<input type="hidden" name="method" value="editChannel"><br><br>
		<input type='hidden' id='orgCurrencyCode' name='orgCurrencyCode'>
		<table  id="editChannelTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1" >


		</table>
		<br> <br>
		<br>
		<br>
		<input type="button" onclick="javascript:save(this);" value="提交">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" onclick="javascript:cancel1();" value="返回">&nbsp;&nbsp;&nbsp; 
	</form>
</div>
<div id="resultListDiv" class="listFence"></div>

<div id="infoLoadingDiv" title="加载信息"
	style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中,
	请稍候...
</div>
<body>
<table id="userTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1">
	<thead> 
		<tr >
			<th >网关订单号</th>
			<th >渠道订单号</th>
			<th >银行卡号</th>
			<th >授权码</th>
			<th >交易日期</th>
			<th >渠道</th>
			<th >交易币种</th>
			<th >交易金额</th>
			<th >已退金额</th>
			<th >退款中金额</th>
			<th >已拒付金额</th>
			<th >可拒付金额</th>
			<th >拒付中金额</th>
			<th >清算状态</th>
			<th >操作</th>
		</tr>
	</thead>
	<tbody> 
		<c:forEach items="${page}" var="dto" varStatus="status">
			<c:choose>
	       <c:when test="${status.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
				<td><input name="partnerId" type="hidden"  value="${dto.partnerId}"> ${dto.tradeOrderNo}</td>
				<td>${dto.channelOrderNo}</td>
				<td>${dto.cardholderCardno}</td>
				<td>${dto.authorisation}</td>
				<td><date:date value="${dto.tranDate}"/></td>
				<td align="center" >
					<c:choose>
					<c:when test="${dto.orgCode=='10076001'}">中银卡司</c:when>
					<c:when test="${dto.orgCode=='10079001'}">中银MOTO</c:when>
					<c:when test="${dto.orgCode=='10080001'}">中银MIGS</c:when>
					<c:when test="${dto.orgCode=='10003001'}">中国银行</c:when>
					<c:when test="${dto.orgCode=='10002001'}">农业银行</c:when>
					<c:when test="${dto.orgCode=='10075001'}">CREDORAX</c:when>
					<c:when test="${dto.orgCode=='10077001'}">Adyen</c:when>
					<c:when test="${dto.orgCode=='10077002'}">Belto</c:when>
					<c:when test="${dto.orgCode=='10077003'}">Cashu</c:when>
					<c:when test="${dto.orgCode=='10078001'}">农行CTV</c:when>       
					<c:when test="${dto.orgCode=='10077004'}">新生支付</c:when>      
					<c:when test="${dto.orgCode=='10081001'}">CT_BOLETO</c:when>     
					<c:when test="${dto.orgCode=='10081002'}">CT_SAFETYPAY</c:when>  
					<c:when test="${dto.orgCode=='10081003'}">CT_DirectDebitsSSL</c:when> 
					<c:when test="${dto.orgCode=='10081004'}">CT_SofortBanking</c:when> 
					<c:when test="${dto.orgCode=='10081005'}">CT_EPS</c:when>        
					<c:when test="${dto.orgCode=='10081006'}">CT_Giropay</c:when>    
					<c:when test="${dto.orgCode=='10081007'}">CT_PagBrasilDebitCard</c:when> 
					<c:when test="${dto.orgCode=='10081008'}">CT_PagBrasilOTF</c:when> 
					<c:when test="${dto.orgCode=='10081009'}">CT_Poli</c:when>        
					<c:when test="${dto.orgCode=='10081010'}">CT_Przelewy24</c:when> 
					<c:when test="${dto.orgCode=='10081016'}">前海万融</c:when> 
					<c:otherwise>未知机构</c:otherwise>
					</c:choose>
				</td>
				<td>${dto.currencyCode}</td>
				<td><fmt:formatNumber value="${dto.orderAmount/1000}"
						pattern="#,##0.00" /></td>
				<td><fmt:formatNumber value="${dto.overRefundAmount/1000}"
						pattern="#,##0.00" /></td>
				<td><fmt:formatNumber value="${dto.doingRefundAmount/1000}"
						pattern="#,##0.00" /></td>
				<td><fmt:formatNumber value="${dto.overBouncedAmount/1000}"
						pattern="#,##0.00" /></td>
				<td><fmt:formatNumber value="${dto.refundAmount/1000}"
						pattern="#,##0.00" /></td>
				<td><fmt:formatNumber value="${dto.doingBouncedAmount/1000}"
						pattern="#,##0.00" /></td>		
				<td>
				<c:choose>
					<c:when test="${dto.doingRefundAmount>0}">退款中</c:when>
					<c:when test="${dto.settlementFlg == '1'}">已清算</c:when> 
				    <c:when test="${dto.settlementFlg == '0'}">未清算</c:when>
				    <c:when test="${dto.settlementFlg == '3'}">退款中</c:when>
				    <c:when test="${dto.settlementFlg == '4'}">退款中</c:when>
				</c:choose>    
				</td>
				<td>
				<input id="delete" type="button" onclick="javascript:delolictr(this);" value="删除" class="button2" />
				<input id="download" type="button" onclick="javascript:register(this,'${dto.orgCode}','${dto.doingRefundAmount}','${dto.settlementFlg}',
				'${dto.payAmount}','${dto.transferRate}','${dto.transferCurrencyCode}',
				'${dto.cardOrg}','${dto.orderAmount}'
				,'${dto.settlementCurrencyCode}','${dto.settlementRate}'
				,'${dto.orderId}','${dto.currencyCode}','${dto.doingBouncedAmount}',
				'${dto.floatValue}','${dto.remark}','${dto.overBouncedAmount}','${dto.assureSettlementFlg}'
				,'${dto.merchantCode}','${dto.checkFlag}','${dto.bouncedRemark}');" value="登记" class="button2" />
			    </td>
			</tr>
		</c:forEach>
	</tbody>
</table>
</body>


<script type="text/javascript" language="javascript">

function delolictr(ele){
	if (confirm("确认删除?")) {
		$(ele).parent("td").parent("tr").remove(); 
		} 
}
function cancel1() {
	$('#editChannelTable').empty();
	$('#editChannelDiv').dialog("close");
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
	var tradeOrderNo=html.find("td:eq(0)").text();
	var channelOrderNo=html.find("td:eq(1)").text();
	var cardholderCardno=html.find("td:eq(2)").text();
	var authorisation=html.find("td:eq(3)").text();
	var tranDate=html.find("td:eq(4)").text();
	var orgName=html.find("td:eq(5)").html();
	var canBouncedAmount=html.find("td:eq(11)").html();
	if(authorisation=="" && (orgCode=="10002001" || orgCode=="10080001" || orgCode=="10076001"))
	{
		alert("授权码不能为空！");
		return false;
	}
	var pars = "partnerId=" + partnerId+ "&tradeOrderNo=" + tradeOrderNo
	+ "&channelOrderNo=" + channelOrderNo
	+ "&authorisation=" + authorisation
	+ "&cardholderCardno=" + cardholderCardno
	+ "&tranDate=" + tranDate
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
	+"&doingBouncedAmount="+doingBouncedAmount
	+"&assureSettlementFlg="+assureSettlementFlg
	+"&merchantCode="+merchantCode
	+"&checkFlag="+checkFlag
	+"&bouncedRemark="+bouncedRemark
	+"&settlementFlg="+settlementFlg
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