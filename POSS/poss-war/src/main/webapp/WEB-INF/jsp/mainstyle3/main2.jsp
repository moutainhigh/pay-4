<%@ page language="java" import="java.util.*" pageEncoding="UTF-8" contentType="text/html; charset=utf-8"%>
<%@ include file="/common/head.jsp"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html>
	<head>
		<title></title>
		
		<!-- 自定义样式 -->
		<!--<link href="${ctx}/css/basic.css" rel="stylesheet" type="text/css"  />-->
		<link href="${ctx}/css/mainstyle3.css" rel="stylesheet" type="text/css"  />
		<link href="${ctx}/css/page.css" rel="stylesheet" type="text/css"  />
		
		<!-- 其他样式 -->

		<!-- jquery及jquery插件样式 -->
		<link href="${ctx}/js/jquery/css/redmond/jquery-ui-1.8.4.custom.css" rel="stylesheet" type="text/css" />
		<link href="${ctx}/js/jquery/plugins/nyroModal-1.6.2/styles/nyroModal.css" rel="stylesheet" type="text/css" />
		
		<!-- jquery   js包 -->
		<script src="${ctx}/js/jquery/js/jquery-1.4.2.min.js" type="text/javascript"></script>
		<script src="${ctx}/js/jquery/js/jquery-ui-1.8.4.custom.min.js" type="text/javascript"></script>
		
		<!-- jquery插件js包 -->
		
		<!-- 其他插件包 -->
		<script src="${ctx}/js/My97DatePicker/WdatePicker.js" type="text/javascript"></script>
		
		<!-- 自定义工具包 -->
		<!--<script src="${ctx}/js/myTools.js" type="text/javascript"></script>-->
		<script src="${ctx}/js/jquery/plugins/validate/validateForJQuery.js" type="text/javascript"></script>
		

		<script type="text/javascript">
			var loadImgStr = "<img border=\"0\" src=\"${ctx}/images/page/blue-loading.gif\" />操作中, 请稍候...";
			
			$(function() {
				//加载top页面
				$("#topDiv").html(loadImgStr);
				$.post("${ctx}/main.do?method=topFrame", {}, function(str) {
					$("#topDiv").html(str);
				});
				
				//加载菜单
				$("#menu_loading").fadeIn("fast");
				$.post("${ctx}/main.do?method=menuFrame", {}, function(str) {
					$("#menu_loading").fadeOut("slow");
					$("#menu").html(str);
				});
				
				$('#handleMessageDiv').dialog({
					autoOpen: false,
					width: 300,
					close: (function () {
						allUnoverlay();
					}),
					open: (function () {
						allOverlay();
					}),
					buttons: {
						"关 闭": function() { 
							$(this).dialog("close");
						}
					}
				});
				
				$('#infoLoadingDiv').dialog({
					autoOpen: false,
					width: 200,
					close: (function () {
						allUnoverlay();
					}),
					open: (function () {
						allOverlay();
					})
				});
			});
			
			function loadHandlePage(a) {
				var temp_load_page = "&nbsp;&nbsp;&nbsp;<img id='temp_load_menu_img' border='0' src='${ctx}images/page/blue-loading.gif'>";
				$("#handlePage").html(temp_load_page);
				var temp = a.innerHTML;
				var temp_load_menu = "&nbsp;&nbsp;&nbsp;<img id='temp_load_menu_img' border='0' width='12' height='12' src='${ctx}images/page/blue-loading.gif'>";
				a.innerHTML += temp_load_menu;
				
				var s_time_pats = new Date();
				var s_parss = s_time_pats.getHours() + s_time_pats.getMinutes() + s_time_pats.getSeconds() + s_time_pats.getMilliseconds();
								
				$('#handlePage').load(a.id + "?time=" + s_parss);
				a.innerHTML = temp;
			}
			
			function allOverlay() {
				$("#allOverlayDiv").css("display", "block");
			}
			function allUnoverlay() {
				$("#allOverlayDiv").css("display", "none");
			}
			
		</script>
	</head>
	
	<body class="ui-widget-content" style="border: 0px">
		<div id="topDiv" class="head ui-state-default">head</div>
		<div class="content" style="text-align: center;">
			<div id="menu" class="left_menu">
				<div id="menu_loading">
					<img src="${ctx}/images/page/blue-loading.gif" />&nbsp;&nbsp;menu loading....
				</div>
			</div>
			<div class="handle">
				<div id="handlePage">&nbsp;</div>
			</div>
		</div>
		<div id="allOverlayDiv" class="ui-widget-overlay" style="display: none;"></div>
		<div id="handleMessageDiv" title="提示信息" style="text-align: left;"></div>
		<div id="infoLoadingDiv" title="加载信息" style="display: none; width: 200px; padding-top: 30px; height: 70px;">
			<img src="${ctx}images/page/blue-loading.gif" />&nbsp;&nbsp;信息加载中, 请稍候...
		</div>
	</body>
</html>
