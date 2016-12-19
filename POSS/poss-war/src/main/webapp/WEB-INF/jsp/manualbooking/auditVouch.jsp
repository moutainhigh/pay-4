<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<HTML xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
	<TITLE>记账凭证申请复核</TITLE>
</HEAD>

<BODY>

<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
			<tr>
				<td height="18">
					<div align="center">
						<font class="titletext">记账凭证申请复核</font>
					</div>
				</td>
			</tr>
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
</table>
		

<DIV class=h15></DIV>

<TABLE width=750 align=center border=0>
	<TBODY>
		<TR>
			<TD align=right width=104>记账申请号：</TD>
			<TD width=172>${vouchData.applicationCode}</TD>
			<TD align=right width=75>&nbsp;</TD>
			<TD width=215>&nbsp;</TD>
			<TD align=right width=57>&nbsp;</TD>
			<TD width=101>&nbsp;</TD>
		</TR>
		<TR>
			<TD align=right>申请时间：</TD>
			<TD>		
			<fmt:formatDate value="${vouchData.createDate}" type="both"/>
          	</TD>
			<TD align=right>&nbsp;</TD>
			<TD>&nbsp;</TD>
			<TD>&nbsp;</TD>
			<TD>&nbsp;</TD>
		</TR>
	</TBODY>
</TABLE>

<TABLE cellSpacing=1 cellPadding=5 width="100%" bgColor=#b8bab7 border=0>
	<TBODY>
		<TR>
			<TD scope=col bgColor=#ffffff colSpan=5>
			<TABLE width="100%" border=0>
				<TBODY>
					<TR>
						<TH scope=col colSpan=3>记&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;账&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;凭&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;证
						</TH>
					</TR>
					<TR>
						<TD width="34%">&nbsp;</TD>
						<TD width="31%">单位：元</TD>						
						<TD width="35%">凭证号：${vouchData.vouchDataId}</TD>
					</TR>
				</TBODY>
			</TABLE>
			</TD>
		<TR>
			<TH scope=col bgColor=#f9f98d>摘要</TH>
			<TH scope=col bgColor=#f9f98d>科目</TH>
			<TH scope=col bgColor=#f9f98d>账户名称</TH>
			<TH scope=col bgColor=#f9f98d>借方</TH>
			<TH scope=col bgColor=#f9f98d>贷方</TH>
		</TR>
		
		<c:forEach items="${vouchData.vouchDataDetails}" var="detail">
		
  		<TR>
    		<TD bgColor=#ffffff>${detail.remark}</TD>
    		<TD bgColor=#ffffff>${detail.accountCode}</TD>
    		<TD bgColor=#ffffff>${detail.accountName}</TD>
    		
			<c:if test='${detail.crdr == "1"}'>
	    		<TD bgColor=#ffffff>${detail.amountStr}</TD>
	    		<TD bgColor=#ffffff>&nbsp;</TD>    		
    		</c:if> 
    		
    		<c:if test='${detail.crdr == "2"}'>    		
	    		<TD bgColor=#ffffff>&nbsp;</TD>
	    		<TD bgColor=#ffffff>${detail.amountStr}</TD>
    		</c:if>
    		
  		</TR>

  		</c:forEach>
  		
		<TR>
			<TD align=right bgColor=#fffff3 colSpan=3><STRONG>合计：</STRONG></TD>
			<TD bgColor=#fffff3>${vouchData.crTotalAmountStr}</TD>
			<TD bgColor=#fffff3>${vouchData.drTotalAmountStr}</TD>
		</TR>
		<TR>
			<TD bgColor=#ffffff>&nbsp;</TD>
			<TD align=right bgColor=#ffffff><STRONG>复核：</STRONG></TD>
			<TD bgColor=#ffffff>${vouchData.auditor}</TD>
			<TD align=right bgColor=#ffffff><STRONG>制单：</STRONG></TD>
			<TD bgColor=#ffffff>${vouchData.creator}</TD>
		</TR>
	</TBODY>
</TABLE>

<script type="text/javascript">
	function validateRemark() {
		var s = "" + document.getElementById("vouchRemark").value;
		
		if (s.length > 150) {
			alert("备注长度不能大于150个字符！");
			return false;
		}
		return true;
	}

	function rejectVouch() {
		if (!confirm("确定审核不通过该请求？")) {
			return;
		}
	
		if (!validateRemark()) {
			return;
		}
	
		var url = "${appContext}/manualbooking/operateVouch.do?method=rejectVouch";
		var form = document.getElementById("backSearchForm");
		form.action = url;
		form.submit();
		return;
	}
	
	function approveVouch() {
		if (!confirm("确定审核通过该请求？")) {
			return;
		}
		
		if (!validateRemark()) {
			return;
		}
	
		var url = "${appContext}/manualbooking/operateVouch.do?method=approveVouch";		
		var form = document.getElementById("backSearchForm");
		form.action = url;
		form.submit();
		return;
	}
</script>


<form id="backSearchForm" name="backSearchForm" method="POST" action="${appContext}/manualbooking/misController.do?method=reSearchVouchs">
<TABLE cellPadding=5 width=580 align=center border=0>
	<TBODY>
		<TR>
			<TD vAlign=top align=right width=182>备注：</TD>
			<TD width=380>
			<TEXTAREA id="vouchRemark" name="vouchRemark" rows=3 cols=50>${vouchData.remark}</TEXTAREA>
			</TD>
		</TR>
	</TBODY>
</TABLE>


<input name="vouchSeq" type="hidden" value="${vouchSearchCriteria.vouchSeq}">
<input name="vouchCode" type="hidden" value="${vouchSearchCriteria.vouchCode}">
<input name="vouchStatus" type="hidden" value="${vouchSearchCriteria.vouchStatus}">
<input name="dateFrom" type="hidden" value="${vouchSearchCriteria.dateFromString}">
<input name="dateTo" type="hidden" value="${vouchSearchCriteria.dateToString}">
<input name="page" type="hidden" value="${vouchSearchCriteria.page}">
<input name="vouchDataId" type="hidden" value="${vouchData.vouchDataId}">

<P align=center>
<INPUT id=button2 type=button value=复核通过 name=button2 onclick="javascript:approveVouch()"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
<INPUT id=button type=button value=复核拒绝 name=button onclick="javascript:rejectVouch()"> &nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
<INPUT id=button2 type="submit" value=返回 name=button2>
</P>
</form>


<font color="#FF0000">
<c:forEach items="${messages}" var="vouchMessage">
	${vouchMessage.message}<br />
</c:forEach>
</font>

<script type="text/javascript">
	var message = "${vouchMessage}";
	if (message != '') {
		alert(message);
	}
	
	if (message != '') {
		var form = document.getElementById('backSearchForm');
		form.submit();   
	}		


</script>
</BODY>
</HTML>

