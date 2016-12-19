/**
 * 
 */
package com.pay.poss.tags;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

public class DDateTag extends TagSupport {
	private static final long serialVersionUID = -2312310581852395045L;
	private Date value;
	private String format;

	@Override
	public int doStartTag() throws JspException {

		if(value==null){
			return super.doStartTag();
		}
		
		try {
			SimpleDateFormat dateformat = new SimpleDateFormat(
					format);
			String s = dateformat.format(value);
			pageContext.getOut().write(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.doStartTag();
	}

	public void setValue(Date value) {
		this.value = value;
	}
	public void setFormat(String format) {
		this.format = format;
	}
}
