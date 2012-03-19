package gov.usgs.ngwmn.dm.cache;

import gov.usgs.ngwmn.dm.cache.fs.FileCache;

public class Statistics {
	private int count;
	private FileCache.Status status;
	
	public synchronized int getCount() {
		return count;
	}

	public synchronized void setCount(int count) {
		this.count = count;
	}
	
	public synchronized void incrementCount(int c) {
		this.count += c;
	}

	public synchronized FileCache.Status getStatus() {
		return status;
	}

	public synchronized void setStatus(FileCache.Status status) {
		this.status = status;
		this.notifyAll();
	}

	@Override
	public String toString() {
		return String.format("Statistics [count=%s, status=%s]", count, status);
	}
	
}
