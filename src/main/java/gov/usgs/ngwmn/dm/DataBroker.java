package gov.usgs.ngwmn.dm;

import gov.usgs.ngwmn.dm.cache.Specifier;
import gov.usgs.ngwmn.dm.io.Pipeline;
import gov.usgs.ngwmn.dm.io.TeeOutputStream;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class DataBroker {

	private DataFetcher harvester;
	private DataFetcher retriever;

	private DataLoader  loader;
	
	private Logger logger = LoggerFactory.getLogger(this.getClass());
	
	public void fetchWellData(Specifier spec, OutputStream out) throws Exception {
		Pipeline pipe = new Pipeline();

		check(spec);
		
		pipe.setOutputStream(out);
		boolean success = configureInput(retriever, spec, pipe);
		
		if ( ! success) {
			// TODO Perhaps loader interface should be changed so it gets a Pipeline
			// onto which it splices its output, so it can record statistics after 
			// load is finished.
			out = new TeeOutputStream(out, loader.getOutputStream(spec));
			pipe.setOutputStream(out);
			success = configureInput(harvester, spec, pipe); 
		}
		
		if ( ! success) {
			signalDataNotFoundMsg(spec, pipe);
		}
		pipe.invoke();
		logger.info("Completed operation for {} result {}", spec, pipe.getStatistics());
	}
	
	private void signalDataNotFoundMsg(Specifier spec, Pipeline pipe) throws Exception {
		logger.warn("No data found for {}", spec);
		throw new Exception("No data found");
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
	
	boolean configureInput(DataFetcher dataFetcher, Specifier spec, Pipeline pipe) throws Exception {
		if (dataFetcher != null) {
			boolean v = dataFetcher.configureInput(spec, pipe);
			pipe.getStatistics().setCalledBy(dataFetcher.getClass());
			return v;
		}
		return false;
	}
	
	// to be replaced with util function - apache commons?
	boolean isEmpty(String string) {
		return string == null || string.length()==0;
	}
}
