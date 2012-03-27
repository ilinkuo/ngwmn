package gov.usgs.ngwmn.dm.io;

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
