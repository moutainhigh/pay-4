<%@ include file="/common/taglibs.jsp"%>
<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Frameset//EN" "http://www.w3.org/TR/html4/frameset.dtd">
<html>
<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=UTF-8">
<title>POSS后台管理</title>
</head>

<frameset id="topest" rows="155px,*" framespacing="0" border="0" frameborder="0">
	
  <frame name="topFrame"   target="contents" src="menu.do?method=head" scrolling="no" noresize>
  
  <frameset id="main" cols="200px,10,*">
    <frameset rows="13,*">
      <frame name="topMenu" target="_self" src="topMenu.htm" scrolling="No" noresize>
      <frame name="menuFrame" target="_self" src="" scrolling="Yes" noresize>
    </frameset>
    
    <frame name="buttonFrame" target="_self" src="frame.htm" scrolling="No" noresize  marginWidth=0 marginHeight=0>
    <frame name="bodyFrame" target="_self" src="body.htm" scrolling="yes" noresize>
    
  </frameset>
  
 
  
  <noframes>
  <body topmargin="0" leftmargin="0">
  <p>此网页使用了框架，但您的浏览器不支持框架。</p>
  </body>
  </noframes>
</frameset>

</html>