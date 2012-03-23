package gov.usgs.ngwmn.dm.io;

import gov.usgs.ngwmn.dm.cache.PipeStatistics;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class TempfileOutputStream extends OutputStream {
	private PipeStatistics stat;
	private File endpoint;
	private File tempfile;
	
	private FileOutputStream delegate;
	
	private static Logger logger = LoggerFactory.getLogger(TempfileOutputStream.class);
	
	public TempfileOutputStream(File ep, File tmp, PipeStatistics s) {
		stat = s;
		if (stat == null) {
			stat = new PipeStatistics();
			stat.setStatus(Status.OPEN);
		}
		endpoint = ep;
		tempfile = tmp;
		
		try {
			delegate = new FileOutputStream(tmp);
		} catch (Exception e) {
			stat.setStatus(PipeStatistics.Status.FAIL);
		}
	}
	
	public Status getStatus() {
		return stat.getStatus();
	}
	
	public PipeStatistics getStatistics() {
		return stat;
	}
	
	public void close() throws IOException {
		if (getStatus() == PipeStatistics.Status.OPEN) {
			try {
				delegate.close();
				tempfile.renameTo(endpoint);
				logger.info("Made tempfile permanent for {}", endpoint);
				stat.setStatus(PipeStatistics.Status.DONE);
			} catch (IOException e) {
				logger.warn("Problem in closing tempfile for {}", endpoint);
				stat.setStatus(PipeStatistics.Status.FAIL);
				throw e;
			}
		}
	}

	public void flush() throws IOException {
		if (getStatus() == PipeStatistics.Status.OPEN) {
			try {
				delegate.flush();
			} catch (IOException ioe) {
				logger.warn("Problem in flushing tempfile for {}", endpoint);
				stat.setStatus(PipeStatistics.Status.FAIL);
				throw ioe;
			}
		}
	}

	public void write(byte[] b, int off, int len) throws IOException {
		if (getStatus() == PipeStatistics.Status.OPEN) {
			try {
				delegate.write(b, off, len);
			} catch (IOException ioe) {
				logger.warn("Problem in write.1 tempfile for {}", endpoint);
				stat.setStatus(PipeStatistics.Status.FAIL);
				throw ioe;
			}
		}
	}

	public void write(byte[] b) throws IOException {
		if (getStatus() == PipeStatistics.Status.OPEN) {
			try {
				delegate.write(b);
			} catch (IOException ioe) {
				logger.warn("Problem in write.2 tempfile for {}", endpoint);
				stat.setStatus(PipeStatistics.Status.FAIL);
				throw ioe;
			}
		}
	}

	public void write(int b) throws IOException {
		if (getStatus() == PipeStatistics.Status.OPEN) {
			try {
				delegate.write(b);
			} catch (IOException ioe) {
				logger.warn("Problem in write.3 tempfile for {}", endpoint);
				stat.setStatus(PipeStatistics.Status.FAIL);
				throw ioe;
			}
		}
	}
}
