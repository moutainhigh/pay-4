<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>后台出款订单成功置失败</title>
<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

</head>
<body>
<c:if test="${errorMsg != null}">
	<p align="left"><font color="red">
	失败原因有可能如下：<br/>
	1.订单号不合法！<br/>
	2.订单不存在！<br/>
	3.APP订单状态不是成功！<br/>
	4.FO退款订单状态不是成功！
</font></p>
</c:if>

<p align="left">
<form action="${ctx}/ebill_channelOrderprocess.do?method=query" method="post" onSubmit="return check(this);">
  <table width="407" height="129" border="0" cellspacing="1" bgcolor="#CCCCCC">
    <tr>
      <td width="184" align="right" bgcolor="#FFFFFF">订单号：</td>
      <td width="216" align="left" bgcolor="#FFFFFF"><input type="text" name="orderId" value="<c:if test='${orderId != null}'>${orderId}</c:if>"/></td>
    </tr>
    <tr>
      <td align="right" bgcolor="#FFFFFF">原因描述：</td>
      <td align="left" bgcolor="#FFFFFF"><textarea name="description" cols="20" rows="3"><c:if test='${desc != null}'>${desc}</c:if>
      </textarea></td>
    </tr>
    <tr>
      <td colspan="2" align="center" bgcolor="#FFFFFF"><input type="submit" value="提交" /></td>
    </tr>
  </table>
</form>
</p>
<script type="text/javascript">
function check(d){

	var orderId = d.orderId.value;
	if(orderId == ''){
		alert('请输入订单号！');
		return false;
	}

	var desc = d.descrition.value;
	if(desc == ''){
		alert('请输入原因！');
		return false;
	}

	return true;
}

</script>
</body>
</html>