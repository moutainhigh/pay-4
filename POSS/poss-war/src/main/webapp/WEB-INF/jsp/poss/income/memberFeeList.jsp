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
	
	<div class="e_cur_tit2">
		<i class="fr"><a href="javascript:exportExcel(${page.totalCount});">下载</a></i><span></span>
	</div>
	<div class="tableList">
	<table class="tablesorter" border="0" cellpadding="0" cellspacing="1" >
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
	    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="2">信用卡快捷支付手续费</th>
	    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="2">借记卡快捷支付手续费</th>
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
	    <th class="tabTitle" align="center" scope="col" style="border-right:2px solid #aaa">笔</th>
	    <th class="tabTitle" align="center" scope="col">金额（元）</th>	
	    <th class="tabTitle" align="center" scope="col" style="border-right:2px solid #aaa">笔</th>			
	    <th class="tabTitle" align="center" scope="col">金额（元）</th>
	    <th class="tabTitle" align="center" scope="col">笔</th>
	  </tr>
	</thead>
	  <c:forEach items="${page.result}" var="dto">
	  <tr>
	  	<td align="center" style="border-right:2px solid #aaa">
	     	<c:if test='${dto.memberType == 1}' var='flg'> 
	     		个人
	     	</c:if>
	     	<c:if test='${flg == false}'> 
	     		企业
	     	</c:if>
	    </td>
	    <td style="border-right:2px solid #aaa">
	    	${dto.memberCode}
	    </td>
	    <td style="border-right:2px solid #aaa">
	    	${dto.name}
	    </td>
	    <td style="border-right:2px solid #aaa">
	    	${dto.innerMemberName}
	    </td>
	    <td align="right">
	    	<fmt:formatNumber value="${dto.fiDepositRefundFee/1000}" pattern="#,##0.00"/>
	    </td>
	     <td align="right" style="border-right:2px solid #aaa">
		     <c:if test='${dto.fiDepositRefundCount == null}'> 
				0
			</c:if>
		     ${dto.fiDepositRefundCount}
	     </td>
	     <td align="right">
	     	<fmt:formatNumber value="${dto.foWithdrowFee/1000}" pattern="#,##0.00"/>
	     </td>
	     <td align="right" style="border-right:2px solid #aaa">
	     	<c:if test='${dto.foWithdrowCount == null}'> 
	     		0
	     	</c:if>
	     	${dto.foWithdrowCount}
	     </td>
	     <td align="right">
	     	<fmt:formatNumber value="${dto.foAccOutFee/1000}" pattern="#,##0.00"/>
	     </td>
	     <td align="right" style="border-right:2px solid #aaa">
	      	<c:if test='${dto.foAccOutCount == null}'> 
	     		0
	     	</c:if>
	      	${dto.foAccOutCount}
	     </td>
	     <td align="right">
	     	<fmt:formatNumber value="${dto.refundFee/1000}" pattern="#,##0.00"/>
	     </td>
	     <td align="right" style="border-right:2px solid #aaa">
	     	<c:if test='${dto.refundCount == null}'> 
	     		0
	     	</c:if>
	    	${dto.refundCount}</td>
	     <td align="right">
	     	<fmt:formatNumber value="${dto.fiMobilePosFee/1000}" pattern="#,##0.00"/>
	     </td>
	     <td align="right" style="border-right:2px solid #aaa">
	     	<c:if test='${dto.fiMobilePosCount == null}'> 
	     		0
	     	</c:if>
	     	${dto.fiMobilePosCount}
	     </td>
	     <td align="right">
	     	<fmt:formatNumber value="${dto.fiDepositFee/1000}" pattern="#,##0.00"/>
	     </td>
	     <td align="right" style="border-right:2px solid #aaa">
	     	<c:if test='${dto.fiDepositCount == null}'> 
	     		0
	     	</c:if>
	     	${dto.fiDepositCount}
	     </td>
	     <td align="right">
	     	<fmt:formatNumber value="${dto.foBankFee/1000}" pattern="#,##0.00"/>
	     </td>
		 <td align="right" style="border-right:2px solid #aaa">
	     	<c:if test='${dto.foBankCount == null}'> 
	     		0
	     	</c:if>
	     	${dto.foBankCount}
	     </td>
		 
		 <td align="right">
	     	<fmt:formatNumber value="${dto.fiAccInFee/1000}" pattern="#,##0.00"/>
	     </td>
	     <td align="right" style="border-right:2px solid #aaa">
	     	<c:if test='${dto.fiAccInCount == null}'> 
	     		0
	     	</c:if>
	     	${dto.fiAccInCount}
	     </td>
	     <td align="right">
	     	<fmt:formatNumber value="${dto.bankWithholdingFee/1000}" pattern="#,##0.00"/>
	     </td>
	     <td align="right" style="border-right:2px solid #aaa">
	      	<c:if test='${dto.bankWithholdingCount == null}'> 
	     		0
	     	</c:if>
	      	${dto.bankWithholdingCount}
	     </td>
	     <td align="right">
	     	<fmt:formatNumber value="${dto.fiBankFee/1000}" pattern="#,##0.00"/>
	     </td>
	     <td align="right" style="border-right:2px solid #aaa">
	     	<c:if test='${dto.fiBankCount == null}'> 
	     		0
	     	</c:if>
	    	${dto.fiBankCount}</td>
	     <td align="right">
	     	<fmt:formatNumber value="${dto.fiAccOutFee/1000}" pattern="#,##0.00"/>
	     </td>
	     <td align="right" style="border-right:2px solid #aaa">
	     	<c:if test='${dto.fiAccOutCount == null}'> 
	     		0
	     	</c:if>
	     	${dto.fiAccOutCount}
	     </td>
	     <td align="right">
	     	<fmt:formatNumber value="${dto.fiPrepaidCardFee/1000}" pattern="#,##0.00"/>
	     </td>
	     <td align="right" style="border-right:2px solid #aaa">
	     	<c:if test='${dto.fiPrepaidCardCount == null}'> 
	     		0
	     	</c:if>
	     	${dto.fiPrepaidCardCount}
	     </td>
	     
	     <td align="right">
	     	<fmt:formatNumber value="${dto.refund2AccFee/1000}" pattern="#,##0.00"/>
	     </td>
	     <td align="right" style="border-right:2px solid #aaa">
	     	<c:if test='${dto.refund2AccCount == null}'> 
	     		0
	     	</c:if>
	    	${dto.refund2AccCount}</td>
	     <td align="right">
	     	<fmt:formatNumber value="${dto.foAccInFee/1000}" pattern="#,##0.00"/>
	     </td>
	     <td align="right" style="border-right:2px solid #aaa">
	     	<c:if test='${dto.foAccInCount == null}'> 
	     		0
	     	</c:if>
	     	${dto.foAccInCount}
	     </td>
	     <td align="right">
	     	<fmt:formatNumber value="${dto.fiConsumeCardFee/1000}" pattern="#,##0.00"/>
	     </td>
	     <td align="right" style="border-right:2px solid #aaa">
	     	<c:if test='${dto.fiConsumeCardCount == null}'> 
	     		0
	     	</c:if>
	     	${dto.fiConsumeCardCount}
	     </td>
	     <td align="right">
	     	<fmt:formatNumber value="${dto.creditQuickPayFee/1000}" pattern="#,##0.00"/>
	     </td>
	     <td align="right" style="border-right:2px solid #aaa">
	     	<c:if test='${dto.creditQuickPayCount == null}'> 
	     		0
	     	</c:if>
	     	${dto.creditQuickPayCount}
	     </td>
	     <td align="right">
	     	<fmt:formatNumber value="${dto.debitQuickPayFee/1000}" pattern="#,##0.00"/>
	     </td>
	     <td align="right" style="border-right:2px solid #aaa">
	     	<c:if test='${dto.debitQuickPayCount == null}'> 
	     		0
	     	</c:if>
	     	${dto.debitQuickPayCount}
	     </td>
	     <td align="right">
	     	<fmt:formatNumber value="${dto.allFee/1000}" pattern="#,##0.00"/>
	     </td>
		 <td align="right"> ${dto.allCount}</td>
	  	</tr>
	  </c:forEach>
	</table>
	<li:pagination methodName="userQuery" pageBean="${page}" sytleName="black2"/>
	</div>
	</c:if>
</body>
</html>
