﻿﻿<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<HEAD><TITLE>手工分录导入</TITLE>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<STYLE type=text/css>.cuttitle {
	FONT-WEIGHT: bold; FONT-SIZE: 12px
}
</STYLE>
<script type="text/javascript">
function confirmUploadVouchData() {
	var url = "${appContext}/manualbooking/vouchDataUploadquery.do?method=confirmUploadVouchData";
	var form = document.getElementById("confirmUploadVouchForm");
	form.action = url;
	form.submit();
	return true;
}

function goUploadMainPage() {
	var url = "${appContext}/manualbooking/vouchDataUpload.do";
	
	var form = document.getElementById("confirmUploadVouchForm");
	form.action = url;
	form.submit();
	return true;
}

</script>

</HEAD>
<BODY>
<DIV class="contentTitle clearfix">
<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
			<tr>
				<td height="18">
					<div align="center">
						<font class="titletext">手工分录导入确认</font>
					</div>
				</td>
			</tr>
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
		</table>
</DIV></br></DIV>
<DIV class=h15></DIV>
<TABLE cellSpacing=1 cellPadding=5 width="100%" bgColor=#b8bab7 border=0>
  <TBODY>
  <TR>
    <TD scope=col bgColor=#ffffff colSpan=5>
      <TABLE width="100%" border=0>
        <TBODY>
        <TR>
          <TH scope=col 
            colSpan=3>记&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;账&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;凭&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;证 
          </TH>
        </TR>
        <TR>
          <TD width="34%">日期：
          	<fmt:formatDate value="${vouchData.createDate}" type="both"/>
          </TD>
          <TD width="31%">单位：元</TD>
          <TD width="35%">凭证编号：${vouchData.vouchDataId}</TD>          
        </TR>
        </TBODY>
      </TABLE>
    </TD>
  <TR>
    <TH scope=col bgColor=#f9f98d >摘要</TH>
    <TH scope=col bgColor=#f9f98d >科目</TH> 
    <TH scope=col bgColor=#f9f98d >账户名称</TH> 
    <TH scope=col bgColor=#f9f98d >借方</TH>
    <TH scope=col bgColor=#f9f98d >贷方</TH> 
  </TR>
  <c:forEach items="${vouchData.vouchDataDetails}" var="dto">
  <TR>
	    <TD bgColor=#ffffff>${dto.remark}</TD>
	    <TD bgColor=#ffffff>${dto.accountCode}</TD>
	    <TD bgColor=#ffffff>${dto.accountName}</TD>
	    <c:choose>
			<c:when test="${1 eq dto.crdr}">
					<TD bgColor=#ffffff><fmt:formatNumber value="${dto.amount}" pattern="#,##0.00"/></TD>
		            <TD bgColor=#ffffff>&nbsp;</TD>		
			</c:when>
			<c:when test="${2 eq dto.crdr}">
					<TD bgColor=#ffffff>&nbsp;</TD>
	                <TD bgColor=#ffffff><fmt:formatNumber value="${dto.amount}" pattern="#,##0.00"/></TD>			
			</c:when>
		</c:choose>
	  </TR>
	  
</c:forEach>
  <TR>
    <TD align=right bgColor=#fffff3 colSpan=3><STRONG>合计：</STRONG></TD>
    <TD bgColor=#fffff3>${vouchData.crTotalAmountStr}</TD>
    <TD bgColor=#fffff3>${vouchData.drTotalAmountStr}</TD>
  </TR>
  <TR>
    <TD bgColor=#ffffff>&nbsp;</TD>
    <TD align=right bgColor=#ffffff>
    	<STRONG>复核：</STRONG>
    </TD>
    <TD bgColor=#ffffff>${vouchData.auditor}</TD>
    <TD align=right bgColor=#ffffff>
    	<STRONG>制单：</STRONG>
    </TD>
    <TD bgColor=#ffffff>${vouchData.creator}</TD>
  </TR>
  </TBODY>
</TABLE>

<form id="confirmUploadVouchForm" name="confirmUploadVouchForm" method="POST" >
<P align=center>
	<INPUT id="button" type="button" value="确  认" name=button onclick="javascript:confirmUploadVouchData()"/> 
&nbsp;&nbsp;&nbsp;&nbsp;&nbsp; 
	<INPUT id=button2 type="button" value="返  回" name=button2 onclick="javascript:goUploadMainPage()"/> 
</P>
</form>
</BODY>
</HTML>
