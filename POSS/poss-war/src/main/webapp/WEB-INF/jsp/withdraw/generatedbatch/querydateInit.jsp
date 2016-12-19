<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<%@page import="java.util.Date"%>
<h2 class="panel_title">查询提现数据</h2>
 <form action="" method="post" id="initForm">
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
    <tr class="trForContent1">
      <td align="right" class="border_top_right4"  >交易时间：</td>
      <td class="border_top_right4">
			<input class="Wdate" type="text" id="startDate" name="startDate" 
				value='<fmt:formatDate value="${startTime}" type="both"/>' style="width: 150px;"  
				onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',maxDate:'#F{$dp.$D(\'endDate\',{d:0});}'});">
		至&nbsp;
			<input class="Wdate" type="text" id="endDate" name="endDate" 
				value='<fmt:formatDate value="${endTime}" type="both"/>' style="width: 150px;"  
				onClick="WdatePicker({dateFmt:'yyyy-MM-dd HH:mm:ss',minDate:'#F{$dp.$D(\'startDate\',{d:0});}'});">
		</td>
      <td class=border_top_right4 align="right" >会员号：</td>
      <td class="border_top_right4" >
	        <input type="text"  name="memberCodeList"  id="memberCodeList" />
      </td>
    </tr>
    <tr class="trForContent1">
      <td align="right" class="border_top_right4"  >目的银行：</td>
	      <td class="border_top_right4" >
	        <li:select name="bankKy" defaultStyle="true" itemList="${targetBankList}"  />
	      </td>
      <td align="right" class="border_top_right4"  >交易金额：</td>
      <td class="border_top_right4">
			<input type="text"  name="applyAmountFrom" id="applyAmountFrom" value=""  />
		至&nbsp;
			<input type="text"  name="applyAmountTo" id="applyAmountTo" value=""  />
		</td>
     </tr>
    <tr class="trForContent1">
      <td align="right" class="border_top_right4"  >付款方会员类型：</td>
      <td class="border_top_right4" >
       	<select name="memberType">
       		<option value="">请选择</option>
       		<option value="1">个人</option>
       		<option value="2">企业</option>
       	</select>
      </td>
      
      <td class=border_top_right4 align="right" >收款方账户类型：</td>
      <td class="border_top_right4" >
     		<select name="memberAccType" style="width: 150px;" id="memberAccType">
				<option value='' checked>--请选择--</option>
				<c:forEach items="${acctTypes}" var="acct">
					<option value='${acct.code }'>${acct.displayName}</option>
				</c:forEach>
			</select>
      </td>
      
    </tr>
   <tr class="trForContent1">
      <td align="right" class="border_top_right4"  >交易类型：</td>
      <td class="border_top_right4" >
        <select name="tradeType">
       		<option value="">请选择</option>
       		<option value="1">对公</option>
       		<option value="0">对私</option>
       	</select>
      </td>
        <td class=border_top_right4 align="right" >银行账户类型：</td>
       <td class="border_top_right4" >
       <select name="bankAcctType">
       		<option value="">请选择</option>
       		<option value="0">借记卡</option>
       		<option value="1">信用卡</option>
       		<option value="2">存折</option>
       	</select>
      </td>
      
    </tr>
    
    <tr class="trForContent1">
      <td align="right" class="border_top_right4"  >交易流水号：</td>
      <td class="border_top_right4" >
        <input type="text"  name="sequenceId"  id="sequenceId"  data="1" />
      </td>
        <td class=border_top_right4 align="right" >业务类型：</td>
       <td class="border_top_right4" >
       	<select name="busiType" id="busiType" style="width: 150px;">
       			<option value="">全部</option>
				<option checked value='0'>提现&nbsp;&nbsp;</option>
				<!--<option value='1'>批量出款</option>
				--><!--<option value='2'>信用卡还款</option>
				--><option value='3'>付款到银行</option>
				<option value='4'>批量付款到银行</option>
			</select>
      </td>
    </tr>
    <tr class="trForContent1">
      <td align="right" class="border_top_right4"  >结算周期：</td>
      <td class="border_top_right4" >
        <select name="accountMode" style="width: 150px;">
			<option value='' checked>全部</option>
			<c:forEach items="${accountModeList}" var="map">
				<option value='${map.value}'>${map.text}</option>
			</c:forEach>
		</select>
      </td>
      <td class=border_top_right4 align="right" >是否垫资：</td>
      <td class="border_top_right4" >
       	 <select name="isLoaning" style="width: 150px;">
			<option value='' checked>全部</option>
			<option value='1' checked>是</option>
			<option value='0' checked>否</option>
		</select>
      </td>
    </tr>
     <tr class="trForContent1">
	     <td align="center" class="border_top_right4" colspan="4">
	     	 <input type="button" onclick="query();" name="submitBtn" value="查询" class="button2">
	      </td>
    </tr>
  </table>
 </form>
 <c:if test="${not empty errorMsg}">
 	<li style="color: red">${errorMsg }</li>
 </c:if>
<div id="resultListDiv" class="listFence"></div>

  <div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
	</div>    
  <script type="text/javascript">
	
	$(document).ready(function(){query();
	
		$('[data]').keypress(function(e){
			if(e.which == 8 ||e.which == 0 )return true;
			if(((e.which >= 45 && e.which <= 57) && e.ctrlKey == false && e.shiftKey == false) || e.which == 0 || e.which == 8) {
				if(e.which == 45){
					return false;
				}
				return true;
			} 
			return false;
		}).bind("paste",function(){return false;}).css({'imeMode':"disabled",'-moz-user-select':"none"});
	}); 
	
	$(function(){
		$(':text[name=applyAmountFrom]').attr('title','输入的金额只能保留两位小数(##.00)!');
		$(':text[name=applyAmountTo]').attr('title','输入的金额只能保留两位小数(##.00)!');
	}); 
	
	function query(pageNo,totalCount,pageSize) {
		var amount1 = $("#applyAmountFrom").val();
		var amount2 = $("#applyAmountTo").val();
		if(!validNumber(amount1)){
			return false;
		}
		if(!validNumber(amount2)){
			return false;
		}
		var reg = /^[0-9]*$/;
		var idValue = $("#memberCodeList").val();
		var memValue = $("#sequenceId").val();
		if(!reg.test(idValue) && idValue != '') {
			alert('会员号格式错误');
			return false;
		}
		if(!reg.test(memValue) && memValue != '') {
			alert('交易流水号格式错误');
			return false;
		}
		if(parseFloat(amount1)>parseFloat(amount2)){
			alert("开始金额不得大于结束金额!");
			return false;
		}
		
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#initForm").serialize()+ "&pageNo=" + pageNo + "&totalCount=" + totalCount+"&pageSize="+pageSize;
			$.ajax({
				type: "POST",
				url: "fundout-withdraw-generatebatch.do?method=query",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#resultListDiv').html(result);
				}
			});
	}
	
	//验证数字的合法性
	function validNumber(objVal){
		if(null == objVal || 0 == objVal.length){
			return true;
		}
		var reg = /^[0-9]+(.[0-9]{1,2})?$/g;
		if(!reg.test(objVal)){
			alert("您输入的金额格式是错误的,请更改后再查询!($###.##)");
			return false;
		}
		var reg2 = /^[0]+(.[0]{1,2})?$/g;
		if(reg2.test(objVal)){
			alert("金额必须大于0");
			return false;
		}
		return true;
	}
	
  </script>