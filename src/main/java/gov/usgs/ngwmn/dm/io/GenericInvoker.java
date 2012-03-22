package gov.usgs.ngwmn.dm.io;

import gov.usgs.ngwmn.dm.cache.Statistics;
import gov.usgs.ngwmn.dm.cache.fs.FileCache;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class GenericInvoker implements Invoker {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());
	
	@Override
	public void invoke(InputStream is, OutputStream os) throws IOException {
		Statistics stat = FileCache.copyStream(is, os);
		logger.info("Copied {} to destination {}, stats={}", new Object[]{is, os, stat});
	}

}
