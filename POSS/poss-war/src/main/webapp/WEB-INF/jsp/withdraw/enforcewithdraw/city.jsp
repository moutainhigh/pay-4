<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<option value="" selected>---请选择城市---</option>
<c:forEach items="${cityList}" var="city">
	<option value="${city.citycode}">${city.cityname}</option>
</c:forEach>
