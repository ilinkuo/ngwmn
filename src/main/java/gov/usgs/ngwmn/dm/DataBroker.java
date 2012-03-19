package gov.usgs.ngwmn.dm;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import gov.usgs.ngwmn.dm.cache.Loader;
import gov.usgs.ngwmn.dm.cache.Retriever;
import gov.usgs.ngwmn.dm.cache.Specifier;
import gov.usgs.ngwmn.dm.cache.Statistics;
import gov.usgs.ngwmn.dm.cache.fs.FileCache;

public class DataBroker {

	private Retriever rtr;
	private Loader ldr;
	
	public Statistics get(Specifier spec, OutputStream puttee)
			throws IOException {
		return rtr.get(spec, puttee);
	}

	public void setCache(FileCache c) {
		rtr = new Retriever(c);	
		ldr = new Loader(c);
	}
	
	public void put(Specifier spec, InputStream is) 
			throws IOException 
	{
		OutputStream dest = ldr.destination(spec);
		
		Statistics s = FileCache.copyStream(is, dest);
	}
	
}
