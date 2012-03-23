package gov.usgs.ngwmn.dm.harvest;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.apache.commons.io.IOUtils;

import gov.usgs.ngwmn.dm.cache.PipeStatistics;
import gov.usgs.ngwmn.dm.io.Invoker;

public class CopyInvoker implements Invoker {

	@Override
	public void invoke(InputStream is, OutputStream os, PipeStatistics stats)
			throws IOException {
		int ct = IOUtils.copy(is,os);
		stats.incrementCount(ct);
	}

}
