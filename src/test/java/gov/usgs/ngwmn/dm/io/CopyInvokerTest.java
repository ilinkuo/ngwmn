package gov.usgs.ngwmn.dm.io;

import static org.junit.Assert.*;

import gov.usgs.ngwmn.dm.cache.PipeStatistics;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;


import org.junit.Before;
import org.junit.Test;

public class CopyInvokerTest extends GenericInvokerTest {

	protected Invoker getVictim() {
		return new CopyInvoker();
	}
	
	@Before
	public void setup() {
		victim = getVictim();
	}
	
	@Test
	public void testInvoke() {
		String sample = "Hello";
		InputStream is = new ByteArrayInputStream(sample.getBytes());
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		
		Pipeline pl = new Pipeline();
		pl.setInputStream(is);
		pl.setOutputStream(os);
		pl.setInvoker(victim);
		try {
			pl.invoke();
			assertEquals("noted success", PipeStatistics.Status.DONE, pl.getStatistics().getStatus());
			assertTrue("stream closed", true);
			assertEquals("contents", sample, os.toString());
			assertEquals("count", sample.length(), pl.getStatistics().getCount());
		} catch (IOException ioe) {
			assertEquals("noted failure", PipeStatistics.Status.FAIL, pl.getStatistics().getStatus());
		}
	}

}
