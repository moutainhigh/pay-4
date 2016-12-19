<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/js/common.js"></script>

<html>
<head>
<title>包量会员配置</title>
<script type="text/javascript">
var validator;
	function processBack(){
		location.href = "memberFlowPackageCfg.do";
	}

	function modify(){
		if(validator.form()) {
				if(parseInt($("#warnFlow").val())>parseInt($("#prePayFlow").val())){
					alert("预警流量需小于预付流量");
					$("#warnFlow").focus();
					return;
				}
				if(parseInt($("#prePayAmount").val())>parseInt($("#prePayFlow").val())){
					alert("预付金额需小于预付流量");
					$("#prePayAmount").focus();
					return;
				}
				if(parseInt($("#shutDownFlow").val())>parseInt($("#prePayFlow").val())){
					alert("关停流量需小于预付流量");
					$("#shutDownFlow").focus();
					return;
				}
				if(parseInt($("#shutDownFlow").val())>parseInt($("#warnFlow").val())){
					alert("关停流量需小于预警流量");
					$("#shutDownFlow").focus();
					return;
				}
				form1.submit();
		}
    }

	function validateBeginDate(){
		var beginDate = $("#beginDate").val();
		var memberCode = $("#memberCode").val();
		var newFlag = $("#newFlag").val();
		if(newFlag == "0"){
			return;
		}
		$.post("${ctx}/report/memberFlowPackageCfg.do?method=queryByBegindate",
				{beginDate:beginDate,
				 memberCode:memberCode,
				 newFlag:newFlag
				},function (msg){
					if(msg!=""){
						alert("包量起始日期重复，请重新选择");
						return;
					}
					form1.submit();
			}
		);
	}

	function reset(){
		$("#prePayAmount").val($("#prePayAmountStr").val());
		$("#prePayFlow").val($("#prePayFlowStr").val());
		$("#averageRate").val($("#averageRateStr").val());
		$("#warnFlow").val($("#warnFlowStr").val());
		$("#warnLinkman").val($("#warnLinkmanStr").val());
		$("#warnLinkmanEmail").val($("#warnLinkmanEmailStr").val());
		$("#warnLinkmanMobile").val($("#warnLinkmanMobileStr").val());
		$("#shutDownFlow").val($("#shutDownFlowStr").val());
		$("#shutDownLinkman").val($("#shutDownLinkmanStr").val());
		$("#shutDownLinkmanEmail").val($("#shutDownLinkmanEmailStr").val());
		$("#shutDownLinkmanMobile").val($("#shutDownLinkmanMobileStr").val());
		$("#prePayDate").val($("#prePayDateStr").val());
		$("#beginDate").val($("#beginDateStr").val());
	}
	
	//页面validate
	$(document).ready(function(){
	
		// 不能包含全角字符
		jQuery.validator.addMethod("hasChn", function(value, element) {
		  return  (escape(value).indexOf("%u") < 0);    
		}, "不能包含中文字符"); 
	
		//聚焦第一个输入框
		$("#memberName").focus();
		validator = $("#form1").validate({
			rules: { 
				memberName:{
					required:true,
					rangelength:[1,256],
					hasChn:false
		    	},
		    	memberCode:{
					required:true,
					rangelength:[11,11],
					number:true
				},
				prePayAmount:{
					required:true,
					number:true
				},
				prePayFlow:{
					required:true,
					number:true
				},
				averageRate:{
					required:true,
					number:true
				},
				warnFlow:{
					required:true,
					number:true
				},
				shutDownFlow:{
					required:true,
					number:true
				},
				prePayDate:{
					required:true
				},
				warnLinkman:{
					required:true
				},
				warnLinkmanEmail:{
					required:true,
					email:true
				},
				warnLinkmanMobile:{
					required:true,
					number:true
				},
				shutDownLinkman:{
					required:true
				},
				shutDownLinkmanEmail:{
					required:true,
					email:true
				},
				shutDownLinkmanMobile:{
					required:true,
					number:true
				}
			}
		});

		if($("#newFlag").val()=='1'){
			$("#beginDate").focus(function() {
				var startMin = $("#sysDate").val();
				WdatePicker({
					dateFmt : 'yyyy-MM-dd',
					skin : 'whyGreen',
					minDate : startMin,
					maxDate : '9999-12-31' 
				});

			});
			if($("#status").val()=='0'){
				$('#beginDate').removeAttr("readonly");
			}
		}
		if($("#newFlag").val()=='0'){
			$('#beginDate').val("");
			$('#beginDate').attr("readonly","readonly");
		}

		if($("#status").val()=='1'){
			$("#prePayAmount").attr("readonly","readonly");
			$("#prePayFlow").attr("readonly","readonly");
			$("#averageRate").attr("readonly","readonly");
			$("#warnFlow").attr("readonly","readonly");
			$("#shutDownFlow").attr("readonly","readonly");
			$("#prePayDate").attr("readonly","readonly");
		}
		
		
	});

	function calRate(){
		var prePayAmount = $('#prePayAmount').val();
		var prePayFlow = $('#prePayFlow').val();
		if(prePayAmount!="" && prePayFlow!="" && parseInt(prePayFlow)>parseInt(prePayAmount)){
			$('#averageRate').val(Math.round(10000*prePayAmount/prePayFlow));
		}
	}

</script>
</head>

<body>
	<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
			<tr>
				<td height="18">
					<div align="center">
						<font class="titletext">包量会员配置修改</font>
					</div>
				</td>
			</tr>
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
	</table>
<br>
	<form action="${ctx}/report/memberFlowPackageCfg.do?method=update" method="post" id="form1" name="form1">
	  <input type="hidden" name="id" value="${memberToUpdate.sequenceId}"/>
	  <input id="sysDate" type="hidden" value="${sysDate}"/>
	  <input id="newFlag" type="hidden" value="${newFlag}"/>
	  <input id="status" type="hidden" value="${memberToUpdate.status}"/>
	  <input id="memberCode" name="memberCode" type="hidden" value="${memberToUpdate.memberCode}"/>
	  <input id="memberName" name="memberName" type="hidden" value="${memberToUpdate.memberName}"/>
	  <table class="border_all2" width="80%" border="0" cellspacing="0"
		cellpadding="1" align="center">	
	   <tr class="trForContent1">
	      	<td class="border_top_right4" align="right" >会员编号：</td>
	      	<td class="border_top_right4" colspan="3">
	        	<input type="text" maxlength="11" size=30 value="${memberToUpdate.memberCode}" disabled> 
	      	</td>
	    </tr>
	    <tr class="trForContent1">
	      	<td class="border_top_right4" align="right" >会员名称：</td>
	      	<td class="border_top_right4" colspan="3">
	        	<input type="text" maxlength="11" size=30 value="${memberToUpdate.memberName}" disabled> 
	      	</td>
	    </tr>
		<tr class="trForContent1">
	    	<td class="border_top_right4" align="right" width="20%">预付金额：</td>
	    	<td class="border_top_right4" align="left" width="30%">
	    		<input type="text" id="prePayAmount" name="prePayAmount" maxlength="19" onblur="calRate();" size="19" value="${memberToUpdate.prePayAmountStr}" ><font style="font-family:MS Gothic;font-size:30px"></font>元<br>
	    		<input type="hidden" id="prePayAmountStr" value="${memberToUpdate.prePayAmountStr}" >
	    	</td>
	    	<td class="border_top_right4" align="right" width="20%">预付流量：</td>
	    	<td class="border_top_right4" align="left" width="30%">
	    		<input type="text" id="prePayFlow" name="prePayFlow" maxlength="19" onblur="calRate();" size="19" value="${memberToUpdate.prePayFlowStr}" ><font style="font-family:MS Gothic;font-size:30px"></font>元<br>
	    		<input type="hidden" id="prePayFlowStr" value="${memberToUpdate.prePayFlowStr}" >
	    	</td>
		</tr>
	    <tr class="trForContent1">
	      	<td class="border_top_right4" align="right">预付日期：</td>
	      	<td class="border_top_right4" align="left">
	      		<input class="Wdate" type="text" id="prePayDate" name="prePayDate" onclick="WdatePicker()" value="${memberToUpdate.prePayDateStr}"/>
	      		<input type="hidden" id="prePayDateStr" value="${memberToUpdate.prePayDateStr}"/>
	      	</td>
	      	<td class="border_top_right4" align="right">包量起始日期：</td>
	      	<td class="border_top_right4" align="left">
	      		<input class="Wdate" type="text" id="beginDate" name="beginDate" value="${memberToUpdate.beginDateStr}"/>
	      		<input type="hidden" id="beginDateStr" value="${memberToUpdate.beginDateStr}"/>
	      	</td>
	    </tr>	  
		<tr class="trForContent1">
	      	<td class="border_top_right4" align="right" >平均费率：</td>
	      	<td class="border_top_right4" colspan="3">
	        	<input type="text" id="averageRate" name="averageRate" maxlength="19" size="19" value="${memberToUpdate.averageRate}"><font style="font-family:MS Gothic;font-size:30px"></font>万分之一<br>
	        	<input type="hidden" id="averageRateStr" value="${memberToUpdate.averageRate}">
	      	</td>
	    </tr>
		<tr class="trForContent1">
	    	<td class="border_top_right4" align="right" width="20%">预警流量：</td>
	    	<td class="border_top_right4" align="left" width="30%">
	    		<input type="text" id="warnFlow" name="warnFlow" maxlength="19" size="19" value="${memberToUpdate.warnFlowStr}"><font style="font-family:MS Gothic;font-size:30px"></font>元<br>
	    		<input type="hidden" id="warnFlowStr" value="${memberToUpdate.warnFlowStr}">
	    	</td>
	    	<td class="border_top_right4" align="right" width="20%">预警联系人：</td>
	    	<td class="border_top_right4" align="left" width="30%">
	    		<input type="text" id="warnLinkman" name="warnLinkman" maxlength="19" size="19" value="${memberToUpdate.warnLinkman}"><font style="font-family:MS Gothic;font-size:30px"></font>
	    		<input type="hidden" id="warnLinkmanStr" value="${memberToUpdate.warnLinkman}">
	    	</td>
		</tr>
		<tr class="trForContent1">
	    	<td class="border_top_right4" align="right" width="20%">预警联系人邮箱：</td>
	    	<td class="border_top_right4" align="left" width="30%">
	    		<input type="text" id="warnLinkmanEmail" name="warnLinkmanEmail" maxlength="19" value="${memberToUpdate.warnLinkmanEmail}"><font style="font-family:MS Gothic;font-size:30px"></font><br>
	    		<input type="hidden" id="warnLinkmanEmailStr" value="${memberToUpdate.warnLinkmanEmail}">
	    	</td>
	    	<td class="border_top_right4" align="right" width="20%">预警联系人号码：</td>
	    	<td class="border_top_right4" align="left" width="30%">
	    		<input type="text" id="warnLinkmanMobile" name="warnLinkmanMobile" maxlength="19" value="${memberToUpdate.warnLinkmanMobile}"><font style="font-family:MS Gothic;font-size:30px"></font><br>
	    		<input type="hidden" id="warnLinkmanMobileStr" value="${memberToUpdate.warnLinkmanMobile}">
	    	</td>
		</tr>
		<tr class="trForContent1">
	      	<td class="border_top_right4" align="right" width="20%">关停流量：</td>
	      	<td class="border_top_right4" width="30%">
	        	<input type="text" id="shutDownFlow" name="shutDownFlow" maxlength="11" value="${memberToUpdate.shutDownFlowStr}"><font style="font-family:MS Gothic;font-size:30px"></font>元<br>
	        	<input type="hidden" id="shutDownFlowStr" value="${memberToUpdate.shutDownFlowStr}">
	      	</td>
	      	<td class="border_top_right4" align="right" width="20%">关停联系人：</td>
	    	<td class="border_top_right4" align="left" width="30%">
	    		<input type="text" id="shutDownLinkman" name="shutDownLinkman" maxlength="19" value="${memberToUpdate.shutDownLinkman}"><font style="font-family:MS Gothic;font-size:30px"></font><br>
	    		<input type="hidden" id="shutDownLinkmanStr" value="${memberToUpdate.shutDownLinkman}">
	    	</td>
	    </tr>
		<tr class="trForContent1">
	    	<td class="border_top_right4" align="right" width="20%">关停联系人邮箱：</td>
	    	<td class="border_top_right4" align="left" width="30%">
	    		<input type="text" id="shutDownLinkmanEmail" name="shutDownLinkmanEmail" maxlength="19" value="${memberToUpdate.shutDownLinkmanEmail}"><font style="font-family:MS Gothic;font-size:30px"></font><br>
	    		<input type="hidden" id="shutDownLinkmanEmailStr" value="${memberToUpdate.shutDownLinkmanEmail}">
	    	</td>
	    	<td class="border_top_right4" align="right" width="20%">关停联系人号码：</td>
	    	<td class="border_top_right4" align="left" width="30%">
	    		<input type="text" id="shutDownLinkmanMobile" name="shutDownLinkmanMobile" maxlength="19" value="${memberToUpdate.shutDownLinkmanMobile}"><font style="font-family:MS Gothic;font-size:30px"></font><br>
	    		<input type="hidden" id="shutDownLinkmanMobileStr" value="${memberToUpdate.shutDownLinkmanMobile}">
	    	</td>
		</tr>
		<tr class="trForContent1">
	      	<td class=border_top_right4 align="right" >备注：</td>
	      	<td class="border_top_right4" colspan="3">
	        	<textarea id="remark" name="remark" rows="5" cols="45"></textarea>
	      	</td>
	    </tr>
	  </table>
	  <br>
	  <br>
	  <input type="button" onclick="modify();" name="submitBtn" value="确认修改" class="button2">&nbsp;&nbsp;&nbsp;
	  <input type="button" onclick="reset();" name="resetBtn" value="重置" class="button2">&nbsp;&nbsp;&nbsp;
	  <input type="button" onclick="javascript:processBack()" name="Submit2" value="返 回" class="button2">
	</form>
</body>
</html>
