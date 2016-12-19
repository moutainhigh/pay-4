<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@page import="com.pay.poss.SessionUserHolderUtil"%>

<HTML>
<head>
<style type="text/css">
body {
	font-size: 12px;
	font-family: 宋体
}

.textbox {
	border-right: #cccccc 1px solid;
	border-top: #cccccc 1px solid;
	border-left: #cccccc 1px solid;
	color: #333333;
	border-bottom: #cccccc 1px solid;
	height: 20px;
	widows: 120px;
	background-color: #fdfdfd
}

.button {
	border-right: #888888 1px solid;
	border-top: #f4f4f4 1px solid;
	border-left: #f4f4f4 1px solid;
	COLOR: #000000;
	padding-top: 2px;
	border-bottom: #888888 1px solid
}

td {
	height: 20px;
}

.td1 {
	font-weight: bold;
	text-align: center;
}
</style>
<script type="text/javascript">
var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
function personalBillsQuery(pageNo,totalCount) {
	
	var paramaters = $("#command").serialize().split('&');
	var count = 0 ;
	for(var i=0;i<paramaters.length;i++){
		
		if(i == 0 && paramaters[i].split('=')[1]==0){
			  count +=1;
		}
		
		if(paramaters[i].split('=')[0]=="ebillTypeId"){
			if( paramaters[i].split('=')[1] ==0){
				  count +=1;
				}
		}
		
	    if(paramaters[i].split('=')[1]==""){
		    count +=1;
		}      	
     }
    if(paramaters.length == count){
        alert('请选择一个条件');
    	return false;
       
    }
    if(($('#createDate').val() != "" && $('#complateDate').val() == "") || ($('#createDate').val() == "" && $('#complateDate').val() != "")){
        alert('日期选择错误');
    	return false;
     }
    
    $('#infoLoadingDiv').dialog('open');
	var datas = $("#command").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount;
	$.ajax({
		type: "POST",
		url: "${ctx}/personalBill.do?method=personalBillsSearch",
		data: datas,
		success: function(result) {
			$('#infoLoadingDiv').dialog('close');
			$('#personalBillsList').html(result);
		}
	});
}
function resetForm(){
//清空文本框
 $('input[type=text]').each(function(){
	 $(this).val(""); 
  });
 
 $("#status").get(0).selectedIndex=0;
}
</script>
</head>
<body>

<table cellSpacing=0 cellPadding=0 width="98%" border=0
	style="margin-top: 20px;">
	<tbody>
		<tr>
			<td vAlign=top width="100%" bgColor=#ffffff>


			<table borderColor="#cccccc" cellSpacing=0 cellPadding=0 width="100%"
				align=center border=0>
				<tbody>
					<tr>
						<td height=25>
						<form action="" name="command" id="command">
						<table cellSpacing="0" cellPadding="2" border="0" align="center">
							<tbody>
								<tr>
									<td class="td1">订单状态</td>
									<td><select id="status" name="status"
										style="width: 120px;">
										<option value="0">全部</option>
										<option value="101">初始化</option>
										<option value="111">支付成功</option>
										<option value="112">支付失败</option>
										<option value="113">订单完成</option>
									</select></td>
									<td class="td1">订单流水号</td>
									<td><input class="textbox" id="orderId" name="orderId" 
									 <%
					                	if(SessionUserHolderUtil.getSessionUserHolder() == null)
						                 {
							            %>
							              disabled="disabled"
							          <%} %>
									 />
									 </td>
									
									<td class="td1">子订单流水号</td>
									<td><input class="textbox" id="itemId" name="itemId" /></td>
									<td class="td1">入款流水号</td>
									<td><input class="textbox" id="gatewayTradeNo"
										name="gatewayTradeNo" /></td>
										
									<td class="td1">出款流水号</td>
									<td><input class="textbox" id="channelSeqId"  name="channelSeqId" 
									   <%
					                	if(SessionUserHolderUtil.getSessionUserHolder() == null)
						                 {
							            %>
							              disabled="disabled"
							          <%} %>
									  /></td>
								   </tr>
								<tr>
									<td class="td1">订单金额</td>
									<td><input class="textbox" id="amount" name="amount" /></td>
									<td class="td1">出款方</td>
									<td><input class="textbox" id="loginName"
										name="loginName" /></td>
									<td class="td1">收款方</td>
									<td><input class="textbox" name="payeeAcctNo" /></td>
									<td class="td1">交易日期</td>
									<td colspan="3"><li:dateTime id="createDate" width="130"
										onfocusId="complateDate" />- <li:dateTime id="complateDate"
										width="130" /></td>
								</tr>
								<tr>
									<td class="td1">收款银行</td>
									<td><input class="textbox" id="backName" name="backName" /></td>
									<td class="td1">付款银行流水号</td>
									<td><input class="textbox" id="bankSerialno"
										name="bankSerialno" /></td>
									<td class="td1">条形码</td>
									<td><input class="textbox" id="billBarCode"
										name="billBarCode" /></td>
									<td class="td1">订单类型</td>
									<td><select name="ebillTypeId" style="width: 120px;">
										<option value="0">全部</option>
										<c:forEach items="${billTypes}" var="billType">
											<c:if test="${billType.value !=12}">
												<option value="${billType.value}">${billType.desc}</option>
											</c:if>
										</c:forEach>
									</select></td>
									<td align="right"><input class="button" type="button"
										value="查询 " onclick="personalBillsQuery()" /> <input
										class="button" type="button" value="清空 "
										onclick="resetForm();" /></td>
									<td>&nbsp;</td>
								</tr>
							</tbody>
						</table>
						</form>
						</td>
					</tr>
					<tr>
						<td>
						<div id="infoLoadingDiv" title="加载信息"
							style="display: none; width: 200px; padding-top: 30px; height: 70px;">
						<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中,
						请稍候...</div>
						<div id="personalBillsList"></div>
						</td>
					</tr>
				</tbody>
			</table>
	</tbody>
</table>
</body>
</html>
