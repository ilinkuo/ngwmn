package gov.usgs.ngwmn.dm.cache;

import java.io.IOException;
import java.io.OutputStream;

public class Retriever {
	private Cache cache;
	
	public Retriever(Cache c) {
		this.cache = c;
	}

	public Statistics get(Specifier spec, OutputStream puttee) 
		throws IOException
	{
		return cache.get(spec, puttee);
	}
	
}
