package com.testpkg.arkx.arkxProject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Period {

@SerializedName("effective_from")
@Expose
private String effectiveFrom;
@SerializedName("rates")
@Expose
private Rates rates;

public String getEffectiveFrom() {
return effectiveFrom;
}

public void setEffectiveFrom(String effectiveFrom) {
this.effectiveFrom = effectiveFrom;
}

public Rates getRates() {
return rates;
}

public void setRates(Rates rates) {
this.rates = rates;
}

}
