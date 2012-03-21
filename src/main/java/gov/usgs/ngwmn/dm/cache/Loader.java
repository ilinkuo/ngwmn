package gov.usgs.ngwmn.dm.cache;

import java.io.IOException;
import java.io.OutputStream;

public class Loader {

	private Cache cache;
	
	public OutputStream destination(Specifier well) 
			throws IOException
	{
		return cache.destination(well);
	}

	public Cache getCache() {
		return cache;
	}

	public Loader(Cache c) {
		super();
		this.cache = c;
	}

}
