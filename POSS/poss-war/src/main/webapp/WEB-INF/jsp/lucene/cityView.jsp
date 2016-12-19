<%@ page contentType="text/html;charset=UTF-8" language="java"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<select id="city" name="city">
	<option value="">请选择</option>
	<c:forEach items="${citys}" var="city">
		<option value="${city.cityname}">${city.cityname}</option>
	</c:forEach>
</select>