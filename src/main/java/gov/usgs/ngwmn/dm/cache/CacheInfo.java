package gov.usgs.ngwmn.dm.cache;

import java.util.Date;

public class CacheInfo {
	private Date created;
	private boolean exists;
	private Date modified;
	private long length;
	
	
	public CacheInfo(Date created, boolean exists, Date modified, long length) {
		super();
		this.created = created;
		this.exists = exists;
		this.modified = modified;
		this.length = length;
	}
	public Date getCreated() {
		return created;
	}
	public boolean isExists() {
		return exists;
	}
	public Date getModified() {
		return modified;
	}
	public long getLength() {
		return length;
	}
	
	
}
