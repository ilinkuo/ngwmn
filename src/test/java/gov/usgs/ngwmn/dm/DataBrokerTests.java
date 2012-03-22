package gov.usgs.ngwmn.dm;

import static org.junit.Assert.assertTrue;
import gov.usgs.ngwmn.WellDataType;
import gov.usgs.ngwmn.dm.cache.Specifier;

import org.junit.Before;
import org.junit.Test;


public class DataBrokerTests {

	Specifier spec;
	
	@Before
	public void setUp() {
		spec = new Specifier();
		spec.setAgencyID("agency");
		spec.setFeatureID("well");
		spec.setTypeID(WellDataType.LOG);
	}

	@Test
	public void test_validation_noDataFetchers() {
		DataBroker broker = new DataBroker();
		try {
			broker.check(spec);
			assertTrue(false);
		} catch (NullPointerException e) {
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	
	
}
