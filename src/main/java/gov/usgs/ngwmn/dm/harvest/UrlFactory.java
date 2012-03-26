package gov.usgs.ngwmn.dm.harvest;

import gov.usgs.ngwmn.WellDataType;
import gov.usgs.ngwmn.dm.cache.Specifier;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Properties;

import com.google.common.base.Strings;


public class UrlFactory {
	
	private static Map<WellDataType, String> urls = new HashMap<WellDataType, String>();
	
	static {
		Properties props   = new Properties();
		ClassLoader loader = UrlFactory.class.getClassLoader();
		String urlFile     = "well-url.properties";
		try {
			props.load( loader.getResourceAsStream(urlFile) );
		} catch (IOException e) {
			throw new RuntimeException("The URL mappings file not found, " + urlFile, e);
		}
		String baseUrl = (String)props.get("BASE");
		for (WellDataType type : WellDataType.values()) {
			String url = (String) props.get( type.toString() );
			url = url.replace("<BASE>",baseUrl);
			urls.put(type, url);
		}
	}

	public String makeUrl(Specifier spec) {
		Specifier.check(spec);
		
		String url = urls.get( spec.getTypeID() );
		
		if ( Strings.isNullOrEmpty(url) ) {
			throw new RuntimeException("UrlFactory failed to construct a url for " + spec);
		}
		
		url = injectParams(url, spec.getAgencyID(), spec.getFeatureID());
		return url;
	}

	String injectParams(String url, String agencyID, String featureID) {
		url = url.replace("<agencyId>", agencyID);
		url = url.replace("<featureId>", featureID);
		return url;
	}
	
}
