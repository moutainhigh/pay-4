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
	<a href="${ctx}/if_poss_query/performanceReport.do?method=downloadRecordList&startDate=${accTime}">下  载</a>
	<table class="tablesorter" border="0" cellpadding="0" cellspacing="1" >
	
	<thead> 
	  <tr>
	    <th class="tabTitle" align="center"  rowspan="2" >商户编号</th>
	    <th class="tabTitle" align="center"  rowspan="2" >商户名称</th>
	    <th class="tabTitle" align="center"  rowspan="2" >交易结算时间</th>
	    <th class="tabTitle" align="center"  colspan="2">帐户支付网关交易</th>
	    <th class="tabTitle" align="center"  colspan="2">信用卡大额网关交易</th>
	    <th class="tabTitle" align="center"  colspan="2">B2C网关交易</th>
	    <th class="tabTitle" align="center"  colspan="2">B2B网关交易</th>
        <th class="tabTitle" align="center"  colspan="2">付款至银行卡</th>
        <th class="tabTitle" align="center"  colspan="2">平台内账户内转费用</th>
        <th class="tabTitle" align="center"  colspan="2">预付卡网关交易</th>
        <th class="tabTitle" align="center"  colspan="2">充值卡网关交易</th>
        <th class="tabTitle" align="center"  colspan="2">信用卡快捷支付网关交易</th>
        <th class="tabTitle" align="center"  colspan="2">借记卡快捷支付网关交易</th>
        <th class="tabTitle" align="center"  colspan="2">合计</th>
     </tr>
     <tr>
     	 <th class="tabTitle" align="center" >交易量</th>
	     <th class="tabTitle" align="center" >手续费收入</th>
	     <th class="tabTitle" align="center" >交易量</th>
	     <th class="tabTitle" align="center" >手续费收入</th>
	     <th class="tabTitle" align="center" >交易量</th>
	     <th class="tabTitle" align="center" >手续费收入</th>
	     <th class="tabTitle" align="center" >交易量</th>
	     <th class="tabTitle" align="center" >手续费收入</th>
	     <th class="tabTitle" align="center" >交易量</th>
	     <th class="tabTitle" align="center" >手续费收入</th>
	     <th class="tabTitle" align="center" >交易量</th>
	     <th class="tabTitle" align="center" >手续费收入</th>
	     <th class="tabTitle" align="center" >交易量</th>
	     <th class="tabTitle" align="center" >手续费收入</th>
	     <th class="tabTitle" align="center" >交易量</th>
	     <th class="tabTitle" align="center" >手续费收入</th>
	     <th class="tabTitle" align="center" >交易量</th>
	     <th class="tabTitle" align="center" >手续费收入</th>
	     <th class="tabTitle" align="center" >交易量</th>
	     <th class="tabTitle" align="center" >手续费收入</th>
	     <th class="tabTitle" align="center" >交易量</th>
	     <th class="tabTitle" align="center" >手续费收入</th>
      </tr>
	</thead> 
	  <c:forEach items="${list}" var="dto">
	  <tr>
	  	 <td>&nbsp;${dto.memberCode}</td>
	  	 <td>&nbsp;${dto.memberName}</td>
	  	 <td>&nbsp;${accTime}</td>
	  	 <td>&nbsp;${dto.accPayAmt}</td> 
	     <td>&nbsp;${dto.accPayFee}</td>
	     <td>&nbsp;${dto.lccAmt}</td> 
	     <td>&nbsp;${dto.lccFee}</td>
	  	 <td>&nbsp;${dto.b2cAmt}</td> 
	     <td>&nbsp;${dto.b2cFee}</td>
	  	 <td>&nbsp;${dto.b2bAmt}</td> 
	     <td>&nbsp;${dto.b2bFee}</td>
	     <td>&nbsp;${dto.p2bAmt}</td> 
	     <td>&nbsp;${dto.p2bFee}</td>
	     <td>&nbsp;${dto.p2aAmt}</td> 
	     <td>&nbsp;${dto.p2aFee}</td>
	     <td>&nbsp;${dto.cecAmt}</td> 
	     <td>&nbsp;${dto.cecFee}</td>
	     <td>&nbsp;${dto.rcAmt}</td> 
	     <td>&nbsp;${dto.rcFee}</td>
	     <td>&nbsp;${dto.cqpAmt}</td> 
	     <td>&nbsp;${dto.cqpFee}</td>
	     <td>&nbsp;${dto.dqpAmt}</td> 
	     <td>&nbsp;${dto.dqpFee}</td>
	     <td>&nbsp;${dto.allAmt}</td> 
	     <td>&nbsp;${dto.allFee}</td>
	  	</tr>
	  </c:forEach>
	</table>
	
	</div>
	</c:if>
</body>
</html>
