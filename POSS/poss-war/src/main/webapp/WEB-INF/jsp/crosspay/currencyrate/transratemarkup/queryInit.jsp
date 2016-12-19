<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<h2 class="panel_title">商户交易汇率Markup管理</h2>
<form action="" method="post" id="mainfromId">
	<table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >会员号</td>
	      <td class="border_top_right4"><input type="text"  name="memberCode"></td>
	      <td align="right" class="border_top_right4" >交易币种：</td>
	      <td class="border_top_right4">
	      	<select name="currency" id="currency">
	      		<option value="" selected>---请选择---</option>
	      		<c:forEach var="currency" items="${currencys}" varStatus="v">
					<option value="${currency.code}">${currency.desc}</option>
				</c:forEach>
	      	</select>
	      </td>
	      <td align="right" class="border_top_right4" >目标币种：</td>
	      <td class="border_top_right4">
	      	<select name="targetCurrency" id="targetCurrency">
	      		<option value="" selected>---请选择---</option>
	      		<c:forEach var="currency" items="${currencys}" varStatus="v">
					<option value="${currency.code}">${currency.desc}</option>
				</c:forEach>
	      	</select>
	      </td>
	      <td align="right" class="border_top_right4" >卡组织</td>
	      <td class="border_top_right4">
	       <select name="cardOrg" id="cardOrg">
	                <option value="">请选择</option>
					<option value="VISA">VISA</option>
					<option value="MASTER">MASTER</option>
					<option value="JCB">JCB</option>
					<option value="AE">AE</option>
					<option value="DC">DC</option>
	      	</select>
	      </td>
	      <td align="right" class="border_top_right4" >状态</td>
	      <td class="border_top_right4">
	       <select name="status" id="status">
	                <option value="">请选择</option>
					<option value="1">生效</option>
					<option value="0">失效</option>
	      	</select>
	      </td>
	     </tr>
	    <tr class="trForContent1">
	      <td align="right" class="border_top_right4" >ID</td>
	      <td class="border_top_right4"><input type="text"  name="id"></td>
	      <td align="right" class="border_top_right4" >创建日期：</td>
	      <td class="border_top_right4">
	      	  <input class="Wdate" type="text" id="createDate" name="startTime"  
	      	   onClick="WdatePicker({dateFmt:'yyyy-MM-dd'})">
	      </td>
	      <td align="right" class="border_top_right4" >卡国家：</td>
	      <td class="border_top_right4">
	      	<select name="cardCountry" id="cardCountry">
	      		<option value="" selected>---请选择---</option>
                <option value="CHN">中国</option>
                <option value="USA">美国</option>
                <option value="CAN">加拿大</option>
                <option value="AUS">澳大利亚</option>
                <option value="JPN">日本</option>
                <option value="MNP">挪威</option>
                <option value="SWE">瑞典</option>
                <option value="GBR">英国</option>
                <option value="EUR">欧盟</option>
                <option value="OOO">其他地区</option>
	      	</select>
	      </td>
	      <td align="right" class="border_top_right4" >卡本币</td>
	      <td colspan="3" class="border_top_right4">
             <select name="cardCurrencyCode" id="cardCurrencyCode">
	      		<option value="" selected>---请选择---</option>
	      		<c:forEach var="currency" items="${currencys}" varStatus="v">
					<option value="${currency.code}">${currency.desc}</option>
				</c:forEach>
	      	</select>
	      </td>
	     </tr>
	    <tr class="trForContent1">
	      <td align="center" class="border_top_right4" colspan="10">
	      <input type="button"  name="butSubmit" value="查  询" class="button2" onclick="search();">
	      <input type="button"  name="butSubmit" value="添  加" class="button2" onclick="toAdd();">
	      </td>
	    </tr>
   </table>
</form>

<c:if test="${not empty responseCode && responseCode == '0000'}">
	<font color="red"><b>操作成功！</b></font>	
</c:if>

<c:if test="${not empty responseCode && responseCode != '0000'}">
	<font color="red"><b>${responseDesc }</b></font>
</c:if>
 
<div id="resultListDiv" class="listFence"></div>

<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
	<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>   
	 
<script type="text/javascript">
    
    $(function(){
    	search();
    });
    
	function toAdd(){
		window.location.href="${ctx}/transRateMarkup/markup.do?method=toAdd";
	}
	
	function search(pageNo,totalCount,pageSize) {
		$('#infoLoadingDiv').dialog('open');
		var pars = $("#mainfromId").serialize();
		$.ajax({
			type: "POST",
			url: "${ctx}/transRateMarkup/markup.do?method=list",
			data: pars,
			success: function(result) {
				$('#infoLoadingDiv').dialog('close');
				$('#resultListDiv').html(result);
			}
		});
	}
  </script>