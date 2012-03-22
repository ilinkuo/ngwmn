package gov.usgs.ngwmn.dm.harvest;

import static org.junit.Assert.*;


import gov.usgs.ngwmn.WellDataType;
import gov.usgs.ngwmn.dm.cache.Specifier;

import org.junit.Before;
import org.junit.Test;

public class UrlFactoryTests {

	Specifier  spec;
	UrlFactory urls;
	
	@Before
	public void setUp() {
		spec = new Specifier();
		spec.setAgencyID("agency");
		spec.setFeatureID("well");
		spec.setTypeID(WellDataType.LOG);
		urls = new UrlFactory();
	}
	
	@Test
	public void test_loadPropertiesFile() {
		boolean success = true;
		try {
			urls = new UrlFactory();
		} catch (RuntimeException e) {
			success = false;
		}
		assertTrue(success);
	}
	
	@Test
	public void test_injectParams() {
		String url = "<agencyId><featureId>";
		String actual = urls.injectParams(url, spec.getAgencyID(), spec.getFeatureID());
		assertEquals("agencywell", actual);
	}	
	

}
