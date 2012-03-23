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
		super.testInvoke();
	}

}
