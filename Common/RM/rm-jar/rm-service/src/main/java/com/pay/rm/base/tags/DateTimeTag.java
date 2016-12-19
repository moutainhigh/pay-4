package com.pay.rm.base.tags;

import javax.servlet.jsp.JspWriter;
import javax.servlet.jsp.tagext.BodyTagSupport;

import com.pay.util.DateUtil;



public class DateTimeTag extends BodyTagSupport {

	private static final long serialVersionUID = -9129235082814376390L;
	private String id; // id = name 
	private Boolean isShowTime = false; //是否显示时间
	private String format = "yyyy-MM-dd"; //时间格式, 例:"yyyy-MM-dd"
	private String value; //值
	private String onfocusId; //下一个聚焦控件的ID, 比如选择开始时间后自动聚焦到下一个选择结束时间的显示框
	private String minDate; //最小时间范围    (前面的日期不能大于后面的日期且两个日期都不能大于 2020-10-01)
	private String maxDate; //最大时间范围    (前面的日期不能大于后面的日期且两个日期都不能大于 2020-10-01)
	private Integer width = 153; //控件宽度
	private Boolean readonly = false; //
	private String caseId; //容器ID,如果是放在其他容器中就是用该参数和src设置路径
	private String src; //如果需要绑定日期到其他容器就需要制定图片路径
	
	public int doStartTag() {
		
		if (value != null && !"".equals(value)) {
			String tempFormat = isShowTime ? "yyyy-MM-dd HH:mm" : "yyyy-MM-dd";
			value = DateUtil.getDateStr(value, tempFormat);
		}
		
		if (isShowTime && "yyyy-MM-dd".equals(format))
			format += " HH:mm";
		
		//设置基本参数
		StringBuffer str = null;
		
		if (caseId != null && !"".equals(caseId))
			str = new StringBuffer("<img onclick=\"WdatePicker({el:$dp.$('" + caseId + "')})\" src=\"" + src + "\" width=\"16\" height=\"22\" align=\"absmiddle\" />");
		else {
			str = new StringBuffer("<input " + (readonly ? "readonly" : "") + " id=\"" + id + "\"" + " name=\"" + id + "\" class=\"Wdate\" style=\"width:" + width.toString() + "px\" onFocus=\"WdatePicker({");
			
			String str1 = ", dateFmt:'" + format + "'";
			str1 += (onfocusId != null && !"".equals(onfocusId) ?", onpicked:function(){$dp.$('" + onfocusId + "').focus()}" : "");
			str1 += (minDate != null && !"".equals(minDate) ? ", minDate:'" + minDate + "'" : "");
			str1 += (maxDate != null && !"".equals(maxDate) ? ", maxDate:'" + maxDate + "'" : "");
			str1 += (readonly ? ", readonly:true" : "");
			
			str.append(str1.substring(2, str1.length()));
			str.append("})\"" + (value != null && !"".equals(value) ? " value=\"" + value + "\"" : "") + " />");
		}
		
		try {
			// 获取输入流对象
			JspWriter out = pageContext.getOut();
			out.println(str.toString());
		} catch (Exception e) {
			e.printStackTrace();
		}

		return (BodyTagSupport.EVAL_BODY_BUFFERED);
	}

	public String getId() {
		return id;
	}

	public void setId(String id) {
		this.id = id;
	}

	public Boolean getIsShowTime() {
		return isShowTime;
	}

	public void setIsShowTime(Boolean isShowTime) {
		this.isShowTime = isShowTime;
	}

	public String getFormat() {
		return format;
	}

	public void setFormat(String format) {
		this.format = format;
	}

	public String getValue() {
		return value;
	}

	public void setValue(String value) {
		this.value = value;
	}

	public String getOnfocusId() {
		return onfocusId;
	}

	public void setOnfocusId(String onfocusId) {
		this.onfocusId = onfocusId;
	}

	public String getMinDate() {
		return minDate;
	}

	public void setMinDate(String minDate) {
		this.minDate = minDate;
	}

	public String getMaxDate() {
		return maxDate;
	}

	public void setMaxDate(String maxDate) {
		this.maxDate = maxDate;
	}

	public Integer getWidth() {
		return width;
	}

	public void setWidth(Integer width) {
		this.width = width;
	}

	public Boolean getReadonly() {
		return readonly;
	}

	public void setReadonly(Boolean readonly) {
		this.readonly = readonly;
	}

	public String getCaseId() {
		return caseId;
	}

	public void setCaseId(String caseId) {
		this.caseId = caseId;
	}

	public String getSrc() {
		return src;
	}

	public void setSrc(String src) {
		this.src = src;
	}
}
