package gov.usgs.ngwmn.dm.cache;

import static org.junit.Assert.*;
import gov.usgs.ngwmn.dm.cache.PipeStatistics.Status;

import org.junit.Test;
import org.junit.Before;
public class PipeStatisticsTest {

	private PipeStatistics victim;
	
	@Before
	public void setup() {
		victim = new PipeStatistics();
	}
	
	@Test
	public void testIncrementCount() {
		victim.setCount(999);
		victim.incrementCount(21);
		
		assertEquals("count",1020, victim.getCount());
	}

	@Test
	public void testSetStatus() {
		victim.setStatus(Status.OPEN);
		assertNull("pre-finish end time", victim.getEnd());
		assertFalse(victim.isDone());
		
		victim.markStart();
		victim.markEnd(Status.FAIL);
		assertNotNull("post-finish done time", victim.getEnd());
		
		assertTrue(victim.isDone());
	}

	@Test
	public void testGetElapsedMSec() throws Exception {
		assertNull("initial elapsed", victim.getElapsedMSec());
		
		victim.markStart();
		assertNull("elapsed time afgter start", victim.getElapsedMSec());
		
		Thread.sleep(200);
		victim.markEnd(Status.DONE);
		
		Long et = victim.getElapsedMSec();
		assertNotNull("elapsed time after finish", et);
		assertTrue("elapsed time is reasonable",et >= 200);
	}

}
