package gov.usgs.ngwmn.dm.cache;

import java.util.Date;

public class PipeStatistics {
	
	public static enum Status {
		OPEN(false),
		FAIL(true),
		DONE(true);
	
		private boolean done;
		Status(boolean isDone) {
			done = isDone;
		}
	}

	private int count;
	private PipeStatistics.Status status;
	private long start = 0;
	private long end = 0;
	private Class calledBy;
	private Specifier specifier;
	
	public synchronized int getCount() {
		return count;
	}

	public synchronized void setCount(int count) {
		this.count = count;
	}
	
	public synchronized void incrementCount(int c) {
		this.count += c;
	}

	public synchronized PipeStatistics.Status getStatus() {
		return status;
	}

	public synchronized void setStatus(PipeStatistics.Status status) {
		this.status = status;
		switch (status) {
		case OPEN:
			// not an end event
			break;
			
		default:
				markEnd();
		}
		this.notifyAll();
	}

	@Override
	public String toString() {
		return String.format("Statistics [count=%s, status=%s]", count, status);
	}
	
	public void markStart() {
		start = System.currentTimeMillis();
	}
	
	public void markEnd() {
		end = System.currentTimeMillis();
	}
	
	public Long getElapsedMSec() {
		if (start > 0 && end > 0) {
			return end-start;
		}
		return null;
	}

	public Class getCalledBy() {
		return calledBy;
	}

	public void setCalledBy(Class calledBy) {
		this.calledBy = calledBy;
	}

	public Date getStartDate() {
		if (start > 0) {
			return new Date(start);
		}
		return null;
	}

	public Date getEnd() {
		if (end > 0) {
			return new Date(end);
		}
		return null;
	}

	public boolean isDone() {
		return getStatus().done;
				
	}

	public Specifier getSpecifier() {
		return specifier;
	}

	public void setSpecifier(Specifier specifier) {
		this.specifier = specifier;
	}
	
}
