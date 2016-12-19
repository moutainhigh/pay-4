<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<h2 class="panel_title">出款复核结果详情</h2>
<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
  <tr class="trForContent1">
	<td>上传复核笔数：<span>${count.totalCount}</span></td>
	<td>复核成功笔数：<span>${count.successCount}</span></td>
	<td>复核失败笔数 ：<span>${count.failedCount}</span></td>
  </tr>
</table>
		<a onclick="MismatchingTrade();" ><input type="button" value="不完全匹配交易"></a>  &nbsp;&nbsp;
		<a onclick="MatchingTrade();" ><input type="button" value="完全匹配交易"></a> 
<br>	
<table id="mismatchingTrade" align="center" class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
 <thead> <tr>
    <th>批次号</th>
    <th>银行名称</th>
    <th>银行账户</th>
    <th>金额</th>
    <th>收款人</th>
  </tr></thead>
 <c:forEach items="${fundoutCheckDtos}" var="mDto" varStatus = "personalStatus">
 		<c:if test="${mDto.status=='2'}"> <!-- 2为复核失败 默认显示失败 -->
		  <c:choose>
	       <c:when test="${personalStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
	    	<td>${mDto.batchNum }</td>
	    	<td>${mDto.bankName }</td>
	    	<td>${mDto.bankAccountCode }</td>
	    	<td>${mDto.amount }</td>
	    	<td>${mDto.bankAccount }</td>
	  	   </tr>
  	   </c:if>
  </c:forEach>
</table>
<table style="display:none;" id="matchingTrade" align="center" class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
<!-- <table style="display:none;" id="matchingTrade" align="center" class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%"> -->
 <thead> <tr>
    <th>批次号</th>
    <th>银行名称</th>
    <th>银行账户</th>
    <th>金额</th>
    <th>收款人</th>
  </tr></thead>
 <c:forEach items="${fundoutCheckDtos}" var="mDto" varStatus = "personalStatus">
 		<c:if test="${mDto.status=='1'}"><!-- 1为复核成功  -->
		  <c:choose>
	       <c:when test="${personalStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
	    	<td>${mDto.batchNum }</td>
	    	<td>${mDto.bankName }</td>
	    	<td>${mDto.bankAccountCode }</td>
	    	<td>${mDto.amount }</td>
	    	<td>${mDto.bankAccount }</td>
	  	   </tr>
  	   </c:if>
  </c:forEach>
</table>
<input type="hidden" id="batchNum" value="${mDto.batchNum }"/>
 <div id="resultListDiv" class="listFence"></div> 
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>
 <script type="text/javascript">
 	function MismatchingTrade(){
 		//不完全匹配
 		$("#matchingTrade").attr("style","display:none;");
 		$("#mismatchingTrade").removeAttr("style");
 	}
 
 	function MatchingTrade(){
 		//完全匹配
	 	$("#matchingTrade").removeAttr("style");
	 	$("#mismatchingTrade").attr("style","display:none;");
 	}
 	
/*  	 function aaa(pageNo,totalCount,pageSize) {
 		var batchNum=$("#batchNum").val();
 		var pars ="&batchNum="batchNum + "&pageNo=" + pageNo + "&totalCount=" + totalCount + "&pageSize=" + pageSize;
 		alert(pars);
 		$('#infoLoadingDiv').dialog('open');
 			$.ajax({
 				type: "POST",
 				url: "./findFundoutCheck.do?method=queryFundoutCheckDetail",
 				data: pars,
 				success: function(result) {
 					$('#infoLoadingDiv').dialog('close');
 					$('#resultListDiv').html(result);
 				}
 			});
 	} */
</script>

	<%-- <li:pagination methodName="aaa" pageBean="${page}" sytleName="black2"/>
	 --%>
</body>

