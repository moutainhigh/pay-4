<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>

<script src="./js/formvalid/formValid.js"></script>
<script src="./js/formvalid/global.js"></script>
<script language="javascript">

function closePage(url){	
	parent.closePage(url);
}

</script>
</head>
<body>


<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">申 诉 图 片 信 息</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<c:if test="${sign!=null}">
<br><br>
	<center>
		<font color="red">${sign}</font>
	</center>
<br><br>
</c:if>

<form id="appealFormBean" name="appealFormBean" action="appealAdd.do" method="post"   onsubmit="return validator(this)" enctype="multipart/form-data">

<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
	<c:if test="${isExist1 == null}"> 
		<tr class="trForContent1">
			<td class="" align="right" >图片资料1：</td>
			<td class="" align="left" >
				 <img src="appealPictureLoad.do?count=1&appealCode=${appealCode}"  />
			</td>
		</tr>
	</c:if>
	<c:if test="${isExist2 == null}"> 
		<tr class="trForContent1">
			<td class="" align="right" >图片资料2：</td>
			<td class="" align="left" >
				 <img src="appealPictureLoad.do?count=2&appealCode=${appealCode}" />
			</td>
		</tr>
	</c:if>
	<c:if test="${isExist3 == null}"> 
		<tr class="trForContent1">
			<td class="" align="right" >图片资料3：</td>
			<td class="" align="left" >
				 <img src="appealPictureLoad.do?count=3&appealCode=${appealCode}" />
			</td>
		</tr>
	</c:if>
	<c:if test="${isExist4 == null}"> 
		<tr class="trForContent1">
			<td class="" align="right" >图片资料4：</td>
			<td class="" align="left" >
				<img src="appealPictureLoad.do?count=4&appealCode=${appealCode}"  />
			</td>
		</tr>
	</c:if>
	<c:if test="${isExist5 == null}"> 
		<tr class="trForContent1">
			<td class="" align="right" >图片资料5：</td>
			<td class="" align="left" >
				 <img src="appealPictureLoad.do?count=5&appealCode=${appealCode}" />
			</td>
		</tr>
	</c:if>
	<c:if test="${isExist6 == null}"> 
		<tr class="trForContent1">
			<td class="" align="right" >图片资料6：</td>
			<td class="" align="left" >
				 <img src="appealPictureLoad.do?count=6&appealCode=${appealCode}"  />
			</td>
		</tr>
	</c:if>	
</table>
<br></br>
<table>
	<tr>	
		
		<td  align="center">	
		 <input id="closeButton" name="closeButton" type="button" value="关闭" onclick="javascript:closePage('appealPictureInfo.do?appealCode=${appealCode}');">			
		</td>
	</tr>
</table>

</form>


</body>

