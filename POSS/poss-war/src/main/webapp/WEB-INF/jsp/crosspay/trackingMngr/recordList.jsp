<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib prefix="li_new" uri="page"%>
<%@ page import="com.hnapay.fundout.util.AmountUtils"%>

<form action="" method="post" name="listFrom">
</form>

<table width="900px"  id="sorterTable" class="tablesorter" border="0" cellpadding="0" cellspacing="1" align="center">
	<thead>
		<tr>
			<th class="item15" width="10%">交易号</th>
			<th class="item15" width="8%">订单号</th>
			<th class="item10" width="10%">交易网站</th>
			<th class="item10" width="10%">运单号</th>
			<th class="item10" width="8%">快递公司</th>
			<th class="item30" width="8%">运单查询网站</th>
			<th class="item10" width="8%">运单上传时间</th>
			<th class="item30" width="4%">运单状态</th>
			<th class="item10" width="6%">>是否妥投</th>
			<th class="item30" width="4%">>备注</th>
			<th class="item10" width="4%">>操作</th>
		</tr>
	</thead>
	<tbody>
		<c:forEach items="${page.result}" var="express" varStatus="index">
		<tr class="even" align="center">
			<td>${express.tradeOrderNo}</td>
			<td>${express.orderId}</td>
			<td>${express.tradeUrl}</td>
		    <td>${express.trackingNo}</td>
		    <td>${express.expressCom}</td>
		    <td>${express.queryUrl}</td>
		    <td><fmt:formatDate value="${express.uploadeDate}" pattern="yyyy-MM-dd"/></td>
			<td>
				<!--1：待审核2：审核通过  3：审核未通过--> 
				<c:if test="${express.status=='1'}">
					待审核
				</c:if>
				<c:if test="${express.status=='2'}">
					审核通过
				</c:if>
				<c:if test="${express.status=='3'}">
					审核未通过
				</c:if>				
			</td>
			<td>
				<!--0：未妥投2：已妥投--> 
				<c:if test="${express.completeFlg=='0'}">
				</c:if>
				<c:if test="${express.completeFlg=='1'}">
					√
				</c:if>			
			</td>
			<td align="left">${express.remark}</td>
			<td><a href="javaScript:;"
				onclick="openCheckDialog('${express.tradeOrderNo}','${express.trackingNo}','${express.expressCom}','${express.queryUrl}')">审核</a></td>
		</tr>
		</c:forEach>
	</tbody>
</table>
<div id="CheckDialog" title="运单审核" style="display:show; width: 300px; padding-top: 30px; height: 70px;">
	<form action="" id="checkStatusForm">
	<input name="id" id="id" type='hidden'/>
	<table >
		<tr>
			<td>交 易 号:</td>
			<td><input name="chk_tradeOrderNo" id="chk_tradeOrderNo" readonly="readonly"/></td>
			</tr>
			<tr><td>运 单 号:</td>
			<td><input name="chk_trackingNo" id="chk_trackingNo" readonly="readonly"/></td>
			</tr>
			<tr>
			<td>快 递 公 司:</td>
			<td>
			<input name="chk_expressCom" id="chk_expressCom" readonly="readonly"/>
	      	</td>
	      	</tr>
	      	<tr>
	      	<td>运单查询网址：</td>
	      	<td>
	      	<input name="chk_queryUrl" id="chk_queryUrl" readonly="readonly"/>
	      	</td>
	      	</tr>
			<tr>
			<td>审 核 原 因:</td>
			<td><textarea cols="30" rows="6" id="chk_remark" name="chk_remark"></textarea></td>
			</tr>
			<tr>
				<td colspan="3" align="center">
					<input type="button"  value="审核通过"  onclick="checkStatus(0);">
					<input type="button"  value="审核不通过"  onclick="checkStatus(1);">
					<input type="button" value="返  回"  onclick="closeCheckDialog();">
				</td>
			</tr>
	</table>
	
	</form>
	
	
	
</div>

<li_new:page methodName="search" pageBean="${page}" sytleName="black2"/>
 <script type="text/javascript">
	 $(document).ready(function(){
		 $("#sorterTable").tablesorter({
			 headers: {
				0: {sorter: false},
				7: {sorter: false}
			}
		});
		$('#CheckDialog').dialog({
			autoOpen: false,
			width: 280
		});
	 }); 
	 var dialog=null;
	 function openCheckDialog(param1,param2,param3,param4) {
		$('#CheckDialog').dialog({
			autoOpen: false,
			width: 280
		});
	 	$("#CheckDialog").find("#chk_tradeOrderNo").val(param1);
	 	$("#CheckDialog").find("#chk_trackingNo").val(param2);
	 	$("#CheckDialog").find("#chk_expressCom").val(param3);
	 	$("#CheckDialog").find("#chk_queryUrl").val(param4);
	 	dialog = $('#CheckDialog').dialog('open');
	 }
	 
	 function closeCheckDialog() {
	 	dialog.dialog("close");		
	 }
	 
	 function checkStatus(actType) {
			
			var pars = $("#checkStatusForm").serialize();
			$.ajax({
				type: "POST",
				url: "${ctx}/crosspay/trackingMngr.do?method=checkExpress&act="+actType,
				data: pars,
				success: function(result) {
					closeCheckDialog();
					$.ajax({
						type: "POST",
						url: "${ctx}/crosspay/trackingMngr.do?method=list",
						data: $("#mainfromId").serialize(),
						success: function(result) {
							$('#resultListDiv').html(result);
						}
					});
				}
			});
		}
	
</script>