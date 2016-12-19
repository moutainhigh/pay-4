<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<html>
<head>
<%--引入用于格式化页面的CSS文件--%>
<link rel="stylesheet" href="./css/main.css">
<script type="text/javascript">
<c:if test="${not empty flag && flag == 1}" >
	alert('新增成功!');
</c:if>
<c:if test="${not empty flag && flag == 0}" >
	alert('新增失败!');
</c:if>
 
function processBack(){
	window.location.href="${ctx}/blacklist.do?method=blacklist2EInit"; 
}
function processAdd(){
	var lhtj = $.trim($("#lhtj").val());
	if ("" == lhtj) {
		alert("录黑途径编码不能为空");
		$("#lhtj").focus();
		return;
	}
	if(!s_isNumber(lhtj)){
		alert("录黑途径编码必须为数字!");
		$("#lhtj").focus();
		return;
	}
	var hmdsj = $.trim($("#hmdsj").val());
	if ("" == hmdsj) {
		alert("负面信息事件编码不能为空");
		$("#hmdsj").focus();;
		return;
	}
	if(!s_isNumber(hmdsj)){
		alert("负面信息事件编码必须为数字!");
		$("#hmdsj").focus();
		return;
	}
	var zzjgdm = $.trim($("#zzjgdm").val());
	if ("" == zzjgdm) {
		alert("组织机构编号不能为空");
		$("#zzjgdm").focus();;
		return;
	}
	var hmdsjbz = $.trim($("#hmdsjbz").val());
	if (hmdsjbz.len_() < 2 || hmdsjbz.len_() > 100) {
		alert('负面信息备注最少2个字符,最大不超过50个汉字！');
		$("#hmdsjbz").focus();
		return;
	}
	
	$("#form1").submit();
}

</script>
</head>

<body>
<br>
<br>
<p align="center">
<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">新增机构名单信息</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>

<form method="post" id="form1" action="${ctx}/blacklist.do?method=addBlacklist2E" >
<table class="inputTable" width="800" border="0" cellspacing="0" cellpadding="3" align="center">

		<tr>
	    	<td align="right">法人代表姓名:</td>
	      	<td>
	      		<input type="text" name="xm" id="xm" value="${dto.xm}" maxlength="15" />
	     	</td>
	     	<td align="right">法人身份证号:</td>
	      	<td>
	      		<input type="text" name="gmsfhm" id="gmsfhm" value="${dto.gmsfhm}" maxlength="18" />
	     	</td>
	    </tr>
	    <tr>
	    	<td align="right">发生地区编码:</td>
	      	<td>
	      		<input type="text" name="fsdq" id="fsdq" value="${dto.fsdq}" maxlength="4" />
	     	</td>
	     	<td align="right">录黑途径编码:</td>
	      	<td>
	      		<input type="text" name="lhtj" id="lhtj" value="${dto.lhtj}" maxlength="2"/><font color="red">*</font>
	     	</td>
	    </tr>
	    <tr>
	    	<td align="right">手机号码:</td>
	      	<td>
	      		<input type="text" name="sjhm" id="sjhm" value="${dto.sjhm}" maxlength="150" />
	     	</td>
	     	<td align="right">固定电话:</td>
	      	<td>
	      		<input type="text" name="gddh" id="gddh" value="${dto.gddh}" maxlength="200" />
	     	</td>
	    </tr>
	    <tr>
	    	<td align="right">银行卡号:</td>
	      	<td>
	      		<input type="text" name="yhkh" id="yhkh" value="${dto.yhkh}" maxlength="200" />
	     	</td>
	     	<td align="right">开户行:</td>
	      	<td>
	      		<input type="text" name="khh" id="khh" value="${dto.khh}" maxlength="100" />
	     	</td>
	    </tr>
	     <tr>
	    	<td align="right">IP地址:</td>
	      	<td>
	      		<input type="text" name="ip" id="ip" value="${dto.ip}" maxlength="300" />
	     	</td>
	     	<td align="right">MAC地址:</td>
	      	<td>
	      		<input type="text" name="mac" id="mac" value="${dto.mac}" maxlength="150" />
	     	</td>
	    </tr>
	     <tr>
	    	<td align="right">机构名称:</td>
	      	<td>
	      		<input type="text" name="jgmc" id="jgmc" value="${dto.jgmc}" maxlength="140" />
	     	</td>
	     	<td align="right">营业执照编号:</td>
	      	<td>
	      		<input type="text" name="yyzzbh" id="yyzzbh" value="${dto.yyzzbh}" maxlength="30" />
	     	</td>
	    </tr>
	    <tr>
	    	<td align="right">邮箱:</td>
	      	<td>
	      		<input type="text" name="email" id="email" value="${dto.email}" maxlength="300" />
	     	</td>
	    	<td align="right">地址:</td>
	      	<td>
	      		<input type="text" name="dz" id="dz" value="${dto.dz}" maxlength="250" />
	     	</td>
	    </tr>
	    <tr>
	     	<td align="right">ICP编号:</td>
	      	<td>
	      		<input type="text" name="icp" id="icp" value="${dto.icp}" maxlength="40" />
	     	</td>
	     	<td align="right">ICP备案人:</td>
	      	<td>
	      		<input type="text" name="icpbar" id="icpbar" value="${dto.icpbar}" maxlength="15" />
	     	</td>
	    </tr>
	    <tr>
	    	<td align="right">URL地址:</td>
	      	<td>
	      		<input type="text" name="urldz" id="urldz" value="${dto.urldz}" maxlength="400" />
	     	</td>
	     	<td align="right">URL跳转地址:</td>
	      	<td>
	      		<input type="text" name="urltzdz" id="urltzdz" value="${dto.urltzdz}" maxlength="400" />
	     	</td>
	    </tr>
	    <tr>
	    	<td align="right">支付人:</td>
	      	<td>
	      		<input type="text" name="zfr" id="zfr" value="${dto.zfr}" maxlength="15" />
	     	</td>
	     	<td align="right">负面信息事件编码:</td>
	      	<td >
	      		<input type="text" name="hmdsj" id="hmdsj" value="${dto.hmdsj}" maxlength="3" /><font color="red">*</font>
	     	</td>
	    </tr>
	    <tr>
	    	<td class="textRight">名单类型：</td>
			<td class="textLeft">
				<select name="sjzt" id="sjzt">
					<option value="1">黑名单</option>
					<option value="2">灰名单</option>
				</select>
				<font color="red">*</font>
			</td>
			<td class="textRight">业务类别：</td>
			<td class="textLeft">
				<select name="ywlb" id="ywlb">
					<option value="01">个人互联网支付</option>
					<option value="02">机构互联网支付</option>
					<option value="03">移动支付</option>
					<option value="04">POSS收单</option>
					<option value="05">预付费卡</option>
					<option value="06">语音支付</option>
					<option value="00">其他</option>
				</select>
				<font color="red">*</font>
			</td>
	    </tr>
	    <tr>
	    	<td class="textRight">组织机构编号：</td>
			<td class="textLeft" colspan="3">
				<input type="text" name="zzjgdm" id="zzjgdm" value="${dto.zzjgdm}" maxlength="18" /><font color="red">*</font>
			</td>
	    </tr>
	    <tr>
	    	<td align="right" >负面信息备注:</td>
	      	<td colspan="3">
	      		<textarea rows="4" cols="100" name="hmdsjbz" id="hmdsjbz" maxlength="100">${dto.hmdsjbz}</textarea>
	     	</td>
	    </tr>
</table>
<br>
<br>

<table class="border_all4" width="75%" border="0" cellspacing="0" cellpadding="0" align="center" id="buttonDisplay">
	<tr class="trbgcolor6" align="center">
		<td>
			<a href="#" class="dialog_link ui-state-default ui-corner-all" onclick="processAdd()">
			<span class="ui-icon ui-icon-disk"></span>&nbsp;添&nbsp;&nbsp;加&nbsp;
			</a>&nbsp;&nbsp;&nbsp;
			<a href="#" class="dialog_link ui-state-default ui-corner-all" onclick="processBack()">
			<span class="ui-icon ui-icon-arrowreturnthick-1-w"></span>&nbsp;返&nbsp;&nbsp;回&nbsp;</a>
		</td>
	</tr>
</table>
</form>
</body>