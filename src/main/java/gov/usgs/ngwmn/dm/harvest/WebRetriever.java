package gov.usgs.ngwmn.dm.harvest;


import gov.usgs.ngwmn.dm.DataFetcher;
import gov.usgs.ngwmn.dm.cache.Specifier;
import gov.usgs.ngwmn.dm.io.CopyInvoker;
import gov.usgs.ngwmn.dm.io.Pipeline;

import java.io.InputStream;

import org.apache.commons.httpclient.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WebRetriever implements DataFetcher {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());

	private UrlFactory urlFactory = new UrlFactory();
	
	@Override
	public boolean configureInput(Specifier spec, Pipeline pipe)
			throws Exception {
		Specifier.check(spec);
		
		pipe.setInvoker(new CopyInvoker());
		
		String url = urlFactory.makeUrl(spec);
		
		logger.info("Fetching data for {} from {}", spec, url);
		
		Harvester harvester = new Harvester();
		int statusCode = harvester.wget(url);
		
        if (statusCode != HttpStatus.SC_OK) {
        	// TODO pipe status or exception? or
        	return false;
        }
		InputStream is = harvester.getInputStream();
		// it's zero, no help here  logger.info("response stream available {}", is.available());
		pipe.setInputStream(is);
		
		return true;
	}

}
