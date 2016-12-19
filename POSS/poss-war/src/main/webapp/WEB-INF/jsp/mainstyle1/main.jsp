<%@ page language="java" contentType="text/html; charset=utf-8"
	pageEncoding="utf-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<title>支付平台支付POSS管理系统</title>
<link href="hnastyle/css/default.css" rel="stylesheet" type="text/css" />
<link rel="stylesheet" type="text/css"
	href="hnastyle/js/themes/default/easyui.css" />
<link rel="stylesheet" type="text/css"
	href="hnastyle/js/themes/icon.css" />
<script type="text/javascript" src="hnastyle/js/jquery-1.4.4.min.js"></script>
<script type="text/javascript"
	src="hnastyle/js/jquery.easyui.min.1.2.2.js"></script>
<script type="text/javascript" src='hnastyle/js/hnmain.js'>
</script>
</head>
<body class="easyui-layout" style="overflow-y: hidden" scroll="no">
	<noscript>
		<div
			style="position: absolute; z-index: 100000; height: 2046px; top: 0px; left: 0px; width: 100%; background: white; text-align: center;">
			浏览器不支持JS</div>
	</noscript>
	<div region="north" split="true" border="false"
		href="main.do?method=topFrame"
		style="overflow: hidden; height: 30px; background: url(hnastyle/images/layout-browser-hd-bg.gif) #7f99be repeat-x center 50%; line-height: 20px; color: #fff; font-family: Verdana, 微软雅黑, 黑体">
	</div>
	<div region="west" hide="true" animate="false" title="导航菜单"
		style="width: 270%; height: 100%; overflow-x: hidden; position: relative;"
		id="west" href="main.do?method=menuFrame"></div>
	<div id="mainPanle" region="center"
		style="background: #eee; overflow-y: hidden">
		<div id="tabs" class="easyui-tabs" fit="true" border="false">
			<div title="工作面板">
				<div class="main_con_wrap">
				<div class="main_wrap">
					<h3 class="main_title">风控</h3>
					<ul class="main_sub_items fengkong">
						<!--
						<li class="main_sub_item">
							<a onclick="pendingWaybill(1);"><img zhi="3" class="input_img" src="images/main_img/3.png" /></a>
						 	<input class="fengkong_03 input_img" type="button" value="" onclick="pendingWaybill(1);">
								<p>待审核运单<font class="ft16red" color="red" id="pendingWaybillCount"></font>件</p>
						</li>
						 -->
						<li class="main_sub_item">
							<a onclick="toUnApproval();"><img zhi="4" class="input_img" src="images/main_img/4.png" /></a>
							<!-- <input class="fengkong_04 input_img" type="button" value="" onclick="toUnApproval();"> -->
								<p>待审核黑名单<font class="ft16red" color="red" id="toUnApproval"></font>件</p>
						</li>
						<li class="main_sub_item">
							<a onclick="pendingWebsite(2);"><img zhi="5" class="input_img" src="images/main_img/5.png" /></a>
							<!-- <input class="fengkong_05 input_img" type="button" value="" onclick="pendingWebsite(2);"> -->
								<p>待审核域名<font class="ft16red" color="red" id="pendingWebsiteCount"></font>件</p>
						</li>
					</ul>
					</div>
					<div class="main_wrap">
					<h3 class="main_title">运营</h3>
					<ul class="main_sub_items yunying">
						<li class="main_sub_item">
							<a onclick="pendingAccount();"><img zhi="6" class="input_img" src="images/main_img/6.png" /></a>
							<!-- <input class="fengkong_06 input_img" type="button" value="" onclick="pendingAccount();"> -->
								<p>开通账户申请<font class="ft16red" id="pendingAccountCount"></font>件</p> 
						</li>
						<li class="main_sub_item">
							<a onclick="toUnpprovalPlatformMembers();"><img zhi="7" class="input_img" src="images/main_img/7.png" /></a>
							<!-- <input class="fengkong_07 input_img" type="button" value="" onclick="toUnpprovalPlatformMembers();"> -->
								<p>关联会员申请<font class="ft16red" color="red" id="unpprovalPlatformMembers"></font>件</p>
						</li>
					</ul>
					</div>
					<div class="main_wrap">
					<h3 class="main_title">财务</h3>
					<ul class="main_sub_items caiwu">
						<li class="main_sub_item">
							<a onclick="pending(1,2)"><img zhi="1"width="80" height="80"   class="input_img" src="images/main_img/1.png" /></a>
							<!-- <input class="fengkong_01 input_img" type="button" value="" onclick="pending(1,2);"> -->
								<p>待处理申诉<font class="ft16red" id="pendingCount"></font>件</p> 
						</li>
						<li class="main_sub_item">
							<a onclick="pendingReview(3,4);"><img zhi="2" class="input_img" src="images/main_img/2.png" /></a>
							<!-- <input class="fengkong_02 input_img" type="button" value="" onclick="pendingReview(3,4);"> -->
								<p>拒付处理复核<font class="ft16red" color="red" id="pendingReviewCount"></font>件</p>
						</li>
						<li class="main_sub_item">
							<a onclick="capitalPool();"><img zhi="8" class="input_img" src="images/main_img/8.png" /></a>
							<!-- <input class="fengkong_08 input_img" type="button" value="" onclick="capitalPool();"> -->
								<p>购结汇头寸不足<font class="ft16red" id="capitalPoolCount"></font>件</p> 
						</li>
					</ul>
					</div>
				</div>
			</div>
		</div>
	</div>
</body>
<script type="text/javascript">

	$(function(){
			$.ajax({
				type: "POST",
				url: "${pageContext.request.contextPath}/capitalPoolManageWork.do?method=workPanels",
				success: function(result) {
					$("#capitalPoolCount").html(result);
				}
			});
		
			$.ajax({
				type: "POST",
				url: "${pageContext.request.contextPath}/accountDreCheckWork.do?method=workPanels",
				//data: pars,
				success: function(result) {
					$("#pendingAccountCount").html(result);
				}
			});
			
			$.ajax({
				type: "POST",
				url: "${pageContext.request.contextPath}/bounced-register.do?method=workPanels",
				//data: pars,
				success: function(result) {
					var json=JSON.parse(result);
					$("#pendingCount").html(json.pendingCount);
					$("#pendingReviewCount").html(json.pendingReviewCount);
				}
			});
			
			$.ajax({
				type: "POST",
				url: "${pageContext.request.contextPath}/expresstrackingPending.do?method=workPanels",
				//data: pars,
				success: function(result) {
		//			alert(result)
					$("#pendingWaybillCount").html(result);
				}
			});
			$.ajax({
				type: "POST",
				url: "${pageContext.request.contextPath}/crosspay/sitesetAuditPending.do?method=workPanels",
				//data: pars,
				success: function(result) {
					$("#pendingWebsiteCount").html(result);
				}
			});
			
		    $.ajax({
				type: "POST",
				url: "${pageContext.request.contextPath}/blackWhiteListApprovalQueryPending.do?method=count&status=0",
				//data: pars,
				success: function(result) {
					$("#toUnApproval").html(result);
				}
			});
		    
		    $.ajax({
				type: "POST",
				url: "${pageContext.request.contextPath}/platformMembersQuery.do?method=findUnpprovalPlatformMembers&status=1",
				success: function(result) {
					$("#unpprovalPlatformMembers").html(result);
				}
			});
		    
		   
		    
		   
	});
	function pending(obj,status){
		var url="${pageContext.request.contextPath}/pending-bounced-register.do?method=bouncedQuery&status="+obj+","+status;		
		addTab("待处理申诉",url);
	}
	function pendingReview(obj,status){
		var url="${pageContext.request.contextPath}/bounced-register.do?method=bouncedApprove&status="+obj+","+status;
		addTab("拒付处理复核",url);
	}
	function pendingWaybill(status){
		var url="${pageContext.request.contextPath}/expresstracking.do?status="+status;
		addTab("运单管理",url);
	}
	function pendingWebsite(status){
		var url="${pageContext.request.contextPath}/crosspay/sitesetAudit.do?statusIn="+"2,5,6";
		addTab("域名审核",url);
	}
	function pendingAccount(){
		var url="${pageContext.request.contextPath}/accountDreCheck.do?status="+"0";
		addTab("企业账户开通审核",url);
	}
	function toUnApproval(){
		var url="${pageContext.request.contextPath}/blackWhiteListApprovalQuery.do?method=blackWhiteListApprovalSearchView&status=0";
		addTab("黑名单审核",url);
	}
	function capitalPool(){
		var url="${pageContext.request.contextPath}/capitalPoolManage.do";
		addTab("资金池头寸管理",url);
	}
	function toUnpprovalPlatformMembers() {
		var url="${pageContext.request.contextPath}/platformMembersQuery.do?method=findByCriteria&status=1";
		addTab("平台会员号绑定管理",url);
	}
	function addTab(title, url){    
	    if ($('#tabs').tabs('exists', title)){    
	        $('#tabs').tabs('select', title);    
	    } else {    
	        var content = '<iframe scrolling="auto" frameborder="0"  src="'+url+'" style="width:100%;height:100%;"></iframe>';    
	        $('#tabs').tabs('add',{    
	            title:title,    
	            content:content,    
	            closable:true    
	        });    
	    }    
	}
	function checknum (){
   	 $(".ft16red").each(function(){
		    	if( $(this).html() == "0" || $(this).html() == null ){
			    	$(this).css("color","#333")
			    }else{
			    	$(this).css("color","red")
			    }
		    })
		    window.clearInterval(time);
   }
	var time = setInterval("checknum()",400); 
	if($(".main_sub_item") !=undefined){
		$(".main_sub_item").hover(function(){
			var zhi = $("img",this).attr("zhi");
			var index =parseInt(zhi);
			$("img",this).attr("src","images/main_img/2."+index+".png");
		},function(){
			var zhi = $("img",this).attr("zhi");
			var index =parseInt(zhi);
			$("img",this).attr("src","images/main_img/"+index+".png")
		})
		
		/* $(".main_sub_item").mouseover(function(){
			var zhi = $("img",this).attr("zhi");
			var index =parseInt(zhi);
			$("img",this).attr("src","images/main_img/2."+index+".png");
		
		})
		$(" .main_sub_item").mouseleave(function(){
			var zhi = $("img",this).attr("zhi");
			var index =parseInt(zhi);
			$("img",this).attr("src","images/main_img/"+index+".png")
		}) */
	}
	
</script>

</html>