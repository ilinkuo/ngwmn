package gov.usgs.ngwmn.dm;

import gov.usgs.ngwmn.dm.cache.Specifier;
import gov.usgs.ngwmn.dm.io.Pipeline;


public interface DataFetcher {
	/** Sets up  input and invoker side of pipeline.
	 * 
	 * @param spec
	 * @param pipe
	 * @return
	 * @throws Exception
	 */
	boolean fetchWellData(Specifier spec, Pipeline pipe) throws Exception;
}
