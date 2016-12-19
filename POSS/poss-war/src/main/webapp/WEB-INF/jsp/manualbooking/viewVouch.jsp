<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<HTML xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
<TITLE>记账申请信息</TITLE>
<META http-equiv=Content-Type content="text/html; charset=utf-8">



</HEAD>
<BODY>

<h2 class="panel_title">记账申请信息</h2>

<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
	<TBODY>
		<TR class="trForContent1">
			<td class="border_top_right4"  align=right>记账申请号：</TD>
			<td class="border_top_right4"  >${vouchData.applicationCode}</TD>
			
			<td class="border_top_right4"  align=right>记账日期：				
				
			</TD>
			<td class="border_top_right4"  ><fmt:formatDate value="${vouchData.accountingDate}" type="both"/></TD>
			
			<td class="border_top_right4"  align=right>状态：</TD>
				<td class="border_top_right4" >
				<c:choose>
									<c:when test="${'0' eq vouchData.status}">
										未复核
									</c:when>
									<c:when test="${'1' eq vouchData.status}">
										审核通过
									</c:when>
									<c:when test="${'2' eq vouchData.status}">
										审核不通过
									</c:when>
									<c:otherwise>
										作废
									</c:otherwise>
			  	 </c:choose>	
				</TD>
			
		</TR>
		<TR class="trForContent1">
			<td class="border_top_right4"  align=right>申请时间：</TD>
			<TD class="border_top_right4" >
			<fmt:formatDate value="${vouchData.createDate}" type="both"/>			
          	</TD>
			<td colspan="6" class="border_top_right4"  align=right>&nbsp;</TD>
			
		</TR>
	</TBODY>
</TABLE>
<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
				<TBODY>
					<TR class="trForContent1">
						<TH scope=col colSpan=3>记&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;账&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;凭&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;证
						</TH>
					</TR>
					<TR class="trForContent1">
						<td class="border_top_right4" align="center" width="50%">单位：元</TD>
						<td class="border_top_right4" align="center" >凭证号：${vouchData.vouchDataId}</TD>
					</TR>
				</TBODY>
			</TABLE>
<table align="center" class="tablesorter" border="0" cellpadding="0" cellspacing="1" width="100%">
	<thead>
	
	<TR class="">
			<TH>摘要</TH>
			<TH>科目</TH>
			<TH>账户名称</TH>
			<TH>借方</TH>
			<TH>贷方</TH>
		</TR>
	</thead>
	<TBODY>
		
	
		
		<c:forEach items="${vouchData.vouchDataDetails}" var="detail" varStatus = "personalStatus">
		<c:choose>
	       <c:when test="${personalStatus.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>
    		<td class=""  >${detail.remark}</TD>
    		<td class=""  >${detail.accountCode}</TD>
    		<td class=""  >${detail.accountName}</TD>
    		
			<c:if test='${detail.crdr == "1"}'>
	    		<td class=""  >${detail.amountStr}</TD>
	    		<td class=""  >&nbsp;</TD>    		
    		</c:if> 
    		
    		<c:if test='${detail.crdr == "2"}'>    		
	    		<td class=""  >&nbsp;</TD>
	    		<td class=""  >${detail.amountStr}</TD>
    		</c:if>
    		
  		</TR>
  			</c:forEach>
  		
		<TR class="trForContent1">
			<td class=""  align=right bgColor=#fffff3 colSpan=3><STRONG>合计：</STRONG></TD>
			<td class=""  bgColor=#fffff3>${vouchData.crTotalAmountStr}</TD>
			<td class=""  bgColor=#fffff3>${vouchData.drTotalAmountStr}</TD>
		</TR>
		<TR class="trForContent1">
			<td class=""  >&nbsp;</TD>
			<td class=""  align=right ><STRONG>复核：</STRONG></TD>
			<td class=""  >${vouchData.auditor}</TD>
			<td class=""  align=right ><STRONG>制单：</STRONG></TD>
			<td class=""  >${vouchData.creator}</TD>
		</TR>
	</TBODY>
</TABLE>
<table class="border_all2" width="100%" border="0" cellspacing="0" cellpadding="1" align="center">
	<TBODY>
		<TR class="trForContent1">
			<td class="border_top_right4" width="50%" align=right >备注：</TD>
			<td class="border_top_right4" align="left">
				<TEXTAREA id=textfield name=textfield  readonly="readonly">${vouchData.remark}</TEXTAREA>
			</TD>
		</TR>
	</TBODY>
</TABLE>

<form id="backSearchForm" name="backSearchForm" method="POST" action="${appContext}/manualbooking/misController.do?method=reSearchVouchs">
<P align=center>
<input name="vouchSeq" type="hidden" value="${vouchSearchCriteria.vouchSeq}">
<input name="vouchCode" type="hidden" value="${vouchSearchCriteria.vouchCode}">
<input name="vouchStatus" type="hidden" value="${vouchSearchCriteria.vouchStatus}">
<input name="dateFrom" type="hidden" value="${vouchSearchCriteria.dateFromString}">
<input name="dateTo" type="hidden" value="${vouchSearchCriteria.dateToString}">
<input name="page" type="hidden" value="${vouchSearchCriteria.page}">

<INPUT id=button type="submit" value=返回 name=button>
</P>
</form>

</BODY>
</HTML>
