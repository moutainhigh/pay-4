<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglibs.jsp"%>

<link type="text/css" href="${ctx}/js/jquery/css/tabs/tabs.css" rel="stylesheet" />
<style type="text/css">
		.border_div_td {
			font-size: 12px;
			width:80px;
			line-height: 20px;
			color: #333333;
		}
</style>
	
<script type="text/javascript">
	$(function() {
		$("#tabs").tabs({
			ajaxOptions: {
				error: function(xhr, status, index, anchor) {
					$(anchor.hash).html("加载Tab页签失败!");
				}
			}
		});

		 $("#tabsTable-1").tablesorter({
			 headers: {
			 	3:{sorter: false}
		 	}}); 
		 $("#tabsTable-2").tablesorter({ 
		        sortList: [[0,0]] 
		    });
		 $("#tabsTable-3").tablesorter({
			 headers: {
			 	2:{sorter: false}
			}});  
	});

	function createReversed(){
		var deal_type = "<c:out value='${ orderDetailDTO.orderType }' />";//订单类型
		var amount = "<c:out value='${ orderDetailDTO.orderAccount }' />";//冲正金额
		var src_order_serial_no = "<c:out value='${ orderKy }' />";//原始交易订单号
		var src_pe_serial_no = "<c:out value='${ orderKy }' />";//原始记账凭证号
		var payeeMemberCode = "<c:out value='${ orderDetailDTO.payeeKy }' />";//收款人客户号
		var payerMemberCode = "<c:out value='${ orderDetailDTO.payerKy }' />";//付款人客户号
		var reaseon = $("#reaseon").val();//冲正申请理由

		if(reaseon == '' || reaseon == null){
			return false;
		}
		//var url = "reversed.do?method=createReversed&deal_type="+deal_type+"&amount="+amount+"&src_order_serial_no="+src_order_serial_no+"&src_pe_serial_no="+src_pe_serial_no+"&reaseon="+encodeURIComponent(encodeURIComponent(reaseon));
		var url = "reversed.createReversed.do?deal_type="+deal_type+"&amount="+amount+"&src_order_serial_no="+src_order_serial_no+"&src_pe_serial_no="+src_pe_serial_no+"&payeeMemberCode="+payeeMemberCode+"&payerMemberCode="+payerMemberCode+"&reaseon="+encodeURIComponent(encodeURIComponent(reaseon));
		window.location.href = url;
	}

	function entryOrderQueryIn(){
		window.location.href = "reversed.query.entryOrderQueryIn.do";
	}
</script>

<div class="demo" style="width: 80%">
<div id="tabs">
	<div id="tabs-1">
		<div>
			<table width="80%" border="1">
				<thead> 
					<tr>    
						<th colspan="4">付款人信息</th>     
					</tr> 
				</thead>
				<tbody>
					<tr>
						<td align="right" class="border_div_td">用户号:</td>
						<td>${orderDetailDTO.payerKy}&nbsp;</td>
						<td align="right" class="border_div_td">用户姓名:</td>
						<td>${orderDetailDTO.payerName}&nbsp;</td>
					</tr>
					<tr>
						<td align="right" class="border_div_td">用户等级:</td>
						<td>${orderDetailDTO.payerLevel}&nbsp;</td>
						<td align="right" class="border_div_td">用户标识:</td>
						<td>${orderDetailDTO.payerId}&nbsp;</td>
					</tr>
					<tr>
						<td align="right" class="border_div_td">付款账户类型:</td>
						<td>
							<c:choose>
								<c:when test="${orderDetailDTO.payerAcctType == null || orderDetailDTO.payerAcctType == ''}">&nbsp;</c:when>
								<c:otherwise><li:select showStyle="desc" name="" itemList="${accountTypes}" selected="${orderDetailDTO.payerAcctType}"/>&nbsp;</c:otherwise>
							</c:choose>
						</td>
						<td align="right" class="border_div_td">付款账户号:</td>
						<td>${orderDetailDTO.payerAcct}&nbsp;</td>
					</tr>
				</tbody> 
			</table>
		</div>
		
		<div>
			<table width="80%" border="1">
				<thead> 
					<tr>    
						<th colspan="4">收款人信息</th>     
					</tr> 
				</thead>
				<tbody>
					<tr>
						<td align="right" class="border_div_td">用户号:</td>
						<td>${orderDetailDTO.payeeKy}&nbsp;</td>
						<td align="right" class="border_div_td">用户姓名:</td>
						<td>${orderDetailDTO.payeeName}&nbsp;</td>
					</tr>
					<tr>
						<td align="right" class="border_div_td">用户等级:</td>
						<td>${orderDetailDTO.payeeLevel}&nbsp;</td>
						<td align="right" class="border_div_td">用户标识:</td>
						<td>${orderDetailDTO.payeeId}&nbsp;</td>
					</tr>
					<tr>
						<td align="right" class="border_div_td">收款账户类型:</td>
						<td>
							<c:choose>
								<c:when test="${orderDetailDTO.payeeAcctType == null || orderDetailDTO.payeeAcctType == ''}">&nbsp;</c:when>
								<c:otherwise><li:select showStyle="desc" name="" itemList="${accountTypes}" selected="${orderDetailDTO.payeeAcctType}"/>&nbsp;</c:otherwise>
							</c:choose>
						</td>
						<td align="right" class="border_div_td">收款账户号:</td>
						<td>${orderDetailDTO.payeeAcct}&nbsp;</td>
					</tr>
				</tbody> 
			</table>
		</div>
		
		<div>
			<table width="80%" border="1">
				<thead> 
					<tr>    
						<th colspan="4">订单信息</th>     
					</tr> 
				</thead>
				<tbody>
					<tr>
						<td align="right" class="border_div_td">商家订单号:</td>
						<td>${orderDetailDTO.orderId}&nbsp;</td>
						<td align="right" class="border_div_td">订单类型:</td>
						<td><li:select showStyle="desc" name="orderType" itemList="${orderStatus}" selected="${orderDetailDTO.orderType}"/>&nbsp;</td>
					</tr>
					<tr>
						<td align="right" class="border_div_td">订单创建时间:</td>
						<td><fmt:formatDate value="${orderDetailDTO.orderCreateTime}" type="both"/>&nbsp;</td>
						<td align="right" class="border_div_td">订单结束时间:</td>
						<td><fmt:formatDate value="${orderDetailDTO.orderEndTime}" type="both"/>&nbsp;</td>
					</tr>
					<tr>
						<td align="right" class="border_div_td">支付方式:</td>
						<td>${orderDetailDTO.payment}&nbsp;</td>
						<td align="right" class="border_div_td">交易状态:</td>
						<td>
							<c:choose>
								<c:when test="${'' eq orderDetailDTO.orderStatus || null == orderDetailDTO.orderStatus}">&nbsp;</c:when>
								<c:otherwise><li:select showStyle="desc" name="" itemList="${tradeStatus}" selected="${orderDetailDTO.orderStatus}"/>&nbsp;</c:otherwise>
							</c:choose>
						</td>
						<td></td>
					</tr>
					<tr>
						<td align="right" class="border_div_td">交易金额:</td>
						<td><fmt:formatNumber value="${orderDetailDTO.orderAccount * 0.001}" pattern="#,##0.00"/>&nbsp;</td>
						<td align="right" class="border_div_td">费用:</td>
						<td><fmt:formatNumber value="${orderDetailDTO.fee * 0.001}" pattern="#,##0.00"/>&nbsp;</td>
					</tr>
					<tr>
						<td align="right" class="border_div_td">商品名称:</td>
						<td>${orderDetailDTO.goodsName}&nbsp;</td>
						<td align="right" class="border_div_td">付款人IP:</td>
						<td>${orderDetailDTO.payerIp}&nbsp;</td>
					</tr>
					<tr>
						<td align="right" class="border_div_td">交易网址:</td>
						<td colspan="3">${orderDetailDTO.orderWebsite}&nbsp;</td>
					</tr>
				</tbody> 
			</table>
		</div>
		
		<div>
			<table width="80%" border="1">
				<thead> 
					<tr>    
						<th colspan="4">订单独特信息</th>     
					</tr> 
				</thead>
				<tbody>
					<tr>
						<td align="right" class="border_div_td">收款人姓名:</td>
						<td>${orderDetailDTO.payeeName}&nbsp;</td>
						<td align="right" class="border_div_td">订单类型:</td>
						<td><li:select showStyle="desc" name="orderType" itemList="${orderStatus}" selected="${orderDetailDTO.orderType}"/>&nbsp;</td>
					</tr>
					<tr>
						<td align="right" class="border_div_td">银行卡号:</td>
						<td>${orderDetailDTO.bankAcct}&nbsp;</td>
						<td align="right" class="border_div_td">银行名称:</td>
						<td><li:select showStyle="desc" name="" itemList="${bankList}" selected="${orderDetailDTO.bankName}"/>&nbsp;</td>
					</tr>
					<tr>
						<td align="right" class="border_div_td">开户行:</td>
						<td>${orderDetailDTO.bankBranch}&nbsp;</td>
						<td align="right" class="border_div_td">省份:</td>
						<td>${orderDetailDTO.provinces}&nbsp;</td>
					</tr>
					<tr>
						<td align="right" class="border_div_td">城市:</td>
						<td>${orderDetailDTO.city}&nbsp;</td>
						<td align="right" class="border_div_td">费用:</td>
						<td><fmt:formatNumber value="${orderDetailDTO.fee * 0.001}" pattern="#,##0.00"/>&nbsp;</td>
					</tr>
					<tr>
						<td align="right" class="border_div_td">清算批次号:</td>
						<td>${orderDetailDTO.liquidateBatchKy}&nbsp;</td>
						<td align="right" class="border_div_td">留言:</td>
						<td>${orderDetailDTO.remarks}&nbsp;</td>
					</tr>	
				</tbody> 
			</table>
		</div>
		
		<div>
			<table width="80%" border="1">
				<thead> 
					<tr>    
						<th colspan="4">冲正申请理由</th>     
					</tr> 
				</thead>
				<tbody>
					<c:if test="${ reaseon == '' || reaseon == null}">
						<tr>
							<td align="center" colspan="4" style="align:center">
								<textarea id="reaseon" name="reaseon" rows="5" cols="100"></textarea>
							</td>
						</tr>
						<tr>
							<td align="center" colspan="4" style="align:center"><input type="button" id="btnSubmit" name="btnSubmit" value="申请冲正" onclick="createReversed()" /></td>
						</tr>		
					</c:if>
					<c:if test="${ reaseon != '' && reaseon != null}">
						<tr>
							<td align="left" colspan="4" valign="top" style="height:80px; text-indent:2em; ">
								${ reversed.reaseon }
							</td>
						</tr>
						<tr>
							<td align="center" colspan="4" style="align:center"><input type="button" id="btnSubmit" name="btnSubmit" value="返回申请冲正" onclick="entryOrderQueryIn()" /></td>
						</tr>			
					</c:if>
				</tbody> 
			</table>
		</div>
	</div>
</div>
</div>
