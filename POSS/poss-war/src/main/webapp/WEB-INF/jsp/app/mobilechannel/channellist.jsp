<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
	
</head>

   <table id=grid style="BORDER-TOP-WIDTH: 0px; FONT-WEIGHT: normal; BORDER-LEFT-WIDTH: 0px; BORDER-LEFT-COLOR: #cccccc; BORDER-BOTTOM-WIDTH: 0px; BORDER-BOTTOM-COLOR: #cccccc; WIDTH: 100%; BORDER-TOP-COLOR: #cccccc; FONT-STYLE: normal; BACKGROUND-COLOR: #cccccc; BORDER-RIGHT-WIDTH: 0px; TEXT-DECORATION: none; BORDER-RIGHT-COLOR: #cccccc" cellSpacing=1 cellPadding=2 rules=all border=0>
              <tbody>
              <tr style="FONT-WEIGHT: bold; FONT-STYLE: normal; BACKGROUND-COLOR: #eeeeee; TEXT-DECORATION: none">
                <td>渠道编号</td>
                <td>渠道名称</td>
                <td>渠道折扣</td>
                <td>系统科目账户</td>
                <td>渠道状态</td>
                <td>渠道打开时间</td>
                <td>渠道关闭时间</td>
                <td>充值类型</td>
                <td>运营商</td>
                <td>操作</td>
               </tr>
              <c:forEach items="${page}" var="mobilechannel">
                 <tr style="FONT-WEIGHT: normal; FONT-STYLE: normal; BACKGROUND-COLOR: white; TEXT-DECORATION: none">
                   <td onClick="showDetail(${mobilechannel.id});"><a href="#">${mobilechannel.code}</a></td>
                   <td>${mobilechannel.name}</td>
                   <td>${mobilechannel.discount}</td>
                   <td>${mobilechannel.memberAccountCode}</td>
                   <td>
	                   <c:if test="${mobilechannel.status eq 1}">打开</c:if>
	                   <c:if test="${mobilechannel.status eq 0}">关闭</c:if>
                   </td>
                   <td>${mobilechannel.startTime}</td>
                   <td>${mobilechannel.closeTime}</td>
                   <td>
                 	<c:if test="${mobilechannel.chargeType eq 0}">慢充</c:if>
                   	<c:if test="${mobilechannel.chargeType eq 1}">快充</c:if>
                   </td>
                  <td>
                 	<c:if test="${mobilechannel.bossType eq 0}">移动</c:if>
                   	<c:if test="${mobilechannel.bossType eq 1}">联通</c:if>
                   	<c:if test="${mobilechannel.bossType eq 2}">电信</c:if>
                   </td>
                   <td>
                   <a href="mobileChannel.do?method=updStatus&id=${mobilechannel.id}&status=1"><c:if test="${mobilechannel.status eq 0}">打开</c:if></a>
                   <a href="mobileChannel.do?method=updStatus&id=${mobilechannel.id}&status=0"><c:if test="${mobilechannel.status eq 1}">关闭</c:if></a>
                   </td>
			     </tr>
              </c:forEach>
               <c:if test="${empty page}">
                  <tr style="FONT-WEIGHT: normal; FONT-STYLE: normal; BACKGROUND-COLOR: white; TEXT-DECORATION: none">
                    <td colspan="16" align="center">没有记录</td>
              </tr>
              </c:if>
           </tbody>
           </table>