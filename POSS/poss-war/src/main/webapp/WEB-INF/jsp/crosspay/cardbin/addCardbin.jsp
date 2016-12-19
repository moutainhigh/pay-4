<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<h2 class="panel_title">创建新规则</h2>
<form action="${ctx}/cardbin/cardfilter.do?method=add" method="post"
	id="mainfromId" enctype="multipart/form-data" onsubmit="return sub();">
	<table class="border_all2" width="700" border="1" cellspacing="0"
		cellpadding="0" align="center">
		<tr class="trForContent1">
			<td align="right" class="border_top_right4">商户会员号：<font
				color="red">*</font></td>
			<td class="border_top_right4"><input name="partnerId"
				id="partnerId" /></td>
		</tr>
		<tr class="trForContent1">
			<td align="right" class="border_top_right4">类型：<font
				color="red">*</font></td>
			<td class="border_top_right4"><select name="cardFilterType"
				id="cardFilterType" onchange="getEndCardNo()">
					<option value="0" selected>卡号</option>
					<option value="1" selected>卡bin段</option>
			</select></td>
		</tr>
		<tr class="trForContent1">
			<td align="right" class="border_top_right4">卡号1：<font
				color="red">*</font></td>
			<td class="border_top_right4"><input type="text"
				id="startCardno" name="startCardno" maxlength="19"
				onblur="getEndCardNo()" /></td>
		</tr>
		<tr class="trForContent1">
			<td align="right" class="border_top_right4">卡号2：<font
				color="red">*</font></td>
			<td class="border_top_right4"><input type="text" id="endCardNo"
				name="endCardNo" maxlength="19"></td>
		</tr>
		<tr class="trForContent1">
			<td align="center" class="border_top_right4" colspan="8"><font
				color=red>新增的卡号或卡bin段将不再收取Markup!</font> <input type="submit"
				value="添  加" class="button2"> <input type="button"
				value="返  回" class="button2" onclick="toIndex();"></td>
		</tr>
	</table>
</form>

<c:if test="${message!=null}">
		<tr>
			<td height="2" ><font color="red" >${message}</font></td>
		</tr>
</c:if>

<script type="text/javascript">
	function toIndex() {
		window.location.href = "${ctx}/cardbin/cardfilter.do";
	}

	function sub() {
		re = new RegExp("^[0-9]+$")
		var partnerId = $('#partnerId').val();
		if ("" == partnerId) {
			alert("请输入商户会员号！");
			return false;
		}
		if (!re.test(partnerId))
		{
			alert("商户会员号必须为数字！");
			return false;
		}
		var cardFilterType = $("#cardFilterType").val();
		if ("" == cardFilterType) {
			alert("请选择类型！");
			return false;
		}

		var startCardno = $("#startCardno").val();
		if ('' == startCardno) {
			alert("请输入卡号1!");
			return false;
		}
		var endCardNo = $("#endCardNo").val();
		if ('1' == cardFilterType && '' == endCardNo) {
			alert("请输入卡号2!");
			return false;
		}

		if (endCardNo.length < 15) {
			alert("卡号长度不足15！");
			return false;
		}
		if (startCardno.length < 15) {
			alert("卡号长度不足15！");
			return false;
		}
		if (startCardno > endCardNo) {
			alert("卡号1不能大于卡号2！");
			$('#endCardNo').val("");
			return false;
		}
		return true;
	}
	function getEndCardNo() {
		var cardFilterType = $("#cardFilterType").val();
		if (0 == cardFilterType) {
			var startCardno = $("#startCardno").val();
			$('#endCardNo').val(startCardno);
			$('#endCardNo').attr('disabled', true);
		} else {
			$('#endCardNo').val("");
			$('#endCardNo').attr('disabled', false);
		}
	}
</script>