<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<p>
添加或修改代扣商户配置
</p>


<form action="" method="post" name="addFrom">
<c:if test="${result != null}">
<input type="hidden" name="id" value="${result.id}"/>
</c:if>
<table>
<tr>
<td align="right">商户号：</td><td align="left"><input type="text" name="merchantCode" value="<c:if test='${result != null}'>${result.merchantCode}</c:if>"/></td>
</tr>
<tr>
<td align="right">商户授权地址：</td><td align="left"><input type="text" name="authorizeAddress" value="<c:if test='${result != null}'>${result.authorizeAddress}</c:if>"/></td>
</tr>
<tr>
<td align="right">通知商户地址：</td><td align="left"><input type="text" name="notifyUrl" value="<c:if test='${result != null}'>${result.notifyUrl}</c:if>"/>如果需异步通知商户，该值不能为空</td>
</tr>
<tr>
<td align="right">密钥：</td><td align="left"><input type="text" name="publicKey" value="<c:if test='${result != null}'>${result.publicKey}</c:if>"/></td>
</tr>
<tr>
<td align="right">是否需要异步通知：</td>
<td align="left">
<input type="radio" name="notifyFlag" value="1" <c:if test='${result != null && result.notifyFlag == 1}'>checked</c:if>/>需要
<input type="radio" name="notifyFlag" value="0" <c:if test='${result != null && result.notifyFlag == 0}'>checked</c:if>/>不需要
</td>
</tr>
<tr>
<td align="right">状态：</td>
<td align="left">
<input type="radio" name="status" value="1" <c:if test='${result != null && result.status == 1}'>checked</c:if>/>正常
<input type="radio" name="status" value="0" <c:if test='${result != null && result.status == 0}'>checked</c:if>/>停用
</td>
</tr>
<tr>
<td span="2" align="center"><input type="button"  name="butSubmit" value="提  交" class="button2" onclick="add();"></td>
</tr>
</table>
</form>
<script>
function add(){

	var d = document.forms[0];
	if('' == d.merchantCode.value){
		alert("商户号不能为空！");
		return;
	}
	if('' == d.publicKey.value){
		alert("密钥不能为空！");
		return;
	}
	var notifyFlag = false;
	for(var i=0;i<d.notifyFlag.length;i++){
		if(d.notifyFlag[i].checked){
			notifyFlag = true;
		}
	}
	if(!notifyFlag){
		alert("请选择是否需要异步通知！");
		return false;
	}

	var status = false;
	for(var i=0;i<d.status.length;i++){
		if(d.status[i].checked){
			status = true;
		}
	}
	if(!status){
		alert("请选择该配置是否生效！");
		return false;
	}

	<c:if test="${result != null}">
		d.action = "${ctx}/context_withhold_merchant.controller.htm?method=update";
	</c:if>

	<c:if test="${result == null}">
		d.action = "${ctx}/context_withhold_merchant.controller.htm?method=add";
	</c:if>
	d.submit();
}
</script>