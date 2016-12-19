<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript" src="${ctx}/js/common.js"></script>
<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">复核结果</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<table  width="85%" class="tablesorter"> 
  <tr>
	<td>上传复核笔数：<span>${applyCount}</span></td>
	<td>复核成功笔数：<span>${successCount}</span></td>
	<td>复核失败笔数 ：<span>${applyCount-successCount}</span></td>
  </tr>
</table>
		<input type="button" onclick="onMismatchingTrade();" value="不完全匹配交易">  &nbsp;&nbsp;
		<input type="button" onclick="onMatchingTrade();" value="完全匹配交易"> 
<br>	
<table width="85%" class="tablesorter" id="mismatchingTrade">
  <tr>
    <th>批次号</th>
    <th>银行名称</th>
    <th>银行账户</th>
    <th>金额</th>
    <th>收款人</th>
  </tr>
 <c:forEach items="${fundoutCheckDtos}" var="mDto">
 		<c:if test="${mDto.status=='2'}"> <!-- 2为复核失败 默认显示失败 -->
		  <tr>
	    	<td>${mDto.batchNum }</td>
	    	<td>${mDto.bankName }</td>
	    	<td>${mDto.bankAccountCode }</td>
	    	<td>${mDto.amount }</td>
	    	<td>${mDto.bankAccount }</td>
	  	   </tr>
  	   </c:if>
  </c:forEach>
</table>

<table style="display:none;" width="85%" class="tablesorter" id="matchingTrade">
  <tr>
    <th>批次号</th>
    <th>银行名称</th>
    <th>银行账户</th>
    <th>金额</th>
    <th>收款人</th>
  </tr>
 <c:forEach items="${fundoutCheckDtos}" var="mDto">
 		<c:if test="${mDto.status=='1'}"><!-- 1为复核成功  -->
		  <tr>
	    	<td>${mDto.batchNum }</td>
	    	<td>${mDto.bankName }</td>
	    	<td>${mDto.bankAccountCode }</td>
	    	<td>${mDto.amount }</td>
	    	<td>${mDto.bankAccount }</td>
	  	   </tr>
  	   </c:if>
  </c:forEach>
</table>
 <script type="text/javascript">
 	function onMismatchingTrade(){
 		//不完全匹配
 		$("#matchingTrade").attr("style","display:none;");
 		$("#mismatchingTrade").removeAttr("style");
 	}
 
 	function onMatchingTrade(){
 		//完全匹配
	 	$("#matchingTrade").removeAttr("style");
	 	$("#mismatchingTrade").attr("style","display:none;");
 	}
</script>
 