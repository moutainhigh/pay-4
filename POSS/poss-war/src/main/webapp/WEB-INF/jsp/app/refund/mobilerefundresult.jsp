<%@ page language="java" contentType="text/html; charset=utf-8" pageEncoding="utf-8"%>
<%@ include file="/common/taglibs.jsp"%>
<html>
<head>
<meta HTTP-EQUIV="Content-Type" CONTENT="text/html; charset=UTF-8">


</head>
<body>
    <h1 style="color:rgb(255,0,0)">
               <c:if test="${result eq true}">手工退款处理成功</c:if>
                <c:if test="${result eq false}">手工退款处理失败</c:if>
                </h1>
         <table class="table_list">
            <tr>
              <td colspan="2" align="center" style="border: 0px;"><button class="btn" type="button" onclick="javascript:history.go(-2)" >返回</button></td>
            </tr>
         </table>
</body>
</html>
