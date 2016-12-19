<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/head.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8">
<title>Insert title here</title>
</head>
<body>
<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=utf-8" />
<meta name="author" content="Chomo" />
<link rel="start" href="http://www.14px.com" title="Home" />
<title>div仿框架布局</title>
<style type="text/css">
* {
	margin: 0;
	padding: 0;
	list-style: none;
}

html {
	height: 100%;
	overflow: hidden;
	background: #fff;
}

body {
	height: 100%;
	overflow: hidden;
	background: #fff;
}

div {
	background: #f60;
	line-height: 1.6;
}

.top {
	position: absolute;
	left: 10px;
	top: 10px;
	right: 10px;
	height: 50px;
}

.side {
	position: absolute;
	left: 10px;
	top: 70px;
	bottom: 70px;
	width: 200px;
	overflow: auto;
}

.main {
	position: absolute;
	left: 220px;
	top: 70px;
	bottom: 70px;
	right: 10px;
	overflow: auto;
}

.bottom {
	position: absolute;
	left: 10px;
	bottom: 10px;
	right: 10px;
	height: 50px;
}

/*---ie6---*/
html {
	_padding: 70px 10px;
}

.top {
	_height: 50px;
	_margin-top: -60px;
	_margin-bottom: 10px;
	_position: relative;
	_top: 0;
	_right: 0;
	_bottom: 0;
	_left: 0;
}

.side {
	_height: 100%;
	_float: left;
	_width: 200px;
	_position: relative;
	_top: 0;
	_right: 0;
	_bottom: 0;
	_left: 0;
}

.main {
	_height: 100%;
	_margin-left: 207px;
	_position: relative;
	_top: 0;
	_right: 0;
	_bottom: 0;
	_left: 0;
}

.bottom {
	_height: 50px;
	_margin-top: 10px;
	_position: relative;
	_top: 0;
	_right: 0;
	_bottom: 0;
	_left: 0;
}
</style>
</head>
<body>
<div class="top"></div>
<div class="side"><br />

</div>
<div class="main"><br />

</div>
<div class="bottom"></div>
</body>
</html>
</body>
</html>