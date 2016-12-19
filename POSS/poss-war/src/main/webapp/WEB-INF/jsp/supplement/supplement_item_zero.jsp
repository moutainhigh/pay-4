<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ include file="/common/taglibs.jsp"%>
<script type="text/javascript">

	function goonSupplement(){
		window.location.href = "supplement.upload.do?method=entryUpload";
	}

	function queryRemedyItemListByExample(){
		window.location.href = "supplement.upload.do?method=queryRemedyItemListByExample";
	}
</script>

<h1 align="left">手工补单</h1>
<p/>
您所上传的文件记录条数为0，请确认后上传。
您可能需要：
<br/>
<a href="#" onclick="goonSupplement()">继续手工补单</a>|
<a href="#" onclick="queryRemedyItemListByExample()">查看补单记录</a>|
<a href="<c:url value="/"/>" onclick="">返回首页</a>
