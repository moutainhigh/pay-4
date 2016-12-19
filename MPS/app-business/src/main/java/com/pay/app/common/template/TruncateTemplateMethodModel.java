package com.pay.app.common.template;

import java.util.List;

import freemarker.template.TemplateMethodModel;
import freemarker.template.TemplateModelException;

public class TruncateTemplateMethodModel implements TemplateMethodModel {
	
    public Object exec(List arguments) throws TemplateModelException {
        return arguments.get(0).toString().substring(0,1);
    }


}
