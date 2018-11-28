package com.testpkg.arkx.arkxProject;

import java.util.List;
import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rate {

@SerializedName("name")
@Expose
private String name;
@SerializedName("code")
@Expose
private String code;
@SerializedName("country_code")
@Expose
private String countryCode;
@SerializedName("periods")
@Expose
private List<Period> periods = null;

public String getName() {
return name;
}

public void setName(String name) {
this.name = name;
}

public String getCode() {
return code;
}

public void setCode(String code) {
this.code = code;
}

public String getCountryCode() {
return countryCode;
}

public void setCountryCode(String countryCode) {
this.countryCode = countryCode;
}

public List<Period> getPeriods() {
return periods;
}

public void setPeriods(List<Period> periods) {
this.periods = periods;
}

}