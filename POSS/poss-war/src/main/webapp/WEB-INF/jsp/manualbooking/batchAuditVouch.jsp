<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<HTML xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
	<TITLE>记账凭证批量申请复核</TITLE>
</HEAD>
<form id="backSearchForm" name="backSearchForm" method="POST" action="${appContext}/manualbooking/misController.do?method=reSearchVouchs">

<BODY>

<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
			<tr>
				<td height="18">
					<div align="center">
						<font class="titletext">记账凭证批量申请复核</font>
					</div>
				</td>
			</tr>
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
</table>
		

<DIV class=h15></DIV>
<c:forEach var="vouchData" items="${batchVouchData}">
		<TABLE width=750 align=center border=0>
	<TBODY>
		<TR>
			<TD align=right width=104>记账申请号：</TD>
			<TD width=172>${vouchData.applicationCode}</TD>
			<TD width=101>&nbsp;</TD>
			
			<TD align=right>申请时间：</TD>
			<TD>		
			<fmt:formatDate value="${vouchData.createDate}" type="both"/>
          	</TD>
		
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
						<td><input  name="batchVouchDataId" type="hidden" value="${vouchData.vouchDataId}"/>   </td>
	
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

<TABLE cellPadding=5 width=580 align=center border=0>
	<TBODY>
		<TR>
			<TD vAlign=top align=right>备注：</TD>
			<TD width=380>
			<TEXTAREA  name="vouchRemark" rows=3 cols=50>${vouchData.remark}</TEXTAREA>
			</TD>
		</TR>
	</TBODY>
</TABLE>
</c:forEach>

		<P align=center>
			<INPUT type="button" value="批量复核通过" onclick="approveVouch();"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
			<INPUT type="button" value="批量复核拒绝" onclick="rejectVouch();"/>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
			<INPUT type="submit" value="返回 "/>
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

	
	function validateRemark() {
		var f_str="";
		$(":input[name=vouchRemark]").each(function(i){
			
				if (this.value.length > 150) {
					alert("备注长度不能大于150个字符！");
					return false;
				}
			    f_str += this.value + "|";			
			});		

		return true;
	}

	function rejectVouch() {
		if (!confirm("确定审核不通过该请求？")) {
			return;
		}
	
		if (!validateRemark()) {
			return;
		}
	
		var url = "${appContext}/manualbooking/operateVouch.do?method=batchRejectVouch";
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
		
		var url = "${appContext}/manualbooking/operateVouch.do?method=batchApproveVouch";
		var form = document.getElementById("backSearchForm");
		form.action = url;
		form.submit();
		return;
	}

</script>
</BODY>
</HTML>

