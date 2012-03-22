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
		spec.setAgencyID("USGS");
		spec.setFeatureID("WELL0");
		spec.setTypeID(WellDataType.LOG);
		urls = new UrlFactory();
	}
	
	@Test
	public void test_makeUrl_forAllData() {
		spec.setTypeID(WellDataType.ALL);
		String url = urls.makeUrl(spec);
		System.out.println(url);
		assertTrue(url.startsWith("http://cida.usgs.gov/gw_data_portal/"));
		assertTrue(url.contains("export?"));
	}
	
	@Test
	public void test_makeUrl_forWaterQualityData() {
		spec.setTypeID(WellDataType.QUALITY);
		String url = urls.makeUrl(spec);
		System.out.println(url);
		assertTrue(url.contains("qw?"));
	}
	
	@Test
	public void test_makeUrl_forWaterLevelData() {
		spec.setTypeID(WellDataType.WATERLEVEL);
		String url = urls.makeUrl(spec);
		System.out.println(url);
		assertTrue(url.contains("sos?"));
	}
	
	@Test
	public void test_makeUrl_forLogData() {
		spec.setTypeID(WellDataType.LOG);
		String url = urls.makeUrl(spec);
		System.out.println(url);
		assertTrue(url.contains("wfs?"));
	}
		
	@Test
	public void test_makeUrl_containsAgencyAndFeature() {
		String url = urls.makeUrl(spec);
		assertTrue(url.contains(spec.getAgencyID()));
		assertTrue(url.contains(spec.getFeatureID()));
	}
	
	@Test
	public void test_makeUrl_startsWithBaseUrl() {
		String url = urls.makeUrl(spec);
		assertTrue(url.startsWith("http://cida.usgs.gov/cocoon/gin/gwdp/agency/"));
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
		assertEquals("USGSWELL0", actual);
	}	
	

}
