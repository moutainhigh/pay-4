<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>

<head>
	
</head>

   <table style="BORDER-TOP-WIDTH: 0px; FONT-WEIGHT: normal; BORDER-LEFT-WIDTH: 0px; BORDER-LEFT-COLOR: #cccccc; BORDER-BOTTOM-WIDTH: 0px; BORDER-BOTTOM-COLOR: #cccccc; WIDTH: 100%; BORDER-TOP-COLOR: #cccccc; FONT-STYLE: normal; BACKGROUND-COLOR: #cccccc; BORDER-RIGHT-WIDTH: 0px; TEXT-DECORATION: none; BORDER-RIGHT-COLOR: #cccccc" cellSpacing=1 cellPadding=2 rules=all border=0>
              <tbody>
              <tr  style="FONT-WEIGHT: bold; FONT-STYLE: normal; BACKGROUND-COLOR: #eeeeee; TEXT-DECORATION: none">
	                 <td>充值渠道</td>
					 <td>订单编号</td>
					 <td>子订单编号</td>
					 <td>订单状态</td>
					 <td>号码</td>
					 <td>省份</td>
					 <td>折扣率</td>
					 <td>折扣金额(元)</td>
					 <td>申请充值金额(元)</td>
					 <td>实充金额(元)</td>
					 <td>充值状态</td>
					 <td>WY订单号</td>
					  <td>提交时间</td>
                      <td>完成时间</td>
				      <td>支付时间</td>
					 <td>操作</td>
               </tr>
              
              <c:forEach items="${page.result}" var="mobileCharge">
                   <tr style="FONT-WEIGHT: normal; FONT-STYLE: normal; BACKGROUND-COLOR: white; TEXT-DECORATION: none">
	                 <td><c:if test="${mobileCharge.channelName eq '19pay'}">直充1</c:if></td>
	                 <td>${mobileCharge.orderId}</td>
	                 <td>${mobileCharge.itemId}</td>
	                 <td>
	                 <c:if test="${mobileCharge.orderStatus eq 101}">初始</c:if>
	                 <c:if test="${mobileCharge.orderStatus eq 111}">支付成功</c:if>
	                 <c:if test="${mobileCharge.orderStatus eq 112}">支付失败</c:if>
	                 <c:if test="${mobileCharge.orderStatus eq 113}">充值完成</c:if>  
	                 </td>
	                 <td>${mobileCharge.mobileNo}</td>
	                 <td>${mobileCharge.provinceName}(${mobileCharge.provinceCode})</td>
	                 <td>${mobileCharge.discount}</td>
	                 <td>${mobileCharge.amount}</td>
	                 <td>${mobileCharge.applyAmount}</td>
	                 <td>${mobileCharge.acceptedAmount}</td>
	                 <td>
	                   <c:if test="${mobileCharge.itemStatus eq 0}">初始</c:if>
	                   <c:if test="${mobileCharge.itemStatus eq 1}">充值中</c:if>
	                   <c:if test="${mobileCharge.itemStatus eq 2}">充值成功</c:if>
	                   <c:if test="${mobileCharge.itemStatus eq 3}">充值部分完成</c:if>
	                   <c:if test="${mobileCharge.itemStatus eq 4}">充值失败</c:if>  
	                 </td>
	                   <td>${mobileCharge.channelSeqId}</td>
	                   <td>${mobileCharge.requestDate}</td>
                       <td>${mobileCharge.completeDate}</td>
				       <td>${mobileCharge.receiveDate}</td>
                    <td>
                        <a href="personalBill.do?method=loadMobileBillsDetail&orderId=${mobileCharge.orderId}&itemId=${mobileCharge.itemId}" target="_target">详情</a>
                    </td>
			     </tr>
              </c:forEach> 
               <c:if test="${empty page}">
                  <tr style="FONT-WEIGHT: normal; FONT-STYLE: normal; BACKGROUND-COLOR: white; TEXT-DECORATION: none">
                    <td colspan="15" align="center">没有记录</td>
              </tr>
              </c:if>
           </tbody>
           </table>
           <li:pagination methodName="mobileBillsQuery" pageBean="${page}" sytleName="black2"/>
	


