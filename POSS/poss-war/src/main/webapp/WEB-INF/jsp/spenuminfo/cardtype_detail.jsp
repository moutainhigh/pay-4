<%@ page contentType="text/html;charset=UTF-8"%>
<%@ include file="/common/taglibs.jsp"%>
<body>

	<table class="inputTable" width="400" border="0" cellspacing="0" cellpadding="3" align="center">
	<tr>	
		<th width="80" nowrap="nowrap">名称</th>
		<td >
			${dto.enumName}
		</td>
	</tr>
	<tr>	
		<th >代码</th>
		<td >
			${dto.enumCode}
		</td>
	</tr>
	<tr>	
		<th >类型</th>
		<td >
		预付卡
		</td>
	</tr>
	<tr>	
		<th >备用一</th>
		<td >
		<div  style="width:100%;word-break : break-all ">
			${dto.value1}
		</div>			
		</td>
	</tr>
	<tr>	
		<th >备用二</th>
		<td >
		<div  style="width:100%;word-break : break-all ">
			${dto.value2}
		</div>			
		</td>
	</tr>	
	</table>
</body>

