<%@ page contentType="text/html; charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<style>
.table_a, .table_a td{ padding:10px; border:1px solid #ddd; border-collapse:collapse;}
</style>
<body>
<form method="post" action="${ctx}/timeoutprocess.do?method=process">
<input type="hidden" name="chargeChannelOrderId" value="${channelOrderId}"/>
<input type="hidden" name="billType" value="${billType}"/>
<table border="0" align="center" cellspacing="1" background="#ddd" class="table_a">
  <tr bgcolor="#CCCCCC">
    <td width="182" align="right" bgcolor="#FFFFFF">订单类型:</td><td width="194" bgcolor="#FFFFFF">${billTypeDesc}</td>
  </tr>
  <tr bgcolor="#CCCCCC">
    <td align="right" bgcolor="#FFFFFF">订单号:</td><td bgcolor="#FFFFFF">${orderId}</td>
  </tr>
  <tr bgcolor="#CCCCCC">
    <td align="right" bgcolor="#FFFFFF">订单金额:</td><td bgcolor="#FFFFFF">${requestAmount}元</td>
  </tr>
  <tr bgcolor="#CCCCCC">
    <td align="right" bgcolor="#FFFFFF">处理结果:</td><td bgcolor="#FFFFFF">
    <input type="radio" name="result" id="result" value="0" onclick="displayChargedAmount(0);"/>成功
    <input type="radio" name="result" value="1" onclick="displayChargedAmount(1);"/>失败</td>
  </tr>  
  <tr bgcolor="#CCCCCC" id="dis_chargedAmount" style="display: none;">
  	<td align="right" bgcolor="#FFFFFF">成功金额:</td><td bgcolor="#FFFFFF"><input name="chargedAmount" id="chargedAmount" type="text" value="" />
  	  元</td>
  </tr>
  <tr bgcolor="#CCCCCC">
    <td align="right" bgcolor="#FFFFFF">备注:</td><td bgcolor="#FFFFFF"><textarea id="remark" name="remark"></textarea></td>
  </tr>    
  <tr>
    <td colspan="2" align="center" bgcolor="#FFFFFF"><input type="button" id="doSubmit" onclick="dosubmit(this.form);" value="确认"/>
      &nbsp;&nbsp;
      <input type="button" onclick="javascript:window.location.href='${ctx}/timeoutprocess.do';" value="返回"/></td>
  </tr>   
</table>
</form>

<script type="text/javascript">

function isNumber(s) {
	var regu = "^(0(\\.\\d{0,2})?|([1-9]+[0]*)+(\\.\\d{0,2})?)$";
	var re = new RegExp(regu);
	if (re.test(s)) {
		return true;
	} else {
		return false;
	}
}


function dosubmit(d){

	var result = d.result;
	var check = false;
	for(var i=0;i<result.length;i++){
		if(result[i].checked){
			check = true;
		}
	}
	if(!check){
		alert('请选择处理结果!');
		return;
	}

	if(result[0].checked && (d.chargedAmount.value == '' || !isNumber(d.chargedAmount.value))){
		alert('请输入正确的成功金额!');
		return;
	}

	var requestAmount = ${requestAmount};
	if(d.chargedAmount.value > requestAmount){
		alert('成功金额不能大于请求金额!');
		return;
	}

	if(result[1].checked){
		d.chargedAmount.value = 0;
	}

	var remark = document.getElementById('remark');
	if(remark.value == ''){
		alert('请输入备注!');
		return;
	}
	d.submit();
}

function displayChargedAmount(flag){

	var dis_chargedAmount = document.getElementById('dis_chargedAmount');

	if(flag == 0){
		dis_chargedAmount.style.display = '';
	}else{
		dis_chargedAmount.style.display = 'none';
	}
	
	
}
</script>
</body>