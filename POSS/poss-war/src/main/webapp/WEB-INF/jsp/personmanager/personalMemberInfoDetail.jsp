<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
<%--引入用于格式化页面的CSS文件--%>
<link rel="stylesheet" href="./css/main.css">
<script src="./js/common.js"></script>
<script src="./js/formvalid/formValid.js"></script>
<script src="./js/formvalid/global.js"></script>
<script language="javascript">


</script>
</head>
<body>


<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">个人会员详细信息</font></div>
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

<form id="merchantFormBean" name="merchantFormBean" action="merchantAdd.do" method="post"   onsubmit="return validator(this)">

<table class="border_all2" width="70%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
		<tr class="trForContent1"><td class="border_top_right4" align="center" colspan="6">会员基本信息：</td></tr>
		<tr class="trForContent2">
			<td class="" align="right" >登录名：</td>
			<td class="" align="left" >&nbsp;${memberInfo.loginName}
			</td>
			
			<td class="" align="right" >姓名：</td>
			<td class="" align="left" >${memberInfo.userName}
				&nbsp;
			</td>
		</tr>	
		
		<tr class="trForContent2">
			<td class="" align="right" >会员号：</td>
			<td class="" align="left" >${memberInfo.memberCode}
				&nbsp;
			</td>
			<td class="" align="right" >手机：</td>
			<td class="" align="left" >${memberInfo.mobile}
				&nbsp;
			</td>
		</tr>	
		<tr class="trForContent2">
			<td class="" align="right" >身份证号码：</td>
			<td class="" align="left" >
			<c:if test="${cusSerFlag !='customerSer'}">
				${memberInfo.cerCode}
			</c:if>
				&nbsp;
			</td>
			<td class="" align="right" >Email：
			<td class="" align="left" >${memberInfo.email} &nbsp;
			</td>
		</tr>	
		<tr class="trForContent2">
			<td class="" align="right" >Sys社区账户：</td>
			<td class="" align="left" >${memberInfo.ssoId}
				&nbsp;
			</td>
			<td class="" align="right" >验证状态：</td>
			<td class="" align="left" >
				<c:if test="${isPaperFile.isPaperFile==0}">未认证</c:if>
				<c:if test="${isPaperFile.isPaperFile==1}">银行卡鉴权</c:if>
				<c:if test="${isPaperFile.isPaperFile==2}">公安网验证</c:if>
				<c:if test="${isPaperFile.isPaperFile==3}">银行卡鉴权且上传影像件</c:if>
				<c:if test="${isPaperFile.isPaperFile==4}">公安网验证且上传影像</c:if>
			&nbsp;</td>
		</tr>	
		<tr class="trForContent2">
			<td class="" align="right" >口令卡绑定：</td>
			<td class="" align="left" >${isMatrixCard}&nbsp;</td>
			<td class="" align="right" >地址：</td>
			<td class="" align="left" >${memberInfo.address}
				&nbsp;
			</td>
		</tr>		
		<tr class="trForContent2">
			<td class="" align="right" >注册时间：</td>
			<td class="" align="left" >${memberInfo.createDateName}
				&nbsp;
			</td>
			<td class="" align="right" >性别：</td>
			<td class="" align="left" >${memberInfo.sex}</td>
		</tr>
		<tr class="trForContent2">
			<td class="" align="right" >国籍：</td>
			<td class="" align="left" >${memberInfo.country}
				&nbsp;
			</td>
			<td class="" align="right" >职业：</td>
			<td class="" align="left" >${memberInfo.profession}</td>
		</tr>	
		<tr class="trForContent1"><td class="" align="center" colspan="6">会员银行账户信息：</td></tr>
		<tr class="trForContent2">
			<td class="" align="right" >银行账户：</td>
			<td class="" align="left" >
				<c:if test="${cusSerFlag !='customerSer'}">
					${bankAcct.bankAcct}
				</c:if>
				&nbsp;
			</td>
			<td class="" align="right" >验证状态：</td>
			<td class="" align="left" >
				<c:if test="${bankAcct.status==0}">未验证</c:if>
				<c:if test="${bankAcct.status==1}">已验证</c:if>
				<c:if test="${bankAcct.status==2}">验证中(未打款)</c:if>
				<c:if test="${bankAcct.status==3}">验证中</c:if>
				<c:if test="${bankAcct.status==4}">鉴权验证中</c:if>
				&nbsp;
			</td>

		</tr>	
		<tr class="trForContent1"><td class="" align="center" colspan="6">会员最近登录信息：</td></tr>
		<tr class="trForContent2">
			<td class="" align="right" >最近登录IP：</td>
			<td class="" align="left" >${loginHistory.loginIp} &nbsp;
			</td>
			<td class="" align="right" >最近登录时间：</td>
			<td class="" align="left" >${loginHistory.loginDate}
				&nbsp;
			</td>
		</tr>		
</table>

</form>


</body>

