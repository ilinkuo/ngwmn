package gov.usgs.ngwmn.dm;

import gov.usgs.ngwmn.dm.cache.Specifier;
import gov.usgs.ngwmn.dm.io.Pipeline;
import gov.usgs.ngwmn.dm.io.TeeOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

public class DataBroker {

	private DataFetcher harvester;
	private DataFetcher retriever;

	private DataLoader  loader;
	
	
	public void fetchWellData(Specifier spec, OutputStream out) throws Exception {
		Pipeline pipe = new Pipeline();

		check(spec);
		
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
	
	void check(Specifier spec) throws Exception {
		if (retriever == null && harvester == null) 
			throw new NullPointerException("At least one Data Fetcher is required");
		Specifier.check(spec);
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
}
