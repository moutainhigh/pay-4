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
	  	<th class="tabTitle" align="center"  rowspan="2">日期</th>
	  	<c:if test = "${optType==1}">
	  		<th class="tabTitle" align="center"  rowspan="2">会员编号</th>
  			<th class="tabTitle" align="center"  rowspan="2">会员名称</th>
	  	</c:if>
	  	<th class="tabTitle" align="center"  rowspan="2">所属销售</th>
	    <th class="tabTitle" align="center"  colspan="2">帐户入款</th>
	    <th class="tabTitle" align="center"  colspan="2">银行卡入款</th>
	    <th class="tabTitle" align="center"  colspan="2">预付卡入款</th>
	    <th class="tabTitle" align="center"  colspan="2">充值卡入款</th>
	    <th class="tabTitle" align="center"  colspan="2">手机POS入款</th>
	    <th class="tabTitle" align="center"  colspan="2">充值</th>
	    <th class="tabTitle" align="center"  colspan="2">转帐（入）</th>
	    <th class="tabTitle" align="center"  colspan="2">银行卡代扣</th>
	    <th class="tabTitle" align="center"  colspan="2">退票</th>
	    <th class="tabTitle" align="center"  colspan="2">账户支付</th>
	    <th class="tabTitle" align="center"  colspan="2">转账（出）</th>	    
	    <th class="tabTitle" align="center"  colspan="2">提现</th>
	    <th class="tabTitle" align="center"  colspan="2">转账到银行卡</th>
	    <th class="tabTitle" align="center"  colspan="2">退款</th>
	    <th class="tabTitle" align="center"  colspan="2">充退</th>
	    <th class="tabTitle" align="center"  colspan="2">信用卡快捷支付</th>
	    <th class="tabTitle" align="center"  colspan="2">借记卡快捷支付</th>
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
	    <th class="tabTitle" align="center" scope="col" style="border-right:2px solid #aaa">笔</th>
	    <th class="tabTitle" align="center" scope="col">金额（元）</th>
	    <th class="tabTitle" align="center" scope="col" style="border-right:2px solid #aaa">笔</th>
	    <th class="tabTitle" align="center" scope="col">金额（元）</th>
	    <th class="tabTitle" align="center" scope="col" style="border-right:2px solid #aaa">笔</th>
	    <th class="tabTitle" align="center" scope="col">金额（元）</th>
	    <th class="tabTitle" align="center" scope="col">笔</th>
	  </tr>
	</thead> 
	  <c:forEach items="${page.result}" var="dto">
	  <tr>
	  	<td style="border-right:2px solid #aaa" >&nbsp;
	  		<fmt:formatDate value="${dto.reportDate}" type="date"/>
	    </td>
	    <c:if test = "${optType==1}">
		    <td style="border-right:2px solid #aaa" >&nbsp;
		    	${dto.memberCode}
		    </td>
		    <td style="border-right:2px solid #aaa" >&nbsp;
		    	${dto.name}
		    </td>
	    </c:if>
	     <td style="border-right:2px solid #aaa" >&nbsp;
		    	${dto.signName}
		   </td>
	    <td>&nbsp;
	    	<fmt:formatNumber value="${dto.fiAccInAmount/1000}" pattern="#,##0.00"/>
	    </td>
	     <td style="border-right:2px solid #aaa" >&nbsp;
		     <c:if test='${dto.fiAccInCount == null}'> 
				0
			</c:if>
		     ${dto.fiAccInCount}
	     </td>
	     <td>&nbsp;
	     	<fmt:formatNumber value="${dto.fiBankAmount/1000}" pattern="#,##0.00"/>
	     </td>
	     <td style="border-right:2px solid #aaa">&nbsp;
	     	<c:if test='${dto.fiBankCount == null}'> 
	     		0
	     	</c:if>
	     	${dto.fiBankCount}
	     </td>
	     <td>&nbsp;
	     	<fmt:formatNumber value="${dto.fiPrepaidCardAmount/1000}" pattern="#,##0.00"/>
	     </td>
	      <td style="border-right:2px solid #aaa">&nbsp;
	      	<c:if test='${dto.fiPrepaidCardCount == null}'> 
	     		0
	     	</c:if>
	      ${dto.fiPrepaidCardCount}
	      </td>
	      <td>&nbsp;
	     	<fmt:formatNumber value="${dto.fiConsumeCardAmount/1000}" pattern="#,##0.00"/>
	     </td>
	     <td style="border-right:2px solid #aaa">&nbsp;
	     	<c:if test='${dto.fiConsumeCardCount == null}'> 
	     		0
	     	</c:if>
	     ${dto.fiConsumeCardCount}</td>
	     <td>&nbsp;
	     	<fmt:formatNumber value="${dto.fiMobilePosAmount/1000}" pattern="#,##0.00"/>
	     </td>
	     <td style="border-right:2px solid #aaa">&nbsp;
	     	<c:if test='${dto.fiMobilePosCount == null}'> 
	     		0
	     	</c:if>
	     	${dto.fiMobilePosCount}
	     </td>
	     <td>&nbsp;
	     	<fmt:formatNumber value="${dto.fiDepositAmount/1000}" pattern="#,##0.00"/>
	     </td>
	     <td style="border-right:2px solid #aaa">&nbsp;
	     	<c:if test='${dto.fiDepositCount == null}'> 
	     		0
	     	</c:if>
	     	${dto.fiDepositCount}
	     </td>
	     <td>&nbsp;
	     	<fmt:formatNumber value="${dto.foAccInAmount/1000}" pattern="#,##0.00"/>
	     </td>
	     <td style="border-right:2px solid #aaa">&nbsp;
	     	<c:if test='${dto.foAccInCount == null}'> 
	     		0
	     	</c:if>
	     	${dto.foAccInCount}
	     </td>
	     <td>&nbsp;
	     	<fmt:formatNumber value="${dto.bankWithholdingAmount/1000}" pattern="#,##0.00"/>
	     </td>
	     <td style="border-right:2px solid #aaa">&nbsp;
	     	<c:if test='${dto.bankWithholdingCount == null}'> 
	     		0
	     	</c:if>
	     	${dto.bankWithholdingCount}
	     </td>
	     <td>&nbsp;
	     	<fmt:formatNumber value="${dto.refund2AccAmount/1000}" pattern="#,##0.00"/>
	     </td>
	     <td style="border-right:2px solid #aaa">&nbsp;
	     	<c:if test='${dto.refund2AccCount == null}'> 
	     		0
	     	</c:if>
	     	${dto.refund2AccCount}
	     </td>
	     <td>&nbsp;
	     	<fmt:formatNumber value="${dto.fiAccOutAmount/1000}" pattern="#,##0.00"/>
	     </td>
	     <td style="border-right:2px solid #aaa">&nbsp;
	     	<c:if test='${dto.fiAccOutCount == null}'> 
	     		0
	     	</c:if>
	     	${dto.fiAccOutCount}
	     </td>
	     <td>&nbsp;
	     	<fmt:formatNumber value="${dto.foAccOutAmount/1000}" pattern="#,##0.00"/>
	     </td>
	     <td style="border-right:2px solid #aaa">&nbsp;
	     	<c:if test='${dto.foAccOutCount == null}'> 
	     		0
	     	</c:if>
	     	${dto.foAccOutCount}
	     </td>
	     <td>&nbsp;
	     	<fmt:formatNumber value="${dto.foWithdrowAmount/1000}" pattern="#,##0.00"/>
	     </td>
	     <td style="border-right:2px solid #aaa">&nbsp;
	     	<c:if test='${dto.foWithdrowCount == null}'> 
	     		0
	     	</c:if>
	     	${dto.foWithdrowCount}
	     </td>
	     <td>&nbsp;
	     	<fmt:formatNumber value="${dto.foBankAmount/1000}" pattern="#,##0.00"/>
	     </td>
	     <td style="border-right:2px solid #aaa">&nbsp;
	     	<c:if test='${dto.foBankCount == null}'> 
	     		0
	     	</c:if>
	     	${dto.foBankCount}
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
	     	<fmt:formatNumber value="${dto.fiDepositRefundAmount/1000}" pattern="#,##0.00"/>
	     </td>
	     <td style="border-right:2px solid #aaa">&nbsp;
	     	<c:if test='${dto.fiDepositRefundCount == null}'> 
	     		0
	     	</c:if>
	     	${dto.fiDepositRefundCount}
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
	     	<fmt:formatNumber value="${dto.allAmount/1000}" pattern="#,##0.00"/>
	     </td>
		 <td>&nbsp; ${dto.allCount}</td>
	  	</tr>
	  </c:forEach>
	</table>
	<div class="e_cur_tit2"><i class="fr"><a href="javascript:exportExcel(${page.totalCount});"><input class="button2" type="button" value="下载"></a></i><span></span></div>
	<li:pagination methodName="userQuery" pageBean="${page}" sytleName="black2"/>
	</div>
	</c:if>
</body>
</html>
