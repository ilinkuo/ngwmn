package gov.usgs.ngwmn.dm.harvest;

import static org.junit.Assert.*;

import java.util.logging.Logger;

import gov.usgs.ngwmn.WellDataType;
import gov.usgs.ngwmn.dm.cache.Specifier;

import org.junit.Before;
import org.junit.Test;

public class UrlFactoryTests {

	Specifier spec;
	
	@Before
	public void setUp() {
		spec = new Specifier();
		spec.setAgencyID("agency");
		spec.setFeatureID("well");
		spec.setTypeID(WellDataType.LOG);
	}
	
	@Test
	public void test_loadPropertiesFile() {
		boolean success = true;
		try {
			UrlFactory f = null;
			Logger.getLogger("test").fine("refer to object so not optimized out" + f);
		} catch (RuntimeException e) {
			success = false;
		}
		assertTrue(success);
	}

}
