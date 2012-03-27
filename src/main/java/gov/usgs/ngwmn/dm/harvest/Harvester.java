package gov.usgs.ngwmn.dm.harvest;


import java.io.InputStream;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.HttpMethod;
import org.apache.commons.httpclient.HttpStatus;
import org.apache.commons.httpclient.methods.GetMethod;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Harvester {
	
	private final Logger logger = LoggerFactory.getLogger(this.getClass());
	
	private InputStream is;
	private int statusCode;
	
	public InputStream getInputStream() {
		return is;
	}
	public int getStatusCode() {
		return statusCode;
	}
	
	public int wget(String url)
			throws Exception {
		logger.info("wget from {}",new Object[]{url});
		
		HttpClient client = new HttpClient();
		HttpMethod method = new GetMethod(url);
		
		statusCode = client.executeMethod(method);
		
        if (statusCode != HttpStatus.SC_OK) {
        	return statusCode;
        }
		is = method.getResponseBodyAsStream();
		// it's zero, no help here  logger.info("response stream available {}", is.available());
		return statusCode;
	}

}
