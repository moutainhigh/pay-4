<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
	<%--引入用于格式化页面的CSS文件--%>
	<link rel="stylesheet" href="./css/main.css">
	<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>

	<script language="javascript">
		<!--
		var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
		$(function(){
			query();
		});
		function query(pageNo, totalCount) {
			$('#infoLoadingDiv').dialog('open');
			var pars = $("#paymentChannelItemFormBean").serialize()+ "&pageNo=" + pageNo
					+ "&totalCount=" + totalCount; ;
			$.ajax({
				type: "POST",
				url: "${ctx}/channelConfig.htm?method=query",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
			$("#checkAll").attr("checked",false);
			selectFlag = false;
		}
		function add() {
			window.location.href="${ctx}/channelConfig.htm?method=initAdd";
		}
		var selectFlag=false;
		function showButton(){
			var showFlag = false;
			$(".can_choose").each(function(index,obj){
				var $thisObj = $(this);
				showFlag = showFlag || $thisObj.attr("checked");
			});
			if(showFlag){
				$("#addPool").show();
			}else{
				$("#addPool").hide();
			}
		}
		function selectAll(){
			selectFlag = !selectFlag;
			$("input[type='checkbox']").each(function(index,obj){
				var $thisObj = $(this);
				if($thisObj.hasClass('can_choose'))
					$thisObj.attr("checked", selectFlag);
			});
			showButton();
		}
		function del(id,orgCode,orgMerchantCode) {

			if(confirm('删除该记录会将配置的商户关联将一起删除，确认删除吗？')){
				var pars = "id=" + id + "&orgCode=" + orgCode + "&orgMerchantCode=" + orgMerchantCode;
				var url="${ctx}/channelConfig.htm?method=del";
				$.post(url,pars,function(res){
					if(1==res){
						alert('操作成功');
						query();
					}else{
						alert(res);
					}
				});
			}

		}

		function addAucrPool(){
			var ids = "ids=";
			$(".can_choose").each(function(index,obj){
				var $thisObj = $(this);
				if($thisObj.attr("checked")){
					ids = ids+ $thisObj.next().val() + "&ids=";
				}
			});
			var url = "${ctx}/channelConfig.htm?method=addToFree";
			if(ids.length > 5){
				ids = ids.substring(0,ids.length - 5);
				$.post(url,ids,function(res){
					if(1==res){
						alert('操作成功');
						query();
						$("#addPool").hide();
					}else{
						alert(res);
					}
				});

			}
		}

		function viewAucrPool(){
			window.location.href = "${ctx}/channelConfig.htm?method=viewAucrPool";
		}

		function batchAdd(){
			window.location.href = "${ctx}/channelConfig.htm?method=initBatchAdd";
		}
		-->
	</script>
</head>

<body>
<h2 class="panel_title">通道二级商户号管理</h2>
<form id="paymentChannelItemFormBean" name="paymentChannelItemFormBean" >
	<table class="border_all2" width="95%" border="0" cellspacing="0"
		   cellpadding="1" align="center">

		<tr class="trForContent1">
			<td class="border_top_right4" align="left">
				渠道名称：<select id="orgCode" name="orgCode">
				<option value="" selected>---请选择---</option>
				<c:forEach var="channel" items="${channelItems}" varStatus="v">
					<option value="${channel.code}">${channel.desc}</option>
				</c:forEach>
			</select>
			</td>
			<td class="border_top_right4" align="left" >
				机构商户号：<input type="text" id="orgMerchantCode" name="orgMerchantCode" value="${orgMerchantCode}">
			</td>
			<td align="left" class="border_top_right4">
				币种：
				<select id="currencyCode" name="currencyCode">
					<option value="">--请选择--</option>
					<c:forEach var="curCode" items="${currencyCodeEnum}" varStatus="v">
						<option value="${curCode.code}">${curCode.desc}</option>
					</c:forEach>
				</select>
				Credorax下必填
			</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="left" >
				MCC：<input type="text" id="mcc" name="mcc" value="">
			</td>
			<td class="border_top_right4" align="left">
				状态：<select id="status" name="status" size="1">
				<option value="" selected>---请选择---</option>
				<option value="1" >启用</option>
				<option value="0" >禁用</option>
			</select>
			</td>
			<td class="border_top_right4" align="left">
				商户类型：<select id="fitMerchantType" name="fitMerchantType" size="1">
				<option value="" selected>---请选择---</option>
				<option value="500" >iPayLinks</option>
				<option value="800" >GCPayment</option>
			</select>
			</td>
		</tr>

		<tr class="trForContent1">
			<td class="border_top_right4" colspan="3" align="center">

				<input type="button" id="addPool" value="添加自动配置表" onclick="addAucrPool();" style="display: none">
				<input type="button" value="查看自动配置表" onclick="viewAucrPool();">

				<input type="button" value="检索" onclick="query();">

				<input type="button" value="新增" onclick="add();">

				<input type="button" value="批量新增" onclick="batchAdd();">
			</td>
		</tr>

	</table>
</form>

<div id="resultListDiv" class="listFence"></div>
<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>
</body>

