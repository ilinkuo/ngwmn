package gov.usgs.ngwmn.functional;

import static org.junit.Assert.*;

import java.io.File;
import java.io.IOException;

import gov.usgs.ngwmn.dm.DataManagerServlet;

import org.junit.BeforeClass;
import org.junit.Test;
import org.xml.sax.SAXException;

import com.meterware.httpunit.GetMethodWebRequest;
import com.meterware.httpunit.HttpNotFoundException;
import com.meterware.httpunit.WebRequest;
import com.meterware.httpunit.WebResponse;
import com.meterware.servletunit.ServletRunner;
import com.meterware.servletunit.ServletUnitClient;

public class BasicServletTest {

	@BeforeClass
	public static void clearCache() {
		File c = new File("/tmp/gwdp-cache");
		if (c.exists() && c.isDirectory()) {
			for (File f : c.listFiles()) {
				boolean did = f.delete();
				if (did) {
					System.out.printf("Deleted cache file %s\n", f);
				} else {
					System.out.printf("Could not delete cache file %s\n", f);
				}
			}
		}
	}
	
	@Test
	public void testWithData() throws Exception {
		ServletRunner sr = new ServletRunner(this.getClass().getResourceAsStream("servlet-test-web.xml"), "/ngwmn");
		
		ServletUnitClient sc = sr.newClient();
		WebRequest req = new GetMethodWebRequest("http://localhost:8080/ngwmn/data?featureID=383453089545001&agency_cd=USGS");
		WebResponse resp = sc.getResponse(req);
		assertNotNull("response", resp);
		
		for (String hn : resp.getHeaderFieldNames()) {
			System.out.printf("Header %s:%s\n", hn, resp.getHeaderField(hn));
		}
		String body = resp.getText();
		System.out.printf("contentLength=%d,size=%d\n", resp.getContentLength(), body.length());
		assertTrue("response size", body.length() > 10000);
	}

	@Test
	public void testWithNoData() throws Exception {
		// this site exists, but has no data (on 2012/03/23)
		ServletRunner sr = new ServletRunner(this.getClass().getResourceAsStream("servlet-test-web.xml"), "/ngwmn");
		
		ServletUnitClient sc = sr.newClient();
		WebRequest req = new GetMethodWebRequest("http://localhost:8080/ngwmn/data?featureID=440713089320801&agency_cd=USGS");
		WebResponse resp = sc.getResponse(req);
		assertNotNull("response", resp);
		
		for (String hn : resp.getHeaderFieldNames()) {
			System.out.printf("Header %s:%s\n", hn, resp.getHeaderField(hn));
		}
		String body = resp.getText();
		System.out.printf("contentLength=%d,size=%d\n", resp.getContentLength(), body.length());
		assertTrue("response size", body.length() > 2000);
	}

	@Test(expected=HttpNotFoundException.class)
	public void testNonSite() throws Exception {
		// this site does not exist, so we expect an Exception when fetching
		ServletRunner sr = new ServletRunner(this.getClass().getResourceAsStream("servlet-test-web.xml"), "/ngwmn");
		
		ServletUnitClient sc = sr.newClient();
		WebRequest req = new GetMethodWebRequest("http://localhost:8080/ngwmn/data?featureID=NOSUCHSITE&agency_cd=USGS");
		WebResponse resp = sc.getResponse(req);
		assertFalse("expected exception", true);
	}

}
