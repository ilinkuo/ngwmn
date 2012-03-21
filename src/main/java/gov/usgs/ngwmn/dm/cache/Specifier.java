package gov.usgs.ngwmn.dm.cache;

import java.util.EnumSet;
import java.util.Set;

import gov.usgs.ngwmn.WellDataType;

public class Specifier {
//	private String agency;
//	private String well;
	private String featureID;
	private WellDataType typeID;
	
//	public String getAgency() {
//		return agency;
//	}
//	public void setAgency(String agency) {
//		this.agency = agency;
//	}
//	public String getWell() {
//		return well;
//	}
//	public void setWell(String well) {
//		this.well = well;
//	}
	
	public String getFeatureID() {
		return featureID;
	}
	public void setFeatureID(String featureID) {
		this.featureID = featureID;
	}
	
	public synchronized WellDataType getTypeID() {
		return typeID;
	}
	
	public void setTypeID(WellDataType typeID) {
		this.typeID = typeID;
	}
	
	@Override
	public String toString() {
		return "Specifier [featureID=" + featureID + " typeID=" + typeID + "]";
	}

	
}
