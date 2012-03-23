package gov.usgs.ngwmn.dm.harvest;


import java.io.InputStream;

import gov.usgs.ngwmn.dm.DataFetcher;
import gov.usgs.ngwmn.dm.cache.Specifier;
import gov.usgs.ngwmn.dm.io.CopyInvoker;
import gov.usgs.ngwmn.dm.io.Pipeline;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Harvester implements DataFetcher {
	
	private static Logger logger = LoggerFactory.getLogger(Harvester.class);

	private UrlFactory urlFactory = new UrlFactory();
	
	@Override
	public boolean configureInput(Specifier spec, Pipeline pipe)
			throws Exception {
		Specifier.check(spec);
		
		pipe.setInvoker(new CopyInvoker());
		
		String url = urlFactory.makeUrl(spec);
		
		logger.info("Fetching data for {} from {}", spec, url);
		
		HttpClient client = new HttpClient();
		HttpMethod method = new GetMethod(url);
		int statusCode = client.executeMethod(method);
		
        if (statusCode != HttpStatus.SC_OK) {
        	// TODO pipe status or exception? or
        	return false;
        }
		InputStream is = method.getResponseBodyAsStream();
		// it's zero, no help here  logger.info("response stream available {}", is.available());
		pipe.setInputStream(is);
		
		return true;
	}

}
