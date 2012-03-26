package gov.usgs.ngwmn.dm.cache;

import java.util.Date;

public class PipeStatistics {
	
	public static enum Status {
		OPEN(false),
		STARTED(false),
		FAIL(true),
		DONE(true);
	
		private boolean done;
		private boolean isDone() {
			return done;
		}
		Status(boolean isDone) {
			done = isDone;
		}
	}

	private int count;
	private PipeStatistics.Status status = Status.OPEN;
	private long start = 0;
	private long end = 0;
	private Class<?> calledBy;
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
		if (status.isDone()) {
			throw new RuntimeException("Use markEnd instead");
		}
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("PipeStatistics [count=").append(count)
				.append(", status=").append(status)
				.append(", start=").append(start)
				.append(", calledBy=").append(calledBy)
				.append(", specifier=").append(specifier)
				.append(", elapsedMSec=").append(getElapsedMSec())
				.append("]");
		return builder.toString();
	}
	
	public synchronized void markStart() {
		if (status != Status.OPEN) {
			throw new RuntimeException("Improper pre-start status");
		}
		status = Status.STARTED;
		start = System.currentTimeMillis();
	}
	
	public synchronized void markEnd(Status endStatus) {
		end = System.currentTimeMillis();
		if (status != Status.STARTED) {
			throw new RuntimeException("Improper pre-end status");
		}
		status = endStatus;
		this.notifyAll();
	}
	
	public synchronized Long getElapsedMSec() {
		if (start > 0 && end > 0) {
			return end-start;
		}
		return null;
	}

	public Class<?> getCalledBy() {
		return calledBy;
	}

	public void setCalledBy(Class<?> calledBy) {
		this.calledBy = calledBy;
	}

	public synchronized Date getStartDate() {
		if (start > 0) {
			return new Date(start);
		}
		return null;
	}

	public synchronized Date getEnd() {
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
