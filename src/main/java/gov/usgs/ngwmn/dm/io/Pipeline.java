package gov.usgs.ngwmn.dm.io;


import gov.usgs.ngwmn.dm.cache.PipeStatistics;
import gov.usgs.ngwmn.dm.cache.PipeStatisticsTest;
import gov.usgs.ngwmn.dm.cache.PipeStatistics.Status;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class Pipeline {
	Invoker invoker;
	InputStream is;
	OutputStream os;
	PipeStatistics statistics = new PipeStatistics();
	
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
