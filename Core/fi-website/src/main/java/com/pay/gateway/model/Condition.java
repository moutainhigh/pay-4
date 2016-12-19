package com.pay.gateway.model;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by cuber.huang on 2016/7/20.
 */
public class Condition {
    private String expression;
    private String validateMapColumn;
    private List<Key> validationColumns;

    public String getExpression() {
        return expression;
    }

    public void setExpression(String expression) {
        this.expression = expression;
    }

    public String getValidateMapColumn() {
        return validateMapColumn;
    }

    public void setValidateMapColumn(String validateMapColumn) {
        this.validateMapColumn = validateMapColumn;
    }

    public List<Key> getValidationColumns() {
        return validationColumns;
    }

    public void setValidationColumns(List<Key> validationColumns) {
        this.validationColumns = validationColumns;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
