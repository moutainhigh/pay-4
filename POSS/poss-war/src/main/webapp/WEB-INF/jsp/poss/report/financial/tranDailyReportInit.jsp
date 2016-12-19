<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/js/common.js"></script>

<html>
<head>
<title>交易日报表查询统计</title>
<script type="text/javascript">
		function isEmpty(fData){
		    return ((fData==null) || (fData.length==0) )
		}
		function validateQuery(){
			var startDate = document.getElementById("startDate").value;
			var endDate = document.getElementById("endDate").value;
			if(isEmpty(startDate)){
				alert("开始时间不能为空");
				return false;
			}
			if(isEmpty(endDate)){
				alert("结束时间不能为空");
				return false;
			}
			if(startDate > endDate){
				alert("开始时间必须小于结束时间");
				return false;
			}
			return true;
		}
		
		function userQuery(pageNo,totalCount,pageSize){
			if(!validateQuery()){
				return false;
			}
		  	$('#infoLoadingDiv').dialog('open');
			var pars = $("#form1").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount+ "&pageSize=" + pageSize;
			$.ajax({
				type: "POST",
				url: "${ctx}/report/tranDailyReprt.do?method=queryTranDailyReportFlow",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#paginationResult').html(result);
				}
			});
        }

		function exportExcel(totalCount) {
			if(totalCount <= 0){
				alert("无结果集,不能下载！");
			}else if(totalCount > 200000){
				alert("结果集大于200000,不能下载！");
			}else{
				if(!validateQuery()){
					return false;
				}
				var pars = $("#form1").serialize();
				window.location="${ctx}/report/tranDailyReprt.do?method=queryTranDailyReportFlow&export=1&"+pars; 
			}
        }

		function selectOptType(optType){	
			if(1== optType){
				document.getElementById('partnerId').disabled=false;
			}else{
				document.getElementById('memberType').value="";
				document.getElementById('memberCode').disabled=true;
			}
		}
	    
</script>
</head>

<body>

<h2 class="panel_title">交易日报表</h2>

<form action="" method="post" id="form1" name="form1">
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
    <tr class="trForContent1">
    	<td align="right" class="border_top_right4">交易日期：</td>
      	<td class="border_top_right4">
	      	<input class="Wdate" type="text" id="startDate" name="startDate" value='<fmt:formatDate value="${startDate}" type="date" pattern="yyyy-MM-dd"/>'  onClick="WdatePicker()">
	        	～
			<input class="Wdate" type="text" id="endDate" name="endDate"  value='<fmt:formatDate value="${endDate}" type="date" pattern="yyyy-MM-dd"/>' onClick="WdatePicker()">
      	</td> 
      	<td align="right" class="border_top_right4">交易类型</td>
      	<td class="border_top_right4">
      		<select name="payType" id="payType">
      			<option value="">全部</option>
      			<option value="DCC">DCC</option>
      			<option value="EDC">EDC</option>
      		</select>
      	</td>     	
    </tr>
    <tr class="trForContent1">
    	 <td class=border_top_right4 align="right" >操作类型：</td>
    	<td class="border_top_right4" >
		    <label>
				<input type="radio" id="r2"  name="optType"  checked="checked" value="2" />
			</label>
		    <label for="r2">
				交易日报表汇总统计
			</label>
			<label>
				<input type="radio" id="r1"  name="optType"  value="1"  />
			</label>
		  	<label for="r1">
				交易日报表明细统计
		    </label>
        </td>
      	<td class=border_top_right4 align="right" >会员编号：</td>
      	<td class="border_top_right4" >
        	<input type="text" name="partnerId" id="partnerId"> 
      	</td>
    </tr>
    <tr class="trForContent1">
    	 <td class=border_top_right4 align="right" >交易币种：</td>
    	<td class="border_top_right4" >
		   <select name="currencyCode" id="currencyCode">
			   	<option value="">全部</option>
			   	<c:forEach var="currency" items="${currencyCodes }">
			   		<option value="${currency.code }">${currency.desc }</option>
			   	</c:forEach>
		   </select>
        </td>
      	<td class=border_top_right4 align="right" >支付币种：</td>
      	<td class="border_top_right4" >
        	<select name="payCurrencyCode" id="payCurrencyCode">
        		<option value="">全部</option>
				<c:forEach var="currency" items="${currencyCodes }">
					<option value="${currency.code }">${currency.desc }</option>
				</c:forEach>
        	</select>
      	</td>
    </tr>
    
    <tr class="trForContent1">   	
    		<td class="border_top_right4" align="right" >所属渠道：</td>
		<td class="border_top_right4" align="left" >
				<select id="orgCode" name="orgCode" size="1">
				<option value="" selected >---请选择---</option>
				<option value="10076001" >中银卡司</option>
				<option value="10079001" >中银MOTO</option>
				<option value="10080001" >中银MIGS</option>
				<option value="10003001" >中国银行</option>
				<option value="10002001" >农业银行</option>
				<option value="10075001" >Credorax</option>
			</select>
		</td>
    
      <td align="center" class="border_top_right4" colspan="3">
      <input type="button" onclick="userQuery();" name="submitBtn" value="查  询" class="button2">
      </td>
    </tr>
  </table>
 </form>
 
<div align="center" id="paginationResult"></div>
<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
</div>
<p>&nbsp;</p>
</body>
</html>
