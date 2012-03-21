package gov.usgs.ngwmn;

import gov.usgs.ngwmn.dm.cache.Specifier;


public interface DataFetcher {
	boolean fetchWellData(Specifier spec, Pipeline pipe) throws Exception;
}
