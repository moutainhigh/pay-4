<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<HTML xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
<TITLE>手工分录导入</TITLE>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<link href="/seashell/intra/css/common.css" rel="stylesheet" type="text/css" />
<link href="/seashell/intra/css/main.css" rel="stylesheet" type="text/css" />
<META content="MSHTML 6.00.2900.3562" name=GENERATOR>
</HEAD>
<BODY>
<script type="text/javascript">

</script>
<!-- <DIV class="contentTitle clearfix">
<DIV class=contentTitleText>


<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
			<tr>
				<td height="18">
					<div align="center">
						<font class="titletext">手工分录导入</font>
					</div>
				</td>
			</tr>
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
		</table>
</DIV>
</DIV> -->
<h2 class="panel_title">手工分录导入</h2>
<form name="vouchUploadForm" 
	  id="vouchUploadForm" method="POST" 
	  action="${appContext}/manualbooking/uploadVouchTemplate.do"
	  enctype="multipart/form-data" >
<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
  <TBODY>
	 
	   <TR class="trForContent1">
	    <TD width="50%" class="border_top_right4" align=right width=259>凭证模板下载：</TD>
	    <TD class="border_top_right4">
	    <A href="${appContext}/manualbooking/vouchDataUpload.do?method=downloadTemplate">凭证模板文件（XLS）</A>
	    </TD>
	  </TR>
	   <TR class="trForContent1">
	    <TD class="border_top_right4" align=right width=259>导入文件：</TD>
	    <TD class="border_top_right4"><INPUT id="file" type="file" name="file"> </TD>
	  </TR>
	 <TR class="trForContent1">
	    <TD colspan="2" class="border_top_right4" align=center width=259><INPUT id="button2" type="submit" value="导入" name="button2" /></TD>
	   </TR>
  </TBODY>
</TABLE>

</form>

<font color="#FF0000">
<c:forEach items="${messages}" var="vouchMessage">
${vouchMessage.message}<br />
</c:forEach>
			
</font>

</BODY>
</HTML>
