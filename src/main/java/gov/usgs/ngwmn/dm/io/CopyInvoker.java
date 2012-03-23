package gov.usgs.ngwmn.dm.io;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import gov.usgs.ngwmn.dm.cache.PipeStatistics;

public class CopyInvoker implements Invoker {

	protected Logger logger = LoggerFactory.getLogger(this.getClass());

	@Override
	public void invoke(InputStream is, OutputStream os, PipeStatistics stats)
			throws IOException {
		int ct = IOUtils.copy(is,os);
		stats.incrementCount(ct);
		logger.info("Copied input to destination, stats={}", new Object[]{stats});
	}

}
