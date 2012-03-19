package gov.usgs.ngwmn.dm.cache;

import gov.usgs.ngwmn.dm.cache.fs.FileCache;

public class Statistics {
	private int count;
	private FileCache.Status status;
	
	public int getCount() {
		return count;
	}

	public void setCount(int count) {
		this.count = count;
	}

	public FileCache.Status getStatus() {
		return status;
	}

	public synchronized void setStatus(FileCache.Status status) {
		this.status = status;
		this.notifyAll();
	}
	

}
