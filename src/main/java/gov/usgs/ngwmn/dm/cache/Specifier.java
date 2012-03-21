package gov.usgs.ngwmn.dm.cache;

public class Specifier {
//	private String agency;
//	private String well;
	private String featureID;
	private String typeID;
	
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
	
	public String getTypeID() {
		return typeID;
	}
	public void setTypeID(String typeID) {
		this.typeID = typeID;
	}
	@Override
	public String toString() {
		return "Specifier [featureID=" + featureID + " typeID=" + typeID + "]";
	}

	
}
