package gov.usgs.ngwmn.dm.cache;

public class Specifier {
//	private String agency;
//	private String well;
	private String featureID;
	
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
	
	@Override
	public String toString() {
		return "Specifier [featureID=" + featureID + "]";
	}

	
}
