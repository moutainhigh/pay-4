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
	  		<th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">交易时间</th>
	  		<th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">商户订单号</th>
		  	<th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">网关订单号</th>
		  	<th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">支付订单号</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">渠道订单号</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">渠道号</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">所属渠道</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">商户号</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">交易金额</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">交易币种</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">支付汇率</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">支付金额</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">支付币种</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">清算币种</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">清算汇率</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">手续费汇率</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">二级渠道号</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">授权码</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">渠道结果</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">渠道支付金额</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">渠道支付币种</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">银行比例手续费</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">银行比例手续费币种</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">银行固定手续费</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">银行固定手续费币种</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">银行存款入账</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">银行存款入账币种</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">支付币种兑CNY汇率</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">渠道支付金额（CNY）</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">银行固定手续费（CNY）</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">银行比例手续费（CNY）</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">清算金额</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">基本户</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">保证金</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">比例费</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">固定费</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">清算币种兑CNY汇率</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">清算金额（CNY）</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">基本户（CNY）</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">保证金（CNY）</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">比例费（CNY）</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">固定费（CNY）</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">汇差</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">汇差收益率</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">总收入</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">毛利</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">毛利率</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">清算日期</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">数据有效性</th>
		    <th class="tabTitle" align="center" style="border-right:2px solid #aaa" scope="col" colspan="1">是否DCC</th>
		  </tr>
		</thead>
		<tbody>
			<c:forEach items="${page.result}" var="dto">
				<tr>
					<td>${dto.createTime }</td>							
					<td>${dto.orderId }</td>               
					<td>${dto.tradeOrderNo }</td>          
					<td>${dto.paymentOrderNo }</td>        
					<td>${dto.channelOrderNo }</td>        
					<td>${dto.orgCode }</td>               
					<td>${dto.orgName }</td>               
					<td>${dto.partnerId }</td>             
					<td>${dto.tranAmount }</td>            
					<td>${dto.tranCurrencyCode }</td>      
					<td>${dto.payRate }</td>               
					<td>${dto.payAmount }</td>             
					<td>${dto.payCurrencyCode }</td>       
					<td>${dto.settlementCurrencyCode }</td>
					<td>${dto.settlementRate }</td>        
					<td>${dto.feeRate }</td>               
					<td>${dto.merchantNo }</td>            
					<td>${dto.authorisation }</td>         
					<td>${dto.status }</td>   
					<!-- 渠道支付金额，取渠道订单中的支付金额 -->             
					<td>${dto.payAmount }</td> 
					<!-- 渠道支付币种，取渠道订单中的支付币种 -->        
					<td>${dto.payCurrencyCode }</td>         
					<td>${dto.bankPerFee }</td>            
					<td>${dto.bankPerCurrencyCode }</td>   
					<td>${dto.bankFixedFee }</td>          
					<td>${dto.bankFixedCurrencyCode }</td> 
					<td>${dto.bankAmount }</td>            
					<td>${dto.bankCurrencyCode }</td>              
					<td>${dto.payCnyRate }</td>   
					<td>${dto.bankPayAmountCny }</td>   
					<td>${dto.bankFixedFeeCny }</td>       
					<td>${dto.bankPerFeeCny }</td>         
					<td>${dto.settlementAmount }</td>      
					<td>${dto.baseAmount }</td>            
					<td>${dto.assureAmount }</td>          
					<td>${dto.perFee }</td>                
					<td>${dto.fixedFee }</td>              
					<td>${dto.settlementCnyRate }</td>     
					<td>${dto.settlementAmountCny }</td>   
					<td>${dto.baseAmountCny }</td>         
					<td>${dto.assureAmountCny }</td>       
					<td>${dto.perFeeCny }</td>                     
					<td>${dto.fixedFeeCny }</td>           
					<td>${dto.rateIncome }</td>            
					<td>${dto.rateRate }</td>              
					<td>${dto.totalIncome }</td>           
					<td>${dto.profit }</td>                
					<td>${dto.profitRate }</td>            
					<td>${dto.settlementDate }</td>        
					<td>${dto.flag}</td>                   
					<td>${dto.payType}</td>                 
				</tr>
			</c:forEach>
		</tbody> 
	</table>
	<div class="e_cur_tit2"><i class="fr"><a href="javascript:exportExcel(${page.totalCount});"><input class="button2" type="button" value="下载"></a></i><span></span></div>
		<li:pagination methodName="userQuery" pageBean="${page}" sytleName="black2"/>
	</div>
	</c:if>
</body>
</html>
