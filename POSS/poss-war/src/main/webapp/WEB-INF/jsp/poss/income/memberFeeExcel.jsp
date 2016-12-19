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
	  	<th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" rowspan="2">用户类型</th>
	  	<th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" rowspan="2">会员编号</th>
	  	<th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" rowspan="2">会员名称</th>
		<th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" rowspan="2">会员所属分子公司</th>
	    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="2">充退手续费</th>
	    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="2">提现手续费</th>
	    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="2">转账(出)手续费</th>
	    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="2">退款手续费</th>
	    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="2">手机POS入款手续费</th>
	    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="2">充值手续费</th>
	    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="2">转账到银行卡手续费</th>
	    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="2">账户入款手续费</th>
	    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="2">银行卡代扣手续费</th>
	    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="2">银行卡入款手续费</th>
	    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="2">账户支付手续费</th>
	    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="2">预付卡入款手续费</th>
	    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="2">退票手续费</th>
	    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="2">转账(入)手续费</th>
	    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="2">充值卡入款手续费</th>
	    <th class="tabTitle" align="center" scope="col" colspan="2">合计手续费收入</th>
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
	    <th class="tabTitle" align="center" scope="col" style="border-right:2px solid #aaa">笔</th>		
	    <th class="tabTitle" align="center" scope="col">金额（元）</th>
	    <th class="tabTitle" align="center" scope="col">笔</th>
	  </tr>
	</thead>
	<tbody>
		<c:forEach items="${result}" var="dto">
		 <tr class="trForContent1 tr_hover">
	     <td class="excel_txt" style="border-right:2px solid #aaa">
	     	<c:if test='${dto.memberType == 1}' var='flg'> 
	     		个人
	     	</c:if>
	     	<c:if test='${flg == false}'> 
	     		企业
	     	</c:if>
		 </td>
	     <td class="excel_txt" style="border-right:2px solid #aaa">${dto.memberCode}</td>
	     <td class="excel_txt" style="border-right:2px solid #aaa">${dto.name}</td>
	     <td class="excel_txt" style="border-right:2px solid #aaa">${dto.innerMemberName}</td>
	     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'"><fmt:formatNumber value="${dto.fiDepositRefundFee/1000}" pattern="0.00"/></td>
	     <td class="excel_txt" style="border-right:2px solid #aaa;mso-number-format:'0'">${dto.fiDepositRefundCount}</td>
	     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'" ><fmt:formatNumber value="${dto.foWithdrowFee/1000}" pattern="0.00"/></td>
	     <td class="excel_txt" style="border-right:2px solid #aaa;mso-number-format:'0'">${dto.foWithdrowCount}</td>
	     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'">
	     	<fmt:formatNumber value="${dto.foAccOutFee/1000}" pattern="0.00"/>
	     </td>
	     <td class="excel_txt" style="border-right:2px solid #aaa;mso-number-format:'0'">${dto.foAccOutCount}</td>
	     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'">
	      	<fmt:formatNumber value="${dto.refundFee/1000}" pattern="0.00"/>
	     </td>
	     <td class="excel_txt" style="border-right:2px solid #aaa;mso-number-format:'0'">${dto.refundCount}</td>
	     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'">
	     	<fmt:formatNumber value="${dto.fiMobilePosFee/1000}" pattern="0.00"/>
	     </td>
	     <td class="excel_txt" style="border-right:2px solid #aaa;mso-number-format:'0'">${dto.fiMobilePosCount}</td>
	     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'">
	     	<fmt:formatNumber value="${dto.fiDepositFee/1000}" pattern="0.00"/>
	     </td>
	     <td class="excel_txt" style="border-right:2px solid #aaa;mso-number-format:'0'">${dto.fiDepositCount}</td>
	     
	     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'">
	     	<fmt:formatNumber value="${dto.foBankFee/1000}" pattern="0.00"/>
	     </td>
	     <td class="excel_txt" style="border-right:2px solid #aaa;mso-number-format:'0'">${dto.foBankCount}</td>
	     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'">
	      	<fmt:formatNumber value="${dto.fiAccInFee/1000}" pattern="0.00"/>
	     </td>
	     <td class="excel_txt" style="border-right:2px solid #aaa;mso-number-format:'0'">${dto.fiAccInCount}</td>
	     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'">
	     	<fmt:formatNumber value="${dto.bankWithholdingFee/1000}" pattern="0.00"/>
	     </td>
	     <td class="excel_txt" style="border-right:2px solid #aaa;mso-number-format:'0'">${dto.bankWithholdingCount}</td>
	     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'">
	     	<fmt:formatNumber value="${dto.fiBankFee/1000}" pattern="0.00"/>
	     </td>
	     <td class="excel_txt" style="border-right:2px solid #aaa;mso-number-format:'0'">${dto.fiBankCount}</td>
	     
	     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'">
	     	<fmt:formatNumber value="${dto.fiAccOutFee/1000}" pattern="0.00"/>
	     </td>
	     <td class="excel_txt" style="border-right:2px solid #aaa;mso-number-format:'0'">${dto.fiAccOutCount}</td>
	     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'">
	      	<fmt:formatNumber value="${dto.fiPrepaidCardFee/1000}" pattern="0.00"/>
	     </td>
	     <td class="excel_txt" style="border-right:2px solid #aaa;mso-number-format:'0'">${dto.fiPrepaidCardCount}</td>
	     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'">
	     	<fmt:formatNumber value="${dto.refund2AccFee/1000}" pattern="0.00"/>
	     </td>
	     <td class="excel_txt" style="border-right:2px solid #aaa;mso-number-format:'0'">${dto.refund2AccCount}</td>
	     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'">
	     	<fmt:formatNumber value="${dto.foAccInFee/1000}" pattern="0.00"/>
	     </td>
	     <td class="excel_txt" style="border-right:2px solid #aaa;mso-number-format:'0'">${dto.foAccInCount}</td>
	     <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'">
	     	<fmt:formatNumber value="${dto.fiConsumeCardFee/1000}" pattern="0.00"/>
	     </td>
		 <td class="excel_txt" style="mso-number-format:'0'">${dto.fiConsumeCardCount}</td>
		 <td class="excel_txt" style="mso-number-format:'\#\,\#\#0\.00'">
	     	<fmt:formatNumber value="${dto.allFee/1000}" pattern="0.00"/>
	     </td>
		 <td class="excel_txt" style="mso-number-format:'0'">${dto.allCount}</td>
	  	</tr>
		</c:forEach>
	</tbody>
  </table>
</body>

