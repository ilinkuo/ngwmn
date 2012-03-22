package gov.usgs.ngwmn.dm.io;

import static org.junit.Assert.*;
import gov.usgs.ngwmn.dm.cache.PipeStatistics;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;

import org.junit.Test;

public class PipelineTest extends Pipeline {

	@Test
	public void testClose() {
		String sample = "Hello";
		InputStream is = new ByteArrayInputStream(sample.getBytes());
		ByteArrayOutputStream os = new ByteArrayOutputStream();
		OpCountOutputStream cos = new OpCountOutputStream(os);
		Invoker victim = new GenericInvoker();
		
		Pipeline pl = new Pipeline();
		pl.setInputStream(is);
		pl.setOutputStream(cos);
		pl.setInvoker(victim);
		try {
			pl.invoke();
			assertEquals("noted success", PipeStatistics.Status.DONE, pl.getStatistics().getStatus());
			assertEquals("stream closed", 1, cos.getCloseCt());
			assertEquals("contents", sample, os.toString());
			assertEquals("count", sample.length(), pl.getStatistics().getCount());
			assertEquals("write byte count", sample.length(), cos.getWriteByteCt());
		} catch (IOException ioe) {
			assertEquals("noted failure", PipeStatistics.Status.FAIL, pl.getStatistics().getStatus());
		}
	}


}
