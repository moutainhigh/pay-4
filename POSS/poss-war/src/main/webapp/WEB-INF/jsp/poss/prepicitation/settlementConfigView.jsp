<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/js/common.js"></script>

<html>
<head>
<title>入款非即时网关配置</title>
<script type="text/javascript">

		function isEmpty(fData){
		    return ((fData==null) || (fData.length==0) )
		}

		function validateQuery(){
			var startDate = document.getElementById("startDate").value;
			var endDate = document.getElementById("endDate").value;
			if(isEmpty(startDate)){
				alert("启用时间不能为空");
				return false;
			}
			if(isEmpty(endDate)){
				alert("停用时间不能为空");
				return false;
			}
			if(startDate > endDate){
				alert("启用时间必须小于停用时间");
				return false;
			}
	
			return true;
		}


		function update(){
			if(!validateQuery()){
				return false;
			}
		  	$('#infoLoadingDiv').dialog('open');
			var pars = $("#form1").serialize();
			$.ajax({
				type: "POST",
				url: "${ctx}/prepicitation/settlementConfig.do?method=update",
				data: pars,
				success: function(result) {
					$('#infoLoadingDiv').dialog('close');
					$('#paginationResult').html(result);
				}
			});
        }

		function processBack(){
			//window.location.href='${ctx}/report/costRateSetting.do';
			window.history.back(); 
		}
        
</script>
</head>

<body>
	<table width="30%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
			<tr>
				<td height="18">
					<div align="center">
						<font class="titletext">入款非即时网关配置-明细</font>
					</div>
				</td>
			</tr>
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
	</table>

<form action="" method="post" id="form1" name="form1">
  <table width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
    <tr>
      	<td  align="left">
      	 	&nbsp;配置网关结算类型：
      	 	<select name="configType" id="configType" disabled="disabled">
      	 		<option value="" <c:if test="${cpNIgSettlementConfigDTO.configType eq '' || cpNIgSettlementConfigDTO.configType == null}">selected</c:if>>全部</option>
				<option value="1" <c:if test="${cpNIgSettlementConfigDTO.configType eq '1'}">selected</c:if>>T+0</option>
				<option value="2" <c:if test="${cpNIgSettlementConfigDTO.configType eq '2'}">selected</c:if>>T+0顺延</option>
				<option value="3" <c:if test="${cpNIgSettlementConfigDTO.configType eq '3'}">selected</c:if>>T+1</option>
				<option value="4" <c:if test="${cpNIgSettlementConfigDTO.configType eq '4'}">selected</c:if>>T+1顺延</option>
				<option value="5" <c:if test="${cpNIgSettlementConfigDTO.configType eq '5'}">selected</c:if>>T+2</option>
				<option value="6" <c:if test="${cpNIgSettlementConfigDTO.configType eq '6'}">selected</c:if>>T+2顺延</option>
	    	</select>
        	结算网关
      	</td>
    </tr>	    
  </table>
  <br>
  <table class="border_all2" width="80%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
	<tr class="trForContent1">
    	<td align="right" class="border_top_right4">入款非即时网关配置</td>
    	<td align="right" class="border_top_right4"></td>
    </tr>
	
	<tr class="trForContent1">
    	<td class=border_top_right4 align="right" >每周结算时间：</td>
    	<td class="border_top_right4" >
			<c:forEach items="${weekDTOList}" var="w" varStatus="p">
   				<input type="checkbox" id="c${p.index+1}" name="sDate" value="${p.index+1}" 
   					<c:if test="${w.isFlag eq 'true'}">checked="checked"</c:if>disabled="disabled"
  					/><label for="c${p.index+1}">${w.name}</label>
	        </c:forEach>
        </td>
    </tr>
	
    <tr class="trForContent1">
    	<td align="right" class="border_top_right4">配置启用/停用日期：</td>
      	<td class="border_top_right4">
	      	<input class="Wdate" type="text" id="startDate" disabled="disabled" name="startDate" value="${startDate}"  >
	        	～
			<input class="Wdate" type="text" id="endDate" disabled="disabled" name="endDate"  value="${endDate}" >
      	</td>
    </tr>
	
	<tr class="trForContent1">
		<td class=border_top_right4 align="center" colspan="2">
      		 <input type="button" onclick="javascript:processBack()" class="Submit2" value=" 返 回 ">	  
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
