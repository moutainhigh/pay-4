<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/js/common.js"></script>

<html>
<head>
<title>配置入款网关结算时间</title>
<script type="text/javascript">
window.onload = function() {
	var status = "${cpNIgSettlementConfigDTO.status}";
	if(status==3){
		document.getElementById("startDate").readOnly=false;
	}else{
		document.getElementById("startDate").readOnly=true;
	}
}
		function isEmpty(fData){
		    return ((fData==null) || (fData.length==0) )
		}

		function checkSEDate(){
			var startDate = document.getElementById("startDate").value;
			var endDate = document.getElementById("endDate").value;
			var configId = document.getElementById("configId").value;
			var configType = document.getElementById("configType").value;
			$.ajax({
				type: "POST",
				url: "${ctx}/prepicitation/settlementConfig.do?method=checkSEDate",
				data: {
					"startDate":startDate,
					"endDate":endDate,
					"configId":configId,
					"configType":configType
				},
				success: function(result) {
					if(result=="false"){
						//alert("配置错误，请重新选择日期!");
						//$('#startDate').val("");
						//$('#endDate').val("");
						$('#checkInput').val("false");
					}else{
						$('#checkInput').val("true");
					}
				}
			});
		}

		function validateQuery(){
			var startDate = document.getElementById("startDate").value;
			var endDate = document.getElementById("endDate").value;
			var tDate = document.getElementById("tDate").value;
			var sDate = document.getElementsByName("sDate");
			var SDateFlag = "false";
			var status = "${cpNIgSettlementConfigDTO.status}";
			
			for(var i=0;i<sDate.length;i++){
				if(sDate[i].checked == true){
					SDateFlag = "true";
				}
			}
			if(SDateFlag=="false"){
				alert("请选择每周结算时间");
				return false;
			}
			
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

			if(status==3){
				if(tDate > startDate){
					alert("启用日期必须大于当天");
					return false;
				}
			}
			if(tDate > endDate){
				alert("停用日期必须大于当天");
				return false;
			}

			if($('#checkInput').val()=="false"){
				alert("配置日期重复错误，请重新选择日期!");
				$('#startDate').val("${startDate}");
				$('#endDate').val("${endDate}");
				return false;
			}

			var tempDay =compareDate(startDate,endDate)
			if(eval(tempDay+1) % 7 != 0){
				alert('配置中的启/停用日期选择需以周为单位')
				return false;
			}
			
			return true;
		}


		function compareDate(s1,s2){
		    var t1=s1.substring(0,4)+"/"+s1.substring(5,7)+"/"+s1.substring(8,10);
		    var t2=s2.substring(0,4)+"/"+s2.substring(5,7)+"/"+s2.substring(8,10);
			var date1 = new Date(t1);
		  	var date2 = new Date(t2);
			var days= date2.getTime() - date1.getTime(); 
			var time = parseInt(days / (1000 * 60 * 60 * 24));
			return time;
		}
		
		function update(){
			if(!validateQuery()){
				return false;
			}else{
				form2.submit();
			}
        }

		function processBack(){
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
						<font class="titletext">配置入款网关结算时间</font>
					</div>
				</td>
			</tr>
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
	</table>

<form action="${ctx}/prepicitation/settlementConfig.do?method=update" method="post" id="form2" name="form2">
<input type="hidden" id="checkInput"  name="checkInput" value="true" />
<input type="hidden" id="configId"  name="configId" value="${cpNIgSettlementConfigDTO.id}" />
<input type="hidden" id="tDate"  name="tDate" value="${tDate}" />
  <table width="80%" border="0" cellspacing="0" cellpadding="1" align="center">
    <tr>
      	<td  align="left">
      	 	&nbsp;配置网关结算类型：
      	 	<select name="configType" id="configType" disabled="disabled">
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
    	<td align="right" class="border_top_right4" width="20%"><div id="newCType">入款<font color='#FF0000'>${newCType}</font>结算网关配置</div></td>
    	<td align="right" class="border_top_right4" width="60%"></td>
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
	      	<input class="Wdate" type="text" id="startDate" name="startDate" value="${startDate}"  onClick="WdatePicker()" onBlur="checkSEDate()">
	        	～
			<input class="Wdate" type="text" id="endDate" name="endDate"  value="${endDate}" onClick="WdatePicker()" onBlur="checkSEDate()">
      	</td>
    </tr>
	
	<tr class="trForContent1">
		<td class=border_top_right4 align="center" colspan="2">
      		 <input type="button" onclick="update();" name="submitBtn" value="确认修改" class="button2">
      		 <input type="button" onclick="javascript:processBack()" class="button2" value=" 返 回 ">	  
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
