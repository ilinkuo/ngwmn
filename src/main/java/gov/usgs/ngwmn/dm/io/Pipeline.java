package gov.usgs.ngwmn.dm.io;


import gov.usgs.ngwmn.dm.cache.PipeStatistics;
import gov.usgs.ngwmn.dm.cache.PipeStatistics.Status;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Pipeline {
	private Invoker invoker;
	private InputStream is;
	private OutputStream os;
	private PipeStatistics statistics = new PipeStatistics();
	
	public void setInputStream(InputStream in) {
		is = in;
	}
	
	public void setOutputStream(OutputStream out) {
		os = out;
	}
	public OutputStream getOutputStream() {
		return os;
	}
	
	public void setInvoker(Invoker invoke) {
		invoker = invoke;
	}
	
	// TODO consider implementing Runnable or Callable to make it easy to multithread input for multiple site download
	
	public void invoke() throws IOException {
		statistics.markStart();
		try {
			invoker.invoke(is,os, statistics);
			statistics.setStatus(Status.DONE);
		} catch (IOException ioe) {
			statistics.setStatus(Status.FAIL);
			throw ioe;
		}
	}
	
	public PipeStatistics getStatistics() {
		return statistics;
	}
}
