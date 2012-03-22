package gov.usgs.ngwmn.dm.cache;

import gov.usgs.ngwmn.dm.DataLoader;

import java.io.IOException;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Loader 
implements DataLoader {

	private Cache cache;
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
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

	@Override
	public OutputStream getOutputStream(Specifier spec) {
		try {
			return destination(spec);
		} catch (IOException ioe) {
			logger.error("Problem building output stream for spec " + spec, ioe);
			throw new RuntimeException(ioe);
		}
	}

}
