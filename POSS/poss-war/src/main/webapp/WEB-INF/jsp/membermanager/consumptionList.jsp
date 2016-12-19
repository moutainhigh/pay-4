
<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
<%--引入用于格式化页面的CSS文件--%>
<link rel="stylesheet" href="./css/main.css">
<script language="javascript">
function  validatMemberTradeSearchForm(){

	var i=0;
		
	if(document.getElementById('tradeTime').value!=""){i++;}
	if(document.getElementById('tradeType').value!=""){i++;}
	
	
	if(i==0){alert("必须至少输入一个查询条件 !");return;}

	this.submitMemberTradeSearchForm();
}
function submitMemberTradeSearchForm(){
	 document.memberTradeSearchFormBean.submit();
}

function showMoreCondition(){
	if( document.getElementById('moreCondition1').style.display==''){
		document.getElementById('moreCondition1').style.display='none';
		document.getElementById('moreCondition2').style.display='none';
	}else{
		document.getElementById('moreCondition1').style.display='';
		document.getElementById('moreCondition2').style.display='';
	}
	 
}

</script>
</head>

<body>
<br>
<br>
<p align="center">
<table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">消 费 信 息 查 询</font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table>
<br>




<table class="border_all2" width="80%" border="0" cellspacing="0"	cellpadding="0" align="center">
	<tr class="trForContent2">
		   <td  align="left"  colspan="6"><font class="titletext">用户名：gungun_99@163.com</font></td>
			
			
	</tr>
	<tr class="trForContent2">
		   
			<td  align="right"  colspan="4"><font color="red">余额：33333元  可用余额：33333元</font></td>
			<td  align="right"  colspan="2"><input type="button" onclick="javascript:showMoreCondition()" value="更多筛选方式"></input></td>
	</tr>
	
	<tr>
	<td  colspan="6">
	<table width="100%" border="0" cellspacing="0"	>
	<tr class="trForContent2">
		<td align="right" width="5%">账户类型：</td>
		<td  align="left" width="15%" >
			<select id="tradeType" name="tradeType" size="1">
				<option value="" selected>---请选择---</option>	
				<option value="" >人民币账户</option>
				<option value="" >美元账户</option>			
			</select>
		</td>
		<td  align="right" width="5%">交易状态：</td>
			<td align="left" width="15%" >
				<select id="tradeType" name="tradeType" size="1">
					<option value="" selected>---请选择---</option>	
					<option value="" >进行中的记录</option>
					<option value="" >成功的记录</option>	
					<option value="" >失败的记录</option>			
				</select>
			</td>
			<td  align="right" width="5%">时间：</td>
			<td align="left" width="15%" >
				<select id="tradeType" name="tradeType" size="1">
					<option value="" selected>---请选择---</option>
					<option value="" >当天</option>
					<option value="" >本周</option>
					<option value="" >本月</option>				
				</select>
			</td>
	</tr>
	<tr class="trForContent2" id="moreCondition1" style="display:none">
		<td   align="right" width="5%">资金流向：</td>
			<td  align="left" width="15%" >
				<select id="tradeType" name="tradeType" size="1">
					<option value="" selected>---请选择---</option>	
					<option value="" >收入</option>	
					<option value="" >支出</option>			
				</select>
			</td>
			<td  align="right" width="5%">消费类型：</td>
			<td align="left" width="15%" >
				<select id="tradeType" name="tradeType" size="1">
					<option value="" selected>---请选择---</option>
					<option value="" >系统担保</option>	
					<option value="" >货到付款</option>
					<option value="" >即时付款</option>					
				</select>
			</td>
			<td  align="right" width="5%">消费场所：</td>
			<td align="left" width="15%" >
				<select id="tradeType" name="tradeType" size="1">
					<option value="" selected>---请选择---</option>	
					<option value="" >系统商城</option>	
					<option value="" >系统频道</option>		
				</select>
			</td>
			
	</tr>
	<tr class="trForContent2" id="moreCondition2" style="display:none">
		<td   align="right" width="5%">退款额度：</td>
			<td  align="left" width="15%" >
				<select id="tradeType" name="tradeType" size="1">
					<option value="" selected>---请选择---</option>
					<option value="" >部分</option>
					<option value="" >全部</option>				
				</select>
			</td>
			<td  align="right" width="5%">对方名称：</td>
			<td align="left" width="15%" >
				<input type="text">
			</td>
			<td  align="right" width="5%">流水号：</td>
			<td align="left" width="15%" >
				<input type="text">
			</td>
			
	</tr>
	<tr class="trForContent2">
		
			<td align="center"  colspan="6"><input type="button" onclick="" value=" 检索 "></input></td>
			
	</tr>
	
</table>
	</td>
	</tr>		
	<tr class="trForContent1">
		   <td   align="right"  colspan="2">&nbsp;</td>
			<td  align="center" colspan="4"><font color="red">收入：33333元  支出：33333元</font></td>
			
			
			
	</tr>	
			
			
	</tr>			
	<tr class="trbgcolorForTitle" align="center" valign="middle">
		<td class="border_right4" width="10%" nowrap><a class="s03"><font
			color="#FFFFFF">交易时间</font> </a></td>
		<td class="border_right4" width="10%" nowrap><a class="s03"><font
			color="#FFFFFF">交易名称|流水号</font> </a></td>
		<td class="border_right4" width="10%" nowrap><a class="s03"><font
			color="#FFFFFF">交易对方</font> </a></td>	
		<td class="border_right4" width="10%" nowrap><a class="s03"><font		
			color="#FFFFFF">收|支</font> </a></td>
		<td class="border_right4" width="10%" nowrap><a class="s03"><font
			color="#FFFFFF">交易金额</font> </a></td>
		<td class="border_right4" width="10%" nowrap><a class="s03"><font
			color="#FFFFFF">交易状态</font> </a></td>
						
		
	</tr>
	<c:forEach items="${tradeList}" var="memberTrade" varStatus = "memberTradeStatus">
	<c:choose>
       <c:when test="${memberTradeStatus.index%2==0}">
             <tr class="trForContent1">
       </c:when>
       <c:otherwise>
             <tr class="trForContent2">
       </c:otherwise>
	</c:choose>			
			<td class="border_top_right4" align="center">${memberTrade.tradeTime}&nbsp;</td>
			<td class="border_top_right4" align="center">${memberTrade.tradeCode}<br>${memberTrade.tradeName}&nbsp;</td>
			<td class="border_top_right4" align="center">${memberTrade.tradeObject}&nbsp;</td>
			<td class="border_top_right4" align="center">${memberTrade.tradeType}&nbsp;</td>
			<td class="border_top_right4" align="center">${memberTrade.tradeMoney}&nbsp;</td>
			<td class="border_top_right4" align="center">${memberTrade.tradeStatus}&nbsp;</td>	
						
		</tr>
	</c:forEach>
</table>




<br>
<br>

<table class="border_all4" width="75%" border="0" cellspacing="0"
	cellpadding="0" align="center" id="buttonDisplay">
	<tr class="trbgcolor6" align="center">
		<td><a class="s03" href="">首页 </a>&nbsp; <a class="s03" href="">上一页
		</a>&nbsp; <a class="s03" href="">下一页 </a>&nbsp; <a class="s03" href="">尾页
		</td>
	</tr>
</table>
</body>

