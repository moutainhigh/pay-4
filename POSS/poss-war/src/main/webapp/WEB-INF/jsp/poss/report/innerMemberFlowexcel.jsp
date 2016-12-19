<%@ page contentType="text/html;charset=UTF-8"%>
<%@ taglib prefix="c"       uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fmt" 	uri="http://java.sun.com/jsp/jstl/fmt" %>
<c:set var="ctx" value="${pageContext.request.contextPath}"/>
<head><meta charset=utf-8 />
<style type="text/css"> 
.excel_txt {
	padding-top: 1px;
	padding-right: 1px;
	padding-left: 1px;
	mso-ignore: padding;
	color: black;
	font-size: 11.0pt;
	font-weight: 400;
	font-style: normal;
	text-decoration: none;
	font-family: 宋体;
	mso-generic-font-family: auto;
	mso-font-charset: 134;
	mso-number-format: "\@";
	text-align: general;
	vertical-align: middle;
	mso-background-source: auto;
	mso-pattern: auto;
	white-space: nowrap;
}
</style> 
</head>
<body>
	<table width="800" border="1">
		<thead>
			<tr class="tabT">
		  	<th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" rowspan="2">日期</th>
		  	<th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" rowspan="2">会员编号</th>
		  	<c:choose>
			  	<c:when test = "${optType == 2}">
			  		<th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" rowspan="2">商户名称</th>
			    </c:when>
			    <c:otherwise>
			    	<th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" rowspan="2">分子公司名称</th>
			    </c:otherwise>
		    </c:choose>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="2">帐户入款</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="2">银行卡入款</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="2">预付卡入款</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="2">充值卡入款</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="2">手机POS入款</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="2">充值</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="2">转帐（入）</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="2">银行卡代扣</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="2">退票</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="2">账户支付</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="2">转账（出）</th>	    
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="2">提现</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="2">转账到银行卡</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="2">退款</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="2">充退</th>
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
			
	<tbody>
		<c:forEach items="${result}" var="dto">
		 <tr class="trForContent1 tr_hover">
	  	 <td class="excel_txt" style="border-right:2px solid #aaa" >
	  		<fmt:formatDate value="${dto.reportDate}" type="date"/>
	     </td>
	     <td class="excel_txt" style="border-right:2px solid #aaa">${dto.memberCode}</td>
	     <td class="excel_txt" style="border-right:2px solid #aaa">${dto.name}</td>
	     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'">
	     	<fmt:formatNumber value="${dto.fiAccInAmount/1000}" pattern="0.00"/>
	     </td>
	     <td class="excel_txt" style="border-right:2px solid #aaa;mso-number-format:'0'">${dto.fiAccInCount}</td>
	     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'" >
	     	<fmt:formatNumber value="${dto.fiBankAmount/1000}" pattern="0.00"/>
	     </td>
	     <td class="excel_txt" style="border-right:2px solid #aaa;mso-number-format:'0'">${dto.fiBankCount}</td>
	     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'">
	     	<fmt:formatNumber value="${dto.fiPrepaidCardAmount/1000}" pattern="0.00"/>
	     </td>
	     <td class="excel_txt" style="border-right:2px solid #aaa;mso-number-format:'0'">${dto.fiPrepaidCardCount}</td>
	     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'">
	      	<fmt:formatNumber value="${dto.fiConsumeCardAmount/1000}" pattern="0.00"/>
	     </td>
	     <td class="excel_txt" style="border-right:2px solid #aaa;mso-number-format:'0'">${dto.fiConsumeCardCount}</td>
	     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'">
	     	<fmt:formatNumber value="${dto.fiMobilePosAmount/1000}" pattern="0.00"/>
	     </td>
	     <td class="excel_txt" style="border-right:2px solid #aaa;mso-number-format:'0'">${dto.fiMobilePosCount}</td>
	     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'">
	     	<fmt:formatNumber value="${dto.fiDepositAmount/1000}" pattern="0.00"/>
	     </td>
	     <td class="excel_txt" style="border-right:2px solid #aaa;mso-number-format:'0'">${dto.fiDepositCount}</td>
	     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'">
	     	<fmt:formatNumber value="${dto.foAccInAmount/1000}" pattern="0.00"/>
	     </td>
	     <td class="excel_txt" style="border-right:2px solid #aaa;mso-number-format:'0'">${dto.foAccInCount}</td>
	     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'">
	     	<fmt:formatNumber value="${dto.bankWithholdingAmount/1000}" pattern="0.00"/>
	     </td>
	     <td class="excel_txt" style="border-right:2px solid #aaa;mso-number-format:'0'">${dto.bankWithholdingCount}</td>
	     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'">
	     	<fmt:formatNumber value="${dto.refund2AccAmount/1000}" pattern="0.00"/>
	     </td>
	     <td class="excel_txt" style="border-right:2px solid #aaa;mso-number-format:'0'">${dto.refund2AccCount}</td>
	     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'">
	     	<fmt:formatNumber value="${dto.fiAccOutAmount/1000}" pattern="0.00"/>
	     </td>
	     <td class="excel_txt" style="border-right:2px solid #aaa;mso-number-format:'0'">${dto.fiAccOutCount}</td>
	     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'">
	     	<fmt:formatNumber value="${dto.foAccOutAmount/1000}" pattern="0.00"/>
	     </td>
	     <td class="excel_txt" style="border-right:2px solid #aaa;mso-number-format:'0'">${dto.foAccOutCount}</td>
	     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'">
	     	<fmt:formatNumber value="${dto.foWithdrowAmount/1000}" pattern="0.00"/>
	     </td>
	     <td class="excel_txt" style="border-right:2px solid #aaa;mso-number-format:'0'">${dto.foWithdrowCount}</td>
	     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'">
	     	<fmt:formatNumber value="${dto.foBankAmount/1000}" pattern="0.00"/>
	     </td>
	     <td class="excel_txt" style="border-right:2px solid #aaa;mso-number-format:'0'">${dto.foBankCount}</td>
	     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'">
	     	<fmt:formatNumber value="${dto.refundAmount/1000}" pattern="0.00"/>
	     </td>
	     <td class="excel_txt" style="border-right:2px solid #aaa;mso-number-format:'0'">${dto.refundCount}</td>
	     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'">
	     	<fmt:formatNumber value="${dto.fiDepositRefundAmount/1000}" pattern="0.00"/>
	     </td>
	     <td class="excel_txt" style="border-right:2px solid #aaa;mso-number-format:'0'">${dto.fiDepositRefundCount}</td>
	     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'">
	     	<fmt:formatNumber value="${dto.allAmount/1000}" pattern="0.00"/>
	     </td>
		 <td class="excel_txt" style="mso-number-format:'0'"> ${dto.allCount}</td>
	  	</tr>
		</c:forEach>
	</tbody>
	</table>
</body>

