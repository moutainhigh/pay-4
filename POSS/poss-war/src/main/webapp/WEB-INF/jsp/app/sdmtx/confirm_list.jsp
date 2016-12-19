<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=UTF-8">
</head>
<script type="text/javascript">
$(document).ready(function() {
	
});
function form1Confirm(){
	if(confirm("确认销账和退款吗，如果确认将执行销账或退款")){
		return true;
	}
	return false;
	
}

</script>
<body>
	<table width="50%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
		<tr>
			<td height="18">
				<div align="center">
					<font class="titletext">水电煤，通讯账单销账确认</font>
				</div>
			</td>
		</tr>
		<tr>
			<td height="1" bgcolor="#000000"></td>
		</tr>
	</table>
		
	<form action="${ctx}/app/sdmtxOrderProcess.do" method="post" id="form1" name="form1"  onsubmit="return form1Confirm() " >
		
	<table class="inputTable" width="700" border="0" cellspacing="0" cellpadding="3" align="center">
    	<tr >
      		<th>订单日期：</th>
      		<td>
        		${orderDate }
      		</td>
    	</tr>
   		<tr >
      		<th>是否有退款文件</th>
      		<td>
        		${hasRefundFile?refundFileName:"无"}
      		</td>
    	</tr>
    	<c:if test="${hasRefundFile}">
    	<tr>
      		
      		<td colspan="2" style="text-align: center">
      	
      		<div style="float: left;width: 49%">
        		<table  class="tablesorter" border="0" cellpadding="0" cellspacing="1" >
			      <thead>
			       <tr>
			           <th style="text-align: center">提交文件中将退款的条形码</th>
			           <th style="text-align: center">退款金额</th>
			         </tr>
			     </thead>
			      <tbody>
			      	<c:set var="refundAmount" value="0"></c:set>
			       <c:forEach items="${refundBarCodeList}" var="itemDto" >
			       <c:set var="refundAmount" value="${refundAmount+itemDto.billAmount}"></c:set>
			          <tr>
			              <td>${itemDto.billBarCode}</td> <td>${itemDto.billAmount/1000}元</td>
			          </tr>
			        </c:forEach> 
			         <c:if test="${not empty refundBarCodeList}">
			         <tr>
			              <td style="color:red">共${fn:length(refundBarCodeList)}笔</td> <td style="color:red">共${refundAmount/1000}元</td>
			          </tr> 
			          </c:if>
			        <c:if test="${empty refundBarCodeList}">
			        	  <tr >
			              <td colspan="2" style="color: red;text-align: center;">无记录</td>
			          	</tr>
			        </c:if>	
			         
			      </tbody>
			    </table>
			   </div>
			
			 <c:if test="${not empty noFoundBarCodeList}">
			   <div style="float: right;width: 49%">
        		<table  class="tablesorter" border="0" cellpadding="0" cellspacing="1" >
			      <thead>
			       <tr>
			           <th style="text-align: center">提交文件中未找到的退款条形码（确认日期是否正确）</th>
			         </tr>
			     </thead>
			      <tbody>
			      	
			       <c:forEach items="${noFoundBarCodeList}" var="barcode" >
			          <tr>
			              <td>${barcode}</td>
			          </tr>
			        </c:forEach>  
			          <c:if test="${empty noFoundBarCodeList}">
			        	 <tr>
			              <td style="color: red;text-align: center;">无记录</td>
			          	</tr>
			       </c:if>	
			      </tbody>
			    </table>
			   </div>
			   </c:if>
			
      		</td>
    	</tr>
    	</c:if>
    	<tr >
      		<td colspan="2" style="text-align: center">
          		<input type="submit" name="submit" value="确认处理销账和退款"   />
          		<a href="${ctx}/app/sdmtxOrder.do">返回修改</a>
      		</td>
    	</tr>
  </table>
		
	<input type="hidden" name="orderDate" value="${orderDate }" />	
	<input type="hidden" name="processSubmitId" value="${processSubmitId }" />	
	<c:forEach items="${refundBarCodeList}" var="itemDto" >
	<input type="hidden" name="refundSeqId" value="${itemDto.sequenceId }" />	
	</c:forEach> 
	</form>
   <iframe style="display:none" id="hideIfr" name="hideIfr" src=""></iframe>
</body>
</html>
