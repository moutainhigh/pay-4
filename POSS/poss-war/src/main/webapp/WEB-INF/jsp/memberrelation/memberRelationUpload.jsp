<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<HTML xmlns="http://www.w3.org/1999/xhtml">
<HEAD>
<TITLE>关联用户导入</TITLE>
<META http-equiv=Content-Type content="text/html; charset=utf-8">
<link href="/seashell/intra/css/common.css" rel="stylesheet" type="text/css" />
<link href="/seashell/intra/css/main.css" rel="stylesheet" type="text/css" />
<META content="MSHTML 6.00.2900.3562" name=GENERATOR>
</HEAD>
<BODY>
<script type="text/javascript">

</script>
<DIV class="contentTitle clearfix">
<DIV class=contentTitleText>


<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
			<tr>
				<td height="18">
					<div align="center">
						<font class="titletext">关联用户数据导入</font>
					</div>
				</td>
			</tr>
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
		</table>
</DIV>
</DIV>
<form name="vouchUploadForm" 
	  id="vouchUploadForm" method="POST" 
	  action="${appContext}/memberrelation/uploadRelationTemplate.do"
	  enctype="multipart/form-data" >
<TABLE width=580 align=center border=0>
  <TBODY>
	  <TR>
	    <TD>&nbsp;</TD>
	    <TD width=311>&nbsp;</TD>
	  </TR>
	  
	  <TR>
	    <TD align=right width=259>关联模板下载：</TD>
	    <TD>
	    <A href="${appContext}/memberrelation/relationDataUpload.do?method=downloadTemplate">关联模板文件（XLS）</A>
	    </TD>
	  </TR>
	  <TR>
	    <TD align=right width=259>导入文件：</TD>
	    <TD><INPUT id="file" type="file" name="file"> </TD>
	  </TR>
  </TBODY>
</TABLE>
<P align=center>
<INPUT id="button2" type="submit" value="导   入" name="button2" /> 
</P>
</form>

<font color="#FF0000">
<c:forEach items="${messages}" var="vouchMessage">
${vouchMessage.message}<br />
</c:forEach>			
</font>


</BODY>
</HTML>
