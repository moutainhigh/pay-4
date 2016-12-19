<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=UTF-8">
</head>
<script type="text/javascript">
//$(document).ready(function() {
//});
</script>
<body>
	<table width="50%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
		<tr>
			<td height="18">
				<div align="center">
					<font class="titletext">水电煤，通讯账单销账处理结果</font>
				</div>
			</td>
		</tr>
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
	</table>
		
	<form action="" method="get" id="form1" name="form1" target="hideIfr" >
		
	<table class="inputTable" width="700" border="0" cellspacing="0" cellpadding="3" align="center">
    	<tr >
      		<th>订单日期：</th>
      		<td>
        		${orderDate }
      		</td>
    	</tr>
    	<tr>
      		<td colspan="2">
      		
			   <div style="float: left;width: 49%">
        		<table  class="tablesorter" border="0" cellpadding="0" cellspacing="1" >
			      <thead>
			       <tr>
			           <th style="text-align: center">销账成功的订单列表</th>
			            <th style="text-align: center">金额</th>
			         </tr>
			     </thead>
			      <tbody>
			      	
			      	 <c:set var="sAmount" value="0"></c:set>
				       <c:forEach items="${sueccesOrders}" var="sDto" >
				       <c:set var="sAmount" value="${sAmount+sDto.billAmount}"></c:set>
			          <tr>
			              <td>${sDto.billBarCode}</td>
			                <td>${sDto.billAmount/1000}</td>
			          </tr>
			        </c:forEach> 
			        <c:if test="${not empty sueccesOrders}">
				         <tr>
				              <td style="color:red">共${fn:length(sueccesOrders)}笔</td> <td style="color:red">共${sAmount/1000}元</td>
				          </tr> 
				          </c:if> 
			          <c:if test="${empty sueccesOrders}">
			        	 <tr>
			              <td colspan="2" style="color: red;text-align: center;">无销账成功的订单</td>
			          	</tr>
			        </c:if>	
			      </tbody>
			    </table>
			   </div>
			  
			   <div style="float: right;width: 49%">
			    <c:if test="${not empty refundSuccessOrders}">
			   	<div style="float: left;width: 100%">
	        		<table  class="tablesorter" border="0" cellpadding="0" cellspacing="1" >
				      <thead>
				       <tr>
				           <th style="text-align: center">退款成功条形码</th>
				           <th style="text-align: center">退款成功金额</th>
				         </tr>
				     </thead>
				      <tbody>
				      <c:set var="refundAmount" value="0"></c:set>
				       <c:forEach items="${refundSuccessOrders}" var="itemDto" >
				       <c:set var="refundAmount" value="${refundAmount+itemDto.billAmount}"></c:set>
				          <tr>
				              <td>${itemDto.billBarCode}</td> <td>${itemDto.billAmount/1000}元</td>
				          </tr>
				        </c:forEach> 
				         <c:if test="${not empty refundSuccessOrders}">
				         <tr>
				              <td style="color:red">共${fn:length(refundSuccessOrders)}笔</td> <td style="color:red">共${refundAmount/1000}元</td>
				          </tr> 
				          </c:if>
				        <c:if test="${empty refundSuccessOrders}">
				        	  <tr >
				              <td colspan="2" style="color: red;text-align: center;">无记录</td>
				          	</tr>
				        </c:if>	
				         
				      </tbody>
				    </table>
			   </div>
			   </c:if>
			    <c:if test="${not empty refundFailedOrders}">
			   <div style="float: left;width: 100%">
	        		<table  class="tablesorter" border="0" cellpadding="0" cellspacing="1" >
				      <thead>
				       <tr>
				           <th style="text-align: center">退款失败的条形码</th>
				           <th style="text-align: center">计划退款金额</th>
				         </tr>
				     </thead>
				      <tbody>
				      	<c:set var="refundAmount" value="0"></c:set>
				       <c:forEach items="${refundFailedOrders}" var="itemDto" >
				       <c:set var="refundAmount" value="${refundAmount+itemDto.billAmount}"></c:set>
				          <tr>
				              <td>${itemDto.billBarCode}</td> <td>${itemDto.billAmount/1000}元</td>
				          </tr>
				        </c:forEach> 
				         <c:if test="${not empty refundFailedOrders}">
				         <tr>
				              <td style="color:red">共${fn:length(refundFailedOrders)}笔</td> <td style="color:red">共${refundAmount/1000}元</td>
				          </tr> 
				          </c:if>
				        <c:if test="${empty refundFailedOrders}">
				        	 <tr >
				              <td colspan="2" style="color: red;text-align: center;">无记录</td>
				          	</tr>
				        </c:if>	
				         
				      </tbody>
			    </table>
			   </div>
			    </c:if>	
			    <c:if test="${not empty refundExceptionOrders}">
			    <div style="float: left;width: 100%">
	        		<table  class="tablesorter" border="0" cellpadding="0" cellspacing="1" >
				      <thead>
				       <tr>
				           <th style="text-align: center">退款异常的条形码</th>
				           <th style="text-align: center">计划退款金额</th>
				         </tr>
				     </thead>
				      <tbody>
				      	<c:set var="refundAmount" value="0"></c:set>
				       <c:forEach items="${refundExceptionOrders}" var="itemDto" >
				       <c:set var="refundAmount" value="${refundAmount+itemDto.billAmount}"></c:set>
				          <tr>
				              <td>${itemDto.billBarCode}</td> <td>${itemDto.billAmount/1000}元</td>
				          </tr>
				        </c:forEach> 
				         <c:if test="${not empty refundExceptionOrders}">
				         <tr>
				              <td style="color:red">共${fn:length(refundExceptionOrders)}笔</td> <td style="color:red">共${refundAmount/1000}元</td>
				          </tr> 
				          </c:if>
				        <c:if test="${empty refundExceptionOrders}">
				        	  <tr >
				              <td colspan="2" style="color: red;text-align: center;">无记录</td>
				          	</tr>
				        </c:if>	
				         
				      </tbody>
			    </table>
			   </div>
			     </c:if>	
			   </div>
			    
      		</td>
    	</tr>
    
  </table>
		<a href="${ctx}/app/sdmtxOrder.do">返回</a>

	</form>

</body>
</html>
