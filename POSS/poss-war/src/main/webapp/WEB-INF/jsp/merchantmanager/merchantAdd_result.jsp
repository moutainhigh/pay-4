<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>

<body>
<script type="text/javascript">
	 alert("${msg}");
	<c:if test="${isSuccess}">
		window.parent.location.href="enterpriseView.do?memberCode=${memberCode}";
	</c:if>
</script>
</body>

