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
	  		<th class="tabTitle" align="center">交易类型</th>
		  	<th class="tabTitle" align="center">渠道号</th>
		  	<th class="tabTitle" align="center">日期</th>
		    <th class="tabTitle" align="center">会员号</th>
		    <th class="tabTitle" align="center">笔数</th>
		    <th class="tabTitle" align="center">渠道支付币种</th>
		    <th class="tabTitle" align="center">渠道支付金额</th>
		    <th class="tabTitle" align="center">手续费支出</th>
		    <th class="tabTitle" align="center">银行存款入账 </th>
		    <th class="tabTitle" align="center">清算币种</th>
		    <th class="tabTitle" align="center">清算金额 </th>
		    <th class="tabTitle" align="center">基本户清算</th>
		    <th class="tabTitle" align="center">保证金清算</th>
		    <th class="tabTitle" align="center">比例费收入</th>	    
		    <th class="tabTitle" align="center">固定费收入</th>
		    <th class="tabTitle" align="center">汇差收入</th>
		    <th class="tabTitle" align="center">总收入</th>
		    <th class="tabTitle" align="center">毛利</th>
		    <th class="tabTitle" align="center">毛利率</th>
	  </tr>
	</thead> 
	  <c:forEach items="${page.result}" var="dto" varStatus = "personalStatus">
		<c:choose>
	       <c:when test="${personalStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
	  	   <td  align="center" >
		    	${dto.payType}
		   </td>
		   <td  align="center">
		    	${dto.orgCode}
		   </td>
		   <td  align="center" >
		    	${dto.createDate }
		   </td>
		    <td  align="center">
		    	${dto.partnerId}
		    </td>
		    <td  align="center">
		    	${dto.tranCount}
	     	</td>
	     	 <td  >
		    	${dto.payCurrencyCode}
		    </td>
		    <td align="center">
		    	${dto.payAmount}
		    </td>
		    <td align="center">
		    	${dto.tranFee}
		    </td>
		    <td align="center">
		    	${dto.bankAmount}
		    </td>
		    <td  align="center">
			    	${dto.settlementCurrencyCode}
			</td>
		    <td align="center">
		    	${dto.settlementAmount}
		    </td>
		    <td align="center">
		    	${dto.baseAmount}
		    </td>
		    <td align="center">
		    	${dto.assureAmount}
		    </td>
		    <td align="center">
		    	${dto.perFee}
		    </td>
		    <td align="center">
		    	${dto.fixedFee}
		    </td>
		    <td align="center">
		    	${dto.rateIncome}
		    </td>
		    <td align="center">
		    	${dto.totalIncome}
		    </td>
		    <td align="center">
		    	${dto.profit}
		    </td>
		    <td align="center">
		     	${dto.profitRate}
			</td>
		 </tr> 
	  </c:forEach>
	</table>
	<div class="e_cur_tit2"><i class="fr"><a href="javascript:exportExcel(${page.totalCount});"><input class="button2" type="button" value="下载"></a></i><span></span></div>
	<li:pagination methodName="userQuery" pageBean="${page}" sytleName="black2"/>
	</div>
	</c:if>
</body>
</html>
