<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/js/common.js"></script>

<html>
<head>
<meta   http-equiv='Expires' content='-10'>
<meta   http-equiv='Pragma' content='No-cache'>
<meta   http-equiv='Cache-Control','private'> 



<script type="text/javascript">
			var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
			
			function isRightDateFormat(date) {
				if(date==null||date.length==0){
					return true;
				}
				var isRight = date.match(new RegExp("^(\\d{4})([-]?)(\\d{1,2})([-]?)(\\d{1,2})$"));
				if (isRight== null) {
					return false;
				}
				return true;
			}
			
			function userQuery(pageNo,totalCount,pageSize) {
				var dateFrom = document.getElementById("dateFrom").value;
			  	if (!isRightDateFormat(dateFrom)) {
			  		alert("开始日期格式错误！应为YYYY-MM-DD.");
			  		return ;
			  	}
			  	
			  	var dateTo = document.getElementById("dateTo").value;
			  	if (!isRightDateFormat(dateTo)) {
			  		alert("结束日期格式错误！应为YYYY-MM-DD.");
			  		return ;
			  	}
			  	
			  	if (dateTo.length!=0&&dateFrom.length!=0&&isRightDateFormat(dateFrom)&&isRightDateFormat(dateTo)) {
			  		if(dateFrom>dateTo){
						alert("起始时间不应晚于结束时间");
						return;
					}
			  	}

		  	     var re = /^[0-9]+.?[0-9]*$/;   //判断字符串是否为数字     
			  	  var amountFrom = document.getElementById("amountFrom").value;
		  	     if(amountFrom.length!=0){
			  	     if (!re.test(amountFrom))   
				  	    {   
				  	        alert("起始金额请输入数字(例:0.02)");   
				  	        return ;   
				  	     }  
		  	     }

				 var amountTo = document.getElementById("amountTo").value;
				  	  	if(amountTo.length!=0){
					  	     if (!re.test(amountTo))   
						  	    {   
						  	        alert("最大金额请输入数字(例:0.02)");   
						  	        return ;   
						  	     }   
				  	  	}			
			  	
				$('#infoLoadingDiv').dialog('open');
				var pars = $("#mainfromId").serialize() + "&pageNo=" + pageNo + "&totalCount=" + totalCount+ "&pageSize=" + pageSize;
				$.ajax({
					type: "POST",
					url: "${ctx}/manualbooking/misController.do?method=queryMis",
					data: pars,
					success: function(result) {
						$('#infoLoadingDiv').dialog('close');
						$('#resultListDiv').html(result);
					}
				});
			}
			
			$(document).ready(function(){userQuery();});
			
			function updateInfostatus(code) {
				$('#deleteRoleDiv').html(loadImgStr);
				//var url = "misController.do?method=delete";
				var url = "manualbooking/misController.do?method=updateVouchstatus";
				var pars = {"id": code,"flag":1};
				$.ajax({
					type: "POST",
					url: url,
					data: pars,
					success: function(result) {
						$('#deleteRoleDiv').dialog('close');
						if (result == 'success') {
							$('#handleMessageDiv').html("操作成功!");
							$('#handleMessageDiv').dialog('open');
							userQuery();
						} else {
							$('#handleMessageDiv').html("操作失败!");
							$('#handleMessageDiv').dialog('open');
						}
					}
				});
			}
			
			
			function updateVouchstatus(code) {
				$('#deleteRoleDiv').html(loadImgStr);
				var url = "misController.do?method=updateVouchstatus";
				var pars = {"id": code,"flag":2};
				$.ajax({
					type: "POST",
					url: url,
					data: pars,
					success: function(result) {
						$('#deleteRoleDiv').dialog('close');
						if (result == 'success') {
							$('#handleMessageDiv').html("操作成功!");
							$('#handleMessageDiv').dialog('open');
							userQuery();
						} else {
							$('#handleMessageDiv').html("操作失败!");
							$('#handleMessageDiv').dialog('open');
						}
					}
				});
			}


			function viewVouch(vouchDataId) {
				var url = "${ctx}/manualbooking/viewVouch.do?method=viewVouch";
			    var a = document.getElementById("vouchDataId");
			    a.value = vouchDataId;			   
			    var vouchViewForm = document.getElementById("vouchViewForm");
			    vouchViewForm.action = url;
			    //alert(vouchDataId);
			    
			   vouchViewForm.submit();
			    return true;
			}			
	
			
			function checkAll()
			{
				$(":input[name=dataId]").attr("checked",$("#checkAllBox").attr("checked"));
			}
			
			function verifyChoice(){
				
				var choiceFlag = false;
				var f_str = "";
				$(":input[@name='dataId']").each(function(){
				   if($(this).attr("checked")==true){
				    	f_str += $(this).val()+",";
				    	choiceFlag = true;
				   }
				})
				
				if(!choiceFlag){
					alert('请选择需要审核的记录！');
					return;
				}
					
			var url = "${ctx}/manualbooking/viewVouch.do?method=batchVerifyVouch";
			$("#batchVouchDataId").val(f_str);			
			    vouchViewForm.action = url;
			    vouchViewForm.submit();
			    return true;
			}
		</script>
		
</head>
<body>

<h2 class="panel_title">记账申请查询</h2>
<form action="" method="post" id="mainfromId">
	<table class="border_all2" width="80%" border="0" cellspacing="0" cellpadding="1" align="center">	
	    <tr class="trForContent1">
	    	<td align="right" class="border_top_right4">申请状态：</td>
	    	<td class="border_top_right4">
	    		<select name="vouchStatus" id="vouchStatus" value="${vouchSearchCriteria.vouchStatus}">
				 	<option value="4" >--请选择--</option>
				 	<option value="0" <c:if test='${vouchSearchCriteria.vouchStatus == "0"}'> selected</c:if>>未复核</option>
				 	<option value="1" <c:if test='${vouchSearchCriteria.vouchStatus == "1"}'> selected</c:if>>审核通过</option>
				 	<option value="2" <c:if test='${vouchSearchCriteria.vouchStatus == "2"}'> selected</c:if>>审核不通过</option>
				 	<option value="3" <c:if test='${vouchSearchCriteria.vouchStatus == "3"}'> selected</c:if>>作废</option>
			    </select>
	    	</td>
	    	<td align="right" class="border_top_right4">凭证号：</td>
	    	<td class="border_top_right4">
	    		<input type="text" name="vouchCode"   value="${vouchSearchCriteria.vouchCode}" style="width: 120px;" />
	    	</td>
	    </tr>
	   
	   
	    <tr class="trForContent1">
	    	<td align="right" class="border_top_right4">创建日期：</td>
	    	<td class="border_top_right4">
	    		<input class="Wdate" type="text" id="dateFrom" name="dateFrom" value='${vouchSearchCriteria.dateFromString}' style="width: 120px;"onClick="WdatePicker()">
		        	～
				<input class="Wdate" type="text" id="dateTo" name="dateTo"  value='${vouchSearchCriteria.dateToString}' style="width: 120px;"onClick="WdatePicker()">
			</td>
	    	<td align="right" class="border_top_right4">金额范围：</td>
	    	<td class="border_top_right4">
	    		<input class="textLeft" type="text" id="amountFrom" name="amountFrom"  value="${vouchSearchCriteria.amountFrom}"  style="width: 100px;">
		        	～
				<input class="textLeft" type="text" id="amountTo" name="amountTo"   value="${vouchSearchCriteria.amountTo}" style="width: 100px;">
	    	</td>
	    </tr>
	    <tr class="trForContent1">
	    	<td class="border_top_right4" align="right">摘要：</td>
	    	<td  class="border_top_right4" colspan="3"><input type="text" id="remark" name="remark"   value="${vouchSearchCriteria.remark}" style="width: 300px;"/></td>
	    <tr class="trForContent1">
	      	<td align="center" class="border_top_right4" colspan="4">
	      		<input type="button" name="butSubmit" value="查询" class="button2" onclick="userQuery()">
	      	</td>
	    </tr>
		    
	    </tbody>
   </table>
</form>

		<div id="resultListDiv" class="listFence">
			
		
		</div>
		<div id="deleteRoleDiv" title="删除用户信息"></div>
		<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
		<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
		</div>
</body>
</html>