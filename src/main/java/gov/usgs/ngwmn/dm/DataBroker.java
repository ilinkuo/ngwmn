package gov.usgs.ngwmn.dm;

import gov.usgs.ngwmn.dm.TeeOutputStream;
import gov.usgs.ngwmn.dm.cache.Specifier;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.security.InvalidParameterException;

public class DataBroker {

	private DataFetcher harvester;
	private DataFetcher retriever;

	private DataLoader  loader;
	
	
	public void fetchWellData(Specifier spec, OutputStream out) throws Exception {
		Pipeline pipe = new Pipeline();

		validate(spec);
		
		pipe.setOutputStream(out);
		boolean success = fetchWellData(retriever, spec, pipe);
		
		if ( ! success) {
			out = new TeeOutputStream(out, loader.getOutputStream(spec));
			pipe.setOutputStream(out);
			success = fetchWellData(harvester, spec, pipe); 
		}
		
		if ( ! success) {
			attachDataNotFoundMsg(pipe);
		}
		pipe.invoke();
	}
	
	private void attachDataNotFoundMsg(Pipeline pipe) {
		
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
	
	void validate(Specifier spec) throws Exception {
		if (retriever == null && harvester == null) 
			throw new NullPointerException("At least one Data Fetcher is required");
		if ( isEmpty(spec.getFeatureID()) ) 
			throw new InvalidParameterException("Well Site Id may not be null");
		if ( spec.getTypeID() != null ) 
			throw new InvalidParameterException("Well data type Id may not be null");
	}
	
	boolean fetchWellData(DataFetcher dataFetcher, Specifier spec, Pipeline pipe) throws Exception {
		if (dataFetcher != null) {
			return dataFetcher.fetchWellData(spec, pipe);
		}
		return false;
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
