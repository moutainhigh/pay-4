/**
 * 
 */
package com.pay.poss.tags;

import java.text.SimpleDateFormat;
import java.util.Calendar;

import javax.servlet.jsp.JspException;
import javax.servlet.jsp.tagext.TagSupport;

import com.pay.util.StringUtil;

public class DateTag extends TagSupport {
	private static final long serialVersionUID = -2312310581852395045L;
	private String value;

	@Override
	public int doStartTag() throws JspException {

		if(StringUtil.isEmpty(value)){
			return super.doStartTag();
		}
		
		try {
			String vv = "" + value;
			long time = Long.valueOf(vv);
			Calendar c = Calendar.getInstance();
			c.setTimeInMillis(time);
			SimpleDateFormat dateformat = new SimpleDateFormat(
					"yyyy-MM-dd HH:mm:ss");
			String s = dateformat.format(c.getTime());
			pageContext.getOut().write(s);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return super.doStartTag();
	}

	public void setValue(String value) {
		this.value = value;
	}
}
