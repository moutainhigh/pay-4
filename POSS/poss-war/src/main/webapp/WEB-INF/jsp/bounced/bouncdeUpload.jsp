<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>

<h2 class="panel_title">拒付批量登记</h2>
<form action="bounced-register.do" enctype="multipart/form-data" method="post" id="batchForm" name="batchForm">

	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
		<tr class="trForContent1">
			<td class="border_top_right4" class='border_top_right4' width='24%' align='center'>渠道： <select id="orgCode" name="orgCode" required="required">
					<option value="" selected>--请选择--</option>
					<option value="10076001">中银卡司</option>
					<option value="10079001">中银MOTO</option>
					<option value="10080001">中银MIGS</option>
					<option value="10003001">中国银行</option>
					<option value="10002001">农业银行</option>
					<option value="10075001">CREDORAX</option>
					<option value="10077001">Adyen</option>
					<option value="10077002">Belto</option>
					<option value="10077003">Cashu</option>
					<option value="10078001">农行CTV</option>
					<option value="10077004">新生支付</option>
					<option value="10081001">CT_BOLETO</option>
					<option value="10081002">CT_SAFETYPAY</option>
					<option value="10081003">CT_DirectDebitsSSL</option>
					<option value="10081004">CT_SofortBanking</option>
					<option value="10081005">CT_EPS</option>
					<option value="10081006">CT_Giropay</option>
					<option value="10081007">CT_PagBrasilDebitCard</option>
					<option value="10081008">CT_PagBrasilOTF</option>
					<option value="10081009">CT_Poli</option>
					<option value="10081010">CT_Przelewy24</option>
					<option value="10081011">CT_Qiwi</option>
					<option value="10081012">CT_SEPA</option>
					<option value="10081013">CT_Teleingreso</option>
					<option value="10081014">CT_TrustPay</option>
					<option value="10081015">CT_iDeal</option>
					<option value="10081016">前海万融</option>
					<option value="00000000">内部调单</option>
			        </select><font color="red" >*</font></td>

			<td class="border_top_right4" class='border_top_right4' width='24%' align='center'>拒付时间：<input
					class='Wdate' type='text' id='cpdDate' name='cpdDate'
					value='<fmt:formatDate value='${todayDate}' type='date' pattern='yyyy-MM-dd'/>'
					onClick='WdatePicker()' required="required"><font color="red" >*</font></td>
			<td class="border_top_right4" class='border_top_right4' width='24%' align='center'>最晚回复时间：<input
					class='Wdate' type='text' id='lastDate' name='lastDate'
					value='<fmt:formatDate value='${todayDate}' type='date' pattern='yyyy-MM-dd'/>'
					onClick='WdatePicker()'>18:00<font color="red" >*</font></td>
			<td class="border_top_right4" class='border_top_right4' align='center'>拒付类型：<select
					id='bouncedType' name='bouncedType'>
						<option value='' selected>---请选择---</option>
						<option value='0'>拒付</option>
						<option value='1'>银行调单</option>
						<option value='2'>内部调单</option>
				</select><font color="red" >*</font></td>		
			</tr>
			<tr align="center" class="trForContent1" colspan='4'>
			<td class="border_top_right4"  colspan="2">
      		文件上传：<input type="file"  name="file" id="file"   value="点击上传">
      		</td>
	     	<td class="border_top_right4" ><input type="button" value="确认提交" onclick="return uploadeAdd(this);"
				class="button2"></td>
			 <td class="border_top_right4" >
			 <a href="./bounced-register.do?method=downLoadFile&fileName=bouncedInside.xlsx" 
			 id="url_template" name="url_template" style="text-decoration:underline;">
			  内部调单模板下载</a></td>
      	
			
     </tr>
		</table>
	</form>
	

<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>   
<script type="text/javascript">
function uploadeAdd(){
var file = $("#file").val();
	
	if("" == file || null == file){
			alert("请选择上传文件！");
			return false;
	}
	var bouncedType = $("#bouncedType").val();
	var lastDate = $("#lastDate").val();
	var cpdDate = $("#cpdDate").val();
	var orgCode = $("#orgCode").val();
	if(bouncedType=="")
		{
		alert("请选择拒付类型！");
		return false;
		}
	if(lastDate=="")
	{
	alert("请输入最后回复日期！");
	return false;
	}
	if(cpdDate=="")
	{
	alert("请输入拒付时间！");
	return false;
	}
	if(orgCode=="")
	{
	alert("请选择渠道！");
	return false;
	}
	document.batchForm.action="bounced-register.do?method=submitUploade";
	document.batchForm.submit();
}
function uploadeUpdate(){
var file = $("#file_trackinguploade").val();
	
	if("" == file || null == file){
			alert("请选择上传文件！");
			return false;
	}
	document.DCCForm.action="dcc_configuration.do?method=submitUploade&type=update";
	document.DCCForm.submit();
}
function back() {
	window.location.replace("./dcc_configuration.do?method=index");
}	
</script>	 
<script type="text/javascript">

	function checkNum(obj) {
		//检查是否是非数字值
		if (isNaN(obj.value)) {
			obj.value = "";
		}
	}
	function decimals(obj) {
		var markup = obj.value;//获取markup
		if (markup && markup.indexOf(".") == -1) {
			var index;
			var zeroCount = 0;
			for (var i = 0; i < markup.length; i++) {
				if (markup.charAt(i) != "0") {
					index = i;
					break;
				} else {
					zeroCount++;
				}
			}
			if (zeroCount == markup.length) {
				$("table tr td input[name=markup]").attr("value", "");
			} else if (index) {
				$("table tr td input[name=markup]").attr("value",
						markup.substr(index));
			}
		} else if (markup && markup.indexOf(".") > -1) {
			var left = markup.split(".")[0];
			var right = markup.split(".")[1];
			var zeroCount = 0;
			for (var i = 0; i < left.length; i++) {
				if (left.charAt(i) == "0") {
					zeroCount++;
				} else {
					break;
				}
			}
			if (zeroCount == left.length) {
				left = left.substr(zeroCount - 1);
				markup = left + "." + right;
				$("table tr td input[name=markup]").attr("value",
						left + "." + right);
			} else if (zeroCount > 0) {
				left = left.substr(zeroCount);
				markup = left + "." + right;
				$("table tr td input[name=markup]").attr("value",
						left + "." + right);
			}
			if (right.length > 2) {
				markup = markup.substring(0, markup.indexOf(".") + 3);
				$("table tr td input[name=markup]").attr("value", markup);
			}
		}
	}
</script>	
