<%@ page language="java" import="java.util.*" pageEncoding="UTF-8"%>


<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Strict//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-strict.dtd">

<html>
<head>
		<title>Filament Group Lab</title>
		<script src="${serverPath}javaScript/jquery/plug/fgmenu/fg.menu.js" type="text/javascript"></script>
		<link href="${serverPath}javaScript/jquery/plug/fgmenu/css/fg.menu.css" rel="stylesheet" type="text/css"  />
		<script language='javascript' src="${serverPath}javaScript/jquery/plug/nyro/js/jquery.nyroModal-1.5.5.pack.js"></script>
		
		<style type="text/css">
			body { font-size:62.5%; margin:0; padding:0; }
			#menuLog { font-size:1.4em; margin:20px; }
			.hidden { position:absolute; top:0; left:-9999px; width:1px; height:1px; overflow:hidden; }
			
			.fg-button { clear:left; margin:0 4px 40px 20px; padding: .4em 1em; text-decoration:none !important; cursor:pointer; position: relative; text-align: center; zoom: 1; }
			.fg-button .ui-icon { position: absolute; top: 50%; margin-top: -8px; left: 50%; margin-left: -8px; }
			a.fg-button { float:left;  }
			button.fg-button { width:auto; overflow:visible; } /* removes extra button width in IE */
			
			.fg-button-icon-left { padding-left: 2.1em; }
			.fg-button-icon-right { padding-right: 2.1em; }
			.fg-button-icon-left .ui-icon { right: auto; left: .2em; margin-left: 0; }
			.fg-button-icon-right .ui-icon { left: auto; right: .2em; margin-left: 0; }
			.fg-button-icon-solo { display:block; width:8px; text-indent: -9999px; }	 /* solo icon buttons must have block properties for the text-indent to work */	
			
			.fg-button.ui-state-loading .ui-icon { background: url(spinner_bar.gif) no-repeat 0 0; }
		</style>
	    <script type="text/javascript">
		    $(function(){
				$('#userSetMenu').menu({ 
					content: $('#userSetMenu').next().html(),
					showSpeed: 100 
				});
				$('#styleSetMenu').menu({ 
					content: $('#styleSetMenu').next().html(),
					showSpeed: 100 
				});
		    });
		    
		    function loadUpdatePasswordPage() {
		    	$('#updataInitPageA').click();
		    }
		    
		    function updatePageSytle(pageStyle) {
		    	var pars = {"pageStyle": pageStyle};
		    	$('#infoLoadingDiv').dialog('open');
		    	$.ajax({
					type: "POST",
					url: "${serverPath}basic/employe/employe!updatePageStyle",
					data: pars,
					success: function(result) {
		    			$('#infoLoadingDiv').dialog('close');
						if (result = 'success') {
							if(document.all) {  
								window.location.reload();
							} else { 
								alert("修改成功!"); 
								window.location.reload();
							} 
						} else {
							$('#handleMessageDiv').html("操作失败, 请联系管理员!");
							$('#handleMessageDiv').dialog('open');
						}
					}
				});
		    }
	    </script>
</head>

<body style="text-align: right; width: 100%;">
	<a tabindex="0" href="#search-engines" class="fg-button fg-button-icon-right ui-widget ui-state-default ui-corner-all" id="styleSetMenu" style="float: right; margin-top: 18px;">
		<span class="ui-icon ui-icon-triangle-1-s"></span><b>页面主题</b>
	</a>
	<div id="search-engines" class="hidden" style="height: 400px; overflow-x:hidden; overflow-y:auto">
	<ul>
		<li style="text-align: left"><a href="#" onclick="javascript: updatePageSytle('blitzer')">
			<img src="${serverPath}javaScript/jquery/styleImg/blitzer.jpg" style="width: 50px; border: 0px;" />
		</a></li>
		<li style="text-align: left"><a href="#" onclick="javascript: updatePageSytle('black-tie')">
			<img src="${serverPath}javaScript/jquery/styleImg/black-tie.jpg" style="width: 50px; border: 0px;" />
		</a></li>
		<li style="text-align: left"><a href="#" onclick="javascript: updatePageSytle('le-frog')">
			<img src="${serverPath}javaScript/jquery/styleImg/le-frog.jpg" style="width: 50px; border: 0px;" />
		</a></li>
		<li style="text-align: left"><a href="#" onclick="javascript: updatePageSytle('start')">
			<img src="${serverPath}javaScript/jquery/styleImg/start.jpg" style="width: 50px; border: 0px;" />
		</a></li>
		<li style="text-align: left"><a href="#" onclick="javascript: updatePageSytle('sunny')">
			<img src="${serverPath}javaScript/jquery/styleImg/sunny.jpg" style="width: 50px; border: 0px;" />
		</a></li>
		<li style="text-align: left"><a href="#" onclick="javascript: updatePageSytle('vader')">
			<img src="${serverPath}javaScript/jquery/styleImg/vader.jpg" style="width: 50px; border: 0px;" />
		</a></li>
	</ul>
	</div>
	
	<a tabindex="0" href="#search-engines" class="fg-button fg-button-icon-right ui-widget ui-state-default ui-corner-all" id="userSetMenu" style="float: right; margin-top: 18px;">
		<span class="ui-icon ui-icon-triangle-1-s"></span><b>用户菜单</b>
	</a>
	<div id="search-engines" class="hidden">
	<ul>
		<li><a href="#" onclick="javascript: loadUpdatePasswordPage()">修改密码</a></li>
		<li><a href="#" onclick="javascript: $('#handleMessageDiv').html('该功能开发中!'); $('#handleMessageDiv').dialog('open');">系统消息</a></li>
		<li><a href="#" onclick="javascript: window.location = '${serverPath}exit'">退出系统</a></li>
	</ul>
	</div>
	<a id="updataInitPageA" href="${serverPath}basic/employe/employe!updatePassword" class="nyroModal"></a>
	<div style="float: left; margin-left: 20px; margin-top: 70px; font-size: 12px;">
		当前用户:&nbsp;${serverEmp.id}
		<c:if test='${serverEmp.name != "" && serverEmp.name != null}'>/&nbsp;${serverEmp.name}</c:if>
	</div>
</body>
</html>
