<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/js/common.js"></script>

<html>
<head>
<title>包量会员配置</title>
<script type="text/javascript">

	var validator;
	//页面validate
	$(document).ready(function(){

		// 不能包含全角字符
		jQuery.validator.addMethod("hasChn", function(value, element) {
		  return  (escape(value).indexOf("%u") < 0);    
		}, "不能包含中文字符"); 

		//聚焦第一个输入框
		$("#memberCode").focus();
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

	});

	function add(){
		if(validator.form()) {
			if($("#newFlag").val()=='1'){
				if($("#beginDate").val()==""){
					alert("新增包量配置起始日期必填");
					return;
				}
				if($("#warnFlow").val()>$("#prePayFlow").val()){
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
				validateBeginDate();
			}
			if($("#newFlag").val()=='0'){
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
    }

	function queryMemberName(memberCode){
		if(memberCode=="" || memberCode.length!=11){
			return;
		}
		$.post("${ctx}/report/memberFlowPackageCfg.do?method=queryByMemberCode",
				{memberCode:memberCode},function (data){
					var objData = eval( "(" + data + ")" );
					$('#memberName').val(objData.memberName);
					$('#sysDate').val(objData.sysDate);
					$('#newFlag').val(objData.isNewFlag);
					if(objData.isNewFlag=="1"){
						$("#beginDate").focus(function() {
							var startMin = objData.sysDate;
							WdatePicker({
								dateFmt : 'yyyy-MM-dd',
								skin : 'whyGreen',
								minDate : startMin,
								maxDate : '9999-12-31' 
							});

						});
						$('#beginDate').removeAttr("readonly");
					}
					if(objData.isNewFlag=="0"){
						$('#beginDate').val("");
						$('#beginDate').attr("readonly","readonly")
					}
			}
		);
	}
	
	function processBack(){
		location.href = "memberFlowPackageCfg.do";
	}

	function reset(){
		$("#memberCode").val();
		$("#memberName").val();
		$("#prePayAmount").val();
		$("#prePayFlow").val();
		$("#averageRate").val();
		$("#warnFlow").val();
		$("#warnLinkman").val();
		$("#warnLinkmanEmail").val();
		$("#warnLinkmanMobile").val();
		$("#shutDownFlow").val();
		$("#shutDownLinkman").val();
		$("#shutDownLinkmanEmail").val();
		$("#shutDownLinkmanMobile").val();
		$("#prePayDate").val();
		$("#beginDate").val();
	}

	function validateBeginDate(){
		var beginDate = $("#beginDate").val();
		var memberCode = $("#memberCode").val();
		var newFlag = $("#newFlag").val();
		if(newFlag == "0"){
			return false;
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

	function calRate(){
		var prePayAmount = $('#prePayAmount').val();
		var prePayFlow = $('#prePayFlow').val();
		if(prePayAmount!="" && prePayFlow!="" && parseInt(prePayFlow)>parseInt(prePayAmount)){
			$('#averageRate').val(Math.round(10000*prePayAmount/prePayFlow));
		}
	}

	function isEmpty(fData){
	    return ((fData==null) || (fData.length==0) );
	}
	
</script>
</head>

<body>
	<table width="25%" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
	<input id="sysDate" type="hidden" value="${sysDate}"/>
	<input id="newFlag" type="hidden" value="${newFlag}"/>
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
			<tr>
				<td height="18">
					<div align="center">
					 <c:if test="${newFlag == '1'}">
					 	<div>
							<font class="titletext">包量会员配置新增</font>
						</div>
					 </c:if>
					 <c:if test="${newFlag == '0'}">
					 	<div>
							<font class="titletext">确认为会员 【${dto.memberName}】续费?</font>
						</div>
					 </c:if>
					</div>
				</td>
			</tr>
			<tr>
				<td height="1" bgcolor="#000000"></td>
			</tr>
	</table>
<br>
	<form action="${ctx}/report/memberFlowPackageCfg.do?method=add" method="post" id="form1" name="form1">
	<table class="border_all2" width="90%" border="0" cellspacing="0"
		cellpadding="0" align="center">	
		<c:if test="${newFlag == '1'}">
			<tr class="trForContent1">
		      	<td class="border_top_right4" align="right">会员编号：</td>
		      	<td class="border_top_right4" align="left" colspan="3">
		        	<input type="text" id="memberCode" onblur="queryMemberName(this.value);" name="memberCode" maxlength="11" ><font style="font-family:MS Gothic;font-size:30px"></font> 
		      	</td>
		    </tr>
		    <tr class="trForContent1">
		      	<td class="border_top_right4" align="right" >会员名称：</td>
		      	<td class="border_top_right4" align="left" colspan="3">
		        	<input type="text" id="memberName" name="memberName" maxlength="11" readonly="readonly"><font style="font-family:MS Gothic;font-size:30px"></font> 
		      	</td>
		    </tr>
	   </c:if>
	   <c:if test="${newFlag == '0'}">
		   <input type="hidden" id="memberCode" name="memberCode" value="${dto.memberCode}">
		   <input type="hidden" id="memberName" name="memberName" value="${dto.memberName}">
	   </c:if>
		<tr class="trForContent1">
	    	<td class="border_top_right4" align="right" width="20%">预付金额：</td>
	    	<td class="border_top_right4" align="left"  width="30%">
	    		<input type="text" id="prePayAmount" name="prePayAmount" maxlength="19" onblur="calRate();" value="${dto.prePayAmountStr}"><font style="font-family:MS Gothic;font-size:30px"></font>元<br>
	    	</td>
	    	<td class="border_top_right4" align="right" width="20%">预付流量：</td>
	    	<td class="border_top_right4" align="left" width="30%">
	    		<input type="text" id="prePayFlow" name="prePayFlow" maxlength="19" onblur="calRate();" value="${dto.prePayFlowStr}"><font style="font-family:MS Gothic;font-size:30px"></font>元<br>
	    	</td>
		</tr>
	    <tr class="trForContent1">
	      	<td class="border_top_right4" align="right">预付日期：</td>
	      	<td class="border_top_right4" align="left">
	      		<input class="Wdate" type="text" id="prePayDate" name="prePayDate" onclick="WdatePicker()" />
	      	</td>
	      	<td class="border_top_right4" align="right">包量起始日期：</td>
	      	<td class="border_top_right4" align="left">
	      		<input class="Wdate" type="text" id="beginDate" name="beginDate" <c:if test="${newFlag == '0'}">readonly='readonly'</c:if>  />
	      	</td>
	    </tr>	  
		<tr class="trForContent1">
	      	<td class=border_top_right4 align="right" >平均费率：</td>
	      	<td class="border_top_right4" align="left" colspan="3">
	        	<input type="text" id="averageRate" name="averageRate" maxlength="19" value="${dto.averageRate}"><font style="font-family:MS Gothic;font-size:30px"></font>万分之一<br>
	      	</td>
	    </tr>
		<tr class="trForContent1">
	    	<td class="border_top_right4" align="right" width="20%">预警流量：</td>
	    	<td class="border_top_right4" align="left" width="30%">
	    		<input type="text" id="warnFlow" name="warnFlow" maxlength="19" value="${dto.warnFlowStr}"><font style="font-family:MS Gothic;font-size:30px"></font>元<br>
	    	</td>
	    	<td class="border_top_right4" align="right" width="20%">预警联系人：</td>
	    	<td class="border_top_right4" align="left" width="30%">
	    		<input type="text" id="warnLinkman" name="warnLinkman" maxlength="19" value="${dto.warnLinkman}"><font style="font-family:MS Gothic;font-size:30px" ></font><br>
	    	</td>
		</tr>
		<tr class="trForContent1">
	    	<td class="border_top_right4" align="right" width="20%">预警联系人邮箱：</td>
	    	<td class="border_top_right4" align="left" width="30%">
	    		<input type="text" id="warnLinkmanEmail" name="warnLinkmanEmail" maxlength="19" value="${dto.warnLinkmanEmail}"><font style="font-family:MS Gothic;font-size:30px"></font><br>
	    	</td>
	    	<td class="border_top_right4" align="right" width="20%">预警联系人号码：</td>
	    	<td class="border_top_right4" align="left" width="30%">
	    		<input type="text" id="warnLinkmanMobile" name="warnLinkmanMobile" maxlength="19" value="${dto.warnLinkmanMobile}"><font style="font-family:MS Gothic;font-size:30px"></font><br>
	    	</td>
		</tr>
		<tr class="trForContent1">
	      	<td class="border_top_right4" align="right" width="20%">关停流量：</td>
	      	<td class="border_top_right4" width="30%">
	        	<input type="text" id="shutDownFlow" name="shutDownFlow" maxlength="11" value="${dto.shutDownFlowStr}"><font style="font-family:MS Gothic;font-size:30px"></font>元<br>
	      	</td>
	      	<td class="border_top_right4" align="right" width="20%">关停联系人：</td>
	    	<td class="border_top_right4" align="left" width="30%">
	    		<input type="text" id="shutDownLinkman" name="shutDownLinkman" maxlength="19" value="${dto.warnLinkman}"><font style="font-family:MS Gothic;font-size:30px"></font><br>
	    	</td>
	    </tr>
		<tr class="trForContent1">
	    	<td class="border_top_right4" align="right" width="20%">关停联系人邮箱：</td>
	    	<td class="border_top_right4" align="left" width="30%">
	    		<input type="text" id="shutDownLinkmanEmail" name="shutDownLinkmanEmail" maxlength="19" value="${dto.shutDownLinkmanEmail}"><font style="font-family:MS Gothic;font-size:30px"></font><br>
	    	</td>
	    	<td class="border_top_right4" align="right" width="20%">关停联系人号码：</td>
	    	<td class="border_top_right4" align="left" width="30%">
	    		<input type="text" id="shutDownLinkmanMobile" name="shutDownLinkmanMobile" maxlength="19" value="${dto.shutDownLinkmanMobile}"><font style="font-family:MS Gothic;font-size:30px"></font><br>
	    	</td>
		</tr>
	  </table>
	 <c:if test="${not empty message}">
	 	<div>
			<li style="color: red;">${message}</li>
		</div>
	 </c:if>
	  <br>
	  <br>
	  <input type="button" onclick="javascript:add();"name="submitBtn" value="保 存" class="button2">&nbsp;&nbsp;&nbsp;
	  <input type="button" onclick="reset();" name="resetBtn" value="重置" class="button2">&nbsp;&nbsp;&nbsp;
	  <input type="button" onclick="javascript:processBack();" name="Submit2" value="返 回" class="button2">
	</form>
</body>
</html>
