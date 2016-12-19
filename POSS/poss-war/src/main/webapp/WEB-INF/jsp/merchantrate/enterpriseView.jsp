<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>


<head>
<%--引入用于格式化页面的CSS文件--%>
<%@ include file="/common/taglibs.jsp"%>

<!-- 自定义样式 -->
<!-- jquery validate -->
<script src="${ctx}/js/jquery/plugins/validate/jquery.validate.min.js" type="text/javascript"></script>
<link href="${ctx}/js/jquery/plugins/validate/jquery.validate.css" type="text/css" rel="stylesheet" />
<script src="${ctx}/js/jquery/plugins/validate/messages_cn.js" type="text/javascript"></script>
<script src="${ctx}/js/jquery/plugins/validate/jquery.form.js" type="text/javascript"></script>

<script src="./js/common.js"></script>
<script src="./js/mainstyle1/body.js"></script>
<script src="./js/formvalid/formValid.js"></script>
<script src="./js/formvalid/global.js"></script>

<script type='text/javascript' src='./js/jquery/plugins/autocomplate/thickbox-compressed.js'></script>
<script type='text/javascript' src='./js/jquery/plugins/autocomplate/jquery.autocomplete.min.js'></script>
<link rel="stylesheet" type="text/css" href="./js/jquery/plugins/autocomplate/jquery.autocomplete.css" />
<link rel="stylesheet" type="text/css" href="./js/jquery/plugins/autocomplate/thickbox.css" />
<%--引入用于格式化页面的CSS文件--%>
<style>
.search_box{ position:relative;}
.pop_con{ height:100px; padding:0; border:1px solid #999; background:#FFFCED; position:absolute; top:-113px; left:0; overflow:auto;}
.pop_con ul{margin:0;padding:0;}
.pop_con li{ padding:0 5px; list-style:none; height:20px; line-height:20px; overflow:hidden;}
.pop_con .hover{ background:#EADFBB}
</style>
 
<script language="javascript">

	$(function(){
		
		$("input[type=text]").blur(function(){
			$(this).val($.trim(this.value));
		});
		var industryBoolean = true;
		var signDepartBoolean = true;
		$("span[id*=Tip]").attr("style","color:orange");
		var refundCurrency=	$("#refundCurrency").val();
		$.ajax({
			type: "POST",
			url: "${ctx}/merchantRate.do?method=getRefundCurrency",
			success: function(result) {
				var globalData = JSON.parse(result);
				for(var k in globalData){
					if(k.trim()==refundCurrency.trim()){
						$("#refundFeeCurrency").append("<option value="+k+" selected>"+globalData[k]+"</option>");
					}else{
						$("#refundFeeCurrency").append("<option value="+k+">"+globalData[k]+"</option>");				
					}
				}
			}
		});
		queryChargeBackConfig();
		
		//“添加商户费率”窗口中添加“地区”选择框事件绑定方法add by davis.guo at 2016-07-27
		$("#countryCode").bind('click change', function(){//change
			var value=$(this).children('option:selected').val();
			localLockSet(value);
		});
	});
	
	//add by davis.guo at 2016-08-04
	function localLockSet(value)
	{
		/*
		a.新增 支付方式（运营人员配置本地化的渠道编码）显示Boleto\EPS等，不勾选Local时，此选项为“--”
		b.若为本地化配置费率，勾选Local，其余选项自动强制选择，卡组织选择local，交易方式选择全部，卡类别选择无卡，交易模型选择全部，交易类型还是202，类型让运营自行选择
		*/
		if(value=="LOCAL"){
			//卡组织选择“local”，并设置不让选择
			$("#organisation option").each(function(){
				if($(this).val() == "LOCAL"){
					$(this).attr("selected","selected");
				    $("#organisation").attr("disabled","disabled");
				};
			});
			
			//交易方式选择“全部”，并设置不让选择
			$("#transType option").each(function(){
				if($(this).val() == "0"){
					$(this).attr("selected","selected");
				    $("#transType").attr("disabled","disabled");
				};
			});
			
			//卡类别选择“无卡”，并设置不让选择
			$("#cardType option").each(function(){
				if($(this).val() == "4"){
					$(this).attr("selected","selected");
				    $("#cardType").attr("disabled","disabled");
				};
			});
			
			//交易模型选择“全部”，并设置不让选择
			$("#transMode option").each(function(){
				if($(this).val() == "0"){
					$(this).attr("selected","selected");
				    $("#transMode").attr("disabled","disabled");
				};
			});
			
			//交易类型还是“202”，并设置不让选择
			$("#dealCode").attr("disabled","disabled");
			
		}else{
			unlockSet();//非本地化，解锁字段。
			$("#localPayCode").val("--");
		}
	}
	//表单提交时，解锁字段，不然无法提交选择的数据 add by davis.guo at 2016-08-04
	function unlockSet()
	{
		//解锁卡组织
		$("#organisation").removeAttr("disabled");
		
		//解锁交易方式
		$("#transType").removeAttr("disabled");
		
		//解锁卡类别
		$("#cardType").removeAttr("disabled");
		
		//解锁交易方式
		$("#transMode").removeAttr("disabled");
		
		//解锁交易类型
		$("#dealCode").removeAttr("disabled");
	}
	//复选框选择“本地化”时，若为本地化配置费率，勾选Local，其余选项（卡组织选择local，交易方式选择全部，卡类别选择无卡，交易模型选择全部，交易类型还是202）自动强制选择。add by davis.guo at 2016-08-04
	function checkLocalLockSet()
	{
		eurCheck();
		if(!$("#chkEur").attr("checked") && !$("#chkNotEur").attr("checked") && $("#local").attr("checked")){//只选择“本地化”时
			localLockSet("LOCAL");
		}
		else if(!$("#local").attr("checked") && ($("#chkEur").attr("checked") || $("#chkNotEur").attr("checked")))
		{
			unlockSet();
		}
	}
	//20160418-Mack-comment below code and add new
	/*var all=["AD","AE","AF","AG","AI","AL","AM","AN","AO","AQ","AR","AS","AT","AU","AW","AZ","BA","BB","BD","BE","BF","BG","BH","BI","BJ","BM","BN","BO","BR","BS","BT","BV","BW","BY","BZ","CA","CC","CF","CG","CH","CI","CK","CL","CM","CN","CO","CR","CS","CT","CU","CV","CY","CZ","DE","DJ","DK","DM","DO","DZ","EC","EE","EG","EH","ER","ES","ET","FI","FJ","FK","FM","FO","FR","GA","GB","GD","GE","GF","GH","GI","GL","GM","GN","GP","GQ","GR","GS","GT","GU","GW","GY","HK","HM","HN","HR","HT","HU","ID","IE","IL","IN","IO","IQ","IR","IS","IT","JM","JO","JP","KE","KG","KH","KI","KM","KN","KP","KR","KW","KY","KZ","LA","LB","LC","LI","LK","LR","LS","LT","LU","LV","LY","MA","MC","MD","MG","MH","MK","ML","MM","MN","MO","MP","MQ","MR","MS","MT","MU","MV","MW","MX","MY","MZ","NA","NC","NE","NF","NG","NI","NL","NO","NP","NR","NU","NZ","OM","PA","PE","PF","PG","PH","PK","PL","PM","PN","PR","PS","PT","PW","PY","QA","RE","RO","RU","RW","SA","SB","SC","SD","SE","SG","SI","SJ","SK","SL","SM","SN","SO","SR","SV","SY","SZ","Sh","St","TC","TD","TF","TG","TH","TJ","TK","TM","TN","TO","TP","TR","TT","TV","TZ","UA","UG","UM","US","UY","UZ","VA","VC","VE","VG","VI","VN","VU","WF","WS","YE","YT","YU","ZA","ZM","ZR","ZW"];
	var eur=["AT","BE","BG","CY","CZ","DE","DK","EE","ES","FI","FR","GB","GR","HR","HU","IE","IT","LT","LU","LV","MT","NL","PL","PT","RO","SE","SI","SK"];
	var notEur=["AD","AE","AF","AG","AI","AL","AM","AN","AO","AQ","AR","AS","AU","AW","AZ","BA","BB","BD","BF","BH","BI","BJ","BM","BN","BO","BR","BS","BT","BV","BW","BY","BZ","CA","CC","CF","CG","CH","CI","CK","CL","CM","CN","CO","CR","CS","CT","CU","CV","DJ","DM","DO","DZ","EC","EG","EH","ER","ET","FJ","FK","FM","FO","GA","GD","GE","GF","GH","GI","GL","GM","GN","GP","GQ","GS","GT","GU","GW","GY","HK","HM","HN","HT","ID","IL","IN","IO","IQ","IR","IS","JM","JO","JP","KE","KG","KH","KI","KM","KN","KP","KR","KW","KY","KZ","LA","LB","LC","LI","LK","LR","LS","LY","MA","MC","MD","MG","MH","MK","ML","MM","MN","MO","MP","MQ","MR","MS","MU","MV","MW","MX","MY","MZ","NA","NC","NE","NF","NG","NI","NO","NP","NR","NU","NZ","OM","PA","PE","PF","PG","PH","PK","PM","PN","PR","PS","PW","PY","QA","RE","RU","RW","SA","SB","SC","SD","SG","SJ","SL","SM","SN","SO","SR","SV","SY","SZ","Sh","St","TC","TD","TF","TG","TH","TJ","TK","TM","TN","TO","TP","TR","TT","TV","TZ","UA","UG","UM","US","UY","UZ","VA","VC","VE","VG","VI","VN","VU","WF","WS","YE","YT","YU","ZA","ZM","ZR","ZW"];
	*/
	var all=["VU","VN","EC","VI","DZ","VG","VE","DM","DO","VC","VA","DE","UZ","UY","DK","DJ","US","UM","UG","UA","ET","ES","ER","EH","EG","EE","TZ","TT","TW","TV","GD","GE","GF","GA","GB","FR","FO","FK","FJ","FM","FI","WS","GY","GW","GU","GT","GS","GR","GQ","WF","GP","GN","GM","GL","GI","GH","RE","RO","AT","AS","AR","AQ","AW","QA","AU","AZ","BA","PT","AD","PW","AG","AE","PR","PS","AF","AL","AI","AO","PY","AM","AN","TG","BW","TF","BV","BY","TD","TK","BS","BR","TJ","Sh","BT","TH","TO","TN","TM","CA","TR","BZ","TP","BF","BG","SV","SS","BH","BI","ST","SY","BB","SZ","BD","SX","BE","BN","BO","BJ","TC","BM","CZ","SD","CY","SC","CW","SE","CV","SG","CU","CT","SJ","CS","SI","SL","SK","SN","SM","SO","SR","CI","RS","CG","RU","CH","CF","RW","CC","CD","CR","CO","CM","CN","CK","SA","CL","SB","LV","LU","LT","LY","LS","LR","MG","MH","ME","MK","ML","MC","MD","MA","MV","MU","MX","MW","MZ","MY","MN","MM","MP","MO","MR","MQ","MT","MS","NF","NG","NI","NL","NA","NC","NE","NZ","NU","NR","NP","NO","OM","PL","PM","PN","PH","PK","PE","PF","PG","PA","HK","ZA","HN","HM","HR","HT","HU","ZM","ID","ZW","ZR","IE","IL","IN","IO","IQ","IR","YE","IS","IT","YU","YT","JP","JO","JM","KI","KH","KG","KE","KP","KR","KM","KN","KW","KY","KZ","LA","LC","LB","LI","LK","KS","LOCAL"];
    var eur=["AT","BE","BG","CY","CZ","DE","DK","EE","ES","FI","FR","GB","GR","HR","HU","IE","IT","LT","LU","LV","MT","NL","PL","PT","RO","SE","SI","SK"];
    var notEur=["VU","VN","EC","VI","DZ","VG","VE","DM","DO","VC","VA","UZ","UY","DJ","US","UM","UG","UA","ET","ER","EH","EG","TZ","TT","TW","TV","GD","GE","GF","GA","FO","FK","FJ","FM","WS","GY","GW","GU","GT","GS","GQ","WF","GP","GN","GM","GL","GI","GH","RE","AS","AR","AQ","AW","QA","AU","AZ","BA","AD","PW","AG","AE","PR","PS","AF","AL","AI","AO","PY","AM","AN","TG","BW","TF","BV","BY","TD","TK","BS","BR","TJ","Sh","BT","TH","TO","TN","TM","CA","TR","BZ","TP","BF","SV","SS","BH","BI","ST","SY","BB","SZ","BD","SX","BN","BO","BJ","TC","BM","SD","SC","CW","CV","SG","CU","CT","SJ","CS","SL","SN","SM","SO","SR","CI","RS","CG","RU","CH","CF","RW","CC","CD","CR","CO","CM","CN","CK","SA","CL","SB","LY","LS","LR","MG","MH","ME","MK","ML","MC","MD","MA","MV","MU","MX","MW","MZ","MY","MN","MM","MP","MO","MR","MQ","MS","NF","NG","NI","NA","NC","NE","NZ","NU","NR","NP","NO","OM","PM","PN","PH","PK","PE","PF","PG","PA","HK","ZA","HN","HM","HT","ZM","ID","ZW","ZR","IL","IN","IO","IQ","IR","YE","IS","YU","YT","JP","JO","JM","KI","KH","KG","KE","KP","KR","KM","KN","KW","KY","KZ","LA","LC","LB","LI","LK","KS"];
	var local=["LOCAL"]

	
	//显示添加机构的dialog
	function shouAddFee(){
		resetForm();
		initData(true);
		//initPayWayData(true);
		$("#saveOrUpdate").val("add");
		$('#addLogDiv').dialog({ 
				position:["center","center"],
				width:650,
				height:410,
				modal:true, 	
				title:'添加商户费率', 
				overlay: {opacity: 0.5, background: "black" ,overflow:'auto' }
		});
	}
	function initData(async){
		$.ajax({
			type: "POST",
			url: '${ctx}/merchantRate.do?method=getCurrencyCodes',
			dataType:"json",
			async:async,
			success: function(result) {
				$.each(result,function(index,item){
					$("#levelCurrencyCode").append("<option value='"+item+"'>"+item+"</option>");
					$("#level1CurrencyCode").append("<option value='"+item+"'>"+item+"</option>");
					$("#level2CurrencyCode").append("<option value='"+item+"'>"+item+"</option>");
					$("#level3CurrencyCode").append("<option value='"+item+"'>"+item+"</option>");
					$("#fixedCurrencyCode").append("<option value='"+item+"'>"+item+"</option>");
					$("#fixed1CurrencyCode").append("<option value='"+item+"'>"+item+"</option>");
					$("#fixed2CurrencyCode").append("<option value='"+item+"'>"+item+"</option>");
					$("#fixed3CurrencyCode").append("<option value='"+item+"'>"+item+"</option>");
				});
			}
		});
	}
	//异步从数据库加载卡组织类型，以填充支付方式add by Davis.guo at 2016-07-28
	function initPayWayData(async){
		/* $.getJSON(url, function(data) {
	        for(var i=0;i<data.length;i++){
	        	str += "<option value='" + data[i].orgCode + "'>" + data[i].orgName + "</option>";
	        }
	        $("#orgCode").html(str);
	    }); */
		$.ajax({
			type: "POST",
			url: '${ctx}/merchantRate.do?method=getLocalPayWay',
			dataType:"json",
			async:async,
			success: function(result) {
				$.each(result,function(index,item){
					$("#localPayCode").append("<option value='"+item.orgCord+"'>"+item.orgName+"</option>");
				});
			}
		});
	}
	function closeFee(){
		$("#addLogDiv").dialog("close");  //关闭Dialog
		resetForm();
	}
	
	function isPirce(s){
	    //s = trim(s);
	    var p =/^[1-9](\d+(\.\d{1,2})?)?$/; 
	    var p1=/^[0-9](\.\d{1,2})?$/;
	    return p.test(s) || p1.test(s);
	}
	
	function addFee() {
	    var countryCode = $('#countryCode').val();
	    var organisation = $('#organisation').val();
	    var transType = $('#transType').val();
	    var cardType = $('#cardType').val();
	    var transMode = $('#transMode').val();
	    var dealCode = $('#dealCode').val();
	    var chargeRate = $('#chargeRate').val();
	    var fixedCharge = $('#fixedCharge').val();
	    var feeType = $('#feeType').val();
	    var localPayCode = $('#localPayCode').val();
	    if(!countryCode){
	    	alert('请输入地区！');
	    	return;
	    }
		//add by davis.guo
	    if(countryCode=='LOCAL' && localPayCode=='--'){
	    	alert('请选择本地化支付方式！');
	    	return;
	    }
	    if(!organisation){
	    	alert('请输入卡组织！');
	    	return;
	    }
	    if(''== cardType){
	    	alert('请选择卡类别！');
	    	return;
	    }
	    if(''== transMode){
	    	alert('请选择交易模型！');
	    	return;
	    }
	    if(''== feeType){
	    	alert('请选择费率类型！');
	    	return;
	    }
	
	    if(''== dealCode){
	    	alert('请选择交易类型！');
	    	return;
	    }
	    
	    if(!$("#monthAmountLevel").val()){
	    	alert('请输入最低月交易量！');
	    	return;
	    }
	    
	    if(!chargeRate){
	    	alert('请输入费率!');
			return;
	    }
	    
	    if(!fixedCharge){
	    	alert('请输入固定费用值!');
			return;
	    }
	    
	    if('1' == feeType){
	    	if('' == fixedCharge){
	    		alert('请输入固定费用值!');
	    		return;
	    	}
	    	if(!isPirce(fixedCharge)){
	    		alert('请输入合法固定费率!');
	    		return;
	    	}
	    }
	    
	    if('2' == feeType){
	    	if('' == chargeRate){
	    		alert('请输入费率!');
	    		return;
	    	}
	    	if(!isPirce(chargeRate)){
	    		alert('请输入合法费率!');
	    		return;
	    	}
	    }
	    
	    if('6' == feeType){
	    	if('' == chargeRate || '' == fixedCharge){
	    		alert('费率或者固定费用不能为空!');
	    		return;
	    	}
	    	if(!isPirce(chargeRate)){
	    		alert('请输入合法费率!');
	    		return;
	    	}
	    	if(!isPirce(fixedCharge)){
	    		alert('请输入合法固定费率!');
	    		return;
	    	}
	    }
	    //分档计费校验
	    if($("#monthAmountLevel1").val()){
	    	if(parseInt($("#monthAmountLevel1").val())<=$("#monthAmountLevel").val()){
	    		alert("第二档交易量必须大于第一档！");
	    		return;
	    	}
	    	if($("#levelCurrencyCode").val()!=$("#level1CurrencyCode").val()){
	    		alert("币种要保持一致！");
	    		return;
	    	}
	    }
	    if($("#monthAmountLevel2").val()){
	    	if(parseInt($("#monthAmountLevel2").val())<=$("#monthAmountLevel1").val()){
	    		alert("第三档交易量必须大于第二档！");
	    		return;
	    	}
	    	if($("#level1CurrencyCode").val()!=$("#level2CurrencyCode").val()){
	    		alert("币种要保持一致！");
	    		return;
	    	}
	    }
	    if($("#monthAmountLevel3").val()){
	    	if(parseInt($("#monthAmountLevel3").val())<=$("#monthAmountLevel2").val()){
	    		alert("第四档交易量必须大于第三档！");
	    		return;
	    	}
	    	if($("#level2CurrencyCode").val()!=$("#level3CurrencyCode").val()){
	    		alert("币种要保持一致！");
	    		return;
	    	}
	    }
	    unlockSet();//提交前解锁表单add by davis.guo at 2016-08-04
	    var pars = $("#addForm").serialize();
	    pars=pars.replace("id=","id="+$("#id").val());
	    var url;
	    if($("#saveOrUpdate").val()=='add'){
	    	url = '${ctx}/merchantRate.do?method=addRate';
	    }else if($("#saveOrUpdate").val()=='update'){
	    	url = '${ctx}/merchantRate.do?method=updateRate';
	    }
		$.ajax({
			type: "POST",
			url: url,
			data: pars,
			success: function(result) {
				if(result=='1000'){
					alert("该纬度的配置已存在！");
				}else if(result){
					alert("操作成功！");
					$("#addLogDiv").dialog("close");  //关闭Dialog
					window.location.reload();  //重新查询所有信息
				}else{
					alert("操作失败！");
				};
			}
		});
		
	}
	
	function updateMerchant() {
		
		var accountMode = $('#accountMode').val();
		var percentage = $('#percentage').val();
		var assurePercentage = $('#assurePercentage').val();
		var assureSettlementCycle = $('#assureSettlementCycle').val();
		/* var withdrawFee = $('#withdrawFee').val(); */
		var batchpayFee = $('#batchpayFee').val();
		var riskFee = $('#riskFee').val();
		var refundFee=$("#refundFee").val();
		
		if(''==accountMode || !isPirce(accountMode)){
			alert('请输入合法的结算周期');
			$('#accountMode').focus();
			return;
		}
		if(''==percentage || !isPirce(percentage)){
			alert('请输入合法的结算比例');
			$('#percentage').focus();
			return;
		}
		if(''==assurePercentage || !isPirce(assurePercentage)){
			alert('请输入合法的保证金比例');
			$('#assurePercentage').focus();
			return;
		}
		if(''==assureSettlementCycle || !isPirce(assureSettlementCycle)){
			alert('请输入合法的保证金结算周期');
			$('#assureSettlementCycle').focus();
			return;
		}
		/* if(''==withdrawFee || !isPirce(withdrawFee)){
			alert('请输入合法的提现手续费');
			$('#withdrawFee').focus();
			return;
		} */
		if(''==batchpayFee || !isPirce(batchpayFee)){
			alert('请输入合法的批量出款手续费');
			$('#batchpayFee').focus();
			return;
		}
		if(''==riskFee || !isPirce(riskFee)){
			alert('请输入合法的风控处理费');
			$('#riskFee').focus();
			return;
		}
		
		if(''==refundFee || !isPirce(refundFee)){
			alert('请输入合法的退款手续费');
			$('#refundFee').focus();
			return;
		}
		//---------增加动态行数据校验开始
		var dyTrFlag = acctWithDrawCheck() ;
		if(!dyTrFlag) return false ;
		var dataArr = constructWithDrawData() ;
		dataArr = encodeURIComponent(encodeURIComponent(window.JSON.stringify(dataArr))) ;
		//---------增加动态行数据校验结束
		var pars = $("#merchantFormBean").serialize();
		pars = pars + "&dataArr=" + dataArr ;		
		var url = '${ctx}/merchantRate.do?method=onSubmit';
		
		$.ajax({
			type: "POST",
			url: url,
			data: pars,
			success: function(result) {
				if(result){
					alert("操作成功！");
					//$("#addLogDiv").dialog("close");  //关闭Dialog
					window.location.reload();  //重新查询所有信息
				}else{
					alert("操作失败！");
				};
			}
		});
	}
	
	function changeProvince(father,son){
		var relationArray = new Array();
		<c:forEach items = "${relationList}" var = "relation" varStatus = "relationStatus">
			relationArray[${relationStatus.index}] = new dropDownListMode('${relation.fatherCode}','${relation.code}','${relation.name}');	
		</c:forEach>
		
		this.changeFatherSelect(father,son,relationArray,null);
	}
	
	function closePage(url){
		parent.closePage(url);
	}
	
	function check(){
		var searchData = document.getElementsByName("searchData");
		for(var i = 0;i<searchData.length;i++){
			if(searchData[i].checked==true){
				document.getElementById('signDepart').value = searchData[i].value;
		    }
		}
		document.getElementById('resultListDiv').style.display = "none";
	}
	
	/* function loadBranchBanks(loadIndex,defaultBankNo){
		var bankName = $("#bankId_"+loadIndex).find("option:selected").text();
		var provinceName = $("#regionBank_"+loadIndex).find("option:selected").text();
		var cityName = $("#cityBank_"+loadIndex).find("option:selected").text();
		//alert(bankName+" "+provinceName+" "+cityName);
		if(bankName && provinceName && cityName){
			var submitObj = {};
			submitObj.bankName = bankName;
			submitObj.provinceName = provinceName;
			submitObj.cityName = cityName;
			$("#branchBankId_"+loadIndex).load("${ctx}/getBranchBanksOptions.do?defaultBankNo="+defaultBankNo,submitObj,function (msg){
			});	
		} 
	} */
	function loadBranchBanks(loadIndex,defaultBankNo){
		var bankName = $("#bankId_"+loadIndex).find("option:selected").text();
		var provinceName = $("#regionBank_"+loadIndex).find("option:selected").text();
		var cityName = $("#cityBank_"+loadIndex).find("option:selected").text();
		//alert(bankName+" "+provinceName+" "+cityName);
		if(bankName && provinceName && cityName){
			var submitObj = {};
			submitObj.bankName = bankName;
			submitObj.provinceName = provinceName;
			submitObj.cityName = cityName;
			$("#branchBankId_"+loadIndex).load("${ctx}/getBranchBanksOptions_2.do?defaultBankNo="+defaultBankNo,submitObj,function (msg){
			});	
		} 
	}
	/*
	添加银行过滤选择 
	add by davis.guo at 2016-08-09
	*/
	function queryBanks(loadIndex,defaultBankNo){
		var bankName = $("#bankId_"+loadIndex).find("option:selected").text();
		var provinceName = $("#regionBank_"+loadIndex).find("option:selected").text();
		var cityName = $("#cityBank_"+loadIndex).find("option:selected").text();
		var bankKaihu = $("#bankKeywords_"+loadIndex).val();
		//alert(bankName+" "+provinceName+" "+cityName+" "+bankKaihu);
		if(bankName && provinceName && cityName){
			var submitObj = {};
			submitObj.bankName = bankName;
			submitObj.provinceName = provinceName;
			submitObj.cityName = cityName;
			submitObj.bankKaihu = bankKaihu;
			$("#branchBankId_"+loadIndex).load("${ctx}/queryBranchBanksOptions.do?defaultBankNo="+defaultBankNo,submitObj,function (msg){
			});	
		} 
	}
	
	function setBrahchBankName(index, obj){
		$("#bankName_"+index).val($("#branchBankId_"+index).find("option:selected").text());
		var bankName = $(obj).find("option:selected").text().trim() ;
		$(obj).next("input:hidden").val(bankName) ;
	}
	
	setTimeout(function(){
		<c:forEach items="${merchantList}" var="dto" varStatus="status">
			loadBranchBanks("${status.count}","${dto.branchBankId}");
		</c:forEach>
	},300);
	
	function submitPrevProcess(){
		$(":input[name=branchBankId]").change();
		return true;	
	}
	
	function setAcctName(){
		$(":input[name=acctName]").val($("#zhName").val());
	}
	
	function del(id){
		var pars = 'id=' + id;
		
		if(confirm('确认删除？删除后将采用PE算费策略配置')){
			$.ajax({
				type: "POST",
				url: "${ctx}/merchantRate.do?method=del",
				data: pars,
				success: function(result) {
					if(result){
						alert("删除成功！");
						$("#addLogDiv").dialog("close");  //关闭Dialog
						window.location.reload();  //重新查询所有信息
					}else{
						alert("删除失败！");
					};
				}
			});
		}
	}
	
	function updateFee(id,countryCode,organisation,transType,cardType,transMode,dealCode,localPayCode,chargeRate,fixedCharge,feeType
			,chargeRate1,chargeRate2,chargeRate3,fixedCharge1,fixedCharge2,fixedCharge3,
			monthAmountLevel,monthAmountLevel1,monthAmountLevel2,monthAmountLevel3
			,levelCurrencyCode,level1CurrencyCode,level2CurrencyCode,level3CurrencyCode,
			fixedCurrencyCode,fixed1CurrencyCode,fixed2CurrencyCode,fixed3CurrencyCode){
		resetForm();
		initData(false);
		$('#id').val(id);
		$("#saveOrUpdate").val("update");
		$("#organisation option").removeAttr("selected");
		$("#chkEur").removeAttr("checked");
		$("#chkNotEur").removeAttr("checked");
		//$("#countryCode").empty();
		var countryCodes = countryCode.split(",");
		var organisations = organisation.split(",");
		
		for(var i=0;i<organisations.length;i++){
			$("#organisation").find("option[value='"+organisations[i]+"']").attr("selected",true);
		}
		/* if(eur.sort().join(",")==countryCode){
			setOption2Select("countryCode",eur,true);
			$("#chkEur").attr("checked","checked");
		}
		if(notEur.join(",")==countryCode){
			setOption2Select("countryCode",notEur,true);
			$("#chkNotEur").attr("checked","checked");
		}
		if(all.join(",")==countryCode){
			setOption2Select("countryCode",all,true);
			$("#chkEur").attr("checked","checked");
			$("#chkNotEur").attr("checked","checked");
		}
		$('#countryCode').val(countryCode); */
		//以上代码逻辑有问题，修改如下modified by davis.guo 2016-08-04
		var v_eur = eur.sort().join(",");
		if(countryCode.indexOf(v_eur) != -1){
			$("#chkEur").attr("checked","checked");
		}
		var v_notEur = notEur.sort().join(",");
		if(countryCode.indexOf(v_notEur) != -1){
			$("#chkNotEur").attr("checked","checked");
		}
		var v_local = local.sort().join(",");
		if(countryCode.indexOf(v_local) != -1){
			$("#local").attr("checked","checked");
		}
		//console.log(eur.sort().join(","));
		//console.log(countryCode);
		checkLocalLockSet();//设置countryCode
		//modified end
		$('#transType').val(transType);
		$('#cardType').val(cardType);
		$('#transMode').val(transMode);
		$('#dealCode').val(dealCode);
		$('#localPayCode').val(localPayCode);
		$('#chargeRate').val(chargeRate);
		$('#fixedCharge').val(fixedCharge);
		$('#feeType').val(feeType);
		$("#chargeRate1").val(chargeRate1);
		$("#chargeRate2").val(chargeRate2);
		$("#chargeRate3").val(chargeRate3);
		$("#fixedCharge1").val(fixedCharge1);
		$("#fixedCharge2").val(fixedCharge2);
		$("#fixedCharge3").val(fixedCharge3);
		$("#monthAmountLevel").val(monthAmountLevel);
		$("#monthAmountLevel1").val(monthAmountLevel1);
		$("#monthAmountLevel2").val(monthAmountLevel2);
		$("#monthAmountLevel3").val(monthAmountLevel3);
		$("#levelCurrencyCode").val(levelCurrencyCode);
		$("#level1CurrencyCode").val(level1CurrencyCode);
		$("#level2CurrencyCode").val(level2CurrencyCode);
		$("#level3CurrencyCode").val(level3CurrencyCode);
		$("#fixedCurrencyCode").val(fixedCurrencyCode);
		$("#fixed1CurrencyCode").val(fixed1CurrencyCode);
		$("#fixed2CurrencyCode").val(fixed2CurrencyCode);
		$("#fixed3CurrencyCode").val(fixed3CurrencyCode);
		
		$('#addLogDiv').dialog({ 
			position:["center","center"],
			width:650,
			modal:true, 	
			title:'修改商户费率', 
			overlay: {opacity: 0.5, background: "black" ,overflow:'auto' }
		});
	}
	
	function submitLastProcess(){
		$("#bankName").val($("#branchBankId").find("option:selected").text());
		$('#bankId').val(getBankIdByBankName($("#bigBankName").val()));
		var bankNameS = $("#bankName").val();
		if(bankNameS.length==0){
			alert("开户不能为空");
			return false;	
		}
		
		return true;
	}
	
	var bigBankIds = [];
	var bigBankNames = [];
	var clickCount = 0;
	$().ready(function() {
		<c:forEach items="${bankList}" var="bank" varStatus = "status">
			bigBankIds[${status.index}] = '${bank.bankId}';
			bigBankNames[${status.index}] = '${bank.bankName}';
		</c:forEach>
		$("#bigBankName").autocomplete(bigBankNames, {
			selectFirst:true,
			matchContains:true,
			minChars:0,
			max:99,
			mustMatch:true
			});
	
	});
	
	function getBankIdByBankName(bankName){
		for(i=0;i<bigBankNames.length;i++){
			if(bankName==bigBankNames[i]){
				return bigBankIds[i];
			}
		}
	}
	
	function addOrUpdate() {
		
		var firstPercent = $('#firstPercent').val();
		var firstCost = $('#firstCost').val();
		var secondPercent = $('#secondPercent').val();
		var thirdPercent = $('#thirdPercent').val();
		var secondCost = $('#secondCost').val();
		var fourPercent = $('#fourPercent').val();
		var fourCost = $('#fourCost').val();
		
		if(!isPirce(firstPercent)){
			alert('请输入合法的百分比');
			$('#firstPercent').focus();
			return;
		}
		if(!isPirce(firstCost)){
			alert('请输入合法的金额');
			$('#firstCost').focus();
			return;
		}
		if(!isPirce(secondPercent)){
			alert('请输入合法的百分比');
			$('#secondPercent').focus();
			return;
		}
		if(secondPercent<firstPercent){
			alert('第二区间起始百分比不能小于第一区间百分比');
			$('#secondPercent').focus();
			return;
		}
		if(!isPirce(thirdPercent)){
			alert('请输入合法的百分比');
			$('#thirdPercent').focus();
			return;
		}
		if(thirdPercent<secondPercent){
			alert('第二区间结束百分比不能小于开始百分比');
			$('#thirdPercent').focus();
			return;
		}
		if(!isPirce(secondCost)){
			alert('请输入合法的百分比');
			$('#secondCost').focus();
			return;
		}
		if(!isPirce(fourPercent)){
			alert('请输入合法的百分比');
			$('#fourPercent').focus();
			return;
		}
		if(fourPercent<thirdPercent){
			alert('第三区间百分比不能小于第二区间结束百分比');
			$('#fourPercent').focus();
			return;
		}
		if(!isPirce(fourCost)){
			alert('请输入合法的金额');
			$('#fourCost').focus();
			return;
		}
		
		var pars = $("#merchantFormBean").serialize() ;
		$.ajax({
			type: "POST",
			url: "${ctx}/chargeBackConfig.do?method=addOrUpdate",
			data: pars,
			success: function(result) {
				if(result){
					alert('操作成功！');
				}else{
					alert('操作成失败！');
				}
			}
		});
	}
	
	function queryChargeBackConfig() {
		
		var pars = 'memberCode=' + '${merchantDto.memberCode}';
		$.ajax({
			type: "POST",
			url: "${ctx}/chargeBackConfig.do?method=getChargebackConfig",
			data: pars,
			success: function(result) {
				var dataObj = eval("("+result+")");//转换为json对象
				if(dataObj == null){
					return;
				}
				$('#firstPercent').val(dataObj.firstPercent);
				$('#firstCost').val(dataObj.firstCost);
				$('#secondPercent').val(dataObj.secondPercent);
				$('#thirdPercent').val(dataObj.thirdPercent);
				$('#secondCost').val(dataObj.secondCost);
				$('#fourPercent').val(dataObj.fourPercent);
				$('#fourCost').val(dataObj.fourCost);
				$('#id').val(dataObj.id);
			}
		});
	} 
	//处理欧盟和非欧盟
	function eurCheck(){
		$("#countryCode").empty();
		/* if($("#chkEur").attr("checked") && $("#chkNotEur").attr("checked") && $("#local").attr("checked")){
			setOption2Select("countryCode",all,true);
		}else if($("#chkEur").attr("checked")){
			setOption2Select("countryCode",eur,true);
		}else if($("#chkNotEur").attr("checked")){
			setOption2Select("countryCode",notEur,true);
		}else if(!$("#chkEur").attr("checked") && !$("#chkNotEur").attr("checked") && !$("#local").attr("checked")){
			setOption2Select("countryCode",all,false);
		}else if($("#local").attr("checked")){
			setOption2Select("countryCode",local,false);
		} */
		//上面写法逻辑有问题。modified by davis.guo at 2016-08-02
		if($("#chkEur").attr("checked")){//欧盟
			setOption2Select("countryCode",eur,true);
		}
		if($("#chkNotEur").attr("checked")){//非欧盟
			setOption2Select("countryCode",notEur,true);
		}
		if($("#local").attr("checked")){//本地
			setOption2Select("countryCode",local,true);
		}
		if(!$("#chkEur").attr("checked") && !$("#chkNotEur").attr("checked") && !$("#local").attr("checked")){//全不选时，默认全选
			setOption2Select("countryCode",all,false);
		}
		
	}
	function setOption2Select(id,data,selected){
		$.each(data,function(index,item){
			if(selected){
				$("#"+id).append("<option selected='selected' value='"+item+"'>"+item+"</option>");
			}else{			
				$("#"+id).append("<option value='"+item+"'>"+item+"</option>");
			}
		});
	}
	
	function resetForm(){
		$("#countryCode").empty();
		setOption2Select("countryCode",all,false);
		$("#chkEur").removeAttr("checked");
		$("#chkNotEur").removeAttr("checked");
		$("#organisation option").removeAttr("selected");
		$('#transType').val("0");
		$('#cardType').val("0");
		$('#transMode').val("0");
		$('#feeType').val("2");
		$('#dealCode').val("202");
		$('#chargeRate').val("");
		$("#chargeRate1").val("");
		$("#chargeRate2").val("");
		$("#chargeRate3").val("");
		$('#fixedCharge').val("");
		$("#fixedCharge1").val("");
		$("#fixedCharge2").val("");
		$("#fixedCharge3").val("");
		$("#monthAmountLevel").val("");
		$("#monthAmountLevel1").val("");
		$("#monthAmountLevel2").val("");
		$("#monthAmountLevel3").val("");
		$("#levelCurrencyCode").val("USD");
		$("#level1CurrencyCode").val("USD");
		$("#level2CurrencyCode").val("USD");
		$("#level3CurrencyCode").val("USD");
		$("#fixedCurrencyCode").val("USD");
		$("#fixed1CurrencyCode").val("USD");
		$("#fixed2CurrencyCode").val("USD");
		$("#fixed3CurrencyCode").val("USD");
	}
	function addDynamicTr(obj){
		//var tr = "<tr class='trForContent1'><td colspan='6' class='border_top_right4'>111</td><td class='border_top_right4' colspan='2'>333</td></tr>" ;
		var $sourceTr = $(obj).parent("td").parent("tr").siblings("tr.acctWithDraw:last") ;
		if($sourceTr.size() == 0){
			$sourceTr = $(obj).parent("td").parent("tr") ;
		}
		//源行数据完整性校验
		var goFlag = dyTrCheck() ;
		if(!goFlag) return false ;
		var tr = "<tr class='trForContent1 acctWithDraw'>"
			+"<td class='border_top_right4' align='right'>提现账户：<font color='red'>*</font></td>"
			+"<td class='border_top_right4'>"
			+"	<select name='acctCode'>"	
			+"		<option value=''>--请选择--</option>"
					+"<c:forEach items='${accts }' var='acct'>"
						+"<option value='${acct.acctCode }'>${acct.acctName}</option>"
					+"</c:forEach>"
				+"</select>"
				+"<font color='red'>*</font>"
			+"</td>"
			+"<td class='border_top_right4' align='right'>提现手续费币种：<font color='red'>*</font></td>"
			+"<td class='border_top_right4'>"
				+"<select name='acctWithDrawCurrencyCode'>"
					+"<option value=''>--请选择--</option>"
					+"<c:forEach items='${currencyCodeEnum}' var='item'>"
						+"<option value='${item.code }'>${item.code }</option>"
					+"</c:forEach>"
				+"</select>"
				+"<font color='red'>*</font>"
			+"</td>"
			+"<td class='border_top_right4' align='right'>提现手续费金额：<font color='red'>*</font></td>"
			+"<td class='border_top_right4'>"
				+"<input type='text' name='acctWithDrawFee' value='' style='width: 100px;' /><font color='red'>*</font>"
			+"</td>"
			+"<td class='border_top_right4' colspan='2'>"
				+"<input type='button' value='删除' onclick='javascript:removeDynamicTr(this);'>"
			+"</td>"
		+"</tr>";
		$(tr).insertAfter($sourceTr) ;
	}
	//删除动态行
	removeDynamicTr = function(obj, acctCode){
		var confirm = window.confirm("确认删除吗？") ;
		if(confirm){
			if(null != acctCode && "" != acctCode){
				//$(obj).parent("td").parent("tr").remove() ;
				var _acctCode = acctCode ;
				$.ajax({
					type:'POST',
					url:'${ctx}/merchantRate.do?method=deleteWithdrawAcctFee',
					data:'acctCode=' + _acctCode,
					dataType:'json',
					success:function(data){
						$(obj).parent("td").parent("tr").remove() ;
					},
					error:function(){
						alert("删除失败！") ;
					}
				}) ;
			}else{
				$(obj).parent("td").parent("tr").remove() ;	
			}
		}
			
	}
	//动态行校验(动态行添加)
	function dyTrCheck(){
		var $sourceTr = $(".acctWithDraw:last") ;
		var acctCode = $sourceTr.find("td:eq(1)").find("select").val() ;
		if("" == acctCode || null == acctCode){
			alert("请选择提现账户！") ;
			return false ;
		}
		var acctWithDrawCurrencyCode = $sourceTr.find("td:eq(3)").find("select").val() ;
		if("" == acctWithDrawCurrencyCode || null == acctWithDrawCurrencyCode){
			alert("请选择提现手续费币种！") ;
			return false ;
		}
		var acctWithDrawFee = $sourceTr.find("td:eq(5)").find("input").val().trim() ;
		if("" == acctWithDrawFee || null == acctWithDrawFee){
			alert("请设置提现手续费金额！") ;
			return false ;
		}
		if(!isPirce(acctWithDrawFee)){
			alert("请输入合法的提现手续费金额！") ;
			return false ;
		}
		return true ;
	}
	//动态行数据校验（保存）
	function acctWithDrawCheck(){
		var $trs = $(".acctWithDraw") ;
		var trSize = $trs.size() ;
		var flag = true ;
		if(trSize > 0){
			$trs.each(function(n, o){
				var acctCode = $(o).find("td:eq(1)").find("select").val() ;
				if("" == acctCode || null == acctCode){
					alert("请选择提现账户！") ;
					flag = false ;
					return false ;
				}
				var acctWithDrawCurrencyCode = $(o).find("td:eq(3)").find("select").val() ;
				if("" == acctWithDrawCurrencyCode || null == acctWithDrawCurrencyCode){
					alert("请选择提现手续费币种！")
					flag = false ;
					return false ;
				}
				var acctWithDrawFee = $(o).find("td:eq(5)").find("input").val().trim() ;
				if("" == acctWithDrawFee || null == acctWithDrawFee){
					alert("请输入提现手续费！")
					flag = false ;
					return false ;
				}
				if(!isPirce(acctWithDrawFee)){
					alert("请输入合法的提现手续费！");
					flag = false ;
					return false ;
				}
			}) ;
		}
		return flag ;
	}
	//组装动态提现数据
	function constructWithDrawData(){
		var $trs = $(".acctWithDraw") ;
		var trSize = $trs.size() ;
		var dataArr = new Array() ;
		if(trSize > 0){
			$trs.each(function(n, o){
				var data = {} ;
				var acctCode = $(o).find("td:eq(1)").find("select").val() ;		
				data['acctCode'] = acctCode ;
				var acctWithDrawCurrencyCode = $(o).find("td:eq(3)").find("select").val() ;
				data['acctWithDrawCurrencyCode'] = acctWithDrawCurrencyCode ;
				var acctWithDrawFee = $(o).find("td:eq(5)").find("input").val().trim() ;
				data['acctWithDrawFee'] = acctWithDrawFee ;
				dataArr.push(data) ;
			}) ;
		}
		return dataArr ;
	}
	
	function configRules(){
		removeAttr();	
		$('#configRulesDiv').dialog({ 
			position:["center","center"],
			width:795,
			height:397,
			modal:true, 	
			title:'拒付罚款规则', 
			overlay: {opacity: 0.5, background: "black" ,overflow:'auto' }
		});
	}
	function back(){
		$('#configRulesDiv').dialog("close");
	}
	function validateConfig(){
		var cardOrg=$("#cardOrg").val();
		if(cardOrg == ''){
			alert("请选择卡组织！！！");
			return ;
		}
		var rules=$("#rules").val();
		if(rules == ''){
			alert("请选择拒付罚款规则！！！");
			return ;
		}
		if(rules == '1'){ //规则1
		 var content1=$("#content1").val();
		 if(content1==''){
		 	alert("请完整的填写拒付规则！！！");
		 	return ;
		 }
		}else
		if(rules == '2'){ //规则2
			 var content2=$("#content2").val();			
			 var content3=$("#content3").val();			
			 var content4=$("#content4").val();			
			 var content5=$("#content5").val();			
			 var content6=$("#content6").val();			
			 var content7=$("#content7").val();			
			 var content8=$("#content8").val();			
			if(content2 == '' ||
			   content3 == '' ||
			   content4 == '' ||
			   content5 == '' ||
			   content6 == '' ||
			   content7 == '' ||
			   content8 == ''){
				alert("请完整的填写拒付规则！！！");
			 	return ;
			}
			 if(content4>=content5){
					alert("请填写正确的拒付规则！！！");
					return ;
				} 
		}else//规则3
		if(rules == '3'){
			 var content9=$("#content9").val();			
			 var content10=$("#content10").val();			
			 var content11=$("#content11").val();			
			 var content12=$("#content12").val();			
			 var content13=$("#content13").val();			
			 var content14=$("#content14").val();			
			 var content15=$("#content15").val();	
			 if(content9 == '' ||
			    content10 == '' ||
			    content11 == '' ||
			    content12 == '' ||
			    content13 == '' ||
			    content14 == '' ||
			    content15 == ''){
				alert("请完整的填写拒付规则！！！");
				return ;
			} 
			 if(content11>=content12){
				alert("请填写正确的拒付规则！！！");
				return ;
			} 
		}else
			if(rules == '4'){//规则4
				 var content16=$("#content16").val();			
				 var content17=$("#content17").val();			
				 var content18=$("#content18").val();			
				 var content19=$("#content19").val();			
				 var content20=$("#content20").val();			
				 var content21=$("#content21").val();			
				 var content22=$("#content22").val();	
				 if(content16 == '' ||
					content17 == '' ||
					content18 == '' ||
					content19 == '' ||
					content20 == '' ||
					content21 == '' ||
					content22 == ''){
					alert("请完整的填写拒付规则！！！");
					return ;
				} 
				if(content18>=content19){
					alert("请填写正确的拒付规则！！！");
					return ;
				} 
		}
		
		var pars = $("#configForm").serialize();
		var id=$("#bouncedId").val();
		if(id == ''){
			$.ajax({
				type: "POST",
				url: "${ctx}/bouncedFineConfig.do?method=addConfig",
				data: pars,
				success: function(result) {
					if(result=='1000'){
						alert("该拒付罚款已配置！！！");
					}else if(result){
						alert("操作成功！");
						$("#configRulesDiv").dialog("close");  //关闭Dialog
						window.location.reload();  //重新查询所有信息
					}else{
						alert("操作失败！");
					};
				}
			});
			return;
		}else{
			var rules =$("#rules").val();
			var cardOrg=$("#cardOrg").val();
			$.ajax({
				type: "POST",
				url: "${ctx}/bouncedFineConfig.do?method=updateConfig",
				data: pars+"&rules="+rules+"&cardOrg="+cardOrg,
				success: function(result) {
			        if(result){
						alert("操作成功！");
						$("#configRulesDiv").dialog("close");  //关闭Dialog
						window.location.reload();  //重新查询所有信息
					}else{
						alert("操作失败！");
					};
				}
			});
		}
	}
	function selectRule(){
		var rules=$("#rules").val();
		if(rules == ''){
			$("#rules1").attr("style","display: none");
			$("#rules2").attr("style","display: none");
			$("#rules3").attr("style","display: none");
			$("#rules4").attr("style","display: none ");
		}else
		if(rules == '1'){
			$("#rules1").removeAttr("style");
			$("#rules2").attr("style","display: none");
			$("#rules3").attr("style","display: none");
			$("#rules4").attr("style","display: none ");
		}else
		if(rules == '2'){
			$("#rules2").removeAttr("style");
			$("#rules1").attr("style","display: none");
			$("#rules3").attr("style","display: none");
			$("#rules4").attr("style","display: none ");
		}else
		if(rules == '3'){
			$("#rules3").removeAttr("style");
			$("#rules1").attr("style","display: none");
			$("#rules4").attr("style","display: none ");
			$("#rules2").attr("style","display: none");
		}else
		if(rules == '4'){
			$("#rules4").removeAttr("style");
			$("#rules1").attr("style","display: none");
			$("#rules3").attr("style","display: none ");
			$("#rules2").attr("style","display: none");
		}
	}
		
		function checkNum(obj) {
			//检查是否是非数字值
			if (isNaN(obj.value)) {
				obj.value = "";
			}
		}
	function decimals(obj){
		var markup=obj.value;//获取markup
		if(markup && markup.indexOf(".")==-1){
			var index;
			var zeroCount=0; 
			for(var i=0;i<markup.length;i++){
				if(markup.charAt(i)!="0"){
					index=i;
					break;
				}else{
					zeroCount++;	
				}
			}
			if(zeroCount==markup.length){
				$(obj).attr("value","");
			}else if(index){
				$(obj).attr("value",markup.substr(index));
			}
		}else if(markup && markup.indexOf(".")>-1){
			var left=markup.split(".")[0];
			var right=markup.split(".")[1];
			var zeroCount=0;  
			for(var i=0;i<left.length;i++){
				if(left.charAt(i)=="0"){
					zeroCount++;
				}else{
					break;
				}
			}
			if(zeroCount==left.length){
				left=left.substr(zeroCount-1);
				markup=left+"."+right;
				$(obj).attr("value",left+"."+right);
			}else if(zeroCount>0){
				left=left.substr(zeroCount);
				markup=left+"."+right;
				$(obj).attr("value",left+"."+right);
			}
			if(right.length>2){
				markup=markup.substring(0,markup.indexOf(".")+3);
				$(obj).attr("value",markup);
			}
		}
	}

	function deleteBouncedConf(obj){
		if(!confirm("确认删除？")) {
			return false;
		}
		$.ajax({
			type: "POST",
			url: "${ctx}/bouncedFineConfig.do?method=deleteBouncedConf",
			data: "id="+obj,
			success: function(result) {
				 if(result){
					alert("操作成功！");
					window.location.reload();  //重新查询所有信息
				}else{
					alert("操作失败！");
				};
			}
		});
	}

	function removeAttr(){
		$("#cardOrg option").each(function(){
				$(this).removeAttr("selected");
				$("#cardOrg").removeAttr("disabled");
		})
		$("#rules option").each(function(){
				$(this).removeAttr("selected");
				$("#rules").removeAttr("disabled");
		})
		$("#content1").removeAttr("value");				
		$("#content2").removeAttr("value");				
		$("#content3").removeAttr("value");				
		$("#content4").removeAttr("value");				
		$("#content5").removeAttr("value");				
		$("#content6").removeAttr("value");				
		$("#content7").removeAttr("value");				
		$("#content8").removeAttr("value");				
		$("#content9").removeAttr("value");				
		$("#content10").removeAttr("value");				
		$("#content11").removeAttr("value");				
		$("#content12").removeAttr("value");				
		$("#content13").removeAttr("value");				
		$("#content14").removeAttr("value");				
		$("#content15").removeAttr("value");				
		$("#content16").removeAttr("value");				
		$("#content17").removeAttr("value");				
		$("#content18").removeAttr("value");				
		$("#content19").removeAttr("value");				
		$("#content20").removeAttr("value");				
		$("#content21").removeAttr("value");				
		$("#content22").removeAttr("value");				
	}
	
	
	function updateBouncedConf(cardOrg,ruleNo,countRate1,fineAmount1,countRate2,fineAmount2,countRate3,fineAmount3,id){
			removeAttr();	
			$("#cardOrg option").each(function(){
				if($(this).val() == cardOrg){
					$(this).attr("selected","selected");
					$("#cardOrg").attr("disabled","disabled");
				};
			})
			$("#rules option").each(function(){
				if($(this).val() == ruleNo){
					$(this).attr("selected","selected");
				    $("#rules").attr("disabled","disabled");
				};
			})
			if(ruleNo == '1'){
			$("#content1").attr("value",countRate1/1000);				
			}else if(ruleNo == '2'){
			$("#content2").attr("value",countRate1);				
			$("#content3").attr("value",fineAmount1);				
			$("#content4").attr("value",countRate1);				
			$("#content5").attr("value",countRate2);				
			$("#content6").attr("value",fineAmount2);				
			$("#content7").attr("value",countRate3);				
			$("#content8").attr("value",fineAmount3);				
			}else if(ruleNo == '3'){
				$("#content9").attr("value",countRate1*100);				
				$("#content10").attr("value",fineAmount1);				
				$("#content11").attr("value",countRate1*100);				
				$("#content12").attr("value",countRate2*100);				
				$("#content13").attr("value",fineAmount2);				
				$("#content14").attr("value",countRate3*100);				
				$("#content15").attr("value",fineAmount3);	
			}else if(ruleNo == '4'){
				$("#content16").attr("value",countRate1*100);				
				$("#content17").attr("value",fineAmount1);				
				$("#content18").attr("value",countRate1*100);				
				$("#content19").attr("value",countRate2*100);				
				$("#content20").attr("value",fineAmount2);				
				$("#content21").attr("value",countRate3*100);				
				$("#content22").attr("value",fineAmount3);	
			}
			$("#bouncedId").attr("value",id);
			selectRule(); 
		$('#configRulesDiv').dialog({ 
			position:["center","center"],
			width:795,
			height:397,
			modal:true, 	
			title:'拒付罚款规则', 
			overlay: {opacity: 0.5, background: "black" ,overflow:'auto' }
		});
	}
	
	
	function setContent4(){
		var content2=$("#content2").val();
		$("#content4").val(content2);
	}
	function setContent7(){
		var content5=$("#content5").val();
		$("#content7").val(content5);
	}
	function setContent11(){
		var content9=$("#content9").val();
		$("#content11").val(content9);
	}
	function setContent14(){
		var content12=$("#content12").val();
		$("#content14").val(content12);
	}
	function setContent18(){
		var content16=$("#content16").val();
		$("#content18").val(content16);
	}
	function setContent21(){
		var content19=$("#content19").val();
		$("#content21").val(content19);
	}
	
</script>
</head>

<body>

<!-- <table width="25%" border="0" cellspacing="0" cellpadding="0"
	align="center" height="21" style="">
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
	<tr>
		<td height="18">
		<div align="center"><font class="titletext">商 户 清 算 信 息 </font></div>
		</td>
	</tr>
	<tr>
		<td height="1" bgcolor="#000000"></td>
	</tr>
</table> -->
<h2 class="panel_title">商户清算信息</h2>
<c:if test="${sign!=null}">
<br><br>
	<center>
		<font color="red">${sign}</font>
	</center>
<br><br>
</c:if>

<form id="merchantFormBean" name="merchantFormBean" action="enterpriseViewEdit.do?method=addConfig" method="post" onsubmit="return (validator(this)&&submitPrevProcess())">
<input type="hidden" id="memberCode" name="memberCode" value="${merchantDto.memberCode}">
<input type="hidden" id="merchantCode" name="merchantCode" value="${merchantDto.merchantCode}">
<table class="border_all2" width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
		
		<tr class="trForContent2"><td class="border_top_right4" align="left" colspan="9">商户结算信息：</td></tr>
		
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >结算周期：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="accountMode" name="accountMode" value="${merchantDto.settlementCycle}" valid="required" errmsg="结算方式不能为空!" style="width: 100px;"/>日<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >结算比例：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="percentage" name="percentage" value="${merchantDto.percentage}" valid="required" errmsg="结算比例不能为空!" style="width: 100px;"/>百分比<font color="red">*</font>
			</td>
			<!-- delete 第二个结算周期  delin.dong 2016年4月21日19:55:22 -->
			<%-- <td class="border_top_right4" align="right" >结算周期：</td>
			<td class="border_top_right4" align="left" colspan="9" >
				<input type="text" id="secondSettlementCycle" name="secondSettlementCycle" value="${merchantDto.percentage}" errmsg="结算比例不能为空!" style="width: 100px;"/>日
			</td> --%>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >是否垫资：</td>
			<td class="border_top_right4" align="left">
				<select id="allowAdvanceMoney" name="allowAdvanceMoney" size="1" valid="required" errmsg="是否垫资不能为空!" style="width: 100px;">
					<option value="" >---请选择---</option>						
					<option value="0" <c:if test="${0 == merchantDto.allowAdvanceMoney || empty merchantDto.allowAdvanceMoney}"> selected="selected" </c:if>>否</option>
					<option value="1"<c:if test="${1 == merchantDto.allowAdvanceMoney}"> selected="selected" </c:if>>是</option>											
				</select>
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >保证金比例：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="assurePercentage" name="assurePercentage" value="${merchantDto.assurePercentage}" errmsg="保证金比例不能为空!" style="width: 100px;"/>百分比<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >保证金结算周期：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="assureSettlementCycle" name="assureSettlementCycle" value="${merchantDto.assureSettlementCycle}" errmsg="保证金结算周期不能为空!" style="width: 100px;"/><font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >退款手续费币种：</td>
			<td class="border_top_right4" align="left" colspan="9" >
			<select style="width: 120px; height: 20px;" id="refundFeeCurrency" name="refundFeeCurrency" valid="required"  errmsg="退款手续费币种不能为空!">
			</select> 
			<input  id="refundCurrency"  name="refundCurrency" type="hidden" value="${merchantDto.refundFeeCurrency}">
			<font color="red">*</font>
			</td>
		</tr>
		<tr class="trForContent1">
			<%-- <td class="border_top_right4" align="right" >提现手续费：</td>
			<td class="border_top_right4" align="left">
				<input type="text" name="withdrawFee" id="withdrawFee" value="${merchantDto.withdrawFee/1000 }" style="width: 100px;" />&nbsp;&nbsp;
				<select name="withdrawFeeCurrency">
					<c:forEach items="${currencyCodeEnum}" var="item">
						<option value="${item.code }" <c:if test="${item.code == merchantDto.withdrawFeeCurrency }">selected="selected"</c:if> >${item.code }</option>
					</c:forEach>
				</select>
				<!-- 美元 --><font color="red">*</font>
			</td> --%>
			<td class="border_top_right4" align="right" >拒付扣款币种：</td>
			<td class="border_top_right4" align="left" >
					<select name="chargeBackFeeCurCode" id="chargeBackFeeCurCode">
						<c:forEach items="${curCodes}" var="map" >
								<option value="${map.currencyCode}" <c:if test="${map.currencyCode == merchantDto.chargeBackFeeCurCode}">selected="selected"</c:if>>${map.currencyCode}</option>
						</c:forEach>
					</select>
					<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >风控处理费：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="riskFee" name="riskFee" value="${merchantDto.riskFee}" errmsg="保证金结算周期不能为空!" style="width: 100px;"/>
				<!-- 添加“风控处理费币种” add by davis.guo at 2016-08-10-->
				<select name="riskFeeCurrency">
					<c:forEach items="${currencyCodeEnum}" var="item">
						<option value="${item.code }" <c:if test="${item.code == merchantDto.riskFeeCurrency }">selected="selected"</c:if>>${item.code }</option>
					</c:forEach>
				</select>
				<!-- 美元 -->
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >退款手续费：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" id="refundFee" name="refundFee" value="${merchantDto.refundFee}"  valid="required"  errmsg="退款手续费不能为空!" style="width: 100px;"/><font color="red">*</font>
			</td>
			<td colspan="2" class="border_top_right4">&nbsp;</td>
		</tr>
		<tr class="trForContent1">
			<td class="border_top_right4" align="right" >批量出款手续费：</td>
			<td class="border_top_right4" align="left">
				<input type="text" name="batchpayFee" id="batchpayFee" value="${merchantDto.batchpayFee/1000}" style="width: 100px;" />&nbsp;&nbsp;
				<select name="batchpayFeeCurrency">
					<c:forEach items="${currencyCodeEnum}" var="item">
						<option value="${item.code }" <c:if test="${item.code == merchantDto.batchpayFeeCurrency }">selected="selected"</c:if>>${item.code }</option>
					</c:forEach>
				</select>
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="right" >
				<c:if test="${merchantDto.refundFixedFeeConf == '1' }">
					 <input type="checkbox" name="refundFixedFeeConf" id="refundFixedFeeConf" checked />		
				</c:if>
				<c:if test="${merchantDto.refundFixedFeeConf == '0' }">
				 <input type="checkbox" name="refundFixedFeeConf"  id="refundFixedFeeConf" />		
				</c:if>
			</td>
			<td class="border_top_right4" align="left" >退款时固定手续费配置退回</td>

			<td class="border_top_right4" align="right"  >
				<c:if test="${merchantDto.refundPerFeeConf == '1' }">
				
					 <input type="checkbox" name="refundPerFeeConf" id="refundPerFeeConf" checked/>		
				</c:if>
				<c:if test="${merchantDto.refundPerFeeConf == '0' }">
					 <input type="checkbox" name="refundPerFeeConf" id="refundPerFeeConf" />		
				</c:if>
			</td>
			<td class="border_top_right4" align="left" >退款时百分比手续费配置退回</td>			
		</tr>
		
		<tr class="trForContent2">
			<td colspan="8" class="border_top_right4" align="left">提现手续费信息：<font color="red">*</font></td>
		</tr>
	<%-- 	<c:if test="${empty  }"></c:if> --%>
		<c:choose>
			<c:when test="${empty feeAccts }">
				<tr class="trForContent1 acctWithDraw">
					<td class="border_top_right4" align="right">提现账户：<font color="red">*</font></td>
					<td class="border_top_right4">
						<select name="acctCode">
							<option value="">--请选择--</option>
							<c:forEach items="${accts }" var="acct">
								<option value="${acct.acctCode }">${acct.acctName}</option>
							</c:forEach>
						</select>
						<font color="red">*</font>
					</td>
					<td class="border_top_right4" align="right">提现手续费币种：<font color="red">*</font></td></td>
					<td class="border_top_right4">
						<select name="acctWithDrawCurrencyCode">
							<option value="">--请选择--</option>
							<c:forEach items="${currencyCodeEnum}" var="item">
								<option value="${item.code }">${item.code }</option>
							</c:forEach>
						</select>
						<font color="red">*</font>
					</td>
					<td class="border_top_right4" align="right">提现手续费金额：</td>
					<td class="border_top_right4">
						<input type="text" name="acctWithDrawFee" value="" style="width: 100px;" /><font color="red">*</font>
					</td>
					<td class="border_top_right4" colspan="2">
						<input type="button" value="新增提现手续费配置" onclick="javascript:addDynamicTr(this);">
					</td>
				</tr>
			</c:when>
			<c:when test="${!empty feeAccts }">
				<c:forEach items="${feeAccts }" var="feeAcct" varStatus="status">
					<tr class="trForContent1 acctWithDraw" >
						<td class="border_top_right4" align="right">提现账户：<font color="red">*</font></td>
						<td class="border_top_right4">
							<select name="acctCode">
								<option value="">--请选择--</option> 
								<c:forEach items="${accts }" var="acct">
									<option value="${acct.acctCode }" <c:if test="${feeAcct.acctCode == acct.acctCode }">selected="selected"</c:if> >${acct.acctName}</option>
								</c:forEach>
							</select>
							<font color="red">*</font>
						</td>
						<td class="border_top_right4" align="right">提现手续费币种：<font color="red">*</font></td></td>
						<td class="border_top_right4">
							<select name="acctWithDrawCurrencyCode">
								<option value="">--请选择--</option>
								<c:forEach items="${currencyCodeEnum}" var="item">
									<option value="${item.code }" <c:if test="${feeAcct.acctWithDrawCurrencyCode == item.code }">selected="selected"</c:if> >${item.code }</option>
								</c:forEach>
							</select>
							<font color="red">*</font>
						</td>
						<td class="border_top_right4" align="right">提现手续费金额：</td>
						<td class="border_top_right4">
							<input type="text" name="acctWithDrawFee" value="${feeAcct.acctWithDrawFee/1000 }" style="width: 100px;" /><font color="red">*</font>
						</td>
						<td class="border_top_right4" colspan="2">
							<c:choose>
								<c:when test="${status.index == 0 }">
									<input type="button" value="新增提现手续费配置" onclick="javascript:addDynamicTr(this);">
								</c:when>
								<c:otherwise>
									<input type='button' value='删除' onclick='javascript:removeDynamicTr(this, "${feeAcct.acctCode}");'>
								</c:otherwise>
							</c:choose>
						</td>
					</tr>
				</c:forEach>
			</c:when>
		</c:choose>
		<tr class="trForContent2"><td class="border_top_right4" align="left" colspan="9">拒付罚款规则配置：&nbsp;&nbsp;&nbsp;<a href="#" onclick="configRules();"><font color="blue">新增规则</font></a></td></tr>
		<tr class="trForContent1">
			<td  align="left" class="border_top_right4" colspan="9" >
				<table>
					<tr>
						<th  class="border_top_right4" style="width: 200px;">卡组织</th>
						<th  class="border_top_right4" style="width: 200px;">配置规则</th>
						<th  class="border_top_right4" style="width: 200px;">规则详情</th>
						<th  class="border_top_right4" style="width: 200px;">操  作</th>
					</tr>
					<c:forEach items="${bouncedConfigs}" var="map">
					<tr>
						<td  class="border_top_right4" style="width: 200px;" align="center">
								${map.cardOrg}
						</td>
						<td  class="border_top_right4" style="width: 200px;"  align="center">
							<c:if test="${map.ruleNo=='1' }">
							规则1
							</c:if>
							<c:if test="${map.ruleNo=='2' }">
							规则2
							</c:if>
							<c:if test="${map.ruleNo=='3' }">
							规则3
							</c:if>
							<c:if test="${map.ruleNo=='4' }">
							规则4
							</c:if>
						
						</td>
						<td  class="border_top_right4" style="width: 650px;"  align="center">
						  <c:if test="${map.ruleNo=='1' }">
							收取${map.countRate1/1000}美元/笔
							</c:if>
							<c:if test="${map.ruleNo=='2' }">
							小于${map.countRate1}笔  收取  ${map.fineAmount1} 美元/笔;
							大于等于${map.countRate1}笔 小于${map.countRate2}笔 收取 ${map.fineAmount2} 美元/笔;
							大于等于${map.countRate3}笔  收取${map.fineAmount3} 美元/笔;
							</c:if>
							<c:if test="${map.ruleNo=='3' }">
							小于${map.countRate1*100}% 收取  ${map.fineAmount1} 美元/笔;
							大于等于${map.countRate1*100}% 小于${map.countRate2*100}% 收取 ${map.fineAmount2} 美元/笔;
							大于等于${map.countRate3*100}%  收取${map.fineAmount3} 美元/笔;
							</c:if>
							<c:if test="${map.ruleNo=='4' }">
							小于${map.countRate1*100}% 收取  ${map.fineAmount1} 美元/笔;
							大于等于${map.countRate1*100}% 小于${map.countRate2*100}% 只收超标部分 ${map.fineAmount2}美元/笔;
							大于等于${map.countRate3*100}%  收取${map.fineAmount3} 美元/笔;
							</c:if>
						</td>
						<td  class="border_top_right4" style="width: 200px;"  align="center">
						<a href="#" onclick="deleteBouncedConf('${map.id}');" >删除</a>&nbsp;&nbsp;
						<a href="#" onclick="updateBouncedConf('${map.cardOrg}','${map.ruleNo}','${map.countRate1}',
						'${map.fineAmount1}','${map.countRate2}','${map.fineAmount2}','${map.countRate3}','${map.fineAmount3}','${map.id}')">修改</a>
						</td>
					</tr>
					</c:forEach>
				</table>
			</td>	
		</tr>
		
		<%-- 
		<tr class="trForContent2"><td class="border_top_right4" align="left" colspan="9">拒付配置信息：</td></tr>
		<tr class="trForContent1">
			<td align="right" class="border_top_right4">小于：</td>	
			<td class="border_top_right4" align="left" style="width: 150px;">
				<input type="text" name="firstPercent" id="firstPercent" value="${firstPercent }" style="width: 80px;"/>%
			</td>
			<td align="left" class="border_top_right4" style="width: 80px;">收取</td>
			<td class="border_top_right4" align="left" style="width: 150px;">
				<input type="text" name="firstCost" id="firstCost" value="${firstCost }" style="width: 80px;"/>
			</td>
			<td align="left" class="border_top_right4" colspan="9" >美元/笔</td>
		</tr>
		<tr class="trForContent1">
			<td align="right" class="border_top_right4" style="width: 100px;">大于等于：</td>	
			<td class="border_top_right4" align="left">
				<input type="text" name="secondPercent" id="secondPercent" value="${secondPercent }" style="width: 80px;"/>%
			</td>
			<td class="border_top_right4" align="left">
				小于
			</td>
			<td class="border_top_right4" align="left">
				<input type="text" name="thirdPercent" id="thirdPercent" value="${thirdPercent }" style="width: 80px;"/>%
			</td>
			<td class="border_top_right4" align="left" style="width: 60px;" colspan="9">
				收取<input type="text" name="secondCost" id="secondCost" value="${secondCost }" style="width: 80px;"/>美元/笔
			</td>
		</tr>
		<tr class="trForContent1">
			<td align="right" class="border_top_right4">大于等于：</td>	
			<td class="border_top_right4" align="left">
				<input type="text" name="fourPercent" id="fourPercent" value="${fourPercent }" style="width: 80px;"/>%
			</td>
			<td class="border_top_right4" align="left" style="width: 100px;">
				收取
			</td>
			<td class="border_top_right4" align="left" style="width: 100px;">
				<input type="text" name="fourCost" id="fourCost" value="${fourCost }" style="width: 80px;"/>
			</td>
			<td class="border_top_right4" align="left" colspan="9">
				美元/笔
			</td>
		</tr>
		 --%>
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		
		<tr class="trForContent2"><td class="border_top_right4" align="left" colspan="9">结算账户信息：</td></tr>
		<c:forEach items="${merchantList}" var="dto" varStatus="status">

			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >开户行所属地区：</td>
				<td class="border_top_right4" align="left" >
					<input type="hidden"  name="liquidateId" type="hidden" value="${dto.liquidateId}" />
					<select id="regionBank_${status.count}" name="province" onchange="changeProvince('regionBank_${status.count}','cityBank_${status.count}');" valid="required" errmsg="所属地区不能为空!" value="${dto.regionBank}" style="width: 118px;">
						<c:forEach items="${provinceList}" var="province">
							<option value="${province.provincecode}" <c:if test="${province.provincecode == dto.regionBank}"> selected="selected" </c:if>>${province.provincename}</option>
						</c:forEach>
					</select>
				<font color="red">*</font>	
				</td>
				<td class="border_top_right4" align="right" >开户行所属城市：</td>
				<td class="border_top_right4" align="left" colspan="9" >
						<select	id="cityBank_${status.count}" name="city"  valid="required" errmsg="所属城市不能为空!" value="${dto.cityBank}" onchange="loadBranchBanks(${status.count })" style="width: 118px;">
							<option value="${dto.cityBank}" selected>${dto.cityBankName}</option>
						</select>
					<font color="red">*</font>
				</td>
			</tr>
			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >商户结算银行：</td>
				<td class="border_top_right4" align="left">
				
					<c:if test="${dto.autoFundout==1}" >
					<input type="hidden" id="bankId_${status.count}" name="bankId"  value="${dto.bankId }"  />
					<select	name="bankId_show"  valid="required" errmsg="商户结算银行名称不能为空!"  
						onchange="loadBranchBanks(${status.count }
							<c:if test='not empty ${dto.bankId }'>,${dto.bankId }</c:if>)" style="width: 118px;">
						<option value="" selected>---请选择---</option>
						<c:forEach items="${bankList}" var="bank">
						<option value="${bank.bankId}" <c:if test="${bank.bankId == dto.bankId}"> selected="selected" </c:if>>${bank.bankName}</option>
						</c:forEach>
					</select>	
					</c:if>
					<c:if test="${dto.autoFundout==0}" >
					<%-- <input type="hidden" id="bankName_${status.count }" name="bankName"   /> --%>
					<select	id="bankId_${status.count}" name="bankId"  valid="required" errmsg="商户结算银行名称不能为空!"  
						onchange="loadBranchBanks(${status.count }
							<c:if test='not empty ${dto.bankId }'>,${dto.bankId }</c:if>)" style="width: 118px;">
						<option value="" selected>---请选择---</option>
						<c:forEach items="${bankList}" var="bank">
						<option value="${bank.bankId}" <c:if test="${bank.bankId == dto.bankId}"> selected="selected" </c:if>>${bank.bankName}</option>
						</c:forEach>
					</select>				
					</c:if> 
					<font color="red">*</font>
					<c:if test="${not empty dto.bigBankName}">
					如果未选择，请选中右边横线提示的银行<U><font color="red">${dto.bigBankName }</font></U>，支行：<font color="red">${dto.bankName }</font></U>
					</c:if>
				</td>
			<td class="border_top_right4" align="right" >SWIFT CODE：</td>
				<td class="border_top_right4" align="left" colspan="9" >
					<input type="text" id="swiftCode" name="swiftCode"  value="${dto.swiftCode}"  />
				</td>
			</tr>
			
			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >结算银行支行：</td>
				<td class="border_top_right4" align="left" colspan="9">				
				<c:if test="${dto.autoFundout==1}" >
					<input type="hidden" id="branchBankId_${status.count}" name="branchBankId"  value="${dto.branchBankId }"  />
					<input type="text" id="bankName_${status.count }" name="bankName"  value="${dto.bankName }" readonly style="color:gray"/>
				</c:if>
				<c:if test="${dto.autoFundout==0}" >
					<select	id="branchBankId_${status.count}" name="branchBankId"  valid="required" errmsg="结算银行支行名称不能为空!"  onchange="setBrahchBankName(${status.count},this)"   >
					</select>
					<input type="hidden"  name="bankName"  value="${dto.bankName }" />
				</c:if>
					<font color="red">*</font>	
					
				<input type="text" id="bankKeywords_${status.count}" name="bankKeywords_${status.count}" class="inp_normal"  value="" style="width:135px;">
				<input type="button" id="getBankForKey_${status.count}" value="按所填关键字过滤"  style="color:#FF0000" onclick="queryBanks(${status.count})"/>
				</td>
			</tr>
			<tr class="trForContent1">
				<td class="border_top_right4" align="right" >银行账户号：</td>
				<td class="border_top_right4" align="left" >
					<input type="text"  name="bankAcct"   maxlength="32" valid="required|isInt" errmsg="商户结算银行账户不能为空!|商户结算银行账户只能为数字"  value="${dto.bankAcct}" <c:if test="${dto.autoFundout==1}" >readonly style="color:gray"</c:if> />
					<font color="red">*</font>	
				</td>
				<td class="border_top_right4" align="right" >商户结算账户：</td>
				<td class="border_top_right4" align="left" colspan="9">
					<input id="acctName" name="acctName"  valid="required" errmsg="商户结算银行名称不能为空!" value="${dto.acctName}" <c:if test="${dto.autoFundout==1}" >readonly style="color:gray"</c:if> style="width: 240px;"/>
					<font color="red">*</font>
				</td>
			</tr>
			<tr class="trForContent1">
					<td class="border_top_right4" align="right" >结算银行地址：</td>
					<td class="border_top_right4" align="left" >
						<input type="text"  name="bankAddress"   maxlength="32"  value="${dto.bankAddress}" <c:if test="${dto.autoFundout==1}" >readonly style="color:gray"</c:if>>
					</td>
					<td class="border_top_right4" align="right" >自动提现：</td>
				<td class="border_top_right4" align="left"  colspan="9">
					<c:if test="${dto.autoFundout==1}" >是</c:if>
					<c:if test="${dto.autoFundout==0}" >否</c:if>
				</td>
			</tr>
			</c:forEach>
			
			
			
			
			
			
			
			
			
			
			
			
			<tr class="trForContent2"><td class="border_top_right4" align="left" colspan="9">商户费率信息：&nbsp;&nbsp;&nbsp;<a href="javascript:shouAddFee();"><font color="blue">新增交易费率配置</font></a></td></tr>
			
			<c:forEach items="${merchantRateList}" var="dto" varStatus="status">
			<tr class="trForContent1">
				<td class="border_top_right4" colspan="6">
				<table border="1" id="feeTable" style="width: 100%">
					<tr id="f1">
						<td class="border_top_right4" align="right" nowrap="nowrap" style="width: 60px; table-layout:fixed; word-break: break-all;">地区：</td>
						<td class="border_top_right4" align="left" style="width: 150px; table-layout:fixed; word-break: break-all;">
							${dto.countryCode}
						</td>
						<td class="border_top_right4" align="right" nowrap="nowrap" style="width: 60px; table-layout:fixed; word-break: break-all;">卡组织：</td>
						<td class="border_top_right4" align="left" style="width: 150px; table-layout:fixed; word-break: break-all;">
							${dto.organisation}
						</td>
						<td class="border_top_right4" align="right" nowrap="nowrap" style="width: 60px; table-layout:fixed; word-break: break-all;">交易方式：</td>
						<td class="border_top_right4" align="left" style="width: 60px; table-layout:fixed; word-break: break-all;">
							<c:if test="${dto.transType == '0'}">全部</c:if>
							<c:if test="${dto.transType == '1'}">EDC</c:if>
							<c:if test="${dto.transType == '2'}">DCC</c:if>
						</td>
						<td class="border_top_right4" align="right" style="width: 60px; table-layout:fixed; word-break: break-all;">卡类别：</td>
						<td class="border_top_right4" align="left" style="width: 60px; table-layout:fixed; word-break: break-all;">
							<c:if test="${dto.cardType == '0'}">全部</c:if>
							<c:if test="${dto.cardType == '1'}">信用卡</c:if>
							<c:if test="${dto.cardType == '2'}">借记卡</c:if>
							<c:if test="${dto.cardType == '3'}">专卡</c:if>
							<c:if test="${dto.cardType == '4'}">无卡</c:if>
						</td>
						<td class="border_top_right4" align="right" style="width: 60px; table-layout:fixed; word-break: break-all;">交易模型：</td>
						<td class="border_top_right4" align="left" style="width: 60px; table-layout:fixed; word-break: break-all;">
							<c:if test="${dto.transMode == '0'}">全部</c:if>
							<c:if test="${dto.transMode == '1'}">3D</c:if>
							<c:if test="${dto.transMode == '2'}">非3D</c:if>
							<c:if test="${dto.transMode == '3'}">LOCAL</c:if>
						</td>
						<td class="border_top_right4" align="right" style="width: 60px; table-layout:fixed; word-break: break-all;">支付方式：</td>
						<td class="border_top_right4" align="left" style="width: 60px; table-layout:fixed; word-break: break-all;">
							<c:if test="${dto.localPayCode == '0'}">--</c:if>
							<c:if test="${dto.localPayCode == '10081001'}">CTBoleto</c:if>
							<c:if test="${dto.localPayCode == '10081002'}">CTSafetyPay</c:if>
							<%-- <c:if test="${dto.localPayCode == '10081003'}">CTDirectDebitsSSL</c:if> --%>
							<c:if test="${dto.localPayCode == '10081004'}">CTSofortBanking</c:if>
							<c:if test="${dto.localPayCode == '10081005'}">CTEPS</c:if>
							<c:if test="${dto.localPayCode == '10081006'}">CTGiropay</c:if>
							<c:if test="${dto.localPayCode == '10081007'}">CTPagBrailDebitCard</c:if>
							<c:if test="${dto.localPayCode == '10081008'}">CTPagBrasilOTF</c:if>
							<c:if test="${dto.localPayCode == '10081009'}">CTPoli</c:if>
							<c:if test="${dto.localPayCode == '10081010'}">CTPrzelewy24</c:if>
							<c:if test="${dto.localPayCode == '10081011'}">CTQIWI</c:if>
							<c:if test="${dto.localPayCode == '10081012'}">CTSEPA</c:if>
							<c:if test="${dto.localPayCode == '10081013'}">CTTeleingreso</c:if>
							<c:if test="${dto.localPayCode == '10081014'}">CTTrustPay</c:if>
							<c:if test="${dto.localPayCode == '10081015'}">CTiDeal</c:if>
							<c:if test="${dto.localPayCode == '10081016'}">前海万融</c:if>
						</td>
						<td style="width: 80px; table-layout:fixed; word-break: break-all;" align="center" rowspan="2">
						<a href="javascript:updateFee('${dto.id}','${dto.countryCode}','${dto.organisation}','${dto.transType}',
						'${dto.cardType}','${dto.transMode}','${dto.dealCode}','${dto.localPayCode}','${dto.chargeRate}','${dto.fixedCharge}','${dto.feeType}',
						'${dto.chargeRate1}','${dto.chargeRate2}','${dto.chargeRate3}','${dto.fixedCharge1}','${dto.fixedCharge2}',
						'${dto.fixedCharge3}','${dto.monthAmountLevel}','${dto.monthAmountLevel1}','${dto.monthAmountLevel2}','${dto.monthAmountLevel3}'
						,'${dto.levelCurrencyCode}','${dto.level1CurrencyCode}','${dto.level2CurrencyCode}','${dto.level3CurrencyCode}',
						'${dto.fixedCurrencyCode}','${dto.fixed1CurrencyCode}','${dto.fixed2CurrencyCode}','${dto.fixed3CurrencyCode}');">修改</a>
						&nbsp;&nbsp;<a href="javascript:del('${dto.id}');">删除</a></td>
					</tr>
					<tr id="f2">
						<td class="border_top_right4" align="right" >交易类型：</td>
						<td class="border_top_right4" align="left" >
							${dto.dealCode}
						</td>
						<td class="border_top_right4" align="right" style="width: 60px; table-layout:fixed; word-break: break-all;">费率：</td>
						<td class="border_top_right4" align="left" >
							<fmt:formatNumber type="number" value="${dto.chargeRate/100}" maxFractionDigits="4"/>
						</td>
						<td class="border_top_right4" align="right" style="width: 60px; table-layout:fixed; word-break: break-all;">固定费用：</td>
						<td class="border_top_right4" align="left" >
							${dto.fixedCharge/1.00 }
							<c:forEach items="${currencyCodeEnum}" var="item">
								<c:if test="${item.code==dto.fixedCurrencyCode}">
									${item.desc}
								</c:if>
							</c:forEach>
						</td>
						<td class="border_top_right4" align="right" >类型：</td>
						<td class="border_top_right4" align="left" >
							<c:if test="${dto.feeType == '1'}">固定费用</c:if>
							<c:if test="${dto.feeType == '2'}">费率</c:if>
							<c:if test="${dto.feeType == '6'}">费率及固定费用</c:if>
						</td>
						<td class="border_top_right4" align="right" style="width: 60px; table-layout:fixed; word-break: break-all;">最低月交易量：</td>
						<td class="border_top_right4" align="left" style="width: 60px; table-layout:fixed; word-break: break-all;">
							${dto.monthAmountLevel}
							<c:forEach items="${currencyCodeEnum}" var="item">
								<c:if test="${item.code==dto.levelCurrencyCode}">
									${item.desc}
								</c:if>
							</c:forEach>
						</td>
						<td class="border_top_right4" align="right" style="width: 60px; table-layout:fixed; word-break: break-all;"></td>
						<td class="border_top_right4" align="left" style="width: 60px; table-layout:fixed; word-break: break-all;">
						</td>
					</tr>
				</table>
				</td>
			</tr>
			</c:forEach>
</table>
<br></br>
<table width="95%" border="0" cellspacing="0"
	cellpadding="1" align="center">	
	<tr>	
		<td  align="center" style="text-align: center">
			<input id="submitForm" name="submitForm" class="button2" type="button" value="保存" onclick="updateMerchant();"/>
		</td>
	</tr>
</table>
<input type="hidden" name="id" id="id" value="${id}"/>
</form>
<!-- 规则 1 拒付罚款  bounced-->
<div id="configRulesDiv" style="display: none" align="center" >
<form id="configForm" name="configForm" >
<input type="hidden" name="id" id ="bouncedId" >
<table border="0" cellspacing="0"
	cellpadding="0" align="center" width="95%">
	<tr>
			<td align="right" nowrap="nowrap">卡组织：</td>
					<td class="" align="left" valign="middle">
						<select name="cardOrg" id="cardOrg">
							<option value="">请选择</option>
								<option value="VISA">VISA</option>
								<option value="MASTER">MASTER</option>
						</select>
			</td>
			<td  align="right" nowrap="nowrap">拒付罚款规则：</td>
					<td class="" align="left" valign="middle">
						<select name="rules" id="rules" onchange="selectRule();">
							<option value="">请选择</option>
							<option value="1">规则1_按笔收取</option>
							<option value="2">规则2_按笔阶梯收取</option>
							<option value="3">规则3_按拒付率阶梯收取</option>
							<option value="4">规则4_按拒付率阶梯收取超标部分</option>
						</select>
						<br>
			</td>
	</tr>
	<tr>
		<td colspan="4"><br><br></td>
	</tr>
	<tr>
		<td colspan="1"><br> </td>
		<td colspan="3"  >
			<div style="display: none" align="left"  id="rules1">
				<table border="0" cellspacing="0" cellpadding="1" align="left" >
					<tr>
						<td>
						收取  <input id="content1" name="content1"  onkeyup="checkNum(this);" onblur="decimals(this)"  > 美元/笔
						</td>
					</tr>
				</table>
			</div>
			<div style="display: none" align="center"  id="rules2">
				<table border="0" cellspacing="0" cellpadding="1" align="cente" >
					<tr>
						<td colspan="2">
						小于 &nbsp;&nbsp;<input name="content2" id="content2"  onkeyup="checkNum(this);" onchange="setContent4();" >笔								
						</td>
						<td>
						收取  <input name="content3" id="content3"  onkeyup="checkNum(this);" onblur="decimals(this)" >美元/笔		
						</td>
					</tr>
					<tr>
						<td colspan="2"><br></td>
					</tr>
					<tr>
						<td colspan="2">
						大于等于 <input readonly="readonly" name="content4" id="content4"   onkeyup="checkNum(this);" >笔
						小于 <input name="content5" id="content5"   onkeyup="checkNum(this);"  onchange="setContent7();" >笔				 &nbsp;&nbsp;		
						</td>
						<td>
						收取  <input name="content6" id="content6"  onkeyup="checkNum(this);" onblur="decimals(this)">美元/笔								
						</td>
					</tr>
						<tr>
					<td colspan="2"><br></td>
					</tr>
					<tr>
						<td colspan="2">
						大于等于<input readonly="readonly" name="content7" id="content7"  onkeyup="checkNum(this);" >笔								
						</td>
						<td>
						收取 <input name="content8" id="content8" onkeyup="checkNum(this);" onblur="decimals(this)">美元/笔						
						</td>
					</tr>
				</table>
			</div>
			<div style="display: none" align="center"  id="rules3">
				<table border="0" cellspacing="0" cellpadding="1" align="cente" >
					<tr>
						<td colspan="2">
						小于 &nbsp;&nbsp;<input name="content9" id="content9" onchange="setContent11();" onkeyup="checkNum(this);" onblur="decimals(this)">%								
						</td>
						<td>
						收取<input name="content10" id="content10"  onkeyup="checkNum(this);" onblur="decimals(this)" >美元/笔								
						</td>
					</tr>
					<tr>
						<td colspan="2"><br></td>
					</tr>
					<tr>
						<td colspan="2">
						大于等于 <input readonly="readonly" name="content11" id="content11"  onkeyup="checkNum(this);" onblur="decimals(this)" >%	 
						小于 <input name="content12" id="content12" onkeyup="checkNum(this);" onblur="decimals(this)" onchange="setContent14();">%	&nbsp;	&nbsp; 			
						</td>
						<td>
						收取<input name="content13" id="content13"  onkeyup="checkNum(this);" onblur="decimals(this)" >美元/笔								
						</td>
					</tr>
						<tr>
					<td colspan="2"><br></td>
					</tr>
					<tr>
						<td colspan="2">
					    大于等于<input readonly="readonly"  name="content14" id="content14"  onkeyup="checkNum(this);" onblur="decimals(this)" >%								
						</td>
						<td>
						收取<input name="content15" id="content15"  onkeyup="checkNum(this);" onblur="decimals(this)">美元/笔								
						</td>
					</tr>
				</table>
			</div>
			<div style="display: none" align="center"  id="rules4">
				<table border="0" cellspacing="0" cellpadding="1" align="cente" >
						<tr>
							<td colspan="2">
							小于 &nbsp;&nbsp;<input name="content16" id="content16" onchange="setContent18();" onkeyup="checkNum(this);" onblur="decimals(this)">%								
							</td>
							<td>
							收取&nbsp;&nbsp;&nbsp;&nbsp;<input name="content17" id="content17" onkeyup="checkNum(this);" onblur="decimals(this)" >美元/笔								
							</td>
						</tr>
					<tr>
						<td colspan="2"><br></td>
					</tr>
						<tr>
							<td colspan="2">
							大于等于<input readonly="readonly" name="content18" id="content18"  onkeyup="checkNum(this);" onblur="decimals(this)">%
							小于<input name="content19" id="content19"  onkeyup="checkNum(this);" onchange="setContent21();" onblur="decimals(this)" >%	&nbsp;&nbsp;				
							</td>
							<td>
							只收超标部分<input  name="content20" id="content20"  onkeyup="checkNum(this);" onblur="decimals(this)" >美元/笔								
							</td>
						</tr>
					<tr>
						<td colspan="2"><br></td>
					</tr>
						<tr>
							<td colspan="2">
							大于等于<input readonly="readonly" name="content21" id="content21"  onkeyup="checkNum(this);" onblur="decimals(this)">%								
							</td>
							<td>
							收取 &nbsp;&nbsp;&nbsp;&nbsp;<input name="content22" id="content22"  onkeyup="checkNum(this);" onblur="decimals(this)">美元/笔								
							</td>
						</tr>
					</table>
			</div>
		</td>
	</tr>
	<tr>
		<td colspan="4"><br></td>
	</tr>
	<tr>
		<td colspan="4" align="center">
			<input type="button" style="height:35px;width:45px;" value="确定" onclick="validateConfig();"> &nbsp;&nbsp;&nbsp;
			<input type="button" style="height:35px;width:45px;" value="返回" onclick="back();">
		
		</td>		
	</tr>
</table>
<input type="hidden" id="memberCode" name="memberCode" value="${merchantDto.memberCode}">
</form>
</div>

<div id="addLogDiv" name="addLogDiv" style="display: none" align="center">
	<form id="addForm" name="addForm">
	<table border="0" id="feeTable" style="width: 100%">
		<tr>
			<td class="border_top_right4" align="right" nowrap="nowrap">地区：</td>
			<td class="border_top_right4" align="left" valign="middle">
				<select id="countryCode" name="countryCode" multiple="multiple" onbeforeactivate="return false" onfocus="this.blur()" onmouseover="this.setCapture()" onmouseout="this.releaseCapture()" size="10" style="width: 118px;">
				</select><font color="red">*</font>
				&nbsp;&nbsp;
					<input type="checkbox" id="chkEur" onclick="checkLocalLockSet()" style="display:inline;vertical-align:middle;"/>欧盟
					<input type="checkbox" id="chkNotEur" onclick="checkLocalLockSet()" style="display:inline;vertical-align:middle;"/>非欧盟
					<input type="checkbox" id="local" onclick="checkLocalLockSet()" style="display:inline;vertical-align:middle;"/>本地化
			</td>
			<td class="border_top_right4" align="right" nowrap="nowrap">卡组织：</td>
			<td class="border_top_right4" align="left" >
				<select id="organisation" name="organisation" multiple="multiple" size="10" style="width: 118px;">
					<option value="VISA">VISA</option>
					<option value="MASTER">MASTER</option>
					<option value="JCB">JCB</option>
					<option value="AE">AE</option>
					<option value="DC">DC</option>
					<option value="LOCAL">LOCAL</option>
				</select>
				<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td class="border_top_right4" align="right" nowrap="nowrap">交易方式：</td>
			<td class="border_top_right4" align="left" >
				<select id="transType" name="transType" style="width: 118px;">
					<option value="0">全部</option>
					<option value="1">EDC</option>
					<option value="2">DCC</option>
				</select>
			</td>
			<td class="border_top_right4" align="right" >卡类别：</td>
			<td class="border_top_right4" align="left" >
				<select id="cardType" name="cardType" style="width: 118px;">
					<option value="0">全部</option>
					<option value="1">信用卡</option>
					<option value="2">借记卡</option>
					<option value="3">专卡</option>
					<option value="4">无卡</option>
				</select>
			</td>
		</tr>
		<tr>
			<td class="border_top_right4" align="right" >交易模型：</td>
			<td class="border_top_right4" align="left" >
				<select id="transMode" name="transMode" style="width: 118px;">
					<option value="0">全部</option>
					<option value="1">3D</option>
					<option value="2">非3D</option>
					<option value="3">LOCAL</option>
				</select>
			</td>
			<td class="border_top_right4" align="right" >类型：</td>
			<td class="border_top_right4" align="left" >
				<select id="feeType" name="feeType" style="width: 118px;">
					<option value="1"> 固定费用</option>
					<option value="2" selected="selected" > 费率</option>
					<option value="6"> 固定费用_费率</option>
				</select>							
			</td>
		</tr>
		<tr>
			<td class="border_top_right4" align="right" >交易类型：</td>
			<td class="border_top_right4" align="left" >
				<input type="text" name="dealCode" id="dealCode" value="202" style="width: 118px;" readonly="readonly"/>
			</td>
			<td class="border_top_right4" align="right" >支付方式：</td>
			<td class="border_top_right4" align="left" >
				<select id="localPayCode" name="localPayCode" style="width: 118px;">
					<option value="0">--</option>
					<option value="10081001">CTBoleto</option>
					<option value="10081002">CTSafetyPay</option>
					<!-- <option value="10081003">CTDirectDebitsSSL</option> -->
					<option value="10081004">CTSofortBanking</option>
					<option value="10081005">CTEPS</option>
					<option value="10081006">CTGiropay</option>
					<option value="10081007">CTPagBrailDebitCard</option>
					<option value="10081008">CTPagBrasilOTF</option>
					<option value="10081009">CTPoli</option>
					<option value="10081010">CTPrzelewy24</option>
					<option value="10081011">CTQIWI</option>
					<option value="10081012">CTSEPA</option>
					<option value="10081013">CTTeleingreso</option>
					<option value="10081014">CTTrustPay</option>
					<option value="10081015">CTiDeal</option>
				</select>
			</td>
		</tr>
		
	</table>
	<table border="0" style="width: 100%">
		<tr>
			<td class="border_top_right4" align="center" >最低月交易量</td>
			<td class="border_top_right4" align="center" >费率(百分比)</td>
			<td class="border_top_right4" align="center" >固定费用</td>
		</tr>
		
		<tr>
			<td class="border_top_right4" align="right" >
				<input id="monthAmountLevel" name="monthAmountLevel" value="0"/>
				<font color="red">*</font>
				<select id="levelCurrencyCode" name="levelCurrencyCode"></select>
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="center" >
				<input id="chargeRate" name="chargeRate"/>
				<font color="red">*</font>
			</td>
			<td class="border_top_right4" align="left" >
				<input id="fixedCharge" name="fixedCharge"/>
				<font color="red">*</font>
				<select id="fixedCurrencyCode" name="fixedCurrencyCode"></select>
				<font color="red">*</font>
			</td>
		</tr>
		<tr>
			<td class="border_top_right4" align="right" >
				<input id="monthAmountLevel1" name="monthAmountLevel1"/>
				&nbsp;<select id="level1CurrencyCode" name="level1CurrencyCode"></select>&nbsp;
			</td>
			<td class="border_top_right4" align="center" >
				<input id="chargeRate1" name="chargeRate1"/>&nbsp;
			</td>
			<td class="border_top_right4" align="left" >
				<input id="fixedCharge1" name="fixedCharge1"/>
				&nbsp;<select id="fixed1CurrencyCode" name="fixed1CurrencyCode"></select>&nbsp;
			</td>
		</tr>
		<tr>
			<td class="border_top_right4" align="right" >
				<input id="monthAmountLevel2" name="monthAmountLevel2"/>
				&nbsp;<select id="level2CurrencyCode" name="level2CurrencyCode"></select>&nbsp;
			</td>
			<td class="border_top_right4" align="center" >
				<input id="chargeRate2" name="chargeRate2"/>&nbsp;
			</td>
			<td class="border_top_right4" align="left" >
				<input id="fixedCharge2" name="fixedCharge2"/>
				&nbsp;<select id="fixed2CurrencyCode" name="fixed2CurrencyCode"></select>&nbsp;
			</td>
		</tr>
		<tr>
			<td class="border_top_right4" align="right" >
				<input id="monthAmountLevel3" name="monthAmountLevel3"/>
				&nbsp;<select id="level3CurrencyCode" name="level3CurrencyCode"></select>&nbsp;
			</td>
			<td class="border_top_right4" align="center" >
				<input id="chargeRate3" name="chargeRate3"/>&nbsp;
			</td>
			<td class="border_top_right4" align="left" >
				<input id="fixedCharge3" name="fixedCharge3"/>
				&nbsp;<select id="fixed3CurrencyCode" name="fixed3CurrencyCode"></select>&nbsp;
			</td>
		</tr>
		<tr>
			<td colspan="3" align="center">
				<input type="button" value="确定" onclick="addFee()" />
				<input type="button" value="取消" onclick="closeFee()"/>
			</td>
		</tr>
	</table>
	<input type="hidden" id="id" name="id" value="">
	<input type="hidden" id="saveOrUpdate" value="">
	<input type="hidden" id="memberCode" name="memberCode" value="${merchantDto.memberCode}">
	</form>
</div>
</body>