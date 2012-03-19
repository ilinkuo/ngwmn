package gov.usgs.ngwmn;

import java.io.IOException;
import java.io.InputStream;
import java.security.InvalidParameterException;

public class DataBroker implements DataFetcher {

	private DataFetcher harvester;
	private DataFetcher retriever;

	private DataLoader loader;
	
	
	public InputStream fetchWellData(String siteId, String typeId) throws Exception {
		
		validate(siteId, typeId);
		
		InputStream result = fetchWellData(retriever, siteId, typeId);
		
		if ( isEmpty(result) ) {
			
			result = fetchWellData(harvester, siteId, typeId); 
			
			if ( ! isEmpty(result) ) {
				loader.loadWellData(siteId, typeId, result);
			}
		}
		
		return result;
	}
	
	public void setHarvester(DataFetcher harvester) {
		this.harvester = harvester;
	}
	public void setRetriever(DataFetcher retriever) {
		this.retriever = retriever;
	}
	public void setLoader(DataLoader loader) {
		this.loader = loader;
	}
	
	void validate(String siteId, String typeId) throws Exception {
		if (retriever == null && harvester == null) 
			throw new NullPointerException("At least one Data Fetcher is required");
		if ( isEmpty(siteId) ) 
			throw new InvalidParameterException("Well Site Id may not be null");
		if ( isEmpty(typeId) ) 
			throw new InvalidParameterException("Well data type Id may not be null");
	}
	
	InputStream fetchWellData(DataFetcher dataFetcher, String siteId, String typeId) throws Exception {
		if (dataFetcher != null) {
			return dataFetcher.fetchWellData(siteId, typeId);
		}
		return null;
	}
	
	// to be replaced with util function - apache commons?
	boolean isEmpty(String string) {
		return string == null || string.length()==0;
	}
	// to be enh to return the read byte back
	boolean isEmpty(InputStream stream) {
		try {
			return stream == null || stream.read() == -1;
		} catch (IOException e) {
			return true;
		}
	}
}
