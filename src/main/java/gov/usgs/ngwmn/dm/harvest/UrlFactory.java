package gov.usgs.ngwmn.dm.harvest;

import gov.usgs.ngwmn.WellDataType;
import gov.usgs.ngwmn.dm.cache.Specifier;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import org.apache.commons.lang.StringUtils;


public class UrlFactory {
	
	private static Map<WellDataType, String> urls = new HashMap<WellDataType, String>();
	
	static {
		Properties props = new Properties();
		ClassLoader loader = UrlFactory.class.getClassLoader();
		try {
			props.load(loader.getResourceAsStream("well-url.properties"));
		} catch (IOException e) {
			throw new RuntimeException("The ", e);
		}
		String baseUrl = (String)props.get("BASE");
		for (WellDataType type : WellDataType.values()) {
			String url = baseUrl + (String) props.get( type.toString() );
			urls.put(type, url);
		}
	}

	public String makeUrl(Specifier spec) {
		Specifier.check(spec);
		
		String url = null;
		for (WellDataType type : WellDataType.values()) {
			url = urls.get(type);
			if ( ! StringUtils.isEmpty(url) ) {
				url = injectParams(url, spec.getAgencyID(), spec.getFeatureID());
				break;
			}
		}
		if ( StringUtils.isEmpty(url) ) {
			throw new RuntimeException("UrlFactory failed to construct a url for " + spec);
		}
		return url;
	}

	String injectParams(String url, String agencyID, String featureID) {
		url = url.replace("<agencyId>", agencyID);
		url = url.replace("<featureId>", featureID);
		return url;
	}
	
}
