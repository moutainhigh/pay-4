<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript" src="${ctx}/js/common.js"></script>

<head>
<title>成本费率配置</title>
<script type="text/javascript">
	function processBack(){
		//window.location.href='${ctx}/report/costRateSetting.do';
		window.history.back(); 
	}
	
	var arr=new Array();
	var pages=0,curPage=0;
	//var maxAmount="899999999999999936";
	var maxAmount="999999999999999";	
	var curMax="";
	var chkPolicyRst="";
	var policyType;
	
	function cfm(){
		if(!validateForm()) return false;
		if(confirm("确定要保存当前的费率配置么？")){
			if(validateDate() && validateNum()){			
				checkPolicy();
				//alert("["+chkPolicyRst + ":"+ chkPolicyRst.length + "]");
				if(""!=chkPolicyRst.replace(/\r\n/g,"")){
					alert("无法保存！当前出款银行渠道的费率配置与以下已有配置存在冲突：\n"+chkPolicyRst);	
				}else{
					saveCurPage();
					document.getElementById("arr").value=arr;	
					form1.submit();
				}
			}
		}	
	}

	function addItem(){
		if(!validateForm() || !validateNum()) return false;
		curMax=(curPage<pages ? arr[pages][3] : document.getElementById("endAmount").value)+"";			
		if(parseFloat(curMax)<parseFloat(maxAmount)){
			arrUp();
			changePage(++pages);
			document.getElementById("startAmount").value=curMax;
			document.getElementById("endAmount").value=maxAmount;
			//enableAmount();
		}else{
			alert("当前终止金额已达上限，无法再新增费率明细！");
			rightItem();
			document.getElementById("endAmount").focus();
		}
	}
	
	function delItem(){
		if(confirm("确定要移除当前费率明细么？")){
			setPage(--pages);
			setDelSta();
			setArrowSta();
			arr.pop();
			setItem(arr.length-1);
			enableAmount();
		}
	}

	function nextItem(){
		if(curPage<pages) changePage(curPage+1);
	}
	
	function previousItem(){
		if(curPage>0) changePage(curPage-1);
	}
	
	function leftItem(){
		if(curPage>0) changePage(0);
	}
	
	function rightItem(){
		if(curPage<pages) changePage(pages);
	}
	
	function changePage(pg){
		if(validateForm() && validateNum()){
			saveCurPage();
			setPage(pg);
			setDelSta();
			setArrowSta();
			enableAmount();
			setItem(curPage);
		}
	}
	
	function setArrowSta(){
		document.getElementById("leftItemBtn").disabled=(curPage>0 ? false : true);
		document.getElementById("previousItemBtn").disabled=(curPage>0 ? false : true);
		document.getElementById("rightItemBtn").disabled=(curPage<pages ? false : true);
		document.getElementById("nextItemBtn").disabled=(curPage<pages ? false : true);		
	}	

	function setDelSta(){
			document.getElementById("delItemBtn").disabled=(curPage<pages || pages==0 ? true : false);
	}

	function enableAmount(){
		document.getElementById("startAmount").disabled=(curPage==0 ? false : true);
		document.getElementById("endAmount").disabled=(curPage==pages ? false : true);
	}
	
	function setItem(pg){
		document.getElementById("fixAmount").value=arr[pg][0];
		document.getElementById("rateAmount").value=arr[pg][1];
		document.getElementById("startAmount").value=arr[pg][2];
		document.getElementById("endAmount").value=arr[pg][3];
		document.getElementById("minCharge").value=arr[pg][4];
		document.getElementById("maxCharge").value=arr[pg][5];
	}
	
	function setPage(curPg){
		curPage=curPg;
		document.getElementById("page").innerHTML=(curPage+1)+"/"+(pages+1);
	}
	
	function arrUp(){
			var arrItem=new Array(6);
			for(var i=0;i<6;i++)
				arrItem[i]="";
			arr.push(arrItem);
	}
	
	function saveCurPage(){
		arr[curPage][0]=document.getElementById("fixAmount").value;
		arr[curPage][1]=document.getElementById("rateAmount").value;
		arr[curPage][2]=document.getElementById("startAmount").value;
		arr[curPage][3]=document.getElementById("endAmount").value;
		arr[curPage][4]=document.getElementById("minCharge").value;
		arr[curPage][5]=document.getElementById("maxCharge").value;
	}
		
	function goItems(){
		//document.getElementById("channelNo").disabled=true;
		//document.getElementById("crDrCard").disabled=true;	
		document.getElementById("pType").disabled=true;	
		//document.getElementById("countType").disabled=true;	
		//document.getElementById("startDate").disabled=true;	
		//document.getElementById("endDate").disabled=true;
		document.getElementById("nextStepBtn").disabled=true;
		document.getElementById("itemsTitle").disabled=false;	
		document.getElementById("calPolicyTable").disabled=false;	
		
		policyType=document.getElementById("pType").value;
		if(policyType==1 || policyType>5){
			document.getElementById("fixAmount").disabled=false;	
		}
		if(policyType>1){
			document.getElementById("rateAmount").disabled=false;	
		}
		document.getElementById("startAmount").disabled=false;	
		document.getElementById("endAmount").disabled=false;	
		if(policyType==3 || policyType==5 || policyType==7 || policyType==9){
			document.getElementById("minCharge").disabled=false;	
		}
		if(policyType==4 || policyType==5 || policyType>7){
			document.getElementById("maxCharge").disabled=false;	
		}
		document.getElementById("policyType").value=policyType;
				
		document.getElementById("leftItemBtn").disabled=false;
		document.getElementById("previousItemBtn").disabled=false;	
		document.getElementById("addItemBtn").disabled=false;	
		//document.getElementById("delItemBtn").disabled=false;	
		document.getElementById("nextItemBtn").disabled=false;	
		document.getElementById("rightItemBtn").disabled=false;	
		document.getElementById("saveBtn").disabled=false;
		setPage(0);
		setArrowSta();
		arrUp();
		document.getElementById("startAmount").value=0;
		document.getElementById("endAmount").value=maxAmount;	
	}
	
	function checkPolicy()
	{
		var pars = "";
		$.ajax({
			type: "POST",
			url: "${ctx}/report/costRateSetting.do?method=checkPolicy&channelNo="+document.getElementById("channelNo").value
					+"&startDate="+document.getElementById("startDate").value
					+"&endDate="+document.getElementById("endDate").value
					+"&fifoChannel="+document.getElementById("fifoChannel").value
					+"&address="+document.getElementById("address").value
					+"&inOut="+document.getElementById("inOut").value
					+"&isPrivate="+document.getElementById("isPrivate").value,
			data: pars,
			async:false,
			success: function(result) {
				chkPolicyRst=result;
			}
		});
	}

	function validateDate(){
		var sDate = document.getElementById("startDate").value;
		var eDate = document.getElementById("endDate").value;
		if(sDate==""){
			alert("启用日期不能为空");
			document.getElementById("startDate").focus();
			return false;
		}
		if(eDate==""){
			alert("停用日期不能为空");
			document.getElementById("endDate").focus();
			return false;
		}
		if(sDate > eDate){
			alert("停用日期必须不早于启用日期");
			document.getElementById("endDate").focus();
			return false;
		}
		return true;
	}

	function validateNum(){
		//alert(policyType);
		if(policyType==1 || policyType>5){
			if(document.getElementById("fixAmount").value.replace(/^\s*|\s*$/g,"")==""){
				alert("固定费用不能为空");
				document.getElementById("fixAmount").focus();
				return false;
			}
		}
		if(policyType>1){
			if(document.getElementById("rateAmount").value.replace(/^\s*|\s*$/g,"")==""){
				alert("费率不能为空");
				document.getElementById("rateAmount").focus();
				return false;
			}			
		}
		
		if(document.getElementById("startAmount").disabled==false){
			if(document.getElementById("startAmount").value.replace(/^\s*|\s*$/g,"")==""){
				alert("起始金额不能为空");
				document.getElementById("startAmount").focus();
				return false;
			}
		}
		if(document.getElementById("endAmount").disabled==false){
			if(document.getElementById("endAmount").value.replace(/^\s*|\s*$/g,"")==""){
				alert("终止金额不能为空");
				document.getElementById("endAmount").focus();
				return false;
			}
		}
		
		if(parseInt(document.getElementById("endAmount").value)<=parseInt(document.getElementById("startAmount").value)){
			alert("终止金额必须大于起始金额");
			if(document.getElementById("endAmount").disabled==false){
				document.getElementById("endAmount").focus();
			}else{
				document.getElementById("startAmount").focus();
			}
			return false;
		}
		
		if(policyType==3 || policyType==5 || policyType==7 || policyType==9){
			if(document.getElementById("minCharge").value.replace(/^\s*|\s*$/g,"")==""){
				alert("下限不能为空");
				document.getElementById("minCharge").focus();
				return false;
			}
		}
		if(policyType==4 || policyType==5 || policyType>7){
			if(document.getElementById("maxCharge").value.replace(/^\s*|\s*$/g,"")==""){
				alert("上限不能为空");
				document.getElementById("maxCharge").focus();
				return false;
			}
		}
		if(policyType==5 || policyType==9){
			if(parseFloat(document.getElementById("minCharge").value)>=parseFloat(document.getElementById("maxCharge").value)){
				alert("下限必须大于上限");
				document.getElementById("maxCharge").focus();
				return false;
			}
		}		

		return true;
	}
	
	function validateForm(){
		 $("#form1").validate({
			rules: { 
		    	fixAmount:{
		    		digits:true,
					range:[0,999999999999999]
				},
				startAmount:{
					digits:true,
					range:[0,999999999999999]
				},
				endAmount:{
					digits:true,
					range:[0,999999999999999]
				},
				minCharge:{
					digits:true,
					range:[0,999999999999999]
				},				
				maxCharge:{
					digits:true,
					range:[0,999999999999999]
				},
				rateAmount:{
					digits:true,
					range:[0,99999]
				}
			}
		 });
		 return $("#form1").valid();
	 }

	 $(document).ready(function(){
		 //页面validate
		 validateForm();
	 });	
</script>
</head>

<body>
	<table width="320" border="0" cellspacing="0" cellpadding="0" align="center" height="21">
			<tr>
				<td height="1" bgcolor="#000000"/>
			</tr>
			<tr>
				<td height="18">
					<div align="center">
						<font class="titletext">出款银行成本费率配置 — 新增</font>
					</div>
				</td>
			</tr>
			<tr>
				<td height="1" bgcolor="#000000"/>
			</tr>
	</table>
	<br>
	
	<form id="form1" name="form1" action="${ctx}/report/costRateSetting.do?method=add" method="post">
	  <input type="hidden" name="fifoChannel" id="fifoChannel" value="${fifoChannel}"/>
	  <input type="hidden" name="arr" id="arr" />
	  <input type="hidden" name="policyType" id="policyType" />	
	  <table id="policyTable" border="0" cellpadding="5" cellspacing="0" width="75%">
	     <tr>
	    	<td align="right" width="22%">出款银行名称：</td>
			<td align="left" width="28%">
	        	<select name="channelNo" id="channelNo">
			    	<c:forEach items="${pciList}" var="pci">
			    		<option value="${pci.orgCode}">${pci.desciption}</option>
			    	</c:forEach>	        	
	        	</select>	    	
	    	</td>
	    	<td align="right" width="22%">对公/私：</td>
	    	<td align="left">	    	
	        	<select name="isPrivate" id="isPrivate">
	       			<option value=0>对私</option>
	       			<option value=1>对公</option>   
	        	</select>	   	    	
	    	</td>
	 	 </tr>
	     <tr>
	    	<td align="right" width="22%">同/跨行：</td>
			<td align="left" width="28%">
	        	<select name="inOut" id="inOut">
	       			<option value=1>同行</option>
	       			<option value=2>跨行</option>       	
	        	</select>	    	
	    	</td>
	    	<td align="right" width="22%">所在地：</td>
	    	<td align="left">	    	
	        	<select name="address" id="address">
	       			<option value=1>同城</option>
	       			<option value=2>异地</option>  
	        	</select>	   	    	
	    	</td>
	 	 </tr>	 	 
	     <tr>
	    	<td align="right" width="22%">缓急程度：</td>
			<td align="left" width="28%">
	        	<select name="isUrgency" id="isUrgency">
	       			<option value=0>普通</option>
	       			<option value=1>加急</option>        	
	        	</select>	    	
	    	</td>
	    	<td align="right" width="22%">出款类型：</td>
	    	<td align="left">	    	
	        	<select name="outType" id="outType">
	       			<option value=1>代发</option>
	       			<option value=2>转账</option> 
	        	</select>	   	    	
	    	</td>
	 	 </tr>
		 <tr>
	      	<td align="right">费率配置类型：</td>
	    	<td align="left">	      	
	        	<select name="pType" id="pType">
					<option value=1>固定费用</option>
					<option value=2>费率</option>
					<option value=3>费率及下限</option>
					<option value=4>费率及上限</option>
					<option value=5>费率及上下限</option>
					<option value=6>固定费用_费率</option>
					<option value=7>固定费用_费率及下限</option>
					<option value=8>固定费用_费率及上限</option>
					<option value=9>固定费用_费率及上下限</option>
	        	</select>	   	      	
	      	</td>
	      	<td align="right">计算方式：</td>
	    	<td align="left">	      	
	        	<select name="countType" id="countType">
					<option value=1>单笔交易</option>
					<!-- 
						<option value=2>按月累计流量</option>
						<option value=3>按年累计流量</option>
						<option value=4>按季度累计流量</option>
					 -->
	        	</select>	   	      	
	      	</td>
	     </tr>
		 <tr>
	      	<td align="right">启用日期：</td>
	      	<td align="left">
	      		<input class="Wdate" type="text" id="startDate" name="startDate" onclick="WdatePicker()" />
	      	</td>
	      	<td align="right">停用日期：</td>
	      	<td align="left">
	      		<input class="Wdate" type="text" id="endDate" name="endDate"  onclick="WdatePicker()" />
	      	</td>
	    </tr>	    
	  </table>
	  <br>
	  <input type="button" id="nextStepBtn" name="nextStepBtn" value="下一步" onclick="javascript:goItems()">
	  <hr>
	  <br>
	  <div id="itemsTitle" align="center" disabled><b>费率明细</b></div>
	  <br>
      <table id="calPolicyTable" border="0" cellpadding="5" cellspacing="0" width="75%" disabled> 
	    <tr>
	    	<td align="right" width="22%">固定费用：</td>
	    	<td align="left" width="28%">
	    		<input type="text" id="fixAmount" name="fixAmount" maxlength="22" size="22" disabled>分
	    	</td>
	    	<td align="right" width="22%">费率：</td>
	    	<td align="left">
	    		<input type="text" id="rateAmount" name="rateAmount" maxlength="5" size="16" disabled>十万分之一
	    	</td>
		</tr>
	    <tr>
	    	<td align="right">起始金额：</td>
	    	<td align="left">
	    		<input type="text" id="startAmount" name="startAmount" maxlength="22" size="22" disabled>分
	    	</td>
	    	<td align="right">终止金额：</td>
	    	<td align="left">
	    		<input type="text" id="endAmount" name="endAmount" maxlength="22" size="22" disabled>分
	    	</td>
		</tr>
	    <tr>
	    	<td align="right">下限：</td>
	    	<td align="left">
	    		<input type="text" id="minCharge"  name="minCharge" maxlength="22" size="22" disabled>分
	    	</td>
	    	<td align="right">上限：</td>
	    	<td align="left">
	    		<input type="text" id="maxCharge" name="maxCharge" maxlength="22" size="22" disabled>分
	    	</td>
		</tr>
	  </table>
	  <br>
	  <input type="button" name="delItemBtn" id="delItemBtn" value=" 移 除 " onclick="javascript:delItem()" disabled>&nbsp;&nbsp;&nbsp;
	  <input type="button" name="leftItemBtn" id="leftItemBtn" value=" << " onclick="javascript:leftItem()" disabled>&nbsp;&nbsp;&nbsp;
	  <input type="button" name="previousItemBtn"  id="previousItemBtn" value=" <  " onclick="javascript:previousItem()" disabled>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	  <span name="page" id="page" align="center"></span>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	  <input type="button" name="nextItemBtn" id="nextItemBtn" value="  > " onclick="javascript:nextItem()" disabled>&nbsp;&nbsp;&nbsp;
	  <input type="button" name="rightItemBtn" id="rightItemBtn" value=" >> " onclick="javascript:rightItem()" disabled>&nbsp;&nbsp;&nbsp;
	  <input type="button" name="addItemBtn" id="addItemBtn" value=" 新 增 " onclick="javascript:addItem()" disabled>
	  <hr>
	  <br>
	  <input type="button" id="saveBtn" name="saveBtn" value=" 保 存 " onclick="javascript:cfm()" disabled>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
	  <input type="button" onclick="javascript:processBack()" name="Submit2" value=" 返 回 ">	  
	</form>
</body>
