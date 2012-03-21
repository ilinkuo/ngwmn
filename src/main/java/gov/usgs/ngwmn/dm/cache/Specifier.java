package gov.usgs.ngwmn.dm.cache;

import java.util.EnumSet;
import java.util.Set;

import gov.usgs.ngwmn.WellDataType;

public class Specifier {
//	private String agency;
//	private String well;
	private String featureID;
	private Set<WellDataType> typeID;
	
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
	
	public synchronized Set<WellDataType> getTypeIDs() {
		if (typeID == null) {
			typeID = EnumSet.noneOf(WellDataType.class);
		}
		return typeID;
	}
	public void addTypeID(WellDataType t) {
		Set<WellDataType> s = getTypeIDs();
		s.add(t);
	}
	
	public synchronized void setTypes(String[] types) {
		typeID = EnumSet.noneOf(WellDataType.class);
		for (String t : types) {
			WellDataType w = WellDataType.valueOf(t);
			typeID.add(w);
		}
	}
	
	
	public void setTypeIDs(Set<WellDataType> typeID) {
		this.typeID = typeID;
	}
	
	@Override
	public String toString() {
		return "Specifier [featureID=" + featureID + " typeID=" + typeID + "]";
	}

	
}
