<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<script type="text/javascript">
	$(document).ready(function(){
		$("#btn").click(function(){
			if(checkInfo()){

				var url="${ctx}/couponList.do?method=add";
				var data= $("#frm").serialize();
				$.post(url,data,function(res){
					if(1==res){
						alert('操作成功');
						window.location.href="${ctx}/couponList.do";
					}else{
						alert(res);
					}
				});
			}
		});
		
	});

	
	
	function checkInfo(){
		
		var orgCode = $("#orgCode").val();
		var couponNumber = $("#couponNumber").val();
		
		if(orgCode == ""){
			alert("请填写发行机构!");
			$("#orgCode").focus();
			return false;
		}
		if(couponNumber == ""){
			alert("请输入优惠券号码!");
			$("#couponNumber").focus();
			return false;
		}
		
		return true;
	}
</script>

<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
			<div align="center">
				<font class="titletext">新增优惠券</font>
			</div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<form action="${ctx}/couponList.do?method=add" method="post" id="frm">
	<table class="border_all2" width="60%" border="0" cellspacing="0" cellpadding="1" align="center">
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">发行机构：</td>
			<td class="border_top_right4">
				<input type="text" name="orgCode" id="orgCode" value="" />必填
			</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">优惠券号码：</td>
			<td class="border_top_right4">
				<input type="text" name="couponNumber" id="couponNumber" value="" />必填
			</td>
		</tr>
		<tr height="6" class="trForContent1">
			<td align="right" class="border_top_right4">面值：</td>
			<td align="left" class="border_top_right4"><input type="text" name="value" id="value"/>必填</td>
		</tr>
		<tr height="6" class="trForContent1">
			<td align="right" class="border_top_right4">最低订单金额：</td>
			<td align="left" class="border_top_right4">
				<input type="text" name="minOrderAmount" id="minOrderAmount"/>
			</td>
		</tr>
		<tr height="6" class="trForContent1">
			<td align="right" class="border_top_right4">生效时间：</td>
			<td align="left" class="border_top_right4">
				<input class="Wdate" type="text" id="effectDate" value="${effectDate}" name="effectDate" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',maxDate:'#F{$dp.$D(\'expireDate\')}'})">
			必填</td>
		</tr>
		<tr height="6" class="trForContent1">
			<td align="right" class="border_top_right4">失效时间：</td>
			<td align="left" class="border_top_right4">
				<input class="Wdate" type="text" id="expireDate" name="expireDate" value="${expireDate}" onClick="WdatePicker({dateFmt:'yyyy-MM-dd',minDate:'#F{$dp.$D(\'effectDate\')}'})">
			必填</td>
		</tr>
		<tr class="trForContent1">
			<td class=border_top_right4 align="right">使用场景：</td>
			<td class="border_top_right4">
				<input type="text" name="scene" id="scene" value="" />
			</td>
		</tr>
		
		<tr class="trForContent1">
			<td align="center" colspan="4">
				<input id="btn" type="button" class="button2" value="保存">
				<input type="button" class="button2" onclick="window.history.go(-1);" value="返回">
			</td>
		</tr>
	</table>
</form>