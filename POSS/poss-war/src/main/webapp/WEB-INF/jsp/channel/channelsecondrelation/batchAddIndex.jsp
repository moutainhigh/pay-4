<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
<%--引入用于格式化页面的CSS文件--%>
<link rel="stylesheet" href="./css/main.css">
<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>
<script src="${ctx}/js/xdate.js" type="text/javascript"></script>

<script language="javascript">
	function controlDate(){
		var memberCode=$("#memberCode").val();
		if(memberCode=="10000003681"){
			var firstDate = new Date();
			firstDate.setDate(1); //第一天
			var endDate = new Date(firstDate);
			endDate.setMonth(firstDate.getMonth()+1);
			endDate.setDate(0);
			//alert("第一天："+firstDate.toString('yyyy-MM-dd')+" 最后一天："+endDate.toString('yyyy-MM-dd'));
			$("#startTime").val(new XDate(firstDate).toString('yyyy-MM-dd'));
			$("#endTime").val(new XDate(endDate).toString('yyyy-MM-dd'));
		}else{
			var type=$("#type").val();
			if(type == '2'){
				$("#startTime").val();
				var firstDate = new Date();
				var endDate = new Date(firstDate);
				endDate.setMonth(firstDate.getMonth()-3);
				$("#endTime").val(new XDate(endDate).toString('yyyy-MM-dd'));
			}else{
				$("#endTime").val('');
			}
		}
	}  
	//全选/全不选
	function cheked(){
		 if($("#check").attr("checked")==true){
			 $("input[type='checkbox']").each(function(){
				 this.checked=true;
			 })
		 }else{
			 $("input[type='checkbox']").each(function(){
				 this.checked=false;
			 })
		 }
	}
	function showButton(){

	}
	//返回	
	function back(){
		window.location.href="${ctx}/second_merchant_relation.htm";
	}
	//批量添加
	function add(){
		var memberCode=$("#memberCode").val();
		var orgCode=$("#orgCode").val();
		var orgMerchantCode="";
		var channelConfigId = "";
		 $("input[type='checkbox']").each(function(){
			 if(this.checked){
				if($(this).val()!='on'){
					orgMerchantCode+=$(this).val()+",";
					channelConfigId+=$(this).next().val() + ",";
				}
			};
		 })
		 if(memberCode==''){
			 alert("请输入会员号！");
			 return;
		 }
		 if(orgCode==''){
			 alert("请选择通道！");
			 return;
		 }
		if(orgMerchantCode==''){
			alert("请选择需要关联的二级商户号！");
			return;
		}
		window.location.href="${ctx}/second_merchant_relation.htm?method=batchAdd&memberCode="+memberCode+"&orgCode="+orgCode+"&orgMerchantCode="+orgMerchantCode + "&channelConfigIds=" + channelConfigId;
	}
	
	function download(){
			var pars = $("#channelConfigForm").serialize();
		window.location.href="${ctx}/second_merchant_relation.htm?method=download&"+pars
	}
</script>
</head>

<body>
<h2 class="panel_title">通道二级商户号添加</h2>
<c:if test="${not empty errorMsg}">
	<p><font color="red"><b>${errorMsg}</b></font></p>
</c:if>
	 
<form id="channelConfigForm" name="channelConfigForm"  enctype="multipart/form-data" action="second_merchant_relation.htm?method=batchQuery"  method="POST"  >
	<table class="border_all2" width="95%" border="0" cellspacing="0"
		cellpadding="1" align="center">		
			
			<tr class="trForContent1">
			   <td align="right" class="border_top_right4"> 通道名称</td>
				<td class="border_top_right4" >
					<select id="orgCode" name="orgCode">
						<option value="" selected>---请选择---</option>
						<c:forEach var="channel" items="${channelItems}" varStatus="v">
							<option value="${channel.orgCode}-${channel.id}-${channel.paymentCategoryCode}"  <c:if test="${orgCode==channel.orgCode && paymentCategoryCode == channel.paymentCategoryCode}">  selected</c:if>> ${channel.name}</option>
						</c:forEach>
					</select>	
				</td>
				<td align="right" class="border_top_right4"> 二级商户号</td>
				<td class="border_top_right4" >
					<input  type="text" id="orgMerchantCode" name="orgMerchantCode"  />
				</td>
			   <td align="right" class="border_top_right4">商户类型</td>
				<td class="border_top_right4" >
					<select id="fitMerchantType" name="fitMerchantType" size="1">
						<option value="" selected>---请选择---</option>
						<option value="500" >iPayLinks</option>
						<option value="800" >GCPayment</option>
					</select>
				</td>
			</tr>	
			<tr class="trForContent1">
					<td align="right" class="border_top_right4"> 类型</td>
					<td class="border_top_right4" >
						<select id="type" name="type" onchange="controlDate();">
							<option value="1" <c:if test="${param.type=='1'}">selected</c:if>  >新增</option>
							<option value="2" <c:if test="${param.type=='2'}">selected</c:if>>已删除</option>
							<option value="3" <c:if test="${param.type=='3'}">selected</c:if>>已关联</option>
						</select>
					</td>
					<td align="right" class="border_top_right4">添加时间：</td>
					<td class="border_top_right4">
				      	<input class="Wdate" type="text" id="startTime" value="${param.startTime}" name="startTime"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
			      	</td>
					<td align="right" class="border_top_right4">删除时间：</td>
					<td class="border_top_right4">
				      	<input class="Wdate" type="text" id="endTime" value="${param.endTime}" name="endTime"  onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
			      	</td>
			   </tr>
			<tr class="trForContent1">			
				<td class="border_top_right4" colspan="6" align="center">
						<input type="submit" class="button2" value="查询" > &nbsp;&nbsp;
						<input type="button" class="button2" value="下载" onclick="download();">&nbsp;&nbsp;
						<input type="button" class="button2" value="返回" onclick="back();">&nbsp;&nbsp;
						<span id="showOrHide">
							会员号：<input  type="text" id="memberCode" name="memberCode" />&nbsp;&nbsp;
						<input type="button" class="button2" value="新增" onclick="add();" id="addButton">
						</span>

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
<table class="tablesorter" border="0" cellpadding="0" cellspacing="1">
			<thead>
				<tr>
					<th>全选/反全<input type="checkbox" onclick="cheked()" id="check"></th>
					<th>会员号</th>
					<th>通道名称</th>
					<th>二级商户号</th>
					<th>交易类型</th>
					<th>模式</th>
					<th>本月拒付比例</th>
					<th>本月交易笔数</th>
					<th>商户类型</th>
					<th>添加时间</th>
					<th>删除时间</th>
				</tr>
			</thead>
			<tbody>
				<c:forEach var="item" items="${list}" varStatus="v">
					<c:choose>
	       <c:when test="${v.index%2==0}">
	             <tr class="trForContent1">
	       </c:when>
	       <c:otherwise>
	             <tr class="trForContent2">
	       </c:otherwise>
		</c:choose>	
						<td>
							<input type="checkbox"  value="${item.orgMerchantCode}" onclick="showButton()" class="can_choose">
							<input type="hidden"  value="${item.id}">
						</td>
						<td>${item.memberCode}</td>
						<td>
						${item.paymentItemName}
						</td>
						<td>${item.orgMerchantCode}</td>
						<td>
								${item.transType}
						</td>
						<td>${item.pattern }</td>
						<td>${item.cptPercent}</td>
						<td>${item.tradeCnt}</td>
						<td>
						<c:if test="${item.fitMerchantType== '500'}">iPayLinks</c:if>
						<c:if test="${item.fitMerchantType== '800'}">GCPayment</c:if></td>
						<td><date:date value="${item.createRelateDate}"/></td>
						<td><date:date value="${item.cancelRelateDate}"/></td>
					</tr>
				</c:forEach>
			</tbody>
		</table>
