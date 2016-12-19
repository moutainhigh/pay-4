<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<%@ taglib uri="poss" prefix="li" %>

<head>
<link rel="stylesheet" href="${ctx}/css/jq.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="${ctx}/css/style.css" type="text/css" media="print, projection, screen" />
<link rel="stylesheet" href="./css/main.css">
<script language='javascript' src="${ctx}/js/jquerydialog.js"></script>
<script src="./js/common.js"></script>
<script language="javascript">
	function operate(oper,url){
		if(confirm("确定要让该链接关闭吗?")){
			window.location = url;
		}		
	}
	
	function payChainDetail(url){
		parent.addMenu("支付链收款链接信息",url);
	}
	
	function modifyTime(no,value){

		widgetDisplay(no,true,value);
	}
	
	function cancelModifyTime(no){
		widgetDisplay(no,false);
	}
	
	function saveModifyTime(no,id){
		//widgetDisplay(no,false);
		
		var effectiveType =  $("#select_"+ no).val();
		var url = "payChainChargeManagerQuery.do?method=modifyPayChainDate&payChainNumber="
				+ no+"&id="+id+"&effectiveType="+effectiveType;
		window.location = url;
	}
	
	function widgetDisplay(no,isEdit,value){
		if(!isEdit){
			$("#span_"+ no).show();
			$("#select_"+ no).hide();
			
			$("#a1_"+ no).show();
			$("#a2_"+ no).hide();
			$("#a3_"+ no).hide();
		}else{
			$("#span_"+ no).hide();
			$("#select_"+ no).show();
			
			$("#a1_"+ no).hide();
			$("#a2_"+ no).show();
			$("#a3_"+ no).show();
			
			//默认选择原来的值
			var sobj = $("#select_"+ no)[0];
			for(var i =0 ; i < sobj.options.length ;i++){
				if(value == sobj.options[i].value){
					sobj.options[i].selected = true;
					break;
				}
			}
		}
	}
</script>
</head>

<body>
<form>

<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">
	<!-- 
		<c:if test="${show=='YES'}">
			<tr > 
				<td height="2" ><font color="red" >操作成功</font></td>
			</tr>
		</c:if>
	 -->
	<tr class="trbgcolorForTitle" align="center" valign="middle">
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">有效时长</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">商户名称</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">收款链接生成时间</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">过期时间</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">收款链接编号</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">收款名称</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">收款描述</font> </a></td>		
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">已支付笔数</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">已支付金额</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">收款链接状态</font> </a></td>
		<td class="border_right4"  nowrap><a class="s03"><font color="#FFFFFF">操作</font> </a></td>

	</tr>
	<c:forEach items="${page.result}" var="payChainManager" varStatus = "payChainManagerStatus">
	<c:choose>
       <c:when test="${payChainManagerStatus.index%2==0}">
             <tr class="trForContent1">
       </c:when>
       <c:otherwise>
             <tr class="trForContent2">
       </c:otherwise>
	</c:choose>
		<td class="border_top_right4" align="center" nowrap>
			<span id="span_${payChainManager.payChainNumber}" style="display:inline;">${payChainManager.effectiveDateName}</span>
			<c:if test="${payChainManager.status =='1' && ! payChainManager.expired && payChainManager.displayEffectiveType <= 5}">
			<select id="select_${payChainManager.payChainNumber}" style="display:none;">
				<c:forEach items="${EffectiveTypeEnums}" var="EffectiveTypeEnum" >
					<c:if test="${EffectiveTypeEnum.type >= payChainManager.displayEffectiveType}" >
					<option value="${EffectiveTypeEnum.type}">${EffectiveTypeEnum.memo}</option>
					</c:if>
				</c:forEach>
			</select>
			<a id="a1_${payChainManager.payChainNumber}" href="javascript:modifyTime('${payChainManager.payChainNumber}','${payChainManager.effectiveDate}');" style="display:inline;">修改</a>&nbsp;
			<a id="a2_${payChainManager.payChainNumber}" href="javascript:saveModifyTime('${payChainManager.payChainNumber}','${payChainManager.id}');" style="display:none;">保存</a>&nbsp;
			<a id="a3_${payChainManager.payChainNumber}" href="javascript:cancelModifyTime('${payChainManager.payChainNumber}');" style="display:none;">取消</a>&nbsp;
			</c:if>
		</td>
		<td class="border_top_right4" align="center" nowrap style="white-space:pre-wrap;word-wrap:break-word;word-break:break-all;" width="70">${payChainManager.zhName}&nbsp;</td>
		<td class="border_top_right4" align="center" nowrap>${payChainManager.strCreateDate}&nbsp;</td>
		<td class="border_top_right4" align="center" nowrap>${payChainManager.strOverdueDate}&nbsp;</td>		
		<td class="border_top_right4" align="center" nowrap>${payChainManager.payChainNumber}&nbsp;</td>
		<td class="border_top_right4" align="center" nowrap style="white-space:pre-wrap;word-wrap:break-word;word-break:break-all;" width="150">${payChainManager.paychainName}&nbsp;</td>
				<td class="border_top_right4" align="center" nowrap style="white-space:pre-wrap;word-wrap:break-word;word-break:break-all;" width="200">${payChainManager.receiptDescription}&nbsp;</td>		
		<td class="border_top_right4" align="center" nowrap>${payChainManager.total}&nbsp;</td>
		<td class="border_top_right4" align="center" nowrap>${payChainManager.strTotalAmount}&nbsp;</td>
		<td class="border_top_right4" align="center" nowrap>${payChainManager.strStatus}&nbsp;</td>
		<td class="border_top_right4" align="center" nowrap>
			<c:if test="${payChainManager.status=='1'}">
				<a href="javascript:operate('close','payChainChargeManagerQuery.do?method=operatePayChain&payChainNumber=${payChainManager.payChainNumber}&operate=close');">&nbsp;关闭</a>&nbsp;
			</c:if>
				<a href="javascript:payChainDetail('payChainChargeManagerQuery.do?method=queryDetail&payChainNumber=${payChainManager.payChainNumber}');">查看</a>&nbsp;
				
		</td>
		</tr>
	</c:forEach>
	
</table>

<table  width="95%" border="0" cellspacing="0"
	cellpadding="0" align="center">
	<tr class="border_top_right4" align="center" valign="middle"></tr>
	</br>
	<tr class="trForContent1" align="center" valign="middle">
		<table width="95%" class="trForContent1">
			<tr>
				<td align="center">
					<span>共支付: ${payChainStatDto.recordsPaySum} </span> 
					<span style="padding:0 20px;">支付总金额: ${payChainStatDto.recordsAmountSum}</span>
				</td>
			</tr>
		</table>
	</tr>
</table>
	
</form>
<li:pagination methodName="payChainManagerQuery" pageBean="${page}" sytleName="black2"/>
</body>

