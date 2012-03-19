package gov.usgs.ngwmn.dm.cache;

import java.io.IOException;
import java.io.OutputStream;

public class Loader {

	private Cache c;
	
	public OutputStream destination(Specifier well) 
			throws IOException
	{
		return c.destination(well);
	}

	public Cache getCache() {
		return c;
	}

	public Loader(Cache c) {
		super();
		this.c = c;
	}

}
