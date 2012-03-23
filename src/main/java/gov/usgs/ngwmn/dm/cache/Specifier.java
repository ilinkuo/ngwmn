package gov.usgs.ngwmn.dm.cache;


import java.security.InvalidParameterException;

import org.apache.commons.lang.StringUtils;

import gov.usgs.ngwmn.WellDataType;

public class Specifier {
	private String agencyID;
	private String featureID;
	private WellDataType typeID;
	
	public String getAgencyID() {
		return agencyID;
	}
	public void setAgencyID(String agency) {
		this.agencyID = agency;
	}
	
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
		StringBuilder builder = new StringBuilder();
		builder.append("Specifier [agencyID=").append(agencyID)
				.append(", featureID=").append(featureID)
				.append(", typeID=").append(typeID).append("]");
		return builder.toString();
	}

	public static void check(Specifier spec) {
		if ( StringUtils.isEmpty(spec.getAgencyID()) ) 
			throw new InvalidParameterException("Well agency Id is required.");
		if ( StringUtils.isEmpty(spec.getFeatureID()) ) 
			throw new InvalidParameterException("Well Feature/Site Id is required.");
		if ( spec.getTypeID() == null ) 
			throw new InvalidParameterException("Well data type Id is required.");
	}
	
}
