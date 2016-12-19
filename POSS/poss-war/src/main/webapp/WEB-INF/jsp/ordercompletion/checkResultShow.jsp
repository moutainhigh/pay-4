<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<style type="text/css">
	a:hover{text-decoration:underline;}
</style>

<script type="text/javascript">

	function editChannel(ele, auditTip, tipColor, reqBatchNo, status){
		$('#editChannelTable').empty();
		var _title = "" ;
		var _color = "" ;
		if(null == auditTip || "" == auditTip){
			_title = "审核" ;
			_color = "black" ;
		}else{
			_title = auditTip ;
			_color = tipColor ;
			$("#editBtn").val(auditTip);
		}
		var _reqBatchNo = reqBatchNo ;
		var _status = status ;
		
		$("#editChannelTable").append("<input type='hidden' name='reqBatchNo' value='"+_reqBatchNo+"'/>")
			.append("<input type='hidden' name='status' id='status' value='"+_status+"' />");
		$("#editChannelTable").append("<p style='color:"+_color+";font-weight:bold;font-size:20px;'>"+ _title +"</p>"
			+"<p>确认"+ _title +"吗？</p><div><label style='position:relative;top:-60px;display:inline-block;'><i style='color:red;'>*</i>备注：</label><textarea style='width:260px;height:80px;' id='remark' name='remark'></textarea></div>");
		
		$('#editChannelDiv').dialog({
			position : "top",
			width : 450,
			modal : true,
			title : '补单审核',
			height : 330,
			overlay : {
				opacity : 0.5,
				background : "black",
				overflow : 'auto'
			}
		}); 
	} 
	
	function cancel1() {
		$('#editChannelTable').empty();
		$('#editChannelDiv').dialog("close");
	}
	 
	function editChannel1(){
		var remark = $("#remark").val().trim() ;
		if(null == remark || "" == remark){
			alert("审核备注不能为空！");
			return false ;
		}
		var status = $("#status").val() ;
		if(status == 1){
			$("#hiddenMet").val("checkPass") ;
		}else if(status == 2){
			$("#hiddenMet").val("checkRefuse") ;
		}
		
		$("#checkForm").submit();
	}
</script>

<div id="editChannelDiv" style="display: none">
	<!-- <h3>补单审核</h3> -->
	<form action="${ctx }/orderCompletionCheck.do" id="checkForm">
		<input type="hidden" name="method" id="hiddenMet" ><br><br>
		<input type="hidden" name="meetListJArrayStr" id="meetListJArrayStr"/>
		<input type="hidden" name="disMeetJArrayStr" id="disMeetJArrayStr"/>
		<table  id="editChannelTable" >


		</table>
		
		<br>
		<input id="editBtn" type="button" onclick="editChannel1();" value="确定">&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<input type="button" onclick="cancel1();" value="返回">&nbsp;&nbsp;&nbsp; 
	</form>
</div>
<h2 class="panel_title">补单审核</h2>
<form action="#" method="post" id="mainfromId">
	<input type="hidden" name="reqBatchNo" id="reqBatchNo" />
		
	<div style="text-align:left;padding-left:20px;">
		<p>银行机构：&nbsp;&nbsp;&nbsp;&nbsp;
			<c:forEach var="channel" items="${paymentChannelItems }" varStatus="v">
				<label id="${channel.orgCode }" style="display:none;">${channel.name }</label>
			</c:forEach>
		</p>
		<p>匹配记录：&nbsp;&nbsp;${fn:length(meetList) }笔</p>
	</div>
	
	<table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1" align="center">
		<thead>
			<tr>
				<th>渠道订单号</th>
				<th>机构端流水号</th>
				<th>支付金额</th>
				<th>支付币种</th>
				<th>授权码</th>
			</tr>
		</thead>
		<tbody>
			
			<c:forEach var="orderM" items="${meetList }" varStatus = "personalStatus">
		<c:choose>
	       <c:when test="${personalStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
				<td><span></span>${orderM.channelOrderNo }<span></span></td>
				<td>
					${orderM.returnNo }
				</td>
				<td>${orderM.amount }</td>
				<td>${orderM.currencyCode }</td>
				<td>${orderM.authorization }</td>
			</tr>
			</c:forEach>
			
		</tbody>
		
	</table>
	<br/>
	<div style="text-align:left;padding-left:20px;">
		<p>不匹配记录：&nbsp;&nbsp;${fn:length(disMeetList) }笔</p>
	</div>
	<table id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1" align="center">
		<thead>
			<tr>
				<th>渠道订单号</th>
				<th>机构端流水号</th>
				<th>支付金额</th>
				<th>支付币种</th>
				<th>授权码</th>
				<!-- <th>失败原因</th> -->
			</tr>
		</thead>
		<tbody>
			<c:forEach var="orderD" items="${disMeetList }" varStatus = "personalStatus">
		<c:choose>
	       <c:when test="${personalStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
				<td><span></span>${orderD.channelOrderNo }<span></span></td>
				<td>
					${orderD.returnNo }
				</td>
				<td>${orderD.amount }</td>
				<td>${orderD.currencyCode }</td>
				<td>${orderD.authorization }</td>
				<%-- <td>${orderD.failReason }</td> --%>
			</tr>
			</c:forEach>
		</tbody>
		
	</table>
	<input type="button" value="审核通过" class="button2" onclick="javascript:editChannel(this, '审核通过', '#008000', '${reqBatchNo}', 1);">
	<input type="button" value="审核拒绝" class="button2" onclick="javascript:editChannel(this, '审核拒绝', 'red', '${reqBatchNo}', 2);">
	<input type="button" value="返  回"   class="button2" onclick="javascript:window.history.go(-1);">
</form>
<script type="text/javascript">
	$(document).ready(function(){
		$("#meetListJArrayStr").val(encodeURIComponent('${meetListJArrayStr}')) ;
		$("#disMeetJArrayStr").val(encodeURIComponent('${disMeetJArrayStr}')) ;
		$("#" + '${orgCode}').removeAttr("style") ;
	}) ;
</script>
