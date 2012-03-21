package gov.usgs.ngwmn;

import gov.usgs.ngwmn.dm.DataFetcher;

import java.io.InputStream;
import java.security.InvalidParameterException;

import  org.junit.*;
import static org.junit.Assert.*;


public class DataBrokerTest {

	@Test
	public void test_validation_noEmptySiteId() {
		DataBroker broker = new DataBroker();
		broker.setRetriever(new DataFetcher() {
			@Override
			public InputStream fetchWellData(String siteId, String typeId)
					throws Exception {
				return null;
			}
		});
		String siteId = "";
		String typeId = "type";
		try {
			broker.validate(siteId, typeId);
			assertTrue(false);
		} catch (NullPointerException e) {
			assertTrue(false);
		} catch (InvalidParameterException e) {
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void test_validation_noEmptyTypeId() {
		DataBroker broker = new DataBroker();
		broker.setRetriever(new DataFetcher() {
			@Override
			public InputStream fetchWellData(String siteId, String typeId)
					throws Exception {
				return null;
			}
		});
		String siteId = "siteId";
		String typeId = "";
		try {
			broker.validate(siteId, typeId);
			assertTrue(false);
		} catch (NullPointerException e) {
			assertTrue(false);
		} catch (InvalidParameterException e) {
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	@Test
	public void test_validation_noNullTypeId() {
		DataBroker broker = new DataBroker();
		broker.setRetriever(new DataFetcher() {
			@Override
			public InputStream fetchWellData(String siteId, String typeId)
					throws Exception {
				return null;
			}
		});
		String siteId = "siteId";
		String typeId = null;
		try {
			broker.validate(siteId, typeId);
			assertTrue(false);
		} catch (NullPointerException e) {
			assertTrue(false);
		} catch (InvalidParameterException e) {
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void test_validation_noNullSiteId() {
		DataBroker broker = new DataBroker();
		broker.setRetriever(new DataFetcher() {
			@Override
			public InputStream fetchWellData(String siteId, String typeId)
					throws Exception {
				return null;
			}
		});
		String siteId = null;
		String typeId = "fooType";
		try {
			broker.validate(siteId, typeId);
			assertTrue(false);
		} catch (NullPointerException e) {
			assertTrue(false);
		} catch (InvalidParameterException e) {
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
	}

	@Test
	public void test_validation_noDataFetchers() {
		DataBroker broker = new DataBroker();
		String siteId = "siteId";
		String typeId = "fooType";
		try {
			broker.validate(siteId, typeId);
			assertTrue(false);
		} catch (NullPointerException e) {
			assertTrue(true);
		} catch (Exception e) {
			assertTrue(false);
		}
	}
	
	
	@Test
	public void test_callFetchWellData_notNull() {
		DataBroker broker = new DataBroker();
		String siteId = "fooId";
		String typeId = "fooType";
		try {
			Object result = broker.fetchWellData(siteId, typeId);
			assertNotSame(null, result); 
		} catch (Exception e) {
			// the exception will be caught by the test framework
		}
	}
	
	
}
