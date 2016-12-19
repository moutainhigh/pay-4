<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<script type="text/javascript">
		$(document).ready(function(){
			 $("#userTable").tablesorter({
				 headers: {
				 	5: {sorter: false}
				 }});      
		});
</script>
</head>
<body>
	<c:if test='${error != null}'> 
		<font color="#FF0000">${error}</font>
	</c:if>
	<c:if test='${error == null}'>
	<div class="tableList">
	
	<table class="tablesorter" border="0" cellpadding="0" cellspacing="1" >
	<thead>
	  <tr class="tabT">
	  	<th class="tabTitle" scope="col" rowspan="2" align="center">日期</th>
	    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="2">网关付款</th>
	    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="2">充值</th>
	    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="2">转账交易</th>
	    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="2">提现交易</th>
	    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="2">转账到银行卡</th>
	    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="2">充退交易</th>
	    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="2">信用卡快捷支付</th>
	    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="2">借记卡快捷支付</th>
	    <th class="tabTitle" align="center" scope="col" colspan="2">合计</th>
	  </tr>
	   <tr class="tabT">
	    <th class="tabTitle" align="center" scope="col">金额（元）</th>
	    <th class="tabTitle" align="center" scope="col" style="border-right:2px solid #aaa">笔</th>
	    <th class="tabTitle" align="center" scope="col">金额（元）</th>
	    <th class="tabTitle" align="center" scope="col" style="border-right:2px solid #aaa">笔</th>
	    <th class="tabTitle" align="center" scope="col">金额（元）</th>
	    <th class="tabTitle" align="center" scope="col" style="border-right:2px solid #aaa">笔</th>
	    <th class="tabTitle" align="center" scope="col">金额（元）</th>
	    <th class="tabTitle" align="center" scope="col" style="border-right:2px solid #aaa">笔</th>
	    <th class="tabTitle" align="center" scope="col">金额（元）</th>
	    <th class="tabTitle" align="center" scope="col" style="border-right:2px solid #aaa">笔</th>
	    <th class="tabTitle" align="center" scope="col">金额（元）</th>
	    <th class="tabTitle" align="center" scope="col" style="border-right:2px solid #aaa">笔</th>
	    <th class="tabTitle" align="center" scope="col">金额（元）</th>
	    <th class="tabTitle" align="center" scope="col" style="border-right:2px solid #aaa">笔</th>
	    <th class="tabTitle" align="center" scope="col">金额（元）</th>
	    <th class="tabTitle" align="center" scope="col" style="border-right:2px solid #aaa">笔</th>
	    <th class="tabTitle" align="center" scope="col">金额（元）</th>
	    <th class="tabTitle" align="center" scope="col">笔</th>
	  </tr>
	</thead> 
	  <c:forEach items="${list}" var="dto">
	  <tr>
	  	<td>&nbsp;
	    	${dto.createDate}
	    </td>
	    <td>&nbsp;
	    	<fmt:formatNumber value="${dto.payAmount/1000}" pattern="#,##0.00"/>
	    </td>
	     <td style="border-right:2px solid #aaa" >&nbsp;
		     <c:if test='${dto.payCount == null}'> 
				0
			</c:if>
		     ${dto.payCount}
	     </td>
	     <td>&nbsp;
	     	<fmt:formatNumber value="${dto.depositAmount/1000}" pattern="#,##0.00"/>
	     </td>
	     <td style="border-right:2px solid #aaa">&nbsp;
	     	<c:if test='${dto.depositCount == null}'> 
	     		0
	     	</c:if>
	     	${dto.depositCount}
	     </td>
	     <td>&nbsp;
	     	<fmt:formatNumber value="${dto.pay2AccAmount/1000}" pattern="#,##0.00"/>
	     </td>
	      <td style="border-right:2px solid #aaa">&nbsp;
	      	<c:if test='${dto.pay2AccCount == null}'> 
	     		0
	     	</c:if>
	      ${dto.pay2AccCount}
	      </td>
	      <td>&nbsp;
	     	<fmt:formatNumber value="${dto.withDrowAmount/1000}" pattern="#,##0.00"/>
	     </td>
	     <td style="border-right:2px solid #aaa">&nbsp;
	     	<c:if test='${dto.withDrowCount == null}'> 
	     		0
	     	</c:if>
	     ${dto.withDrowCount}</td>
	     <td>&nbsp;
	     	<fmt:formatNumber value="${dto.pay2BankAmount/1000}" pattern="#,##0.00"/>
	     </td>
	     <td style="border-right:2px solid #aaa">&nbsp;
	     	<c:if test='${dto.pay2BankCount == null}'> 
	     		0
	     	</c:if>
	     	${dto.pay2BankCount}
	     </td>
	     <td>&nbsp;
	     	<fmt:formatNumber value="${dto.refundAmount/1000}" pattern="#,##0.00"/>
	     </td>
	     <td style="border-right:2px solid #aaa">&nbsp;
	     	<c:if test='${dto.refundCount == null}'> 
	     		0
	     	</c:if>
	     	${dto.refundCount}
	     </td>
	     <td>&nbsp;
	     	<fmt:formatNumber value="${dto.creditQuickPayAmount/1000}" pattern="#,##0.00"/>
	     </td>
	     <td style="border-right:2px solid #aaa">&nbsp;
	     	<c:if test='${dto.creditQuickPayCount == null}'> 
	     		0
	     	</c:if>
	     	${dto.creditQuickPayCount}
	     </td>
	     <td>&nbsp;
	     	<fmt:formatNumber value="${dto.debitQuickPayAmount/1000}" pattern="#,##0.00"/>
	     </td>
	     <td style="border-right:2px solid #aaa">&nbsp;
	     	<c:if test='${dto.debitQuickPayCount == null}'> 
	     		0
	     	</c:if>
	     	${dto.debitQuickPayCount}
	     </td>
	     <td>&nbsp;
	     	<fmt:formatNumber value="${(dto.payAmount+dto.depositAmount +dto.pay2AccAmount +dto.pay2BankAmount + dto.withDrowAmount+dto.refundAmount+dto.creditQuickPayAmount+dto.debitQuickPayAmount)/1000}" pattern="#,##0.00"/>
	     </td>
		 <td>&nbsp; ${dto.payCount + dto.depositCount + dto.pay2AccCount + dto.pay2BankCount + dto.withDrowCount+dto.refundCount+dto.creditQuickPayCount+dto.debitQuickPayCount} </td>
	  	</tr>
	  </c:forEach>
	</table>
	</div>
	</c:if>
</body>
</html>
