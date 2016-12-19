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

		function exportFile(totalCount) {
			if(totalCount <= 0){
				alert("无结果集,不能下载！");
			}
			else{
				if(!validateQuery()){
					return false;
				}
				document.getElementById("form1").method="POST";
				document.getElementById("form1").action="${ctx}/report/constingSerarch.do?method=download";
				document.getElementById("form1").submit();
			}
        }
</script>
</head>
<body>
	<c:if test='${error != null}'> 
		<font color="#FF0000">${error}</font>
	</c:if>
	<c:if test='${error == null}'>
	<div class="tableList">
	<a href="javascript:onClick=exportFile(${page.count});">下载查询结果</a>
	<table class="tablesorter" border="0" cellpadding="0" cellspacing="1" >
	<thead>
	  <tr class="tabT">
	    <th class="tabTitle" scope="col">日期</th>
	    <th class="tabTitle" scope="col">用户类型</th>
	    <c:choose>
	    	 <c:when test="${isSub == false}">    
	    	   	<th class="tabTitle" scope="col">会员号</th>
		    	<th class="tabTitle" scope="col">会员名称</th>
		   		<th class="tabTitle" scope="col">商户等级</th>
  		 	</c:when>
  		 	<c:otherwise>  	
  		 		<th class="tabTitle" scope="col">分子公司</th>
   			</c:otherwise>
		</c:choose>
	    <th class="tabTitle" scope="col">业务类型</th>
	    <th class="tabTitle" scope="col">交易量(元)</th>
	    <th class="tabTitle" scope="col">笔数(笔)</th>
	    <th class="tabTitle" scope="col">银行渠道</th>
	    <th class="tabTitle" scope="col">成本(元)</th>
	  </tr>
	</thead> 
	  	
	  <c:forEach items="${page.result}" var="dto">
	  	<tr>  
		    <td>&nbsp;${dto.date}</td>
		    <td>&nbsp;${dto.memberType}</td>
		    <c:choose>
		    	 <c:when test="${isSub == false}">    
		    	   	  <td>&nbsp;${dto.memberCode}</td>
			    	  <td>&nbsp;${dto.memberName}</td>
			          <td>&nbsp;${dto.memberLevel}</td>
	  		 	</c:when>
	  		 	<c:otherwise>  	
	  		 		 <td>&nbsp;${dto.subName}</td>
	   			</c:otherwise>
		    </c:choose>
		    <td>&nbsp;
		      <c:choose>
		    	 <c:when test="${dto.orderType == '1'}">    
		    	 	支付平台帐户充值
		    	 </c:when>
		    	 <c:when test="${dto.orderType == '2'}"> 
		    		   银行卡支付
		    	 </c:when>
		    	 <c:when test="${dto.orderType == '3'}">    
		    	 	提现
		    	 </c:when>
		    	 <c:when test="${dto.orderType == '4'}">    
		    	 	委托提现
		    	 </c:when>
		    	 <c:when test="${dto.orderType == '5'}">    
		    	 	单笔付款到银行
		    	 </c:when>
		    	 <c:when test="${dto.orderType == '6'}">    
		    	 	批量付款到银行
		    	 </c:when>
		    	 <c:when test="${dto.orderType == '7'}">    
		    	 	提现退票
		    	 </c:when>
		    	 <c:when test="${dto.orderType == '8'}">    
		    	 	直付到银行的退票
		    	 </c:when>
		    	 <c:when test="${dto.orderType == '9'}">    
		    	 	批量直付到银行退票
		    	 </c:when>
		    	 <c:when test="${dto.orderType == '10'}">    
		    	 	网关退款
		    	 </c:when>
		    	 <c:when test="${dto.orderType == '11'}">    
		    	 	充值退款
		    	 </c:when>
		    	 <c:otherwise>  	
	  		 		 其它 
	   			</c:otherwise>
		     </c:choose>
		    </td>
		    <td>&nbsp;
		   	 <fmt:formatNumber value="${dto.tradeValue}" pattern="#,##0.00"/>
		    </td>
		    <td>&nbsp;${dto.tradeCount} </td>
		    <td>&nbsp;${dto.channelItem} </td>
		    <td>&nbsp;
		   	 <fmt:formatNumber value="${dto.costValue}" pattern="#,##0.00"/>
		    </td>
	  	</tr>
	  </c:forEach>
	</table>
	<div id="showSum" align="left"> 汇总：交易量：<fmt:formatNumber value="${page.totalTradeVal}" pattern="#,##0.00"/>（元），笔数：${page.count}（笔），成本：<fmt:formatNumber value="${page.totalCostVal}" pattern="#,##0.00"/>(元)</div>
	<li:pagination methodName="userQuery" pageBean="${page}" sytleName="black2"/>
	</div>
	</c:if>
</body>
</html>
