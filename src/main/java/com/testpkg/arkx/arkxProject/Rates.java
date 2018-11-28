package com.testpkg.arkx.arkxProject;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Rates {

@SerializedName("super_reduced")
@Expose
private Double superReduced;
@SerializedName("reduced")
@Expose
private Double reduced;
@SerializedName("standard")
@Expose
private Double standard;
@SerializedName("reduced1")
@Expose
private Double reduced1;
@SerializedName("reduced2")
@Expose
private Double reduced2;
@SerializedName("parking")
@Expose
private Double parking;

public Double getReduced1() {
	return reduced1;
}

public void setReduced1(Double reduced1) {
	this.reduced1 = reduced1;
}

public Double getReduced2() {
	return reduced2;
}

public void setReduced2(Double reduced2) {
	this.reduced2 = reduced2;
}

public Double getParking() {
	return parking;
}

public void setParking(Double parking) {
	this.parking = parking;
}

public Double getSuperReduced() {
return superReduced;
}

public void setSuperReduced(Double superReduced) {
this.superReduced = superReduced;
}

public Double getReduced() {
return reduced;
}

public void setReduced(Double reduced) {
this.reduced = reduced;
}

public Double getStandard() {
return standard;
}

public void setStandard(Double standard) {
this.standard = standard;
}

}
