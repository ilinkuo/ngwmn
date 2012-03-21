package gov.usgs.ngwmn.dm;

import gov.usgs.ngwmn.dm.cache.Specifier;
import gov.usgs.ngwmn.dm.io.Pipeline;


public interface DataFetcher {
	boolean fetchWellData(Specifier spec, Pipeline pipe) throws Exception;
}
