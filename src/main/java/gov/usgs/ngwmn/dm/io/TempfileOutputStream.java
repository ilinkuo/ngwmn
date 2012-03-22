package gov.usgs.ngwmn.dm.io;

import gov.usgs.ngwmn.dm.cache.PipeStatistics;
import gov.usgs.ngwmn.dm.cache.PipeStatistics.Status;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class TempfileOutputStream extends OutputStream {
	private PipeStatistics stat;
	private File endpoint;
	private File tempfile;
	
	private OutputStream delegate;
	private PipeStatistics.Status status = PipeStatistics.Status.OPEN;
	
	public TempfileOutputStream(File ep, File tmp, PipeStatistics s) {
		stat = s;
		endpoint = ep;
		tempfile = tmp;
		
		try {
			delegate = new FileOutputStream(tmp);
		} catch (Exception e) {
			stat.setStatus(PipeStatistics.Status.FAIL);
		}
	}
	
	public void close() throws IOException {
		if (status == PipeStatistics.Status.OPEN) {
			try {
				delegate.close();
				tempfile.renameTo(endpoint);
				stat.setStatus(PipeStatistics.Status.DONE);
			} catch (IOException e) {
				stat.setStatus(PipeStatistics.Status.FAIL);
				throw e;
			}
		}
	}

	public boolean equals(Object obj) {
		return delegate.equals(obj);
	}

	public void flush() throws IOException {
		if (status == PipeStatistics.Status.OPEN) {
			try {
				delegate.flush();
			} catch (IOException ioe) {
				stat.setStatus(PipeStatistics.Status.FAIL);
				throw ioe;
			}
		}
	}

	public int hashCode() {
		return delegate.hashCode();
	}

	public String toString() {
		return delegate.toString();
	}

	public void write(byte[] b, int off, int len) throws IOException {
		if (status == PipeStatistics.Status.OPEN) {
			try {
				delegate.write(b, off, len);
			} catch (IOException ioe) {
				stat.setStatus(PipeStatistics.Status.FAIL);
				throw ioe;
			}
		}
	}

	public void write(byte[] b) throws IOException {
		if (status == PipeStatistics.Status.OPEN) {
			try {
				delegate.write(b);
			} catch (IOException ioe) {
				stat.setStatus(PipeStatistics.Status.FAIL);
				throw ioe;
			}
		}
	}

	public void write(int b) throws IOException {
		if (status == PipeStatistics.Status.OPEN) {
			try {
				delegate.write(b);
			} catch (IOException ioe) {
				stat.setStatus(PipeStatistics.Status.FAIL);
				throw ioe;
			}
		}
	}
}