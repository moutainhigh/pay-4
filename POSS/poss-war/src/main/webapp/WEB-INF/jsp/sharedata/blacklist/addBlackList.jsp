<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	$(document).ready(function(){
		$("#frm2").hide();
		$("#type1").click(function(){
			$("#frm1").show();
			$("#frm2").hide();
		});
		$("#type2").click(function(){
			$("#frm2").show();
			$("#frm1").hide();
			$("#type4").attr("checked", "checked");
		});
		$("#type3").click(function(){
			$("#frm1").show();
			$("#frm2").hide();
			$("#type1").attr("checked", "checked");
		});
		$("#type4").click(function(){
			$("#frm2").show();
			$("#frm1").hide();
		});
		$("#btn1").click(function(){
			if(validate1()){
				var url="${ctx}/context_fundout_foblacklist.controller.htm";
				var data="method=checkRepeat&id=" + $("#id1").val();
				$.post(url, data, function(res){
					if(res == "yes"){
						alert("黑名单ID已存在，请重新填写");
						$("#msg").html("");
					}else{
						$("#frm1").submit();
					}
				});
			}
		});
		$("#btn2").click(function(){
			if(validate2()){
				var url="${ctx}/context_fundout_foblacklist.controller.htm";
				var data="method=checkRepeat&id=" + $("#id2").val();
				$.post(url, data, function(res){
					if(res == "yes"){
						alert("黑名单ID已存在，请重新填写");
					}else{
						$("#frm2").submit();
					}
				});
			}
		});	
	});

	function validate1(){
		var id1 = $("#id1").val();
		var way1 = $("#way1").val();
		var event1 = $("#event1").val();
		var markTime1 = $("#markTime1").val();
		if($.trim(id1).length < 1){
			alert("黑名单ID不能为空");
			$("#id1").focus();
			return false;
		}else if($.trim(id1).length != 12){
			alert("黑名单ID必须为12位数字");
			$("#id1").focus();
			return false;
		}
		if($.trim(way1).length < 1){
			alert("录黑途径不能为空");
			$("#way1").focus();
			return false;
		}else if($.trim(way1).length != 2){
			alert("录黑途径必须为2位数字");
			$("#way1").focus();
			return false;
		}
		if($.trim(event1).length < 1){
			alert("黑名单事件不能为空");
			$("#event1").focus();
			return false;
		}else if($.trim(event1).length != 3){
			alert("黑名单事件必须为3位数字");
			$("#event1").focus();
			return false;
		}
		if($.trim(markTime1).length < 1){
			alert("标记时间不能为空");
			$("#markTime1").focus();
			return false;
		}
		return true;
	}

	function validate2(){
		var id2 = $("#id2").val();
		var way2 = $("#way2").val();
		var event2 = $("#event2").val();
		var markTime2 = $("#markTime2").val();
		if($.trim(id2).length < 1){
			alert("黑名单ID不能为空");
			$("#id2").focus();
			return false;
		}else if($.trim(id2).length != 12){
			alert("黑名单ID必须为12位数字");
			$("#id2").focus();
			return false;
		}
		if($.trim(way2).length < 1){
			alert("录黑途径不能为空");
			$("#way2").focus();
			return false;
		}else if($.trim(way2).length != 2){
			alert("录黑途径必须为2位数字");
			$("#way2").focus();
			return false;
		}
		if($.trim(event2).length < 1){
			alert("黑名单事件不能为空");
			$("#event2").focus();
			return false;
		}else if($.trim(event2).length != 3){
			alert("黑名单事件必须为3位数字");
			$("#event2").focus();
			return false;
		}
		if($.trim(markTime2).length < 1){
			alert("标记时间不能为空");
			$("#markTime2").focus();
			return false;
		}
		return true;
	}
</script>
<%@page import="java.util.Date"%>
<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">黑名单新增</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form action="${ctx}/context_fundout_foblacklist.controller.htm?method=add" method="post" id="frm1" name="frm1">
	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">黑名单ID：</td>
			<td class="border_top_right4">
				<input type="text" name="id" id="id1" maxlength="12" />
			</td>
			<td class=border_top_right4 align="right">黑名单类型：</td>
			<td class="border_top_right4">
				<input type="radio" id="type1" name="type" value="1" checked="checked">个人黑名单
				<input type="radio" id="type2" name="type" value="2">机构黑名单
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">成员单位编码：</td>
			<td class="border_top_right4">
				<input type="text" name="memberUnitCode" id="memberUnitCode1" value="1003" readonly="readonly" />
			</td>
			<td class=border_top_right4 align="right">身份证号码：</td>
			<td class="border_top_right4">
				<input type="text" name="identityCode" id="identityCode1" maxlength="18" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">姓名：</td>
			<td class="border_top_right4">
				<input type="text" name="name" id="name1" />
			</td>
			<td class=border_top_right4 align="right">发生地区：</td>
			<td class="border_top_right4">
				<input type="text" name="occuredArea" id="occuredArea1" maxlength="4" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">录黑途径：</td>
			<td class="border_top_right4">
				<input type="text" name="way" id="way1" maxlength="2" />
			</td>
			<td class=border_top_right4 align="right">黑名单事件：</td>
			<td class="border_top_right4">
				<input type="text" name="event" id="event1" maxlength="3" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">手机号码：</td>
			<td class="border_top_right4">
				<input type="text" name="mobile" id="mobile1" />
			</td>
			<td class=border_top_right4 align="right">银行卡号：</td>
			<td class="border_top_right4">
				<input type="text" name="bankCode" id="bankCode1" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">开户行：</td>
			<td class="border_top_right4">
				<input type="text" name="bankName" id="bankName1" size="40"/>
			</td>
			<td class=border_top_right4 align="right">邮箱：</td>
			<td class="border_top_right4">
				<input type="text" name="email" id="email1" size="40" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">ICP编号：</td>
			<td class="border_top_right4">
				<input type="text" name="icpCode" id="icpCode1" size="40"/>
			</td>
			<td class=border_top_right4 align="right">ICP备案人：</td>
			<td class="border_top_right4">
				<input type="text" name="icpMember" id="icpMember1" size="40" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">支付人：</td>
			<td class="border_top_right4">
				<input type="text" name="payerName" id="payerName1" size="40"/>
			</td>
			<td class=border_top_right4 align="right">标记时间：</td>
			<td class="border_top_right4">
				<input class="Wdate" type="text" id="markTime1" name="markTime" style="width: 150px;"   
   	   			onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'markTime\',{d:0});}'})">
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">数据状态：</td>
			<td class="border_top_right4" colspan="3">
				<select name="status" id="status1">
					<option value="1">黑名单</option>
					<option value="2">无效黑名单</option>
				</select>
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">固定电话号码：</td>
			<td class="border_top_right4" colspan="3">
				<input type="text" name="telephone" id="telephone1" size="100" />(多个号码间用‘#’隔开)
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">IP地址：</td>
			<td class="border_top_right4" colspan="3">
				<input type="text" name="ip" id="ip1" size="100" />(多个地址间用‘#’隔开)
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">MAC地址：</td>
			<td class="border_top_right4" colspan="3">
				<input type="text" name="mac" id="mac1" size="100" />(多个地址间用‘#’隔开)
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">URL地址：</td>
			<td class="border_top_right4" colspan="3">
				<input type="text" name="urlAddress" id="urlAddress1" size="100" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">URL跳转地址：</td>
			<td class="border_top_right4" colspan="3">
				<input type="text" name="urlBranchAddress" id="urlBranchAddress1" size="100" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">地址：</td>
			<td class="border_top_right4" colspan="3">
				<input type="text" name="address" id="address1" size="100" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">黑名单事件备注：</td>
			<td class="border_top_right4" colspan="3">
				<input type="text" name="remark" id="remark1" size="100" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td align="center" class="border_top_right4" colspan="4">
				<input id="btn1" type="button" class="button2" value="保存">
			</td>
		</tr>
		<c:if test="${not empty info}">
			<li style="color: red;" id="msg">${info}</li>
		</c:if>
	</table>
</form>
<form action="${ctx}/context_fundout_foblacklist.controller.htm?method=add" method="post" id="frm2" name="frm2">
	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">黑名单ID：</td>
			<td class="border_top_right4">
				<input type="text" name="id" id="id2" maxlength="12" />
			</td>
			<td class=border_top_right4 align="right">黑名单类型：</td>
			<td class="border_top_right4">
				<input type="radio" name="type" id="type3" value="1" checked="checked">个人黑名单
				<input type="radio" name="type" id="type4" value="2">机构黑名单
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">成员单位编码：</td>
			<td class="border_top_right4">
				<input type="text" name="memberUnitCode" id="memberUnitCode2" value="1003" readonly="readonly" />
			</td>
			<td class=border_top_right4 align="right">法人代表公民身份证号码：</td>
			<td class="border_top_right4">
				<input type="text" name="identityCode" id="identityCode2" maxlength="18" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">法人代表姓名：</td>
			<td class="border_top_right4">
				<input type="text" name="name" id="name2" />
			</td>
			<td class=border_top_right4 align="right">发生地区：</td>
			<td class="border_top_right4">
				<input type="text" name="occuredArea" id="occuredArea2" maxlength="4" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">录黑途径：</td>
			<td class="border_top_right4">
				<input type="text" name="way" id="way2"  maxlength="2" />
			</td>
			<td class=border_top_right4 align="right">黑名单事件：</td>
			<td class="border_top_right4">
				<input type="text" name="event" id="event2" maxlength="3" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">手机号码：</td>
			<td class="border_top_right4">
				<input type="text" name="mobile" id="mobile2" />
			</td>
			<td class=border_top_right4 align="right">银行卡号：</td>
			<td class="border_top_right4">
				<input type="text" name="bankCode" id="bankCode2" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">开户行：</td>
			<td class="border_top_right4">
				<input type="text" name="bankName" id="bankName2" size="40"/>
			</td>
			<td class=border_top_right4 align="right">邮箱：</td>
			<td class="border_top_right4">
				<input type="text" name="email" id="email2" size="40" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">ICP编号：</td>
			<td class="border_top_right4">
				<input type="text" name="icpCode" id="icpCode2" size="40"/>
			</td>
			<td class=border_top_right4 align="right">ICP备案人：</td>
			<td class="border_top_right4">
				<input type="text" name="icpMember" id="icpMember2" size="40" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">支付人：</td>
			<td class="border_top_right4">
				<input type="text" name="payerName" id="payerName2" size="40"/>
			</td>
			<td class=border_top_right4 align="right">营业执照编号：</td>
			<td class="border_top_right4">
				<input type="text" name="businessCode" id="businessCode2" size="40" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">组织结构代码：</td>
			<td class="border_top_right4">
				<input type="text" name="orgCode" id="orgCode2" size="40" />
			</td>
			<td class=border_top_right4 align="right">机构名称：</td>
			<td class="border_top_right4">
				<input type="text" name="orgName" id="orgName2" size="40" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">数据状态：</td>
			<td class="border_top_right4">
				<select name="status" id="status2">
					<option value="1">黑名单</option>
					<option value="2">无效黑名单</option>
				</select>
			</td>
			<td class=border_top_right4 align="right">标记时间：</td>
			<td class="border_top_right4">
				<input class="Wdate" type="text" id="markTime2" name="markTime" style="width: 150px;"   
   	   			onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'markTime\',{d:0});}'})">
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">固定电话号码：</td>
			<td class="border_top_right4" colspan="3">
				<input type="text" name="telephone" id="telephone2" size="100" />(多个号码间用‘#’隔开)
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">IP地址：</td>
			<td class="border_top_right4" colspan="3">
				<input type="text" name="ip" id="ip2" size="100" />(多个地址间用‘#’隔开)
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">MAC地址：</td>
			<td class="border_top_right4" colspan="3">
				<input type="text" name="mac" id="mac2" size="100" />(多个地址间用‘#’隔开)
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">URL地址：</td>
			<td class="border_top_right4" colspan="3">
				<input type="text" name="urlAddress" id="urlAddress2" size="100" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">URL跳转地址：</td>
			<td class="border_top_right4" colspan="3">
				<input type="text" name="urlBranchAddress" id="urlBranchAddress2" size="100" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">地址：</td>
			<td class="border_top_right4" colspan="3">
				<input type="text" name="address" id="address2" size="100" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">黑名单事件备注：</td>
			<td class="border_top_right4" colspan="3">
				<input type="text" name="remark" id="remark2" size="100" />
			</td>
		</tr>
		<tr class="trForContent1">
			<td align="center" colspan="4">
				<input id="btn2" type="button" class="button2" value="保存">
			</td>
		</tr>
	</table>
</form>