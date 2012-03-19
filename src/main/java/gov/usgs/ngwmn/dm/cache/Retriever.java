package gov.usgs.ngwmn.dm.cache;

import java.io.IOException;
import java.io.OutputStream;

public class Retriever {
	private Cache c;
	
	public Retriever(Cache c) {
		this.c = c;
	}

	public Statistics get(Specifier spec, OutputStream puttee) 
		throws IOException
	{
		return c.get(spec, puttee);
	}
	
}
