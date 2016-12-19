package com.pay.gateway.model;

import org.apache.commons.lang.builder.ToStringBuilder;

import java.util.List;

/**
 * Created by cuber.huang on 2016/7/20.
 */
public class PayGate {
    private GeneralData generalData;
    private List<Condition> conditions;

    public GeneralData getGeneralData() {
        return generalData;
    }

    public void setGeneralData(GeneralData generalData) {
        this.generalData = generalData;
    }

    public List<Condition> getConditions() {
        return conditions;
    }

    public void setConditions(List<Condition> conditions) {
        this.conditions = conditions;
    }

    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this);
    }
}
