package gov.usgs.ngwmn.dm.io;

import gov.usgs.ngwmn.dm.cache.PipeStatistics;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public interface Invoker {
	void invoke(InputStream is, OutputStream os, PipeStatistics stats) throws IOException;
}
