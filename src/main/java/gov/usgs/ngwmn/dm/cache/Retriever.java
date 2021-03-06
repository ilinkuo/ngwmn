package gov.usgs.ngwmn.dm.cache;

import gov.usgs.ngwmn.dm.DataFetcher;
import gov.usgs.ngwmn.dm.io.Pipeline;

public class Retriever implements DataFetcher {
	private Cache cache;
	
	public Retriever(Cache c) {
		this.cache = c;
	}

	@Override
	public boolean configureInput(Specifier spec, Pipeline pipe)
			throws Exception 
	{
		if (cache.contains(spec)) {
			pipe.getStatistics().setCalledBy(this.getClass());
			return cache.fetchWellData(spec, pipe); 
		}
		return false;
	}
	
}
